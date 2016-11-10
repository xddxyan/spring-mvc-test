package com.metasoft.service;

import java.util.Base64;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metasoft.model.Constant;
import com.metasoft.model.Session;
import com.metasoft.model.User;
import com.metasoft.model.exception.GeneralException;
import com.metasoft.service.dao.UserDaoService;
import com.metasoft.util.RsaUtils;

@Service
public class UserService{
	@Autowired
	private UserDaoService userDaoService;
	@Autowired
	private SessionService sessionService;

	private static final String kUsernameRegex = "^(?!\\d+$)[a-z0-9]{6,20}$";
	private static final String kPasswdRegex = "^[A-Za-z0-9]{6,16}$";
	private static final String kBlankRegex = "^\\s*$";

	/**
	 * 注册新用户
	 * @param user
	 * @param sid
	 * @throws GeneralException 
	 */
	public void register(User user, String sid) throws GeneralException {
		byte[] passwdBytes = Base64.getDecoder().decode(user.getPassword());
		user.setPassword(RsaUtils.decryptWithPkcs8(passwdBytes, RsaUtils.gPrivateKey));
		user.setCreatedate(System.currentTimeMillis());
		userDaoService.register(user);
		if(sid != null){
			setSession(sid, user);
		}
	}

	/**
	 * 验证用户
	 * @param username
	 * @param passwd
	 * @param sid
	 * @return
	 */
	public User authenticate(String username, String passwd, String sid) {
		User user = userDaoService.getUserByName(username);
		if (user == null || sid == null) {
			return null;
		}
		//String value = DigestUtils.sha512Hex(user.getPassword());
		byte[] passwdBytes = Base64.getDecoder().decode(passwd);
		passwd = RsaUtils.decryptWithPkcs8(passwdBytes, RsaUtils.gPrivateKey);
		if (user.getPassword().equals(passwd)) {
			setSession(sid, user);
			return user;
		}
		return null;
	}
	
	/**
	 * 更改密码
	 * @param uid
	 * @param passwd
	 */
	public void changePassword(long uid, String passwd) {
		User user = userDaoService.selectById(uid);
		user.setPassword(passwd);
		userDaoService.update(user);
	}
	
	/**
	 * 检测密码
	 * @param originPwd
	 * @param passwd
	 * @param repeatPwd
	 * @return
	 */
	public String checkPasswd(String originPwd, String passwd, String repeatPwd){
		if (originPwd == null || originPwd.isEmpty() || Pattern.matches(kBlankRegex, originPwd)) {
			return "empty_originpwd";
		} else if (passwd == null || passwd.isEmpty()
				|| Pattern.matches(kBlankRegex, passwd)) {
			return "empty_passwd";
		} else if (repeatPwd == null || repeatPwd.isEmpty()
				|| Pattern.matches(kBlankRegex, repeatPwd)) {
			return "empty_repeat";
		} else if (!Pattern.matches(kPasswdRegex, passwd)) {
			return "invalid_passwd";
		} else if (!passwd.equals(repeatPwd)) {
			return "repeat_not_match";
		//} else if (!DigestUtils.sha512Hex(oldp + user.getSalt()).equals(
		//		user.getPassword())) {
		//	return "8";
		}
		return "ok";
	}
	
	/**
	 * 检测用户名
	 * @param username
	 * @return
	 */
	public String checkUsername(String username){
		if (Pattern.matches(kBlankRegex, username) || username == null || username.isEmpty()) {
			return "empty_username";
		}
		
		if (Pattern.matches(kUsernameRegex, username)){
			return "ok";
		}
		
		return "invalid_username";
	}
	
	/**
	 * 将session 放入redis中
	 * @param sid
	 * @param user
	 */
	public void setSession(String sid, User user){
		Session session = new Session();
		session.setId(sid);
		session.setUserid(Long.toString(user.getId()));
		session.setUsername(user.getUsername());
		sessionService.setSession(session);
	}
}
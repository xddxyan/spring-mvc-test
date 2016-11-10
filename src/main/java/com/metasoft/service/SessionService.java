package com.metasoft.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.metasoft.model.Constant;
import com.metasoft.model.Session;

@Component
public class SessionService {
	private ThreadLocal<HttpServletRequest> sessionIdLocal = new ThreadLocal<HttpServletRequest>();
	@Value("${session.expired}")
	public int sessionExpired;
	//@Autowired
	//private JedisService jedisService;

	public HttpServletRequest get() {
		return sessionIdLocal.get();
	}

	public void bind(HttpServletRequest req) {
		sessionIdLocal.set(req);
	}

	public void unbind() {
		sessionIdLocal.remove();
	}

	public void setSession(Session session) {
		HttpServletRequest request = get();
		HttpSession httpSession = request.getSession();
		httpSession.setAttribute("session", session);
		/*Map<String, String> map = new HashMap<String, String>();
		map.put("uid", session.getUid());
		map.put("uname", session.getUsername());
		map.put("power", String.valueOf(session.getPower()));
		//设置redis超时
		jedisService.hmset(session.getSid(), map);
		jedisService.expire(session.getSid(), sessionExpired);*/
	}

	public void removeSession() {
		setSession(null);
		//if(null!=session)
			//jedisService.del(session.getSid());
	}

	public Session getSession() {
		HttpServletRequest request = get();
		HttpSession httpSession = request.getSession();
		Session session = (Session) httpSession.getAttribute("session");
		return session;
		/*String sessionId = GetCookieName(request);
		Map<String, String> map = jedisService.hgetAll(sessionId);
		if (!map.isEmpty()) {
			Session session = new Session();
			session.setSid(sessionId);
			session.setUid(map.get("uid"));
			session.setUsername(map.get("uname"));
			String power = map.get("power");
			session.setPower(Long.valueOf(power==null?"0":power));
			return session;
		}*/
	}

	public String getSessionId() {
		return GetByCookieName(get());
	}
	
	public static Cookie GetCookie(String sid, int expiry) {
		Cookie cookie = new Cookie(Constant.COOKIE_NAME, sid);
		cookie.setPath("/");
		cookie.setHttpOnly(false);
		cookie.setMaxAge(expiry);
		return cookie;
	}
	public static String GetByCookieName(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Constant.COOKIE_NAME)) {
					String value = cookie.getValue();
					if (!value.isEmpty()) {
						return value;
					}
					return null;
				}
			}
		}
		return null;
	}

}
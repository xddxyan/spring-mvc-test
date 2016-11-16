package com.metasoft.service.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.metasoft.model.User;
import com.metasoft.model.exception.GeneralException;
import com.metasoft.model.mapper.UserMapper;
import com.metasoft.model.mybatis.GenericDaoService;

@Service
@Transactional(readOnly = true,propagation = Propagation.NOT_SUPPORTED)
public class UserDaoService extends GenericDaoService<User, UserMapper> {

	public User getUserByName(String username) {
		return mapper.getUserByName(username);
	}
	
	//@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation=Isolation.SERIALIZABLE)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
	public void register(User user) throws GeneralException{
		User duplicateUser = getUserByName(user.getUsername());
		if(null != duplicateUser)
			throw new GeneralException(0,"duplicate.user");
		super.insert(user);
	}
}
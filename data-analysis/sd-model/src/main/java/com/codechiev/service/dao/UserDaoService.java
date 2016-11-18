package com.codechiev.service.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codechiev.model.User;
import com.codechiev.model.exception.GeneralException;
import com.codechiev.model.mapper.UserMapper;
import com.codechiev.model.mybatis.GenericDaoService;

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
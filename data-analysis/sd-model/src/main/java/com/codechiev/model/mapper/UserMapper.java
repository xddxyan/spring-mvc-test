package com.codechiev.model.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.codechiev.model.User;
import com.codechiev.model.mybatis.GenericMapper;

public interface UserMapper extends GenericMapper<User>{

	 @Select("SELECT * FROM p_users WHERE username = #{username}")
	 User getUserByName(@Param("username") String username);

}

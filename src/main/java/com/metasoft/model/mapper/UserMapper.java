package com.metasoft.model.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.metasoft.model.User;
import com.metasoft.model.mybatis.GenericMapper;

public interface UserMapper extends GenericMapper<User>{

	 @Select("SELECT * FROM p_users WHERE username = #{username}")
	 User getUserByName(@Param("username") String username);

}

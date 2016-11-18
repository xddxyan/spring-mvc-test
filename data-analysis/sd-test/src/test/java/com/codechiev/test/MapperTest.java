package com.codechiev.test;

import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codechiev.service.dao.UserDaoService;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"/application-core.xml"})  
@ActiveProfiles("development")
public class MapperTest {

	@PostConstruct
	private void init(){

	}

	@Autowired
	UserDaoService dao;
	@Test
	public void mapper(){
		List<com.codechiev.model.User> users = dao.selectByIdAsc(0, 1);
		System.out.println(new Gson().toJson(users));
	}
	
}

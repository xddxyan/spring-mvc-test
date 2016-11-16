package com.test.codechiev;

import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.metasoft.service.dao.UserDaoService;

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
		List<com.metasoft.model.User> users = dao.selectByIdAsc(0, 1);
		System.out.println(new Gson().toJson(users));
	}
	
}

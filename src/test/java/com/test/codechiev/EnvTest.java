package com.test.codechiev;

import javax.annotation.PostConstruct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"/application-debug.xml"})  
@ActiveProfiles("production")
public class EnvTest {

    @Autowired
    Environment env;

	@PostConstruct
	private void init(){

	}

	@Test
	public void env(){

		System.out.println(env.toString());
	}

}
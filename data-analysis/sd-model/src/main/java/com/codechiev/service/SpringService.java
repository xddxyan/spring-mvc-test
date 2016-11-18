package com.codechiev.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SpringService implements ApplicationContextAware {
	
	Logger log = LoggerFactory.getLogger(SpringService.class);
	
	private static ApplicationContext applicationContext;
	public static boolean development=false;

	/*
	 * 保存web.xml中配置的spring context
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringService.applicationContext = applicationContext;
	}
	
	@PostConstruct 
	public void init(){
		Environment env = SpringService.getBean(Environment.class);
        for(String en: env.getActiveProfiles() ) {
        	log.info(en);
			if(en.equals("development") ){
				SpringService.development = true;
			}
        }
	}

	public static ApplicationContext getApplicationContext() {

		return applicationContext;
	}

	public static Object getBean(String name) {
		if (applicationContext == null) {
			return null;
		}
		return applicationContext.getBean(name);
	}

	public static <T> T getBean( Class<T> requiredType) {
		if (applicationContext == null) {
			return null;
		}
		return applicationContext.getBean(requiredType);
	}

	public static String[] getBeanNamesOfType(Class<?> type) {
		if (applicationContext == null) {
			return new String[0];
		}
		return applicationContext.getBeanNamesForType(type);
	}


}
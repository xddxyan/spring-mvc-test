package com.metasoft.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

@Service
public class LocalizationService{
	@Autowired
	private MessageSource messageSource;

	/**
	 * @param key "this is something"
	 * @param new String[] { "foo", "bar" }
	 * @return "zhe shi foo bar"
	 */
	public String getLocalString(String key, String[] strs) {
		try{
			return messageSource.getMessage(key, strs, Locale.CHINA);
		}catch(NoSuchMessageException e){
			return key;
		}
	}
	
	public String getLocalString(String key) {
		try{
			return messageSource.getMessage(key, null, Locale.CHINA);
		}catch(NoSuchMessageException e){
			return key;
		}
	}

}

package com.codechiev.service;

import org.springframework.stereotype.Service;

import com.codechiev.util.RandomUtils;

@Service
public class CaptchaService{
	//@Autowired
	private JedisService jedisService;
	private static final int gExpireSecs = 360;
	private static final String kCaptchaKey = "captcha";

	public void putTouchCaptcha(String captcha, String sid) {
		String key = "touchcaptcha"+sid;
		jedisService.set(key, captcha);
		jedisService.expire(key, gExpireSecs);
	}
	public String getTouchCaptcha(String sid) {
		String key = "touchcaptcha"+sid;
		return jedisService.get(key);
	}
	
	public String getCaptcha(String key) {
		String value = RandomUtils.GetRandomString().substring(0, 3);
		jedisService.hset(kCaptchaKey, key, value);
		return value;
	}

	public boolean checkCaptcha(String key, String captcha) {
		String value = jedisService.hget(kCaptchaKey, key);
		if (value != null && value.equals(captcha)) {
			return true;
		}
		return false;
	}

	public void delCaptcha(String field) {
		jedisService.hdel(kCaptchaKey, field);
	}

}
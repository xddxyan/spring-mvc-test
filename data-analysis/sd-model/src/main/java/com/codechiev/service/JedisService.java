package com.codechiev.service;

import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisService {

	private JedisPool pool;
	
	public JedisService(JedisPoolConfig config, String host, int port, int timeout, String passwd){
		pool = new JedisPool(config, host, port, timeout, passwd);
	}
	
	public void destroy(){
		pool.destroy();
	}

	public String get(String key){
		Jedis jedis = null;
		try {
		  jedis = pool.getResource();
		  return jedis.get(key);
		} finally {
		  if (jedis != null) {
		    jedis.close();
		  }
		}
	}
	public String hget(String key, String field){
		Jedis jedis = null;
		try {
		  jedis = pool.getResource();
		  return jedis.hget(key, field);
		} finally {
		  if (jedis != null) {
		    jedis.close();
		  }
		}
	}
	public void hset(String key, String field, String val){
		Jedis jedis = null;
		try {
		  jedis = pool.getResource();
		  jedis.hset(key, field, val);
		} finally {
		  if (jedis != null) {
		    jedis.close();
		  }
		}
	}
	public void set(String key, String val){
		Jedis jedis = null;
		try {
		  jedis = pool.getResource();
		  jedis.set(key, val);
		} finally {
		  if (jedis != null) {
		    jedis.close();
		  }
		}
	}
	public Map<String, String> hgetAll(String key){
		Jedis jedis = null;
		try {
		  jedis = pool.getResource();
		  return jedis.hgetAll(key);
		} finally {
		  if (jedis != null) {
		    jedis.close();
		  }
		}
	}
	public void hmset(String key, Map<String, String> hash){
		Jedis jedis = null;
		try {
		  jedis = pool.getResource();
		  jedis.hmset(key, hash);
		} finally {
		  if (jedis != null) {
		    jedis.close();
		  }
		}
	}
	public void expire(String key, int expired){
		Jedis jedis = null;
		try {
		  jedis = pool.getResource();
		  jedis.expire(key, expired);
		} finally {
		  if (jedis != null) {
		    jedis.close();
		  }
		}
	}
	public void del(String key){
		Jedis jedis = null;
		try {
		  jedis = pool.getResource();
		  jedis.del(key);
		} finally {
		  if (jedis != null) {
		    jedis.close();
		  }
		}
	}
	public void hdel(String key, String field){
		Jedis jedis = null;
		try {
		  jedis = pool.getResource();
		  jedis.hdel(key, field);
		} finally {
		  if (jedis != null) {
		    jedis.close();
		  }
		}
	}
}
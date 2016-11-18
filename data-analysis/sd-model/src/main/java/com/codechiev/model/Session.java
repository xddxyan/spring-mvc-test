package com.codechiev.model;

public class Session {
	private String id;//session id
	private String userid;//user id
	private String username;
	//private long authority; 

	public String getUsername() {
		return username;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
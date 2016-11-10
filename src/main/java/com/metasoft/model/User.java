package com.metasoft.model;

import org.copycat.framework.annotation.Column;
import org.copycat.framework.annotation.AutoId;
import org.copycat.framework.annotation.Table;

@Table("p_users")
public class User {
	@AutoId("p_users_seq")
	@Column("id")
	private long id;
	@Column("power")
	private long power;
	@Column("username")
	private String username;
	@Column("password")
	private String password;
	@Column("email")
	private String email;
	@Column("createdate")
	private long createdate;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}

	public long getPower() {
		return power;
	}

	public void setPower(long power) {
		this.power = power;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
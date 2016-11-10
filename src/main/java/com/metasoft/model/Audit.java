package com.metasoft.model;

public class Audit {
	private long id;
	private long power;
	private String name;
	private String url;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPower() {
		return power;
	}

	public void setPower(long power) {
		this.power = power;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
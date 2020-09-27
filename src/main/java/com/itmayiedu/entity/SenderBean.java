package com.itmayiedu.entity;

import java.util.List;

public class SenderBean {
	private String name;
	private String className;
	private List<String> persistencer;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<String> getPersistencer() {
		return persistencer;
	}
	public void setPersistencer(List<String> persistencer) {
		this.persistencer = persistencer;
	}
	
	
}

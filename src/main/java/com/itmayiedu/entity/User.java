package com.itmayiedu.entity;

import java.io.Serializable;

public class User implements Serializable{

	private Integer id;

	private String name;

	private Integer age;

	public User(){}
	public User(Integer id,String userName,Integer userSex){
		this.id=id;
		this.name=userName;
		this.age=userSex;
	}
	// ..get/set方法
	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public Integer getAge() {

		return age;
	}

	public void setAge(Integer age) {

		this.age = age;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
}

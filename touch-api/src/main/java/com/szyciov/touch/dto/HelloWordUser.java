package com.szyciov.touch.dto;

public class HelloWordUser {

	private String account;
	
	private Integer age;
	
	private String nickname;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "账户->"+account+" ； 年龄->"+age+" ； 昵称->"+nickname;
	}
	
}

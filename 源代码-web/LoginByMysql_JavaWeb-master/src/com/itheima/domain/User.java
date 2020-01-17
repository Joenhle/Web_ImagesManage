package com.itheima.domain;

import java.util.Date;

public class User {

	
	private String username;
	private String password;
	private String email;
	private String image_number;
	private String register_date;
	private String jurisdiction;
	
	
	public String getJurisdiction() {
		return jurisdiction;
	}
	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getImage_number() {
		return image_number;
	}
	public void setImage_number(String image_number) {
		this.image_number = image_number;
	}
	public String getRegister_date() {
		return register_date;
	}
	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}
	
}

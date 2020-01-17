package com.itheima.web.formbean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

//FormBean：与用户的输入对应
//验证用户的输入
public class UserFormBean {
	private String username;
	private String password;
	private String repassword;
	private String email;
	//封装错误信息key=输入域的名称 value=错误消息
	private Map<String, String> errors = new HashMap<String, String>();
	
	public Map<String, String> getErrors() {
		return errors;
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
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	//验证
	public boolean validate(){
		boolean isOk = true;
		
		/*
		 * username:不能为空，且必须是3~8位的字母
		 * password:不能为空，且必须是3~8为的数字
		 * repassword:必须与password一致
		 * birthday:必须是一个日期类型
		 * email:必须符合email的书写规范
		 */
		if(username==null||username.trim().equals("")){
			isOk = false;
			errors.put("username", "用户名不能为空");
		}else{
			if(!username.matches("[a-zA-Z]{3,8}")){
				isOk = false;
				errors.put("username", "用户名必须是3~8位的字母");
			}
				
		}
		
		if(password==null||password.trim().equals("")){
			isOk = false;
			errors.put("password", "密码不能为空");
		}else{
			if(!password.matches("\\d{3,8}")){
				isOk = false;
				errors.put("password", "密码必须是3~8位的数字");
			}
		}
		if(repassword!=null){
			if(!password.equals(repassword)){
				isOk = false;
				errors.put("repassword", "两次密码必须一致");
			}
		}
	
		if(email!=null){
			if(!email.matches("\\w+@\\w+(\\.\\w+)+")){
				isOk = false;
				errors.put("email", "请输入正确的邮箱");
			}
		}
		return isOk;
	}
}

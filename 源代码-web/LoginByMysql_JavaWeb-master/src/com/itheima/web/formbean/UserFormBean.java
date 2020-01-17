package com.itheima.web.formbean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

//FormBean�����û��������Ӧ
//��֤�û�������
public class UserFormBean {
	private String username;
	private String password;
	private String repassword;
	private String email;
	//��װ������Ϣkey=����������� value=������Ϣ
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
	//��֤
	public boolean validate(){
		boolean isOk = true;
		
		/*
		 * username:����Ϊ�գ��ұ�����3~8λ����ĸ
		 * password:����Ϊ�գ��ұ�����3~8Ϊ������
		 * repassword:������passwordһ��
		 * birthday:������һ����������
		 * email:�������email����д�淶
		 */
		if(username==null||username.trim().equals("")){
			isOk = false;
			errors.put("username", "�û�������Ϊ��");
		}else{
			if(!username.matches("[a-zA-Z]{3,8}")){
				isOk = false;
				errors.put("username", "�û���������3~8λ����ĸ");
			}
				
		}
		
		if(password==null||password.trim().equals("")){
			isOk = false;
			errors.put("password", "���벻��Ϊ��");
		}else{
			if(!password.matches("\\d{3,8}")){
				isOk = false;
				errors.put("password", "���������3~8λ������");
			}
		}
		if(repassword!=null){
			if(!password.equals(repassword)){
				isOk = false;
				errors.put("repassword", "�����������һ��");
			}
		}
	
		if(email!=null){
			if(!email.matches("\\w+@\\w+(\\.\\w+)+")){
				isOk = false;
				errors.put("email", "��������ȷ������");
			}
		}
		return isOk;
	}
}

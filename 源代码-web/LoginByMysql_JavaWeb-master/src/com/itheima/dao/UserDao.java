package com.itheima.dao;

import java.awt.Image;

import com.itheima.domain.Images;
import com.itheima.domain.User;

public interface UserDao {
	
	void addUser(User user);
	
	void deleteUser(String username);
	
    void update_image_number(String username,String image_number);
	
	User findUser(String username);

	User findUser(String username,String password);
	
	User findUser_email_and_password(String email,String password);
	
    User findUser_byemail(String email);
    
    String get_all_user_info();
    
    void update_user(User user);
}

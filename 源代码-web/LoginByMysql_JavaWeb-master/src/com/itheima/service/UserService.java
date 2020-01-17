package com.itheima.service;

import java.awt.Image;

import com.itheima.domain.Images;
import com.itheima.domain.User;
import com.itheima.exception.UserExistsException;

public interface UserService {
	
	void register(User user) throws UserExistsException;
	
	User login(String username_or_eamil,String password);
	
}

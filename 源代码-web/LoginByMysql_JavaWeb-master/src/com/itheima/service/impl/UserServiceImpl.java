package com.itheima.service.impl;

import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.dao.impl.UserDaoMysqlImpl;
import com.itheima.domain.Images;
import com.itheima.domain.Logs;
import com.itheima.domain.User;
import com.itheima.exception.UserExistsException;
import com.itheima.service.UserService;
import com.itheima.util.Log;


public class UserServiceImpl implements UserService {
	
	private UserDao dao = new UserDaoMysqlImpl();
	public User login(String username_or_eamil, String password) {
		
		 if(dao.findUser(username_or_eamil, password)!=null) {
			 return dao.findUser(username_or_eamil, password);
		 }else if(dao.findUser_email_and_password(username_or_eamil, password)!=null) {
			 return dao.findUser_email_and_password(username_or_eamil, password);
		 }else {
			 return null;
		 }
	}

	public void register(User user) throws UserExistsException {
		//先判断用户是否已经存在
		String username = user.getUsername();
		User u = dao.findUser(username);
		if(u!=null)
			throw new UserExistsException("用户名已经存在");
		
		//不存在才进行注册
		//user.setPassword(MD5.encode(user.getPassword()));//对密码进行加密
		
		user.setPassword(user.getPassword());
		user.setImage_number(String.valueOf(0));
	    Date date=new Date();
	    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
	    user.setRegister_date(dateFormat.format(date));
	    user.setJurisdiction("普通用户");
	    
		dao.addUser(user);
		Logs logs=new Logs();
		logs.setPath("C:\\Users\\\\dell\\Desktop\\LoginByMysql_JavaWeb-master(3)\\LoginByMysql_JavaWeb-master\\WebContent\\images_user\\timg.jpeg");
		logs.setOperation(""+user.getUsername()+"完成了注册");
		logs.setOperation_time(dateFormat.format(date));
		Log.addLog(logs);
		
	}
	
}

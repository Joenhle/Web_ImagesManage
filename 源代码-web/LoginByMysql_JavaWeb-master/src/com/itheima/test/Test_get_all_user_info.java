package com.itheima.test;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoMysqlImpl;
import com.itheima.domain.User;

public class Test_get_all_user_info {

	public static void main(String[] args) {
        UserDao userDao=new UserDaoMysqlImpl();
        userDao.deleteUser("hjh");
	}

}

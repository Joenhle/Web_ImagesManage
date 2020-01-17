package com.itheima.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.itheima.domain.User;
import com.itheima.exception.UserExistsException;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;

public class UserServiceTest {
	private UserService service = new UserServiceImpl();
	@Test
	public void testLogin() {
		User u = service.login("yyk", "sorry1");
		Assert.assertNull(u);
	}

	@Test
	public void testRegister() throws UserExistsException {
		User user = new User();

		user.setUsername("yyk");
		user.setPassword("sorry");
		user.setEmail("yyk@itcast.cn");

		service.register(user);
	}

}

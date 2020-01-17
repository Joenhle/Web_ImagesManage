package com.itheima.web.controler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.domain.Logs;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.util.Log;

public class LoginServlet extends HttpServlet {
	private UserService service = new UserServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//根据用户名和密码检测用户是否存在
		
		request.setCharacterEncoding("UTF-8");//客户端网页我们控制为UTF-8
		
		request.getSession().setAttribute("valid",true);
		request.getSession().setAttribute("is_user_info_valid", true);
		
		String username_or_email = request.getParameter("username_or_email");
		String password = request.getParameter("password");
	
		User user = service.login(username_or_email, password);
		boolean valid=true;
		if(user==null){
			//不存在，转向原消息页面，提示用户名或密码不正确
			System.out.println("用户不存在");
			valid=false;
			request.getSession().setAttribute("valid",valid);
			request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
			return;
		}
		//存在，把USER放到session中转向主页
		System.out.println("用户存在");
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		Logs logs=new Logs();
		logs.setPath("C:\\tomcat\\apache-tomcat-7.0.96\\webapps\\day10_login_Mysql\\images_user\\timg.jpeg");
		logs.setOperation(""+user.getUsername()+"完成了登录");
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
		logs.setOperation_time(dateFormat.format(date));
		Log.addLog(logs);
		
		request.getRequestDispatcher("/WEB-INF/pages/easyindex.jsp").forward(request, response);
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

package com.itheima.web.controler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoMysqlImpl;
import com.itheima.domain.Logs;
import com.itheima.domain.User;
import com.itheima.util.Log;

/**
 * Servlet implementation class HouDuan_updateuser
 */
@WebServlet("/HouDuan_updateuser")
public class HouDuan_updateuser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HouDuan_updateuser() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");//客户端网页我们控制为UTF-8
		User user_guanliyuan=(User) request.getSession().getAttribute("user");
		
		User user=new User();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));		
		user.setEmail(request.getParameter("email"));
		user.setImage_number(request.getParameter("image_number"));
		user.setJurisdiction(request.getParameter("jurisdiction"));
		UserDao userDao=new UserDaoMysqlImpl();
		
	    Logs logs=new Logs();
		logs.setPath("C:\\tomcat\\apache-tomcat-7.0.96\\webapps\\day10_login_Mysql\\images_user\\timg.jpeg");
		logs.setOperation(""+user_guanliyuan.getUsername()+"管理员在后端修改了"+user.getJurisdiction()+user.getUsername()+"的数据"+"");
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logs.setOperation_time(df.format(date));
		Log.addLog(logs);
		
		userDao.update_user(user);
		
		
		request.getRequestDispatcher("/WEB-INF/pages/ManagerUsers.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

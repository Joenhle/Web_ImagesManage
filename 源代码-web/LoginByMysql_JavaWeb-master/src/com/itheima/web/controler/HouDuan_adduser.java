package com.itheima.web.controler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoMysqlImpl;
import com.itheima.domain.Logs;
import com.itheima.domain.User;
import com.itheima.exception.UserExistsException;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.util.Log;

/**
 * Servlet implementation class HouDuan_adduser
 */
@WebServlet("/HouDuan_adduser")
public class HouDuan_adduser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public HouDuan_adduser() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");//客户端网页我们控制为UTF-8
		User user_guanliyuan=(User) request.getSession().getAttribute("user");
		
		request.getSession().setAttribute("is_info_valid","true");
		UserService service = new UserServiceImpl();
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		User user=new User();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setEmail(request.getParameter("email"));
		user.setImage_number(request.getParameter("image_number"));
		user.setJurisdiction(request.getParameter("jurisdiction"));
		System.out.println(request.getParameter("jurisdiction"));
	    user.setRegister_date(df.format(date));
	    UserDao userDao=new UserDaoMysqlImpl();
		try{
			service.register(user);
			Logs logs=new Logs();
			logs.setPath("C:\\tomcat\\apache-tomcat-7.0.96\\webapps\\day10_login_Mysql\\images_user\\timg.jpeg");
			logs.setOperation(""+user_guanliyuan.getUsername()+"管理员在后端创建了"+user.getJurisdiction()+user.getUsername()+"");
			logs.setOperation_time(df.format(date));
			Log.addLog(logs);
			
			request.getRequestDispatcher("/WEB-INF/pages/ManagerUsers.jsp").forward(request, response);
			return;
		}catch(UserExistsException e){
			//5、用户存在:UserExistsException
			//回显数据，并提示用户用户名已经存在
			request.getSession().setAttribute("is_info_valid","用户名已存在");
			request.getRequestDispatcher("/WEB-INF/pages/ManagerUsers.jsp").forward(request, response); 
		}catch(Exception e){
			e.printStackTrace();
			request.getSession().setAttribute("is_info_valid","格式不正确");
			request.getRequestDispatcher("/WEB-INF/pages/ManagerUsers.jsp").forward(request, response); 
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}

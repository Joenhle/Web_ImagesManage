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
import com.itheima.util.Image;
import com.itheima.util.Log;

/**
 * Servlet implementation class HouDuan_deleteimage
 */
@WebServlet("/HouDuan_deleteimage")
public class HouDuan_deleteimage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HouDuan_deleteimage() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");// 客户端网页我们控制为UTF-8

		User user_session = (User) request.getSession().getAttribute("user");
		if (request.getAttribute("quanxian_valid") == null || request.getAttribute("quanxian_valid") != "true") {
			request.setAttribute("quanxian_valid", "\"true\"");
		}

		String[] images_info = request.getParameter("deleted_image").split(",");
		String url_path = images_info[1];
		url_path = url_path.substring(url_path.lastIndexOf("/") + 1);
		String real_path = "C:\\tomcat\\apache-tomcat-7.0.96\\webapps\\day10_login_Mysql\\images_user\\"
				+ url_path;

		if (user_session.getJurisdiction().equals("管理员") || user_session.getUsername().equals(images_info[3])) {
			if (Image.delete_image(real_path) == true) {
				
				Logs logs=new Logs();
				logs.setPath(real_path);
				logs.setOperation(""+user_session.getJurisdiction()+user_session.getUsername()+"在后台删除了图片");
				Date date=new Date();
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
				logs.setOperation_time(dateFormat.format(date));
				
				Log.addLog(logs);
				
				UserDao userDao = new UserDaoMysqlImpl();
				User user = userDao.findUser(images_info[3]);
				userDao.update_image_number(user.getUsername(),
						String.valueOf(Integer.parseInt(user.getImage_number()) - 1));
			}
		}else {
			request.setAttribute("quanxian_valid", "\"抱歉你的权限不够\"");
		}

		request.getRequestDispatcher("/WEB-INF/pages/ManageImages.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}

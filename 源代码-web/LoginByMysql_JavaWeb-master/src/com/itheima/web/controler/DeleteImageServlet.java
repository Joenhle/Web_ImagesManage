package com.itheima.web.controler;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoMysqlImpl;
import com.itheima.domain.Logs;
import com.itheima.domain.User;
import com.itheima.util.Image;
import com.itheima.util.Log;

/**
 * Servlet implementation class DeleteImageServlet
 */
@WebServlet("/DeleteImageServlet")
public class DeleteImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public DeleteImageServlet() {
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();
		User user=(User) request.getSession().getAttribute("user");
		List<String> arr=(List<String>) session.getAttribute("image_list");
		request.setCharacterEncoding("UTF-8");//客户端网页我们控制为UTF-8
		String []paramValues = request.getParameterValues("src");
		for (int i = 0; i < paramValues.length; i++) {
			String Src=paramValues[i].substring(paramValues[i].lastIndexOf("/")+1);
			for(int j=0;j<arr.size();++j) {
			
				if(Src.equals(arr.get(j))) {
				    arr.remove(j);
				}
			}
			Src="C:\\tomcat\\apache-tomcat-7.0.96\\webapps\\day10_login_Mysql\\images_user"+"\\"+Src;
			
			if(Image.delete_image(Src)==true) {
				Logs logs=new Logs();
				logs.setPath(Src);
				logs.setOperation(""+user.getUsername()+"删除了图片");
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				logs.setOperation_time(df.format(date));
				Log.addLog(logs);
			};
		}
		
		
		user.setImage_number(String.valueOf(Integer.parseInt(user.getImage_number())-paramValues.length));
		UserDao userDao=new UserDaoMysqlImpl();
		userDao.update_image_number(user.getUsername(), user.getImage_number());
		request.getSession().setAttribute("user",user);
		
		session.setAttribute("image_list", arr);
		request.getRequestDispatcher("/WEB-INF/pages/user_deleteImages.jsp").forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

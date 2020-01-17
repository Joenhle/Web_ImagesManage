package com.itheima.web.controler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoMysqlImpl;
import com.itheima.domain.Images;
import com.itheima.domain.Logs;
import com.itheima.domain.User;
import com.itheima.util.Image;
import com.itheima.util.Log;

/**
 * Servlet implementation class HouDuan_updateImage
 */
@WebServlet("/HouDuan_updatei mage")
public class HouDuan_updateimage extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public HouDuan_updateimage() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");//客户端网页我们控制为UTF-8
		
		User user=(User) request.getSession().getAttribute("user");
		if (request.getAttribute("quanxian_valid") == null || request.getAttribute("quanxian_valid") != "true") {
			request.setAttribute("quanxian_valid", "\"true\"");
		}
		
		Images images=new Images();
		images.setOwner(request.getParameter("owner"));
		images.setCountry(request.getParameter("country"));
		images.setAcquisition_time(request.getParameter("acquisition_time"));
		images.setAcquisition_long(request.getParameter("acquisition_long"));
		images.setSatellite(request.getParameter("satellite"));
		images.setRemark(request.getParameter("remark"));
		images.setLongitude_range(request.getParameter("longitude_range"));
		images.setLatitude_range(request.getParameter("latitude_range"));
		images.setImage_category(request.getParameter("image_category"));
		images.setPath(request.getParameter("path"));
		
		
		if(user.getJurisdiction().equals("管理员") || user.getUsername().equals(images.getOwner()) ) {
			Image.updateImage(images);
			Logs logs=new Logs();
			String real_path=images.getPath().substring(images.getPath().lastIndexOf("/")+1);
			real_path="C:\\tomcat\\apache-tomcat-7.0.96\\webapps\\day10_login_Mysql\\images_user\\"+real_path;
			logs.setPath(real_path);
			logs.setOperation(""+user.getJurisdiction()+user.getUsername()+"在后台修改了图片信息");
			Date date=new Date();
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
			logs.setOperation_time(dateFormat.format(date));
			Log.addLog(logs);
			
		}else {
			request.setAttribute("quanxian_valid", "\"抱歉你的权限不够\"");
		}
				
		request.getRequestDispatcher("/WEB-INF/pages/ManageImages.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);
	}

}

package com.itheima.web.controler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoMysqlImpl;
import com.itheima.domain.User;
import com.itheima.util.JdbcUtil;
import com.itheima.util.Postman;

/**
 * Servlet implementation class ForgetServlet
 */
@WebServlet("/ForgetServlet")
public class ForgetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getSession().setAttribute("valid",true);
		request.getSession().setAttribute("is_user_info_valid", true);
		
	     String username_or_email=request.getParameter("username_or_email");
	     String email=new String();
	     boolean is_email=false;
	     if(username_or_email.indexOf("@")!=-1) {
	        is_email=true;
	     }
	     Connection conn = null;
		 PreparedStatement stmt = null;
		 ResultSet rs = null;
	     try {
	    	 String sql = "SELECT *FROM images WHERE ";
	    	 if(is_email==true) {
	    		 sql+="email='"+username_or_email+"' ";
	    	 }else {
	    		 sql+="username='"+username_or_email+"' ";
	    	 }
	    	 
	    	conn=JdbcUtil.getConnection();
	    	stmt=conn.prepareStatement(sql);
	    	rs = stmt.executeQuery();
	    	Postman.send_message_findback(rs.getString("email"));
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
	  
    	request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    	return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

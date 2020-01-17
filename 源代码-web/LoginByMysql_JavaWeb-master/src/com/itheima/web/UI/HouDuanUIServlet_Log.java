package com.itheima.web.UI;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HouDuanUIServlet_Log
 */
@WebServlet("/HouDuanUIServlet_Log")
public class HouDuanUIServlet_Log extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public HouDuanUIServlet_Log() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.getRequestDispatcher("/WEB-INF/pages/log.jsp").forward((ServletRequest)request, (ServletResponse)response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

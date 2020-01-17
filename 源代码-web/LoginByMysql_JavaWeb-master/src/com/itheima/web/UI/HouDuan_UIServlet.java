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
 * Servlet implementation class HouDuan_UIServlet
 */
@WebServlet("/HouDuan_UIServlet")
public class HouDuan_UIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public HouDuan_UIServlet() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.getRequestDispatcher("/WEB-INF/pages/ManagerUsers.jsp").forward((ServletRequest)request, (ServletResponse)response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

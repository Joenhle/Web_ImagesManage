package com.itheima.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.domain.User;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {

   
    public LoginFilter() {
        // TODO Auto-generated constructor stub 
    }

	
	public void destroy() {
		// TODO Auto-generated method stub
	}

	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        User user = (User)session.getAttribute("user");
        if(user != null){
            chain.doFilter(request,response);
        } else{
        	out.println("<script type=\"text/javascript\">alert(\"您还未登录，三秒后跳转至登录界面\");</script>");
            response.setHeader("refresh","3;"+request.getContextPath()+"/servlet/LoginUIServlet");
        }

	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}

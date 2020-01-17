package com.itheima.web.controler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import com.itheima.domain.Images;
import com.itheima.util.Image;
import com.itheima.util.JdbcUtil;

/**
 * Servlet implementation class IndexServlet
 * 用于实现主页导航栏的搜索
 */
@WebServlet("/IndexSearch_MenuServlet")
public class IndexSearch_MenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexSearch_MenuServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String key = new String();
		String value = new String();
		key = java.net.URLDecoder.decode(request.getParameter("key"), "utf-8");
		value = java.net.URLDecoder.decode(request.getParameter("value"), "utf-8");
		
		if(value.equals("所有")) {
			request.getSession().setAttribute("is_all_images", "true");
			request.getRequestDispatcher("/WEB-INF/pages/easyindex.jsp").forward(request, response);
			return;
		}
		
		if(value.equals("赤道")) {
			value="0S~0N";
		}else if(value.equals("北回归线")) {
			value="30N~30N";
		}else if(value.equals("南回归线")) {
			value="30S~30S";
		}
		
		List<Images> Images_List = new ArrayList<Images>();
		System.out.println(key);
		System.out.println(value);
		Images_List = Image.find_by_accurate(key, value);
		
		List<String> arr = new ArrayList<String>();

		if (Images_List != null) {
			for (int i = 0; i < Images_List.size(); ++i) {
				String temp = Images_List.get(i).getPath();
				temp = temp.substring(temp.lastIndexOf("\\") + 1);
				arr.add(temp);
				System.out.println(temp);
			}
		}else {
			System.out.println("空");
		}
		request.getSession().setAttribute("is_all_images", "false");
		request.getSession().removeAttribute("image_list");
		request.getSession().setAttribute("image_list", arr);
		request.getRequestDispatcher("/WEB-INF/pages/easyindex.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

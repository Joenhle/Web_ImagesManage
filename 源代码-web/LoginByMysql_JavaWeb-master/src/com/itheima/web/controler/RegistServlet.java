package com.itheima.web.controler;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import com.itheima.domain.User;
import com.itheima.exception.UserExistsException;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.util.FillFormBean;
import com.itheima.web.formbean.UserFormBean;
//处理用户注册
public class RegistServlet extends HttpServlet {
	private UserService service = new UserServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");//客户端网页我们控制为UTF-8
		System.out.println(request.getParameter("username"));
		request.getSession().setAttribute("valid",true);
		request.getSession().setAttribute("is_user_info_valid", true);
		
		boolean is_user_info_valid=true;
		//1、验证用户输入
		//1。1把请求中的数据封装到一个FormBean中
		UserFormBean formBean = FillFormBean.toFromBean(request,UserFormBean.class);
		
		
		/*if(!formBean.validate()){
			//2、验证不通过，跳转到输入页面。回显用户原来写的数据
			request.setAttribute("formBean", formBean);
			request.getRequestDispatcher("/WEB-INF/pages/regist.jsp").forward(request, response);
			return;
		}*/
		
		//3、验证通过：填充模型
		try{
			User user = new User();
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			//注册一个类型转换器，完成String类型的birthday到Date类型的Birthday
			BeanUtils.copyProperties(user, formBean);
			//基本数据类型和String类型
			//4、转换有错，给出提示。转向一个全局的消息页面
			service.register(user);
			HttpSession session=request.getSession();
			session.setAttribute("user", user);
			request.getRequestDispatcher("/WEB-INF/pages/easyindex.jsp").forward(request, response);
			return;
		}catch(UserExistsException e){
			//5、用户存在:UserExistsException
			//回显数据，并提示用户用户名已经存在
			is_user_info_valid=false;
			System.out.println("进");
			request.getSession().setAttribute("is_user_info_valid", is_user_info_valid);
			request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
			return;
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("message", "注册失败");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

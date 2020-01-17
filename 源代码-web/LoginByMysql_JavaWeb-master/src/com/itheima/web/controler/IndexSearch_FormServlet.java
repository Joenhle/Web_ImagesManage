package com.itheima.web.controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Images;
import com.itheima.domain.User;
import com.itheima.util.Check_Image_Info;
import com.itheima.util.Image;

/**
  * 实现主页的表单搜索功能
 */
@WebServlet("/IndexSearch_FormServlet")
   /*
        * 该servlet用于主页的form表单查询 
    * */
public class IndexSearch_FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public IndexSearch_FormServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        

		request.setCharacterEncoding("UTF-8");//客户端网页我们控制为UTF-8
		
		//如果客户端表单搜索格式不正确则进行提示回传
		if(request.getAttribute("is_image_info_valid")==null || request.getAttribute("is_image_info_valid")!="true") {
		   request.setAttribute("is_image_info_valid", "\"true\"");
			//session的多次相同变量赋值取最后一次
		}
		
		Enumeration<String> paramNames = request.getParameterNames();
		Images image=new Images();
		List<Images> images_list=new ArrayList<Images>();
		List<String> arr=new ArrayList<String>();
		User user=(User) request.getSession().getAttribute("user");
		
		
		while (paramNames.hasMoreElements()) {
			String paraName = paramNames.nextElement();
			String []paramValues = request.getParameterValues(paraName);
		    if(paramValues[0].length()==0 || paramValues[0].equals("未知")) {
		    	continue;
		    }
		    if(paraName.equals("image_category")) {
		    	image.setImage_category(paramValues[0]);
		    }else if(paraName.equals("country")) {
		    	image.setCountry(paramValues[0]);
		    }else if(paraName.equals("resolution")) {
		    	if(Check_Image_Info.Check_resolution_with_range(paramValues[0])==false) {
		    		request.setAttribute("is_image_info_valid", "\"分辨率格式有问题\"");
		    		request.getRequestDispatcher("/WEB-INF/pages/easyindex.jsp").forward(request, response);
		    		return;
		    	}
		    	image.setResolution(paramValues[0]);
		    }else if(paraName.equals("longitude_range")) {
		    	if(Check_Image_Info.Check_longitude_range(paramValues[0])==false) {
		    		request.setAttribute("is_image_info_valid", "\"经度格式有问题\"");
		    		request.getRequestDispatcher("/WEB-INF/pages/easyindex.jsp").forward(request, response);
		    		return;
		    	}
		    	image.setLongitude_range(paramValues[0]);
		    }else if(paraName.equals("latitude_range")) {
		    	if(Check_Image_Info.Check_latitude_range(paramValues[0])==false) {
		    		request.setAttribute("is_image_info_valid", "\"纬度格式有问题\"");
		    		request.getRequestDispatcher("/WEB-INF/pages/easyindex.jsp").forward(request, response);
		    		return;
		    	}
		    	image.setLatitude_range(paramValues[0]);
		    }else if(paraName.equals("acquisition_time")) {
		    	if(Check_Image_Info.Check_acquisition_time_of_compare(paramValues[0])==false) {
		    		request.setAttribute("is_image_info_valid", "\"采集时间格式有问题\"");
		    		request.getRequestDispatcher("/WEB-INF/pages/easyindex.jsp").forward(request, response);
		    		return;
		    	}
		    	image.setAcquisition_time(paramValues[0]);
		    }else if(paraName.equals("acquisition_long")) {
		    	if(Check_Image_Info.Check_acquisition_long_with_range(paramValues[0])==false) {
		    		request.setAttribute("is_image_info_valid", "\"采集时长格式有问题\"");
		    		request.getRequestDispatcher("/WEB-INF/pages/easyindex.jsp").forward(request, response);
		    		return;
		    	}
		    	image.setAcquisition_long(paramValues[0]);
		    }else if(paraName.equals("satellite")) {
		    	image.setSatellite(paramValues[0]);
		    }else if(paraName.equals("remark")) {
		    	image.setRemark(paramValues[0]); 
		    }
		}
		
		images_list=Image.find_by_multiple(image);
		if(images_list!=null) {
			for(int i=0;i<images_list.size();++i) {
				String temp = images_list.get(i).getPath();
				temp = temp.substring(temp.lastIndexOf("\\") + 1);
				arr.add(temp);
			}
		}else {
			System.out.println("空");
		}
		request.getSession().setAttribute("is_all_images", "false");
		request.getSession().removeAttribute("image_list");
		request.getSession().setAttribute("image_list", arr);
		request.getRequestDispatcher("/WEB-INF/pages/easyindex.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doGet(request, response);
	}

}

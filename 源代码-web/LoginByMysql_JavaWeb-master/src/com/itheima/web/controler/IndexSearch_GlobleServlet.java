package com.itheima.web.controler;

import com.itheima.domain.Images;
import com.itheima.util.Image;
import com.itheima.web.controler.IndexSearch_GlobleServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







@WebServlet({"/IndexSearch_GlobleServlet"})
public class IndexSearch_GlobleServlet
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    
    String biaoqian = request.getParameter("biaoqian");
    String[] temp = biaoqian.split(",");
    List<Images> images_list = new ArrayList<>();
    List<String> arr = new ArrayList<>();
    
    if (temp != null && temp.length == 1 && temp[0].equals("查看全部")) {
      request.getSession().setAttribute("is_all_images", "true");
      request.getRequestDispatcher("/WEB-INF/pages/easyindex.jsp").forward((ServletRequest)request, (ServletResponse)response);
      
      return;
    } 
    if (temp != null && temp.length != 0) {
      images_list = Image.find_by_biaoqian(temp);
    }
    
    if (images_list != null) {
      for (int i = 0; i < images_list.size(); i++) {
        String temp2 = ((Images)images_list.get(i)).getPath();
        temp2 = temp2.substring(temp2.lastIndexOf("\\") + 1);
        arr.add(temp2);
      } 
    } else {
      System.out.println("空");
    } 
    request.getSession().setAttribute("is_all_images", "false");
    request.getSession().removeAttribute("image_list");
    request.getSession().setAttribute("image_list", arr);
    request.getRequestDispatcher("/WEB-INF/pages/easyindex.jsp").forward((ServletRequest)request, (ServletResponse)response);
  }


  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { doGet(request, response); }
}

package com.itheima.web.controler;

import com.itheima.domain.Images;
import com.itheima.util.Image;
import com.itheima.web.controler.UploadSearch_MenuServlet;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







@WebServlet({"/UploadSearch_MenuServlet"})
public class UploadSearch_MenuServlet
  extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    
    String key = new String();
    String value = new String();
    key = URLDecoder.decode(request.getParameter("key"), "utf-8");
    value = URLDecoder.decode(request.getParameter("value"), "utf-8");
    
    List<Images> Images_List = new ArrayList<>();
    List<String> arr = new ArrayList<>();
    
    if (value.equals("所有")) {
      request.getSession().setAttribute("is_all_images", "true");
      request.getRequestDispatcher("/WEB-INF/pages/user_uploadImages.jsp").forward((ServletRequest)request, (ServletResponse)response);
      
      return;
    } 
    if (key.equals("latitude_range")) {
      Images image = new Images();
      if (value.equals("赤道附近")) {
        value = "15S~15N";
      } else if (value.equals("北回归线附近")) {
        value = "15N~45N";
      } else if (value.equals("南回归线附近")) {
        value = "45S~15S";
      } 
      image.setLatitude_range(value);
      Images_List = Image.find_by_multiple(image);
    } else {
      Images_List = Image.find_by_accurate(key, value);
    } 
    
    if (Images_List != null) {
      for (int i = 0; i < Images_List.size(); i++) {
        String temp = ((Images)Images_List.get(i)).getPath();
        temp = temp.substring(temp.lastIndexOf("\\") + 1);
        arr.add(temp);
      } 
    } else {
      System.out.println("空");
    } 
    
    request.getSession().setAttribute("is_all_images", "false");
    request.getSession().removeAttribute("image_list");
    request.getSession().setAttribute("image_list", arr);
    request.getRequestDispatcher("/WEB-INF/pages/user_uploadImages.jsp").forward((ServletRequest)request, (ServletResponse)response);
  }



  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { doGet(request, response); }
}

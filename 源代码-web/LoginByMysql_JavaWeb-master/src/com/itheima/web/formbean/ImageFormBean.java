package com.itheima.web.formbean;

import java.util.HashMap;
import java.util.Map;

import com.sun.tools.javac.resources.CompilerProperties.Errors;

public class ImageFormBean {
      private String location;
      private String owner;
      
      private Map<String,String> errors = new HashMap<String, String>();
      
      public Map<String, String> getErrors() {
		 return errors;
	  }
      
      public String getLocation() {
		return location;
	  }
      public void setLocation(String location) {
		this.location = location;
	  }
      
      public String getOwner() {
		return owner;
	  }
      
      public void setOwner(String owner) {
		this.owner = owner;
	  }
      
      public boolean validate() {
    	  boolean isOk = true;
    	  
    	  /*
    	   * location字段不能为空
    	   * */
    	  
    	  if (location==null || location.trim().equals("")) {
			isOk=false;
			errors.put("location", "地址不能为空");
		  }
    	  
    	  return isOk;
      }

}

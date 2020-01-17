package com.itheima.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

//����������е�������䵽formbean��
public class FillFormBean {
	public static <T> T toFromBean(HttpServletRequest request,Class<T> clazz){
		try{
			T bean = clazz.newInstance();
			Map params = request.getParameterMap();
			BeanUtils.populate(bean, params);
			
			return bean;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}

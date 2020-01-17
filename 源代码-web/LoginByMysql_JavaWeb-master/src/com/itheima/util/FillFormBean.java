package com.itheima.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

//把请求对象中的数据填充到formbean中
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

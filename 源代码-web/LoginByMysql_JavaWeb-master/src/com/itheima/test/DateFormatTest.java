package com.itheima.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

public class DateFormatTest {

	public static void main(String[] args) throws Exception {
		String date= "2012-02-08";
		DateLocaleConverter df = new DateLocaleConverter();
	
		System.out.println(	df.convert(date,"yyyy-MM-dd"));
		
	}

}

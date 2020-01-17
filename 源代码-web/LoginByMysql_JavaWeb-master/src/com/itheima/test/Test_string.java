package com.itheima.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test_string {

	public static void main(String[] args) {
		/*String arr1="1947.05.07";
		String arr2="1997.05.27";
		System.out.println(arr2.compareTo(arr1));*/
		
		
	    /*Date date=new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		String NowTime=format.format(date);
		System.out.println(NowTime);*/
		
		/*String temp=".1.2";
		String []arr=new String[3];
		arr=temp.split("\\.");
		System.out.println(arr.length);
		if(arr[0].equals("")) {
			System.out.println("yes");
		}else {
			System.out.println(arr.toString());
		}*/
		
		
		String arr="*";
		String temp=arr.replace("*", "aaaaaaaa");
		System.out.println(temp);
		
	}

}
package com.itheima.test;

import com.itheima.util.Get_Image_Resolution;

public class Test_Get_Image_Resolution {

	public static void main(String[] args) {
         String result=Get_Image_Resolution.getResolution2("C:\\Users\\dell\\Desktop\\image_temp\\1.jpg");
         System.out.println(result);
         System.out.println(result.length());
	}

}

package com.itheima.test;

import java.io.File;

public class Test_Delete_Image {

	public static void main(String[] args) {
		
         File file=new File("C:\\Users\\dell\\Desktop\\LoginByMysql_JavaWeb-master\\WebContent\\images_user\\hjh_1.jpg");
         if(file.exists()) {
        	 file.delete();
         }else {
			System.out.println("文件不存在");
		}
	}

}

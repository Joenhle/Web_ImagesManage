package com.itheima.test;

import com.itheima.util.Check_Image_Info;

public class Test_Check_Image_Info {

	public static void main(String[] args) {
		String arr="20S~50N";
		String str="40N~80N";
		if(Check_Image_Info.Check_latitude_range(arr, str)==true) {
			System.out.println("在范围内");
		}else {
			System.out.println("不在");
		}
	}

}

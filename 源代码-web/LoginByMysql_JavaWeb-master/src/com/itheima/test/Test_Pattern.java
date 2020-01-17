package com.itheima.test;

import java.util.regex.Pattern;

public class Test_Pattern {

	public static void main(String[] args) {
		Pattern pattern=Pattern.compile("[0-9]*");
		System.out.println(pattern.matcher("123456").matches());
	}
	
}

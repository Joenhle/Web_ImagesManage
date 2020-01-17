package com.itheima.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;


public class MD5 {
	public static String encode(String value){
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte b[] = md.digest(value.getBytes());
			
			
			Encoder encoder = Base64.getEncoder();
			
			return encoder.encodeToString(b);
			
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
		
	}
}

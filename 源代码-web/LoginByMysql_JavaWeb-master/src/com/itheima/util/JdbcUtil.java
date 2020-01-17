package com.itheima.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtil {
	private static String driverClassName;
	private static String url;
	private static String username;
	private static String password;
	static{
		//�������ļ�dbcfg.propertiesΪ���ϲ�����ֵ
		
		try {
			InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("dbcfg.properties");
			Properties props = new Properties();
			props.load(in);
			driverClassName = props.getProperty("driverClassName");
			url = props.getProperty("url");
			username = props.getProperty("username");
			password = props.getProperty("password");
			Class.forName(driverClassName);
		} catch (Exception e) {
			throw new RuntimeException(e);
			
		}
		
	}
	public static Connection getConnection() throws Exception{
		return DriverManager.getConnection(url, username, password);
	}
	//�ͷ���Դ
	public static void release(ResultSet rs,Statement stmt,Connection conn){
		if(rs!=null){
			try{
				rs.close();
			}catch(Exception e){
				
			}
			rs=null;
		}
		if(stmt!=null){
			try{
				stmt.close();
			}catch(Exception e){
				
			}
			stmt=null;
		}
			
		if(conn!=null){
			try{
				conn.close();
			}catch(Exception e){
				
			}
			conn=null;
		}
		
	}
}

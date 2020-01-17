package com.itheima.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.itheima.domain.Logs;



public class Log {
	
   public static void addLog(Logs logs) {
		Connection conn = null;
		PreparedStatement stmt = null;

		ResultSet rs = null;
		try {
			conn=JdbcUtil.getConnection();
			String sql="insert into logs (path,operation,operation_time) values(?,?,?)";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1,logs.getPath());
			stmt.setString(2, logs.getOperation());
			stmt.setString(3, logs.getOperation_time());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.release(rs, stmt, conn);
		}
   }
	
   public static String get_all_logs_info() {

      List<String> all_logs_list=new ArrayList<String>();
      String result="[";
      
      Connection conn=null;
	  PreparedStatement stmt=null;
	  ResultSet rs=null;
		
	  try {
			conn=JdbcUtil.getConnection();
			stmt=conn.prepareStatement("select *from logs ");
			rs=stmt.executeQuery();
			
			while(rs.next()) {
				String url_path="/day10_login_Mysql/images_user/"+rs.getString("path").substring(rs.getString("path").lastIndexOf("\\") + 1);
				String arr="['','"+url_path+"','"+rs.getString("operation")+"','"+rs.getString("operation_time")+"']";
				all_logs_list.add(arr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.release(rs, stmt, conn);
		}
		
		for(int i=0;i<all_logs_list.size();++i) {
			if(i!=all_logs_list.size()-1) {
				result+=all_logs_list.get(i)+",";
			}else {
				result+=all_logs_list.get(i)+"]";
			}
		}
		
		return result;
   }
   
}

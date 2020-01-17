package com.itheima.dao.impl;

import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.itheima.dao.UserDao;
import com.itheima.domain.Images;
import com.itheima.domain.User;
import com.itheima.exception.DaoException;
import com.itheima.util.JdbcUtil;

public class UserDaoMysqlImpl implements UserDao {

	public void addUser(User user) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=JdbcUtil.getConnection();

			String sql="INSERT INTO user (username,password,email,image_number,register_date,jurisdiction) VALUES (?,?,?,?,?,?)";

			stmt=conn.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getImage_number());
			stmt.setString(5, user.getRegister_date());
			stmt.setString(6, user.getJurisdiction());
			
			stmt.executeUpdate();
		} catch (Exception e) {
			throw  new DaoException();
		}finally {
			JdbcUtil.release(rs, stmt, conn);
		}
	}

	public User findUser(String username) {
		
		Connection conn=null;
		PreparedStatement stmt=null;  //�ɷ�ֹsqlע��
		ResultSet rs=null;
		try {
			conn=JdbcUtil.getConnection();
			stmt=conn.prepareStatement("select *from user where username=?");
			stmt.setString(1, username);
			rs=stmt.executeQuery();
			if(rs.next()) {
				
				User user=new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setImage_number(rs.getString("image_number"));
				user.setRegister_date(rs.getString("register_date"));
				user.setJurisdiction(rs.getString("jurisdiction"));
				
				return user;
			}
			else
				return null;
			} catch (Exception e) {
			throw  new DaoException();
		}finally {
			JdbcUtil.release(rs, stmt, conn);
		}
	}

	public User findUser(String username, String password) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=JdbcUtil.getConnection();
//			stmt=conn.createStatement();
			//' or 1=1 username='
//			String sql="select *from users where username='"+username+"' and password='"+password+"'";
			stmt=conn.prepareStatement("select *from user where username=? and password=?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs=stmt.executeQuery();
			if(rs.next()) {
				User user=new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setImage_number(rs.getString("image_number"));
				user.setRegister_date(rs.getString("register_date"));
				user.setJurisdiction(rs.getString("jurisdiction"));
				return user;
			}
			else
				return null;
			} catch (Exception e) {
			throw  new DaoException();
		}finally {
			JdbcUtil.release(rs, stmt, conn);
		}
	}
	
	public User findUser_email_and_password(String email,String password) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=JdbcUtil.getConnection();
			stmt=conn.prepareStatement("select *from user where email=? and password=?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs=stmt.executeQuery();
			if(rs.next()) {
				User user=new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setImage_number(rs.getString("image_number"));
				user.setRegister_date(rs.getString("register_date"));
				user.setJurisdiction(rs.getString("jurisdiction"));
				
				return user;
			}
			else
				return null;
			} catch (Exception e) {
			throw  new DaoException();
		}finally {
			JdbcUtil.release(rs, stmt, conn);
		}
	}
	
	public User findUser_byemail(String email) {
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=JdbcUtil.getConnection();
			stmt=conn.prepareStatement("select *from user where email=?");
			stmt.setString(1, email);
			rs=stmt.executeQuery();
			if(rs.next()) {
				 User user=new User();
				 
				 user.setUsername(rs.getString("username"));
			     user.setPassword(rs.getString("password"));
				 user.setEmail(rs.getString("email"));
				 user.setImage_number(rs.getString("image_number"));
				 user.setRegister_date(rs.getString("register_date"));
				 user.setJurisdiction(rs.getString("jurisdiction"));
				 
				 return user;
			}else {
				return null;
			}
		}catch (Exception e) {
			e.printStackTrace();	
		}finally {
			JdbcUtil.release(rs, stmt, conn);
		}
		return null;
	}
    
	public void update_image_number(String username,String image_number) {

		
		Connection conn=null;
		PreparedStatement stmt=null;  
		ResultSet rs=null;
		try {
			conn=JdbcUtil.getConnection();
			stmt=conn.prepareStatement("UPDATE `user` SET image_number=? WHERE username=? ");
			stmt.setString(1,image_number);
			stmt.setString(2, username);
			stmt.execute();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.release(rs, stmt, conn);
		}
	}
	
	public String get_all_user_info() {

		List<String> all_user_list=new ArrayList<String>();
		String result="[";
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=JdbcUtil.getConnection();
			stmt=conn.prepareStatement("select *from user ");
			rs=stmt.executeQuery();
			
			while(rs.next()) {
				String arr="['','"+rs.getString("username")+"','"+rs.getString("email")+"','"+rs.getString("image_number")+"','"+rs.getString("jurisdiction")+"','"+rs.getString("register_date")+"']";
				all_user_list.add(arr);
			}
			
			} catch (Exception e) {

		}finally {
			JdbcUtil.release(rs, stmt, conn);
		}
		
		for(int i=0;i<all_user_list.size();++i) {
			
			if(i!=all_user_list.size()-1) {
				result+=all_user_list.get(i)+",";
			}else {
				result+=all_user_list.get(i)+"]";
			}	
		}	
		return result;
	}
	
	public void deleteUser(String username) {

		Connection conn=null;
		PreparedStatement stmt=null;	
		ResultSet rs=null;
		
		try {
			conn=JdbcUtil.getConnection();
			String sql="delete from user where username=?";
			
			stmt=conn.prepareStatement(sql);
			stmt.setString(1,username);
			stmt.execute();
			
		} catch (Exception e) {
		    e.printStackTrace();
		}finally {
			JdbcUtil.release(rs, stmt, conn);
	    }
	}
	
	public void update_user(User user) {

		Connection conn=null;
		PreparedStatement stmt=null;  
		ResultSet rs=null;
		try {
			conn=JdbcUtil.getConnection();
			if(user.getPassword().equals("")) {
				stmt=conn.prepareStatement("UPDATE `user` SET email=?,image_number=?,jurisdiction=? WHERE username=? ");
				stmt.setString(1,user.getEmail());
				stmt.setString(2, user.getImage_number());
				stmt.setString(3, user.getJurisdiction());
				stmt.setString(4, user.getUsername());
			}else {
				stmt=conn.prepareStatement("UPDATE `user` SET password=?,email=?,image_number=?,jurisdiction=? WHERE username=? ");
				stmt.setString(1,user.getPassword());
				stmt.setString(2,user.getEmail());
				stmt.setString(3, user.getImage_number());
				stmt.setString(4, user.getJurisdiction());
				stmt.setString(5, user.getUsername());
			}
			System.out.println(stmt.toString());
			stmt.execute();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.release(rs, stmt, conn);
		}
	}
}

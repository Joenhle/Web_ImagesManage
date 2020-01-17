package com.itheima.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.itheima.domain.Images;
import com.itheima.util.Image;
import com.itheima.util.JdbcUtil;

public class Test {

	public static void main(String[] args) {
		List<Images> images_list = new ArrayList<Images>();
		images_list=Image.find_by_accurate("country", "中国");
		
		if(images_list==null) {
			System.out.println("没有找到");
		}else {
			for (Images image : images_list) {
				System.out.println(image.getOwner() + " " + image.getCountry());
			}
		}
	}

	public static List<Images> find_by_fuzzy(String keyword) {

		List<Images> images_list = new ArrayList<Images>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement("SELECT *FROM images WHERE CONCAT(`country`,`last_time`,`resolution`,`acquisition_time`,`acquisition_long`,`satellite`,`longitude_range`,`latitude_range`,`image_category`,IFNULL(`remark`,'')) LIKE BINARY? ");
			stmt.setString(1, "%" + keyword + "%");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Images images = new Images();
				images.setPath(rs.getString("path"));
				images.setOwner(rs.getString("owner"));
				images.setCountry(rs.getString("country"));
				images.setLast_time(rs.getString("last_time"));
				images.setResolution(rs.getString("resolution"));
				images.setAcquisition_time(rs.getString("acquisition_time"));
				images.setAcquisition_long(rs.getString("acquisition_long"));
				images.setSatellite(rs.getString("satellite"));				
				images.setLongitude_range(rs.getString("longitude_range"));
				images.setLatitude_range(rs.getString("latitude_range"));
				images.setImage_category(rs.getString("image_category"));
				images.setRemark(rs.getString("remark"));
				images_list.add(images);
			}
			if (images_list.size() > 0) {
				return images_list;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.release(rs, stmt, conn);
		}

		return images_list;
	}

	public static List<Images> find_by_accurate(String table_name, String keyword) {
		List<Images> images_list = new ArrayList<Images>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement("SELECT *FROM images WHERE "+table_name+"  LIKE BINARY? ");
			stmt.setString(1, "%" + keyword + "%");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Images images = new Images();
				images.setPath(rs.getString("path"));
				images.setOwner(rs.getString("owner"));
				images.setCountry(rs.getString("country"));
				images.setLast_time(rs.getString("last_time"));
				images.setResolution(rs.getString("resolution"));
				images.setAcquisition_time(rs.getString("acquisition_time"));
				images.setAcquisition_long(rs.getString("acquisition_long"));
				images.setSatellite(rs.getString("satellite"));				
				images.setLongitude_range(rs.getString("longitude_range"));
				images.setLatitude_range(rs.getString("latitude_range"));
				images.setImage_category(rs.getString("image_category"));
				images.setRemark(rs.getString("remark"));
				images_list.add(images);
			}
			if (images_list.size() > 0) {
				return images_list;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.release(rs, stmt, conn);
		}
		return images_list;
	}

}

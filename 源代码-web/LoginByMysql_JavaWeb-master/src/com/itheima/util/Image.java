package com.itheima.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoMysqlImpl;
import com.itheima.domain.Images;
import com.itheima.domain.User;
import com.itheima.exception.DaoException;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

public class Image {

	public static boolean load_image(String filename, String savePath, InputStream in) {
		String path = null;
		// 设置上传图片的保存路径
		// String savePath=this.getServletContext().getRealPath("images");
		File file = new File(savePath);
		// 判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(savePath + "不存在images_user文件");
			// 创建目录
			file.mkdir();
		}

		try {
			// (文件名、目录名或卷标语法是否正确)
			filename = filename.substring(filename.lastIndexOf("\\") + 1);
			if (filename.substring(filename.lastIndexOf(".") + 1).equals("png")
					|| filename.substring(filename.lastIndexOf(".") + 1).equals("jpg")
					|| filename.substring(filename.lastIndexOf(".") + 1).equals("jpeg")) {

				// InputStream in = item.getInputStream();// 獲得上傳的輸入流
				FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);

				// 进行文件的上传
				int len = 0;
				byte buffer[] = new byte[1024];
				while ((len = in.read(buffer)) > 0)// 每次讀取
				{
					out.write(buffer, 0, len);
				}
				in.close();
				out.close();
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/*
	 * 这里的path采用绝对路径
	 * 浏览器的src需要在DeleteImageServlet里完成路径转化后才可作为path
	 * */
	public static boolean delete_image(String path) {
        	
		System.out.println(path);
		/*
		 * 2019.12.24决定不从项目目录下删除图片
		 * 因为后端显示某某某删除图片的时候需要展示图片
		//先是在绝对路径里删了它
		try {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			} else {
				System.out.println("文件不存在，删除错误");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		//如果文件存在，本地删掉后再在数据库里删除它
		try {

			Connection conn = JdbcUtil.getConnection();
			Statement stem = conn.createStatement();
			try {
				String ppath = path.replaceAll("\\\\", "\\\\\\\\");
				String sql = "delete from images where path='" + ppath + "'";
				System.out.println(sql);
				int count = stem.executeUpdate(sql);
				if (count > 0) {
					System.out.println("删除成功");
				} else {
					System.out.println("删除失败");
					return false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (stem != null) {
						stem.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	/*
	 * 将图片信息上传到数据库
	 * 记录图片的url
	 * 记录图片的上传者
	 *记录图片的location 
	 *记录图片最近一次的操作（上传，更改）时间
	 **/
	public static void addImage(Images images) {
		Connection conn = null;
		PreparedStatement stmt = null;

		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			String sql = "insert into images (path,owner,country,last_time,resolution,acquisition_time,acquisition_long,satellite,remark,longitude_range,latitude_range,image_category) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, images.getPath());
			stmt.setString(2, images.getOwner());
			stmt.setString(3, images.getCountry());
			stmt.setString(4, images.getLast_time());
			stmt.setString(5, images.getResolution());
			stmt.setString(6, images.getAcquisition_time());
			stmt.setString(7, images.getAcquisition_long());
			stmt.setString(8, images.getSatellite());
			stmt.setString(9, images.getRemark());	
			stmt.setString(10, images.getLongitude_range());
			stmt.setString(11, images.getLatitude_range());
			stmt.setString(12, images.getImage_category());	
			stmt.executeUpdate();
		} catch (Exception e) {
			throw new DaoException();
		} finally {
			JdbcUtil.release(rs, stmt, conn);
		}
	}
	
	/*
	 * 用于更改图片的信息
	 * */
	public static void updateImage(Images images) {  
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		int []is_exist=new int[10];
		try {
			conn=JdbcUtil.getConnection();
			String simple_path=images.getPath().substring(images.getPath().lastIndexOf("/")+1);
		
			String sql="update `images` SET owner=?,country=?,acquisition_time=?,acquisition_long=?,satellite=?,remark=?,longitude_range=?,latitude_range=?,image_category=? where path like '%"+simple_path+"%'";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, images.getOwner());
			stmt.setString(2, images.getCountry());
			stmt.setString(3, images.getAcquisition_time());
			stmt.setString(4, images.getAcquisition_long());
			stmt.setString(5, images.getSatellite());
			stmt.setString(6, images.getRemark());
			stmt.setString(7, images.getLongitude_range());
			stmt.setString(8, images.getLatitude_range());
			stmt.setString(9, images.getImage_category());
			
			System.out.println(stmt.toString());
			stmt.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.release(rs, stmt, conn);
			
		}
	}
	
	/*
	 * 获取数据库里图片的所有信息
	 * */
	public static String get_all_images_info() {
		List<String> all_images_list=new ArrayList<String>();
		String result="[";
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=JdbcUtil.getConnection();
			stmt=conn.prepareStatement("select *from images ");
			rs=stmt.executeQuery();
			
			while(rs.next()) {
				String url_path="/day10_login_Mysql/images_user/"+rs.getString("path").substring(rs.getString("path").lastIndexOf("\\") + 1);
				String arr="['','"+url_path+"','"+rs.getString("remark")+"','"+rs.getString("owner")+"','"+rs.getString("image_category")+"','"+rs.getString("country")+"','"+rs.getString("satellite")+"','"+rs.getString("resolution")+"','"+rs.getString("longitude_range")+"','"+rs.getString("latitude_range")+"','"+rs.getString("acquisition_time")+"','"+rs.getString("acquisition_long")+"']";
				all_images_list.add(arr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.release(rs, stmt, conn);
		}
		
		for(int i=0;i<all_images_list.size();++i) {
			if(i!=all_images_list.size()-1) {
				result+=all_images_list.get(i)+",";
			}else {
				result+=all_images_list.get(i)+"]";
			}
		}
		
		return result;
	}
	
	/*
	 *获取数据库里某人的所有图片的信息 
	 **/
	public static String get_all_images_info_with_commonuser(User user) {
		List<String> all_images_list=new ArrayList<String>();
		String result="[";
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=JdbcUtil.getConnection();
			stmt=conn.prepareStatement("select *from images where username='"+user.getUsername()+"' ");
			rs=stmt.executeQuery();
			
			while(rs.next()) {
				String url_path="/day10_login_Mysql/images_user/"+rs.getString("path").substring(rs.getString("path").lastIndexOf("\\") + 1);
				String arr="['','"+url_path+"','"+rs.getString("remark")+"','"+rs.getString("owner")+"','"+rs.getString("image_category")+"','"+rs.getString("country")+"','"+rs.getString("satellite")+"','"+rs.getString("resolution")+"','"+rs.getString("longitude_range")+"','"+rs.getString("latitude_range")+"','"+rs.getString("acquisition_time")+"','"+rs.getString("acquisition_long")+"']";
				all_images_list.add(arr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.release(rs, stmt, conn);
		}
		
		for(int i=0;i<all_images_list.size();++i) {
			if(i!=all_images_list.size()-1) {
				result+=all_images_list.get(i)+",";
			}else {
				result+=all_images_list.get(i)+"]";
			}
		}
		
		return result;
	}
	
	/*
	 * 模糊搜索，数据库全局搜索
	 * */

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
	/*
	 * 精确搜索
	 * table_name表示搜索的哪一个属性
	 * keyword表示包含的关键词
	 * 用于导航栏
	 * */
	public static List<Images> find_by_accurate(String table_name, String keyword) {

		List<Images> images_list = new ArrayList<Images>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConnection();
			if(keyword.equals("其他")) {
				if(table_name.equals("country")) {
					stmt = conn.prepareStatement("SELECT *FROM images WHERE country not like '%中国%' and country not like '%日本%' and country not like '%英国%' and country not like '%美国%' ");
				}else if(table_name.equals("latitude_range")) {
					stmt = conn.prepareStatement("SELECT *FROM images WHERE latitude_range not like '%0S~0N%' and latitude_range not like '%30N~30N%' and latitude_range not like '%30S~30S%' ");
				}else if(table_name.equals("image_category")) {
					stmt = conn.prepareStatement("SELECT *FROM images WHERE image_category not like '%遥感图像%' and image_category not like '%卫星图像%' and image_category not like '%环境监测%' ");
				}else if (table_name.equals("satellite")) {
					stmt = conn.prepareStatement("SELECT *FROM images WHERE satellite not like '%SVIA%' and satellite not like '%SVIB%' and satellite not like '%SVIC%' and satellite not like '%SVID%' ");
				}
			}else {
				stmt = conn.prepareStatement("SELECT *FROM images WHERE "+table_name+"  LIKE BINARY? ");
				stmt.setString(1, "%" + keyword + "%");
			}		
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
	
	/*
	 * 多项查找(无权限限制版，及首页form表单搜索)
	 * image里的值表示寻找要求
	 * 用于搜索框
	 * */
	public static List<Images> find_by_multiple(Images image) {

		List<Images> images_list=new ArrayList<Images>();
		List<Images> result_list=new ArrayList<Images>();
		int []is_exist=new int[4];
		
	    String sql="select *from images ";
	    if(image.getCountry()!="" || image.getSatellite()!="" || image.getImage_category()!="" || image.getRemark()!="") {
	    	sql+="where ";
	    }
	    if(image.getCountry()!="") {
	    	is_exist[0]=1;
	    	sql+="country='"+image.getCountry()+"' ";
	    }
	    if(image.getSatellite()!="") {
	    	if(is_exist[0]==1) {
	    		sql+="and";
	    	}
	    	sql+=" satellite='"+image.getSatellite()+"' ";
	    }
	    if(image.getImage_category()!="") {
	    	if(is_exist[0]==1 || is_exist[1]==1) {
	    		sql+="and";
	    	}
	    	sql+=" image_category='"+image.getImage_category()+"' ";
	    }
	    if(image.getRemark()!="") {
	    	if(is_exist[0]==1 ||is_exist[1]==1 || is_exist[2]==1) {
	    		sql+="and";
	    	}
	    	sql+=" remark like '%"+image.getRemark()+"%' ";
	    }
		System.out.println(sql);
		
	    Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement(sql);
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.release(rs, stmt, conn);
		}
		
		if(images_list.size()==0) {
			return null;
		}else {
			for(int i=0;i<images_list.size();++i) {
				Images imagetemp=images_list.get(i);
			    if(image.getResolution()!="") {
			    	if(Check_Image_Info.Check_resolution(imagetemp.getResolution(), image.getResolution())==false) {
			    		System.out.println("wrong 1");
			    		System.out.println(imagetemp.getResolution());
			    		System.out.println(image.getResolution());
			    		continue;
			    	}
			    }
			    if(image.getAcquisition_time()!="") {
			    	if(Check_Image_Info.Check_acquisition_time(imagetemp.getAcquisition_time(), image.getAcquisition_time())==false) {
			    		System.out.println("wrong 2");
			    		System.out.println(imagetemp.getAcquisition_time());
			    		System.out.println(image.getAcquisition_time());
			    		continue;
			    	}
			    }
			    if(image.getAcquisition_long()!="") {
			    	if(Check_Image_Info.Check_acquisition_long(imagetemp.getAcquisition_long(), image.getAcquisition_long())==false) {
			    		System.out.println("wrong 3");
			    		System.out.println(imagetemp.getAcquisition_long());
			    		System.out.println( image.getAcquisition_long());
			    		continue;
			    	}
			    }
			    if(image.getLongitude_range()!="") {
			    	if(Check_Image_Info.Check_longitude_range(imagetemp.getLongitude_range(), image.getLongitude_range())==false) {
			    		System.out.println("wrong 4");
			    		System.out.println(imagetemp.getLongitude_range());
			    		System.out.println(image.getLongitude_range());
			    		continue;
			    	}
			    }
			    if(image.getLatitude_range()!="") {
			    	if(Check_Image_Info.Check_latitude_range(imagetemp.getLatitude_range(), image.getLatitude_range())==false) {
			    		System.out.println("wrong 5");
			    		System.out.println(imagetemp.getLatitude_range());
			    		System.out.println(image.getLatitude_range());
			    		continue;
			    	}
			    }
			    result_list.add(imagetemp);
			}
		}
		return result_list;
	}

	/*
	 * 多项查找(权限限制版，及删除页form表单搜索)
	 * image里的值表示寻找要求
	 * 用于搜索框
	 * */
	public static List<Images> find_by_multiple_with_jurisdiction(Images image) {

		List<Images> images_list=new ArrayList<Images>();
		List<Images> result_list=new ArrayList<Images>();
		
		int []is_exist=new int[5];
		UserDao userDao=new UserDaoMysqlImpl();
		User user=userDao.findUser(image.getOwner());
		
	    String sql="select *from images ";
	    if(image.getCountry()!="" || image.getSatellite()!="" || image.getImage_category()!="" || image.getRemark()!="") {
	    	sql+="where ";
	    }
	    if(user.getJurisdiction().equals("普通用户")) {
	    	is_exist[0]=1;
	    	sql+="owner='"+image.getOwner()+"' ";
	    } 
	    if(image.getCountry()!="") {
	    	if(is_exist[0]==1) {
	    		sql+="and ";
	    	}
	    	sql+="country='"+image.getCountry()+"' ";
	    	is_exist[1]=1;
	    }
	    if(image.getSatellite()!="") {
	    	if(is_exist[0]==1 || is_exist[1]==1) {
	    		sql+="and ";
	    	}
	    	sql+="satellite='"+image.getSatellite()+"' ";
	    	is_exist[2]=1;
	    }
	    if(image.getImage_category()!="") {
	    	if(is_exist[0]==1 || is_exist[1]==1 || is_exist[2]==1) {
	    		sql+="and ";
	    	}
	    	sql+="image_category='"+image.getImage_category()+"' ";
	    	is_exist[3]=1;
	    }
	    if(image.getRemark()!="") {
	    	if(is_exist[0]==1 ||is_exist[1]==1 || is_exist[2]==1 || is_exist[3]==1) {
	    		sql+="and ";
	    	}
	    	sql+="remark like '%"+image.getRemark()+"%' ";
	    }
		System.out.println(sql);
		
	    Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = JdbcUtil.getConnection();
			stmt = conn.prepareStatement(sql);
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.release(rs, stmt, conn);
		}
		
		if(images_list.size()==0) {
			return null;
		}else {
			for(int i=0;i<images_list.size();++i) {
				Images imagetemp=images_list.get(i);
			    if(image.getResolution()!="") {
			    	if(Check_Image_Info.Check_resolution(imagetemp.getResolution(), image.getResolution())==false) {
			    		continue;
			    	}
			    }
			    if(image.getAcquisition_time()!="") {
			    	if(Check_Image_Info.Check_acquisition_time(imagetemp.getAcquisition_time(), image.getAcquisition_time())==false) {
			    		continue;
			    	}
			    }
			    if(image.getAcquisition_long()!="") {
			    	if(Check_Image_Info.Check_acquisition_long(imagetemp.getAcquisition_long(), image.getAcquisition_long())==false) {
			    		continue;
			    	}
			    }
			    if(image.getLongitude_range()!="") {
			    	if(Check_Image_Info.Check_longitude_range(imagetemp.getLongitude_range(), image.getLongitude_range())==false) {
			    		continue;
			    	}
			    }
			    if(image.getLatitude_range()!="") {
			    	if(Check_Image_Info.Check_latitude_range(imagetemp.getLatitude_range(), image.getLatitude_range())==false) {
			    		continue;
			    	}
			    }
			    result_list.add(imagetemp);
			}
		}
		return result_list;
	}
	
	/*
	 * 用于标签搜索(无限限制版，及上传页和首页全局搜索)
	 * */
	 public static List<Images> find_by_biaoqian(String[] arr) {
		    List<Images> images_list = new ArrayList<>();
		    String sql = "SELECT *FROM images WHERE ";
		    for (int i = 0; i < arr.length; i++) {
		      if (i == 0) {
		        sql = String.valueOf(sql) + "CONCAT(`country`,`last_time`,`resolution`,`acquisition_time`,`acquisition_long`,`satellite`,`longitude_range`,`latitude_range`,`image_category`,IFNULL(`remark`,'')) LIKE '%" + arr[i] + "%' ";
		      } else {
		        sql = String.valueOf(sql) + "and CONCAT(`country`,`last_time`,`resolution`,`acquisition_time`,`acquisition_long`,`satellite`,`longitude_range`,`latitude_range`,`image_category`,IFNULL(`remark`,'')) LIKE '%" + arr[i] + "%' ";
		      } 
		    } 
		    
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
		    try {
		      conn = JdbcUtil.getConnection();
		      stmt = conn.prepareStatement(sql);
		      System.out.println(String.valueOf(stmt.toString()) + "###");
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
		      }
		      return null;
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      JdbcUtil.release(rs, stmt, conn);
		    } 
		    
		    return images_list;
		  }
	
	
	/*
	 * 用于标签搜索(权限限制版，及删除页全局搜索)
	 * */
	 public static List<Images> find_by_biaoqian_with_jurisdiction(String[] arr,String owner) {
		    List<Images> images_list = new ArrayList<>();
		    String sql = "SELECT *FROM images WHERE ";
		    UserDao userDao=new UserDaoMysqlImpl();
		    User user=userDao.findUser(owner);
		    
		    if(user.getJurisdiction().equals("普通用户")) {
		    	sql+="owner='"+user.getUsername()+"' ";
		    }
		    
		    for (int i = 2; i < arr.length; i++) {
		      if (i == 0) {
		        sql = String.valueOf(sql) + "and CONCAT(`country`,`last_time`,`resolution`,`acquisition_time`,`acquisition_long`,`satellite`,`longitude_range`,`latitude_range`,`image_category`,IFNULL(`remark`,'')) LIKE '%" + arr[i] + "%' ";
		      } else {
		        sql = String.valueOf(sql) + "and CONCAT(`country`,`last_time`,`resolution`,`acquisition_time`,`acquisition_long`,`satellite`,`longitude_range`,`latitude_range`,`image_category`,IFNULL(`remark`,'')) LIKE '%" + arr[i] + "%' ";
		      } 
		    } 
		    
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
		    try {
		      conn = JdbcUtil.getConnection();
		      stmt = conn.prepareStatement(sql);
		      System.out.println(String.valueOf(stmt.toString()) + "###");
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
		      }
		      return null;
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      JdbcUtil.release(rs, stmt, conn);
		    } 
		    
		    return images_list;
		  }
}

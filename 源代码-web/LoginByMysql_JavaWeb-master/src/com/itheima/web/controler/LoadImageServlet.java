package com.itheima.web.controler;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoMysqlImpl;
import com.itheima.domain.Images;
import com.itheima.domain.Logs;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.util.Check_Image_Info;
import com.itheima.util.Get_Image_Resolution;
import com.itheima.util.Image;
import com.itheima.util.Log;
import com.itheima.web.controler.LoadImageServlet;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet({ "/LoadImageServlet" })
public class LoadImageServlet extends HttpServlet {

	private UserService service = (UserService) new UserServiceImpl();

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("进1");

		req.setCharacterEncoding("UTF-8");
		User user = (User) req.getSession().getAttribute("user");
		int loaded_images_number = 0;

		if (req.getAttribute("is_image_info_valid") == null || req.getAttribute("is_image_info_valid") != "true") {
			req.setAttribute("is_image_info_valid", "\"true\"");
		}

		try {
			String savePath = "C:\\tomcat\\apache-tomcat-7.0.96\\webapps\\day10_login_Mysql\\images_user";

			String filename = null;
			String path = null;
			boolean isload_success = false;
			List<String> error_image_formate = new ArrayList<>();

			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload((FileItemFactory) diskFileItemFactory);
			upload.setHeaderEncoding("UTF-8");

			List<FileItem> list = upload.parseRequest(req);

			if (!ServletFileUpload.isMultipartContent(req)) {

				System.out.println("出1");

				return;
			}

			Images images = new Images();
			for (FileItem item : list) {
				String itemName = item.getFieldName();
				if (item.isFormField()) {
					if (itemName.equals("country")) {
						if (item.getString("utf-8").equals("") || item.getString("utf-8").trim().equals("")) {
							req.setAttribute("is_image_info_valid", "\"国家信息不能为空\"");
							System.out.println("出2");
							req.getRequestDispatcher("/WEB-INF/pages/user_uploadImages.jsp")
									.forward((ServletRequest) req, (ServletResponse) resp);
							return;
						}
						images.setCountry(item.getString("utf-8"));
						continue;
					}
					if (itemName.equals("acquisition_time")) {
						if (item.getString("utf-8").equals("") || item.getString("utf-8").trim().equals("")
								|| !Check_Image_Info.Check_acquisition_time(item.getString("utf-8").trim())) {
							req.setAttribute("is_image_info_valid", "\"采样时间为空或有误\"");
							System.out.println("出3");
							req.getRequestDispatcher("/WEB-INF/pages/user_uploadImages.jsp")
									.forward((ServletRequest) req, (ServletResponse) resp);
							return;
						}
						images.setAcquisition_time(item.getString("utf-8"));
						continue;
					}
					if (itemName.equals("acquisition_long")) {
						if (item.getString("utf-8").equals("") || item.getString("utf-8").trim().equals("")
								|| !Check_Image_Info.Check_acquisition_long(item.getString("utf-8").trim())) {
							System.out.println("出4");
							req.setAttribute("is_image_info_valid", "\"采样时长为空或有误\"");
							req.getRequestDispatcher("/WEB-INF/pages/user_uploadImages.jsp")
									.forward((ServletRequest) req, (ServletResponse) resp);
							return;
						}
						images.setAcquisition_long(item.getString("utf-8"));
						continue;
					}
					if (itemName.equals("satellite")) {
						images.setSatellite(item.getString("utf-8"));
						continue;
					}
					if (itemName.equals("longitude_range")) {
						if (item.getString("utf-8").equals("") || item.getString("utf-8").trim().equals("")
								|| !Check_Image_Info.Check_longitude_range(item.getString("utf-8").trim())) {
							System.out.println(item.getString("utf-8"));
							System.out.println("出5");
							req.setAttribute("is_image_info_valid", "\"经度范围为空或有误\"");
							req.getRequestDispatcher("/WEB-INF/pages/user_uploadImages.jsp")
									.forward((ServletRequest) req, (ServletResponse) resp);
							return;
						}
						images.setLongitude_range(item.getString("utf-8"));
						continue;
					}
					if (itemName.equals("latitude_range")) {
						if (item.getString("utf-8").equals("") || item.getString("utf-8").trim().equals("")
								|| !Check_Image_Info.Check_latitude_range(item.getString("utf-8").trim())) {
							System.out.println("出6");
							req.setAttribute("is_image_info_valid", "\"纬度范围为空或有误\"");
							req.getRequestDispatcher("/WEB-INF/pages/user_uploadImages.jsp")
									.forward((ServletRequest) req, (ServletResponse) resp);
							return;
						}
						images.setLatitude_range(item.getString("utf-8"));
						continue;
					}
					if (itemName.equals("image_category")) {
						images.setImage_category(item.getString("utf-8"));
						continue;
					}
					if (itemName.equals("owner")) {
						images.setOwner(item.getString("utf-8"));
						continue;
					}
					if (itemName.equals("last_time")) {
						images.setLast_time(item.getString("utf-8"));
						continue;
					}
					if (itemName.equals("remark")) {
						images.setRemark(item.getString("utf-8"));
						continue;
					}
					System.out.println(item.getString("utf-8"));
				}
			}

			try {
				for (FileItem item : list) {
					filename = item.getName();
					if (filename == null || filename.trim().equals("")) {
						continue;
					}

					filename = filename.substring(filename.lastIndexOf("\\") + 1);
					if (!item.isFormField()) {
						Random random = new Random();
						int ranNumber = random.nextInt(1000);
						isload_success = Image.load_image(
								String.valueOf(images.getOwner()) + ranNumber + "_" + filename, savePath,
								item.getInputStream());

						path = String.valueOf(savePath) + "\\" + images.getOwner() + ranNumber + "_" + filename;

						if (isload_success) {					
							try {
								loaded_images_number++;
								images.setPath(path);
								images.setResolution(Get_Image_Resolution.getResolution2(path));
								Date date = new Date();
								SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								images.setLast_time(df.format(date));
								Logs logs=new Logs();
								logs.setPath(images.getPath());
								logs.setOperation(""+user.getUsername()+"上传了图片");
								logs.setOperation_time(df.format(date));
								Log.addLog(logs);
								Image.addImage(images);
								continue;
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}
						} else {
							error_image_formate.add(filename);
						}
					}

				}
				
				//上传完图片之后，更新数据库里用户的image_number值
				user.setImage_number(String.valueOf(Integer.parseInt(user.getImage_number())+loaded_images_number));
				UserDao userDao=new UserDaoMysqlImpl();
				userDao.update_image_number(user.getUsername(),user.getImage_number());
				req.getSession().setAttribute("user",user);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (error_image_formate.size() > 0) {
				String temp = "\"图片[";
				for (int i = 0; i < error_image_formate.size(); i++) {
					temp = String.valueOf(temp) + (String) error_image_formate.get(i) + " ";
				}
				temp = String.valueOf(temp) + "]格式错误或不是图片请重传\"";
				req.setAttribute("is_image_info_valid", temp);
				System.out.println("出7");
				req.getRequestDispatcher("/WEB-INF/pages/user_uploadImages.jsp").forward((ServletRequest) req,
						(ServletResponse) resp);

				return;
			}

			System.out.println("出8");
			
			req.getSession().setAttribute("is_all_images", "true");
			req.getRequestDispatcher("/WEB-INF/pages/user_uploadImages.jsp").forward((ServletRequest) req,
					(ServletResponse) resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		doGet(req, resp);
	}
}

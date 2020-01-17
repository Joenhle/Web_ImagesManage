package com.itheima.util;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoMysqlImpl;
import com.itheima.domain.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


/*此类用来给别人发邮件
 * */
public class Postman {
	
     private static Session session = null;
     private static Transport transport = null;
     
     public static void init() throws Exception {
     	//设置邮件账号
         Properties props = new Properties();
         //设置用户的认证方式
         props.setProperty("mail.smtp.auth", "true");
         //设置传输协议
         props.setProperty("mail.transport.protocol", "smtp");
         //设置发件人的SMTP服务器地址
         props.setProperty("mail.smtp.host", "smtp.qq.com");
         //2、创建定义整个应用程序所需的环境信息的 Session 对象
         session = Session.getDefaultInstance(props);
         //设置调试信息在控制台打印出来
         session.setDebug(true);
         //3、创建邮件的实例对象
         //4、根据session对象获取邮件传输对象Transport
         transport = session.getTransport();
         //设置发件人的账户名和密码
         transport.connect("820863776@qq.com", "vsnbglertrowbegi");
     }

     public static void send_message_findback(String email) throws Exception {
    	 
		 if(null == session) {
			 init();
		 }
		 MimeMessage message = new MimeMessage(session);
		 message.setFrom(new InternetAddress("820863776@qq.com"));
	     message.setRecipients(Message.RecipientType.TO, email);
	     message.setSubject("找回密码");
	     MimeBodyPart text = new MimeBodyPart();
	     MimeBodyPart text2 = new MimeBodyPart();
	     MimeBodyPart text3 = new MimeBodyPart();
	     text.setContent("<p>请查收密码</p>", "text/html;charset=UTF-8");
	     User user = new User();
	     UserDao userDao= new UserDaoMysqlImpl();
	     user=userDao.findUser_byemail(email);
	     MimeMultipart mm = new MimeMultipart();
	     if(user!=null) {
	    	 System.out.println("我找到了用户");
	    	 text2.setContent("<p>账号:"+user.getUsername()+"</p>", "text/html;charset=UTF-8");
	    	 text3.setContent("<p>密码:"+user.getPassword()+"</p>", "text/html;charset=UTF-8");
	    	 mm.addBodyPart(text2);
	    	 mm.addBodyPart(text3);
	     }else {
	    	 text.setContent("<p>抱歉您未注册过账号，或管理员已删除您的账号</p>"
	    	 		        +"<br>"
	    			        +"<p>如有问题，请联系820863776@qq.com</p>","text/html;charset=UTF-8");
	     }
	     mm.addBodyPart(text);
	     mm.setSubType("mixed");
	     message.setContent(mm);
	     message.saveChanges();
	     message.setHeader("Content-Transfer-Encoding", "base64");
	     Message msg=message;
	     //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
         transport.sendMessage(msg, msg.getAllRecipients());
	 }
     
     public static void close() throws Exception {
         transport.close();
     }
     
}

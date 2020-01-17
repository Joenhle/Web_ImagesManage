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


/*�������������˷��ʼ�
 * */
public class Postman {
	
     private static Session session = null;
     private static Transport transport = null;
     
     public static void init() throws Exception {
     	//�����ʼ��˺�
         Properties props = new Properties();
         //�����û�����֤��ʽ
         props.setProperty("mail.smtp.auth", "true");
         //���ô���Э��
         props.setProperty("mail.transport.protocol", "smtp");
         //���÷����˵�SMTP��������ַ
         props.setProperty("mail.smtp.host", "smtp.qq.com");
         //2��������������Ӧ�ó�������Ļ�����Ϣ�� Session ����
         session = Session.getDefaultInstance(props);
         //���õ�����Ϣ�ڿ���̨��ӡ����
         session.setDebug(true);
         //3�������ʼ���ʵ������
         //4������session�����ȡ�ʼ��������Transport
         transport = session.getTransport();
         //���÷����˵��˻���������
         transport.connect("820863776@qq.com", "vsnbglertrowbegi");
     }

     public static void send_message_findback(String email) throws Exception {
    	 
		 if(null == session) {
			 init();
		 }
		 MimeMessage message = new MimeMessage(session);
		 message.setFrom(new InternetAddress("820863776@qq.com"));
	     message.setRecipients(Message.RecipientType.TO, email);
	     message.setSubject("�һ�����");
	     MimeBodyPart text = new MimeBodyPart();
	     MimeBodyPart text2 = new MimeBodyPart();
	     MimeBodyPart text3 = new MimeBodyPart();
	     text.setContent("<p>���������</p>", "text/html;charset=UTF-8");
	     User user = new User();
	     UserDao userDao= new UserDaoMysqlImpl();
	     user=userDao.findUser_byemail(email);
	     MimeMultipart mm = new MimeMultipart();
	     if(user!=null) {
	    	 System.out.println("���ҵ����û�");
	    	 text2.setContent("<p>�˺�:"+user.getUsername()+"</p>", "text/html;charset=UTF-8");
	    	 text3.setContent("<p>����:"+user.getPassword()+"</p>", "text/html;charset=UTF-8");
	    	 mm.addBodyPart(text2);
	    	 mm.addBodyPart(text3);
	     }else {
	    	 text.setContent("<p>��Ǹ��δע����˺ţ������Ա��ɾ�������˺�</p>"
	    	 		        +"<br>"
	    			        +"<p>�������⣬����ϵ820863776@qq.com</p>","text/html;charset=UTF-8");
	     }
	     mm.addBodyPart(text);
	     mm.setSubType("mixed");
	     message.setContent(mm);
	     message.saveChanges();
	     message.setHeader("Content-Transfer-Encoding", "base64");
	     Message msg=message;
	     //�����ʼ��������͵������ռ��˵�ַ��message.getAllRecipients() ��ȡ�������ڴ����ʼ�����ʱ��ӵ������ռ���, ������, ������
         transport.sendMessage(msg, msg.getAllRecipients());
	 }
     
     public static void close() throws Exception {
         transport.close();
     }
     
}

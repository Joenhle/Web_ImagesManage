<%@page import="java.sql.Statement"%>
<%@page import="com.itheima.util.JdbcUtil"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sun.tools.javac.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>上传头像Insert title here</title>

<%
     ArrayList<String> arr=new ArrayList<String>();
     Connection conn = null;
     try{
    	 conn=JdbcUtil.getConnection();
    	 Statement stmt = conn.createStatement();
    	 
    	 ResultSet rs = stmt.executeQuery("select path from images");
    	 
    	 while(rs.next()){
    		 arr.add(rs.getString(1));
    		 System.out.println(rs.getString(1));
    	 }
    	 
    	 rs.close();
    	 stmt.close();
    	 
     }catch(Exception e){
    	 e.printStackTrace();
     }
      
%>

</head>
<body>
	<c:if test="${empty sessionScope.user}">
		<a href="${pageContext.request.contextPath }/servlet/RegistUIServlet">注册</a>
		<a href="${pageContext.request.contextPath }/servlet/LoginUIServlet">登陆</a>
	</c:if>
	<c:if test="${!empty sessionScope.user}">
    	欢迎您：${sessionScope.user.username }&nbsp;&nbsp;
    	<a href="${pageContext.request.contextPath }/servlet/LogoutServlet">注销</a>
		<br>

		<form
			action="${pageContext.request.contextPath }/servlet/LoadImageServlet"
			method="post" enctype="multipart/form-data">
			<input type="text" name="location" >输入图片的拍摄地点 <br>
			<input type="hidden" name="owner" value="${sessionScope.user.username}">
		    <input type="file" name="images" multiple="multiple">
			<input type="submit">
			<input type="reset">
		</form>

        <%
		   for(int i=0;i<arr.size();++i){
			   String temp=arr.get(i);
		%>
		   <img alt="" src="<%=temp%>" >
		<%
		   }
		%>
	
	</c:if>
</body>
</html>
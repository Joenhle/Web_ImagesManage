<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   	<title>主页</title>
  </head>
  
  <body>
    	<h1>黑马网站</h1><hr>
    <c:if test="${empty sessionScope.user}">
	    <a href="${pageContext.request.contextPath }/servlet/RegistUIServlet">注册</a>
	    <a href="${pageContext.request.contextPath }/servlet/LoginUIServlet">登陆</a>
    </c:if>
    <c:if test="${!empty sessionScope.user}">
    	欢迎您：${sessionScope.user.username }&nbsp;&nbsp;
    	<a href="${pageContext.request.contextPath }/servlet/LogoutServlet">注销</a>
    </c:if>
  </body>
</html>

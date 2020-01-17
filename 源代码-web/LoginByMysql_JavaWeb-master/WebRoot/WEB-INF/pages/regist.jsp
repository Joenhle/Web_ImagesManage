<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>用户注册</title>
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/js/calendar.css">
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/calendar.js"></script>
	</head>
	<body>
    	<form action="${pageContext.request.contextPath }/servlet/RegistServlet" method="post">
    		<table width="60%" border="1">
    			<tr>
    				<td>用户名</td>
    				<td>
    					<input type="text" name="username" value="${formBean.username }"/>${formBean.errors.username }
    				</td>
    			</tr>
    			<tr>
    				<td>密码</td>
    				<td>
    					<input type="password" name="password" value="${formBean.password }"/>${formBean.errors.password}
    				</td>
    			</tr>
    			<tr>
    				<td>确认密码</td>
    				<td>
    					<input type="password" name="repassword" value="${formBean.repassword }"/>${formBean.errors.repassword}
    				</td>
    			</tr>
    			<tr>
    				<td>邮箱</td>
    				<td>
    					<input type="text" name="email" value="${formBean.email }"/>${formBean.errors.email}
    				</td>
    			</tr>
    			<tr>
    				<td colspan="2">
    					<input type="submit" value="注册"/>
    				</td>
     			</tr>
    		</table>
    	</form>
	</body>
</html>
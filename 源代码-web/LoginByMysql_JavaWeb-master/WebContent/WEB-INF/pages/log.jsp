<%@page import="com.itheima.util.Log"%>
<%@page import="com.itheima.util.Image"%>
<%@page import="com.itheima.domain.User"%>
<%@page import="com.itheima.dao.impl.UserDaoMysqlImpl"%>
<%@page import="com.itheima.dao.UserDao"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.itheima.util.JdbcUtil"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>日志</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Filter Functionality with CSS3" />
    <meta name="keywords" content="filter, css3, portfolio, functionality" />
    <meta name="author" content="Codrops" />

    <!--管理用户-->
    <link rel="stylesheet"
	      href="${pageContext.request.contextPath }/css/mauser_index.css">
	<link rel="stylesheet"
	      href="${pageContext.request.contextPath }/css/ManageUsers.css">
	<link rel="stylesheet"
	      href="${pageContext.request.contextPath }/css/UserDeleteImagesCss/reset.min.css">
    <script src="${pageContext.request.contextPath }/js/log.js"></script>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css">
    <script src="https://cdn.bootcss.com/datatables/1.10.16/js/jquery.dataTables.js"></script>


<script type="text/javascript">

<%

   String all_logs_info=Log.get_all_logs_info();

%>
var data =<%=all_logs_info%>

</script>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <c:set var="user" value="${sessionScope.user}"/>
    
    <script type="text/javascript">
    function quanxian_valid() {
    	if (${quanxian_valid }!="true" ) {
			alert(${quanxian_valid });
	 }
	}
    
    
    </script>
</head>
  
  
    
<body onload="quanxian_valid()">
<header class="header">
    <div class="header-inner body-width">
        <a href="http://www.sei.ecnu.edu.cn/" class="logo"></a>

        <nav class="header-nav">
            <ul>
                <c:if test="${user.jurisdiction=='管理员'}">
                <li><span class="line"></span> <a >你好，管理员${sessionScope.user.username }</a></li>
                <li><span class="line"></span> <a class="icon-text__pink">日志查看</a></li>
			    <li><span class="line"></span> <a href="${pageContext.request.contextPath }/servlet/HouDuanUIServlet">用户管理</a></li>
			       <li><span class="line"></span> <a href="${pageContext.request.contextPath }/servlet/HouDuanUIServlet_Images">图片管理</a></li>
                <li><span class="line"></span> <a href="${pageContext.request.contextPath }/servlet/IndexUIServlet">首页</a></li>
                <li>
                    <span class="line"></span>
                    <a href="${pageContext.request.contextPath }/servlet/DeleteUIServlet">删除图片</a>
                </li>
                <li>
                    <span class="line"></span>
                    <a href="${pageContext.request.contextPath }/servlet/UploadUIServlet">上传图片</a>
                </li>
                <li>
                    <span class="line"></span>
                    <a class="icon-text__pink register">已登录</a>
                </li>
                <li>
                    <span class="line"></span>
                    <a href="${pageContext.request.contextPath }/servlet/LogoutServlet"
                    >注销</a>
                </li>
                </c:if>
                <c:if test="${empty sessionScope.user}">
                <li><span class="line"></span> <a href="#">数据信息可视化与管理平台</a></li>
                <li><span class="line"></span> <a href="#" class="icon-text__pink register">已注销</a></li>
                <li><span class="line"></span> <a
                        href="${pageContext.request.contextPath }/servlet/LoginUIServlet">请重新登录</a>
                </li>
              </c:if>
            </ul>
        </nav>
    </div>
    <div class="header-shadow"></div>
</header>
<section class="content">  

    <table id="books" class="table table-striped table-bordered row-border hover order-column" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>序号</th>
            <th>图片</th>
            <th>操作</th>
            <th>操作时间</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</section>
<script src="${pageContext.request.contextPath }/js/log.js"></script>

</body>
</html>
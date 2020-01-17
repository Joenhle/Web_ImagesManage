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
    <title>图片管理</title>
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
    <script src="${pageContext.request.contextPath }/js/ManageImgs.js"></script>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css">
    <script src="https://cdn.bootcss.com/datatables/1.10.16/js/jquery.dataTables.js"></script>


<script type="text/javascript">

<%
 
   String all_images_info=Image.get_all_images_info();

%>
var data =<%=all_images_info%>

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
                 <li><span class="line"></span> <a href="${pageContext.request.contextPath }/servlet/HouDuanUIServlet_Log">日志查看</a></li>
			    <li><span class="line"></span> <a href="${pageContext.request.contextPath }/servlet/HouDuanUIServlet">用户管理</a></li>
			    </c:if>
			    <c:if test="${user.jurisdiction=='普通用户'}">
			    <li><span class="line"></span> <a >你好，${sessionScope.user.username }</a></li>
			    </c:if>
			    <c:if test="${!empty sessionScope.user}">
			    <li><span class="line"></span> <a class="icon-text__pink">图片管理</a></li>
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
    <div class="btn-group operation">
        
        <button id="btn_edit" type="button" class="btn bg-info">
            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
        </button>
        <button id="btn_delete" type="button" class="btn btn-success">
            <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
        </button>
    </div>
    <div class="modal fade" id="addBook" role="dialog">
       <form action="${pageContext.request.contextPath }/servlet/HouDuan_adduser" method="post" id="add_userform">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">添加用户</h4>
                </div>
                <div id="addBookModal" class="modal-body">
                    <div class="form-horizontal">
                        <div class="form-group">
                            <label for="bookName" class="col-sm-2 control-label" >用户名:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="bookName" type="text" name="username">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="bookAuthor" class="col-sm-2 control-label">密码:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="bookAuthor" type="password" name="password">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="bookPrice" class="col-sm-2 control-label">图片数量:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="bookPrice" type="number" name="image_number">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="bookPublish" class="col-sm-2 control-label">权限:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="bookPublish" type="text" name="jurisdiction">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="bookISBN" class="col-sm-2 control-label">邮箱:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="bookISBN" type="email" name="email">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="center-block">
                        <button id="cancelAdd" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button id="addBooksInfo" type="button" class="btn btn-success" data-dismiss="modal">保存</button>
                    </div>
                </div>
            </div>
        </div>
        </form>
    </div>
    <div class="modal fade" id="editBookInfo" role="dialog">
        <form action="${pageContext.request.contextPath }/servlet/HouDuan_updateimage" method="post" "
         id="change_userform">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">修改图片信息</h4>
                </div>
                <div id="editBookModal" class="modal-body">
                    <div class="form-horizontal">
                        <div class="form-group">
                            <label for="editBookName" class="col-sm-2 control-label">描述:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editBookName" type="text" name="remark" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editBookAuthor" class="col-sm-2 control-label">上传者:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editBookAuthor" type="text" name="owner">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editBookPrice" class="col-sm-2 control-label">图片类型:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editBookPrice" type="text" name="image_category">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editBookPublish" class="col-sm-2 control-label">国家:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editBookPublish" type="text" name="country">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editBookISBN" class="col-sm-2 control-label">卫星:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editBookISBN" type="text" name="satellite">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editBookName" class="col-sm-2 control-label">分辨率:*</label>
                            <div class="col-sm-10">
                                <p class="form-control" id="editresolution" type="text" name="resolution" > </p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editBookAuthor" class="col-sm-2 control-label">经度范围:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editlongitude_range" type="text" name="longitude_range">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editBookPrice" class="col-sm-2 control-label">纬度范围:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editlatitude_range" type="text" name="latitude_range">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editBookPublish" class="col-sm-2 control-label">采集时间:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editacquisition_time" type="text" name="acquisition_time">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editBookISBN" class="col-sm-2 control-label">采集时长:*</label>
                            <div class="col-sm-10">
                                <input class="form-control" id="editacquisition_long" type="text" name="acquisition_long">
                            </div>
                        </div>
                        
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="center-block">
                        <button id="cancelEdit" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button id="saveEdit" type="button" class="btn btn-success" data-dismiss="modal">保存</button>
                    </div>
                </div>
            </div>
        </div>
        </form>
    </div>
    <div class="modal fade" id="deleteBook" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">确认要删除吗？</h4>
                </div>
                <div class="modal-footer">
                    <form method="post" enctype="application/x-www-form-urlencoded"
							id="delete_userform"
							action="${pageContext.request.contextPath }/servlet/HouDuan_deleteimage">
					</form>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="delete" type="button" class="btn btn-danger" data-dismiss="modal">删除</button>
                </div>
            </div>
        </div>
    </div>

    <table id="books" class="table table-striped table-bordered row-border hover order-column" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>序号</th>
            <th>图片</th>
            <th>描述</th>
            <th>上传者</th>
            <th>图片类型</th>
            <th>国家</th>
            <th>卫星</th>
            <th>分辨率</th>
            <th>经度范围</th>
            <th>纬度范围</th>
            <th>采集时间</th>
            <th>采集时长</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</section>
<script src="${pageContext.request.contextPath }/js/ManageImgs.js"></script>

</body>
</html>
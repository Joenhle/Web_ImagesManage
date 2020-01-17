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
<title>首页</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Filter Functionality with CSS3" />
<meta name="keywords" content="filter, css3, portfolio, functionality" />
<meta name="author" content="Codrops" />

<link rel="shortcut icon" href="../favicon.ico">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/UserDeleteImagesCss/demo.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/UserDeleteImagesCss/DropMenu.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/UserDeleteImagesCss/Formselect.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/UserDeleteImagesCss/index.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/UserDeleteImagesCss/QQPhotoAlbum.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/UserDeleteImagesCss/css.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/UserDeleteImagesCss/reset.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/UserDeleteImagesCss/reset.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/UserDeleteImagesCss/RotatePhotoAlbum.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/UserDeleteImagesCss/SelectImages.css"
	media="screen" type="text/css" />

<script
	src="${pageContext.request.contextPath }/js/UserDeleteImagesJS/ClickImgLarger.js"></script>
<script
	src="${pageContext.request.contextPath }/js/UserDeleteImagesJS/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath }/js/UserDeleteImagesJS/jquery.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/modernizr.custom.29473.js"></script>
<script
	src="${pageContext.request.contextPath }/js/UserDeleteImagesJS/select.js"></script>
<script
	src="${pageContext.request.contextPath }/js/UserDeleteImagesJS/SelectImages.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/UserDeleteImagesJS/tagsinput.js"
	charset="utf-8"></script>

<script src="https://cdn.jsdelivr.net/npm/jquery/dist/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/font-awesome/css/font-awesome.min.css" />
<script
	src="https://cdn.jsdelivr.net/gh/stevenjoezhang/live2d-widget/autoload.js"></script>


<style type="text/css">
* {
	font-family: "sta cartman", "开心软件黑体简体", "开心软件二黑简体", sans-serif
}
</style>

<script>

	//删除图片接口
	function Send_to_servlet() {
		var images_list = document.getElementsByClassName("selected");
		var form_submit = document.getElementById('form_submit');
		for (var i = 2; i < images_list.length; ++i) {
			var str = images_list[i].innerHTML;
			var imgSrc = str.substring(10, str.length - 2);

			var node = document.createElement("input");
			node.setAttribute("type", "hidden");
			node.setAttribute("name", "src")
			node.setAttribute("value", imgSrc);
			form_submit.appendChild(node);
		}
		form_submit.submit();
	}
	
	
	//用于上传图片信息或格式的后端回传
	function isvalid() {
		if ( ${is_image_info_valid } != "true" ) {
			alert(${is_image_info_valid });
		}
	}
	
	function VersionClick(event) {
		alert("123")
        x=event.target
        document.getElementById("hidden_image_category").value=x.value
        alert(document.getElementById("hidden_image_category").value)
    }
	
	function SatelliteClick(event) {
        x=event.target
        document.getElementById("hidden_satellite").value=x.value
    }
	
</script>



<%
	if (session.getAttribute("is_all_images") == null || session.getAttribute("is_all_images") == "true") {
		List<String> arr = new ArrayList<String>();
		Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select path from images");

			while (rs.next()) {
				String temp = rs.getString(1);
				String image = temp.substring(temp.lastIndexOf("\\") + 1);
				arr.add(image);
			}

			rs.close();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("image_list", arr);
	} else {

	}
%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <c:set var="user" value="${sessionScope.user}"/>
</head>

<body onload="isvalid()">
	<header class="header">
    <div class="header-inner body-width">
        <a href="https://www.ecnu.edu.cn/" class="logo"></a>

        <nav class="header-nav">
            <ul>
                <c:if test="${empty sessionScope.user}">
                <li><span class="line"></span> <a href="#">数据信息可视化与管理平台</a></li>
                <li><span class="line"></span> <a
                        href="${pageContext.request.contextPath }/servlet/RegistUIServlet"
                        class="icon-text__pink register">注册</a></li>
                <li><span class="line"></span> <a
                        href="${pageContext.request.contextPath }/servlet/LoginUIServlet">请登录</a>
                </li>
                </c:if>
                <c:if test="${!empty sessionScope.user}">
                <li><span class="line"></span> <a>欢迎您：${sessionScope.user.username }&nbsp;&nbsp;</a></li>
                </c:if>
                <c:if test="${user.jurisdiction=='管理员'}">
                <li><span class="line"></span> <a href="${pageContext.request.contextPath }/servlet/HouDuanUIServlet_Log">日志查看</a></li>
                <li><span class="line"></span> <a href="${pageContext.request.contextPath }/servlet/HouDuanUIServlet">用户管理</a></li>
                </c:if>
                <c:if test="${!empty sessionScope.user}">
                <li><span class="line"></span> <a href="${pageContext.request.contextPath }/servlet/HouDuanUIServlet_Images">图片管理</a></li>
                <li><span class="line"></span> <a class="icon-text__pink register">首页</a></li>
			    <li><span class="line"></span> <a href="${pageContext.request.contextPath }/servlet/DeleteUIServlet" >删除图片</a></li>
			    <li><span class="line"></span> <a href="${pageContext.request.contextPath }/servlet/UploadUIServlet">上传图片</a></li>
			    <li><span class="line"></span> <a class="icon-text__pink register">已登录</a></li>
			    <li><span class="line"></span> <a
				href="${pageContext.request.contextPath }/servlet/LogoutServlet">注销</a>
			    </li>
                </c:if>
            </ul>
        </nav>
    </div>
    <div class="header-shadow"></div>
</header>

	<div class="main">
		<br> <br>
		<div class="main-inner body-width">
			<div class="banner clearfix">
				<div class="Photocontainer">
					<div id="carousel">
						<figure> <img
							src="${pageContext.request.contextPath }/images_web/1.jpg" alt=""></figure>
						<figure> <img
							src="${pageContext.request.contextPath }/images_web/2.jpg" alt=""></figure>
						<figure> <img
							src="${pageContext.request.contextPath }/images_web/3.jpg" alt=""></figure>
						<figure> <img
							src="${pageContext.request.contextPath }/images_web/4.jpg" alt=""></figure>
						<figure> <img
							src="${pageContext.request.contextPath }/images_web/5.jpg" alt=""></figure>
						<figure> <img
							src="${pageContext.request.contextPath }/images_web/6.jpg" alt=""></figure>
						<figure> <img
							src="${pageContext.request.contextPath }/images_web/7.jpg" alt=""></figure>
						<figure> <img
							src="${pageContext.request.contextPath }/images_web/8.jpg" alt=""></figure>
						<figure> <img
							src="${pageContext.request.contextPath }/images_web/9.jpg" alt=""></figure>
					</div>
				</div>

				<script src='js/none.js'></script>
				<div style="text-align: center; clear: both">
					<script src="/gg_bd_ad_720x90.js" type="text/javascript"></script>
					<script src="/follow.js" type="text/javascript"></script>
				</div>
			</div>

			<div class="main-cont main-album">
				<div class="main-cont__title">
					<ul class="main-cont__list clearfix">
						<br>
						<br>
						<div class="box">
							<div class="tagsinput-primary form-group">
								<input name="tagsinput" id="tagsinputval" class="tagsinput"
									data-role="tagsinput" value="查看全部"  placeholder="输入后回车(查看全部用于查看全部图片)">
								<button class="btn" onClick="getinput()">获取输入的值</button>

							</div>
							<form action="${pageContext.request.contextPath }/servlet/IndexSearch_GlobleServlet" method="post" id="form_submit_globle">
						
						</form>
							<script
								src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
						</div>
						<script type="text/javascript">
						function getinput() {
							var form_submit_globle = document.getElementById('form_submit_globle');
							var node = document.createElement("input");
							node.setAttribute("type", "hidden");
							node.setAttribute("name", "biaoqian")
							node.setAttribute("value",$('#tagsinputval').val());
							form_submit_globle.appendChild(node);							
							form_submit_globle.submit();			
						}
						</script>

						<div style="text-align: center; clear: both;">
							<script src="/gg_bd_ad_720x90.js" type="text/javascript"></script>
							<script src="/follow.js" type="text/javascript"></script>
						</div>
					</ul>
				</div>
				<ul class="main-cont__list clearfix">
					<form class="search_form"
						action="${pageContext.request.contextPath }/servlet/IndexSearch_FormServlet"
						method="post">
						<input type="hidden" name="image_category" value="123"
							id="hidden_image_category"> <input type="hidden"
							name="satellite" value="" id="hidden_satellite">
						<table border="1" class="mytable">
							<tr>
								<input type="hidden" name="owner"
									value="${sessionScope.user.username}">
							</tr>
							
							<tr>
								<td width="20%" align="center" class="table_font">国家</td>
								<td><input type="text" name="country" id="country" /></td>
							</tr>
							<tr>
								<td width="20%" align="center" class="table_font">图片类型</td>
								<td align="center"><select class="dropdown">
										<option value="遥感图像" onclick="VersionClick(event)">遥感图像</option>
										<option value="卫星样图" onclick="VersionClick(event)">卫星样图</option>
										<option value="环境监测" onclick="VersionClick(event)">环境监测</option>
										<option value="未知" onclick="VersionClick(event)">类型未知</option>
								</select></td>
							</tr>
							<tr>
								<td width="20%" align="center" class="table_font">分辨率</td>
								<td><input type="text" neme="image_resolution"
									placeholder="长*宽*波动范围"></td>
							</tr>
							<tr>
								<td width="20%" align="center" class="table_font">经度范围</td>
								<td><input type="text" name="longitude_range"
									id="longitude_range" placeholder="xW(E)~yW(E)"></td>
							</tr>
							<tr>
								<td width="20%" align="center" class="table_font">纬度范围</td>
								<td><input type="text" name="latitude_range"
									id="latitude_range" placeholder="xS(N)~yS(N)"></td>
							</tr>
							<tr>
								<td width="20%" align="center" class="table_font">采集时间</td>
								<td><input type="text" name="acquisition_time"
									id="acquisition_time" placeholder="起始~结束" /></td>
							</tr>
							<tr>
								<td width="20%" align="center" class="table_font">采集时长</td>
								<td><input type="text" name="acquisition_long"
									id="acquisition_long" placeholder="时长*波动范围" />h</td>
							</tr>
							<tr>
								<td width="20%" align="center" class="table_font">卫星</td>
								<td align="center"><select class="dropdown">
										<option value="SVIA" onclick="SatelliteClick()">SVIA</option>
										<option value="SVIB" onclick="SatelliteClick()">SVIB</option>
										<option value="SVIC" onclick="SatelliteClick()">SVIC</option>
										<option value="SVID" onclick="SatelliteClick()">SVID</option>
										<option value="未知" onclick="SatelliteClick()">卫星未知</option>
								</select></td>
							</tr>
							<tr>
								<td width="20%" align="center" class="table_font">备注</td>
								<td><input type="text" name="remark" id="remark"></td>
							</tr>
							<tr>
								<td><input type="submit" value="提交"></td>
								<td><input type="reset" value="重置"></td>
							</tr>
						</table>
						<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
					</form>

					<label class="switch"> <input type="checkbox"> <span>Night</span>
					</label>
					<script
						src="${pageContext.request.contextPath }/js/UserDeleteImagesJS/select.js"></script>
					<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>

					<div class="dropmenu">
						<ul class="hList">
							<li><a href="#click" class="menu">
									<h2 class="menu-title">国家</h2>
									<ul class="menu-dropdown">
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("country","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("中国","utf-8"),"utf-8")%>'">中国</li>
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("country","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("日本","utf-8"),"utf-8")%>'">日本</li>
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("country","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("美国","utf-8"),"utf-8")%>'">美国</li>
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("country","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("英国","utf-8"),"utf-8")%>'">英国</li>
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("country","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("所有","utf-8"),"utf-8")%>'">所有</li>
									</ul>
							</a></li>
							<li><a href="#click" class="menu">
									<h2 class="menu-title menu-title_2nd">经纬度</h2>
									<ul class="menu-dropdown">
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("latitude_range","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("赤道附近","utf-8"),"utf-8")%>'">赤道附近</li>
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("latitude_range","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("北回归线附近","utf-8"),"utf-8")%>'">北回归线附近</li>
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("latitude_range","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("南回归线附近","utf-8"),"utf-8")%>'">南回归线附近</li>
									    <li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("latitude_range","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("所有","utf-8"),"utf-8")%>'">所有</li>
									</ul>
							</a></li>
							<li><a href="#click" class="menu">
									<h2 class="menu-title menu-title_3rd">图像类型</h2>
									<ul class="menu-dropdown">
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("image_category","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("遥感图像","utf-8"),"utf-8")%>'">遥感图像</li>
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("image_category","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("卫星样图","utf-8"),"utf-8")%>'">卫星样图</li>
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("image_category","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("环境监测","utf-8"),"utf-8")%>'">环境监测</li>
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("image_category","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("所有","utf-8"),"utf-8")%>'">所有</li>
									</ul>
							</a></li>
							<li><a href="#click" class="menu">
									<h2 class="menu-title menu-title_4th">卫星</h2>
									<ul class="menu-dropdown">
									    <li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("satellite","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("SVIA","utf-8"),"utf-8")%>'">SVIA</li>
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("satellite","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("SVIB","utf-8"),"utf-8")%>'">SVIB</li>
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("satellite","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("SVIC","utf-8"),"utf-8")%>'">SVIC</li>
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("satellite","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("SVID","utf-8"),"utf-8")%>'">SVID</li>
										<li onclick="javascript:window.location.href='${pageContext.request.contextPath }/servlet/IndexSearch_MenuServlet?key=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("satellite","utf-8"),"utf-8")%>&value=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("所有","utf-8"),"utf-8")%>'">所有</li>
									</ul>
							</a></li>
						</ul>
						<script
	                        src="${pageContext.request.contextPath }/js/UserDeleteImagesJS/jquery.min.js"></script>
					</div>

					<div class="image_div">
						<div class="tupian">
							<ul id="image_ul">
								<c:forEach items="${sessionScope.image_list}" var="image">
										<%--<li><img class="pic" src="${pageContext.request.contextPath }/images_user/${image }"></li> --%>
										<li><img class="pic"src="${pageContext.request.contextPath }/images_user/${image }"></li>
									</c:forEach>
							</ul>
						</div>
						<div id="outerdiv"
							style="position: fixed; top: 0; left: 0; background: rgba(0, 0, 0, 0.7); z-index: 2; width: 100%; height: 100%; display: none;">
							<div id="innerdiv" style="position: absolute;">
								<img id="bigimg" style="border: 5px solid #fff;" src="" />
							</div>
						</div>
						<script
	                      src="${pageContext.request.contextPath }/js/UserDeleteImagesJS/ClickImgLarger.js"></script>
					</div>
					
				</ul>
			</div>
		</div>
		<footer class="footer">
		<div class="footer-container">
			<div class="footer-link">
				<div class="footer-link__item">
					<h4 class="footer-title">关于我们</h4>
				</div>
				<div class="footer-link__item">
					<h4 class="footer-title">联系邮箱:</h4>
					<br> <a>10185101220@stu.ecnu.edu.cn</a> <br> <a>10185101221@stu.ecnu.edu.cn</a>
				</div>
				<div class="footer-link__item">
					<div class="footer-orcode">
						<img
							src="${pageContext.request.contextPath }/images_web/ecnu_se.jpg"
							alt="#">
					</div>
					<div class="orcode-text">
						<p>扫描二维码</p>
						<p>关注我们</p>
					</div>
				</div>
				<div class="footer-link__item">
					<h4 class="footer-title">联系商家:</h4>
					<a class="footer-title">East China Normal University</a>
				</div>
				<div class="footer-link__item">
					<h4 class="footer-title">广告服务</h4>
				</div>
			</div>
			<div class="fopter-copyright">
				<p>
					Copyright &copy; 2019.Company name All rights reserved.<a
						target="_blank" href="https://www.ecnu.edu.cn/">华东师范大学中山北路3663号</a>
				</p>
			</div>
		</div>
		</footer>
		<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
	</div>


</body>
</html>
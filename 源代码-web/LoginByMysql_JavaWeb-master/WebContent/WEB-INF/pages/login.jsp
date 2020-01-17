<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>登录注册</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/auth.css">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath }/js/auth.js"></script>
   

    <script type="text/javascript">
      function isvalid() {
          if (${sessionScope.valid}!=null && ${sessionScope.valid}==false) {
              alert('用户名或密码错误');
             
          }
      }
      
      function is_user_info_valid () { 
    	  if (${sessionScope.is_user_info_valid }!=null && ${sessionScope.is_user_info_valid }==false) {
        	  alert('用户名已存在');
        	
          }
	}
      
   </script>

</head>

<body onload="isvalid();is_user_info_valid()" class="custom-background">

<div class="snow-container"
     style="position:fixed;top:0;left:0;width:100%;height:100%;pointer-events:none;"></div>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/registerbg.js"></script>
<div class="lowin">
    <div class="lowin-brand">
        <img src="${pageContext.request.contextPath }/images_web/kodinger.jpg" alt="logo">
    </div>
    <div class="lowin-wrapper">
        <div class="lowin-box lowin-login">
            <div class="lowin-box-inner">
                <form action="${pageContext.request.contextPath }/servlet/LoginServlet" method="post" id="login_form">
                    <p>用户登录</p>
                    <div class="lowin-group">
                        <label>用户名或邮箱 <a href="#" class="login-back-link">登录?</a></label>
                        <input type="text" autocomplete="email" required="required" name="username_or_email" class="lowin-input">
                    </div>
                    <div class="lowin-group password-group">
                        <label>密码 <a href="#" class="forgot-link">忘记密码?</a></label>
                        <input type="password" name="password"  autocomplete="current-password" class="lowin-input">
                    </div>
                    <button class="lowin-btn login-btn" ">
                        登录
                    </button>

                    <div class="text-foot">
                        没有账号? <a href="" class="register-link">注册</a>
                    </div>
                </form>
            </div>
        </div>

        <div class="lowin-box lowin-register">
            <div class="lowin-box-inner">
                <form action="${pageContext.request.contextPath }/servlet/RegistServlet" method="post" id="registform">
                    <p>用户注册</p>
                    <div class="lowin-group">
                        <label>用户名</label>
                        <input type="text" name="username" required="required" autocomplete="name" class="lowin-input">
                    </div>
                    <div class="lowin-group">
                        <label>邮箱</label>
                        <input type="email" autocomplete="email" name="email" class="lowin-input" required="required">
                    </div>
                    <div class="lowin-group">
                        <label>密码</label>
                        <input type="password" name="password" autocomplete="current-password" class="lowin-input" required="required">
                    </div>

                    <button class="lowin-btn" >
                        注册
                    </button>
                    <div class="text-foot">
                        已有账号? <a href="" class="login-link">登录</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <footer class="lowin-footer">

    </footer>
</div>
<div class="snow-container"
     style="position:fixed;top:0;left:0;width:100%;height:100%;pointer-events:none;z-index:11;"></div>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/all.js"></script>
<script src="${pageContext.request.contextPath }/js/auth.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
<script>
    Auth.init({
        login_url: "${pageContext.request.contextPath }/servlet/LoginServlet",
        forgot_url: "${pageContext.request.contextPath }/servlet/ForgetServlet",
    });
</script>

</body>
</html>

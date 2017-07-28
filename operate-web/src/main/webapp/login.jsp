<%@ page contentType="text/html; charset=UTF-8" import="com.szyciov.util.SystemConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%   
String path = request.getContextPath();   
String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;
String page_CopyrightDescription_value = SystemConfig.getSystemProperty("page_CopyrightDescription_value");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <base href="<%=basePath%>">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="author" content="好约车">
  <title>登录</title>
  <link rel="stylesheet" type="text/css" href="content/css/style.css"/>
  <style type="text/css">
  	.yunguanlogin_logo{width:186px;height: 50px;margin:0 auto;padding-top:250px;background: url("img/logo.png") no-repeat center center;}
    .banner{height: 100%;display: block;width: 100%;padding-bottom: 40px;}
  	html,body{height:100%;}
   .footer{line-height: 40px;height:40px;position:absolute;bottom:0px;width:100%;color: #787878;font-size: 12px;text-align: center;background: #fff;} 
  </style>
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script type="text/javascript" src="content/js/common.js"></script>
  <script type="text/javascript"  src="content/plugins/jquery-validate/js/jquery.validate.min.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
  <script type="text/javascript">
  		var message = '${message}';
	    if(window.top!==window.self){
	   			window.top.location.href = "<%=request.getContextPath()%>"+"/login.jsp?message="+message;
	   	}
	   
  </script>
</head>

<body>
<img src="content/img/img_bgdl.png" class="banner">
<div class="login_page" style="position:absolute;top:0px;top:80px;right:120px;">
<div class="yunguanlogin_logo" style="margin-bottom:-70px;"></div>
    <form id="loginform" method="POST" action="User/Login">
       <ul class="login_box" style="margin:0 auto;position:static;border-radius:8px;height:100%;padding-bottom:20px;">
           <li><em class="ico_user"></em> <input type="text" autocomplete="off" name="username" placeholder="帐号" required></li>
           <li><em class="ico_lock"></em> <input type="text" style="display:none;"/><input type="password" autocomplete="off" id="password" name="password" placeholder="密码" required>
           	<label for="password" class="error" style="display: none;"></label>
           </li>
           <li><em class="ico_lock"></em> 
            <input style="width: 41%;" id="code" name="code" />
            <div id="imgcode" style="width: 46%;height: 34px;border-radius: 4px;float: right;border: 1px solid rgba(128, 128, 128, 0.39);"></div>
           	<label id="message" for="code" class="error" style="display: none;"></label>
           </li>
           <button class="login_btn" id="loginbtn" type="button">登录</button>
       </ul>
    </form>
</div>
<div class="pop_box" id="window">
    <div class="tip_box_b form">
        <h3>提示</h3>
        <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
        <div class="w400">
    		当前浏览器版本过低，为了更好的体验，建议升级至IE9以上版本，或者使用谷歌浏览器。
            <br>
            <button class="Lbtn red" type="button" onclick="closeWindow();">确定</button>
        </div>
    </div>
</div>
<div class="footer"><%=page_CopyrightDescription_value%></div>
<script>
	$(function(){
		if(message){
	    	$("#message").html(message);
	    	$("#message").show();
	    }else{
	    	$("#message").hide();
	    }
		
		$("#loginbtn").click(function() {
			if(!ieError()) {
				return;
			}
			var form = $("#loginform");
            $("#password").val(encodepwd($("#password").val()));
			form.submit();
		});
		
		$("#code").keydown(function(event){
		    var evt = window.event||event;
		    if((evt.keyCode || evt.which)==13){
		    	if(!ieError()) {
					return;
				}
		    	var form = $("#loginform");
                $("#password").val(encodepwd($("#password").val()));
				form.submit();
		    }
		}); 
		
		$("#loginform").validate({
		      rules: {
		          username: {
		              required: true,
		              minlength: 2
		          },
		          password: {
		              required: true,
		              minlength: 5
		          },
		          code:{
		          	 required: true,
		          }
		      },
		      messages: {
		          username: {
		              required: "账号不能为空",
		              minlength: "字符长度需大于2"
		          },
		          password: {
		              required: "密码不能为空",
		              minlength: "字符长度需大于5"
		          },
		          code:{
		          	 required: "验证码不能为空"
		          }
		      }
		  });
		
		$("#imgcode").html("<img style='margin-top:-1px' src='User/GetImgCode?t="+Math.random()+"'>");
		$("#imgcode").click(function(){
			$("#imgcode").html("<img style='margin-top:-1px' src='User/GetImgCode?t="+Math.random()+"'>");
		});
		ieError();
	});
	
	/**
  	 * 验证ie版本
  	 */
  	function ieError() {
  		if(!checkIe()) {
  			$("#window").show();
  			return false;
  		}
  		return true;
  	}
  	
  	function closeWindow() {
  		$("#window").hide();
  	}
</script>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" import="com.szyciov.util.SystemConfig" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%   
String path = request.getContextPath();   
String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;
String leaseurl = SystemConfig.getSystemProperty("leaseurl");
String page_CopyrightDescription_value = SystemConfig.getSystemProperty("page_CopyrightDescription_value");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <base href="<%=basePath%>">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="网站内容描述，后台记得改！">
  <meta name="keywords" content="网页关键字，后台记得换掉">
  <meta name="author" content="好约车">
  <title>登录</title>
  <link rel="stylesheet" type="text/css" href="content/css/shouyelogoutdialog.css" />
  <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/denglu.css"/>
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
  <script type="text/javascript" src="content/js/common.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
  <script type="text/javascript">
  		var message = '${message}';
	    if(window.top!==window.self){
	   			window.top.location.href = "<%=request.getContextPath()%>"+"/login.jsp?message="+message;
	   	}
	    var leaseurl = '<%=leaseurl%>'; 
	    var temphref="";
  </script>
   <style>
  .banner{height: 100%;display: block;width: 100%;padding-bottom: 40px;}
  html,body{height:100%;}
  .footer{line-height: 40px;height:40px;position:absolute;bottom:0px;width:100%;color: #787878;font-size: 12px;text-align: center;background: #fff;} 
  </style>
</head>
<body>
<img src="content/img/img_bgdl.png" alt="banner" class="banner">
<div class="login_wrap">
	<div class="login_inner">
		<div class="login_header">
			<div class="h_left login_current" id="goorgan">机构端</div>
			<div class="h_middle"></div>
			<div class="h_right" id="golease">租赁端</div>
		</div>
		<div class="login_con">
			<div class="login_hint" id="login_hint">图片验证码校验失败</div>
			<div class="login_top">
				<form id="logform" method="POST" action="User/Login">
				<input type="hidden" id="remember" name="remember" />
				<div class="inp_box">
					<input type="text" autocomplete="off" name="username" id="username" value="" placeholder="请输入帐号"/>
				</div>
				<div class="inp_box inp_box_1">
					<input type="text" style="display:none;"/>
					<input type="password" autocomplete="off" name="password" id="password" value="" placeholder="请输入密码"/>
				</div>
				<div class="code">
					<div class="inp_box">
						<input type="text" autocomplete="off" name="code" id="code" value="" placeholder="请输入验证码"/>
					</div>
					<div class="code_r" id="imgcode"></div>
				</div>
				</form>
			</div>
			<div class="login_middle">
				<label for="checkbox">
					<input type="checkbox" id="checkbox" class="check_op" style="opacity:1;"/>
					<span class="check_span" style="left:-4px;">记住账号</span>
				</label>
			</div>
			<div class="login_bottom" id="loginbtn">
				登录
			</div>
		</div>
	</div>
</div>
<div class="popup_box" id="window">
	<div class="popup popup_noaddress">
		<div class="popup_title">
			<span>提示</span>
			<i class="close"></i>
		</div>
		<div class="popup_content">
			当前浏览器版本过低，为了更好的体验，建议升级至IE9以上版本，或者使用谷歌浏览器。
		</div>
		<div class="popup_footer">
			<span class="sure" onclick='closeWindow();'>确定</span>
		</div>
	</div>
</div>
<div class="footer"><%=page_CopyrightDescription_value%></div>
<script>
  	$(document).ready(function() {
  		var account = getCookie("organ_account");
  		if(account){
  			$("#username").val(account);
  			$("#password").val("");
  			$("#checkbox").attr("checked","checked");
  			$("#remember").val("1");
  		}else{
  			$("#checkbox").removeAttr("checked")
  			$("#remember").val("0");
  		}
  		if(message){
	    	$("#login_hint").html(message);
	    	$("#login_hint").show();
	    }else{
	    	$("#login_hint").hide();
	    }
  		
  		$("#username,#password,#code").keyup(function(){
  			validateForm($(this).attr("name"));
  		});
  		
  		$("#checkbox").click(function(){
  			if(this.checked){
  				$("#remember").val("1");
  			}else{
  				$("#remember").val("0");
  			}
  		});
  		
		$("#loginbtn").click(function(){
			if(!ieError()) {
				return;
			}
			var form = $("#logform");
			if(!validateForm()){
				return;
			}
			$("#password").val(encodepwd($("#password").val()));
			form.submit();
		});
		
		$("#imgcode").html("<img style='margin-top:2px' src='"+temphref+"User/GetImgCode?t="+Math.random()+"'>");
		$("#imgcode").click(function(){
			$("#imgcode").html("<img style='margin-top:2px' src='"+temphref+"User/GetImgCode?t="+Math.random()+"'>");
		});
		
		$("#golease").click(function(){
			$("#username").val("");
			$("#password").val("");
			var account = getCookie("lease_account");
	  		if(account){
	  			$("#username").val(account);
	  			$("#password").val("");
	  			$("#checkbox").attr("checked","checked");
	  			$("#remember").val("1");
	  		}else{
	  			$("#checkbox").removeAttr("checked")
	  			$("#remember").val("0");
	  		}
			temphref = leaseurl;
			clearHint();
			$("#logform").attr({"action":temphref+"User/Login"});
			$(this).removeClass("login_current");
			$("#goorgan").removeClass("login_current");
			$(this).addClass("login_current");
			$("#imgcode").click();
		});
		$("#goorgan").click(function(){
			$("#username").val("");
			$("#password").val("");
			var account = getCookie("organ_account");
	  		if(account){
	  			$("#username").val(account);
	  			$("#password").val("");
	  			$("#checkbox").attr("checked","checked");
	  			$("#remember").val("1");
	  		}else{
	  			$("#checkbox").removeAttr("checked")
	  			$("#remember").val("0");
	  		}
			temphref = "";
			clearHint();
			$("#logform").attr({"action":temphref+"User/Login"});
			$(this).removeClass("login_current");
			$("#golease").removeClass("login_current");
			$(this).addClass("login_current");
			$("#imgcode").click();
		});
		
		$("#code").keydown(function(event){
		    var evt = window.event||event;
		    if((evt.keyCode || evt.which)==13){
		    	if(!ieError()) {
					return;
				}
		    	var form = $("#logform");
				if(!validateForm()){
					return;
				}
                $("#password").val(encodepwd($("#password").val()));
				form.submit();
		    }
		});
		ieError();
  	});
  	var usernameval;
  	var passwordval;
  	var codeval;
  	/**
  	 * 验证form表单完整性
  	 */
  	function validateForm(validatedom){
  		if(!validatedom||validatedom=="username"){
  			var username = $("#username").val();
    		if(!username){
    			usernameval = "请输入账号!";
    			$("#login_hint").html(usernameval);
  	    	$("#login_hint").show();
    			return false;
    		}else if(usernameval){
    			clearHint();
    		}
  		}
  		
  		if(!validatedom||validatedom=="password"){
  			var password = $("#password").val();
    		if(!password){
    			passwordval = "请输入密码!";
    			$("#login_hint").html(passwordval);
  	    	$("#login_hint").show();
    			return false;
    		}
    		if(password.length<6||password.length>16){
    			passwordval = "密码长度在6-16位，请确认!";
    			$("#login_hint").html(passwordval);
  	    	$("#login_hint").show();
    			return false;
    		}if(passwordval){
    			checkstr(password);
    		}else if(passwordval){
    			clearHint();
    		}
  		}
  		
  		if(!validatedom||validatedom=="code"){
  			var code = $("#code").val();
    		if(!code){
    			codeval = "请输入验证码!";
    			$("#login_hint").html(codeval);
  	    	$("#login_hint").show();
    			return false;
    		}
    		if(code.length!=4){
    			codeval = "验证码长度为4位!";
    			$("#login_hint").html(codeval);
  	    	$("#login_hint").show();
    			return false;
    		}else if(codeval){
    			clearHint();
    		}
  		}
  		return true;
  	}

  	function clearHint(){
  		usernameval=null;
    	passwordval=null;
    	codeval=null;
  		$("#login_hint").html("");
    	$("#login_hint").hide();
  	}
  	
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
  	/**
  	*字母，特殊符号，数字
  	*/
  	function checkstr(str1){
  		debugger;
  		if(/\d+/.test(str1)){
  		if(/[a-zA-Z]+/.test(str1)){
  		if(/[\.@#\$%\^&\*\(\)\[\]\\?\\\/\|\-~`\+\=\,\r\n\:\'\"]+/.test(str1)){
  		return true;
  		}
  		$("#login_hint").html("密码必须由数字，字母，特殊符号组成");
  		return false;
  		}
  		$("#login_hint").html("密码必须由数字，字母，特殊符号组成");
  		return false;
  		}
  		$("#login_hint").html("密码必须由数字，字母，特殊符号组成");
  		return false;
  		}
</script>
</body>
</html>


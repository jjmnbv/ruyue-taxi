<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <title>用车规则—超级管理员</title>
  <base href="<%=basePath%>">
  <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/xiugaimima.css"/> 
  <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
  <script type="text/javascript" src="content/js/common.js"></script>
  <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
  <script type="text/javascript" src="js/basecommon.js"></script>
</head>
<body>
<div class="con_box">
	<div class="con_header">
		修改密码
	</div>
	<div class="con_con">
		<div class="con_left">
			<div class="con_pass">原密码</div>
			<div class="con_pass">新密码</div>
			<div class="con_pass">重复新密码</div>
		</div>
		<div class="con_middle">
			<form action="" method="post" autocomplete="on">
				<div class="con_inp">
					<input type="text" name="" id="passwordOld" onfocus="this.type='password'" value="" placeholder="原密码" onkeyup="checkSubmit();" maxlength="16" autocomplete="off"/>
				</div>
 			<div class="con_inp">
 				<input type="text" name="" id="passwordNew" onfocus="this.type='password'" value="" placeholder="8-16位字母、符号和数字组成"  onkeyup="checkSubmit();" maxlength="16" autocomplete="off"/>
 			</div>
 			<div class="con_inp">
 				<input type="text" name="" id="passwordNew1" onfocus="this.type='password'" value="" placeholder="8-16位字母、符号和数字组成"  onkeyup="checkSubmit();" maxlength="16" autocomplete="off"/>
 			</div>
 			<div class="con_inp con_sub con_sub_pre" id="submitRed">提交</div>
			</form>
		</div>
		<div class="con_right" style="display: none;">
			<div class="con_hint" id="con_hint1"><!-- 原密码错误，还可以输入<span>4</span>次 --></div>
			<div class="con_hint" id="con_hint2"><!-- 新密码不能和原密码相同 --></div>
			<div class="con_hint" id="con_hint3"><!-- 两次输入的新密码不一致 --></div>
		</div>
	</div>
	<script type="text/javascript" src="js/updatePassword/index.js"></script>
	<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
	</script>
</body>
</html>
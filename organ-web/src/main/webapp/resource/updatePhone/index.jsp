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
  <link rel="stylesheet" type="text/css" href="content/css/xiugailianxifangshi.css"/> 
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
		修改联系方式
	</div>
	<div class="con_con">
		<div class="con_left">
			<div class="con_pass">旧手机号码</div>
			<div class="con_pass">新手机号码</div>
		</div>
		<div class="con_middle">
			<form action="" method="post">
				<div class="con_inp">
					<label>${orgOrgan.phone}</label>
				</div>
				<div class="con_inp">
					<input name="phone" id="phone" value="" placeholder=""  maxlength="11" autocomplete="off" onfocus="this.type='text'" />
				</div>
 			<div class="con_inp con_sub" onclick="save();">提交</div>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="js/updatePhone/index.js"></script>
	<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
	</script>
</body>
</html>
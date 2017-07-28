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
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>个人用户协议条款</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
	</head>
	<style type="text/css">
		body{overflow:hidden;}
		.breadcrumb{text-decoration:underline;}
		.form label{float:left;line-height: 30px;height:30px;}
		.form select,.form input[type=text]{width:70%;float:left;}
		.form #inp_box1 label.error{padding-left: 0;}
		body{overflow:hidden;}
		@media screen and (max-width: 1010px){
		  	.row_css_1 .col-5{
				width:60%;
			}
		}
	</style>

	<body onload="method();">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 系统设置> 修改密码
		</div>
		<div class="content">
		  <form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
		  <div class="row form row_css_1" style="padding-top: 30px;">
		  	<div class="row">
			  	<div class="col-5" style="text-align: left;">
			  		<label>旧密码<em class="asterisk"></em></label>
	           		<input id="oldpassword" name="oldpassword" type="password" width="200px" placeholder="输入原密码" maxlength="16" style="width:54%;" autocomplete="off"><br>
			  	</div>
		  	</div>
		  	
		  	<div class="row">
		  	<div class="col-5" style="text-align: left;">
		  		<label>新密码<em class="asterisk"></em></label>
            	<input id="password" name="password" type="password" width="200px" placeholder="8到16位字母、符号和数字组成" maxlength="16" style="width:54%;" autocomplete="off"><br>
		  	</div>
		  	</div>
		  	
		  	<div class="row">
			  	<div class="col-5" style="text-align: left;">
			  		<label>重复新密码<em class="asterisk"></em></label>
	            	<input id="password2" type="password" name="password2" width="200px" placeholder="8到16位字母、符号和数字组成" maxlength="16" style="width:54%;" autocomplete="off"><br>
			  	</div>
		  	</div>
		  </div>
	  	</form>
		  <div class="row" id="editBtn">	
				<div class="col-12" style="text-align: left;">
                     <button class="Lbtn orange" style="margin-left: 13%;" onclick="save()">提交</button>
                </div>
		  </div>
		</div>
		
		<script type="text/javascript" src="js/changepwd/index.js"></script>
	</body>
</html>

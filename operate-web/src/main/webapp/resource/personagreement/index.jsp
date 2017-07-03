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
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/ueditor/themes/default/css/ueditor.css" />
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" charset="utf-8" src="content/plugins/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="content/plugins/ueditor/ueditor.all.js"></script>
		<script type="text/javascript" charset="utf-8" src="content/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
	</head>
	<style type="text/css">
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		body{background: #DADADA!important;}
		.crumbs{font-size: 14px;}
	</style>

	<body>
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > 个人用户协议
			<button class="SSbtn blue back" onclick="edit()" id="editButton" id="editDisBtn">编辑</button>
			<button class="SSbtn blue back" onclick="cancel()" id="backButton">返回</button>
			<input type="hidden" id="usertype" value="${usertype}">
		</div>
		<div class="content">
		  <form id="formss" class="form" style="padding-top: 30px;">
		    <input id="id" name="id" value="${personagreement.id}" type="hidden">
			<div class="row form" style="text-align: center;">
				<div class="col-12">
					<label style="text-align: center;padding: 10px 0 10px 0;"><h1>个人用户协议条款</h1></label>
				</div>
			</div>
			<div class="row form" id="edit" style="display: none;">
				<div class="col-12">
					<script id="content" name="content" type="text/plain"></script>
				</div>
			</div>
			<div class="row form" id="view">
				<div class="col-12" id="contentDiv">${personagreement.content}</div>
			</div>
		  </form>
		  <div class="row" id="editBtn">	
				<div class="col-12" style="text-align: right;">
                     <button class="Mbtn orange" style="margin-right: 20px;" onclick="save()">保存</button>
                     <button class="Mbtn grey" onclick="cancel()">取消</button>
                </div>
		  </div>
		</div>
		
		<script type="text/javascript" src="js/personagreement/index.js"></script>
	</body>
</html>

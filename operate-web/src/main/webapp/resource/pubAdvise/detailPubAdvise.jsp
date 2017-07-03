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
		<title>广告配置详情</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/css/laterchange.css" />
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
		.form select{width:68%;}
		.breadcrumb{text-decoration:underline;}
	</style>

	<body style="overflow: hidden;">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > <a href="PubAdvise/Index" class="breadcrumb">广告页配置</a> > 广告页详情
			<button class="SSbtn blue back" onclick="window.history.go(-1);" id="backButton">返回</button>
		</div>
		
		<div class="content">
			<form id="formss" class="form form-configuration" style="padding-top: 30px;">
				<div class="row form">
					<div class="col-4">
						<label class="col-4-label">App类型<em class="asterisk"></em></label>
						<select id="apptype" name="apptype" disabled="disabled">
							<option value="${adimage.apptype}">${adimage.apptypeName}</option>
						</select>
					</div>
					<div class="col-4">
						<label>名称<em class="asterisk"></em></label>
						<input type="text" placeholder="输入名称" id="name" maxlength="20" name="name" disabled="disabled" value="${adimage.name}">
					</div>
					<div class="col-4">
						<label>有效期<em class="asterisk"></em></label>
						<input style="width:30%;" id="starttime" name="starttime" type="text" readonly="readonly" placeholder="开始日期" value="${adimage.starttime}">至
						<input style="width:30%;" id="endtime" name="endtime" type="text" readonly="readonly" placeholder="结束日期" value="${adimage.endtime}">
					</div>
				</div>
				<div class="row form">
	       			<div class="col-12">
	             		<label style="text-align: left;width:41%;">上传广告页(<em class="asterisk"></em>仅限png格式，大小不超过3M)</label>
	           		</div>
	          	</div>
	       		<div class="row form">
	       			<div class="col-12">
	              		<div id="imgShow" style="width: 200px;height: 210px;margin: auto;">
	               			<img width="200px" height="210px" src="${basepath}/${adimage.imgaddr}" id="imgback">
						</div>
					</div>
	       		</div>
			</form>
		</div>
	</body>
</html>

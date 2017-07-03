<%@page import="com.szyciov.util.SystemConfig"%>
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
		<title>维护广告配置</title>
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
		<script type="text/javascript" src="content/plugins/fileupload/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.fileupload.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
	</head>
	<style type="text/css">
		.form select{width:68%;}
		.breadcrumb{text-decoration:underline;}
		label[for='imgaddr']{
			padding-left: 0px!important;
		}
	</style>

	<body>
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > <a href="PubAdvise/Index" class="breadcrumb">广告页配置</a> > <span id="advisePageTitle">新增广告页</span>
			<button class="SSbtn blue back" onclick="window.history.go(-1);" id="backButton">返回</button>
		</div>
		
		<div class="content">
		  <form id="formss" class="form form-configuration" style="padding-top: 30px;">
		    <input id="id" name="id" value="${adimage.id}" type="hidden">
		    <input id="imgtype" name="imgtype" value="0" type="hidden">
		    <input id="hideApptype" type="hidden" value="${adimage.apptype}">
		    <input id="hideName" type="hidden" value="${adimage.name}">
		    <input id="hideStarttime" type="hidden" value="${adimage.starttime}">
		    <input id="hideEndtime" type="hidden" value="${adimage.endtime}">
		    <input id="hideImgaddr" type="hidden" value="${adimage.imgaddr}">
			<div class="row form">
					<div class="col-4">
						<label class="col-4-label">App类型<em class="asterisk"></em></label>
						<select id="apptype" name="apptype">
							<option value="">选择</option>
							<c:forEach items="${apptypeList}" var="apptype">
								<option value="${apptype.value}">${apptype.text}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="col-4-label">名称<em class="asterisk"></em></label>
						<input type="text" placeholder="输入名称" id="name" maxlength="20" name="name" class="userName">
					</div>
					<div class="col-4">
						<label>有效期<em class="asterisk"></em></label>
							<input style="width:30%;" id="starttime" name="starttime" readonly="readonly" type="text" placeholder="开始日期" class="searchDate">至
							<input style="width:30%;" id="endtime" name="endtime" readonly="readonly" type="text" placeholder="结束日期" class="searchDate">
					</div>
				</div>
				<div class="row form">
           			<div class="col-12">
	                	<label class="uploading" style="text-align: left;width:41%;">上传广告页(<em class="asterisk"></em>仅限png格式，大小不超过3M)</label>
               		</div>
           		</div>
           		<div class="row form">
           			<div class="col-12">
           				<input id="imgaddr" name="imgaddr" type="hidden">
           				<div style="width: 200px;height: 210px;">
           					<img src="content/img/ing_tupian.png" style="background-color:gray;width: 200px;height: 210px;" id="imgback"/>
		                	<input id="fileupload" style="position: relative;top: -215px;left: 0px;width: 200px;height: 210px;font-size: 80px;opacity:0;" type="file" name="file" multiple/>
           				</div>
						<a id="clear" href="javascript:void(0)">删除</a>
               		</div>
           		</div>
			</form>
			<div class="row" id="editBtn">	
				<div class="col-12" style="text-align: left;">
                     <button class="Mbtn orange" style="margin-right: 20px;" onclick="save()">保存</button>
                     <button class="Mbtn grey" onclick="cancel()">取消</button>
                </div>
			</div>
		</div>
		
		<script type="text/javascript" src="js/pubAdvise/editPubAdvise.js"></script>
		<script type="text/javascript">
			var serviceAddress = "<%=SystemConfig.getSystemProperty("fileserver")%>"
		</script>
	</body>
</html>

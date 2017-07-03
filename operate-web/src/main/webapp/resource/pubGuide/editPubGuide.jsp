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
		<title>维护引导页配置</title>
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
	</head>
	<style type="text/css">
		.form select{width:68%;}
		.breadcrumb{text-decoration:underline;}
		label[for='imgaddr']{
			padding-left: 0px!important;
		}
		
		@media screen and (max-width: 1010px){
		  .row_css_xiugai .col-4{
		  	width:45%;
		  }
		}
	</style>

	<body>
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > <a href="PubGuide/Index" class="breadcrumb">引导页配置</a> > <span id="guidePageTitle">新增引导页</span>
			<button class="SSbtn blue back" onclick="window.history.go(-1);" id="backButton">返回</button>
		</div>
		
		<div class="content">
			<form id="formss" class="form" style="padding-top: 30px;">
			    <input id="id" name="id" value="${adimage.id}" type="hidden">
			    <input id="imgtype" name="imgtype" value="1" type="hidden">
			    <input id="hideVersion" type="hidden" value="${adimage.version}">
			    <input id="hideImgaddr" type="hidden" value="${adimage.imgaddr}">
			    <input id="hideApptype" type="hidden" value="${adimage.apptype}">
				<div class="row form row_css_xiugai">
					<div class="col-4">
						<label class="css_label">App类型<em class="asterisk"></em></label>
						<select id="apptype" name="apptype">
							<option value="">选择</option>
							<c:forEach items="${apptypeList}" var="apptype">
								<option value="${apptype.value}">${apptype.text}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="css_label">版本号<em class="asterisk"></em></label>
						<input type="text" placeholder="输入版本号，如V1.1.1" id="version" maxlength="20" name="version">
					</div>
				</div>
				<div class="row form">
					<div class="col-12">
						<label style="text-align: left;width:41%;">上传引导页(<em class="asterisk"></em>仅限png格式，大小不超过3M)</label>
						<input id="imgaddr" name="imgaddr" type="hidden" placeholder="点击上传图片">
						<input id="img1" name="img1" type="hidden">
						<input id="img2" name="img2" type="hidden">
						<input id="img3" name="img3" type="hidden">
					</div>
				</div>
	           	<div class="row form">
	           		<div class="col-4">
	           			<div style="width: 200px;height: 210px;">
	           				<img src="content/img/ing_tupian.png" width="200px" height="210px" class="imgback" id="imgback1"/>
			        		<input id="fileupload1" class="fileupload" type="file" name="file" multiple style="width: 200px;height: 210px;position: relative;top: -215px;left: 0px;font-size: 80px;opacity:0;"/>
	           			</div>
			        	<a class="clear" href="javascript:clear(1);">删除</a>
	               	</div>
	               	<div class="col-4">
		               	<div style="width: 200px;height: 210px;">
		               		<img src="content/img/ing_tupian.png" width="200px" height="210px" class="imgback" id="imgback2"/>
				        	<input id="fileupload2" class="fileupload" type="file" name="file" multiple style="width: 200px;height: 210px;position: relative;top: -215px;left: 0px;font-size: 80px;opacity:0;"/>
			        	</div>
			        	<a class="clear" href="javascript:clear(2);">删除</a>
	               	</div>
	               	<div class="col-4">
	               		<div style="width: 200px;height: 210px;">
		               		<img src="content/img/ing_tupian.png" width="200px" height="210px" class="imgback" id="imgback3"/>
				        	<input id="fileupload3" class="fileupload" type="file" name="file" multiple style="width: 200px;height: 210px;position: relative;top: -215px;left: 0px;font-size: 80px;opacity:0;"/>
			        	</div>
			        	<a class="clear" href="javascript:clear(3);">删除</a>
	               	</div>
	           	</div>
				<div class="row form" id="editBtn">	
					<div class="col-12" style="text-align: left;">
	                     <a href="javascript:void(0);" class="Mbtn orange" style="margin-right: 20px;" onclick="save()">保存</a>
	                     <a href="javascript:void(0);" class="Mbtn grey" onclick="cancel()">取消</a>
	                </div>
				</div>
			</form>
		</div>
		
		<script type="text/javascript" src="js/pubGuide/editPubGuide.js"></script>
		<script type="text/javascript">
			var serviceAddress = "<%=SystemConfig.getSystemProperty("fileserver")%>"
		</script>
	</body>
</html>

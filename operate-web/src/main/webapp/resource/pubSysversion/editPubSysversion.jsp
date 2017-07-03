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
		<title>维护版本说明</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/css/laterchange.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
	</head>
	<style type="text/css">
		.form select{width:68%;}
		.breadcrumb{text-decoration:underline;}
		label[for='changelog']{padding-left: 0px!important;}
		.asterisk{position:relative;top:1px;}
	</style>

	<body style="overflow:hidden;">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > <a href="PubSysversion/Index" class="breadcrumb">版本管理</a> > <span id="sysversionPageTitle">新增发布版本</span>
			<button class="SSbtn blue back" onclick="edit()" id="editButton">修改</button>
		</div>
		
		<div class="content">
			<form id="formss" class="form form-imprint" style="padding-top: 30px;">
				<input id="id" name="id" value="${sysversion.id}" type="hidden">
				<input id="hidePlatformtype" type="hidden" value="${sysversion.platformtype}">
				<input id="hideSystemtype" type="hidden" value="${sysversion.systemtype}">
				<input id="hideCurversion" type="hidden" value="${sysversion.curversion}">
				<input id="hideMaxversionno" type="hidden" value="${sysversion.maxversionno}">
				<input id="hideChangelog" type="hidden" value="${sysversion.changelog}">
				<input id="hideReleasedate" type="hidden" value="${sysversion.releasedate}">
				<div class="row form">
					<div class="col-4">
						<label class="imprint-items">App类型<em class="asterisk"></em></label>
						<select id="platformtype" name="platformtype">
							<option value="">选择</option>
							<c:forEach items="${platformtypeList}" var="platformtype">
								<option value="${platformtype.value}">${platformtype.text}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="imprint-items">版本号<em class="asterisk"></em></label>
						<input type="text" placeholder="输入版本号，如V1.1.1" id="curversion" maxlength="20" name="curversion">
					</div>
					<div class="col-4">
						<label class="imprint-items">版本发布日期<em class="asterisk"></em></label>
						<input id="releasedate" name="releasedate" type="text" readonly="readonly" placeholder="选择日期" value="" class="searchDate">
					</div>
				</div>
				<div class="row form">
					<div class="col-4">
						<label class="imprint-items">适用系统<em class="asterisk"></em></label>
						<select id="systemtype" name="systemtype">
							<option value="2">全部</option>
							<c:forEach items="${systemtypeList}" var="systemtype">
								<option value="${systemtype.value}">${systemtype.text}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-6 col-6-update" style="float: left;">
						<label>是否强制更新<em class="asterisk"></em></label>
						<input name="isForceUpdate" type="radio" value="1" checked="checked" />是
						<input name="isForceUpdate" type="radio" value="2" />否
					</div>
				</div>
				<div class="row form">
					<label style="text-align: left;">新版本说明<em class="asterisk"></em></label>
					<textarea rows="20" id="changelog" name="changelog" oninput="limitLength()" style="overflow-y:auto"></textarea>
				</div>
			</form>
			<div class="row" id="editBtn">	
				<div class="col-12" style="text-align: right;">
					<button class="Mbtn orange" style="margin-right: 20px;" onclick="save()">保存</button>
					<button class="Mbtn grey" onclick="cancel()">取消</button>
				</div>
			</div>
		</div>
		
		<script type="text/javascript" src="js/pubSysversion/editPubSysversion.js"></script>
	</body>
</html>

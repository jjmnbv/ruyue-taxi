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
		<title>版本说明</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<style type="text/css">
			.form select{width:68%;}
			.select2-container .select2-choice{height:30px;}
			.select2-container{width:68%;padding:0px;}
			.dataTables_wrapper{margin: 0px 0px 10px 0px;}
			.breadcrumb{text-decoration:underline;}
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.DTFC_ScrollWrapper{
				margin-top:-20px;
			}
		</style>
	</head>
	
	<body>
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > 版本管理
			<button class="SSbtn blue back" onclick="add()" id="addBtn">新增</button>
			<input type="hidden" id="usertype" value="${usertype}">
		</div>
		<div class="content">
			<div class="row form" style="padding-top: 30px;">
				<div class="col-4">
					<label>App类型</label>
					<select id="platformtype">
						<option value="">选择</option>
						<c:forEach items="${platformtypeList}" var="platformtype">
							<option value="${platformtype.value}">${platformtype.text}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-4">
					<label>版本号</label>
					<input style="width: 60%" id="curversion" name="curversion" type="text" placeholder="选择当前版本号">
				</div>
				<div class="col-4">
					<label>发布日期</label>
					<input style="width:30%;" id="startTime" name="startTime" readonly="readonly" type="text" placeholder="开始日期" class="searchDate">至
					<input style="width:30%;" id="endTime" name="endTime" readonly="readonly" type="text" placeholder="结束日期" class="searchDate">
				</div>
			</div>
			<div class="row form">
				<div class="col-4">
					<label>适用系统</label>
					<select id="systemtype">
						<option value="">全部</option>
						<c:forEach items="${systemtypeList}" var="systemtype">
							<option value="${systemtype.value}">${systemtype.text}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-8" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
				</div>
			</div>
			<div class="row">
				<div class="col-10" style="margin-top: 15px;"><h4>产品发布版本信息</h4></div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		<script type="text/javascript" src="js/pubSysversion/index.js"></script>
	</body>
</html>

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
		<title>抵用券详情</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/opuseraccount_media.css" />
		
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

	</head>
	
	<style type="text/css">
	    /* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		/* 固定列样式调整  */
		th, td { white-space: nowrap; }
        div.dataTables_wrapper {
            width: $(window).width();
            margin: 0 auto;
        }
        .DTFC_ScrollWrapper{
			margin-top:-20px;
		}
	</style>

	<body  class="opuseraccount_css_body">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="OpUserAccount/Index">用户账户</a> > 抵用券详情
		<button class="SSbtn red_q back" onclick="callBack()">返回</button>
		</div>
		<div class="content">
			<input name="userId" id="userId" value="${userId}" type="hidden">
			
			<input name="expenseTypeExport" id="expenseTypeExport" type="hidden">
			<input name="startTimeExport" id="startTimeExport" type="hidden">
			<input name="endTimeExport" id="endTimeExport" type="hidden">
			
			<input name="account" id="account" value="${account}" type="hidden">
			<input name="nickName" id="nickName" value="${nickName}" type="hidden">
			
			<div class="row form" style="padding-top: 30px;">
				<div class="col-4 opuseraccount_css_col_1">
					<label>抵用券状态</label>
					<select id="expenseType" name="expenseType">
						<option value="" selected="selected">全部</option>
						<option value="0">未使用</option>
						<option value="1">已使用</option>
						<option value="2">已过期</option>
					</select>
				</div>
				<div class="col-4 opuseraccount_css_col_4">
					<label class="opuseraccount_css_label_1">时间</label>
					<input style="width:30%;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate" readonly="readonly">至
					<input style="width:30%;" id="endTime" name="endTime" type="text" placeholder="结束日期" value="" class="searchDate" readonly="readonly">
				</div>

				<div class="col-4 opuseraccount_css_col_2" style="text-align: right;">
					<button class="Mbtn red_q" onclick="search();">查询</button>
					<button class="Mbtn grey_w" onclick="cancel();">清空</button>
				</div>
	
			</div>
			<div class="row">
			   <label class="col-4" style="margin-top: 15px;"><h4>抵用券信息【<font color="red">${account}</font>】</h4></label>
			   <div class="col-8" style="text-align: right;">
			  	 <button class="Mbtn blue_q" id="export" onclick="exportExcel()">导出数据</button>
			   </div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>

		<script type="text/javascript" src="js/opUserAccount/couponDetail.js"></script>
	</body>
</html>

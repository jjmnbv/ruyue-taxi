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
		<title>抵用券明细</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
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

	<body>
		<div class="crumbs">
		    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="OrganAccount/Index">机构账户</a> > 往来明细
		    <button class="SSbtn blue back" onclick="callBack()">返回</button>
		</div>
		<div class="content">
			<input name="organId" id="organId" value="${organId}" type="hidden">
			
			<input name="typeExport" id="typeExport" type="hidden">
			<input name="startTimeExport" id="startTimeExport" type="hidden">
			<input name="endTimeExport" id="endTimeExport" type="hidden">
			<input name="fullName" id="fullName" value="${fullName}" type="hidden">
			
			<div class="row form" style="padding-top: 30px;">
			    <ul class="tabmenu">
                    <li><a href="OrganAccount/AccountDetail?organId=${organId}&fullName=${fullName}" style="text-decoration: none;">账户明细</a></li>
                    <li class="on">抵用券明细</li>
                </ul>
			    
				<div class="col-4">
					<label>类型</label>
					<select id="type" name="type">
						<option value="" selected="selected">全部</option>
						<option value="1">充值返券</option>
						<option value="2">账单结算扣款</option>
						<option value="3">注册返券</option>
						<option value="4">活动返券</option>
						<option value="5">违约清零</option>
					</select>
				</div>
				<div class="col-4">
					<label>时间</label>
					<input style="width:30%;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate" readonly="readonly">至
					<input style="width:30%;" id="endTime" name="endTime" type="text" placeholder="结束日期" value="" class="searchDate" readonly="readonly">					
				</div>

				<div class="col-4" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey" onclick="clearParameter();">清空</button>
				</div>
	
			</div>
			<div class="row">
			   <div class="col-10" style="margin-top: 15px;width:60%;"><h4>抵用券明细【<span class="font_red">${fullName}</span>】</h4></div>
			   <div class="col-2" style="text-align: right;width:40%;">
			       <button class="Mbtn blue" onclick="exportExcel()">导出数据</button>
			   </div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>

		<script type="text/javascript" src="js/organAccount/couponDetail.js"></script>
	</body>
</html>

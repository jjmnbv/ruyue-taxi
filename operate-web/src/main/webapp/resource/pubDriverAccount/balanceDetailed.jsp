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
		<title>余额明细</title>
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
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="PubDriverAccount/Index"> 司机账户</a> > 余额明细
			<button class="SSbtn blue back" onclick="callback()">返回</button>
		</div>
			
		<div class="content">
			<input name="driverid" id="driverid" value="${driverid}" type="hidden">
			
			<input name="driverName" id="driverName" value="${driverName}" type="hidden">
			<input name="driverAccount" id="driverAccount" value="${driverAccount}" type="hidden">
			
			<div class="row form" style="padding-top: 30px;">
				<div class="col-3">
					<label>交易类型</label>
					<select id="queryTradetype" name="queryTradetype">
						<option value="" selected="selected">全部</option>
						<option value="0">充值</option>
						<option value="23">提现</option>
						<option value="1">订单结算</option>
						<option value="4">订单收入</option>
					</select>
					<input id="queryTradetypes" name="queryTradetypes" value="" type="hidden"/>
				</div>
				<div class="col-3">
					<label>时间</label>
					<input style="width:30%;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate" readonly="readonly">至
					<input style="width:30%;" id="endTime" name="endTime" type="text" placeholder="结束日期" value="" class="searchDate" readonly="readonly">					
					<input name="startTimes" id="startTimes" type="hidden">
					<input name="endTimes" id="endTimes" type="hidden">
				</div>

				<div class="col-6" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey" onclick="cancel();">清空</button>
				</div>
	
			</div>
			<div class="row">
			   <div class="col-10" style="margin-top: 15px;width:60%;"><h4>余额明细【${driverName} ${driverAccount}】</h4></div>
			   <div class="col-2" style="text-align: right;width:40%;">
			       <button class="Mbtn orange" onclick="exportExcel()">导出数据</button>
			   </div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>

		<script type="text/javascript" src="js/pubDriverAccount/balanceDetailed.js"></script>
	</body>
</html>

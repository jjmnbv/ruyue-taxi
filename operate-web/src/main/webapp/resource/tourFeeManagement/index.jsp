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
		<title>结算管理</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" />
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
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>

	</head>
	
	<style type="text/css">
		/* 前端对于类似页面的补充 */
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
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
		
		.opuseraccount_css_body .grey {position: relative;bottom: 2px;}
	</style>
	
	<body class="opuseraccount_css_body">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 结算管理</div>
		<div class="content">
		    <input id="ordernoExport" name="ordernoExport" type="hidden"/>
		    <input id="platenoExport" name="platenoExport" type="hidden"/>
		    <input id="jobnumExport" name="jobnumExport" type="hidden"/>
		    <input id="driveridExport" name="driveridExport" type="hidden"/>
		    <input id="paymentstatusExport" name="paymentstatusExport" type="hidden"/>
		    <input id="companyidExport" name="companyidExport" type="hidden"/>
		    <input id="tradenoExport" name="tradenoExport" type="hidden"/>
		    <input id="starttimeExport" name="starttimeExport" type="hidden"/>
		    <input id="endtimeExport" name="endtimeExport" type="hidden"/>
		    
			<div class="row form" style="padding-top: 30px;">
				<div class="col-3">
					<label>订单号</label><input id="orderno" name="orderno" type="text" placeholder="订单号">
				</div>
				
				<div class="col-3">
					<label>车牌号</label><input id="plateno" name="plateno" type="text" placeholder="车牌号">
				</div>
				
				<div class="col-3">
					<label>资格证号</label><input id="jobnum" name="jobnum" type="text" placeholder="资格证号">
				</div>
				
				<div class="col-3">
					<label>司机</label><input id="driverid" name="driverid" type="text" placeholder="姓名/手机号">
				</div>
			</div>
			<div class="row form">
				<div class="col-3">
					<label>结算状态</label>
					<select id="paymentstatus" name="paymentstatus" style="width: 68%;margin-left:-4px;">
						<option value="" selected="selected">全部</option>
						<option value="0">未结算</option>
						<option value="1">已结算</option>
					</select>
				</div>
				
				<div class="col-3">
					<label>服务车企</label>
					<select id="companyid" name="companyid" style="width: 66%;margin-left:-4px;">
						<option value="" selected="selected">全部</option>
						<c:forEach var="companyidList" items="${companyidList}">
							<option value="${companyidList.id}">${companyidList.text}</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="col-3">
					<label>交易流水号</label><input id="tradeno" name="tradeno" type="text" placeholder="交易流水号">
				</div>
				
				<div class="col-3">
					<label>收款时间</label>
					<input style="width:30%;" id="starttime" name="starttime" type="text" placeholder="开始时间" value="" class="searchDate" readonly="readonly">至
					<input style="width:30%;" id="endtime" name="endtime" type="text" placeholder="结束时间" value="" class="searchDate" readonly="readonly">
				</div>
			</div>
			<div class="row form">
			    <div class="col-12" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey" onclick="clearSearch();">清空</button>
				</div>
			</div>
			<div class="row">
				<div class="col-10" style="margin-top: 15px;width:60%;"><h4>行程费用结算信息（注：线下付现订单）</h4></div>
			    <div class="col-2" style="text-align: right;width:40%;">
			        <button class="Mbtn blue" onclick="exportExcel()">导出数据</button>
			    </div>
		    </div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<script type="text/javascript" src="js/tourFeeManagement/index.js"></script>
	</body>
</html>

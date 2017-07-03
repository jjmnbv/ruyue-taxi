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
<meta name="description" content="">
<meta name="keywords" content="">
<title>首页—超级管理员</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="content/css/common.css" />
<link rel="stylesheet" type="text/css" href="content/css/zstyle.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
<script type="text/javascript" src="js/basecommon.js"></script>
<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<style type="text/css">
	.paginate_button,
	#dataGrid_wrapper #dataGrid_paginate>span{
		cursor:pointer;
	}
	.div_container{
		display: inline-block;
	    line-height: 38px;
	    border: 1px solid #dadada;
	    border-radius: 2px;
	    padding-left: 10px;
	    margin-right: 119px;
	    width:244px;
	    margin-bottom: 20px;
	    margin-right: 56px;
	}
/* 	.select_val{
		height:30px;
		line-height:30px;
	} */
</style>	
</head>
<body>
		<div class="content">
			<div class="con_box  rule">
				<label>服务车企</label>
            	<div class="select_box" style="height: 40px;line-height: 38px;width: 244px;margin-left: -4px;">
	                <input placeholder="请选择服务车企" class="select_val" data-value="" value="" id="queryCompany">
	                <ul class="select_content">
	                	<li data-value="">全部服务车企</li>
	                	<c:forEach items="${leLeasecompany}" var="leLeasecompany">
	                		<li data-value="${leLeasecompany.id}">${leLeasecompany.shortName}</li>
	                	</c:forEach>
	                </ul>
	                <input id="queryCompanys" value="" type="hidden"/>
       			</div>
				<label>订单号</label>
				<div class="div_container">
					<input type="text" placeholder="订单的编号" id="queryOrderNo" value=""/>
				</div>
				<input id="queryOrderNos" value="" type="hidden"/>
				<button type="button" class="btn_red" onclick="search();">查询</button>
				<label>用车时间</label>
				<div class="datebox">
					<input type="text" placeholder="请选择时间" class="date" id="startTime" value="" readonly="readonly"><input type="text" placeholder="请选择时间" class="date" style="padding-left: 10px;" id="endTime" value="" readonly="readonly">
					<input id="startTimes" value="" type="hidden"/>
					<input id="endTimes" value="" type="hidden"/>
				</div>
				<label style="margin-left:-57px;">下单人</label>
				<div class="div_container">
					<input type="text" placeholder="姓名或手机号码" id="queryUserMessage" value=""/>
				</div>
				<input id="queryUserMessages" value="" type="hidden"/>
				<button class="btn_grey" onclick="empty();">清空</button>
				<h3>
					订单信息 <span class="daochu" style="cursor: pointer;" onclick="exportExcel();">导出数据</span>
				</h3>
				<div class="select_box queryOrder">
					<input placeholder="全部订单状态" class="select_val" data-value="" value="" id="queryOrder">
					<ul class="select_content">
						<li data-value="">全部订单状态</li>
						<li data-value="0,1">等待接单</li>
						<li data-value="2,3,4">等待服务</li>
						<li data-value="5,6">服务中</li>
						<!-- <li data-value="7-0">未支付</li>
						<li data-value="7-1">已支付</li> -->
						<li data-value="7-4">未结算</li>
						<li data-value="7-2">结算中</li>
						<li data-value="7-3">已结算</li>
						<!-- <li data-value="9">已完成</li> -->
						<li data-value="8">已取消</li>
					</ul>
					<input id="queryOrders" value="" type="hidden"/>
				</div>
				<div class="select_box queryVehicleMode">
					<input placeholder="全部用车方式" class="select_val" data-value="" value="" id="queryVehicleMode">
					<ul class="select_content">
						<li data-value="">全部用车方式</li>
						<li data-value="1">约车</li>
						<li data-value="2">接机</li>
						<li data-value="3">送机</li>
					</ul>
					<input id="queryVehicleModes" value="" type="hidden"/>
				</div>
				<div class="select_box queryPaymentMethod">
					<input placeholder="全部支付方式" class="select_val" data-value="" value="" id="queryPaymentMethod">
					<ul class="select_content">
						<li data-value="">全部支付方式</li>
						<!-- <li data-value="0">个人支付</li> -->
						<li data-value="2">机构支付</li>
						<!-- <li data-value="1">个人垫付</li> -->
					</ul>
					<input id="queryPaymentMethods" value="" type="hidden"/>
				</div>
				<button class="btn_grey" onclick="empty1();">清空</button>
				<table id="dataGrid"></table>
			</div>
		</div>
		<script type="text/javascript" src="js/orgIndex/index.js"></script>
		<script type="text/javascript">
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
		</script>
</body>
</html>
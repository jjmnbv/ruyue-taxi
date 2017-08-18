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
<link rel="stylesheet" type="text/css"
	href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css" />
<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript"
	src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
<script type="text/javascript"
	src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
</head>
<body>
		<div class="content">
			<div class="con_box  rule">
				<label style="width: 80px;">用车时间</label>
				<div class="datebox">
					<input type="text" placeholder="请选择时间" class="date" id="startTime" value="" readonly="readonly"><input type="text" placeholder="请选择时间" class="date" style="padding-left: 10px;" id="endTime" value="" readonly="readonly">
					<input id="startTimes" value="" type="hidden"/>
					<input id="endTimes" value="" type="hidden"/>
				</div>
				<button type="button" class="btn_red" onclick="search();">查询</button><br>
				<label style="width: 80px;">乘车人信息</label><input type="text" placeholder="姓名或手机号码" id="queryUserMessage" value="" style="margin-right: 0px;"/>
				<input id="queryUserMessages" value="" type="hidden"/>
				<button class="btn_grey" onclick="empty();" style="margin-left:56px;">清空</button>
				<h3>
					订单信息 <span class="daochu" style="cursor: pointer;" onclick="exportExcel();">导出数据</span>
				</h3>
				<div class="select_box queryOrder"  style="width: 17%;">
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
						<li data-value="7-9">已关闭</li>
					</ul>
					<input id="queryOrders" value="" type="hidden"/>
				</div>
				<div class="select_box queryVehicleMode"  style="width: 16%;">
					<input placeholder="全部用车方式" class="select_val" data-value="" value="" id="queryVehicleMode">
					<ul class="select_content">
						<li data-value="">全部用车方式</li>
						<li data-value="1">约车</li>
						<li data-value="2">接机</li>
						<li data-value="3">送机</li>
					</ul>
					<input id="queryVehicleModes" value="" type="hidden"/>
				</div>
				<div class="select_box queryPaymentMethod"  style="width: 16%;">
					<input placeholder="全部支付方式" class="select_val" data-value="" value="" id="queryPaymentMethod">
					<ul class="select_content">
						<li data-value="">全部支付方式</li>
						<!-- <li data-value="0">个人支付</li> -->
						<li data-value="2">机构支付</li>
						<!-- <li data-value="1">个人垫付</li> -->
					</ul>
					<input id="queryPaymentMethods" value="" type="hidden"/>
				</div>
				<div class="select_box queryExpensetype" style="width: 16%;">
					<input placeholder="全部费用类型" class="select_val" data-value="" value="" id="queryExpensetype">
					<ul class="select_content">
						<li data-value="">全部费用类型</li>
						<li data-value="1">正常行驶</li>
						<li data-value="2">取消处罚</li>
					</ul>
					<input id="queryExpensetypes" value="" type="hidden"/>
				</div>
				<button class="btn_grey" onclick="empty1();">清空</button>
				<table id="dataGrid"></table>
			</div>
		</div>
		<script type="text/javascript" src="js/myOrder/index.js"></script>
		<script type="text/javascript">
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
		</script>
</body>
</html>
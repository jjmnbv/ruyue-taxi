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
		<title>B2C联盟订单</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
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
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		
		<style type="text/css">
			.orderhref{
		    	text-decoration:underline;
		    	color:green;
		    }
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
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > B2C联盟订单</div>
		<div class="content">
			<ul class="tabmenu" style="padding-top: 20px;">
				<li><a href="OrderManage/NetAboutCarIndex" style="text-decoration: none;">网约车订单</a></li>
				<li class="on">出租车订单</li>
			</ul>
			
			<input name="orderNoExport" id="orderNoExport" type="hidden">
			<input name="orderStatusExport" id="orderStatusExport" type="hidden">
			<input name="orderPersonExport" id="orderPersonExport" type="hidden">
			<input name="driverExport" id="driverExport" type="hidden">
			<input name="payTypeExport" id="payTypeExport" type="hidden">
			<input name="cancelPartyExport" id="cancelPartyExport" type="hidden">
			<input name="orderSourceExport" id="orderSourceExport" type="hidden">
			<input name="tradeNoExport" id="tradeNoExport" type="hidden">
			<input name="minUseTimeExport" id="minUseTimeExport" type="hidden">
			<input name="maxUseTimeExport" id="maxUseTimeExport" type="hidden">
            <input name="leasescompanyidExport" id="leasescompanyidExport" type="hidden">
		
			<div class="row form" style="margin-top:20px;">
				<div class="col-3">
					<label>订单来源</label>
					<select id="orderSource" style="margin-left: -4px">
						<option value="">全部</option>
						<option value="CG">乘客端 | 个人</option>
						<option value="CY">运管端</option>
					</select>
				</div>
				<div class="col-3">
                    <label>合作方</label><input id="leasescompanyid" type="hidden" placeholder="服务车企">
                </div>
				<div class="col-3">
					<label>订单状态</label>
					<select id="orderStatus">
						<option value="">全部</option>
						<option value="2">待出发</option>
						<option value="3">已出发</option>
						<option value="4">已抵达</option>
						<option value="6">服务中</option>
						<option value="9">待确费</option>
						<option value="7567">未付结</option>
						<option value="70">未支付</option>
						<option value="74">未结算</option>
						<option value="78">已付结</option>
						<option value="71">已支付</option>
						<option value="73">已结算</option>
						<option value="8">已取消</option>
					</select>
				</div>
				<div class="col-3">
					<label>下单人</label><input id="orderPerson" type="text" placeholder="请选择下单人">
				</div>
			</div>
			<div class="row form">
				<div class="col-3">
					<label>订单号</label><input id="orderNo" type="text" placeholder="订单号">
				</div>
				<div class="col-3">
					<label>司机</label><input id="driver" type="text" placeholder="姓名/手机号">
				</div>
				<div class="col-3">
					<label>取消方</label>
					<select id="cancelParty">
						<option value="">全部</option>
						<option value="3">下单人</option>
						<option value="1">客服</option>
						<option value="4">系统</option>
					</select>
				</div>
				<div class="col-3">
					<label>支付渠道</label>
					<select id="payType" style="margin-left: -4px">
						<option value="">全部</option>
						<option value="2">微信支付</option>
						<option value="3">支付宝支付</option>
						<option value="1">余额支付</option>
					</select>
				</div>
			</div>
			<div class="row form">
                <!-- <div class="col-3">
                    <label>服务车企</label><input id="leasescompanyid" type="hidden" placeholder="服务车企">
                </div> -->
                <div class="col-3">
					<label>交易流水号</label><input id="tradeNo" type="text" placeholder="交易流水号">
				</div>
			    <div class="col-4">
					<label style="width:22.5%;padding-right:11px;">用车时间</label>
					<input style="width:30%;" id="minUseTime" name="minUseTime" readonly="readonly" type="text" placeholder="开始时间" value="" class="searchDate">
					至
			        <input style="width:30%;" id="maxUseTime" name="maxUseTime" readonly="readonly" type="text" placeholder="结束时间" value="" class="searchDate">
				</div>
				<div class="col-5" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey" onclick="clearParameter();">清空</button>
				</div>
			</div>
			<div class="row">
			   <div class="col-10" style="margin-top: 15px;"><h4>订单信息</h4></div>
			   <div class="col-2" style="text-align: right;">
			       <button class="Mbtn blue" onclick="exportExcel()">导出数据</button>
			   </div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<script type="text/javascript" src="js/tocOrderManage/taxiorder.js"></script>
	</body>
</html>

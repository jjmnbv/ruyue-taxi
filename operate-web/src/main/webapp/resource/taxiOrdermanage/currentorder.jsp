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
		<title>出租车订单</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css" />
		
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
			.form select{width:68%;}
			.select2-container .select2-choice{height:30px;}
			.select2-container{width:68%;padding:0px;}
			.dataTables_wrapper{margin:10px 0px;}
			.tabmenu{margin-bottom:10px;}
			.breadcrumb{text-decoration:underline;}
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			tbody tr td:first-child{
				text-align:left;
			}
			.w400 label.error{padding-left:15px!important;}
		</style>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 出租车订单</div>
		<div class="content">
			<ul class="tabmenu" style="padding-top: 10px;">
				<li><a href="TaxiOrderManage/Index" style="text-decoration: none;">待接订单</a></li>
				<li class="on">当前订单</li>
				<li><a href="TaxiOrderManage/AbnormalOrderIndex" style="text-decoration: none;">异常订单</a></li>
				<li><a href="TaxiOrderManage/WaitgatheringOrderIndex" style="text-decoration: none;">待收款订单</a></li>
				<li><a href="TaxiOrderManage/HistoryOrderIndex" style="text-decoration: none;">已完成订单</a></li>
                <li><a href="TaxiOrderManage/CancelOrderIndex" style="text-decoration: none;">已取消订单</a></li>
			</ul>
		
			<div class="row form" style="margin-bottom: 20px;">
                <div class="col-3">
                    <label>订单来源</label>
                    <select id="ordersource">
                        <option value="">全部</option>
                        <option value="CG">乘客端 | 个人</option>
                        <option value="CY">运管端</option>
                    </select>
                </div>
                <div class="col-3">
                    <label>服务车企</label><input id="belongleasecompany" type="hidden" placeholder="服务车企">
                </div>
                <div class="col-3">
                    <label>司机</label><input id="drivername" type="hidden" placeholder="请选择司机">
                </div>
                <div class="col-3">
                    <label class="ordermanage_css_label_1">下单人</label><input id="orderperson" type="hidden" placeholder="请选择下单人">
                </div>
                <div class="col-3">
                    <label>订单号</label><input id="orderno" type="text" style="width: 68%" placeholder="订单号">
                </div>
                <div class="col-3">
                    <label>订单状态</label>
                    <select id="orderstatus">
                        <option value="">全部</option>
                        <option value="2">待出发</option>
                        <option value="3">已出发</option>
                        <option value="4">已抵达</option>
                        <option value="6">服务中</option>
                        <option value="9">待确费</option>
                    </select>
                </div>
                <div class="col-3" style="white-space: nowrap;">
                    <label>用车时间</label>
                    <input style="width:42%;" id="minUseTime" name="minUseTime" readonly="readonly" type="text" placeholder="开始日期" value="" class="searchDate">
                    至
                    <input style="width:42%;" id="maxUseTime" name="maxUseTime" readonly="readonly" type="text" placeholder="结束日期" value="" class="searchDate">
                </div>
				<div class="col-3" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey_b" onclick="initSearch();">清空</button>
				</div>
			</div>
			<div class="row" style="position:relative;">
				<div class="col-6">
					<h4 style="position:relative;top:20px;">当前订单信息</h4>
				</div>
			</div>
			<table id="manualOrderdataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<script type="text/javascript" src="js/taxiOrdermanage/currentorder.js"></script>
	</body>
</html>

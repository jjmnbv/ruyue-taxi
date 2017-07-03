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
		<title>个人订单</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
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
		<script type="text/javascript" src="js/basecommon.js"></script>
		
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
		</style>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 个人订单</div>
		<div class="content">
			<ul class="tabmenu" style="padding-top: 20px;">
				<li class="on">待人工派单</li>
				<li><a href="OrderManage/PersonCurrentOrderIndex" style="text-decoration: none;">当前订单</a></li>
				<li><a href="OrderManage/PersonAbnormalOrderIndex" style="text-decoration: none;">异常订单</a></li>
				<li><a href="OrderManage/PersonWaitgatheringOrderIndex" style="text-decoration: none;">待收款订单</a></li>
				<li><a href="OrderManage/PersonHistoryOrderIndex" style="text-decoration: none;">已完成订单</a></li>
			</ul>
		
			<div class="row form" style="margin-top:20px;">
				<div class="col-3">
					<label>订单号</label><input id="orderno" type="text" style="width: 69%" placeholder="订单号">
				</div>
				<div class="col-3">
					<label>订单类型</label>
					<select id="ordertype">
						<option value="">全部</option>
						<option value="1">约车</option>
						<option value="2">接机</option>
						<option value="3">送机</option>
					</select>
				</div>
				<div class="col-3">
					<label class="ordermanage_css_label_1">下单人</label><input id="orderperson" type="hidden" placeholder="请选择下单人">
				</div>
				<div class="col-3">
					<label>订单来源</label>
					<select id="ordersource">
						<option value="">全部</option>
						<option value="BC">乘客端 | 因公</option>
						<option value="CJ">乘客端 | 因私</option>
						<option value="BZ">租赁端 | 因公</option>
						<option value="CZ">租赁端 | 因私</option>
						<option value="BJ">机构端</option>
					</select>
				</div>
				
				<div class="col-12" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey_b" onclick="initSearch();">清空</button>
				</div>
			</div>
			<div class="row" style="position:relative;">
				<div class="col-6">
					<h4 style="position:relative;top:20px;">待人工派单订单信息</h4>
				</div>
			</div>
			<table id="manualOrderdataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<script type="text/javascript" src="js/personordermanage/index.js"></script>
	</body>
</html>

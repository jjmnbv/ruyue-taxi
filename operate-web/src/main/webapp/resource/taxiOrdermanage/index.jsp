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
			.breadcrumb{text-decoration:underline;}
			.tabmenu{margin-bottom:10px;}
			th, td { white-space: nowrap;}
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.col-3{width: 23%;}
			.form label.error{padding-left:17px!important;}
			#cancelDetail table{
				width: 100%;
			}
			#cancelDetail td{
				padding-right: 18px;
				padding-bottom: 5px;
				color:#9e9e9e;
			}
		</style>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 出租车订单</div>
		<div class="content">
			<ul class="tabmenu" style="padding-top: 10px;">
				<li class="on">待接订单</li>
				<li><a href="TaxiOrderManage/CurrentOrderIndex" style="text-decoration: none;">当前订单</a></li>
				<li><a href="TaxiOrderManage/AbnormalOrderIndex" style="text-decoration: none;">异常订单</a></li>
				<li><a href="TaxiOrderManage/WaitgatheringOrderIndex" style="text-decoration: none;">待收款订单</a></li>
				<li><a href="TaxiOrderManage/HistoryOrderIndex" style="text-decoration: none;">已完成订单</a></li>
                <li><a href="TaxiOrderManage/CancelOrderIndex" style="text-decoration: none;">已取消订单</a></li>
			</ul>
		
			<div class="row form">
                <div class="col-3">
                    <label>订单来源</label>
                    <select id="ordersource">
                        <option value="">全部</option>
                        <option value="CG">乘客端 | 个人</option>
                        <option value="CY">运管端</option>
                    </select>
                </div>
                <div class="col-3">
                    <label>订单号</label><input id="orderno" type="text" style="width: 69%" placeholder="订单号">
                </div>
                <div class="col-3">
                    <label class="ordermanage_css_label_1">下单人</label><input id="orderperson" type="hidden" placeholder="请选择下单人">
                </div>
                <div class="col-3 ordermanage_css_col_1" style="width: 31%">
                    <label>下单时间</label>
                    <input style="width:33%;" id="minUseTime" name="minUseTime" readonly="readonly" type="text" placeholder="开始日期" value="" class="searchDate">
                    至
                    <input style="width:33%;" id="maxUseTime" name="maxUseTime" readonly="readonly" type="text" placeholder="结束日期" value="" class="searchDate">
                </div>
				<div class="col-12" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey_b" onclick="initSearch();">清空</button>
				</div>
			</div>
			<div class="row" style="position:relative;">
				<div class="col-6">
					<h4 style="position:relative;top:20px;">待接订单信息</h4>
				</div>
			</div>
			<table id="manualOrderdataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>

		<div class="pop_box" id="cancelpartyFormDiv" style="display: none;">
			<div class="tip_box_b" style="width: 600px;">
				<h3>取消订单</h3>
				<img src="content/img/btn_guanbi.png" class="close" alt="关闭">
				<div style="width: 540px">
					<input type="hidden" id="ordernoHide">
					<input type="hidden" id="ordertypeHide">
					<input type="hidden" id="usetypeHide">
					<input type="hidden" id="identifyingHide">
					<input type="hidden" id="orderstatusHide">
					<form id="cancelpartyForm" method="get" class="form">
						<div class="row" style="padding-bottom: 18px">
							<label style="float: left;">责任方<em class="asterisk"></em></label>
							<select id="dutyparty" name="dutyparty" style="width: 60%" onchange="showCancelreason();">
								<option value="">请选择</option>
								<option value="1">乘客</option>
								<option value="3">客服</option>
								<option value="4">平台</option>
							</select>
						</div>
						<div class="row" style="padding-bottom: 18px">
							<label style="float: left;">取消原因<em class="asterisk"></em></label>
							<select id="cancelreason" name="cancelreason" style="width: 60%" onchange="showDutyparty();">
								<option value="">请选择</option>
								<option value="1">不再需要用车</option>
								<option value="5">业务操作错误</option>
								<option value="6">暂停供车服务</option>
							</select>
						</div>
						<div id="cancelDetail" style="margin-left: 40px;">

						</div>
					</form>
					<button class="Lbtn red" onclick="save()">提交</button>
					<button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
				</div>
			</div>
		</div>
		
		<script type="text/javascript" src="js/taxiOrdermanage/index.js"></script>
	</body>
</html>

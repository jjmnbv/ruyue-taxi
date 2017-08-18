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
			.stab{border-bottom:2px solid #ededed;}
			.stab>div{display:inline-block;padding:4px 14px;text-align:center;}
			.shen_on{background:#ededed;}
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.w400 label.error{padding-left:15px!important;}
			tbody tr td:first-child{
				text-align:left;
			}
		</style>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 出租车订单</div>
		<div class="content">
			<ul class="tabmenu" style="padding-top: 10px;">
				<li><a href="TaxiOrderManage/Index" style="text-decoration: none;">待接订单</a></li>
				<li><a href="TaxiOrderManage/CurrentOrderIndex" style="text-decoration: none;">当前订单</a></li>
				<li class="on">异常订单</li>
				<li><a href="TaxiOrderManage/WaitgatheringOrderIndex" style="text-decoration: none;">待收款订单</a></li>
				<li><a href="TaxiOrderManage/HistoryOrderIndex" style="text-decoration: none;">已完成订单</a></li>
                <li><a href="TaxiOrderManage/CancelOrderIndex" style="text-decoration: none;">已取消订单</a></li>
			</ul>
			
			<ul class="tabbox">
				<li style="display:block">
					<div class="stab">
						<div><a href="TaxiOrderManage/AbnormalOrderIndex" style="text-decoration: none;">待复核</a></div>
						<div class="shen_on">已复核</div>
					</div>
					<div class="stabox">
						<div class="row form" style="margin-top: 40px;">
							<div class="col-3">
								<label>订单来源</label>
								<select id="ordersource">
									<option value="">全部</option>
									<option value="CG">乘客端 | 个人</option>
									<option value="CY">运管端</option>
								</select>
							</div>
							<div class="col-3">
								<label>复核方</label>
								<select id="reviewperson">
									<option value="">全部</option>
									<option value="1">司机</option>
									<option value="2">乘客</option>
								</select>
							</div>
							<div class="col-3">
								<label>付款方式</label>
								<select id="paymentmethod">
									<option value="">全部</option>
									<option value="1">线下付现</option>
									<option value="0">在线支付</option>
								</select>
							</div>
							<div class="col-3">
								<label>订单状态</label>
								<select id="paymentstatus">
									<option value="">全部</option>
									<option value="5">未付结</option>
									<option value="0">未支付</option>
									<option value="4">未结算</option>
									<option value="8">已付结</option>
									<option value="1">已支付</option>
									<option value="3">已结算</option>
								</select>
							</div>
							<div class="col-3">
								<label>订单号</label><input id="orderno" type="text" placeholder="订单号">
							</div>
							<div class="col-9" style="text-align: right;">
								<button class="Mbtn green_a" onclick="search();">查询</button>
								<button class="Mbtn grey_b" onclick="initSearch();">清空</button>
							</div>
						</div>
						<div class="row" style="position:relative;top:20px;">
							<div class="col-6">
								<h4 style="position:relative;top:20px;">已复核订单信息</h4>
							</div>
							<div class="col-6" style="text-align: right;">
								<button href="javascript:void(0);" class="Mbtn blue_q" onclick="exportOrder()" id="exportBtn">导出数据</button>
							</div>
						</div>
						<table id="manualOrderdataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%">
							<thead>
								<tr>
									<th rowspan="2">操作</th>
									<th rowspan="2">订单来源</th>
									<th rowspan="2">订单号</th>
									<th rowspan="2">订单状态</th>
									<th rowspan="2">复核方</th>
									<th rowspan="2">付款方式</th>
									<th rowspan="2">差异金额(元)</th>
									<th colspan="3" style="border-bottom-width: 0px">复核后</th>
									<th rowspan="2">服务车企</th>
								</tr>
								<tr>
									<th>行程费用(元)</th>
									<th>实付金额(元)</th>
									<th style="border-right: 1px solid #ddd;">优惠金额(元)</th>
								</tr>
							</thead>
						</table>
					</div>
				</li>
			</ul>
		</div>
		
		<div class="pop_box" id="cancelpartyFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3>申请复核</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<input type="hidden" id="orderno" name="orderno">
	            	<form id="cancelpartyForm" method="get" class="form">
	            		<div class="row" style="padding-bottom: 18px">
		            		<label style="float: left;">复核方<em class="asterisk"></em></label>
		            		<select id="reviewpersonAgain" name="reviewpersonAgain" style="width: 60%">
		            			<option value="">选择复核方</option>
		            			<option value="1">司机</option>
		            			<option value="2">下单人</option>
		            		</select>
		            	</div>
		            	<div class="row">
			                <label style="float: left;">申请原因<em class="asterisk"></em></label>
			                <textarea id="reasonTextarea" name="reasonTextarea" style="width: 60%" rows="2" cols="3" maxlength="200" placeholder="填写申请复核原因"></textarea>
			            </div>
	            	</form>
	                <button class="Lbtn red" onclick="save()">确定</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<script type="text/javascript" src="js/taxiOrdermanage/wasabnormalorder.js"></script>
	</body>
</html>

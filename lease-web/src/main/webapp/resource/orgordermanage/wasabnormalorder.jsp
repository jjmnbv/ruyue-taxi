<%@page import="com.szyciov.util.SystemConfig"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String yingyan_ak = SystemConfig.getSystemProperty("yingyan_ak");
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>异常订单 - 已复核</title>
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
			.dataTables_wrapper{margin: 0px 0px 10px 0px;}
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
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 机构订单</div>
		<div class="content">
			<ul class="tabmenu" style="padding-top: 30px;">
				<li><a href="OrderManage/OrgOrderIndex" style="text-decoration: none;">待人工派单</a></li>
				<li><a href="OrderManage/OrgCurrentOrderIndex" style="text-decoration: none;">当前订单</a></li>
				<li class="on">异常订单</li>
				<li><a href="OrderManage/OrgWaitgatheringOrderIndex" style="text-decoration: none;">待收款订单</a></li>
				<li><a href="OrderManage/OrgHistoryOrderIndex" style="text-decoration: none;">已完成订单</a></li>
			</ul>
			
			<ul class="tabbox">
				<li style="display:block">
					<div class="stab">
						<div><a href="OrderManage/OrgAbnormalOrderIndex" style="text-decoration: none;">待复核</a></div>
						<div class="shen_on">已复核</div>
					</div>
					<div class="stabox">
						<div class="row form" style="margin-top: 40px;">
							<div class="col-3">
								<label>订单号</label><input id="orderno" type="text" style="width: 68%" placeholder="订单号">
							</div>
							<div class="col-3">
								<label>订单类型</label>
								<select id="ordertype" style="margin-left: -3px">
									<option value="">全部</option>
									<option value="1">约车</option>
									<option value="2">接机</option>
									<option value="3">送机</option>
								</select>
							</div>
							<div class="col-3">
								<label>所属机构</label><input id="organName" type="hidden" placeholder="请选择机构名称">
							</div>
							<div class="col-3">
								<label>复核方</label>
								<select id="reviewperson">
									<option value="">全部</option>
									<option value="1">司机</option>
									<option value="2">下单人</option>
								</select>
							</div>
							<div class="col-3">
								<label>订单来源</label>
								<select id="ordersource" style="margin-left: -3px">
									<option value="">全部</option>
									<option value="BC">乘客端 | 因公</option>
									<option value="BZ">租赁端 | 因公</option>
									<option value="BJ">机构端</option>
								</select>
							</div>
                            <div class="col-3">
                                <label>服务车企</label><input id="leasescompanyid" type="hidden" placeholder="服务车企">
                            </div>
							<div class="col-6" style="text-align: right;">
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
						<table id="manualOrderdataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
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
	            	<form id="cancelpartyForm" method="get">
	            		<div class="row form" style="padding-bottom: 18px">
		                	<div class="col-12">
		                		<label style="float: left;">复核方<em class="asterisk"></em></label>
			            		<select id="reviewpersonAgain" name="reviewpersonAgain" style="width: 60%">
			            			<option value="">选择复核方</option>
			            			<option value="1">司机</option>
			            			<option value="2">下单人</option>
			            		</select>
		                	</div>
		            	</div>
		            	<div class="row form">
		            		<div class="col-12">
				                <label style="float: left;">申请原因<em class="asterisk"></em></label>
				                <textarea id="reasonTextarea" name="reasonTextarea" style="width: 60%" rows="2" cols="3" maxlength="200" placeholder="填写申请复核原因"></textarea>
			            	</div>
			            </div>
	            	</form>
	                <button class="Lbtn red" onclick="save()">确定</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<script type="text/javascript" src="js/orgordermanage/wasabnormalorder.js"></script>
	</body>
</html>

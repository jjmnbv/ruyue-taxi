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
		<title>历史订单</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css" />
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript" src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="content/js/common.js"></script>
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
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.w400 label.error{padding-left:15px!important;}
			tbody tr td:first-child{
				text-align:left;
			}
            .ordermanage_css_body #s2id_orderperson {
                float: none;
            }
            .col-3{width: 23%;}
		</style>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 因私网约车订单</div>
		<div class="content">
			<ul class="tabmenu" style="padding-top: 30px;">
				<li ><a href="OrderManage/PersonOrderIndex" style="text-decoration: none;">待接订单</a></li>
				<li><a href="OrderManage/PersonCurrentOrderIndex" style="text-decoration: none;">当前订单</a></li>
				<li><a href="OrderManage/PersonAbnormalOrderIndex" style="text-decoration: none;">异常订单</a></li>
				<li><a href="OrderManage/PersonWaitgatheringOrderIndex" style="text-decoration: none;">待收款订单</a></li>
                <li><a href="OrderManage/PersonHistoryOrderIndex" style="text-decoration: none;">已完成订单</a></li>
				<li class="on">已取消订单</li>
			</ul>
		
			<div class="row form" style="margin-top: 40px;">
                <div class="col-3">
                    <label>订单来源</label>
                    <select id="ordersource">
                        <option value="">全部</option>
                        <option value="CJ">乘客端 | 因私</option>
                        <option value="CZ">租赁端 | 因私</option>
                    </select>
                </div>
                <div class="col-3">
                    <label>订单性质</label>
                    <select id="ordernature">
                        <option value="">全部</option>
                        <option value="0">自营单</option>
                        <option value="1">联盟单</option>
                    </select>
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
                    <label>服务车企</label><input id="belongleasecompany" type="hidden" placeholder="服务车企">
                </div>
                <div class="col-3">
                    <label>订单号</label><input id="orderno" type="text" placeholder="订单号">
                </div>
                <div class="col-3">
                    <label>司机</label><input id="drivername" type="hidden" placeholder="请选择司机">
                </div>
                <div class="col-3">
                    <label class="ordermanage_css_label_1">下单人</label><input id="orderperson" type="hidden" placeholder="请选择下单人">
                </div>
                <div class="col-3">
                    <label>订单状态</label>
                    <select id="paymentstatus">
                        <option value="">全部</option>
                        <option value="0">未支付</option>
                        <option value="1">已支付</option>
                        <option value="9">已关闭</option>
                        <option value="10">已取消</option>
                    </select>
                </div>
                <div class="col-3">
                    <label>交易流水号</label><input id="tradeno" type="text" placeholder="交易流水号" style="width: 68%">
                </div>
                <div class="col-3">
                    <label>取消方</label>
                    <select id="cancelparty">
                        <option value="">全部</option>
                        <option value="1">乘客</option>
                        <option value="0">客服</option>
                        <option value="4">系统</option>
                    </select>
                </div>
                <div class="col-3">
                    <label>取消性质</label>
                    <select id="cancelnature" >
                        <option value="">全部</option>
                        <option value="1">有责</option>
                        <option value="2">免责</option>
                    </select>
                </div>
                <div class="col-3" style="white-space: nowrap">
                    <label style="width: 31%;margin-left: -3px">用车时间</label>
                    <input style="width:19%;min-width: 140px" id="minUseTime" name="minUseTime" readonly="readonly" type="text" placeholder="开始日期" value="" class="searchDate">
                    至
                    <input style="width:19%;min-width: 140px" id="maxUseTime" name="maxUseTime" readonly="readonly" type="text" placeholder="结束日期" value="" class="searchDate">
                </div>
				<div class="col-12" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey_b" onclick="initSearch();">清空</button>
				</div>
			</div>
			<div class="row" style="position:relative;top:20px;">
				<div class="col-6">
					<h4 style="position:relative;top:20px;">已取消订单信息</h4>
				</div>
				<div class="col-6" style="text-align: right;">
					<button href="javascript:void(0);" class="Mbtn blue_q" onclick="exportOrder()" id="exportBtn">导出数据</button>
				</div>
			</div>
			<table id="manualOrderdataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<div class="pop_box" id="exemptionFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3>免责处理</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="exemptionForm" method="get" class="form">
                        <input type="hidden" id="ordernoHide">
		            	<div class="row">
			                <label style="float: left;">免责原因<em class="asterisk"></em></label>
			                <textarea id="exemption" name="exemption" style="width: 60%" rows="2" cols="3" maxlength="100" placeholder="请输入免责原因"></textarea>
			            </div>
                        <div>
                            <font size="1" style="padding-left: 123px;">
                                <span class="font_red">警告：</span>
                                <span class="font_grey">执行操作后，乘客将不再承担取消费用。</span>
                            </font>
                        </div>
	            	</form>
	                <button class="Lbtn red" onclick="save()">确定</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<script type="text/javascript" src="js/personordermanage/cancelorder.js"></script>
	</body>
</html>

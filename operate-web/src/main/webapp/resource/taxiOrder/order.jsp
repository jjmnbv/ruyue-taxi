<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.szyciov.util.SystemConfig"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="好约车">
    <title>出租车下单</title>
    <base href="<%=basePath%>" >
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
	<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css" />
	<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="content/plugins/ztimepicker/ztimepicker.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="content/css/style.css" />
	<link rel="stylesheet" type="text/css" href="content/css/laterchange.css" />
	<link rel="stylesheet" type="text/css" href="css/order/address.css" rel="stylesheet">
	
	<script type="text/javascript" src="content/js/jquery.js"></script>
	<script type="text/javascript" src="content/js/common.js"></script>
	<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
	<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
	<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
	<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
	<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
	<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="content/plugins/ztimepicker/ztimepicker.js"></script>
	<script type="text/javascript" src="content/plugins/cityselect1/cityselect1.js"></script>
	<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
	<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
	<script type="text/javascript" src="content/plugins/select2/app.js"></script>
	<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
	<script type="text/javascript" src="js/basecommon.js"></script>
    <style type="text/css">
    	label.error:not([for=onAddress]):not([for=offAddress]):not([for=usetime]) {
    		padding-left: 23%!important;
    		float: left;
    	}
    	label.error[for=onAddress],label.error[for=offAddress],label.error[for=usetime]{
    		padding-left: 0!important;
    		float: left;
    	}
    	.tabmenu a{text-decoration:none;}
    	.breadcrumb{text-decoration:underline;}
    	.myaddclass{
			position: relative;
			top: -14px;
		}
		.myaddclass .cityinput{
		  	border: 0px;
		    position: absolute;
		    top: 13px;
		}
		.select_box{position:relative;}
		.select_box label.error,label[for="passengerPhone"]{color:#f33333;position:absolute;right:0px;top:-26px;}
		input[type=text] {line-height: 0px;padding:0px;}
		.textarea_box label.error{color:#f33333;position:absolute;right:0px;top:-76px;}
		.pop_y{position: absolute;height: 1035px;width: 653px;bottom: 0px;left: 6px;background: rgba(255,255,255,0.6);z-index: 200;}
		th, td { white-space: nowrap; }
		div.dataTables_wrapper {
			width: $(window).width();
			margin: 0 auto;
		}
		.DTFC_ScrollWrapper{
			margin-top:-20px;
		}
		label:not(.error){width: 100px!important;}
		hr{
			border-top: solid 1px #bdbdbd;
			margin-top: 0px;
		}
    </style>
</head>
<body style="overflow: hidden;">
<div class="crumbs"><a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > 出租车 </div>
<div class="content">
	<form id="formss" class="form"  method="post">
		<input type="hidden" id="orderno">
		<input type="hidden" id="usetype">
		<input type="hidden" id="ordertype">
		<div class="row">
			<div class="col-4">
				<label>服务车企<em class="asterisk"></em></label>
				<select id="company" name="company" style="width:68%;">
					<option value="">全部</option>
					<c:forEach items="${companyList}" var="company">
						<option value="${company.id}">${company.shortName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-4">
				<label>下单人<em class="asterisk"></em></label>
				<input type="hidden" value="" id="userid" name="userid" placeholder="可输入搜索"/>
			</div>
			<div class="col-4 col-yueche">
				<label>乘车人<em class="asterisk"></em></label>
				<input type="hidden" value="" id="passengers" name="passengers" style="width: 55.4%" placeholder="可输入搜索"/>
				<button class="Mbtn blue" type="button" onclick="showFavorite();">常用</button>
			</div>
			<div class="col-4 col-yueche">
				<label>乘车人电话<em class="asterisk"></em></label>
				<input type="hidden" value="" id="passengerphone" name="passengerphone" style="width: 55.4%" placeholder="可输入搜索"/>
				<button class="Mbtn blue" type="button" onclick="showFavorite();">常用</button>
			</div>
		</div>
		<div class="row">
			<div class="col-4 col-yueche">
				<label style="float: left;">用车时间<em class="asterisk"></em></label>
				<div class="col-6">
					<div class="ztimepicker" style="padding-left: 10px;">
						<input type="text" readonly value="" class="ztimepicker_input" name="usetime" id="usetime" style="width: 91%;"/>
						<div class="ztimebox">
							<div class="znow">现在用车</div>
							<div class="ztimebox_s">
								<ul class="zday">
								</ul>
								<ul class="zhour">
								</ul>
								<ul class="zmin">
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-4">
				<label style="padding-right: 28.8px;">调度费</label>
				<input type="text" placeholder="可输入100元以下金额" style="width: 55%;" id="schedulefee" name="schedulefee" oninput="onlyInputNum(this);" onblur="getCost();">元
			</div>
			<div class="col-4">
				<label style="padding-right: 28.8px;">打表来接</label>
				<select style="width: 55%;" id="meterrange" name="meterrange" onchange="getCost();">
					<option value="0">不愿打表来接</option>
					<option value="3">3公里内打表来接</option>
					<option value="5">5公里内打表来接</option>
					<option value="10">10公里内愿打表来接</option>
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-12 validatecustomstyle" style="position:relative;">
				<div style="width: 7%;float: left;">
					<label>上车地址<em class="asterisk"></em></label>
				</div>
				<div style="width: 14%;float: left;padding-left: 4px;" id="oncityDiv">
					<input type="text" placeholder="选择城市" id="oncityname" style="width: 100%;" readonly="readonly">
				</div>
				<div style="width: 72%;float: left;">
					<input type="hidden" id="onCity" name="oncity"/>
					<input type="hidden" id="onAddressMarkid">
					<input type="text" placeholder="可输入搜索" style="width: 99%;margin-left: 2px;" name="onaddress" id="onAddress" class="onAddress" maxLength="200"/>
					<div class="dizhibox" style="position:absolute;top:40px;left:21.6%;z-index:1000;border:1px solid #ededed;background:#fff;width:70%;">
						<div class="stab">
							<div id = "searchResult"class="shen_on">搜索结果</div>
							<div>常用地址</div>
						</div>
						<div class="stabox bresult tangram-suggestion">&nbsp 请输入搜索关键字</div>
						<ul class="stabox bhide">
						</ul>
					</div>
				</div>
				<div style="width: 5%;float: left;">
					<button class="Mbtn blue onMap" type="button" onclick="onMap();">地图</button>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-12 validatecustomstyle" style="position:relative;">
				<div style="width: 7%;float: left;">
					<label>下车地址<em class="asterisk"></em></label>
				</div>
				<div style="width: 14%;float: left;padding-left: 4px;" id="offcityDiv">
					<input type="text" placeholder="选择城市" id="offcityname" style="width: 100%;" readonly="readonly">
				</div>
				<div style="width: 72%;float: left;">
					<input type="hidden" id="offCity" name="offcity"/>
					<input type="hidden" id="offAddressMarkid">
					<input type="text" placeholder="可输入搜索" style="width: 99%;margin-left: 2px;" name="offaddress" id="offAddress" class="onAddress" maxLength="200"/>
					<div class="dizhibox" style="position:absolute;top:40px;left:21.6%;z-index:1000;border:1px solid #ededed;background:#fff;width:70%;">
						<div class="stab">
							<div id = "searchResult1"class="shen_on">搜索结果</div>
							<div>常用地址</div>
						</div>
						<div class="stabox bresult tangram-suggestion">&nbsp 请输入搜索关键字</div>
						<ul class="stabox bhide">
						</ul>
					</div>
				</div>
				<div style="width: 5%;float: left;">
					<button class="Mbtn blue offMap" type="button" onclick="offMap();">地图</button>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-12">
				<label style="padding-right: 28.8px;" class="col-12-label">行程备注</label>
				<textarea rows="2" style="width:91%;" placeholder="填写行程备注" name="tripremark" id="tripremark" maxlength="22"></textarea>
			</div>
		</div>
		<div class="row" style="padding-left: 12px;">
			<div class="col-12">
				<h2>支付方式</h2>
			</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-3" id="paymethod">
				<input type="radio" name="paymentmethod" value="1" checked/>线下付现
				<input type="radio" name="paymentmethod" value="0"/>在线支付
			</div>
		</div>
        <br/>
        <div class="row"><div class="col-12"><h2>指派司机</h2></div></div>
        <hr />
        <div class="row">
            <div class="col-3" style="padding: 18.5px 10px">
                <input type="radio" name="driverModeType" value="0" checked/>不指派
                <input type="radio" name="driverModeType" value="1" />人工指派
            </div>
            <div class="col-6 hidden" id="manualSelectDriver">
                <div class="col-8">
                    <label class="label-late">指派司机<em class="asterisk"></em></label>
                    <input type="hidden" value="" id="manualSelectDriverInput" name="manualSelectDriverInput"/>
                </div>
                <div class="col-4">
                    <button class="Mbtn blue" type="button">选择</button>
                </div>
            </div>
        </div>
		<br/><br/>
		<div class="row" style="padding-left: 12px;" id="estimateCostDiv">
        	<div class="col-1" style="margin-right:2%;width:auto;"><h2>预估费用</h2></div>
        	<div class="col-3"><h2>¥<span id="estimatedcost">0.0元</span></h2></div>
        	<div class="col-4" style="width:auto;">
        		<h2>
        		<span id="premiumrateSpan" style="display:none;">溢价<span id="premiumrate">1.0倍</span></span> 
        		<span id="couponpriceSpan" style="display:none;">券已抵扣<span id="couponprice">0元</span>
        		</h2>
        	</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-12"  align="center"><button class="Lbtn red callCar" style="width:30%;" type="button" onclick="createOrder();">开始叫车</button></div>
		</div>
	</form>
</div>
<%@include file="window.jsp"%>
<script type="text/javascript" src="js/taxiOrder/order.js"></script>
<script type="text/javascript" src="js/taxiOrder/baidu.js"></script>
</body>
</html>
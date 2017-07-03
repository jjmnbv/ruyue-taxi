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
		 <c:if test="${empty opAccountrules.id}">
			<title>新增网约车计费规则</title>
		</c:if>
		<c:if test="${not empty opAccountrules.id}">
			<title>修改网约车计费规则</title>
		</c:if>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/css/laterchange.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
		<link rel="stylesheet" type="text/css" href="css/opAccountRules/ztimepicker1.css">
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="content/plugins/cityselect1/cityselect1.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
	</head>
	
	<style>
		.breadcrumb{text-decoration:underline;}
		.form label{float:left;line-height: 30px;height:30px;}
		.form select,.form input[type=text]{width:70%;float:left;}
/* 		.form #inp_box1 label.error{padding-left: 0;margin-left:0;width:175%;} */
		.form-account .col-4 .unit{position:relative;top:4px;}
		.city_container{
			min-width:300px;
			height:210px;
			position:absolute;
			z-index: 9999;
			background: white;
			border:1px solid #e1e1e1;
			font-size: 14px;
			color:#333;
			display: none;
			margin-left: 135px;
			margin-top: 30px;
		}
		#nightstarttime1 label.error {margin-left: -33%!important;}
		#nightstarttime2 label.error {margin-left: 5%!important;}
    </style>

	<body style="overflow:hidden;">
	    <input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > <a href="OpAccountrules/Index" class="breadcrumb">网约车计费规则</a> > 
			 <c:if test="${empty opAccountrules.id}">
				新增规则
			</c:if>
			<c:if test="${not empty opAccountrules.id}">
				修改规则
			</c:if>
			<button class="SSbtn blue back" onclick="window.history.go(-1);" style="margin-top: -1px;">返回</button>
		</div>
		
		<div class="content">
			<input name="cityValue" id="hideCityValue" type="hidden" value="${opAccountrules.city}">
		    <input name="cityName" id="hideCityName" type="hidden" value="${opAccountrules.cityName}">
		    <input name="rulestype" id="hideRulestype" type="hidden" value="${opAccountrules.rulestype}">
		    <input name="vehiclemodelsid" id="hideVehiclemodelsid" type="hidden" value="${opAccountrules.vehiclemodelsid}">
		    <input name="timetype" id="hideTimetype" type="hidden" value="${opAccountrules.timetype}">
		  	<form id="formss" class="form-account">
			    <input name="id" id="id" value="${opAccountrules.id}" type="hidden">
				<div class="row form" style="padding-top: 30px;">
					<div class="col-4">
						<label class="account-items">城市名称<em class="asterisk"></em></label>
						<input type="hidden" id="city" name="city" value="${opAccountrules.city}">
						<div id="inp_box1" style="width: 100%">
							<c:if test="${empty opAccountrules.id}">
								<input type="text" id="cityname" readonly="readonly" name="cityname" style="width: 60%" value="${opAccountrules.cityName}"/>
							</c:if>
							<c:if test="${not empty opAccountrules.id}">
								<input type="text" id="cityname" readonly="readonly" name="cityname" style="width: 60%" value="${opAccountrules.cityName}" disabled/>
							</c:if>
						</div>
					</div>
					<div class="col-4">
						<label class="account-items">规则类型<em class="asterisk"></em></label>
						<select id="rulestype" name="rulestype">
							<option value="" selected="selected">请选择</option>
							<c:forEach var="rulesType" items="${ruleTypeList}">
								<option value="${rulesType.value}">${rulesType.text}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="account-items">服务车型<em class="asterisk"></em></label>
						<select id="vehiclemodelsid" name="vehiclemodelsid">
							<option value="" selected="selected">请选择</option>
							<c:forEach var="vehiclemodels" items="${vehiclemodelsList}">
								<option value="${vehiclemodels.id}">${vehiclemodels.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row form">
					<div class="col-4">
						<label class="account-items">起步价<em class="asterisk"></em></label>
						<input name="startprice" id="startprice" type="text" class="accoune-price" value="${opAccountrules.startprice}" placeholder="起步价" style="width: 60%;" maxlength="6">&nbsp;&nbsp;元
					</div>
					<div class="col-4">
						<label class="account-items">里程价<em class="asterisk"></em></label>
						<input name="rangeprice" id="rangeprice" type="text" class="accoune-price" value="${opAccountrules.rangeprice}" placeholder="里程价格" style="width: 44%;" maxlength="5">&nbsp;&nbsp;元/公里
					</div>
					<div class="col-4">
						<label class="account-items">时间价<em class="asterisk"></em></label>
						<input name="timeprice" id="timeprice" type="text" class="accoune-price" value="${opAccountrules.timeprice}" placeholder="时间价格" style="width: 48%;" maxlength="4">&nbsp;&nbsp;元/分钟
					</div>
				</div>
				<div class="row form">
					<div class="col-4">
						<label class="account-items">时间类型<em class="asterisk"></em></label>
						<select id="timetype" name="timetype" onchange= "changeTimeType()" style="width: 60%">
							<option value="" selected="selected">请选择</option>
							<option value="0">总用时</option>
							<option value="1">低速用时</option>
						</select>
					</div>
					<div id="perhourDiv" style="display: ${opAccountrules.timetype == '0'?'none':'block'};" class="col-4">
						<label class="account-items">时速<em class="asterisk"></em></label>
						<input name="perhour" id="perhour" type="text" class="accoune-price" value="${opAccountrules.perhour}" placeholder="时速"  style="width: 42%" maxlength="5">&nbsp;&nbsp; 公里/小时
					</div>
					<div class="col-4">
						<label class="account-items">回空里程<em class="asterisk"></em></label>
						<input name="deadheadmileage" id="deadheadmileage" type="text" class="accoune-price" value="${opAccountrules.deadheadmileage}" placeholder="" style="width: 48%;" maxlength="4">&nbsp;&nbsp;公里
					</div>
				</div>
				<div class="row form">
					<div class="col-4">
						<label class="account-items">回空费价<em class="asterisk"></em></label>
						<input name="deadheadprice" id="deadheadprice" type="text" class="accoune-price" value="${opAccountrules.deadheadprice}" placeholder="" style="width: 48%;" maxlength="4">&nbsp;&nbsp;元/公里
					</div>
					<div class="col-4">
						<label class="account-items">夜间征收时段<em class="asterisk"></em></label>
						<div class="col-1" id="nightstarttime1">
		                    <div class="ztimepicker" >
								<input type="text" readonly value="${opAccountrules.nightstarttime}" class="ztimepicker_input" name="nightstarttime" id="nightstarttime" style="width:45%;margin-left:-3%;"/><span style="margin-left: 2%;">-</span>
								<div class="ztimebox" style="margin-left:-3%;">
									<div class="ztimebox_s">
										<ul class="zhour">
										</ul>
										<ul class="zmin">
										</ul>
									</div>
							    </div>
							</div>
						</div>
						<div class="col-1" id="nightstarttime2">
							<div class="ztimepicker">
								<input type="text" readonly value="${opAccountrules.nightendtime}" class="ztimepicker_input" name="nightendtime" id="nightendtime" style="width:45%;margin-left:35%;"/>
								<div class="ztimebox" style="margin-left:35%;">
									<div class="ztimebox_s">
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
						<label class="account-items">夜间费价<em class="asterisk"></em></label>
						<input name="nighteprice" id="nighteprice" type="text" class="accoune-price" value="${opAccountrules.nighteprice}" placeholder="" style="width: 48%;" maxlength="4">&nbsp;&nbsp;元/公里
					</div>
				</div>
		  	</form>
		  <div class="row form">	
				<div class="col-12" style="text-align: right;">
                     <button class="Mbtn orange" style="margin-right: 20px;" onclick="save()">保存</button>
                     <button class="Mbtn grey" onclick="window.history.go(-1);">取消</button>
                </div>				
		  </div>
		</div>
		<script type="text/javascript" src="js/opAccountRules/editAccountRules.js"></script>
		<script type="text/javascript" src="js/opAccountRules/ztimepicker1.js"></script>
	</body>
</html>

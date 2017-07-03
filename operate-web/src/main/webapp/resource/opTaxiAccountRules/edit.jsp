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
		<title>修改规则</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/css/laterchange.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
		
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
/* 		.form #inp_box1 label.error{padding-left: 0;} */
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
    </style>

	<body>
	    <input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > <a href="OpTaxiAccountRules/Index" class="breadcrumb">计费规则</a> > 维护规则
			<button class="SSbtn blue back" onclick="window.history.go(-1);" style="margin-top: -1px;">返回</button>
		</div>
		
		<div class="content">
		  	<form id="formss" class="form-account">
			    <input name="id" id="id" value="${rule.id}" type="hidden">
			    <input name="rulesstate" id="rulesstate" value="${rule.rulesstate}" type="hidden">
				<div class="row form" style="padding-top: 30px;">
					<div class="col-4">
						<label class="account-items">城市名称<em class="asterisk"></em></label>
						<input type="hidden" id="city" name="city" value="${rule.city}">
						<input type="hidden" id="markid" name="markid" value="">
						<div id="inp_box1">
							<c:if test="${empty rule.id}">
								<input type="text" id="cityname" readonly="readonly" name="cityname" style="width: 48%" value="${rule.cityname}" />
							</c:if>
							<c:if test="${not empty rule.id}">
								<input type="text" id="cityname" readonly="readonly" name="cityname" style="width: 68%" value="${rule.cityname}" disabled/>
							</c:if>
						</div>
					</div>
					<div class="col-4">
						<label class="account-items">起租价<em class="asterisk"></em></label>
						<input name="startprice" id="startprice" type="text" class="accoune-price" value="${rule.startprice}" placeholder="起租价" style="width: 44%;" maxlength="6">&nbsp;&nbsp;元
					</div>
					<div class="col-4">
						<label class="account-items">起租里程<em class="asterisk"></em></label>
						<input name="startrange" id="startrange" type="text" class="accoune-price" value="${rule.startrange}" placeholder="起租里程" style="width: 48%;" maxlength="6">&nbsp;&nbsp;公里
					</div>
				</div>
				<div class="row form">
					<div class="col-4">
						<label class="account-items">续租价<em class="asterisk"></em></label>
						<input name="renewalprice" id="renewalprice" type="text" class="accoune-price" value="${rule.renewalprice}" placeholder="续租价" style="width: 48%;" maxlength="6">&nbsp;&nbsp;元/公里
					</div>
					<div class="col-4">
						<label class="account-items">附加费<em class="asterisk"></em></label>
						<input name="surcharge" id="surcharge" type="text" class="accoune-price" value="${rule.surcharge}" placeholder="附加费" style="width: 44%;" maxlength="6">&nbsp;&nbsp;元
					</div>
					<div class="col-4">
						<label class="account-items">标准里程<em class="asterisk"></em></label>
						<input name="standardrange" id="standardrange" type="text" class="accoune-price" value="${rule.standardrange}" placeholder="标准里程" style="width: 48%;" maxlength="6">&nbsp;&nbsp;公里
					</div>
				</div>
				<div class="row form">
					<div class="col-4">
						<label class="account-items">空驶费率<em class="asterisk"></em></label>
						<input name="emptytravelrate" id="emptytravelrate" type="text" class="accoune-price" value="${rule.emptytravelrate}" placeholder="空驶费率" style="width: 48%;" maxlength="6">&nbsp;&nbsp;%
					</div>
				</div>
		  	</form>
			<br/><br/><br/><br/>
			  <div class="row form">	
					<div class="col-12" style="text-align: left;">
					注:
					<p>标准里程：标准里程内，不征收空驶费，超过标准里程部分，须征收空驶费。</p>
					<p>空驶费率：针对超出标准里程部分的计费单价，空驶费率相对于续租价而言。</p>
	                </div>				
			  </div>
			  <div class="row form">	
					<div class="col-12" style="text-align: right;">
	                     <button class="Mbtn orange" style="margin-right: 20px;" onclick="save()">保存</button>
	                     <button class="Mbtn grey" onclick="window.history.go(-1);">取消</button>
	                </div>				
			  </div>
			</div>
		<script type="text/javascript" src="js/opTaxiAccountRules/editAccountRules.js"></script>
	</body>
</html>

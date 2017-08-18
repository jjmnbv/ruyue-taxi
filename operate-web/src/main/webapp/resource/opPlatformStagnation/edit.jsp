<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
	<c:if test="${empty opPlatformServiceorgan.id}">
		<title>新增平台驻点信息</title>
	</c:if>
	<c:if test="${not empty opPlatformServiceorgan.id}">
		<title>修改平台驻点信息</title>
	</c:if>
	<base href="<%=basePath%>" >
	<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="content/css/style.css" />
	<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
	<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
	<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
	<link rel="stylesheet" type="text/css" href="content/plugins/cityselect4/style.css" />
	<script type="text/javascript" src="content/js/jquery.js"></script>
	<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
	<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
	<script type="text/javascript"  src="content/js/common.js"></script>
	<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
	<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
	<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
	<script type="text/javascript" src="content/plugins/letterselect/letterselect.js"></script>
	<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
	<script src="content/plugins/cityselect4/Popt.js"></script>
	<script src="content/plugins/cityselect4/cityJson.js"></script>
	<script src="content/plugins/cityselect4/citySet.js"></script>

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

	.form #inp_box1 label.error{padding-left: 0;}

	@media screen and (min-width: 790px) and (max-width: 1310px) {


		#inp_box1{
			width:32% !important;
		}
		#inp_box1 input{
			width:100% !important;
		}
		#inp_box1 label[for=cityName]{
			width:158%;
		}
	}
	#nightstarttime1 label.error {margin-left: -33%!important;}
	#nightstarttime2 label.error {margin-left: 5%!important;}


</style>

<body style="overflow:hidden;">
<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
<div class="crumbs">
	<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > <a href="OpPlatformStagnation/Index" class="breadcrumb">平台驻点信息</a> >
	<c:if test="${empty opPlatformStagnation.id}">
		新增平台驻点信息
	</c:if>
	<c:if test="${not empty opPlatformStagnation.id}">
		修改平台驻点信息
	</c:if>
	<button class="SSbtn blue back" onclick="window.history.go(-1);" style="margin-top: -1px;">返回</button>
</div>

<div class="content">
	<form id="formss" class="form-account">
		<input name="id" id="id" value="${opPlatformStagnation.id}" type="hidden">
		<div class="row form" style="padding-top: 30px;">
			<div class="col-4">
				<label class="account-items">驻点城市<em class="asterisk"></em></label>
				<div id="inp_box1" style="width: 68%">
					<input type="hidden" id="cityId"  value="">
					<input name="city" id="city" type="text"  value="${opPlatformStagnation.city}" onfocus="getSelectCity();" placeholder="请选择" style="width: 60%;"  readonly>
				</div>
			</div>
			<div class="col-4">
				<label class="account-items">负责人姓名<em class="asterisk"></em></label>
				<input name="responsible" id="responsible" type="text"  value="${opPlatformStagnation.responsible}" placeholder="请输入负责人姓名" style="width: 60%;">
			</div>
			<div class="col-4">
				<label class="account-items">负责人电话<em class="asterisk"></em></label>
				<input name="responsibleway" id="responsibleway" type="text"  value="${opPlatformStagnation.responsibleway}" placeholder="请输入负责人电话" style="width: 60%;">
			</div>
		</div>
		<div class="row form" style="padding-top: 30px;">
			<div class="col-4">
				<label class="account-items">邮政编码<em class="asterisk"></em></label>
				<input name="postcode" id="postcode" type="text"  value="${opPlatformStagnation.postcode}" placeholder="请输入邮政编码" style="width: 60%;" maxlength="6">
			</div>
			<div class="col-8">
				<label class="account-items"  style="width: auto;">通信地址<em class="asterisk"></em></label>
				<input name="contactaddresscity" id="contactaddresscity" type="text"  value="${opPlatformStagnation.contactaddresscity}" placeholder="请选择" style="width: 30%;margin-right: 10px;" >
				<input name="contactaddress" id="contactaddress" type="text"  value="${opPlatformStagnation.contactaddress}" placeholder="请输入详细地址" style="width: 40%;" >
			</div>

		</div>

		<div class="row form" style="padding-top: 30px;">
			<div class="col-4">
				<label class="account-items">母公司名称<em class="asterisk"></em></label>
				<input name="parentcompany" id="parentcompany" type="text"  value="${opPlatformStagnation.parentcompany}" placeholder="请输入母公司名称" style="width: 60%;">
			</div>
			<div class="col-8">
				<label class="account-items"  style="width: auto;">母公司通信地址<em class="asterisk"></em></label>
				<input name="parentadcity" id="parentadcity" type="text"  value="${opPlatformStagnation.parentad}" placeholder="请选择" style="width: 30%;margin-right: 10px;" >
				<input name="parentad" id="parentad" type="text"  value="${opPlatformStagnation.parentad}" placeholder="请输入详细地址" style="width: 40%;" >
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
<div class="kongjian_list" id="kongjian_list">
	<div class="box">
		<div class="title">
			<span>ABCDE</span>
			<span>FGHIJ</span>
			<span>KLMNO</span>
			<span>PQRST</span>
			<span>UVWXYZ</span>
		</div>
		<div class="con">

		</div>
		<div class="con">

		</div>
		<div class="con">

		</div>
		<div class="con">

		</div>
		<div class="con">

		</div>
	</div>
</div>
<script type="text/javascript" src="js/opPlatformStagnation/edit.js"></script>

</body>
</html>

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
		<title>新增平台服务机构</title>
	</c:if>
	<c:if test="${not empty opPlatformServiceorgan.id}">
		<title>修改平台服务机构</title>
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
	<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > <a href="OpPlatformServiceorgan/Index" class="breadcrumb">平台服务机构</a> >
	<c:if test="${empty opPlatformServiceorgan.id}">
		新增服务机构
	</c:if>
	<c:if test="${not empty opPlatformServiceorgan.id}">
		修改服务机构
	</c:if>
	<button class="SSbtn blue back" onclick="window.history.go(-1);" style="margin-top: -1px;">返回</button>
</div>

<div class="content">
	<form id="formss" class="form-account">
		<input name="id" id="id" value="${opPlatformServiceorgan.id}" type="hidden">
		<div class="row form" style="padding-top: 30px;">
			<div class="col-4">
				<label class="account-items">服务机构名称<em class="asterisk"></em></label>
				<input name="servicename" id="servicename" type="text" value="${opPlatformServiceorgan.servicename}" placeholder="请输入服务机构名称" style="width: 60%;" >
			</div>
			<div class="col-4">
				<label class="account-items">服务机构代码<em class="asterisk"></em></label>
				<input name="serviceno" id="serviceno" type="text"  value="${opPlatformServiceorgan.serviceno}" placeholder="请输入服务机构代码" style="width: 60%;" >
			</div>
			<div class="col-4">
				<label class="account-items">机构负责人<em class="asterisk"></em></label>
				<input name="responsiblename" id="responsiblename" type="text"  value="${opPlatformServiceorgan.responsiblename}" placeholder="请输入机构负责人" style="width: 60%;" >
			</div>
		</div>
		<div class="row form" style="padding-top: 30px;">
			<div class="col-4">
				<label class="account-items">负责人电话<em class="asterisk"></em></label>
				<input name="responsiblephone" id="responsiblephone" type="text"  value="${opPlatformServiceorgan.responsiblephone}" placeholder="请输入负责人电话" style="width: 60%;">
			</div>
			<div class="col-4">
				<label class="account-items">机构管理人<em class="asterisk"></em></label>
				<input name="managername" id="managername" type="text" value="${opPlatformServiceorgan.managername}" placeholder="请输入机构管理人" style="width: 60%;" >
			</div>
			<div class="col-4">
				<label class="account-items">管理人电话<em class="asterisk"></em></label>
				<input name="managerphone" id="managerphone" type="text"  value="${opPlatformServiceorgan.managerphone}" placeholder="请输入管理人电话" style="width: 60%;" >
			</div>
		</div>
		<div class="row form" style="padding-top: 30px;">
			<div class="col-4">
				<label class="account-items">紧急联系电话<em class="asterisk"></em></label>
				<input name="contactphone" id="contactphone" type="text" value="${opPlatformServiceorgan.contactphone}" placeholder="请输入紧急联系电话" style="width: 60%;" >
			</div>
			<div class="col-4">
				<label class="account-items">机构设立日<em class="asterisk"></em></label>
				<input name="createdate" id="createdate" type="text"  value="${fn:substring(opPlatformServiceorgan.createdate, 0, 10)}" placeholder="请选择" style="width: 60%;">
			</div>
			<div class="col-4">
				<label class="account-items">机构所在地<em class="asterisk"></em></label>
				<input type="hidden" id="address" name="address" value="${opPlatformServiceorgan.address}">
				<div id="inp_box1" style="width: 68%">
					<input name="addressName" id="addressName" type="text"  value="${opPlatformServiceorgan.addressName}" onfocus="getSelectCity();" placeholder="请选择" style="width: 60%;"  readonly>
				</div>
			</div>
		</div>
		<div class="row form" style="padding-top: 30px;">
			<div class="col-8">
				<label class="account-items"  style="width: auto;">机构详细地址<em class="asterisk"></em></label>
				<input name="detailaddresscity" id="detailaddresscity" type="text"  value="${opPlatformServiceorgan.detailaddresscity}" placeholder="请选择" style="width: 30%;margin-right: 10px;" >
				<input name="detailaddress" id="detailaddress" type="text"  value="${opPlatformServiceorgan.detailaddress}" placeholder="请输入机构详细地址" style="width: 40%;" >
			</div>

		</div>
		<div class="row form" style="padding-top: 30px;">
			<div class="col-8">
				<label class="account-items" style="width: auto;">文书送达地址<em class="asterisk"></em></label>
				<input name="mailaddresscity" id="mailaddresscity" type="text"  value="${opPlatformServiceorgan.mailaddresscity}" placeholder="请选择" style="width: 30%;margin-right: 10px;" >
				<input name="mailaddress" id="mailaddress" type="text"  value="${opPlatformServiceorgan.mailaddress}" placeholder="请输入文书送达详细地址" style="width: 40%;" >
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
<script type="text/javascript" src="js/opPlatformServiceorgan/edit.js"></script>

</body>
</html>

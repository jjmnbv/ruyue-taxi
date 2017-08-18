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
		<title>新增经营许可信息</title>
	</c:if>
	<c:if test="${not empty opPlatformServiceorgan.id}">
		<title>修改经营许可信息</title>
	</c:if>
	<base href="<%=basePath%>" >
	<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="content/css/style.css" />
	<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
	<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
	<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
	<link rel="stylesheet" type="text/css" href="content/plugins/cityselect4/style.css" />
	<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
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
	<script type="text/javascript"  src="content/plugins/cityselect1/cityselect1.js"></script>
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

	/* 添加城市样式 */
	#pubCityaddr{position:absolute;display:inline-block;}
	#pubCityaddr>.addcitybtn{background: #997C26;padding: 2px 10px;color:#fff;margin-left: 13px;}
	#pubCityaddr .kongjian_list{top:26px!important;}
	.added{display:inline-block;padding:2px 4px;}
	.added .ico_x_a{float:right;margin-left: -6px;}
	.addcbox{padding:6px 10px;margin:0px 37px 10px 37px;border:1px solid #ccc;min-height:100px;line-height:30px;}

</style>

<body style="overflow:hidden;">
<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
<div class="crumbs">
	<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > <a href="OpPlatformBusinesslicense/Index" class="breadcrumb">经营许可信息</a> >
	<c:if test="${empty opPlatformBusinesslicenseVo.id}">
		新增经营许可信息
	</c:if>
	<c:if test="${not empty opPlatformBusinesslicenseVo.id}">
		修改经营许可信息
	</c:if>
	<button class="SSbtn blue back" onclick="window.history.go(-1);" style="margin-top: -1px;">返回</button>
</div>

<div class="content">
	<form id="formss" class="form-account">
		<input name="id" id="id" value="${opPlatformBusinesslicenseVo.id}" type="hidden">
		<div class="row form" style="padding-top: 30px;">
			<div class="col-4">
				<label class="account-items">经营许可证号<em class="asterisk"></em></label>
				<input name="certificate" id="certificate" type="text"  value="${opPlatformBusinesslicenseVo.certificate}" placeholder="请输入经营许可证号" style="width: 60%;">
			</div>
			<div class="col-4">
				<label class="account-items">经营许可地<em class="asterisk"></em></label>
				<div id="inp_box1" style="width: 68%">
					<input type="hidden" id="address" name="address"  value="${opPlatformBusinesslicenseVo.address}">
					<input name="addressName" id="addressName" type="text"  value="${opPlatformBusinesslicenseVo.addressName}" onfocus="getSelectCity();" placeholder="请选择" style="width: 60%;"  readonly>
				</div>
			</div>
			<div class="col-4">
				<label class="account-items">发证机构<em class="asterisk"></em></label>
				<input name="organization" id="organization" type="text"  value="${opPlatformBusinesslicenseVo.organization}" placeholder="请输入发证机构" style="width: 60%;">
			</div>

		</div>

		<div class="row form" style="padding-top: 30px;">
			<div class="col-4">
				<label class="account-items">有效期限<em class="asterisk"></em></label>

				<input name="startdate" id="startdate" type="text"  value="${fn:substring(opPlatformBusinesslicenseVo.startdate, 0, 10)}" placeholder="请选择开始时间" style="width: 25%;margin-right: 20px;">
				<input name="stopdate" id="stopdate" type="text"  value="${fn:substring(opPlatformBusinesslicenseVo.stopdate, 0, 10)}" placeholder="请选择结束时间" style="width: 25%;">
			</div>
			<div class="col-4">
				<label class="account-items">初次发证日期<em class="asterisk"></em></label>
				<input name="certifydate" id="certifydate" type="text"  value="${fn:substring(opPlatformBusinesslicenseVo.certifydate, 0, 10)}" placeholder="请选择" style="width: 60%;">
			</div>
			<div class="col-4">
				<label class="account-items">证件状态<em class="asterisk"></em></label>
				<select name="state" id="state" style="width: 60%;" >
					<option value="1" <c:if test="${opPlatformBusinesslicenseVo.state == 1}">selected</c:if> >有效</option>
					<option value="0" <c:if test="${opPlatformBusinesslicenseVo.state == 0}">selected</c:if>>无效</option>
				</select>
			</div>
		</div>
		<div class="row form" style="padding-top: 30px;">
			<div id ="scopeDiv" class="col-4" style="margin-left: 50px;">
				<div class="row">
					<div class="col-8" style="padding-left:4.4%;">
						<label style="text-align: left;">经营区域<em class="asterisk"></em></label>
						<div id="pubCityaddr">
							<input type="button" class="addcitybtn" value="+添加城市" style="margin-left: 5px;">
							<!-- <div class="addcitybtn">+添加城市</div> -->
						</div>
					</div>

				</div>

				<input id="businessScope" name="businessScope" type="hidden" value=""/>
				<div id ="addcboxId" class="addcbox" style="margin:0px 30px 10px 108px;border:1px solid #ccc;min-height:100px;line-height:30px;">
					<c:forEach var="scope" items="${opPlatformBusinesslicenseVo.scopes}">
						<div class="added" id="${scope.operationarea}">${scope.operationarea}<em class="ico_x_a"></em></div>
					</c:forEach>

				</div>
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
<script type="text/javascript" src="js/opPlatformBusinesslicense/edit.js"></script>

</body>
</html>

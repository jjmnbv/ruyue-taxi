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
		<title>信息设置</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/css/laterchange.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/plugins/cityselect1/cityselect1.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
	</head>
	
	<style type="text/css">
	    .breadcrumb{text-decoration:underline;}
	    .col-3 {width: 27%;text-align: right;}
	    label.error {float: left;margin-left: 24.5%;line-height: 100%;height: auto;}
	    label.error[for='cityname']{float: left;margin-left: -8%;line-height: 100%;height: auto;}
	    .city_container{margin-left:-10px}
	</style>

	<body style="overflow:hidden;">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a>> 平台信息设置</div>
		<div class="content">
		    <form id="editForm" class="form-information">
		    <input name="id" id="id" value="${opPlatformInfo.id}" type="hidden">
		    <input type="hidden" name="citymarkid" id="citymarkid" value="${sendrules.citymarkid}">
			<div class="row" style="padding-top: 30px;">
			<div class="col-12" style="border-bottom: 1px solid #dbdbdb;"><h4>平台公司信息</h4></div>
			   <div class="col-6">
			    <div class="col-12">
				<label class="col-3">公司名称<em class="asterisk"></em></label> 
				<input class="col-6" type="text" id="companyname" name="companyname" value="${opPlatformInfo.companyname}"  placeholder="请填写公司名称" maxlength="20"/>
				</div>
			   </div>
			   <div class="col-6">
			    <div class="col-12">
				<label class="col-3">公司简称<em class="asterisk"></em></label> 
				<input class="col-6" type="text" id="companyshortname" name="companyshortname" value="${opPlatformInfo.companyshortname}" placeholder="请填写公司简称" maxlength="6"/>
				</div>
			   </div>
			</div>
			<div class="row" >
			<div class="col-6">
			<div>
			 <div class="col-12">
				<label class="col-3">所属城市<em class="asterisk"></em></label>
				<div class="col-6" style="float:left"> 
				<div class="input_box2">
					<input style="width: 106%;margin-left: -10px;" type="text" placeholder="请选择城市" id="cityname" name="cityname" class="tally" readonly="readonly" value="${opPlatformInfo.city}"/>
				</div>
				</div>
			 </div>
			</div>
			</div>
			</div>
			<div class="row">
			<div class="col-12" style="border-bottom: 1px solid #dbdbdb;"><h4>平台客服电话</h4></div>
				<div class="col-6">
					<div class="col-12">
						<label class="col-3">电话号码<em class="asterisk"></em></label> 
						<input class="col-6" type="text" id=servcieTel name="servcieTel" value="${opPlatformInfo.servcieTel}" placeholder="请填写客服电话" maxlength="13"/>
					</div>
				</div>
			</div>
			</form>
			<div style="text-align: center;">
				<button class="Mbtn red_q" style="margin-right: 20px;" onclick="save()">保存</button>
                <button class="Mbtn grey_w" href="javascript:void(0);" onclick="homeHref()">返回</button>
			</div>
		</div>
		
		<script type="text/javascript" src="js/customerphone/index.js"></script>
	</body>
</html>
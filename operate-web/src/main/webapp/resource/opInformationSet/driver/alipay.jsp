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
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="content/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="content/plugins/toastr/toastr.css" />
<link rel="stylesheet" type="text/css"
	href="content/plugins/select2/select2.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="content/plugins/select2/select2_metro.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>

<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/js/bootstrap.min.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript"
	src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
<script type="text/javascript"
	src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
<script type="text/javascript"
	src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
<script type="text/javascript"
	src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
<script type="text/javascript"
	src="content/plugins/select2/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="content/plugins/select2/app.js"></script>
<script src="content/plugins/fileupload/jquery.ui.widget.js"></script>
<script src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
<script src="content/plugins/fileupload/jquery.fileupload.js"></script>
	<style type="text/css">
		/* 目前样式select在form中无法自适应 */
		.form label{
			float:left;
		}
		.form select,.form input[type=text]{
			width:70%;
			float:left;
		}
		.form label{
			line-height: 30px;
			height:30px;
		}
		.form .select2-container{
			width:70%;
			float:left;
			margin-top: -5px;
		}
		.tip_box_c{
		    max-height: 726px;
		}
		.breadcrumb{text-decoration:underline;}
		.tip_box_b label.error {margin-left: 0!important;}
		.col-5{
		    margin-left: 24%;
		}
	</style>
</head>
<body>
	<div class="crumbs">
		<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="OpInformationSet/driver/Index">司机收款账户管理</a> > 支付宝收款账户
	</div>
	<div class="content">
		<div class="row form" style="padding-top: 30px;">
		</div> 
		<form id="editAlipayForm" class="form" method="post">
			<input type="hidden" id="id" name="id" value="${opPlatformInfo.id}"/>
			<div style="height: 30px;width: 100%;">
				<font size="4px" style="margin-left: 10px;">账户信息配置&nbsp;
				</font>
				<hr style="border:#000 dotted 1px;margin-top:auto;" />
			</div>
			<div class="row form" style="padding-top: 20px;">
				
			</div>
			<div class="row form" style="padding-top: 20px;">
				
			</div>
			<div class="row">
				<div class="col-10">
					<label>商家支付宝账户<em class="asterisk"></em></label>
					<textarea style="width: 720px;height: 80px;" id="driveralipayaccount" name="driveralipayaccount"  maxlength="50">${opPlatformInfo.driveralipayaccount}</textarea>
				</div>
			</div>
			<div class="row">	
				<div class="col-10">
					<label>APPID<em class="asterisk"></em></label>
					<textarea style="width: 720px;height: 80px;" id="driveralipayappid" name="driveralipayappid"  maxlength="100">${opPlatformInfo.driveralipayappid}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>应用私钥<em class="asterisk"></em></label>
					<textarea style="width: 720px;height: 300px;" id="driveralipayprivatekey" name="driveralipayprivatekey"  maxlength="3500">${opPlatformInfo.driveralipayprivatekey}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>支付宝公钥<em class="asterisk"></em></label>
					<textarea style="width: 720px;height: 100px;" id="driveralipaypublickey" name="driveralipaypublickey"  maxlength="3500">${opPlatformInfo.driveralipaypublickey}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>合作伙伴ID<em class="asterisk"></em></label>
					<textarea style="width: 720px;height: 80px;" id="driveralipaypartnerid" name="driveralipaypartnerid"  maxlength="100">${opPlatformInfo.driveralipaypartnerid}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>合作伙伴私钥<em class="asterisk"></em></label>
					<textarea style="width: 720px;height: 300px;" id="driveralipaypartnerprivatekey" name="driveralipaypartnerprivatekey"  maxlength="3500">${opPlatformInfo.driveralipaypartnerprivatekey}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>合作伙伴公钥<em class="asterisk"></em></label>
					<textarea style="width: 720px;height: 100px;" id="driveralipaypartnerpublickey" name="driveralipaypartnerpublickey"  maxlength="3500">${opPlatformInfo.driveralipaypartnerpublickey}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<font color="red" style="margin-left: 30%;">收款账户涉及乘客、司机及机构客户的充值及收款，请勿随意更改</font>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>&nbsp;</label>
					<span style="width: 720px;float: left;text-align: center;">
						<button class="Mbtn red" onclick="save()" type="button">保存</button>
						<button class="Mbtn grey" onclick="cancel()" type="button" style="margin-left: 50px;">取消</button>
					</span>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="js/opInformationSet/driver/alipay.js"></script>
	<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
	</script>
</body>
</html>

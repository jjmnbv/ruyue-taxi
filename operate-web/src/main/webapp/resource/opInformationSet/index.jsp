<%@ page contentType="text/html; charset=UTF-8"
	import="com.szyciov.util.SystemConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		div p{
			width: 720px;
			float: left;
			word-break: break-all;
			border: 1px solid #E8E8E8;
		}
	</style>
</head>
<body>
	<div class="crumbs">
		<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 乘客收款账户管理
	</div>
	<div class="content">
		<div class="row form" style="padding-top: 30px;">
		</div> 
		<div class="form">
			<div style="height: 30px;width: 100%;">
				<font size="4px" style="margin-left: 10px;">支付宝收款账户管理&nbsp;
				</font>
				<c:choose>
					<c:when test="${opPlatformInfo.alipaystatus == '1'}">
						<font style="font-size: 16px;">【已开通</b>】</font>
						<button class="SSbtn grey_b" onclick="closeAlipay();" style="float: right;">禁用账户</button>
					</c:when>
					<c:otherwise>
						<font style="font-size: 16px;">【<b>未开通</b>】</font>
						<button class="SSbtn red" onclick="openAlipay();" style="float: right;">开通账户</button>
					</c:otherwise>
				</c:choose>
				<hr style="border:#000 dotted 1px;margin-top:auto;"/>
			</div>
			<div class="row form" style="padding-top: 20px;">
				
			</div>
			<button class="Mbtn green_a" onclick="allocation('alipay');">配置账户</button>
			<div class="row form" style="padding-top: 20px;">
				
			</div>
			<div class="row">
				<div class="col-10">
					<label>商家支付宝账户</label>
					<p style="height: 80px;">${fn:length(opPlatformInfo.aliPayAccount) == 0 ? "无" : opPlatformInfo.aliPayAccount}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>APPID</label>
					<p style="height: 80px;">${fn:length(opPlatformInfo.aliPayAppId) == 0 ? "无" : opPlatformInfo.aliPayAppId}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>应用私钥</label>
					<p style="height: 300px;overflow:hidden;">${fn:length(opPlatformInfo.aliPayPrivateKey) == 0 ? "无" : opPlatformInfo.aliPayPrivateKey}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>支付宝公钥</label>
					<p style="height: 100px;overflow:hidden;">${fn:length(opPlatformInfo.aliPayPublicKey) == 0 ? "无" : opPlatformInfo.aliPayPublicKey}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>合作伙伴ID</label>
					<p style="height: 80px;">${fn:length(opPlatformInfo.aliPayPartnerId) == 0 ? "无" : opPlatformInfo.aliPayPartnerId}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>合作伙伴私钥</label>
					<p style="height: 300px;overflow:hidden;">${fn:length(opPlatformInfo.aliPayPartnerPrivateKey) == 0 ? "无" : opPlatformInfo.aliPayPartnerPrivateKey}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>合作伙伴公钥</label>
					<p style="height: 100px;overflow:hidden;">${fn:length(opPlatformInfo.aliPayPartnerPublicKey) == 0 ? "无" : opPlatformInfo.aliPayPartnerPublicKey}</p>
				</div>
			</div>
			<div class="row form" style="padding-top: 30px;">
			</div>
			<div style="height: 30px;width: 100%;">
				<font size="4px" style="margin-left: 10px;">微信收款账户管理&nbsp;
				</font>
				<c:choose>
					<c:when test="${opPlatformInfo.wechatstatus == '1'}">
						<font style="font-size: 16px;">【已开通】</font>
						<button style="float: right;" class="SSbtn grey_b" onclick="closeWechat();">禁用账户</button>
					</c:when>
					<c:otherwise>
						<font style="font-size: 16px;">【<b>未开通</b>】</font>
						<button class="SSbtn red" onclick="openWechat();" style="float: right;">开通账户</button>
					</c:otherwise>
				</c:choose>
				<hr style="border:#000 dotted 1px;margin-top:auto;" />
			</div>
			<div class="row form" style="padding-top: 20px;">
				
			</div>
			<button class="Mbtn green_a" onclick="allocation('wechat');">配置账户</button>
			<div class="row form" style="padding-top: 20px;">
				
			</div>
			<div class="row">
				<div class="col-10">
					<label>商家微信账户</label>
					<p style="height: 40px;">${fn:length(opPlatformInfo.wechatPayAccount) == 0 ? "无" : opPlatformInfo.wechatPayAccount}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>APPID</label>
					<p style="height: 80px;">${fn:length(opPlatformInfo.wechatAppId) == 0 ? "无" : opPlatformInfo.wechatAppId}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>商户号</label>
					<p style="height: 80px;">${fn:length(opPlatformInfo.wechatMerchantNo) == 0 ? "无" : opPlatformInfo.wechatMerchantNo}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label>商户密钥</label>
					<p style="height: 80px;overflow:hidden;">${fn:length(opPlatformInfo.wechatSecretKey) == 0 ? "无" : opPlatformInfo.wechatSecretKey}</p>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/opInformationSet/index.js"></script>
	<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
	</script>
</body>
</html>

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
<title>乘客收款账户管理</title>
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
			width: 70%;
			float: right;
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
					<c:when test="${leLeasescompany.alipaystatus == '1'}">
						<!-- <font style="font-size: 16px;color: white;background: #BDBDBD;">已开通</font> -->
						<font style="font-size: 16px;">【<b>已开通</b>】</font>
						<button class="SSbtn grey_b" onclick="closeAlipay();" style="float: right;">禁用账户</button>
					</c:when>
					<c:otherwise>
						<!-- <font style="font-size: 16px;color: white;background: #BDBDBD;">未开通</font> -->
						<font style="font-size: 16px;">【未开通】</font>
						<button class="SSbtn red" onclick="openAlipay();" style="float: right;">开通账户</button>
					</c:otherwise>
				</c:choose>
				<hr style="border:#000 dotted 1px;margin-top:auto;" />
			</div>
			<div class="row form" style="padding-top: 20px;">
				
			</div>
			<button class="Mbtn green_a" onclick="allocation('alipay','支付宝');">配置账户</button>
			<div class="row form" style="padding-top: 20px;">
				
			</div>
			<%--<div class="row">
				<div class="col-4">
					<label>商家支付宝账户<em class="asterisk"></em></label>
					<input type="text" id="alipayAccount" name="alipayAccount" value="${leLeasescompany.alipayAccount}" disabled="disabled" maxlength="100" placeholder="无">
				</div>
				<div class="col-4">
					<label>合作伙伴ID<em class="asterisk"></em></label>
					<input type="text" id="alipaypartnerid" name="alipaypartnerid" value="${leLeasescompany.alipaypartnerid}" disabled="disabled" maxlength="100" placeholder="无">
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>APPID<em class="asterisk"></em></label>
					<input type="text" id="alipayappid" name="alipayappid" value="${leLeasescompany.alipayappid}" disabled="disabled" maxlength="100" placeholder="无">
				</div>
				<div class="col-4">
					<label>合作伙伴私钥<em class="asterisk"></em></label>
					<input type="text" id="alipayprivatekey" name="alipayprivatekey" value="${leLeasescompany.alipayprivatekey}" disabled="disabled" maxlength="100">
					<textarea rows="4" cols="4" style="width: 70%;" id="alipaypartnerprivatekey" name="alipaypartnerprivatekey" disabled="disabled" maxlength="1000" placeholder="无">${leLeasescompany.alipaypartnerprivatekey}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>应用私钥<em class="asterisk"></em></label>
					<input type="text" id="alipayprivatekey" name="alipayprivatekey" value="${leLeasescompany.alipayprivatekey}" disabled="disabled" maxlength="100">
					<textarea rows="4" cols="4" style="width: 70%;" id="alipayprivatekey" name="alipayprivatekey" disabled="disabled" maxlength="1000" placeholder="无">${leLeasescompany.alipayprivatekey}</textarea>
				</div>
				<div class="col-4">
					<label>合作伙伴公钥<em class="asterisk"></em></label>
					<input type="text" id="alipaypublickey" name="alipaypublickey" value="${leLeasescompany.alipaypublickey}" disabled="disabled" maxlength="100">
					<textarea rows="5" cols="5" style="width: 70%;" id="alipaypartnerpublickey" name="alipaypartnerpublickey" disabled="disabled" maxlength="300" placeholder="无">${leLeasescompany.alipaypartnerpublickey}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>支付宝公钥<em class="asterisk"></em></label>
					<input type="text" id="alipaypublickey" name="alipaypublickey" value="${leLeasescompany.alipaypublickey}" disabled="disabled" maxlength="100">
					<textarea rows="5" cols="5" style="width: 70%;" id="alipaypublickey" name="alipaypublickey" disabled="disabled" maxlength="300" placeholder="无">${leLeasescompany.alipaypublickey}</textarea>
				</div>
			</div> --%>
			<div class="row">
				<div class="col-6">
					<label>商家支付宝账户</label>
					<p>${fn:length(leLeasescompany.alipayAccount) == 0 ? "无" : leLeasescompany.alipayAccount}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>APPID</label>
					<p>${fn:length(leLeasescompany.alipayappid) == 0 ? "无" : leLeasescompany.alipayappid}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>应用私钥</label>
					<p>${fn:length(leLeasescompany.alipayprivatekey) == 0 ? "无" : leLeasescompany.alipayprivatekey}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>支付宝公钥</label>
					<p>${fn:length(leLeasescompany.alipaypublickey) == 0 ? "无" : leLeasescompany.alipaypublickey}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>合作伙伴ID</label>
					<p>${fn:length(leLeasescompany.alipaypartnerid) == 0 ? "无" : leLeasescompany.alipaypartnerid}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>合作伙伴私钥</label>
					<p>${fn:length(leLeasescompany.alipaypartnerprivatekey) == 0 ? "无" : leLeasescompany.alipaypartnerprivatekey}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>合作伙伴公钥</label>
					<p>${fn:length(leLeasescompany.alipaypartnerpublickey) == 0 ? "无" : leLeasescompany.alipaypartnerpublickey}</p>
				</div>
			</div>
			<div class="row form" style="padding-top: 30px;">
			</div>
			<div style="height: 30px;width: 100%;">
				<font size="4px" style="margin-left: 10px;">微信收款账户管理&nbsp;
				</font>
				<c:choose>
					<c:when test="${leLeasescompany.wechatstatus == '1'}">
						<font style="font-size: 16px;">【<b>已开通</b>】</font>
						<button style="float: right;" class="SSbtn grey_b" onclick="closeWechat();">禁用账户</button>
					</c:when>
					<c:otherwise>
						<font style="font-size: 16px;">【未开通】</font>
						<button class="SSbtn red" onclick="openWechat();" style="float: right;">开通账户</button>
					</c:otherwise>
				</c:choose>
				<hr style="border:#000 dotted 1px;margin-top:auto;" />
			</div>
			<div class="row form" style="padding-top: 20px;">
				
			</div>
			<button class="Mbtn green_a" onclick="allocation('wechat','微信');">配置账户</button>
			<div class="row form" style="padding-top: 20px;">
				
			</div>
			<%-- <div class="row">
				<div class="col-4">
					<label>商家微信账户<em class="asterisk"></em></label>
					<input type="text" id="wechatAccount" name="wechatAccount" value="${leLeasescompany.wechatAccount}" disabled="disabled" maxlength="100" placeholder="无">
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>APPID<em class="asterisk"></em></label>
					<input type="text" id="wechatappid" name="wechatappid" value="${leLeasescompany.wechatappid}" disabled="disabled" maxlength="100" placeholder="无">
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>商户号<em class="asterisk"></em></label>
					<input type="text" id="wechatmerchantno" name="wechatmerchantno" value="${leLeasescompany.wechatmerchantno}" disabled="disabled" maxlength="100">
					<textarea rows="4" cols="4" style="width: 70%;" id="wechatmerchantno" name="wechatmerchantno" disabled="disabled" maxlength="100" placeholder="无">${leLeasescompany.wechatmerchantno}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<label>商户密钥<em class="asterisk"></em></label>
					<input type="text" id="wechatsecretkey" name="wechatsecretkey" value="${leLeasescompany.wechatsecretkey}" disabled="disabled" maxlength="100">
					<textarea rows="5" cols="5" style="width: 70%;" id="wechatsecretkey" name="wechatsecretkey" disabled="disabled" maxlength="100" placeholder="无">${leLeasescompany.wechatsecretkey}</textarea>
				</div>
			</div> --%>
			<div class="row">
				<div class="col-6">
					<label>商家微信账户</label>
					<p>${fn:length(leLeasescompany.wechatAccount) == 0 ? "无" : leLeasescompany.wechatAccount}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>APPID</label>
					<p>${fn:length(leLeasescompany.wechatappid) == 0 ? "无" : leLeasescompany.wechatappid}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>商户号</label>
					<p>${fn:length(leLeasescompany.wechatmerchantno) == 0 ? "无" : leLeasescompany.wechatmerchantno}</p>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>商户密钥</label>
					<p>${fn:length(leLeasescompany.wechatsecretkey) == 0 ? "无" : leLeasescompany.wechatsecretkey}</p>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/accountReceivable/index.js"></script>
	<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
	</script>
</body>
</html>

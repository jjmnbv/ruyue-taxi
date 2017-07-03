<%@ page contentType="text/html; charset=UTF-8" %>
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
		<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="AccountReceivable/driver/Index">司机收款账户管理</a> > 支付宝收款账户
	</div>
	<div class="content">
		<div class="row form" style="padding-top: 30px;">
		</div> 
		<form id="editAlipayForm" class="form" method="post">
			<input type="hidden" id="id" name="id" value="${leLeasescompany.id}"/>
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
				<div class="col-6">
					<label>商家支付宝账户<em class="asterisk"></em></label>
					<textarea rows="2" cols="4" style="width: 70%;" id="driveralipayaccount" name="driveralipayaccount"  maxlength="50">${leLeasescompany.driveralipayaccount}</textarea>
				</div>
			</div>
			<div class="row">	
				<div class="col-6">
					<label>APPID<em class="asterisk"></em></label>
					<textarea rows="3" cols="4" style="width: 70%;" id="driveralipayappid" name="driveralipayappid"  maxlength="100">${leLeasescompany.driveralipayappid}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>应用私钥<em class="asterisk"></em></label>
					<textarea rows="28" cols="4" style="width: 70%;" id="driveralipayprivatekey" name="driveralipayprivatekey"  maxlength="3500">${leLeasescompany.driveralipayprivatekey}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>支付宝公钥<em class="asterisk"></em></label>
					<textarea rows="9" cols="5" style="width: 70%;" id="driveralipaypublickey" name="driveralipaypublickey"  maxlength="3500">${leLeasescompany.driveralipaypublickey}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>合作伙伴ID<em class="asterisk"></em></label>
					<textarea rows="3" cols="4" style="width: 70%;" id="driveralipaypartnerid" name="driveralipaypartnerid"  maxlength="100">${leLeasescompany.driveralipaypartnerid}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>合作伙伴私钥<em class="asterisk"></em></label>
					<textarea rows="28" cols="4" style="width: 70%;" id="driveralipaypartnerprivatekey" name="driveralipaypartnerprivatekey"  maxlength="3500">${leLeasescompany.driveralipaypartnerprivatekey}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<label>合作伙伴公钥<em class="asterisk"></em></label>
					<%-- <input type="text" id="alipaypublickey" name="alipaypublickey" value="${leLeasescompany.alipaypublickey}"  maxlength="100"> --%>
					<textarea rows="6" cols="5" style="width: 70%;" id="driveralipaypartnerpublickey" name="driveralipaypartnerpublickey"  maxlength="3500">${leLeasescompany.driveralipaypartnerpublickey}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-6">
					<font color="red" style="margin-left: 12%;">收款账户涉及乘客、司机及机构客户的充值及收款，请勿随意更改</font>
				</div>
			</div>
		</form>
		<div class="row">
			<div class="col-6" style="text-align: right;">
				<button class="Mbtn orange" onclick="save()">保存</button>
				<button class="Mbtn grey" onclick="cancel()">取消</button>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/accountReceivable/driver/alipay.js"></script>
	<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
	</script>
</body>
</html>

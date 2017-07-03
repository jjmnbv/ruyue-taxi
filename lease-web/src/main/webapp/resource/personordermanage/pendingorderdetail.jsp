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
		<title>个人订单</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="css/orgordermanage/orderdetail.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.skinFlat.css" id="styleSrc"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css" />
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="content/plugins/ion.rangeslider/js/ion-rangeSlider/ion.rangeSlider.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<style type="text/css">
			.form select{width:68%;}
			.breadcrumb{text-decoration:underline;}
		</style>
	</head>
	<body  class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="OrderManage/PersonOrderIndex">个人订单</a> > 订单详情
			<button class="SSbtn blue back" onclick="window.history.go(-1);">返回</button>
		</div>
		<div class="content" style="overflow: auto !important;">
			<h2 id="ddxx" style="padding-top: 30px;"></h2>
			
			<table class="table_a">
				<tr>
					<td class="grey_c ordermanage_td_1">下单人信息</td>
					<td id="xdrxx"></td>
					<td class="grey_c">订单类型</td>
					<td><span id="ddlx" class="font_grey">约车/接机/送机</span></td>
					<td class="grey_c">用车时间</td>
					<td id="ycsj"></td>
				</tr>
				<tr>
					<td class="grey_c">乘车人信息</td>
					<td><span id="ccrxx" class="font_grey"></span></td>
					<td class="grey_c">上车地址</td>
					<td id="scdz" title=""></td>
					<td class="grey_c">下车地址</td>
					<td id="xcdz" title=""><span class="font_grey"></span></td>
				</tr>
				<tr>
					<td class="grey_c">下单车型</td>
					<td id="xdcx"></td>
					<td class="grey_c">下单时间</td>
					<td><span id="xdsj" class="font_grey"></span></td>
					<td class="grey_c"><span class="font_grey">支付方式</span></td>
					<td id="zffs"></td>
				</tr>
				<tr id="ycsytr">
					<td class="grey_c">用车事由</td>
					<td colspan=5><span id="ycsy" class="font_grey"></span></td>
				</tr>
				<tr>
					<td class="grey_c">具体行程</td>
					<td colspan=5><span id="jtxc" class="font_grey"></span></td>
				</tr>
				<tr>
					<td id="hbhtitle" class="grey_c">航班号</td>
					<td id="hbhtd"><span id="hbh" class="font_grey"></span></td>
					<td id="jlsjtitle" class="grey_c">降落时间</td>
					<td id="jlsj"></td>
					<td class="grey_c">下单来源</td>
					<td id="xdly"></td>
				</tr>
			</table>
		</div>
		<script type="text/javascript">
			var orderObj = {
				orderno: "<%=request.getParameter("orderno")%>"
			};
		</script>
		<script type="text/javascript" src="js/personordermanage/pendingorderdetail.js"></script>
	</body>
</html>

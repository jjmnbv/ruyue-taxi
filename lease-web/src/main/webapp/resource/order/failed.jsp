<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
  <meta name="description" content="">
  <meta name="keywords" content="">
  <title>${title}</title>
  <base href="<%=basePath%>" >
  <link rel="stylesheet" type="text/css" href="content/css/style.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/yueche_tijiaochenggong.css"/>
  
  
  <script type="text/javascript" src="content/js/jquery.js"></script>
  
  <script type="text/javascript" src="js/order/failed.js"></script>
  <style type="text/css">
  .td_right{background:#C1BFBF;}
  </style>
</head>
<body style="background:white;height:800px;">
<input value="${orderno}" type="hidden" id="orderno"/>
<input value="${order.ordertype}" type="hidden" id="ordertype"/>
<input value="${order.paymethod}" type="hidden" id="paymethod"/>
<input value="${order.usetype}" type="hidden" id="usetype"/>
<div class="row col-12" style="font-size:20px;text-align:center;">
	<b>${title}</b>
</div>
<hr/>
<div class="row col-12"style="text-align:center;">
	<h1>${content}</h1>
</div>
<div style="height:200px;"></div>
<hr/>
<div class="row col-12" style="text-align:center;">
	<button class="Mbtn red  btn_left">人工派单</button>
	<button class="Mbtn blue  btn_right">取消订单</button>
</div>
<!--弹窗开始-->
<div class="popup_box" style="display:none;">
	<!--弹窗-->
   <div class="popup" id="cancel" >
		<div class="popup_title">
			<span>温馨提示</span>
			<i class="close"></i>
		</div>
		<div class="popup_content" style="font-size:24px;text-align:center;">
			您确定要取消吗?
		</div>
		<div class="popup_footer">
			<span class="cancel">不取消</span>
			<span class="sure">确定取消</span>
		</div>
	</div>
</div>
<!--弹窗结束-->
<!--底部-->
</body>
</html>

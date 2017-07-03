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
  <title>约车-提交成功</title>
  <base href="<%=basePath%>" >
  <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/yueche_tijiaochenggong.css"/>
  <script type="text/javascript" src="content/js/jquery.js"></script>
  
  <script type="text/javascript" src="js/order/success.js"></script>
</head>
<body>
<input value="${orderno}" type="hidden" id="orderno"/>
<input value="${ordertype}" type="hidden" id="ordertype"/>
<%@include file="../head.jsp"%>
<div class="con_body">
	<div class="title">提交成功</div>
	<img src="content/img/icon_tjcg.png"/>
	<div class="info">本次约车订单信息已经提交成功，系统正在进行派单，您可以在[我的订单]中查看订单详情。</div>
	<div class="btn_box">
		<span class="btn_left">查看订单</span>
		<span class="btn_right">取消订单</span>
	</div>
</div>
<!--弹窗开始-->
<div class="popup_box" id="window">
	<!--弹窗-->
    <div class="popup" id="table">
		<div class="popup_title">
			<span></span>
			<i class="close"></i>
		</div>
		<div class="popup_content">
			<table>
				<tr>
					<td class="td_right">乘车人</td>
					<td class="td_1"></td>
					<td class="td_right">用车时间</td>
					<td></td>
				</tr>
				<tr>
					<td class="td_right">上车地点</td>
					<td style="width:170px;"></td>
					<td class="td_right">下车地点</td>
					<td style="width:170px;"></td>
				</tr>
			</table>
		</div>
		<div class="popup_footer">
			<span class="cancel" style="display:none;">取消</span>
			<span class="sure">确定</span>
		</div>
   </div>
   <div class="popup" id="cancel" style="display:none;">
		<div class="popup_title">
			<span>温馨提示</span>
			<i class="close"></i>
		</div>
		<div class="popup_content">
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
 <%@include file="../foot.jsp"%>
</body>
</html>

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
  <meta name="description" content="">
  <meta name="keywords" content="">
  <title>我的订单—订单支付</title>
  <base href="<%=basePath%>">
  <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/zstyle.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/zsingle.css"/>
  <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
  <script type="text/javascript" src="content/js/common.js"></script>
  <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=AFLc9FVyIHUWExKYFETDeF6T&s=1"></script>
  <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
  <script type="text/javascript" src="js/basecommon.js"></script>
  
</head>
<body>
<div class="rule_box">
    <div class="crumbs">我的订单 > 订单详情 >  支付</div>
    <a class="btn_green" style="float: right;width:60px;height: 27px;line-height: 27px;margin-top: -41px;margin-right: 30px;" href="javascript:void(0);" onClick="callBack();">返回</a>
    <div class="new_rule_box payway">
        <div class="new_rule_b">
           支付金额 <span class="bigger_red"> ¥${orderamount}</span>
        </div>
        <div class="rule_box_a" style="padding: 60px 40px 40px  40px;line-height: 300%;">
        	<input id="orderno" name="orderno" value="${orderno}" type="hidden"/>
        	<c:if test="${ll.alipaystatus == '1'}">
        		<input type="radio" name="payway" value="3"><img src="content/img/img_zfb.png"><br>
        	</c:if>
        	<c:if test="${ll.wechatstatus == '1'}">
        		<input type="radio" name="payway" value="2"><img src="content/img/img_wx.png">
        	</c:if>
        	<%-- <c:if test="${ll.wechatstatus == '0' && ll.alipaystatus == '0'}">
        		没有开通支付方式
        	</c:if> --%>
        	<c:if test="${ll.wechatstatus == '1' || ll.alipaystatus == '1'}">
        		<a href="javascript:void(0)" class="btn_red" onclick="save();">下一步</a>
        	</c:if>
        </div>
    </div>
</div>
<script type="text/javascript">
var base = document.getElementsByTagName("base")[0].getAttribute("href");
	function save(){
		var orderno = $("#orderno").val();
		var paytype = $("input[name='payway']:checked").val();
		if(!paytype){
			toastr.warning("请选择一种支付方式", "提示");
			return ;
		}
		/* if(paytype=="3"){
			toastr.warning("支付宝支付开发中", "提示");
			return ;
		} */
		window.location.href = base+"MyOrder/GoPay?orderno="+orderno+"&paytype="+paytype;
	}
	function callBack(){
		var orderno = $("#orderno").val();
    	location.href = base+"MyOrder/Details?id="+orderno;
    };
</script>
</body>
</html>
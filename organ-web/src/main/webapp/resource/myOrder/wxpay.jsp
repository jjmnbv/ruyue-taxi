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
  <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=AFLc9FVyIHUWExKYFETDeF6T"></script>
  <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
  <script type="text/javascript" src="js/basecommon.js"></script>
  
</head>
<body>
<div class="rule_box">
    <div class="crumbs">我的订单 > 订单详情 >  支付</div>
    <a class="btn_green" style="float: right;width:60px;height: 27px;line-height: 27px;margin-top: -41px;margin-right: 30px;" href="javascript:void(0);" onClick="callBack();">返回</a>
    <div class="new_rule_box">
        <div class="new_rule_b">
          	 支付金额 <span class="bigger_red"> ¥${orderamount}</span>
        </div>
        <div class="rule_box_a" style="padding: 60px 40px 40px  40px;line-height: 300%;">
        	<div id="waitdom" style="width:251px;height:251px;background-color:aliceblue;top:105px;left:72px;position:absolute;">
        		<span id="info">生成支付信息中</span>
        	</div>
        	<img alt="" src="img/myOrder/weixindi.png">
        	<img id="qrzf" style="display:none;width:251px;height:251px;background-color:aliceblue;top:105px;left:72px;position:absolute;" src="">
        	<label></label>
        </div>
    </div>
</div>
<script type="text/javascript">
var base = document.getElementsByTagName("base")[0].getAttribute("href");
	var orderno = "${orderno}";
	$.post("MyOrder/UpdatePaytype", {"orderno":orderno,"paytype":"2"},function(data){
		if(data.status == "success"){
			var code_url = data.code_url;
			$("#waitdom").hide();
			$("#qrzf").attr("src","MyOrder/GetWXQRCode?codeurl="+code_url);
			$("#qrzf").show();
			checkOrderState();
		}else{
			$("#info").html(data.message?data.message:"支付信息生成失败，请重试！");
			toastr.error("支付遇到问题！", "提示");
		}
    });
	
	var t;
	function checkOrderState(){
		$.post("MyOrder/CheckOrderState", {"orderno":orderno},function(data){
			if(data.status == "success"){
				if(data.payed){
					//支付成功界面
					toastr.success("支付成功！", "提示");
					setTimeout(function(){
						//跳转到订单详情界面
						window.location.href = base+"MyOrder/Details?id="+orderno;
					},500);
					clearTimeout(t);
				}else{
					t = setTimeout(function(){
						checkOrderState();
					},5000);
				}
			}
	    });
	}
	function callBack(){
    	location.href = base+"MyOrder/Details?id="+orderno;
    };
</script>
</body>
</html>
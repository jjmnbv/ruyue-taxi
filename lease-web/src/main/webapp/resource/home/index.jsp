<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%   
String path = request.getContextPath();   
String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;   
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" > 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <script type="text/javascript" src="content/js/jquery.js"></script>
 <script type="text/javascript"  src="content/js/common.js"></script>
 <link rel="stylesheet" type="text/css" href="content/css/style.css"/>
</head>
<style>
 h2{line-height:250%;border-bottom:1px solid #dbdbdb;margin:0px -20px 20px -20px;padding-left:20px;}
.radius{border:1px solid #ddd;border-radius:5px;text-align:center;}
.radius>.col-6{height:50px;}
.radius strong{display:block;line-height: 130%; }
.radius img{width:50px;height:50px;margin-bottom:10px;}
.order_tittle{line-height:50px;border-bottom:1px solid #ddd;font-size:16px;line-height: 38px;}
.order_num {border-bottom:1px solid #ddd;font-size:20px;font-weight:bold;line-height: 38px;}
.radius strong+strong{margin-top:20px;}
.col-2 .radius{padding:30px 0;margin-top:10px;}
</style>
<body>
<div class="crumbs">首页  </div>
<div class="content">
			<h2>实时营运数据</h2>
            <div class="row">
                <div class="col-4">
	                <div class="radius row">
		                <div class="order_tittle col-6">
		                	<img style="width:18px;height:18px;margin-bottom: 6px;" src="img/home/icon_order.png">
		                	<span style="vertical-align:text-bottom;margin-left:5px;">总订单数</span>
		                </div>
		                <div class="order_num font_red col-6" id="totalcount">0</div><br>
		                <div class="col-6" style="border-right:1px solid #ddd;">
		                	<strong>${payedcount}</strong>
		                	已支付订单
		                </div>
		                 <div class="col-6">
		                	<strong>${unpayedcount}</strong>
		                	待支付订单
		                </div>
	                </div>
            	</div>
                  <div class="col-4">
	                <div class="radius row">
		                <div class="order_tittle col-6">
		                	<img style="width:18px;height:18px;margin-bottom: 6px;" src="img/home/icon_money.png">
		                	<span style="vertical-align:text-bottom;margin-left:5px;">总收益金额</span>
		                </div>
		                <div class="order_num font_red col-6" id="totalsy">0</div><br>
		                <div class="col-3" style="border-right:1px solid #ddd;">
		                	<strong>￥${totalmoney}</strong>
		                	总订单金额
		                </div>
		                 <div class="col-3" style="border-right:1px solid #ddd;">
		                	<strong>￥${cytotalmoney}</strong>
		                	差异金额
		                </div>
		                 <div class="col-3" style="border-right:1px solid #ddd;">
		                	<strong>￥${couponmoney}</strong>
		                	优惠金额
		                </div>
		                 <div class="col-3">
		                	<strong>￥${cancelmoney}</strong>
		                	取消费用
		                </div>
	                </div>
            	</div>
            	  <div class="col-4">
	                <div class="radius row">
		                <div class="order_tittle col-6"  style="border-right:1px solid #ddd;border-bottom:none;font-size:20px;font-weight:bold;line-height: 65px;">￥${payedmoney}</div>
		                <div class="order_num col-6"  style="border-bottom:none;line-height:65px;">￥${unpayedmoney}</div><br>
		                <div class="col-6" style="border-right:1px solid #ddd;">
		                	已支付金额
		                </div>
		                 <div class="col-6">
		                   	待支付金额
		                </div>
	                </div>
            	</div>
            </div>
            <div class="row">
                <div class="col-2"> 
                  <div class="radius">
	                  <img src="img/home/icon_zuori.png">
	                  <strong>￥${tomorrowmoney}</strong>
	                	 订单金额
	                   <strong>${tomorrowcount}</strong>
	                  	 订单数量
                  </div>
                </div>
                 <div class="col-2"> 
                  <div class="radius">
	                  <img src="img/home/icon_jintian.png">
	                  <strong>￥${todaymoney}</strong>
	                	 订单金额
	                   <strong>${todaycount}</strong>
	                  	 订单数量
                  </div>
                </div>
                 <div class="col-2"> 
                  <div class="radius">
	                  <img src="img/home/icon_benyue.png">
	                  <strong>￥${themonthmoney}</strong>
	                	 订单金额
	                   <strong>${themonthcount}</strong>
	                  	 订单数量
                  </div>
                </div>
                 <div class="col-2"> 
                  <div class="radius">
	                  <img src="img/home/icon_jieji.png">
	                  <strong>￥${jjmoney}</strong>
	                	 订单金额
	                   <strong>${jjcount}</strong>
	                  	 订单数量
                  </div>
                </div>
                 <div class="col-2"> 
                  <div class="radius">
	                  <img src="img/home/icon_songji.png">
	                  <strong>￥${sjmoney}</strong>
	                	 订单金额
	                   <strong>${sjcount}</strong>
	                  	 订单数量
                  </div>
                </div>
                 <div class="col-2"> 
                  <div class="radius">
	                  <img src="img/home/icon_yuyue.png">
	                  <strong>￥${ycmoney}</strong>
	                	 订单金额
	                   <strong>${yccount}</strong>
	                  	 订单数量
                  </div>
                </div>
            </div>
            
</div>
</body>
 <script type="text/javascript">
 	var payedcount = ${payedcount};
 	var unpayedcount = ${unpayedcount};
 	var totalcount = parseInt(payedcount)+parseInt(unpayedcount);
 	$("#totalcount").html(totalcount);
 	
 	var totalmoney = ${totalmoney};
 	var cytotalmoney = ${cytotalmoney};
 	var couponmoney = ${couponmoney};
 	var cancelmoney = ${cancelmoney};
 	var totalsy = "￥"+parseFloat(totalmoney-cytotalmoney-couponmoney+cancelmoney).toFixed(1);
 	$("#totalsy").html(totalsy);
 </script>
</html>
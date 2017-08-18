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
.order_tittle{line-height:50px;font-size:16px;line-height: 38px;border-bottom:1px solid #ddd;}
.order_num {border-bottom:1px solid #ddd;font-size:20px;font-weight:bold;line-height: 38px;}
.order_num1 {font-size:30px;font-weight:bold;line-height: 38px;}
.radius strong+strong{margin-top:20px;}
.col-2 .radius{padding:30px 0;margin-top:10px;}
.breakcrumbs{text-decoration:underline}
.col-13 {width: 14.28571428%;}
/*总订单金额错位*/
@media screen and ( min-width:790px ) and ( max-width:1200px ) {
	.media_css_order_num1{
		position: absolute !important;
    	width: 100% !important;
    	text-align: right !important;
    	padding-right: 20px !important;
	}
}
</style>
<body style="height:auto; overflow-y:scroll">
<div class="crumbs"><a class="breakcrumbs">首页</a>  </div>
<div class="content">
			<h2>toB业务实时营运数据</h2>
           <%--  <div class="row">
                <div class="col-4">
	                <div class="radius row" style="position:relative;">
		                <div class="order_tittle col-6" style="height:100px;">
		                    <img style="width:20px;height:20px;" src="img/home/icon_order.png">
		                	<span style="vertical-align:text-bottom;margin-left:5px;">总订单数</span>
		                </div>
		                <div class="order_num1 font_red col-6" id="totalcount" style="padding-top:45px;">${ordersCom}</div>
	                </div>
            	</div>
                  <div class="col-4">
	                <div class="radius row" style="position:relative;">
		                <div class="order_tittle col-6"style="height:100px;">
		                    <img style="width:20px;height:20px;" src="img/home/icon_money.png">
		                	<span style="vertical-align:text-bottom;margin-left:5px;">总订单金额</span>
		                </div>
		                <div class="order_num1 font_red col-6 media_css_order_num1" id="totalsy" style="padding-top:45px;">￥${ordersmoneyCom}</div>
	                </div>
            	</div>
            </div> --%>
            <div class="row">
            <div class="col-4">
	                <div class="radius row">
		                <div class="order_tittle col-6">
		                    <img style="width:18px;height:18px;margin-bottom: 6px;" src="img/home/icon_order.png">
		                	<span style="vertical-align:text-bottom;margin-left:5px;">总订单数</span>
		                </div>
		                <div class="order_num font_red col-6" id="totalcount">${ordersCom}</div><br>
	                </div>
            	</div>
                  <div class="col-4">
	                <div class="radius row">
		                <div class="order_tittle col-6">
		                    <img style="width:18px;height:18px;margin-bottom: 6px;" src="img/home/icon_money.png">
		                	<span style="vertical-align:text-bottom;margin-left:5px;">总收益金额</span>
		                </div>
		                <div class="order_num font_red col-6" id="totalsy">￥${ordersmoneyCom}</div><br>
	                </div>
            	</div>
            </div>
            <div class="row">
               <div class="col-2"> 
                  <div class="radius">
	                  <img src="img/home/icon_zuori.png">
	                  <strong>￥${ordersmoneyComY}</strong>
	                	 订单金额
	                   <strong>${ordersComY}</strong>
	                  	 订单数量
                  </div>
                </div>
                 <div class="col-2"> 
                  <div class="radius">
	                  <img src="img/home/icon_jintian.png">
	                  <strong>￥${ordersmoneyComT}</strong>
	                	 订单金额
	                   <strong>${ordersComT}</strong>
	                  	 订单数量
                  </div>
                </div>
                 <div class="col-2"> 
                  <div class="radius">
	                  <img src="img/home/icon_benyue.png">
	                  <strong>￥${ordersmoneyComM}</strong>
	                	 订单金额
	                   <strong>${ordersComM}</strong>
	                  	 订单数量
                  </div>
                </div>
                <div class="col-2"> 
                  <div class="radius">
	                  <img src="img/home/icon_jieji.png">
	                  <strong>￥${pickupordersMoneyC}</strong>
	                	 订单金额
	                   <strong>${pickupordersC}</strong>
	                  	 订单数量
                  </div>
                </div>
                 <div class="col-2"> 
                  <div class="radius">
	                  <img src="img/home/icon_songji.png">
	                  <strong>￥${dropoffordersMoneyC}</strong>
	                	 订单金额
	                   <strong>${dropoffordersC}</strong>
	                  	 订单数量
                  </div>
                </div>
                 <div class="col-2"> 
                  <div class="radius">
	                  <img src="img/home/icon_yuyue.png">
	                  <strong>￥${carordersMoneyC}</strong>
	                	 订单金额
	                   <strong>${carordersC}</strong>
	                  	 订单数量
                  </div>
                </div>
            </div>
            <h2>toC业务实时营运数据</h2>
            <div class="row">
                <div class="col-4">
	                <div class="radius row">
		                <div class="order_tittle col-6">
		                    <img style="width:18px;height:18px;margin-bottom: 6px;" src="img/home/icon_order.png">
		                	<span style="vertical-align:text-bottom;margin-left:5px;">总订单数</span>
		                </div>
		                <div class="order_num font_red col-6" id="totalcount">${ordersComP}</div><br>
		                <div class="col-6" style="border-right:1px solid #ddd;">
		                	<strong>${paidorders}</strong>
		                	已支付订单
		                </div>
		                 <div class="col-6">
		                	<strong>${bepaidorders}</strong>
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
		                <div class="order_num font_red col-6" id="totalsy">￥${incomemoney}</div><br>
		                <div class="col-3" style="border-right:1px solid #ddd;">
		                	<strong>￥${ordersmoneyComP}</strong>
		                	总订单金额
		                </div>
		                 <div class="col-3" style="border-right:1px solid #ddd;">
		                	<strong>￥${diffmoney}</strong>
		                	差异金额
		                </div>
		                 <div class="col-3" style="border-right:1px solid #ddd;">
		                	<strong>￥${cancelmoney}</strong>
		                	优惠金额
		                </div>
		                 <div class="col-3">
		                	<strong>￥${couponmoney}</strong>
		                	取消费用
		                </div>
	                </div>
            	</div>
            	  <div class="col-4">
	                <div class="radius row">
		                <div class="order_tittle col-6"  style="border-right:1px solid #ddd;border-bottom:none;font-size:20px;font-weight:bold;line-height: 65px;">￥${payedmoney}</div>
		                <div class="order_num col-6"  style="border-bottom:none;line-height:65px;">￥${bepaidmoney}</div><br>
		                <div class="col-6" style="border-right:1px solid #ddd;">
		                	已入账金额
		                </div>
		                 <div class="col-6">
		                   	待入账金额
		                </div>
	                </div>
            	</div>
            </div>
            <div class="row">
             <div class="col-13"> 
                  <div class="radius">
	                  <img src="img/home/icon_zuori.png">
	                  <strong>￥${ordersmoneyComYP}</strong>
	                	 订单金额
	                   <strong>${ordersComYP}</strong>
	                  	 订单数量
                  </div>
                </div>
                 <div class="col-13"> 
                  <div class="radius">
	                  <img src="img/home/icon_jintian.png">
	                  <strong>￥${ordersmoneyComTP}</strong>
	                	 订单金额
	                   <strong>${ordersComTP}</strong>
	                  	 订单数量
                  </div>
                </div>
                 <div class="col-13"> 
                  <div class="radius">
	                  <img src="img/home/icon_benyue.png">
	                  <strong>￥${ordersmoneyComMP}</strong>
	                	 订单金额
	                   <strong>${ordersComMP}</strong>
	                  	 订单数量
                  </div>
                </div>
                <div class="col-13"> 
                  <div class="radius">
	                  <img src="img/home/icon_jieji.png">
	                  <strong>￥${pickupordersMoney}</strong>
	                	 订单金额
	                   <strong>${pickuporders}</strong>
	                  	 订单数量
                  </div>
                </div>
                 <div class="col-13"> 
                  <div class="radius">
	                  <img src="img/home/icon_songji.png">
	                  <strong>￥${dropoffordersMoney}</strong>
	                	 订单金额
	                   <strong>${dropofforders}</strong>
	                  	 订单数量
                  </div>
                </div>
                 <div class="col-13"> 
                  <div class="radius">
	                  <img src="img/home/icon_yuyue.png">
	                  <strong>￥${carordersMoney}</strong>
	                	 订单金额
	                   <strong>${carorders}</strong>
	                  	 订单数量
                  </div>
                </div>
               <div class="col-13"> 
                  <div class="radius">
	                  <img src="img/home/icon_taxi.png">
	                  <strong>￥${taxiordersMoney}</strong>
	                	 订单金额
	                   <strong>${taxiorders}</strong>
	                  	 订单数量
                  </div>
                </div>
            </div>
            
</div>
</body>
 <script type="text/javascript">
 	/* var payedcount = ${payedcount};
 	var unpayedcount = ${unpayedcount};
 	var totalcount = parseInt(payedcount)+parseInt(unpayedcount);
 	$("#totalcount").html(totalcount);
 	
 	var totalmoney = ${totalmoney};
 	var cytotalmoney = ${cytotalmoney};
 	var totalsy = parseInt(totalmoney)-parseInt(cytotalmoney);
 	$("#totalsy").html(totalsy); */
 </script>
</html>
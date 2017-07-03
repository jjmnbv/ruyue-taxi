<%@ page contentType="text/html; charset=UTF-8"
		import="com.szyciov.util.SystemConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String yingyan_ak = SystemConfig.getSystemProperty("yingyan_ak");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <title>我的订单—订单详情</title>
  <base href="<%=basePath%>">
  <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/zstyle.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/zsingle.css"/>
  <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
  <script type="text/javascript" src="content/js/common.js"></script>
  <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>"></script>
  <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
  <script type="text/javascript" src="js/basecommon.js"></script>
</head>
<style>
.star{display:inline-block;width:40px;height:40px;}
.star+.star{margin-left:10px;}
.sgrey{background:url(content/img/btn_xinxinbig.png) no-repeat center;}
.syellow{background:url(content/img/btn_xinxinbig_pre.png) no-repeat center;}
textarea{width:100%;resize:none;border:1px solid #e4e4e4;border-radius:3px;padding:6px 4px;margin:6px 2px;}
</style>
<body>
<div class="rule_box">
    <div class="crumbs">我的订单 > 订单详情</div>
    <!-- <button class="btn_green" tyle="float: right; width: 60px; height: 27px; line-height: 27px; margin-top: -4px; margin-right: 30px;">返回</button> -->
    <a class="btn_green" style="float: right;width:60px;height: 27px;line-height: 27px;margin-top: -41px;margin-right: 30px;" href="javascript:void(0);" onClick="callBack();">返回</a>
    <div class="new_rule_box">
        <div class="new_rule_b">
            <table>
                <tr>
                    <td class="t">订单编号</td>
                    <td>${orgOrderDetails.orderno}<input id="orderno" name="" value="${orgOrderDetails.orderno}" type="hidden"/></td>
                    <td class="t">订单状态</td>
                   	<td class="font-red">${orgOrderDetails.orderStatusShow}</td>
                </tr>
                <c:if test="${orgOrderDetails.orderstatus == '8'}">
					<tr>
						<td class="t">取消时间</td>
						<td>${orgOrderDetails.canceltimeShow}</td>
						<td class="t">取消方</td>
						<td>${orgOrderDetails.cancelparty}</td>
					</tr>
				</c:if>
                <tr>
                    <td class="t">客服电话</td>
                    <td>${orgOrderDetails.servicesphone}</td>
                    <td class="t">服务车企</td>
                    <td>${orgOrderDetails.companyName}</td>
                </tr>
            </table>
            <!-- 
            <li data-value="0">待接单</li>
			<li data-value="1">待人工派单</li>
			<li data-value="2">司机未出发</li>
			<li data-value="3">司机已出发</li>
			<li data-value="4">司机已抵达</li>
			<li data-value="5">已接到乘客</li>
			<li data-value="6">服务中</li>
			<li data-value="7">行程结束</li>
			<li data-value="8">订单取消</li>
			orderstatus    0-待接单，1-待人工派单，2-待出发，3-已出发，4-已抵达，5-接到乘客，6-服务中，7-行程结束，8-已取消
			paymentstatus  0-未支付，1-已支付，2-结算中，3-已结算
			 -->
            <c:if test="${orgOrderDetails.orderStatusShow == '等待接单'}">
            	<a class="btn_red" style="float: right;margin-top: -52px;margin-right: 100px;" href="javascript:void(0);" onClick="cancelOrder('${orgOrderDetails.orderno}');">取消订单</a>
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '等待服务'}">
            	<a class="btn_red" style="float: right;margin-top: -52px;margin-right: 100px;" href="javascript:void(0);" onClick="cancelOrder('${orgOrderDetails.orderno}');">取消订单</a>
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '服务中'}">
            	
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '未支付'}">
            	<a class="btn_red" style="float: right;margin-top: -52px;margin-right: 100px;" href="javascript:void(0);" onClick="payment('${orgOrderDetails.orderno}','${orgOrderDetails.orderamount}');">去支付</a>
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '已支付'}">
	            <c:choose>
	            	<c:when test="${empty orgOrderDetails.usercomment}">
	            		<a class="btn_red pinjiabtn" style="float: right;margin-top: -52px;margin-right: 100px;" href="javascript:void(0);">评价</a>
	            	</c:when>
	            	<c:otherwise>
	            		<a class="btn_red chakanpinjiabtn" style="float: right;margin-top: -52px;margin-right: 100px;" href="javascript:void(0);">查看评价</a>
	            	</c:otherwise>
	            </c:choose>
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '已取消'}">
            	
            </c:if>
            	<!-- <a class="btn_red" style="float: right;margin-top: -52px;margin-right: 100px;" href="javascript:void(0);" onClick="javascript:.go(-1);">去评价</a> -->
        </div>
        <div class="rule_box_a">
            <table style="word-wrap:break-word;word-break:break-all;white-space:normal;">
                <tr>
                    <td class="t">乘车人</td>
                    <td>${orgOrderDetails.passengerCar}</td>
                    <td class="t">用车时间</td>
                    <td>${orgOrderDetails.usetimeShow}</td>
                </tr>
                <tr>
                    <td class="t">上车地点</td>
                    <td>${orgOrderDetails.onaddress}</td>
                    <td class="t">下车地点</td>
                    <td>${orgOrderDetails.offaddress}</td>
                </tr>
                <tr>
                    <td class="t">用车方式</td>
                    <td>${orgOrderDetails.ordertype}</td>
                    <td class="t">用车事由</td>
                    <td>${orgOrderDetails.vehiclessubjecttype}</td>
                </tr>
                <tr>
                    <td class="t">服务车型</td>
                    <td>${orgOrderDetails.modelName}</td>
                    <td class="t"></td>
                    <td>${orgOrderDetails.vehiclessubject}</td>
                </tr>
                <tr>
                    <td colspan="4" height="20"></td>
                </tr>
                <tr>
                    <td class="t">司机</td>
                    <td>${orgOrderDetails.driverName}</td>
                    <td class="t">车牌号</td>
                    <td>${orgOrderDetails.plateNo}</td>
                </tr>
                <tr>
                    <td class="t">品牌车系</td>
                    <td>${orgOrderDetails.vehcBrandName}</td>
                    <td class="t"></td>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="4" height="20"></td>
                </tr>
                <tr>
                    <td class="t">支付方式</td>
                    <td>${orgOrderDetails.paymethod}</td>
                    <td class="t"></td>
                    <td></td>
                </tr>
                <c:if test="${orgOrderDetails.orderStatusShow == '等待接单'}">
                	<tr>
	                    <td class="t">预估费用</td>
	                    <td><span class="bigred">${orgOrderDetails.estimatedcost}</span><span class="font-blue">费用说明</span> </td>
	                    <td class="t"></td>
	                    <td></td>
                	</tr>
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '等待服务'}">
	            	<tr>
	                    <td class="t">预估费用</td>
	                    <td><span class="bigred">${orgOrderDetails.estimatedcost}</span><span class="font-blue">费用说明</span> </td>
	                    <td class="t"></td>
	                    <td></td>
                	</tr>
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '服务中'}">
	            	<tr>
	                    <td class="t">实时费用</td>
	                    <td><span class="bigred">${orderCost.cost}</span><span class="font-blue">费用说明</span> </td>
	                    <td class="t"></td>
	                    <td></td>
                	</tr>
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '未支付' || orgOrderDetails.orderStatusShow == '未结算' || orgOrderDetails.orderStatusShow == '结算中' || orgOrderDetails.orderStatusShow == '已结算'}">
	            	<tr>
	                    <td class="t">实际费用</td>
	                    <td><span class="bigred">${orgOrderDetails.orderamount}</span><span class="font-blue">费用说明</span> </td>
	                    <td class="t"></td>
	                    <td></td>
                	</tr>
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '已支付'}">
	            	<tr>
	                    <td class="t">实际费用</td>
	                    <td><span class="bigred">${orgOrderDetails.orderamount}</span><span class="font-blue">费用说明</span> </td>
	                    <td class="t"></td>
	                    <td></td>
                	</tr>
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '已取消'}">
	            	<tr>
	                    <td class="t">预估费用</td>
	                    <td><span class="bigred">${orgOrderDetails.estimatedcost}</span><span class="font-blue">费用说明</span> </td>
	                    <td class="t"></td>
	                    <td></td>
                	</tr>
	            </c:if>
            </table>
            <c:if test="${orgOrderDetails.orderStatusShow == '服务中'}">
	             <div class="trail" data-value = "cheliang">查看车辆位置>></div>	
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '未支付' || orgOrderDetails.orderStatusShow == '未结算' || orgOrderDetails.orderStatusShow == '结算中' || orgOrderDetails.orderStatusShow == '已结算'}">
            	 <div class="trail" data-value = "xingcheng">查看行程轨迹>></div>
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '已支付'}">
            	 <div class="trail" data-value = "xingcheng">查看行程轨迹>></div>
            </c:if>
           
        </div>
    </div>
    <!--费用说明弹窗-->
    <div class="pop_box feiyon">
        <div class="pop">
            <div class="head" style="width:563px;">
            	费用说明
                <img src="content/img/btn_close.png" alt="关闭" class="close">
            </div>
            <div class="con_a"  style="height: 362px;" id="realTimeDetails">
            	<%-- <c:if test="${orgOrderDetails.orderStatusShow == '待接单'}">
                	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车费总计   <span class="bigred">${orderCost.cost}</span>
                	<br><br> 
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '待服务'}">
	            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车费总计   <span class="bigred">${orderCost.cost}</span>
	            	<br><br> 
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '服务中'}">
	            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车费总计   <span class="bigred">${orderCost.cost}</span>
	            	<br><br> 
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '未支付'}">
	            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车费总计   <span class="bigred">${orderCost.cost}</span>
	            	<br><br> 
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '已完成'}">
	            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车费总计   <span class="bigred">${orderCost.cost}</span>
	            	<br><br> 
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '已取消'}">
	            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车费总计   <span class="bigred">${orderCost.cost}</span>
					<br><br> 
	            </c:if> --%>
	            <%-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车费总计   <span class="bigred">${orderCost.cost}</span>
				<br><br> 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;里程费（${orderCost.mileage}）${orderCost.rangecost}
				<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时长费（${orderCost.times}）${orderCost.timecost}
				<br><br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计费规则 ${orderCost.startprice} 起步费+${orderCost.rangeprice}+${orderCost.timeprice} --%>
        	</div>
            <div class="foot"><button class="btn_red">确定</button> </div>
        </div>
    </div>
    <!--行程轨迹明弹窗-->
    <div class="pop_box ditu">
        <div class="pop" style="width: 700px;margin-left: -346px!important;margin-top: -100px">
            <div class="head" id="dituDiv">
                                                 查看车辆位置
                <img src="content/img/btn_close.png" alt="关闭" class="close">
            </div>
            <div class="con_b">
                <div id="map_canvas" style="width: auto; height: 500px; border: #ccc solid 1px;"></div>
            </div>
        </div>
    </div>
    <!--评价页面 -->
    <div class="pop_box pinjia">
        <div class="pop_a">
            <div class="head">
             	  评价
                <img src="content/img/btn_close.png" alt="关闭" class="close">
            </div>
            <div class="con_a">
           		<div class="star sgrey"></div>
              	<div class="star sgrey"></div>
              	<div class="star sgrey"></div>
              	<div class="star sgrey"></div>
              	<div class="star sgrey"></div>
              	<textarea rows="6" maxlength="200" placeholder="您对本次服务的感受" autofocus id="pinjiaContent"></textarea>
            </div>
            <div class="foot"><button class="btn_red">确定</button> </div>
        </div>
    </div>
    
     <!--评价页面 -->
    <div class="pop_box chakanpinjia">
        <div class="pop_a">
            <div class="head">
             	  评价
                <img src="content/img/btn_close.png" alt="关闭" class="close">
            </div>
            <div class="con_a">
            	<c:if test="${orgOrderDetails.userrate=='1'}">
            		<div class="star syellow"></div>
	              	<div class="star sgrey"></div>
	              	<div class="star sgrey"></div>
	              	<div class="star sgrey"></div>
	              	<div class="star sgrey"></div>
            	</c:if>
            	<c:if test="${orgOrderDetails.userrate=='2'}">
            		<div class="star syellow"></div>
	              	<div class="star syellow"></div>
	              	<div class="star sgrey"></div>
	              	<div class="star sgrey"></div>
	              	<div class="star sgrey"></div>
            	</c:if>
            	<c:if test="${orgOrderDetails.userrate=='3'}">
	            	<div class="star syellow"></div>
	              	<div class="star syellow"></div>
	              	<div class="star syellow"></div>
	              	<div class="star sgrey"></div>
	              	<div class="star sgrey"></div>
            	</c:if>
            	<c:if test="${orgOrderDetails.userrate=='4'}">
	            	<div class="star syellow"></div>
	              	<div class="star syellow"></div>
	              	<div class="star syellow"></div>
	              	<div class="star syellow"></div>
	              	<div class="star sgrey"></div>
            	</c:if>
            	<c:if test="${orgOrderDetails.userrate=='5'}">
	            	<div class="star syellow"></div>
	              	<div class="star syellow"></div>
	              	<div class="star syellow"></div>
	              	<div class="star syellow"></div>
	              	<div class="star syellow"></div>
            	</c:if>
              	<textarea rows="6" maxlength="200" placeholder="您对本次服务的感受" autofocus>${orgOrderDetails.usercomment}</textarea>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="js/myOrder/details.js"></script>
<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
	
	function payment(orderno,orderamount){
		window.location.href=base+"MyOrder/Payment?orderamount="+orderamount+"&orderno="+orderno;
		
	}
	//判断  至少要有一种支付方式
	
	function cancelOrder(orderId){
		Zconfirm("您确定取消吗？",function(){
			//Zalert(11,orderId);
			$(window.parent.document).find(".pop_index").css("display","none");
			//$(window.parent.document).find(".pop_index").hide();
			var data = {
				orderId : orderId
			};
			$.post("MyOrder/CancelOrder", data, function(json) {
				/* $(window.parent.document).find(".popup_box").hide();
				$(window.parent.document).find(".pop_box").css("display","none"); */
				if(json.status != 0){
					toastr.error(json.message, "提示");	
				}
				$(".pop_box").hide();
				$(window.parent.document).find(".pop_index").hide();
				location.href = base+"MyOrder/Details?id="+orderId;
			});
	    });
	};
	
	$(document).ready(function() {
        $(".font-blue").click(function(){
            	var orderno = $("#orderno").val();
            	var data = {
            		orderno : orderno
            	}
            	$.post("MyOrder/RealTimeDetails", data, function(status) {
        			var html = ""
       				html="<span style='margin-left:50px;font-size: 18px;'>车费总计 </span><span class='bigred' style='margin-left: 50px;'>"+status.cost+"</span>"
        			
       				//+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车费总计   <span class='bigred'>"+status.cost+"</span>"
        			+"<br><br>"
        			+"<span style='margin-left:100px;width: 180px;display:inline-block;'>起步价</span><span style='margin-right:100px;'>"+status.startprice+"</span>"
        			//+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;里程费（"+status.mileage+"）"+status.rangecost
        			+"<br>"
        			//+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时长费（"+status.times+"）"+status.timecost
					+'<span style="margin-left:100px;width: 180px;display:inline-block;">里程费（'+status.mileage+'）</span><span style="margin-right:100px;">'+status.rangecost+'</span>'   			
        			+"<br>"
        			//+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计费规则&nbsp;"+status.startprice+"&nbsp;起步费+"+status.rangeprice+"+"+status.timeprice;
        			+'<span style="margin-left:100px;width: 180px;display:inline-block;">时长费（'+status.times+'）</span><span style="margin-right:100px;">'+status.timecost+'</span>'
        			if(status.deadheadcost != '0.0元' && status.res == 'resY'){
        				html+='<br><span style="margin-left:100px;width: 180px;display:inline-block;">空驶费（'+status.deadheadmileage+'）</span><span style="margin-right:100px;">'+status.deadheadcost+'</span>'
        			}
					if(status.nightcost != '0.0元' && status.res == 'resY'){
						html+='<br><span style="margin-left:100px;width: 180px;display:inline-block;">夜间费（'+status.mileage+'）</span><span style="margin-right:100px;">'+status.nightcost+'</span>'
        			}
        			$("#realTimeDetails").html(html);
        			$(".feiyon").show();
        		});
        });
        
        $(".trail").click(function(){
        	var val = $(".trail").attr("data-value");
        	if(val == 'xingcheng'){
        		$("#dituDiv").html("查看行程轨迹"+"<img src='content/img/btn_close.png' alt='关闭' class='close'>");
        	}
            $(".ditu").show();
        });
        //评价弹窗
        $(".pinjiabtn").click(function(){
        	$(".star").removeClass("syellow").addClass("sgrey");
        	$("#pinjiaContent").val("");
            $(".pinjia").show();
        });
        
        $(".pop_box .close").click(function(){
            $(".pop_box").hide();
        });
       
        //星星选择
        $(".star").click(function(){
        	var n=$(this).index();
        	for(i=0;i<5;i++){
        		if(i<=n){
        			$(".star").eq(i).removeClass("sgrey").addClass("syellow");
            	}else{
            		if($(this).hasClass("syellow")){
            			$(".star").eq(i).removeClass("syellow").addClass("sgrey");
            		}
            	}
        		 
        	}
        });
        
      //点星星确定
        $(".pinjia .btn_red").click(function(){
			var orderno = $("#orderno").val();
        	//星星几颗
            var star = $(".pinjia .syellow").length;
        	if(star == 0){
        		toastr.error("请您对服务做出评价", "提示");
        		return;
        	}
        	//内容
        	var content = $("#pinjiaContent").val();
        	if(content == '' || content == null){
        		toastr.error("请您对服务做出评价", "提示");
        		return;
        	}
        	var data = {
       			orderno : orderno,
        		star : star,
       			content : content
   			};
   			$.post("MyOrder/UpdateUserrate", data, function(status) {
   				if (status.ResultSign == "Successful") {
   					var message = status.MessageKey == null ? status
   							: status.MessageKey;
   					$(".pop_box").hide();
   	   				toastr.options.onHidden = function() {
   	   					location.href = base+"MyOrder/Details?id="+orderno;
   					}
   					toastr.success(message, "提示");
   				} else {
   					
   				}
   			});
            //$(".star").removeClass("syellow").addClass("sgrey");
        });
      
        
      //查看评价弹窗
        $(".chakanpinjiabtn").click(function(){
            $(".chakanpinjia").show();
        });
    });
	function callBack(){
    	location.href = base+"MyOrder/Index";
    };
    
</script>
</body>
</html>
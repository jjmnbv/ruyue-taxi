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
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
  <script type="text/javascript" src="content/js/common.js"></script>
  <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>"></script>
</head>
<body>
<%-- <div class="header">
		<div class="header_box">
			<h1>网约车</h1>
			<ul class="menu">
				<c:forEach items="${navinfo}" var="nav" varStatus="status">
					<li class="${nav.cssclass}"><a href="${nav.href}">${nav.menuname}</a></li>
				</c:forEach>
			</ul>
			<div class="sub">
				${username} <span class="news"><a href="Message/Index">消息<i class="num">${unReadNum}</i></a></span><span><a href="User/Logout">退出</a></span>
			</div>
			<ul class="menu">
            	<li class=" current"><a href="http://localhost:8080/organ-web/User/Index">首页</a></li>
			</ul>
		</div>
		<img src="content/img/img_banner.png" alt="banner" class="banner">
</div> --%>
<div class="rule_box">
    <div class="crumbs">我的订单 > 订单详情</div>
    <a class="btn_green" style="float: right;width:60px;height: 27px;line-height: 27px;margin-top: -41px;margin-right: 30px;" href="javascript:void(0);" onClick="callBack('${bill}','${billsId}');">返回</a>
    <div class="new_rule_box">
        <div class="new_rule_b">
            <table>
					<tr>
						<td class="t">订单编号</td>
						<td>${orgOrderDetails.orderno}<input id="orderno" name=""
							value="${orgOrderDetails.orderno}" type="hidden" /></td>
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
					 -->
						<td class="t">订单状态</td>
						<td class="font-red">${orgOrderDetails.orderStatusShow}</td>
						<!-- 
                    <c:if test="${orgOrderDetails.orderstatus == '0'}">
                    	<td class="font-red">待接单</td>
		            </c:if>
		            <c:if test="${orgOrderDetails.orderstatus == '1'}">
						<td class="font-red">待人工派单</td>		            
					</c:if>
		            <c:if test="${orgOrderDetails.orderstatus == '2'}">
		            	<td class="font-red">司机未出发</td>
		            </c:if>
		            <c:if test="${orgOrderDetails.orderstatus == '3'}">
		            	<td class="font-red">司机已出发</td>
		            </c:if>
		            <c:if test="${orgOrderDetails.orderstatus == '4'}">
		            	<td class="font-red">司机已抵达</td>
		            </c:if>
		            <c:if test="${orgOrderDetails.orderstatus == '5'}">
		            	<td class="font-red">已接到乘客</td>
		            </c:if>
		            <c:if test="${orgOrderDetails.orderstatus == '6'}">
		            	<td class="font-red">服务中</td>
		            </c:if>
		            <c:if test="${orgOrderDetails.orderstatus == '7'}">
		            	<td class="font-red">行程结束</td>
		            </c:if>
		            <c:if test="${orgOrderDetails.orderstatus == '8'}">
		            	<td class="font-red">订单取消</td>
		            </c:if>
		             -->
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
            <!-- <a class="btn_red" style="float: right;margin-top: -52px;margin-right: 100px;" href=”#” onClick="javascript :history.go(-1);">返回</a> -->
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
	                    <td><span class="bigred">${orgOrderDetails.estimatedcost}</span><span class="font-blue" style="cursor:pointer;">费用说明</span> </td>
	                    <td class="t"></td>
	                    <td></td>
                	</tr>
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '等待服务'}">
	            	<tr>
	                    <td class="t">预估费用</td>
	                    <td><span class="bigred">${orgOrderDetails.estimatedcost}</span><span class="font-blue" style="cursor:pointer;">费用说明</span> </td>
	                    <td class="t"></td>
	                    <td></td>
                	</tr>
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '服务中'}">
	            	<tr>
	                    <td class="t">实时费用</td>
	                    <td><span class="bigred">${orgOrderDetails.orderamount}</span><span class="font-blue" style="cursor:pointer;">费用说明</span> </td>
	                    <td class="t"></td>
	                    <td></td>
                	</tr>
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '未支付' || orgOrderDetails.orderStatusShow == '未结算' || orgOrderDetails.orderStatusShow == '结算中' || orgOrderDetails.orderStatusShow == '已结算'}">	
	            	<tr>
	                    <td class="t">实际费用</td>
	                    <td><span class="bigred">${orgOrderDetails.orderamount}</span><span class="font-blue" style="cursor:pointer;">费用说明</span> </td>
	                    <td class="t"></td>
	                    <td></td>
                	</tr>
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '已支付'}">
	            	<tr>
	                    <td class="t">实际费用</td>
	                    <td><span class="bigred">${orgOrderDetails.orderamount}</span><span class="font-blue" style="cursor:pointer;">费用说明</span> </td>
	                    <td class="t"></td>
	                    <td></td>
                	</tr>
	            </c:if>
	            <c:if test="${orgOrderDetails.orderStatusShow == '已取消'}">
	            	<tr>
	                    <td class="t">预估费用</td>
	                    <td><span class="bigred">${orgOrderDetails.estimatedcost}</span><span class="font-blue" style="cursor:pointer;">费用说明</span> </td>
	                    <td class="t"></td>
	                    <td></td>
                	</tr>
	            </c:if>
            </table>
            <c:if test="${orgOrderDetails.orderStatusShow == '服务中'}">
	             <div class="trail" data-value = "xingcheng" style="cursor:pointer;">查看车辆位置>></div>	
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '未支付' || orgOrderDetails.orderStatusShow == '未结算' || orgOrderDetails.orderStatusShow == '结算中' || orgOrderDetails.orderStatusShow == '已结算'}">
            	 <div class="trail" data-value = "xingcheng" style="cursor:pointer;">查看行程轨迹>></div>
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '已支付'}">
            	 <div class="trail" data-value = "xingcheng" style="cursor:pointer;">查看行程轨迹>></div>
            </c:if>
        </div>
    </div>
    <!--费用说明弹窗-->
	<div class="pop_box feiyon">
	    <div class="pop">
	        <div class="head" style="width:563px;">
			   	 费用说明
			    <img src="content/img/btn_close.png" alt="关闭" class="close" style="cursor:pointer;">
			</div>
		<div class="con_a"  style="height: 362px;">
			<c:if test="${orgOrderDetails.orderStatusShow == '等待接单'}">
               	<span style="margin-left:50px;font-size: 18px;">车费总计 </span><span class="bigred" style="margin-left: 50px;">${orgOrderDetails.estimatedcost}</span>
				<br><br> 
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '等待服务'}">
            	<span style="margin-left:50px;font-size: 18px;">车费总计 </span><span class="bigred" style="margin-left: 50px;">${orgOrderDetails.estimatedcost}</span>
            	<br><br> 
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '服务中'}">
            	<span style="margin-left:50px;font-size: 18px;">车费总计 </span> <span class="bigred" style="margin-left: 50px;">${orderCost.cost}</span>
            	<br><br> 
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '未支付' || orgOrderDetails.orderStatusShow == '未结算' || orgOrderDetails.orderStatusShow == '结算中' || orgOrderDetails.orderStatusShow == '已结算'}">
            	<span style="margin-left:50px;font-size: 18px;">车费总计 </span><span class="bigred" style="margin-left: 50px;">${orderCost.cost}</span>
            	<br><br> 
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '已支付'}">
            	<span style="margin-left:50px;font-size: 18px;">车费总计 </span><span class="bigred" style="margin-left: 50px;">${orderCost.cost}</span>
            	<br><br> 
            </c:if>
            <c:if test="${orgOrderDetails.orderStatusShow == '已取消'}">
            	<span style="margin-left:50px;font-size: 18px;">车费总计 </span><span class="bigred" style="margin-left: 50px;">${orgOrderDetails.estimatedcost}</span>
				<br><br> 
            </c:if>
            <span style="margin-left:100px;width: 180px;display:inline-block;">起步价</span><span style="margin-right:100px;">${orderCost.startprice}</span>
            <br>
			<span style="margin-left:100px;width: 180px;display:inline-block;">里程费（${orderCost.mileage}）</span><span style="margin-right:100px;">${orderCost.rangecost}</span>
			<br>
			<span style="margin-left:100px;width: 180px;display:inline-block;">时长费（${orderCost.times}）</span><span style="margin-right:100px;">${orderCost.timecost}</span>
			<c:if test="${orderCost.deadheadcost != '0.0元' && orderCost.res == 'resY'}">
			<br>
			<span style="margin-left:100px;width: 180px;display:inline-block;">空驶费（${orderCost.deadheadmileage}）</span><span style="margin-right:100px;">${orderCost.deadheadcost}</span>	
			</c:if>
			<c:if test="${orderCost.nightcost != '0.0元' && orderCost.res == 'resY'}">
			<br>
			<span style="margin-left:100px;width: 180px;display:inline-block;">夜间费（${orderCost.mileage}）</span><span style="margin-right:100px;">${orderCost.nightcost}</span>	
			</c:if>
			<%-- <br><br>
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
</div>
<script type="text/javascript" src="js/orgIndex/details.js"></script>
<script type="text/javascript">
var base = document.getElementsByTagName("base")[0].getAttribute("href");
</script>
</body>
</html>
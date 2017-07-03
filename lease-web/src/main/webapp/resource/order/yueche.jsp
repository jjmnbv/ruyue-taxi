<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="好约车">
    <title>约车</title>
    <base href="<%=basePath%>" >
     <%@include file="resource.jsp"%>
     <style type="text/css">
     	.validatecustomstyle label.error {padding-left: 23%}
     	.vehiclessubjectstyle label.error {padding-left: 13%!important}
     	.validateusetimestyle label.error {padding-left: 0%}
     	.tabmenu a{text-decoration:none;}
		.breadcrumb{text-decoration:underline;}
     </style>
</head>
<body style="overflow:hidden;">
<div class="crumbs"><a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > 我要下单 </div>
<div class="content">
		<ul class="tabmenu">
			<li class="on"><a href="Order/yueche">约车</a></li>
			<li><a href="Order/jieji">接机</a></li>
			<li><a href="Order/songji">送机</a></li>
		</ul>
        <form id="form" class="form form-yueche"  method="post">
        <input type="hidden" name="ordertype" id="ordertype" value="1"/>
        	  <div class="row">
        		<div class="col-3 col-3-usetype" id="usetype"><label class="genre label-late">用车类型<em class="asterisk"></em></label>
            		<input type="radio" name="usetype" value="0" style="margin-left:0px;" class="public" checked/>因公用车
<!--             		<input type="radio" name="usetype" value="1" style="margin-left:13px;" class="private"/>因私用车 -->
            	</div>
            	<div class="col-9 col-9-usetype"></div>
        	</div>
            <div class="row row-user" id="user">
                <div class="col-3 col-3-person">
                	<label class="label-person label-late">下单人<em class="asterisk"></em></label>
                	<input type="hidden" value="" id="userid" name="userid" />
                </div>
                <div class="col-1 col-1-btn"><button class="Mbtn blue" type="button" data-owner="userid">选择</button></div>
                <div class="col-3 col-3-person">
                	<label class="label-person label-late">乘车人<em class="asterisk"></em></label>
                	<input type="hidden" value="" id="passengers" name="passengers"/>
                </div>
                <div class="col-1 col-1-btn"><button class="Mbtn blue passengers" type="button" data-owner="passengers">常用</button></div>
                <div class="col-3">
                	<label class="label-width label-late" style="margin-left: -30px;width: 40%;">乘车人电话<em class="asterisk"></em></label>
                	<input type="hidden" value="" id="passengerphone" name="passengerphone" />
                </div>
                <div class="col-1 col-1-btn"><button class="Mbtn blue passengerphone" type="button" data-owner="passengerphone">常用</button></div>
            </div>
            <div class="row row-transport">
                <div class="col-3 validateusetimestyle">
                	<label class="label-transport label-late">用车时间<em class="asterisk"></em></label>

                     <div class="ztimepicker">
						<input type="text" readonly value="" class="ztimepicker_input" name="usetime" id="usetime" style="width:100%;"/>
						<div class="ztimebox">
							<div class="znow">现在用车</div>
							<div class="ztimebox_s">
								<ul class="zday">
								</ul>
								<ul class="zhour">
								</ul>
								<ul class="zmin">
								</ul>
							</div>
						</div>
					</div>
                </div>
                <div class="col-1 col-1-none"></div>
                <div class="col-7 validatecustomstyle col-7-up" style="position:relative;"><label style="width:14%;margin-left: -5px;" class="address label-late">上车地址<em class="asterisk"></em></label>
                	<input type="hidden" id="onCity" name="oncity" style="width:10%"/>
                	<input type="text" placeholder="可输入搜索" style="width:75%;" name="onaddress" id="onAddress" maxLength="200"/>
                	<!-- 前端补充  -->
			  		<div class="dizhibox" style="position:absolute;top:36px;left:24.9%;z-index:1000;border:1px solid #ededed;background:#fff;width:73%;">
				  		<div class="stab">
				  			<div id = "searchResult"class="shen_on">搜索结果</div>
				  			<div>常用地址</div>
				  		</div>
				  		<div class="stabox bresult tangram-suggestion">&nbsp 请输入搜索关键字</div>
			  			<ul class="stabox bhide">
			  				<!-- <li>香菇蓝受</li>
			  				<li>你为什么要吃屎1</li>
			  				<li>你为什么要吃屎</li>
			  				<li>香菇蓝受</li>
			  				<li>你为什么要吃屎</li>
			  				<li>香菇蓝受</li>
			  				<li>你为什么要吃屎</li>
			  				<li>香菇蓝受</li> -->
						</ul>
		  			</div>
		  			<!-- 前端补充结束  -->
                </div>
                <div class="col-1 col-1-btn" ><button class="Mbtn blue onMap" type="button">地图</button></div>
            </div>
            <div class="row">
                <div class="col-7 validatecustomstyle col-7-valid" style="position:relative;"><label style="width:14%;margin-left: -5px;" class="address label-late">下车地址<em class="asterisk"></em></label>
					<input type="hidden" id="offCity" name="offcity" style="width:10%"/>
                	<input type="text" placeholder="可输入搜索" style="width:75%;" name="offaddress" id="offAddress" maxLength="200"/>
                	<!-- 前端补充  -->
                	<div class="dizhibox" style="position:absolute;top:36px;left:24.9%;z-index:1000;border:1px solid #ededed;background:#fff;width:73%;">
				  		<div class="stab">
				  			<div id = "searchResult1" class="shen_on">搜索结果</div>
				  			<div>常用地址</div>
				  		</div>
				  		<div class="stabox bresult tangram-suggestion">&nbsp 请输入搜索关键字</div>
			  			<ul class="stabox bhide">
			  				<!-- <li>香菇蓝受</li>
			  				<li>你为什么要吃屎2</li>
			  				<li>你为什么要吃屎</li>
			  				<li>香菇蓝受</li>
			  				<li>你为什么要吃屎</li>
			  				<li>香菇蓝受</li>
			  				<li>你为什么要吃屎</li>
			  				<li>香菇蓝受</li> -->
						</ul>
		  			</div>
		  			<!-- 前端补充结束  -->
                </div>
                <div class="col-1 col-1-btn" ><button class="Mbtn blue offMap" type="button">地图</button></div>
                <div class="col-3"><label style="width:32%;" class="label-transport label-late">用车事由<em class="asterisk"></em></label>
					<select name="vehiclessubjecttype" id="vehiclessubjecttype" style="width:69%;margin-left:-8px;">
<!-- 							<option value="">请选择</option> -->
<!-- 							<option value="">---------------------------</option> -->
							<c:forEach var="item" items="${reason}">
								<option value="${item.id}">${item.value}</option>
							</c:forEach>
					</select>
				</div>
            </div>
            <div class="row">
                <div class="col-7 vehiclessubjectstyle col-7-cause" id="vehiclessubjectDiv">
                	<label style="width:14%;float:left;margin-left: -5px;" class="address label-late">用车事由说明<em class="asterisk"></em></label>
                	<textarea rows="3" style="width:86%;margin-left:5px;" placeholder="填写用车事由说明" name="vehiclessubject" id="vehiclessubject" maxlength="250"></textarea>
                </div>
                <div class="col-4">
                	<label style="float:left;margin-left: -18px;" class="travel-note label-late">行程备注</label>
                	<textarea rows="3" style="margin-left: 15px;"  placeholder="填写行程备注" name="tripremark"  id="tripremark" maxlength="250" class="tripremark"></textarea>
				</div>
            </div>
            <div class="row"><div class="col-12"><h2>车型信息</h2></div></div>
            <hr/>
           <div class="row">
<!--             	<div class="col-12"> -->
<!--             		<ul id="cartypes"> -->

<!--             		</ul> -->
<!--             	</div> -->
	            <!-- start of 车型 -->
	            <div class="clo-12">
	            	<div class="cantainer">
	            		<div id="car_info">
							<div class="left" style="display:none;">
								<img src="img/order/btn_left_press.png"/>
							</div>
							<div class="center">
								<input type="hidden" value="" id="cartype" name="selectedmodel"/>
								<div class="center_box" id="cartypelist">
	<!-- 								<div class="item"> -->
	<!-- 									<div class="item_title">经济型（5座）</div> -->
	<!-- 									<div class="qibujia"><span>xx</span>起步价</div> -->
	<!-- 									<div class="gongli"><span>xx</span>元/公里</div> -->
	<!-- 									<div class="fenzhong"><span>xx</span>元/分钟</div> -->
	<!-- 									<img class="item_car" src="img/order/bg_putongshangwuche.png" alt="" /> -->
	<!-- 									<div class="item_active"></div> -->
	<!-- 								</div> -->
								</div>
							</div>
							<div class="right" style="display:none;">
								<img src="img/order/btn_right_def.png"/>
							</div>
						</div> 
	            	</div>
	            </div>
	            <!-- end of 车型 -->
            </div>
        	<div class="row"><div class="col-12"><h2>支付方式</h2></div></div>
        	<hr/>
	        <div class="row">
	        		<div class="col-1"></div>
	                <div class="col-3" id="paymethod" style="width:auto;">
	                	<input type="radio" name="paymethod" value="2" checked/>机构支付
<!-- 	            		<input type="radio" name="paymethod" value="1" />个人垫付 -->
	                </div>
	        </div>
            <br/>
            <div class="row"><div class="col-12"><h2>指派司机</h2></div></div>
            <hr />
            <div class="row">
                <div class="col-1"></div>
                <div class="col-3" style="padding: 18.5px 10px" id="driverMode">
                    <input type="radio" name="driverModeType" value="0" checked/>不指派
                    <input type="radio" name="driverModeType" value="1" />人工指派
                </div>
                <div class="col-6 hidden" id="manualSelectDriver">
                    <div class="col-8">
                        <label class="label-late">指派司机<em class="asterisk"></em></label>
                        <input type="hidden" value="" id="manualSelectDriverInput" name="manualSelectDriverInput"/>
                    </div>
                    <div class="col-4">
                        <button class="Mbtn blue" type="button">选择</button>
                    </div>
                </div>
            </div>
	        <br/><br/>
	        <div class="row">
	        	<div class="col-1" style="margin-right:2%;width:auto;"><h2>预估费用</h2></div>
	        	<div class="col-3"><h2>¥<span id="estimatedcost">0.0元</span></h2></div>
	        	<div class="col-4" style="width:auto;">
	        		<h2>预估里程<span id="estimatedmileage">0公里</span>,预估时长<span id="estimatedtime">0分钟</span></h2>
	        	</div>
	        </div>
<!-- 	        <div class="row" style="display:none;"  id= "payable"><font style="color:red;">机构账户已超出限额；建议使用个人垫付，否则无法叫车</font></div> -->
	        <hr/><br/>
	        <div class="row">
		        	<div class="col-12"  align="center"><button class="Lbtn red callCar" style="width:30%;" type="button" id="sub">开始叫车</button></div>
		    </div>
        </form>
</div>
<%@include file="window.jsp"%>
<input type="hidden" value="0" id="orderno"/>
<script src="js/order/index.js"></script>
<script src="js/order/common.js"></script>
<script src="js/order/baidu.js" type="text/javascript"></script>
</body>
</html>
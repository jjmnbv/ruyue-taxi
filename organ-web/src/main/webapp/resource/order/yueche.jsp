<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.szyciov.util.SystemConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String page_Title_value = SystemConfig.getSystemProperty("page_Title_value");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <title><%=page_Title_value%></title>
    <base href="<%=basePath%>" >
    <%@include file="resource.jsp"%>
</head>
<body >
<input type="hidden" id="orderno" value="${orderno}"/>
<input type="hidden" id="notpay" value="${user.notpay}"/>
<input type="hidden" id="hasrule" value="${user.hasrule}"/>
<input type="hidden" value="1" id="ordertype" name="ordertype"/>
<%@include file="../head.jsp"%>
<!--内容区域开始-->
<form id="form">
    <div class="con_body">
        <div class="content_box">
            <div class="left_logo_yueche"></div>
            <div class="top"></div>
            <div class="middle">
                <!--1-->
                <div class="box_2 block">
                    <div class="title">服务车企</div>
                    <div class="select_box" id="lease">
                        <input  class="select_val" data-value="" value="" placeholder="请选择"  name="companyid" id="companyId">
                        <ul class="select_content">
                        </ul>
                    </div>
                    <div class="seq_box">
                        <img src="content/img/icon_yuan.png"/>
                        <span class="seq">1</span>
                    </div>
                </div>
                <!--2-->
                <div class="box_2 block">
                    <div class="title">乘车人</div>
                    <div class="title_1">
                        <span class="title_active">*</span>
                        <span class="title_info">姓名</span>
                    </div>
                    <div class="select_box mar select_box_1" id="passengerDiv">
                        <input  class="select_val" data-value="" value="" id="passengers" placeholder="请选择或输入乘车人" name="passengers"/>
                        <ul class="select_con">
                        </ul>
                    </div>
                    <div class="title_1">
                        <span class="title_active">*</span>
                        <span class="title_info">手机号码</span>
                    </div>
                    <div class="input_box mar">
                        <input  class="inp" data-value="" value="" id="passengerPhone" placeholder="请选择或输入手机号码" name="passengerphone">
                    </div>
                    <div class="seq_box">
                        <img src="content/img/icon_yuan.png"/>
                        <span class="seq">2</span>
                    </div>
                    <div class="title_1">
                        <label for="checkbox">
<!-- 						<span class="checkbox_ie"></span> -->
<!-- 						<input type="checkbox" id="addFavUser" class="check_op" style="opacity:10;"/> -->
<!-- 						<span>添加为常用联系人</span> -->
                        </label>
                    </div>
                </div>
                <!--3-->
                <div class="box_2 block">
                    <em class="ditu_1"></em>
                    <em class="ditu_2"></em>
                    <div class="title">用车信息</div>
                    <div class="title_1">
                        <span class="title_active">*</span>
                        <span class="title_info">用车时间</span>
                    </div>
                    <div class="select_box mar select_box_1">
                        <div class="ztimepicker">
                            <input type="text" style="height: 40px; line-height: 40px;"  readonly value="" class="ztimepicker_input" name="usetime" id="useTime"/>
                            <div class="ztimebox" style="left: -11px; top: 40px;">
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
                    <div class="title_1">
                        <span class="title_active">*</span>
                        <span class="title_info">上车地点</span>
                    </div>
                    <div style="    width: 76px; float: left;top: 11px;margin-top: 19px;border-radius: 3px;">
                        <input type="hidden" id="onCity" name="oncity" style="width:72px;"/>
                    </div>
                    <div class="select_box mar wei2 select_box_1" style="top: 11px;padding-left: 0;">
                        <input  class="select_val" data-value="" value="" id="onAddress" style="height: 100%;display: block;position: initial;top: 13px;text-indent: 10px;" name="onaddress" placeholder="请选择或输入">
                        <ul class="select_con select_con_1">

                        </ul>
                    </div>
                    <div class="title_1" style="margin-top: 24px;">
                        <span class="title_active">*</span>
                        <span class="title_info">下车地点</span>
                    </div>
                    <div style="    width: 76px; float: left;top: 11px;margin-top: 19px;border-radius: 3px;" >
                        <input type="hidden" id="offCity" name="offcity" style="width:72px;"/>
                    </div>
                    <div class="select_box mar wei2 select_box_1" style="top: 11px;padding-left: 0;">
                        <input  class="select_val" data-value="" value="" style="height: 100%;display: block;position: initial;top: 13px;text-indent: 10px;" id="offAddress" name="offaddress" placeholder="请选择或输入">
                        <ul class="select_con select_con_1">

                        </ul>
                    </div>
                    <div class="seq_box">
                        <img src="content/img/icon_yuan.png"/>
                        <span class="seq">3</span>
                    </div>
                </div>
                <!--4-->
                <div class="box_2 block">
                    <div class="title">用车事由</div>
                    <div class="title_1">
                        <span class="title_active">*</span>
                        <span class="title_info">事由</span>
                    </div>
                    <div class="select_box mar">
                        <input  class="select_val" data-value="" value="" id="vehiclessubjectType" name="vehiclessubjecttype" placeholder="请选择">
                        <ul class="select_content">
                        </ul>
                    </div>
                    <div class="textarea_box mar">
                        <textarea class="textarea" placeholder="用车事由说明" id="vehiclesSubject" name="vehiclessubject" maxlength="250"></textarea>
                    </div>
                    <div class="seq_box">
                        <img src="content/img/icon_yuan.png"/>
                        <span class="seq">4</span>
                    </div>
                </div>
                <!--5-->
                <div class="box_2 block">
                    <div class="title">行程备注</div>
                    <div class="textarea_box mar">
                        <textarea class="textarea" placeholder="填写行程内容" id="tripRemark" name="tripremark" maxlength="250"></textarea>
                    </div>
                    <div class="seq_box">
                        <img src="content/img/icon_yuan.png"/>
                        <span class="seq">5</span>
                    </div>
                </div>
                <!--6-->
                <div class="box_2 block">
                    <div class="title">车辆信息</div>
                    <div class="car_box title_1">
                        <!--主要内容开始-->
                        <div id="car_info">
                            <div class="left" style="display: none;">
                                <img src="content/img/btn_zuo.png"/>
                            </div>
                            <div class="center">
                                <input type="hidden" id="carType" name="cartype"/>
                                <div class="center_box" id="carTypeList">
                                    <!-- 								<div class="item"> -->
                                    <!-- 									<div class="item_title">经济型（5座）</div> -->
                                    <!-- 									<div class="qibujia"><span class="price_car">xx</span>起步价</div> -->
                                    <!-- 									<div class="gongli"><span>xx</span>元/公里</div> -->
                                    <!-- 									<div class="fenzhong"><span>xx</span>元/分钟</div> -->
                                    <!-- 									<img class="item_car" src="content/img/bg_shushixing.png" alt="" /> -->
                                    <!-- 									<div class="item_active"></div> -->
                                    <!-- 								</div> -->
                                </div>
                            </div>
                            <div class="right" style="display: none;">
                                <img src="content/img/btn_you.png"/>
                            </div>
                        </div>
                        <!--主要内容结束-->
                    </div>
                    <div class="seq_box">
                        <img src="content/img/icon_yuan.png"/>
                        <span class="seq">6</span>
                    </div>
                </div>
                <!--7-->
                <div class="box_2 block">
                    <div class="title">支付方式</div>
                    <div class="title_1" id="paymethod">
                        <label for="ra1">
                            <!-- 						<span class="radio_ie ra_span"></span> -->
                            <input type="radio" name="paymethod" id="ra1" class="ra" value="2" style="opacity:10;" checked/>机构支付
                        </label>
<!--                         <label for="ra2" class="ra2"> -->
                            <!-- 						<span class="radio_ie ra_span"></span> -->
<!--                             <input type="radio" name="paymethod" id="ra2" class="ra" value="1" style="opacity:10;"/>个人垫付 -->
<!--                         </label> -->
                    </div>
                    <div class="seq_box">
                        <img src="content/img/icon_yuan.png"/>
                        <span class="seq">7</span>
                    </div>
                </div>
                <!--结束-->
                <div class="block">
                    <div class="">
                        <span class="yugu">预估费用</span>
                        <span class="price">￥0.0</span>
                        <span class="price_info">预估里程0公里,预估时长0分钟</span>
                    </div>
                    <div class=""><span style="color:red;">(*预约用车会增加附加费用30元，本公司保留最终解释权)</span></div>
<!--                     <div class="mar m" style="display:none;"  id="payable"> -->
<!--                         企业账户已超出限额;建议使用个人垫付,否则无法叫车 -->
<!--                     </div> -->
                    <div class="seq_box">
                        <img src="content/img/icon_yqg.png"/>
                        <!-- 					<span class="seq seq_gou"></span> -->
                    </div>
                </div>
                <!--开始叫车按钮-->
                <div class="block">
                    <div class="start_btn">开始叫车</div>
                </div>
            </div>
            <div class="bottom"></div>
        </div>
    </div>
</form>
<!--内容区域结束-->
<!--弹窗开始-->
<%@include file="window.jsp"%>
<script type="text/javascript" src="js/order/baidu.js"></script>
<script type="text/javascript" src="js/order/common.js"></script>


<!--弹窗结束-->
<%@include file="../foot.jsp"%>
</body>
</html>

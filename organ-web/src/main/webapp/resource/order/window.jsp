<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.szyciov.util.SystemConfig"%>
<%
String ak = SystemConfig.getSystemProperty("yingyan_ak");
%>
<div class="popup_box" id="window">
	<!--提示弹窗-->
    <div class="popup popup_hint">
		<div class="popup_title">
			<span>提示</span>
			<i class="close"></i>
		</div>
		<div class="popup_content">
			您有订单未支付，现在不能下单，请完成支付后再进行下单。
		</div>
		<div class="popup_footer">
			<span class="cancel">取消</span>
			<span class="sure">确定</span>
		</div>
	</div>
	<div class="popup popup_noaddress">
		<div class="popup_title">
			<span>提示</span>
			<i class="close"></i>
		</div>
		<div class="popup_content">
			请选择地址
		</div>
		<div class="popup_footer">
			<span class="sure">我知道了</span>
		</div>
	</div>
	<!--地图弹窗-->
    <div class="popup popup_ditu" data-owner="onCty" id="map">
		<div class="popup_title">
			<span>提示</span>
			<i class="close"></i>
		</div>
		<div class="popup_content">
			<!--百度地图容器 -->
 			<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=<%=ak %>&s=1"></script>
			<label style="width: 10%;margin-left: 10px;">搜索地址</label>
			<input type="text" style="width: 90%;height:30px;border: 1px solid #dbdbdb;margin-left: 10px;padding-left:5px;border-radius:5px;" placeholder="详细地址" name="keyword" id="suggest"/>
 	  		<div style="width:100%;height:300px;border:#ccc solid 1px;margin-top:10px" id="dituContent"></div>
 	  		<div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
		</div>
		<div class="popup_footer">
			<span class="cancel">取消</span>
			<span class="sure">确定</span>
		</div>
	</div>
</div>
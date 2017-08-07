<%@page import="com.szyciov.util.SystemConfig"%>
<%@ page contentType="text/html; charset=UTF-8"%>
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
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>行程</title>
		<base href="<%=basePath%>" >
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript"  src="js/itinerarySharing/selfcommon.js"></script>
	</head>
	<body style="margin:0px;padding:0px;">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<!-- 产品名称 -->
		<div id="log" style="position:absolute;top:0px;right:1px;left:1px;height:50px;background-color:#eaeaea;z-index:9999;">
			<img src="img/itinerarySharing/icon_logo3@2x.png" style="width:100px;height:35px;margin-top:6px;margin-left:11px;" />
		</div>
		<div id="404info" style="position:absolute;top:0px;bottom:0px;left:0px;right:0px;display:none;background-color:#f0f0f0;">
			<div style="position:relative;width:200px;height:210px;margin:auto;margin-top:100px;">
				<img src="img/itinerarySharing/404.png" style="margin:15px;padding:0px;width:170px;height:170px;">
				<span style="position:absolute;left:0px;right:0px;bottom:0px;text-align:center;">您来晚了，分享已经结束</span>
			</div>
		</div>
		<div id="mapinfo" style="display:none;">
			<!-- 司机信息 -->
			<div id="driverinfo" style="position:relative;height:80px;background-color:rgba(255, 253, 253, 0.92);top:52px;z-index:9999;">
				<div style="position:absolute;top:0px;left:0px;bottom:0px;width:85px;">
					<img id="driverimg" style="width:80px;height:80px;" />
				</div>
				<div style="position:absolute;top:0px;left:85px;bottom:0px;right:0px;">
					<div style="height:30px;padding-top:10px;">
						<span id="drivername" style="margin-left:10px;height:30px;line-height:30px;"></span>
					</div>
					<div style="height:40px;">
						<span id="carinfo" style="margin-left:10px;height:30px;line-height:30px;"></span>
					</div>
				</div>
			</div>
			
			<div id="addressinfo" style="font-size:15px;color:#949494;position:absolute;top:138px;right:10px;left:10px;height:60px;background-color:white;border-radius:5px;z-index:9999;">
				<div style="margin-left:5px;margin-right:5px;height:30px;">
					<div style="float:left;width:60%;height:100%;">
						<img style="float:left;width:30px;height:30px;" src="img/itinerarySharing/icon_shangche.png"/><span id="onaddress" style="float:left;width:60%;height:100%;line-height:30px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap"></span>
					</div>
					<span id="onaddresstime" style="text-align:right;float:left;width:40%;height:100%;line-height: 30px;">
						
					</span>
				</div>
				<div style="margin-left:5px;margin-right:5px;height:30px;">
					<div style="float:left;width:60%;height:100%;">
						<img style="float:left;width:30px;height:30px;" src="img/itinerarySharing/icon_xiache.png"/><span id="offaddress" style="float:left;width:60%;height:100%;line-height:30px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap"></span>
					</div>
					<span id="offaddresstime" style="text-align:right;float:left;width:40%;height:100%;line-height:30px;">
						
					</span>
				</div>
			</div>
			
			<div id="map_canvas" style="border:#ccc solid 1px;position:absolute;top:0px;bottom:0px;left:0px;right:0px;"></div>
		</div>
		<script type="text/javascript">
			var orderObj = {
				orderno: "<%=request.getParameter("orderno")%>"
			};
		</script>
		<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>&s=1"></script>
		<script type="text/javascript" src="js/itinerarySharing/index.js"></script>
	</body>
</html>

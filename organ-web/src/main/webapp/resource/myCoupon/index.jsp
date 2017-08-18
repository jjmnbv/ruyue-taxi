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
<title>用车规则页</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="content/css/voucher.css">
<link rel="stylesheet" type="text/css" href="content/css/common.css" />
<link rel="stylesheet" type="text/css" href="content/css/zstyle.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<style type="text/css">

</style>
</head>
<body>
<div class="content">
	<div class="con_box  rule">
		<c:if test="${results == 'yes'}">
			<ul class="voucher_con">
				<c:forEach items="${myCouponDTO}" var="myCouponDTO" >  
		            <li class="li_red">
						<strong>${myCouponDTO.name}</strong><span>(${myCouponDTO.lecompanyname})</span>
						<div class="time">${myCouponDTO.outimestart}至${myCouponDTO.outtimeend}</div>
						<div class="v_bottom_l">${myCouponDTO.usetypename}</div>
						<div class="v_price">￥<span>${myCouponDTO.money}</span></div>
						<div class="v_bottom_r">${myCouponDTO.servicetype}</div>
					</li>
		        </c:forEach>  
			</ul>
		</c:if>
		<!--无可用劵-->
		<c:if test="${results == 'no'}">
			<div class="v_none">
				<img src="content/img/icon_wsj.png" alt="无可用券">暂无可用抵用券
			</div>
		</c:if>
	</div>
</div>
</body>
</html>

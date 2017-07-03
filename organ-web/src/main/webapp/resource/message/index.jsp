<%@ page contentType="text/html; charset=UTF-8" import="com.szyciov.util.SystemConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String page_CopyrightDescription_value = SystemConfig.getSystemProperty("page_CopyrightDescription_value");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <title>消息中心</title>
  <base href="<%=basePath%>">
  <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/zstyle.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/xiaoxizhongxin.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/index.css"/>
  <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
  <link rel="stylesheet" type="text/css" href="content/css/shouyelogoutdialog.css" />
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
  <script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
  <script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.min.js"></script>
  <script type="text/javascript" src="content/js/common.js"></script>
  <script type="text/javascript" src="js/basecommon.js"></script>
</head>
<style>
    .info_box {
         display: table;
         cellspacing: 10px;
         width: 1100px;
         margin: 0px auto;
    }
    .dataTables_wrapper{text-align:left!important;}
    .dataTables_empty{text-align: center;}
    label{width: auto;margin-right: 0px;}
</style>
<body>
<div class="header">
		<div class="header_box">
			<img src="content/img/logo.png" class="header_logo"/>
			<ul class="menu">
				<c:forEach items="${navinfo}" var="nav" varStatus="status">
					<li class="${nav.cssclass}"><a href="${nav.href}">${nav.menuname}</a></li>
				</c:forEach>
			</ul>
			<div class="sub">
				<span onclick="showInfo();">${username}</span> <span class="news"><a href="Message/Index">消息<i id="num">${unReadNum}</i></a></span><span><a href="User/Logout">退出</a></span>
			</div>
		</div>
		<img src="content/img/img_banner.png" alt="banner" class="banner">
		<input name="unReadNum" id="unReadNum" value="${unReadNum}" type="hidden">
</div>
<!--内容开始-->
<div class="info_box">
	<table id="example" ></table>
</div>
<!--内容结束-->
<!--底部-->
<div class="footer"><%=page_CopyrightDescription_value%></div>

<script type="text/javascript" src="js/message/index.js"></script>
<!--弹窗开始-->
	<div class="popup_box" style="z-index:2;" id="userinfo">
	   <div class="batch_change popup" style="height:327px;">
				<div class="popup_title">
					<span>个人资料</span>
					<i class="close"></i>
				</div>
				<div class="popup_content" style="height:200px;">
					<table style="width:100%;margin:0 48px;">
						<tr>
							<td>姓名&nbsp;&nbsp;&nbsp;<label id="nickname_i"></label></td>
							<td>手机号码&nbsp;&nbsp;&nbsp;<label id="account_i"></label></td>
						</tr>
						<tr>
							<td>性别&nbsp;&nbsp;&nbsp;<label id="sex_i"></label></td>
							<td>角色&nbsp;&nbsp;&nbsp;<label id="role_i"></label></td>
						</tr>
						<tr>
							<td style="vertical-align:baseline;"><label>所属部门</label>&nbsp;&nbsp;&nbsp;<label id="dept_i"></label></td>
							<td><label style="vertical-align:top;">头像</label>&nbsp;&nbsp;&nbsp;<img id="img_i" style="width:80px;height:80px;" src="www.sdcs" /></td>
						</tr>
					</table>
				</div>
				<div class="popup_footer">
					<span class="sure close">确定</span>
				</div>
		</div>
	</div>
</body>
</html>

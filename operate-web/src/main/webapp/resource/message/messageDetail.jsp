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
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>消息详情</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="css/message/xiaoxizhongxin.css" />

		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
	</head>
	
	<style type="text/css">
	.info_content .info_con {background-color: white; }
	.breadcrumb{text-decoration:underline;}
	.info_content {padding: 0px 0px;}
	.crumbs .back{
		position: relative;
		top:2px;
	}
	</style>

	<body>
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="Message/Index">消息中心</a> > 消息详情
            <button class="SSbtn blue back" onclick="back()">返回</button>
		</div>
		<div class="info_content">
		    <div class="info_con">
		        <div class="title">
			        <span class="span_1"><b>${orgUserNews.title}</b></span>
			        <span class="span_2">${createTime}</span>
		        </div>
		        <div class="inner">
			        ${orgUserNews.content}
		        </div>
	        </div>
		</div>

		<script type="text/javascript" src="js/message/messageDetail.js"></script>
	</body>
</html>

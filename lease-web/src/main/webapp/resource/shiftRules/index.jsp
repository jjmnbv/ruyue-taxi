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
		<title>交接班规则</title>
		<base href="<%=basePath%>" >
		<%@include file="resource.jsp"%>
	</head>
	
	<style type="text/css">
		/* 前端对于类似页面的补充 */
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		/* 固定列样式调整  */
		th, td { white-space: nowrap; }
        div.dataTables_wrapper {
            width: $(window).width();
            margin: 0 auto;
        }
        .DTFC_ScrollWrapper{
			margin-top:-20px;
		}
	</style>
	
	<body>
		<div class="crumbs">
		   <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 交接班规则
		   <button class="SSbtn green_a back" onclick="add()">新增</button>
		</div>
		<div class="content">
			<div class="row form" style="padding-top: 30px;">
				<div class="col-4">
					<label class="col-1"  style="width:80px;">城市名称</label><input class="col-3" id="city" name="city" type="text" placeholder="全部">
				</div>

				<div class="col-1" style="text-align: left;width:15%;position:relative;bottom:2px;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
				</div>
	
			</div>
			<div class="row">
				<div class="col-12" style="margin-top: 15px;"><h4>交接班规则信息</h4></div>
		    </div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		<%@include file="window.jsp"%>
		
		<script type="text/javascript" src="js/shiftRules/index.js"></script>
		<script type="text/javascript" src="js/shiftRules/window.js"></script>
	</body>
</html>

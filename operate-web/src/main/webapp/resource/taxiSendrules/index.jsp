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
		<title>派单规则设置</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/css/laterchange.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>				
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
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
		select{
			width: 68%!important;
		}
		td button{margin-left: 4px;}
	</style>
	<body>
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 出租车派单规则
			<button class="SSbtn blue back" onclick="add()" id="addBtn">新增</button>
		</div>
		<div class="content">
			<div class="row form form-send" style="padding-top: 20px;">
				<div class="col-3">
					<label class="col-1 col-1-city">所属城市</label><input class="col-3" id="city" name="city" type="text" placeholder="全部">
				</div>
				<div class="col-3">
					<label>用车类型</label>
					<select id="usecartype">
						<option value="">全部</option>
						<option value="1">即刻用车</option>
						<option value="0">预约用车</option>
					</select>
				</div>
				<div class="col-3">
					<label>派单方式</label>
					<select id="sendtype">
						<option value="">全部</option>
						<option value="0">强派</option>
						<option value="1">抢派</option>
						<option value="2">抢单</option>
					</select>
				</div>
				<div class="col-3">
					<label>派单模式</label>
					<select id="sendmodel">
						<option value="">全部</option>
						<option value="0">系统</option>
						<option value="1">系统+人工</option>
					</select>
				</div>

				<div class="col-12" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey_b" onclick="initSearch();">清空</button>
				</div>
				
				<input type="hidden" id="usertype" value="${usertype}">
			</div>
			<div class="row">
				<div class="col-12" style="margin-top: 15px;"><h4>派单规则信息</h4></div>
		    </div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>

		<script type="text/javascript" src="js/taxiSendrules/index.js"></script>
	</body>
</html>

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
		<title>协议条款</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
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
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<style type="text/css">
			.form select{width:68%;}
			.dataTables_wrapper{margin: 0px 0px 10px 0px;}
			.breadcrumb{text-decoration:underline;}
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.DTFC_ScrollWrapper{
				margin-top:-20px;
			}
			/*样式修改*/
			.table-bordered thead tr th{ 
		        border-bottom-width: 1px; 
		    }
		    .crumbs .back{
		    	position: relative;
		    	top:2px;
		    }
		</style>
	</head>
	
	<body>
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > 合作协议管理
			<button class="SSbtn blue back" onclick="add()" id="addBtn">新增</button>
		</div>
		<div class="content">
			<div class="row form" style="padding-top: 30px;">
				<div class="col-4">
					<label>车企名称</label>
					<input id="queryCompanyname" name="queryCompanyname" type="text" placeholder="请输入">
				</div>
				<div class="col-4">
					<label>业务类型</label>
					<select id="queryServicetype" name="queryServicetype">
						<option value="">全部</option>
						<option value="0">网约车</option>
						<option value="1">出租车</option>
					</select>
				</div>
				<div class="col-4" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey_w" onclick="emptys();">清空</button>
				</div>
			</div>
			<div class="row">
			   <div class="col-10" style="margin-top: 15px;"><h4>加盟车企协议信息</h3></div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		<script type="text/javascript" src="js/pubCooagreement/index.js"></script>
	</body>
</html>

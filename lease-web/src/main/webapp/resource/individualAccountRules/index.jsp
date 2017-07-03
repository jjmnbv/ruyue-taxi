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
		<title>个性化计费规则</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
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
		
	</head>
	
	<style type="text/css">
	    /* 前端对于类似页面的补充 */
		.form select{width:68%;}
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
		tbody tr td:first-child{
			text-align:left;
		}
	</style>
	
	<body>
		<div class="crumbs">
		   <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 客户计费规则
		   <button class="SSbtn blue back" onclick="add()">新增</button>		   
		</div>
		<div class="content">
			<div class="row form" style="padding-top: 30px;">
				<div class="col-4">
					<label>机构名称</label>
					<select id="organId" name="organId">
						<option value="" selected="selected">全部</option>
						<c:forEach var="orgOrgan" items="${orgOrgan}">
							<option value="${orgOrgan.id}">${orgOrgan.shortName}</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="col-4">
					<label>有效期限</label>					
	            	<input style="width:30%;" id="startTime" name="startTime" type="text" placeholder="起始时间" value="" class="searchDate" readonly="readonly">至
	            	<input style="width:30%;" id="endTime" name="endTime" type="text" placeholder="结束时间" value="" class="searchDate" readonly="readonly">
				</div>
				
				<div class="col-4">
					<label>状态</label>
					<select id="ruleState" name="ruleState">
						<option value="" selected="selected">全部</option>
						<option value="0">草稿</option>
						<option value="1">启用</option>
						<option value="2">禁用</option>
						<option value="3">已过期</option>
					</select>
				</div>

				<div class="col-12" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
				</div>
				
				<div class="col-12" style="margin-top: 15px;"><h4>客户计费规则信息</h4></div>
			</div>
			
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		
		<script type="text/javascript" src="js/individualAccountRules/index.js"></script>
	</body>
</html>

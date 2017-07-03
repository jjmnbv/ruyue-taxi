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
		<title>查看账单详情</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
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
	</head>
	
	<style type="text/css">
	   /* 前端对于类似页面的补充 */
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
		<input id="id" name="id" value="${orgOrganBill.id}" type="hidden">
		<div class="crumbs">
		     <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="OrganBill/Index">机构账单</a> > 账单详情
		     <button class="SSbtn blue back" onclick="window.history.back()">返回</button>		     
		</div>
		
		<input id="billName" name="billName" value="${orgOrganBill.name}" type="hidden">
		<input id="shortName" name="shortName" value="${orgOrganBill.shortName}" type="hidden">
		<input id="remark" name="remark" value="${orgOrganBill.remark}" type="hidden">
		<input id="money" name="money" value="${orgOrganBill.money}" type="hidden">

		<div class="content">
			<div class="row" style="padding-top: 30px;">
				<h4><b><label style="margin-left: 10px;">账单名称:</label><label>${orgOrganBill.name}(${orgOrganBill.shortName})</label></b></h4>
				<label style="margin-left: 10px;">账单编号:</label><label>${orgOrganBill.id}</label><br>
				<label style="margin-left: 10px;">账单备注:</label><label>${orgOrganBill.remark}</label><br>
				<label style="margin-left: 10px;">账单金额:</label><label>￥${orgOrganBill.money}</label>
			</div>
			<div class="row">
			   <div class="col-10" style="margin-top: 15px;width:60%;"><h4>对应订单信息</h4></div>
			   <div class="col-2" style="text-align: right;width:40%;">
			       <button class="Mbtn blue" onclick="exportExcel()">导出数据</button>
			   </div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>

		<script type="text/javascript" src="js/organBill/billDetail.js"></script>
	</body>
</html>

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
		<title>重新生成账单</title>
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
		.tip_box_c .row {
            margin-right: -5px;
            margin-left: -5px;
        }
        .tip_box_c .close {float: right;margin-top: -23px;margin-right: 10px;}
	</style>
	
	<body>
	    <input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<input id="id" name="id" value="${orgOrganBill.id}" type="hidden">
		<input id="organId" name="organId" value="${orgOrganBill.organId}" type="hidden">
		<input id="source" name="source" value="${orgOrganBill.source}" type="hidden">
		<div class="crumbs">
		     <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="OrganBill/Index">机构账单</a> > 重新生成账单
		     <button class="SSbtn blue back" onclick="window.history.back()">返回</button>		     
		</div>

		<div class="content">
			<div class="row" style="padding-top: 30px;">
				<form id="formname">
				<!-- <h4 class="col-10"><label>账单名称:</label><label>${orgOrganBill.name}(${orgOrganBill.shortName})</label></h4> -->
					<h4 class="col-10" style="width: 80%;"><b><label>账单名称<em class="asterisk"></em></label><input name="name" id="name" type="text" maxlength="20" value="${orgOrganBill.name}"><label>(${orgOrganBill.shortName})</label></b></h4>
					<div class="col-1" style="text-align: right;width: 8%;">
					     <button type="button" class="Mbtn green_a" onclick="searchPreview();">预览</button>
					</div>
					<div class="col-1" style="text-align: right;width: 11%;">
					     <button type="button" class="Mbtn green_a" onclick="save();">生成账单</button>
					</div>
					<div class="col-12">
					     <label style="margin-right: 10px;">备注信息</label><input name="remark" id="remark" type="text" maxlength="30" placeholder="最多30个字" style="width: 80%;margin-right: 10px;">
					</div>
                </form>
			</div>
			
			<div id="searchDiv">
	                <label class="col-9" style="margin-top: 15px;margin-left: -15px;">共选了<span id="checkNum"></span>个订单</label>
	                <label class="col-3" style="text-align: right;margin-top: 15px;">账单金额：￥<span id="checkAmount"></span></label>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
			</div>
		</div>
		
		<div class="pop_box" id="previewFormDiv" style="display: none;">
			<div class="tip_box_c" style="overflow-x:hidden;">
	            <h3><b><label>账单名称:</label><label><span id="billName"></span>(${orgOrganBill.shortName})</label></b></h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="row">
				        <label style="margin-left: 5px;">账单备注:<span id="billRemark"></span></label><br>
				        <label style="margin-left: 5px;">账单金额:￥<span id="billMoney"></span></label><br>
	                <table id="dataGridPreview" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
	            </div>
	        </div>
		</div>

		<script type="text/javascript" src="js/organBill/reCreateBill.js"></script>
	</body>
</html>

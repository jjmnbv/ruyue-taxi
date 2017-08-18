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
    <title>抵用券明细</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/zstyle.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
    <script type="text/javascript" src="content/js/jquery.js"></script>
    <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
    <script type="text/javascript" src="content/js/common.js"></script>
    <script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
    <script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
    <script type="text/javascript" src="js/basecommon.js"></script>
    <script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
    <script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
</head>
<style>
    td{text-align: center;}
    .font-green{color:#37c137;}
</style>
<body>

<div class="rule_box transaction">
    <div class="crumbs">账户管理 > 抵用券明细
    <button class="btn_green back" style="float: right;width:60px;height: 27px;line-height: 27px;margin-top: -4px;margin-right: 30px;" onclick="back()">返回</button> </div>
    <input id="leasesCompanyId" name="leasesCompanyId" value="${leasesCompanyId}" type="hidden">
    <input id="organId" name="organId" value="${organId}" type="hidden">
    <form id="searchForm">
    <label>时间</label>
    <div class="datebox" style="width: 20%;">
        <input type="text" id="startTime" name="startTime" placeholder="开始时间" class="date" readonly="readonly"><input type="text" id="endTime" name="endTime" placeholder="结束时间" class="date" style="padding-left: 10px;" readonly="readonly">
    </div>
   	<label>类型</label>
	<div class="select_box" style="height: 40px;line-height: 38px;width: 20%;;margin-left: -4px;">
		<input placeholder="全部" class="select_val" data-value="" value="" id="type" disabled="disabled">
		<ul class="select_content">
			<li data-value="">全部</li>
			<li data-value="1">充值返劵</li>
			<li data-value="2">账单结算扣款</li>
			<li data-value="3">注册返劵</li>
			<li data-value="4">活动返劵</li>
			<li data-value="5">违约清零</li>
		</ul>
	</div>
    <button type="button" class="btn_red" onclick="search();">查询</button>
    <button type="button" class="btn_grey" onclick="cancels();">清空</button>
    </form>
    <table id="dataGrid" ></table>
</div>

<script type="text/javascript" src="js/financialManagement/couponDetail.js"></script>
</body>
</html>

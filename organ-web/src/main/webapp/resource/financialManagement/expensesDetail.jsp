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
    <title>交易明细</title>
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
    <div class="crumbs">账户管理 > 交易明细
    <button class="btn_green back" style="float: right;width:60px;height: 27px;line-height: 27px;margin-top: -4px;margin-right: 30px;" onclick="back()">返回</button> </div>
    <input id="leasesCompanyId" name="leasesCompanyId" value="${leasesCompanyId}" type="hidden">
    <input id="organId" name="organId" value="${organId}" type="hidden">
    <form id="searchForm">
    <label>时间</label>
    <div class="datebox">
        <input type="text" id="startTime" name="startTime" placeholder="" class="date" readonly="readonly"><input type="text" id="endTime" name="endTime" placeholder="" class="date" style="padding-left: 10px;" readonly="readonly">
    </div>
    <input type="checkbox" id="type" name="type" value="0" checked>充值
    <input type="checkbox" id="type" name="type" value="2" checked>结算
    <input type="checkbox" id="type" name="type" value="1" checked>提现
    <button type="button" class="btn_red" onclick="search();">查询</button>
    </form>
    <table id="dataGrid" ></table>
</div>

<script type="text/javascript" src="js/financialManagement/expensesDetail.js"></script>
</body>
</html>

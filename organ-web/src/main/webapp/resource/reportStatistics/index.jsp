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
  <title>首页—超级管理员</title>
  <base href="<%=basePath%>">
  <link rel="stylesheet" type="text/css" href="content/css/common.css"/>
  <link rel="stylesheet" type="text/css" href="content/css/zstyle.css"/>
  <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
  <link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
  <script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
  <script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
  <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
  <script type="text/javascript" src="content/js/common.js"></script>
  <script type="text/javascript" src="js/basecommon.js"></script>
</head>
<style>
.datebox{margin-bottom: 0px;}
.h3class{padding: 0px 0px 10px 0px!important;}
body{height:950px;}
</style>
<body>
<div class="con_box  rule">
  	<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
    <h3 class="h3class">机构用车统计 <span class="daochu" onclick="exportExcal()" style=" cursor:pointer">批量导出</span></h3>
     <label>用车时间</label>
    <div class="datebox">
        <input type="text" id="starttime" class="date" readonly><input type="text" id="endtime" class="date" style="padding-left: 10px;" readonly>
    </div>
    <div class="select_box">
        <input id="ordertypeOne" placeholder="全部用车" class="select_val" data-value="" value=""  readonly>
        <ul class="select_content">
            <c:forEach var="ordertype" items="${ordertype}">
				<li data-value="${ordertype.value}">${ordertype.text}</option>
			</c:forEach>
        </ul>
    </div>
    <button class="btn_red" id="search" onclick="search()">查询</button>
    <button class="btn_grey" id="reset" onclick="reset()">清空</button>
    <table id="dataGrid" ></table>

    <h3 style="margin-top: 40px;">部门用车统计 <span class="daochu" onclick="exportExcal1()"style=" cursor:pointer">批量导出</span></h3>
    <label>用车时间</label>
    <div class="datebox">
        <input type="text" id="starttime1" class="date" readonly><input type="text" id="endtime1" class="date" style="padding-left: 10px;" readonly>
    </div>
    <div class="select_box">
        <input id="ordertypeTow" placeholder="全部用车" class="select_val" data-value="" value="">
        <ul class="select_content">
            <c:forEach var="ordertype" items="${ordertype}">
				<li data-value="${ordertype.value}">${ordertype.text}</option>
			</c:forEach>
        </ul>
    </div>
    <button class="btn_red" id="search" onclick="search1()">查询</button>
    <button class="btn_grey" id="reset" onclick="reset1()">清空</button>
    <table id="dataGrid1" ></table>
</div>
<script type="text/javascript" src="js/reportStatistics/index.js"></script>
</body>
</html>

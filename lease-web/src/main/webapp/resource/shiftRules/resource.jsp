<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.szyciov.util.SystemConfig"%>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
%>
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="content/css/style.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css"/>


<script type="text/javascript"  src="content/js/jquery.js"></script>
<script type="text/javascript"  src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
<script type="text/javascript"  src="content/js/common.js"></script>
<script type="text/javascript"  src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
<script type="text/javascript"  src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>				
<script type="text/javascript"  src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
<script type="text/javascript"  src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
<script type="text/javascript"  src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
<script type="text/javascript"  src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript"  src="js/basecommon.js"></script>
<script type="text/javascript"  src="content/plugins/select2/select2.js"></script>
<script type="text/javascript"  src="content/plugins/select2/select2_locale_zh-CN.js"></script>
<script type="text/javascript"  src="content/plugins/select2/app.js"></script>
<script type="text/javascript"  src="content/plugins/cityselect1/cityselect1.js"></script>

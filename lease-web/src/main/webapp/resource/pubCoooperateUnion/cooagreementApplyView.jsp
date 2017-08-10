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
    <title>合作协议</title>
    <base href="<%=basePath%>" >
    <link rel="stylesheet" type="text/css" href="content/css/style.css" />
    <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
    <link rel="stylesheet" type="text/css" href="content/plugins/ueditor/themes/default/css/ueditor.css" />

    <script type="text/javascript" src="content/js/jquery.js"></script>
    <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
    <script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
    <script type="text/javascript"  src="content/js/common.js"></script>
    <script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
    <script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
    <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="content/plugins/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="content/plugins/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" charset="utf-8" src="content/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" src="js/basecommon.js"></script>
</head>
<style type="text/css">
    .select2-container .select2-choice{height:30px;}
    .select2-container{width:68%;padding:0px;}
    .dataTables_wrapper{margin: 0px 0px 10px 0px;}
    .breadcrumb{text-decoration:underline;}
    body{background: #DADADA!important;}
    .crumbs{font-size: 14px;}
</style>

<body style="height: 580px;overflow: hidden;background-color: transparent !important;">


    <div class="row">
        <div style="margin-bottom: 10px"><h2 id="cooagreementTitle">${cooa.cooname}</h2></div>
        <div style="border-bottom: 1px solid #d1d1d1"></div>
    </div>
    <div class="row">
        <div id="cooagreementContent" style="height: 480px;overflow-y: auto;padding: 10px;border: 1px solid #d1d1d1;margin-top: 20px;">
            ${cooa.coocontent}
        </div>
    </div>

</body>
</html>

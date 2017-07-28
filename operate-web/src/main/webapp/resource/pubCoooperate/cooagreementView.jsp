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

<body>
<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
<div class="crumbs">
    <a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > <a href="javascript:void(0);" onclick="back()" class="breadcrumb">战略伙伴</a> > 合作协议

    <button class="SSbtn blue back" onclick="back()" id="backButton">返回</button>
</div>
<div class="content">
    <form id="formss" class="form" style="padding-top: 30px;">
        <div class="row form" style="text-align: center;">
            <div class="col-12">
                <label style="text-align: center;padding: 10px 0 10px 0;"><h1>${vo.cooname}</h1></label>
            </div>
        </div>
        <div class="row form" id="view">
            <div class="col-12" id="contentDiv">${vo.coocontent}</div>
        </div>
    </form>
</div>
<script>
    function back(){window.location.href="coooperate"}
</script>
</body>
</html>

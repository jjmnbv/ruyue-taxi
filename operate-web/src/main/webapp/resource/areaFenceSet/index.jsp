<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width"/>
    <title>区域栅栏管理</title>
    <base href="<%=basePath%>">

<!-- 公用CSS -->
<link rel="stylesheet" type="text/css" href="content/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/css/style.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/font-awesome/css/font-awesome.min.css"/>
<link href="content/css/style_chewutong.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="content/plugins/bootstrap-switch/static/css/bootstrap3/bootstrap-switch.css"/>
<style type="text/css">
    .paging_bootstrap_full_number {
        float: right;
    }

    .popover {
        /*max-width: 500px;*/
        word-break: break-all;
        word-wrap: break-word;
    }

    .btn.blue {
        color: white;
        text-shadow: none;
        background-color: #4d90fe;

    }

    .form-horizontal .control-label {
        padding-top: 12px;
    }

    .asterisk:after {
        padding: 0;
    }
</style>


</head>
<body style="overflow:hidden;">
<div class="crumbs">
    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >区域栅栏管理
    <a class="btn btn-primary  blue pull-right" id="btnInsert " style="margin-top: -6px;"
       href="<%=basePath%>AreaFenceSet/AreaFenceSetEdit/0"><img src="img/trafficflux/icon/add.png"/>新增</a>
</div>

<div class="content">
    <!-- 查询 -->
    <div class="form-horizontal">
        <div class="row">
            <div class="col-4">
                <div class="form-group">
                    <label class="control-label col-4">栅栏名称</label>
                    <div class="col-8">
                        <input class="form-control" type="text" placeholder="栅栏名称" id="txtAFS_NAME">
                    </div>
                </div>
            </div>
            <div class="col-8">
                <div class="pull-right">
                    <button type="button" class="btn btn-default blue "
                            id="btnSearch">
                        <img src="img/trafficflux/icon/seacrch.png" alt=""/>查询
                    </button>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <table class="table table-bordered  table-striped table-hover" id="dtGrid"></table>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/js/bootstrap.min.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="content/plugins/bootbox/bootbox.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>

<script type="text/javascript" src="js/areaFenceSet/index.js"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>


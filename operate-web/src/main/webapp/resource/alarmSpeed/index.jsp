<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>超速</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css"/>
    <style type="text/css">
        .btn.blue {
            color: white;
            text-shadow: none;
            background-color: #4d90fe;
        }

        .glyphicon-arrow-left::before {
            content: "<";
        }

        .glyphicon-arrow-right::before {
            content: ">";
        }
    </style>

</head>
<body style="height:auto; overflow-y:scroll">
<div class="crumbs"><a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > 超速</div>
<div class="content">
    <div class="form-horizontal">
        <div class="row ">
            <div class="col-4">
                <div class="form-group">
                    <label class="control-label col-4">服务车企</label>
                    <div class="col-8">
                        <select id="company">
                            <option value="">全部</option>
                            <c:forEach items="${opUserCompany }" var="state">
                                <option value="${state.value }">${state.text }</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <div class="col-4">
                <div class="form-group">
                    <label class="control-label col-4">开始时间起</label>
                    <div class="col-8">
                        <input type="text" class="form-control  datetimepicker"
                               name="startTime" id="startTime" readonly="readonly"/>
                    </div>
                </div>
            </div>

            <div class="col-4">
                <div class="form-group">
                    <label class="control-label col-4"> 开始时间止 </label>
                    <div class="col-8">
                        <input type="text" class="form-control  datetimepicker"
                               name="startTimeStop" id="startTimeStop" readonly="readonly"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row ">

            <div class="col-4">
                <div class="form-group">
                    <label class="control-label col-4">车牌</label>
                    <div class="col-8">
                        <input type="text" class="form-control" name="plate"
                               id="plate"/>
                    </div>
                </div>
            </div>

            <div class="col-4">
                <div class="form-group">
                    <label class="control-label col-4">IMEI</label>
                    <div class="col-8">
                        <input type="text" class="form-control" name="imei"
                               id="imei"/>
                    </div>
                </div>
            </div>

            <div class="col-4">
                <div class="form-group">
                    <label class="control-label col-4 ">时长范围</label>
                    <div class="col-8">
                        <select class="form-control" id="timeRange"
                                name="timeRange">
                            <option value="">请选择</option>
                            <c:forEach items="${timeList }" var="time">
                                <option value="${time.Value }">${time.Text }</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </div>

            <div class="row ">
                <div class="col-md-8" style="margin-left:33%;">
                    <div class="pull-right">
                        <button type="button" class="btn btn-default blue "
                                id="btnSearch">
                            <img src="img/trafficflux/icon/seacrch.png" alt=""/>查询
                        </button>
                        <button type="button" class="btn btn-default blue"
                                id="btnClear">
                            <img src="img/trafficflux/icon/refresh.png" alt=""/>重置
                        </button>
                        <button type="button" class="btn btn-default  blue"
                                id="btnExport">
                            <img src="img/trafficflux/icon/export.png"/>导出
                        </button>
                    </div>
                </div>
        </div>
    </div>

    <div class="row">
        <div class="col-12">
            <table
                    class="table table-bordered table-striped table-hover"
                    cellspacing="0" id="dtGrid" width="100%"></table>
        </div>
    </div>
</div>

</body>

</html>
<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="content/js/bootstrap.min.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
<script type="text/javascript" src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="js/alarmSpeed/index.js?v=1.2"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>
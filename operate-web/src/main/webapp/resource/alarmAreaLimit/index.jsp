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
    <title>区域栅栏</title>
    <base href="<%=basePath%>">
    <style type="text/css">
        .paging_bootstrap_full_number {
            float: right;
        }

        table.dataTable tbody td.center {
            min-width: 191px;

        }

        .popover {
            /*max-width: 500px;*/
            word-break: break-all;
            word-wrap: break-word;
        }
    </style>
</head>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>

<!-- 公用CSS -->

<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/css/style.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/font-awesome/css/font-awesome.min.css"/>
<link href="content/css/style_chewutong.css" rel="stylesheet" type="text/css"/>

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
    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 区域栅栏
</div>

<div class="content">
    <!-- 查询  -->
    <div class="form-horizontal">
        <div class="row">

            <div class="col-4">
                <div class="form-group">
                    <label class="control-label col-4">服务车企</label>
                    <div class="col-8">
                        <select id="selDepartment">
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
                               name="endTime" id="endTime" readonly="readonly"/>
                    </div>
                </div>
            </div>

        </div>
        <div class="row">

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
                        <select class="form-control" id="timeRange" name="timeRange">
                            <option value="">请选择</option>
                            <c:forEach items="${timeList }" var="timeRange">
                                <option value="${timeRange.Value }">${timeRange.Text }</option>
                            </c:forEach>

                        </select>
                    </div>
                </div>
            </div>

        </div>

        <div class="row">

            <div class="col-4">
                <div class="form-group">
                <label class="control-label col-4 ">越界</label>
                <div class="col-8">
                    <select class="form-control" id="outOfBounds" name="outOfBounds">
                        <option value="">请选择</option>
                        <c:forEach items="${outOfBounds }" var="bound">
                            <option value="${bound.Value }">${bound.Text }</option>
                        </c:forEach>
                    </select>
                </div>
                </div>
            </div>

            <div class="col-8">
                <div class="pull-right" style="margin-top: 7px;">
                    <button type="button" class="btn btn-default blue "
                            id="btnSearch">
                        <img src="img/trafficflux/icon/seacrch.png" alt=""/>查询
                    </button>
                    <button type="button" class="btn btn-default blue"
                            id="btnClear" onclick="onClear() ">
                        <img src="img/trafficflux/icon/refresh.png" alt=""/>重置
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
<script type="text/javascript" src="content/js/jquery.combotree.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
<script type="text/javascript" src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="js/alarmAreaLimit/index.js"></script>



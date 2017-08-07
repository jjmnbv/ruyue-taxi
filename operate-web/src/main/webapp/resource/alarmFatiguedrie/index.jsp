<%@page import="com.szyciov.util.SystemConfig" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String apikey = SystemConfig.getSystemProperty("vmsApikey");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>疲劳驾驶</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css"/>
    <link href="content/plugins/bootstrap-multiselect/dist/css/bootstrap-multiselect.css" rel="stylesheet"
          type="text/css"/>


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

        .btn .caret {
            margin-left: 0;
            float: right;
            overflow: auto;
            margin-top: 9px;
            margin-bottom: 7px;
        }
    </style>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var apikey = "<%=apikey%>";
    </script>
</head>
<body style="height:auto; overflow-y:scroll">
<div class="crumbs"><a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > 疲劳驾驶</div>
<div class="content">
    <form>
        <div class="form-horizontal">
            <div class="row ">
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
                        <label class="control-label col-4">报警时间起</label>
                        <div class="col-8">
                            <input type="text" class="form-control  datetimepicker" name="alarmTime" id="alarmTime"
                                   readonly="readonly"/>
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="form-group">
                        <label class="control-label col-4">
                            报警时间止
                        </label>
                        <div class="col-8">
                            <input type="text" class="form-control  datetimepicker" name="alarmTimeStop"
                                   id="alarmTimeStop" readonly="readonly"/>
                        </div>
                    </div>
                </div>

            </div>

            <div class="row ">
                <div class="col-4">
                    <div class="form-group">
                        <label class="control-label col-4">车牌</label>
                        <div class="col-8">
                            <input type="text" class="form-control" name="plate" id="plate" placeholder="车牌"/>
                        </div>
                    </div>
                </div>

                <div class="col-4">
                    <div class="form-group">
                        <label class="control-label col-4">IMEI</label>
                        <div class="col-8">
                            <input type="text" class="form-control" name="imei" id="imei" placeholder="IMEI"/>
                        </div>
                    </div>
                </div>

                <div class="col-4">
                    <div class="form-group">
                        <label class="control-label col-4 ">时长范围</label>
                        <div class="col-8">
                            <select class="form-control" id="selLength" name="timeRange">
                                <option value="">请选择</option>
                                <c:forEach items="${durationList}" var="item">
                                    <option value="${item.Value}">${item.Text}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>

            </div>
            <div class="row ">
                <div class="col-4">
                    <div class="form-group">
                        <label class="control-label col-4">报警类型</label>
                        <div class="col-8">
                            <div>
                                <select id="selAlarmType" class="form-control" multiple="multiple">
                                    <c:forEach items="${alarmTypeList}" var="item">
                                        <option value="${item.Value}">${item.Text}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-8">
                    <div class="pull-right">
                        <button type="button" class="btn btn-default blue " id="btnSearch"><img
                                src="img/trafficflux/icon/seacrch.png" alt=""/>查询
                        </button>&nbsp;
                        <button type="button" class="btn btn-default blue" id="btnClear" onclick="onClear()"><img
                                src="img/trafficflux/icon/refresh.png" alt=""/>重置
                        </button>
                        <button type="button" class="btn btn-default  blue" id="btnExport"><img
                                src="img/trafficflux/icon/export.png"/>导出
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </form>


    <div class="row">
        <div class="col-12">
            <table class="table table-bordered table-condensed table-striped table-hover" cellspacing="0" id="dtGrid"
                   width="100%"></table>
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
<script type="text/javascript" src="content/plugins/bootstrap-multiselect/dist/js/bootstrap-multiselect.js"></script>

<script type="text/javascript" src="js/alarmFatiguedrie/index.js?v=1.2"></script>

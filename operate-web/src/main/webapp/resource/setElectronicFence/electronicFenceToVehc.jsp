<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*" %>
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
    <title>系统设置 电子栅栏设置</title>
    <base href="<%=basePath%>">
    <style type="text/css">
        .paging_bootstrap_full_number {
            float: right;
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
<link rel="stylesheet" type="text/css" href="content/plugins/font-awesome/css/font-awesome.min.css"/>
<link href="content/css/style_chewutong.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="content/plugins/bootstrap-switch/static/css/bootstrap3/bootstrap-switch.css"/>
<style type="text/css">
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
    #dtGrid2{
        width: 100% !important;
    }
    #dtGrid2_wrapper .col-sm-6{
        padding:0;
    }
    .crumbs{
        padding-right: 16px;
    }
    #btnBind,
    #btnCancel{
        margin-top: -6px;
    }
    #btnBind{
        margin-right: 10px;
    }
</style>

</head>
<div class="crumbs">
    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >
    <a href="<%=basePath%>SetElectronicFence/Index">电子栅栏设置 ></a>  分配车辆
    <button type="button" id="btnCancel" class="btn blue btn pull-right"><i class="fa fa-reply"
                                                                               style="width:auto;"></i>返回
    </button>
    <button type="button" class="btn blue btn pull-right" id="btnBind" onclick="onBind()"><img
            src="img/trafficflux/icon/add.png"/>添加车辆
    </button>
</div>
<div class="content">

    <input type="hidden" id="id" name="id" value="${id}">

    <div class="form-horizontal">
        <div class="row">
            <div class="col-8" id="switchState">
                &nbsp;
            </div>
            <div class="col-3">
                <div class="form-group">
                    <label class="control-label col-4">车牌：</label>
                    <div class="col-8">
                        <input class="form-control" type="text" placeholder="车牌" id="plate">
                    </div>
                </div>
            </div>
            <div class="col-1">
                <button type="button" class="btn btn-default blue pull-right"
                        id="btnSearch">
                    <img src="img/trafficflux/icon/seacrch.png" alt=""/>查询
                </button>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <div class="portlet box blue">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-reorder"></i>受监控车辆列表
                    </div>
                </div>
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form id="frmmodal" action="#" class="form-horizontal" role="form">
                        <div class="modal-body">

                            <div class="row">
                                <div class="col-4">
                                    <div class="form-group">
                                        <input type="hidden" id="txtFLAG" name="txtFLAG"/>
                                        <label class="control-label col-4">栅栏名称：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="name"></p>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <table class="table table-bordered table-condensed table-striped table-hover"
                                               id="dtGrid"></table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <!-- END FORM-->
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="mdTimeFenceToVehc" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
         style="min-height:350px;">
        <div class="modal-dialog">
            <div class="modal-content" style="width:810px">
                <form id="frmmodal" action="#" class="form-horizontal" role="form">
                    <div class="modal-header">
                        <a class="close" data-dismiss="modal">x</a>
                        <input type="hidden" id="txtinuprid"/>
                        <h4 class="modal-title">添加车辆</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">

                            <div class="col-4">
                                <div class="form-group">
                                    <label class="control-label col-4">车牌</label>
                                    <div class="col-8">
                                        <input class="form-control" type="text" placeholder="车牌" id="plate1"
                                               name="plate">
                                    </div>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                    <label class="control-label col-4">设备IMEI</label>
                                    <div class="col-8">
                                        <input class="form-control" type="text" placeholder="设备IMEI" id="imei">
                                    </div>
                                </div>
                            </div>
                            <div class="col-4">
                                <div class="form-group">
                                    <label class="control-label col-4">型号名称</label>
                                    <div class="col-8">
                                        <input class="form-control" type="text" placeholder="型号名称" id="entityName">
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-4">
                                <div class="form-group">
                                    <label class="control-label col-4">设备状态</label>
                                    <div class="col-8">
                                        <select class="form-control" id="workStatus"
                                                name="workStatus">
                                            <option value="">请选择</option>
                                            <c:forEach items="${workStatusList }" var="list">
                                                <option value="${list.Value }">${list.Text }</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="col-8">
                                <div class="form-group">
                                    <div class="pull-right" style="width: 80px">
                                        <button type="button" class="btn blue btn-default " id="btnSearchForMd">
                                            <img src="img/trafficflux/icon/seacrch.png" alt=""/>查询
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <table class="table table-bordered table-condensed table-striped table-hover"
                               id="dtGrid2"></table>
                    </div>

                </form>
            </div>
        </div>
    </div>

</div>

</body>
</html>

<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
<script type="text/javascript"  src="content/js/common.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
<script type="text/javascript" src="content/js/jquery.combotree.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
<script type="text/javascript" src="content/plugins/bootstrap-typeahead/bootstrap-typeahead.min.js"></script>
<script type="text/javascript" src="content/plugins/ion.rangeslider/js/ion-rangeSlider/ion.rangeSlider.js"></script>
<script type="text/javascript" src="content/plugins/bootbox/bootbox.min.js"></script>
<script type="text/javascript" src="js/setTimeFence/timefenabstract.js"></script>

<script type="text/javascript" src="js/setElectronicFence/electronicFenceToVehc.js"></script>
<script>
    var basePath = "<%=basePath%>";
</script>





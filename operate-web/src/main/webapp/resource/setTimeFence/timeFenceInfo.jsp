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
    <title>系统设置 时间栅栏设置</title>
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
<link rel="stylesheet" href="content/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<link rel="stylesheet" href="content/plugins/zTree_v3/css/zTreeStyle/metro.css"/>
<link href="content/css/loading.css" rel="stylesheet" type="text/css"/>
<link href="content/css/style_chewutong.css" rel="stylesheet" type="text/css"/>
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

    #cbMonitorPeriod label {
        width: auto;
    }

    .form-horizontal .control-label {
        text-align: left;
    }
</style>


<style type="text/css">
    .div-inline {
        direction: ltr;
        display: table-cell;
    }

    .acc-images li img:hover {
        opacity: 1;
        box-shadow: 0px 0px 0px 4px #72c02c;
        transition: all 0.4s ease-in-out 0s;
        -moz-transition: all 0.4s ease-in-out 0s;
        -webkit-transition: all 0.4s ease-in-out 0s;
    }

    .acc-images li img {
        width: 80px;
        height: 80px;
        opacity: 0.6;
        margin: 0 2px 8px;
    }

    .acc-images-delete {
        text-align: right;
        width: 80px;
        font-size: 12px;
    }
    #btnCancel{
        margin-top: -6px;
    }
</style>


</head>
<div class="crumbs">
    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >
    <a href="<%=basePath%>SetTimeFence/Index">时间栅栏设置 ></a> 分配车辆详情
    <a id="btnCancel" class="btn blue btn-md pull-right" onclick="window.opener = null; window.open('', '_self'); window.close();return" href="javascript:self.close()">
        <img src="img/trafficflux/icon/shutdown.png" />关闭</a>

</div>
<!-- 头部 -->
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
                <button type="button" class="btn btn-default  blue pull-right" id="btnSearch"><img
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
                                        <input type="hidden" id="name1" name="name1"/>
                                        <label class="control-label col-4">栅栏名称：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="name"></p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">允许运行时段：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="allowRunTime"></p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">重复周期：</label>
                                        <div class="col-8">
                                            <p id="cbMonitorPeriod" class="form-control-static"></p>
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
<script type="text/javascript" src="content/js/loading.js"></script>
<script type="text/javascript" src="js/setTimeFence/timeFenceInfo.js"></script>
<script>
    var basePath="<%=basePath%>";
</script>






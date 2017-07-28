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
    <title>基础数据 | 维护司机档案信息</title>
    <base href="<%=basePath%>">
    <style type="text/css">
        .paging_bootstrap_full_number {
            float: right;
        }
    </style>
</head>

<!-- 公用CSS -->
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/css/style.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="content/plugins/font-awesome/css/font-awesome.min.css"/>
<link rel="stylesheet" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.css"/>
<link rel="stylesheet" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.skinModern.css" id="styleSrc"/>
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
    #cbMonitorPeriod label{
        width:auto;
    }
    .form-horizontal .control-label{
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
</style>

</head>
<div class="crumbs">
    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a>>
    <a href="<%=basePath%>SetTimeFence/Index">时间栅栏设置</a>> 时间栅栏管理
</div>


<div class="content">

    <input type="hidden" id="id" name="id" value="${id}">

    <div class="row">
        <div class="col-12">
            <div class="portlet box blue">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-reorder"></i>时间栅栏设置
                    </div>
                </div>
                <div class="portlet-body form">
                    <!-- BEGIN FORM-->
                    <form id="frmmodal" action="#" class="form-horizontal" role="form">
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <label class="control-label">
                                            栅栏名称
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <div class="col-8">
                                            <input class="form-control" type="text" id="name" name="name"
                                                   required="required" maxlength="20" placeholder="栅栏名称不超过20个字符"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <label class="control-label">
                                            选择允许运行时段
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <div class="col-8">
                                            <input type="text" id="rangeName" name="rangeName"/>
                                            <input type="hidden" id="startTime" name="startTime" value="480"/>
                                            <input type="hidden" id="endTime" name="endTime" value="1080"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <label class="control-label">重复周期</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <div class="col-12">
                                            <div id="cbMonitorPeriod">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div class="col-6">
                                <button type="submit" class="btn blue"><i class="fa fa-pencil"></i> 保存</button>
                                <button type="button" id="btnCancel" class="btn default "><img
                                        src="img/trafficflux/icon/shutdown_ga.png"/> 取消
                                </button>
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
<script type="text/javascript" src="js/setTimeFence/edit.js"></script>
<script>
   var basePath="<%=basePath%>";
</script>







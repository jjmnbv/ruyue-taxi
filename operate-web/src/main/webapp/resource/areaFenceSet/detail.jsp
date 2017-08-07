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
    <title>基础数据 | 栅栏详情</title>
    <base href="<%=basePath%>">
    <style type="text/css">
        .paging_bootstrap_full_number {
            float: right;
        }
    </style>

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
    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页 ></a>
    <a href="<%=basePath%>AreaFenceSet/Index">区域栅栏管理 ></a> 栅栏详情
    <a id="btnCancel" class="btn blue btn pull-right"
       onclick="window.opener = null; window.open('', '_self'); window.close();return" href="javascript:self.close()">
        <img src="img/trafficflux/icon/shutdown.png"/>关闭</a>

</div>
<!-- 头部 -->
<div class="content">
    <div class="form-horizontal">
        <input type="hidden" id="id" name="id" value="${id}">
        <div class="row">
            <div class="col-8" id="pFENCESTATUS">
                &nbsp;
            </div>
            <div class="col-3">
                <div class="form-group">
                    <label class="control-label col-4">车牌：</label>
                    <div class="col-8">

                        <input class="form-control" type="text" placeholder="车牌" id="txtPlates">

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
                                        <label class="control-label col-4">栅栏名称：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="txtName"></p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-8">
                                    <div class="form-group">
                                        <label class="control-label col-4">允许运行城市<a id="btnSelRole"
                                                                                       onclick="onIniCity()">(按树形查看)</a>：</label>

                                        <div class="col-8">
                                            <p class="form-control-static" id="txtArea"></p>
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

    <div class="modal fade" id="mdSelCity" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog" style="width:300px">
            <div class="modal-content">
                <form action="#" class="form-horizontal">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h3 class="modal-title"><label>允许运行城市：</label></h3>
                    </div>
                    <div class="portlet-body">
                        <div class="scroller" style="height: 450px; overflow: auto" data-rail-visible="1"
                             data-handle-color="#a1b2bd">
                            <div class="zTreeDemoBackground  left">
                                <ul class="ztree" id="cityList"></ul>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn blue default" data-dismiss="modal"><img
                                src="img/trafficflux/icon/shutdown.png"/>关闭</a></button>
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
<script type="text/javascript" src="content/plugins/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="content/plugins/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>

<script type="text/javascript" src="js/areaFenceSet/detail.js?v=1.6"></script>

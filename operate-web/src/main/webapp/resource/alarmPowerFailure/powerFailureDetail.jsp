<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.szyciov.util.SystemConfig" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String yingyan_ak = SystemConfig.getSystemProperty("yingyan_ak");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width"/>
    <title>断电报警详情</title>
    <base href="<%=basePath%>">

</head>

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
<link href="http://api.map.baidu.com/library/TrafficControl/1.4/src/TrafficControl_min.css" rel="stylesheet" type="text/css" />


<style type="text/css">
    #map_canvas {
        width: 100%;
        height: 600px;
        overflow: hidden;
        margin: 0;
    }

    .ListStyle li {
        margin-bottom: 15px;
        margin-top: 10px;
        margin-left: 9px;
    }

    .util-btn-group-margin-bottom-5 .btn-groupnew {
        margin-bottom: 5px !important;
    }

    .btn-groupnew, .btn-group-vertical {
        position: relative;
        display: inline-block;
        vertical-align: middle;
    }

    #tDiv > .dropdown-menu > li > a,
    #dDiv > .dropdown-menu > li > a {
        padding: 0px 3px 3px 3px;
        color: #ffffff;
        text-decoration: none;
        display: block;
        clear: both;
        font-weight: normal;
        line-height: 18px;
        white-space: nowrap;
        width: 77px;
        margin: 5px auto;
        height: 20px;
    }

    #tDiv > .dropdown-menu,
    #dDiv > .dropdown-menu {
        min-width: 90px;
    }

    #tDiv > .dropdown-menu.pull-right,
    #dDiv > .dropdown-menu.pull-right {
        right: 10px;
        left: auto;
    }
    img{max-width:none;}
    .BMapLabel{
        color:green !important;
        max-width:none;
    }
    .blue{
        background: #4d90fe;
    }
    .btn.btn-primary:hover{
        background-color:#0362FD;
    }
    .btn.btn-xs.blue{color:#fff;}
    .BMap_bubble_content{
        margin-top: 20px;
    }
</style>
<style type="text/css">
    .vehc-list {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .vehc-list > li {
        position: relative;
        padding: 10px 5px;
        border-bottom: 1px solid #eaeaea;
    }

    .vehc-list li.last-line {
        border-bottom: none;
    }

    .vehc-list li > .vehc-bell {
        margin-left: 10px;
    }

    .vehc-list li > .vehc-icon {
        float: left;
        width: 30px;
        padding-left: 5px;
    }

    .vehc-list li > .vehc-title {
        margin-right: 10px;
    }

    .vehc-list li > .vehc-title .vehc-status {
        margin-left: 30px;
    }

    .vehc-list li > .run {
        color: #28b779;
    }

    .vehc-list li > .stop {
        color: #333333;
    }

    .vehc-list li > .offline {
        color: #ffb848;
    }

    .vehc-list li .vehc-title .vehc-title-sp {
        margin-right: 5px;
    }

    .vehc-list li.vehc-done .vehc-title-sp {
        text-decoration: line-through;
    }

    .vehc-list li.vehc-done {
        background: #f6f6f6;
    }

    .vehc-list li.vehc-done:hover {
        background: #f4f4f4;
    }

    .vehc-list li:hover {
        background: #f9f9f9;
    }
    .portlet >.portlet-title >.caption{
        font-size:18px;
    }
    .portlet >.portlet-title >.caption >i {
        float: left;
        margin-top: 1px;
        display: inline-block !important;
        font-size: 13px;
        margin-right: 5px;
        color: #666;
    }
    .fa-check:before{
        content:"√";
        font-weight: bold;
        font-size:20px;
    }
    p {
        margin: 0 0 10px;
    }
    .form-control-static {
        padding-top: 0px
    }
    .btn.blue {
        color: white;
        text-shadow: none;
        background-color: #4d90fe;

    }
    #btnCancel{
        margin-top: -6px;
    }
</style>
</head>

<body>
<div class="crumbs">
    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >
    <a href="<%=basePath%>AlarmPowerFailure/Index">断电报警</a>>断电报警详情
    <button type="button" id="btnCancel" class="btn blue btn-md pull-right"><i class="fa fa-reply" style="width:auto;"></i>返回</button>
</div>


<div class="content">
    <input type="hidden" value="${queryOutage.eqpId}" id="eqpId">
    <input type="hidden" value="${queryOutage.departmentId}" id="departmentId">

    <div class="row">
        <div class="col-12">
            <div class="portlet box blue">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-reorder"></i>断电报警详情
                    </div>
                </div>
                <div class="portlet-body form">
                    <form class="form-horizontal" role="form">
                        <div class="form-body">
                            <h3 class="form-section">车辆信息</h3>
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">车牌：</label>
                                        <div class="col-md-8">
                                            <p class="form-control-static" id="pV_PLATES">
                                                ${queryOutage.plate}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">IMEI：</label>
                                        <div class="col-md-8">
                                            <p class="form-control-static" id="pV_PROPERTY">
                                                ${queryOutage.imei}

                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">所属部门：</label>
                                        <div class="col-md-8">
                                            <p class="form-control-static" id="pRC_DEPARTMENT">
                                                ${queryOutage.department}

                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">

                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">报警时间：</label>
                                        <div class="col-md-8">
                                            <p class="form-control-static" id="pAPF_STARTDATE">
                                                ${queryOutage.alarmTime}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <h3 class="form-section">处理信息</h3>
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">处理时间：</label>
                                        <div class="col-md-8">
                                            <p class="form-control-static" id="pOPERATTIME">
                                                ${queryOutage.processingTime}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">处理人：</label>
                                        <div class="col-md-8">
                                            <p class="form-control-static" id="pOPERATOR">
                                                ${queryOutage.processingPeople}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">核实人姓名：</label>
                                        <div class="col-md-8">
                                            <p class="form-control-static" id="pVERIFYPERSON">
                                                ${queryOutage.processingPeople}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">


                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">核实人部门：</label>
                                        <div class="col-md-8">
                                            <p class="form-control-static" id="pVCD_ID">
                                                ${queryOutage.department}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">断电原因：</label>
                                        <div class="col-md-8">
                                            <p class="form-control-static" id="pREASON">
                                                ${queryOutage.reasonText}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="control-label col-md-4">备注信息：</label>
                                        <div class="col-md-8">
                                            <p class="form-control-static" id="pREMARKS">
                                                ${queryOutage.remarks}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <h3 class="form-section" id="hTrack">报警位置跟踪</h3>
                            <div class="row" id="map">
                                <div class="col-md-12">
                                    <div id="map_canvas">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>
<script type="text/javascript" src="content/js/jquery.js"></script>
<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="content/plugins/select2/app.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
<script type="text/javascript" src="content/js/loading.js"></script>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=AFLc9FVyIHUWExKYFETDeF6T&s=1"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/TrafficControl/1.4/src/TrafficControl_min.js"></script>
<script type="text/javascript" src="js/vehicleTrajectory/directiontranslate.js"></script>
<script type="text/javascript" src="js/alarmPowerFailure/powerFailureDetail.js?ver=<%=Math.random()%>"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>


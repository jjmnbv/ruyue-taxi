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
    <title>行驶轨迹</title>
    <base href="<%=basePath%>">

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
    #map_canvas {
        width: 100%;
        height: 655px;
        overflow: hidden;
        margin: 0;
    }

    .note-success {
        background-color: #EBFCEE;
        border-color: #39b3d7;
        color: #39b3d7;
    }

    .note:hover {
        background-color: #f9f9f9;
    }

    .track-list {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .btnStyle {
        float: right;
        width: 300px;
        height: 50px;
        margin-top: 5px;
        background-color: white;
        border: 0px black solid;
        position: absolute;
        z-index: 1;
        right: 10px;
        background: url('img/trafficflux/vehcTrack/bg_map_01.png') no-repeat center;
        background-size: 100% 80%;
    }

    .speedStyle {
        float: right;
        width: 300px;
        height: 110px;
        background-color: white;
        border: 0px black solid;
        position: absolute;
        z-index: 1;
        right: 10px;
        top: 50px;
        display: none;
        background: url('img/trafficflux/vehcTrack/bg_map_02.png') no-repeat center;
        background-size: 100% 100%;
    }

    .liStyle {
        border-bottom: 1px #cccccc solid;
        border-top: 1px #cccccc solid;
        border-left: 0px;
        border-right: 0px;
        height: 130px;
    }

    .liStyle p {
        width: 200px;
    }

    .btn-defaultnew {
        color: #333;
        background-color: #fff;
        border-color: white;
        padding: 6px 13px;
        font-size: 12px;
    }

    .btn-defaultnew:hover, .btn-defaultnew:focus, .btn-defaultnew:active, .btn-defaultnew.active {
        color: blue;
        /*border-color: #adadad;*/
    }

    .trackStyle {
        list-style: none;
    }

    .trackStyleli {
        /*  margin-left: -40px; */
        margin-top: 5px;
        margin-bottom: 5px;
        height: 145px;
        border-bottom: 1px #cccccc solid;
        border-top: 1px #cccccc solid;
        border-left: 0px;
        border-right: 0px;
    }

    .BMap_bubble_content p {
        margin: 0 0 10px;
    }

    img {
        max-width: none;
    }

    .green_a {
        background: #1E93EE;
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
    <a href="<%=basePath%>AlarmElectronicLimit/Index">电子围栏</a>>行驶轨迹
    <button type="button" id="btnCancel" class="btn blue btn-md pull-right"><i class="fa fa-reply" style="width:auto;"></i>返回</button>
</div>


<div class="content">
    <input type="hidden" value="${trackId}" id="trackId">
    <input type="hidden" value="${fenceViolation.eqpId}" id="eqpId">
    <input type="hidden" value="${fenceViolation.trackStatus}" id="trackStatus">

    <div class="row">
        <div class="col-12">
            <div class="portlet box blue">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-reorder"></i>行驶轨迹
                    </div>
                </div>
                <div class="portlet-body form">
                    <form class="form-horizontal" role="form">

                        <div class="form-body">
                            <h3 class="form-section">行程基本信息</h3>
                            <div class="row">
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">车牌：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="pV_PLATES">
                                                ${fenceViolation.plate}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">IMEI：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="pV_IMEI">
                                                ${fenceViolation.imei}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">所属部门：</label>
                                        <div class="col-5">
                                            <p class="form-control-static" id="pV_DEPT">
                                                ${fenceViolation.department}
                                            </p>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <div class="row">
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">违规时长：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="fenceName">
                                                ${fenceViolation.fenceName}
                                            </p>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">开始时间：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="pAAL_STRATTIME">
                                                ${fenceViolation.startTime}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <!--/span-->
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">结束时间：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="pAAL_ENDTIME">
                                                ${fenceViolation.endTime}
                                            </p>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">违规时长：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="pV_PROPERTY">
                                                ${fenceViolation.lengthOfViolation}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">违规里程：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="illegalMileage">
                                                ${fenceViolation.illegalMileage}km
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">违规开始地点：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="startOfViolation">
                                                ${fenceViolation.startOfViolation}km
                                            </p>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">违规结束地点：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="endOfViolation">
                                                ${fenceViolation.endOfViolation}km
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <h3 class="form-section">行驶轨迹</h3>
                            <div class="row">
                                <div class="col-12">
                                    <div id="map_canvas">
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
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="content/plugins/select2/app.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
<script type="text/javascript" src="content/js/loading.js"></script>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>&s=1"></script>
<script type="text/javascript" src="js/vehicleTrajectory/directiontranslate.js"></script>
<script type="text/javascript" src="js/alarmElectronicLimit/elctronicTravelTrack.js??ver=<%=Math.random()%>"></script>


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
    <title>当前行程</title>
    <base href="<%=basePath%>">

    <!-- 公用CSS -->
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/combotree.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css"
          type="text/css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/zTree_v3/css/zTreeStyle/metro.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/trackdetail.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/style_chewutong.css"/>

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
    <script type="text/javascript" src="content/plugins/select2/select2.js"></script>
    <script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
    <script type="text/javascript" src="content/plugins/select2/app.js"></script>
    <script type="text/javascript" src="content/js/jquery.combotree.js"></script>
    <script type="text/javascript" src="content/plugins/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="content/plugins/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
    <script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=AFLc9FVyIHUWExKYFETDeF6T&s=1"></script>
    <script src="content/js/echarts-all.js" type="text/javascript"></script>
    <script type="text/javascript" src="content/js/loading.js"></script>
    <script src="js/alarmWaterTemp/directiontranslate.js" type="text/javascript"></script>
    <script src="content/plugins/amcharts/amcharts.js" type="text/javascript"></script>
    <script src="content/plugins/amcharts/serial.js" type="text/javascript"></script>
    <script src="content/plugins/amcharts/pie.js" type="text/javascript"></script>
    <script src="content/plugins/amcharts/gauge.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/basecommon.js"></script>
    <style type="text/css">
        .form select {
            width: 68%;
        }

        .select2-container .select2-choice {
            height: 30px;
        }

        .select2-container {
            width: 68%;
            padding: 0px;
        }

        .dataTables_wrapper {
            margin: 10px 0px;
        }

        .breadcrumb {
            text-decoration: underline;
        }

        .tabmenu {
            margin-bottom: 10px;
        }

        th, td {
            white-space: nowrap;
        }

        div.dataTables_wrapper {
            width: $(window) . width();
            margin: 0 auto;
        }

        #map_canvas {
            width: 100%;
            height: 500px;
            overflow: hidden;
            margin: 0;
        }

        .track_bg_pImei {
            width: 165px;
            background-size: 100% 100%;
        }

        .track_bg_1_1 {
            line-height: 40px;
            height: 40px;
        }
        .btn.blue {
            color: white;
            text-shadow: none;
            background-color: #4d90fe;

        }
        #btnCancel{
            margin-top: -6px;
        }
        .row{
            margin-left: -5px;
        }
    </style>
</head>
<div class="crumbs">
    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >
    <%--<a href="<%=basePath%>AlarmSpeed/Index">超速 ></a>--%> 当前行程
    <a id="btnCancel" class="btn blue btn pull-right"
       onclick="window.opener = null; window.open('', '_self'); window.close();return" href="javascript:self.close()">
        <img src="img/trafficflux/icon/shutdown.png"/>关闭</a>
</div>

<!-- 左边 -->
<div class="page-content-wrapper">
    <div class="content">
        <div class="page-content-body">
            <!-- 写具体业务代码 -->

            <div class="portlet">
                <div class="portlet-title">
                    <div class="caption">
                        行程基本信息
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-4">
                            <div class="col-6"></div>
                            <div class="col-6"><p id="pV_IMEI" class="track_bg_1 track_bg_pImei" >
                                ${trackDetail.imei}</p>
                            </div>
                        </div>
                        <div class="col-4 ">
                            <div class="col-6">
                                <p class="car_icon"></p>
                            </div>
                            <div class="col-6">
                                <p id="pV_PLATES" class="track_bg_1"  style="margin-top: 5px;float:left;">${trackDetail.plate}</p>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="col-md-6"><p class="people"/></p></div>
                            <div class="col-md-6" style="margin-top: -10px;">
                                <p id="pV_DEPT" class="track_bg_1_1"
                                   style=" float: left;margin-top: 12px;">
                                    ${trackDetail.departmentName}</p></div>
                        </div>
                    </div>

                    <%--  @*
                      <div class="col-4">
                          <div id="idlingspeedchar" style="width: 100%; height: 200px;"></div>
                      </div>
                      *@--%>
                </div>
            </div>
            <div class="row">
                <div class="col-9">
                    <div class="portlet">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa" style="margin-top:0px"></i>实时数据
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div class="row">
                                <div class="col-3">
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="control-label col-6">车辆状态：</label>
                                            <div class="col-6">
                                    <span class="form-control-static" id="pVS_STATUS">
                                        ${trackDetail.workStatusText}
                                    </span>
                                            </div>
                                        </div>
                                    </div>
                                    <br/>
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="control-label col-6">当前电压：</label>
                                            <div class="col-6">
                                    <span class="form-control-static" id="pVS_VOLTAGE">
                                        ${trackDetail.voltage} v
                                    </span>
                                            </div>
                                        </div>
                                    </div>
                                    <br/>
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="control-label col-6">当前里程：</label>
                                            <div class="col-6">
                                    <span class="form-control-static" id="pVS_SUBMILEAGE">
                                        ${trackDetail.totalMileage} km
                                    </span>
                                            </div>
                                        </div>
                                    </div>
                                    <br/>
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="control-label col-6">故障代码：</label>
                                            <div class="col-6">
                                    <span class="form-control-static" id="pVS_FAULTCODE">
                                        ${trackDetail.faultCode}
                                    </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-9" style="height:175px;margin-top:-50px">
                                    <div id="avgspeedchar" class="dash_board" style="height:300px"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-3">
                    <div class="portlet">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa time" style="margin-top:0px"></i>时间
                            </div>
                        </div>
                        <div class="portlet-body">

                            <br/>
                            <div class="row">
                                <div class="col-2">
                                    <p class="img_timestar"/>
                                </div>
                                <div class="col-3">
                                    <span class="form-control-static">开始时间</span>
                                </div>
                                <div class="col-6">
                                        <span class="form-control-static" id="pVT_STARTTIME">
                                            ${trackDetail.trackStartTime}
                                        </span>
                                </div>
                            </div>
                            <br/>
                            <br/>
                            <div class="row">
                                <div class="col-2">
                                    <p class="img_update"/>
                                </div>
                                <div class="col-3">
                                    <span class="form-control-static">更新时间</span>
                                </div>
                                <div class="col-6">
                                        <span class="form-control-static" id="pVS_UPDATETIME">
                                            ${trackDetail.updateTime}
                                        </span>
                                </div>
                            </div>
                            <br/>
                            <br/>
                            <div class="row">
                                <div class="col-2">
                                    <p class="img_time_spent"/>
                                </div>
                                <div class="col-3">
                                    <span class="form-control-static">总时长</span>
                                </div>
                                <div class="col-6">
                                        <span class="form-control-static" id="pVT_TOTALTIME">
                                            ${trackDetail.totalTimeText}
                                        </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="portlet">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa icon_dai"></i>怠速详情
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="row">
                        <table class="table table-bordered table-condensed table-striped table-hover"
                               id="dtGrid"></table>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-4">
                    <div class="portlet box" style="background-color: #BB7D45; border: 1px solid #BB7D45; ">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa icon_trive_light"></i>驾驶行为 <span
                                    style="font-size: 14px; color: #FFFFFF;">（单位：次）</span>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div class="row">
                                <div class="col-6">
                                    <div class="dashboard-stat" style="margin-bottom:0px;">
                                        <div class="visual">
                                            <img src="img/trafficflux/track/img_suddonspeed.png"
                                                 class="img-responsive"/>
                                        </div>
                                        <div class="details">
                                            <div class="number"
                                                 style="color: #333333; font-size: 16px; font-weight:300;">
                                                ${alarmTimesCount.alarmAccelerateTimes}
                                            </div>
                                            <div class="desc" style="color: #333333">
                                                急加速
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="dashboard-stat" style="margin-bottom:0px;">
                                        <div class="visual">
                                            <img src="img/trafficflux/track/img_sharpslowdown.png"
                                                 class="img-responsive"/>
                                        </div>
                                        <div class="details">
                                            <div class="number"
                                                 style="color: #333333; font-size: 16px; font-weight:300;">
                                                ${alarmTimesCount.alarmDecelerateTimes}
                                            </div>
                                            <div class="desc" style="color: #333333">
                                                急减速
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <div class="dashboard-stat" style="margin-bottom:0px;">
                                        <div class="visual">
                                            <img src="img/trafficflux/track/img_suddonsharpturn.png"
                                                 class="img-responsive"/>
                                        </div>
                                        <div class="details">
                                            <div class="number"
                                                 style="color: #333333; font-size: 16px; font-weight:300;">

                                                ${alarmTimesCount.alarmSharpTurnTimes}
                                            </div>
                                            <div class="desc" style="color: #333333">
                                                急转弯
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="dashboard-stat" style="margin-bottom:0px;">
                                        <div class="visual">
                                            <img src="img/trafficflux/track/img_alarmrapidchangelanes.png"
                                                 class="img-responsive"/>
                                        </div>
                                        <div class="details">
                                            <div class="number"
                                                 style="color: #333333; font-size: 16px; font-weight:300;">

                                                0
                                            </div>
                                            <div class="desc" style="color: #333333">
                                                快速变道
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="portlet box " style="background-color: #DAA843; border :1px solid #DAA843; ">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa icon_yuxing_light"></i>运行管控 <span
                                    style="font-size: 14px; color: #FFFFFF;">（单位：次）</span>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div class="row">
                                <div class="col-6">
                                    <div class="dashboard-stat" style="margin-bottom:0px;">
                                        <div class="visual">
                                            <img src="img/trafficflux/track/img_alarmspeeding.png"
                                                 class="img-responsive"/>
                                        </div>
                                        <div class="details">
                                            <div class="number"
                                                 style="color: #333333; font-size: 16px; font-weight:300;">
                                                ${trackDetail.speedingCount}
                                            </div>
                                            <div class="desc" style="color: #333333">
                                                超速行驶
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="dashboard-stat" style="margin-bottom:0px;">
                                        <div class="visual">
                                            <img src="img/trafficflux/track/img_alarmidling.png"
                                                 class="img-responsive"/>
                                        </div>
                                        <div class="details">
                                            <div class="number"
                                                 style="color: #333333; font-size: 16px; font-weight:300;">
                                                ${trackDetail.idlingCount}

                                            </div>
                                            <div class="desc" style="color: #333333">
                                                怠速超时
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <div class="dashboard-stat" style="margin-bottom:0px;">
                                        <div class="visual">

                                        </div>
                                        <div class="details">
                                            <div class="number"
                                                 style="color: #333333; font-size: 16px; font-weight:300;">
                                            </div>
                                            <div class="desc" style="color: #333333">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="portlet box" style="background-color: #F15454; border: 1px solid #F15454; ">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa icon_warn_light"></i>报警信息 <span
                                    style="font-size: 14px; color: #FFFFFF;">（单位：次）</span>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div class="row">
                                <div class="col-6">
                                    <div class="dashboard-stat" style="margin-bottom:0px;">
                                        <div class="visual">
                                            <img src="img/trafficflux/track/img_alarmcollision.png"
                                                 class="img-responsive"/>
                                        </div>
                                        <div class="details">
                                            <div class="number"
                                                 style="color: #333333; font-size: 16px; font-weight:300;">
                                                ${trackDetail.collisionCount}

                                            </div>
                                            <div class="desc" style="color: #333333">
                                                碰撞
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="dashboard-stat" style="margin-bottom:0px;">
                                        <div class="visual">
                                            <img src="img/trafficflux/track/img_alarmrollover.png"
                                                 class="img-responsive"/>
                                        </div>
                                        <div class="details">
                                            <div class="number"
                                                 style="color: #333333; font-size: 16px; font-weight:300;">
                                                0

                                            </div>
                                            <div class="desc" style="color: #333333">
                                                侧翻
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <div class="dashboard-stat" style="margin-bottom:0px;">
                                        <div class="visual">
                                            <img src="img/trafficflux/track/img_alarmwater.png" class="img-responsive"/>
                                        </div>
                                        <div class="details">
                                            <div class="number"
                                                 style="color: #333333; font-size: 16px; font-weight:300;">
                                                ${trackDetail.watertempCount}

                                            </div>
                                            <div class="desc" style="color: #333333">
                                                水温超标
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="dashboard-stat" style="margin-bottom:0px;">
                                        <div class="visual">
                                            <img src="img/trafficflux/track/img_alarmpowerfailure.png"
                                                 class="img-responsive"/>
                                        </div>
                                        <div class="details">
                                            <div class="number"
                                                 style="color: #333333; font-size: 16px; font-weight:300;">
                                                ${trackDetail.powerfailureCount}

                                            </div>
                                            <div class="desc" style="color: #333333">
                                                断电报警
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="portlet">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="history_route"></i>行驶轨迹
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-12">
                            <div id="map_canvas">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<!-- 尾部 -->
</body>
<!-- 公用JS -->
</html>

<script src="content/js/currenttrack.js" type="text/javascript"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>

<%
    String trackStatus = (String) request.getAttribute("trackStatus");
    if (trackStatus.equals("1")) {
%>
<script type="text/javascript">
    $(function () {
        loadCurrenttrack("${eqpId}", "${trackId}");
    });
</script>
<%
    }
%>

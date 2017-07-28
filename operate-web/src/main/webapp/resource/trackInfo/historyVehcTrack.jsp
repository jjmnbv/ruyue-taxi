<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.*" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>历史行程</title>
    <base href="<%=basePath%>">
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
    <script src="js/alarmWaterTemp/directiontranslate.js" type="text/javascript"></script>
    <script src="content/plugins/amcharts/amcharts.js" type="text/javascript"></script>
    <script src="content/plugins/amcharts/serial.js" type="text/javascript"></script>
    <script src="content/plugins/amcharts/pie.js" type="text/javascript"></script>
    <script src="content/plugins/amcharts/gauge.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/basecommon.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
    </script>
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
<body>
<div class="crumbs">
    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >
    <%--<a href="<%=basePath%>AlarmSpeed/Index">超速 ></a> --%>历史行程
    <a id="btnCancel" class="btn blue btn pull-right"
       onclick="window.opener = null; window.open('', '_self'); window.close();return" href="javascript:self.close()">
        <img src="img/trafficflux/icon/shutdown.png"/>关闭</a>
</div>

<!-- 左边 -->
<div class="page-content-wrapper">
    <div class="content">
        <div class="page-content-body">
            <div class="row">
                <div class="col-12" id="divtrack">
                    <div class="portlet" style="margin-bottom:0px">
                        <div class="portlet-title">
                            <div class="caption">
                                单行程行车数据
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
                                <%--<div class="col-3">
                                    <div class="col-6"><p class="img_trave"/></div>
                                    <div class="col-6"><p id="pD_NAME" class="track_bg_1_1" style=" float: left;">
                                        @Model.D_DRIVERNAME</p></div>
                                </div>--%>
                            </div>
                        </div>
                    </div>
                    <div class="portlet">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="fa time"></i>时间
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div class="row">
                                <div class="col-3">
                                    <p class="location_icon"></p>
                                    <div style="text-align:center">
                                        <span class="form-control-static">行程总时长: </span>
                                        <span class="form-control-static"
                                              id="pVT_TOTALTIME">${trackDetail.totalTimeText}</span>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="col-4" style="text-align:left">
                                        <p class="form-control-static"
                                           id="pVT_STARTTIME">${trackDetail.trackStartTime}</p>
                                        <p class="form-control-static">行程开始时间</p>
                                    </div>
                                    <div class="col-4">
                                        <p class="car"></p>
                                        <div style="text-align:center">
                                            <span class="form-control-static">怠速总时长: </span>
                                            <span class="form-control-static"
                                                  id="pVT_IDLETIME">${trackDetail.idleTimeText}</span>
                                        </div>
                                    </div>
                                    <div class="col-4" style="text-align:right">
                                        <p class="form-control-static"
                                           id="pVT_ENDTIME">${trackDetail.strokeEndTime}</p>
                                        <p class="form-control-static">行程结束时间</p>
                                    </div>
                                </div>
                                <div class="col-3">
                                    <p class="destination_icon"></p>
                                    <div style="text-align:center">
                                        <span class="form-control-static">行驶时长: </span>
                                        <span class="form-control-static"
                                              id="pVT_RUNTIME">${trackDetail.runTimeText}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="portlet">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="mileage"></i>里程<span
                                    style="font-size: 14px; color: #969696;">（单位：km）</span>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <div class="row">
                                <p style="font-size:16px">
                                    行程总里程数<span style="font-weight: bold; color: #2AADE0; "><span
                                        class="form-control-static"
                                        id="pVT_TOTALMILEAGE">${trackDetail.trackMileage}</span>
                km</span>,各分段行程里程数和所占比例如下：
                                </p>
                                <div class="col-8">
                                    <input type="hidden" id="VT_MILEAGE0020" value="${trackDetail.mileage0020}"/>
                                    <input type="hidden" id="VT_MILEAGE2040" value="${trackDetail.mileage2040}"/>
                                    <input type="hidden" id="VT_MILEAGE4060" value="${trackDetail.mileage4060}"/>
                                    <input type="hidden" id="VT_MILEAGE6090" value="${trackDetail.mileage6090}"/>
                                    <input type="hidden" id="VT_MILEAGE90120" value="${trackDetail.mileage90120}"/>
                                    <input type="hidden" id="VT_MILEAGE120" value="${trackDetail.mileage120}"/>
                                    <div id="mileagechar" style="height:220px"></div>
                                </div>
                                <div class="col-4">
                                    <div id="chartdiv" style="width: 100%; height: 220px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-8">
                            <div class="portlet">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="speed"></i>车速<span
                                            style="font-size: 14px; color: #969696;">（单位：km/h）</span>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-6">
                                            <div class="col-2">
                                                <p class="track_bg_1" style="float:left">平均速度</p>
                                            </div>
                                            <div class="col-10">
                                                <input type="hidden" id="VT_AVGSPEED"
                                                       value="${trackDetail.avgTrackSpeed}"/>
                                                <div id="avgspeedchar"
                                                     style="height: 175px; margin-top: -10px;"></div>
                                            </div>
                                        </div>
                                        <div class="col-6">
                                            <div class="col-2">
                                                <p class="track_bg_1" style="float:left;">最高速度</p>
                                            </div>
                                            <div class="col-10">
                                                <input type="hidden" id="VT_MAXSPEED"
                                                       value="${trackDetail.maxSpeed}"/>
                                                <div id="maxspeedchar"
                                                     style=" height:175px;margin-top:-10px;"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="portlet">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa icon_dai"></i>怠速详情
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="scroller" style="height: 175px;" data-always-visible="1"
                                         data-rail-visible="0">
                                        <table class="table table-bordered table-condensed table-striped table-hover"
                                               id="dtGrid"></table>
                                    </div>
                                </div>
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
                            <div class="portlet box "
                                 style="background-color: #DAA843; border :1px solid #DAA843; ">
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
                                                    <img src="img/trafficflux/track/img_alarmwater.png"
                                                         class="img-responsive"/>
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

            <%-- <div class="row">
                 <div class="col-12">
                     <table
                             class="table table-bordered table-condensed table-striped table-hover"
                             id="dtGrid" width="100%"></table>
                 </div>
             </div>--%>
        </div>
    </div>
</div>
</body>
</html>

<script src="content/js/historytrack.js" type="text/javascript"></script>
<%
    String trackStatus = (String) request.getAttribute("trackStatus");
    if (trackStatus.equals("2")) {
%>
<script type="text/javascript">
    $(function () {
        loadHistorytrack("${eqpId}", "${trackId}");
    });
</script>
<%
    }
%>






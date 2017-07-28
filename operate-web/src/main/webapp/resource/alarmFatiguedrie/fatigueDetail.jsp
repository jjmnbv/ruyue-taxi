<%@page import="com.szyciov.util.SystemConfig" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String yingyan_ak = SystemConfig.getSystemProperty("yingyan_ak");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>违规详情</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css"/>
    <link href="content/css/style_chewutong.css" rel="stylesheet" type="text/css"/>
    <link href="content/plugins/bootstrap-multiselect/dist/css/bootstrap-multiselect.css" rel="stylesheet"
          type="text/css"/>

    <style type="text/css">
        #map_canvas {
            width: 100%;
            height: 600px;
            overflow: hidden;
            margin: 0;
        }

        .form-control-static {
            padding-top: 0px
        }

        .btn.blue {
            color: white;
            text-shadow: none;
            background-color: #4d90fe;

        }

        #btnCancel {
            margin-top: -6px;
        }
    </style>

</head>
<body style="height:auto; overflow-y:scroll">
<div class="crumbs"><a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> >
    <a href="<%=basePath%>AlarmFatiguedrie/Index">疲劳驾驶 ></a>违规详情
    <button type="button" id="btnCancel" class="btn blue btn-md pull-right"><i class="fa fa-reply"
                                                                               style="width:auto;"></i>返回
    </button>
</div>
<div class="content">

    <input type="hidden" value="${id}" id="id">
    <div class="row">
        <div class="col-12">
            <div class="portlet box blue">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-reorder"></i>违规详情
                    </div>
                </div>
                <div class="portlet-body form">
                    <form class="form-horizontal" role="form">
                        <div class="form-body">
                            <h3 class="form-section">车辆信息</h3>
                            <div class="row">
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">车牌：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="pV_PLATES">
                                                ${queryFatigueDriving.plate}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <!--/span-->
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">IMEi：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="pV_PROPERTY">
                                                ${queryFatigueDriving.imei}

                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <!--/span-->
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">所属部门：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="pRC_DEPARTMENT">
                                                ${queryFatigueDriving.department}

                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <!--/span-->
                            </div>

                            <div class="row">
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">报警类型：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="pV_VEHCMODAL">
                                                ${queryFatigueDriving.alarmType}

                                            </p>
                                        </div>
                                    </div>
                                </div>
                                <!--/span-->
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">报警时间：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="pD_DIRVERNAME">
                                                ${queryFatigueDriving.alarmTime}
                                            </p>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">超时时长：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="pAFD_LENGTH">
                                                ${queryFatigueDriving.timeoutTime}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-4">
                                    <div class="form-group">
                                        <label class="control-label col-4">报警地点：</label>
                                        <div class="col-8">
                                            <p class="form-control-static" id="pAFD_LENGTH">
                                                ${queryFatigueDriving.alarmLocation}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <h3 class="form-section">违规详情</h3>
                            <div class="row">
                                <div class="col-12">
                                    <table class="table table-bordered  table-striped table-hover "
                                           id="dtGrid"></table>
                                </div>
                            </div>
                            <h3 class="form-section" id="hTrack" style="display:none">违规行程</h3>
                            <div class="row" id="map" style="display:none">
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
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>&s=1"></script>
<script type="text/javascript" src="js/vehicleTrajectory/directiontranslate.js"></script>
<script type="text/javascript" src="content/js/loading.js"></script>

<script type="text/javascript" src="js/alarmFatiguedrie/fatigueDriveDetail.js"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>
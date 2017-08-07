<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>怠速详情</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/font-awesome/css/font-awesome.min.css"/>
    <link href="content/css/style_chewutong.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .form-control-static{
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

    <script type="text/javascript" src="content/js/jquery.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        $(function () {
            $("#btnCancel").click(function () {
                location.href = basePath + "AlarmIdle/Index";
            })
        })

    </script>


</head>
<body style="height:auto; overflow-y:scroll">
<div class="crumbs"><a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a>>
    <a href="<%=basePath%>AlarmIdle/Index">怠速 ></a> 怠速详情
    <button type="button" id="btnCancel" class="btn blue btn-md pull-right"><i class="fa fa-reply" style="width:auto;"></i>返回</button>
</div>
<div class="content">
    <div class="portlet box blue">
        <div class="portlet-title">
            <div class="caption">
                <i class="fa fa-reorder"></i>怠速报警详情
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
                                        ${queryIdle.plate}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="control-label col-4">IMEI：</label>
                                <div class="col-8">
                                    <p class="form-control-static" id="IMEI">
                                        ${queryIdle.imei}

                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="control-label col-4">所属部门：</label>
                                <div class="col-8">
                                    <p class="form-control-static" id="pRC_DEPARTMENT">
                                        ${queryIdle.department}

                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">

                        <div class="col-4">
                            <div class="form-group">
                                <label class="control-label col-4">违规级别：</label>
                                <div class="col-8">
                                    <p class="form-control-static" id="pILLEGALLEVEL">
                                        ${queryIdle.timeRange}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="control-label col-4">开始时间：</label>
                                <div class="col-8">
                                    <p class="form-control-static" id="pAI_STARTTIME">
                                        ${queryIdle.startTime}

                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="control-label col-4">结束时间：</label>
                                <div class="col-8">
                                    <p class="form-control-static" id="pAI_ENDTIME">
                                        ${queryIdle.endTime}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4">
                            <div class="form-group">
                                <label class="control-label col-4">怠速时长：</label>
                                <div class="col-8">
                                    <p class="form-control-static" id="pAI_LENGTH">
                                        ${queryIdle.idleTime}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-4">
                                <div class="form-group">
                                    <label class="control-label col-4">地点（起点）：</label>
                                    <div class="col-8">
                                        <p class="form-control-static" id="pADDRESS">
                                            ${queryIdle.location}

                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <h3 class="form-section">处理信息</h3>
                    <div class="row">
                        <div class="col-4">
                            <div class="form-group">
                                <label class="control-label col-4">处理时间：</label>
                                <div class="col-8">
                                    <p class="form-control-static" id="pOPERATTIME">
                                        ${queryIdle.operateTime}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="control-label col-4">处理人：</label>
                                <div class="col-8">
                                    <p class="form-control-static" id="pOPERATOR">
                                        ${queryIdle.person}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="control-label col-4">核查结果：</label>
                                <div class="col-8">
                                    <p class="form-control-static" id="pAI_RESULT">
                                        ${queryIdle.oilCharge}
                                    </p>
                                </div>
                            </div>
                        </div>


                    </div>
                    <div class="row">
                        <div class="col-4">
                            <div class="form-group">
                                <label class="control-label col-4">核实人姓名：</label>
                                <div class="col-8">
                                    <p class="form-control-static" id="pVERIFYPERSON">
                                        ${queryIdle.person}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="control-label col-4">核实人电话：</label>
                                <div class="col-8">
                                    <p class="form-control-static" id="pVERIFYPERSONPHONE">
                                        ${queryIdle.phone}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label class="control-label col-4">核实人部门：</label>
                                <div class="col-8">
                                    <p class="form-control-static" id="pVCD_ID">
                                        ${queryIdle.department}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4">
                            <div class="form-group">
                                <label class="control-label col-4">怠速原因：</label>
                                <div class="col-8">
                                    <p class="form-control-static" id="pREASON">
                                        ${queryIdle.reason}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>

</html>

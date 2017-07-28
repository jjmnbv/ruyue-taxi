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
    <title>基础数据 | 温度趋势</title>
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
    .btn.blue {
        color: white;
        text-shadow: none;
        background-color: #4d90fe;

    }

    .glyphicon-arrow-left::before {
        content: "<";
    }

    .glyphicon-arrow-right::before {
        content: ">";
    }

    .form-horizontal .control-label {
        padding-top: 12px;
    }

    .asterisk:after {
        padding: 0;
    }
</style>


</head>
<body style="overflow:hidden;">
<div class="crumbs">
    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a>
    <a href="<%=basePath%>AlarmWaterTemp/Index">>水温报警</a> >温度趋势详情
</div>
<!-- 头部 -->
<div class="content">

    <form class="form-horizontal" role="form">
        <input type="hidden" id="id" value="${id }"/>
        <div class="portlet box blue">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-reorder"></i>温度报警详情
                </div>
            </div>
        </div>
        <div class="form-body">
            <h3 class="form-section">车辆信息</h3>
            <div class="row">
                <div class="col-4">
                    <div class="form-group">
                        <label class="control-label col-4">车牌：</label>
                        <div class="col-8">
                            <p class="form-control-static" id="plate">

                            </p>
                        </div>
                    </div>
                </div>
                <!--/span-->

                <!--/span-->
                <div class="col-4">
                    <div class="form-group">
                        <label class="control-label col-4">所属车企：</label>
                        <div class="col-8">
                            <p class="form-control-static" id="department">

                            </p>
                        </div>
                    </div>
                </div>
                <!--/span-->
                <div class="col-4">
                    <div class="form-group">
                        <label class="control-label col-4">报警时间：</label>
                        <div class="col-8">
                            <p class="form-control-static" id="alarmTime">

                            </p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">

                <div class="col-4">
                    <div class="form-group">
                        <label class="control-label col-4">解除时间：</label>
                        <div class="col-8">
                            <p class="form-control-static" id="releaseTime">

                            </p>
                        </div>
                    </div>
                </div>

                <div class="col-4">
                    <div class="form-group">
                        <label class="control-label col-4">最高温度：</label>
                        <div class="col-8">
                            <p class="form-control-static" id="maxTemp"></p>
                        </div>
                    </div>
                </div>
            </div>


            <!--/row-->
            <h3 class="form-section">水温趋势</h3>
            <div id="placeholder" style="min-height:300px" class="chart">
            </div>
        </div>
    </form>

</div>
<!-- 尾部 -->
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
<script type="text/javascript" src="content/plugins/flot/jquery.flot.js"></script>
<script type="text/javascript" src="content/plugins/flot/jquery.flot.time.js"></script>
<script type="text/javascript" src="content/plugins/flot/jquery.flot.resize.js"></script>

<script type="text/javascript" src="js/alarmWaterTemp/directiontranslate.js"></script>
<script type="text/javascript" src="js/alarmWaterTemp/showDetail.js?v=1.6"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>
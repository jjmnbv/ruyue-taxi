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
    <title>围栏管理</title>
    <base href="<%=basePath%>">
    <style type="text/css">
        .paging_bootstrap_full_number {
            float: right;
        }
    </style>
</head>

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
<link href="content/css/style_chewutong.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="content/plugins/bmap/css/DrawingManager.css"/>
<style type="text/css">
    #map_canvas {
        width: 100%;
        height: 600px;
        overflow: hidden;
        margin: 0;
    }
</style>
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
    .form-horizontal .form-group{
        margin-left: 5px;
        margin-right: 5px;
    }
    #btnCancel1{
        margin-top: -6px;
    }

</style>

</head>
<div class="crumbs">
    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >
    <a href="<%=basePath%>SetElectronicFence/Index">电子围栏设置 ></a>围栏管理
    <button type="button" id="btnCancel1" class="btn blue btn pull-right"><i class="fa fa-reply"
                                                                            style="width:auto;"></i>返回
    </button>
</div>

<div class="content">

    <input type="hidden" id="id" name="id" value="${id}">

    <div class="row margin-top-10">
        <div class="col-10">
            <div id="map_canvas">
            </div>
            <div id="result">
                <input type="button" value="清除所有多边形" onclick="clearAll()"/>
            </div>
        </div>
        <div class="col-2">
            <div class="portlet-body form">
                <!-- BEGIN FORM-->
                <form id="frmmodal" action="#" class="form-horizontal" role="form">
                    <div class="form-body">
                        <div class="row margin-top-10">
                            <div class="form-group">
                                <label class="control-label ">围栏名称<span class="required" style="color: red">*</span></label>
                            </div>
                        </div>
                        <div class="row margin-top-10">
                            <div class="form-group">
                                <input type="text" class="form-control " id="name" name="name" required="required"
                                       placeholder="请输入围栏名称" style="width: 100%"/>
                            </div>
                        </div>
                        <div class="row margin-top-10">
                            <div class="form-group">
                                <label class="control-label">报警类型 <span class="required" style="color: red">*</span></label>
                            </div>
                        </div>
                        <div class="row margin-top-10">
                            <div class="form-group">
                                <select class="form-control" id="alartType" name="alartType" required="required">
                                    <option value="">请选择</option>

                                    <c:forEach items="${alartTypeList }" var="alartType">
                                        <option value="${alartType.Value }">${alartType.Text }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-foot">
                        <div class="row margin-top-10">
                            <div class="col-12">
                                <div class="col-6">
                                    <button type="submit" class="btn blue"><i class="fa fa-pencil"></i> 保存</button>
                                </div>
                                <div class="col-6">
                                    <button type="button" id="btnCancel" class="btn default "><img
                                            src="img/trafficflux/icon/shutdown_ga.png"/> 取消
                                </div>
                            </div>
                        </div>
                        <div class="row margin-top-10">
                            <div class="alert alert-block alert-info fade in ">
                                <h4 class="alert-heading">小提示!</h4>
                                <p>
                                    1、画围栏之前，可以拖动地图，及使用鼠标滚轮放大缩小地图到需要的位置后，再开始画围栏；
                                </p>
                                <p>
                                    2、选中需要的图形类型,点鼠标左键开始画围栏、双击鼠标左键结束画围栏、围栏自动闭合；
                                </p>
                                <p>
                                    3、围栏使用的是封闭多边形，各个点依次连接，各个边不要有交叉；
                                </p>
                                <p>
                                    4、围栏可在合理范围内适当设置的大一点，减少误报的可能！
                                </p>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
           

</div>
<!-- 尾部 -->
</body>
<!-- 公用JS -->
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
<script type="text/javascript" src="content/plugins/bootstrap-typeahead/bootstrap-typeahead.min.js"></script>
<script type="text/javascript" src="content/plugins/ion.rangeslider/js/ion-rangeSlider/ion.rangeSlider.js"></script>
<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=AFLc9FVyIHUWExKYFETDeF6T&s=1"></script>
<script type="text/javascript" src="content/plugins/bmap/DistanceTool.js"></script>
<script type="text/javascript" src="content/plugins/bmap/DrawingManager.js"></script>
<script type="text/javascript" src="js/setElectronicFence/edit.js"></script>
<script type="text/javascript" src="js/setElectronicFence/directiontranslate.js"></script>
<script>
    $(function () {
        $("#btnCancel1").click(function () {
            location.href = "AreaFenceSet/Index";
        })
    })

</script>
<script>
    var basePath = "<%=basePath%>";
</script>






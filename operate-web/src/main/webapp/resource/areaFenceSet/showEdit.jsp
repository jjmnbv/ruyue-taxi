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
    <title>基础数据 | 区域栅栏设置</title>
    <base href="<%=basePath%>">
    <style type="text/css">

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
    <link href="content/css/loading.css" rel="stylesheet" type="text/css"/>

    <style type="text/css">
        .paging_bootstrap_full_number {
            float: right;
        }
        .btn.blue {
            color: white;
            text-shadow: none;
            background-color: #4d90fe;

        }

        .form-horizontal .control-label {
            text-align: left;
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

    </style>
</head>
<body>
<div class="crumbs">
    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >
    <a href="<%=basePath%>AreaFenceSet/Index">区域栅栏设置</a>> 区域栅栏管理
</div>
<div class="content">

    <input type="hidden" id="id" name="id" value="${id}">

    <div class="row">
        <div class="col-12">
            <div class="portlet box blue">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-reorder"></i>区域栅栏设置
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
                                            <input class="form-control" type="text" id="txtName"
                                                   name="txtName" required="required" maxlength="20"
                                                   placeholder="栅栏名称不超过20个字符"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <label class="control-label">
                                            允许运行城市
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <div class="col-md-2">
                                            <div class="scroller" style="height: 300px; overflow: auto"
                                                 data-rail-visible="1" data-handle-color="#a1b2bd">
                                                <div class="zTreeDemoBackground  left">
                                                    <ul class="ztree" id="cityList"></ul>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <textarea class="form-control" cols="75" rows="6" style="height: 300px"
                                                      readonly id="citySel"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <div class="col-6">
                                    <button type="submit" class="btn blue"><i class="fa fa-pencil"></i> 保存
                                    </button>
                                    <button type="button" id="btnCancel" class="btn default"><img
                                            src="img/trafficflux/icon/shutdown_ga.png"/> 取消
                                    </button>
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
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="content/js/common.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
<script type="text/javascript" src="content/js/jquery.combotree.js"></script>
<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript" src="js/basecommon.js"></script>
<script type="text/javascript" src="content/plugins/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="content/plugins/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="content/js/loading.js"></script>
<script type="text/javascript" src="js/areaFenceSet/showEdit.js?v=1.6"></script>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>

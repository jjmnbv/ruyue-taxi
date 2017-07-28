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
    <title>怠速</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css"/>


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
    </style>

</head>
<body style="height:auto; overflow-y:scroll">
<div class="crumbs"><a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > 怠速</div>
<div class="content">
    <div class="form-horizontal">
        <div class="row ">
            <div class="col-4">
                <div class="form-group">
                    <label class="control-label col-4">服务车企</label>
                    <div class="col-8">
                        <select id="company">
                            <option value="">全部</option>
                            <c:forEach items="${opUserCompany }" var="state">
                                <option value="${state.value }">${state.text }</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="form-group">
                    <label class="control-label col-4">开始时间起</label>
                    <div class="col-8">
                        <input type="text" class="form-control  datetimepicker"
                               name="startTime" id="startTime" readonly="readonly"/>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="form-group">
                    <label class="control-label col-4"> 开始时间止 </label>
                    <div class="col-8">
                        <input type="text" class="form-control  datetimepicker"
                               name="startTimeStop" id="startTimeStop" readonly="readonly"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row ">

            <div class="col-md-4">
                <div class="form-group">
                    <label class="control-label col-md-4">车牌</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" name="plate"
                               id="plate"/>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="form-group">
                    <label class="control-label col-md-4">IMEI</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" name="imei"
                               id="imei"/>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="form-group">
                    <label class="control-label col-md-4 ">时长范围</label>
                    <div class="col-md-8">
                        <select class="form-control" id="timeRange" name="timeRange">
                            <option value="">请选择</option>
                            <c:forEach items="${timeList }" var="timeRange">
                                <option value="${timeRange.Value }">${timeRange.Text }</option>
                            </c:forEach>

                        </select>
                    </div>
                </div>
            </div>



        </div>

        <div class="row">
            <div class="col-md-8" style="margin-left:33%;">
                <div class="pull-right">
                    <button type="button" class="btn btn-default blue "
                            id="btnSearch">
                        <img src="img/trafficflux/icon/seacrch.png" alt=""/>查询
                    </button>
                    <button type="button" class="btn btn-default blue"
                            id="btnClear">
                        <img src="img/trafficflux/icon/refresh.png" alt=""/>重置
                    </button>
                    <button type="button" class="btn btn-default  blue"
                            id="btnExport">
                        <img src="img/trafficflux/icon/export.png"/>导出
                    </button>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-12">
                <table
                        class="table table-bordered table-condensed table-striped table-hover"
                        cellspacing="0" id="dtGrid" width="100%"></table>
            </div>
        </div>
    </div>

    <div class="modal fade" id="mdAdd" tabindex="-1" role="dialog"
         aria-hidden="true" data-backdrop="static"
         style="min-height: 350px">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="frmmodal" action="#" class="form-horizontal"
                      role="form">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true"></button>
                        <h4 class="modal-title">怠速报警处理</h4>
                        <input class="form-control" id="id" type="hidden" name="id"/>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="control-label col-md-3">核查结果<span style="color: red" class="required">*</span></label>
                            <div class="col-md-8">
                                <!-- <select class="form-control " id="selResult" style="width:100%" required="required"></select> -->
                                <select name="oilCharge" id="oilCharge" class="form-control">
                                    <option value="">请选择</option>
                                    <c:forEach items="${resultList}" var="item">
                                        <option value="${item.Value}">${item.Text}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-md-3"> 核实人姓名<span
                                    class="required" style="color: red">*</span>
                            </label>
                            <div class="col-md-8">
                                <input name="verifyPerson" class="form-control"
                                       id="verifyPerson" maxlength="8" required="required"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3"> 核实人电话 </label>
                            <div class="col-md-8">
                                <input name="verifyPersonTel" title="请输入正确格式的手机号"
                                       class="form-control" id="verifyPersonTel" type="text"
                                       placeholder="手机号格式：13714563657" maxlength="11" pattern="1[358][0-9]{9}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3"> 核实人车企 </label>
                            <div class="col-md-8">

                                <select id="company">
                                    <option value="">全部</option>
                                    <c:forEach items="${opUserCompany }" var="state">
                                        <option value="${state.value }">${state.text }</option>
                                    </c:forEach>
                                </select>

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3"> 处理人姓名 </label>
                            <div class="col-md-8">
                                <input class="form-control" id="operateId" name="operateId" type="hidden">
                                <input class="form-control" type="text" id="operateStaff"
                                       name="operateStaff" maxlength="8"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3"> 怠速原因<span
                                    class="required">*</span>
                            </label>
                            <div class="col-md-8">
												<textarea name="idleReason" class="form-control"
                                                          id="idleReason" maxlength="100" rows="3" required="required"
                                                          placeholder="最多输入100个字说明"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="pull-right">
                            <button type="submit" class="btn blue" id="submit">
                                <i class="fa fa-pencil"></i> 提交
                            </button>
                            <button type="button" class="btn default"
                                    data-dismiss="modal">
                                <img src="img/trafficflux/icon/shutdown_ga.png"/>
                                取消
                            </button>
                        </div>
                    </div>
                </form>
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
<script type="text/javascript" src="content/js/loading.js"></script>
<script type="text/javascript" src="js/alarmIdle/index.js?v=1.2"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>
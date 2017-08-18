<%@ page contentType="text/html; charset=UTF-8" import="com.szyciov.util.DateUtil"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <title>战略伙伴</title>
    <base href="<%=basePath%>" >
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="content/css/style.css" />
    <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
    <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.css" />
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>

    <script type="text/javascript" src="content/js/jquery.js"></script>
    <script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
    <script type="text/javascript"  src="content/js/common.js"></script>
    <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
    <script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
    <script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
    <script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
    <script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
    <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
    <script type="text/javascript" src="content/plugins/select2/select2.js"></script>
    <script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
    <script type="text/javascript" src="content/plugins/select2/app.js"></script>
    <script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="js/basecommon.js"></script>
</head>
<style type="text/css">
    /* 前端对于类似页面的补充 */
    .form select{width:68%;}
    .select2-container .select2-choice{height:30px;}
    .select2-container{width:68%;padding:0px;}
    .dataTables_wrapper{margin: 0px 0px 10px 0px;}
    .breadcrumb{text-decoration:underline;}

    .tip_box_b label{float:left;line-height: 30px;height:30px;}
    .tip_box_b select,.tip_box_b input[type=text]{width:63%;float:left;margin-top: 0px;}
    .tip_box_b label.error {padding-left: 0%;margin-left: 30%!important;}

    th, td { white-space: nowrap; }
    div.dataTables_wrapper {
        width: $(window).width();
        margin: 0 auto;
    }
    .DTFC_ScrollWrapper{
        margin-top:-20px;
    }
    #dataGrid_wrapper .DTFC_LeftBodyWrapper tbody tr td:first-child{
        text-align:left!important;
    }
</style>
<body class="leLeasescompany_css_body" style="overflow: hidden">
<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 战略伙伴
</div>
<div class="content">
    <div class="form" style="padding-top: 30px;">
        <div class="row">
            <div class="col-3">
                <label>合作编号</label>
                <input id="coono" type="text" placeholder="请输入合作编号">
            </div>
            <div class="col-3">
                <label>战略伙伴</label>
                <input id="leasecompany" type="text" placeholder="全部" style="width: 66%">
            </div>
            <div class="col-3">
                <label>加盟业务</label>
                <select id="servicetype">
                    <option value="">全部</option>
                    <option value="0">网约车</option>
                    <option value="1">出租车</option>
                </select>
            </div>
            <div class="col-3">
                <label>合作状态</label>
                <select id="coostate">
                    <option value="">全部</option>
                    <option value="0">待审核</option>
                    <option value="2">未达成</option>
                    <option value="1">合作中</option>
                    <option value="3">已终止</option>
                    <option value="4">已过期</option>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-6">
                <label style="width: 15%">申请时间</label>
                <input style="width:100px;margin-left: -3px" id="startTime" name="startTime" type="text" placeholder="开始日期" class="searchDate" readonly="readonly">
                至
                <input style="width:100px;" id="endTime" name="endTime" type="text" placeholder="结束日期"  class="searchDate" readonly="readonly">
            </div>
            <div class="col-6" style="text-align: right;">
                <button id="search" class="Mbtn green_a">查询</button>
                <button class="Mbtn grey_w" onclick="clearSearchBox();">清空</button>
            </div>
        </div>
    </div>
    <br/>
    <div class="row">
        <div class="col-4">
            <h4>战略伙伴信息</h4>
        </div>
    </div>
    <table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
</div>
<div class="pop_box" id="reviewPop" style="display: none;">
    <div class="tip_box_b">
        <h3 id="titleForm">审核</h3>
        <img src="content/img/btn_guanbi.png" class="close" onclick="closereviewPop()" alt="关闭">
        <div class="w400">
            <form method="get" class="form-horizontal  m-t" id="frmmodal">
                <div class="row">
                    <div class="col-12" style="text-align:left;margin-top: 15px;font-size: 17px;">
                        <input type="radio" name="reviewRadio" value="1">同意联盟
                    </div>
                    <div class="col-12" style="text-align:left;">
                        <span style="float: left;margin-left: 50px;padding-right: 15px;line-height: 30px;">申请时间</span>
                        <input style="width:100px;margin-left: -3px" id="validateTimeStart" disabled="disabled" type="text" placeholder="开始日期" value="<%=DateUtil.format(new Date(), "yyyy-MM-dd")%>" class="searchDate" readonly="readonly">
                        <span style="float: left;padding: 4px;">-</span>
                        <input style="width:100px;" id="validateTimeEnd" type="text" placeholder="结束日期"  class="searchDate" readonly="readonly">
                    </div>
                </div>
                <div class="row">
                    <div class="col-12" style="text-align:left;">
                        <input type="radio" name="reviewRadio" value="0">拒绝联营
                    </div>
                    <div class="col-12" style="text-align:left;">
                        <textarea rows="3" id="reviewResion" style="width: 70%;margin: 0px 50px;" placeholder="请输入拒绝原因" maxlength="50"></textarea>
                    </div>
                    <div class="col-12" id="reviewResionTip" style="text-align: left;margin-left: 60px;padding: 0px;color: red;margin-top: -10px;display: none">
                        请输入拒绝原因
                    </div>
                </div>
            </form>
            <button id="reviewPopConfirmBtn" class="Lbtn red" onclick="reviewPopConfirm()">确定</button>
            <button class="Lbtn grey" style="margin-left: 10%;" onclick="closereviewPop()">取消</button>
        </div>
    </div>
</div>
<script type="text/javascript" src="js/pubCoooperatePartner/index.js"></script>
<script type="text/javascript">
    var base = document.getElementsByTagName("base")[0].getAttribute("href");
</script>
</body>
</html>

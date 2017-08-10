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
    <title>申请合作</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>

    <script type="text/javascript" src="content/js/jquery.js"></script>
    <script type="text/javascript" src="content/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="content/js/common.js"></script>
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
    <script type="text/javascript" src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="js/basecommon.js"></script>
</head>
<style type="text/css">
    /* 前端对于类似页面的补充 */
    .form select {
        width: 68%;
    }
    .pop_box{z-index: 1111 !important;}

    .step {
        background-repeat: no-repeat;
        height: 90px;
        width: 130px;
        background-position-x: center;
        background-position-y: center;
    }

    .s1 {
        background-image: url("img/pubCoooperate/image_01_def.png");
    }

    .s1.active {
        background-image: url("img/pubCoooperate/image_01_set.png");
    }

    .s2 {
        background-image: url("img/pubCoooperate/image_02_def.png");
    }

    .s2.active {
        background-image: url("img/pubCoooperate/image_02_set.png");
    }

    .s3 {
        background-image: url("img/pubCoooperate/image_03_def.png");
    }

    .s3.active {
        background-image: url("img/pubCoooperate/image_03_set.png");
    }

    .s4 {
        background-image: url("img/pubCoooperate/image_04_def.png");
    }

    .s4.active {
        background-image: url("img/pubCoooperate/image_04_set.png");
    }

    .jiantou {
        background-image: url("img/pubCoooperate/icon_jiantou8.png");
        background-repeat: no-repeat;
        background-position-x: center;
        background-position-y: 45px;
        height: 90px;
        width: 70px;
    }

    ul.header li {
        line-height: 90px;
        float: left;
        text-align: center;
        margin-right: 2px;
        color: #fff;
        clear: none;
    }


    ul.header {
        margin-left: auto;
        margin-right: auto;
        width: 750px;
        height: 90px;
    }

    .Mbtn.white {
        color: #666;
        background-color: white;
        border: 1px solid #999;
    }

    .Mbtn.white:hover {
        opacity: 1;
    }

    table tr th {
        text-align: center
    }

    #step4 .table > tbody > tr > td{
        border: none;
    }

    #step4 .table > tbody > tr > td:nth-child(odd){
        text-align: right;
    }

    #step4 .table > tbody > tr > td:nth-child(even){
        text-align: left;
    }

    .hidden{
        display: none;
    }
</style>
<body class="leLeasescompany_css_body">
<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >
    <a class="breadcrumb" href="javascript:void(0);" onclick="back()">合作运营管理</a> >
    申请合作
    <button class="SSbtn red back" onclick="backStep()">返回</button>
</div>
<div class="content">
    <div class="row" style="padding-top: 30px;">
        <ul class="header">
            <li class="step s1 active"></li>
            <li class="jiantou"></li>
            <li class="step s2"></li>
            <li class="jiantou"></li>
            <li class="step s3"></li>
            <li class="jiantou"></li>
            <li class="step s4"></li>
        </ul>
    </div>
    <div id="step1" class="form"
         style="padding-top: 70px;width: 750px;margin-left: auto;margin-right: auto;">
        <div class="row">
            <div class="col-12">
                <label style="width: 80px">合作类型</label>
                <select id="cootype" placeholder="请选择合作类型" style="width: 650px">
                    <option value="">请选择合作类型</option>
                    <option value="0">B2B联盟</option>
                    <option value="1">B2C联营</option>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-12" style="color: red">
                <div id="cootypeError" class="hidden" style="float: right;font-size: 12px;margin-top: -13px;margin-right: 25px;">请选择合作类型</div>
            </div>
        </div>
        <div class="row" style="margin-top: 30px">
            <div class="col-12">
                <label style="width: 80px">合作方</label>
                <input id="leaseCompanyName" type="text" placeholder="请输入合作车企全称" style="width: 650px">
            </div>
        </div>
        <div class="row hidden" id="leaseCompanyNameError2">
            <div class="col-12" style="color: red">
                <div  class="" style="float: right;font-size: 12px;margin-top: -13px;margin-right: 25px;">请输入合作车企全称</div>
            </div>
        </div>
        <div class="row hidden" id="leaseCompanyNameError">
            <div class="col-12" style="color: red">
                <div  class="" style="float: right;font-size: 12px;margin-top: -13px;margin-right: 25px;">您输入的合作方不存在</div>
            </div>
        </div>
        <div class="row" style="margin-top: 30px">
            <div class="col-12">
                <label style="width: 80px">加盟业务</label>
                <select id="servicetype" placeholder="请选择加盟业务" style="width: 650px">
                    <option value="">请选择加盟业务</option>
                    <option value="0">网约车</option>
                    <option value="1">出租车</option>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-12" style="color: red">
                <div id="servicetypeError" class="hidden" style="float: right;font-size: 12px;margin-top: -13px;margin-right: 25px;">请选择加盟业务</div>
            </div>
        </div>
        <div class="row" style="margin-top: 40px">
            <div style="width: 300px;margin-left: auto;margin-right: auto">
                <button class="Mbtn red" type="button" style="margin-right: 80px" onclick="goStep2()">下一步</button>
                <button class="Mbtn white" type="button" onclick="goBack()">取消</button>
            </div>
        </div>
    </div>
    <div id="step2" class="form hidden" style="padding-top: 70px;">
        <div class="row">
            <div class="col-4">
                <h4>开放资源信息</h4>
            </div>
            <div class="col-8">
                <button id="step2EditBtn" class="Sbtn green" type="button" style="float: right;margin-right: 5px;" onclick="vehiclelPopup()">添加</button>
            </div>
        </div>
        <table id="selectedTable" class="table table-striped table-bordered" cellspacing="0" width="100%" selectedId="">
            <thead>
                <tr>
                    <th>车牌号</th>
                    <th>品牌车系</th>
                    <th>服务车型</th>
                    <th>资格证号</th>
                    <th>司机信息</th>
                    <th>登记城市</th>
                </tr>
            </thead>
            <tbody id="selectedResourceTbody">

            </tbody>
        </table>
        <div class="row" style="margin-top: 40px">
            <div style="width: 300px;margin-left: auto;margin-right: auto">
                <button class="Mbtn red" type="button" style="margin-right: 80px" onclick="goStep3()">下一步</button>
                <button class="Mbtn white" type="button" onclick="goBack()">取消</button>
            </div>
        </div>
    </div>
    <div id="step3" class="form hidden" style="padding-top: 70px;margin: 0px 15px;">
        <iframe  id="cooagreementIframe" name="iframe" frameborder=no height="550px" width="100%" ></iframe>

        <div class="row">
            <div style="margin-top: 10px;margin-left: 25px;">
                <input id="cooagreementCheck" type="checkbox">我已阅读并同意该协议
            </div>
        </div>
        <div class="row" style="margin-top: 40px">
            <div style="width: 300px;margin-left: auto;margin-right: auto">
                <button class="Mbtn" id="step3ConfirmBtn" type="button" style="margin-right: 80px" onclick="goStep4()">提交</button>
                <button class="Mbtn white" type="button" onclick="goBack()">取消</button>
            </div>
        </div>
    </div>
    <div id="step4" class="form hidden" style="padding-top: 70px;margin: 0px 15px;text-align: center;">
        <img style="margin-bottom: 15px" src="img/pubCoooperate/icon_ok.png">
        <div style="margin-bottom: 10px;font-size: 12px;color: #999">您的申请已经提交成功，请耐心等待。</div>
        <table class="table" style="margin:auto;width:40%;">
            <tr>
                <td style="text-align: right;width: 30%">合作编号</td>
                <td class="" id="coono4"></td>
            </tr>
            <tr>
                <td style="text-align: right">合作类型</td>
                <td class="" id="cootype4"></td>
            </tr>
            <tr>
                <td style="text-align: right">合作方</td>
                <td class="" id="leaseCompanyName4"></td>
            </tr>
            <tr>
                <td style="text-align: right">加盟业务</td>
                <td class="" id="servicetype4"></td>
            </tr>
        </table>
    </div>
</div>

<div class="pop_box" id="vehiclelPop">
    <div class="tip_box_a" style="width:1200px;margin:60px auto auto auto;">
        <div class="row form">
            <div class="col-4">
                <label class="col-3" style="text-align:right;">品牌车系</label>
                <input id="vehclineid" type="text" placeholder="全部" style="width: 68%;text-align: left;height: 33px">
            </div>
            <div class="col-4" id="vehiclemodelsdiv">
                <label class="col-3" style="text-align:right;">服务车型</label>
                <select id="vehiclemodels" style="width: 68%;" placeholder="服务车型">
                    <option value="">全部</option>
                </select>
            </div>
            <div class="col-4">
                <label class="col-3" style="text-align:right;">资格证号</label>
                <input id="jobnum" type="text" placeholder="资格证号" style="width: 68%"/>
            </div>
            <div class="col-4">
                <label class="col-3" style="text-align:right;">登记城市</label>
                <select id="cityaddrid" style="width: 68%;">
                    <option value="">全部</option>
                </select>
            </div>
            <div class="col-4">
                <label class="col-3" style="text-align:right;">车牌号</label>
                <input id="fullplateno" type="text" placeholder="车牌号" style="width: 68%" maxlength="7"/>
            </div>
            <div class="col-4" style="float: right">
                <button class="Mbtn grey_w" onclick="clearParameter();" style="float: right;">清空</button>
                <button class="Mbtn green_a" style="float: right;margin-right: 12px;" type="button" onclick="searchGridResource()">查询</button>
            </div>
        </div>
        <div class="row">
            <div class="col-4">
                <h4 style="text-align: left ;   margin-bottom: -15px;font-size: 16px;">已选择车<span id="selectVeCount">0</span>辆</h4>
            </div>
        </div>
        <div class="row">
            <div  class="col-12" >
                <table id="resourceSelect" class="table table-bordered" cellspacing="0" width="100%"></table>
            </div>
        </div>
        <div class="row" style="margin:auto;">
            <div  class="col-12" style="text-align: right;">
                <button class="Mbtn blue confirmPop" type="button">保存</button>
                <button class="Mbtn red closePop" type="button">关闭</button>
            </div>
        </div>
    </div>
</div>




</div>
<script type="text/javascript" src="js/pubCoooperateUnion/applyCoooperate.js"></script>
<script type="text/javascript">
    var base = document.getElementsByTagName("base")[0].getAttribute("href");
</script>
</body>
</html>

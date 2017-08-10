<%@ page contentType="text/html; charset=UTF-8" import="com.szyciov.util.SystemConfig"%>
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
		<title>资源信息</title>
		<base href="<%=basePath%>" >
        <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
        <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
        <link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css" />
        <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="content/plugins/ztimepicker/ztimepicker.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="content/plugins/cityselect/cityselect.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="content/css/style.css" />
        <link rel="stylesheet" type="text/css" href="css/order/che_xing.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/order/address.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="content/css/laterchange.css" />

        <script type="text/javascript" src="content/js/jquery.js"></script>
        <script type="text/javascript" src="content/js/common.js"></script>
        <script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
        <script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
        <script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
        <script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
        <script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
        <script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
        <script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.min.js"></script>
        <script type="text/javascript" src="content/plugins/ztimepicker/ztimepicker.js"></script>
        <script type="text/javascript" src="content/plugins/cityselect/cityselect.js"></script>
        <script type="text/javascript" src="content/plugins/select2/select2.js"></script>
        <script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
        <script type="text/javascript" src="content/plugins/select2/app.js"></script>
        <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
        <script type="text/javascript" src="js/basecommon.js"></script>
		
		<style type="text/css">

            div.dataTables_wrapper div.dataTables_info{
                text-align: left;
            }

            .select2-chosen{
                float: left;
            }
		</style>
		

	</head>
	<body coooId="${coooId}" servicetype="${cooo.servicetype}">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >
            <a class="breadcrumb" href="PubCoooperateUnion/Index">合作运营管理</a> >
            <span id="titleText">查看资源</span>
			<button class="SSbtn blue back" onclick="window.history.go(-1);">返回</button>
		</div>
		<div class="content">
			<div class="row">
			    <div class="col-6" style="margin-top: 15px;"><h4>车辆信息</h4></div>
                <div class="col-6"><button id="addBtn" type="button" class="hidden Mbtn green_a" style="float: right" onclick="pop()"><i class="fa fa-paste"></i>编辑</button></div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>

        <div class="pop_box" id="vehiclelPop">
            <div class="tip_box_a" style="width:1200px;margin:60px auto auto auto;">
                <div class="row form">
                    <div class="col-3">
                        <label class="col-3" style="text-align:left;">品牌车系</label>
                        <input id="vehclineid" type="text" placeholder="品牌车系">
                    </div>
                    <div class="col-3">
                        <label class="col-3" style="text-align:left;">服务车型</label>
                        <select id="vehiclemodels" style="width: 68%;" placeholder="服务车型">
                            <option value="">全部</option>
                        </select>
                    </div>
                    <div class="col-3">
                        <label class="col-3" style="text-align:left;">服务状态</label>
                        <select id="workstatus" style="width: 68%;">
                            <option value="">全部</option>
                            <option value="2">下线</option>
                            <option value="0">空闲</option>
                            <option value="1">服务中</option>
                        </select>
                    </div>
                    <div class="col-3">
                        <label class="col-3" style="text-align:left;">登记城市</label>
                        <select id="cityaddrid" style="width: 68%;">
                            <option value="">全部</option>
                        </select>
                    </div>
                </div>
                <div class="row form">
                    <div class="col-3">
                        <label class="col-3" style="text-align:left;">车牌号码</label>
                        <input id="fullplateno" type="text" placeholder="车牌号码"/>
                    </div>
                    <div class="col-3">
                        <label class="col-3" style="text-align:left;">资格证号</label>
                        <input id="jobnum" type="text" placeholder="资格证号" style="width: 68%"/>
                    </div>
                    <div class="col-6" >
                        <button class="Mbtn grey_w" onclick="clearParameter();" style="float: right;">清空</button>
                        <button class="Mbtn green_a" style="float: right;margin-right: 12px;" type="button" onclick="searchGridResource()">查询</button>
                    </div>
                </div>
                <div class="row">
                    <div  class="col-12" >
                        <table id="resourceSelect" class="table table-bordered" cellspacing="0" width="100%"></table>
                    </div>
                </div>
                <div class="row" style="margin:auto;">
                    <div  class="col-12" style="text-align: right;">
                        <button class="Mbtn blue confirmPop" type="button">确定</button>
                        <button class="Mbtn red closePop" type="button">关闭</button>
                    </div>
                </div>
            </div>
        </div>
		<script type="text/javascript" src="js/pubCoooperateUnion/updateResource.js"></script>
		<script type="text/javascript">
			var base = document.getElementsByTagName("base")[0].getAttribute("href");

            $(document).ready(function () {
                if("${query}" == "add"){
                    $("#titleText").text("调整资源");
                    $("#addBtn").removeClass("hidden");
                }

            });
		</script>
	</body>
</html>

<%@ page contentType="text/html; charset=UTF-8"%>
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
		<title>乘客投诉</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<style type="text/css">
			.form select{width:68%;}
			.dataTables_wrapper{margin: 0px 0px 10px 0px;}
			.breadcrumb{text-decoration:underline;}
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.DTFC_ScrollWrapper{
				margin-top:-20px;
			}
			/*样式修改*/
			.table-bordered thead tr th{ 
		        border-bottom-width: 1px; 
		    }
		    .crumbs .back{
		    	position: relative;
		    	top:2px;
		    }
            .tabmenu{margin-bottom:10px;}
            .breadcrumb{text-decoration:underline;}
            .stab{border-bottom:2px solid #ededed;}
            .stab>div{display:inline-block;padding:4px 14px;text-align:center;}
            .shen_on{background:#ededed;}
            th, td { white-space: nowrap; }
            div.dataTables_wrapper {
                width: $(window).width();
                margin: 0 auto;
            }

            .hidden{
                display: none;
            }
		</style>
	</head>
	
	<body style="overflow: hidden">
    <div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页 </a>>乘客投诉</div>
    <div class="content">
        <div style="padding-top: 30px;">
            <ul class="tabbox">
                <li style="display:block">
                    <div class="stab">
                        <div><a href="PubComplaintManage/Index" style="text-decoration: none;">待处理投诉</a></div>
                        <div class="shen_on">已处理投诉</div>
                    </div>
                    <div class="stabox">
                        <div class="row">
                            <div class="col-3">
                                <span class="col-4" style="text-align: right"><label>订单编号</label></span>
                                <input id="orderno" type="text" placeholder="订单编号" style="width: 60%">
                            </div>
                            <div class="col-3">
                                <span class="col-4" style="text-align: right"><label>下单人信息</label></span>
                                <input id="userid" type="text" placeholder="下单人姓名/手机号" style="width: 60%">
                            </div>
                            <div class="col-3">
                                <label>资格证号</label>
                                <input id="jobnum" type="text" placeholder="资格证号" style="width: 60%">
                            </div>
                            <div class="col-3">
                                <label>司机信息</label>
                                <input id="driverid" type="text" placeholder="司机姓名/手机号" style="width: 60%">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-3">
                                <span class="col-4" style="text-align: right"><label>车牌号</label></span>
                                <input id="fullplateno" type="text" placeholder="车牌号" style="width: 60%">
                            </div>
                            <div class="col-3">
                                <span class="col-4" style="text-align: right"><label>投诉类型</label></span>
                                <select style="width: 60%;" id="type">
                                    <option value="" selected="selected">全部</option>
                                </select>
                            </div>
                            <div class="col-3">
                                <span style="text-align: right"><label>核实结果</label></span>
                                <select style="width: 60%;" id="processresult">
                                    <option value="" selected="selected">全部</option>
                                    <option value="0" >情况属实</option>
                                    <option value="1" >与事实不符</option>
                                </select>
                            </div>
                            <div class="col-3">
                                <label>处理时间</label>
                                <input style="width:30%;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate"readonly="readonly">至
                                <input style="width:30%;" id="endTime" name="endTime" type="text" placeholder="结束日期" value="" class="searchDate"readonly="readonly">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12" style="text-align: right;">
                                <button class="Mbtn red_q" id="search">查询
                                </button>
                                <button class="Mbtn grey_w" id="reset" onclick="reset()">清空</button>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-4">
                                <h4>待处理投诉信息</h4>
                            </div>
                        </div>
                        <table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <div class="pop_box" id="resultPop" style="display: none;">
        <div class="tip_box_b">
            <h3 id="title">处理投诉</h3>
            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
            <div class="row" style="margin-top: 20px">
                <label style="width: 15%">核实结果</label>
                <span id="processresultPop" style="width: 300px;display: -webkit-inline-box;"></span>
            </div>
            <div class="row" style="margin-top: 20px">
                <label style="width: 15%">处理意见</label>
                <textarea style="width:300px;height:90px;vertical-align: top;" id="processrecordPop" class="50" rows="5" maxlength="200"></textarea>
            </div>
            <div class="row hidden" id="processrecordTips" style="text-align: right;margin-right: 50px;margin-top: 0px;font-size: 12px;color: red;">
                请输入处理意见
            </div>
            <div class="row" style="margin-top: 20px;text-align: right;margin-right: 50px;">
                <button class="Mbtn blue confirmPop" type="button" onclick="confirmPop()">确定</button>
                <button class="Mbtn red closePop" type="button" onclick="closePop()">关闭</button>
            </div>
        </div>
    </div>
		<script type="text/javascript" src="js/pubComplaintManage/processed.js"></script>
	</body>
</html>

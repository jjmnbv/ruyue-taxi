<%@page import="com.szyciov.util.SystemConfig"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");	
	String apikey = SystemConfig.getSystemProperty("vmsApikey");	
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>行程数据</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css" />
		<link rel="stylesheet" type="text/css" href="content/css/combotree.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/zTree_v3/css/zTreeStyle/metro.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/font-awesome/css/font-awesome.min.css" />
		<link href="content/css/custom.css" rel="stylesheet" type="text/css" />
		<link href="content/css/style_chewutong.css" rel="stylesheet" type="text/css" />
		
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
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript" src="content/js/jquery.combotree.js"></script>
		<script type="text/javascript" src="content/plugins/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="content/plugins/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript" src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
		<script type="text/javascript"  src="content/plugins/ion.rangeslider/js/ion-rangeSlider/ion.rangeSlider.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript" src="content/js/trafficflux/date.js"></script>
		<style type="text/css">
			.form select{width:68%;}
			.select2-container .select2-choice{height:30px;}
			.select2-container{width:68%;padding:0px;}
			.dataTables_wrapper{margin:10px 0px;}
			.breadcrumb{text-decoration:underline;}
			.tabmenu{margin-bottom:10px;}
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.green_a{
			background:#1E93EE;}
			.orange{
			background:#1E93EE;}
			.blue{
			background:#1E93EE;
			}
			.grey_q {
    			background: #1E93EE;
			}
			.margin-top-20{
				margin-top:10px;
			}
			.dashboard-stat{
				margin-bottom: 10px;
			}
			.col-2 {
		    margin-top: 5px;
		    }
		    .form-horizontal .control-label{
		    	padding-top :10px;
		    }
		</style>
		<script type="text/javascript">
			var basePath = "<%=basePath%>";
			var vmsApiUrl = "<%=vmsApiUrl%>";
			var apikey="<%=apikey%>";
		</script>
	</head>
	<body >
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >行程记录
		</div>
		<div class="page-content-wrapper">
            <div class="content">
                <div class="page-content-body">
                    <!-- 写具体业务代码 -->
					<input type="hidden" id="eqpId" name="eqpId" value="${eqpId}">
                	<input type="hidden" id="imei" name="imei" value="${imei}">
                	<input type="hidden" id="plate" name="plate" value="${plate}">
					<div class="form-horizontal">
					    <div class="row col-12">
					        <div class="col-4">
					            <div class="form-group">
					                <label class="control-label col-4">
					                    	车牌
					                </label>
					                <div class="col-8">
					                    <input type="hidden" style="width:100%" id="selPlates" />
					                </div>
					            </div>
					        </div>
					        <div class="col-6">
					            <div class="form-group">
					                <label class="control-label col-3">时间范围</label>
					                <div class="col-8">
					                    <div class="input-group">
					                        <input type="text" style="width: 100%;" class=" searchDate" name="txtStartDate" id="txtStartDate"  />
					                        <span class="input-group-addon">
					                            	至
					                        </span>
					                        <input type="text" style="width: 100%;" class="searchDate" name="txtEndDate" id="txtEndDate" />
					                    </div>
					                </div>
					            </div>
					        </div>
					        <div class="col-2">
					            <div class="form-group pull-right">
					                <button type="button" class="Mbtn green_a" id="btnSearch"><img src="img/trafficflux/icon/seacrch.png"/>查询</button>
					                <button type="button" class="Mbtn orange" id="btnExport"><img src="img/trafficflux/icon/export.png"/>导出</button>
					            </div>
					        </div>
					    </div>
					</div>
					<div class="row">
					    <div class="col-12">
					        <div class="portlet box blue">
					            <div class="portlet-title">
					                <div class="caption">
					                    <i class="fa fa-reorder"></i>小计信息
					                </div>
					            </div>
					            <div class="portlet-body">
					                <div class="row ">
					                    <div class="col-3">
					                        <div class="dashboard-stat blue">
					                            <div class="visual">
					                                <img src="img/trafficflux/track/img_numbertrips.png" class="img-responsive" />
					                            </div>
					                            <div class="details">
					                                <div class="number" id="dTrackSum">
					                                    0
					                                </div>
					                                <div class="desc">
					                                    	行程数(次)
					                                </div>
					                            </div>
					                        </div>
					                    </div>
					                    <div class="col-3">
					                        <div class="dashboard-stat green">
					                            <div class="visual">
					                                <img src="img/trafficflux/track/img_mileage.png" class="img-responsive" />
					                            </div>
					                            <div class="details">
					                                <div class="number" id="dMileageSum">
					                                    0
					                                </div>
					                                <div class="desc">
					                                    	里程(km)
					                                </div>
					                            </div>
					                        </div>
					                    </div>
					                    <div class="col-3">
					                        <div class="dashboard-stat purple">
					                            <div class="visual">
					                                <img src="img/trafficflux/track/img_averagedailymileage.png" class="img-responsive" />
					                            </div>
					                            <div class="details">
					                                <div class="number" id="dAverageMileage">
					                                    0
					                                </div>
					                                <div class="desc">
					                                   	 日均里程(km/天)
					                                </div>
					                            </div>
					                        </div>
					                    </div>
					                    <div class="col-3">
					                        <div class="dashboard-stat yellow">
					                            <div class="visual">
					                                <img src="img/trafficflux/track/img_fuelconsumption.png" class="img-responsive" />
					                            </div>
					                            <div class="details">
					                                <div class="number" id="dOilSum">
					                                    0
					                                </div>
					                                <div class="desc">
					                                    	耗油量(L)
					                                </div>
					                            </div>
					                        </div>
					                    </div>
					                </div>
					                <div class="row">
					                    <div class="col-3">
					                        <div class="dashboard-stat blue">
					                            <div class="visual">
					                                <img src="img/trafficflux/track/img_lapsedtime.png" class="img-responsive" />
					                            </div>
					                            <div class="details">
					                                <div class="number" id="dTimeSum">
					                                    0
					                                </div>
					                                <div class="desc">
					                                    	运行时间(h)
					                                </div>
					                            </div>
					                        </div>
					                    </div>
					                    <div class="col-3">
					                        <div class="dashboard-stat green">
					                            <div class="visual">
					                                <img src="img/trafficflux/track/img_idletime.png" class="img-responsive" />
					                            </div>
					                            <div class="details">
					                                <div class="number" id="dIdleTimeSum">
					                                    00:00:00
					                                </div>
					                                <div class="desc">
					                                    	怠速时长(h:m:s)
					                                </div>
					                            </div>
					                        </div>
					                    </div>
					                    <div class="col-3">
					                        <div class="dashboard-stat purple">
					                            <div class="visual">
					                                <img src="img/trafficflux/track/img_consumption.png" class="img-responsive" />
					                            </div>
					                            <div class="details">
					                                <div class="number" id="dFuelConsumption">
					                                    0
					                                </div>
					                                <div class="desc">
					                                   	 油耗(L/100km)
					                                </div>
					                            </div>
					                        </div>
					                    </div>
					                    <div class="col-3">
					                        <div class="dashboard-stat yellow">
					                            <div class="visual">
					                                <img src="img/trafficflux/track/img_idlingconsumption.png" class="img-responsive" />
					                            </div>
					                            <div class="details">
					                                <div class="number" id="dIdleFuelSum">
					                                    0
					                                </div>
					                                <div class="desc">
					                                    	怠速耗油量(L)
					                                </div>
					                            </div>
					                        </div>
					                    </div>
					                </div>
					            </div>
					        </div>
					    </div>
					</div>
					<div class="row">
					    <div class="col-12">
					        <table class="table table-striped table-bordered" id="dtGrid"></table>
					    </div>
					</div>
                </div>
            </div>
        </div>
	</body>
	<script type="text/javascript" src="js/track/trackRecord.js?v=1.6"></script>
</html>

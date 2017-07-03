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
		<link rel="stylesheet" type="text/css" href="content/css/trackdetail.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/style_chewutong.css" />
		
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
		
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=AFLc9FVyIHUWExKYFETDeF6T"></script>
		<script src="content/plugins/amcharts/amcharts.js" type="text/javascript"></script>
		<script src="content/plugins/amcharts/serial.js" type="text/javascript"></script>
		<script src="content/plugins/amcharts/pie.js" type="text/javascript"></script>
		<script src="content/plugins/amcharts/gauge.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		
		
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
		</style>
		<style type="text/css">
		    #map_canvas {
		        width: 100%;
		        height: 500px;
		        overflow: hidden;
		        margin: 0;
		    }

		</style>
	</head>
	<body >
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden"/>
		<input name="eqpId" id="eqpId" value="${eqpId}" type="hidden"/>
		<input name="trackId" id="trackId" value="${trackId}" type="hidden"/>
		
		
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >
			<a href="<%=basePath%>Track/Index">行程数据 > </a>
			<a href="<%=basePath%>Track/TrackRecord">行程记录 > </a>行程记录详情</div>
		<div class="page-content-wrapper">
            <div class="content">
                <div class="page-content-body">
                    <!-- 写具体业务代码 -->
				
					<div class="portlet" style="margin-bottom:0px;padding-top:10px;">
					    <div class="portlet-title">
					        <div class="caption">
					            单行程行车数据
					        </div>
					    </div>
					    <div class="portlet-body">
					        <div class="row">
					            <div class="col-3 ">
					                <p id="pV_PLATES" class="track_bg_1"></p>
					            </div>
					            <div class="col-3">
					                <div class="col-6"><p class="car_icon"></p></div>
					                <div class="col-6"><p id="pV_PROPERTY" class="track_bg_1" style=" float: left;"></p></div>
					            </div>
					            <div class="col-3">
					                <div class="col-6"><p class="people"></p></div>
					                <div class="col-6"><p id="pV_DEPT" class="track_bg_1_1" style=" float: left;"></p></div>
					            </div>
					            <div class="col-3">
					                <div class="col-6"><p class="img_trave"></p></div>
					                <div class="col-6"><p id="pD_NAME" class="track_bg_1_1" style=" float: left;"></p></div>
					            </div>
					        </div>
					    </div>
					</div>
					
					<div class="portlet">
					    <div class="portlet-title">
					        <div class="caption">
					            <i class="fa time"></i>时间
					        </div>
					    </div>
					    <div class="portlet-body">
					        <div class="row">
					            <div class="col-3">
					                <p class="location_icon"></p>
					                <div style="text-align:center">
					                    <span class="form-control-static">行程总时长: </span>
					                    <span class="form-control-static" id="pVT_TOTALTIME"></span>
					                </div>
					            </div>
					            <div class="col-6">
					                <div class="col-4" style="text-align:left">
					                    <p class="form-control-static" id="pVT_STARTTIME"></p>
					                    <p class="form-control-static">行程开始时间</p>
					                </div>
					                <div class="col-4">
					                    <p class="car"></p>
					                    <div style="text-align:center">
					                        <span class="form-control-static">怠速总时长: </span>
					                        <span class="form-control-static" id="pVT_IDLETIME"></span>
					                    </div>
					                </div>
					                <div class="col-4" style="text-align:right">
					                    <p class="form-control-static" id="pVT_ENDTIME"></p>
					                    <p class="form-control-static">行程结束时间</p>
					                </div>
					            </div>
					            <div class="col-3">
					                <p class="destination_icon"></p>
					                <div style="text-align:center">
					                    <span class="form-control-static">行驶时长: </span>
					                    <span class="form-control-static" id="pVT_RUNTIME"></span>
					                </div>
					            </div>
					        </div>
					    </div>
					</div>
					
					<div class="portlet">
					    <div class="portlet-title">
					        <div class="caption">
					            <i class="mileage"></i>里程<font style="font-size: 14px; color: #969696;">（单位：km）</font>
					        </div>
					    </div>
					    <div class="portlet-body">
					        <div class="row">
					            <p style="font-size:16px">
					                行程总里程数<font color="#2AADE0" style="font-weight: bold;"><span class="form-control-static" id="pVT_TOTALMILEAGE"></span> km</font>,各分段行程里程数和所占比例如下：
					            </p>
					            <div class="col-8">
					                <div id="mileagechar" style="height:220px"></div>
					            </div>
					            <div class="col-4">
					                <div id="chartdiv" style="width: 100%; height: 220px;"></div>
					            </div>
					        </div>
					    </div>
					</div>
					<div class="row">
					    <div class="col-8">
					        <div class="portlet">
					            <div class="portlet-title">
					                <div class="caption">
					                    <i class="speed"></i>车速<font style="font-size: 14px; color: #969696;">（单位：km/h）</font>
					                </div>
					            </div>
					            <div class="portlet-body">
					                <div class="row">
					                    <div class="col-6">
					                        <div class="col-2">
					                            <p class="track_bg_1" style="float:left">平均速度</p>
					                        </div>
					                        <div class="col-10">
					                            <div id="avgspeedchar" style="height: 175px; margin-top: -10px;"></div>
					                        </div>
					                    </div>
					                    <div class="col-6">
					                        <div class="col-2">
					                            <p class="track_bg_1" style="float:left;">最高速度</p>
					                        </div>
					                        <div class="col-10">
					                            <div id="maxspeedchar" style=" height:175px;margin-top:-10px;"></div>
					                        </div>
					                    </div>
					                </div>
					            </div>
					        </div>
					    </div>
					    <div class="col-4">
					        <div class="portlet">
					            <div class="portlet-title">
					                <div class="caption">
					                    <i class="oil"></i>油耗 <font style="font-size: 14px; color: #969696;">（单位：L）</font>
					                </div>
					            </div>
					            <div class="portlet-body">
					                <div class="row">
					                    <div class="col-6">
					                        <div class="dashboard-stat" style="margin-bottom:0px;">
					                            <div class="visual">
					                                <img src="img/trafficflux/track/img_oil.png" class="img-responsive" />
					                            </div>
					                            <div class="details">
					                                <div class="number" id="pVT_CUMULATIVEOIL" style="color: #333333; font-size: 16px; font-weight: 300; ">
					                                    0
					                                </div>
					                                <div class="desc" style="color: #333333">
					                                    耗油量
					                                </div>
					                            </div>
					                        </div>
					                    </div>
					                    <div class="col-6">
					                        <div class="dashboard-stat" style="margin-bottom:0px;">
					                            <div class="visual">
					                                <img src="img/trafficflux/track/img_oil_dai.png" class="img-responsive" />
					                            </div>
					                            <div class="details">
					                                <div class="number" id="pVT_IDLEFUEL" style="color: #333333; font-size: 16px; font-weight:300;">
					                                    0
					                                </div>
					                                <div class="desc" style="color: #333333">
					                                    怠速耗油量
					                                </div>
					                            </div>
					                        </div>
					                    </div>
					                </div>
					                <div class="row">
					                    <div class="col-6">
					                        <div class="dashboard-stat" style="margin-bottom:0px;">
					                            <div class="visual">
					                                <img src="img/trafficflux/track/img_oil_mail.png" class="img-responsive" />
					                            </div>
					                            <div class="details">
					                                <div class="number" id="pVT_FUELCONSUMPTION" style="color: #333333; font-size: 16px; font-weight: 300; ">
					                                    0
					                                </div>
					                                <div class="desc" style="color: #333333">
					                                    百公里油耗
					                                </div>
					                            </div>
					                        </div>
					                    </div>
					                </div>
					            </div>
					        </div>
					    </div>
					</div>
					
					<div class="portlet">
					    <div class="portlet-title">
					        <div class="caption">
					            <i class="history_route"></i>行驶轨迹
					        </div>
					    </div>
					    <div class="portlet-body">
					        <div class="row">
					            <div class="col-12">
					                <div id="map_canvas">
					                </div>
					            </div>
					        </div>
					    </div>
					</div>
					
                </div>
            </div>
        </div>
		
		
	</body>
	<script type="text/javascript" src="js/track/trackRecordDetail.js"></script>
	<script type="text/javascript">
		var basePath = "<%=basePath%>";
	</script>
</html>

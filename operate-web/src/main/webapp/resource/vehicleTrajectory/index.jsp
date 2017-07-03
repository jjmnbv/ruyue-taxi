<%@page import="com.szyciov.util.SystemConfig"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String yingyan_ak = SystemConfig.getSystemProperty("yingyan_ak");
	String vmsApiUrl = SystemConfig.getSystemProperty("vmsApiUrl");	
	String apikey = SystemConfig.getSystemProperty("vmsApikey");	
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>车辆轨迹</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.skinModern.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript"  src="content/plugins/ion.rangeslider/js/ion-rangeSlider/ion.rangeSlider.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<style type="text/css">
		    #map_canvas {
		        width: 100%;
		        height: 655px;
		        overflow: hidden;
		        margin: 0;
		    }
		    .note-success {
		        background-color: #EBFCEE;
		        border-color: #39b3d7;
		        color: #39b3d7;
		    }
		    .note:hover {
		        background-color: #f9f9f9;
		    }
		    .track-list {
		        list-style: none;
		        padding: 0;
		        margin: 0;
		    }
		    .btnStyle {
		        float: right;
		        width: 300px;
		        height: 50px;
		        margin-top: 5px;
		        background-color: white;
		        border: 0px black solid;
		        position: absolute;
		        z-index: 1;
		        right: 10px;
		        background: url('img/trafficflux/vehcTrack/bg_map_01.png') no-repeat center;
		        background-size: 100% 80%;
		    }
		    .speedStyle {
		     	float: right; 
		        width: 300px;
		        height: 110px;
		        background-color: white;
		        border: 0px black solid;
		        position: absolute;
		        z-index: 1;
		        right: 10px;
		        top: 50px;
		        display: none;
		        background: url('img/trafficflux/vehcTrack/bg_map_02.png') no-repeat center;
		        background-size: 100% 100%;
		    }
		    .liStyle {
		        border-bottom: 1px #cccccc solid;
		        border-top: 1px #cccccc solid;
		        border-left: 0px;
		        border-right: 0px;
		        height: 130px;
		    }
		    .liStyle p {
		            width: 200px;
		        }
		    .btn-defaultnew {
		        color: #333;
		        background-color: #fff;
		        border-color: white;
		        padding: 6px 13px;
		        font-size:12px;
		    }
			.btn-defaultnew:hover, .btn-defaultnew:focus, .btn-defaultnew:active, .btn-defaultnew.active {
		            color: blue;
		            /*border-color: #adadad;*/
		        }
		    .trackStyle {
		        list-style: none;
		    }
		    .trackStyleli {
		       /*  margin-left: -40px; */
		        margin-top: 5px;
		        margin-bottom: 5px;
		        height: 145px;
		        border-bottom: 1px #cccccc solid;
		        border-top: 1px #cccccc solid;
		        border-left: 0px;
		        border-right: 0px;
		    }
		  .BMap_bubble_content p {
   			 	margin: 0 0 10px; 
			}
			img{max-width:none;}
			.green_a{
			background:#1E93EE;}
		</style>
		<script type="text/javascript">
			var basePath = "<%=basePath%>";
			var vmsApiUrl = "<%=vmsApiUrl%>";
			var apikey="<%=apikey%>";
			var echoplate="${plate}";//回显车牌
			var echoeqpId="${eqpId}";//回显设备Id
			var echostartTime="${startTime}";
			var echoendTime="${endTime}";
			
		</script>
	</head>
	<body >
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 车辆轨迹
			 <button type="button" class="Mbtn blue_q pull-right" id="btnExport"  style="margin-top: -5px;"onclick="ExportData()">
				<img src="img/trafficflux/icon/export.png"/>
			                                                  导出
			 </button>
		</div>
		<div class="content">
			<div class="row" >
			    <div class="col-10" style="position:relative;overflow-y:hidden;">
			        <!--按钮-->
			        <div class="btnStyle" >
			            <div class="col-2" style="margin-top: 2px;width:70px;">
			                <button type="button" class="btn btn-defaultnew" id="play" onclick="play();" disabled><img src="img/trafficflux/vehcTrack/btn_bofang_no.png" style="width:16px;height:16px;margin-right:6px;" id="playImg" />播放 <span style="margin:8px;height:24px;border:1px #cccccc solid;"></span></button>
			            </div>
			            <div class="col-2" style="margin-top: 2px; width: 70px;">
			                <button type="button" class="btn btn-defaultnew" id="pause" onclick="pause();" disabled><img src="img/trafficflux/vehcTrack/btn_stop_no.png" style="width:16px;height:16px;margin-right:6px;" id="pauseImg" />暂停 <span style="margin:8px;height:24px;border:1px #cccccc solid;"></span></button>
			            </div>
			            <div class="col-2" style="margin-top: 2px;width: 70px;">
			                <button type="button" class="btn btn-defaultnew" id="reset" onclick="reset()" disabled><img src="img/trafficflux/vehcTrack/btn_chong_no.png" style="width:16px;height:16px;margin-right:6px;" id="resetImg" />重置 <span style="margin:8px;height:24px;border:1px #cccccc solid;"></span></button>
			            </div>
			            <div class="col-2" style="margin-top: 2px; width: 70px;">
			                <button type="button" class="btn btn-defaultnew" id="speedChoice"><img src="img/trafficflux/vehcTrack/btn_speed_def.png" style="width:16px;height:16px;margin-right:6px;" id="speedChoiceImg" />速度</button>
			            </div>
			        </div>
			        <!--速度选择div-->
			        <div id="speed" class="speedStyle" >
			            <div style="margin-left:20px;margin-top:5px;margin-right:10px;">
			                <label class="control-label" style="font-size:12px;margin-bottom:20px;">回放速度</label>
			                <img src="img/trafficflux/trajectory/hor-menu-search-close.png" style="width:12px;height:12px;float:right;margin-top:2px;" onclick="hideSpeed()" />
			                <div class="col-11" style="margin-top:-12px;">
			                    <input type="text" id="rangeName" name="rangeName" />
			                    <input type="hidden" id="rangeFrom" name="rangeFrom" value="100" />
			                </div>
			            </div>
			        </div>
			        <div id="map_canvas">
			        </div>
			        <!--轨迹列表-->
			        <div style="display:none; width:100%;" id="hList">
			            <ul class="nav nav-tabs" style="margin-bottom:0px">
			                <li class="active">
			                    <a data-toggle="tab" style="padding:4px 15px">轨迹列表</a>
			                </li>
			            </ul>
			            <div class="tab-content">
			                <div class="tab-pane active">
			                    <table class="table table-condensed table-hover tableheader gridHeader" id="dtGridHeader" style="margin-bottom:0px;">
			                        <thead>
			                            <tr>
			                                <th>
			                                    	序号
			                                </th>
			                                <th>
			                                    	方向
			                                </th>
			                                <th>
			                                    	GPS时间
			                                </th>
			                                <th>
			                                    	速度(km/h)
			                                </th>
			                                <th>
			                                    	经度
			                                </th>
			                                <th>
			                                    	纬度
			                                </th>
			                                <th>
			                                    	位置描述
			                                </th>
			                            </tr>
			                        </thead>
			                    </table>
			                    <div style="height: 200px;overflow:auto;" class="scroller" id="Grid">
			                        <table class="table table-condensed table-hover gridHeader" id="dtGrid" style="border-top-width:0px;">
			                            <tbody>
			                                <tr id="trackList" style="display:none"></tr>
			                            </tbody>
			                        </table>
			                    </div>
			                </div>
			            </div>
			        </div>
			    </div>
			    <div class="col-2" style="padding: 6px 5px;">
			        <div class="row">
			            <div class="col-12">
			                <input type="hidden" style="width:100%" id="selPlates"   />
			            </div>
			        </div>
			        <div class="row">
			            <div class="col-12">
			            	<input id="txtStartDate" name="txtStartDate"  style="width:100%" type="text" placeholder="开始时间" value="" class="searchDate">
			            </div>
			        </div>
			        <div class="row">
			            <div class="col-12">
			            	<input id="txtEndDate" name="txtEndDate"  style="width:100%" type="text" placeholder="结束时间" value="" class="searchDate">
			            </div>
			        </div>
			        <div class="row">
			            <div class="col-12">
			                <div class="col-6">
			                    <div class="checkbox-list" style="margin-top: 15px; display: none;">
			                        <label>
			                            <input id="follow" type="checkbox" checked /><span style="font-size:12px;">画面跟随</span>
			                            <input type="hidden" id="txtAddress" />
			                        </label>
			                    </div>
			                </div>
			                <button class="Mbtn green_a pull-right" id="btnSearch">
			                 <img src="img/trafficflux/icon/seacrch.png"/>                                
			                                                    查询
			                </button>
			            </div>
			        </div>
			        <!--轨迹回放-->
			        <div class="row" id="PointList" style="display:none;margin-bottom:10px;border:black 0px solid;height:100px;">
			            <div class="col-12">
			                <ul class="trackStyle">
			                    <li class="trackStyleli">
			                        <div class="note">
			                            <div class="control-label" style="font-size:12px;">起点</div>
			                            <div class="control-label" id="startPoint" style="color: green; font-size: 12px;"></div>
			                            <div class="control-label" style="font-size:12px;">终点</div>
			                            <div class="control-label" id="endPoint" style="color: red ;font-size:12px;"></div>
			                        </div>
			                    </li>
			                </ul>
			            </div>
			        </div>
			        <div id="historyTrack" class="row" style="display:none;">
			            <div class="col-12">
			                <div class="portlet">
			                    <div class="portlet-title">
			                        <div class="caption">
			                            <i class="fa fa-check"></i>历史行程:<span id="spTrackCount">0</span>条
			                        </div>
			                    </div>
			                    <div class="portlet-body">
			                        <div class="vehc-content" style='height: 288px; overflow-y: scroll; overflow-x:hidden;'>
			                            <ul class="track-list"></ul>
			                        </div>
			                    </div>
			                </div>
			            </div>
			        </div>
			    </div>
			</div>
			<!-- 导出 -->
			<div class="modal fade" id="mdExport" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" style="min-height:350px">
			    <div class="modal-dialog">
			        <div class="modal-content">
			            <form id="frmmodal" action="#" class="form-horizontal" role="form">
			                <div class="modal-header">
			                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			                    <h4 class="modal-title">导出</h4>
			                </div>
			                <div class="modal-body">
			                    <div class="row">
			                        <div class="form-group">
			                            <label class="control-label col-3">导出类型<em class="asterisk"> </em></label>
			                            <div class="col-8">
			                                <div class="radio-list" id="radioList">
			                                    <label class="radio-inline">
			                                        <span><input type="radio" name="optionsRadios" id="gj" value="1" checked="checked"></span>导出轨迹
			                                    </label>
			                                    <label class="radio-inline">
			                                        <span><input type="radio" name="optionsRadios" id="xc" value="2"></span>导出行程
			                                    </label>
			                                </div>
			                            </div>
			                        </div>
			                    </div>
			                    <div class="row">
			                        <div class="form-group">
			                            <label class="control-label col-3"> 车牌<em class="asterisk"> </em></label>
			                            <div class="col-8 ">
			                                <input type="hidden" style="width:100%" id="mdselPlates" required="required" title="请选择车辆" />
			                            </div>
			                        </div>
			                    </div>
			                    <div class="row">
			                        <div class="form-group">
			                            <label class="control-label col-3">开始时间<em class="asterisk"> </em></label>
			                            <div class="col-8 ">
			                            	<input id="txtStartTime" name="txtStartTime" readonly="readonly" title="请选择开始时间" style="width:100%;" type="text" placeholder="开始时间" value="" class="searchDate">
			                            </div>
			                        </div>
			                    </div>
			                    <div class="row">
			                        <div class="form-group">
			                            <label class="control-label col-3">结束时间<em class="asterisk"> </em></label>
			                            <div class="col-8 ">
			                            	<input id="txtEndTime" name="txtEndTime" readonly="readonly" style="width:100%;" type="text" placeholder="结束时间" title="请选择结束时间" value="" class="searchDate">
			                            </div>
			                        </div>
			                    </div>
			                </div>
			                <div class="modal-footer">
			                    <div class="pull-right" id="footer">
			                        <button type="submit" class="Mbtn blue_q"><img src="img/trafficflux/icon/export.png"/>导出</button>
			                        <button type="button" class="Mbtn green_a" data-dismiss="modal">取消</button>
			                    </div>
			                </div>
			            </form>
			        </div>
			    </div>
			</div>
		</div>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>"></script>
		<script type="text/javascript" src="js/vehicleTrajectory/index.js??ver=<%=Math.random()%>"></script>
		<script type="text/javascript" src="js/vehicleTrajectory/directiontranslate.js"></script>
	</body>
</html>

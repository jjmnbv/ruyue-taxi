<%@page import="com.szyciov.util.SystemConfig"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String yingyan_ak = SystemConfig.getSystemProperty("yingyan_ak");
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>出租车订单</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="css/orgordermanage/orderdetail.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/ion.rangeslider/css/ion.rangeSlider.skinFlat.css" id="styleSrc"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css" />
		
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
		<script type="text/javascript" src="content/plugins/ion.rangeslider/js/ion-rangeSlider/ion.rangeSlider.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<style type="text/css">
			.form select{width:68%;}
			.dataTables_wrapper{margin: 0px 0px 10px 0px;}
			.breadcrumb{text-decoration:underline;}
			.quantext{position: relative;z-index: 2!important;}
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.DTFC_ScrollWrapper{
				margin-top:-20px;
				height: auto!important;
			}
			/*#changeDriverRecorddataGrid{
				width: 1443px!important;
			}
			#reviewRecordDataGrid{
				width: 1423px!important;
			}*/
			#orderCostTable td{text-align: center;}
			
			/*样式增加*/
			#commentDataGrid{
				border:none;
     			margin:0!important;
				border-collapse:separate;
  				border-spacing:0 10px;
			}
			#commentDataGrid_wrapper .col-sm-12{
				padding-top:0;
				padding-bottom:0;
			}
			#commentDataGrid_wrapper .col-sm-6{
				display: none;
			}
			#commentDataGrid .sorting_disabled{
				display:none;
			}
			#commentDataGrid td{
				padding:0;
				border-top:none;
				border: 1px solid #ddd;
				
			}
		</style>
	</head>
	<body  class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > toC订单 > <a class="breadcrumb" href="OrderManage/TaxiIndex">出租车订单</a> > 订单详情
			<button class="SSbtn blue back" onclick="window.history.go(-1);">返回</button>
		</div>
		<div class="content" style="overflow: auto !important;">
			<h2 id="ddxx" style="padding-top: 30px;"></h2>
			
			<table class="table_a" border="1">
				<tr>
					<td class="grey_c">下单人信息</td>
					<td id="xdrxx"></td>
					<td class="grey_c">订单类型</td>
					<td><span id="ddlx" class="font_grey">出租车</span></td>
					<td class="grey_c">用车时间</td>
					<td id="ycsj"></td>
				</tr>
				<tr>
					<td class="grey_c">乘车人信息</td>
					<td><span id="ccrxx" class="font_grey"></span></td>
					<td class="grey_c">上车地址</td>
					<td id="scdz" title=""></td>
					<td class="grey_c">下车地址</td>
					<td id="xcdz" title=""><span class="font_grey"></span></td>
				</tr>
				<tr>
					<td class="grey_c">下单时间</td>
					<td><span id="xdsj" class="font_grey"></span></td>
					<td class="grey_c">下单来源</td>
					<td id="xdly"></td>
					<td class="grey_c">订单状态</td>
					<td><span id="ddzt" class="font_green"></span></td>
				</tr>
				<tr>
					<td class="grey_c">备注</td>
					<td colspan="5"><span id="bz" class="font_grey"></span></td>
				</tr>
				<tr>
					<td class="grey_c">车牌号</td>
					<td id="cph"></td>
					<td class="grey_c">司机信息</td>
					<td><span id="sjxx" class="font_grey"></span></td>
					<%--<td class="grey_c">更新时间</td>--%>
					<%--<td id="gxsjtd" style="border-top-style: none;"><span id="gxsj" class="font_grey"></span></td>--%>
                    <td class="grey_c">服务车企</td>
                    <td id="fwcqtd"></td>
				</tr>
				<tr id="qxtr">
					<td class="grey_c">支付方式</td>
					<td id="zffs"></td>
					<td class="grey_c">调度费用(元)</td>
					<td><span id="ddfy" class="font_red"></span></td>
					<td class="grey_c">行程费用(元)</td>
					<td><span id="xcfy" class="font_red"></span></td>
				</tr>
				<tr>
					<td class="grey_c">支付渠道</td>
					<td id="zfqd"></td>
					<td class="grey_c"></td>
					<td></td>
					<td class="grey_c"></td>
					<td></td>
				</tr>
			</table>
			
			<ul class="tabmenu">
			    <li class="on"><a href="javascript:void(0)" style="text-decoration: none;">时间轴</a></li>
			    <li><a href="javascript:void(0)" style="text-decoration: none;">人工派单记录</a></li>
			    <li><a href="javascript:void(0)" style="text-decoration: none;">更换司机记录</a></li>
			    <li><a href="javascript:void(0)" style="text-decoration: none;">更换车辆记录</a></li>
			    <li><a href="javascript:void(0)" style="text-decoration: none;">复核记录</a></li>
			    <li><a href="javascript:void(0)" style="text-decoration: none;">客服备注</a></li>
			</ul>
			<ul class="tabbox">
				<li style="display:block">
					<ul class="timeaxis">
						<li style="width: 13%!important;">
							<div class="quan">下单<br/>时间</div>
							<div id="xdsjDiv" class="quantext">
								
							</div>
						</li>
							<li style="width: 13%!important;">
							<div id="jdsjIcon" class="quan ing">接单<br/>时间</div>
							<div id="jdsjDiv" class="quantext">
								
							</div>
						</li>
							<li style="width: 13%!important;">
							<div id="cfsjIcon" class="quan ing">出发<br/>时间</div>
							<div id="cfsjDiv" class="quantext">
								
							</div>
						</li>
							<li style="width: 13%!important;">
							<div id="ddsjIcon" class="quan ing">抵达<br/>时间</div>
							<div id="ddsjDiv" class="quantext">
								
							</div>
						</li>
							<li style="width: 13%!important;">
							<div id="kssjIcon" class="quan ing">开始<br/>时间</div>
							<div id="kssjDiv" class="quantext">
								
							</div>
						</li>
							<li style="width: 13%!important;">
							<div id="jssjIcon" class="quan ing">结束<br/>时间</div>
							<div id="jssjDiv" class="quantext">
								
							</div>
						</li>
					</ul>
					
					<div class="col-8">
						<div id="map_canvas" style="width: auto; height: 500px; border: #ccc solid 1px;"></div>
					</div>
					<ul class="col-4">
						<div class="col-12">
							<label class="control-label" style="font-size:12px;">播放速度</label>
							<div class="col-12">
								<input type="text" id="rangeName" name="rangeName" />
								<input type="hidden" id="rangeFrom" name="rangeFrom" value="100" />
							</div>
						</div>
						<div class="col-12">
							<label>
								<input id="follow" type="checkbox" checked /><span style="font-size:12px;">画面跟随</span>
								<input type="hidden" id="txtAddress" />
							</label>
						</div>
						<div class="col-12">
							<div class="col-4">
								<button type="button" class="SSbtn blue" onclick="play()"><i class="fa fa-paste"></i>播放</button>
							</div>
							<div class="col-4">
								<button type="button" class="SSbtn blue" onclick="pause()"><i class="fa fa-paste"></i>暂停</button>
							</div>
							<div class="col-4">
								<button type="button" class="SSbtn blue" onclick="reset()"><i class="fa fa-paste"></i>重播</button>
							</div>
						</div>
					</ul>
				</li>
				<li>
					<div class="col-12">
						<table class="table_a" border="1">
							<tr>
								<td class="grey_c">派单司机</td>
								<td id="pdsj"></td>
								<td class="grey_c">派单时间</td>
								<td><span id="pdtime" class="font_grey"></span></td>
								<td class="grey_c">操作人</td>
								<td id="czr"></td>
							</tr>
							<tr>
								<td class="grey_c">人工派单原因</td>
								<td colspan="5" style="border-top-style: none;"><span id="rgpdyy" class="font_grey"></span></td>
							</tr>
						</table>
					</div>
				</li>
				<li>
					<div class="col-12">
						<table id="changeDriverRecorddataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
					</div>
				</li>
				<li>
					<div class="col-12">
						<table id="changeVehicleRecorddataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
					</div>
				</li>
				<li>
					<div class="col-12">
						<h2>原始订单费用明细</h2>
						<table class="table_a" id="orderCostTable">
							<tr>
								<td class="grey_c">行程费用(元)</td>
								<td class="grey_c">服务里程(公里)</td>
								<td class="grey_c">服务时长(分钟)</td>
								<td class="grey_c">服务开始时间</td>
								<td class="grey_c">服务结束时间</td>
							</tr>
							<tr>
								<td id="re_xcfy"></td>
								<td id="re_fwlc"></td>
								<td id="re_fwsc"></td>
								<td id="re_fwkssj"></td>
								<td id="re_fwjssj"></td>
							</tr>
						</table>
					</div>
					<div class="col-12">
						<h2>订单复核记录明细</h2>
						<table id="reviewRecordDataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
					</div>
				</li>
				<li>
					<div class="col-12">
						<table id="commentDataGrid" class="table table-striped table-bordered" width="100%" border="0" cellspacing="10" cellpadding="0" ></table>
					</div>
				</li>
			</ul>
		</div>

		<script type="text/javascript">
			var orderObj = {
				orderno: "<%=request.getParameter("orderno")%>"
			};
		</script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>"></script>
		<script type="text/javascript" src="js/tocOrderManage/taxiorderdetail.js"></script>
	</body>
</html>

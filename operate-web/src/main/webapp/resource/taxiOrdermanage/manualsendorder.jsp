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
		<title>个人订单</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
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
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<style type="text/css">
			.breadcrumb{text-decoration:underline;}
			.w400 label.error{padding-left:8px!important;width:100%;margin-left: 0px}
		
			/* 前端对于类似页面的补充   开始*/
			.table_a{margin-top:14px;}
			.form label{width:40%;}
			.form select,.form input[type="text"]{width:57%;}
			.form select{margin-left:-4px;}
			td .SSbtn{margin-left:30px;margin-top:-4px;}
			td.center{text-align:left!important;}
			.col-5{margin-top:14px;}
			.col-7 input[type="text"]{margin-left:3px;}
			.tip_box_b textarea ,.tip_box_b select{width:68%;}
			.tip_box_b textarea {vertical-align:text-top;}
			.tip_box_b label{width:30%;}
			/* 前端对于类似页面的补充   结束*/
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.DTFC_ScrollWrapper{
				margin-top:-20px;
			}
			#driverDataGrid_wrapper>.row{
				width: 91%!important;
				float: right;
			}
	   </style>
	   <script type="text/javascript">
		  $(document).ready(function() {
			  //前端补充司机选择效果
			  $("body").on('click','.center', function () {
				 $("td.center").removeClass("grey_c");
	 			 $(this).addClass("grey_c");
			  });
		  });
	   </script>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="TaxiOrderManage/Index">出租车订单</a> > <span id="manuTitle">人工派单</span>
			<button class="SSbtn blue back" target="iframe" onclick="window.history.go(-1);">返回</button>
		</div>
		<div class="content">
			<div class="col-7" style="padding-top: 30px;">
				<div class="row ordermanage_css_row_2">
					<table class="table_a">
						<tr>
							<td class="grey_c">下单人</td>
							<td id="xdr"></td>
							<td class="grey_c">用车时间</td>
							<td id="ycsj"></td>
						</tr>
					</table>
				</div>
				<div class="row">
					<h2 style="margin-top: 50px;margin-bottom:15px;padding-bottom: 10px;border-bottom: 1px solid #dbdbdb">地图可视派单</h2>
					<div id="map_canvas" style="width: auto; height: 500px; border: #ccc solid 1px;"></div>
				</div>
			</div>
			<div class="col-5 ordermanage_css_col_3" style="margin-left: -25px;">
				<div class="row form">
					<div class="col-6 ordermanage_css_col_2">
						<label>车牌</label>
						<input type="hidden" id="vehicleid" name="vehicleid" style="width: 57%;" placeholder="车牌">
					</div>
					<div class="col-6 ordermanage_css_col_2">
						<label>车辆状态</label>
						<select id="sjselect" onchange="driverInfoChange()">
							<option value="">全部</option>
							<option value="0" selected="selected">空闲</option>
							<option value="1">服务中</option>
							<option value="2">下线</option>
						</select>
					</div>
				</div>
				<div class="row form">
					<div class="col-6 ordermanage_css_col_2">
						<label>距离</label>
						<select  id="jlselect" onchange="driverInfoChange()">
							<option value="">不限</option>
							<option value="3000">3公里内</option>
							<option value="5000">5公里内</option>
							<option value="10000">10公里内</option>
						</select>
					</div>
					<div class="col-6 ordermanage_css_col_2" style="text-align: right;">
						<button class="Mbtn green_a" onclick="driverInfoChange();">查询</button>
						<button class="Mbtn grey_b" onclick="initSearch();">清空</button>
					</div>
				</div>
				<div class="row form ordermanage_css_row_1">
					<div class="col-12">
						<table id="driverDataGrid" class="table table-striped table-bordered" cellspacing="0" style="float: right;" width="100%"></table>
					</div>
					<div class="col-9" style="float: left;display: none;" id="sendFail">
						<span><button class="LLbtn orange" onclick="sendFail();">派单失败</button></span>
					</div>
				</div>
			</div>
		</div>
		
		<div class="pop_box" id="manualSendOrderFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="manualSendOrderFormTitle">人工派单</h3>
	            <input type="hidden" id="driverid" name="driverid"/>
	            <input type="hidden" id="vehicleidHide" name="vehicleidHide">
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="manualSendOrderForm" method="get" class="form">
	            		<input type="hidden" id="id" name="id"/>
		                <div class="row">
			                <div class="col-12">
			                	<label style="float: left;" id="orderreasonTextareaLabel">派单原因<em class="asterisk"></em></label>
			                	<textarea id="orderreasonTextarea" name="orderreasonTextarea" rows="5" cols="3" style="width: 62%" placeholder="填写人工派单原因" maxlength="100"></textarea>
			                </div>
	            		</div>
	            	</form>
	                <button class="Lbtn red" onclick="save()">确定</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=<%=yingyan_ak%>"></script>
		<script type="text/javascript" src="js/taxiOrdermanage/manualsendorder.js"></script>
		<script type="text/javascript">
			var orderObj = {
				orderno: "<%=request.getParameter("orderno")%>",
				type: "<%=request.getParameter("type")%>"
			};
			var serviceAddress = "<%=SystemConfig.getSystemProperty("fileserver")%>";
		</script>
	</body>
</html>

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
		<title>司机操作记录</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
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
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<style type="text/css">
			.form select{width:68%;}
			.select2-container .select2-choice{height:30px;}
			.select2-container{width:68%;padding:0px;}
			.dataTables_wrapper{margin: 0px 0px 10px 0px;}
			.breadcrumb{text-decoration:underline;}
			.stab{border-bottom:2px solid #ededed;}
			.stab>div{display:inline-block;padding:4px 14px;text-align:center;}
			.shen_on{background:#ededed;}
			th, td { white-space: nowrap;}
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.tip_box_b input[type=text]{width: 63%;}
			.DTFC_ScrollWrapper{
			margin-top:-20px;}
			.pop_box{z-index: 1111 !important;}
		</style>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="javascript:void(0);" onclick="rebacke()">网约车绑定</a>>操作记录
			<button class="SSbtn blue back" onclick="rebacke()">返回</button>
		</div>
		<div class="content">
			<ul class="tabbox">
				<li style="display:block">
					<div class="stab">
						<div class="shen_on">司机操作记录</div>
						<div><a href="driverVehicleRecord/car/vehicleRecord" style="text-decoration: none;">车辆操作记录</a></div>
					</div>
					<div class="stabox">
						<div class="row form" style="margin-top: 40px;">
							<div class="col-3">
								<label>司机</label>
								<input id="queryDriverId" type="hidden" placeholder="姓名/手机号">
							</div>
							
							<div class="col-3">
								<label>资格证号</label>
								<input id="queryDriverNum" type="hidden" placeholder="司机资格证号">
							</div>
							<div class="col-3">
								<label>操作类型</label>
								<select id="bindStatus">
									<option value="">全部</option>
									<option value="0">解绑</option>
									<option value="1">绑定</option>
								</select>
							</div>
							<%--<div class="col-3">--%>
								<%--<label>归属车企</label>--%>
								<%--<select id="belongLeasecompany">--%>
									<%--<option value="">全部</option>--%>
								<%--</select>--%>
							<%--</div>--%>
							<div class="col-3 ordermanage_css_col_1">
								<label>操作时间</label>
								<input style="width:30%;" id="minUseTime" name="minUseTime" readonly="readonly" type="text" placeholder="开始日期" value="" class="searchDate">
								至
			            		<input style="width:30%;" id="maxUseTime" name="maxUseTime" readonly="readonly" type="text" placeholder="结束日期" value="" class="searchDate">
							</div>
							
							<div class="col-12" style="text-align: right;">
								<button class="Mbtn green_a" onclick="search();">查询</button>
								<button class="Mbtn gray_a" onclick="clearOptions();">清空</button>
							</div>
						</div>
						<div class="row">
							<div class="col-12" style="position:relative;margin-top:15px;">
								<h4>历史记录信息</h4>
							</div>
						</div>
						<table id="grid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
					</div>
				</li>
			</ul>
		</div>
		<script type="text/javascript" src="js/drivervehiclebind/carbind/driverRecord.js"></script>
	</body>
</html>

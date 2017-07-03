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
		<title>操作记录</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css"/>
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

	</head>
	
	<style type="text/css">
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}

		table.dataTable{width:100%!important;}
		
		/* 固定列样式调整  */
		th, td { white-space: nowrap; }
        div.dataTables_wrapper {
            width: $(window).width();
            margin: 0 auto;
        }
        .DTFC_ScrollWrapper{
			margin-top:-20px;
		}
	</style>
	
	<body>
		<div class="crumbs">
		    <a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="PubDriverVehicleRef/Index">网约车绑定</a> > 操作记录
		    <button class="SSbtn blue back" onclick="callBack()" style="margin-top: -1px;">返回</button>
		</div>
		<div class="content">  
			<div class="row form" style="padding-top: 30px;">
			    <ul class="tabmenu">
                    <li><a href="PubDriverVehicleRef/DriverOperateDetail" style="text-decoration: none;">司机操作记录</a></li>
                    <li class="on">车辆操作记录</li>
                </ul>
				
				<div class="col-3">
					<label>车牌号</label>
					<input id="plateno" name="plateno" type="text" placeholder="车牌号">
				</div>
				
				<div class="col-3">
					<label>车架号</label>
					<input id="vin" name="vin" type="text" placeholder="车架号">
				</div>
				
				<div class="col-3">
					<label>操作类型</label>
					<select id="bindstate" name="bindstate">
						<option value="" selected="selected">全部</option>
						<option value="1">绑定</option>
						<option value="0">解绑</option>
					</select>
				</div>
				
				<div class="col-3">
					<label>操作时间</label>
					<input style="width:30%;" id="starttime" name="starttime" type="text" placeholder="开始日期" value="" class="searchDate" readonly="readonly">至
					<input style="width:30%;" id="endtime" name="endtime" type="text" placeholder="结束日期" value="" class="searchDate" readonly="readonly">					
				</div>
			</div>
			<div class="row form">
				<div class="col-12" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey" onclick="clearSearch();">清空</button>
				</div>
			</div>
			
			<div class="row">
			   <div class="col-12" style="margin-top: 15px;"><h4>历史记录信息</h4></div>
			</div>
			   <table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>

		</div>

		<script type="text/javascript" src="js/pubDriverVehicleRef/vehicleoperatedetail.js"></script>
	</body>
</html>

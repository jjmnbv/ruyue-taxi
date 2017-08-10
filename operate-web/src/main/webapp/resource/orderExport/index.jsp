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
		<title>订单导出</title>
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
			.dataTables_wrapper{margin:10px 0px;}
			.breadcrumb{text-decoration:underline;}
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			tbody tr td:first-child{
				text-align:left;
			}
			.w400 label.error{padding-left:15px!important;}
			.col-3{width: 23%;}
		</style>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 订单导出</div>
		<div class="content">
			<div class="row form" style="margin-bottom: 20px;padding-top: 30px;">
				<div class="col-4">
					<label>订单类型</label>
					<select id="usetype" name="usetype" onchange="orderon(this)">
						<option value="" selected="selected">全部</option>
						<option value="0">机构因公</option>
						<option value="1">机构因私</option>
						<option value="2">个人</option>
					</select>
				</div>
				<div class="col-4" id="organidDiv" style="display:none">
					<label>所属机构</label><input id="organid" type="hidden" placeholder="请选择所属机构" >
				</div>
				<div class="col-4"  id="organidDiv1">
					<label>所属机构</label><input id="organid1" type="hidden" placeholder="请选择所属机构"disabled="disabled" >
				</div>
				<div class="col-4">
					<label>用车类型</label>
					<select id="ordertype" name="ordertype">
					   <option value="" selected="selected">全部</option>
						<option value="1">约车</option>
						<option value="2">接机</option>
						<option value="3">送机</option>
						<option value="4">出租车</option>
					</select>
				</div>
				<div class="col-4">
					<label>支付状态</label>
					<select id="paymentstatus" name="paymentstatus">
						<option value="" selected="selected">全部</option>
						<option value="4">未结算</option> 
                        <option value="2">结算中</option> 
                        <option value="3">已结算</option> 
                        <option value="5">未付结</option> 
                        <option value="8">已付结</option> 
                        <option value="1">已支付</option>
                        <option value="0">未支付</option>  
					</select>
				</div>
				<div class="col-4 ordermanage_css_col_1" style="width: 33%">
					<label>用车时间</label>
					<input style="width:32%;" id="minUseTime" name="minUseTime" readonly="readonly" type="text" placeholder="开始日期" value="" class="searchDate">
					至
            		<input style="width:32%;" id="maxUseTime" name="maxUseTime" readonly="readonly" type="text" placeholder="结束日期" value="" class="searchDate">
				</div>
				<div class="col-4">
					<label style="width:32%;">下单人</label><input id="passengers" type="hidden" placeholder="请选择下单人">
				</div>
				<div class="col-4" style="width: 33.5%;">
					<label >司机</label><input id="driver" type="hidden" placeholder="请选择司机">
				</div>
				<div class="col-4" style="width: 33.5%;">
					<label >服务车企</label><input id="leasescompany" type="hidden" placeholder="请选择服务车企">
				</div>
				<div class="col-12" style="text-align: right;">
					<button class="Mbtn red_q" onclick="search();">查询</button>
					<button class="Mbtn grey_w" onclick="reset();">清空</button>
				</div>
				<div class="col-12" style="text-align: right;">
					<button class="Mbtn blue_q" onclick="exportExcel();">导出数据</button>
				</div>
			</div>
			<div class="row" style="position:relative;">
				<div class="col-6">
				</div>
			</div>
			<table id="manualOrderdataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<script type="text/javascript" src="js/orderExport/index.js"></script>
	</body>
</html>

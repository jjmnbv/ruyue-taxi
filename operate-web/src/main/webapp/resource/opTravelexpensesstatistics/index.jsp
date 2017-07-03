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
		<title>出租车行程费用统计</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="content/js/common.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>	
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript" src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript">
		$(function(){$('input, textarea').placeholder();})
		</script>
		<style type="text/css">
		h2{line-height:250%;border-bottom:1px solid #dbdbdb;margin:0px -20px 20px -20px;padding-left:20px;}
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
				
		.tip_box_b label{float:left;line-height: 30px;height:30px;}
		.tip_box_b select,.tip_box_b input[type=text]{width:63%;float:left;margin-top: 0px;}
		.tip_box_b label.error {padding-left: 0%;margin-left: 30%!important;}
		
		th, td { white-space: nowrap; }
		div.dataTables_wrapper {
		  width: $(window).width();
		  margin: 0 auto;
		}
		.DTFC_ScrollWrapper{
		margin-top:-20px;
		}
	</style>
	</head>
	<body style="overflow-x:hidden;overflow-y:hidden">
	<!-- <body> -->
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页 </a>>出租车行程费用统计</div>
		<div class="content">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden"/>
		<div style="padding-top: 30px;">
		<div class="row">
		<div class="col-8"><h2>按车企统计</h2></div>
		<div class="col-4" style="text-align:right;border-bottom: 1px solid #dbdbdb;padding-bottom:7px">
	      <button class="Mbtn blue_q" style="margin-top:10px"onclick="exportExcel()">导出数据</button>
	   </div>
		</div>
           <div class="row">
              <div class="col-6" style="margin-top:-5px">
               <div class="col-4" style="text-align:right">
					<label>时间</label>
					<select id="timeType" name="timeType"style="width: 100px;"onchange="changeTime()">
						<option value="0" selected="selected">按日</option> 
                        <option value="1">按月</option>
					</select>
			   </div>
			    <div class="col-8" style="text-align:left" id="time1">
					<input style="width:40%;" id="startTime1" name="startTime1" type="text" placeholder="开始日期" value="" class="searchDate1"readonly="readonly">
					    至
		            <input style="width:40%;" id="endTime1" name="endTime1" type="text" placeholder="结束日期" value="" class="searchDate1"readonly="readonly">
				 </div>
				  <div class="col-8" id="time" style="text-align:left;display:none">
					<input style="width:40%;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate"readonly="readonly">
					    至
		            <input style="width:40%;" id="endTime" name="endTime" type="text" placeholder="结束日期" value="" class="searchDate"readonly="readonly">
				 </div>
				</div>
				<div class="col-3">
					<label>客户名称</label>
					<select id="customer" name="customer"style="width: 200px;">
						<option value="" selected="selected">全部</option>
						<c:forEach var="customer" items="${customer}">
							<option value="${customer.id}">${customer.text}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-3">
					<label>入账状态</label>
					<select id="accounttype" name="accounttype"style="width: 233px;">
						<option value="" selected="selected">全部</option>
						<option value="2">未付结</option> 
                        <option value="3">已付结</option> 
                        <option value="4">未支付</option> 
                        <option value="5">已支付</option> 
                        <option value="6">未结算</option> 
                        <option value="7">已结算</option> 
					</select>
				</div>
			</div>
			<div class="row">
			<div class="col-12" style="text-align:right">
					<button class="Mbtn red_q" onclick="search()">查询
		           </button>
		           <button class="Mbtn grey_w" id="reset" onclick="reset()">清空</button>
				</div>
			</div>
           <table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
            </div>
        <div class="row">
		   <div class="col-8"><h2>按司机统计</h2></div>
		   <div class="col-4" style="text-align:right;border-bottom: 1px solid #dbdbdb;padding-bottom:7px">
	       <button class="Mbtn blue_q" style="margin-top:10px"onclick="exportExcel1()">导出数据</button>
	       </div>
		</div>
		 <div class="row">
              <div class="col-6" style="margin-top:-5px">
               <div class="col-4" style="text-align:right">
					<label>时间</label>
					<select id="timeType1" name="timeType1"style="width: 100px;"onchange="changeTime1()">
						<option value="0" selected="selected">按日</option> 
                        <option value="1">按月</option>
					</select>
			   </div>
			    <div class="col-8" style="text-align:left" id="time2">
					<input style="width:40%;" id="startTime2" name="startTime2" type="text" placeholder="开始日期" value="" class="searchDate1"readonly="readonly">
					    至
		            <input style="width:40%;" id="endTime2" name="endTime2" type="text" placeholder="结束日期" value="" class="searchDate1"readonly="readonly">
				 </div>
				  <div class="col-8" id="time3" style="text-align:left;display:none">
					<input style="width:40%;" id="startTime3" name="startTime3" type="text" placeholder="开始日期" value="" class="searchDate"readonly="readonly">
					    至
		            <input style="width:40%;" id="endTime3" name="endTime3" type="text" placeholder="结束日期" value="" class="searchDate"readonly="readonly">
				 </div>
				</div>
				<div class="col-3">
					<label>客户名称</label>
					<select id="customer1" name="customer1"style="width: 200px;">
						<option value="" selected="selected">全部</option>
						<c:forEach var="customer" items="${customer}">
							<option value="${customer.id}">${customer.text}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-3">
					<label>司机</label>
					<input id="driver" name="driver" type="hidden" placeholder="姓名/手机号"style="width:64%;">
				</div>
			</div>
			<div class="row">
				<div class="col-4" style="margin-left:4.5%">
				<label>入账状态</label>
					<select id="accounttype1" name="accounttype1"style="width: 233px;">
						<option value="" selected="selected">全部</option>
						<option value="2">未付结</option> 
                        <option value="3">已付结</option> 
                        <option value="4">未支付</option> 
                        <option value="5">已支付</option> 
                        <option value="6">未结算</option> 
                        <option value="7">已结算</option> 
					</select>
				</div>
			</div>
			<div class="row">
			<div class="col-12" style="text-align:right">
					<button class="Mbtn red_q" onclick="search1()">查询
		           </button>
		           <button class="Mbtn grey_w" id="reset" onclick="reset1()">清空</button>
				</div>
			</div>
           <table id="dataGrid1" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
            </div>
           </div>
		<script type="text/javascript" src="js/opTravelexpensesstatistics/index.js"></script>
	</body>
</html>

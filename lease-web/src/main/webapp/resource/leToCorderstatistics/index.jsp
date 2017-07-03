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
		<title>toC订单统计</title>
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
	</head>
	
	
	<body style="overflow-x:hidden;overflow-y:hidden">
	 <!-- <body> -->
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页 </a>> toC订单统计<style type="text/css">
	     a{color:#000;text-decoration:underline;}
	   h2{line-height:250%;border-bottom:1px solid #dbdbdb;margin:0px -20px 20px -20px;padding-left:20px;}
       .form select{width:68%;}
			.select2-container .select2-choice{height:30px;}
			.select2-container{width:68%;padding:0px;}
			.dataTables_wrapper{margin: 0px 0px 10px 0px;}
			.breadcrumb{text-decoration:underline;}
			th, td { white-space: nowrap; }
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
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
		tbody tr td:first-child{
		}
		.stab{border-bottom:2px solid #ededed;}
			.stab>div{display:inline-block;padding:4px 14px;text-align:center;}
			.shen_on{background:#ededed;}
	</style></div>
		<div class="content">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden"/>
		 <h2 style="padding-top: 30px;"><img style="width:19px;height:19px;" src="img/index/icon_car.png">&nbsp;&nbsp;&nbsp;网约车订单统计</h2>
		 <div style="padding-top: 10px;">
		    <ul class="tabbox">
				<li style="display:block">
					<div class="stab">
						<div id="carTimeA"><a href="javascript:carTime();"style="text-decoration:none;">按月度统计</a></div>
						<div id="carCityA"><a href="javascript:carCity(this);"style="text-decoration: none;">按城市统计</a></div>
						<div id= "carTimeE"style="float:right;margin-top:-10px;"><button class="Mbtn blue_q" onclick="exportOrganExcel()">导出数据</button></div>
						<div id= "carCityE"style="float:right;margin-top:-10px;display:none"><button class="Mbtn blue_q" onclick="exportOrganExcel1()">导出数据</button></div>
					</div>
					<div class="stabox">
					 </div>
               </li>
           </ul>
         </div>
		<div id ="carTime"style="padding-top: 10px;">
         <div class="row">
               <div class="col-3"text-align: right;">
					<label>时间</label>
	            		<input style="width:30%;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate"readonly="readonly">至
	            		<input style="width:30%;" id="endTime" name="endTime" type="text" placeholder="结束日期" value="" class="searchDate"readonly="readonly">
					</div>
			   <div class="col-3"text-align: right;">
					<label>业务类型</label>
					<select id="ordertype" name="ordertype"style="width: 132px;padding-top: 5px;padding-bottom: 5px;text-align:right">
						<option value="" selected="selected">全部</option>
						<c:forEach var="ordertype" items="${ordertype}">
							<option value="${ordertype.value}">${ordertype.text}</option>
						</c:forEach>
					</select>
				</div>
             <div class="col-3" style="text-align: right;">
           <button class="Mbtn red_q" onclick="search()">查询
           </button>
           <button class="Mbtn grey_w" id="reset" onclick="reset()">清空</button>
           </button>
           </div>
           </div>
           <table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
           </div>
           
           <div id ="carCity"style="padding-top: 10px;">
           <div class="row" >
               <div class="col-3" text-align: right;">
					<label>时间</label>
	            		<input style="width:30%;" id="startTime1" name="startTime1" type="text" placeholder="开始日期" value="" class="searchDate"readonly="readonly">
					</div>
				<div class="col-3"text-align: right;>
					<label>城市</label> 
					<select id="city" name="city"style="width: 132px;padding-top: 5px;padding-bottom: 5px;">
						<option value="" selected="selected">全部</option>
						<c:forEach var="pubCityAddr" items="${pubCityAddr}">
							<option value="${pubCityAddr.id}">${pubCityAddr.city}</option>
						</c:forEach>
					</select>
			    </div>
             <div class="col-3" style="text-align: right;">
           <button class="Mbtn red_q" onclick="search1()">查询
           </button>
            <button class="Mbtn grey_w" id="reset" onclick="reset1()">清空</button>
           </button>
           </div>
           </div>
           <table id="dataGrid1" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
           </div>
           
		 <h2><img style="width:19px;height:19px;" src="img/index/icon_taxi.png">&nbsp;&nbsp;&nbsp;出租车订单统计</h2>
           <div style="padding-top: 10px;">
		    <ul class="tabbox">
				<li style="display:block">
					<div class="stab">
						<div id="taxiTimeA"><a href="javascript:taxiTime();"style="text-decoration: none;">按月度统计</a></div>
						<div id="taxiCityA"><a href="javascript:taxiCity(this);"style="text-decoration: none;">按城市统计</a></div>
						<div id= "taxiTimeE"style="float:right;margin-top:-10px;"><button class="Mbtn blue_q" onclick="exportOrganExcel2()">导出数据</button></div>
						<div id= "taxiCityE"style="float:right;margin-top:-10px;display:none"><button class="Mbtn blue_q" onclick="exportOrganExcel3()">导出数据</button></div>
					</div>
					<div class="stabox">
					  </div>
               </li>
           </ul>
		</div>
			<div id="taxiTime"style="padding-top: 10px;">
             <div class="row" >
               <div class="col-6">
					<label>时间</label>
	            		<input style="width:15%;" id="startTime2" name="startTime2" type="text" placeholder="开始日期" value="" class="searchDate"readonly="readonly">至
	            		<input style="width:15%;" id="endTime2" name="endTime2" type="text" placeholder="结束日期" value="" class="searchDate"readonly="readonly">
					</div>
             <div class="col-3" style="text-align: right;">
           <button class="Mbtn red_q" onclick="search2()">查询
           </button>
           <button class="Mbtn grey_w" id="reset" onclick="reset2()">清空</button>
           </button>
           </div>
           </div>
           <table id="dataGrid2" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
           </div>
           <div  id = "taxiCity"style="padding-top: 10px;">
            <div class="row">
               <div class="col-3">
					<label>时间</label>
	            		<input style="width:30%;" id="startTime3" name="startTime3" type="text" placeholder="开始日期" value="" class="searchDate"readonly="readonly">
					</div>
				<div class="col-3">
					<label>城市</label> 
					<select id="city1" name="city1"style="width: 132px;padding-top: 5px;padding-bottom: 5px;">
						<option value="" selected="selected">全部</option>
						<c:forEach var="pubCityAddr" items="${pubCityAddr}">
							<option value="${pubCityAddr.id}">${pubCityAddr.city}</option>
						</c:forEach>
					</select>
			       </div>
             <div class="col-3" style="text-align: right;">
           <button class="Mbtn red_q" onclick="search3()">查询
           </button>
            <button class="Mbtn grey_w" id="reset" onclick="reset3()">清空</button>
           </button>
           </div>
           </div>
           <table id="dataGrid3" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
            </div>
		</div>

		<script type="text/javascript" src="js/leToCorderstatistics/index.js"></script>
	</body>
</html>

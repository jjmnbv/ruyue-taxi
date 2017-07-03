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
		<title>司机订单统计</title>
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
	
	<style type="text/css">
	      .clear:after,.row1:after {clear:both;content:' ';display:block;}
			[class*=col1]{float: left;line-height: 150%;padding:6px 10px;min-height:1px;}
			.col1-1 {width: 8.33333333%;}
			.col1-2 {width: 16.66666667%;}
			.col1-3 {width: 25%;}
			.col1-4 {width: 33.33%;}
			.col1-5 {width: 41.66666667%;}
			.col1-6 {width: 50%;}
			.col1-7 {width: 58.33333333%;}
			.col1-8 {width: 66.66666667%;}
			.col1-9 {width: 75%;}
			.col1-10 {width: 83.33333333%;}
			.col1-11 {width: 91.66666667%;}
			.col1-12 {width: 100%;}
	   a{color:#000;text-decoration:underline;}
	   h2{line-height:250%;border-bottom:1px solid #dbdbdb;margin:0px -20px 20px -20px;padding-left:20px;}
       .radius{border:1px solid #ddd;border-radius:5px;text-align:center;}
       .radius>.col1-6{height:50px;}
       .radius>.col1-3{height:50px;}
       .radius strong{display:block;line-height: 150%; }
       .radius img{width:20px;height:20px;margin-bottom:10px;}
       .order_tittle{border-bottom:1px solid #ddd;font-size:16px;}
       .order_num {border-bottom:1px solid #ddd;font-size:27px;font-weight:bold;line-height: 37px;}
       .order_num1 {border-bottom:1px solid #ddd;border-left:1px solid #ddd;line-height: 37px;}
       .radius strong+strong{margin-top:20px;}
       .col1-2 .radius{padding:30px 0;margin-top:10px;}
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
			
			
		@media screen and (min-width:790px) and (max-width:890px){
			.lepersonal_css_col_1 .order_tittle{
				padding:6px 0;
			}
			.lepersonal_css_col_2 .order_tittle{
				padding:6px 0;
			}
			.lepersonal_css_col_3 .order_tittle1{
				padding:6px 0;
			}
		}
		.lepersonal_css_col_1 .order_num{
				padding:6px 0;
			}
	</style>

    <body style="overflow-x:hidden;overflow-y:hidden">
	<!-- <body> -->
	<div class="crumbs">
		<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页 </a>> 司机订单统计</div>
		<div class="content">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<input name="organId" id="organId" value="${organId}" type="hidden">
			<div class="form" style="padding-top: 30px;">
			<div class="row">
				<div class="col-4"style="text-align: center;">
					<label>司机</label>
					<input id="driver" name="driver" type="hidden" placeholder="姓名/手机号"style="width:64%;">
				</div>
					<div class="col-4" style="text-align: center;">
					<label>时间</label>
	            		<input style="width:30%;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate"readonly="readonly">至
	            		<input style="width:30%;" id="endTime" name="endTime" type="text" placeholder="结束日期" value="" class="searchDate"readonly="readonly">
					</div>
					<div class="col-4"style="text-align: center;">
                    <label>车牌</label>
				   <input id="plateno" name="plateno" type="hidden" placeholder="车牌"style="width:64%;">
				</div>
				</div>
			<div class="row">
			<div class="col-4"style="text-align: center;">
					<label >品牌车系</label>
					<input id="vehcBrand" name="vehcBrand" type="hidden" placeholder="请输入车系名称"style="width:64%;">
				</div>
			<div class="col-4"style="text-align: center;">
                    <label>资格证号</label>
				   <input id="jobnum" name="jobnum" type="hidden" placeholder="司机资格证号"style="width:64%;">
				</div>
				<div class="col-4" style="text-align: right;">
					<button class="Mbtn red_q" onclick="search();">查询</button>
					<button class="Mbtn grey_w" id="reset" onclick="reset()">清空</button>
				</div>
			</div>
			</div>
			<h2><img style="width:19px;height:19px;" src="img/index/icon_census.png">&nbsp;&nbsp;&nbsp;订单统计</h2>
			<div class="row1">
                <div class="col1-4 lepersonal_css_col_1">
	                <div class="radius row1">
		                <div class="col-4" style="border-bottom:1px solid #ddd;line-height:30px;"><img src="img/index/icon_order.png">
		                	<span style="vertical-align:text-bottom;margin-left:5px;font-size:20px;"id="">总订单数</span>
		                </div>
		                <div class="order_num font_red col1-2" id="sumAllOrders"></div>
		                <div class="col-4" style="border-bottom:1px solid #ddd;line-height:30px;border-left:1px solid #ddd;"><img style="margin-bottom:px;" src="img/index/icon_variant.png">
		                	<span style="vertical-align:text-bottom;margin-left:5px;font-size:20px"id="">差异订单</span>
		                </div>
		                <div class="order_num col1-2" style="font-size:20px" id="sumOrderreview"></div>
		                <br>
		                <div class="col1-3" style="border-right:1px solid #ddd;">
		                <img style="width: 30px;height:30px;margin-bottom:0px;" src="img/home/icon_jieji.png">
		                <span style="vertical-align:text-bottom;margin-left:5px;font-size:20px;font-weight:bold"id="sumPickporders"></span>
		                </div>
		                 <div class="col1-3"style="border-right:1px solid #ddd;">
		                 <img style="width: 30px;height:30px;margin-bottom:0px;" src="img/home/icon_songji.png">
		                	<span style="vertical-align:text-bottom;margin-left:5px;font-size:20px;font-weight:bold"id="sumDropofforders"></span>
		                </div>
		                <div class="col1-3" style="border-right:0px solid #ddd;">
		                <img style="width: 30px;height:30px;margin-bottom:0px;" src="img/home/icon_yuyue.png">
		                	 <span style="vertical-align:text-bottom;margin-left:5px;font-size:20px;font-weight:bold"id="sumCarorders"></span>
		                </div>
		                <div class="col1-3" style="border-left:1px solid #ddd;">
		                <img style="width: 28px;height:28px;margin-bottom:0px;" src="img/home/icon_taxi_28x28.png">
		                	 <span style="vertical-align:text-bottom;margin-left:5px;font-size:20px;font-weight:bold"id=sumTaxiOrders></span>
		                </div>
	                </div>
            	</div>
            	<div class="col1-4 lepersonal_css_col_1">
	                <div class="radius row1">
		                <div class="order_tittle col1-6"><img margin-bottom:0px;" src="img/index/icon_money.png">
		                	<span style="vertical-align:text-bottom;margin-left:5px;font-size:20px"id="">收益金额</span></div>
		                <div class="order_num font_red col1-6" id="sumIncomePrice"></div>
		                <div class="col1-6" style="border-right:1px solid #ddd;">
		                  <span id="sumOrdermoney"style="font-weight:bold;font-size:18px"></span><br>
		                    <span style="font-size:18px"id=>订单金额</span>
		                </div>
		                 <div class="col1-6">
		                 <span id="sumOrderreviewPrice"style="font-weight:bold;font-size:18px"></span><br>
		                 <span style="font-size:18px"id=>差异金额</span>
		                </div>
	                </div>
            	</div>
            	<div class="col1-4 lepersonal_css_col_3">
	                <div class="radius row1">
		                <div class="order_tittle1 col1-6"><img src="img/index/icon_abnormal.png">
		                	<span style="margin-left:5px;font-size:18px">异常订单</span></div>
		                <div class="col1-6" style="border-left:1px solid #ddd;border-bottom:1px solid #ddd;">
		                <span id="sumConfirmedorders"></span></br>
		                <span id="">已处理</span>
		                </div>
		                <div class="order_num1 col1-6"id="sumAlluporders"></div>
		                 <div class="col1-6"style="border-left:1px solid #ddd">
		                 <span id="sumProcessedorders"></span></br>
		                 <span id="">待处理</span></br>
		                	
		                </div>
	                </div>
            	</div>
            </div>
           <div class="row">
           <div class="col-8"><h2><img style="width:19px;height:19px;" src="img/index/icon_liebiao.png">&nbsp;&nbsp;&nbsp;订单统计信息</h2></div>
           <div class="col-4" style="text-align: right;border-bottom: 1px solid #dbdbdb;padding-bottom:17px">
           <button class="Mbtn blue_q" onclick="exporttoCExcel()">导出数据
           </button>
           </div>
           <table id="dataGridtoC" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
           </div>
		</div>
		<script type="text/javascript" src="js/leDriverorderstatistics/index.js"></script>
	</body>
</html>

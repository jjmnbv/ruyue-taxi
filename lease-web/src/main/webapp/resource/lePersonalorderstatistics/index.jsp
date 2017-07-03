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
		<title>个人订单统计</title>
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
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页 </a>> 个人订单统计<style type="text/css">
	   [class*=Acol]{float: left;line-height: 150%;padding:6px 10px;min-height:1px;}
	    .clear:after,.row1:after {clear:both;content:' ';display:block;}
		.Acol-1 {width: 8.33333333%;}
		.Acol-2 {width: 16.66666667%;}
		.Acol-3 {width: 25%;}
		.Acol-4 {width: 33.33%;}
		.Acol-5 {width: 41.66666667%;}
		.Acol-6 {width: 50%;}
		.Acol-7 {width: 58.33333333%;}
		.Acol-8 {width: 66.66666667%;}
		.Acol-9 {width: 75%;}
		.Acol-10 {width: 83.33333333%;}
		.Acol-11 {width: 91.66666667%;}
		.Acol-12 {width: 100%;}
		a{color:#000;text-decoration:underline;}
	   h2{line-height:250%;border-bottom:1px solid #dbdbdb;margin:0px -20px 20px -20px;padding-left:20px;}
       .radius{border:1px solid #ddd;border-radius:5px;text-align:center;}
       .radius>.Acol-6{height:50px;}
       .radius strong{display:block;line-height: 150%; }
       .radius img{width:20px;height:20px;margin-bottom:10px;}
       .order_tittle{line-height:50px;border-bottom:1px solid #ddd;font-size:16px;line-height: 0px;}
       .order_tittle1{line-height:50px;font-size:16px;line-height: 0px;}
       .order_num {border-bottom:1px solid #ddd;font-size:20px;font-weight:bold;line-height: 37px;}
       .order_num1 {border-bottom:0px solid #ddd;border-left:0px solid #ddd;font-size:20px;font-weight:bold;}
       .order_num2 {border-bottom:1px solid #ddd;border-left:1px solid #ddd;line-height: 37px;}
       .radius strong+strong{margin-top:20px;}
       .Acol-2 .radius{padding:30px 0;margin-top:10px;}
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
			.lepersonal_css_col_1 .Acol-4{
				padding:6px 0;
			}
			.lepersonal_css_col_2 .order_tittle{
				padding:6px 0;
			}
			.lepersonal_css_col_3 .order_tittle1{
				padding:6px 0;
			}
		}
		@media screen and (min-width:790px) and (max-width:1200px){
			.lepersonal_css_col_4 label{
				padding:6px 0;
				width:40%;
			}
			.lepersonal_css_col_4 select{
				width:85px !important;
			}
		}
	</style></div>
		<div class="content">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<h2 style="padding-top:30px"><img style="width:19px;height:19px;" src="img/index/icon_census.png">&nbsp;&nbsp;&nbsp;订单统计（注：个人结算订单）</h2>
		<div class="row from">
					<div class="col-3" style="padding-top: 0px; width:40%">
					<label class="col-2" style="text-align:right;">时间</label>
	            		<input style="width:30%;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate"readonly="readonly">至
	            		<input style="width:30%;" id="endTime" name="endTime" type="text" placeholder="结束日期" value="" class="searchDate"readonly="readonly">
					</div>
					 <button class="Mbtn red_q" onclick="search1()">查询</button>
					  <button class="Mbtn grey_w" id="reset" onclick="reset1()">清空</button>
		</div>
			<div class="row1">
                <div class="Acol-4 lepersonal_css_col_1">
	                <div class="radius row1">
		                <div class="col-4" style="border-bottom:1px solid #ddd;line-height:30px;">
		                <img src="img/index/icon_order.png">
		                	<span style="vertical-align:text-bottom;margin-left:5px;font-size:20px;"id="">订单总数</span>
		                </div>
		                <div class="order_num font_red Acol-2" id="sumAllOrders"></div>
		                <div class="col-4" style="border-bottom:1px solid #ddd;line-height:30px;border-left:1px solid #ddd;"><img style="margin-bottom:px;" src="img/index/icon_variant.png">
		                	<span style="vertical-align:text-bottom;margin-left:5px;font-size:20px"id="">差异订单</span>
		                </div>
		                <div class="order_num col-2" style="font-size:20px" id="sumOrderreview"></div>
		                <br>
		                <div class="Acol-4" style="border-right:1px solid #ddd;height:50px;">
		                <img style="width: 30px;height:30px;margin-bottom:0px;" src="img/home/icon_jieji.png">
		                <span style="margin-left:5px;font-size:20px;font-weight:bold"id="sumPickporders"></span>
		                </div>
		                 <div class="Acol-4"style="border-right:1px solid #ddd;height:50px;">
		                 <img style="width: 30px;height:30px;margin-bottom:0px;" src="img/home/icon_songji.png">
		                	<span style="margin-left:5px;font-size:20px;font-weight:bold"id="sumDropofforders"></span>
		                </div>
		                <div class="Acol-4" style="border-right:0px solid #ddd;height:50px;">
		                <img style="width: 30px;height:30px;margin-bottom:0px;" src="img/home/icon_yuyue.png">
		                	 <span style="margin-left:5px;font-size:20px;font-weight:bold"id="sumCarorders"></span>
		                </div>
	                </div>
            	</div>
            	<div class="Acol-4 lepersonal_css_col_2">
	                <div class="radius row1">
		                <div class="order_tittle Acol-6"><img src="img/index/icon_money.png">
		                	<span style="margin-left:5px;font-size:20px">收益金额</span></div>
		                <div class="order_num font_red Acol-6" id="sumIncomePrice"></div>
		                <div class="Acol-6" style="border-right:1px solid #ddd;">
		                <span id="sumOrdermoney"style="font-weight:bold;font-size:18px"></span><br>
		                	 <span style="font-size:18px">订单金额</span>
		                </div>
		                 <div class="Acol-6">
		                 <span id="sumDiffmoney"style="font-weight:bold;font-size:18px"></span><br>
		                 <span style="font-size:18px">差异金额</span>
		                </div>
	                </div>
            	</div>
            	<div class="Acol-4 lepersonal_css_col_3">
	                <div class="radius row1">
		                <div class="order_tittle1 Acol-6"><img src="img/index/icon_abnormal.png">
		                	<span style="margin-left:5px;font-size:18px">异常订单</span></div>
		                <div class="Acol-6" style="border-left:1px solid #ddd;border-bottom:1px solid #ddd;">
		                <span id="sumConfirmedorders"></span></br>
		                <span id="">已处理</span>
		                </div>
		                <div class="order_num1 Acol-6"id="sumAlluporders"></div>
		                 <div class="Acol-6"style="border-left:1px solid #ddd">
		                 <span id="sumProcessedorders"></span></br>
		                 <span id="">待处理</span></br>
		                	
		                </div>
	                </div>
            	</div>
            </div>
            <div class="row">
           <div class="col-8">
           <h2><img style="width:19px;height:19px;" src="img/index/icon_month.png">&nbsp;&nbsp;&nbsp;按月度统计</h2>
           </div>
           <div class="col-4" style="text-align: right;border-bottom: 1px solid #dbdbdb;padding-bottom:17px">
           <button class="Mbtn blue_q" onclick="exportOrganExcel2()">导出数据
           </div>
           </div>
           <div class="row">
					<div class="col-3" style="padding-top: 0px;text-align: center;">
					<label>时间</label>
	            		<input style="width:30%;" id="startTime1" name="startTime1" type="text" placeholder="开始日期" value="" class="searchDate"readonly="readonly">至
	            		<input style="width:30%;" id="endTime1" name="endTime1" type="text" placeholder="结束日期" value="" class="searchDate"readonly="readonly">
					</div>
					<div class="col-3"style="padding-top: 0px;text-align: center;">
					<label>业务类型</label>
					<select id="ordertype" name="ordertype"style="width: 132px;padding-top: 5px;padding-bottom: 5px;">
						<option value="" selected="selected">全部</option>
						<c:forEach var="ordertype" items="${ordertype}">
							<option value="${ordertype.value}">${ordertype.text}</option>
						</c:forEach>
					</select>
				    </div>
				    <div class="col-3"style="padding-top: 0px;text-align: center;">
					<label>支付类型</label>
					<select id="paymethod" name="paymethod" style="width: 132px;padding-top: 5px;padding-bottom: 5px;">
						<option value="" selected="selected">全部</option>
					</select>
				    </div>
				     <div class="col-3" style="text-align: right;">
				          <button  class="Mbtn red_q" onclick="search2()">查询
				          </button>
				          <button class="Mbtn grey_w" id="reset" onclick="reset2()">清空</button>
                    </div>
		</div>
           <table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
           <div class="row">
           <div class="col-8">
			 <h2><img style="width:19px;height:19px;" src="img/index/icon_city.png">&nbsp;&nbsp;&nbsp;按城市统计</h2>
		   </div>
		  <div class="col-4" style="text-align: right;border-bottom: 1px solid #dbdbdb;padding-bottom:17px">
		    <button class="Mbtn blue_q" onclick="exportOrganExcel3()">导出数据
		   </div>
		   </div>
			 <div class="row">
					<div class="col-3" style="padding-top: 0px;text-align: center;">
					<label>时间</label>
	            		<input style="width:30%;" id="startTime2" name="startTime2" type="text" placeholder="开始日期" value="" class="searchDate"readonly="readonly">
					</div>
					<div class="col-3"style="padding-top: 0px;text-align: center;">
					<label>业务类型</label>
					<select id="ordertype1" name="ordertype1"style="width: 132px;padding-top: 5px;padding-bottom: 5px;">
						<option value="" selected="selected">全部</option>
						<c:forEach var="ordertype" items="${ordertype}">
							<option value="${ordertype.value}">${ordertype.text}</option>
						</c:forEach>
					</select>
				    </div>
				   <div class="col-3"style="padding-top: 0px;text-align: center;">
					<label>所属城市</label> 
					<select id="city" name="city" style="width: 132px;padding-top: 5px;padding-bottom: 5px;">
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
		           </div>
		</div>
           <table id="dataGrid1" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>

		<script type="text/javascript" src="js/lePersonalorderstatistics/index.js"></script>
	</body>
</html>

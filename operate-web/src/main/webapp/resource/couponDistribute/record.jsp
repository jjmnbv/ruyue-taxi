<%@ page contentType="text/html; charset=UTF-8" import="com.szyciov.util.SystemConfig"%>
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
		<title>发放记录</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<style type="text/css">
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		.tip_box_b input[type=text]{width: 63%;}
		
		/* 添加城市样式 */
		#pubCityaddr{position:absolute;display:inline-block;}
		#pubCityaddr>.addcitybtn{background: #997C26;padding: 2px 10px;color:#fff;margin-left: 13px;}
		#pubCityaddr .kongjian_list{top:26px!important;}
		.added{display:inline-block;padding:2px 4px;}
		.added .ico_x_a{float:right;margin-left: -6px;}
		.addcbox{padding:6px 10px;margin:0px 37px 10px 37px;border:1px solid #ccc;min-height:100px;line-height:30px;}
		.kongjian_list .box .con {max-height: 200px;overflow-y: auto;}
		th, td { white-space: nowrap; }
		div.dataTables_wrapper {
		  width: $(window).width();
		  margin: 0 auto;
		}
		.DTFC_ScrollWrapper{
		margin-top:-20px;
		}
		.pop_box{z-index: 1111 !important;}
		
		.tip_box_d #plateNoProvince option{
			font-size:12px;
			padding:0 10px;
		}
		.tip_box_d #plateNoCity option{
			font-size:12px;
			padding:0 10px;
		}
		#pubCityaddr>.city_container{
			margin-left: 12px;
		}
		.ico_x_a{
			display:block;
		}
		</style>
		
		<script type="text/javascript" src="content/js/jquery.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
		<script type="text/javascript"  src="content/js/bootstrap.min.js"></script>
		<script type="text/javascript"  src="content/js/common.js"></script>
		
		<script type="text/javascript" src="content/plugins/data-tables/js/jquery.dataTables.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.bootstrap.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fnSearch.js"></script>
		<script type="text/javascript" src="content/plugins/data-tables/js/dataTables.fixedColumns.js"></script>
		<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript"  src="content/plugins/cityselect1/cityselect1.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.iframe-transport.js"></script>

	</head>
	<body style="overflow-x:hidden;overflow-y:hidden;">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > <a class="breadcrumb" href="Couponing/Index">派发管理</a> > 发放记录
			<button class="SSbtn blue back" onclick="window.history.back();">返回</button>
		</div>
		<div class="content">
		  <div class="row">
	            		<input type="hidden" id="id" value="${data.id}"/>
	            		<div class="col-8">
	            		        截至当前：累计发放<span>${data.total}</span>张，
	            		        累计发放金额<span>${data.totalmoney}</span>元，
	            		        累计使用金额<span>${data.usedtotalmoney}</span>元，
	            		        使用率为<span>${data.usedpercent}</span>%，
	            		        废弃率为<span>${data.abandonedpercent}</span>%。
	            		</div>
	        </div>
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-3"><label>领取账号</label>
						<input id="account" name="account" type="text" placeholder="全部" />
					</div>
					
					 <div class="col-3"><label>发放时间</label>
						<input style="width:30%;" id="sendstarttime" name="sendstarttime" readonly="readonly" type="text" placeholder="开始日期" value="" class="searchDate">
						至
			            <input style="width:30%;" id="sendendtime" name="sendendtime" readonly="readonly" type="text" placeholder="结束日期" value="" class="searchDate">
					 </div>
					 <div class="col-3"><label>抵用券状态</label>
						<select id="couponstatus" name="couponstatus" >
							<option value="">全部</option>
							<option value="0">未使用</option>
							<option value="1">已使用</option>
							<option value="2">已过期</option>
						</select>
					</div>
					<div class="col-3" style="text-align: right;">
						<button class="Mbtn green_a" onclick="search();">查询</button>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-12" style="margin-top: 15px;"><h4>发放记录【<span class="font_red">${data.name}</span>】</h4></div>
		    </div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<script type="text/javascript" src="js/couponDistribute/record.js"></script>
		<script type="text/javascript">
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
		</script>
	</body>
</html>

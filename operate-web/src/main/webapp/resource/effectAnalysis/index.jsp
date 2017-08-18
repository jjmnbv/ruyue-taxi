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
		<title>成效分析</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/css/laterchange.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="css/couponDistribute/letterselect.css">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<style type="text/css">
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.dataTables_wrapper{margin: 0px 0px 10px 0px;}
		.breadcrumb{text-decoration:underline;}
		.tip_box_b input[type=text]{width: 63%;}
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
		.ico_x_a{
			display:block;
		}
         .col-3{
            width:33%;
        } 
        h4{
           font-weight: bold;
        }
/*         .col-sm-12{
           margin-left: 3.4%;
        }
        .col-sm-7{
           width: 42%
        }
   */
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
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 成效分析
		</div>
		<div class="content">
		<div class="form" style="padding-top: 30px;">
		<div class="row" style="margin-bottom: 30px;padding-top: 30px;">
	            		<div class="col-8" style="padding-left:4.4%;">
	            		   <h4>
	            		        新用户充值率
	            		   </h4>
	            		</div>
	    </div>
	    <div class="row">
				    <div class="col-3" style="width:42%;"><label style="width:16%;">时间</label>
				        <select id="rdatetype" style="width:15%;">
							<option value="0">按日</option>
							<option value="1">按月</option>
						</select>
						<input style="width:25%;" id="querysendstarttime" name="querysendstarttime" readonly="readonly" type="text" placeholder="开始日期" value="" class="searchDate">
						至
			            <input style="width:25%;" id="querysendendtime" name="querysendendtime" readonly="readonly" type="text" placeholder="结束日期" value="" class="searchDate">
					</div>
					<div class="col-3">
						<button class="Mbtn green_a" onclick="searchRechargePercent();">查询</button>
					</div>
	    </div>
	    <table id="rechargeGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		
		<div class="row" style="margin-bottom: 30px;margin-top: 30px;">
	            		<div class="col-8" style="padding-left:4.4%;">
	            		       <h4> 抵用券使用率</h4>
	            		</div>
	    </div>
	     <div class="row">
	                 
				    <div class="col-3" style="width:42%;"><label style="width:16%;">时间</label>
				        <select id="cdatetype" style="width:15%;">
							<option value="0">按日</option>
							<option value="1">按月</option>
						</select>
						<input style="width:25%;" id="usedstarttime" name="usedstarttime" readonly="readonly" type="text" placeholder="开始日期" style="width:25%;" value="" class="searchDate">
						至
			            <input style="width:25%;" id="usedendtime" name="usedendtime" readonly="readonly" type="text" placeholder="结束日期" style="width:25%;"  value="" class="searchDate">
					</div>
					<div class="col-3" style="width:25%;"><label>城市名称</label>
					    <input id="city" name="city" type="text" style="width: 68%;">
					</div>
					<div class="col-3" style="width:25%;">
						<button class="Mbtn green_a" onclick="search();" style="margin-left: 30px;">查询</button>
						<button class="Mbtn gary" style="margin-left: 10px;" onclick="emptys();">清空</button>
					</div>
	    </div>
	   <table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		</div>
		<script type="text/javascript" src="js/effectAnalysis/index.js"></script> 
		<script type="text/javascript">
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
		</script>
	</body>
</html>

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
		<title>销售管理</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/opOrderstatistics.css" />
		
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
	</head>
	<body class="opOrderstatistics_css_body">
	 <div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页 </a>> 销售管理
	 <style type="text/css">
	 h2{line-height:250%;border-bottom:2px solid #dbdbdb;margin:0px -20px 20px -20px;padding-left:20px;}
	.radius{border:1px solid #ddd;border-radius:5px;text-align:center;}
	.radius>.col-6{height:50px;}
	.radius strong{display:block;line-height: 130%; }
	.order_tittle{line-height:50px;font-size:10px ;color:#FFFFFF;line-height: 38px;}
	.order_num {border-bottom:1px solid #ddd;font-size:20px;font-weight:bold;line-height: 38px;}
	.order_num1 {font-size:25px;font-weight:bold;line-height: 38px;color:#FFFFFF}
	.order_num2 {font-size:15px;line-height: 38px;color:#FFFFFF}
	.radius strong+strong{margin-top:20px;}
	.col-2 .radius{padding:30px 0;margin-top:10px;}
	.tabmenu{text-align:left}
	.content{padding-top:20px;height:940px;}
	 .tabmenu .li{
					width:120px;
					height:60px;
					line-height:60px;
					background:#E0E0E0;
					font-size:10px;
					display:block;
					text-align:center;
					text-decoration:none;
					border-right:1px solid 	#ededed;
					float:left; 
					list-style-type:none;
					}
     .tabmenu .li:hover{ 
						width:120px;
						height:60px;
						line-height:60px;
						background:#FF8000;
						text-align:center;
                       }
     .ok{
                        width:120px;
						height:60px;
						line-height:60px;
						background:#FF7400;
						text-align:center;
						border-radius:5px;
						color: #FFE473;
     }
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
		.tabmenu a{text-decoration:none;}
	 </style>
	 </div>
	 <div class="content">
	 <input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
	     <div class="row">
                <div class="col-3">
	                <div class="radius row opOrderstatistics_css_row_1" style="background-color:#00CACA">
		                <div class="order_tittle col-6" style="height:100px;">
		                	<span style="vertical-align:text-bottom;font-size:15px;">截止日前</span>
		                </div>
		                <div class="order_num1 col-6" id="totalcount"></div>
		                <div class="order_num2 col-6" id="totalcount">订单合计</div>
		                <div class="order_num1 col-6 opOrderstatistics_css_col_3" id="totalcount"></div>
		                <div class="order_num1 col-6 opOrderstatistics_css_col_1" id="orders"></div>
	                </div>
            	</div>
                  <div class="col-3">
	                <div class="radius row opOrderstatistics_css_row_1" style="background-color:#00CACA">
		                <div class="order_tittle col-6" style="height:100px;">
		                	<span style="vertical-align:text-bottom;font-size:15px;">截止日前</span>
		                </div>
		                <div class="order_num1 col-6" id="totalcount"></div>
		                <div class="order_num2 col-6 opOrderstatistics_css_col_2" id="totalcount">订单金额合计(元)</div>
		                <div class="order_num1 col-6 opOrderstatistics_css_col_3" id="totalcount"></div>
		                <div class="order_num1 col-6 opOrderstatistics_css_col_1" id="ordersMoney"></div>
	                </div>
            	</div>
            </div>
	    <h2></h2>
	    <div>
        <div class="row" style="margin-bottom: 10px;">
	    <div class="col-12">
			<ul class="tabmenu">
				<li><a href="OpOrderstatistics/month">按月统计</a></li>
				<li><a href="OpOrderstatistics/quarter">按季度统计</a></li>
				<li class="ok">按年度统计</li>
			</ul>
			<button style="float:right ;margin-top:30px;margin-left:20px;" class="Mbtn blue_q" onclick="exportExcel()">导出</button>
			<button style="float:right ;margin-top:30px;" class="Mbtn red_q" onclick="search()">查询</button>
		</div>
       </div>
       <h2></h2>
       </div>
	 <div class="row form">
	   <div class="col-4 " style="padding-top: 0px;">
	     <label>选择年度</label>
	      <input style="width:30%;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate"readonly="readonly">
	   </div>
	    <div class="col-4 " style="padding-top: 0px;">
	     <label>客户名称</label>
	     <select id="custom" name="custom">
						<option value="" selected="selected">全部</option>
						<c:forEach var="custom" items="${custom}">
							<option value="${custom.id}">${custom.compayName}</option>
						</c:forEach>
					</select>
	   </div>
	 </div>
	 <table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
	 <script type="text/javascript" src="js/opOrderstatistics/year.js"></script>
	  <script>
    $(document).ready(function() {
        $(".tabmenu>li").click(function() {
            $(this).addClass("ok").siblings().removeClass("ok");
            var n=$(this).index();
            $(".tabbox>li:eq("+n+")").show().siblings().hide();
        });
    })
</script>
	</body>
</html>
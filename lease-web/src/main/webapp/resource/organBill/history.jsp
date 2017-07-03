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
		<title>机构账单</title>
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
		
		.jindu>li{list-style-type:square;margin-top:10px;text-align:left;margin-left:20px;display:table}
		.jindu span{padding:0px 10px;}
		.j_time{width:150px;display:table-cell;padding-left:20px;background:url(content/img/dian_a.png) no-repeat left 1px;}
		.j_text{width:220px;padding-left:20px;display:table-cell;font-size:12px;word-break:break-all;overflow:auto;}
		.j_timeno{width:150px;display:table-cell;padding-left:20px;background:no-repeat left 1px;}
		
		.tip_box_b #cancellationForm label.error{margin-left: 12%!important;}
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
        div.tip_box_c{overflow-x:hidden;}
        
        
        @media screen and (min-width:790px) and (max-width:1050px){
			.organbill_css_col_1 .select2-container{
				width:60%;
			}
			.organbill_css_col_2{
				padding-left:0;
			}
			.organbill_css_col_2 label{
				width:22%;
			}
			.organbill_css_col_3{
				padding-left:0;
			}
			.organbill_css_col_3 select{
				width:67%;
			}
			.organbill_css_col_4{
				padding-left:0;
				float:right;
				/* width:12%; */
			}
		}
		.organbill_css_col_4{
			float:right;
		}
	</style>
	
	<body>
		<input id="billsId" name="billsId" type="hidden">
		<input id="tabnum" name="tabnum" type="hidden">
		
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 机构账单</div>
		<div class="content">  
			<div class="row form" style="padding-top: 30px;">
			    <ul class="tabmenu">
                    <li><a href="OrganBill/Index" style="text-decoration: none;">当前账单</a></li>
                    <li class="on">历史账单</li>
                </ul>
				
				<input name="organIdHisExport" id="organIdHisExport" type="hidden">
			    <input name="startTimeHisExport" id="startTimeHisExport" type="hidden">
			    <input name="endTimeHisExport" id="endTimeHisExport" type="hidden">
			    <input name="billStateHisExport" id="billStateHisExport" type="hidden">
				
				<div class="col-4 organbill_css_col_1">
					<label>机构</label>
					<input id="organIdHis" name="organIdHis" type="text" placeholder="全部">
					<!-- <select id="organIdHis" name="organIdHis">
						<option value="" selected="selected">全部</option>
						<c:forEach var="orgOrgan" items="${orgOrgan}">
							<option value="${orgOrgan.id}">${orgOrgan.fullName}</option>
						</c:forEach>
					</select> -->
				</div>
				
				<div class="col-4 organbill_css_col_2">
					<label>时间</label>
					<input style="width:30%;" id="startTimeHis" name="startTimeHis" type="text" placeholder="开始日期" value="" class="searchDate" readonly="readonly">至
					<input style="width:30%;" id="endTimeHis" name="endTimeHis" type="text" placeholder="结束日期" value="" class="searchDate" readonly="readonly">					
				</div>
				
				<div class="col-4 organbill_css_col_3">
					<label>状态</label>
					<select id="billStateHis" name="billStateHis">
						<option value="" selected="selected">全部</option>
						<option value="7">已结算</option>
						<option value="8">已作废</option>
					</select>
				</div>

			</div>
			<div class="row">
			   <div class="col-12 organbill_css_col_4" style="text-align: right;">
					<button class="Mbtn green_a" onclick="searchHis();">查询</button>
					<button class="Mbtn grey" onclick="clearParameter();">清空</button>
			   </div>
			</div>
			<div class="row">
			   <div class="col-10" style="margin-top: 15px;"><h4>已处理账单信息</h4></div>
			   <div class="col-2" style="text-align: right;">
			       <button class="Mbtn orange" onclick="exportExcelHis()">导出数据</button>
			   </div>
			</div>
			   <table id="dataGridHis" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>


		</div>
		
		<div class="pop_box" id="logDiv" style="display: none;">
			<div class="tip_box_b form">
	            <h3>查看账单日志</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<ul class="jindu" id="jindu">	            	
	            	</ul>
	            </div>
	        </div>
		</div>


		<script type="text/javascript" src="js/organBill/history.js"></script>
	</body>
</html>

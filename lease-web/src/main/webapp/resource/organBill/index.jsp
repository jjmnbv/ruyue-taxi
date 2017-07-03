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
		.tip_box_c .close {float: right;margin-top: -23px;margin-right: 10px;}
		
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
        .tip_box_c .row {
            margin-right: -5px;
            margin-left: -5px;
        }
        .pop_box{z-index: 1111 !important;}
        #dataGrid_wrapper .DTFC_LeftBodyWrapper tbody tr td:first-child{
			text-align:left!important;
		}
		#dataGridManual_wrapper .col-sm-6{display:none;}
		
		@media screen and (max-width:1050px){
			.organbill_css_col_4 button{
				padding:6px 5px;
			}
		}
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
			}
			.organbill_css_col_5 button{
				padding:6px 5px;
			}
		}
		@media screen and (min-width:790px) and (max-width:1190px){
			.organbill_pop_col_1 label{
				width: 36% !important;
			}
			.organbill_pop_col_1 input{
				width: 61% !important;
			}
			.organbill_pop_col_2 label{
				width: 22% !important;
			}
			.organbill_pop_col_2 .select2-container{
				width: 68% !important;
			}
			.organbill_pop_col_3{
				padding-left:0;
			}
			.organbill_pop_col_3 label{
				width: 22% !important;
			}
			.organbill_pop_col_4{
				padding-left:0;
				padding-right:0;
			}
			.organbill_pop_col_4 label{
				width: 47% !important;
			}
			.organbill_pop_col_4 select{
				width: 50% !important;
			}
			.organbill_pop_col_5 button{
				padding:6px 4px;
			}
		}
	</style>
	
	<body>
		<input id="billsId" name="billsId" type="hidden">
		<input id="tabnum" name="tabnum" type="hidden">
		
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 机构账单</div>
		<div class="content">
			<div class="row form" style="padding-top: 30px;">
			    <ul class="tabmenu">
                    <li class="on">当前账单</li>
                    <li><a href="OrganBill/HistoryIndex" style="text-decoration: none;">历史账单</a></li>
                </ul>
				
				<input name="organIdExport" id="organIdExport" type="hidden">
			    <input name="startTimeExport" id="startTimeExport" type="hidden">
			    <input name="endTimeExport" id="endTimeExport" type="hidden">
			    <input name="billStateExport" id="billStateExport" type="hidden">
				
				<div class="col-4 organbill_css_col_1">
					<label>机构</label>
					<input id="organId" name="organId" type="text" placeholder="全部">
					<!-- <select id="organId" name="organId">
						<option value="" selected="selected">全部</option>
						<c:forEach var="orgOrgan" items="${orgOrgan}">
							<option value="${orgOrgan.id}">${orgOrgan.fullName}</option>
						</c:forEach>
					</select> -->
				</div>
				
				<div class="col-4 organbill_css_col_2">
					<label>时间</label>
					<input style="width:30%;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate" readonly="readonly">至
					<input style="width:30%;" id="endTime" name="endTime" type="text" placeholder="结束日期" value="" class="searchDate" readonly="readonly">					
				</div>
				
				<div class="col-4 organbill_css_col_3">
					<label>状态</label>
					<select id="billState" name="billState">
						<option value="" selected="selected">全部</option>
						<option value="2">待核对</option>
						<option value="3">待机构核对</option>
						<option value="4">待机构付款</option>
						<option value="5">机构退回账单</option>
						<!-- <option value="6">机构已付款</option> -->
					</select>
				</div>

				
			</div>
			<div class="row">
			   <div class="col-12 organbill_css_col_4" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey" onclick="clearParameter();">清空</button>
			   </div>
			</div>
			<div class="row">
			   <div class="col-8" style="margin-top: 15px;"><h4>未处理账单信息</h4></div>
			   <div class="col-4" style="text-align: right;">
			       <button class="Mbtn blue" onclick="manualGenerate();">手动生成账单</button>
			       <button class="Mbtn orange" onclick="exportExcel()">导出数据</button>
			   </div>
			</div>
			   <table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
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
		
		<div class="pop_box" id="waitCheckFormDiv" style="display: none;">
			<div class="tip_box_c">
	            <h3><b>待核账单：<label id="billName"></label>(<label id="billShortName"></label>)</b></h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="row">
	                <form id="waitCheckForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	                <div class="row">
	                    <div class="col-8">
	                         <label>账单编号:</label><label id="billId"></label><br>
	                         <label>账单备注:</label><label id="billRemark"></label><br>
	                         <label>账单金额:￥</label><label id="billMoney"></label>
	                    </div>
	                    <div class="col-4"  style="text-align: right;">
	                         <button type="button" class="Mbtn green_a" onclick="saveCheck()">核对完成</button>
	                    </div>	                    
	                </div>
	                <div class="row">
			            <div class="col-10" style="margin-top: 15px;width:60%;"><h4>对应订单信息</h4></div>
			            <div class="col-2" style="text-align: right;width:40%;">
			                 <button type="button" class="Mbtn blue" onclick="exportOrderExcel()">导出数据</button>
			            </div>
			        </div>
	                <table id="dataGridWaitCheck" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
	                </form>
	            </div>
	        </div>
		</div>
		
		<div class="pop_box" id="cancellationFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="title">作废账单</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="cancellationForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<label style="width:68%;text-align: left;">作废缘由<em class="asterisk"></em>:</label><br>
	            		<textarea style="width:68%;" id="remark" name="remark" class="50" rows="5" placeholder="请填写作废缘由" maxlength="200"></textarea>
	            	</form>
	            	<div class="col-8" style="text-align: right;">
	                     <button class="Mbtn orange" onclick="saveCancellation()">确认作废</button>
	                </div>
	                <div class="col-4" style="text-align: left;">
	                     <button class="Mbtn grey" style="margin-left: 5%;" onclick="canel()">取消</button>
	                </div>
	            </div>
	        </div>
		</div>
		
		<div class="pop_box" id="manualFormDiv" style="display: none;">
			<div class="tip_box_c">
	            <h3>手动生成账单</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="row">
	                <input name="organIdManualSearch" id="organIdManualSearch" type="hidden">
	                <div class="row form">
	                    <form id="manualForm" method="get" class="form-horizontal  m-t" id="frmmodal">  
	                       <div class="col-12">
	                         <div class="col-5 organbill_pop_col_1">
	                              <label>账单名称<em class="asterisk"></em></label>
		                          <input id="name" name="name" type="text" placeholder="请填写账单名称" maxlength="20">
	                         </div>
	                         <div class="col-7">
	                              <label>账单备注</label>
		                          <input id="remarkManual" name="remarkManual" type="text" placeholder="" maxlength="30">
		                     </div>
		                   </div>
	                    </form> 
	                    <div class="col-4 organbill_pop_col_2">
					      <label>机构</label>
					      <input id="organIdManual" name="organIdManual" type="text" placeholder="全部">
					      <!-- <select id="organIdManual" name="organIdManual">
						       <c:forEach var="orgOrgan" items="${orgOrgan}">
							     <option value="${orgOrgan.id}">${orgOrgan.fullName}</option>
						       </c:forEach>
					      </select> -->
				        </div>
				
				        <div class="col-4 organbill_pop_col_3">
					      <label>时间</label>
					      <input style="width:30%;" id="startTimeManual" name="startTimeManual" type="text" placeholder="开始日期" value="" class="searchDate" readonly="readonly">至
					      <input style="width:30%;" id="endTimeManual" name="endTimeManual" type="text" placeholder="结束日期" value="" class="searchDate" readonly="readonly">					      
				        </div>
				
				        <div class="col-3 organbill_pop_col_4" style="width: 23%;">
					      <label style="width: 35%;">订单状态</label>
					      <select id="billStateManual" name="billStateManual" style="width: 60%;">
						    <option value="" selected="selected">全部</option>
						    <option value="2">已复核</option>
						    <option value="0">未结算</option>
					      </select>
				        </div>

				        <div class="col-1 organbill_pop_col_5" style="text-align: right;width: 10%;">
					      <button class="Mbtn green_a" onclick="searchManual();">查询</button>
				        </div>				      
	                </div>
	                <div id="searchDiv">
	                    <label class="col-9">共选了<span id="checkNum"></span>个订单</label>
	                    <label class="col-3" style="text-align: right;">账单金额：￥<span id="checkAmount"></span></label>
	                    <table id="dataGridManual" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
	                    <div class="col-12" style="text-align: right;">
	                       <button class="Mbtn blue" onclick="saveGenerate()">生成账单</button>
	                       <button class="Mbtn grey" style="margin-left: 20px" onclick="canel()">取消</button>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div>

		<script type="text/javascript" src="js/organBill/index.js"></script>
	</body>
</html>

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
		<title>报警管理</title>
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
			input#plateno:focus {border:1px solid #f33333;color: #333;}
	</style>
	</head>
	<body style="overflow-x:hidden;overflow-y:hidden">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页 </a>>报警管理</div>
		<div class="content">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden"/>
		<input name="processstatus" id="processstatus" value="0" type="hidden">
		<input name="returnId" id="returnId" value="${getId.id}" type="hidden">
		<div style="padding-top: 30px;">
		    <ul class="tabbox">
				<li style="display:block">
					<div class="stab">
						<div class="shen_on">待处理报警</a></div>
						<div><a href="PubAlarmprocessYichuli/Index" style="text-decoration: none;">已处理报警</a></div>
					</div>
					<div class="stabox">
			<div class="row">
              <div class="col-4">
					<label>报警来源</label>
					<select id="alarmsource" name="alarmsource"style="width: 64%;">
						<option value="" selected="selected">全部</option>
						<option value="0">乘客</option> 
                        <option value="1">司机</option> 
					</select>
				</div>
				<div class="col-4">
					<label>报警类型</label>
					<select id="alarmtype" name="alarmtype"style="width: 64%;">
						<option value="" selected="selected">全部</option>
						<option value="0">候客报警</option> 
                        <option value="1">行程报警</option> 
					</select>
				</div>
				<div class="col-4">
					<label style= "width:55px">司机</label>
					<input id="driver" name="driver" type="hidden" placeholder="姓名/手机号"style="width:64%;">
				</div>
			</div>
			<div class="row">
			<div class="col-4">
					<label style= "width:55px">乘客</label>
					<input id="passenger" name="passenger" type="hidden" placeholder="姓名/手机号"style="width:64%;">
				</div>
			 <div class="col-4">
                     <label style= "width:55px">车牌号</label>
				   <input id="plateno" name="plateno" type = "text" placeholder=""style="width:64%;">
			 </div>
			 <div class="col-4">
					<label>报警时间</label>
	            		<input style="width:30%;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate"readonly="readonly">至
	            		<input style="width:30%;" id="endTime" name="endTime" type="text" placeholder="结束日期" value="" class="searchDate"readonly="readonly">
					</div>
			</div>
		<div class="row">
			<div class="col-12" style="text-align: right;">
		           <button class="Mbtn red_q" onclick="search()">查询
		           </button>
		           <button class="Mbtn grey_w" id="reset" onclick="reset()">清空</button>
                 </div>
			</div>
			<div class="row">
				<div class="col-4">
					<h4>涉险报警信息</h4>
				</div>
			</div>
           <table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
					</div>
				</li>
			</ul>
			</div>
           </div>
		<div class="pop_box" id="chuliFormDiv" style="display: none;">
		 <input name="id" id="id" type="hidden"/>
			<div class="tip_box_b">
	            <h3 id="title">处理报警</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            	<label style="text-align: center;padding-left: 20px;margin-top:-5px">处理结果<em class="asterisk"></em></label>
	                <input style="margin-left:-40%" type="radio"id="processresult"name="processresult" value ="0"/> 假警
	                <input type="radio"id="processresult"name="processresult" value ="1"/> 涉嫌遇警
	              <br><br><br>
	            	<label style="text-align: center;padding-left: 20px;">处理记录<em class="asterisk"></em></label>
	            	<textarea style="width:320px;height:220px;margin-left: -7%;" id="processrecord" name="processrecord" class="50" rows="5" maxlength="200"placeholder="描述处理的方式"></textarea>
	            	<div style="text-align:center;margin-top:20px">
	                <button class="Mbtn red_q"  onclick="save()">提交</button>
	                <button class="Mbtn grey_w" onclick="canel()"style="margin-left:10%">取消</button>
	                </div>
	        </div>
		</div>
		<script type="text/javascript" src="js/pubAlarmprocess/daichuli.js"></script>
		<script type="text/javascript" src="js/pubAlarmprocess/common.js"></script>
	</body>
</html>

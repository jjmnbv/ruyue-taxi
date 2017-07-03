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
		<title>个人用户</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/peuser_css_media.css" />
		<style type="text/css">
		/* 目前样式select在form中无法自适应 */
		.form label{
			float:left;
		}
		.form select,.form input[type=text]{
			width:40%;
			float:left;
		}
		.form label{
			line-height: 30px;
			height:30px;
		}
		.form .select2-container{
			width:40%;
			float:left;
			margin-top: -5px;
		}
		#dataGrid_wrapper .DTFC_LeftBodyWrapper tbody tr td:first-child{
			text-align:left!important;
		}
		</style>
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
		.peuser_css_btn_1 {left: 0px!important;}
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

		<script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.fileupload.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<style>
			.peuser_css_col_12 label.error{
			    line-height: 13px;
    			height: 10px;
			}
		</style>
	</head>
	<body class="peuser_css_body">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 用户管理</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-4 peuser_css_col_4"><label>账号</label>
						<input class="peuser_css_input_1" type="text" id="queryAccount" name="queryAccount" value="" placeholder="手机号码"/>
						<input type="hidden" id="queryAccounts" name="queryAccounts" value="">
					</div>
					<div class="col-4 peuser_css_col_2">
						<label class="peuser_css_time_1">注册时间</label>
	            		<input style="width:130px;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate peuser_css_input_2" readonly="readonly">
						<label class="peuser_css_label_2" style="text-align: center;width: 15px;">至</label>
	            		<input style="width:130px;" id="endTime" name="endTime" type="text" placeholder="结束日期" value="" class="searchDate peuser_css_input_2" readonly="readonly">
						<input type="hidden" id="startTimes" name="startTimes" value="">
						<input type="hidden" id="endTimes" name="endTimes" value="">
					</div>
					<div class="col-3 peuser_css_col_3"><label>状态</label>
						<select id="queryCompanystate">
							<option value="">全部</option>
							<option value="0">正常</option>
							<option value="1">禁用</option>
						</select>
						<input type="hidden" id="queryCompanystates" name="queryCompanystates" value="">
					</div>
				</div>
				<div class="row">
				    <div class="col-12" style="text-align: right;">
						<button class="Mbtn red_q" onclick="search();">查询</button>
						<button class="Mbtn grey_w" onclick="clearParameter();">清空</button>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-4" style="margin-top: 15px;">
					<h4>用户基本信息</h4>
				</div>
				<div class="col-8" style="text-align: right;">
					<button class="Mbtn blue_q peuser_css_btn_1" onclick="exportExcel();" id="export">导出数据</button>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		<div class="pop_box" id="disabledDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="titleForm">禁用下单</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="disabledForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="userid" name="userid"/>
	            		<div class="row">
		            		<div class="col-12">
								<label>禁用时间<em class="asterisk"></em></label>
			            		<input id="starttime" name="starttime" type="hidden" value="">
								<label style="width: 84px;text-align:center;">当前时间</label>
								<label style="width: 26px;">至</label>
			            		<input style="width:34%;" id="endtime" name="endtime" type="text" placeholder="结束日期" value="" class="searchDate1" readonly="readonly">
							</div>
	            			<div class="col-12">
				                <label>禁用原因<em class="asterisk"></em></label>
				                <textarea rows="5" cols="" id="reason" name="reason" style="width: 67.7%;margin-left:-9px;" placeholder="填写禁用原因" maxlength="20"></textarea>
		                	</div>
		                	<div class="col-12">
				                <label style="margin-left: -7px;">用户:</label>
				                <label id="disabledaccount" style="width: 261px;text-align:left;margin-left:8px;"></label>
		                	</div>
	            		</div>
	            	</form>
	                <button class="Lbtn red" onclick="save()">禁用</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		<script type="text/javascript" src="js/peUser/index.js"></script>
		<script type="text/javascript">
			var browserVersion=navigator.appVersion;
			var userAgent = navigator.userAgent;
			browserVersion = parseInt(userAgent.slice(-4));
	
			if (userAgent.indexOf("Firefox") > -1 && browserVersion <= 30 ) {
			  $('.peuser_css_col_3>select').css('padding-top','3px')
			} //判断是否Firefox浏览器30版本
		
			var serviceAddress = "<%=SystemConfig.getSystemProperty("fileserver")%>";
			var base = document.getElementsByTagName("base")[0].getAttribute("href");
		</script>
	</body>
</html>

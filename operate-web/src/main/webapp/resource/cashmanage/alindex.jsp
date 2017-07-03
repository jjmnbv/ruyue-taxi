<%@page import="com.szyciov.util.SystemConfig"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String yingyan_ak = SystemConfig.getSystemProperty("yingyan_ak");
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>提现管理</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/ordermanage_media.css" />
		
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
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript"  src="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.js"></script>
		<script type="text/javascript" src="js/basecommon.js"></script>
		<style type="text/css">
			.form select{width:68%;}
			.select2-container .select2-choice{height:30px;}
			.select2-container{width:68%;padding:0px;}
			.dataTables_wrapper{margin: 0px 0px 10px 0px;}
			.breadcrumb{text-decoration:underline;}
			.stab{border-bottom:2px solid #ededed;}
			.stab>div{display:inline-block;padding:4px 14px;text-align:center;}
			.shen_on{background:#ededed;}
			th, td { white-space: nowrap;}
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			} 
		
			.tip_box_b input[type=text]{width: 63%;}
			.DTFC_ScrollWrapper{
			margin-top:-20px;
		}
		
		</style>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 提现管理</div>
		<div class="content">
			<ul class="tabbox">
				<li style="display:block">
					<div class="stab">
						<div><a href="CashManage/Index" style="text-decoration: none;">待处理提现</a></div>
						<div class="shen_on">已处理提现</div>
					</div>
					<div class="stabox">
						<div class="row form" style="margin-top: 40px;">
							<div class="col-3">
								<label>用户类型</label>
								<select id="usertype">
									<option value="">全部</option>
									<option value="2">司机</option>
									<option value="1">乘客</option>
								</select>
							</div>
							
							<div class="col-3">
								<label>申请账号</label><input id="account" type="hidden" placeholder="申请账号">
							</div>
							
							<div class="col-3">
								<label>账户名称</label><input id="creditcardname" type="hidden" placeholder="账户名称">
							</div>
							
							<div class="col-3 ordermanage_css_col_1">
								<label>处理时间</label>
								<input style="width:30%;" id="minUseTime" name="minUseTime" readonly="readonly" type="text" placeholder="开始日期" value="" class="searchDate">
								至
			            		<input style="width:30%;" id="maxUseTime" name="maxUseTime" readonly="readonly" type="text" placeholder="结束日期" value="" class="searchDate">
							</div>
							
							<div class="col-12" style="text-align: right;">
								<button class="Mbtn green_a" onclick="search();">查询</button>
								<button class="Mbtn gray_a" onclick="clearOptions();">清空</button>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								<h4>已处理列表</h4>
							</div>
						</div>
						<table id="grid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
					</div>
				</li>
			</ul>
		</div>
		
		<div class="pop_box" id="notcashdiv" style="display: none;">
			<div class="tip_box_b">
	            <h3>提示</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<input type="hidden" id="orderno" name="orderno">
	            	<form id="cancelpartyForm" method="get" class="form">
	            		<input id="currentid" type="hidden"/>
	            		<div class="row">
	            		  	<label style="float: left;">确定不予提现？</label>
	            		</div>
		            	<div class="row">
			                <textarea id="reasonTextarea" name="reasonTextarea" style="width:70%;float:left;margin-left:20px;" rows="2" cols="3" placeholder="填写不予提现的原因（必填）"></textarea>
			            </div>
	            	</form>
	                <button class="Lbtn red" onclick="cashreject()">确定</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="cancelNotDiv()">取消</button>
	            </div>
	        </div>
		</div>
		
		<script type="text/javascript" src="js/cashmanage/alindex.js"></script>
	</body>
</html>

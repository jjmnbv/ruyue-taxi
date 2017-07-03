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
		<title>交接记录</title>
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
		</style>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 交接记录</div>
		<div class="content">
			<ul class="tabbox">
				<li style="display:block">
					<div class="stabox">
						<div class="row form" style="margin-top: 40px;">
							<div class="col-3">
								<label>车牌号</label>
								<input id="plateNo" type="text" maxlength="8" placeholder="车牌号">
							</div>
							
							<div class="col-3">
								<label>司机</label><input id="driverid" type="hidden" placeholder="当班司机">
							</div>
							
							<div class="col-3 ordermanage_css_col_1">
								<label style="float: left;line-height: 30px;">当前状态</label>
								<!--<label>交接类型</label>-->
 								<select  id="driverState">
									<option value="">全部</option>
									<option value="0">空闲</option>
									<option value="1">服务中</option>
									<option value="2">下线</option>
									<option value="-1">未绑定</option>
								</select>
							</div>
							
							<div class="col-12" style="text-align: right;">
								<button class="Mbtn green_a" onclick="search();">查询</button>
								<button class="Mbtn gray_a" onclick="clearOptions();">清空</button>
							</div>
						</div>
						<div class="row">
							<div class="col-12" style="position:relative;margin-top:15px;">
								<h4>服务车辆信息</h4>
							</div>
						</div>
						<table id="grid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
					</div>
				</li>
			</ul>
		</div>
		<script type="text/javascript" src="js/driverShiftManage/driverShiftRecord/index.js"></script>
	</body>
</html>

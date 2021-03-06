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
		<title>司机管理</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
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
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		
		<style type="text/css">
		/* 前端对于类似页面的补充 */
		.form select{width:68%;}
		.select2-container .select2-choice{height:30px;}
		.select2-container{width:68%;padding:0px;}
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

		.tip_box_b label.error {margin-left: 1%!important;}

		</style>
	</head>
	<body style="overflow:hidden;">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 司机管理
			<button class="SSbtn blue back" onclick="add()">新增</button>
		</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-4"><label>司机</label>
						<input id="queryKeyword" name="queryKeyword" type="text" placeholder="姓名/手机号" style="width: 68%;">
 					</div>
					<div class="col-4"><label>服务状态</label>
						<select id="queryWorkStatus" name="queryWorkStatus">
							<option value="" selected="selected">全部</option>
							<c:forEach var="workStatus" items="${workStatus}">
								<option value="${workStatus.value}">${workStatus.text}</option>
							</c:forEach>
						</select>
						<input id="queryWorkStatuss" name="queryWorkStatuss" type="hidden" value="">
					</div>
					<div class="col-4"><label>登记城市</label>
						<input id="queryCity" name="queryCity" type="text" placeholder="请选择城市" value=""/>
						<input id="queryCitys" name="queryCitys" type="hidden" value=""/>
					</div>

				</div>
				<div class="row">
					<!-- 新增绑定状态 -->
					<div class="col-4"><label>绑定状态</label>
						<!-- 未绑定 0 、已绑定 1  -->
						<select id="queryBoundState" name="queryBoundState">
							<option value="" selected="selected">全部</option>
							<option value="0">未绑定</option>
							<option value="1">已绑定</option>
						</select>
						<input id="queryBoundStates" name="queryBoundStates" type="hidden" value=""/>
					</div>
					<!-- 新增绑定状态 -->
					<div class="col-4"><label>资格证号</label>
						<input id="queryJobNum" name="queryJobNum" type="text" placeholder="司机资格证号" />
					</div>
					<div class="col-4"><label>司机类型</label>
						<!--  网约车 0 、 出租车 1  -->
						<select id="queryVehicleType" name="queryVehicleType">
							<option value="" selected="selected">全部</option>
							<option value="0">网约车</option>
							<option value="1">出租车</option>
						</select>
						<input id="queryVehicleTypes" name="queryVehicleTypes" type="hidden" value=""/>	
					</div>
				</div>
				<div class="row">
					<div class="col-4" style="text-align: left;">
						<label>在职状态</label>
						<select id="queryJobStatus" name="queryJobStatus">
							<option value="" selected="selected">全部</option>
							<option value="0">在职</option>
							<option value="1">离职</option>
						</select>
						<input id="queryJobStatuss" name="queryJobStatuss" type="hidden" value=""/>
					</div>
				</div>
				<div class="row">
					<div class="col-12" style="text-align: right;">
						<button class="Mbtn green_a" onclick="search();">查询</button>
						<button class="Mbtn gary" onclick="emptys();">清空</button>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<h4>司机管理列表</h4>
				</div>
				<div class="col-8" style="text-align: right;">
					<button class="Mbtn orange" onclick="exportData();">导出数据</button>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>

	<script type="text/javascript" src="js/pubDriver/index.js"></script>
	<script type="text/javascript">
		var base = "<%=basePath%>";
		/**
		 * 新增
		 */
		function add() {
			window.location.href=base+"PubDriver/AddIndex?title=新增司机";
		}
		
	</script>	
	</body>
</html>

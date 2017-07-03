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
		<title>客户管理</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/css/leLeasescompany_css_media.css" />
		
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
	</head>
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
		#dataGrid_wrapper .DTFC_LeftBodyWrapper tbody tr td:first-child{
			text-align:left!important;
		}
	</style>
	<body class="leLeasescompany_css_body">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 客户管理
			<button class="SSbtn green_q back" onclick="add()">新增</button>
		</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-4"><label>客户名称</label>
						<select id="queryName">
							<option value="">全部</option>
							<c:forEach items="${list}" var="list">
								<option value="${list.id}">${list.shortName}</option>
							</c:forEach>
						</select>
						<input type="hidden" id="queryNames" name="queryNames" value="">
					</div>
					<div class="col-4"><label>所属城市</label>
						<select id="queryCity">
							<option value="">全部</option>
							<c:forEach items="${list1}" var="list1">
								<option value="${list1.city}">${list1.city}</option>
							</c:forEach>
						</select>
						<input type="hidden" id="queryCitys" name="queryCitys" value="">
					</div>
					<div class="col-4"><label>账号状态</label>
						<select id="queryCompanystate">
							<option value="">全部</option>
							<option value="0">正常</option>
							<option value="1">禁用</option>
						</select>
						<input type="hidden" id="queryCompanystates" name="queryCompanystates" value="">
					</div>
					</div>
					<div class="row">
					<div class="col-4 leLeasescompany_css_col_1">
						<label>创建时间</label>
	            		<input style="width:30%;" id="startTime" name="startTime" type="text" placeholder="开始日期" value="" class="searchDate" readonly="readonly">
						至
	            		<input style="width:30%;" id="endTime" name="endTime" type="text" placeholder="结束日期" value="" class="searchDate" readonly="readonly">
						<input type="hidden" id="startTimes" name="startTimes" value="">
						<input type="hidden" id="endTimes" name="endTimes" value="">
					</div>
					<div class="col-8" style="text-align: right;">
						<button class="Mbtn red_q" onclick="search();">查询</button>
						<button class="Mbtn grey_w" onclick="emptys();">清空</button>
					</div>
				</div>
				<!-- <div class="col-12 leLeasescompany_css_col_2" style="text-align: right;">
					<button class="Mbtn blue" onclick="add()">新增</button>
					
				</div> -->
			</div>
			<div class="row">
				<div class="col-4">
					<h4>平台服务车企信息</h4>
				</div>
				<div class="col-8" style="text-align: right;">
					<button class="Mbtn blue_q" onclick="exportExcel();">导出数据</button>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		<div class="pop_box" id="examineTocDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="titleForm">toC审核</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<input type="hidden" id="id" name="id"/>
	            		<div class="row">
	            			<div class="col-12">
			            		<label id="examineTocTitle" style="width: 78%;"></label>
		                	</div>
	            		</div>
	            		<div class="row">
	            			<div class="col-12" style="text-align:left;">
				                <label style="width: 22%;">审核结果：</label>
				                <input id="examine" name="examine" type="radio" value="0" checked="checked" />通过
						   		<input id="examine" name="examine" type="radio" value="1" />不通过
		                	</div>
	            		</div>
	            	</form>
	                <button class="Lbtn red" onclick="save()">确定</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
		</div>
		<script type="text/javascript" src="js/leLeasescompany/index.js"></script>
		<script type="text/javascript">
		var base = document.getElementsByTagName("base")[0].getAttribute("href");
		</script>
	</body>
</html>

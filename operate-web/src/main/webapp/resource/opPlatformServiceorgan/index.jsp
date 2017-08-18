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
		<title>服务机构信息</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/bootstrap-datepicker/bootstrap-datetimepicker.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
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
		<script type="text/javascript" src="content/plugins/fileupload/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.fileupload.js"></script>
		<style type="text/css">
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
		</style>
	</head>
	
	<body>
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs">
			<a href="javascript:void(0);" onclick="homeHref()" class="breadcrumb">首页</a> > 服务机构信息
			<button class="SSbtn blue back" onclick="add()" id="addBtn" style="margin-top: -1px;">新增</button>
			<input type="hidden" id="usertype" value="${usertype}">
		</div>
		<div class="content">
			<div class="row form" style="padding-top: 30px;">
				<div class="col-3">
					<label>机构名称</label>
					<input id="servicename" name="servicename" type="text" placeholder="">

				</div>
				<div class="col-3">
					<label class="col-1">机构所在地</label><input class="col-3" id="address" name="address" type="text" placeholder="全部">
				</div>
				<div class="col-3">
					<label>机构负责人</label>
					<input id="responsiblename" name="responsiblename" type="text" placeholder="">
				</div>
				<div class="col-3">
					<label>负责人电话</label>
					<input id="responsiblephone" name="responsiblephone" type="text" placeholder="">
				</div>
			</div>
			<div class="row form">
				<div class="col-12" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
					<button class="Mbtn grey" onclick="clearSearch();">清空</button>
				</div>
			</div>
			<div class="row">
				<div class="col-4" style="margin-top: 15px;">
					<h4>平台服务机构信息</h4>
				</div>
				<div class="col-8" style="text-align: right;">
					<button class="Mbtn blue_q peuser_css_btn_1" onclick="exportExcel();" id="export">导出数据</button>
					<button class="Mbtn blue_q peuser_css_btn_1" onclick="importExcel();" id="import">批量导入</button>
					<button class="Mbtn blue_q peuser_css_btn_1" onclick="download();" id="download">下载模板</button>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>



		<div class="pop_box" id="importExcelDiv" style="display: none;">
			<div class="tip_box_b">
				<h3 id="importExcel">导入服务机构信息</h3>
				<img src="content/img/btn_guanbi.png" class="close" alt="关闭">
				<div class="row">
					<div class="col-12">
						提示：请确保文件的格式与导入平台服务机构excel模板的格式一致
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<label style="margin-right: 334px;">上传文件<em class="asterisk"></em></label>
						<input id="importfile" name="importfile" type="file" multiple style="margin-left: 177px;margin-top: -21px;"><br>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<button class="Lbtn red" onclick="canel()">关闭</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 导入 -->
		<div class="pop_box" id="importExcelDiv1" style="display: none;">
			<div class="tip_box_b">
				<h3 id="importExcelTip">提示</h3>
				<img src="content/img/btn_guanbi.png" class="close" alt="关闭">
				<div class="row">
					<div class="col-10" id="importExcelDiv2" style="margin-left: 70px;width: 350px;text-align: left;overflow-y: auto;height: 200px;">
					</div>
				</div>
				<div class="row">
					<div class="col-10" style="text-align: right;">
						<button class="Mbtn red"  onclick="canel1()">确定</button>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="js/opPlatformServiceorgan/index.js"></script>
	</body>
</html>

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
		<title>车辆保险信息</title>
		<base href="<%=basePath%>" >
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="content/css/style.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/select2/select2_metro.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="content/plugins/letterselect/letterselect.css">
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/dataTables.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/data-tables/css/fixedColumns.bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="content/plugins/cityselect1/cityselect1.css">
		
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
		
		.tip_box_d #plateNoProvince option{
			font-size:12px;
			padding:0 10px;
		}
		.tip_box_d #plateNoCity option{
			font-size:12px;
			padding:0 10px;
		}
		#pubCityaddr>.city_container{
			margin-left: 12px;
		}
		.ico_x_a{
			display:block;
		}
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
		<script type="text/javascript"  src="content/plugins/cityselect1/cityselect1.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2.js"></script>
		<script type="text/javascript" src="content/plugins/select2/select2_locale_zh-CN.js"></script>
		<script type="text/javascript" src="content/plugins/select2/app.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="content/plugins/fileupload/jquery.fileupload.js"></script>
		<script type="text/javascript" src="content/plugins/letterselect/letterselect.js"></script>
	</head>
	<body style="overflow:hidden;">
		<div class="crumbs">
			<a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 车辆保险信息
			<button class="SSbtn green_q back" onclick="add()">新增</button>
		</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-3"><label>营运类型</label>
						<select id="queryVehicleType" name="queryVehicleType">
							<option value="" selected="selected">全部</option>
							<option value="0">网约车</option>
							<option value="1">出租车</option>
						</select>
						<input id="queryVehicleTypes" name="queryVehicleTypes" type="hidden" value=""/>	
					</div>
					<div class="col-3"><label>保险类型</label>
						<select id="queryinsurType" name="queryinsurType">
							<option value="" selected="selected">全部</option>
							<c:forEach var="insurType" items="${insurType}">
								<option value="${insurType.value}">${insurType.text}</option>
							</c:forEach>
						</select>
						<input id="queryinsurTypes" name="queryinsurTypes" type="hidden" value=""/>	
					</div>
					<div class="col-3"><label>车牌号码</label>
						<input id="queryPlateNo" name="queryPlateNo" type="text" placeholder="请输入车牌号码" style="width: 68%;">
						<input id="queryPlateNos" name="queryPlateNos" type="hidden" value="">
					</div>
					<div class="col-3"><label>车牌识别码</label>
						<input id="queryVin" name="queryVin" type="text" placeholder="请输入车牌识别码" style="width: 68%;">
						<input id="queryVins" name="queryVins" type="hidden" value="">
					</div>
				</div>
				<div class="row">
					<div class="col-3"><label>保险号</label>
						<input id="queryInsurNum" name="queryInsurNum" type="text" placeholder="请输入保险号" style="width: 68%;">
						<input id=queryInsurNums name="queryInsurNums" type="hidden" value="">
					</div>
					<div class="col-3"><label>保险公司</label>
						<input id="queryInsurCom" name="queryInsurCom" type="text" placeholder="请输入保险公司" style="width: 68%;">
						<input id="queryInsurComs" name="queryInsurComs" type="hidden" value="">
					</div>
					<div class="col-6" style="text-align: right;">
							<button class="Mbtn red_q" onclick="search();">查询</button>
							<button class="Mbtn grey_w" onclick="emptys();">清空</button>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-4">
					<h4>车辆保险信息</h4>
				</div>
				<div class="col-8" style="text-align: right;">
					<button class="Mbtn blue_q" onclick="download()">下载模板</button>
					<button class="Mbtn blue_q" onclick="importExcel();">批量导入</button>
					<button class="Mbtn blue_q" onclick="exportExcel();">导出数据</button>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>
		
		<div class="pop_box" id="importExcelDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="importExcel">导入车辆保险信息</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="row">
	            	<div class="col-12">
	            		提示：请确保文件的格式与导入车辆保险excel模板的格式一致
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
	            		<button class="Mbtn red" onclick="canel()">关闭</button>
	            	</div>
	            </div>
	        </div>
		</div>
		
		<!-- 导入 -->
		<div class="pop_box" id="importExcelDiv1" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="importExcel">提示</h3>
	            <img src="content/img/btn_guanbi.png" class="close" alt="关闭">
	            <div class="row">
	            	<div class="col-10" >
	            		<div id="importExcelDiv2" style="margin-left : 100px; text-align: left;overflow-y: auto;height: 200px;">
	            		
	            		</div>
	            	</div>
	            </div>
	            <div class="row">
	            	<div class="col-10" style="text-align: right;">
	            		<button class="Mbtn red"  onclick="canel1()">确定</button>
	            	</div>
	            </div>
	        </div>
		</div>
		<script type="text/javascript" src="js/pubVehInsur/index.js"></script>
	</body>
	
</html>
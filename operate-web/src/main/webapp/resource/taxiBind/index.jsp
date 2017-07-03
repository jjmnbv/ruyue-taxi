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
		<title>出租车绑定</title>
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
									
			#unBindingVelDataGrid_wrapper{padding: 0 35px;}
			th, td { white-space: nowrap;}

			#unBindingVelDataGrid_info{  display: none;}
			#unBindingVelDataGrid_paginate{display: none;}
			#bindingVelDataGrid_info{display: none;}
			#bindingVelDataGrid_paginate{display: none;}
			
			div.dataTables_wrapper {
			  width: $(window).width();
			  margin: 0 auto;
			}
			.tip_box_b input[type=text]{width: 63%;}
			.DTFC_ScrollWrapper{margin-top:-20px;}
			.pop_box{z-index: 1111 !important;}
			
			.tip_box_b label.error {margin-left: 0%!important;padding-left: 20%;}
			.purple{background: #A020F0;}
		</style>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<input name="plate" id="plate" type="hidden">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> > 出租车绑定</div>
		<div class="content">
			<ul class="tabbox">
				<li style="display:block">
					<div class="stabox">
						<div class="row form" style="padding-top: 30px;">
							<div class="col-4">
								<label>品牌车系</label>
								<input id="vehclineid" name="vehclineid" type="text" placeholder="请选择品牌车系" value="">
 							</div>
							
							<div class="col-4">
								<label>车牌号</label>
								<input id="plateno" name="plateno" type="text" placeholder="车牌号">
							</div>
							
							<div class="col-4  ">
								<label>绑定状态</label>
								<select id="boundstate" name="boundstate">
									<option value="" selected="selected">全部</option>
									<option value="0">未绑定</option>
									<option value="1">已绑定</option>
								</select>
							</div>
						</div>
						<div class="row form">
							<div class="col-4">
								<label>服务状态</label>
								<select id="workstatus" name="workstatus">
									<option value="" selected="selected">全部</option>
									<option value="0">空闲</option>
									<option value="1">服务中</option>
									<option value="2">下线</option>
								</select>
							</div>
							<div class="col-4">
								<label>登记城市</label>
								<select id="city" name="city">
									<option value="" selected="selected">全部</option>
									<c:forEach var="cityList" items="${cityList}">
										<option value="${cityList.id}">${cityList.text}</option>
									</c:forEach>
								</select>
							</div>
							<form id="searchForm">
							<div class="col-4">
								<label>已绑定人数</label>
								<input id="bindpersonnum" name="bindpersonnum" type="text" placeholder="如：2">
							</div>
							</form>
						</div>
						<div class="row form">
							<div class="col-4">
								<label>当班司机</label>
								<input id="driverid" name="driverid" type="text" placeholder="姓名/手机号">
							</div>
							<div class="col-4">
								<label>排班状态</label>
								<select id="ondutystatus" name="ondutystatus">
									<option value="" selected="selected">全部</option>
									<option value="0">未排班</option>
									<option value="1">已排班</option>
								</select>
							</div>
							<div class="col-4">
								<label>营运状态</label>
								<select id="vehiclestatus" name="vehiclestatus">
									<option value="" selected="selected">全部</option>
									<option value="0">营运中</option>
									<option value="1">维修中</option>
								</select>
							</div>
							<div class="col-12" style="text-align: right;">
								<button class="Mbtn green_a" onclick="search();">查询</button>
								<button class="Mbtn gray_a" onclick="clearSearch();">清空</button>
							</div>
						</div>
						<div class="row">
							<label class="col-12" style="margin-top: 15px;"><h4>服务车辆信息</h4></label>
			            </div>
						<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
					</div>
				</li>
			</ul>
		</div>


		<div class="pop_box" id="bindingVel" style="display: none;">
			<div class="tip_box_c" style="position:relative;overflow-y:auto;" style="width:760px; margin:70px auto;">
				<img src="content/img/btn_guanbi.png" class="close" alt="关闭" style="POSITION: absolute; top: 17px; right: 18px;">
				<h3 id="bindingVelTitleForm" style="margin-left: 39px;text-align: center;margin-bottom: 50px">绑定司机</h3>
				<div class="row form">
					<input type="hidden" id="vehcile" name="vehcile">
					<input type="hidden" id="cityId" name="cityId">
					<div class="col-4"><label style="width:36%;">资格证号</label>
						<input style="width:60%;" id="queryJobnum" name="queryJobnum" type="text" placeholder="请输入资格证号"></div>
					<div class="col-4"><label>司机</label>
						<input id="queryDriver" name="queryDriver" type="text" placeholder="姓名/手机号"></div>
					<div  class="col-4" style="text-align: right;">
						<button class="Mbtn red" onclick="query();">查询</button>
						<!-- <button class="Mbtn gray_a" onclick="clearbinding();">清空</button> -->
					</div>
				</div>
				<table id="bindingVelDataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
				
				<div class="row">            	
	            	<div class="col-12">已绑定司机<em class="asterisk"></em></div>
	            </div>
				<div id ="addcboxId" class="addcbox" style="margin:0px 0px 10px 0px;border:1px solid #ccc;min-height:100px;line-height:30px;" width="100%">           			
	            </div>

			</div>
		</div>

		<div class="pop_box" id="unwrapVel" style="display: none;">
			<div class="tip_box_b form" style=" width: 785px;max-height: 608px;overflow-y: auto;overflow-x: hidden;">
				<h3 id="unwrapVelTitleForm">解绑司机</h3>
				<img src="content/img/btn_guanbi.png" class="close" alt="关闭">
				<form id="unwrapVelForm" method="get" class="form-horizontal  m-t" id="frmmodal">
					<input id="unwrapVelId" name="unwrapVelId" type="hidden"/>
					<input id="vehicleId" name="vehicleId" type="hidden"/>
					<!-- <div style="overflow-y: auto;height: 320px"> -->
						<table id="unBindingVelDataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
					<!-- </div> -->
					<div class="row" style="text-align: left;">
						<div class="col-12">
							<label style="vertical-align:top;width: 20%">解绑原因<em class="asterisk"></em></label>
							<textarea cols="40" rows="4" id="unBindReason" name="unBindReason" maxlength="100"  placeholder="解绑原因" style="width: 70%;"></textarea>
						</div>
					</div>
				</form>
				<button class="Lbtn red" onclick="unwrapVelSave()">确定</button>
				<button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
			</div>
		</div>

		<div class="pop_box" id="notcashdiv" style="display: none;">
			<div class="tip_box_b">
				<h3>提示</h3>
				<img src="content/img/btn_guanbi.png" class="close" alt="关闭">
				<div class="w400">
					<div class="row">
						<div class="col-12">
							<label style="float: left;text-align: left;margin-left: 30px">选择当班司机</label>
							<select id="plateNoProvince" class="col-5" style="margin-top: -5px;line-height: 100%;" name="plateNoProvince">
								<option value="">请选择</option>
							</select>
						</div>
					</div>
					<button class="Lbtn red" onclick="processed()">确定</button>
					<button class="Lbtn grey" style="margin-left: 10%;" onclick="cancelNotDiv()">取消</button>
				</div>
			</div>
		</div>

		<script type="text/javascript" src="js/taxiBind/index.js"></script>
	</body>
</html>

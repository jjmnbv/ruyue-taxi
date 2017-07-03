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
		<title>网约车绑定</title>
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
			.pop_box{z-index: 1111 !important;}
			.tip_box_b input[type=text]{width: 63%;}
			.DTFC_ScrollWrapper{
			margin-top:-20px;}
		</style>
	</head>
	<body class="ordermanage_css_body">
		<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
		<div class="crumbs"><a class="breadcrumb" href="javascript:void(0);" onclick="homeHref()">首页</a> >基础数据>车人管理>网约车 </div>
		<div class="content">
			<ul class="tabbox">
				<li style="display:block">
					<div class="stabox">
						<div class="row form" style="margin-top: 40px;">
							<div class="col-4">
								<label>司机</label>
								<input id="queryDriverId" type="hidden" placeholder="姓名/手机号">
 							</div>
							
							<div class="col-4">
								<label>资格证号</label>
								<input id="queryDriverNum" type="hidden" placeholder="司机资格证号">
							</div>
							
							<div class="col-4  ">
								<label>绑定状态</label>
								<select  id="bindStatus">
									<option value="">全部</option>
									<option value="0">未绑定</option>
									<option value="1">已绑定</option>
								</select>
							</div>
						</div>
						<div class="row form">
							<div class="col-4">
								<label>服务状态</label>
								<select  id="workStatus">
									<option value="">全部</option>
									<option value="0">空闲</option>
									<option value="1">服务中</option>
									<option value="2">下线</option>
								</select>
							</div>

							<div class="col-4">
								<label>服务车型</label>
								<select  id="modelId">
									<option value="">全部</option>
								</select>
							</div>

							<div class="col-4  ">
								<label>登记城市</label>
								<select  id="city">
									<option value="">全部</option>
								</select>
							</div>

						</div>
						<div class="row form">
							<div class="col-4">
								<label>品牌车系</label>
								<input id="lineName" type="hidden" placeholder="请选择品牌车系">
								<%--<input id="lineName" name="queryBrandCars" type="text" placeholder="请选择品牌车系" value="">--%>
							</div>

							<div class="col-4">
								<label>车牌号</label>
								<input id="platenoStr" type="text" placeholder="车牌号">
							</div>
							
							<div class="col-4">
								<label>归属车企</label>
								<select id="belongLeasecompany">
									<option value="">全部</option>
								</select>
							</div>

							<div class="col-12" style="text-align: right;">
								<button class="Mbtn green_a" onclick="search();">查询</button>
								<button class="Mbtn gray_a" onclick="clearOptions();">清空</button>
							</div>
						</div>
						<div class="row">
							<div class="col-4" style="position:relative;margin-top:15px;">
								<h4>服务司机信息</h4>
							</div>
							<div class="col-8" style="text-align: right;">
								<button class="Mbtn blue" onclick="toRecord();">操作记录</button>
							</div>
						</div>
						<table id="grid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
					</div>
				</li>
			</ul>
		</div>


		<div class="pop_box" id="bindingVel" style="display: none;">
			<div class="tip_box_c" style="position:relative;overflow-y:auto;" style="width:760px; margin:70px auto;">
				<img src="content/img/btn_guanbi.png" class="close" alt="关闭" onclick="canelBind()" style="POSITION: absolute; top: 17px; right: 18px;">
				<h3 id="bindingVelTitleForm" style="margin-left: 39px;text-align: center;margin-bottom: 50px">绑定车辆</h3>
				<div class="row form">
					<input type="hidden" id="driid" name="driid">
					<input type="hidden" id="cityId" name="cityId">
					<input type="hidden" id="leasecompanyId" name="leasecompanyId">
					<div class="col-4" style="text-align: left"><label style="text-align: left;width:19%">品牌车系</label><input style="width:60%;" id="queryBrandCars" name="queryBrandCars" type="text" placeholder="请选择品牌车系"></div>
					<div class="col-4"><label>车牌号</label><input id="queryPlateNo" name="queryPlateNo" type="text" placeholder="请输入车牌号"></div>
					<div  class="col-4" style="text-align: right;">
						<button class="Mbtn red" onclick="query();">查询</button>
						<button class="Mbtn gray_a" onclick="clearbinding();">清空</button>
					</div>
				</div>
				<table id=bindingVelDataGrid class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
			</div>
		</div>

		<div class="pop_box" id="unwrapVel" style="display: none;">
			<div class="tip_box_b form">
				<h3 id="unwrapVelTitleForm">解除绑定</h3>
				<img src="content/img/btn_guanbi.png" class="close" alt="关闭">
				<form id="unwrapVelForm" method="get" class="form-horizontal  m-t" id="frmmodal">
					<input id="unwrapVelId" name="unwrapVelId" type="hidden"/>
					<input id="vehicleId" name="vehicleId" type="hidden"/>
					<div class="row">
						<div class="col-12" style="text-align: left;">
							<label>司机：</label>
							<label id="driverNamePhone" style="text-align: left;width: 300px;"></label>
						</div>
					</div>
					<div class="row">
						<div class="col-12" style="text-align: left;">
							<label>车牌：</label>
							<label id="unBindPlateNo"  style="text-align: left;width: 300px;"></label>
						</div>
					</div>
					<div class="row">
						<div class="col-12" style="text-align: left;">
							<label>品牌车系：</label>
							<label id="brandCars"  style="text-align: left;width: 300px;"></label>
						</div>
					</div>
					<div class="row" style="text-align: left;">
						<div class="col-12">
							<label style="vertical-align:top;">解绑原因<em class="asterisk"></em></label>
							<textarea cols="40" rows="4" id="unBindReason" name="unBindReason" placeholder="解绑原因" maxlength="100" style="width: 300px;"></textarea>
							<label for="unBindReason" class="error" style="display: inline-block;padding-left: 0px;">最大长度不能超过200个字符</label>
						</div>
					</div>
				</form>
				<button class="Lbtn red" onclick="unwrapVelSave()">确定</button>
				<button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
			</div>
		</div>

		<script type="text/javascript" src="js/drivervehiclebind/carbind/index.js"></script>
	</body>
</html>

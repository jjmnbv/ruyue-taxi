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
			<button class="SSbtn green_q back" onclick="add()">新增</button>
		</div>
		<div class="content">
			<div class="form" style="padding-top: 30px;">
				<div class="row">
					<div class="col-3"><label>司机</label>
						<input id="queryKeyword" name="queryKeyword" type="text" placeholder="姓名/手机号" value="" style="width: 68%;">
						<input id="queryKeywords" name="queryKeywords" type="hidden" value="">	
					</div>
					<div class="col-3"><label>服务状态</label>
						<select id="queryWorkStatus" name="queryWorkStatus">
							<option value="" selected="selected">全部</option>
							<c:forEach var="workStatus" items="${workStatus}">
								<option value="${workStatus.value}">${workStatus.text}</option>
							</c:forEach>
						</select>
						<input id="queryWorkStatuss" name="queryWorkStatuss" type="hidden" value="">
					</div>
					<div class="col-3"><label>登记城市</label>
						<input id="queryCity" name="queryCity" type="text" placeholder="请选择城市" value=""/>
						<input id="queryCitys" name="queryCitys" type="hidden" value=""/>
					</div>
					<!-- 新增绑定状态 -->
					<div class="col-3"><label>绑定状态</label>
						<!-- 未绑定 0 、已绑定 1  -->
						<select id="queryBoundState" name="queryBoundState">
							<option value="" selected="selected">全部</option>
							<option value="0">未绑定</option>
							<option value="1">已绑定</option>
						</select>
						<input id="queryBoundStates" name="queryBoundStates" type="hidden" value=""/>	
					</div>
				</div>
				<div class="row">
					<div class="col-3"><label>司机类型</label>
						<!--  网约车 0 、 出租车 1  -->
						<select id="queryVehicleType" name="queryVehicleType">
							<option value="" selected="selected">全部</option>
							<option value="0">网约车</option>
							<option value="1">出租车</option>
						</select>
						<input id="queryVehicleTypes" name="queryVehicleTypes" type="hidden" value=""/>	
					</div>
					<div class="col-3"><label>司机身份</label>
						<select id="queryIdEntityType" name="queryIdEntityType">
							<option value="" selected="selected">全部</option>
							<option value="0">普通司机</option>
							<option value="1">特殊司机</option>
						</select>
						<input id="queryIdEntityTypes" name="queryIdEntityTypes" type="hidden" value=""/>
					</div>
					<div class="col-3"><label>服务机构</label>
						<%-- <select id="queryServiceOrg" name="queryServiceOrg">
							<option value="" selected="selected">全部</option>
							<c:forEach var="orgOrganShortName" items="${orgOrganShortName}">
								<option value="${orgOrganShortName.fullName}">${orgOrganShortName.fullName}</option>
							</c:forEach>
						</select> --%>
						<input id="queryServiceOrg" name="queryServiceOrg" type="text" placeholder="请选择服务机构" value=""/>
						<input id="queryServiceOrgs" name="queryServiceOrgs" type="hidden" value=""/>
					</div>
					<div class="col-3"><label>在职状态</label>
						<select id="queryJobStatus" name="queryJobStatus">
							<option value="" selected="selected">全部</option>
							<option value="0">在职</option>
							<option value="1">离职</option>
						</select>
						<input id="queryJobStatuss" name="queryJobStatuss" type="hidden" value=""/>
					</div>
				</div>
				<div class="row">
					<div class="col-3"><label>资格证号</label>
						<input id="queryJobNum" name="queryJobNum" type="text" placeholder="司机资格证号" value="" style="width: 68%;">
						<input id="queryJobNums" name="queryJobNums" type="hidden" value="">	
					</div>
					<div class="col-3"><label>归属车企</label>
						<select id="belongleasecompanyQuery" name="belongleasecompanyQuery">
							<option value="" selected="selected">全部</option>
							<c:forEach var="belongleasecompany" items="${belongleasecompany}">
								<option value="${belongleasecompany.value}">${belongleasecompany.text}</option>
							</c:forEach>
						</select>
						<input id="belongleasecompanyQuerys" name="belongleasecompanyQuerys" type="hidden" value="">	
					</div>
					<div class="col-6" style="text-align: right;">
						<button class="Mbtn red_q" onclick="search();">查询</button>
						<button class="Mbtn grey_w" onclick="emptys();">清空</button>
					</div>
				</div>
				<!-- <div class="col-6" style="text-align: right;">
					<button class="Mbtn green_a" onclick="search();">查询</button>
				</div> -->
			</div>
			<div class="row">
				<div class="col-4">
					<h4>司机基本信息</h4>
				</div>
				<div class="col-8" style="text-align: right;">
					<button class="Mbtn blue_q" onclick="exportData();">导出数据</button>
				</div>
			</div>
			<table id="dataGrid" class="table table-striped table-bordered" cellspacing="0" width="100%"></table>
		</div>

		<div class="pop_box" id="bindingVel" style="display: none;">
			<div class="tip_box_c" style="position:relative;overflow-y:auto;" style="width:760px; margin:70px auto;">
				<img src="content/img/btn_guanbi.png" class="close" alt="关闭" style="POSITION: absolute; top: 17px; right: 18px;">
	            <h3 id="bindingVelTitleForm" style="margin-left: 39px;">绑定车辆</h3>
	            <div class="row form">
	            	<input type="hidden" id="driid" name="driid">
	            	<input type="hidden" id="cityId" name="cityId">
		            <div class="col-4"><label style="width:36%;">品牌车系</label><input style="width:60%;" id="queryBrandCars" name="queryBrandCars" type="text" placeholder="请选择品牌车系"></div>
		            <div class="col-4"><label>车牌</label><input id="queryPlateNo" name="queryPlateNo" type="text" placeholder="请输入车牌号"></div>
		            <div  class="col-4" style="text-align: right;">
		                <button class="Mbtn green_a" onclick="query();">查询</button>
		                <!-- <button class="Mbtn red" style="margin-left: 20px;">重置</button> -->
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
		            <div class="row">
		            	<div class="col-12" style="text-align: left;">
		            		<label>司机：</label>
		            		<label id="driverNamePhone" style="text-align: left;width: 300px;"></label>
		            	</div>
		            </div>
		            <div class="row">
		            	<div class="col-12" style="text-align: left;">
		            		<label>车牌：</label>
		            		<label id="plateNo"  style="text-align: left;width: 300px;"></label>
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
		            		<textarea cols="40" rows="4" id="unBindReason" name="unBindReason" placeholder="解绑原因" style="width: 300px;"></textarea>
		            	</div>
		            </div>
	            </form>
	            <button class="Lbtn red" onclick="unwrapVelSave()">确定</button>
                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	        </div>
		</div>
	<script type="text/javascript" src="js/pubDriver/index.js"></script>
	<script type="text/javascript">
	var base = document.getElementsByTagName("base")[0].getAttribute("href");
		var bindingVelDataGrid;
		//绑定
		function bindingVel(id){
			$.ajax({
				type: "GET",
				url:"PubDriver/GetById",
				cache: false,
				data: { id: id },
				success: function (json) {
					$("#driid").val(json.id);
					$("#cityId").val(json.city);
					$("#bindingVelTitleForm").html("绑定车辆 <font color='orange'>【"+json.name+"】</font>");
					$("#queryBrandCars").select2("data", {
						id : "",
						text : ""
					});
					$("#queryPlateNo").val("");
					$("#bindingVel").show();
					
					initSelectQueryBrandCars();
					
					if(!bindingVelDataGrid){
						bindingVelInitGrid();
					}else{
						query();
					}
				}
			});
		}
		//解绑
		function unwrapVel(id){
			$.ajax({
				type: "GET",
				url:"PubDriver/CheckUnbundling",
				cache: false,
				data: { id: id },
				success: function (json) {
					if(json > 0){
						toastr.error("服务中与有预约订单，不能解绑", "提示");
					}else{
						$("#unBindReason").val("");
						$.ajax({
							type: "GET",
							url:"PubDriver/UnwrapVel",
							cache: false,
							data: { id: id },
							success: function (json) {
								$("#unwrapVelId").val(json.id);
								$("#driverNamePhone").html(json.driverNamePhone);
								$("#plateNo").html(json.plateNo);
								$("#brandCars").html(json.brandCars);
								$("#unwrapVel").show();
								unwrapVelForm();
								// 清除验证提示
								var editForm = $("#unwrapVelForm").validate();
								editForm.resetForm();
								editForm.reset();
							}
						});
					}
				}
			});
			
		}
		
		/**
		 * 表单校验
		 */
		function unwrapVelForm() {
			$("#unwrapVelForm").validate({
				rules: {       
					unBindReason: {required: true, maxlength: 200}
				},
				messages: {
					unBindReason: {required: "请输入解绑原因",maxlength: "最大长度不能超过200个字符"}
				}
			})
		}
		
		function unwrapVelSave(){
			var form = $("#unwrapVelForm");
			if(!form.valid()) return;
			var url = "PubDriverVehicleRef/UpdatePubDriverVehicleRef";
			var id =$("#unwrapVelId").val();
			var unBindReason = $("#unBindReason").val();
			var plateNo = $("#plateNo").text();
			var driverNamePhone = $("#driverNamePhone").text();
			var data = {
					driverId : id,
					unBindReason : unBindReason,
					plateNo : plateNo,
					driverNamePhone : driverNamePhone
				}
			
			$.ajax({
				type: 'POST',
				dataType: 'json',
				url: url,
				data: JSON.stringify(data),
				contentType: 'application/json; charset=utf-8',
				async: false,
				success: function (status) {
					if (status.ResultSign == "Successful") {
						var message = status.MessageKey == null ? status : status.MessageKey;
		            	toastr.success(message, "提示");
		            
						$("#unwrapVel").hide();
						dataGrid._fnReDraw();
					} else {
						var message = status.MessageKey == null ? status : status.MessageKey;
		            	toastr.error(message, "提示");
					}	
				}
			});
		}
		/**
		 * 表格初始化
		 */
		function bindingVelInitGrid() {
			var cityId = $("#cityId").val();
			var gridObj = {
				id: "bindingVelDataGrid",
		        sAjaxSource: "PubDriverVehicleRef/GetPubDriverVehicleRefByQuery?cityId="+cityId,
		      //iLeftColumn: 1,//（固定表头，1代表固定几列）
		        scrollX: true,//（加入横向滚动条）
		        columns: [
			        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
			        {mDataProp: "plateNo", sTitle: "车牌号", sClass: "center", sortable: true },
			        {mDataProp: "brandCars", sTitle: "品牌车系", sClass: "center", sortable: true },
			        {mDataProp: "serviceModels", sTitle: "服务车型", sClass: "center", sortable: true },
			        {mDataProp: "color", sTitle: "颜色", sClass: "center", sortable: true },
			        {mDataProp: "city", sTitle: "所属城市", sClass: "center", sortable: true },
			        {
		                //自定义操作列
		                "mDataProp": "ZDY",
		                "sClass": "center",
		                "sTitle": "操作",
		                "sWidth": 60,
		                "bSearchable": false,
		                "sortable": false,
		                "mRender": function (data, type, full) {
		                    var html = "";
		                    html += '&nbsp; <button type="button" class="SSbtn orange"  onclick="bindingVelAdd(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 绑定</button>';
		                    return html;
		                }
		            }
		        ]
		    };
		    
			bindingVelDataGrid = renderGrid(gridObj);
		}
		$(".tip_box_c .close").click(function () {
			//dataGrid._fnReDraw();
			$(this).closest(".pop_box").hide();
		});
		/**
		 * 查询
		 */
		function query() {
			var conditionArr = [
				{ "name": "queryBrandCars", "value": $("#queryBrandCars").val() },
				{ "name": "queryPlateNo", "value": $("#queryPlateNo").val() },
				{ "name": "key", "value": $("#cityId").val() }
			];
			bindingVelDataGrid.fnSearch(conditionArr);
		}
		
		function bindingVelAdd(velid){
			var id = $("#driid").val();
			var url = "PubDriverVehicleRef/CreatePubDriverVehicleRef";
			var data = {
				vehicleId : velid,
				driverId : id
			}
			$.ajax({
				type: 'POST',
				dataType: 'json',
				url: url,
				data: JSON.stringify(data),
				contentType: 'application/json; charset=utf-8',
				async: false,
				success: function (status) {
					if (status.ResultSign == "Successful") {
						var message = status.MessageKey == null ? status : status.MessageKey;
		            	toastr.success(message, "提示");
		            
						$("#bindingVel").hide();
						dataGrid._fnReDraw();
					} else {
						var message = status.MessageKey == null ? status : status.MessageKey;
		            	toastr.error(message, "提示");
					}	
				}
			});
		}
		
		function initSelectQueryBrandCars() {
			var cityId = $("#cityId").val();
			$("#queryBrandCars").select2({
				placeholder : "",
				minimumInputLength : 0,
				multiple : false, //控制是否多选
				allowClear : true,
				ajax : {
					url : "PubDriverVehicleRef/GetBrandCars?cityId="+cityId,
					dataType : 'json',
					data : function(term, page) {
						return {
						};
					},
					results : function(data, page) {
						return {
							results: data
						};
					}
				}
			});
		}
		function unwrapRecord(id){
			window.location.href=base+"PubDriverVehicleRef/Index?id="+id;
		}
		/**
		 * 新增
		 */
		function add() {
			window.location.href=base+"PubDriver/AddIndex?title=新增司机信息";
		}
		
	</script>	
	</body>
</html>

var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	validateForm();
	initSelectBrandCars();
	initSelectQueryBrandCars();
	getSelectCitys();
	$(".close").click(function(){
		$("#select2-drop").css("display","none");
//		$("#select2-drop").remove();
	})
	
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "PubVehicle/GetPubVehicleByQuery",
        iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        language: {
        	sEmptyTable: "暂无服务车辆信息"
        },
        columns: [
//	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 100,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    html += '<button type="button" class="SSbtn green_q" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
                    html += '&nbsp; <button type="button" class="SSbtn red_q"  onclick="del(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 删 除</button>';
                    return html;
                }
            },
            //新增的
            {mDataProp: "vehicleType", sTitle: "车辆类型", sClass: "center", sortable: true,
            	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.vehicleType == '0'){
	        			html+='<font>网约车</font>';
	        		}else{
	        			html+='<font>出租车</font>';
	        		}
	        		return html;
	        	}	
            },
            {mDataProp: "serviceCars", sTitle: "服务车型", sClass: "center", sortable: true,
            	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.serviceCars == null){
	        			html+='<font>/</font>';
	        		}else if(full.vehicleType == '1'){
	        			html+='<font>/</font>';
	        		}else{
	        			html+='<font>'+full.serviceCars+'</font>';
	        		}
	        		return html;
	        	}
            },
            {mDataProp: "workStatus", sTitle: "服务状态", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.workStatus == '空闲'){
	        			html+='<font color="green">'+full.workStatus+'</font>';
	        		}else if(full.workStatus == '服务中'){
	        			html+='<font color="red">'+full.workStatus+'</font>';
	        		}else if(full.workStatus == '下线'){
	        			html+='<font>'+full.workStatus+'</font>';
	        		}else{
	        			html+='<font>/</font>';
	        		}
	        		return html;
	        	}	
	        },
	        //新增的
	        {mDataProp: "boundState", sTitle: "绑定状态", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.boundState > 0){
	        			html+='<font>已绑定</font>';
	        		}else{
	        			html+='<font color="red">未绑定</font>';
	        		}
	        		return html;
	        	}		
	        },
	        //新增的
	        {mDataProp: "vehicleStatus", sTitle: "营运状态", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.vehicleStatus == '0'){
	        			html+='<font>营运中</font>';
	        		}else{
	        			html+='<font>维修中</font>';
	        		}
	        		return html;
	        	}	
	        },
	        {mDataProp: "showPlateNo", sTitle: "车牌号", sClass: "center", sortable: true },
	        {mDataProp: "brandCars", sTitle: "品牌车系", sClass: "center", sortable: true },
	        {mDataProp: "vin", sTitle: "车架号", sClass: "center", sortable: true },
	        {mDataProp: "color", sTitle: "颜色", sClass: "center", sortable: true },
	        {mDataProp: "loads", sTitle: "荷载人数", sClass: "center", sortable: true },
	        {mDataProp: "belongleasecompanyName", sTitle: "归属车企", sClass: "center", sortable: true },
	        {mDataProp: "city", sTitle: "登记城市", sClass: "center", sortable: true },
	        //新增的
	        {mDataProp: "businessScope", sTitle: "经营区域", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		return showToolTips(full.businessScope);
	        	}
	        }
	      //{mDataProp: "driverMessage", sTitle: "司机信息", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 表单校验
 */
function validateForm() {
	$("#editForm").validate({
		rules: {       
//			plateNoProvince: {required: true},
//			plateNoCity: {required: true},
			plateNo: {required: true,maxlength : 5},
			vin: {required: true, maxlength: 17},
			brandCars: {required: true},
			color: {required: true, maxlength: 8},
			loads: {required: true, maxlength: 2,digits:true},
			businessScope:{required: true, maxlength:50},
			vehicleType : {required : true},
			belongleasecompany : { required : true }
		},
		messages: {
//			plateNoProvince: {required: "请填写完整的车牌号"},
//			plateNoCity: {required: "请填写完整的车牌号"},
			plateNo: {required: "请填写完整的车牌号",maxlength: "车牌号最大长度不能超过5个字符"},
			vin: {required: "请输入车架号",maxlength: "最大长度不能超过17个字符"},
			brandCars: {required: "请选择品牌车系"},
			color: {required: "请输入车颜色",maxlength: "最大长度不能超过8个字符"},
			loads: {required: "请输入荷载人数",maxlength: "最大长度不能超过2个字符"},
			businessScope:{required: "请输入经营区域", maxlength:50},
			vehicleType : {required : "请选择车辆类型"},
			belongleasecompany : {required : "请选择归属车企"}
		}
	})
}

/**
 * 新增
 */
function add() {
	$("#vehicleStatusDiv").hide();
	$("#titleForm").html("新增车辆信息");
	$("#editFormDiv").show();
	showObjectOnForm("editForm", null);
	$(".addcitybtn").val("+添加城市");
	$("#vehclineId").select2("val","");
	$("#vehicleType").prop("disabled",false);
	$(".addcbox").empty();
	
	var editForm = $("#editForm").validate();
	editForm.resetForm();
	editForm.reset();
	$("#citymarkid").val("");
	getSelectCity();
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "queryBrandCars", "value": $("#queryBrandCars").val() },
		{ "name": "queryPlateNo", "value": $("#queryPlateNo").val() },
		{ "name": "queryWorkStatus", "value": $("#queryWorkStatus").val() },
		{ "name": "queryCity", "value": $("#queryCity").val() },
		{ "name": "queryServiceCars", "value": $("#queryServiceCars").val() },
		{ "name": "queryVehicleType", "value": $("#queryVehicleType").val() },
		{ "name": "queryVehicleStatus", "value": $("#queryVehicleStatus").val() },
		{ "name": "queryBoundState", "value": $("#queryBoundState").val() },
		{ "name": "belongleasecompanyQuery", "value": $("#belongleasecompanyQuery").val() }
	];
	dataGrid.fnSearch(conditionArr);
	$("#queryBrandCarss").val($("#queryBrandCars").val());
	$("#queryPlateNos").val($("#queryPlateNo").val());
	$("#queryWorkStatuss").val($("#queryWorkStatus").val());
	$("#queryCitys").val($("#queryCity").val());
	$("#queryServiceCarss").val($("#queryServiceCars").val());
	$("#queryVehicleTypes").val($("#queryVehicleType").val());
	$("#queryVehicleStatuss").val($("#queryVehicleStatus").val());
	$("#queryBoundStates").val($("#queryBoundState").val());
	$("#belongleasecompanyQuerys").val($("#belongleasecompanyQuery").val());
}
/**
 * 修改
 * @param {} id
 */
function edit(id) {
	$("#titleForm").html("修改车辆信息");
	$.ajax({
		type: "GET",
		url:"PubVehicle/GetById",
		cache: false,
		data: { id: id },
		success: function (json) {
			checkUpdate(json.id,function(){
				$("#vehicleStatusDiv").show();
				$("#editFormDiv").show();
				var data = {
					id : json.plateNoProvince
				};
				$.post("PubVehicle/GetPlateNoCity", data, function(ret) {
					redrawVersionList(ret);
					showObjectOnForm("editForm", json);
					$(".addcitybtn").val("+添加城市");
					$(".addcbox").empty();
					var businessScopeArr = json.businessScope.split(",");
					var businessScopeTempArr = json.businessScopeTemp.split(",");
					
					for(var i in businessScopeArr) {
						var cityhtml='<div class="added" id="'+businessScopeArr[i]+'">'+businessScopeTempArr[i]+'<em class="ico_x_a"></em></div>';
						$(".addcbox").append(cityhtml);
					}
					
					$("#vehclineId").select2("data", {
						id : json.vehclineId,
						text : json.brandCars
					});
					$("#city").val(json.city);
					$("#citymarkid").val(json.citymarkid);
					$("#cityName").val(json.cityName);
					
					//车辆类型
					$("#vehicleTypes").val(json.vehicleType);
					var html = "";
					html += "<label>车辆类型<em class='asterisk'></em></label>";
					html += "<select id='vehicleType' name='vehicleType' disabled='disabled' style='width:68%;margin-right:4px;float:right;'>";
					html += "<option value=''>全部</option>";
					if(json.vehicleType == 0){
						html += "<option value='0' selected='selected'>网约车</option>";
						html += "<option value='1'>出租车</option>";
					}else{
						html += "<option value='0'>网约车</option>";
						html += "<option value='1' selected='selected'>出租车</option>";
					}
					html += "</select>";
					$("#vehicleTypeDiv").html(html);
					
					//营运状态
	   	   			var html1 ="";
	   	   			html1 = "<label>营运状态<em class='asterisk'></em></label>";
					if(json.vehicleStatus == 0){
						html1 += "<input id='vehicleStatus' name='vehicleStatus' type='radio' value='0' checked='checked'/>营运中 ";
						html1 += "<input id='vehicleStatus' name='vehicleStatus' type='radio' value='1'/>维修中";
					}else{
						html1 += "<input id='vehicleStatus' name='vehicleStatus' type='radio' value='0'/>营运中 ";
						html1 += "<input id='vehicleStatus' name='vehicleStatus' type='radio' value='1' checked='checked'/>维修中";
					}
					$("#vehicleStatusDiv").html(html1);
					
					var editForm = $("#editForm").validate();
					editForm.resetForm();
					editForm.reset();
					$("#id").val(id);
//					dataGrid._fnReDraw();
					getSelectCity();
				});
				
			},function(){
				toastr.error("当前车辆已绑定司机，请解绑后修改", "提示");
			});
		},
		error: function (xhr, status, error) {
			$("#id").val("");
			return;
		}
    });
}

function checkUpdate(id,suceessCallback,failCalback){
	$.ajax({
		type: "GET",
		url:"PubVehicle/CheckDelete",
		cache: false,
		data: { id: id },
		success: function (json) {
			if(json > 0){
				failCalback();
			}else{
				suceessCallback();
			}
		}
    });
}

/**
 * 删除
 * @param {} id
 */
function del(id) {
	checkUpdate(id,function(){
	var comfirmData={
		tittle:"提示",
		context:"您确定要删除车辆吗?",
		button_l:"不删除",
		button_r:"删除",
		click: "deletePost('" + id + "')",
		htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	Zconfirm(comfirmData);
	},function(){
		toastr.error("当前车辆已绑定司机，请解绑后再删除", "提示");
	});
}

function deletePost(id){
	var data = {id: id};
	$.post("PubVehicle/DeletePubVehicle", data, function (status) {
		if (status.ResultSign == "Successful") {
			var message = status.MessageKey == null ? status : status.MessageKey;
            toastr.success(message, "提示");
			dataGrid._fnReDraw();
		} else {
			var message = status.MessageKey == null ? status : status.MessageKey;
			toastr.error(message, "提示");
		}
	});
}

/**
 * 保存
 */
function save() {
	var form = $("#editForm");
	if(!form.valid()) return;
	
    
    //车牌
    var plateNo = $("#plateNo").val();
    var Regx1 = /^[A-Za-z0-9]*$/;
    if (Regx1.test(plateNo)){
    	if(plateNo.length != 5){
    		toastr.error("车牌号必须是5位", "提示");
        	return;
    	}
    }else{
    	toastr.error("请输入正确的车牌号", "提示");
    	return;
    }
    
  //车架号
	var id = $("#id").val();
	var vin = $("#vin").val();
	var Regx = /^[A-Z0-9]*$/;
    if (Regx.test(vin)){
    	if(vin.length != 17){
    		toastr.error("车架号必须是17位", "提示");
        	return;
    	}else{
    		var flag = false;
    		var data = {
    			id : id,
    			vin : vin
    		}
    		$.ajax({
    			type : 'GET',
    			dataType : 'json',
    			url : "PubVehicle/CheckPubVehicleVin",
    			data : data,
    			contentType : 'application/json; charset=utf-8',
    			async : false,
    			success : function(status) {
    				if(status>0){
    					flag = true;	
    				}
    			}
    		});
    		if(flag){
    			toastr.error("车架号已存在", "提示");
    			return;
    		}
    	}
    }else{
    	toastr.error("车架号只能为大写字母跟数字组合", "提示");
    	return;
    }
    
	
//	if($("#setAsDefault")[0].checked){  
//        //0 常用   
//		$("#setAsDefault").val("0");
//    }else{  
//    	// 取消常用
//    	$("#setAsDefault").val("1");
//    }  
	
	
	var url = "PubVehicle/CreatePubVehicle";
	if($("#id").val()) {
		url = "PubVehicle/UpdatePubVehicle";
	}
	var data = form.serializeObject();
	
	// 车牌省
	var plateNoProvince = data.plateNoProvince;
	if(plateNoProvince == null || plateNoProvince == ''){
		toastr.error("请选择车牌省", "提示");
		return;
	}
	//车牌市
	var plateNoCity = data.plateNoCity;
	if(plateNoCity == null || plateNoCity == ''){
		toastr.error("请选择车牌市", "提示");
		return;
	}
	
    var vehclineId = data.vehclineId;
    if(vehclineId == null || vehclineId == ''){
    	toastr.error("请选择品牌车系", "提示");
    	return;
    }
    
    //城市
    var city = data.city;
    if(city == null || city == ''){
    	toastr.error("请选择登记城市", "提示");
    	return;
    }
    
    // 经营范围
    var businessScopeDiv = $(".addcbox").children();
//    if(businessScopeDiv.length == 0) {
//    	toastr.error("请选择经营区域", "提示");
//    	return;
//    }
    if(businessScopeDiv.length == 0 || document.getElementById("addcboxId").innerHTML == "" ||document.getElementById("addcboxId").innerHTML == null){
		toastr.error("经营区域不能为空", "提示");
    	return;
	}
    
    var businessScopeArr = [];
    for(var i = 0; i < businessScopeDiv.length; i++) {
    	businessScopeArr.push(businessScopeDiv[i].id);
    }
    
   data.businessScope = businessScopeArr.join(",");
    
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
            
				$("#editFormDiv").hide();
				showObjectOnForm("editForm", null);
				dataGrid._fnReDraw();
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
			}	
		}
	});
}

/**
 * 取消
 */
function canel() {
	$("#editFormDiv").hide();
	$("#importExcelDiv").hide();
	$("#select2-drop").css("display","none");
}

/***
 * 
 * 初始化  搜索下拉 
 */
function initSelectBrandCars() {
	$("#vehclineId").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubVehcline/GetBrandCars",
			dataType : 'json',
			data : function(term, page) {
				return {
					id : term
				};
			},
			results : function(data, page) {
				return {
					results : data
				};
			}
		}
	});
}

function initSelectQueryBrandCars() {
	$("#queryBrandCars").select2({
		placeholder : "请选择品牌车系",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubVehicle/GetBrandCars",
			dataType : 'json',
			data : function(term, page) {
				return {
					id : term
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
/**
 * 新增的清空功能
 * 
 */
function emptys(){
	$("#queryBrandCars").select2("val","");
	$("#queryPlateNo").val("");
	$("#queryWorkStatus").val("");
	$("#queryCity").val("");
	$("#queryServiceCars").val("");
	$("#queryVehicleType").val("");
	$("#queryVehicleStatus").val("");
	$("#queryBoundState").val("");
	$("#belongleasecompanyQuery").val("");
	search();
}

/**
 * 下载模板
 * */
function download(){
	window.location.href = base+"PubVehicle/DownLoad";
}

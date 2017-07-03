var dataGrid;
var bindingVelDataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	initSelect2();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "PubDriverVehicleRef/GetDriverInfoByQuery",
        iLeftColumn: 3,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无服务司机信息"
        },
        columns: [
	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 80,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    if(full.boundstate == 0) {
                    	html += '<button type="button" class="SSbtn red" onclick="bindingVel(' + "'" + full.id + "','" + full.name + "','" + full.city + "'" +')"><i class="fa fa-paste"></i>绑定</button>';
                    } else {
                    	html += '<button type="button" class="SSbtn green_a" onclick="unwrapVel(' + "'" + full.id + "','" + full.vehicleid + "','" + full.name + "','" + full.phone + "','" + full.plateno + "','" + full.vehcbrandname +"'" +')"><i class="fa fa-paste"></i>解绑</button>';
                    }
                    return html;
                }
            },
            {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", sortable: true },
            {mDataProp: "name", sTitle: "姓名", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if(data != null) {
                    	return '<span class="font_red">' + data + '</span>';
                    } else {
                    	return '/';
                    }
                }
            },
            {mDataProp: "phone", sTitle: "手机号", sClass: "center", sortable: true },
            {mDataProp: "cityvisual", sTitle: "登记城市", sClass: "center", sortable: true },
            {mDataProp: "boundstatevisual", sTitle: "绑定状态", sClass: "center", sortable: true },
            {mDataProp: "workstatusvisual", sTitle: "服务状态", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if(data != null) {
                    	if(full.boundstate == 0) {
                        	return '/';
                        } else {
                        	if (full.workstatus == 0) {
                        		return '<span class="font_green">' + full.workstatusvisual + '</span>';
                        	} else if (full.workstatus == 1) {
                        		return '<span class="font_red">' + full.workstatusvisual + '</span>';
                        	} else {
                        		return full.workstatusvisual;
                        	}
                        }
                    } else {
                    	return '/';
                    }
                }
            },
            {mDataProp: "vehiclemodelsname", sTitle: "服务车型", sClass: "center", sortable: true },
            {mDataProp: "vehcbrandname", sTitle: "品牌车系", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if(data != null) {
                    	if(full.boundstate == 0) {
                        	return '/';
                        } else {
                        	return full.vehcbrandname;
                        }
                    } else {
                    	return '/';
                    }
                }
            },
            {mDataProp: "plateno", sTitle: "车牌号", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if(data != null) {
                    	if(full.boundstate == 0) {
                        	return '/';
                        } else {
                        	return full.plateno;
                        }
                    } else {
                    	return '/';
                    }
                }
            }
            
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

function initSelect2() {
	$("#driver").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubDriverVehicleRef/GetDriverByNameOrPhone",
			dataType : 'json',
			data : function(term, page) {
				return {
					driver: term,
					vehicletype: '0',
					jobstatus: '0'
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});
	
	$("#jobnum").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubDriverVehicleRef/GetDriverByJobnum",
			dataType : 'json',
			data : function(term, page) {
				return {
					jobnum: term,
					jobstatus: '0'
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});
	
	$("#vehclineid").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubDriverVehicleRef/GetVehcbrand",
			dataType : 'json',
			data : function(term, page) {
				return {
					vehcbrandname: term
				};
			},
			results : function(data, page) {
				return {
					results: getDataVar(data)
				};
			}
		}
	});
}

function getDataVar(data) {
	var datavar = [];
	for (var i=0;i<data.length;i++) {
		datavar.push(data[i]);
	}
	return datavar;
}

var isclear = false;
/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "driver", "value": $("#driver").val() },
		{ "name": "jobnum", "value": $("#jobnum").val() },
		{ "name": "boundstate", "value": $("#boundstate").val() },
		{ "name": "workstatus", "value": $("#workstatus").val() },
		{ "name": "vehiclemodelsid", "value": $("#vehiclemodelsid").val() },
		{ "name": "city", "value": $("#city").val() },
		{ "name": "vehclineid", "value": $("#vehclineid").val() },
		{ "name": "plateno", "value": $("#plateno").val() }
	];
	
	if (isclear) {
		dataGrid.fnSearch(conditionArr,"暂无服务司机信息");
	} else {
		dataGrid.fnSearch(conditionArr);
	}
}

/**
 * 清空
 */
function clearSearch() {
	$("#driver").select2("val","");
	$("#jobnum").select2("val","");
	$("#boundstate").val("");
	$("#workstatus").val("");
	$("#vehiclemodelsid").val("");
	$("#city").val("");
	$("#vehclineid").select2("val","");
	$("#plateno").val("");
	
	isclear = true;
	search();
	isclear = false;
}

/**
 * 绑定
 */
function bindingVel(id,name,city) {
	$("#driid").val(id);
	$("#cityId").val(city);
	$("#bindingVelTitleForm").html("绑定车辆 <font color='orange'>【"+name+"】</font>");
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

function initSelectQueryBrandCars() {
	var cityId = $("#cityId").val();
	$("#queryBrandCars").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubDriverVehicleRef/GetVehcbrandByCity/"+cityId,
			dataType : 'json',
			data : function(term, page) {
				return {
					vehcbrandname: term
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
 * 绑定车辆表格初始化
 */
function bindingVelInitGrid() {
	var cityId = $("#cityId").val();
	var gridObj = {
		id: "bindingVelDataGrid",
        sAjaxSource: "PubDriverVehicleRef/GetUnbandCarsByCity?cityId="+cityId,
        //iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        language: {
        	sEmptyTable: "暂无可绑定服务车辆信息"
        },
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "plateno", sTitle: "车牌号", sClass: "center", sortable: true },
	        {mDataProp: "vehcbrandname", sTitle: "品牌车系", sClass: "center", sortable: true },
	        {mDataProp: "vehiclemodelsname", sTitle: "服务车型", sClass: "center", sortable: true },
	        {mDataProp: "color", sTitle: "颜色", sClass: "center", sortable: true },
	        {mDataProp: "city", sTitle: "登记城市", sClass: "center", sortable: true },
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
                    html += '<button type="button" class="SSbtn red"  onclick="bindingVelAdd(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 绑定</button>';
                    return html;
                }
            }
        ]
    };
    
	bindingVelDataGrid = renderGrid(gridObj);
}

function query() {
	var conditionArr = [
		{ "name": "vehclineid", "value": $("#queryBrandCars").val() },
		{ "name": "plateno", "value": $("#queryPlateNo").val() },
		{ "name": "key", "value": $("#cityId").val() }
	];
	if (isclear) {
		bindingVelDataGrid.fnSearch(conditionArr,"暂无可绑定服务车辆信息");
	} else {
		bindingVelDataGrid.fnSearch(conditionArr);
	}
}

/**
 * 清空
 */
function clearQuery() {
	$("#queryBrandCars").select2("val","");
	$("#queryPlateNo").val("");
	
	isclear = true;
	query();
	isclear = false;
}

/**
 * 绑定车辆
 */
function bindingVelAdd(velid) {
	var id = $("#driid").val();
	var url = "PubDriverVehicleRef/CreatePubDriverVehicleRef";
	var data = {
		vehicleid : velid,
		driverid : id
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
            	
            	bindingVelDataGrid._fnReDraw();
            	dataGrid._fnReDraw();
			}	
		}
	});
}








/**
 * 解绑
 */
function unwrapVel(driverid,vehicleid,name,phone,plateno,vehcbrandname) {
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: "PubDriverVehicleRef/JudgeUncompleteOrder?driverid=" + driverid +"&vehicleid=" + vehicleid,
		data: null,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				$("#unwrapDriverId").val(driverid);
				$("#unwrapVehicleId").val(vehicleid);
				if (name == null || name == "") {
					$("#driverNamePhone").html(phone);
				} else {
					$("#driverNamePhone").html(name + ' ' + phone);
				}
				$("#carPlateNo").html(plateno);
				$("#brandCars").html(vehcbrandname);
				$("#unBindReason").val("");
				$("#unwrapVel").show();
				unwrapVelForm();
				// 清除验证提示
				var editForm = $("#unwrapVelForm").validate();
				editForm.resetForm();
				editForm.reset();
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
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
			unBindReason: {required: true, maxlength: 100}
		},
		messages: {
			unBindReason: {required: "请输入解绑原因",maxlength: "最大长度不能超过100个字符"}
		}
	})
}

/**
 * 解绑操作
 */
function unwrapVelSave() {
	var form = $("#unwrapVelForm");
	if(!form.valid()) return;
	var url = "PubDriverVehicleRef/UpdatePubDriverVehicleRef";
	var driverId =$("#unwrapDriverId").val();
	var vehicleId =$("#unwrapVehicleId").val();
	var unBindReason = $("#unBindReason").val();
	var data = {
			driverid : driverId,
			vehicleid : vehicleId,
			unbindreason : unBindReason
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
 * 取消
 */
function canel() {
	$("#unwrapVel").hide();
}

/**
 * 操作记录
 */
function operateRecord() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "PubDriverVehicleRef/DriverOperateDetail";
}

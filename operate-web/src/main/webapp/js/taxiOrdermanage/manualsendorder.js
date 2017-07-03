var map;
var currentOrder;
var errMsg;
$(function () {
	errMsg = "请您输入派单原因";
	if(orderObj.type == "2") {
		$("#manualSendOrderFormTitle").text("更换车辆");
		$("#manuTitle").text("更换车辆");
		$("#orderreasonTextareaLabel").html('更换原因<em class="asterisk"></em>');
		$("#orderreasonTextarea").attr("placeholder", "填写更换车辆原因");
		errMsg = "请您输入更换原因";
	}
	
	initOrder();
	validateForm();
});

/**
 * 初始化订单数据
 */
function initOrder() {
	$("#vehicleid").select2({
        placeholder: "车牌",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "TaxiOrderManage/GetTaxiPlatonoBySelect",
            dataType: 'json',
            data: function (term, page) {
                return {
                	plateno: term
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
	
	$("#vehicleid").on("change", function() {
		driverInfoChange();
    })
	
	var data = {orderno: orderObj.orderno};
	$.get($("#baseUrl").val() + "TaxiOrderManage/GetOpTaxiOrderByOrderno?datetime=" + new Date().getTime(), data, function (result) {
		if (result) {
			currentOrder = result;
			if(null == result.nickname || "" == result.nickname) {
				$("#xdr").text(result.account);
			} else {
				$("#xdr").text(result.nickname + " " + result.account);
			}
			$("#ycsj").text(timeStamp2String(result.usetime));
			bindMap(result);
			$("#sjselect").val("0");
			if(result.isusenow == true) {
				$("#sjselect").attr("disabled", true);
			}
			initDriverDataGrid();
		} else {
			driverInfoChange();
		}
	});
}

/**
 * 绑定地图
 * @param {} order
 */
function bindMap(order) {
	map = new BMap.Map("map_canvas", {enableMapClick:false});
	var point = new BMap.Point(order.onaddrlng, order.onaddrlat);
	map.centerAndZoom(point, 15);
	map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
	map.enableScrollWheelZoom();
   
	bindOnAddress(order);
	bindOffAddress(order);
}

/**
 * 创建上车点
 * @param {} order
 */
function bindOnAddress(order) {
	var pt = new BMap.Point(order.onaddrlng, order.onaddrlat);
	var myIcon = new BMap.Icon("img/orgordermanage/icon_shangche.png", new BMap.Size(100,50));
	var marker2 = new BMap.Marker(pt, {icon:myIcon});  // 创建标注
	map.addOverlay(marker2); 
}

/**
 * 创建下车点
 * @param {} order
 */
function bindOffAddress(order) {
	var pt = new BMap.Point(order.offaddrlng, order.offaddrlat);
	var myIcon = new BMap.Icon("img/orgordermanage/icon_xiache.png", new BMap.Size(100,50));
	var marker2 = new BMap.Marker(pt, {icon:myIcon});  // 创建标注
	map.addOverlay(marker2); 
}

/**
 * 司机信息下拉框改变后触发事件
 */
function driverInfoChange() {
	// 清除坐标点
	map.clearOverlays();
	
	bindOnAddress(currentOrder);
	bindOffAddress(currentOrder);
	var oData = [
		{ "name": "distance", "value":  $("#jlselect").val()},
		{ "name": "orderLon", "value":  currentOrder.onaddrlng},
		{ "name": "orderLat", "value":  currentOrder.onaddrlat},
		{ "name": "driverState", "value": $("#sjselect").val()},
		{ "name": "vehicleid", "value": $("#vehicleid").val()},
		{ "name": "isDriverState", "value": "1"}
	];
	dataGrid.fnSearch(oData);
}

/**
 * 初始化司机查询表格
 */
function initDriverDataGrid() {
	var gridObj = {
		id: "driverDataGrid",
        sAjaxSource: $("#baseUrl").val() + "TaxiOrderManage/GetDriverByQuery",
        lengthChange: false,
        bInfo: false,
        userQueryParam: [
        	{ "name": "orderLon", "value":  currentOrder.onaddrlng},
			{ "name": "orderLat", "value":  currentOrder.onaddrlat},
			{ "name": "oncity", "value": currentOrder.oncity},
			{ "name": "offcity", "value": currentOrder.offcity},
			{ "name": "driverid", "value": currentOrder.driverid},
			{ "name": "orderNo", "value": orderObj.orderno},
        ],
        iDisplayLength: 3,
        columns: [{
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "信息",
                "sWidth": 50,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = [];
                    html.push("<div class='col-6'>车牌：");
                    html.push(full.plateno);
                    html.push("</div><div class='col-6'>");
                    html.push("状态：");
                    if(full.workstatus == "0") {
                    	html.push("空闲");
    				} else if(full.workstatus == "1") {
    					html.push("服务中");
    				} else if(full.workstatus == "2") {
    					html.push("下线");
    				} else {
    					html.push("");
    				}
                    html.push("</div><br/><div class='col-12'>");
                    html.push("姓名：");
                    html.push(full.name + " " + full.phone);
                    html.push("</div><br/><div class='col-12'>");
                    html.push("约：" + Math.ceil(full.duration / 60) + "分钟到达 总里程为：" + (full.distance / 1000).toFixed(1) + "公里");
                    html.push("</div><div class='col-12'>");
                    html.push("<button type=\"button\" class=\"SSbtn blue\" onclick=\"manualSendOrder('" + full.driverid + "', '" + full.id + "');\"><i class=\"fa fa-paste\"></i>给TA派单</button>");
                    html.push("</div>");
                    return html.join("");
                }
            }
        ],
        userHandle: function(oSettings, result) {
        	map.clearOverlays();
        	bindMap(currentOrder);
        	var opts = {width: 400, height: 135};
        	for(var index in result.aaData) {
        		var vehicle = result.aaData[index];
        		var point = new BMap.Point(vehicle.lng, vehicle.lat);
        		var driverImg = "img/orgordermanage/icon_green.png";
        		if(vehicle.workstatus == "0") {
        			driverImg = "img/orgordermanage/icon_green.png";
        		} else if(vehicle.workstatus == "1") {
        			driverImg = "img/orgordermanage/icon_red.png";
        		} else if(vehicle.workstatus == "2") {
        			driverImg = "img/orgordermanage/icon_gray.png";
        		}
				var myIcon = new BMap.Icon(driverImg, new BMap.Size(50, 50));
				var marker = new BMap.Marker(point, {icon:myIcon});
				map.addOverlay(marker);
				initMarker(marker, point, vehicle, opts);
        	}
        	if((null == result || null == result.aaData || result.aaData.length == 0) && orderObj.type != 2) {
        		$("#sendFail").show();
        	} else {
        		$("#sendFail").hide();
        	}
        }
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 派单失败
 */
function sendFail() {
	var comfirmData={
		tittle:"提示",
		context:"<h4>确定直接判定派单失败？</h4>",
		contextAlign:"left",
		button_l:"确定",
		button_r:"取消",
		click: "toSendFail()",
		htmltex:"<input type='hidden' placeholder='添加的html'>"
	};
	ZconfirmLeft(comfirmData);
}

/**
 * 执行派单失败
 */
function toSendFail() {
	$.ajax({
		type: "GET",
		dataType: "json",
		url: $("#baseUrl").val() + "TaxiOrderManage/SendFail/" + orderObj.orderno + "?date=" + new Date(),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
				window.location.href = $("#baseUrl").val() + "TaxiOrderManage/LabourOrderIndex";
			} else {
            	toastr.error(message, "提示");
			}
		}
	});
}

function initMarker(marker, point, vehicle, opts) {
	var htmlArr = [];
	htmlArr.push("<div class=\"col-3\">");
	htmlArr.push("<input type='hidden' id= 'selectDriverId' value=" + vehicle.driverid + "/>");
	htmlArr.push("<img src=\"" + (serviceAddress + "/" + vehicle.headportraitmax) + "\" width='80px' height = '89px'/>");
	htmlArr.push("</div>");
	htmlArr.push("<div class='col-9'><div style='float:left;'>");
	htmlArr.push("车牌：");
 	htmlArr.push(vehicle.plateno);
 	htmlArr.push("</div><div style='margin-left:160px;'>");
	htmlArr.push("状态：");
	if(vehicle.workstatus == "0") {
		htmlArr.push("空闲");
	} else if(vehicle.workstatus == "1") {
		htmlArr.push("服务中");
	} else if(vehicle.workstatus == "2") {
		htmlArr.push("下线");
	} else {
		htmlArr.push("");
	}
	htmlArr.push("</div></div></br><div class='col-9'>");
	htmlArr.push("姓名：");
	htmlArr.push(vehicle.name);
	htmlArr.push("(" + vehicle.phone + ")");
	htmlArr.push("</div>");
	htmlArr.push("<div class=\"col-12\">");
	htmlArr.push("约：" + Math.ceil(vehicle.duration / 60) + "分钟到达 总里程为：" + (vehicle.distance / 1000).toFixed(1) + "公里");
	htmlArr.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
    htmlArr.push("<button type=\"button\" class=\"SSbtn blue\" onclick=\"manualSendOrder('" + vehicle.driverid + "', '" + vehicle.id + "');\"><i class=\"fa fa-paste\"></i>给TA派单</button>");
	htmlArr.push("</div>");
	marker.addEventListener("click", function(e) {
		var infoWindow = new BMap.InfoWindow(htmlArr.join(""), opts);
		this.openInfoWindow(infoWindow, point);
	});
}

/**
 * 表单校验
 */
function validateForm() {
	$("#manualSendOrderForm").validate({
		rules: {
			orderreasonTextarea: {required: true}
		},
		messages: {
			orderreasonTextarea: {required: errMsg}
		}
	})
}

/**
 * 派单
 * @param {} orderno
 */
function manualSendOrder(driverid, vehicleid) {
	$("#driverid").val(driverid);
	$("#vehicleidHide").val(vehicleid);
	$("#manualSendOrderFormDiv").show();
	
	showObjectOnForm("manualSendOrderForm", null);
	
	var editForm = $("#manualSendOrderForm").validate();
	editForm.resetForm();
	editForm.reset();
}

/**
 * 保存
 */
function save() {
	var form = $("#manualSendOrderForm");
	if(!form.valid()) {
		var orderreason = $("#orderreasonTextarea").val();
		if(null == orderreason || orderreason == "") {
			toastr.error(errMsg, "提示");
			return;
		}
		return;
	}
	if(orderObj.type == "1") {
		manualsendorder();
	} else if(orderObj.type == "2") {
		changeDriver();
	}
}

/**
 * 更换司机
 */
function changeDriver() {
	var formData = {
		orderreason: $("#orderreasonTextarea").val(),
		orderno: orderObj.orderno,
		vehicleid: $("#vehicleidHide").val()
	}
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "TaxiOrderManage/ChangeVehicle",
		data: JSON.stringify(formData),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
				$("#manualSendOrderFormDiv").hide();
            	toastr.options.onHidden = function() {
            		window.location.href = $("#baseUrl").val() + "TaxiOrderManage/CurrentOrderIndex";
            	}
            	toastr.success(message, "提示");
			} else {
            	toastr.error(message, "提示");
			}
		}
	});
}


/**
 * 人工派单
 */
function manualsendorder() {
	var formData = {
		orderreason: $("#orderreasonTextarea").val(),
		orderno: orderObj.orderno,
		vehicleid: $("#vehicleidHide").val()
	}
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "TaxiOrderManage/ManualSendOrder",
		data: JSON.stringify(formData),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
				$("#manualSendOrderFormDiv").hide();
            	toastr.options.onHidden = function() {
            		window.location.href = $("#baseUrl").val() + "TaxiOrderManage/LabourOrderIndex";
            	}
            	toastr.success(message, "提示");
			} else {
            	toastr.error(message, "提示");
			}
		}
	});
}

/**
 * 取消
 */
function canel() {
	$("#manualSendOrderFormDiv").hide();
	driverInfoChange();
}

/**
 * 初始化查询条件
 */
function initSearch() {
	$("#vehicleid").select2("val", "");
	$("#sjselect").val("0");
	$("#jlselect").val("");
	driverInfoChange();
}

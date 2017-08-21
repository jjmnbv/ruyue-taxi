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
        	if(null == result || null == result.aaData || result.aaData.length == 0) {
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
	showObjectOnForm("cancelpartyForm", null);
	showCancelreason();
	var editForm = $("#cancelpartyForm").validate();
	editForm.resetForm();
	editForm.reset();
	//查询取消费用
	var data = {
		orderno: currentOrder.orderno,
		ordertype: currentOrder.ordertype,
		usetype: currentOrder.usetype
	};
	$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "TaxiOrderManage/GetCancelPriceDetail",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			if (result.status == 0) {
				$("#identifyingHide").val(result.identifying);
				var pricereason = result.pricereason;
				if(pricereason == 2) { //orderstatusHide的值为1表示没有司机接单，值为2表示司机已接单
					$("#orderstatusHide").val(1);
				} else {
					$("#orderstatusHide").val(2);
				}
				initCancelWindow();
				if(pricereason == 2 || pricereason == 3) {
					$("#cancelDetail").html("");
				} else {
					$("#cancelDetail").html(getCancelShowTable(result));
				}
				$("#cancelpartyFormDiv").show();
			} else {
				toastr.error(result.message, "提示");
			}
		}
	});
}

/**
 * 责任方和取消原因显示
 */
function initCancelWindow() {
	var orderstatus = $("#orderstatusHide").val();
	//责任方下拉框显示
	var dutypartyHtml = '<option value="">请选择</option>';
	if(orderstatus == 1) { //没有司机接单
		dutypartyHtml += '<option value="1">乘客</option>';
		dutypartyHtml += '<option value="3">客服</option>';
		dutypartyHtml += '<option value="4">平台</option>';
	} else {
		dutypartyHtml += '<option value="1">乘客</option>';
		dutypartyHtml += '<option value="2">司机</option>';
		dutypartyHtml += '<option value="3">客服</option>';
		dutypartyHtml += '<option value="4">平台</option>';
	}
	$("#dutyparty").html(dutypartyHtml);

	//取消原因显示
	var cancelreasonHtml = '<option value="">请选择</option>';
	if(orderstatus == 1) { //没有司机接单
		cancelreasonHtml += '<option value="1">不再需要用车</option>';
		cancelreasonHtml += '<option value="5">业务操作错误</option>';
		cancelreasonHtml += '<option value="6">暂停供车服务</option>';
	} else {
		cancelreasonHtml += '<option value="1">不再需要用车</option>';
		cancelreasonHtml += '<option value="2">乘客迟到违约</option>';
		cancelreasonHtml += '<option value="3">司机迟到违约</option>';
		cancelreasonHtml += '<option value="4">司机不愿接乘客</option>';
		cancelreasonHtml += '<option value="5">业务操作错误</option>';
		cancelreasonHtml += '<option value="6">暂停供车服务</option>';
	}
	$("#cancelreason").html(cancelreasonHtml);
}

/**
 * 取消费用说明
 * @param result
 * @returns {string}
 */
function getCancelShowTable(result) {
	var ordercancelrule = result.ordercancelrule;
	var html = '<table>';
	if(result.pricereason == 4) { //司机迟到
		html += '<tr><td colspan="4" style="text-align: left;">说明</td></tr>';
		html += '<tr><td>取消时差(分钟)</td><td>免责取消时限(分钟)</td><td>司机迟到时长(分钟)</td><td>迟到免责时限(分钟)</td></tr>';
		html += '<tr><td>' + result.canceltimelag + '</td><td>' + ordercancelrule.cancelcount + '</td><td>' + result.driverlate + '</td><td>' + ordercancelrule.latecount + '</td></tr>';
	} else {
		html += '<tr><td colspan="3" style="text-align: left;">说明</td></tr>';
		if(result.pricereason != 1) {
			html += '<tr><td colspan="3" style="text-align: left;">乘客需支付取消费用<span class="font_red">' + result.price + '</span></td></tr>';
		}
		html += '<tr><td>取消时差(分钟)</td><td>免责取消时限(分钟)</td><td>司机是否抵达</td></tr>';
		html += '<tr><td>' + result.canceltimelag + '</td><td>' + ordercancelrule.cancelcount + '</td>';
		if(result.driverarraival == true) {
			html += '<td>正常抵达</td></tr>';
		} else {
			html += '<td>未抵达</td></tr>';
		}
	}
	html += '</table>';
	return html;
}

/**
 * 关闭取消弹框
 */
function cancelpartyFormDivCanel() {
	$("#cancelpartyFormDiv").hide();
}

function saveCancelparty(){
	var form = $("#cancelpartyForm");
	if(!form.valid()) {
		return;
	}

	var identifying = $("#identifyingHide").val();

	var data = {
		orderno: currentOrder.orderno,
		ordertype: currentOrder.ordertype,
		usetype: currentOrder.usetype,
		identifying: identifying,
		dutyparty: $("#dutyparty").val(),
		cancelreason: $("#cancelreason").val()
	};

	$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "TaxiOrderManage/CancelOpTaxiOrder",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var url;
			if(orderObj.type == "1") {
				url = $("#baseUrl").val() + "TaxiOrderManage/Index";
			} else {
				url = $("#baseUrl").val() + "TaxiOrderManage/CurrentOrderIndex";
			}
			toastr.options.onHidden = function() {
				window.location.href = url;
			}
			if (result.status == "success") {
				toastr.success(result.message, "提示");
			} else {
				toastr.error(result.message, "提示");
			}
			$("#cancelpartyFormDiv").hide();
		}
	});
}

/**
 * 根据责任方对应显示取消原因
 */
function showCancelreason() {
	var dutyparty = $("#dutyparty").val();
	var html = '';

	var orderstatus = $("#orderstatusHide").val();
	if(orderstatus == 1) { //没有司机接单
		if(dutyparty == 1) {
			html += '<option value="1">不再需要用车</option>';
		} else if(dutyparty == 3) {
			html += '<option value="5">业务操作错误</option>';
		} else if(dutyparty == 4) {
			html += '<option value="6">暂停供车服务</option>';
		} else {
			html += '<option value="">请选择</option>';
			html += '<option value="1">不再需要用车</option>';
			html += '<option value="5">业务操作错误</option>';
			html += '<option value="6">暂停供车服务</option>';
		}
	} else {
		if(dutyparty == 1) {
			html += '<option value="">请选择</option>';
			html += '<option value="1">不再需要用车</option>';
			html += '<option value="2">乘客迟到违约</option>';
		} else if(dutyparty == 2) {
			html += '<option value="">请选择</option>';
			html += '<option value="3">司机迟到违约</option>';
			html += '<option value="4">司机不愿接乘客</option>';
		} else if(dutyparty == 3) {
			html += '<option value="5">业务操作错误</option>';
		} else if(dutyparty == 4) {
			html += '<option value="6">暂停供车服务</option>';
		} else {
			html += '<option value="">请选择</option>';
			html += '<option value="1">不再需要用车</option>';
			html += '<option value="2">乘客迟到违约</option>';
			html += '<option value="3">司机迟到违约</option>';
			html += '<option value="4">司机不愿接乘客</option>';
			html += '<option value="5">业务操作错误</option>';
			html += '<option value="6">暂停供车服务</option>';
		}
	}
	$("#cancelreason").html(html);
	resetCancelForm();
}

/**
 * 根据取消原因对应显示责任方
 */
function showDutyparty() {
	var cancelreason = $("#cancelreason").val();
	if(cancelreason == 1 || cancelreason == 2) {
		$("#dutyparty").val(1);
	} else if(cancelreason == 3 || cancelreason == 4) {
		$("#dutyparty").val(2);
	} else if(cancelreason == 5) {
		$("#dutyparty").val(3);
	} else if(cancelreason == 6) {
		$("#dutyparty").val(4);
	}
	showCancelreason();
	$("#cancelreason").val(cancelreason);
	resetCancelForm();
}

/**
 * 表单校验重置
 */
function resetCancelForm() {
	var form = $("#cancelpartyForm").validate();
	form.resetForm();
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
	});
	$("#cancelpartyForm").validate({
		rules : {
			dutyparty : {
				required : true
			},
			cancelreason : {
				required : true
			}
		},
		messages : {
			dutyparty : {
				required : "请选择责任方"
			},
			cancelreason : {
				required : "请选择取消原因"
			}
		}
	});
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

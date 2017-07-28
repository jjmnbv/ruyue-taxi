var map;
var currentOrder;
$(function () {
	if(orderObj.type == "2") {
		$("#manualSendOrderFormTitle").text("更换司机");
		$("#manuTitle").text("更换司机");
		$("#orderreasonTextareaLabel").text("更换原因");
		$("#orderreasonTextarea").attr("placeholder", "填写更换原因");
	}
	
	initOrder();
	initEvent();
	validateForm();
});

/**
 * 初始化订单数据
 */
function initOrder() {
	var data = {orderno: orderObj.orderno};
	$.get($("#baseUrl").val() + "OrderManage/GetOrgWaitingOrderByOrderno?datetime=" + new Date().getTime(), data, function (result) {
		if (result) {
			currentOrder = result;
			
			if(null == result.nickname || "" == result.nickname) {
				$("#xdr").text(result.account);
			} else {
				$("#xdr").text(result.nickname + " " + result.account);
			}
			$("#ycsj").text(timeStamp2String(result.usetime));
			$("#xdcx").text(result.selectedmodelname);
			bindMap(result);
			$("#sjselect").val("0");
			if(result.isusenow == true) {
				$("#sjselect").attr("disabled", true);
			}
			initDriverDataGrid();
		} else {
			
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
	
	var cx = $("#cxselect").val();
	var jl = $("#jlselect").val();
	var driverName = $("#driverName").val();
	
	var oData = [
		{ "name": "selectedmodel", "value": cx},
		{ "name": "distance", "value":  jl},
		{ "name": "driverName", "value":  driverName},
		{ "name": "orderLon", "value":  currentOrder.onaddrlng},
		{ "name": "orderLat", "value":  currentOrder.onaddrlat},
    	{ "name": "orderOncityName", "value":  currentOrder.oncityname},
		{ "name": "orderOffcityName", "value":  currentOrder.offcityname},
		{ "name": "driverState", "value": $("#sjselect").val()},
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
        sAjaxSource: $("#baseUrl").val() + "OrderManage/GetOrgDriverByQuery",
        lengthChange: false,
        bInfo: false,
        userQueryParam: [
        	{ "name": "orderLon", "value":  currentOrder.onaddrlng},
			{ "name": "orderLat", "value":  currentOrder.onaddrlat},
        	{ "name": "orderOncityName", "value":  currentOrder.oncityname},
			{ "name": "orderOffcityName", "value":  currentOrder.offcityname},
			{ "name": "oncity", "value": currentOrder.oncity},
			{ "name": "offcity", "value": currentOrder.offcity},
			{ "name": "driverid", "value": currentOrder.driverid},
			{ "name": "orderNo", "value": currentOrder.orderno},
			{ "name": "usetype", "value": "1"},
			{ "name": "orderSelectedmodel", "value": currentOrder.selectedmodel}
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
                    html.push("</div><br/><div class='col-6'>");
                    html.push("品牌车系：");
                    html.push(full.brandname);
                    html.push("</div><div class='col-6'>");
                    html.push("车型：");
                    html.push(full.modelname);
                    html.push("</div><br/><div class='col-12'>");
                    html.push("姓名：");
                    html.push(full.drivername);
                    html.push("</div><br/><div class='col-12'>");
                    html.push("约：" + Math.ceil(full.duration / 60) + "分钟到达 总里程为：" + (full.distance / 1000).toFixed(1) + "公里");
                    html.push("</div><div class='col-12'>");
                    html.push("<button type=\"button\" class=\"SSbtn blue\" onclick=\"manualSendOrder('" + full.driverid + "', '" + full.modelsid + "', '" + full.vehicleid + "');\"><i class=\"fa fa-paste\"></i>给TA派单</button>");
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
        		var driver = result.aaData[index];
        		var point = new BMap.Point(driver.lng, driver.lat);
        		var driverImg = "img/orgordermanage/icon_green.png";
        		if(driver.workstatus == "0") {
        			driverImg = "img/orgordermanage/icon_green.png";
        		} else if(driver.workstatus == "1") {
        			driverImg = "img/orgordermanage/icon_red.png";
        		} else if(driver.workstatus == "2") {
        			driverImg = "img/orgordermanage/icon_gray.png";
        		}
				var myIcon = new BMap.Icon(driverImg, new BMap.Size(50, 50));
				var marker = new BMap.Marker(point, {icon:myIcon});
				map.addOverlay(marker);
				initMarker(marker, point, driver, opts);
        	}
        }
    };
    
	dataGrid = renderGrid(gridObj);
}

function initMarker(marker, point, driver, opts) {
	var htmlArr = [];
	htmlArr.push("<div class=\"col-3\">");
	htmlArr.push("<input type='hidden' id= 'selectDriverId' value=" + driver.driverid + "/>");
	htmlArr.push("<img src=\"" + (serviceAddress + "/" + driver.headportraitmax) + "\" width='80px' height = '89px'/>");
	htmlArr.push("</div>");
	htmlArr.push("<div class='col-9'><div style='float:left;'>");
	htmlArr.push("车牌：");
 	htmlArr.push(driver.plateno);
 	htmlArr.push("</div><div style='margin-left:160px;'>");
	htmlArr.push("状态：");
	if(driver.workstatus == "0") {
		htmlArr.push("空闲");
	} else if(driver.workstatus == "1") {
		htmlArr.push("服务中");
	} else if(driver.workstatus == "2") {
		htmlArr.push("下线");
	} else {
		htmlArr.push("");
	}
	htmlArr.push("</div></div></br><div class='col-9'><div style='float:left;'>");
	htmlArr.push("品牌车系：");
	htmlArr.push(driver.brandname);
	htmlArr.push("</div><div style='margin-left:160px;'>");
	htmlArr.push("车型：");
	htmlArr.push(driver.modelname);
	htmlArr.push("</div></div></br><div class='col-9'>");
	htmlArr.push("姓名：");
	htmlArr.push(driver.drivername);
	htmlArr.push("(" + driver.phone + ")");
	htmlArr.push("</div>");
	htmlArr.push("<div class=\"col-12\">");
	htmlArr.push("约：" + Math.ceil(driver.duration / 60) + "分钟到达 总里程为：" + (driver.distance / 1000).toFixed(1) + "公里");
	htmlArr.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
    htmlArr.push("<button type=\"button\" class=\"SSbtn blue\" onclick=\"manualSendOrder('" + driver.driverid + "', '" + driver.modelsid + "', '" + driver.vehicleid + "');\"><i class=\"fa fa-paste\"></i>给TA派单</button>");
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
			pricecopySelect: {required: true, minlength: 1}
		},
		messages: {
			pricecopySelect: {required: "请选择车型计费方式！", minlength: "请选择车型计费方式！"}
		}
	})
}

/**
 * 派单
 * @param {} orderno
 */
function manualSendOrder(driverid, modelsid, vehicleid) {
	$("#driverid").val(driverid);
	$("#modelsid").val(modelsid);
	$("#vehicleid").val(vehicleid);
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
	if(!form.valid()) return;
	
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
		olddriverid: currentOrder.driverid,
		olddrivername: currentOrder.drivername,
		olddriverphone: currentOrder.driverphone,
		newdriverid: $("#driverid").val(),
		orderreason: $("#orderreasonTextarea").val(),
		pricecopy: $("#pricecopySelect").val(),
		orderno: orderObj.orderno,
		modelsid: $("#modelsid").val(),
		vehicleid: $("#vehicleid").val(),
		usetype: "1"
	}
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "OrderManage/ChangeOrgDriver",
		data: JSON.stringify(formData),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
				$("#manualSendOrderFormDiv").hide();
                var tmp = $("#tmp").val();
                var url = $("#baseUrl").val() + "OrderManage/PersonCurrentOrderIndex";
                if(null != tmp && "tmp" == tmp) {
                    url = $("#baseUrl").val() + "TmpOrderManage/PersonCurrentOrderIndex";
                }
            	toastr.options.onHidden = function() {
            		window.location.href = url;
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
		driverid: $("#driverid").val(),
		orderreason: $("#orderreasonTextarea").val(),
		pricecopy: $("#pricecopySelect").val(),
		orderno: orderObj.orderno,
		factmodel: $("#modelsid").val(),
		vehicleid: $("#vehicleid").val(),
		usetype: "1"
	}
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "OrderManage/ManualSendOrder",
		data: JSON.stringify(formData),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
				$("#manualSendOrderFormDiv").hide();
            	toastr.options.onHidden = function() {
            		window.location.href = $("#baseUrl").val() + "OrderManage/PersonOrderIndex";
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
}


function initEvent() {
	document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
         if(e && e.keyCode==13){ // enter 键
        	 driverInfoChange();
        }
    }; 
}

/**
 * 初始化查询条件
 */
function initSearch() {
	$("#cxselect").val("");
	$("#sjselect").val("0");
	$("#jlselect").val("");
	$("#driverName").val("");
	driverInfoChange();
}

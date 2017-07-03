/**
 * 页面默认加载处理
 */
$(document).ready(function() {
    initOrder();
});

/**
 * 初始化订单数据
 */
function initOrder() {
	var data = {orderno: orderObj.orderno};
	$.get($("#baseUrl").val() + "OrderManage/GetOpOrderByOrderno?datetime=" + new Date().getTime(), data, function (result) {
		if (result) {
			renderPageByOrder(result);
		} else {
			
		}
	});
}

/**
 * 根据订单数据渲染界面
 * @param {} order
 */
function renderPageByOrder(order) {
	var ordertype = order.ordertype;
	switch(ordertype) {
		case "1": ordertype = "约车"; break;
		case "2": ordertype = "接机"; break;
		case "3": ordertype = "送机"; break;
		default: ordertype = ""; break; 
	}
	
	var ordersource = order.orderno.substring(0, 2);
	switch(ordersource) {
		case "CG": ordersource = "乘客端 | 个人"; break;
		case "CY": ordersource = "运营端"; break;
		default: ordersource = "/"; break; 
	}
	
	var orderstatus = order.orderstatus;
	switch(orderstatus) {
		case "0": 
		case "1": orderstatus = "待接单"; break;
		case "2": orderstatus = "待出发"; break;
		case "3": orderstatus = "已出发"; break;
		case "4": orderstatus = "已抵达"; break;
		case "5": orderstatus = "接到乘客"; break;
		case "6": orderstatus = "服务中"; break;
		case "7": orderstatus = "行程结束"; break;
		case "8": orderstatus = "已取消"; break;
		default: orderstatus = ""; break; 
	
	}
	
	$("#ddxx").text("订单基本信息，订单号：" + order.orderno);
	//下单人信息
	if(null == order.nickname || "" == order.nickname) {
		$("#xdrxx").text(order.account);
	} else {
		$("#xdrxx").text(order.nickname + " " + order.account);
	}
	//订单类型
	$("#ddlx").text(ordertype);
	//用车时间
	$("#ycsj").text(timeStamp2String(order.usetime));
	//下单人信息
	if(null == order.passengers || "" == order.passengers) {
		$("#ccrxx").text(order.passengerphone);
	} else {
		$("#ccrxx").text(order.passengers + " " + order.passengerphone);
	}
	//上车地址
	var oncity = "(" + order.oncityName + ")" + order.onaddress
	$("#scdz").text(limitLength(oncity));
	$("#scdz").attr("title", oncity);
	//下车地址
	var offcity = "(" + order.offcityName + ")" + order.offaddress
	$("#xcdz").text(limitLength(offcity));
	$("#xcdz").attr("title", offcity);
	//下单车型
	$("#xdcx").text(order.selectedmodelname);
	//下单时间
	$("#xdsj").text(timeStamp2String(order.undertime));
	//下单来源
	$("#xdly").text(ordersource);
	
	//只有送机才需要显示航班号和起飞时间
	if(order.ordertype == "2") {
		$("#hbh").text(order.fltno);
		$("#jlsj").text(timeStamp2String(order.falltime));
	} else {
		$("#hbhtr").remove();
	}
}

/**
 * 显示长度限制
 * @param addr
 */
function limitLength(text) {
	if(null != text && text.length > 18) {
		return text.substr(0, 18) + "...";
	}
	return text;
}

/**
 * 人工派单
 */
function manualSendOrder() {
	window.location.href = $("#baseUrl").val() + "OrderManage/ManualSendOrderIndex?orderno=" + orderObj.orderno + "&type=1";
}

/**
 * 返回
 */
function goback() {
	window.location.href = $("#baseUrl").val() + "OrderManage/OrderIndex";
}

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
	$.get($("#baseUrl").val() + "OrderManage/GetOrgOrderByOrderno?datetime=" + new Date().getTime(), data, function (result) {
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
		case "0":
		case "1": ordertype = "约车"; break;
		case "2": ordertype = "接机"; break;
		case "3": ordertype = "送机"; break;
		default: ordertype = ""; break; 
	}
	
	var paymethod = order.paymethod;
	switch(paymethod) {
		case "0": paymethod = "个人支付"; break;
		case "1": paymethod = "个人垫付"; break;
		case "2": paymethod = "机构支付"; break;
		default: paymethod = ""; break; 
	}
	
	var ordersource = order.orderno.substring(0, 2);
	if(ordersource == "BC") {
		ordersource = "乘客端 | 因公";
	} else if(ordersource == "BZ") {
		ordersource = "租赁端 | 因公";
	} else if(ordersource == "BJ") {
		ordersource = "机构端";
	} else {
		ordersource = "/";
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
	
	if(null == order.nickname || "" == order.nickname) {
		$("#xdrxx").text(order.account);
	} else {
		$("#xdrxx").text(order.nickname + " " + order.account);
	}
	$("#ddlx").text(ordertype);
	$("#ycsj").text(timeStamp2String(order.usetime));
	
	if(null == order.passengers || "" == order.passengers) {
		$("#ccrxx").text(order.passengerphone);
	} else {
		$("#ccrxx").text(order.passengers + " " + order.passengerphone);
	}
	
	var oncity = "(" + order.oncityName + ")" + order.onaddress;
	$("#scdz").text(limitLength(oncity));
	$("#scdz").attr("title", oncity);
	
	var offcity = "(" + order.offcityName + ")" + order.offaddress;
	$("#xcdz").text(limitLength(offcity));
	$("#xcdz").attr("title", offcity);
	
	$("#xdcx").text(order.selectedmodelname);
	$("#xdsj").text(timeStamp2String(order.undertime));
	$("#zffs").text(paymethod);
	
	$("#ycsy").text(order.vehiclessubject);
	$("#jtxc").text(order.onaddress);
	
	$("#xdly").text(ordersource);
	//只有送机才需要显示航班号和起飞时间
	if(order.ordertype == "2") {
		$("#hbh").text(order.fltno);
		$("#jlsj").text(timeStamp2String(order.falltime));
	} else {
		$("#hbh").remove();
		$("#jlsj").remove();
		$("#hbhtd").remove();
		$("#hbhtitle").remove();
		$("#jlsjtitle").remove();
		$("#xdly").attr("colspan", 6);
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
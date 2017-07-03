var map, flag = true;
var driverChangeDataGrid;
var reviewDataGrid;
var commentDataGrid;

/**
 * 页面默认加载处理
 */
$(document).ready(function() {
    $(".tabmenu>li").click(function() {
        $(this).addClass("on").siblings().removeClass("on");
        var n=$(this).index();
        $(".tabbox>li:eq("+n+")").show().siblings().hide();
        if( n == 3 && flag ){
        	flag = false;
        	reviewRecordDataGridInit();
        }
        
        if(n == 4) {
        	commentDataGrid.fnSearch({});
        }
    });
	
    initOrder();
    rangeNameInit();
    artificialOrderRecord();
    changeDriverRecorddataGridInit();
    
    ordercomment();
    validateForm();
});

/**
 * 时间栅栏初始化
 */
function rangeNameInit() {
	$("#rangeName").ionRangeSlider({
		min: 0,
		max: 1000,
		from: 100,
		to: 100,
		step: 100,
		grid: true,
		grid_snap: true,
		onChange: function (obj) {
			
		}, 
		onFinish: function (obj) {
			$("#rangeFrom").val(obj.from);
		}
	});
}

/**
 * 初始化订单数据
 */
function initOrder() {
	var data = {orderno: orderObj.orderno};
	$.get($("#baseUrl").val() + "OrderManage/GetOpOrderByOrderno?datetime=" + new Date().getTime(), data, function (result) {
		if (result) {
			initTraceData(result);
			renderPageByOrder(result);
			initFirstOrder(result);
		} else {
			
		}
	});
}


/**
 * 初始化订单数据
 */
function initTraceData(order) {
	var startDate = order.starttime;
	if(null == startDate || "" == startDate) {
		bindMap(null, order);
		return;
	}
    var data = {
        orderno: order.orderno,
        ordertype: order.ordertype,
        usetype: order.usetype
    };
	$.get($("#baseUrl").val() + "OrderManage/GetOpOrderTraceData?datetime=" + new Date().getTime(), data, function (result) {
		if (result && result.status =="0" && "1" != result.isInterface) {
			bindMap(result, order);
			bindGps(result, order);
		} else {
			bindMap(null, order);
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
		case "0": orderstatus = "待接单"; break;
		case "1": orderstatus = "待人工派单"; break;
		case "2": orderstatus = "待出发"; break;
		case "3": orderstatus = "已出发"; break;
		case "4": orderstatus = "已抵达"; break;
		case "5": orderstatus = "接到乘客"; break;
		case "6": orderstatus = "服务中"; break;
		case "7": orderstatus = "行程结束"; break;
		case "8": orderstatus = "已取消"; break;
		default: orderstatus = ""; break; 
	}
	
	var paymentstatus = order.paymentstatus;
	switch(paymentstatus) {
		case "0": paymentstatus = "未支付"; break;
		case "1": paymentstatus = "已支付"; break;
		case "2": paymentstatus = "结算中"; break;
		case "3": paymentstatus = "已结算"; break;
		default: paymentstatus = ""; break; 
	}
	
	$("#ddxx").text(ordertype + "订单基本信息，订单号：" + order.orderno);
	
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
	//乘车人信息
	if(null == order.passengers || "" == order.passengers) {
		$("#ccrxx").text(order.passengerphone);
	} else {
		$("#ccrxx").text(order.passengers + " " + order.passengerphone);
	}
	//上车地址
	var onaddress = "(" + order.oncityName + ")" + order.onaddress;
	if(null == order.oncityName || "" == order.oncityName) {
		onaddress = order.onaddress;
	}
	$("#scdz").text(limitLength(onaddress));
	$("#scdz").attr("title", onaddress);
	//下车地址
	var offaddress = "(" + order.offcityName + ")" + order.offaddress;
	if(null == order.offcityName || "" == order.offcityName) {
		offaddress = order.offaddress;
	}
	$("#xcdz").text(limitLength(offaddress));
	$("#xcdz").attr("title", offaddress);
	//下单车型
	$("#xdcx").text(order.selectedmodelname);
	//下单时间
	$("#xdsj").text(timeStamp2String(order.undertime));
	//下单来源
	$("#xdly").text(ordersource);
	//只有接机才需要显示航班号和降落时间
	if(order.ordertype != "2") {
		$("#hbh").remove();
		$("#qfsj").remove();
		$("#hbhtd").remove();
		$("#hbhtitle").remove();
		$("#jlsjtitle").remove();
        // $("#gxsjtd").attr("colspan", 5);
        $("#fwcqtd").attr("colspan", 5);
	} else {
		//航班号
		$("#hbh").text(order.fltno);
		//起飞时间
		$("#qfsj").text(order.falltime);
	}
	//计费车型
	$("#jfcx").text(order.pricemodelname);
	//更新时间
    // $("#gxsj").text(timeStamp2String(order.updatetime));
    // 服务车企
    $("#fwcqtd").text(order.belongleasecompanytext);
	//订单状态
	$("#ddzt").text(orderstatus);
	//司机信息
	if(null == order.drivername && null == order.driverphone) {
		$("#sjxx").text("");
	} else {
		$("#sjxx").text(order.drivername + " " + order.driverphone);
	}
	//车牌号
	if(null == order.plateno) {
		$("#cph").text("");
	} else {
		$("#cph").text(order.plateno);
	}
	//实际车型
	$("#sjcx").text(order.factmodelname);
	//支付状态
	$("#zfzt").text(paymentstatus);
	//取消订单隐藏相应单元格
	if(order.orderstatus == "8") {
		$("#cxtr").hide();
		$("#qxtr").show();
		$("#qxsj").text(timeStamp2String(order.canceltime));
		var cancelparty = order.cancelparty;
		switch(cancelparty) {
			case "0": cancelparty = "客服";break;
			case "1": cancelparty = "客服";break;
			case "2": cancelparty = "客服";break;
			case "3": cancelparty = "下单人";break;
			case "4": cancelparty = "系统";break;
			default: cancelparty = "---"; break; 
		}
		$("#qxr").text(cancelparty);
	} else {
		$("#cxtr").show();
		$("#qxtr").hide();
	}
	
	var startprice = order.startprice;
	var timeprice = order.timeprice;
	var rangeprice = order.rangeprice;
	var timetype = order.timetype;
	var slowtimes = order.slowtimes;
	if(null != order.reviewcounttimes) {
		slowtimes = order.reviewcounttimes;
	}
	//服务时长
	var times = order.times;
	if(null != order.reviewtimes) {
		times = Math.ceil(order.reviewtimes/60);
	}
	//服务价格
	var mileage = order.mileage;
	if(null != order.reviewmileage) {
		mileage = (order.reviewmileage/1000).toFixed(1);
	}
	var showtime = 0;
	if(timetype == 0) {
		showtime = times;
	} else if(timetype == 1) {
		showtime = slowtimes;
	}
	var rangecost = (mileage * rangeprice).toFixed(1);
	var timecost = (showtime * timeprice).toFixed(1);
	//服务开始后的状态才需要显示实际金额、实际里程费、实时时间补贴
	if(order.orderstatus < 6 || order.orderstatus == 8) {
		$(".sjtitle").remove();
		$("#zfzttd").attr("colspan", 3);
	} else {
		if(order.orderstatus == 7) {
			$("#ssjetd").text("订单金额");
			$("#sslcftd").text("里程费");
			$("#sssjbttd").text("时长费");
		} else {
			$("#ssjetd").text("实时金额");
			$("#sslcftd").text("实时里程费");
			$("#sssjbttd").text("实时时长费");
		}

        // 起步价
        $("#qbj").text(order.startprice == 0 ? "￥0.0" : "￥" + order.startprice);
        // 空驶费
        $("#ksf").text(order.deadheadcost == 0 ? "￥0.0" : "￥" + order.deadheadcost);
        // 夜间费
        $("#yjf").text(order.nightcost == 0 ? "￥0.0" : "￥" + order.nightcost);
		//实时里程费
		$("#sslcf").text("￥" + rangecost + "(" + mileage + "公里*" + rangeprice + "元/公里)");
		//实时时间补贴
		$("#sssjbt").text("￥" + timecost + "(" + showtime + "分钟*" + timeprice + "元/分钟)");
		//实时金额
		var cost = order.cost;
		if(order.orderstatus == 7) {
			$("#ssje").text("￥" + order.shouldpayamount);
		} else {
			$("#ssje").text("￥" + cost);
		}
	}
	
	//下单时间
	var xdsjHtmlArr = [];
	xdsjHtmlArr.push("下单时间：");
	xdsjHtmlArr.push(formatTimeForDetail(order.undertime));
	xdsjHtmlArr.push("</br>");
	$("#xdsjDiv").html(xdsjHtmlArr.join(""));
	
	//接单时间
	if(order.orderstatus > 1 && order.orderstatus < 8 && order.ordertime) {
		$("#jdsjIcon").removeClass("quan ing");
		$("#jdsjIcon").addClass("quan");
		
		var jdsjHtmlArr = [];
		jdsjHtmlArr.push("接单时间：");
		jdsjHtmlArr.push(formatTimeForDetail(order.ordertime));
		jdsjHtmlArr.push("</br>");
		jdsjHtmlArr.push("车牌：");
		jdsjHtmlArr.push(order.plateno);
		jdsjHtmlArr.push("</br>");
		jdsjHtmlArr.push("品牌车系：");
		jdsjHtmlArr.push(order.vehcbrandname + "." + order.vehclinename);
		jdsjHtmlArr.push("</br>");
		jdsjHtmlArr.push("接单车型：");
		jdsjHtmlArr.push(order.factmodelname);
		jdsjHtmlArr.push("</br>");
		$("#jdsjDiv").html(jdsjHtmlArr.join(""));
	} else {
		$("#jdsjDiv").remove();
	}
	
	//出发时间
	if(order.orderstatus > 2 && order.orderstatus < 8 && order.departuretime) {
		$("#cfsjIcon").removeClass("quan ing");
		$("#cfsjIcon").addClass("quan");
		
		var cfsjHtmlArr = [];
		cfsjHtmlArr.push("出发时间：");
		cfsjHtmlArr.push(formatTimeForDetail(order.departuretime));
		cfsjHtmlArr.push("</br>");
		cfsjHtmlArr.push("车牌：");
		cfsjHtmlArr.push(order.plateno);
		cfsjHtmlArr.push("</br>");
		cfsjHtmlArr.push("品牌车系：");
		cfsjHtmlArr.push(order.vehcbrandname + "." + order.vehclinename);
		cfsjHtmlArr.push("</br>");
		cfsjHtmlArr.push("接单车型：");
		cfsjHtmlArr.push(order.factmodelname);
		cfsjHtmlArr.push("</br>");
		cfsjHtmlArr.push("出发地址：");
		cfsjHtmlArr.push(order.departureaddress);
		cfsjHtmlArr.push("</br>");
		$("#cfsjDiv").html(cfsjHtmlArr.join(""));
	} else {
		$("#cfsjDiv").remove();
	}
	
	//抵达时间
	if(order.orderstatus > 3 && order.orderstatus < 8 && order.arrivaltime) {
		$("#ddsjIcon").removeClass("quan ing");
		$("#ddsjIcon").addClass("quan");
		
		var ddsjHtmlArr = [];
		ddsjHtmlArr.push("抵达时间：");
		ddsjHtmlArr.push(formatTimeForDetail(order.arrivaltime));
		ddsjHtmlArr.push("</br>");
		ddsjHtmlArr.push("抵达地址：");
		ddsjHtmlArr.push(order.arrivaladdress);
		ddsjHtmlArr.push("</br>");
		$("#ddsjDiv").html(ddsjHtmlArr.join(""));
	} else {
		$("#ddsjDiv").remove();
	}
	
	//开始时间
	var starttime = order.starttime;
	if(null != order.reivewstarttime) {
		starttime = order.reivewstarttime;
	}
	if(order.orderstatus > 5 && order.orderstatus < 8 && starttime) {
		$("#kssjIcon").removeClass("quan ing");
		$("#kssjIcon").addClass("quan");
		
		var kssjHtmlArr = [];
		kssjHtmlArr.push("开始时间：");
		kssjHtmlArr.push(formatTimeForDetail(starttime));
		kssjHtmlArr.push("</br>");
		if(order.orderstatus == "6") {
			kssjHtmlArr.push("实时金额：");
		} else {
			kssjHtmlArr.push("订单金额：");
		}
		kssjHtmlArr.push($("#ssje").text());
		kssjHtmlArr.push("</br>");
		kssjHtmlArr.push("里程：");
		kssjHtmlArr.push(mileage + "公里");
		kssjHtmlArr.push("</br>");
		kssjHtmlArr.push("时长：");
		kssjHtmlArr.push(convertMinute(times));
		kssjHtmlArr.push("</br>");
		kssjHtmlArr.push("上车地址：");
		kssjHtmlArr.push(order.startaddress);
		kssjHtmlArr.push("</br>");
		$("#kssjDiv").html(kssjHtmlArr.join(""));
	} else {
		$("#kssjDiv").remove();
	}
	
	//结束时间
	var endtime = order.endtime;
	if(null != order.reviewendtime) {
		endtime = order.reviewendtime;
	}
	if(order.orderstatus == 7 && endtime) {
		$("#jssjIcon").removeClass("quan ing");
		$("#jssjIcon").addClass("quan");
		
		var jssjHtmlArr = [];
		jssjHtmlArr.push("结束时间：");
		jssjHtmlArr.push(formatTimeForDetail(endtime));
		jssjHtmlArr.push("</br>");
		jssjHtmlArr.push("订单金额：");
		jssjHtmlArr.push($("#ssje").text());
		jssjHtmlArr.push("</br>");
		jssjHtmlArr.push("里程费：");
		jssjHtmlArr.push($("#sslcf").text());
		jssjHtmlArr.push("</br>");
		jssjHtmlArr.push("时间补贴：");
		jssjHtmlArr.push($("#sssjbt").text());
		jssjHtmlArr.push("</br>");
		jssjHtmlArr.push("下车地址：");
		jssjHtmlArr.push(order.endaddress);
		jssjHtmlArr.push("</br>");
		$("#jssjDiv").html(jssjHtmlArr.join(""));
	} else {
		$("#jssjDiv").remove();
	}
	
	//完成时间
	if(order.orderstatus == 7 && order.completetime) {
		$("#wcsjIcon").removeClass("quan ing");
		$("#wcsjIcon").addClass("quan");
		
		var jssjHtmlArr = [];
		jssjHtmlArr.push("完成时间：");
		jssjHtmlArr.push(formatTimeForDetail(order.completetime));
		jssjHtmlArr.push("</br>");
		jssjHtmlArr.push("订单金额：");
		jssjHtmlArr.push($("#ssje").text());
		jssjHtmlArr.push("</br>");
		jssjHtmlArr.push("里程费：");
		jssjHtmlArr.push($("#sslcf").text());
		jssjHtmlArr.push("</br>");
		jssjHtmlArr.push("时间补贴：");
		jssjHtmlArr.push($("#sssjbt").text());
		jssjHtmlArr.push("</br>");
		jssjHtmlArr.push("下车地址：");
		jssjHtmlArr.push(order.endaddress);
		jssjHtmlArr.push("</br>");
		$("#wcsjDiv").html(jssjHtmlArr.join(""));
	} else {
		$("#wcsjDiv").remove();
	}
		
}

/**
 * 初始化原始订单数据
 */
function initFirstOrder(order) {
	var data = {orderno: orderObj.orderno};
	$.get($("#baseUrl").val() + "OrderManage/GetFirstOrderByOrderno?datetime=" + new Date().getTime(), data, function (result) {
		var orderamount = 0; //订单金额
		var recheckprice = 0; //差异金额
		var mileage = 0; //服务里程
		var starttime = ""; //服务开始时间
		var endtime = ""; //服务结束时间
		var times = 0; //服务时长
		var slowtimes = 0; //累计时间
		var startprice = 0; //起步价
		var rangeprice = 0; //里程单价
		var timeprice = 0; //时间单价
		var timetype = ""; //时间类型
		if(null != order.originalorderamount) {
			orderamount = order.originalorderamount;
		}
		if (result && result.orderno) {
			mileage = (result.rawmileage/1000).toFixed(1); //服务里程
			var rawstarttime = new Date(); //服务开始时间
			if(null != result.rawstarttime && "" != result.rawstarttime) {
				rawstarttime = new Date(result.rawstarttime);
				starttime = timeStamp2String(result.rawstarttime, "/");
			}
			var rawendtime = new Date(); //服务结束时间
			if(null != result.rawendtime && "" != result.rawendtime) {
				rawendtime = new Date(result.rawendtime);
				endtime = timeStamp2String(result.rawendtime, "/");
			}
			times = Math.ceil((rawendtime - rawstarttime)/1000/60); //服务时长
			slowtimes = result.rawtimes; //累计时间
			if(null != result.pricecopy) {
				var pricecopy = JSON.parse(result.pricecopy);
				startprice = pricecopy.startprice;
				rangeprice = pricecopy.rangeprice;
				timeprice = pricecopy.timeprice;
				timetype = pricecopy.timetype;
			}
		} else {
			mileage = order.mileage; //服务里程
			var orderstarttime = new Date(); //服务开始时间
			if(null != order.starttime && "" != order.starttime) {
				orderstarttime = new Date(order.starttime);
				starttime = timeStamp2String(order.starttime, "/");
			}
			var orderendtime = new Date(); //服务结束时间
			if(null != order.endtime && "" != order.endtime) {
				orderendtime = new Date(order.endtime);
				endtime = timeStamp2String(order.endtime, "/");
			}
			times = Math.ceil((orderendtime - orderstarttime)/1000/60); //服务时长
			if(null != order.pricecopy) {
				var pricecopy = JSON.parse(order.pricecopy);
				slowtimes = pricecopy.slowtimes; //累计时间
				startprice = pricecopy.startprice;
				rangeprice = pricecopy.rangeprice;
				timeprice = pricecopy.timeprice;
				timetype = pricecopy.timetype;
			}
		}
		var subsidytime = 0;
		if(timetype == 0) {
			subsidytime = times;
		} else if(timetype == 1) {
			subsidytime = slowtimes;
		}
		var timecost = (subsidytime * timeprice).toFixed(2); //时间补贴
		var rangecost = (mileage * rangeprice).toFixed(2); //里程费
		if(order.orderstatus == 7) {
			$("#ddje").text(parseFloat(orderamount));
			$("#fwlc").text(mileage);
			$("#fwsc").text(times);
			$("#fwjssj").text(endtime);
			$("#ljsj").text(subsidytime);
			$("#sjbt").text(timecost);
			$("#lcf").text(rangecost);
		} else {
			$("#ddje").text("/");
			$("#fwlc").text("/");
			$("#fwsc").text("/");
			$("#fwjssj").text("/");
			$("#ljsj").text("/");
			$("#sjbt").text("/");
			$("#lcf").text("/");
		}
		if(order.orderstatus == 7 || order.orderstatus == 6) {
			$("#fwkssj").text(starttime);
		} else {
			$("#fwkssj").text("/");
		}
		
		// 小屏幕时此表格过长而导致底部出现横向滚动条问题修改
		if( $(window).width()<=800 ){
			var starttimeStr = '<a title="'+starttime+'">'+starttime+'</a>';
			var endtimeStr = '<a title="'+endtime+'">'+endtime+'</a>';
		}else{
			var starttimeStr = starttime;
			var endtimeStr = endtime;
		}
	});
}
/**
 * 人工派单记录
 */
function artificialOrderRecord() {
	var data = {orderno: orderObj.orderno};
	$.get($("#baseUrl").val() + "OrderManage/GetOpSendOrderRecord?datetime=" + new Date().getTime(), data, function (result) {
		if (result) {
			$("#pdsj").text(result.driverinfo);
			$("#pdtime").text(result.createtime);
			$("#czr").text(result.operatorname);
			$("#rgpdyy").text(limitLength(result.reason));
			$("#rgpdyy").attr("title", result.reason);
		} 
	});
}


/**
 * 界面地图渲染
 * @param {} order
 */
function bindMap(traceData, order) {
	if(null == map) {
		map = new BMap.Map("map_canvas", {enableMapClick:false});
		var point = new BMap.Point(order.onaddrlng, order.onaddrlat);
		map.centerAndZoom(point, 15);
	}
	//展示预估起点、预估终点
	var startpoint = new BMap.Point(order.onaddrlng, order.onaddrlat);
	var icon = new BMap.Icon("img/orgordermanage/icon_shangche.png", new BMap.Size(48, 48));
	var marker = new BMap.Marker(startpoint, { icon: icon });
	map.addOverlay(marker);
	var content = "<p>&nbsp上车地点：" + order.onaddress + "</p>";
	var startinfoWindow = new BMap.InfoWindow(content);
	marker.addEventListener("click", function() {
		map.openInfoWindow(startinfoWindow,startpoint);
	});
	
	var endpoint = new BMap.Point(order.offaddrlng, order.offaddrlat);
	icon = new BMap.Icon("img/orgordermanage/icon_xiache.png", new BMap.Size(48, 48));
	marker = new BMap.Marker(endpoint, { icon: icon });
	map.addOverlay(marker);
	content = "<p>&nbsp下车地点：" + order.offaddress + "</p>";
	var endinfoWindow = new BMap.InfoWindow(content);
	marker.addEventListener("click", function() {
		map.openInfoWindow(endinfoWindow,endpoint);
	});
	
	if(null != traceData && traceData.points.length > 0) {
		var pointsLength = traceData.points.length;
		var point = new BMap.Point(traceData.points[pointsLength - 1].location[0], traceData.points[pointsLength - 1].location[1]);
		map.centerAndZoom(point, 15);
	}
	map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
	map.enableScrollWheelZoom();
}

var points = [];
var car;
var index = 0;
var timer;
var gpsList;

/**
 * 行驶轨迹渲染
 * @param {} gpsList
 * @param {} order
 */
function bindGps(traceData, order) {
	if (traceData == null || traceData == undefined || traceData.points.length == 0) {
        return;
    }
    
   	gpsList = traceData.points;
   	var orderstatus = order.orderstatus;
   	
   	//已出发、已抵达、接到乘客，显示司机当前位置
   	if(orderstatus == "3" || orderstatus == "4" || orderstatus == "5") {
   		var driverPoint = new BMap.Point(gpsList[gpsList.length - 1].location[0], gpsList[gpsList.length - 1].location[1]);
   		var driver = new BMap.Marker(driverPoint, { icon: icon = new BMap.Icon("img/orgordermanage/icon_zaixian.png", iconSize) });
   		map.addOverlay(driver);
   		return;
   	}
   	
   	map.clearOverlays();
   	
   	//服务中:显示实际上车位置、预估下车位置，实时显示司机位置;  服务结束:显示实际上车位置、实际下车位置
    if(orderstatus == "6" || orderstatus == "7") {
	    var startpoint = new BMap.Point(gpsList[0].location[0], gpsList[0].location[1]);
    	var icon = new BMap.Icon("img/orgordermanage/icon_shangche.png", new BMap.Size(48, 48));
    	var marker = new BMap.Marker(startpoint, { icon: icon });
    	map.addOverlay(marker);
    	
    	var content = "<p><font style='font-weight:bold;'>&nbsp服务开始时间：" + timeStamp2String(order.starttime) + "</font></p>";
    	var startinfoWindow = new BMap.InfoWindow(content, {width: 250, height: 40});
		marker.addEventListener("click", function() {
			map.openInfoWindow(startinfoWindow,startpoint);
		});
		map.panTo(startpoint);
		
		var endpoint = new BMap.Point(gpsList[gpsList.length - 1].location[0], gpsList[gpsList.length - 1].location[1]);
		if(orderstatus == "6") {
			icon = new BMap.Icon("img/orgordermanage/icon_zaixian.png", new BMap.Size(48, 48));
			marker = new BMap.Marker(endpoint, { icon: icon });
		} else {
			icon = new BMap.Icon("img/orgordermanage/icon_xiache.png", new BMap.Size(48, 48));
			marker = new BMap.Marker(endpoint, { icon: icon });
			content = "<p><font style='font-weight:bold;'>服务结束时间：" + timeStamp2String(order.endtime) + "</font></p>";
			var endinfoWindow = new BMap.InfoWindow(content, {width: 250, height: 40});
			marker.addEventListener("click", function() {
				map.openInfoWindow(endinfoWindow,endpoint);
			});
		}
		map.addOverlay(marker);
    	
		for (var i = 0; i < gpsList.length; i++) {
			var point = new BMap.Point(gpsList[i].location[0], gpsList[i].location[1]);
			points.push(point);
		}
    }
	var polyline = new BMap.Polyline(points, { strokeColor: "#00FF7F", strokeWeight: 6, strokeOpacity: 0.5 });
	map.addOverlay(polyline);
}

/**
 * 播放
 */
function play() {
	if(null == gpsList || undefined == gpsList) {
		return;
	}
	if(null == car || "" == car) {
		var iconSize = new BMap.Size(48, 48);
		car = new BMap.Marker(points[0], { icon: icon = new BMap.Icon("img/orgordermanage/icon_zaixian.png", iconSize) });
		map.addOverlay(car);
	}
	
	var point = new BMap.Point(gpsList[index].location[0], gpsList[index].location[1]);
	car.setZIndex(0);
	car.setPosition(point);
	
	if (index > 0) {
		var rePoint = new BMap.Point(gpsList[index - 1].location[0], gpsList[index - 1].location[1]);
		map.addOverlay(new BMap.Polyline([rePoint, point], { strokeColor: "#00BFFF", strokeWeight: 6, strokeOpacity: 0.5 }));
	}
	if ($("#follow").is(":checked")) {
		map.panTo(point);
	}
	
	index++;
	
	if (index < points.length) {
		timer = window.setTimeout("play()", parseInt(100000/($("#rangeName").val() + 1)));
	} else {
		index = 0;
		toastr.success("轨迹回放完成", "提示信息");
	}
}

/**
 * 暂停轨迹绘制
 */
function pause() {
	if (timer) {
		window.clearTimeout(timer);
	}
}

/**
 * 重置轨迹绘制
 */
function reset() {
	if (timer) {
		window.clearTimeout(timer);
	}
	
	index = 0;
	play();
}


/**
 * 更换司机记录表格数据初始化
 */
function changeDriverRecorddataGridInit() {
	var gridObj = {
		id: "changeDriverRecorddataGrid",
        sAjaxSource: $("#baseUrl").val() + "OrderManage/GetOpChangeDriverByQuery",
        userQueryParam: [{name: "orderNo", value: orderObj.orderno}],
        language: {
        	sEmptyTable: "暂无司机更换记录"
        },
        columns: [
            {mDataProp: "rownum", sTitle: "序号", sClass: "center", sortable: true },
	        {mDataProp: "beforedriverinfo", sTitle: "更换前司机", sClass: "center", sortable: true },
	        {mDataProp: "afterdriverinfo", sTitle: "更换后司机", sClass: "center", sortable: true },
	        {mDataProp: "reason", sTitle: "更换原因", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
	        		return showToolTips(full.reason, 15);
	        	}
	        },
	        {mDataProp: "changetime", sTitle: "更换时间", sClass: "center", sortable: true },
	        {mDataProp: "operatorname", sTitle: "操作人", sClass: "center", sortable: true }
        ]
    };
    
	driverChangeDataGrid = renderGrid(gridObj);
}

/**
 * 复议记录表格数据初始化
 */
function reviewRecordDataGridInit() {
	var gridObj = {
		id: "reviewRecordDataGrid",
        sAjaxSource: $("#baseUrl").val() + "OrderManage/GetOpOrderReviewByQuery",
        userQueryParam: [{name: "orderNo", value: orderObj.orderno}],
        scrollX: true,
        iLeftColumn: 1,
        language: {
        	sEmptyTable: "暂无任何复核记录"
        },
        columns: [
            {mDataProp: "rownum", sTitle: "序号", sClass: "center", sortable: true },
	        {mDataProp: "reviewedprice", sTitle: "订单金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "price", sTitle: "差异金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "mileage", sTitle: "服务里程(公里)", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
	        		var mileage = (full.mileage/1000).toFixed(1);
	        		if(full.mileage != full.rawmileage) {
	        			return "<span class='font_red'>" + mileage + "</span>";
	        		} else {
	        			return mileage;
	        		}
	        	}
	        },
	        {mDataProp: "times", sTitle: "服务时长(分钟)", sClass: "center", sortable: true,
	        	"mRender": function(data, type, full) {
	        		return Math.ceil(full.times/60);
		        }
	        },
	        {
                "mDataProp": "STARTTIME",
                "sClass": "center",
                "sTitle": "服务开始时间",
                "mRender": function (data, type, full) {
                	if(full.starttime != full.rawstarttime) {
                		return "<span class='font_red'>" + timeStamp2String(full.starttime) + "</span>";
                	}
                	return timeStamp2String(full.starttime);
                }
            },
            {
                "mDataProp": "ENDTIME",
                "sClass": "center",
                "sTitle": "服务结束时间",
                "mRender": function (data, type, full) {
                	if(full.endtime != full.rawendtime) {
                		return "<span class='font_red'>" + timeStamp2String(full.endtime) + "</span>";
                	}
                	return timeStamp2String(full.endtime);
                }
            },
            {mDataProp: "counttimes", sTitle: "计费时长(分钟)", sClass: "center", sortable: true,
            	"mRender": function(data, type, full) {
            		if(null != full.pricecopy) {
            			var pricecopy = JSON.parse(full.pricecopy);
            			var timetype = pricecopy.timetype;
            			if(timetype == 0) { //总用时
            				var times = Math.ceil(full.times/60); //复核后服务时长
            				var rawstarttime = new Date(rawstarttime);
            				var rawendtime = new Date(rawendtime);
            				var rawtimes = Math.ceil((rawendtime - rawstarttime)/1000/60); //复核前服务时长
            				if(times != rawtimes) {
            					return "<span class='font_red'>" + times + "</span>";
            				} else {
            					return times;
            				}
            			} else if(timetype == 1) { //低速用时
            				if(full.counttimes != full.rawtimes) {
                    			return "<span class='font_red'>" + full.counttimes + "</span>";
                    		} else {
                    			return full.counttimes;
                    		}
            			}
            		}
            	}
            },
	        {mDataProp: "timesubsidies", sTitle: "时间补贴(元)", sClass: "center", sortable: true },
	        {mDataProp: "mileageprices", sTitle: "里程费(元)", sClass: "center", sortable: true },
	        {
                "mDataProp": "REVIEWPERSON",
                "sClass": "center",
                "sTitle": "提出复核方",
                "mRender": function (data, type, full) {
					if(full.reviewperson == "1") {
						return "司机";
					} else if(full.reviewperson == "2") {
						return "下单人";
					} else {
						return "/";
					}
            	}
            },
            {mDataProp: "reason", sTitle: "申请原因", sClass: "center", sortable: true,
            	"mRender":function(data, type, full) {
                	return showToolTips(full.reason, 7);
            	}
            },
            {mDataProp: "opinion", sTitle: "处理意见", sClass: "center", sortable: true,
            	"mRender":function(data, type, full) {
                	return showToolTips(full.opinion, 8);
            	}
            },
	        {
                "mDataProp": "REVIEWTIME",
                "sClass": "center",
                "sTitle": "复核时间",
                "mRender": function (data, type, full) {
                	return timeStamp2String(full.reviewtime);
                }
            },
	        {mDataProp: "operatorname", sTitle: "复核人", sClass: "center", sortable: true }
        ]
    };
    
	reviewDataGrid = renderGrid(gridObj);
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
 * 查询客户备注信息
 */
function ordercomment() {
	var gridObj = {
			id: "commentDataGrid",
	        sAjaxSource: $("#baseUrl").val() + "OrderManage/GetOpOrderCommentByQuery",
	        userQueryParam: [{name: "orderno", value: orderObj.orderno}],
	        iDisplayLength: 3,
	        language: {
	        	sEmptyTable: "暂无客服备注记录"
	        },
	        columns: [
 	            {
					mDataProp : "KFBZ",
					sClass : "center",
					sortable : true,
					"mRender" : function(data, type, full) {
						var html = "",
						specialName,
						icon;
					if(full.usertype == 1) {
						specialName = '超管';
					} else {
						specialName = full.rolename;
					}
					if(full.remarktype == 0) {
						icon = 'content/img/icon_fuhe.png';
					} else if(full.remarktype == 1) {
						icon = 'content/img/icon_tousu.png';
					} else if(full.remarktype == 2) {
						icon = 'content/img/icon_qita.png';
					}
					html  = '<div style="text-align:left;position: relative;width:100%;min-height:78px;font-size: 14px;padding:20px 56px;line-height: 1;">'
						+ '<div style="margin-bottom:10px;">'
						+ '<span style="min-width:140px;color:rgb(243,51,51);overflow: hidden;display: inline-block;">'
						+ '<e style="margin-right:10px;float:left;">'+specialName+'</e>'
						+ '<e style="float:left;">'+full.nickname+'</e>'
						+ '</span>'
						+ '<span style="color:rgb(105,105,105)">'+full.createtime+'</span>'
						+ '</div>'
						+ '<div style="color:rgb(26,26,26);line-height: 1.2;">'+full.remark+'</div>'
						+ '<img src="'+icon+'" style="position: absolute;left:0;top:0;"/>'
						+ '</div>';
					return html;
					}
 	            }
	        ]
	    };
	    
	commentDataGrid = renderGrid(gridObj);
}

/**
 * 添加客服备注弹窗
 */
function remark() {
	$("#orderCommentFormDiv").show();
	
	$("input[name=remarktype]").get(0).checked = true;
	$("#remark").val("");
	
	var editForm = $("#orderCommentForm").validate();
	editForm.resetForm();
	editForm.reset();
}

/**
 * 隐藏客服备注弹窗
 */
function canel() {
	$("#orderCommentFormDiv").hide();
}

/**
 * 添加客服备注
 */
function add() {
	var form = $("#orderCommentForm");
	if(!form.valid()) return;
	
	var formData = {
		orderno: orderObj.orderno,
		remarktype: $("input[name='remarktype']:checked").val(),
		remark: $("#remark").val()
	}
	
	$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "OrderManage/AddOpOrderRemark",
		data: JSON.stringify(formData),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
            	toastr.success(message, "提示");
			} else {
            	toastr.error(message, "提示");
			}
			$("#orderCommentFormDiv").hide();
			commentDataGrid.fnSearch({});
		}
	});
}

/**
 * 表单校验
 */
function validateForm() {
	$("#orderCommentForm").validate({
		rules: {
			remark: {
				required : true,
				maxlength : 100
			}
		},
		messages: {
			remark: {
				required: "请输入备注内容",
				minlength: "备注内容最大为100个字符"
			}
		}
	})
}

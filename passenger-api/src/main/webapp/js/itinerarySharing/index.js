var map;
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
	$.get($("#baseUrl").val() + "Passenger/GetOrder?datetime=" + new Date().getTime(), orderObj, function (result) {
		if (result&&result.status==0) {
			initDriverInfo(result.order);
			initTraceData(result.order);
		}else{
			init404Info();
		}
	});
}

function init404Info(){
	$("#404info").show();
	$("#mapinfo").hide();
}

function initDriverInfo(order){
	$("#404info").hide();
	$("#mapinfo").show();
	$("#driverimg").attr("src",order.driverimg);
	if(order.name){
		$("#drivername").html(order.name);
	}
	
	if(order.vehcbrand&&order.vehcline&&order.carnum){
		$("#carinfo").html(order.vehcbrand+""+order.vehcline+"&nbsp;&nbsp;&nbsp;"+order.carnum);
	}
	
	if(order.onaddress){
		$("#onaddress").html(order.onaddress);
	}
	
	if(order.offaddress){
		$("#offaddress").html(order.offaddress);
	}
	
	if(order.starttime){
		$("#onaddresstime").html(order.starttime.substring(11,16)+"上车");
	}
	
	if(order.endtime){
		$("#offaddresstime").html(order.endtime.substring(11,16)+"下车");
	}
}

/**
 * 初始化订单数据
 */
function initTraceData(order) {
	// var startDate = order.starttime;
	// var endDate = order.endtime;
	// if(order.orderstatus < 6) { //如果订单未开始服务，出发时间-当前时间
	// 	startDate = order.departuretime;
	// 	endDate = timeStamp2String(new Date());
	// } else if(order.orderstatus == 6) { //订单服务中，开始时间-当前时间
	// 	startDate = order.starttime;
	// 	endDate = timeStamp2String(new Date());
	// } else { //服务结束，开始时间-结束时间
	// 	startDate = order.starttime;
	// 	endDate = order.endtime;
	// }
	var data = {
		orderno: order.orderno,
        usetype: order.usetype,
        ordertype: order.ordertype
	};
	$.get($("#baseUrl").val() + "Passenger/GetOrderTraceData?datetime=" + new Date().getTime(), data, function (result) {
		if (result && result.status == 0) {
			bindMap(result, order);
			bindGps(result, order);
		} else {
			bindMap(null, order);
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
	var starticon = new BMap.Icon("img/itinerarySharing/icon_shangche.png", new BMap.Size(48, 48));
	var startmarker = new BMap.Marker(startpoint, { icon: starticon });
	map.addOverlay(startmarker);
	var startcontent = "<p>&nbsp上车地点：" + order.onaddress + "</p>";
	var startinfoWindow = new BMap.InfoWindow(startcontent);
    startmarker.addEventListener("click", function() {
		map.openInfoWindow(startinfoWindow,startpoint);
	});
	
	var endpoint = new BMap.Point(order.offaddrlng, order.offaddrlat);
	var endicon = new BMap.Icon("img/itinerarySharing/icon_xiache.png", new BMap.Size(48, 48));
	var endmarker = new BMap.Marker(endpoint, { icon: endicon });
	map.addOverlay(endmarker);
	var endcontent = "<p>&nbsp下车地点：" + order.offaddress + "</p>";
	var endinfoWindow = new BMap.InfoWindow(endcontent);
    endmarker.addEventListener("click", function() {
		map.openInfoWindow(endinfoWindow,endpoint);
	});
	
	if(null != traceData && traceData.points.length > 0) {
		var pointsLength = traceData.points.length;
		var point = new BMap.Point(traceData.points[pointsLength - 1].location[0], traceData.points[pointsLength - 1].location[1]);
		map.centerAndZoom(point, 15);
	}
	var opts = {anchor:BMAP_ANCHOR_BOTTOM_RIGHT}   
	map.addControl(new BMap.NavigationControl(opts));  //添加默认缩放平移控件
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
   	//清除标记
   	map.clearOverlays();
   	//上车坐标
    var startpoint = new BMap.Point(order.onaddrlng,order.onaddrlat);
	var shangcheicon = new BMap.Icon("img/itinerarySharing/icon_shangche.png", new BMap.Size(48, 48));
	var startmarker = new BMap.Marker(startpoint, { icon: shangcheicon });
	map.addOverlay(startmarker);
	//下次坐标
	var endpoint = new BMap.Point(order.offaddrlng, order.offaddrlat);
	var xiacheicon = new BMap.Icon("img/itinerarySharing/icon_xiache.png", new BMap.Size(48, 48));
	var endmarker = new BMap.Marker(endpoint, { icon: xiacheicon });
	map.addOverlay(endmarker);

   	//显示司机当前位置
   	if(orderstatus == "3" || orderstatus == "4" || orderstatus == "5"|| orderstatus == "6"|| orderstatus == "9") {
   		if(order.driverposition){
   			var driverPoint = new BMap.Point(order.driverposition.lng, order.driverposition.lat);
            var driver = new BMap.Marker(driverPoint, { icon: icon = new BMap.Icon("img/itinerarySharing/icon_zaixian.png", new BMap.Size(48, 48)) });
   			if(order.ordertype=="4"){
   	   			//出租车
                driver = new BMap.Marker(driverPoint, { icon: icon = new BMap.Icon("img/itinerarySharing/icon_hc2@2x.png", new BMap.Size(48, 48)) });
			}
   	   		map.addOverlay(driver);
   		}
   	}
   	var geoc = new BMap.Geocoder();
   	//开始坐标可以点击显示时间
   	if(orderstatus == "6"||orderstatus == "9" || orderstatus == "7"){
    	var content = "<p><font style='font-weight:bold;'>&nbsp服务开始时间：" + order.starttime + "</font></p>";
    	var startinfoWindow = new BMap.InfoWindow(content, {width: 250, height: 40});
    	startmarker.addEventListener("click", function() {
			map.openInfoWindow(startinfoWindow,startpoint);
		});
		map.panTo(startpoint);
		//解析上车地址
		geoc.getLocation(startpoint, function(rs) {
			var addComp = rs.addressComponents;
			var actualOnaddress = addComp.city + addComp.district + addComp.street + addComp.streetNumber;
			$("#actualOnaddress").text(actualOnaddress);
		});
   	}
   	//结束时间可以点击显示结束时间
   	if(orderstatus == "7"){
		var content = "<p><font style='font-weight:bold;'>服务结束时间：" + order.endtime + "</font></p>";
		var endinfoWindow = new BMap.InfoWindow(content, {width: 250, height: 40});
		endmarker.addEventListener("click", function() {
			map.openInfoWindow(endinfoWindow,endpoint);
		});
		map.addOverlay(endmarker);
		//解析下车地址
		geoc.getLocation(endpoint, function(rs) {
			var addComp = rs.addressComponents;
			var actualOffaddress = addComp.city + addComp.district + addComp.street + addComp.streetNumber;
			$("#actualOffaddress1").text(actualOffaddress);
			$("#actualOffaddress2").text(actualOffaddress);
		});
   	}
   	
   	//服务中:显示实际上车位置、预估下车位置，实时显示司机位置;  服务结束:显示实际上车位置、实际下车位置
    if(orderstatus == "6"||orderstatus == "9" || orderstatus == "7") {
		for (var i = 0; i < gpsList.length; i++) {
			var point = new BMap.Point(gpsList[i].location[0], gpsList[i].location[1]);
			points.push(point);
		}
		var polyline = new BMap.Polyline(points, { strokeColor: "#00FF7F", strokeWeight: 6, strokeOpacity: 0.5 });
		map.addOverlay(polyline);
    }
}
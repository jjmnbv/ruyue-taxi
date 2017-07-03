var map;
var interval;
var driverChangeDataGrid;
var reviewDataGrid;
var commentDataGrid;
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
			initTraceData(result);
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
	$.get($("#baseUrl").val() + "OrderManage/GetGpsTraceData?datetime=" + new Date().getTime(), data, function (result) {
		if (result) {
			bindMap(result.app, order);
			bindGps(result.app, order);
            bindObdGps(result.obd, order);
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

function bindObdGps(traceData, order) {
    if (traceData == null || traceData == undefined || traceData.points.length == 0) {
        return;
    }

    gpsList = traceData.points;
    var orderstatus = order.orderstatus;

    //已出发、已抵达、接到乘客，显示司机当前位置
    if(orderstatus == "3" || orderstatus == "4" || orderstatus == "5") {
        return;
    }

    //服务中:显示实际上车位置、预估下车位置，实时显示司机位置;  服务结束:显示实际上车位置、实际下车位置
    if(orderstatus == "6" || orderstatus == "7") {
        points = [];
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

    var polyline = new BMap.Polyline(points, { strokeColor: "#1C86EE", strokeWeight: 6, strokeOpacity: 0.5 });
    map.addOverlay(polyline);
}

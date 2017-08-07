var map;
var gc = new BMap.Geocoder();
var vehcList = null;
var count = 0;
var marker;
var currentZoomType = 0;
var vehcMapList = [];
var isFrom = 1;// 标识是1:查询，2：运行停止离线，3：显示车牌，4：拖动，5缩放，6行点击
var spRunCount = 0;
var spStopCount = 0;
var spOffLineCount = 0;
var isGoCount;
var deleteVehcIndex = [];
var vehctableList = [];
var params = {             //好约车参数
		isNullName:false,
		checkSwitch:true,
		isNewWord:false,
		passengers:null,
		passengerPhone:null,
		text:null,
		userid:null,
		organid:null,
		airports:null,
		cities:null,
		companies:null,
		onLat : 0,
		onLng : 0,
		onAddress: null,
		offLat : 0,
		offLng : 0,
		offAddress: null,
		cartypes:null,
		cartypeindex:0,
		cartypemin:4      //车型最小显示个数
	};
$(function() {
	$(".checkbox-list").css("visibility", "visible");
	bindControl();
	bindMap(); // 绑定地图行驶轨迹
	$("#btnSearch").click(function() {
		currentZoomType = map.getZoom();
		getVehcList();
	});
});

function bindControl() {  
	$("#selPlates").select2({
		placeholder : "车牌",
		minimumInputLength : 1,
		allowClear : true,
		ajax : {
			url : 'Location/getVehclineByQuery',
			dataType : 'json',
			data : function(term, page) {
				return {
					plate : term,
					apikey:apikey
				};
			},
			results: function (data, page) {
				var eqpList=[];
				if(data!=null){
					for(var i=0;i<data.vhecEqpList.length;i++){
						var person=new Object(); 
						person.id=data.vhecEqpList[i].eqpId;  
						person.text=data.vhecEqpList[i].plate +"|"+data.vhecEqpList[i].imei;
						eqpList.push(person);
					}
				}
                return { results: eqpList };
              }
		}
	});
	// 绑定显示状态改变事件
	$("#cbVehcStateRun").click(function() {
		isFrom = 2;// 标识是1:查询，2：运行停止离线，3：显示车牌，4：拖动，5缩放，6行点击
		isGoCount = false;
		map.clearOverlays();
		bindVehcMarker();
		if ($(this).is(":checked")) {
			$("#spRunCount").text(spRunCount);
		} else {
			$("#spRunCount").text(0);
		}
	});
	$("#cbVehcStateStop").click(function() {
		isFrom = 2;// 标识是1:查询，2：运行停止离线，3：显示车牌，4：拖动，5缩放，6行点击
		isGoCount = false;
		map.clearOverlays();
		bindVehcMarker();

		if ($(this).is(":checked")) {
			$("#spStopCount").text(spStopCount);
		} else {
			$("#spStopCount").text(0);
		}

	});
	$("#cbVehcStateOffLine").click(function() {
		isFrom = 2;// 标识是1:查询，2：运行停止离线，3：显示车牌，4：拖动，5缩放，6行点击
		isGoCount = false;
		map.clearOverlays();
		bindVehcMarker();
		if ($(this).is(":checked")) {
			$("#spOffLineCount").text(spOffLineCount);
		} else {
			$("#spOffLineCount").text(0);
		}

	});
	$("#cbVehcPlates").click(function() {
		map.clearOverlays();
		isGoCount = false;
		isFrom = 3;// 标识是1:查询，2：运行停止离线，3：显示车牌，4：拖动，5缩放，6行点击
		bindVehcMarker();
	});
}

// 绑定地图行驶轨迹
function bindMap() {
	map = new BMap.Map("map_canvas");
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);
	map.addControl(new BMap.NavigationControl()); // 添加默认缩放平移控件
	map.enableScrollWheelZoom();

	map.addEventListener("dragend", function() {
		isFrom = 4;// /标识是1:查询，2：运行停止离线，3：显示车牌，4：拖动，5缩放，6行点击
		SetVehcMap();
	});// 注册地图拖动完成时间 拖动从全局变量中获得新地区未加载的店，画到地图中

	map.addEventListener("zoomend", function() {// 朱迪地图缩放事件
		// 当前缩放度比之前小则从全局变量中获得新地区未加载的店，画到地图中

		if (parseInt(currentZoomType) >= parseInt(map.getZoom())) {
			currentZoomType = map.getZoom();
			isFrom = 5;// 标识是1:查询，2：运行停止离线，3：显示车牌，4：拖动，5缩放，6行点击
			SetVehcMap();
		}
	});

	map.centerAndZoom(new BMap.Point(116.404, 39.915), 12);
	var ctrl = new BMapLib.TrafficControl({
		showPanel : false
	// 是否显示路况提示面板
	});
	map.addControl(ctrl);
	ctrl.setAnchor(BMAP_ANCHOR_BOTTOM_RIGHT);

	map.centerAndZoom(new BMap.Point(120.305456, 31.570037), 12);
	map.enableScrollWheelZoom(true);
	var stCtrl = new BMap.PanoramaControl(); // 构造全景控件
	stCtrl.setOffset(new BMap.Size(20, 20));
	map.addControl(stCtrl);// 添加全景控件
}

// 从全局变量中获得新地区未加载的店，画到地图中
function SetVehcMap() {
	if ($(".combotree-chosen").text() != "请选择") {
		if (vehcList != null && vehcList != undefined) {
			if (vehcList.length > 0) {
				bindVehcMarker();
			}
		}
	}

}

// 关闭加载度
function fnLoading() {
	_loading.hide();
}
function myFun(result) {
	var cityName = result.name;
	map.centerAndZoom(cityName, 12);
}
// 绑定车辆地图标记
var point = null;
// 绑定倒计时
var setTimeoutId = 0;
var seconds = 300;
function initWork() {
	if (vehcList != null && vehcList != undefined) {
		if (vehcList.length > 0) {
			if (seconds == 0) {
				seconds = 300;
				bindVehcMarker();
			} else {
				seconds -= 1;
			}
			setTimeoutId = setTimeout(initWork, 300000);
		} else {
			clearTimeout(setTimeoutId);
			seconds = 0
		}
	}
}

function bindVehcMarker() {
	// map.clearOverlays();
	if (vehcList == null || vehcList == undefined) {
		return;
	}
	// 是否显示
	var isShowRun = $("#cbVehcStateRun").prop("checked");// 运行状态
	var isShowStop = $("#cbVehcStateStop").prop("checked");// 停止状态
	var isShowOffLine = $("#cbVehcStateOffLine").prop("checked");// 离线状态
	var isShowPlates = $("#cbVehcPlates").prop('checked'); // 是否显示车牌
	var html = "";
	// 获取地图显示范围
	var bs = map.getBounds(); // 获取可视区域
	var bssw = bs.getSouthWest(); // 可视区域左下角
	var bsne = bs.getNorthEast(); // 可视区域右上角
	if (isFrom == 1 || isFrom == 2 || isFrom == 3)// 标识是1:查询，2：运行停止离线，3：显示车牌，4：拖动，5缩放，6行点击
	{
		map.clearOverlays();
		if (isFrom != 1) {
			vehcList = $.unique(vehcList.concat(vehcMapList));
		}
		vehcMapList = [];
	}
	for (var i = 0; i < vehcList.length; i++) {
		vehctableList.push(vehcList[i])
		if (vehcList[i].longitude == 0 || vehcList[i].latitude == 0) {
			continue;
		}
		// 如果不显示运行状态的车辆，则不添加车辆地图标记
		if (vehcList[i].workStatus == "在线" && !isShowRun) {
			continue;
		}
		// 如果不显示停止状态的车辆，则不添加车辆地图标记
		if (vehcList[i].workStatus == "断线" && !isShowStop) {
			continue;
		}
		// 如果不显示离线状态的车辆，则不添加车辆地图标记
		if (vehcList[i].workStatus == "离线" && !isShowOffLine) {
			continue;
		}
		var contentObj = {};
		// 在显示区域内的点才加载
		if (vehcList[i].longitude >= bssw.lng && vehcList[i].longitude <= bsne.lng
				|| vehcList[i].latitude >= bssw.lat
				&& vehcList[i].latitude <= bsne.lat) {
			// 将描绘的点存好。拖动及缩放时不再执行
			if (vehcMapList == null || vehcMapList == undefined
					|| vehcMapList.length == 0) {
				vehcMapList.push(vehcList[i]);
				deleteVehcIndex.push(vehcList[i]);
			} else {
				if ($.inArray(vehcList[i], vehcMapList) > -1) {
					continue;
				} else {
					vehcMapList.push(vehcList[i]);
					deleteVehcIndex.push(vehcList[i]);
				}
			}
			content = setMap(vehcList[i]);
			contentObj = {
				content : content
			};

		} else {
			contentObj = {};
		}

		// 当不是点击查询，离线，运行，停止时执行表格逻辑//标识是1:查询，2：运行停止离线，3：显示车牌，4：拖动，5缩放，6行点击
		if (isFrom != 2 && isFrom != 1) {
			continue;
		}
		
		// 加载车辆列表
		if (i == vehcList.length - 1) {
			html += "<li class=\"last-line\" ><div class=\"vehc-icon\">";
		} else {
			html += "<li><div class=\"vehc-icon\">";
		}
		var status = "";
		if (vehcList[i].workStatus == "在线") {
			html += "<img src=\"" + basePath +"img/trafficflux/run.png\" /></div><div class=\"vehc-title run\">";
			if (vehcList[i].speed > 0) {
				status = "运行";
			}
			if (vehcList[i].speed == null || vehcList[i].speed == 0) {
				status = "运行/怠速";
			}
		} else if (vehcList[i].workStatus == "断线") {
			html += "<img src=\"" + basePath +"img/trafficflux/stop.png\" /></div><div class=\"vehc-title stop\">";
			status = "停止";
		} else {
			html += "<img src=\"" + basePath +"img/trafficflux/offline.png\" /></div><div class=\"vehc-title offline\">";
			status = "离线";
		}

		html += "<input type=\"hidden\" class=\"indexNum\" value=\"" + i + "\"/>";
		html += "<input type=\"hidden\" class=\"content\" value=\"" + contentObj.content + "\"/>";
		html += "<input type=\"hidden\" class=\"longitude\" value=\"" + vehcList[i].longitude + "\"/>";
		html += "<input type=\"hidden\" class=\"latitude\" value=\"" + vehcList[i].latitude + "\"/>";
		html += "<p><span class=\"vehc-title-sp\">IMEI："+ vehcList[i].imei + "</span></p>";
		html += "<p style=\"text-indent:2em\"><span class=\"vehc-title-sp\">" + vehcList[i].plate + "</span>";
		html += "<span class=\"vehc-status\">" + status + "</span></p></div> </li>";
		// class=\"label label-sm label-success\"
		
	}
	if (deleteVehcIndex.length > 0) {
		for (var deleteIndex = 0; deleteIndex < deleteVehcIndex.length; deleteIndex++) {
			vehcList.splice($.inArray(deleteVehcIndex[deleteIndex], vehcList),0);
		}
		deleteVehcIndex = [];
	}
	// 标识是1:查询，2：运行停止离线，3：显示车牌，4：拖动，5缩放，6行点击
	if (isFrom == 1 || isFrom == 2) {
		$(".vehc-list").html(html);
		$(".vehc-list li").on("click",function() {
			$(".vehc-list li").css("background-color", "");
			$(this).css("background-color", "#e9e9e9");
			var long = $(this).find(".longitude").val();
			var lat = $(this).find(".latitude").val();
			var content = $(this).find(".content").val();
			var address = $(this).find(".address").val();
			var point = new BMap.Point(long, lat);
			map.panTo(point);
			isFrom = 6;// 标识是1:查询，2：运行停止离线，3：显示车牌，4：拖动，5缩放，6行点击
			isGoCount = false;
			SetVehcMap();// 点击表格行，转到地图点附近时，加载当前视图区域未画到地图上的点
			var mapindex = $(this).find(".indexNum").val();// 表格中有部分点conten为空，所以此处根据存的索引以及车辆id给该行赋值
			if (content == null || content == undefined
					|| content == "undefined") {
				content = setMap(vehctableList[mapindex]);
				$(this).find(".content").val(content);
			}
			var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象
			map.openInfoWindow(infoWindow, point); // 开启信息窗口
			gc.getLocation(point, function(rs) {
				content = content.replace("未解析地址", rs.address);
				infoWindow.setContent(content);
				address = rs.address;
			});
		});
	}
	// 因为时间太短不出现，所以此处延迟关闭
	setInterval("fnLoading()", 2000);
}

var opts = {
	width : 355, // 信息窗口宽度
	height : 200, // 信息窗口高度
	enableMessage : false,
	offset : new BMap.Size(0, -20)
}

function openInfo(contentObj, e) {
	var p = e.target;
	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
	var infoWindow = new BMap.InfoWindow(contentObj.content, opts); // 创建信息窗口对象
	map.openInfoWindow(infoWindow, point); // 开启信息窗口
	if (!contentObj.address) {
		gc.getLocation(point, function(rs) {
			contentObj.content = contentObj.content
					.replace("未解析地址", rs.address);
			infoWindow.setContent(contentObj.content);
			contentObj.address = rs.address;
		});
	}
}

// 地图描点方法，提取出来，为了重用
function setMap(vehcmap) {
	// 是否显示
	var isShowRun = $("#cbVehcStateRun").prop("checked");// 运行状态
	var isShowStop = $("#cbVehcStateStop").prop("checked");// 停止状态
	var isShowOffLine = $("#cbVehcStateOffLine").prop("checked");// 离线状态
	var isShowPlates = $("#cbVehcPlates").prop('checked'); // 是否显示车牌
	point = new BMap.Point(vehcmap.longitude, vehcmap.latitude);
	var icon;
	var iconSize = new BMap.Size(24, 29);
	var direction = vehcmap.direction;
	var reocarstatus = ""; // 瑞卡车态
	// 设置地图图标
	if (vehcmap.workStatus == "在线") {
		icon = new BMap.Icon(basePath +"img/trafficflux/run_car.png", iconSize);
	} else if (vehcmap.workStatus == "断线") {
		icon = new BMap.Icon(basePath +"img/trafficflux/stop_car.png", iconSize);
	} else {
		icon = new BMap.Icon(basePath +"img/trafficflux/offline_car.png", iconSize);
	}
	// 添加车辆地图标注
	marker = new BMap.Marker(point);
	map.addOverlay(marker);
	marker.setIcon(icon);
	var label = "";
	if (isShowPlates) {
		label = new BMap.Label(vehcmap.plate, {
			offset : new BMap.Size(20, -10)
		});
		label.setStyle({
			fontSize : "14px",
			border : "0",
			textAlign : "center"
		});
	} else {
		label = new BMap.Label();
		label.setStyle({
			display : "none"
		});
	}
	// 设置地图弹出框内容
	var status = "";
	if (vehcmap.workStatus == "在线") {
		status = "运行";
		label.setStyle({
			color : "green" // 给label设置样式，任意的CSS都是可以的
		});
	} else if (vehcmap.workStatus == "断线") {
		status = "停止";
		label.setStyle({
			color : "gray" // 给label设置样式，任意的CSS都是可以的
		});
	} else {
		status = "离线";
		label.setStyle({
			color : "red" // 给label设置样式，任意的CSS都是可以的
		});
	}
	marker.setLabel(label);
	var speed = vehcmap.speed;
	if(speed==null || speed == undefined){
		speed = 0;
	}
	if(vehcmap.department == null || vehcmap.department == undefined){
		vehcmap.department = '';
	}
	if(vehcmap.plate ==null || vehcmap.plate == undefined){
		vehcmap.plate= '';
	}
	content = "<p style='font-weight:bold;text-align:left '>车牌："
			+ vehcmap.plate
			+ "<span style=' float :right; _position:relative;'>定位状态 : "
			+ vehcmap.locState + " &nbsp;&nbsp;&nbsp;</span></p>";
	content += "<p style='text-align:left '>车企： " + vehcmap.department
			+ "<span style='float:right; _position:relative;'>航向：" + direction
			+ "&nbsp;&nbsp;&nbsp;</span> </p>";
	content += "<p>状态：" + status + " &nbsp;&nbsp;" + vehcmap.longTime 
			+ " &nbsp;&nbsp;车速： " + speed + "km/h"
			+ "</p>";
	content += "<p>时间：" + vehcmap.locTime + "</p>";
	if (vehcmap.address == null || vehcmap.address == undefined) {
		content += "<p>位置：未解析地址</p></div>";
	} else {
		content += "<p>位置：" + vehcmap.address + "</p>";
	}
	content += "<p style='float:left;margin-left:175px;margin-right:5px;'><a  class='btn  btn-xs blue' href='" +basePath + "Location/realimeTracking?imei="
		+ vehcmap.imei
		+ "'><img src='" + basePath +"img/trafficflux/vehcCommon/moveTrack.png' />实时追踪</a></p>";
   content += "<p style='float:right;margin-right:5px;margin-bottom:12px;'>"
   			+"&nbsp;&nbsp;<a class='btn  btn-xs blue' href='" +basePath + "VehicleTrajectory/Index/" + vehcmap.imei +"' > "
   			+"<img src='"+basePath +"img/trafficflux/vehcCommon/refresh.png' /> 车辆轨迹</a></p>";
	if ((vehcmap.Flag != "" && vehcmap.Flag != null)
			&& (vehcmap.XCFlag != "" && vehcmap.XCFlag != null)) {

		content += "<p style='float:left;margin-left:75px;margin-right:5px;margin-top:12px;'>";
		content += "<a target='_blank' href='" +basePath + "Vehc/showDetail?id=" + vehcmap.imei
				+ "' class='btn default btn-xs blue'> <img src='" + basePath +"img/trafficflux/vehcCommon/returnCar.png' /> 车辆档案</a>";
		content += "</p>";

		// 实时追踪、车辆轨迹、行程记录
		content += "<div class='btn-group dropup' style='width:100px;position:relative;margin-top:12px;' id='tDiv'>";
		content += "<button type='button' class='btn default btn-xs blue' onclick='showUlThreeList()' style='height:20px;margin-top:1px;'><img src='" + basePath +"img/trafficflux/vehcCommon/currentTravel.png' />行程轨迹</button>";
		content += "<button type='button' class='btn default btn-xs blue dropdown-toggle' data-toggle='dropdown' onclick='showUlThreeList()' style='height:20px;margin-top:1px;'><i class='fa fa-angle-down'></i></button>";
		content += "<ul class='dropdown-menu pull-right' role='menu' style='list-style:none;margin:0px;padding:0px;width:50px;' class='ListStyle'>";
		if (vehcmap.XCFlag.indexOf("1") > -1) {
			content += "<p style='float:right;margin-right:5px;margin-bottom:12px;'><a  class='btn  btn-xs blue' href='/Location/RealtimeTracking/"
				+ vehcmap.imei
				+ "'><img src='" + basePath +"img/trafficflux/vehcCommon/moveTrack.png' />实时追踪</a></p>";
		}
		if (vehcmap.XCFlag.indexOf("2") > -1) {
			content += "<li><a  class='btn  btn-xs blue' href='/VehcLocation/TrackPlayback/"
					+ vehcmap.imei
					+ "'><img src='" + basePath +"img/trafficflux/vehcCommon/refresh.png' />车辆轨迹</a></li>";
		}
		if (vehcmap.XCFlag.indexOf("3") > -1) {
			content += "<li><a  class='btn  btn-xs blue' href='/VehcTrack/VehcTrackRecord/"
					+ vehcmap.imei
					+ "'><img src='" + basePath +"img/trafficflux/vehcCommon/icon_xingcheng.png' />行程记录</a></li>";
		}
		content += "</ul></div>";

		// 栅栏设置
		content += "<div class='btn-group dropup' style='float:right;width:100px;margin-right:-5px;position:relative;margin-top:12px;' id='dDiv'>";
		content += "<button type='button' class='btn default btn-xs blue' onclick='showUlList()' style='height:20px;margin-top:1px;'><img src='" + basePath +"img/trafficflux/vehcCommon/icon_zhanlan.png'/>栅栏设置</button>";
		content += "<button type='button' class='btn default btn-xs blue dropdown-toggle' data-toggle='dropdown' onclick='showUlList()' style='height:20px;margin-top:1px;'><i class='fa fa-angle-down'></i></button>";
		content += "<ul class='dropdown-menu pull-right' role='menu' style='list-style:none;margin:0px;padding:0px;width:50px;' class='ListStyle'>";
		// 根据栅栏设置权限显示
		if (vehcmap.Flag.indexOf("1") > -1) {
			content += "<li><a href='javascript:void(0)' class='btn btn-xs blue' onclick=showTimeModel('"
					+ vehcmap.plate
					+ "','"
					+ vehcmap.imei
					+ "')><img src='" + basePath +"img/trafficflux/vehcCommon/icon_clock_def.png' /> 时间栅栏 </a></li>";
		}
		if (vehcmap.Flag.indexOf("2") > -1) {
			content += "<li><a href='javascript:void(0)' class='btn btn-xs blue' onclick=showAreaModel('"
					+ vehcmap.plate
					+ "','"
					+ vehcmap.imei
					+ "')><img src='" + basePath +"img/trafficflux/vehcCommon/icon_quyu.png' /> 区域栅栏 </a></li>";
		}
		if (vehcmap.Flag.indexOf("3") > -1) {
			content += "<li><a href='javascript:void(0)' class='btn btn-xs blue' onclick=showElectronModel('"
					+ vehcmap.plate
					+ "','"
					+ vehcmap.imei
					+ "')><img src='" + basePath +"img/trafficflux/vehcCommon/icon_dianzi.png' /> 电子栅栏 </a></li>";
		}
		content += "</ul></div>";
	} else if ((vehcmap.Flag == "" || vehcmap.Flag == null)
			&& (vehcmap.XCFlag == "" || vehcmap.XCFlag == null)) {
//	 content += "<p style='float:right;margin-right:5px;margin-top:12px;'>";
//	 content += "<a target='_blank' href='" +basePath + "Vehc/showDetail?id=" +
//	 vehcmap.imei
//	 + "' class='btn default btn-xs blue'> <img src='" + basePath
//	 +"img/trafficflux/vehcCommon/returnCar.png' /> 车辆档案</a>";
//	 content += "</p>";
	} else {
		content += "<p style='float:left;margin-left:165px;margin-right:5px;margin-top:12px;'>";
		content += "<a target='_blank' href='" +basePath + "Vehc/showDetail?id=" + vehcmap.imei
				+ "' class='btn default btn-xs blue'> <img src='" + basePath +"img/trafficflux/vehcCommon/returnCar.png' /> 车辆档案</a>";
		content += "</p>";
		if (vehcmap.XCFlag != "" && vehcmap.XCFlag != null) {
			// 实时追踪、历史轨迹、轨迹回放、行程记录
			content += "<div class='btn-group dropup' style='width:100px;position:relative;margin-top:12px;' id='tDiv'>";
			content += "<button type='button' class='btn default btn-xs blue' onclick='showUlThreeList()' style='height:20px;margin-top:1px;'><img src='" + basePath +"img/trafficflux/vehcCommon/currentTravel.png' />行程轨迹</button>";
			content += "<button type='button' class='btn default btn-xs blue dropdown-toggle' data-toggle='dropdown' onclick='showUlThreeList()' style='height:20px;margin-top:1px;'><i class='fa fa-angle-down'></i></button>";
			content += "<ul class='dropdown-menu pull-right' role='menu' style='list-style:none;margin:0px;padding:0px;width:50px;' class='ListStyle'>";
			content += "</ul></div>";
		} else if (vehcmap.Flag != "" && vehcmap.Flag != null) {
			// 栅栏设置
			content += "<div class='btn-group dropup' style='float:right;width:100px;margin-right:-5px;position:relative;margin-top:12px;' id='dDiv'>";
			content += "<button type='button' class='btn default btn-xs blue' onclick='showUlList()' style='height:20px;margin-top:1px;'><img src='" + basePath +"img/trafficflux/vehcCommon/icon_zhanlan.png'/>栅栏设置</button>";
			content += "<button type='button' class='btn default btn-xs blue dropdown-toggle' data-toggle='dropdown' onclick='showUlList()' style='height:20px;margin-top:1px;'><i class='fa fa-angle-down'></i></button>";
			content += "<ul class='dropdown-menu pull-right' role='menu' style='list-style:none;margin:0px;padding:0px;width:50px;' class='ListStyle'>";
			// 根据栅栏设置权限显示
			if (vehcmap.Flag.indexOf("1") > -1) {
				content += "<li><a href='javascript:void(0)' class='btn btn-xs blue' onclick=showTimeModel('"
						+ vehcmap.plate
						+ "','"
						+ vehcmap.imei
						+ "')><img src='" + basePath +"img/trafficflux/vehcCommon/icon_clock_def.png' />时间栅栏 </a></li>";
			}
			if (vehcmap.Flag.indexOf("2") > -1) {
				content += "<li><a href='javascript:void(0)' class='btn btn-xs blue' onclick=showAreaModel('"
						+ vehcmap.plate
						+ "','"
						+ vehcmap.imei
						+ "')><img src='" + basePath +"img/trafficflux/vehcCommon/icon_quyu.png' /> 区域栅栏 </a></li>";
			}
			if (vehcmap.Flag.indexOf("3") > -1) {
				content += "<li><a href='javascript:void(0)' class='btn btn-xs blue' onclick=showElectronModel('"
						+ vehcmap.plate
						+ "','"
						+ vehcmap.imei
						+ "')><img src='" + basePath +"img/trafficflux/vehcCommon/icon_dianzi.png' /> 电子栅栏 </a></li>";
			}
			content += "</ul></div>";
		}
	}
	contentObj = {
		content : content
	};
	marker.addEventListener("click", openInfo.bind(null, contentObj));
	return content;
}

// 获取车辆列表
function getVehcList() {
	var eqpId = $("#selPlates").select2("val");
	var departmentId=$("#companyId").val();
	vehcList = null;
	$(".vehc-list").empty();
	$("#spVehcCount").text("0");
	map.clearOverlays();
	$("#cbVehcStateRun").prop("checked", true);
	$("#cbVehcStateRun").closest("span").addClass("checked");
	$("#cbVehcStateStop").prop("checked", true);
	$("#cbVehcStateStop").closest("span").addClass("checked");
	$("#cbVehcStateOffLine").prop("checked", true);
	$("#cbVehcStateOffLine").closest("span").addClass("checked");
	$.ajax({
		url : "Location/QueryLocation",
		cache : false,
		data : {
			processOption : 2,// 纠偏选项 1_不纠偏;2_百度纠偏;默认不纠偏
			eqpId : eqpId,
			relationType:1,
			departmentId:departmentId
		},
		success : function(data) {
			vehcList = data.vehcLocation;
			vehctableList = [];
			spRunCount = data.runCount;
			spStopCount = data.stopCount;
			spOffLineCount = data.offlineCount;
			$("#spOffLineCount").text(spOffLineCount);
			$("#spStopCount").text(spStopCount);
			$("#spRunCount").text(spRunCount);
			$("#spVehcCount").text(vehcList.length);
			vehcMapList = [];
			deleteVehcIndex = [];
			isFrom = 1;// 标识是1:查询，2：运行停止离线，3：显示车牌，4：拖动，5缩放，6行点击
			isGoCount = true;// 循环中会对车辆计数
			if (vehcList == null || vehcList == undefined
					|| vehcList.length == 0) {
				toastr.success("当前车辆未安装设备或设备未激活", "提示信息");
				return;
			}
			map.centerAndZoom(new BMap.Point(
					vehcList[vehcList.length - 1].longitude,
					vehcList[vehcList.length - 1].latitude), map.getZoom()); // 初始化地图,设置中心点坐标和地图级别
			// vehcList[0]
			// 绑定车辆列表、地图标记
			bindVehcMarker();
			initWork();// 定时更新
		},
		error : function(xhr, status, error) {
			//toastr.error(xhr.responseText,"提示");
		},
		complete : function(xhr, ts) {
			_loading.hide();
		}
	});
}

function showUlList() {

	$("#tDiv").removeClass("btn-groupnew dropup open");
	$("#tDiv").addClass("btn-group dropup");

	if ($("#dDiv").hasClass("btn-group dropup")) {
		$("#dDiv").removeClass("btn-group dropup");
		$("#dDiv").addClass("btn-groupnew dropup open");
	} else if ($("#dDiv").hasClass("btn-groupnew dropup open")) {
		$("#dDiv").removeClass("btn-groupnew dropup open");
		$("#dDiv").addClass("btn-group dropup");
	} else {
		$("#dDiv").addClass("btn-groupnew dropup open");
	}
}

function showUlThreeList() {
	$("#dDiv").removeClass("btn-groupnew dropup open");
	$("#dDiv").addClass("btn-group dropup");
	if ($("#tDiv").hasClass("btn-group dropup")) {
		$("#tDiv").removeClass("btn-group dropup");
		$("#tDiv").addClass("btn-groupnew dropup open");
	} else if ($("#tDiv").hasClass("btn-groupnew dropup open")) {
		$("#tDiv").removeClass("btn-groupnew dropup open");
		$("#tDiv").addClass("btn-group dropup");
	} else {
		$("#tDiv").addClass("btn-groupnew dropup open");
	}
}

/**
 * 获取加入toC业务的租赁公司列表            好约车代码
 */
function initCompanyList(){
	$.ajax({
	    type: 'POST',
	    // url: "Order/GetCompanyList" ,
        url: "Order/GetBelongCompanyList" ,
	    dataType: "json",
	    success: function(data){
	    	if(data.status == 0 && data.count > 0){
	    		params.companies = data.lease;
	    		initCompanySelect();
	    	}else if(data.status != 0){
//	    		alert(data.message);
	    	}
	    },
	    contentType:"application/json"
	});
}
/**
 * 初始化租赁公司下拉组件         好约车代码
 */
function initCompanySelect(){
	$.each(params.companies,function(index,item){
		var option = $("<option>").val(item.id).text(item.shortName);
		$("#companyId").append(option);
	});
}


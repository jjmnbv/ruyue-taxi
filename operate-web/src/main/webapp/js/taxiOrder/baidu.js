var map;
var mapParam = {
	result: null,
	city: null,
	currentAct: null,
	suggest: null,
	onAddress: null,
	offAddress: null,
	onaddrlng: 0,
	onaddrlat: 0,
	offaddrlng: 0,
	offaddrlat: 0
};

var suggestInit = null;
var suggestCost = false;

//页面初始化
$(function() {
	initMap();
	initCitySelect1();
	initForm();
});

/**
 * 初始化城市插件
 */
function initCitySelect1() {
	showCitySelect1(
		"#oncityDiv",
		"PubInfoApi/GetCitySelect1",
		null,
		function(backVal, $obj) {
			
		}
	);
	
	//上车城市
	showCitySelect1(
		"#oncityDiv",
		"Order/GetTaxiBusCity",
		null,
		function(backVal, $obj) {
			cityBack($obj.data('id'), $obj.text(), "on");
		}
	);
	var citySpan = $("#oncityDiv").find(".city_container").find(".city_con>span");
	if(citySpan.length > 0) {
		$(citySpan[0]).click();
	}
	
	//下车城市
	showCitySelect1(
		"#offcityDiv",
		"PubInfoApi/GetCitySelect1",
		null,
		function(backVal, $obj) {
			cityBack($obj.data('id'), $obj.text(), "off");
		}
	);
	var oncity = $("#onCity").val();
	if(null == oncity || oncity == "") {
		var offCitySpan = $("#offcityDiv").find(".city_container").find(".city_con>span");
		if(offCitySpan.length > 0) {
			$(offCitySpan[0]).click();
		}
	} else {
		cityBack(oncity, $('#oncityname').val(), "off");
	}
}

/**
 * 选择城市回调
 */
function cityBack(cityid, cityname, type) {
	if(type == "on") {
		changeCity(cityname, $("#onAddress")[0]);
		$('#oncityname').val(cityname);
		$("#onCity").val(cityid);
		initSearchMap("onAddress");
		checkRules();
	} else if(type == "off") {
		changeCity(cityname, $("#offAddress")[0]);
		$('#offcityname').val(cityname);
		$("#offCity").val(cityid);
		initSearchMap("offAddress");
	}
}

/**
 * 地图初始化
 */
function initMap() {
	map = new BMap.Map("dituContent", {enableMapClick:false});
	map.centerAndZoom($("#oncityname").val(), 18); // 初始化地图,设置城市和地图级别。
	map.enableScrollWheelZoom(); //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom(); //启用地图惯性拖拽，默认禁用
	var icon = new BMap.Icon("content/img/tuding.png", new BMap.Size(48, 48));
	var marker = new BMap.Marker(map.getCenter(),{icon:icon});
	map.addEventListener("dragging",function(){
		//如果地图上只有默认标注,则不清除
		if(map.getOverlays().length = 1 && map.getOverlays()[0] === marker){
			marker.setPosition(map.getCenter());
		}else{
			map.addOverlay(marker); // 将标注添加到地图中
			marker.setPosition(map.getCenter());
		}
	});
	map.addEventListener("dragend",function(){
		getAddress(map.getCenter().lng,map.getCenter().lat);
	});
}

/**
 * 根据经纬度解析地址(逆解析)
 * @param lng
 * @param lat
 */
function getAddress(lng, lat){
	var data = {
		orderStartLng:lng,
		orderStartLat:lat
	};
	$.ajax({
	    type: 'POST',
	    url: "Order/GetAddress" ,
	    dataType: "json",
	    data:JSON.stringify(data),
	    success: function(result){
	    	if(result.status == 0){
	    		mapParam.city = getCity1(result.city);
	    		if(null == mapParam.city) {
	    			return;
	    		}
	    		if(mapParam.currentAct == "offAddress" || $("#map").attr("data-owner") == "offAddress"){
					mapParam.offaddrlng = result.lng;
					mapParam.offaddrlat = result.lat;
					$("#offAddressMarkid").val(mapParam.city.markid);
				}else if(mapParam.currentAct == "onAddress" || $("#map").attr("data-owner") == "onAddress"){
					mapParam.onaddrlng = result.lng;
					mapParam.onaddrlat = result.lat;
					$("#onAddressMarkid").val(mapParam.city.markid);
				}
	    		mapParam.suggest.setInputValue(result.address);
	    		suggestInit = $("#suggest").val();
	    	}else if(data.status != 0){
	    		
	    	}
	    },
	    contentType:"application/json"
	});
}

/**
 * 页面初始化函数
 */
function initForm() {
	if(!mapParam.suggest) {
		mapParam.suggest = initSearchMap("suggest");
	}
	mapParam.currentAct = "onAddress";
	mapParam.onAddress = initSearchMap("onAddress");
	mapParam.currentAct = "offAddress";
	mapParam.offAddress = initSearchMap("offAddress");
}

/**
 * 地图检索输入框初始化
 * @param {} idName
 */
function initSearchMap(idName) {
//建立一个自动完成的对象
	var city = null;
	if(idName == "onAddress") {
		city = $("#oncityname").val();
	} else if(idName == "offAddress") {
		city = $("#offcityname").val();
	} else {
		city = map;
	}
	var mapOpt = {
		"input" : idName,
		"location" : city
	};
	if(idName != "suggest"){
		mapOpt.onSearchComplete = searchComplete;
	}
	var ac = new BMap.Autocomplete(mapOpt);
	if(idName == "suggest"){
		//鼠标放在下拉列表上的事件
		ac.addEventListener("onhighlight", function(e) {
			var _value;
			if (e.fromitem.index > -1) {
				_value = e.fromitem.value;
			}    
			if (e.toitem.index > -1) {
				_value = e.toitem.value;
			}  
		});
		
		//鼠标点击下拉列表后的事件
		ac.addEventListener("onconfirm", function(e) {
			var _value = e.item.value;
			var address = _value.province + _value.city + _value.district +  _value.street +  _value.business;
			resultClickFunc(_value.city,address,idName);
			ac.setInputValue(_value.district +  _value.street +  _value.business);
			suggestInit = $("#suggest").val();
			getCost();
		});
	}
	return ac;
}

/**
 * 检索完成回调
 */
function searchComplete(result){
	$(".tangram-suggestion-main").hide();
	mapParam[mapParam.currentAct].hide();
	
	var actCity = null;
	if(mapParam.currentAct == "onAddress") {
		actCity = $("#oncityname").val();
	} else if(mapParam.currentAct == "offAddress") {
		actCity = $("#offcityname").val();
	}
	var poiArray = new Array();
	var m = 0;
	$("#" + mapParam.currentAct).parent().find(".dizhibox .bresult").empty();
	for(var i=0;i<result.getNumPois();i++){
		$("td[id^='tangram-suggestion--TANGRAM'][id$='-item" + i + "']").parent().hide();
		$(".tangram-suggestion-main").hide();
		var keyword = result.keyword;
		var poi = result.getPoi(i);
		if(poi.city != actCity) {
			continue;
		}
		poiArray[m] = poi;
		m++;
		var city = poi.city+poi.district;
		var address = poi.district+poi.streetNumber + poi.business;
		var text = (address + "&nbsp;").replace(keyword,"<b>"+keyword+"</b>");
		var tagSpan = $("<span>").addClass("tangram-suggestion-grey");
		tagSpan.html(city).append("<br/>");
		var tagI = $("<i>").addClass("route-icon").css("cursor","default");
		tagI.append(text);
		tagI.append(tagSpan);
		tagI.click(function(){
			var poi = mapParam.result[$(this).index()];
			var city = poi.city;
			var address = poi.district+poi.streetNumber + poi.business;
			resultClickFunc(city,address,mapParam.currentAct);
		});
		$("#" + mapParam.currentAct).parent().find(".dizhibox .bresult").append(tagI);
	}
	mapParam.result = poiArray;
}

/**
 * 结果列表点击事件
 * @param city
 * @param address
 * @param idName
 */
function resultClickFunc(city,address,idName){
	getLngLat(city,address,idName);
	moveMap(city,address);
	changeCity(city,$("#"+idName)[0]);
	mapParam[idName].setInputValue(address);
	//点击后收起
	$("#" + mapParam.currentAct).parent().find(".dizhibox").slideUp(500);
	$(".tangram-suggestion-main").hide();
}

/**
 * 根据地址解析经纬度
 * @param city
 * @param address
 */
function getLngLat(city,address,idName){
	var data = {
		city:city,
		address:address
	};
	$.ajax({
	    type: 'POST',
	    url: "Order/GetLatLng" ,
	    dataType: "json",
	    data:JSON.stringify(data),
	    success: function(result){
	    	if(result.status == 0){
	    		if(mapParam.currentAct == "offAddress" || $("#map").attr("data-owner") == "offAddress"){
					mapParam.offaddrlng = result.lng;
					mapParam.offaddrlat = result.lat;
				}else if(mapParam.currentAct == "onAddress" || $("#map").attr("data-owner") == "onAddress"){
					mapParam.onaddrlng = result.lng;
					mapParam.onaddrlat = result.lat;
				}
	    		getCost();
	    	}else if(data.status != 0){
	    		
	    	}
	    },
	    contentType:"application/json"
	});
}

/**
 * 城市下拉列表改变时更换地图检索对象所属城市
 * @param {} city
 * @param {} suggest
 */
function changeCity(city, element){
	if(!mapParam.onAddress || !mapParam.offAddress) {
		initForm();
	}
	mapParam[element.id].setLocation(city);
	mapParam.suggest.setLocation(city);
	element.value=""; //切换城市清空详细地址
	var cityObj = element.id == "onAddress"?$("#oncityname").val():$("#offcityname").val();
	mapParam.city = getCity1(city);
	if(null == mapParam.city) {
		return;
	}
	
	//地图搜索切换城市
	if(element.id == "onAddress" || $("#map_confirm").attr("data-owner") == "onAddress"){
		$("#onCity").val(mapParam.city.id);
		$("#oncityname").val(mapParam.city.city);
		$("#onAddressMarkid").val(mapParam.city.markid);
	}else if(element.id == "offAddress" || $("#map_confirm").attr("data-owner") == "offAddress"){
		$("#offCity").val(mapParam.city.id);
		$("#offcityname").val(mapParam.city.city);
		$("#offAddressMarkid").val(mapParam.city.markid);
	}
}

/**
 * 根据输入框中地址信息将对弹出窗口地图进行定位
 * @param {} city
 * @param {} address
 */
function moveMap(city, address){
	map.clearOverlays();
	function myFun(){
		var pp = null;
		if(mapParam.currentAct == "onAddress" && null != params.onAddress && $.trim(params.onAddress) == $.trim($("#onAddress").val())) {
			pp = new BMap.Point(params.onLng, params.onLat);
		} else if(mapParam.currentAct == "offAddress" && null != params.offAddress && $.trim(params.offAddress) == $.trim($("#offAddress").val())){
			pp = new BMap.Point(params.offLng, params.offLat);
		} else {
			var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
		}
		
		map.centerAndZoom(pp, 18);
		map.addOverlay(new BMap.Marker(pp));    //添加标注
	}
	var local = new BMap.LocalSearch(map, {
		  renderOptions:{map: map},
		  onSearchComplete: myFun
	});
	local.search(city+address);
}

/**
 * 选择上车地址
 */
function onMap() {
    $("#suggest").val("");
	suggestInit = null;
	if(isUserNull()) {
		$("#nouserid").show();
		return;
	}
	var oncityname = $("#oncityname").val();
	if(null == oncityname || oncityname == "") {
		return;
	}
	mapParam.currentAct = "onAddress";
	moveMap(oncityname,$("#onAddress").val());
	$("#map").attr("data-owner","onAddress");
	showOffaddress(map);
	$("#map").show();
}

/**
 * 选择下车地址
 */
function offMap() {
    $("#suggest").val("");
	suggestInit = null;
	if(isUserNull()) {
		$("#nouserid").show();
		return;
	}
	var offcityname = $("#offcityname").val();
	if(null == offcityname || offcityname == "") {
		return;
	}
	mapParam.currentAct = "offAddress";
	moveMap(offcityname,$("#offAddress").val());
	$("#map").attr("data-owner","offAddress");
	showOnaddress(map);
	$("#map").show();
}

/**
 * 展示上车地址
 */
function showOnaddress(onMap) {
	var onAddress = $("#onAddress").val();
	if (null != mapParam.onaddrlng && mapParam.onaddrlng > 0
			&& null != mapParam.onaddrlat && mapParam.onaddrlat > 0
			&& null != onAddress && "" != onAddress) {
		var onPoint = new BMap.Point(mapParam.onaddrlng, mapParam.onaddrlat);
		var icon = new BMap.Icon("img/orgordermanage/icon_shangche.png",
				new BMap.Size(48, 48));
		var marker = new BMap.Marker(onPoint, {
			icon : icon
		});
		onMap.addOverlay(marker);
		var content = "<p>" + $("#onAddress").val() + "</p>";
		var startinfoWindow = new BMap.InfoWindow(content);
		marker.addEventListener("click", function() {
			onMap.openInfoWindow(startinfoWindow, onPoint);
		});
	}
}

/**
 * 展示下车地址
 * @param offMap
 */
function showOffaddress(offMap) {
	var offAddress = $("#offAddress").val();
	if (null != mapParam.offaddrlng && mapParam.offaddrlng > 0
			&& null != mapParam.offaddrlat && mapParam.offaddrlat > 0
			&& null != offAddress && "" != offAddress) {
		var offPoint = new BMap.Point(mapParam.offaddrlng, mapParam.offaddrlat);
		var icon = new BMap.Icon("img/orgordermanage/icon_xiache.png",
				new BMap.Size(48, 48));
		var marker = new BMap.Marker(offPoint, {
			icon : icon
		});
		offMap.addOverlay(marker);
		var content = "<p>" + $("#onAddress").val() + "</p>";
		var endinfoWindow = new BMap.InfoWindow(content);
		marker.addEventListener("click", function() {
			offMap.openInfoWindow(endinfoWindow, offPoint);
		});
	}
}


/**
 * 地图获取地址
 */
function mapConfirm() {
	if(null == suggestInit || "" == suggestInit || suggestInit != $("#suggest").val()) {
		$("#noaddress").show();
		return;
	}
	if($("#map").attr("data-owner") == "onAddress"){
		suggestCost = true;
		checkRules(mapParam.city.id);
		if(suggestCost == false) {
			toastr.error("该城市不提供服务", "提示");
			return;
		}
		suggestCost = false;
		$("#onCity").val(mapParam.city.id);
		$("#oncityname").val(mapParam.city.city);
		changeCity(mapParam.city.city,$("#onAddress")[0]);
		params.onLat = mapParam.onaddrlat;
		params.onLng = mapParam.onaddrlng;
		params.onAddress = $("#suggest").val();
	}else{
		$("#offCity").val(mapParam.city.id);
		$("#offcityname").val(mapParam.city.city);
		changeCity(mapParam.city.city,$("#offAddress")[0]);
		params.offLat = mapParam.offaddrlat;
		params.offLng = mapParam.offaddrlng;
		params.offAddress = $("#suggest").val();
	}
   //使用setInputValue方法不会再次调用下拉搜索
   $("#map").hide();
   if($("#suggest").val() == "") {
	   return;
   }
   mapParam[$("#map").attr("data-owner")].setInputValue($("#suggest").val());
   //$("#suggest").val("");
   getCost();
}

function closeNoaddress() {
	$("#noaddress").hide();
}

/**
 * 关闭地图弹窗
 */
function mapCancel() {
	$("#suggest").val("");
	$("#map").hide();
}
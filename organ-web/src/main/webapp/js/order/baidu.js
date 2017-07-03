var map;
var mapParam = {
	suggest: null,
	onAddress: null,
	offAddress: null,
	city:null,
	currentAct:null,
	onaddrlng: 0,
	onaddrlat: 0,
	offaddrlng: 0,
	offaddrlat: 0
}

var suggestInit = null;
var suggestCost = false;

$(function(){
	initMap();
});

/**
 * 页面初始化函数
 */
function initSuggest() {
	mapParam.suggest = initSearchMap("suggest");
	if($("#ordertype").val() == "2"){
		mapParam.offAddress = initSearchMap("offAddress");
	}else if($("#ordertype").val() == "3"){
		mapParam.onAddress = initSearchMap("onAddress");
	}else{
		mapParam.onAddress = initSearchMap("onAddress");
		mapParam.offAddress = initSearchMap("offAddress");
	}
}

/**
 * 地图初始化
 */
function initMap() {
	map = new BMap.Map("dituContent", {enableMapClick:false});
	map.centerAndZoom($("#onCity").next().val(), 18);                   // 初始化地图,设置城市和地图级别。
	map.enableScrollWheelZoom();                       //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom();                       //启用地图惯性拖拽，默认禁用
	var icon = new BMap.Icon("content/img/tuding.png", new BMap.Size(48, 48));
	var marker = new BMap.Marker(map.getCenter(),{icon:icon});
	map.addEventListener("dragging",function(){
		//如果地图上只有默认标注,则不清除
		if(map.getOverlays().length = 1 && map.getOverlays()[0] === marker){
			marker.setPosition(map.getCenter());
		}else{
			map.addOverlay(marker);                             // 将标注添加到地图中
			marker.setPosition(map.getCenter());
		}
	});
	map.addEventListener("dragend",function(){
		getAddress(map.getCenter().lng,map.getCenter().lat);
	});
}

/**
 * 地图检索输入框初始化
 * @param {} idName
 */
function initSearchMap(idName) {
	//建立一个自动完成的对象
	var ac = null;
	if(idName == "suggest") {
		ac = new BMap.Autocomplete({
			"input" : idName,
			"location" : map,
		});
	} else {
		var city = null;
		if(idName == "onAddress") {
			city = $("#onCity").next().val();
		} else if(idName == "offAddress") {
			city = $("#offCity").next().val();
		}
		ac = new BMap.Autocomplete({
			"input" : idName,
			"location" : city,
			"onSearchComplete" : searchComplete
		});
	}
	
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
		changeCity(_value.city,$("#"+mapParam.currentAct)[0]);
		ac.setInputValue(_value.district +  _value.street +  _value.business);
		moveMap(_value.city,$("#" + mapParam.currentAct).val());
		getCost();
		suggestInit = $("#suggest").val();
		return false;
	});
	
	return ac;
}


/**
 * 检索完成回调
 */
function searchComplete(result){
	var actCity = null;
	if(mapParam.currentAct == "onAddress") {
		actCity = $("#onCity").next().val();
	} else if(mapParam.currentAct == "offAddress") {
		actCity = $("#offCity").next().val();
	} else {
		return;
	}
	
	for(var i=0;i<result.getNumPois();i++){
		var poi = result.getPoi(i);
		if(poi.city != actCity) {
			$("td[id^='tangram-suggestion--TANGRAM'][id$='-item" + i + "']").parent().hide();
		}
	}
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
	    		for(var i in params.cities){
	    			if(params.cities[i].city == result.city){
	    				mapParam.city = params.cities[i];
	    				break;
	    			}
	    		}
	    		if($("#map").attr("data-owner") == "offAddress"){
					mapParam.offaddrlng = result.lng;
					mapParam.offaddrlat = result.lat;
				}else if($("#map").attr("data-owner") == "onAddress"){
					mapParam.onaddrlng = result.lng;
					mapParam.onaddrlat = result.lat;
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
 * 根据地址解析经纬度
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
	    		changeCity(city,$("#"+idName)[0]);
	    		mapParam[idName].setInputValue(address);
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
	if($("#ordertype").val() == "1" && (!mapParam.onAddress || !mapParam.offAddress)){
		initSuggest();
	}else if($("#ordertype").val() == "2" && !mapParam.offAddress){
		initSuggest();
	}else if($("#ordertype").val() == "3" && !mapParam.onAddress){
		initSuggest();
	}
	for(var i in params.cities){
		if(params.cities[i].city == city){
			mapParam.city = params.cities[i];
			break;
		}
	}
	if(mapParam[element.id]) mapParam[element.id].setLocation(city);
	mapParam.suggest.setLocation(city);
	element.value = "";
	var cityObj = element.id == "onAddress"?$("#onCity").next():$("#offCity").next();
	if(city == cityObj.val()) {
		getCost();  //不切换城市时,直接加载费用
		return;  //如果已经切换过了则不再切换	
	}
	//地图搜索切换城市
	if(element.id == "onAddress" || $(".popup_ditu").attr("data-owner") == "onAddress"){
		$("#onCity").val(mapParam.city.id);
		$("#onCity").next().val(mapParam.city.city);
		//如果不是接机送机类型则切换机场地址
		if($("#ordertype").val() != "1") changeAirPortByCity();
	}else if(element.id == "offAddress" || $(".popup_ditu").attr("data-owner") == "offAddress"){
		$("#offCity").val(mapParam.city.id);
		$("#offCity").next().val(mapParam.city.city);
		//如果不是接机送机类型则切换机场地址
		if($("#ordertype").val() != "1") changeAirPortByCity();
	}
	//最后加载车型获取费用
	getCarTypes();
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
		if(mapParam.currentAct == "onAddress" || $(".popup_ditu").attr("data-owner") == "onAddress"){
			mapParam.onaddrlng = pp.lng;
			mapParam.onaddrlat = pp.lat;
		}else if(mapParam.currentAct == "offAddress" || $(".popup_ditu").attr("data-owner") == "offAddress"){
			mapParam.offaddrlng = pp.lng;
			mapParam.offaddrlat = pp.lat;
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
 * IP定位
 * @param result
 */
function getCityByLocation(){
	function myFun(result){
		if($("#orderno").val() != "") return;
		var cityName = result.name;
		changeCity(cityName,$("#onAddress")[0]);
		changeCity(cityName,$("#offAddress")[0]);
	    myCity = null;
	}
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);
}
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
}

var suggestInit = null;
var suggestCost = false;

initMap();

/**
 * 页面初始化函数
 */
function initForm() {
	if(!mapParam.suggest) mapParam.suggest = initSearchMap("suggest");
	if($("#ordertype").val() == "2" && !mapParam.offAddress){
		mapParam.offAddress = initSearchMap("offAddress");
	}else if($("#ordertype").val() == "3" && !mapParam.onAddress){
		mapParam.onAddress = initSearchMap("onAddress");
	}else if(!mapParam.offAddress && !mapParam.onAddress){
		mapParam.onAddress = initSearchMap("onAddress");
		mapParam.offAddress = initSearchMap("offAddress");
	}
}

/**
 * 地图初始化
 */
function initMap() {
	map = new BMap.Map("dituContent");
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
	var city = null;
	if(idName == "onAddress") {
		city = $("#onCity").next().val();
	} else if(idName == "offAddress") {
		city = $("#offCity").next().val();
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
		//地图弹窗挡住了百度搜索建议
		$("#map").css("z-index",0);
		//鼠标放在下拉列表上的事件
		ac.addEventListener("onhighlight", function(e) {
			var _value;
			if (e.fromitem.index > -1) {
				_value = e.fromitem.value;
			}    
			if (e.toitem.index > -1) {
				_value = e.toitem.value;
			}  
//			ac.setInputValue(_value.district +  _value.street +  _value.business);
		});
		
		//鼠标点击下拉列表后的事件
		ac.addEventListener("onconfirm", function(e) {
			var _value = e.item.value;
			var address = _value.province + _value.city + _value.district +  _value.street +  _value.business;
			ac.setInputValue(_value.district +  _value.street +  _value.business);
			resultClickFunc(_value.city,address,idName);
			suggestInit = $("#suggest").val();
		});
	}
	return ac;
}

/**
 * 检索完成回调
 */
function searchComplete(result){
	mapParam[mapParam.currentAct].hide();
	var actCity = null;
	if(mapParam.currentAct == "onAddress") {
		actCity = $("#onCity").next().val();
	} else if(mapParam.currentAct == "offAddress") {
		actCity = $("#offCity").next().val();
	}
	var poiArray = new Array();
	var m = 0;
	$("#" + mapParam.currentAct).parent().find(".dizhibox .bresult").empty();
	for(var i=0;i<result.getNumPois();i++){
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
		var tagI = $("<i>").addClass("route-icon").css("cursor","default").css("background-position-y","-13px");
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
	$("#" + mapParam.currentAct).parent().find(".dizhibox").slideUp(500);
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
	    		if(mapParam.currentAct == "offAddress" || $("#map").attr("data-owner") == "offAddress"){
					mapParam.offaddrlng = result.lng;
					mapParam.offaddrlat = result.lat;
				}else if(mapParam.currentAct == "onAddress" || $("#map").attr("data-owner") == "onAddress"){
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
 * @param city
 * @param address
 */
function getLngLat(city,address,idName){
//	var myGeo = new BMap.Geocoder();  //解析地址坐标
//	myGeo.getPoint(address, function(point){
//		if (point) {
//			if(idName == "offAddress" || $("#map").attr("data-owner") == "offAddress"){
//				mapParam.offaddrlng = point.lng;
//				mapParam.offaddrlat = point.lat;
//			}else if(idName == "onAddress" || $("#map").attr("data-owner") == "onAddress"){
//				mapParam.onaddrlng = point.lng;
//				mapParam.onaddrlat = point.lat;
//			}
//	  	}
//	});
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
	    		//最后加载车型获取费用
	    		getCarTypes();
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
	for(var i in params.cities){
		if(params.cities[i].city == city){
			mapParam.city = params.cities[i];
			break;
		}
	}
	if(!mapParam.onAddress || !mapParam.offAddress) initForm();
	mapParam[element.id].setLocation(city);
	mapParam.suggest.setLocation(city);
	element.value=""; //切换城市清空详细地址
	var cityObj = element.id == "onAddress"?$("#onCity").next():$("#offCity").next();
	if(city == cityObj.val()) {
		getCost();  //不切换城市时,直接加载费用
		return;  //如果已经切换过了则不再切换	
	}
	//地图搜索切换城市
	if(element.id == "onAddress" || $("#map").attr("data-owner") == "onAddress"){
		$("#onCity").val(mapParam.city.id);
		$("#onCity").next().val(mapParam.city.city);
	}else if(element.id == "offAddress" || $("#map").attr("data-owner") == "offAddress"){
		$("#offCity").val(mapParam.city.id);
		$("#offCity").next().val(mapParam.city.city);
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

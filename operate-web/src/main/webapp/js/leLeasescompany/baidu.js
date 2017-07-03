var map;
var mapParam = {
	suggest: null,
	city:$("#city").val(),
	cityName:$("#cityName").val(),
	cityAddress: null,
	address : $("#address").val()
}

$(function() {
	initForm();
});

/**
 * 页面初始化函数
 */
function initForm() {
	initMap();
	
	mapParam.suggest = initSearchMap("suggest");
	mapParam.cityAddress = initSearchMap("address");
}

/**
 * 地图初始化
 */
function initMap() {
	map = new BMap.Map("dituContent");
	map.centerAndZoom(mapParam.cityName, 18);                   // 初始化地图,设置城市和地图级别。
	map.enableScrollWheelZoom();                       //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom();                       //启用地图惯性拖拽，默认禁用
	var icon = new BMap.Icon("content/img/tuding.png", new BMap.Size(48, 48));
	var marker = new BMap.Marker(map.getCenter(),{ icon: icon });
	map.addEventListener("dragging",function(){
		//如果地图上只有默认标注,则不清除
		if(map.getOverlays().length = 1 && map.getOverlays()[0] === marker){
			marker.setPosition(map.getCenter());
		}else{
			map.clearOverlays();
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
	var mapOpt = {
		"input" : idName,
		"location" : map
	};
	var ac = new BMap.Autocomplete(mapOpt);
	ac.setInputValue(mapParam.address);
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
		ac.setInputValue(_value.district +  _value.street +  _value.business);
		var address = _value.province + _value.city + _value.district +  _value.street +  _value.business;
		mapParam.cityName = _value.city;
		moveMap(mapParam.cityName,address);
	});
	return ac;
}


/**
 * 城市下拉列表改变时更换地图检索对象所属城市
 * @param {} city
 * @param {} suggest
 */
function changeCity(city){
	mapParam.suggest.setLocation(city);
	mapParam.cityAddress.setLocation(city);
}

/**
 * 根据输入框中地址信息将对弹出窗口地图进行定位
 * @param {} city
 * @param {} address
 */
function moveMap(city, address){
	map.clearOverlays();
	/*
	map.centerAndZoom(city, 12);*/
	function myFun(){
		var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
		
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
	    url: "LeLeasescompany/GetAddress" ,
	    dataType: "json",
	    data:JSON.stringify(data),
	    success: function(result){
	    	if(result.status == 0){
//	    		if(mapParam.currentAct == "offAddress" || $("#map").attr("data-owner") == "offAddress"){
//					mapParam.offaddrlng = result.lng;
//					mapParam.offaddrlat = result.lat;
//				}else if(mapParam.currentAct == "onAddress" || $("#map").attr("data-owner") == "onAddress"){
//					mapParam.onaddrlng = result.lng;
//					mapParam.onaddrlat = result.lat;
//				}
	    		mapParam.suggest.setInputValue(result.address);
	    		mapParam.cityName = result.city;
	    	}else if(data.status != 0){
	    		
	    	}
	    },
	    contentType:"application/json"
	});
}


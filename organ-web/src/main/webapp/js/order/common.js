var params = {
	index:1,//表单流程序号
	order : null,
	cities : null,
	airports : null,
	lease : null,
	mostcontact : null,
	mostaddress : null,
	usecarreason : null,
	onCity : null,
	offCity : null,
	onLat : 0,
	onLng : 0,
	onAddress : null,
	offLat : 0,
	offLng : 0,
	offAddress : null,
	cartypes : null,
	cartypeindex : 0,  //车型当前序号
	cartypemin : 3  // 车型最小显示个数
};



$(function(){
	initIndex();
	if($("#orderno").val() != ""){
		setTimeout(checkHistoryOrder,500);
	}else{
		setTimeout(formFlowControl,500);
	}
});

/**
 * 加载首页信息
 */
function initIndex() {
	if(!checkHasRule() || checkNotPay()) return;
	serializeObject();           //追加序列化表单功能
	getCities();                     //获取城市列表
	getBusCities();
	getAirPorts();                //获取全国机场列表
	getCarTypes();              //获取车型列表
	getLeaseList();              //获取服务车企列表
	getMostContact();       //获取常用联系人列表
	getMostAddress();      //获取常用地址列表
	getUseCarReason();     //获取用车事由列表
	initElement();
}

/**
 * 表单流程控制
 */
function formFlowControl(){
	disableForm("form",true);
	openElement(1);
}

function openElement(index){
	if(params.index < 6) {
		getCost();
	}
	params.index = index;
	changeClass();
	params.index == 1 ? open_1() : 
	params.index == 2 ? open_2() : 
	params.index == 3 ? open_3() : 
	params.index == 4 ? open_4() : 
	params.index == 5 ? open_5() : 
	params.index == 6 ? open_6() : 
	params.index == 7 ? open_7() : open_1();
}

function changeClass(){
	var src = $(".block").eq(params.index-1).find(".seq_box img").attr("src");
	src = src.replace("_pre","");
	$(".block").eq(params.index-1).addClass("box_1");
	$(".block").eq(params.index-1).find(".seq_box img").remove();
	$(".block").eq(params.index-1).find(".seq_box").prepend($("<img>").attr("src",src.replace(".png","_pre.png")));
	$(".block").eq(params.index-1).removeClass("box_2");
}

function open_1(){
	$("#companyId").attr("disabled",false);
}
function open_2(){
	$("#passengers").next().find("li").show();
	$("#passengers").attr("disabled",false);
	$("#passengerPhone").attr("disabled",false);
	$("#addFavUser").attr("disabled",false);
}
function open_3(){
	$("#useTime").attr("disabled",false);
	$("#onCity").next().attr("disabled",false);
	$("#offCity").next().attr("disabled",false);
	$("#onAddress,#offAddress").attr("disabled",false);
    if($("#ordertype").val() == "2"){
    	$("#fltno").attr("disabled",false);
		$("#fallTime").attr("disabled",false);
		$(".ditu_2").show();
    }else if($("#ordertype").val() == "3"){
		$(".ditu_1").show();
    }else{
    	$(".ditu_1,.ditu_2").show();
    }
}
function open_4(){
	$("#vehiclessubjectType").next().find("li").show();
	$("#vehiclessubjectType").attr("disabled",false);
	$("#vehiclesSubject").attr("disabled",false);
}
function open_5(){
	$("#tripRemark").attr("disabled",false);
}
function open_6(){
	$("#car_info").show();	
	$("#carTypeList .item").eq(params.cartypeindex).click();
	openElement(7);
}
function open_7(){
	$("#paymethod input[type='radio']").attr("disabled",false);
	open_compele();
}
function open_compele(){
	var src = $(".block").eq(7).find(".seq_box img").attr("src");
	src = src.replace("_pre","");
	$(".block").eq(7).find(".seq_box img").remove();
	$(".block").eq(7).find(".seq_box").prepend($("<img>").attr("src",src.replace(".png","_pre.png")));
}

/**
 * 检查是否分配了用车规则
 * true - 有  false - 没有
 */
function checkHasRule(){
	if($("#hasrule").val() == "false"){
		var content = "您没有用车权限,请和管理员联系.";
		var sureFunc = function(){
			window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + "User/Index";
		};
		showHint(content,false,sureFunc);
		disableForm("form",true);
		return false;
	}
	return true;
}

/**
 * 检查是否有未支付订单
 * true - 有  false - 没有
 */
function checkNotPay(){
	if($("#notpay").val() == "true"){
		var content = "您有订单未支付，现在不能下单，请完成支付后再进行下单。";
		var sureFunc = function(){
			window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + "User/Index";
		};
		showHint(content,true,sureFunc);
		disableForm("form",true);
		return true;
	}
	return false;
}

/**
 * 检查是否存在订单号
 */
function checkHistoryOrder() {
	$.ajax({
		type : 'GET',
		url : "Order/CheckOrderState/" + $("#orderno").val(),
		dataType : "json",
		success : function(result) {
			writeHistoryOrder(result);
		}
	});
	$(".block").each(function(index,item){
		if(index < 7){
			openElement(index + 1);
		}
	});
}

/**
 * 存在订单号则自动填写表单信息
 * @param result
 */
function writeHistoryOrder(result) {
	if (result.status != 0) return;
	params.order = result.order;
	var order = params.order;
	// 选取服务车企
	$.each(params.lease, function(index, obj) {
		if (obj.id == order.companyid) {
			$("#companyId").attr("data-value", obj.id);
			$("#companyId").val(obj.shortName);
		}
	});
	$.each(params.cities, function(index, obj) {
		if (obj.id == order.oncity) {
			changeCity(obj.city, $("#onAddress")[0]);
			if(mapParam.onAddress){
				mapParam.onAddress.setInputValue(order.onaddress);
			}
			mapParam.onaddrlng = order.onaddrlng;
			mapParam.onaddrlat = order.onaddrlat;
		}
		if (obj.id == order.offcity) {
			changeCity(obj.city, $("#offAddress")[0]);
			if(mapParam.offAddress){
				mapParam.offAddress.setInputValue(order.offaddress);
			}
			mapParam.offaddrlng = order.offaddrlng;
			mapParam.offaddrlat = order.offaddrlat;
		}
	});
	$.each(params.usecarreason, function(index, obj) {
		if (obj.id == order.vehiclessubjecttype) {
			$("#vehiclessubjectType").attr("data-value", obj.id);
			$("#vehiclessubjectType").val(obj.text);
		}
	});
	$("#passengers").val(order.passengers);
	$("#passengers").attr("data-value", order.passengers);
	$("#passengerPhone").val(order.passengerphone);
	$("#passengerPhone").attr("data-value", order.passengerphone);
	$("#useTime").val(timeStamp2String(order.usetime,"-")+":00");
	$("#vehiclesSubject").val(order.vehiclessubject);
	$("#tripRemark").val(order.tripremark);
	$("#paymethod input[value='" + order.paymethod + "']").click();
	if($("#ordertype").val() == "2"){
		$("#fltno").val(order.fltno);
		$("#fallTime").val(timeStamp2String(order.falltime,"-")+":00");
	}
}

/**
 * 禁用表单
 * 
 * @param formId
 * @param isDisabled
 */
function disableForm(formId, isDisabled) {
	$("form[id='" + formId + "'] :text").attr("disabled", isDisabled);
	$("form[id='" + formId + "'] textarea").attr("disabled", isDisabled);
	$("form[id='" + formId + "'] select").attr("disabled", isDisabled);
	$("form[id='" + formId + "'] :radio").attr("disabled", isDisabled);
	$("form[id='" + formId + "'] :checkbox").attr("disabled", isDisabled);
    $("#onCity").next().attr("disabled", isDisabled);
    $("#offCity").next().attr("disabled", isDisabled);
    if($("#ordertype").val() == "2"){
    	isDisabled ? $(".ditu_2").hide() : $(".ditu_2").show();
    }else if($("#ordertype").val() == "3"){
    	isDisabled ? $(".ditu_1").hide() : $(".ditu_1").show();
    }else{
    	isDisabled ? $(".ditu_1,.ditu_2").hide() : $(".ditu_1,.ditu_2").show();
    }
    isDisabled ? $("#passengers").parent().find("ul li").hide() : $("#passengers").parent().find("ul li").show();
    isDisabled ? $("#vehiclessubjectType").parent().find("ul li").hide() : $("#vehiclessubjectType").parent().find("ul li").show();
    isDisabled ? $("#car_info").hide() : $("#car_info").show();
}

/**
 * 展示上车地址
 */
function showOnaddress(onMap) {
	var onAddress = $("#onAddress").val();
	var onaddrlat = 0;
	var onaddrlng = 0;
	var flag = false;
	if (null != mapParam.onaddrlng && mapParam.onaddrlng > 0
			&& null != mapParam.onaddrlat && mapParam.onaddrlat > 0
			&& null != onAddress && "" != onAddress) {
		flag = true;
		onaddrlat = mapParam.onaddrlat;
		onaddrlng = mapParam.onaddrlng;
	}
	if($("#ordertype").val() == "2" && null != onAddress && "" != onAddress) { //接机
		onAddress = $("#onAddress").find("option:selected").text();
		flag = true;
		onaddrlat = $("#onAddress").find("option:selected").attr("lat");
		onaddrlng = $("#onAddress").find("option:selected").attr("lng");
	}
	
	if(flag) {
		var onPoint = new BMap.Point(onaddrlng, onaddrlat);
		var icon = new BMap.Icon("img/orgordermanage/icon_shangche.png",
				new BMap.Size(48, 48));
		var startmarker = new BMap.Marker(onPoint, {
			icon : icon
		});
		onMap.addOverlay(startmarker);
		var content = "<p>" + onAddress + "</p>";
		var startinfoWindow = new BMap.InfoWindow(content);
		startmarker.addEventListener("click", function() {
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
	var offaddrlat = 0;
	var offaddrlng = 0;
	var flag = false;
	if (null != mapParam.offaddrlng && mapParam.offaddrlng > 0
			&& null != mapParam.offaddrlat && mapParam.offaddrlat > 0
			&& null != offAddress && "" != offAddress) {
		flag = true;
		offaddrlat = mapParam.offaddrlat;
		offaddrlng = mapParam.offaddrlng;
	}
	if($("#ordertype").val() == "3" && null != offAddress && "" != offAddress) { //送机
		offAddress = $("#offAddress").find("option:selected").text();
		flag = true;
		offaddrlat = $("#offAddress").find("option:selected").attr("lat");
		offaddrlng = $("#offAddress").find("option:selected").attr("lng");
	}
	
	if(flag) {
		var offPoint = new BMap.Point(offaddrlng, offaddrlat);
		var icon = new BMap.Icon("img/orgordermanage/icon_xiache.png",
				new BMap.Size(48, 48));
		var endmarker = new BMap.Marker(offPoint, {
			icon : icon
		});
		offMap.addOverlay(endmarker);
		var content = "<p>" + offAddress + "</p>";
		var endinfoWindow = new BMap.InfoWindow(content);
		endmarker.addEventListener("click", function() {
			offMap.openInfoWindow(endinfoWindow, offPoint);
		});
	}
}

/**
 * 弹出提示窗
 */
function showHint(content, isShowCancel, sureFunc) {
	$(".popup_hint .popup_content").text(content);
	if (isShowCancel) {
		$(".popup_hint .popup_footer .cancel").show();
	} else {
		$(".popup_hint .popup_footer .cancel").hide();
	}
	if (!sureFunc) {
		sureFunc = function() {
			$('.popup_hint').hide();
			$("#window").hide();
		}
	}
	$('.popup_hint .popup_footer .sure').unbind();
	$('.popup_hint .popup_footer .cancel').unbind();
	$(".popup_footer .cancel").click(function() {
		$(".popup_hint").hide();
		$("#window").hide();
	});
	$('.popup_hint .popup_footer .sure').click(sureFunc);
	$(".popup_ditu").hide();
	$(".popup_noaddress").hide();
	$(".popup_hint").show();
	$("#window").show();
}

function initMapActive(){
	//	地图弹窗
	$(".ditu_1,.ditu_2").click(function () {
		$("#suggest").val("");
		if($(this).attr("class") == "ditu_1"){
			var oncity = $("#onCity").next().val();
			if(null == oncity || oncity == "") {
					return;
			}
			$(".popup_ditu").attr("data-owner","onAddress");
			mapParam.currentAct = "onAddress";
			moveMap(oncity,$("#onAddress").val());
			showOffaddress(map);
			mapParam.suggest.setLocation(oncity);
		}else{
			var offcity = $("#offCity").next().val();
			if(null == offcity || offcity == "") {
					return;
			}
			$(".popup_ditu").attr("data-owner","offAddress");
			mapParam.currentAct = "offAddress";
			moveMap(offcity,$("#offAddress").val());
			showOnaddress(map);
			mapParam.suggest.setLocation(offcity);
		}
		$(".popup_hint").hide();
		$(".popup_noaddress").hide();
		$(".popup_ditu").show();
		$("#window").show();
		$("#userinfo").hide();
	})
	$(".popup_ditu .cancel").click(function(){
		$(".popup_ditu").hide();
		$("#window").hide();
	});
	$(".popup_noaddress .sure").click(function(){
	  $("#popup_noaddress").hide();
	  $("#window").hide();
   });
	$(".popup_ditu .sure").click(function(){
		if(null == suggestInit || "" == suggestInit || suggestInit != $("#suggest").val()) {
			$(".popup_ditu").hide();
    		$(".popup_hint").hide();
    		$(".popup_noaddress").show();
			$("#window").show();
			return;
		}
		if($(".popup_ditu").attr("data-owner") == "onAddress"){
			suggestCost = true;
			checkCartype(mapParam.city.id);
			if(suggestCost == false) {
				toastr.error("该城市不提供服务", "提示");
				return;
			}
			suggestCost = false;
			$("#onCity").val(mapParam.city.id);
			$("#onCity").next().val(mapParam.city.city);
			changeCity(mapParam.city.city,$("#onAddress")[0]);
			params.onLat = mapParam.onaddrlat;
			params.onLng = mapParam.onaddrlng;
			params.onAddress = $("#suggest").val();
			getCarTypes();
			$("#onAddress").focus();
		}else{
			$("#offCity").val(mapParam.city.id);
			$("#offCity").next().val(mapParam.city.city);
			changeCity(mapParam.city.city,$("#offAddress")[0]);
			params.offLat = mapParam.offaddrlat;
			params.offLng = mapParam.offaddrlng;
			params.offAddress = $("#suggest").val();
			$("#offAddress").focus();
		}
	    //使用setInputValue方法不会再次触发下拉搜索
		$(".popup_ditu").hide();
		$("#window").hide();
		if($("#suggest").val() == "") return;
	    mapParam[$(".popup_ditu").attr("data-owner")].setInputValue($("#suggest").val());
		$("#suggest").val("");
		getCost();
	});
}

function initLeaseActive(){
	// 服务车企选择控件鼠标移入动画
	$("#lease").mouseover(function() {
		$("#companyId").focus();
		$("#lease ul").show();
	});
	// 服务车企选择控件鼠标移出动画
	$("#lease").mouseout(function() {
		$("#lease ul").hide();
	});
	// 更换服务车企时重新加载车型
	$("#companyId").change(function(){
		openElement(2);
		getCarTypes();
	});
}

function initFavActive(){
	//	常用联系人复选框点击事件
	$(":checkbox").click(function () {
		if($(this).is(":checked") ){
			addMostContact($("#passengers").val(), $("#passengerPhone").val());
			$(this).parent().find(".checkbox_ie").css("background-image","url(../img/btn_yggldxk_pre.png)")
		}else{
			$(this).parent().find(".checkbox_ie").css("background-image","url(../img/btn_yggldxk.png)")
		}
	});
	$("#passengers,#passengerPhone").blur(function(){
		if($("#passengers").val() != "" && $("#passengePhone").val() != ""){
			openElement(3);
		}
	});
	$("#passengers").keyup(function(){
		getMostContact();
	});
	$("#passengers").parent().mouseover(function(){
		$("#passengers").focus();
		$(this).find("ul").show();
	});
	$("#passengers").parent().mouseleave(function(){
		$(this).find("ul").hide();
	});
}

function initAddressActive(){
	var count = 0;
	$("#onAddress,#offAddress").focus(function() {
		mapParam.currentAct = $(this).attr("id");
	});
	if($("#ordertype").val() == "2"){
		$("#fltno,#fallTime,#onAddress,#offAddress").blur(function(){
			if($("#fltno").val() != "" && $("#fallTime").val() != "" && 
				$("#onAddress").val() != "" && $("#offAddress").val() != ""){
				openElement(4);
			}
		});
	}else{
		// 上下车地址变更后,重新获取预估
		$("#onAddress,#offAddress").blur(function(){
			if($("#onAddress").val() != "" && $("#offAddress").val() != ""){
				openElement(4);
			}
		});
	}
}

function initVtypeActive(){
	// 用车事由选择控件鼠标移入动画
	$("#vehiclessubjectType").parent().mouseover(function() {
		$("#vehiclessubjectType").focus();
		$("#vehiclessubjectType").parent().find("ul").show();
	});
	// 用车事由选择控件鼠标移出动画
	$("#vehiclessubjectType").parent().mouseout(function() {
		$("#vehiclessubjectType").parent().find("ul").hide();
	});
	$("#vehiclessubjectType,#vehiclesSubject").blur(function(){
		if($("#vehiclessubjectType").val() != "" && $("#vehiclesSubject").val() != ""){
			openElement(5);
		}
	});
	$("#tripRemark").blur(function(){
		if($("#tripRemark").val() != "") openElement(6);
	});
}

function initCarTypeActive(){
	$("#carTypeList").empty();
	for(var i in params.cartypes){
		var item = $("<div>").addClass("item");
		var item_title = $("<div>").addClass("item_title").text(params.cartypes[i].model);
		var qibujia = $("<div>").addClass("qibujia").append($("<span>").addClass("price_car").text(params.cartypes[i].startprice)).append("起步价");
		var gongli = $("<div>").addClass("gongli").append($("<span>").text(params.cartypes[i].rangeprice)).append("元/公里");
		var fenzhong = $("<div>").addClass("fenzhong").append($("<span>").text(params.cartypes[i].timeprice)).append("元/分钟");
		var item_car = $("<img>").addClass("item_car").attr("src",params.cartypes[i].img).error(function(){$(this).attr("src","content/img/bg_shushixing.png")});
		var item_active = $("<div>").addClass("item_active");
		
		item.append(item_title);
		item.append(qibujia);
		item.append(gongli);
		item.append(fenzhong);
		item.append(item_car);
		item.append(item_active);
		$("#carTypeList").append(item);
	}
	$("#carTypeList .item").click(function(){
		$("#carTypeList .item_active").hide();
		$(this).find(".item_active").show();
		$("#carType").val(params.cartypes[$(this).index()].id);
		getCost();
	});
}

function initPayMethodActive(){
	//	支付方式单选按钮点击事件
	$(":radio").click(function () {
		if( $(this).is(":checked") ){
			$(this).parent().find(".radio_ie").css("background-image","url(../img/btn_danxuan_pre.png)");
			$(this).parent().siblings().find(".radio_ie").css("background-image","url(../img/btn_danxuan.png)");
		}else{
			$(this).parent().find(".radio_ie").css("background-image","url(../img/btn_danxuan.png)");
			$(this).parent().siblings().find(".radio_ie").css("background-image","url(../img/btn_danxuan_pre.png)")
		}
	});
}
/**
 * 初始化页面元素
 */
function initElement() {
	// ie浏览器
	if (navigator.appName == "Microsoft Internet Explorer") {
		// placeholder属性的兼容性问题处理
		// placeholder($(".textarea_box").eq(0), "用车事由说明");
		// placeholder($(".textarea_box").eq(1), "填写行程内容");
	}
	// 下单按钮点击事件
	$(".start_btn").click(createOrder);
	initLeaseActive();
	initFavActive();
	initMapActive();
	initAddressActive();
	initVtypeActive();
	initPayMethodActive();
	initTimeEvent();
	
}

/**
 * 更改时间时获取预估费用
 */
function initTimeEvent(){
	$("#useTime").change(function(){
		getCost();
	});
}

/**
 * 获取城市列表
 */
function getCities() {
	$.ajax({
		type : 'POST',
		url : "Order/GetCities",
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.status == 0 && data.count > 0) {
				params.cities = data.cities;
			} else if (data.status != 0) {
				// alert(data.message);
			}
		},
		contentType : "application/json"
	});
}

/**
 * 获取业务城市
 */
function getBusCities(){
	$(".citySelector").remove();
	$(".cityinput").remove();
	$("#onCity").attr("name", "oncity");
	$("#offCity").attr("name", "offcity");
	
	var ordertype = $("#ordertype").val();
	var companyid = $("#companyId").attr("data-value");
	if(null == companyid || companyid == "") {
		initCitySelect();
		return;
	}
	var data = {
		ordertype: ordertype,
		companyid: companyid
	};
	$.ajax({
	    type: 'POST',
	    url: "Order/GetOrgUserPubBusCity" ,
	    data: JSON.stringify(data),
	    dataType: "json",
	    success: function(data){
	    	params.buscities = data.cities;
    		initCitySelect();
	    },
	    contentType:"application/json"
	});
}

/**
 * 初始化城市选择控件
 */
function initCitySelect() {
	var defaultonCity = "";
	if(null != params.buscities && params.buscities.length > 0) {
		defaultonCity = params.buscities[0].city;
	}
	
	var defaultoffCity = "";
	if(null != defaultonCity && defaultonCity != "") {
		defaultoffCity = defaultonCity;
	} else {
		if(null != params.cities && params.cities.length > 0) {
			defaultoffCity = params.cities[0].city;
		}
	}
	
	params.onCity = new Vcity.CitySelector({
		id : 'onCity',
		// url:'Order/GetCities',
		allCity : params.buscities,
		defaultCity:defaultonCity,
		showInitcial : false,
		fnCallBack : function(city) {
			// 如果是接机送机类型则切换机场地址
			if ($("#ordertype").val() == "2") {
				changeAirPortByCity();
				resetCost();
			} else {
				changeCity(city.city, $("#onAddress")[0]);
				resetCost();
			}
			// 先切换地址再获取车型
			getCarTypes();
		}
	});
	params.offCity = new Vcity.CitySelector({
		id : 'offCity',
		// url:'Order/GetCities',
		allCity : params.cities,
		defaultCity:defaultoffCity,
		showInitcial : false,
		fnCallBack : function(city) {
			// 如果是接机送机类型则切换机场地址
			if ($("#ordertype").val() == "3") {
				changeAirPortByCity();
			} else {
				changeCity(city.city, $("#offAddress")[0]);
				resetCost();
			}
		}
	});
}

/**
 * 获取全国机场信息
 */
function getAirPorts() {
	$.ajax({
		url : "Order/GetAirPorts",
		async : false,
		success : function(result) {
			if (result.status == 0) {
				params.airports = result.airports;
			}
		},
		error : function(msg) {
		}
	});
}

/**
 * 重置预估文本
 */
function resetCost() {
	$(".price").text("￥0.0");
	$(".price_info").text("预估里程0公里,预估时长0分钟");
}

/**
 * 获取服务车企列表
 */
function getLeaseList() {
	$.ajax({
	    type: 'POST',
	    url: "Order/GetLeaseList" ,
	    dataType: "json",
	    success: function(data){
	    	if(data.status == 0 && data.count > 0){
	    		params.lease = data.lease;
	    		$("#lease ul").empty();
	    		for(var i in params.lease){
	    			var li = $("<li>").attr("data-value",params.lease[i].id).text(params.lease[i].shortName);
		    		$("#lease ul").append(li);
	    		}
	    		$("#lease ul li").click(function(){
	    			if($(this).attr("data-value") != $("#companyId").attr("data-value")){
	    				$("#companyId").attr("data-value",$(this).attr("data-value")); //先赋值
	    				$("#companyId").val($(this).attr("data-value")); //先赋值
	    				getBusCities();
	    				$("#onAddress").val("");
	    		    	$("#offAddress").val("");
	    				resetCost();
	    				getCarTypes();
	    				openElement(2);
	    			}
	    			$("#lease ul").hide();
	    		});
//	    		$("#lease ul li").eq(0).click();
	    	}else if(data.status != 0){
//	    		alert(data.message);
	    	}
	    },
	    contentType:"application/json"
	});
}

/**
 * 添加常用联系人
 * 
 * @param name
 * @param phone
 */
function addMostContact(name, phone) {
	if(!name || !phone || name == "" || phone == "") return;
	for(var i in params.mostcontact){
		//如果联系人已存在则不添加
		if(params.mostcontact[i].name == name && params.mostcontact[i].phone == phone){
			return;
		}
	}
	var reg = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
	if(!reg.test(phone)) {
		$(":checkbox").click();
		toastr.error("请正确填写乘车人电话","提示");
		return;	
	}
	var data = {
		name:name,
		phone:phone
	};
	$.ajax({
	    type: 'POST',
	    url: "Order/AddMostContact" ,
	    data: JSON.stringify(data),
	    dataType: "json",
	    success: function(data){
	    	if(data.status == 0){
	    		getMostContact();
	    	}else{
//	    		alert(data.message);
	    	}
	    },
	    contentType:"application/json"
	});
}

/**
 * 删除常用联系人
 * 
 * @param id
 */
function delMostContact(id) {
	if(!id || id == "") return;
	var data = {
		id:id
	}
	$.ajax({
	    type: 'POST',
	    url: "Order/DelMostContact" ,
	    data: JSON.stringify(data),
	    dataType: "json",
	    success: function(data){
	    	if(data.status == 0){
	    		$("#passengers,#passengerPhone").val("");
    			getMostContact();
	    	}else if(data.status != 0){
//	    		alert(data.message);
	    	}
	    },
	    contentType:"application/json"
	});
}

/**
 * 获取常用联系人
 */
function getMostContact() {
	var data = {};
	if($("#passengers").val() != ""){
		data.sSearch = $("#passengers").val();
	}
	$.ajax({
	    type: 'POST',
	    url: "Order/GetMostContact" ,
	    dataType: "json",
	    data:JSON.stringify(data),
	    success: function(result){
	    	if(result.status == 0 && result.count > 0){
	    		params.mostcontact = result.mostcontact;
	    		$("#passengerDiv").mouseout(function(){
	    			$("#passengerDiv ul").hide();
	    		});
	    		$("#passengerDiv ul").empty();
	    		for(var i in params.mostcontact){
	    			var name = $("<span>").text(params.mostcontact[i].name);
	    			var phone = $("<span>").text(params.mostcontact[i].phone);
	    			var li = $("<li>").attr("data-value",params.mostcontact[i].id).append(name).append($("<br>")).append(phone);
	    			if(params.mostcontact[i].id != ""){
	    				li.append($("<i>"));
	    			}
		    		$("#passengerDiv ul").append(li);
	    		}
	    		$("#passengerDiv li").mousedown(function(){
	    			return false;
	    		});
        		$("#passengerDiv li").click(function(){
        			var name = $(this).find("span").eq(0).text();
        			var phone = $(this).find("span").eq(1).text();
        			$("#passengers").attr("data-value",name);
        			$("#passengers").val(name);
        			$("#passengerPhone").attr("data-value",phone);
        			$("#passengerPhone").val(phone);
        			$("#passengerDiv ul").hide();
        		});
        		$("#passengerDiv li i").click(function(){
        			var name = params.mostcontact[$(this).parent().index()].name;
        			var id = params.mostcontact[$(this).parent().index()].id;
    	    		$(".popup_hint .popup_content").text("确定删除常用联系人 "+name+"？");
    	    		$('.popup_hint .popup_footer .cancel').unbind("click");
    	    		$('.popup_hint .popup_footer .cancel').click(function(){
    	    			$('.popup_hint').hide();
    	    			$("#window").hide();
    	    		});
    	    		$(".popup_hint .popup_footer .cancel").show();
    	    		$('.popup_hint .popup_footer .sure').unbind("click");
    	    		$('.popup_hint .popup_footer .sure').click(function(){
    	    			$('.popup_hint').hide();
    	    			$("#window").hide();
    	    			delMostContact(id);
    	    		});
    	    		$(".popup_ditu").hide();
    	    		$(".popup_noaddress").hide();
    	    		$(".popup_hint").show();
    	    		$("#window").show();
        		});
	    	}else if(result.status != 0){
//	    		alert(data.message);
	    	}
	    },
	    contentType:"application/json"
	});
}

/**
 * 获取常用地址
 */
function getMostAddress() {
	$.ajax({
		type : 'POST',
		url : "Order/GetMostAddress",
		dataType : "json",
		success : function(data) {
			if (data.status == 0 && data.count > 0) {
				params.mostaddress = data.mostaddress;
			} else if (data.status != 0) {
				// alert(data.message);
			}
		},
		contentType : "application/json"
	});
}

/**
 * 获取用车事由
 */
function getUseCarReason() {
	$.ajax({
		type : 'POST',
		url : "Order/GetUseCarReason",
		dataType : "json",
		success : function(data) {
			if (data.status == 0 && data.count > 0) {
				params.usecarreason = data.usecarreason;
				var ul = $("#vehiclessubjectType").parent().find("ul");
				ul.empty();
				for ( var i in params.usecarreason) {
					var li = $("<li>").attr("data-value",params.usecarreason[i].id).text(params.usecarreason[i].text);
					ul.append(li);
				}
				$("#vehiclessubjectType").parent().find("ul li").click( function() {
					$("#vehiclessubjectType").parent().find("ul").hide();
				});
			} else if (data.status != 0) {
				// alert(data.message);
			}
		},
		contentType : "application/json"
	});
}
/**
 * 获取车型
 */
function getCarTypes(){
	var data = {
			companyid:$("#companyId").attr("data-value"),
			city:$("#onCity").val(),
			ordertype:$("#ordertype").val()
	};
	$.ajax({
	    type: 'POST',
	    url: "Order/GetCarTypes" ,
	    data: JSON.stringify(data),
	    dataType: "json",
	    success: function(data){
	    	if(data.status == 0){
	    		params.cartypes = data.cartypes;
    			if(data.count > params.cartypemin){
    				$(".left,.right").show();
    			}else{
    				$(".left,.right").hide();
    			}
	    		initCarTypeActive();
	    		initCarTypeAnimate();
	    		if(params.order){
		    		$.each(params.cartypes,function(index,obj){
		    			if(obj.id == params.order.selectedmodel){
		    				$("#carType").val(params.order.selectedmodel);
		    				params.cartypeindex = index;
		    				$("#carTypeList .item").each(function(){
		    					if($(this).index() < index && $(this).index() < (params.cartypes.length - params.cartypemin)){
		    						$(this).hide();
		    					}
		    				});
		    				$("#carTypeList .item").eq(index).click();
		    			}
		    		});
	    		}else{
	    			//加载车型后默认选择第一个
	    			$("#carTypeList .item").eq(0).click();
	    		}
	    	}else if(data.status != 0){
	    		
	    	}
	    },
	    contentType:"application/json"
	});
}

/**
 * 验证城市是否有业务
 */
function checkCartype(city){
	var data = {
		companyid:$("#companyId").attr("data-value"),
		city:city,
		ordertype:$("#ordertype").val()
	};
	$.ajax({
	    type: 'POST',
	    url: "Order/GetOrgUserPubBusCity",
	    async: false,
	    data: JSON.stringify(data),
	    dataType: "json",
	    success: function(data){
	    	if(data.status == 0){
    			if(suggestCost && $(".popup_ditu").attr("data-owner") == "onAddress" && data.count == 0) {
    				suggestCost = false;
        		}
	    	}else if(data.status != 0){
	    		if(suggestCost && $(".popup_ditu").attr("data-owner") == "onAddress") {
	    			suggestCost = false;
        		}
	    	}
	    },
	    contentType:"application/json"
	});
}

/**
 * 车型切换动画效果渲染
 */
function initCarTypeAnimate() {
	params.cartypeindex = 0;
	$(".left img").unbind("click");
	$(".right img").unbind("click");
	$(".left img").click(function() {
		if (params.cartypeindex <= 0) {
			params.cartypeindex = 0;
			return;
		}
		params.cartypeindex = params.cartypeindex - 1;
		$("#carTypeList .item").eq(params.cartypeindex).show(250, function() {
			$(this).click();
		});
	});
	$(".right img").click(function() {
		if (params.cartypeindex >= params.cartypes.length - params.cartypemin) {
			params.cartypeindex = params.cartypes.length - params.cartypemin;
			return;
		}
		$("#carTypeList .item").eq(params.cartypeindex).hide(250);
		$("#carTypeList .item").eq(params.cartypeindex + 1).click();
		params.cartypeindex = params.cartypeindex + 1;
	});
}

/**
 * 获取预估费用
 */
function getCost() {
	params.onLng = mapParam.onaddrlng;
	params.onLat = mapParam.onaddrlat;
	params.offLng = mapParam.offaddrlng;
	params.offLat = mapParam.offaddrlat;
	if ($("#ordertype").val() == 2) {
		params.onLng = parseFloat($("#onAddress option:selected").attr("lng"));
		params.onLat = parseFloat($("#onAddress option:selected").attr("lat"));
	} else if ($("#ordertype").val() == 3) {
		params.offLng = parseFloat($("#offAddress option:selected").attr("lng"));
		params.offLat = parseFloat($("#offAddress option:selected").attr("lat"));
	}
	var data = {
		cartype : $("#carType").val(),
		companyid : $("#companyId").attr("data-value"),
		usetime:$("#useTime").val(),
		city : $("#onCity").val(),
		ordertype : $("#ordertype").val(),
		rulestype : '1',
		onAddress : $("#onAddress").val(),
		offAddress : $("#offAddress").val(),
		paymethod : $("#paymethod").val(),
		onaddrlng : params.onLng,
		onaddrlat : params.onLat,
		offaddrlng : params.offLng,
		offaddrlat : params.offLat
	};
	if (data.onaddrlng == 0 || data.onaddrlat == 0 || data.offaddrlng == 0
			|| data.offaddrlat == 0 || data.onAddress == ""
			|| data.offAddress == "" || $("#carTypeList").is(":hidden") || data.cartype == "") {
		resetCost();
		return;
	}
	if (data.companyid == "") {
		resetCost();
		toastr.error("请选择服务车企", "提示");
		return;
	}
	if (data.cartype == "") {
		resetCost();
		toastr.error("请选择车型", "提示");
		return;
	}
	$.ajax({
		type : 'POST',
		url : "Order/GetOrgOrderCost",
		data : JSON.stringify(data),
		dataType : "json",
		success : function(data) {
			if (data.status == 0) {
				if(data.reversefee != "0元"){
					$("#reversefee").text(data.reversefee);
					$("#reversefeeSpan").show();
				}else{
					$("#reversefeeSpan").hide();
				}
//				if(data.couponprice != "0元"){
//					$("#couponpriceSpan").show();
//					$("#couponprice").text(data.couponprice);
//					//优惠券金额需要页面单独计算
//					data.cost = data.cost.replace("元","") - data.couponprice.replace("元","") + "元";
//				}else{
//					$("#couponpriceSpan").hide();
//				}
				if (!data.payable) {
					var content = "机构账户余额不足,不可继续下单。";
					var sureStr = "我知道了";
					$('.popup_hint .popup_footer .sure').html(sureStr);
					showHint(content, false, function(){
						window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "User/Index/";
					});
				}else{  //如果余额足够,则还原提示按钮
					$('.popup_hint .popup_footer .sure').html("确定");
				}
				$(".price_info").text("预估里程" + data.mileage + ",预估时长" + data.times);
				$(".price").text("￥" + data.cost);
			} else {
				// alert(data.message);
			}
		},
		contentType : "application/json"
	});
}

/**
 * 提交表单
 */
function createOrder() {
	if (!$("#form").valid())
		return;
	var data = {
		selectedmodel : $("#carType").val(),
		companyid : $("#companyId").attr("data-value"),
		usetype : '0',
		ordertype : $("#ordertype").val(),
		rulestype : '1',
		passengers : $("#passengers").val(),
		passengerphone : $("#passengerPhone").val(),
		usetime : $("#useTime").val(),
		paymethod : $("#paymethod input[type='radio']:checked").val(),
		vehiclessubjecttype : $("#vehiclessubjectType").attr("data-value"),
		vehiclessubject : $("#vehiclesSubject").val(),
		tripremark : $("#tripRemark").val(),
		oncity : $("#onCity").val(),
		offcity : $("#offCity").val(),
		onaddress : $("#onAddress").val(),
		offaddress : $("#offAddress").val(),
		onaddrlng : params.onLng,
		onaddrlat : params.onLat,
		offaddrlng : params.offLng,
		offaddrlat : params.offLat
	};
	if($("#ordertype").val() == "2"){
		data.fltno = $("#fltno").val();
		data.falltime = $("#fallTime").val();
	}
	$.ajax({
		type : 'POST',
		url : "Order/CreateOrgOrder",
		data : JSON.stringify(data),
		dataType : "json",
	    beforeSend: function () {
	        // 禁用按钮防止重复提交
	    	$(".start_btn").unbind();
	    },
	    complete: function () {
	    	$(".start_btn").click(createOrder);
	    },
		success : function(data) {
			if (data.status == 0) {
				window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "Order/Success/" + data.orderno;
			} else if (data.status == 2004) {
				var content = "您有订单未支付，现在不能下单，请完成支付后再进行下单。";
				// var sureFunc = function(){
				// alert("这里应该跳转到支付页面.");
				// };
				var sureFunc = null;
				showHint(content, true, sureFunc);
			} else if (data.status == 2001) {
				var content = "您没有用车权限，请和管理员联系。";
				showHint(content, false);
			} else if(data.status == 2009){
				var content = "机构账户余额不足,不可继续下单。";
				var sureStr = "我知道了";
				$('.popup_hint .popup_footer .sure').html(sureStr);
				showHint(content, false, function(){
					window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "User/Index/";
				});
			} else {
				$('.popup_hint .popup_footer .sure').html("确定");
				showHint(data.message, false);
			}
		},
		contentType : "application/json"
	});
}

/**
 * 接机送机根据城市切换机场
 */
function changeAirPortByCity() {
	var type;
	if ($("#ordertype").val() == 2)
		type = 0;
	else if ($("#ordertype").val() == 3)
		type = 1;
	var cityid = type == 0 ? $("#onCity").val() : $("#offCity").val();
	type == 0 ? $("#onAddress").empty() : $("#offAddress").empty();
	for ( var key in params.airports) {
		if (params.airports[key].city == cityid) {
			var option = document.createElement("option");
			$(option).attr("value", params.airports[key].name);
			$(option).attr("selected", "selected");
			$(option).attr("lng", params.airports[key].lng);
			$(option).attr("lat", params.airports[key].lat);
			$(option).attr("type", type);
			$(option).text(params.airports[key].name);
			if (type == 0) {
				$("#onAddress").append(option);
				params.onLng = params.airports[key].lng;
				params.onLat = params.airports[key].lat;
			} else {
				$("#offAddress").append(option);
				params.offLng = params.airports[key].lng;
				params.offLat = params.airports[key].lat;
			}
			$(option).click(function() {
				if ($(this).attr("type") == 0) {
					params.onLng = $(this).attr("lng");
					params.onLat = $(this).attr("lat");
				} else {
					params.offLng = $(this).attr("lng");
					params.offLat = $(this).attr("lat");
				}
			});
		}
	}
}

/**
 * 重置表单
 */
function resetForm() {
	$("#form")[0].reset();
}

/**
 * 验证表单函数
 */
function validData() {
	var rules = {
		companyid : {
			required : true
		},
		passengers : {
			required : true
		},
		passengerphone : {
			required : true,
			isMobile : true
		},
		usetime : {
			required : true
		},
		onaddress : {
			required : true
		},
		offaddress : {
			required : true
		},
		vehiclessubject : {
			required : true
		}
	};
	var messages = {
		companyid : {
			required : "请选择服务车企"
		},
		passengers : {
			required : "请选择乘车人"
		},
		passengerphone : {
			required : "请输入乘车人电话",
			isMobile : "请输入正确的乘车人电话"
		},
		usetime : {
			required : "请选择用车时间"
		},
		onaddress : {
			required : "请输入上车地址"
		},
		offaddress : {
			required : "请输入下车地址"
		},
		vehiclessubject : {
			required : "请输入用车事由"
		}
	};
	if ($("#ordertype").val() == "2") {
		rules.fltno = {
			required : true,
			isFltNum : true
		};
		rules.falltime = {
			required : true
		};
		messages.fltno = {
			required : "请输入航班号",
			isFltNum : "请正确填写航班号"
		};
		messages.falltime = {
			required : "请输入降落时间"
		};
	}
	$("#form").validate({
		ignore : "",
		rules : rules,
		messages : messages
	});
}

/**
 * 序列化form表单
 */
function serializeObject() {
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = "" + [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
	// 追加手机号码验证
	$.validator.addMethod("isMobile",function(value, element) {
			var length = value.length;
			var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
			return this.optional(element) || (length == 11 && mobile.test(value));
	}, "请正确填写您的手机号码");

	// 追加航班号验证
	$.validator.addMethod("isFltNum", function(value, element) {
		var maxlen = $(element).attr("maxlength");
		maxlen = maxlen == 0 ? 6 : maxlen;
		var length = value.length;
		var fltno = /^[\w\d]{6}$/;
		return this.optional(element) || (length == maxlen && fltno.test(value));
	}, "请正确填写您的航班号");
	// 初始化表单验证组件
	validData();
}

/**
 * 时间戳格式化
 * @param time
 * @param format
 * @returns {String}
 */
function timeStamp2String(time, format){
	var mark = "-";
	if(null != format && format.length > 0) {
		mark = format;
	}
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
	var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	return year + mark + month + mark + date+" "+hour+":"+minute;
}
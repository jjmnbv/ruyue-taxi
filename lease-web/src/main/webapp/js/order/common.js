var params = {
	isNullName:false,
	isNewWord:false,
	passengers:null,
	passengerPhone:null,
	text:null,
	userid:null,
	organid:null,
	airports:null,
	cities:null,
	onLat : 0,
	onLng : 0,
	onAddress: null,
	offLat : 0,
	offLng : 0,
	offAddress: null,
	cartypes:null,
	cartypeindex:0,
	cartypemin:4,      //车型最小显示个数
	paymethod:0
};
$(function() {
	validData();
	initSerializeForm();
	initSelect2Active();
	getCities();
	getBusCities();
	initOrgUserGrid();
	initFavUserGrid();
    initManualSelectDriver();
	initEvent();
	initOrgan();
    if($(".paginate_button").length>2){
        // var turnpage='<div class="turnpage"> 到第 <input type="number"/> 页 <a data-dt-idx="3" tabindex="0" aria-controls="example"  class="Mbtn red">确定</a></div>';
        //$(".dataTables_paginate").append(turnpage);
        // var s='<a class="paginate_button " aria-controls="example" data-dt-idx="2" tabindex="0">7</a>';
        // $(".dataTables_paginate span").append(s);
    }
    if($("#ordertype").val() > 1){
        getAirPorts();
	}
    setTimeout('disableForm("form",true)',500);


    $("[name=driverModeType]").on("click", function(){
        var mode = $(this).val();
        if(mode == 0){
            $("#manualSelectDriver").addClass("hidden");
            $("#manualSelectDriverInput").rules("remove");
            $("#manualSelectDriverInput").select2("data", {
                id : "",
                text : ""
            });
        } else {
            $("#manualSelectDriver").removeClass("hidden");
            $("#manualSelectDriverInput").rules("add", {required: true});
        }
    });

});

function disableForm(formId,isDisabled){
    $("form[id='"+formId+"'] textarea").attr("disabled",isDisabled); 
    $("form[id='"+formId+"'] select").attr("disabled",isDisabled); 
    $("form[id='"+formId+"'] :radio").attr("disabled",isDisabled); 
    $("form[id='"+formId+"'] :checkbox").attr("disabled",isDisabled);
    $("#onCity").next().attr("disabled", isDisabled);
    $("#offCity").next().attr("disabled", isDisabled);
    $("#passengers,#passengerphone").select2("enable", !isDisabled);
    $("#usetime,#falltime,#fltno").attr("disabled", isDisabled);
    $("#onAddress,#offAddress").attr("disabled",isDisabled);
    $("#usetype input[type='radio']").attr("disabled",false);
    $("#manualSelectDriverInput").select2("enable", !isDisabled);
	//按钮在表单禁用时,使用另外的逻辑
	if(isDisabled){
		  for(var i = 1;i<=4;i++){
			  $("button").eq(i).unbind();
			  $("button").eq(i).click(function(){
				  $("#nouserid").show();
			  });
		  }
		  $("#sub").unbind();

        $("#manualSelectDriver button").unbind().click(function(){
            $("#nouserid").show();
        });
	}else{
		  $("#sub").unbind();
		  $("#sub").click(createOrder);
		  $("button").eq(1).unbind();
		  $("button").eq(1).click(function(){
			  $("#favorite_search").click();
			  $(".favorite").show(); 
		  });
		  $("button").eq(2).unbind();
		  $("button").eq(2).click(function(){
			  $("#favorite_search").click();
			  $(".favorite").show(); 
		  });
		  if($(".onMap")[0]){
			  $(".onMap").unbind();
			  $(".onMap").click(function(){
				  var oncity = $("#onCity").next().val();
				  if(null == oncity || oncity == "") {
					  return;
				  }
				  suggestInit = null;
				  $("#suggest").val("");
				  mapParam.currentAct = "onAddress";
				  if($("#ordertype").val() == 2){
					  moveMap(oncity,$("#onAddress").text());
				  }else{
					  moveMap(oncity,$("#onAddress").val());
				  }
				  $("#map").attr("data-owner","onAddress");
				  showOffaddress(map);
				  $("#map").show();
			  });
		  }
		  if($(".offMap")[0]){
			  $(".offMap").unbind();
			  $(".offMap").click(function(){
				  var offcity = $("#offCity").next().val();
				  if(null == offcity || offcity == "") {
					  return;
				  }
				  suggestInit = null;
				  $("#suggest").val("");
				  mapParam.currentAct = "offAddress";
				  if($("#ordertype").val() == 3){
					  moveMap(offcity,$("#offAddress").text());
				  }else{
					  moveMap(offcity,$("#offAddress").val());
				  }
				  $("#map").attr("data-owner","offAddress");
				  showOnaddress(map);
				  $("#map").show();
			  });
		  }

        $("#manualSelectDriver button").unbind().click(function(){
            initManualSelectPop();
            $("#manualSelectDriverPop").show();
        });

	}
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
		var marker = new BMap.Marker(onPoint, {
			icon : icon
		});
		onMap.addOverlay(marker);
		var content = "<p>" + onAddress + "</p>";
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
		var marker = new BMap.Marker(offPoint, {
			icon : icon
		});
		offMap.addOverlay(marker);
		var content = "<p>" + offAddress + "</p>";
		var endinfoWindow = new BMap.InfoWindow(content);
		marker.addEventListener("click", function() {
			offMap.openInfoWindow(endinfoWindow, offPoint);
		});
	}
}

function formatUserIdResult(state){
	if (!state.id) { return state.text; }
	//取出小括号的内容(包括小括号)
	var arr = state.text.match(/(\().*?(\))/);
	if(arr!=null && arr.length>0){
		var result = arr[0];
		//标红显示
		return state.text.replace(result,"<font style=\"color:red\">"+result+"</font>");
	}else{
		return state.text;
	}
}
/**
 * 初始化select2控件
 */
function initSelect2Active(){
	$("#userid").select2({
		placeholder : "下单人",
		minimumInputLength : 0,
		allowClear : true,
		formatResult: formatUserIdResult,
		formatSelection: formatUserIdResult,
		ajax : {
			type : 'POST',
			url : "Order/GetOrgUserForSelect",
			dataType : 'json',
            params: { //select2的contentType要这样写.否则无法使用requestBody接收参数
            	contentType: "application/json; charset=utf-8"
            },
			data : function(term, page) {
            	//参数必须格式化
            	var data = {
    					sSearch : term,
    					usetype : $("#usetype input[type='radio']:checked").val(),
    					ordertype: $("#ordertype").val()
    			};
            	return JSON.stringify(data);
			},
			results : function(data, page) {
				return {
					results : data
				};
			}
		}
	});
	
	var useridLastResult = [];
	$("#passengers").select2({
		placeholder : "乘车人",
		minimumInputLength : 0,
		allowClear : true,
		ajax : {
			type : 'POST',
			url : "Order/GetFavUserForSelect",
			dataType : 'json',
            params: { //select2的contentType要这样写.否则无法使用requestBody接收参数
            	contentType: "application/json; charset=utf-8"
            },
			data : function(term, page) {
            	//参数必须格式化
            	var data = {
    					sSearch : term,
    					userid : $("#userid").val(),
    					type : 0
    			};
            	return JSON.stringify(data);
			},
			results : function(data, page) {
				useridLastResult = {
					results : data
				};
				return useridLastResult;
			}
		},
		createSearchChoice: function (term) {
	        if(useridLastResult.results.some(function(r) { return r.text == term })) {
	            return {id: term, text: term};
	        }
	        else {
	            return {id: term, text: term+" (新增)" };
	        }
	    }
	});

	$("#passengerphone").select2({
		placeholder : "乘车人电话",
		minimumInputLength : 0,
		allowClear : true,
		ajax : {
			type : 'POST',
			url : "Order/GetFavUserForSelect",
			dataType : 'json',
            params: { //select2的contentType要这样写.否则无法使用requestBody接收参数
            	contentType: "application/json; charset=utf-8"
            },
			data : function(term, page) {
            	//参数必须格式化
            	var data = {
    					sSearch : term,
    					userid : $("#userid").val(),
    					type : 1
    			};
            	return JSON.stringify(data);
			},
			results : function(data, page) {
				useridLastResult = {
					results : data
				};
				return useridLastResult;
			}
		},
		createSearchChoice: function (term) {
	        if(useridLastResult.results.some(function(r) { return r.text == term})) {
	            return {id: term, text: term};
	        }
	        else {
	            return {id: term  , text: term+" (新增)" };
	        }
	    }
	});
	
    $("#userid").on("change", function(e) {
    	resetCost();
    	getCarTypes();
    	getMostAddress();
    	if($("#userid").val() != "") {
    		disableForm("form",false);
    	} else {
	    	disableForm("form",true);
    	} 
    	$("#userid").valid();
    	$("#onAddress").val("");
    	$("#offAddress").val("");
    	getBusCities();
    });
    
    $("#passengers,#passengerphone").on("change",function(){
    	$("#passengers").valid();
    	$("#passengerphone").valid();
    });
    
	$("#passengers").on("select2-close", function() {
		if($("#passengers").select2('data').text){
			if($("#passengers").select2('data').text.indexOf(" ") < 0) return;
			var nickname = $("#passengers").select2('data').text.split(" ")[1];
			var phone = $("#passengers").select2('data').text.split(" ")[0];
			if(nickname != "(新增)"){
				$("#passengerphone").select2("data", {
					id : phone,
					text : phone
				});
			}
			params.isNullName = nickname == "";
			nickname = nickname == "" ? "佚名" : nickname;
			nickname = nickname == "(新增)" ? phone : nickname;
			$("#passengers").select2("data", {
				id : nickname,
				text : nickname
			});	
		}
	});

	$("#passengerphone").on("select2-close", function() {
		if($("#passengerphone").select2('data').text){
			if($("#passengerphone").select2('data').text.indexOf(" ") < 0) return;
			var nickname = $("#passengerphone").select2('data').text.split(" ")[1];
			var phone = $("#passengerphone").select2('data').text.split(" ")[0];
			$("#passengerphone").select2("data", {
				id : phone,
				text : phone
			});	
			params.isNullName = nickname == "";
			nickname = nickname == "" ? "佚名" : nickname;
			if(nickname != "(新增)"){
				$("#passengers").select2("data", {
					id : nickname,
					text : nickname
				});
			}
		}
	});

    $("#manualSelectDriverInput").select2({
        placeholder : "指派司机",
        minimumInputLength : 0,
        allowClear : true,
        ajax : {
            type : 'POST',
            url : "Order/getManualSelectDriverForSelect",
            dataType : 'json',
            params: {
                contentType: "application/json; charset=utf-8"
            },
            data : function(term, page) {
                //参数必须格式化
                var data = {
                    keyword : term
                };
                return JSON.stringify(data);
            },
            results : function(data, page) {
                return {
                    results : data
                };
            }
        }
    });

    $("#manualSelectDriverInput").on("change",function(){
        $("#manualSelectDriverInput").valid();
    });
}

/**
 * 获取所有城市
 */
function getCities() {
	$.ajax({
	    type: 'POST',
	    url: "Order/GetCities" ,
	    dataType: "json",
	    async : false,
	    success: function(data){
	    	if(data.status == 0) {
	    		params.cities = data.cities;
	    	}
	    },
	    contentType:"application/json"
	});
}

/**
 * 获取业务城市列表
 */
function getBusCities(){
	$(".citySelector").remove();
	$(".cityinput").remove();
	$("#onCity").attr("name", "oncity");
	$("#offCity").attr("name", "offcity");
	
	var userid = $("#userid").val();
	var usetype = $("#usetype input[type='radio']:checked").val();
	var ordertype = $("#ordertype").val();
	if(null == userid || userid == "") {
		initCitySelect();
		return;
	}
	var data = {
		userid: userid,
		usetype: usetype,
		ordertype: ordertype
	};
	$.ajax({
	    type: 'POST',
	    url: "Order/GetOrgUserBusCity" ,
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
function initCitySelect(){
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
	
	var onCity = new Vcity.CitySelector({
		id:'onCity',
//		url:'Order/GetCities',
		allCity : params.buscities,
		defaultCity:defaultonCity,
		showInitcial:false,
		fnCallBack:function(city){
			//如果是接机送机类型则切换机场地址
			if($("#ordertype").val() == "2"){
				changeAirPortByCity();
				resetCost();
			}else{
				changeCity(city.city,$("#onAddress")[0]);
				resetCost();
			}
			//先切换地址再获取车型
			getCarTypes();
		}
	});
	var offCity = new Vcity.CitySelector({
		id:'offCity',
//		url:'Order/GetCities',
		allCity : params.cities,
		defaultCity:defaultoffCity,
		showInitcial:false,
		fnCallBack:function(city){
			//如果是接机送机类型则切换机场地址
			if($("#ordertype").val() == "3"){
				changeAirPortByCity();
			}else{
				changeCity(city.city,$("#offAddress")[0]);
				resetCost();
			}
			//先切换地址再获取车型
			getCarTypes();
		}
	});
}

/**
 * 初始化下单人中的机构列表
 */
function initOrgan() {
	$("#organ").val("");
	var usetype = $("#usetype input[type='radio']:checked").val();
	if(usetype == "0") { //因公
		$("#organ").find("option[data='org_pub']").show();
		$("#organ").find("option[data='org_pri']").hide();
	} else { //因私
		$("#organ").find("option[data='org_pub']").hide();
		$("#organ").find("option[data='org_pri']").show();
	}
}

/**
 * 表格初始化
 */
function initOrgUserGrid() {
	var gridObj = {
		id: "orderman",
		sAjaxSource: "Order/GetOrgUser",
		userQueryParam: [
  	       {name: "ordertype", value: $("#ordertype").val()}
  	    ],
	    columns: [
	        {mDataProp: "id", sTitle: "ID", sClass: "hiddenCol", sortable: false},
	        {mDataProp: "organId", sTitle: "ORGANID", sClass: "hiddenCol", sortable: false},
	        {mDataProp: "nickName", sTitle: "姓名", sClass: "center", sortable: false },
	        {mDataProp: "account", sTitle: "账号", sClass: "center", sortable: false },
	        {mDataProp: "orgcaption", sTitle: "机构名称", sClass: "center", sortable: false },
	        {mDataProp: "citycaption", sTitle: "所属城市", sClass: "center", sortable: false },
	        {
                "mDataProp": "xxxx",
                "sClass": "center",
                "sTitle": "是否存在未支付订单",
                "mRender": function (data, type, full) {
                	return full.notpay?"<font color='red'>存在</font>":"不存在";
                }
            }
	    ]
	};
	var orderman = renderGrid(gridObj);
	$("#orderman_confirm").click(function(){
		if(params.passengers || params.passengerPhone){
		    $("#userid").select2("data", {
				id : params.userid,
				text : params.text
			});
			$("#organid").val(params.organid);
			getCarTypes();
			disableForm("form",false);
		}
    	$("#userid").valid();
		orderman.$("tr").removeClass("selected");
		$('.orderman').hide();
		getBusCities();
	});
	
	$("#orderman_cancel").click(function(){
		orderman.$("tr").removeClass("selected");
		$('.orderman').hide();
	});
	
	$("#orderman_search").click(function(){
		gridSearch(orderman,$("#orderman_keyword").val());
	});
	
    $('#orderman tbody').on( 'click', 'tr', function () {
    	if($(this).find("td").length == 1) return;
    	orderman.$('.selected').removeClass('selected');
        $(this).addClass('selected');
	    params.userid = $(this).find("td").eq(0).text();
	    params.organid = $(this).find("td").eq(1).text()
	    params.passengers = $(this).find("td").eq(2).text();
	    params.passengerPhone = $(this).find("td").eq(3).text()
        params.text = params.passengerPhone + " " +  params.passengers;
	    if($(this).find("td").eq(6).text() == "存在") params.text+="(未支付)";
        if($("#userid").next()[0]){
        	$("#userid").next().remove();
        }
	});
}

function gridSearch(grid,sSearch){
	var phone = $("#userid").select2("data").text.substr(0,11);
	sSearch = sSearch == "手机号码/姓名" ? "" : sSearch;
	var userQueryParam = [
     {name: "organid", value: $("#organ").val()},
     /*{name: "userid", value: $("#user input[type='hidden']").eq(0).val()},*/
     {name:"userid",value:phone},
     {name: "usetype", value: $("#usetype :radio:checked").val()},
     {name: "ordertype", value: $("#ordertype").val()},
     {name: "sSearch", value: sSearch}
   ]
	grid.fnSearch(userQueryParam);
}
/**
 * 表格初始化
 */
function initFavUserGrid() {
	var gridObj = {
		id: "favorite",
		sAjaxSource: "Order/GetFavUser",
	    userQueryParam: [
	       {name: "organid", value: $("#organ").val()},
	       {name: "userid", value: $("#user").find(":hidden").eq(0).val()},
	       {name: "usetype", value: $("#usetype :radio:checked").val()}
	    ],
	    columns: [
	       {mDataProp: "name", sTitle: "姓名", sClass: "center", sortable: false},
	       {mDataProp: "phone", sTitle: "手机号码", sClass: "center", sortable: false }
	    ]
	};

	var favorite = renderGrid(gridObj);
	gridObj.userQueryParam = [];
	$("#favorite_search").click(function(){
		gridSearch(favorite,$("#favorite_keyword").val());
	});

	$("#favorite_confirm").click(function(){
		if(params.passengers || params.passengerPhone){
	        $("#passengers").select2("data", {
	        	id : params.passengers,
				text : params.passengers
			});
	        $("#passengerphone").select2("data", {
	        	id : params.passengerPhone,
				text : params.passengerPhone
			});
		}
    	$("#passengers").valid();
    	$("#passengerphone").valid();
        favorite.$("tr").removeClass("selected");
		$('.favorite').hide();
	});
	
	$("#favorite_cancel").click(function(){
		favorite.$("tr").removeClass("selected");
		$('.favorite').hide();
	});
	
    $('#favorite tbody').on( 'click', 'tr', function () {
    	if($(this).find("td").length == 1) return;
    	favorite.$('.selected').removeClass('selected');
        $(this).addClass('selected');
        var nickname = $(this).find("td").eq(0).text();
        var phone = $(this).find("td").eq(1).text();
        nickname = nickname == "" ? "佚名" : nickname; 
        params.passengers = nickname;
        params.passengerPhone = phone;
        params.text = params.passengerPhone + " " +  params.passengers;
        if($("#passengers").next()[0]){
        	$("#passengers").next().remove();
        }
        if($("#passengerphone").next()[0]){
        	$("#passengerphone").next().remove();
        }
    } );
}

/**
 * 切换城市时,切换到相应机场
 */
function changeAirPortByCity(){
	var type;
	if($("#ordertype").val() == 2) type = 0;
	else if($("#ordertype").val() == 3) type = 1;
	var cityid = type==0?$("#onCity").val():$("#offCity").val();
	if(type==0){  //去掉切换城市后,select值没有清除的bug
		$("#onAddress").empty();	
		$("#onAddress").val("");
	}else{
		$("#offAddress").empty();
		$("#offAddress").val("");
	}
	for(var key in params.airports){
		if(params.airports[key].city == cityid){
			var option = document.createElement("option");
			$(option).attr("value",params.airports[key].name);
			$(option).attr("selected","selected");
			$(option).attr("lng",params.airports[key].lng);
			$(option).attr("lat",params.airports[key].lat);
			$(option).attr("type",type);
			$(option).text(params.airports[key].name);
			if(type == 0){
				$("#onAddress").append(option);
			}else{
				$("#offAddress").append(option);
			}
			$(option).click(function(){
				if($(this).attr("type") == 0){
					params.onLng = $(this).attr("lng");
					params.onLat  = $(this).attr("lat");
				}else{
					params.offLng = $(this).attr("lng");
					params.offLat  = $(this).attr("lat");
				}
				getCost();
			});
		} 
	}
}

/**
 * 获取机场信息
 */
function getAirPorts(){
	$.ajax({
		 url : "Order/GetAirPorts",
         async : false,
         success : function(result) {
        	 if(result.status == 0){
        		 params.airports = result.airports;
        	 }
         },
         error : function(msg) {
         }
	});
}

/**
 * 获取常用地址
 * @param sSearch
 */
function getMostAddress(sSearch){
	var phone = $("#userid").select2("data").text.substr(0,11);
	var data = {
		/*userid:$("#userid").val(),*/
		userid:phone,	
		sSearch:sSearch
	};
    $.ajax({
        url : "Order/GetMostAddress",
        data : JSON.stringify(data),
        type : 'post',
        dataType : 'json',
	    contentType:"application/json",
        success : function(result) {
        	if(result.status == 0 && result.count > 0){
        		doGetMostAddressSuccess(result);
        	}else{
        		$(".dizhibox .bhide").empty();
        	}
        }
    });
}

/**
 * 获取常用地址成功处理逻辑
 * @param result
 */
function doGetMostAddressSuccess(result){
	$(".dizhibox .bhide").empty();
	for(var i in result.mostaddress){
		var city = result.mostaddress[i].citycaption;
		var address = result.mostaddress[i].address;
		var title = result.mostaddress[i].title;
		var li = $("<li>").append(title);
		
		$(".dizhibox .bhide").append(li);
	}
	if($("#ordertype").val() == "1" || $("#ordertype").val() == "3"){
		$(".dizhibox .bhide").eq(0).find("li").click(function(){
			var mostaddress = result.mostaddress[$(this).index()];
			suggestCost = true;
			checkCartype(mostaddress.city);
			if(suggestCost == false) {
				toastr.error("该城市不提供服务", "提示");
				return;
			}
			suggestCost = false;
			changeCity(mostaddress.citycaption,$("#onAddress")[0]);
			getCarTypes();
			mapParam.onAddress.setInputValue(mostaddress.title);
			mapParam.onaddrlat = mostaddress.lat;
			mapParam.onaddrlng = mostaddress.lng;
		});
	}
	if($("#ordertype").val() == "1"){
		$(".dizhibox .bhide").eq(1).find("li").click(function(){
			var mostaddress = result.mostaddress[$(this).index()];
			changeCity(mostaddress.citycaption,$("#offAddress")[0]);
			getCarTypes();
			mapParam.offAddress.setInputValue(mostaddress.title);
			mapParam.offaddrlat = mostaddress.lat;
			mapParam.offaddrlng = mostaddress.lng;
		});
	}else if($("#ordertype").val() == "2"){
		$(".dizhibox .bhide").eq(0).find("li").click(function(){
			var mostaddress = result.mostaddress[$(this).index()];
			changeCity(mostaddress.citycaption,$("#offAddress")[0]);
			getCarTypes();
			mapParam.offAddress.setInputValue(mostaddress.title);
			mapParam.offaddrlat = mostaddress.lat;
			mapParam.offaddrlng = mostaddress.lng;
		});
	}
}

/**
 * 获取车型
 */
function getCarTypes(){
	if($('#userid').val() == "" || $("#onCity").val() == "") return;
	$("#cartypelist").empty();
	$("#cartypelist").css("width","1000px");
	$.ajax({
	    type: 'POST',
	    url: "Order/GetCarTypes/",
	    data:JSON.stringify({
	    	userid:$('#userid').val(),
	    	ordertype:$("#ordertype").val(),
	    	city:$("#onCity").val(),
	    	usetype:$("#usetype input[type='radio']:checked").val()
	    }),
	    success: function(data){
    		if(data.status != 0){
    			
    		}else{
    			doGetCarTypeSuccess(data);
    		}
	    },
	    contentType:"application/json"
	});
}

/**
 * 获取车型成功处理逻辑
 * @param data
 */
function doGetCarTypeSuccess(data){
	if(data.count > params.cartypemin){
		$(".left,.right").show();
	}else{
		$(".left,.right").hide();
	}
	$("#cartypelist").empty();
	for(var i in data.cartypes){
		params.cartypes = data.cartypes;
		var item = $("<div>").addClass("item");
		var item_title = $("<div>").addClass("item_title").text(data.cartypes[i].name);
		var qibujia = $("<div>").addClass("qibujia").append($("<span>").addClass("price_car").text(data.cartypes[i].startprice)).append("元起步价");
		var gongli = $("<div>").addClass("gongli").append($("<span>").text(data.cartypes[i].rangeprice)).append("元/公里");
		var fenzhong = $("<div>").addClass("fenzhong").append($("<span>").text(data.cartypes[i].timeprice)).append("元/分钟");
		var item_car = $("<img>").addClass("item_car").attr("src",data.cartypes[i].logo).error(function(){$(this).attr("src","img/order/bg_putongshangwuche.png")});
		var item_active = $("<div>").addClass("item_active");
		item.append(item_title);
		item.append(qibujia);
		item.append(gongli);
		item.append(fenzhong);
		item.append(item_car);
		item.append(item_active);
		$("#cartypelist").append(item);
	}
	$("#cartypelist .item").click(function(){
		$("#cartypelist .item_active").hide();
		$(this).find(".item_active").show();
		$("#cartype").val(data.cartypes[$(this).index()].id);
		getCost();
	});
	//加载车型后默认选择第一个
	$("#cartypelist .item").eq(0).click();
	//初始化车型切换动画
	initCarTypeAnimate();
}

/**
 * 验证城市是否有业务
 */
function checkCartype(city){
	if($('#userid').val() == "") {
		return;
	}
	$.ajax({
	    type: 'POST',
	    url: "Order/GetOrgUserBusCity",
	    async: false,
	    data:JSON.stringify({
	    	userid:$('#userid').val(),
	    	ordertype:$("#ordertype").val(),
	    	city:city,
	    	usetype:$("#usetype input[type='radio']:checked").val()
	    }),
	    success: function(data){
    		if(data.status != 0){
    			suggestCost = false;
    		}else{
    			if(suggestCost && mapParam.currentAct == "onAddress" && data.count == 0) {
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
function initCarTypeAnimate(){
	params.cartypeindex = 0;
	$(".left img").unbind("click");
	$(".right img").unbind("click");
	$(".left img").click(function(){
		if(params.cartypeindex <= 0){
			params.cartypeindex = 0;
			return;
		}
		params.cartypeindex = params.cartypeindex-1;
		$("#cartypelist .item").eq(params.cartypeindex).show(250,function(){
			$(this).click();
		});
	});
	$(".right img").click(function(){
		if(params.cartypeindex >= params.cartypes.length-params.cartypemin){
			params.cartypeindex = params.cartypes.length-params.cartypemin;
			return;
		}
		$("#cartypelist .item").eq(params.cartypeindex).hide(250);
		$("#cartypelist .item").eq(params.cartypeindex+1).click();
		params.cartypeindex = params.cartypeindex+1;
	});
}

/**
 * 初始化按钮事件
 */
function initEvent() {
	initMapEvent();
	initHintEvent();
	initUserEvent();
	initPageButtonEvent();
}

/**
 * 初始化页面各按钮事件
 */
function initPageButtonEvent(){
   $("#onAddress").focus(function(){
	   mapParam.currentAct = "onAddress";
	   $("#map").attr("data-owner","onAddress")
   });
   $("#onAddress").blur(function(){
	  $("#searchResult").trigger('click');
   });
   $("#offAddress").focus(function(){
	   mapParam.currentAct = "offAddress";
	   $("#map").attr("data-owner","offAddress")
   });
   $("#offAddress").blur(function(){
		  $("#searchResult1").trigger('click');
	   });
   $("#nouserid button").click(function(){
		  $("#nouserid").hide(); 
   });
   $("#noaddress button").click(function(){
	  $("#noaddress").hide(); 
   });
   $("#onAddress").blur(getCost);
   $("#offAddress").blur(getCost);
   $("#sub").click(createOrder);
}

/**
 * 初始化下单人弹窗按钮事件
 */
function initUserEvent(){
   $("#user").find(":button").click(function(){
	   text = "";
	   if($(this).attr("data-owner") == "userid"){
		   $("#orderman_keyword").val("");
		   $("#organ").val("");
		   $("#orderman_search").click();
		   $(".orderman").show(); 
	   }else{
		   $("#favorite_search").click();
    	   $(".favorite").show(); 
	   }
   });
}

/**
 * 初始化提示弹窗按钮事件
 */
function initHintEvent(){
   //所有弹窗的右上角X按钮,都是关闭弹窗
   $(".pop_box .close").click(function(){
	  $(".pop_box").hide(); 
   });
   //派单弹窗按钮事件
   $("#sendorder :button").eq(0).click(function(){
	   $("#sendorder").hide();
   });
   $("#sendorder :button").eq(1).click(function(){
	   manticOrder();
   });
   $("#sendorder :button").eq(2).click(function(){
	   cancelOrder();
   });
   //余额不足弹窗按钮事件
   $("#unpayable :button").eq(0).click(function(){
	   $("#unpayable").hide();
   });
}

/**
 * 初始化地图按钮事件
 */
function initMapEvent(){
   $(".onMap").click(function(){
	   if($("#ordertype").val() == 2){
		   moveMap($("#onCity").next().val(),$("#onAddress").text());
	   }else{
		   mapParam.currentAct = "onAddress";
		   moveMap($("#onCity").next().val(),$("#onAddress").val());
	   }
	   $("#map").attr("data-owner","onAddress");
	   $("#map").show();
   });
   
   $(".offMap").click(function(){
	   if($("#ordertype").val() == 3){
		   moveMap($("#offCity").next().val(),$("#offAddress").text());
	   }else{
		   mapParam.currentAct = "offAddress";
		   moveMap($("#offCity").next().val(),$("#offAddress").val());
	   }
	   $("#map").attr("data-owner","offAddress");
	   $("#map").show();
   });
   
   $("#map_cancel").click(function(){
	   $("#suggest").val("");
	   $("#map").hide();
   });
   
   $("#map_confirm").click(function(){
		if(null == suggestInit || "" == suggestInit || suggestInit != $("#suggest").val()) {
			$("#noaddress").show();
			return;
		}
		if($("#map").attr("data-owner") == "onAddress"){
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
			getCarTypes();
			params.onLat = mapParam.onaddrlat;
			params.onLng = mapParam.onaddrlng;
			params.onAddress= $("#suggest").val();
			$("#onAddress").focus();
		}else{
			$("#offCity").val(mapParam.city.id);
			$("#offCity").next().val(mapParam.city.city);
			changeCity(mapParam.city.city,$("#offAddress")[0]);
			params.offLat = mapParam.offaddrlat;
			params.offLng = mapParam.offaddrlng;
			params.offAddress= $("#suggest").val();
			$("#offAddress").focus();
		}
	   //使用setInputValue方法不会再次调用下拉搜索
	   $("#map").hide();
	   if($("#suggest").val() == "") return;
	   mapParam[$("#map").attr("data-owner")].setInputValue($("#suggest").val());
	   $("#suggest").val("");
	   getCost();
   });
}

/**
 * 重置表单
 */
function resetForm(){
	$(".select2-search-choice-close").eq(2).mousedown();
	$(".select2-search-choice-close").eq(1).mousedown();
	$(".select2-search-choice-close").mousedown();
	$("#cartype").val("");
	$("#cartypelist").html("");
    $("#estimatedcost").text("0.0元");
    $("#estimatedmileage").text("0公里");
    $("#estimatedtime").text("0分钟");
	$("#usetype input[type='radio']").eq(0).click();
	$("#payable").hide();
	$("#paymethod input[type='radio']").eq(0).removeAttr("disabled");
	var usetime = $("#usetime").val();
	$("#form")[0].reset();
	$("#usetime").val(usetime);
	$("#onCity").val(params.buscities[0].id);
	$("#onCity").next().val(params.buscities[0].city);
	$("#offCity").val(params.buscities[0].id);
	$("#offCity").next().val(params.buscities[0].city);
}

/**
 * 显示下单结果
 * @param data
 */
function showResult(data){
	if(data.status == 0){
		$("#orderno").val(data.orderno);
		var drivermode = $("[name=driverModeType]:checked").val();
		$("#sendorder .hint_content").html("正在进行系统派单...");
		if(drivermode == 1){                  //如果是页面指派则直接跳转到成功页
            toastr.success("下单成功", "提示");
            setTimeout(function(){window.location.href = window.location},2800);
			return;
		}else if(data.sendtype == 3){     //纯人工
			$("#sendorder .hint_content").html("请执行人工派单...");
			$("#sendorder :button").eq(0).hide();
			$("#sendorder :button").eq(1).show();
		}else if(data.sendmodel == 0){  //纯系统
			$("#sendorder :button").eq(0).show();
			$("#sendorder :button").eq(1).hide();
		}else{                                            //系统+人工
			$("#sendorder :button").eq(0).hide();
			$("#sendorder :button").eq(1).show();
		}
		$("#sendorder").show();
	}else if(data.status == 2009){
		$("#unpayable").show();
	}else{
		Zalert("下单失败",data.message);
		$("#sendorder").hide();
	}
}

/**
 * 取消订单
 */
function cancelOrder(){
    var p = {
	    		"orderno":$("#orderno").val(), 
	    		"ordertype": $("#ordertype").val(), 
	    		"usetype": $("#usetype input[type='radio']:checked").val()
    		};
   	$.ajax({
	    type: 'POST',
	    url: "Order/CancelOrder" ,
	    data: JSON.stringify(p),
	    dataType: "json",
	    contentType:"application/json",
	    success:function(data){
	    	if(data.status == 0 || data.status == 2003){
				$("#sendorder").hide();
				toastr.success("订单已被取消", "提示");
	    	}else if(data.status == 2007){
	    		$("#sendorder").hide();
	    		toastr.error("当前订单不可取消", "提示");
	    	}else{
	    		toastr.error(data.message, "提示");
	    	}
	    }
	});
}

/**
 * 人工派单
 */
function manticOrder(){
	//客服直接指派,不弹窗
   var p = {
			   "orderno":$("#orderno").val(),
			   "remind":false, 
			   "ordertype": $("#ordertype").val(), 
			   "usetype": $("#usetype input[type='radio']:checked").val()
		   };
	$.ajax({
  	    type: 'POST',
  	    url: "Order/ManticOrder" ,
  	    data: JSON.stringify(p),
  	    dataType: "json",
  	    contentType:"application/json",
       	success:function(data){
	    	if(data.status == 0){
	    		var orderno = $("#orderno").val();
	    		var type = 1;         //1-人工指派
	    		var usetype = 0;   //0-因公 1-因私(现在没有因私业务)
	    		var ordertype = $("#ordertype").val();
	    		window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + 
	    		'OrderManage/ManualSendOrderIndex?orderno='+orderno+'&type='+type+'&usetype='+usetype+'&ordertype='+ordertype;
	    		return;
	    	}else if(data.status == 2002){
	    		toastr.error("订单已被接走", "提示");
	    	}else if(data.status == 2003 && data.order.cancelparty == '4'){
	    		toastr.error("派单超时，订单已被取消", "提示");
	    	}else if(data.status == 2003){
	    		toastr.error("订单已被取消", "提示");
	    	}else{
	    		toastr.error(data.message, "提示");
	    	}
	    	$("#sendorder").hide();
	    }
  	});
}

/**
 * 创建订单
 */
function createOrder(){
	if(!$("#form").valid()){
        return;
    }

    var driverId = "";
    var vehicleId = "";

    if($("[name=driverModeType]:checked").val() == 1){
        driverId = $("#manualSelectDriverInput").val().split("_")[0];
        vehicleId = $("#manualSelectDriverInput").val().split("_")[1];
    }

	var data = {
			ordertype:$("#ordertype").val(),
			usetype:$("#usetype input[type='radio']:checked").val(),
			userid:$("#userid").val(),
			passengers:params.isNullName ? "" : $("#passengers").val(),
			passengerphone:$("#passengerphone").val(),
			usetime:$("#usetime").val(),
			oncity:$("#onCity").val(),
			offcity:$("#offCity").val(),
			onaddress:$("#onAddress").val(),
			offaddress:$("#offAddress").val(),
			onaddrlng:params.onLng,
			onaddrlat:params.onLat,
			offaddrlng:params.offLng,
			offaddrlat:params.offLat,
			vehiclessubjecttype:$("#vehiclessubjecttype").val(),
			vehiclessubject:$("#vehiclessubject").val(),
			tripremark:$("#tripremark").val(),
			selectedmodel:$("#cartype").val(),
			paymethod:$("#paymethod input[type='radio']:checked").val(),
            manualDriver:$("[name=driverModeType]:checked").val(),
            driverid:driverId,
            vehicleid:vehicleId

	};
	params.paymethod = $("#paymethod input[type='radio']:checked").val();
	if($("#ordertype").val() == "2"){
		data.fltno = $("#fltno").val();
		data.falltime = $("#falltime").val();
	}
	$.ajax({
	    type: 'POST',
	    url: "Order/CreateOrgOrder" ,
//	    data: JSON.stringify($("#form").serializeObject()),
	    data: JSON.stringify(data),
	    dataType: "json",
	    success: function(data){
	    	showResult(data);
	    },
	    contentType:"application/json"
	});
}

/**
 * 重置预估文本
 */
function resetCost(){
	$("#estimatedcost").text("0.0元");
	$("#estimatedmileage").text("0.0公里");
	$("#estimatedtime").text("0分钟");
}

/**
 * 获取预估费用
 */
function getCost(){
	var cartype = $("#cartype").val();
	var usetype= $("#usetype").find("input[type='radio']:checked").val();
	var rulestype = usetype == "0"?"1":"0";
	var city       = $("#onCity").val();
	var ordertype=$("#ordertype").val();
	var userid = $("#userid").val();
	params.onLng = mapParam.onaddrlng;
	params.onLat = mapParam.onaddrlat;
	params.offLng = mapParam.offaddrlng;
	params.offLat = mapParam.offaddrlat;
	if(ordertype == 2){
		params.onLng = parseFloat($("#onAddress option:selected").attr("lng"));
		params.onLat = parseFloat($("#onAddress option:selected").attr("lat"));
	}else if(ordertype == 3){
		params.offLng = parseFloat($("#offAddress option:selected").attr("lng"));
		params.offLat = parseFloat($("#offAddress option:selected").attr("lat"));
	}
	if(params.onLng == 0 || params.onLat == 0 
		|| params.offLng == 0 || params.offLat == 0
		|| $("#onAddress").val() == "" || $("#offAddress").val() == ""
		|| userid == ""){ 
		return;
	}
	if(cartype == ""){ 
//		toastr.error("请选择车型", "提示"); 
		return;
	}
	var data = {
			cartype:cartype,
			usetype:usetype,
			city:city,
			ordertype:ordertype,
			userid:userid,
			rulestype:rulestype,
			onaddrlng:params.onLng,
			onaddrlat:params.onLat,
			offaddrlng:params.offLng,
			offaddrlat:params.offLat
	};
	
	$.ajax({
	    type: 'POST',
	    url: "Order/GetOrgOrderCost",
	    data: JSON.stringify(data),
	    dataType: "json",
	    success: function(data){
	    	if(data.status == 0){
	    		if($("#usetype input[type='radio']:checked").val() == "0"){
	    			if(!data.payable){
	    				$("#unpayable").show();
	    			}else{
	    				$("#unpayable").hide();
	    			}
	    		}
	    		$("#estimatedcost").text(data.cost);
	    		$("#estimatedmileage").text(data.mileage);
	    		$("#estimatedtime").text(data.times);
	    	}else{
//	    		toastr.error(data.message, "提示");
	    	}
	    },
	    contentType:"application/json"
	});
}

function initSerializeForm(){
	$.fn.serializeObject = function() {
	    var o = {};
	    var a = this.serializeArray();
	    $.each(a,function() {
	        if (o[this.name] !== undefined) {
	            if (!o[this.name].push) {
	                o[this.name] = ""+[o[this.name]];
	            }
	            o[this.name].push(this.value || '');
	        } else {
	            o[this.name] = this.value || '';
	        }
	    });
	    return o;
	};
	
	//追加手机号码验证
	$.validator.addMethod("isMobile", function(value, element) {
	    var length = value.length;
	    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
	    return this.optional(element) || (length == 11 && mobile.test(value));
	}, "请正确填写您的手机号码");
	
	//追加航班号验证
	$.validator.addMethod("isFltNum", function(value, element) {
		var maxlen = $(element).attr("maxlength");
		maxlen = maxlen == 0 ? 6 : maxlen;
	    var length = value.length;
	    var fltno = /^[\w\d]{6}$/;
	    return this.optional(element) || (length == maxlen && fltno.test(value));
	}, "请正确填写您的航班号");
}

function validData(){
    var rules = {
    	userid: {
            required: true
        },
        passengers: {
            required: true
        },
        passengerphone:{
        	required: true,
        	isMobile: true
        },
        usetime:{
        	required: true
        },
        onaddress:{
        	required: true
        },
        offaddress:{
        	required: true
        },
        vehiclessubjecttype:{
        	required: true
        },
        vehiclessubject:{
        	required: true
        }
    };
    var messages = {
    	userid: {
            required: "请选择下单人"
        },
        passengers: {
            required: "请选择乘车人"
        },
        passengerphone: {
            required: "请输入乘车人电话",
            isMobile: "请输入正确格式的电话号码"
        },
        usetime:{
        	required: "请选择用车时间"
        },
        onaddress:{
        	required: "请输入上车地址"
        },
        offaddress:{
        	required: "请输入下车地址"
        },
        vehiclessubjecttype:{
        	required: "请选择用车事由类型"
        },
        vehiclessubject:{
        	required: "请输入用车事由说明"
        },
        manualSelectDriverInput:{
            required: "请选择指派司机"
        }
    };
    if($("#ordertype").val() == "2"){
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
		ignore:[],
		rules : rules,
		messages : messages
    });
}

function initManualSelectDriver(){
    var grid = {
        id: "manualSelectDriverPopTable",
        sAjaxSource: "Order/GetManualSelectDriver",
        userQueryParam: [
            // {name: "ordertype", value: $("#ordertype").val()}
        ],
        columns: [
            {mDataProp: "id", sTitle: "ID", sClass: "hiddenCol", sortable: false},
            {mDataProp: "jobNum", sTitle: "资格证号", sClass: "center", sortable: false},
            {mDataProp: "name", sTitle: "姓名", sClass: "center", sortable: false },
            {mDataProp: "phone", sTitle: "手机号", sClass: "center", sortable: false },
            {mDataProp: "vehicleModelName", sTitle: "服务车型", sClass: "center", sortable: false },
            {mDataProp: "plateNo", sTitle: "车牌号", sClass: "center", sortable: false },
            {
                mDataProp: "workStatus",
                sClass: "center",
                sTitle: "服务状态",
                "mRender": function (data, type, full) {
                    switch(data){
                        case "0":
                            return "空闲"
                        case "1":
                            return "服务中";
                        case "2":
                            return "下线";
                        case "3":
                        default:
                            return "未绑定";
                    }
                }
            }
        ]
    };
    var manualSelectDriverPopTable = renderGrid(grid);

    grid.userQueryParam = [];
    $("#manualSelectDriverPopSearch").on("click", function(){
        manualSelectDriverPopSearch(manualSelectDriverPopTable);
    });

    $('#manualSelectDriverPopTable tbody').on( 'click', 'tr', function () {
        if($(this).find("td").length == 1) return;
        $('#manualSelectDriverPopTable tbody tr.selected').removeClass('selected');
        $(this).addClass('selected');
    });

    $("#manualSelectDriverPop button.confirmPop").on("click", function(){
        var manualId = "";
        var manualText = "";
        if($('#manualSelectDriverPopTable tbody tr.selected').length > 0){
            manualId = $('#manualSelectDriverPopTable tbody tr.selected td')[0].innerText;
            manualText = $('#manualSelectDriverPopTable tbody tr.selected td')[3].innerText
                + " " + $('#manualSelectDriverPopTable tbody tr.selected td')[2].innerText;
        }

        $("#manualSelectDriverInput").select2("data", {
            id : manualId,
            text : manualText
        });
        $("#manualSelectDriverInput").valid();
        $("#manualSelectDriverPop").hide();
        $('#manualSelectDriverPopTable tbody tr.selected').removeClass('selected');
    });

    $("#manualSelectDriverPop button.closePop").on("click", function(){
        $("#manualSelectDriverPop").hide();
    });

}

function manualSelectDriverPopSearch(grid){
    var userQueryParam = [
        {name: "phone", value: $("#manualDriverPhone").val()},
        {name: "jobNum", value: $("#manualDriverJobNum").val()},
        {name: "plateNo", value: $("#manualDriverPlateNo").val()}
    ]
    grid.fnSearch(userQueryParam);
}

function initManualSelectPop(){
    $("#manualDriverPhone").val("");
    $("#manualDriverJobNum").val("");
    $("#manualDriverPlateNo").val("");
    $("#manualSelectDriverPopSearch").click();
}



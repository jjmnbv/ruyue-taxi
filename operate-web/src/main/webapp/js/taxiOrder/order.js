var favoriteGrid;
var params = {};
//页面初始化
$(function() {
	validForm();
	disabledForm(true);
	initAddress();
	initSelect2();
	initFavUserGrid();
    initManualSelectDriver();

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

    $("#company").on("change", function(){
        if($("[name=driverModeType]:checked").val() == 1){
            $("#manualSelectDriverInput").select2("data", {
                id : "",
                text : ""
            });
            $("#manualSelectDriverInput").valid();
        }
    });
    
    //取消责任方弹窗按钮事件
    $("#cancelpartyFormDiv :button").eq(0).click(function(){
 	   cancelOrder();
    });
    $("#cancelpartyFormDiv :button").eq(1).click(function(){
 	   cancel();
    });
});

/**
 * 表单验证
 */
function validForm() {
	$("#formss").validate({
		rules : {
			userid : {
				required : true
			},
			passengers : {
				passengers : true
			},
			passengerphone : {
				required : true,
				isMobile : true
			},
			usetime : {
	        	required : true
	        },
	        schedulefee : {
	        	range : [0, 99],
	        	digits : true
	        },
	        onaddress : {
	        	required : true
	        },
	        offaddress : {
	        	required : true
	        }
		},
		messages : {
			userid : {
				required : "请选择下单人"
			},
			passengers : {
				passengers : "请选择乘车人"
			},
			passengerphone : {
				required : "请输入乘车人号码",
				isMobile : "请输入正确格式的电话号码"
			},
			usetime : {
	        	required : "请选择用车时间"
	        },
	        schedulefee : {
	        	range : "只能输入0-99的整数",
	        	digits : "只能输入0-99的整数"
	        },
	        onaddress : {
	        	required : "请输入上车地址"
	        },
	        offaddress : {
	        	required : "请输入下车地址"
	        },
            manualSelectDriverInput:{
                required: "请选择指派司机"
            }
		},
		ignore: ""
	});
	
    $("#cancelpartyForm").validate({
        rules : {
            dutyparty : {
                required : true
            },
            cancelreason : {
                required : true
            }
        },
        messages : {
            dutyparty : {
                required : "请选择责任方"
            },
            cancelreason : {
                required : "请选择取消原因"
            }
        }
    });
}

function initAddress() {
	//常用地址跟搜索地址切换
    $(".stab>div").click(function() {
        $(this).addClass("shen_on").siblings().removeClass("shen_on");
        var n=$(this).index();
        var x=$(this).parents(".dizhibox");
        x.find(".stabox:eq("+n+")").removeClass("bhide").siblings(".stabox").addClass("bhide");
        $(".tangram-suggestion-main").hide();
    });
    
    $("#onAddress").focus(function(){
    	if($.trim($(this).val()) == ""){
    		$(this).parent().find(".dizhibox").find(".bresult").empty();
    	}else{
    		$(this).val($.trim($(this).val())+" ");
    	}
        $(this).parent().find(".dizhibox").slideDown(500);
 	    mapParam.currentAct = "onAddress";
 	    $("#map").attr("data-owner","onAddress");
    });
    $("#offAddress").focus(function(){
    	if($.trim($(this).val()) == ""){
    		$(this).parent().find(".dizhibox").find(".bresult").empty();
    	}else{
    		$(this).val($.trim($(this).val())+" ");
    	}
        $(this).parent().find(".dizhibox").slideDown(500);
 	   mapParam.currentAct = "offAddress";
 	   $("#map").attr("data-owner","offAddress");
    });
    
    $("#onAddress,#offAddress").blur(function() {
        $(this).parent().find(".dizhibox").slideUp(500);
    });
    $(".dizhibox").mousedown(function(e){
    	return false;
    });
    $("#onAddress").blur(function(){
  	  $("#searchResult").trigger('click');
     });
    $("#offAddress").blur(function(){
		  $("#searchResult1").trigger('click');
	   });
}

/**
 * 初始化select2插件
 */
function initSelect2() {
	$("#userid").select2({
		placeholder : "下单人",
		minimumInputLength : 0,
		allowClear : true,
		formatResult: formatUserIdResult,
		formatSelection: formatUserIdResult,
		ajax : {
			type : 'POST',
			url : "Order/GetPeUserForSelect",
			dataType : 'json',
            params: { //select2的contentType要这样写.否则无法使用requestBody接收参数
            	contentType: "application/json; charset=utf-8"
            },
			data : function(term, page) {
            	//参数必须格式化
            	var data = {
    				sSearch : term
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
	$("#userid").on("change", function(e) {
    	$("#passengers").select2("val", "");
    	$("#passengerphone").select2("val", "");
    	getMostAddress();
    	
    	//如果下单人为空，禁用其他标签
    	if(isUserNull()) {
    		disabledForm(true);
    	} else {
    		disabledForm(false);
    	}
    });
	
	$("#passengers").select2({
		placeholder : "乘车人",
		minimumInputLength : 0,
		allowClear : true,
		ajax : {
			type : 'POST',
			url : "Order/GetMostContactForSelect",
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
	        	term = term.substring(0, 20);
	            return {id: term, text: term+" (新增)" };
	        }
	    }
	});
	$("#passengers").on("select2-selected", function(e) {
    	var passengers = e.choice.text.split(" ");
    	if(passengers.length != 2) {
    		return;
    	}
    	if(passengers[1] == "(新增)") {
    		$("#passengers").select2("data", {
            	id : passengers[0],
    			text : passengers[0]
    		});
    	} else {
    		params.passengerPhone = passengers[0];
        	params.passengers = passengers[1];
        	setFavorite();
    	}
    });
	
	$("#passengerphone").select2({
		placeholder : "乘车人电话",
		minimumInputLength : 0,
		allowClear : true,
		ajax : {
			type : 'POST',
			url : "Order/GetMostContactForSelect",
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
	$("#passengerphone").on("select2-selected", function(e) {
    	var passengerphone = e.choice.text.split(" ");
    	if(passengerphone.length != 2) {
    		return;
    	}
    	if(passengerphone[1] == "(新增)") {
    		$("#passengerphone").select2("data", {
            	id : passengerphone[0],
    			text : passengerphone[0]
    		});
    	} else {
    		params.passengerPhone = passengerphone[0];
        	params.passengers = passengerphone[1];
        	setFavorite();
    	}
    });

    $("#manualSelectDriverInput").select2({
        placeholder : "指派司机",
        minimumInputLength : 0,
        allowClear : true,
        ajax : {
            type : 'POST',
            url : "Order/getTaxiManualSelectDriverForSelect",
            dataType : 'json',
            params: {
                contentType: "application/json; charset=utf-8"
            },
            data : function(term, page) {
                //参数必须格式化
                var data = {
                    keyword : term,
                    leaseCompanyId: $("#company").val()
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
 * 表格初始化
 */
function initFavUserGrid() {
	//初始化常用联系人表格数据
	var gridObj = {
		id: "favorite",
		sAjaxSource: "Order/GetMostContact",
	    userQueryParam: [
	       {name: "userid", value: $("#userid").val()}
	    ],
	    columns: [
	       {mDataProp: "name", sTitle: "姓名", sClass: "center", sortable: false},
	       {mDataProp: "phone", sTitle: "手机号码", sClass: "center", sortable: false }
	    ]
	};
	favoriteGrid = renderGrid(gridObj);
	
	//表格数据点击事件
	$('#favorite tbody').on( 'click', 'tr', function () {
    	if($(this).find("td").length == 1) {
    		return;
    	}
    	favoriteGrid.$('.selected').removeClass('selected');
        $(this).addClass('selected');
        var nickname = $(this).find("td").eq(0).text();
        var phone = $(this).find("td").eq(1).text();
        params.passengers = nickname;
        params.passengerPhone = phone;
    } );
}

/**
 * 查询常用联系人
 */
function searchFavorite() {
	var phone = $("#userid").select2("data").text.substr(0,11);
	var userQueryParam = [
     /* {name: "userid", value: $("#userid").val()},*/
      {name:"userid",value:phone},
      {name: "sSearch", value: $("#favorite_keyword").val()}
    ];
	favoriteGrid.fnSearch(userQueryParam);
}

/**
 * 初始化查询联系人
 */
function initFavorite() {
	$("#favorite_keyword").val("");
	searchFavorite();
}

/**
 * 展示常用联系人弹窗
 */
function showFavorite() {
	if(isUserNull()) {
		$("#nouserid").show();
		return;
	}
	
	$("#favorite_keyword").val("");
	initFavorite();
	$("#passengersDiv").show();
}

/**
 * 关闭常用联系人弹窗
 */
function cancelFavorite() {
	$("#passengersDiv").hide();
}

/**
 * 向下拉框设置常用联系人
 */
function setFavorite() {
	if(params.passengers || params.passengerPhone){
        $("#passengers").select2("data", {
        	id : params.passengers,
			text : (null == params.passengers || "" == params.passengers) ? "佚名" : params.passengers
		});
        $("#passengerphone").select2("data", {
        	id : params.passengerPhone,
			text : params.passengerPhone
		});
	}
	$("#passengers").valid();
	$("#passengerphone").valid();
	favoriteGrid.$("tr").removeClass("selected");
	$('#passengersDiv').hide();
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
        		$(".dizhibox .bhide").empty();
        		for(var i in result.mostaddress){
	    			var city = result.mostaddress[i].citycaption;
	    			var address = result.mostaddress[i].address;
	    			var title = result.mostaddress[i].title;
        			var li = $("<li>").append(title);
	    			
	    			$(".dizhibox .bhide").append(li);
        		}
        		
        		//给常用地址设置点击事件
        		$(".dizhibox .bhide").eq(0).find("li").click(function(){
    				var mostaddress = result.mostaddress[$(this).index()];
    				suggestCost = true;
    				checkRules(mostaddress.city);
    				if(suggestCost == false) {
    					toastr.error("该城市不提供服务", "提示");
    					return;
    				}
    				suggestCost = false;
    				changeCity(mostaddress.citycaption,$("#onAddress")[0]);
    				mapParam.onAddress.setInputValue(mostaddress.title);
    				mapParam.onaddrlat = mostaddress.lat;
    				mapParam.onaddrlng = mostaddress.lng;
    				getCost();
    			});
        		
        		$(".dizhibox .bhide").eq(1).find("li").click(function(){
    				var mostaddress = result.mostaddress[$(this).index()];
    				changeCity(mostaddress.citycaption,$("#offAddress")[0]);
    				mapParam.offAddress.setInputValue(mostaddress.title);
    				mapParam.offaddrlat = mostaddress.lat;
    				mapParam.offaddrlng = mostaddress.lng;
    				getCost();
    			});
        	}else{
        		$(".dizhibox .bhide").empty();
        	}
        }
    });
}

/**
 * 验证下单人是否为空
 */
function isUserNull() {
	var userid = $("#userid").val();
	if(null == userid || "" == userid) {
		return true;
	} else {
		return false;
	}
}

/**
 * 禁用/解禁表单标签
 * @param isDisabled
 */
function disabledForm(isDisabled) {
	$("#passengers").attr("disabled", isDisabled);
	$("#passengerphone").attr("disabled", isDisabled);
	$("#passengers").select2("enable", !isDisabled);
	$("#passengerphone").select2("enable", !isDisabled);
	$("#usetime").attr("disabled", isDisabled);
	$("#schedulefee").attr("disabled", isDisabled);
	$("#meterrange").attr("disabled", isDisabled);
	$("#oncityname").attr("disabled", isDisabled);
	$("#onAddress").attr("disabled", isDisabled);
	$("#offcityname").attr("disabled", isDisabled);
	$("#offAddress").attr("disabled", isDisabled);
	$("#tripremark").attr("disabled", isDisabled);
	$("input[name='paymentmethod']").attr("disabled", isDisabled);
    $("input[name='driverModeType']").attr("disabled", isDisabled);
    $("#manualSelectDriverInput").select2("enable", !isDisabled);

    if(isDisabled){
        $("#manualSelectDriver button").unbind().click(function(){
            $("#nouserid").show();
        });
    }else{
        $("#manualSelectDriver button").unbind().click(function(){
            initManualSelectPop();
            $("#manualSelectDriverPop").show();
        });
    }
}

/**
 * 关闭下单人提示窗
 */
function closeNouseid() {
	$("#nouserid").hide();
}

/**
 * 获取预估费用
 */
function getCost() {
	params.onLng = mapParam.onaddrlng;
	params.onLat = mapParam.onaddrlat;
	params.offLng = mapParam.offaddrlng;
	params.offLat = mapParam.offaddrlat;
	var paymethod = $("#paymethod").find("input[type='radio']:checked").val();
	
	if (params.onLng == 0 || params.onLat == 0 || params.offLng == 0
			|| params.offLat == 0 || $("#onAddress").val() == ""
			|| $("#offAddress").val() == "" || userid == "") {
		return;
	}
	var data = {
		city : $("#onCity").val(),
		usetype : 2,
		paymethod : paymethod,
		schedulefee : $("#schedulefee").val(),
		meterrange : $("#meterrange").val(),
		onaddrlng:params.onLng,
		onaddrlat:params.onLat,
		offaddrlng:params.offLng,
		offaddrlat:params.offLat
	};
	$.ajax({
        url : "Order/GetOpTaxiOrderCost",
        data: JSON.stringify(data),
        type : 'post',
        dataType : 'json',
	    contentType:"application/json",
        success : function(result) {
        	var status = result.status;
        	if(status == 0) {
	    		if(result.taxiOrderCost.premiumrate > 1){    //是否是溢价时段
	    			$("#premiumrateSpan").show();
	    			$("#premiumrate").text(result.taxiOrderCost.premiumrate);
	    		}else{
	    			$("#premiumrateSpan").hide();
	    		}
	    		if(result.taxiOrderCost.couponprice > 0){    //是否有可用优惠券
	    			$("#couponpriceSpan").show();
	    			$("#couponprice").text(result.taxiOrderCost.couponprice)  + "元";
	    			//优惠券金额需要页面单独计算
	    			result.taxiOrderCost.cost = (result.taxiOrderCost.cost - result.taxiOrderCost.couponprice) + "元";
	    		}else{
	    			$("#couponpriceSpan").hide();
	    		}
        		$("#estimateCostDiv").show();
        		$("#estimatedcost").text(result.taxiOrderCost.cost + "元");
        	} else {
        		$("#estimateCostDiv").hide();
        	}
        }
    });
}

/**
 * 验证上车城市是否存在计费规则
 */
function checkRules(city) {
	var data = null;
	var async = true;
	if(null != city && "" != city) {
		async = false;
	} else {
		city = $("#onCity").val();
	}
	
	$.ajax({
        url : "Order/CheckTaxiBusCity?city=" + city + "&date=" + new Date(),
        type : 'get',
        dataType : 'json',
        async: async,
	    contentType:"application/json",
        success : function(result) {
        	if(null != result.status && mapParam.currentAct == "onAddress" && result.status == -1) {
        		suggestCost = false;
        	} else {
        		$("#estimateCostDiv").show();
        		$("#estimatedcost").text("0.0元");
        	}
        }
    });
}

/**
 * 创建订单
 */
function createOrder() {
	var form = $("#formss");
	if(!form.valid()) {
		toastr.error("信息填写不完整", "提示");
		return;
	}

    var driverId = "";
    var vehicleId = "";

    if($("[name=driverModeType]:checked").val() == 1){
        driverId = $("#manualSelectDriverInput").val().split("_")[0];
        vehicleId = $("#manualSelectDriverInput").val().split("_")[1];
    }

	var data = {
		// companyid : $("#company").val(),
        belongleasecompany: $("#company").val(),
		userid : $("#userid").val(),
		passengers : $("#passengers").val(),
		passengerphone : $("#passengerphone").val(),
		usetime : $("#usetime").val(),
		schedulefee : $("#schedulefee").val(),
		meterrange : $("#meterrange").val(),
		oncity : $("#onCity").val(),
		offcity : $("#offCity").val(),
		onaddress : $("#onAddress").val(),
		offaddress : $("#offAddress").val(),
		onaddrlng : params.onLng,
		onaddrlat : params.onLat,
		offaddrlng : params.offLng,
		offaddrlat : params.offLat,
		tripremark : $("#tripremark").val(),
		paymentmethod : $("input[name='paymentmethod']:checked").val(),
        manualDriver:$("[name=driverModeType]:checked").val(),
        driverid:driverId,
        vehicleid:vehicleId
	};
	$.ajax({
	    type: 'POST',
	    url: "Order/CreateOpTaxiOrder" ,
	    data: JSON.stringify(data),
	    dataType: "json",
	    success: function(data){
	    	if(data.status == 0 || data.status == 'success') {

                if($("[name=driverModeType]:checked").val() == 1){
                    toastr.success("下单成功", "提示");
                    setTimeout(function(){window.location.href = window.location},2800);
                    return;
                }else {
                    $("#sendorderTitle").text("提交成功");
                    $("#sendorderContent").text("订单已提交成功，系统正在派单中");
                    $("#sendorder").show();
                    $("#orderno").val(data.orderno);
                    $("#usetype").val(data.usetype);
                    $("#ordertype").val(data.ordertype);
                }
	    	} else if(data.status == 2005) {
	    		$("#sendorderTitle").text("下单失败");
	    		$("#sendorderContent").text("下单人有订单未支付，现在不能下单，需完成支付后再进行下单");
	    		$("#errororder").show();
	    	} else if(data.status == 2000) {
	    		$("#sendorderTitle").text("派单失败");
	    		$("#sendorderContent").text(data.message);
	    		$("#errororder").show();
	    	} else if(data.status == 2009) {
	    		$("#sendorderTitle").text("下单失败");
	    		$("#sendorderContent").text("账户余额不足，请充值");
	    		$("#errororder").show();
	    	} else {
	    		$("#sendorderTitle").text("下单失败");
	    		$("#sendorderContent").text(data.message);
	    		$("#errororder").show();
	    	}
	    },
	    contentType:"application/json"
	});
}

/**
 * 后台派单
 */
function backgroundOrder() {
	$("#sendorder").hide();
	initPage();
}

/**
 * 关闭弹窗
 */
function closeErrororder() {
	$("#errororder").hide();
}

/**
 * 取消订单弹窗
 */
function cancelDivOrder() {
	getCancelShowTable();
}

/**
 * 不取消订单
 */
function noCancelOrder() {
	$("#cancelorder").hide();
	$("#sendorderTitle").text("提交成功");
	$("#sendorderContent").text("订单已提交成功，系统正在派单中");
	$("#sendorder").show();
}

/**
 * 取消订单
 */
function cancelOrder() {
	$("#cancelorder").hide();
	$("#cancelpartyFormDiv").hide();
	initPage();
	var p = {"orderno":$("#orderno").val(), "ordertype": $("#ordertype").val()};
   	$.ajax({
	    type: 'POST',
	    url: "Order/CancelOrder" ,
	    data: JSON.stringify(p),
	    dataType: "json",
	    contentType:"application/json",
	    success:function(data){
	    	
	    }
	});
}

/**
 * 初始化页面
 */
function initPage() {
	$("#company").val("");
	$("#userid").select2("val", "");
	$("#passengers").select2("val", "");
	$("#passengerphone").select2("val", "");
	$(".znow").click();
	$("#schedulefee").val("");
	$("#meterrange").val("0");
	$("#tripremark").val("");
	$("input[name='paymentmethod']").eq(0).click();
	$("#onAddress").val("");
	$("#offAddress").val("");
	$("#estimatedcost").text("0.0元");
	$("#dutyparty").val("");
	$("#cancelreason").val("");
	
    $("#manualSelectDriverInput").select2("data", {
        id : "",
        text : ""
    });
    disabledForm(true);
}

//追加手机号码验证
$.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写您的手机号码");

//乘车人验证
$.validator.addMethod("passengers", function(value, element) {
	var passengersData = $("#passengers").select2("data");
	if(null == passengersData || null == passengersData.text || "" == passengersData.text) {
		return false;
	}
	return true;
}, "请选择乘车人");


function initManualSelectDriver(){
    var grid = {
        id: "manualSelectDriverPopTable",
        sAjaxSource: "Order/GetTaxiManualSelectDriver",
        userQueryParam: [
            // {name: "ordertype", value: $("#ordertype").val()}
        ],
        columns: [
            {mDataProp: "id", sTitle: "ID", sClass: "hiddenCol", sortable: false},
            {mDataProp: "jobNum", sTitle: "资格证号", sClass: "center", sortable: false},
            {mDataProp: "name", sTitle: "姓名", sClass: "center", sortable: false },
            {mDataProp: "phone", sTitle: "手机号", sClass: "center", sortable: false },
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
        {name: "plateNo", value: $("#manualDriverPlateNo").val()},
        {name: "leaseCompanyId", value: $("#company").val()}
    ]
    grid.fnSearch(userQueryParam);
}

function initManualSelectPop(){
    $("#manualDriverPhone").val("");
    $("#manualDriverJobNum").val("");
    $("#manualDriverPlateNo").val("");
    $("#manualSelectDriverPopSearch").click();
}

/**
 * 取消费用说明
 * @param result
 * @returns {string}
 */
function getCancelShowTable(result) {
    var ordercancelrule = result.ordercancelrule;
    var html = '<table>';
    if(result.pricereason == 4) { //司机迟到
        html += '<tr><td colspan="4" style="text-align: left;">说明</td></tr>';
        html += '<tr><td>取消时差(分钟)</td><td>免责取消时限(分钟)</td><td>司机迟到时长(分钟)</td><td>迟到免责时限(分钟)</td></tr>';
        html += '<tr><td>' + result.canceltimelag + '</td><td>' + ordercancelrule.cancelcount + '</td><td>' + result.driverlate + '</td><td>' + ordercancelrule.latecount + '</td></tr>';
    } else {
        html += '<tr><td colspan="3" style="text-align: left;">说明</td></tr>';
        if(result.pricereason != 1) {
			html += '<tr><td colspan="3" style="text-align: left;">乘客需支付取消费用<span class="font_red">' + result.price + '</span></td></tr>';
		}
        html += '<tr><td>取消时差(分钟)</td><td>免责取消时限(分钟)</td><td>司机是否抵达</td></tr>';
        html += '<tr><td>' + result.canceltimelag + '</td><td>' + ordercancelrule.cancelcount + '</td>';
        if(result.driverarraival == true) {
            html += '<td>正常抵达</td></tr>';
        } else {
            html += '<td>未抵达</td></tr>';
        }
    }
    html += '</table>';
    return html;
}

/**
 * 根据责任方对应显示取消原因
 */
function showCancelreason() {
    var dutyparty = $("#dutyparty").val();
    var html = '';

    var orderstatus = $("#orderstatusHide").val();
    if(orderstatus == 1) { //没有司机接单
        if(dutyparty == 1) {
            html += '<option value="1">不再需要用车</option>';
        } else if(dutyparty == 3) {
            html += '<option value="5">业务操作错误</option>';
        } else if(dutyparty == 4) {
            html += '<option value="6">暂停供车服务</option>';
        } else {
            html += '<option value="">请选择</option>';
            html += '<option value="1">不再需要用车</option>';
            html += '<option value="5">业务操作错误</option>';
            html += '<option value="6">暂停供车服务</option>';
        }
    } else {
        if(dutyparty == 1) {
            html += '<option value="">请选择</option>';
            html += '<option value="1">不再需要用车</option>';
            html += '<option value="2">乘客迟到违约</option>';
        } else if(dutyparty == 2) {
            html += '<option value="">请选择</option>';
            html += '<option value="3">司机迟到违约</option>';
            html += '<option value="4">司机不愿接乘客</option>';
        } else if(dutyparty == 3) {
            html += '<option value="5">业务操作错误</option>';
        } else if(dutyparty == 4) {
            html += '<option value="6">暂停供车服务</option>';
        } else {
            html += '<option value="">请选择</option>';
            html += '<option value="1">不再需要用车</option>';
            html += '<option value="2">乘客迟到违约</option>';
            html += '<option value="3">司机迟到违约</option>';
            html += '<option value="4">司机不愿接乘客</option>';
            html += '<option value="5">业务操作错误</option>';
            html += '<option value="6">暂停供车服务</option>';
        }
    }
    $("#cancelreason").html(html);
}

/**
 * 根据取消原因对应显示责任方
 */
function showDutyparty() {
    var cancelreason = $("#cancelreason").val();
    if(cancelreason == 1 || cancelreason == 2) {
        $("#dutyparty").val(1);
    } else if(cancelreason == 3 || cancelreason == 4) {
        $("#dutyparty").val(2);
    } else if(cancelreason == 5) {
        $("#dutyparty").val(3);
    } else if(cancelreason == 6) {
        $("#dutyparty").val(4);
    }
    showCancelreason();
    $("#cancelreason").val(cancelreason);
}

/**
 * 取消
 */
function cancel() {
	$("#cancelpartyFormDiv").hide();
}
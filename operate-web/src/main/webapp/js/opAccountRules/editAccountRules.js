var defaultCity = {
//	id : 'A229B89C-F480-4F26-8A6F-29078B9E5B3B',
//	name : '锦州市',
//	markid : '0000020004'
};
/**
 * 页面初始化
 */
$(function () {
	initData();
	validateForm();
});

/**
 * 初始化城市下拉框
 */
function getSelectCity() {
	var cityParam = {
		container : $("#cityname").parent(),
		url : "PubInfoApi/GetCitySelect1",
		defValue : defaultCity.markid,
		callbackFn : changeCityCallBack,
	}
	if($("#cityname").val() != ""){
		cityParam.defValue = $("#markid").val();
	}
	showCitySelect1(
		cityParam.container,
		cityParam.url,
		cityParam.defValue,
		cityParam.callbackFn
	);
}

function changeCityCallBack(fullInfo,cityObj){
	$("#city").val(cityObj.data("id"));
	$("#cityname").val(cityObj.text());
	$("#cityname").valid();
}

/**
 * 下拉框赋值
 */
function initData() {
	$("#city").val($("#hideCityValue").val());
	$("#cityname").val($("#hideCityName").val());
	$("#rulestype").val($("#hideRulestype").val());
	$("#vehiclemodelsid").val($("#hideVehiclemodelsid").val());
	$("#timetype").val($("#hideTimetype").val());
	if($("#city").val() != ""){
		$("#cityname").attr("disabled",true);
	}
	getSelectCity();
}

/**
 * 表单校验
 */
function validateForm() {
	$("#formss").validate({
		rules: {
			cityname: {required: true},
			rulestype: {required: true},
			perhour: {required: true},
			vehiclemodelsid: {required: true},
			startprice: {required: true, number:true, limitNum:[4, 1]},
			rangeprice: {required: true, number:true, limitNum:[3, 1]},
			timeprice: {required: true, number:true, limitNum:[2, 1]},
			timetype: {required: true},
			perhour: {required: true, number:true, limitNum:[3, 1]},
			deadheadmileage : {required: true, number:true,limitNum:[4, 1]},
			deadheadprice : {required: true, number:true,limitNum:[4, 1]},
			nighteprice : {required: true, number:true,limitNum:[4, 1]},
			nightstarttime : {required: true},
			nightendtime : {required: true}
		},
		messages: {
			cityname: {required: "请选择所属城市"},
			rulestype: {required: "请选择规则类型"},
			perhour: {required: "请选择规则类型"},
			vehiclemodelsid: {required: "请选择服务车型"},
			startprice: {required: "请输入起步价",  number:"只能输入数字和小数点", limitNum:"最大4位整数，1位小数"},
			rangeprice: {required: "请输入里程价", number:"只能输入数字和小数点", limitNum:"最大3位整数，1位小数"},
			timeprice: {required: "请输入时间价", number:"只能输入数字和小数点", limitNum:"最大2位整数，1位小数"},
			timetype: {required: "请输入时间类型"},
			perhour: {required: "请输入时速", number:"只能输入数字和小数点", limitNum:"最大3位整数，1位小数"},
			deadheadmileage: {required: "请输入回空里程", number:"只能输入数字和小数点", limitNum:"最大4位整数，1位小数"},
			deadheadprice: {required: "请输入回空费价", number:"只能输入数字和小数点", limitNum:"最大4位整数，1位小数"},
			nighteprice: {required: "请输入夜间费价", number:"只能输入数字和小数点", limitNum:"最大4位整数，1位小数"},
			nightstarttime : {required: "请输入夜间征收时段"},
			nightendtime : {required: "请输入夜间征收时段"}
		}
	})
}

/**
 * 保存
 */
function save() {
	var timeType = $("#timetype").val();
	if (timeType=="0") {
		$("#perhour").val("");
	}
	var form = $("#formss");
	if(!form.valid()) return;
	var data = form.serializeObject();
	var url = "OpAccountrules/CreateOpAccountRules";
	if($("#id").val()) {
		url = "OpAccountrules/EditOpAccountRules";
	}
	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
				toastr.options.onHidden = function() {
					window.location.href=$("#baseUrl").val()+"OpAccountrules/Index";
            	}
				toastr.success(message, "提示");
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
			}	
		}
	});
}

function changeTimeType() {
	var timeType = $("#timetype").val();
	if (timeType=="0") {
		$("#perhourDiv").hide();
	} else {
		$("#perhourDiv").show();
	}
}

$.validator.addMethod("limitNum", function(value, element, param) {
	var rep = new RegExp("^\\d{0,"+param[0]+"}(\\.\\d){0,"+param[1]+"}$");
	return rep.test(value);
}, "数字格式不正确");

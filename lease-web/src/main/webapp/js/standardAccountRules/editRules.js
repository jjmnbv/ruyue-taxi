var dataGrid;

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
	var parent = document.getElementById("inp_box1");
	var city = document.getElementById("city");
	var cityName = document.getElementById("cityName");
	getData(parent, cityName, city, "StandardAccountRules/GetPubCityAddrList", 30, 0);
}

/**
 * 下拉框赋值
 */
function initData() {
	$("#city").val($("#cityValue").val());
	$("#cityName").val($("#cityNameHidden").val());
	$("#rulesType").val($("#rulestype").val());
	$("#carType").val($("#cartype").val());  
	$("#timeType").val($("#timetype").val());
	
	if($("#id").val() != "") {
		$("#city").attr("disabled","disabled");
		$("#cityName").attr("disabled","disabled");
		$("#rulesType").attr("disabled","disabled");
		$("#carType").attr("disabled","disabled");
	}
}

/**
 * 表单校验
 */
function validateForm() {
	$("#formss").validate({
		rules: {
			cityName: {required: true},
			rulesType: {required: true},
			carType: {required: true},
			startPrice: {required: true, number:true, limitNum:[4, 1]},
			rangePrice: {required: true, number:true, limitNum:[3, 1]},
			timePrice: {required: true, number:true, limitNum:[2, 1]},
			timeType: {required: true},
			perhour: {required: true, number:true, limitNum:[3, 1]},
			deadheadmileage: {required: true, number:true, limitNum:[3, 1]},
			deadheadprice: {required: true, number:true, limitNum:[3, 1]},
			nighteprice: {required: true, number:true, limitNum:[3, 1]},
			nightstarttime: {required: true},
			nightendtime: {required: true}
		},
		messages: {
			cityName: {required: "请选择所属城市"},
			rulesType: {required: "请选择规则类型"},
			carType: {required: "请选择服务车型"},
			startPrice: {required: "请输入起步价", number:"只能输入数字和小数点", limitNum:"最大4位整数，1位小数"},
			rangePrice: {required: "请输入里程价", number:"只能输入数字和小数点", limitNum:"最大3位整数，1位小数"},
			timePrice: {required: "请输入时间价", number:"只能输入数字和小数点", limitNum:"最大2位整数，1位小数"},
			timeType: {required: "请输入时间类型"},
			perhour: {required: "请输入时速", number:"只能输入数字和小数点", limitNum:"最大3位整数，1位小数"},
			deadheadmileage:{required: "请输入回空里程", number:"只能输入数字和小数点", limitNum:"最大3位整数，1位小数"},
			deadheadprice:{required: "请输入回空费价", number:"只能输入数字和小数点", limitNum:"最大3位整数，1位小数"},
			nighteprice:{required: "请输入夜间费价", number:"只能输入数字和小数点", limitNum:"最大3位整数，1位小数"},
			nightstarttime: {required: "请选择夜间征收时段"},
			nightendtime: {required: "请选择夜间征收时段"},
		}
	})
}

/**
 * 保存
 */
function save() {
	var timeType = $("#timeType").val();
	if (timeType=="0") {
		$("#perhour").val("");
	}
	
	var form = $("#formss");
	if(!form.valid()) return;
	
	var url = "StandardAccountRules/CreateStandardAccountRules";
	if($("#id").val()) {
		url = "StandardAccountRules/UpdateStandardAccountRules";
		$("#city").removeAttr("disabled");
		$("#rulesType").removeAttr("disabled");
		$("#carType").removeAttr("disabled");
	}
	
	var data = form.serializeObject();
	
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
					window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "StandardAccountRules/Index";
            	}
				toastr.success(message, "提示");
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
            	
            	if($("#id").val() != "") {
            		$("#city").attr("disabled","disabled");
            		$("#cityName").attr("disabled","disabled");
            		$("#rulesType").attr("disabled","disabled");
            		$("#carType").attr("disabled","disabled");
            	}
			}	
		}
	});
}

function changeTimeType() {
	var timeType = $("#timeType").val();
	if (timeType=="0") {
		$("#perhourDiv").hide();
	} else {
		$("#perhourDiv").show();
	}
}

/**
 * value 验证的值
 * param[0] 最大整数位
 * param[1] 最大小数位
 */
$.validator.addMethod("limitNum", function(value, element, param) {
	var rep = new RegExp("^\\d{0,"+param[0]+"}(\\.\\d){0,"+param[1]+"}$");
	return rep.test(value);
}, "数字格式不正确");

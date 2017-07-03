var dataGrid;
var validator;

/**
 * 页面初始化
 */
$(function () {
	var id = $("#id").val();
	if(null != id && "" != id) {
		$("#taxiSendrulePageTitle").text("修改规则");
	}
	
	validateForm();
	initTable();
});

/**
 * 初始化表格数据
 */
function initTable() {
	initData();
	initShow();
}

/**
 * 表单验证
 */
function validateForm() {
	validator = $("#formss").validate({
		rules : {
			cityname : {
				required : true
			},
			systemsendinterval : {
				required : true,
				number : true,
				limitNum : [5, 0],
				positiveNum : true
			},
			driversendinterval : {
				required : true,
				number : true,
				limitNum : [5, 0],
				positiveNum : true
			},
			personsendinterval : {
				required : true,
				number : true,
				limitNum : [5, 0],
				positiveNum : true
			},
			initsendradius : {
				required : true,
				number : true,
				limitNum : [5, 1]
			},
			maxsendradius : {
				required : true,
				number : true,
				limitNum : [5, 1],
				positiveNum : true
			},
			increratio : {
				required : true,
				number : true,
				limitNum : [5, 1],
				positiveNum : true
			},
			pushnum : {
				required : true,
				number : true,
				limitNum : [5, 0],
				positiveNum : true
			},
			carsinterval : {
				required : true,
				number : true,
				limitNum : [5, 0],
				positiveNum : true
			}
		},
		messages : {
			cityname : {
				required : "请选择城市"
			},
			systemsendinterval : {
				required : "请输入系统派单时限",
				number : "只能输入正整数",
				limitNum : "只能输入正整数",
				positiveNum : "请输入大于0的数值"
			},
			driversendinterval : {
				required : "请输入司机抢单时限",
				number : "只能输入正整数",
				limitNum : "只能输入正整数",
				positiveNum : "请输入大于0的数值"
			},
			personsendinterval : {
				required : "请输入人工派单时限",
				number : "只能输入正整数",
				limitNum : "只能输入正整数",
				positiveNum : "请输入大于0的数值"
			},
			initsendradius : {
				required : "请输入初始派单半径",
				number : "只能输入数字",
				limitNum : "最多保留一位小数"
			},
			maxsendradius : {
				required : "请输入最大派单半径",
				number : "只能输入数字",
				limitNum : "最多保留一位小数",
				positiveNum : "请输入大于0的数值"
			},
			increratio : {
				required : "请输入半径递增比",
				number : "只能输入数字",
				limitNum : "最多保留一位小数",
				positiveNum : "请输入大于0的数值"
			},
			pushnum : {
				required : "请输入推送数量",
				number : "只能输入正整数",
				limitNum : "只能输入正整数",
				positiveNum : "请输入大于0的数值"
			},
			carsinterval : {
				required : "请输入约车时限",
				number : "只能输入正整数",
				limitNum : "只能输入正整数",
				positiveNum : "请输入大于0的数值"
			}
		}
	});
}

/**
 * 初始化表格数据
 */
function initData() {
	var id = $("#id").val();
	if(null != id && "" != id) {
		$("#usetype").attr("disabled", true);
	} else {
		showCitySelect1(
			".input_box2", 
			"PubInfoApi/GetCitySelect1", 
			$("#citymarkid").val(), 
			function(backVal, $obj) {
				$('#cityname').val($obj.text());
				$("#city").val($obj.data('id'));
				$("#cityname").valid();
			}
		);
	}
}

/**
 * 初始化标签显示
 */
function initShow() {
	validator.resetForm();
	
	var sendmodel = $("input[name='sendmodel']:checked").val();
	if(sendmodel == "0") { //系统
		$("#personsendinterval").val("");
		$("#personsendintervalLabel").hide();
	} else if(sendmodel == "1") { //系统+人工
		$("#personsendintervalLabel").show();
	}
	
	var usetype = $("#usetype").val();
	if(usetype == "0") { //预约用车
		$("#initsendradius").val("");
		$("#increratio").val("");
		
		$("#carsintervalLabel").show();
		$("#initsendradiusLabel").hide();
		$("#increratioLabel").hide();
	}else if(usetype == "1") { //即刻用车
		$("#carsinterval").val("");
		
		$("#carsintervalLabel").hide();
		$("#initsendradiusLabel").show();
		$("#increratioLabel").show();
	}
	
	var sendtype = $("#sendtype").val();
	if(sendtype == "0") { //强派
		$("#driversendinterval").val("");
		
		$("#driversendintervalLabel").hide();
	} else { //抢派、抢单
		$("#driversendintervalLabel").show();
		$("#pushnumlimitLabel").show();
	}
	
	if(usetype == "1" && sendtype == "0") { //即刻且派单方式
		$("input[name='pushnumlimit']").get(0).checked = true;
		
		$("#pushnumlimitLabel").hide();
	} else {
		$("#pushnumlimitLabel").show();
	}
	
	var pushnumlimit = $("input[name='pushnumlimit']:checked").val();
	if(pushnumlimit == "0") { //不限制
		$("#pushnum").val("");
		$("#pushnum").attr("disabled", true);
	} else {
		$("#pushnum").attr("disabled", false);
	}
}

/**
 * 限制推送数量输入
 */
function limitPush() {
	var pushnum = $("#pushnum").val();
	onlyInputNum($("#pushnum"));
	var taxiDriverCount = $("#taxiDriverCount").val();
	if(!isNaN(pushnum) && parseInt(pushnum) > parseInt(taxiDriverCount)) {
		$("#pushnum").val(taxiDriverCount);
		toastr.error("不可超过" + taxiDriverCount + "人次", "提示");
	}
}

/**
 * 保存数据
 */
function save() {
	var form = $("#formss");
	if(!form.valid()) {
		$("input[type='text']").each(function() {
			if($(this).attr("id") == "driversendinterval") {debugger
				
			}
			if($(this).is(":disabled") || $(this).is(":hidden")) {
				return true;
			}
			if(null == $(this).val() || "" == $(this).val()) {
				toastr.error("请输入完整信息", "提示");
				return false;
			}
		});
		return;
	}
	
	var usetype = $("#usetype").val();
	var sendtype = $("#sendtype").val();
	var sendmodel = $("input[name='sendmodel']:checked").val();
	
	var personsendinterval = parseInt($("#personsendinterval").val()) * 60;
	var driversendinterval = parseInt($("#driversendinterval").val());
	var initsendradius = parseFloat($("#initsendradius").val());
	var maxsendradius = parseFloat($("#maxsendradius").val());
	var systemsendinterval = parseInt($("#systemsendinterval").val());
	var carsinterval = parseInt($("#carsinterval").val());
	
	//即刻用车、抢单/抢派，系统派单时限>司机抢单时限
	if(usetype == "1" && (sendtype == "1" || sendtype == "2") && (systemsendinterval*60 <= driversendinterval)) {
		toastr.error("系统派单时限应大于司机抢单时限", "提示");
		return;
	}
	
	//系统+人工且抢派/抢单，人工派单时限>司机抢单时限
	if(sendmodel == "1" && (sendtype == "1" || sendtype == "2") && personsendinterval <= driversendinterval) {
		toastr.error("人工派单时限应大于司机抢单时限", "提示");
		return;
	}
	
	//即刻用车时，最大派单半径>=最小派单半径
	if(usetype == "1" && initsendradius > maxsendradius) {
		toastr.error("最大派单半径应大于等于最小派单半径", "提示");
		return;
	}
	
	//系统+人工且预约用车，约车时限>系统派单时限
	if(sendmodel == "1" && usetype == "0" && carsinterval <= systemsendinterval) {
		toastr.error("约车时限应大于系统派单时限", "提示");
		return;
	}
	
	$("#usetype").attr("disabled", false);
	var data = form.serializeObject();
	var url = "TaxiSendrules/AddTaxiSendRules";
	if($("#id").val()) {
		url = "TaxiSendrules/EditTaxiSendRules";
		$("#usetype").attr("disabled", true);
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
					window.location.href = $("#baseUrl").val()+"TaxiSendrules/Index";
            	}
				toastr.success(message, "提示");
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
			}	
		}
	});
}

/**
 * 只能输入正数
 * @param data
 */
function onlypositiveNum(data) {
	var value = $(data);
	var rep = new RegExp("^\\d{1,5}\\.?\\d{0,1}$");
	if(!rep.test(value.val())) {
		onlyInputNum(value);
	}
	if(value.val().indexOf(".") == -1 && value.val().length > 5) {
		value.val(value.val().substring(0, 5));
	}
	if(value.val().length > 2) {
		var first = value.val().substring(0, 1);
		var second = value.val().substring(1, 2);
		if(first == 0 && second != ".") {
			value.val(value.val().substring(1, value.val().length));
			onlypositiveNum(data);
		}
	}
}

/**
 * 限制数字格式
 */
$.validator.addMethod("limitNum", function(value, element, param) {
	var rep = new RegExp("^\\d{1,"+param[0]+"}(\\.\\d){0,"+param[1]+"}$");
	return rep.test(value);
}, "数字格式不正确");

/**
 * 正数
 */
$.validator.addMethod("positiveNum", function(value, element, param) {
	return value > 0;
}, "数字格式不正确");


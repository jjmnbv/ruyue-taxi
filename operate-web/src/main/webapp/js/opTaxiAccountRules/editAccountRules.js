var defaultCity = {
//	id : 'A229B89C-F480-4F26-8A6F-29078B9E5B3B',
//	name : '锦州市',
//	markid : '0000020004'
};
/**
 * 页面初始化
 */
$(function () {
	getSelectCity();
	validateForm();
	/**
	 * 添加对“起租价”“起租里程”“续租价”“附加费”“标准里程”“空驶费率”等输入框均只能输入数字，且只能有一位小数
	 */
	$("#startprice,#startrange,#renewalprice,#surcharge,#standardrange,#emptytravelrate").keyup(function () {
		 $(this).val($(this).val().replace(/[^0-9.]/g, ''));
	        if(!/^[0-9]+([.]{1}[0-9]{0,1})?$/g.test($(this).val())){
	        	var t=$(this)[0].value;
	        	var subt=t.substring(0,t.length-1);
	        	$(this).val(subt);
	        }
    }).bind("paste", function () {  //CTR+V事件处理    
    	 $(this).val($(this).val().replace(/[^0-9.]/g, ''));
         if(!/^[0-9]+([.]{1}[0-9]{1})?$/g.test($(this).val())){
         	$(this).val("");
         }
    }).css("ime-mode", "disabled"); //CSS设置输入法不可用   
	
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
}

/**
 * 表单校验
 */
function validateForm() {
	$.validator.addMethod("limitNum", function(value, element, param) {
		var rep = new RegExp("^\\d{0,"+param[0]+"}(\\.\\d){0,"+param[1]+"}$");
		return rep.test(parseFloat(value));
	});

	$.validator.addMethod("limitStandardRange", function(value, element, param) {
		return value > parseInt($("#startrange").val());
	});
	$("#formss").validate({
		ignore:[],
		rules: {
			cityname: {required: true},
			startprice: {required: true, number:true, limitNum:[4, 1]},
			startrange: {required: true, number:true, limitNum:[4, 1]},
			renewalprice: {required: true, number:true, limitNum:[4, 1]},
			surcharge: {required: true, number:true, limitNum:[4, 1]},
			standardrange: {required: true, number:true, limitNum:[4, 1],limitStandardRange:true},
			emptytravelrate: {required: true, number:true, limitNum:[4,1]}
		},
		messages: {
			cityname: {required: "请选择所属城市"},
			startprice: {required: "请输入起租价",  number:"只能输入数字和小数点", limitNum:"最大4位整数，1位小数"},
			startrange: {required: "请输入起租里程", number:"只能输入数字和小数点", limitNum:"最大4位整数，1位小数"},
			renewalprice: {required: "请输入续租价", number:"只能输入数字和小数点", limitNum:"最大4位整数，1位小数"},
			surcharge: {required: "请输入附加费", number:"只能输入数字和小数点", limitNum:"最大4位整数，1位小数"},
			standardrange: {required: "请输入标准里程", number:"只能输入数字和小数点", limitNum:"最大4位整数，1位小数",limitStandardRange:"标准里程应大于起租里程"},
			emptytravelrate: {required: "请输入空驶费率", number:"只能输入数字和小数点", limitNum:"最大4位整数，1位小数"}
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
	if(!form.valid()) {
		if($("#cityname").val()!="" && $("#startprice").val()!="" && $("#startrange").val()!="" &&
			$("#renewalprice").val()!="" && $("#surcharge").val()!="" && $("#standardrange").val()!="" &&
			$("#emptytravelrate").val()!="" && parseInt($("#startrange").val())>=parseInt($("#standardrange").val())){
			toastr.error("标准里程应大于起租里程", "提示");
			return;
		}else{
		toastr.error("请输入完整信息", "提示");
		return;
		}
	}
	var data = form.serializeObject();
	var url = "OpTaxiAccountRules/AddRule";
	if($("#id").val() != "") {
		url = "OpTaxiAccountRules/EditRule";
	}
	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (result) {
			if (result.status == 0) {
				toastr.options.onHidden = function() {
					window.location.href=$("#baseUrl").val()+"OpTaxiAccountRules/Index";
            	}
				var msg = $("#id").val() != "" ? "维护规则成功" : "新增规则成功";
				toastr.success(msg, "提示");
			} else {
            	toastr.error(result.message, "提示");
			}	
		}
	});
}

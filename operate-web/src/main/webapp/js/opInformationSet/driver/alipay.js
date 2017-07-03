/**
 * 页面初始化
 */
$(function() {
	validateAlipayForm();
});

function save(){
	var form = $("#editAlipayForm");
	var data = form.serializeObject();
	if (!form.valid()) {
		if(null == data.driveralipayaccount || "" == data.driveralipayaccount) {
			toastr.error("请输入商家支付宝账户", "提示");
			return;
		}
		if(null == data.driveralipayappid || "" == data.driveralipayappid) {
			toastr.error("请输入APPID", "提示");
			return;
		}
		if(null == data.driveralipayprivatekey || "" == data.driveralipayprivatekey) {
			toastr.error("请输入应用私钥", "提示");
			return;
		}
		if(null == data.driveralipaypublickey || "" == data.driveralipaypublickey) {
			toastr.error("请输入支付宝公钥", "提示");
			return;
		}
		if(null == data.driveralipaypartnerid || "" == data.driveralipaypartnerid) {
			toastr.error("请输入合作伙伴ID", "提示");
			return;
		}
		if(null == data.driveralipaypartnerprivatekey || "" == data.driveralipaypartnerprivatekey) {
			toastr.error("请输入合作伙伴私钥", "提示");
			return;
		}
		if(null == data.driveralipaypartnerpublickey || "" == data.driveralipaypartnerpublickey) {
			toastr.error("请输入合作伙伴公钥", "提示");
			return;
		}
		return;
	}
	var url = "OpInformationSet/driver/UpdateAlipay";
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : url,
		data : JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
				toastr.options.onHidden = function() {
					window.location.href = base+"OpInformationSet/driver/Index";
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
 * 表单校验
 */
function validateAlipayForm() {
	$("#editAlipayForm").validate({
		rules : {
            driveralipayaccount : {
				required : true,
				alipay : true
			},
            driveralipayappid : {
				required : true,
				alipay : true
			},
            driveralipayprivatekey : {
				required : true,
				alipay : true
			},
            driveralipaypublickey : {
				required : true,
				alipay : true
			},
            driveralipaypartnerid : {
				required : true,
				alipay : true
			},
            driveralipaypartnerpublickey : {
				required : true,
				alipay : true
			},
            driveralipaypartnerprivatekey : {
				required : true,
				alipay : true
			}
			
		},
		messages : {
            driveralipayaccount : {
				required : "请输入商家支付宝账户"
			},
            driveralipayappid : {
				required : "请输入APPID"
			},
            driveralipayprivatekey : {
				required : "请输入应用私钥"
			},
            driveralipaypublickey : {
				required : "请输入支付宝公钥"
			},
            driveralipaypartnerid : {
				required : "请输入合作伙伴ID"
			},
            driveralipaypartnerpublickey : {
				required : "请输入合作伙伴公钥"
			},
            driveralipaypartnerprivatekey : {
				required : "请输入合作伙伴私钥"
			}
		}
	})
}


function cancel(){
	window.location.href = base+"OpInformationSet/driver/Index";
};

$.validator.addMethod("alipay", function(value, element, params) {
	//验证不能输入中文
	if (/[\u4E00-\u9FA5]/i.test(value)) {
	    return false;
	}else{
		return true;
	}
}, "只能输入英文字母、数字和符号");

/**
 * 页面初始化
 */
$(function() {
	validateWechatForm();
});

function save(){
	var form = $("#editWechatForm");
	var data = form.serializeObject();
	if (!form.valid()) {
		if(null == data.driverwechatpayaccount || "" == data.driverwechatpayaccount) {
			toastr.error("请输入商家微信账户", "提示");
			return;
		}
		if(null == data.driverwechatappid || "" == data.driverwechatappid) {
			toastr.error("请输入APPID", "提示");
			return;
		}
		if(null == data.driverwechatmerchantno || "" == data.driverwechatmerchantno) {
			toastr.error("请输入商户号", "提示");
			return;
		}
		if(null == data.driverwechatsecretkey || "" == data.driverwechatsecretkey) {
			toastr.error("请输入商户密钥", "提示");
			return;
		}
		return;
	}
	var url = "OpInformationSet/driver/UpdateWechat";
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
function validateWechatForm() {
	$("#editWechatForm").validate({
		rules : {
            driverwechatpayaccount : {
				required : true,
				wechat : true
			},
            driverwechatappid : {
				required : true,
				wechat : true
			},
            driverwechatmerchantno : {
				required : true,
				wechat : true
			},
            driverwechatsecretkey : {
				required : true,
				wechat : true
			}
		},
		messages : {
            driverwechatpayaccount : {
				required : "请输入商家微信账户"
			},
            driverwechatappid : {
				required : "请输入APPID"
			},
            driverwechatmerchantno : {
				required : "请输入商户号"
			},
            driverwechatsecretkey : {
				required : "请输入商户密钥"
			}
		}
	})
}


function cancel(){
	window.location.href = base+"OpInformationSet/driver/Index";
};

$.validator.addMethod("wechat", function(value, element, params) {
	//验证不能输入中文
	if (/[\u4E00-\u9FA5]/i.test(value)) {
	    return false;
	}else{
		return true;
	}
}, "只能输入英文字母、数字和符号");

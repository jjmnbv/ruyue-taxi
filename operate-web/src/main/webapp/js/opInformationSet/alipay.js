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
		if(null == data.aliPayAccount || "" == data.aliPayAccount) {
			toastr.error("请输入商家支付宝账户", "提示");
			return;
		}
		if(null == data.aliPayAppId || "" == data.aliPayAppId) {
			toastr.error("请输入APPID", "提示");
			return;
		}
		if(null == data.aliPayPrivateKey || "" == data.aliPayPrivateKey) {
			toastr.error("请输入应用私钥", "提示");
			return;
		}
		if(null == data.aliPayPublicKey || "" == data.aliPayPublicKey) {
			toastr.error("请输入支付宝公钥", "提示");
			return;
		}
		if(null == data.aliPayPartnerId || "" == data.aliPayPartnerId) {
			toastr.error("请输入合作伙伴ID", "提示");
			return;
		}
		if(null == data.aliPayPartnerPrivateKey || "" == data.aliPayPartnerPrivateKey) {
			toastr.error("请输入合作伙伴私钥", "提示");
			return;
		}
		if(null == data.aliPayPartnerPublicKey || "" == data.aliPayPartnerPublicKey) {
			toastr.error("请输入合作伙伴公钥", "提示");
			return;
		}
		return;
	}
	var url = "OpInformationSet/UpdateAlipay";
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
					window.location.href = base+"OpInformationSet/Index";
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
			aliPayAccount : {
				required : true,
				alipay : true
			},
			aliPayAppId : {
				required : true,
				alipay : true
			},
			aliPayPrivateKey : {
				required : true,
				alipay : true
			},
			aliPayPublicKey : {
				required : true,
				alipay : true
			},
			aliPayPartnerId : {
				required : true,
				alipay : true
			},
			aliPayPartnerPublicKey : {
				required : true,
				alipay : true
			},
			aliPayPartnerPrivateKey : {
				required : true,
				alipay : true
			}
			
		},
		messages : {
			aliPayAccount : {
				required : "请输入商家支付宝账户"
			},
			aliPayAppId : {
				required : "请输入APPID"
			},
			aliPayPrivateKey : {
				required : "请输入应用私钥"
			},
			aliPayPublicKey : {
				required : "请输入支付宝公钥"
			},
			aliPayPartnerId : {
				required : "请输入合作伙伴ID"
			},
			aliPayPartnerPublicKey : {
				required : "请输入合作伙伴公钥"
			},
			aliPayPartnerPrivateKey : {
				required : "请输入合作伙伴私钥"
			}
		}
	})
}


function cancel(){
	window.location.href = base+"OpInformationSet/Index";
};

$.validator.addMethod("alipay", function(value, element, params) {
	//验证不能输入中文
	if (/[\u4E00-\u9FA5]/i.test(value)) {
	    return false;
	}else{
		return true;
	}
}, "只能输入英文字母、数字和符号");

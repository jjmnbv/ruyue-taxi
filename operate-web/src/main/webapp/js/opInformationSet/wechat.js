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
		if(null == data.wechatPayAccount || "" == data.wechatPayAccount) {
			toastr.error("请输入商家微信账户", "提示");
			return;
		}
		if(null == data.wechatAppId || "" == data.wechatAppId) {
			toastr.error("请输入APPID", "提示");
			return;
		}
		if(null == data.wechatMerchantNo || "" == data.wechatMerchantNo) {
			toastr.error("请输入商户号", "提示");
			return;
		}
		if(null == data.wechatSecretKey || "" == data.wechatSecretKey) {
			toastr.error("请输入商户密钥", "提示");
			return;
		}
		return;
	}
	var url = "OpInformationSet/UpdateWechat";
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
function validateWechatForm() {
	$("#editWechatForm").validate({
		rules : {
			wechatPayAccount : {
				required : true,
				wechat : true
			},
			wechatAppId : {
				required : true,
				wechat : true
			},
			wechatMerchantNo : {
				required : true,
				wechat : true
			},
			wechatSecretKey : {
				required : true,
				wechat : true
			}
		},
		messages : {
			wechatPayAccount : {
				required : "请输入商家微信账户"
			},
			wechatAppId : {
				required : "请输入APPID"
			},
			wechatMerchantNo : {
				required : "请输入商户号"
			},
			wechatSecretKey : {
				required : "请输入商户密钥"
			}
		}
	})
}


function cancel(){
	window.location.href = base+"OpInformationSet/Index";
};

$.validator.addMethod("wechat", function(value, element, params) {
	//验证不能输入中文
	if (/[\u4E00-\u9FA5]/i.test(value)) {
	    return false;
	}else{
		return true;
	}
}, "只能输入英文字母、数字和符号");

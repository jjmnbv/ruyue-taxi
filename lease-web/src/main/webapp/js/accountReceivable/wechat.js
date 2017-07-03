/**
 * 页面初始化
 */
$(function() {
	validateWechatForm();
});
function save(){
	
	var form = $("#editWechatForm");
	var data = form.serializeObject();
	if (!form.valid()){
		if(data.wechatAccount == '' || data.wechatAccount == null){
			toastr.error("请输入微信账户", "提示");
			return;
		}
		if(data.wechatappid == '' || data.wechatappid == null){
			toastr.error("请输入微信APPID", "提示");
			return;
		}
		if(data.wechatmerchantno == '' || data.wechatmerchantno == null){
			toastr.error("请输入微信商户号", "提示");
			return;
		}
		if(data.wechatsecretkey == '' || data.wechatsecretkey == null){
			toastr.error("请输入微信商户密钥", "提示");
			return;
		}
		return;
	}
	var url = "AccountReceivable/UpdateWechat";

	
	
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
					window.location.href = base+"AccountReceivable/Index";
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
			wechatAccount : {
				required : true,
				wechat : true
			},
//			alipayAccount : {
//				required : true,
//				alipay : true
//			},
			wechatappid : {
				required : true,
				wechat : true
			},
			wechatmerchantno : {
				required : true,
				wechat : true
			},
			wechatsecretkey : {
				required : true,
				wechat : true
			},
//			alipayappid : {
//				required : true,
//				alipay : true
//			},
//			alipayprivatekey : {
//				required : true,
//				alipay : true
//			},
//			alipaypublickey : {
//				required : true,
//				alipay : true
//			},
//			alipaypartnerid : {
//				required : true,
//				alipay : true
//			},
//			alipaypartnerpublickey : {
//				required : true,
//				alipay : true
//			},
//			alipaypartnerprivatekey : {
//				required : true,
//				alipay : true
//			}
		},
		messages : {
			wechatAccount : {
				required : "请输入微信账号"
			},
//			alipayAccount : {
//				required : "请输入支付宝账号"
//			},
			wechatappid : {
				required : "请输入微信APPID"
			},
			wechatmerchantno : {
				required : "请输入微信商户号"
			},
			wechatsecretkey : {
				required : "请输入微信商户密钥"
			},
//			alipayappid : {
//				required : "请输入支付宝APPID"
//			},
//			alipayprivatekey : {
//				required : "请输入支付宝应用私钥"
//			},
//			alipaypublickey : {
//				required : "请输入支付宝公钥"
//			},
//			alipaypartnerid : {
//				required : "请输入合作伙伴ID"
//			},
//			alipaypartnerpublickey : {
//				required : "请输入合作伙伴私钥"
//			},
//			alipaypartnerprivatekey : {
//				required : "请输入合作伙伴公钥"
//			}
		}
	})
}


function cancel(){
	window.location.href = base+"AccountReceivable/Index";
};

$.validator.addMethod("wechat", function(value, element, params) {
	//验证不能输入中文
	if (/[\u4E00-\u9FA5]/i.test(value)) {
	    return false;
	}else{
		return true;
	}
}, "不能输入中文，请输入英文字母、数字、字符");

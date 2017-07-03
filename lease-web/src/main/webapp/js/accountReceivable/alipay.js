/**
 * 页面初始化
 */
$(function() {
	validateAlipayForm();
});
function save(){
	
	var form = $("#editAlipayForm");
	var data = form.serializeObject();
	if (!form.valid()){
		if(data.alipayAccount == '' || data.alipayAccount == null){
			toastr.error("请输入支付宝账号", "提示");
			return;
		}
		if(data.alipayappid == '' || data.alipayappid == null){
			toastr.error("请输入支付宝APPID", "提示");
			return;
		}
		if(data.alipayprivatekey == '' || data.alipayprivatekey == null){
			toastr.error("请输入支付宝应用私钥", "提示");
			return;
		}
		if(data.alipaypublickey == '' || data.alipaypublickey == null){
			toastr.error("请输入支付宝公钥", "提示");
			return;
		}
		if(data.alipaypartnerid == '' || data.alipaypartnerid == null){
			toastr.error("请输入合作伙伴ID", "提示");
			return;
		}
		if(data.alipaypartnerprivatekey == '' || data.alipaypartnerprivatekey == null){
			toastr.error("请输入合作伙伴私钥", "提示");
			return;
		}
		if(data.alipaypartnerpublickey == '' || data.alipaypartnerpublickey == null){
			toastr.error("请输入合作伙伴公钥", "提示");
			return;
		}
		
		return;
	}
	var url = "AccountReceivable/UpdateAlipay";
	
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
function validateAlipayForm() {
	$("#editAlipayForm").validate({
		rules : {
//			wechatAccount : {
//				required : true
//			},
			alipayAccount : {
				required : true,
				alipay : true
			},
//			wechatappid : {
//				required : true
//			},
//			wechatmerchantno : {
//				required : true
//			},
//			wechatsecretkey : {
//				required : true
//			},
			alipayappid : {
				required : true,
				alipay : true
			},
			alipayprivatekey : {
				required : true,
				alipay : true
			},
			alipaypublickey : {
				required : true,
				alipay : true
			},
			alipaypartnerid : {
				required : true,
				alipay : true
			},
			alipaypartnerpublickey : {
				required : true,
				alipay : true
			},
			alipaypartnerprivatekey : {
				required : true,
				alipay : true
			}
		},
		messages : {
//			wechatAccount : {
//				required : "请输入微信账号"
//			},
			alipayAccount : {
				required : "请输入支付宝账号"
			},
//			wechatappid : {
//				required : "请输入微信APPID"
//			},
//			wechatmerchantno : {
//				required : "请输入微信商户号"
//			},
//			wechatsecretkey : {
//				required : "请输入微信商户密钥"
//			},
			alipayappid : {
				required : "请输入支付宝APPID"
			},
			alipayprivatekey : {
				required : "请输入支付宝应用私钥"
			},
			alipaypublickey : {
				required : "请输入支付宝公钥"
			},
			alipaypartnerid : {
				required : "请输入合作伙伴ID"
			},
			alipaypartnerpublickey : {
				required : "请输入合作伙伴公钥"
			},
			alipaypartnerprivatekey : {
				required : "请输入合作伙伴私钥"
			}
		}
	})
}


function cancel(){
	window.location.href = base+"AccountReceivable/Index";
};

$.validator.addMethod("alipay", function(value, element, params) {
	//验证不能输入中文
	if (/[\u4E00-\u9FA5]/i.test(value)) {
	    return false;
	}else{
		return true;
	}
}, "不能输入中文，请输入英文字母、数字、字符");

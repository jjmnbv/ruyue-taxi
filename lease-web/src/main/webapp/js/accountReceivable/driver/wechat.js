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
		if(data.driverwechatpayaccount == '' || data.driverwechatpayaccount == null){
			toastr.error("请输入微信账户", "提示");
			return;
		}
		if(data.driverwechatappid == '' || data.driverwechatappid == null){
			toastr.error("请输入微信APPID", "提示");
			return;
		}
		if(data.driverwechatmerchantno == '' || data.driverwechatmerchantno == null){
			toastr.error("请输入微信商户号", "提示");
			return;
		}
		if(data.driverwechatsecretkey == '' || data.driverwechatsecretkey == null){
			toastr.error("请输入微信商户密钥", "提示");
			return;
		}
		return;
	}
	var url = "AccountReceivable/driver/UpdateWechat";

	
	
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
					window.location.href = base+"AccountReceivable/driver/Index";
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
				required : "请输入微信账号"
			},
            driverwechatappid : {
				required : "请输入微信APPID"
			},
            driverwechatmerchantno : {
				required : "请输入微信商户号"
			},
            driverwechatsecretkey : {
				required : "请输入微信商户密钥"
			}
		}
	})
}


function cancel(){
	window.location.href = base+"AccountReceivable/driver/Index";
};

$.validator.addMethod("wechat", function(value, element, params) {
	//验证不能输入中文
	if (/[\u4E00-\u9FA5]/i.test(value)) {
	    return false;
	}else{
		return true;
	}
}, "不能输入中文，请输入英文字母、数字、字符");

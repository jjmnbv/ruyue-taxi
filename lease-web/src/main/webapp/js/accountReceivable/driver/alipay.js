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
		if(data.driveralipayaccount == '' || data.driveralipayaccount == null){
			toastr.error("请输入支付宝账号", "提示");
			return;
		}
		if(data.driveralipayappid == '' || data.driveralipayappid == null){
			toastr.error("请输入支付宝APPID", "提示");
			return;
		}
		if(data.driveralipayprivatekey == '' || data.driveralipayprivatekey == null){
			toastr.error("请输入支付宝应用私钥", "提示");
			return;
		}
		if(data.driveralipaypublickey == '' || data.driveralipaypublickey == null){
			toastr.error("请输入支付宝公钥", "提示");
			return;
		}
		if(data.driveralipaypartnerid == '' || data.driveralipaypartnerid == null){
			toastr.error("请输入合作伙伴ID", "提示");
			return;
		}
		if(data.driveralipaypartnerprivatekey == '' || data.driveralipaypartnerprivatekey == null){
			toastr.error("请输入合作伙伴私钥", "提示");
			return;
		}
		if(data.driveralipaypartnerpublickey == '' || data.driveralipaypartnerpublickey == null){
			toastr.error("请输入合作伙伴公钥", "提示");
			return;
		}
		
		return;
	}
	var url = "AccountReceivable/driver/UpdateAlipay";
	
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
            driveralipaypartnerprivatekey : {
				required : true,
				alipay : true
			},
            driveralipaypartnerpublickey : {
				required : true,
				alipay : true
			}
		},
		messages : {
            driveralipayaccount : {
				required : "请输入支付宝账号"
			},
            driveralipayappid : {
				required : "请输入支付宝APPID"
			},
            driveralipayprivatekey : {
				required : "请输入支付宝应用私钥"
			},
            driveralipaypublickey : {
				required : "请输入支付宝公钥"
			},
            driveralipaypartnerid : {
				required : "请输入合作伙伴ID"
			},
            driveralipaypartnerprivatekey : {
				required : "请输入合作伙伴公钥"
			},
            driveralipaypartnerpublickey : {
				required : "请输入合作伙伴私钥"
			}
		}
	})
}


function cancel(){
	window.location.href = base+"AccountReceivable/driver/Index";
};

$.validator.addMethod("alipay", function(value, element, params) {
	//验证不能输入中文
	if (/[\u4E00-\u9FA5]/i.test(value)) {
	    return false;
	}else{
		return true;
	}
}, "不能输入中文，请输入英文字母、数字、字符");

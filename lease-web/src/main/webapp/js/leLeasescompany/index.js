/**
 * 页面初始化
 */
$(function() {
	validateForm();
	validateToCForm();
	validateEditForm();
});
function update(){
	$("#save").attr("type","button");
	$("#update").attr("type","hidden");
	
	$("#contacts").attr("disabled",false);
	$("#phone").attr("disabled",false);
	$("#mail").attr("disabled",false);
	$("#servicesPhone").attr("disabled",false);
	$("#wechatAccount").attr("disabled",false);
	$("#alipayAccount").attr("disabled",false);
	$("#busNumSensitive").removeAttr("disabled");
	$("#busNumSensitive1").removeAttr("disabled");
	$("#driverSensitive").removeAttr("disabled");
	$("#driverSensitive1").removeAttr("disabled");
	$("#alipayappid").removeAttr("disabled");
	$("#alipayprivatekey").removeAttr("disabled");
	$("#alipaypublickey").removeAttr("disabled");
	$("#wechatappid").removeAttr("disabled");
	$("#wechatmerchantno").removeAttr("disabled");
	$("#wechatsecretkey").removeAttr("disabled");
	$("#alipaypartnerid").removeAttr("disabled");
	$("#alipaypartnerpublickey").removeAttr("disabled");
	$("#alipaypartnerprivatekey").removeAttr("disabled");
}

function save(){
	
	var form = $("#editForm");
	if (!form.valid())
		return;
	
	var url = "LeLeasescompany/UpdateLeLeasescompany";

	var data = form.serializeObject();
	//电话验证
	var phone = data.phone;
	var message = "请输入有效的手机号码";
	if(regPhone(phone)){
		toastr.error(message, "提示");
		return;
	}
//客服电话
//	var servicesPhone = data.servicesPhone;
//	var  re = /^0\d{2,3}-?\d{7,8}$/;
//	if(!re.test(servicesPhone)){
//		toastr.error("请输入有效的客服电话", "提示");
//		return;
//    }
	
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
				toastr.success(message, "提示");
				$("#save").attr("type","hidden");
				$("#update").attr("type","button");
				
				$("#contacts").attr("disabled",true);
				$("#phone").attr("disabled",true);
				$("#mail").attr("disabled",true);
				$("#servicesPhone").attr("disabled",true);
				$("#wechatAccount").attr("disabled",true);
				$("#alipayAccount").attr("disabled",true);
				$("#busNumSensitive").attr("disabled",true);
				$("#busNumSensitive1").attr("disabled",true);
				$("#driverSensitive").attr("disabled",true);
				$("#driverSensitive1").attr("disabled",true);
				
				$("#alipayappid").attr("disabled",true);
				$("#alipayprivatekey").attr("disabled",true);
				$("#alipaypublickey").attr("disabled",true);
				$("#wechatappid").attr("disabled",true);
				$("#wechatmerchantno").attr("disabled",true);
				$("#wechatsecretkey").attr("disabled",true);
				
				$("#alipaypartnerid").attr("disabled",true);
				$("#alipaypartnerpublickey").attr("disabled",true);
				$("#alipaypartnerprivatekey").attr("disabled",true);
				
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
function validateEditForm() {
	$("#editForm").validate({
		rules : {
			contacts : {
				required : true,
				maxlength : 6
			},
			phone : {
				required : true,
				maxlength : 11
				
			},
			mail : {
				required : true,
				email : true
			},
			servicesPhone : {
				required : true,
				digits : true
			},
			wechatAccount : {
				required : true
			},
			alipayAccount : {
				required : true
			},
			
			wechatappid : {
				required : true
			},
			wechatmerchantno : {
				required : true
			},
			wechatsecretkey : {
				required : true
			},
			alipayappid : {
				required : true
			},
			alipayprivatekey : {
				required : true
			},
			alipaypublickey : {
				required : true
			},
			alipaypartnerid : {
				required : true
			},
			alipaypartnerpublickey : {
				required : true
			},
			alipaypartnerprivatekey : {
				required : true
			}
		},
		messages : {
			oldPassword : {
				required : "请输入联系人",
				maxlength : "最大长度不能超过6个字符"
			},
			userpassword : {
				required : "请输入手机号码",
				maxlength : "最大长度不能超过11个字符"
			},
			userpasswordTo : {
				required : "请输入电子邮件"
			},
			servicesPhone : {
				required : "请输入客服电话"
			},
			wechatAccount : {
				required : "请输入微信账号"
			},
			alipayAccount : {
				required : "请输入支付宝账号"
			},
			
			wechatappid : {
				required : "请输入微信APPID"
			},
			wechatmerchantno : {
				required : "请输入微信商户号"
			},
			wechatsecretkey : {
				required : "请输入微信商户密钥"
			},
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
				required : "请输入合作伙伴私钥"
			},
			alipaypartnerprivatekey : {
				required : "请输入合作伙伴公钥"
			}
		}
	})
}

function updatePassword(){
	$("#oldPassword").val("");
	$("#userpassword").val("");
	$("#userpasswordTo").val("");
	$("#updatePassword").show();
	var editForm = $("#editPasswordForm").validate();
	editForm.resetForm();
	editForm.reset();
};

function cancel(){
	$("#updatePassword").hide();
	$("#updateToC").hide();
};

/**
 * 表单校验
 */
function validateForm() {
	$("#editPasswordForm").validate({
		rules : {
			oldPassword : {
				required : true,
				maxlength : 16,
				minlength : 6
			},
			userpassword : {
				required : true,
				maxlength : 16,
				minlength : 6
				
			},
			userpasswordTo : {
				required : true,
				maxlength : 16,
				minlength : 6,
				equalTo : userpassword
			}
		},
		messages : {
			oldPassword : {
				required : "请输入密码",
				maxlength : "最大长度不能超过16个字符",
				minlength : "至少6位"
			},
			userpassword : {
				required : "请输入密码",
				maxlength : "最大长度不能超过16个字符",
				minlength : "至少6位"
			},
			userpasswordTo : {
				required : "请输入密码",
				maxlength : "最大长度不能超过16个字符",
				minlength : "至少6位",
				equalTo : "两次密码不一致"
			}
		}
	})
}

function savePassword(){
	var form = $("#editPasswordForm");
	if (!form.valid())
		return;
	
	var url = "LeLeasescompany/CheckPassword";
//	if ($("#id").val()) {
//		url = "LeVehiclemodels/UpdateLeVehiclemodels";
//	}

	var data = form.serializeObject();
	
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
				toastr.success(message, "提示");
				$("#updatePassword").hide();
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
function validateToCForm() {
	$("#updateToCForm").validate({
		rules : {
			remarks : {
				required : true,
				maxlength : 200
			}
		},
		messages : {
			remarks : {
				required : "请输入备注",
				maxlength : "最大长度不能超过200个字符"
			}
		}
	})
}
function updateToC(){
	$("#updateToC").show();
	var editForm = $("#updateToCForm").validate();
	editForm.resetForm();
	editForm.reset();
};
function saveToC(){
	var form = $("#updateToCForm");
	if (!form.valid())
		return;
	
	if ($("#checkboxToC").get(0).checked) {
		
		var url = "LeLeasescompany/UpdateToC";

		var data = form.serializeObject();
		
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
						window.location.href = base+"LeLeasescompany/Index";
					}
					toastr.success(message, "提示");
					$("#updateToC").hide();
				} else {
					var message = status.MessageKey == null ? status : status.MessageKey;
					toastr.error(message, "提示");
				}
			}
		});
	}else{
		toastr.error("申请加入toC业务，请勾选同意协议内容", "提示");
	}
};

function toCAgreement(){
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : 'LeLeasescompany/GetLeCompanyAgreement',
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			$('body').css('overflow', 'hidden');
			var html = "";
			var html1 = "";
			html+="<div><h4>"+status.shortname+"</h4></div>"
			+"<div  style='padding: 0 14px;width:400px;height:300px;text-align:left;'>"+status.content+"</div>";
			$("#tocState").html(html);
			$("#toCAgreement").show();
		},
		error : function(status){
			toastr.error("没有《加入toC协议》，请和运管端管理员联系", "提示");
		}
	});
};
function outToC(){
	var comfirmData={
			tittle:"提示",
			context:"退出后需要重新申请，您确认要退出toC业务吗？",
			button_l:"不退出",
			button_r:"确定",
			click: "outToC1()",
			htmltex:"<input type='hidden' placeholder='添加的html'> "
		};
		Zconfirm(comfirmData);
}
function outToC1(){
	var form = $("#updateToCForm");
	var data = form.serializeObject();
	var url = "LeLeasescompany/OutToC";
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
					window.location.href =base+"LeLeasescompany/Index";
				}
				toastr.success(message, "提示");
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
				toastr.error(message, "提示");
			}
		}
	});
}
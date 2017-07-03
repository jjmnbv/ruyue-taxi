$(document).ready(function() {
	validateForm();
}); 
function method() {
	var timer = setInterval(function() {
		var oldpassword = $("#oldpassword").val();
		if(null != oldpassword && "" != oldpassword) {
			$("#oldpassword").val("");
		}
	}, 10);
	
	setTimeout(function() {
		clearInterval(timer);
	}, 1000);
}
 /**
   * 表单校验
   */
function validateForm() {
	$("#editForm").validate({
	rules: {
		oldpassword: {required: true,minlength:6,maxlength: 16},
		password:{required: true,minlength:6,maxlength: 16},
		password2:{required: true,minlength:6,maxlength: 16,equalTo:"#password"}
	},
	messages: {
		oldpassword: {required: "请输入正确长度的密码",minlength:"最小长度不能少于6个字符", maxlength: "最大长度不能超过16个字符"},
		password: {required: "请输入正确长度的密码",minlength:"最小长度不能少于6个字符", maxlength: "最大长度不能超过16个字符"},
		password2:{required: "请输入正确长度的密码",minlength:"最小长度不能少于6个字符", maxlength: "最大长度不能超过16个字符",equalTo:"两次输入的新密码不一致"}
		}
	});
}


/**
 * 保存
 */
function save() {
	// $("#oldpassword").val("");
  	$("#save").attr({"disabled":"disabled"});
  	var form = $("#editForm");
  	if(!form.valid()){
  		$("#save").removeAttr("disabled");
  		return;
  	} 
  	var oldpassword =$("#oldpassword").val();
  	var password =$("#password").val();
  	var password2 =$("#password2").val();
  	if(password != password2){
  		toastr.error("两次输入的新密码不一致");
		return;
  	}
  	if(oldpassword == password){
  		toastr.error("新密码不能与原密码相同");
		return;
  	}
  	var url = "User/ChangePwd";
  	var data = form.serializeObject();
  	
  	$.ajax({
  		type: 'POST',
  		dataType: 'json',
  		url: url,
  		data: JSON.stringify(data),
  		contentType: 'application/json; charset=utf-8',
  		async: false,
  		success: function (status) {
  			var message = status.message;
  			if(message === undefined){
  				if(status.logontimes == '0'){
  					toastr.error("密码错误已达5次，请明日再试");
  					return;
  				}else{
	  				toastr.error("原密码输入错误"+status.logontimes+"次后，当天将不能再进行密码更改");
	  				return;
  				}
  			}
  			if (status.status == "success") {
         		toastr.success(message, "提示");
  				$("#editFormDiv").hide();
  				showObjectOnForm("editForm", null);
  			} else {
  				toastr.error(message, "提示");
  			}
  			$("#save").removeAttr("disabled");
  		},
  		error:function(XMLHttpRequest, textStatus, errorThrown){
  			$("#save").removeAttr("disabled");
  		}
  	});
}
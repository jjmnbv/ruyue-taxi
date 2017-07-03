/**
 * 页面初始化
 */
$(function () {
	initData();
	validateForm();
});
/**
 * 初始化表格数据
 */
function initData() {
		showCitySelect1(
			".input_box2", 
			"PubInfoApi/GetCitySelect1", 
			$("#citymarkid").val(), 
			function(backVal, $obj) {
				$('#cityname').val($obj.text());
				$("#city").val($obj.data('id'));
			}
		);
}
/**
 * 表单校验
 */
function validateForm() {
	$("#editForm").validate({
		rules: {
			servcieTel: {required: true, digits:true, maxlength: 13},
			companyname: {required: true,maxlength: 20},
			companyshortname: {required: true,maxlength: 6},
			cityname:{required:true},
			
		},
		messages: {
			servcieTel: {required: "客服电话不能为空", digits:"请输入数字", maxlength: "最大长度不能超过13个字符"},
			companyname: {required: "公司名称不能为空",maxlength: "最大长度不能超过20个字符"},
			companyshortname: {required: "公司简称不能为空",maxlength: "最大长度不能超过6个字符"},
			cityname:{required:"城市不能为空"},
		}
	})
}

/**
 * 保存
 */
function save() {
	var form = $("#editForm");
	if(!form.valid()) return;

	var url = "OpInformationSet/UpdateServcietel";
	var servcieTel = $("#servcieTel").val();
	var companyname = $("#companyname").val();
	var companyshortname = $("#companyshortname").val();
	var city = $("#cityname").val();
	var id = $("#id").val();
	var data = {
			servcieTel : servcieTel,
			companyname : companyname,
			companyshortname : companyshortname,
			 city: city,
			 id : id
	}
//	var data = form.serializeObject();
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;

            	toastr.options.onHidden = function() {
            		window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "/Customerphone/Index";
				}
				toastr.success(message, "提示");

			} else {
				
			}	
		}
	});
}

$.validator.addMethod("accountcheck", function(value, element, param) {
	var rep = /^[\u4e00-\u9fa5]+$/;
	var ret;
	for (var i=0;i<value.length;i++) {
		if (rep.test(value[i])) {
			return false;
		}
	}
	return true;
}, "账号格式不正确，请输入字母、数字和符号");

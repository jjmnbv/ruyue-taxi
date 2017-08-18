
/**
 * 页面初始化
 */
$(function () {
	initData();
	validateForm();
});




/**
 * 下拉框赋值
 */
function initData() {
    $("#contactaddresscity").click(function (e) {
        SelCity(this,e);
    });
    $("#parentadcity").click(function (e) {
        SelCity(this,e);
    });

}

/**
 *

 * 表单校验
 */
function validateForm() {
	$("#formss").validate({
		rules: {
            city: {required: true},
            responsible: {required: true},
            responsibleway: {required: true,minlength : 11, phone : true},
            postcode: {required: true,maxlength:6},
            contactaddresscity: {required: true},
            contactaddress: {required: true},
            parentcompany: {required: true},
            parentadcity: {required: true},
            parentad: {required: true}
		},
		messages: {
            city: {required: "请选择驻点城市"},
            responsible: {required: "请输入负责人姓名"},
            responsibleway: {required: "请输入负责人电话"},
            postcode: {required: "请输入邮政编码"},
            contactaddresscity: {required: "请选择通信地址所在城市"},
            contactaddress: {required: "请输入详细通信地址"},
            parentcompany: {required: "请输入母公司名称"},
            parentadcity: {required: "请选择母公司地址城市"},
            parentad: {required: "请输入母公司详细地址"}
		}
	})
}

/**
 * 保存
 */
function save() {
	var form = $("#formss");
	if(!form.valid()) return;
	var data = form.serializeObject();
	var url = "OpPlatformStagnation/Create";
	if($("#id").val()) {
		url = "OpPlatformStagnation/Update";
	}
	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (result) {
			if (result.status == "success") {
				toastr.options.onHidden = function() {
					window.location.href=$("#baseUrl").val()+"OpPlatformStagnation/Index";
            	};
				toastr.success(result.message, "提示");
			} else {
            	toastr.error(result.message, "提示");
			}	
		}
	});
}


/**
 * 初始化城市下拉框
 */
function getSelectCity() {
    var parent = document.getElementById("inp_box1");
    var cityId = document.getElementById("cityId");
    var city = document.getElementById("city");
    getData(parent, city,cityId, "PubInfoApi/GetCitySelect2", 30, 0);
}


$.validator.addMethod("phone", function(value, element, params) {
    if(regPhone(value)){
        return false;
    }else{
        return true;
    }
}, "请输入正确的手机号码");

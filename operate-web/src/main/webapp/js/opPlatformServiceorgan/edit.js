
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
	dateFormat();
    $("#detailaddresscity").click(function (e) {
        SelCity(this,e);
    });
    $("#mailaddresscity").click(function (e) {
        SelCity(this,e);
    });

}

/**
 * 表单校验
 */
function validateForm() {
	$("#formss").validate({
		rules: {
            servicename: {required: true},
            serviceno: {required: true},
            responsiblename: {required: true},
            responsiblephone: {required: true,minlength : 11,
                phone : true},
            managername: {required: true},
            managerphone: {required: true,minlength : 11,
                phone : true},
            contactphone: {required: true,minlength : 11,
                phone : true},
            createdate: {required: true},
            addressName: {required: true},
            detailaddresscity: {required: true},
            mailaddresscity: {required: true}
		},
		messages: {
            servicename: {required: "请输入服务机构名称"},
            serviceno: {required: "请输入服务机构代码 "},
            responsiblename: {required: "请输入负责人姓名"},
            responsiblephone: {required: "请输入负责人电话"},
            managerphone: {required: "请输入管理人电话"},
            contactphone: {required: "请输入紧急联系电话"},
            createdate: {required: "请选择机构设立日"},
            addressName: {required: "请选择机构所在地"},
            detailaddresscity: {required: "请选择机构详细地址城市"},
            mailaddresscity: {required: "请选择文书送达详细地址城市"}

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
	var url = "OpPlatformServiceorgan/Create";
	if($("#id").val()) {
		url = "OpPlatformServiceorgan/Update";
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
					window.location.href=$("#baseUrl").val()+"OpPlatformServiceorgan/Index";
            	};
				toastr.success(result.message, "提示");
			} else {
            	toastr.error(result.message, "提示");
			}	
		}
	});
}

//日期 控件 加载
function dateFormat() {
    $('#createdate').datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        todayBtn:  1,
        autoclose: 1,
    });


}
/**
 * 初始化城市下拉框
 */
function getSelectCity() {
    var parent = document.getElementById("inp_box1");
    var city = document.getElementById("address");
    var cityName = document.getElementById("addressName");
    getData(parent, cityName, city, "PubInfoApi/GetCitySelect2", 30, 0);
}


$.validator.addMethod("phone", function(value, element, params) {
    //联系方式
//	var phone = $("#phone").val();
//	var message = "请输入有效的手机号码";
    if(regPhone(value)){
//		toastr.error(message, "提示");
        return false;
    }else{
        return true;
    }
}, "请输入正确的手机号码");

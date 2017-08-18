
/**
 * 页面初始化
 */
$(function () {
	initData();
	validateForm();
	dateFormat();
	getSelectCitys();
});




/**
 * 下拉框赋值
 */
function initData() {

}

/**
 *

 * 表单校验
 */
function validateForm() {
	$("#formss").validate({
		rules: {
            certificate: {required: true},
            address: {required: true},
            organization: {required: true},
            startdate: {required: true},
            stopdate: {required: true},
            certifydate: {required: true},

		},
		messages: {
            certificate: {required: "请输入经营许可证号"},
            address: {required: "请选择经营许可地"},
            organization: {required: "请输入发证机构"},
            startdate: {required: "请选择有效期限开始时间"},
            stopdate: {required: "请选择有效期限结束时间"},
            certifydate: {required: "初次发证日期"}
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
	var url = "OpPlatformBusinesslicense/Create";
	if($("#id").val()) {
		url = "OpPlatformBusinesslicense/Update";
	}
    // 经营范围
    var businessScopeDiv = $(".addcbox").children();
    if(businessScopeDiv.length == 0 || document.getElementById("addcboxId").innerHTML == "" ||document.getElementById("addcboxId").innerHTML == null){
        toastr.error("经营区域不能为空", "提示");
        return;
    }else{
        var businessScopeArr = [];
        for(var i = 0; i < businessScopeDiv.length; i++) {
            // console.log(businessScopeDiv[i]);
            businessScopeArr.push(businessScopeDiv[i].id);
        }
        data.operationareas = businessScopeArr;
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
					window.location.href=$("#baseUrl").val()+"OpPlatformBusinesslicense/Index";
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
    var address = document.getElementById("address");
    var addressName = document.getElementById("addressName");
    getData(parent, addressName,address, "PubInfoApi/GetCitySelect2", 30, 0);
}

/**
 * 日期格式化
 */
function dateFormat() {
    $('#startdate').datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        todayBtn:  1,
        autoclose: 1,
    });
    $('#stopdate').datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        todayBtn:  1,
        autoclose: 1,

    });
    $('#certifydate').datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        todayBtn:  1,
        autoclose: 1,
    });
}

/**
 * 初始化经营范围选择
 */
function getSelectCitys() {

    showCitySelect1(
        "#pubCityaddr",
        "PubInfoApi/GetCitySelect1",
        null,
        function(backVal, $obj) {
            // var cityId=$obj.attr("data-id");
            var divs = document.getElementById("addcboxId").childNodes;
            for(var i=0;i<divs.length;i++){
                if(divs[i].id == $obj.text()){
                    toastr.error("城市已经存在，请重新选择", "提示");
                    return;
                }
            }
            var cityName=$obj.text();
            var cityhtml='<div class="added" id="'+cityName+'"  >'+cityName+'<em class="ico_x_a"></em></div>';
            var tempStr = $(".addcbox").text();
            var bool = tempStr.indexOf(cityName);
            if(bool<1){
                $(".addcbox").append(cityhtml);
            }
        }
    );
}
//删除经营范围
$(".addcbox").on('click','.ico_x_a', function () {
    $(this).parent(".added").remove();
});
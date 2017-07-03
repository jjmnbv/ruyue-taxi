
$(function() {
	validateForm();
	getSelectCity();
	$(".close").click(function(){
		$(".select2-drop-active").css("display","none");
	});
	//如果是修改，则不可编辑
	if ($("#id").val()) {
		$('#jobNum').attr("disabled",true);
	}


});

/**
 * 表单校验
 */
function validateForm() {
	$("#editDriverForm").validate({
		rules : {
			jobNum : {
				required : true,
				jobNum : true
			},
			name : {
				required : true,
				maxlength : 20
			},
			phone : {
				required : true,
				digits : true,
				phone : true
			},
			driverYears : {
				required : true
			},
			driversNum : {
				required : true,
				minlength: 18,
				driversNum:true
			},
			idCardNum : {
				required : true,
				idCardNo : true
			},
			driversType :{
				required : true
			},
			cityName : {
				required : true
			},
			vehicleType : {
				required : true
			}
		},
		messages : {
			jobNum : {
				required : "请输入正确的资格证号",
			},
			name : {
				required : "请输入司机姓名",
				maxlength : "最大长度不能超过20个字符"
			},
			phone : {
				required : "请输入手机号码",
				phone:"请输入正确的手机号码"
			},
			driverYears : {
				required : "请输入驾驶工龄"
			},
			driversNum : {
				required : "请输入司机驾驶证",
				minlength : "长度必须为18个字符"
			},
			idCardNum : {
				required : "请填写正确格式的身份证号"
			},
			driversType :{
				required : "请输入驾驶类型"
			},
			cityName : {
				required : "请输入所属城市"
			},
			vehicleType : {
				required : "请选择司机类型"
			}
		}
	})
}
/**
 * 返回取消提示
 * */
function callBack(id){
	var comfirmData={
		tittle:"提示",
		context:"您当前未点击保存，确认放弃保存吗？",
		button_l:"否",
		button_r:"是",
		click: "callBack1()",
		htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	Zconfirm(comfirmData);
};
function callBack1() {
	// history.go(-1);
	// history.back();
	window.location.href = base+"PubDriver/Index";
}
// 1
$('#fileupload').fileupload(
		{
			url : "PubDriver/UploadFile",
			dataType : 'json',
			done : function(e, data) {
				if (data.result.status == "success") {
					$("#imgShow").show();
					$("#imgback")
							.attr(
									"src",
									data.result.basepath + "/"
											+ data.result.message[0]);
					$("#headPortraitMax").val(data.result.message[0]);
				}else{
		        	toastr.error(data.result.error, "提示");
		        }
			}
		});
$("#imgback").click(function() {
	$("#fileupload").click();
});
$("#clear").click(function() {
	$("#fileupload").val("");
	$("#imgback").attr("src", "img/pubDriver/zhengmian.jpg");
	// $("#imgShow").hide();
	$("#headPortraitMax").val("");
});
// 2
$('#fileupload2').fileupload(
		{
			url : "PubDriver/UploadFile",
			dataType : 'json',
			done : function(e, data) {
				if (data.result.status == "success") {
					$("#imgShow2").show();
					$("#imgback2")
							.attr(
									"src",
									data.result.basepath + "/"
											+ data.result.message[0]);
					$("#driverPhoto").val(data.result.message[0]);
				}else{
		        	toastr.error(data.result.error, "提示");
		        }
			}
		});
$("#imgback2").click(function() {
	$("#fileupload2").click();
});
$("#clear2").click(function() {
	$("#fileupload2").val("");
	$("#imgback2").attr("src", "img/pubDriver/zhengmian.jpg");
	// $("#imgShow2").hide();
	$("#driverPhoto").val("");
});
// 3
$('#fileupload3').fileupload(
		{
			url : "PubDriver/UploadFile",
			dataType : 'json',
			done : function(e, data) {
				if (data.result.status == "success") {
					$("#imgShow3").show();
					$("#imgback3")
							.attr(
									"src",
									data.result.basepath + "/"
											+ data.result.message[0]);
					$("#idCardFront").val(data.result.message[0]);
				}else{
		        	toastr.error(data.result.error, "提示");
		        }
			}
		});
$("#imgback3").click(function() {
	$("#fileupload3").click();
});
$("#clear3").click(function() {
	$("#fileupload3").val("");
	$("#imgback3").attr("src", "img/pubDriver/zhengmian.jpg");
	// $("#imgShow3").hide();
	$("#idCardFront").val("");
});
// 4
$('#fileupload4').fileupload(
		{
			url : "PubDriver/UploadFile",
			dataType : 'json',
			done : function(e, data) {
				if (data.result.status == "success") {
					$("#imgShow4").show();
					$("#imgback4")
							.attr(
									"src",
									data.result.basepath + "/"
											+ data.result.message[0]);
					$("#idCardBack").val(data.result.message[0]);
				}else{
		        	toastr.error(data.result.error, "提示");
		        }
			}
		});
$("#imgback4").click(function() {
	$("#fileupload4").click();
});
$("#clear4").click(function() {
	$("#fileupload4").val("");
	$("#imgback4").attr("src", "img/pubDriver/beimian.jpg");
	// $("#imgShow4").hide();
	$("#idCardBack").val("");
});
//
/**
 * 保存
 */
function save() {
	var values = [];
	$("#serviceOrgName li").each(function(){
		values.push($(this).attr("data-value"));
	});
	$("#serviceOrgId").val(values.join(","));
	var form = $("#editDriverForm");
	if (!form.valid())
		return;
	
	//身份证验证
	var idCardNum = $("#idCardNum").val();
	
	//联系方式
	var phone = $("#phone").val();

	var id = $("#id").val();
	//工号 验证
	var jobNum = $("#jobNum").val();

	//驾驶证号码 验证
	var driversNum = $("#driversNum").val();


	var url = "PubDriver/CreatePubDriver";
	if ($("#id").val()) {
		url = "PubDriver/UpdatePubDriver";
	}

	var data = form.serializeObject();


	//驾驶工龄
	var driverYears = data.driverYears;
	var isNum = /^\d+(\.\d+)?$/;
	if(!isNum.test(driverYears)){
	    toastr.error("请输入正确的驾驶工龄", "提示");
	    return;
	}else{
		if(driverYears>99){
			toastr.error("驾驶工龄只能为2个字符", "提示");
			return;
		}
	}
	
	var city = data.city;
	if(city == null || city == ''){
		toastr.error("请选择所属城市", "提示");
		return;
	}
	
	//司机照片
	var headPortraitMax = data.headPortraitMax;
	if(headPortraitMax == null || headPortraitMax == ''){
		toastr.error("请上传司机照片", "提示");
		return;
	}
	
	//司机驾驶证
	var driverPhoto = data.driverPhoto;
	if(driverPhoto == null || driverPhoto == ''){
		toastr.error("请上传司机驾驶证", "提示");
		return;
	}
	
	//司机身份证
	var idCardFront = data.idCardFront;
	if(idCardFront == null || idCardFront == ''){
		toastr.error("请上传司机身份证正面", "提示");
		return;
	}
	
	//司机身份证
	var idCardBack = data.idCardBack;
	if(idCardBack == null || idCardBack == ''){
		toastr.error("请上传司机身份证反面", "提示");
		return;
	}
	
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : url,
		data : JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.options.onHidden = function() {
					window.location.href = base+"PubDriver/Index";
				}
				toastr.success(message, "提示");

			} else {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.error(message, "提示");
			}
		}
	});
}


function haspro(map) {
	if (!map) {
		return false
	}
	for ( var pro in map) {
		if (!pro) {
			return false
		}
		return true
	}
}


function cancle() {
	$("#addServiceOrg").hide();
	$(".select2-drop-active").css("display","none");
}

/**
 * 初始化城市下拉框
 */
function getSelectCity() {
//	var parent = document.getElementById("inp_box1");
//	var city = document.getElementById("city");
//	var cityName = document.getElementById("cityName");
//	getData(parent, cityName, city, "StandardAccountRules/GetPubCityAddrList", 30, 0);
	showCitySelect1(
		"#inp_box1", 
		"PubInfoApi/GetCitySelect1", 
		$("#citymarkId").val(),
		function(backVal, $obj) {
			$('#cityName').val($obj.text());
			$("#city").val($obj.data('id'));
		}
	);
}
//判断司机身份显示 。 隐藏  网约车显示，出租车隐藏
function getIdEntityTypeDiv(){
	var vehicleType = $("#vehicleType").val();
	if(vehicleType == 1){
		$("#idEntityTypeDiv").attr("style","display: none;");
		$("#serviceOrg").hide();
	}else{
		$("#idEntityTypeDiv").show();
		var val = $('input:radio[name="idEntityType"]:checked').val();
		if(val == 1){
			$("#serviceOrg").show();
        }
	}
}
$(function() {
	validateForm();
	if ($('input[name="idEntityType"]:checked').val() == '1') {
		$("#serviceOrg").show();
	}
});
// 绑定事件，普通司机隐藏服务机构 特殊司机显示服务机构
function res(cs) {
	if (cs == 0) {
		$("#serviceOrg").hide();
	}
	if (cs == 1) {
		$("#serviceOrg").show();
	}
}
/**
 * 表单校验
 */
function validateForm() {
	$("#editDriverForm").validate({
		rules : {
			jobNum : {
				required : true,
				maxlength : 10
			},
			name : {
				required : true,
				maxlength : 20
			},
			phone : {
				required : true
			},
			driverYears : {
				required : true
			},
			driversNum : {
				required : true,
				maxlength : 15
			},
			idCardNum : {
				required : true
			}
		},
		messages : {
			jobNum : {
				required : "请输入输入资格证号",
				maxlength : "最大长度不能超过10个字符"
			},
			name : {
				required : "请输入司机姓名",
				maxlength : "最大长度不能超过20个字符"
			},
			phone : {
				required : "请输入手机号码"
			},
			driverYears : {
				required : "请输入驾驶工龄"
			},
			driversNum : {
				required : "请填写正确格式的工商执照编码",
				maxlength : "最大长度不能超过15个字符"
			},
			idCardNum : {
				required : "请填写正确格式的身份证号"
			}
		}
	})
}
function callBack() {
	// history.go(-1);
	// history.back();
	window.location.href = base+"PubDriver/Index";
}
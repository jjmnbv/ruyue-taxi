
/**
 * 页面初始化
 */
$(document).ready(function() {
	validateForm();
});

/**
 * 表单校验
 */
function validateForm() {
	$("#withdrawForm").validate({
		rules: {
			amount: {required: true, number:true, limitNum:[8, 1], min:0.1, limitMax:true}
		},
		messages: {
			amount: {required: "请输入提现金额", number:"金额格式错误", limitNum:"整数位最多8位，小数位最多1位", min:"输入金额须大于0", limitMax:"提现金额不能超过当前可提现金额"}
		}
	})
}
	
/**
 * value 验证的值
 * param[0] 最大整数位
 * param[1] 最大小数位
 */
$.validator.addMethod("limitNum", function(value, element, param) {
	var rep = new RegExp("^\\d{0,"+param[0]+"}(\\.\\d){0,"+param[1]+"}$");
	return rep.test(value);
}, "金额格式有误");	

/**
 * value 验证的值
 * param[0] 最大整数位
 * param[1] 最大小数位
 */
$.validator.addMethod("limitMax", function(value, element, param) {
	if (parseFloat(value) > parseFloat($("#tixian").text())) {
		return false;
	} else {
		return true;
	}
	
}, "提现金额不能超过当前可提现金额");	

/**
 * 处理整数前面多余的0
 */
function overFormat(obj) {
	if(/^0+\d+\.?\d*.*$/.test(obj.value)){
		obj.value = obj.value.replace(/^0+(\d+\.?\d*).*$/, '$1');
	}
}

/**
 * 提现提交
 */
function save() {
	var form = $("#withdrawForm");
	if(!form.valid()) return;

	var data = {"organId": $("#organId").val(),
			    "leasesCompanyId": $("#leasesCompanyId").val(),
			    "amount": $("#amount").val()};
	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: "FinancialManagement/WithdrawOrganAccount",
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
            	window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "FinancialManagement/GetPubWithdraw/" + status.WithdrawNo + "?leasesCompanyId=" + $("#leasesCompanyId").val() + "&organId=" + $("#organId").val();
            
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.success(message, "提示");
			}	
		}
	});
}

/**
 * 提现提交
 */
$(".qbtx").click(function() {
	$("#amount").val($("#tixian").text());
});

/**
 * 返回
 */
function callBack() {
	window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "FinancialManagement/Index";
}


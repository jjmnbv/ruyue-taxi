$.validator.addMethod("account", function(value, element, params) {
	//验证机构账号
//	var account = $("#account").val();
	var re =  /^[0-9a-zA-Z]*$/g;
	if (!re.test(value)){  
//		toastr.error("机构账号只能是数字和字母", "提示");
	    return false;  
	}else{
		return true;
	} 
}, "账号只能是数字和字母");
$.validator.addMethod("idCardNo", function(value, element, params) {
	//身份证验证
//	var code = $("#idCardNo").val();
	if(!value || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(value)){
//		toastr.error("身份证号格式错误", "提示");
		return false;
	}else{
		return true;
	}
}, "身份证号格式错误");
$.validator.addMethod("jobNum", function(value, element, params) {
	//身份证验证
//	var code = $("#idCardNo").val();
	if(!value || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(value)){
//		toastr.error("身份证号格式错误", "提示");
		return false;
	}else{
		return true;
	}
}, "资格证号格式错误");
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
$.validator.addMethod("lineOfCredit", function(value, element, params) {
	//信用额度的验证
//	var lineOfCredit = data.lineOfCredit;
	var isNum = /^\d+(\.\d+)?$/;
	if(!isNum.test(value)){
//	    toastr.error("请输入正确的信用额度", "提示");
	    return false;
	}else{
		return true;
	}
}, "请输入正确的信用额度");

// $.validator.addMethod("jobNum", function(value, element, params) {
// 	//  工号
// 	var re =  /^[0-9a-zA-Z]*$/g;
// 	if (!re.test(value)){
// //		toastr.error("工号只能是数字和字母", "提示");
// 	    return false;
// 	}else{
// 		return true;
// 	}
// }, "工号只能是数字和字母");


$.validator.addMethod("driversNum", function(value, element, params) {
	//  驾驶证号
	var re =  /^[0-9][0-9a-zA-Z]{17}$/;
	if (!re.test(value)){
//		toastr.error("请输入正确的驾驶证号码", "提示");
	    return false;
	}else{
		return true;
	}
}, "驾驶证号码只能是数字和字母");
$.validator.addMethod("driversType", function(value, element, params) {
	//驾驶类型/
	if (value == null || value == '' || value == '0'){  
//		toastr.error("请输入正确的驾驶证号码", "提示");
	    return false;  
	}else{
		return true;
	} 
}, "请选择驾驶类型");

$.validator.addMethod("driverYears", function(value, element, params) {
	//  驾驶类型
	var isNum = /^\d+(\.\d+)?$/;
	if(!isNum.test(value)){
		return false;
	}else{
		return true;
	}
}, "请输入正确的驾驶工龄");
$.validator.addMethod("driverYearss", function(value, element, params) {
	var vals = parseInt(value);
	if(vals > 100){
		return false;
	}else{
		return true;
	}
}, "驾驶工龄只能小于100");

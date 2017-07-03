$.validator.addMethod("plateNoLen", function(value, element, params) {
	if(value.length != 5){
		return false;
	}else{
		return true;
	}
}, "车牌号必须是5位");



$.validator.addMethod("plateNoTest", function(value, element, params) {
	var Regx1 = /^[A-Za-z0-9]*$/;
	if (Regx1.test(value)){
		 return true;
	}else{
		return false;
	}
}, "请输入正确的车牌号");

$.validator.addMethod("vinTest", function(value, element, params) {
	var Regx = /^[A-Z0-9]*$/;
	if (Regx.test(value)){
		 return true;
	}else{
		return false;
	}
}, "车架号只能为大写字母跟数字组合");


$.validator.addMethod("vinExists", function(value, element, params) {

	var id = $("#id").val();
	var vin = $("#vin").val();
	var data = {
		id : id,
		vin : vin
	};
	var flag = false;
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : "PubVehicle/CheckPubVehicleVin",
		data : data,
		contentType : 'application/json; charset=utf-8',
		async : false,
		complete:function () {
			
		},
		success : function(status) {
			if(status>0){
				flag = true;
			}
		}
	});
	if(flag){
		return false;
	}else{
		return true;
	}
}, "车架号已存在");



$.validator.addMethod("businessScopeEmpty", function(value, element, params) {
	var vehicleType = $("#vehicleType").val();
	//网约车才判断经营范围
	if(vehicleType=="0") {
		// 经营范围
		var businessScopeDiv = $(".addcbox").children();

		if (businessScopeDiv.length == 0 || document.getElementById("addcboxId").innerHTML == "" || document.getElementById("addcboxId").innerHTML == null) {
			return false;
		}
	}
	return true;

}, "经营区域不能为空");

$.validator.addMethod("minLoads", function(value, element, params) {
	var Regx = /^[1-9][0-9]*$/;

	if (!Regx.test(value)){
 		return false;
	}else{
		return true;
	}

}, "请输入正确荷载人数");

$.validator.addMethod("cityEmpty", function(value, element, params) {
    //城市
    var city = $("#city").val();
    if(city == null || city == ''){
        return false;
    }else{
        return true;
    }

}, "请输入城市");



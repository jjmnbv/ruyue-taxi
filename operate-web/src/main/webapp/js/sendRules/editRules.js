var browserVersion=navigator.appVersion;
var userAgent = navigator.userAgent;
browserVersion = parseInt(userAgent.slice(-4));

if (userAgent.indexOf("Firefox") > -1 && browserVersion <= 30 ) {
  $('#models').css('padding-top','5px')
} //判断是否Firefox浏览器30版本

var dataGrid;
var validator;

/**
 * 页面初始化
 */
$(function () {
	var id = $("#id").val();
	if(null != id && "" != id) {
		$("#taxiSendrulePageTitle").text("修改规则");
	}
	
	initData();
	validateForm();
	initShow();
	
	//派单时间等输入框只能输入0-9
	$("#systemsendinterval,#driversendinterval,#personsendinterval,#increratio,#sendinterval,#carsinterval,#pushnum").keyup(function () {
		var c=$(this);  
        if(/[^\d]/.test(c.val())){//替换非数字字符  
          var temp_amount=c.val().replace(/[^\d]/g,'');  
          $(this).val(temp_amount);  
        }  
   }).bind("paste", function () {  //CTR+V事件处理   
	   $(this).val("");
   }).css("ime-mode", "disabled"); //CSS设置输入法不可用   
	
	//初始化半径和最大半径可保留一位小数
	$("#initsendradius,#maxsendradius").keyup(function () {
		var c=$(this);  
        if(/[^\d\.]/.test(c.val())){//替换非数字字符  
          var temp_amount=c.val().replace(/[^\d\.]/g,'');  
          $(this).val(temp_amount);  
        } 
    }).bind("paste", function () {  //CTR+V事件处理   
	   $(this).val("");
    }).css("ime-mode", "disabled"); //CSS设置输入法不可用   
	
});


/**
 * 初始化城市下拉框数据
 */
function initData() {
	var id = $("#id").val();
	if(null != id && "" != id) {
		$("#usetype").attr("disabled", true);
	} else {
		showCitySelect1(
			".input_box2", 
			"PubInfoApi/GetCitySelect1", 
			$("#citymarkid").val(), 
			function(backVal, $obj) {
				$('#cityname').val($obj.text());
				$("#city").val($obj.data('id'));
				$("#cityname").valid();
			}
		);
	}
} 

/**
 * 表单校验
 */
function validateForm() {
	validator=$("#formss").validate({
		rules: {
			cityName: {required: true},
			systemSendInterval: {required: true, min:1, limitNum:[2,0], digits:true},
			driverSendInterval: {required: true, min:1, limitNum:[2,0], digits:true},
			personSendInterval: {required: true, min:1, limitNum:[2,0], digits:true},
			initSendRadius: {required: true, checkDecimal:true,  number:true},
			maxSendRadius: {required: true,  checkDecimal:true, min:0.1, number:true},
			increRatio: {required: true, maxlength: 3, min:1,limitNum:[3,0], digits:true},
			pushNum: {required: true, maxlength: 2, min:1, digits:true},
			sendInterval: {required: true, maxlength: 2, min:1,limitNum:[2,0], digits:true},
			carsInterval: {required: true, maxlength: 3, min:1, limitNum:[3,0],digits:true}
		},
		messages: {
			cityName: {required: "请选择城市"},
			systemSendInterval: {required: "请输入系统派单时限", min:"请输入大于0正整数",limitNum:"最大为两位正整数", digits:"请输入整数"},
			driverSendInterval: {required: "请输入司机抢单时限", min:"请输入大于0正整数",limitNum:"最大为两位正整数", digits:"请输入整数"},
			personSendInterval: {required: "请输入人工派单时限", min:"请输入大于0正整数",limitNum:"最大为两位正整数", digits:"请输入整数"},
			initSendRadius: {required: "请输入初始派单半径",  number:"只能输入数字"},
			maxSendRadius: {required: "请输入最大派单半径",min:"请输入大于0正数", number:"只能输入数字"},
			increRatio: {required: "请输入半径递增比", min:"请输入大于0正整数", digits:"请输入整数",limitNum:"最大为三位正整数"},
			pushNum: {required: "请输入推送数量", min:"请输入大于0正整数", digits:"请输入整数",limitNum:"最大为两位正整数"},
			sendInterval: {required: "请输入人工派单时限", min:"请输入大于0正整数",limitNum:"最大为两位正整数", digits:"请输入整数"},
			carsInterval:{required:"请输入约车时限", min:"请输入大于0正整数", digits:"请输入正整数",limitNum:"最大为三位正整数"}
		}
	})
}

/**
 * 保存
 */
function save() {
	var form = $("#formss");
	if(!form.valid()){ 
		$("input[type='text']").each(function() {
			if($(this).is(":disabled") || $(this).is(":hidden")) {
				return true;
			}
			if(null == $(this).val() || "" == $(this).val()) {
				toastr.error("请输入完整信息", "提示");
				return false;
			}
		});
		return;
	};
	
	if(!compareValue()) return;
	
	
	var url = "SendRules/CreateSendRules";
	if($("#id").val()) {
		url = "SendRules/UpdateSendRules";
	}
	
	var data = form.serializeObject();
	handleFormData(data);
	if($("#sendtype").val() == "3")//纯人工派单
		data.personSendInterval=data.sendInterval;
	
	
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
            		window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "SendRules/Index";
            	} 
				toastr.success(message, "提示");            	    
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
			}	
		}
	});
}

/**
 * 在新建和修改时处理表单中不可见的元素，将值置为空
 */
function handleFormData(data){
	if(typeof data != 'undefined' && data != null){
		for(var e in data){
			if($("input[name="+e+"]").is(":hidden") && $("input[name="+e+"]").attr("type")!="hidden")
				data[e]="";
		}
		
		if($("input[name=pushNum]").attr("disabled")=="disabled" || $("input[name=pushNumLimit]")=="0")
			data["pushNum"]="";
	}
}

/**
 * 比较约车派单时间等
 */
function compareValue() {
	var message = "";
	 
	var useType=$("#usetype").val();
	var sendType=$("#sendtype").val();
	var sendModel=$("input[name='sendModel']:checked").val();
	
	var systemSendInterval=parseInt($("#systemsendinterval").val(),10);
	var driverSendInterval=parseInt($("#driversendinterval").val(),10);
	var personSendInterval=parseInt($("#personsendinterval").val(),10);
	
	var initSendRadius=parseFloat($("#initsendradius").val());
	var maxSendRadius=parseFloat($("#maxsendradius").val());
	var increRatio=parseInt($("#increratio").val(),10);
	
	var carsInterval=parseInt($("#carsinterval").val(),10);
	 
	if(useType=="1" && (sendType=="0"||sendType=="2") && initSendRadius>maxSendRadius)
	    message="最大派单半径应大于等于最小派单半径";
	
	if(useType=="0" && (sendType=="0"||sendType=="2") && sendModel=="0" && carsInterval<=systemSendInterval){
		message="约车时限应大于系统派单时限";
	}
	
	if(useType=="0" && (sendType=="0"||sendType=="2") && sendModel=="1" && carsInterval<=systemSendInterval+personSendInterval){
		message="约车时限应大于系统派单时限与人工派单时限之和";
	}
	
	if(sendType=="2" && systemSendInterval*60<=driverSendInterval){
	    message="系统派单时限应大于司机抢单时限";
	}
	
	if (message != "") {
		toastr.error(message, "提示");
		return false;
	} else {
		return true;
	}
}

/**
 * 修改时值初始化
 */
function valueFormat() {
	var model = document.getElementById("models");
	var modelValue = $("#model").val();
	for (var i = 0; i < model.length; i++) {
	  if (model[i].value == modelValue) {
		model[i].selected = true;
	  }
	}
	
	$("#city").val($("#cityValue").val());
	$("#cityName").val($("#cityNameHidden").val());
	
	$("#cityName").change(function() {
		changeCity();
	});
}

/**
 * 初始化标签显示
 */
function initShow() {
	validator.resetForm();
	
	var usetype=$("#usetype").val();
	var sendtype = $("#sendtype").val();
	var sendmodel=$("input[name='sendModel']:checked").val();
	
	$("#sendintervalLabel").hide();
	$("#sendmodelLabel").show();
	$("#sendradiusLabel").show();
	$("#pushnumLabel").show();
	$("#pushnumlimitLabel").show();
	
	if(usetype == "1"){//即刻用车
	     $("#carsintervalLabel").hide();
	     sendRadiusShowControl(true,true,true); 
	}else{//预约用车
		 $("#carsintervalLabel").show();
		 sendRadiusShowControl(false,true,false); 
	}
	
	if(sendtype == "0"){//强派
		if(usetype == "1"){
		    pushShowControl(false,true);
		}else{
			pushShowControl(true,true);
		}
		if(sendmodel == "0"){//系统
			sendModelShowControl(true,false,false);
		  }else{//系统+人工
			 sendModelShowControl(true,false,true);
		}
	}else if(sendtype == "2"){//抢单
		  if(sendmodel == "0"){//系统
			  sendModelShowControl(true,true,false);
			  pushShowControl(true,true);
		  }else{//系统+人工
			  sendModelShowControl(true,true,true);
			  pushShowControl(true,true);
		}
	}else{//纯人工
		$("#sendintervalLabel").show();
		$("#sendmodelLabel").hide();
		$("#sendradiusLabel").hide();
		$("#pushnumLabel").hide();
		$("#pushnumlimitLabel").hide();
	}
	
	var pushnumlimit = $("input[name='pushNumLimit']:checked").val();
	if(pushnumlimit == "0") { //不限制
		$("#pushnum").val("");
		$("#pushnum").attr("disabled", true);
	} else {
		$("#pushnum").attr("disabled", false);
	}
}

function sendModelShowControl(system,driver,person){
	system ? $("#systemsendintervalLabel").show() : $("#systemsendintervalLabel").hide();
	driver ? $("#driversendintervalLabel").show() : $("#driversendintervalLabel").hide();
	person ? $("#personsendintervalLabel").show() : $("#personsendintervalLabel").hide();
}

function sendRadiusShowControl(init,max,incre){
	init? $("#initsendradiusLabel").show() : $("#initsendradiusLabel").hide();
	max ? $("#maxsendradiusLabel").show() : $("#maxsendradiusLabel").hide();
	incre ? $("#increratioLabel").show() : $("#increratioLabel").hide();
}

function pushShowControl(pushnum,pushnumlimit){
	pushnum ? $("#pushnumLabel").show() : $("#pushnumLabel").hide();
	pushnumlimit ? $("#pushnumlimitLabel").show() : $("#pushnumlimitLabel").show();
}


/**
 * 选择城市时，向首字母简称赋值
 */
function changeCity() {
	var city = $("#city").val();
	if (city=="") {
		$("#cityInitials").val("");
	} else {
		    var url = "SendRules/GetCityById?city=" + city + "&datetime=" + new Date().getTime();
			
			$.ajax({
				type: 'GET',
				dataType: 'json',
				url: url,
				contentType: 'application/json; charset=utf-8',
				async: false,
				success: function (json) {
					$("#cityInitials").val(json.cityInitials);
				}
			});
	}
}

/**
 * 校验输入的数字格式
 */
$.validator.addMethod("limitNum", function(value, element, param) {
	if(param[1]==0){//验证正整数
		var rep = new RegExp("^[1-9]\\d{0,"+(param[0]-1)+"}$");
		return rep.test(value);
	}else{
		var rep = new RegExp("^[1-9]\\d{0,"+(param[0]-1)+"}(\\.\\d)?$");
		var rep1=/^0(\.[0-9])?$/;
		return rep.test(value)||rep1.test(value);
	}
}, "数字格式不正确");

/**
 * 校验小数格式
 */
$.validator.addMethod("checkDecimal", function(value, element, param) {
	    var fmtrep1=/^\d+(\.\d+)?$/;
	    var fmtrep2=/^0\d+(\.\d+)?$/;
	    var valid=this;
	    
	    if(!fmtrep1.test(value) || fmtrep2.test(value)){
	    	 $(element).data("error-msg","数字格式不正确");
	    	return false;
	    }
	    
	    	if(value.indexOf(".")>=0){
	    		if(value.substr(value.indexOf(".")+1).length>1){
	    			 $(element).data("error-msg","只可保留一位小数");
	    	    	return false;
	    		}else{
	    			return true;
	    		}
	    	}else{//为整数
	    		if(value.length>2){
	    			 $(element).data("error-msg","最大为两位正整数");
	    	    	return false;
	    		}else{
	    			return true;
	    		}
	    	}
	    return rep2.test(value);
}, function(param,element){
	return $(element).data("error-msg");
});

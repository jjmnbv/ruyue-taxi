var windowContent = {
	active : null,
	citylist : null,
	window : null,
	id : null,
	city : null,
	cityname : null,
	autoshifttime : null,
	manualshifttime : null
}

/**
 * 页面初始化
 */
$(function () {
	initWindow();
	initFormRule();
	
	/**
	 * 添加自主交接时间、人工指派交接时间输入限制，只能输入数字
	 */
	 $("#autoshifttime,#manualshifttime").keyup(function () {
//         $(this).val($(this).val().replace(/[^0-9]/g, ''));
//         if(!/[1-9]\d*/g.test($(this).val()))
//        	 $(this).val("");
		 if(this.value.length==1){
			 this.value=this.value.replace(/[^1-9]/g,'')
			 }else{
			 this.value=this.value.replace(/\D/g,'')
			 }
     }).bind("paste", function () {  //CTR+V事件处理    
    	 if(this.value.length==1){
			 this.value=this.value.replace(/[^1-9]/g,'')
			 }else{
			 this.value=this.value.replace(/\D/g,'')
			 }
     }).css("ime-mode", "disabled"); //CSS设置输入法不可用   


});

/**
 * 初始化弹窗
 */
function initWindow(){
	windowContent.window = $("#window");
	initCityActive();
	$("#save").click(function(){
		save();
	});
	$("#cancel").click(function(){
		$("#window").hide();
	});
}

function changeCityCallBack(fullInfo,cityObj){
	$("#cityid").val(cityObj.data("id"));
	$("#cityname").val(cityObj.text());
}

/**
 * 初始化城市控件
 */
function initCityActive(){
	$("#cityid").val(defaultCity.id);
	$("#cityname").val(defaultCity.name);
	var cityParam = {
		container : $("#cityname").parent(),
		url : "PubInfoApi/GetCitySelect1",
		defValue : defaultCity.markid,
		callbackFn : changeCityCallBack
	}
	showCitySelect1(
		cityParam.container,
		cityParam.url,
		cityParam.defValue,
		cityParam.callbackFn
	);
	
//	windowContent.active = new Vcity.CitySelector({
//		id:'cityselect',
//		url:'LeShiftRules/GetCityList',
////		allCity : windowContent.citylist,
//		defaultCity:"深圳市",
//		showInitcial:false,
//		firstClick : false,
//		fnCallBack:function(city){
////			alert(city.city);
//		}
//	});
}

/**
 * 保存
 */
function save(){
	if(!checkValid()) return;
	windowContent.id = $("#id").val();
	windowContent.city = $("#cityid").val();
	windowContent.cityname = $("#cityname").val();
	windowContent.autoshifttime = $("#autoshifttime").val();
	windowContent.manualshifttime = $("#manualshifttime").val();
	var data = {
			id : windowContent.id,
			city : windowContent.city,
			cityname : windowContent.cityname,
			autoshifttime : windowContent.autoshifttime,
			manualshifttime : windowContent.manualshifttime
	};
	$.ajax({
	    type: 'POST',
	    url: "LeShiftRules/"+(windowContent.id==""?"AddRules":"ModifyRules"),
	    dataType: "json",
	    data : JSON.stringify(data),
	    success: function(data){
//	    	console.log(data);
	    	if(data.status == 0){
	    		if(windowContent.id==""){
	    			toastr.success("新增规则成功", "提示");
	    		}else{
	    			toastr.success("维护规则成功", "提示");
	    		}
	    		search();
	    		$("#window").hide();
	    	}else if(data.status != 0){
	    		toastr.error(data.message, "提示");
	    	}
	    },
	    contentType:"application/json"
	});
}

function onlyNum(){
	return (/[\d.]/.test(String.fromCharCode(event.keyCode)));
}

function onlyInt(input){
	/*if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))  
	    if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))  
	    event.returnValue=false;*/
	//input.value=input.value.replace(/[1-9]\d*/g,'')
	if(/[1-9]\d*/g.test(input.value))
	   input.value="";
}

function checkValid(){
	return $("#form").valid();
}

function initFormRule(){
	var hasEmpty = '请输入完整信息';
	//追加正整数验证
	$.validator.addMethod("isInt", function(value, element) {
	   var aint=parseInt(value);	
	   return aint>0&& (aint+"")==value;   
	});
    var rules = {
    		cityname:{
            	required: true
            },
            autoshifttime:{
            	required: true,
            	isInt: true
            },
            manualshifttime:{
            	required: true,
            	isInt: true
            }
        };
    var messages = {
		cityname:{
        	required: "请输入城市名称"
        },
        autoshifttime:{
        	required: "请输入自主交班时限",
        	isInt: "请输入正整数,最小为1"
        },
        manualshifttime:{
        	required: "请输入人工指派时限",
        	isInt: "请输入正整数,最小为1"
        }
    };
    $("#form").validate({
		ignore:"",
		rules : rules,
		messages : messages,
		errorPlacement : function(error, element) {
			error.appendTo(element.parent());  
		},
		invalidHandler : function(form,validator){
			toastr.error(hasEmpty,"提示");
			return false;
		}
    });
}
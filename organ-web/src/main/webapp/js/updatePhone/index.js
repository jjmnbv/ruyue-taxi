$(document).ready(function() {
//		placeholder属性的兼容性问题处理
	if( navigator.appName == "Microsoft Internet Explorer" ){
		placeholder( $(".con_inp").eq(1) , "6-16位字母、符号和数字组成" )
		placeholder( $(".con_inp").eq(2) , "6-16位字母、符号和数字组成" )
	}
});

function save(){
	var phone = $("#phone").val();
	if(phone != ''){
		var myreg = /^(((13[0-9]{1})|(15[0-3,5-9])|(18[0-9]{1})|(14[5,7,9])|(17[0,1,3,5-8]))+\d{8})$/; 
		if(!(myreg.test(phone))){ 
			toastr.error("手机号码格式不对", "提示");
		}else{
			$.post("UpdatePhone/UpdatePhone", {"phone":phone}, function (data) {
				toastr.options.onHidden = function() {
					window.location.href = base+"UpdatePhone/Index";
				}
				toastr.success("更改成功", "提示");
		    });
		} 
	}else{
		toastr.error("手机号码不能为空", "提示");
	}
}

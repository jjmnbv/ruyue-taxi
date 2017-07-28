$(document).ready(function() {
//		placeholder属性的兼容性问题处理
	if( navigator.appName == "Microsoft Internet Explorer" ){
		placeholder( $(".con_inp").eq(1) , "密码必须由8到16位字母、数字和符号(!@#%&$()*+)组成" )
		placeholder( $(".con_inp").eq(2) , "密码必须由8到16位字母、数字和符号(!@#%&$()*+)组成" )
	}
	
//	$("#passwordNew1").blur(function(){
//		$("#submitRed").attr("class","on_inp con_sub");
//		$("#submitRed").attr("onclick","save();"); 
//    })
});
function checkSubmit(){
	var passwordOld = $("#passwordOld").val();
	var passwordNew = $("#passwordNew").val();
	var passwordNew1 = $("#passwordNew1").val();
	if((passwordOld!=null && passwordOld!='' && passwordOld.length>5) && (passwordNew!=null && passwordNew!='' && passwordNew.length>5) && (passwordNew1!=null && passwordNew1!='' && passwordNew1.length>5)){
		$("#submitRed").attr("class","on_inp con_sub");
		$("#submitRed").attr("onclick","save();"); 
	}else{
		$("#submitRed").attr("class","con_inp con_sub con_sub_pre");
		$("#submitRed").removeAttr("onclick"); 
	}
};
function save(){
	var passwordOld = $("#passwordOld").val();
	var passwordNew = $("#passwordNew").val();
	var passwordNew1 = $("#passwordNew1").val();
	if(passwordOld == ''){
		$(".con_right").removeAttr("style");
		$("#con_hint2").html("");
		$("#con_hint3").html("");
		$("#con_hint1").html("必填项");
		return;
	}
	if(passwordNew == ''){
		$(".con_right").removeAttr("style");
		$("#con_hint1").html("");
		$("#con_hint3").html("");
		$("#con_hint2").html("必填项");
		return;
	}
	if(passwordNew1 == ''){
		$(".con_right").removeAttr("style");
		$("#con_hint1").html("");
		$("#con_hint2").html("");
		$("#con_hint3").html("必填项");
		return;
	}
	if(passwordOld === passwordNew){
		$(".con_right").removeAttr("style");
		$("#con_hint1").html("");
		$("#con_hint3").html("");
		$("#con_hint2").html("新密码不能和原密码相同");
//			toastr.error("新密码不能和原密码相同", "提示");
	}else{
		if(passwordNew === passwordNew1){
			debugger;
//			var reg = /^(\w){8,16}$/;
			var reg1 = /\d+/;
			var reg2 =/[a-zA-Z]+/;
            var reg3 =/(?=.*[a-z])(?=.*\d)(?=.*[!@#%&$()*+])[a-z\d!@#%&$()*+]{8,16}/i;
            // var reg3 = /[\.@#\$%\^&\*\(\)\[\]\\?\\\/\|\-~`\+\=\,\r\n\:\'\"]+/;
			var b = !reg1.exec(passwordNew);
			var c = !reg2.exec(passwordNew);
			var d = !reg3.exec(passwordNew);
			if(passwordNew.length > 7 && passwordNew.length < 17 && reg1.exec(passwordNew) && reg2.exec(passwordNew) && reg3.test(passwordNew)){

				$.post("UpdatePassword/CheckPasswords", {"userpassword":passwordOld}, function (data) {
					if(data.count > 0){
						$.post("UpdatePassword/updatePassword", {"userpassword":passwordNew}, function (data) {
							$("#con_hint1").html("");
							$("#con_hint2").html("");
							$("#con_hint3").html("");
							toastr.options.onHidden = function() {
								window.location.href = base+"User/Logout";
							}
							toastr.success("密码更改成功", "提示");
					    });
					}else{
						if(data.logontimes == 0){
							$(".con_right").removeAttr("style");
							$("#con_hint2").html("");
							$("#con_hint3").html("");
							$("#con_hint1").html("密码错误已达5次，请明日再试");
							$("#con_hint1").attr("style","color: gray;");
//  							toastr.error("密码错误已达5次，请明日再试", "提示");
						}else{
							$(".con_right").removeAttr("style");
							$("#con_hint2").html("");
							$("#con_hint3").html("");
							$("#con_hint1").html("原密码输入错误"+data.logontimes+"次后，当天将不能再进行密码更改");
//  							toastr.error("原密码输入错误"+data.logontimes+"次后，当天将不能再进行密码更改", "提示");
						}
					}
				});
			}else{
				$(".con_right").removeAttr("style");
				$("#con_hint1").html("");
				$("#con_hint3").html("");
				$("#con_hint2").html("密码由8-16位的英文字母、符号和数字组成");
//  				toastr.error("密码有6-16位的大小写英文字母、符号和数字组成", "提示");
			}
		}else{
			$(".con_right").removeAttr("style");
			$("#con_hint1").html("");
			$("#con_hint2").html("");
			$("#con_hint3").html("两次输入的新密码不一致");
//  			toastr.error("两次输入的新密码不一致", "提示");
		}
	}
	
}

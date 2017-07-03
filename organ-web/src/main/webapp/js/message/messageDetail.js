
/**
 * 页面初始化
 */
$(function () {
	initUnreadNum();
});

/**
 * 初始化未读消息
 */
function initUnreadNum() {
	var unReadNum = $("#unReadNum").val();
	if (unReadNum > 0) {
		$("#num").addClass("num");
		$("#num").show();
	} else {
		$("#num").removeClass("num");
		$("#num").hide();
	}
}

function back() {
	window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "Message/Index";
}

function showInfo(){
	$.ajax({
			type: "GET",
			url:"User/GetCurrentUserInfo",
			cache: false,
			data: null,
			success: function (json) {
				if(json.status=="success"){
					if(json.userinfo){
						$("#nickname_i").html(json.userinfo.nickname);
						$("#account_i").html(json.userinfo.account);
						
						$("#sex_i").html(json.userinfo.sex=="0"?"男":"女");
						$("#role_i").html(json.userinfo.rolename);
						$("#dept_i").html(json.userinfo.deptname);
						$("#img_i").attr("src",json.userinfo.img);
					}
					$("#userinfo").show();
				}else if(json.status=="super"){
					//超管不弹窗
				}
			},
			error: function (xhr, status, error) {
				return;
			}
	    });
	
}
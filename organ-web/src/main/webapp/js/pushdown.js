
var pushdown_t;
/**
 * 检查usertoken是否失效
 * 是否是被挤下线了
 */
function checkValid(){
	var url = "User/CheckValid";
	$.ajax({
		type:"post",
		url:url,
		cache:false,
		data:"{}",
		contentType : "application/json",
		dataType : "json",
		success:function(json){
			if(json!=null&&json.status=="success"){
				if(json.pushdown){
					//挤下线了
					showPushDown();
//					window.location.href = "User/Logout";
					if(pushdown_t){
						clearTimeout(pushdown_t);
					}
				}else{
					//没有挤下线
					pushdown_t = setTimeout(function(){
						checkValid();
					},2000);
				}
			}
		}
  });
}

/**
 * 显示挤下线弹框
 */
function showPushDown(){
	var parentdiv = document.createElement("div");
	parentdiv.style.display="block";
	parentdiv.style.zIndex="999999";
	parentdiv.className="popup_box";
	var contenthtml = "<div class='popup' style='width:600px;'><div class='popup_title'><span>账号异常提醒</span><i class='close' onclick='go2login()'></i></div><div class='popup_content'><label>检测到您的账号在其他地点登录，您被迫下线。<br> 若非您本人登录，请注意保护您的账号安全，及时修改密码。</label></div><div class='popup_footer'><span class='sure' onclick='go2login()'>重新登录</span></div></div>";
	parentdiv.innerHTML = contenthtml;
	window.top.document.body.appendChild(parentdiv);
}

var go2login = function(){
	var base="";
	if(document.getElementsByTagName("base")){
		base= document.getElementsByTagName("base")[0].getAttribute("href");
	}
	window.top.location.href=base+"User/Logout";
};

$(function(){
	checkValid();
});
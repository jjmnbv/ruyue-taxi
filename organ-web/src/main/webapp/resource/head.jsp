<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="header">
	<div class="header_box">
		<img src="content/img/logo.png" class="header_logo"/>
		<ul class="menu">
			<c:forEach items="${navinfo}" var="nav" varStatus="status">
				<li class="${nav.cssclass}"><a href="${nav.href}">${nav.menuname}</a></li>
			</c:forEach>
		</ul>
		<div class="sub">
			<span onclick="showInfo();">${username}</span> <span class="news"><a href="Message/Index">消息<i id="num"><span id="unReadNum">${unReadNum}</span></i></a></span><span><a href="User/Logout">退出</a></span>
		</div>
	</div>
	<img src="content/img/img_banner.png" alt="banner" class="banner">
	<script type="text/javascript" src="js/pushdown.js"></script>
	<!--弹窗开始-->
	<div class="popup_box" style="z-index:2;" id="userinfo">
	   <div class="batch_change popup" style="height:327px;">
				<div class="popup_title">
					<span>个人资料</span>
					<i class="close"></i>
				</div>
				<div class="popup_content" style="height:200px;">
					<table style="width:100%;margin:0 48px;">
						<tr>
							<td>姓名&nbsp;&nbsp;&nbsp;<label id="nickname_i"></label></td>
							<td>手机号码&nbsp;&nbsp;&nbsp;<label id="account_i"></label></td>
						</tr>
						<tr>
							<td>性别&nbsp;&nbsp;&nbsp;<label id="sex_i"></label></td>
							<td>角色&nbsp;&nbsp;&nbsp;<label id="role_i"></label></td>
						</tr>
						<tr>
							<td style="vertical-align:baseline;"><label>所属部门</label>&nbsp;&nbsp;&nbsp;<label id="dept_i"></label></td>
							<td><label style="vertical-align:top;">头像</label>&nbsp;&nbsp;&nbsp;<img id="img_i" style="width:80px;height:80px;" src="www.sdcs" /></td>
						</tr>
					</table>
				</div>
				<div class="popup_footer">
					<span class="sure close">确定</span>
				</div>
		</div>
	</div>
	<script type="text/javascript">
		var unReadNum = ${unReadNum};
		if (unReadNum > 0) {
			$("#num").addClass("num");
			$("#num").show();
		} else {
			$("#num").removeClass("num");
			$("#num").hide();
		}
	
		if($("#ordertype")[0]){
			$(".header_box ul li").eq($("#ordertype").val()).addClass("current");
		}else{
			$(".header_box ul li").eq(0).addClass("current");
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
		function initCount() {
			$.ajax({
				type: "GET",
				url:"Message/GetUnReadNewsCount",
				cache: false,
				data: null,
				success: function (json) {
					$("#unReadNum").text(json);
					if (json > 0) {
						$("#num").addClass("num");
						$("#unReadNum").show();
					} else {
						$("#num").removeClass("num");
						$("#unReadNum").hide();
					}
				},
				error: function (xhr, status, error) {
					return;
				},
				complete: ajaxComplete
		    });
		}
		//定时查找未读消息数量
	    window.onload = function () {
	    	setInterval(initCount, 2000);
	    }
	</script>
</div>

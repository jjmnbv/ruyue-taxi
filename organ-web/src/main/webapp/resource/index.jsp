<%@ page contentType="text/html; charset=UTF-8" import="com.szyciov.util.SystemConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String page_CopyrightDescription_value = SystemConfig.getSystemProperty("page_CopyrightDescription_value");
	String page_Title_value = SystemConfig.getSystemProperty("page_Title_value");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="description" content="">
	<meta name="keywords" content="">
	<title><%=page_Title_value%></title>
	<link rel="Bookmark" href="content/img/bitbug_favicon.ico">
  	<link rel="Shortcut Icon" href="content/img/bitbug_favicon.ico"> 
	<link rel="stylesheet" type="text/css" href="content/css/index.css" />
	<link rel="stylesheet" type="text/css" href="content/css/shouyelogoutdialog.css" />
	<script type="text/javascript" src="content/js/jquery.js"></script>
	<script type="text/javascript" src="content/js/common.js"></script>
	<script type="text/javascript">
	    var shouldlogout = '${shouldlogout}';
	    var account = '${account}';
	    var differentip = '${differentip}';
  	</script>
</head>
<body>
	<%@include file="head.jsp"%>
	<div class="con">
		<div class="sidebar">
			<h2>个人中心</h2>
			<ul>
				<c:forEach items="${menuinfo}" var="menu" varStatus="status">
					<c:choose>
	              	  <c:when test="${status.count==1}">
	                     <li class="${menu.cssclass} current"><a href="${menu.href}" target="iframe">${menu.menuname}</a></li>
		              </c:when>
		              <c:otherwise>
		              	<li class="${menu.cssclass}"><a href="${menu.href}" target="iframe">${menu.menuname}</a></li>
			          </c:otherwise>
	              	</c:choose>
				</c:forEach>
			</ul>
		</div>
		<div class="content">
			<div class="con_box">
				<iframe src="${menuinfo[0].href}" id="iframe" name="iframe" frameborder=no onload="reinitIframeEND()"></iframe>
			</div>
		</div>
	</div>
	<div class="footer"><%=page_CopyrightDescription_value%></div>
	<div class="pop_index"></div>
	
	<div class="popup_box" id="shouldtips_dialog" style="z-index:3;">
		<div class="hint_win popup" style="width:525px;">
			<div class="popup_title">
				<span>账号异常提醒</span>
				<i class="close"></i>
			</div>
			<div class="popup_content">
				<span>检测您本次登录的IP地址和上次采用${different4account}帐号登录的地址不同。</span><br/>
				<span>若非您本人登录，请注意你的账号安全，即时修改密码。</span>
			</div>
			<div class="popup_footer">
				<span class="sure">确定</span>
			</div>
		</div>
	</div>
	
	<div class="popup_box" id="shouldlogout_dialog" style="z-index:2;">
		<div class="hint_win popup">
			<div class="popup_title">
				<span>提示</span>
				<i class="close"></i>
			</div>
			<div class="popup_content">
				<b>你好：</b><br/>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你所使用的账户需要关联主账号才能使用，请先使用主账号登录进行关联！</span>
			</div>
			<div class="popup_footer">
				<span class="sure">去关联账号</span>
			</div>
		</div>
	</div>
</body>
<script>
var base = document.getElementsByTagName("base")[0].getAttribute("href");
	var orderno = '${orderno}';
	$(document).ready(function() {
		if(account){
			setCookie("organ_account",account);
		}
		$(".sidebar li").click(function() {
			$(this).addClass("current").siblings().removeClass("current");
		});
		$("#shouldlogout_dialog .sure,#shouldlogout_dialog .close").click(function(){
			window.location.href = base+"User/Logout";
		});
		$("#shouldtips_dialog .sure,#shouldtips_dialog .close").click(function(){
			$("#shouldtips_dialog").hide();
		});
		initCount();
		if(shouldlogout=='true'){
			$("#shouldlogout_dialog").show();
			return;
		}
		if(differentip=='true'){
			$("#shouldtips_dialog").show();
		}
		
		if(orderno){
			go2Order(orderno);
		}
		
		// 监听滚动事件
    	/* $(iframe.contentWindow.document).find('#toast-container').css('center', $(window).scrollTop()+290); */
		
		$(window).on('scroll', popScroll);
	   	popScroll();
	    function popScroll(){
	    	$(iframe.contentWindow.document).find('#toast-container').css('top', $(window).scrollTop()+120);
	    	// $(iframe.contentWindow.document).find('.tip_box_d').css('margin-top', scrTop+80);
	    }
	});
	
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

	//iframe高度自适应

  var iframe = document.getElementById("iframe"); 
  preheight=0;
  function reinitIframe(){ 
	 try{
    	  var bHeight = iframe.contentWindow.document.body.scrollHeight;  
		  var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;  
	      var height = Math.max(bHeight, dHeight); 
	      var keyH=height-preheight;
	      if(keyH>30){      	
	    	  iframe.style.height = height + "px";
	        	 preheight=height;
	          }   
	} catch(ex){}
 }  
	      
 
  function reinitIframeEND(){  
	  window.clearInterval(timer-1); 
	  preheight=0;
	  iframe.style.height ="";
	  var timer = window.setInterval("reinitIframe()", 2000); //定时开始       
  }
  function go2Order(orderno){
	  $(".sidebar ul li a:contains('我的订单')").parent().click();
	  $("#iframe").attr("src","MyOrder/Details?id=" + orderno);
  }
  
  window.onscroll=function(){
	    var scrollTop=document.documentElement.scrollTop||document.body.scrollTop;
	    document.getElementById("iframe").contentWindow.bscroll(scrollTop);
	 

	}
</script>
</html>
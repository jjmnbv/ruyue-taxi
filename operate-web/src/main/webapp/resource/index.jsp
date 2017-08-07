<%@ page contentType="text/html; charset=UTF-8" import="com.szyciov.util.SystemConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%   
String path = request.getContextPath();   
String basePath = request.getScheme()+"://" +request.getServerName()+":" +request.getServerPort()+path+"/" ;
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
  <meta name="author" content="好约车">
  <title><%=page_Title_value%></title>
  <link rel="Bookmark" href="content/img/bitbug_favicon.ico">
  <link rel="Shortcut Icon" href="content/img/bitbug_favicon.ico"> 
  <link rel="stylesheet" type="text/css" href="content/css/index.css"/>
	<link rel="stylesheet" type="text/css" href="css/index/pop.css"/>
  <link rel="stylesheet" type="text/css" href="css/index/index.css"/>
  <link rel="stylesheet" type="text/css" href="content/plugins/toastr/toastr.css" />
  <link rel="stylesheet" type="text/css" href="content/css/style.css" />
  <!--样式修改-->
  <style type="text/css">
  	#userinfo{
  		background: none;
  		line-height: 300%;
  	}
  	#userinfo::after{
  		content:'';
	    background: url(content/img/btn_r.png) no-repeat center right;
	    width: 20px;
    	height: 45px;
    	display: block;
    	float:right;
  	}
  	.index_css_label_1{
  		padding-right:12px !important;
  	}
  	label.error{
  	    padding-left: 0px !important;  
  	}
  </style>
  
  <script type="text/javascript" src="content/js/jquery.js"></script>
  <script type="text/javascript"  src="content/js/common.js"></script>
  <script type="text/javascript" src="content/plugins/toastr/toastr.min.js"></script>
  <script type="text/javascript" src="js/basecommon.js"></script>
	<script type="text/javascript" src="js/pop.js"></script>

	<script type="text/javascript" src="content/plugins/jquery-validate/js/jquery.validate.js"></script>
	<script type="text/javascript" src="content/plugins/jquery-placeholder/jquery.placeholder.min.js"></script>
</head>
<body>
<div class="header_bar">
<input name="baseUrl" id="baseUrl" value="<%=basePath%>" type="hidden">
	<a href="Message/Index" target="iframe"><i class="news">
    <div id="newsNum"><span id="unReadNum"></span></div>
      <!-- <li>报警提醒<span class="font_red">1239</span>条</li>
      <li>年检提醒<span class="font_red">1230</span>条</li>
      <li>车务提醒<span class="font_red">1230</span>条</li>
      <li><a href="Message/Index" target="iframe">查看更多 > </a></li> -->
  </i></a>
  <!-- <i class="news">
    <div class="news_num"><span id="unReadNum"></span></div>
    <ul class="news_li">
      <li>报警提醒<span class="font_red">1239</span>条</li>
      <li>年检提醒<span class="font_red">1230</span>条</li>
      <li>车务提醒<span class="font_red">1230</span>条</li>
      <li><a href="Message/Index" target="iframe">查看更多 > </a></li>
    </ul>
  </i> -->
  
  <img src="img/icon_user84.png " alt="用户头像" class="portait">
  <ul class="operation">
    <li id="userinfo"><img src="img/icon_user84.png" alt="用户头像" class="">${nickname} </li>
    <li id="changepwd">修改密码</li>
    <li><a href="User/Logout">安全退出</a></li>
  </ul>
</div>
<ul class="side_bar">
  <!--menu开始-->
	<c:forEach items="${menulist}" var="menu" varStatus="status">
		<%-- <c:if test="${status.count==3}">
      		<li><a href="javascript:void(0);" onclick="getLogin();" target="iframe"><i class="ico xitongguanli"></i>车辆监管</a></li>					
		</c:if> --%>
	    <c:choose>
              <c:when test="${not empty menu.children}">
              	 <c:choose>
	              	 <c:when test="${status.count==1}">
	                  	<li class="current on">
	                 </c:when>
			         <c:otherwise>
			         	<li>
			         </c:otherwise>
		         </c:choose>
		            <i class="${menu.cssclass}"></i>${menu.menuname}
      				<ul class="submenu">
      					<c:forEach items="${menu.children}" var="subMenu">
      						<c:choose>
      							<c:when test="${not empty subMenu.children}">
      								<li>
								        <div class="sub_l"><i class="${subMenu.cssclass}"></i>${subMenu.menuname}</div>
								        	<ul class="sub_r">
								        		<li>
									        		<c:forEach items="${subMenu.children}" var="subbMenu">
									        			<a href="${subbMenu.applicationdomain}${subbMenu.url}" target="iframe">${subbMenu.menuname}</a>
									        		</c:forEach>
								        		</li>
								        	</ul>
								    </li>
      							</c:when>
      							<c:otherwise>
      								<li>
								        <%-- <div class="sub_l"><i class="ico ico_s_1${subMenu.cssclass}"></i>${subMenu.menuname}</div> --%>
								        <div class="sub_l"><a href="${subMenu.applicationdomain}${subMenu.url}" target="iframe"><i class="${subMenu.cssclass}"></i>${subMenu.menuname}</a></div>
								    </li>
      							</c:otherwise>
      						</c:choose>
      					</c:forEach>
      				</ul>	
		          </li>
              </c:when>
              <c:otherwise>
             	 	<c:choose>
	              	  <c:when test="${status.count==1}">
	                     <li class="current on"><a href="${menu.applicationdomain}${menu.url}" target="iframe"><i class="${menu.cssclass}"></i>${menu.menuname}</a></li>
		              </c:when>
		              <c:otherwise>
		              	 <li><a href="${menu.applicationdomain}${menu.url}" target="iframe"><i class="${menu.cssclass}"></i>${menu.menuname}</a></li>
		              </c:otherwise>
	              	</c:choose>
              </c:otherwise>
         </c:choose>
	</c:forEach>
	<!--menu结束-->
	<%-- <c:if test="${fn:length(menulist)<3}">
		<li><a href="javascript:void(0);" onclick="getLogin();" target="iframe"><i class="ico xitongguanli"></i>车辆监管</a></li>					
	</c:if> --%>
</ul>

<div id="iframebox" class="main">
	<iframe  src="${home_href}" id="iframe" name="iframe" frameborder=no  onload="reinitIframeEND()"></iframe>
</div>
<div class="footer"><%=page_CopyrightDescription_value%></div>
<div class="pop_box" id="editFormDiv" style="display: none;">
			<div class="tip_box_b">
	            <h3 id="title">修改密码</h3>
	            <img src="content/img/btn_guanbi.png" onclick="canel()" class="close" alt="关闭">
	            <div class="w400">
	            	<form id="editForm" method="get" class="form-horizontal  m-t" id="frmmodal">
	            		<label>旧密码<em class="asterisk"></em></label>
		                <input id="oldpassword" name="oldpassword" type="password" placeholder="8到16位字母、数字和符号"><br>
		                <label>新密码<em class="asterisk"></em></label>
		                <input id="password" name="password" type="password" placeholder="8到16位字母、数字和符号"><br>
		                <label>重复新密码<em class="asterisk"></em></label>
		                <input id="password2" type="password" name="password2" placeholder="8到16位字母、数字和符号"><br>
	            	</form>
	                <button id="save" class="Lbtn red" onclick="save()">提交</button>
	                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
	            </div>
	        </div>
</div>

<div class="pop_box" id="userInfoFormDiv" style="display: none;">
		<div class="tip_box_b">
            <h3 id="title">个人信息</h3>
            <img src="content/img/btn_guanbi.png" onclick="canel()" class="close" alt="关闭">
            <div class="w400">
            	<form id="userInfoForm" method="get" class="form-horizontal  m-t" id="frmmodal">
            		<label>角色类别：</label> <input type="text" name="roletype_caption" disabled="disabled" style="border:none;background-color:white;"><br>
	                <label>用户姓名：</label> <input type="text" name="nickname_caption" disabled="disabled" style="border:none;background-color:white;"><br>
	                <label>用户账号：</label> <input type="text" name="account_caption" disabled="disabled" style="border:none;background-color:white;"><br>
	                <label class="index_css_label_1"><em class="asterisk"></em>手机号：</label><input type="text" value="${user.telphone}" id="userphone" name="telphone"><br>
	                <label>角色名称：</label> <input type="text" name="rolename_caption" disabled="disabled" style="border:none;background-color:white;"><br>
	                <label>邮箱：</label> <input type="hidden" name="email"><input type="text" name="email_caption" disabled="disabled" style="border:none;background-color:white;"><br>
            	</form>
                <button id="saveuserinfo" class="Lbtn red" onclick="saveUserInfo()">提交</button>
                <button class="Lbtn grey" style="margin-left: 10%;" onclick="canel()">取消</button>
            </div>
        </div>
</div>

<!--人工指派交接班弹窗 start-->
<div class="pop_box" id="notcashdiv" style="display: none;">
	<div class="tip_box_b">
		<h3>提示</h3>
		<img src="content/img/btn_guanbi.png" class="close" alt="关闭">
		<div class="w400">
			<div class="row">
				<div class="col-12">
					<input type="radio" value="0" class="col-3" style="margin-top: 4px;padding: 0px;" name="shftType" checked="checked">
					<label style="float: left;text-align: left">选择接班司机</label>
					<select id="plateNoProvince" class="col-5" style="margin-top: -5px;line-height: 100%;" name="plateNoProvince">
						<option value="">请选择</option>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<input type="radio" class="col-3" value="1" style="margin-top: 4px;padding: 3px;line-height: 100%;" name="shftType">
					<label style="float: left;text-align: left">车辆回收</label>
				</div>
			</div>
			<input type="hidden" id="pendingId" name="orderno">


			<button class="Lbtn red" onclick="processed()">确定</button>
			<button class="Lbtn grey" style="margin-left: 10%;" onclick="cancelNotDiv()">取消</button>
		</div>
	</div>
</div>

<!--人工指派交接班弹窗 end-->
<script>
/* function getLogin(){
	window.location.href ="http://www.cardata.com.cn/";
} */
jQuery.validator.addMethod("phon", function(value, element){
	var length = value.length;
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]){1})+\d{8})$/;
	return this.optional(element) || (length == 11 && myreg.test(value));
}, "请输入正确的号码");
  $(document).ready(function() {
	 // setInterval('getMessage()',3000);

	  validateForm();
  	var m=0;
  	$(".side_bar li").mouseover(function (e) {
        var Ey=e.clientY;
        var range=Math.abs(Ey-m);
        $(this).addClass("on").siblings().removeClass("on");
        if(range>35){
        var th=$(this).find(".submenu");
        var windowH=$(window).height();
        var y=windowH-Ey;
        var L=th.children("li").length*90;
        if(y<L){
          var top=y-L-10;
          th.css({"top":top,'bottom':'initial'});
          if(y<90){
          	th.css({
          		"bottom": 0,
          		'top': 'initial'
          	});
          }
        }else{
        	th.css({"top":'0','bottom':'initial'});
        }
        m=Ey;
        }
    });
    $(".side_bar li").mouseout(function () {
      $(".side_bar li").removeClass("on");
    });
    $(".side_bar>li").on("click",function(){
      $('body').css({
    	  'overflow': 'initial',
    	  'overflow': 'auto'
      });
      $(this).addClass("current").siblings().removeClass("current");
    });
    $(".news").mouseover(function(){
      $(".news_li").show();
      $(".operation").hide();
    });
    $(".portait").mouseover(function(){
      $(".operation").show();
      $(".news_li").hide();
    });
    $(".operation").hover(function(){
        $(".operation").show();
      }, function(){
        $(".operation").hide();
      });
    $("body").click(function(){
        $(".operation").hide();
        $(".news_li").hide();
    });
    
    $(window).on('load resize', function(){
    	var  maxwidth=900;
    	if($(window).width()>=maxwidth){
	        var ww=$(window).width()-110;
	        $("#iframebox").width(ww);
	        $(".submenu").css("left","90px");
	      	iframe.contentWindow.document.body.style.overflow = "hidden"; 
        }else{
        	$("#iframebox").width(maxwidth-110);
        	$(".submenu").css("left","74px");
        	iframe.contentWindow.document.body.style.overflow = "hidden"; 
        }
    });
	// 监听滚动事件
   	$(window).on('scroll', popScroll);
   	popScroll();
    function popScroll(){
    	var scrTop = $(window).scrollTop() - 60;
    	if( scrTop >=0 ){
	    	$(iframe.contentWindow.document).find('.tip_box_a').css('margin-top', scrTop+80);
	    	$(iframe.contentWindow.document).find('.tip_box_b').css('margin-top', scrTop+80);
	    	$(iframe.contentWindow.document).find('.tip_box_c').css('margin-top', scrTop+30);
	    	$(iframe.contentWindow.document).find('.tip_box_d').css('margin-top', scrTop+80);
	    	$(iframe.contentWindow.document).find('.tip_box_e').css('top', scrTop+50);
    	}else{
    		$(iframe.contentWindow.document).find('.tip_box_a').css('margin-top', '80px');
	    	$(iframe.contentWindow.document).find('.tip_box_b').css('margin-top', '80px');
	    	$(iframe.contentWindow.document).find('.tip_box_c').css('margin-top', '50px');
	    	$(iframe.contentWindow.document).find('.tip_box_d').css('margin-top', '80px');
	    	$(iframe.contentWindow.document).find('.tip_box_e').css('top', 50);
    	}
    	$(iframe.contentWindow.document).find('#toast-container').css('top', $(window).scrollTop()+290);
    	// $(iframe.contentWindow.document).find('.tip_box_d').css('margin-top', scrTop+80);
    }
    
  	//修改密码
    $("#changepwd").click(function(){
    	showObjectOnForm("editForm", null);
    	var editForm = $("#editForm").validate();
    	editForm.resetForm();
    	editForm.reset();
    	$("#editFormDiv").show();
    });
  	
    $("#userinfo").click(function(){
    	$.ajax({
  			type: "GET",
  			url:"User/GetCurrentUserInfo",
  			cache: false,
  			data: null,
  			success: function (json) {
  				showObjectOnForm("userInfoForm", json); 
	  	    	var editForm = $("#userInfoForm").validate();
	  	    	editForm.resetForm();
	  	    	editForm.reset();
	  	    	$("#userInfoFormDiv").show();
  			},
  			error: function (xhr, status, error) {
  				return;
  			}
  	    });
    });
  	
    //查找未读消息数量
 /*    function initCount() {
		$.ajax({
			type: "GET",
			url:"Message/GetUnReadNewsCount",
			cache: false,
			data: null,
			success: function (json) {
				$("#unReadNum").text(json);
				if (json > 0) {
					$("#newsNum").addClass("news_num");
					$("#unReadNum").show();
				} else {
					$("#newsNum").removeClass("news_num");
					$("#unReadNum").hide();
				}
			},
			error: function (xhr, status, error) {
				return;
			},
			complete: ajaxComplete
	    });
	} */
    //定时查找未读消息数量
    window.onload = function () {
    	//setInterval(initCount, 2000);
    }
    
 	// 处理弹窗后禁止滚动
	setInterval(function(){
		var $popBox = $(iframe.contentWindow.document).find('.pop_box');
		var tempFlag = false;
		for(var i=0; i<$popBox.length; i++){
			if( $popBox.eq(i).css('display') == 'block'){
				tempFlag = true;
			}
		};
		if( tempFlag ){
			$('body').css('overflow', 'hidden');
		}else{
			$('body').css({
				'overflow': 'initial',
				'overflow': 'auto'
			});
		}
	},100);
    
  });
  
  
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
	  var timer = window.setInterval("reinitIframe()", 1000); //定时开始
  }  
  
  /**
   * 表单校验
   */
  function validateForm() {
  	$("#editForm").validate({
  		rules: {
  			oldpassword: {required: true,maxlength: 16},
  			password:{required: true,minlength:8,maxlength: 16,checkPassword:true},
  			password2:{required: true,minlength:8,maxlength: 16,equalTo:"#password"}
  		},
  		messages: {
  			oldpassword: {required: "请输入正确长度的密码", maxlength: "最大长度不能超过16个字符"},
  			password: {required: "请输入正确长度的密码",minlength:"最小长度不能少于8个字符", maxlength: "最大长度不能超过16个字符",checkPassword:"必须为字母、数字和符号(!@#%&$()*+)组成"},
  			password2:{required: "请输入正确长度的密码",minlength:"最小长度不能少于8个字符", maxlength: "最大长度不能超过16个字符",equalTo:"两次密码需相同"}
  		}
  	});
  	
  	$("#userInfoForm").validate({
  		rules: {
  			telphone:{required: true,minlength:11,maxlength:11,phon:true}
  		},
  		messages: {
  			telphone:{required: "请输入正确的号码",minlength:"请输入正确的号码",maxlength:"请输入正确的号码",phon:"请输入正确的号码"}
  		}
  	});
  }
  
  /**
   * 保存
   */
  function save() {
  	$("#save").attr({"disabled":"disabled"});
  	var form = $("#editForm");
  	if(!form.valid()){
  		$("#save").removeAttr("disabled");
  		return;
  	} 
  	
  	var url = "User/ChangePwd";
  	var data = form.serializeObject();
  	
  	$.ajax({
  		type: 'POST',
  		dataType: 'json',
  		url: url,
  		data: JSON.stringify(data),
  		contentType: 'application/json; charset=utf-8',
  		async: false,
  		success: function (status) {
  			var message = status.message;
  			if (status.status == "success") {
         		toastr.success(message, "提示");
  				$("#editFormDiv").hide();
  				showObjectOnForm("editForm", null);
  			} else {
  				toastr.error(message, "提示");
  			}
  			$("#save").removeAttr("disabled");
  		},
  		error:function(XMLHttpRequest, textStatus, errorThrown){
  			$("#save").removeAttr("disabled");
  		}
  	});
  }


	/* function getMessage(){
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: "User/WarnMessage",
			contentType: 'application/json; charset=utf-8',
			async: false,
			success: function (resultObj) {
				if(resultObj!=null && resultObj.length>0 ){
					for(var i=0;i<resultObj.length;i++){
						var messageObj = resultObj[i];
						showPops(messageObj);
					}}
			},
			complete: ajaxComplete
		});
	} */


	function showPops(messageObj){
		switch (messageObj.business) {
			case "pending#":
				if(messageObj.operation=='make#'){
					showPendingPop(messageObj.message);
				}
				break;
			case "order#":
				if(messageObj.operation=='make#'){
					//出租车
					if(messageObj.message.orderstyle=='1'){
						showTaxiLabourPop(messageObj.message)
					}else{
						showLabourPop(messageObj.message);
					}
				}else if(messageObj.operation=='read#'){
					showSuccessPop(messageObj.message);
				}
				break;
			case "alarmprocess#":
				if(messageObj.operation=='make#'){
					showAlarmprocessPop(messageObj.message);
				}
				break;
			default:
				// ...
		}
	}

  function showSuccessPop(resultObj){
	  var pop = new Pop(resultObj.title,
			  "",
			  "<table class=\"table_a\"> " +
			  "<tr style='height: 30px'><td class=\"grey_c\">接单司机：</td><td>"+resultObj.driverName+"</td>" +
			  "<td class=\"grey_c\">车牌号：</td><td>"+resultObj.carNum+"</td></tr>" +
			  "<tr style='height: 30px'>" +
			  "<td class=\"grey_c\">品牌车系：</td><td>"+resultObj.carBrand+"</td>" +
			  "<td class=\"grey_c\">车型：</td><td>"+resultObj.carType+"</td></tr>" +
			  "<tr style='height: 30px'><td class=\"grey_c\">订单号：</td><td colspan='3'>"+resultObj.orderNum+"</td></tr></table>"
			  ,resultObj.orderNum,
			  false);
	  pop.showDiv();
	  pop.closeDiv();
  }

  function toOrderPage(orderNum,paymethod) {
	  //如果是机构支付，改标记为0 以适应页面显示
	  if(paymethod=="2"){
		  paymethod="0";
	  }
	  $('#iframe')[0].src='OrderManage/ManualSendOrderIndex?orderno='+orderNum+'&type=1';

	  $('#'+orderNum).remove();

  }



function showLabourPop(resultObj){
	  var pop = new Pop(resultObj.title,
			  "toOrderPage(\""+resultObj.orderNum+"\",\""+resultObj.paymethod+"\")",
			  "<table class=\"table_a\">" +
			  "<tr style='height: 30px'>" +
			  "<td class=\"grey_c\">订单类型：</td><td style='text-align: left'>"+resultObj.orderType+"</td>" +
			  "<td class=\"grey_c\">用车时间：</td><td >"+resultObj.timeStr+"</td></tr>" +
			  "<tr style='height: 30px'>" +
			  "<td class=\"grey_c\">下单车型：</td><td >"+resultObj.carType+"</td>" +
			  "<td class=\"grey_c\">下单人：</td><td >"+resultObj.userName+"</td></tr>" +
			  "<tr style='height: 30px'>" +
			  "<td class=\"grey_c\">订单号：</td><td colspan='3'>"+resultObj.orderNum+"</td>" +
			  "</tr></table>",
			  resultObj.orderNum,
			  true);
	  pop.showDiv();
	  pop.closeDiv();

  }

  //跳转到出租车待人工派单
function toTaxiOrderPage(orderNum) {
	$('#iframe')[0].src='TaxiOrderManage/ManualSendTaxiOrderIndex?orderno='+orderNum+'&type=1';

	$('#'+orderNum).remove();

}

function showTaxiLabourPop(resultObj){
	var pop = new Pop(resultObj.title,
			"toTaxiOrderPage(\""+resultObj.orderNum+"\")",
			"<table class=\"table_a\">" +
			"<tr style='height: 30px'>" +
			"<td class=\"grey_c\">订单类型：</td><td style='text-align: left'>出租车</td>" +
			"<td class=\"grey_c\">用车时间：</td><td >"+resultObj.timeStr+"</td></tr>" +
			"<tr style='height: 30px'>" +
			"<td class=\"grey_c\">下单人：</td><td colspan='3'>"+resultObj.userName+"</td></tr>" +
			"<tr style='height: 30px'>" +
			"<td class=\"grey_c\">订单号：</td><td colspan='3'>"+resultObj.orderNum+"</td>" +
			"</tr></table>",
			resultObj.orderNum,
			true);
	pop.showDiv();
	pop.closeDiv();

}
  /**
   * 取消
   */
  function canel() {
  	$("#editFormDiv").hide();
  	$("#userInfoFormDiv").hide();
  }
  
  function saveUserInfo(){
  	$("#saveuserinfo").attr({"disabled":"disabled"});
  	var form = $("#userInfoForm");
  	if(!form.valid()){
  		$("#saveuserinfo").removeAttr("disabled");
  		return;
  	} 
  	
  	var url = "User/Update";
  	var data = form.serializeObject();
  	
  	$.ajax({
  		type: 'POST',
  		dataType: 'json',
  		url: url,
  		data: JSON.stringify(data),
  		contentType: 'application/json; charset=utf-8',
  		async: false,
  		success: function (status) {
  			var message = status.message;
  			if (status.status == "success") {
         		toastr.success(message, "提示");
  				$("#userInfoFormDiv").hide();
  				showObjectOnForm("userInfoForm", null);
  			} else {
  				toastr.error(message, "提示");
  			}
  			$("#saveuserinfo").removeAttr("disabled");
  		},
  		error:function(XMLHttpRequest, textStatus, errorThrown){
  			$("#saveuserinfo").removeAttr("disabled");
  		}
  	});
  }
  
  /**
   * 校验密码,密码必须为8到16位数字字母符号组合，!@#%&$()*+ 0-9A-Za-z
   */
  $.validator.addMethod("checkPassword", function(value, element, param) {
      var reg=/(?=.*[a-z])(?=.*\d)(?=.*[!@#%&$()*+])[a-z\d!@#%&$()*+]{8,16}/i;
      return this.optional(element) || reg.test(value);
  }, "密码格式不正确");
</script>
<script type="text/javascript" src="js/index.js"></script>
</body>
</html>

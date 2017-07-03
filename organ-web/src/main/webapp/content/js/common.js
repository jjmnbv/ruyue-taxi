$(document).ready(function() {
	//select函数
	$(".select_content>li").live('click', function () {
		var text=$(this).text();
		var val=$(this).attr("data-value");
		var x=$(this).parent();
		
//		x.prev(".select_val").attr("value",text);
//		x.prev(".select_val").attr("data-value",val);
		x.parent().find(".select_val").attr("value",text);
		x.parent().find(".select_val").attr("data-value",val);
	});
	//共同关闭按钮控制
	$(".close").live('click', function () {
		$(this).closest(".pop_box").hide();
		$(".popup_box").hide();
    	$(window.parent.document).find(".pop_index").css("display","none");
	});
});
//index页的pop_index遮幕打包
function pophide(){
   	$(window.parent.document).find(".pop_index").css("display","none");
}
function popshow(){
   	$(window.parent.document).find(".pop_index").css("display","block");
}
//alert封装
function Zalert(tittle,context){
	var html='<div class="pop_box" style="display: block;"><div class="pop"><div class="head">'+tittle+'<img src="content/img/btn_close.png" alt="关闭" class="close"></div><div class="con_c"> '+context+'</div><div class="foot"><button class="btn_red close">确定</button></div></div></div>';
	$("body").append(html);
	$(window.parent.document).find(".pop_index").css("display","block");
}

//confirm封装
function Zconfirm(comfirmData,callback){
	var defaultValue=
	{   title:"提示",
		context:"",
		button_l:"取消",
		button_r:"确定",
		htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	if(comfirmData.constructor==String)
	{
		defaultValue.context=comfirmData || defaultValue.context;
	}
	else if(comfirmData.constructor==Object)
	{
		defaultValue.title=comfirmData.title || defaultValue.title;
		defaultValue.context=comfirmData.context || defaultValue.context;
		defaultValue.button_l=comfirmData.button_l || defaultValue.button_l;
		defaultValue.button_r=comfirmData.button_r || defaultValue.button_r;
		defaultValue.htmltex=comfirmData.htmltex || defaultValue.htmltex;
	}
	var html='<div class="pop_box" style="display: block;"><div class="pop"><div class="head">'+defaultValue.title+'<img src="content/img/btn_close.png" alt="关闭" class="close"></div><div class="con_c"> '+defaultValue.context+'</div><br>'+defaultValue.htmltex+'<div class="foot"><button class="btn_red"  id="button_r">'+defaultValue.button_r+'</button><button class="btn_grey close">'+defaultValue.button_l+'</button></div></div></div> ';
	$("body").append(html);
	$(window.parent.document).find(".pop_index").show();
	$("#button_r").live('click', function () { callback();})
}
/*//placeholder属性在ie下兼容
function placeholder ( pEle , con ) {
	var pEle = pEle
	if( pEle.find("input,textarea").val() == "" ){
		pEle.append("<span class='placeholder_ie'>"+con+"</span>");
	}
	pEle.click(function () {
		$(this).find(".placeholder_ie").css("display","none");
		$(this).find("input,textarea").focus();
	})
	pEle.find("input,textarea").blur(function () {
		if( pEle.find("input,textarea").val() == "" ){
			pEle.find(".placeholder_ie").css("display","inline-block");
		}
	})
}*/

/**
 * 设置cookie
 * @param c_name
 * @param value
 * @param expiredays
 */
function setCookie(c_name,value,expiredays){
	var exdate=new Date();
	exdate.setDate(exdate.getDate()+expiredays);
	document.cookie=c_name+ "=" +escape(value)+((expiredays==null) ? "" : ";expires="+exdate.toGMTString())+";path=/";
}

var meter;
$("html").on("click",function(){
	meter=setTimeout(intop(), 1000 )
});



function intop(){
	if($(".pop")){
		var top=$(window.parent.document).scrollTop();
		$(".pop").css("top",top);
		clearTimeout(meter);
	}
}

function bscroll(scrollTop){
	   $(".pop").css("top",scrollTop);
}
/**
 * 获取cookie
 * @param c_name
 * @returns
 */
function getCookie(c_name){
	if(document.cookie.length>0){
	  var c_start=document.cookie.indexOf(c_name + "=");
	  if(c_start!=-1){
		    c_start=c_start + c_name.length+1;
		    var c_end=document.cookie.indexOf(";",c_start);
		    if (c_end==-1){
		    	 c_end=document.cookie.length;
		    }
		    return unescape(document.cookie.substring(c_start,c_end))
	    }
	  }
	return ""
}

/**
 * 验证ie版本
 */
function checkIe() {
	var browser=navigator.appName 
	var b_version=navigator.appVersion
	var version=b_version.split(";");
	if(version.length < 2) {
		return true;
	}
	var trim_Version=version[1].replace(/[ ]/g,""); 
	if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0") { 
		return false;
	} else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE7.0") { 
		return false;
	} else if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE8.0") { 
		return false;
	}
	return true;
}

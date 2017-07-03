$(document).ready(function() {
	//页面跳转时滚动条回到顶部
    window.parent.scrollTo(0,0);
    //select函数
	$(".select_content>li").click(function () {
		var text=$(this).text();
		var val=$(this).attr("data-value");
		var x=$(this).parent();
		x.prev(".select_val").val(text);
		x.prev(".select_val").attr("data-value",val);
	});
	$(".pop_box .close").on('click', function () {
		$(this).closest(".pop_box").hide();
	});
	//头像关闭操控
	$("body").click(function () {
		$(window.parent.document).find(".news_li,.operation").css("display","none");
	});
	//alert点击确定关闭
	$("body").on('click','#close', function () {
		$(this).closest(".pop_box").remove();
	});
	$("body").on('click','#button_r', function () {
		$(this).closest(".pop_box").remove();
	});
	$("body").on('click','#button_l', function () {
		$(this).closest(".pop_box").remove();
	});
	//头像关闭操控
	$("body").click(function () {
		$(window.parent.document).find(".news_li,.operation").css("display","none");
	});
});
//alert封装
function Zalert(tittle,context){
	var scrTop = $(window.parent.document).scrollTop() - 60;
	if(scrTop >= 0 ){
		scrTop = scrTop + 80 + 'px';
	}else{
		scrTop = 80 + 'px';
	}
	var html='<div class="pop_box" style="display: block;"><div class="tip_box_a" style="margin-top:'+scrTop+'"><h2>'+tittle+'</h2><div class="tip_text"> '+context+'</div><button class="Lbtn red" id="close">确定</button></div></div> ';
	$("body").append(html);
}
//confirm封装
function Zconfirm(comfirmData){
	var tittle=comfirmData.tittle ||"标题",
		context=comfirmData.context ||"内容",
		button_l=comfirmData.button_l||"否",
		button_r=comfirmData.button_r||"是",
		htmltex=comfirmData.htmltex ||"",
		click=comfirmData.click ||"";
	var scrTop = $(window.parent.document).scrollTop() - 60;
	if(scrTop >= 0 ){
		scrTop = scrTop + 80 + 'px';
	}else{
		scrTop = 80 + 'px';
	}
	var html='<div class="pop_box" style="display: block;"><div class="tip_box_a" style="margin-top:'+scrTop+'"><h2>'+tittle+'</h2><div class="tip_text"> '+context+'</div>'+htmltex+'<button class="Lbtn grey"  id="close">'+button_l+'</button><button class="Lbtn red" id="button_r" onclick="'+click+'">'+button_r+'</button></div></div> ';
	$("body").append(html);
}

//左边是，右边否
function ZconfirmLeft(comfirmData){
	var tittle=comfirmData.tittle ||"标题",
		context=comfirmData.context ||"内容",
		contextAlign=comfirmData.contextAlign ||"center",
		button_l=comfirmData.button_l||"是",
		button_r=comfirmData.button_r||"否",
		htmltex=comfirmData.htmltex ||"",
		click=comfirmData.click ||"";
	var scrTop = $(window.parent.document).scrollTop() - 60;
	if(scrTop >= 0 ){
		scrTop = scrTop + 80 + 'px';
	}else{
		scrTop = 80 + 'px';
	}
	var html='<div class="pop_box" style="display: block;"><div class="tip_box_a" style="margin-top:'+scrTop+'"><h2>'+tittle+'</h2><div class="tip_text" style="text-align: ' + contextAlign + '"> '+context+'</div>'+htmltex+'<button class="Lbtn red" id="button_l" onclick="'+click+'">'+button_l+'</button><button class="Lbtn grey"  id="close">'+button_r+'</button></div></div> ';
	$("body").append(html);
}

//String.prototype.Zhide = function(){
//	return this.hide();
//}
//
//function Zshow(id){
//	$(id).hideshow();
//}

//confirm封装
//function Zconfirm(comfirmData,callback){
//	
//	var defaultValue=
//	{   title:"提示",
//	    context:"",
//		button_l:"否",
//		button_r:"是",
//		htmltex:"<input type='hidden' placeholder='添加的html'> "
//	};
//	if(comfirmData.constructor==String)
//	{
//		defaultValue.context=comfirmData || defaultValue.context;
//	}
//	else if(comfirmData.constructor==Object)
//	{
//		defaultValue.title=comfirmData.title || defaultValue.title;
//		defaultValue.context=comfirmData.context || defaultValue.context;
//		defaultValue.button_l=comfirmData.button_l || defaultValue.button_l;
//		defaultValue.button_r=comfirmData.button_r || defaultValue.button_r;
//		defaultValue.htmltex=comfirmData.htmltex || defaultValue.htmltex;
//	}
//	var html='<div class="pop_box" style="display: block;"><div class="tip_box_a"><h2>'+defaultValue.title+'</h2><div class="tip_text"> '+defaultValue.context+'</div>'+defaultValue.htmltex+'<button class="Lbtn grey"  id="close">'+defaultValue.button_l+'</button><button class="Lbtn red" id="button_r">'+defaultValue.button_r+'</button></div></div> ';
//	$("body").append(html);
//	$("#button_r").click(function(){ callback();})
//	
//}
function Zconfirm(comfirmData,callback){
	
	var defaultValue=
	{   title:"提示",
	    context:"",
		button_l:"否",
		button_r:"是",
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
	var scrTop = $(window.parent.document).scrollTop() - 60;
	if(scrTop >= 0 ){
		scrTop = scrTop + 80 + 'px';
	}else{
		scrTop = 80 + 'px';
	}
	var html='<div class="pop_box" style="display: block;"><div class="tip_box_a" style="margin-top:'+scrTop+'"><h2>'+defaultValue.title+'</h2><div class="tip_text"> '+defaultValue.context+'</div>'+defaultValue.htmltex+'<button class="Lbtn grey"  id="close">'+defaultValue.button_l+'</button><button class="Lbtn red" id="button_r">'+defaultValue.button_r+'</button></div></div> ';
	$("body").append(html);
	if(callback&&!comfirmData.click){
		$("#button_r").click(function(){ callback();});
	}else if(comfirmData.click){
		$("#button_r").attr({"onclick":comfirmData.click});
	}
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
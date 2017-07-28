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
	//头像关闭操控
	$("body").click(function () {
		$(window.parent.document).find(".news_li,.operation").css("display","none");
	});
	
   /* $(window).scroll(function () {
    	var maxTop=$(document).height()-$(window).height()+160;
    	if($(window).scrollTop()<maxTop){
    		var top=$(window).scrollTop()-60;
    	}else{
    		var top=maxTop-60;
    	}
    	$(window.parent.document).find(".tip_box_a,.tip_box_b").css("top",top);
    });*/
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
/*//confirm封装
function Zconfirm(comfirmData){
	var tittle=comfirmData.tittle ||"标题",
		context=comfirmData.context ||"内容",
		button_l=comfirmData.button_l||"否",
		button_r=comfirmData.button_r||"是",
		htmltex=comfirmData.htmltex ||"",
		click=comfirmData.click ||"";
	var html='<div class="pop_box" style="display: block;"><div class="tip_box_a"><h2>'+tittle+'</h2><div class="tip_text"> '+context+'</div>'+htmltex+'<button class="Lbtn grey"  id="close">'+button_l+'</button><button class="Lbtn red" id="button_r" onclick="'+click+'">'+button_r+'</button></div></div> ';
	$("body").append(html);
}
/*
String.prototype.Zhide = function(){
	return this.hide();
}

function Zshow(id){
	$(id).hideshow();
}*/

//confirm封装
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



/********md5*********/
/*
 * A JavaScript implementation of the RSA Data Security, Inc. MD5 Message
 * Digest Algorithm, as defined in RFC 1321.
 * Version 2.1 Copyright (C) Paul Johnston 1999 - 2002.
 * Other contributors: Greg Holt, Andrew Kepert, Ydnar, Lostinet
 * Distributed under the BSD License
 * See http://pajhome.org.uk/crypt/md5 for more info.
 */

/*
 * Configurable variables. You may need to tweak these to be compatible with
 * the server-side, but the defaults work in most cases.
 */
var hexcase = 0;  /* hex output format. 0 - lowercase; 1 - uppercase        */
var b64pad  = ""; /* base-64 pad character. "=" for strict RFC compliance   */
var chrsz   = 8;  /* bits per input character. 8 - ASCII; 16 - Unicode      */

/*
 * These are the functions you'll usually want to call
 * They take string arguments and return either hex or base-64 encoded strings
 */
function hex_md5(s){ return binl2hex(core_md5(str2binl(s), s.length * chrsz));}
function b64_md5(s){ return binl2b64(core_md5(str2binl(s), s.length * chrsz));}
function str_md5(s){ return binl2str(core_md5(str2binl(s), s.length * chrsz));}
function hex_hmac_md5(key, data) { return binl2hex(core_hmac_md5(key, data)); }
function b64_hmac_md5(key, data) { return binl2b64(core_hmac_md5(key, data)); }
function str_hmac_md5(key, data) { return binl2str(core_hmac_md5(key, data)); }

/*
 * Perform a simple self-test to see if the VM is working
 */
function md5_vm_test()
{
    return hex_md5("abc") == "900150983cd24fb0d6963f7d28e17f72";
}

/*
 * Calculate the MD5 of an array of little-endian words, and a bit length
 */
function core_md5(x, len)
{
	/* append padding */
    x[len >> 5] |= 0x80 << ((len) % 32);
    x[(((len + 64) >>> 9) << 4) + 14] = len;

    var a =  1732584193;
    var b = -271733879;
    var c = -1732584194;
    var d =  271733878;

    for(var i = 0; i < x.length; i += 16)
    {
        var olda = a;
        var oldb = b;
        var oldc = c;
        var oldd = d;

        a = md5_ff(a, b, c, d, x[i+ 0], 7 , -680876936);
        d = md5_ff(d, a, b, c, x[i+ 1], 12, -389564586);
        c = md5_ff(c, d, a, b, x[i+ 2], 17,  606105819);
        b = md5_ff(b, c, d, a, x[i+ 3], 22, -1044525330);
        a = md5_ff(a, b, c, d, x[i+ 4], 7 , -176418897);
        d = md5_ff(d, a, b, c, x[i+ 5], 12,  1200080426);
        c = md5_ff(c, d, a, b, x[i+ 6], 17, -1473231341);
        b = md5_ff(b, c, d, a, x[i+ 7], 22, -45705983);
        a = md5_ff(a, b, c, d, x[i+ 8], 7 ,  1770035416);
        d = md5_ff(d, a, b, c, x[i+ 9], 12, -1958414417);
        c = md5_ff(c, d, a, b, x[i+10], 17, -42063);
        b = md5_ff(b, c, d, a, x[i+11], 22, -1990404162);
        a = md5_ff(a, b, c, d, x[i+12], 7 ,  1804603682);
        d = md5_ff(d, a, b, c, x[i+13], 12, -40341101);
        c = md5_ff(c, d, a, b, x[i+14], 17, -1502002290);
        b = md5_ff(b, c, d, a, x[i+15], 22,  1236535329);

        a = md5_gg(a, b, c, d, x[i+ 1], 5 , -165796510);
        d = md5_gg(d, a, b, c, x[i+ 6], 9 , -1069501632);
        c = md5_gg(c, d, a, b, x[i+11], 14,  643717713);
        b = md5_gg(b, c, d, a, x[i+ 0], 20, -373897302);
        a = md5_gg(a, b, c, d, x[i+ 5], 5 , -701558691);
        d = md5_gg(d, a, b, c, x[i+10], 9 ,  38016083);
        c = md5_gg(c, d, a, b, x[i+15], 14, -660478335);
        b = md5_gg(b, c, d, a, x[i+ 4], 20, -405537848);
        a = md5_gg(a, b, c, d, x[i+ 9], 5 ,  568446438);
        d = md5_gg(d, a, b, c, x[i+14], 9 , -1019803690);
        c = md5_gg(c, d, a, b, x[i+ 3], 14, -187363961);
        b = md5_gg(b, c, d, a, x[i+ 8], 20,  1163531501);
        a = md5_gg(a, b, c, d, x[i+13], 5 , -1444681467);
        d = md5_gg(d, a, b, c, x[i+ 2], 9 , -51403784);
        c = md5_gg(c, d, a, b, x[i+ 7], 14,  1735328473);
        b = md5_gg(b, c, d, a, x[i+12], 20, -1926607734);

        a = md5_hh(a, b, c, d, x[i+ 5], 4 , -378558);
        d = md5_hh(d, a, b, c, x[i+ 8], 11, -2022574463);
        c = md5_hh(c, d, a, b, x[i+11], 16,  1839030562);
        b = md5_hh(b, c, d, a, x[i+14], 23, -35309556);
        a = md5_hh(a, b, c, d, x[i+ 1], 4 , -1530992060);
        d = md5_hh(d, a, b, c, x[i+ 4], 11,  1272893353);
        c = md5_hh(c, d, a, b, x[i+ 7], 16, -155497632);
        b = md5_hh(b, c, d, a, x[i+10], 23, -1094730640);
        a = md5_hh(a, b, c, d, x[i+13], 4 ,  681279174);
        d = md5_hh(d, a, b, c, x[i+ 0], 11, -358537222);
        c = md5_hh(c, d, a, b, x[i+ 3], 16, -722521979);
        b = md5_hh(b, c, d, a, x[i+ 6], 23,  76029189);
        a = md5_hh(a, b, c, d, x[i+ 9], 4 , -640364487);
        d = md5_hh(d, a, b, c, x[i+12], 11, -421815835);
        c = md5_hh(c, d, a, b, x[i+15], 16,  530742520);
        b = md5_hh(b, c, d, a, x[i+ 2], 23, -995338651);

        a = md5_ii(a, b, c, d, x[i+ 0], 6 , -198630844);
        d = md5_ii(d, a, b, c, x[i+ 7], 10,  1126891415);
        c = md5_ii(c, d, a, b, x[i+14], 15, -1416354905);
        b = md5_ii(b, c, d, a, x[i+ 5], 21, -57434055);
        a = md5_ii(a, b, c, d, x[i+12], 6 ,  1700485571);
        d = md5_ii(d, a, b, c, x[i+ 3], 10, -1894986606);
        c = md5_ii(c, d, a, b, x[i+10], 15, -1051523);
        b = md5_ii(b, c, d, a, x[i+ 1], 21, -2054922799);
        a = md5_ii(a, b, c, d, x[i+ 8], 6 ,  1873313359);
        d = md5_ii(d, a, b, c, x[i+15], 10, -30611744);
        c = md5_ii(c, d, a, b, x[i+ 6], 15, -1560198380);
        b = md5_ii(b, c, d, a, x[i+13], 21,  1309151649);
        a = md5_ii(a, b, c, d, x[i+ 4], 6 , -145523070);
        d = md5_ii(d, a, b, c, x[i+11], 10, -1120210379);
        c = md5_ii(c, d, a, b, x[i+ 2], 15,  718787259);
        b = md5_ii(b, c, d, a, x[i+ 9], 21, -343485551);

        a = safe_add(a, olda);
        b = safe_add(b, oldb);
        c = safe_add(c, oldc);
        d = safe_add(d, oldd);
    }
    return Array(a, b, c, d);

}

/*
 * These functions implement the four basic operations the algorithm uses.
 */
function md5_cmn(q, a, b, x, s, t)
{
    return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s),b);
}
function md5_ff(a, b, c, d, x, s, t)
{
    return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
}
function md5_gg(a, b, c, d, x, s, t)
{
    return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
}
function md5_hh(a, b, c, d, x, s, t)
{
    return md5_cmn(b ^ c ^ d, a, b, x, s, t);
}
function md5_ii(a, b, c, d, x, s, t)
{
    return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
}

/*
 * Calculate the HMAC-MD5, of a key and some data
 */
function core_hmac_md5(key, data)
{
    var bkey = str2binl(key);
    if(bkey.length > 16) bkey = core_md5(bkey, key.length * chrsz);

    var ipad = Array(16), opad = Array(16);
    for(var i = 0; i < 16; i++)
    {
        ipad[i] = bkey[i] ^ 0x36363636;
        opad[i] = bkey[i] ^ 0x5C5C5C5C;
    }

    var hash = core_md5(ipad.concat(str2binl(data)), 512 + data.length * chrsz);
    return core_md5(opad.concat(hash), 512 + 128);
}

/*
 * Add integers, wrapping at 2^32. This uses 16-bit operations internally
 * to work around bugs in some JS interpreters.
 */
function safe_add(x, y)
{
    var lsw = (x & 0xFFFF) + (y & 0xFFFF);
    var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
    return (msw << 16) | (lsw & 0xFFFF);
}

/*
 * Bitwise rotate a 32-bit number to the left.
 */
function bit_rol(num, cnt)
{
    return (num << cnt) | (num >>> (32 - cnt));
}

/*
 * Convert a string to an array of little-endian words
 * If chrsz is ASCII, characters >255 have their hi-byte silently ignored.
 */
function str2binl(str)
{
    var bin = Array();
    var mask = (1 << chrsz) - 1;
    for(var i = 0; i < str.length * chrsz; i += chrsz)
        bin[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (i%32);
    return bin;
}

/*
 * Convert an array of little-endian words to a string
 */
function binl2str(bin)
{
    var str = "";
    var mask = (1 << chrsz) - 1;
    for(var i = 0; i < bin.length * 32; i += chrsz)
        str += String.fromCharCode((bin[i>>5] >>> (i % 32)) & mask);
    return str;
}

/*
 * Convert an array of little-endian words to a hex string.
 */
function binl2hex(binarray)
{
    var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
    var str = "";
    for(var i = 0; i < binarray.length * 4; i++)
    {
        str += hex_tab.charAt((binarray[i>>2] >> ((i%4)*8+4)) & 0xF) +
            hex_tab.charAt((binarray[i>>2] >> ((i%4)*8  )) & 0xF);
    }
    return str;
}

/*
 * Convert an array of little-endian words to a base-64 string
 */
function binl2b64(binarray)
{
    var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    var str = "";
    for(var i = 0; i < binarray.length * 4; i += 3)
    {
        var triplet = (((binarray[i   >> 2] >> 8 * ( i   %4)) & 0xFF) << 16)
            | (((binarray[i+1 >> 2] >> 8 * ((i+1)%4)) & 0xFF) << 8 )
            |  ((binarray[i+2 >> 2] >> 8 * ((i+2)%4)) & 0xFF);
        for(var j = 0; j < 4; j++)
        {
            if(i * 8 + j * 6 > binarray.length * 32) str += b64pad;
            else str += tab.charAt((triplet >> 6*(3-j)) & 0x3F);
        }
    }
    return str;
}

/*
 * encode pwd
 */
function encodepwd(srcpwd){
    return b64_md5(srcpwd)+"==";
}
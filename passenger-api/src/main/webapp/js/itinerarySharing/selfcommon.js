var convertFormToJson = function(form){
	var o = {};
	for (var i = 0, len = form.length; i < len; i++) {
		if (form[i].value != "" && form[i].value != "NaN") {
			var value = form[i].value;
			o[form[i].name] = value;
		}
	}
	return o;
}

/**
 * 将表单上的元素转换成json对象
 * @return {}
 */
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * 日期格式化
 * @param {} time
 * @return {}
 */
function timeStamp2String(time, format){
	var mark = "-";
	if(null != format && format.length > 0) {
		mark = format;
	}
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
	var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	return year + mark + month + mark + date+" "+hour+":"+minute+":"+second;
}

/**
 * 日期格式化 MM/dd hh:mm
 * @param {} time
 * @return {}
 */
function formatTimeForDetail(time){
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
	var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	return month + "/" + date+" "+hour+":"+minute;
}

//手机号码验证
function regPhone(phone){
	var myreg = /^(((13[0-9]{1})|(15[0-3,5-9])|(18[0-9]{1})|(14[5,7,9])|(17[0,1,3,5-8]))+\d{8})$/; 
	return !myreg.test(phone);
}

/**
 * 将时间(秒)转换为xx小时xx分钟格式
 * @param second
 */
function convertSecond(second) {
	var minute = Math.ceil(second/60);
	if(minute < 60) {
		return minute + "分钟";
	} else {
		var hour = parseInt(minute/60) + "小时";
		if(minute%60 > 0) {
			hour = hour + parseInt(minute%60) + "分钟";
		}
		return hour;
	}
}

/**
 * 将时间(分)转换为xx小时xx分钟格式
 * @param minute
 */
function convertMinute(minute) {
	return convertSecond(minute * 60);
}


/**
 * 传入某格的内容和长度，
 * 	如果长度不为空，则按长度截取，如果为空，则默认按8个字符截取
 * 	如果传入内容data没有超过长度，则不显示tooltip
 * @param data
 * @param len 传入为中文字符的len
 * @returns {String}
 */
function showToolTips(data, len, placement){
	if(len == undefined || len == null || len == ""){
		len = 8;
	}
	if(placement == undefined || placement == null || placement == ""){
		placement = "auto left";
	}
	
	var content = data == null ? "" : data;
	if (content && content.length > len) {
		content = content.substring(0, len) + "...";
    }else{
    	return content;
    }
	return "<span title='" + data + "'>" + content + "</span>";
//	var title = '<span style=\'word-break:break-all;display:inline-block;\'>'+data+'</span>';
//	return "<div data-toggle=\"tooltip\" data-title=\""+title+"\" data-html=\"true\" data-placement=\""+placement+"\">"+content+"</div>";
}

/**
 * 限制订单号只能输入数字和字母
 */
function checkOrderno(orderno) {
	$(orderno).val($(orderno).val().replace(/[\W\_]/g, ""));
}
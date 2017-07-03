/**
 * 页面初始化
 */
$(function () {
	initSelectDriver();
	initSelectPassenger();
//	initSelectPlateno();
	});
/**
 * 司机联想输入框
 * @returns
 */
function initSelectDriver() {
	$("#driver").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false,
		allowClear : true,
		ajax : {
			url : "PubAlarmprocess/GetPubAlarmprocessDriver",
			dataType : 'json',
			data : function(term, page) {
				return {
					driver: term
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});
}
/**
 * 乘客联想输入框
 * @returns
 */
function initSelectPassenger() {
	$("#passenger").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false,
		allowClear : true,
		ajax : {
			url : "PubAlarmprocess/GetPubAlarmprocessPassenger",
			dataType : 'json',
			data : function(term, page) {
				return {
					passenger: term
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});
}
/**
 * 车牌号
 * @returns
 */
/*function initSelectPlateno() {
	$("#plateno").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false,
		allowClear : true,
		ajax : {
			url : "PubAlarmprocess/GetPubAlarmprocessPlateno",
			dataType : 'json',
			data : function(term, page) {
				return {
					plateno: term
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});
} */
var base = document.getElementsByTagName("base")[0].getAttribute("href");

$(function () {
	dateFormat();
	validateForm();
	});

/**
 * 保存
 */
function save() {
	var form = $("#editDriverForm");
	if (!form.valid())
		return;
	
	var id  = $("#insurId").val();
	var insurcom = $("#insurcom").val();
	var insurtype = $("#insurtype").val();
	var insurnum = $("#insurnum").val();
	
	var insurcount = $("#insurcount").val();
	var insureff = $("#startTime").val();
	var insurexp = $("#endTime").val();
	
	var oDate1 = new Date(insureff);
    var oDate2 = new Date(insurexp);
    if(oDate1.getTime() > oDate2.getTime()){
    	var message = "开始日期不能大于结束日期";
    	toastr.error(message, "提示");
    	return ;
    }	
    
	var data = {id:id,insurcom:insurcom,insurtype:insurtype,insurnum:insurnum,insurcount:insurcount,insureff:insureff,insurexp:insurexp};
	$.ajax({
		type: "post",
		contentType:"application/json",
		url: "PubVehInsur/UpdatePubVehInsur",
		data: JSON.stringify(data),
		success: function(status){
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
	            toastr.success(message, "提示");
	        	window.location.href=base+"PubVehInsur/Index";
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
				toastr.error(message, "提示");
			}
		}
	})
	
}


function dateFormat() {
	$('.searchDate').datetimepicker({
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });
	Date.prototype.format =function(format)
	{
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
	}
	var date=new Date();
	$("#endTime").val(date.format('yyyy-MM-dd'));
	 date.setDate(1);
	$("#startTime").val(date.format('yyyy-MM-dd'));
}

function callBack(){
	window.location.href=base+"PubVehInsur/Index";
}

/**
 * 表单校验
 */
function validateForm() {
	$("#editDriverForm").validate({
		rules : {
			insurcom : {
				required : true
			},
			insurtype :{
				required : true
			},
			insurnum : {
				required : true
			},
			insurcount :{
				required : true
			},
			startTime : {
				required: true
			},
			endTime : {
				required: true
			}
		},
		messages : {	
			insurcom : {
				required : "请输入保险公司名称"
			},
			insurtype : {
				required : "请选择保险类型"
			},
			insurnum : {
				required : "请输入保险号"
			},
			insurcount :{
				required : "请输入保险金额"
			},
			startTime : {
				required : "请选择开始日期"
			},
			endTime : {
				required : "请选择结束日期"
			}
			
		}
	})
}
 

$(function() {
	dateFormat();
	initData();
	validateForm();
});

/**
 * 初始化表单数据
 */
function initData() {
	var id = $("#id").val();
	//如果是修改页面，初始化表单数据
	if(null != id && "" != id) {
		$("#advisePageTitle").text("修改广告页");
		$("#apptype").val($("#hideApptype").val());
		$("#name").val($("#hideName").val());
		$("#starttime").val($("#hideStarttime").val());
		$("#endtime").val($("#hideEndtime").val());
		var imagaddr = $("#hideImgaddr").val();
		$("#imgaddr").val(imagaddr);
		if(null != imagaddr && "" != imagaddr) {
			$("#imgShow").show();
			$("#imgback").attr("src", serviceAddress + "/" + imagaddr);
		} else {
			$("#imgShow").hide();
			$("#imgback").attr("src", "content/img/ing_tupian.png");
		}
	}
}

/**
 * 表单验证
 */
function validateForm() {
	$("#formss").validate({
		ignore: [],
		rules : {
			apptype : {
				required : true
			},
			name : {
				required : true
			},
			endtime : {
				timerequired : true,
				timeslot : true
			},
			imgaddr : {
				required : true
			}
		},
		messages : {
			apptype : {
				required : "App类型不能为空"
			},
			name : {
				required : "名称不能为空"
			},
			endtime : {
				required : "开始时间和结束时间不能为空",
				timeslot : "开始时间不能大于结束时间"
			},
			imgaddr : {
				required : "广告图片不能为空"
			}
		}
	});
}

/**
 * 保存数据
 */
function save() {
	var form = $("#formss");
	if(!form.valid()) {
		return;
	}
	var id = $("#id").val();
	var url = $("#baseUrl").val() + "PubAdimage/CreatePubAdimage";
	if(null != id && "" != id) {
		url = $("#baseUrl").val() + "PubAdimage/EditPubAdimage";
	}
	var data = form.serializeObject();
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : url,
		data : JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.options.onHidden = function() {
					window.location.href = $("#baseUrl").val() + "PubAdvise/Index";
				}
				toastr.success(message, "提示");
			} else {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.error(message, "提示");
			}
		}
	});
}

$("#fileupload").fileupload({
	url:$("#baseUrl").val() + "PubAdimage/UploadFile",
    dataType: 'json',
    done: function(e, data) {
        if(data.result.status=="success"){
        	$("#imgShow").show();
        	$("#imgback").attr("src",data.result.basepath+"/"+data.result.message[0]);
        	$("#imgaddr").val(data.result.message[0]);
        }
    }
});

$("#clear").click(function(){
	$("#fileupload").val("");
	$("#imgback").attr("src","content/img/ing_tupian.png");
	$("#imgShow").hide();
	$("#imgaddr").val("");
});

/**
 * 取消
 */
function cancel() {
	var hideApptype = $("#hideApptype").val();
	var hideName = $("#hideName").val();
	var hideStarttime = $("#hideStarttime").val();
	var hideEndtime = $("#hideEndtime").val();
	var hideImgaddr = $("#hideImgaddr").val();
	
	var id = $("#id").val();
	var apptype = $("#apptype").val();
	var name = $("#name").val();
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var imgaddr = $("#imgaddr").val();
	
	//判断页面是否修改
	if (apptype != hideApptype || name != hideName
			|| starttime != hideStarttime || endtime != hideEndtime
			|| imgaddr != hideImgaddr) {
		var msg = "是否放弃新增?";
		if(null != id && "" != id) {
			msg = "是否放弃修改?";
		}
		var comfirmData = {
			tittle : "提示",
			context : msg,
			button_l : "否",
			button_r : "是",
			htmltex : "<input type='hidden' placeholder='添加的html'> "
		};
		Zconfirm(comfirmData, function() {
			window.location.href = $("#baseUrl").val() + "PubAdvise/Index";
		});
	} else {
		window.location.href = $("#baseUrl").val() + "PubAdvise/Index";
	}
}

function dateFormat() {
	$('.searchDate').datetimepicker({
        format: "yyyy/mm/dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: true,
        clearBtn: true
    });
}

/**
 * 开始时间结束时间都不能为空
 */
$.validator.addMethod("timerequired", function(value, element, params) {
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if(null != starttime && "" != starttime && null != endtime && "" != endtime) {
		return true;
	}
	return false;
}, "开始时间和结束时间都不能为空")

/**
 * 自定义有效时间校验
 */
$.validator.addMethod("timeslot", function(value, element, params) {
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if(null != starttime && "" != starttime && null != endtime && "" != endtime) {
		return starttime <= endtime;
	}
	return true;
}, "开始时间不能大于结束时间")

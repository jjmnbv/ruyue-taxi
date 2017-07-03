
$(function() {
	initData();
	validateForm();
	initDate();
});

/**
 * 初始化表单数据
 */
function initData() {
	var id = $("#id").val();
	//如果是修改页面，初始化表单数据
	if(null != id && "" != id) {
		$("#sysversionPageTitle").text("版本详情");
		var maxversionno = $("#hideMaxversionno").val();
		$("#platformtype").val($("#hidePlatformtype").val());
		$("#curversion").val($("#hideCurversion").val());
		$("#systemtype").val($("#hideSystemtype").val());
		$("#changelog").val($("#hideChangelog").val());
		$("#releasedate").val($("#hideReleasedate").val());
		if(null != maxversionno && "" != maxversionno && maxversionno > 0) {
			$("input[type='radio'][value='1']").attr("checked", true);
		} else {
			$("input[type='radio'][value='2']").attr("checked", true);
		}
		
		$("#platformtype").attr("disabled", "disabled");
		$("#curversion").attr("disabled", "disabled");
		$("#maxversionno").attr("disabled", "disabled");
		$("#systemtype").attr("disabled", "disabled");
		$("#changelog").attr("disabled", "disabled");
		$("#releasedate").attr("disabled", "disabled");
		$("input[type='radio']").attr("disabled", "disabled");
		$("#editButton").show();
		$("#editBtn").hide();
	} else {
		$("#editButton").hide();
		$("#editBtn").show();
	}
}

/**
 * 表单验证
 */
function validateForm() {
	$("#formss").validate({
		rules : {
			platformtype : {
				required : true
			},
			curversion : {
				required : true,
				version : true
			},
			releasedate : {
				required : true
			},
			changelog : {
				required : true,
				maxlength : 200
			}
		},
		messages : {
			platformtype : {
				required : "App类型不能为空"
			},
			curversion : {
				required : "当前版本号不能为空"
			},
			releasedate : {
				required : "发布日期不能为空"
			},
			changelog : {
				required : "新版本说明不能为空",
				maxlength : "新版本说明最大为200个字符"
			}
		}
	});
}

function initDate() {
	$('.searchDate').datetimepicker({
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        clearBtn: true
    });
}

/**
 * 修改
 */
function edit() {
	$("#sysversionPageTitle").text("修改发布版本");
	$("#platformtype").removeAttr("disabled");
	$("#curversion").removeAttr("disabled");
	$("#maxversionno").removeAttr("disabled");
	$("#systemtype").removeAttr("disabled");
	$("#changelog").removeAttr("disabled");
	$("#releasedate").removeAttr("disabled");
	$("input[type='radio']").removeAttr("disabled");
	$("#editButton").hide();
	$("#editBtn").show();
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
	var url = $("#baseUrl").val() + "PubSysversion/CreatePubSysversion";
	if(null != id && "" != id) {
		url = $("#baseUrl").val() + "PubSysversion/EditPubSysversion";
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
					window.location.href = $("#baseUrl").val() + "PubSysversion/Index";
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

/**
 * 取消
 */
function cancel() {
	var hidePlatformtype = $("#hidePlatformtype").val();
	var hideCurversion = $("#hideCurversion").val();
	var hideSystemtype = $("#hideSystemtype").val();
	var hideChangelog = $("#hideChangelog").val();
	
	var id = $("#id").val();
	var platformtype = $("#platformtype").val();
	var curversion = $("#curversion").val();
	var systemtype = $("#systemtype").val();
	var changelog = $("#changelog").val();
	
	//判断页面是否修改
	if (platformtype != hidePlatformtype || curversion != hideCurversion
			|| systemtype != hideSystemtype || changelog != hideChangelog) {
		var msg = "是否放弃新增?";
		if (null != id && "" != id) {
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
			window.location.href = $("#baseUrl").val() + "PubSysversion/Index";
		});
	} else {
		window.location.href = $("#baseUrl").val() + "PubSysversion/Index";
	}
}

/**
 * 限制协议内容的字数
 */
function limitLength() {
	var content = $("#content").val();
	if(null != content && content.length > 200) {
		$("#content").val(content.substr(0, 200));
	}
}

/**
 * 自定义版本号验证
 */
$.validator.addMethod("version", function(value, element, param) {
	var rep = /^[V]\d+\.\d*\.?\d+$/;
	return rep.test(value);
}, "当前版本格式不正确");
var ue;
var usertype;
$(function() {
	initBtn();
	ue = initUeditor("content", 5000);
	initData();
});

/**
 * 初始化表单数据
 */
function initData() {
	$("#editButton").show();
	$("#editBtn").hide();
	$("#backButton").hide();
	
	$("#edit").hide();
	$("#view").show();
}

/**
 * 修改
 */
function edit() {
	$("#editButton").hide();
	$("#editBtn").show();
	$("#backButton").show();
	
	$("#view").hide();
	$("#edit").show();
	
	UE.getEditor('content').setContent($("#contentDiv").html(), false);
}

/**
 * 保存数据
 */
function save() {
	var content = ue.getContentTxt();
	if(null == content || content.length == 0) {
		toastr.error("协议内容不能为空", "提示");
		return;
	}
	url = $("#baseUrl").val() + "PubPersonagreement/EditPubPersonagreement";
	var data = $("#formss").serializeObject();
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
					window.location.href = $("#baseUrl").val() + "PubPersonagreement/Index";
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
	var hideContent = $("#hideContent").val();
	var id = $("#id").val();
	var content = $("#content").val();
	
	//判断页面是否修改
	if (content != hideContent) {
		var msg = "是否放弃修改?";
		var comfirmData = {
			tittle : "提示",
			context : msg,
			button_l : "否",
			button_r : "是",
			htmltex : "<input type='hidden' placeholder='添加的html'> "
		};
		Zconfirm(comfirmData, function() {
			window.location.href = $("#baseUrl").val() + "PubPersonagreement/Index";
		});
	} else {
		window.location.href = $("#baseUrl").val() + "PubPersonagreement/Index";
	}
}

/**
 * 初始化按钮
 */
function initBtn() {
	usertype = $("#usertype").val();
	if(null != usertype && usertype != 1) {
		$("#editDisBtn").attr("disabled", "disabled");
	}
}

var ue;
$(function() {
	ue = initUeditor("content", 5000);
	validateForm();
	initData();
});

/**
 * 初始化表单数据
 */
function initData() {
	var id = $("#id").val();
	//如果是修改页面，初始化表单数据
	if(null != id && "" != id) {
		$("#pubCooagreement").text("修改协议");
		$("#leasecompanyid").attr("disabled","disabled");
		$("#servicetype").attr("disabled","disabled");
		$.ajax({
			type: "GET",
			url:"PubCooagreement/GetById",
			cache: false,
			data: { id: id },
			success: function (json) {
				showObjectOnForm("formss", json);
				$("#dataHtml").html(json.coocontent);
				ue.ready(function() {
					ue.setContent($("#dataHtml").html());
				});
			}
	    });
	}
}

/**
 * 表单验证
 */
function validateForm() {
	$("#formss").validate({
		rules : {
			leasecompanyid : {
				required : true
			},
			servicetype : {
				required : true
			},
			cooname : {
				required : true,
				maxlength : 30
			}
		},
		messages : {
			leasecompanyid : {
				required : "车企名称不能为空"
			},
			servicetype : {
				required : "业务类型不能为空"
			},
			cooname : {
				required : "协议名称不能为空",
				maxlength : "最大长度30个字符"
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
	
	var content = ue.getContentTxt();
	if(null == content || "" == content) {
		toastr.error("协议内容不能为空", "提示");
		return;
	}
	
	if(content.length > 5000) {
		toastr.error("协议内容最大长度为5000个字符", "提示");
		return;
	}
	
	var id = $("#id").val();
	var url = $("#baseUrl").val() + "PubCooagreement/CreatePubCooagreement";
	if(null != id && "" != id) {
		url = $("#baseUrl").val() + "PubCooagreement/UpdatePubCooagreement";
	}
	var data = form.serializeObject();
//	data.coocontent = content;
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
					window.location.href = $("#baseUrl").val() + "PubCooagreement/Index";
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
	var id = $("#id").val();
	//判断页面是否修改
	if(id != null && id != '') {
		var msg = "是否放弃新增?";
		if(null != id && "" != id) {
			msg = "是否放弃修改?";
		}
		var comfirmData={
			tittle:"提示",
			context:msg,
			button_l:"否",
			button_r:"是",
			htmltex:"<input type='hidden' placeholder='添加的html'> "
		};
		Zconfirm(comfirmData, function() {
			window.location.href = $("#baseUrl").val() + "PubCooagreement/Index";
		});
	} else {
		window.location.href = $("#baseUrl").val() + "PubCooagreement/Index";
	}
}

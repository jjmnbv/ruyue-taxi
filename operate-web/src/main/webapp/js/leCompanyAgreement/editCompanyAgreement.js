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
		$("#leasescompanyid").val($("#hideLeasescompanyid").val());
		$("#shortname").val($("#hideShortname").val());
		$("#leasescompanyid").attr("disabled", "disabled");
		ue.ready(function() {
			ue.setContent($("#contentHide").html());
		});
		$("#companyAgreePageTitle").text("修改协议");
	}
}

/**
 * 表单验证
 */
function validateForm() {
	$("#formss").validate({
		rules : {
			leasescompanyid : {
				required : true
			},
			shortname : {
				required : true,
				maxlength : 20
			}
		},
		messages : {
			leasescompanyid : {
				required : "服务车企不能为空"
			},
			shortname : {
				required : "协议简称不能为空",
				maxlength : "最大长度20个字符"
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
	var url = $("#baseUrl").val() + "CompanyAgreement/CreateCompanyAgreement";
	if(null != id && "" != id) {
		url = $("#baseUrl").val() + "CompanyAgreement/EditCompanyAgreement";
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
					window.location.href = $("#baseUrl").val() + "CompanyAgreement/Index";
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
	var hideLeasescompanyid = $("#hideLeasescompanyid").val();
	var hideShortname = $("#hideShortname").val();
	
	var id = $("#id").val();
	var leasescompanyid = $("#leasescompanyid").val();
	var shortname = $("#shortname").val();
	
	//判断页面是否修改
	if(leasescompanyid != hideLeasescompanyid || shortname != hideShortname) {
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
			window.location.href = $("#baseUrl").val() + "CompanyAgreement/Index";
		});
	} else {
		window.location.href = $("#baseUrl").val() + "CompanyAgreement/Index";
	}
}

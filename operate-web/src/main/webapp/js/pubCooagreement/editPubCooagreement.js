var ue;
$(function() {
	ue = initUeditor("content", 10000);
	validateForm();
	initData();
	initSelectGetLeasecompanyid();
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
				$("#leasecompanyid").select2("data", {
					id : json.leasecompanyid,
					text : json.companyName
				});
				if(json.servicetype == 0){
					$("#servicetype").html("<option value='0'>网约车</option>");
				}else{
					$("#servicetype").html("<option value='1'>出租车</option>");
				}
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
				required : "请输入车企名称"
			},
			servicetype : {
				required : "请选择服务类型"
			},
			cooname : {
				required : "请输入协议名称",
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
	var data = form.serializeObject();
	var url = $("#baseUrl").val() + "PubCooagreement/CreatePubCooagreement";
	if(null != id && "" != id) {
		url = $("#baseUrl").val() + "PubCooagreement/UpdatePubCooagreement";
		data.leasecompanyid = $("#leasecompanyid").val();
		data.servicetype = $("#servicetype").val();
	}
	
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

function initSelectGetLeasecompanyid() {
	$("#leasecompanyid").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubCooagreement/GetLeLeasescompanyList",
			dataType : 'json',
			data : function(term, page) {
				return {
					text : term
				};
			},
			results : function(data, page) {
				return {
					results : data
				};
			}
		}
	});
}

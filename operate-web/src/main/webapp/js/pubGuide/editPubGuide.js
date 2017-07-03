var formValidate;

$(function() {
	initData();
	validateForm();
});

/**
 * 初始化表单数据
 */
function initData() {
	var id = $("#id").val();
	$(".imgShow").hide();
	$(".imgback").attr("src", "content/img/ing_tupian.png");
	//如果是修改页面，初始化表单数据
	if(null != id && "" != id) {
		$("#guidePageTitle").text("修改引导页");
		$("#apptype").val($("#hideApptype").val());
		$("#version").val($("#hideVersion").val());
		var imagaddr = $("#hideImgaddr").val();
		$("#imgaddr").val(imagaddr);
		var imgArr = imagaddr.split(",");
		for(var m = 0;m < imgArr.length;m++) {
			if(null != imgArr[m] && "" != imgArr[m]) {
				$("#img" + (m + 1)).val(imgArr[m]);
				$("#imgShow" + (m + 1)).show();
				$("#imgback" + (m + 1)).attr("src", serviceAddress + "/" + imgArr[m]);
			}
		}
	}
}

/**
 * 表单验证
 */
function validateForm() {
	formValidate = $("#formss").validate({
		ignore: [],
		rules : {
			apptype : {
				required : true
			},
			version : {
				required : true,
				version : true
			},
			imgaddr : {
				required : true
			}
		},
		messages : {
			apptype : {
				required : "App类型不能为空"
			},
			version : {
				required : "版本号不能为空"
			},
			imgaddr : {
				required : "引导页图片不能为空"
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
					window.location.href = $("#baseUrl").val() + "PubGuide/Index";
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

$(".fileupload").fileupload({
	url:$("#baseUrl").val() + "PubAdimage/UploadFile",
    dataType: 'json',
    done: function(e, data) {
        if(data.result.status=="success"){
        	var id = $(this).attr("id");
        	var num = id.substring(id.length - 1, id.length);
        	$("#imgShow" + num).show();
        	$("#imgback" + num).attr("src",data.result.basepath+"/"+data.result.message[0]);
        	$("#img" + num).val(data.result.message[0]);
        	getImgaddr();
        }
    }
});

function clear(num) {
	$("#fileupload" + num).val("");
	$("#imgback" + num).attr("src","content/img/ing_tupian.png");
	$("#imgShow" + num).hide();
	$("#img" + num).val("");
	getImgaddr();
}

function getImgaddr() {
	var img1 = $("#img1").val();
	var img2 = $("#img2").val();
	var img3 = $("#img3").val();
	
	var imgaddr = new Array();
	if(null != img1 && "" != img1) {
		imgaddr[0] = img1;
	}
	if(null != img2 && "" != img2) {
		imgaddr[1] = img2;
	}
	if(null != img3 && "" != img3) {
		imgaddr[2] = img3;
	}
	$("#imgaddr").attr("value", imgaddr.join(","));
}

/**
 * 取消
 */
function cancel() {
	var hideApptype = $("#hideApptype").val();
	var hideVersion = $("#hideVersion").val();
	var hideImgaddr = $("#hideImgaddr").val();
	
	var id = $("#id").val();
	var apptype = $("#apptype").val();
	var version = $("#version").val();
	var imgaddr = $("#imgaddr").val();
	
	//判断页面是否修改
	if (apptype != hideApptype || hideVersion != version || imgaddr != hideImgaddr) {
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
			window.location.href = $("#baseUrl").val() + "PubGuide/Index";
		});
	} else {
		window.location.href = $("#baseUrl").val() + "PubGuide/Index";
	}
}

$.validator.addMethod("version", function(value, element, param) {
	var rep = /^[V]\d+\.\d*\.?\d+$/;
	return rep.test(value);
}, "版本号格式不正确");


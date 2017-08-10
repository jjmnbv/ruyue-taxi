var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	validateForm();
	initGrid();
});

/**
 * 表单校验
 */
function validateForm() {
	$("#createForm").validate({
		rules: {
			name: {required: true, maxlength: 20, namecheck: true},
			ruletype: {required: true},
			consumetype: {required: true},
			cycleday1: {required: true, digits:true, min: 1, maxlength: 3},
			consumelowtimes1: {consumelowtimes1required: true, min: 1, digits:true, maxlength: 3},
			consumelowtimes2: {consumelowtimes2required: true, min: 1, digits:true, maxlength: 3, consumehightimes1check: true},
			consumehightimes1: {consumehightimes1required: true, min: 1, digits:true, maxlength: 3, consumehightimes1check: true},
			consumehightimes2: {consumehightimes2required: true, min: 1, digits:true, maxlength: 3},
			
			consumemoneysingelfull: {consumemoneysingelfullrequired: true, min: 1, digits:true, maxlength: 6},
			cycleday2: {cycleday2required: true, digits:true, min: 1, maxlength: 3},
			consumemoneycyclelow1: {consumemoneycyclelow1required: true, min: 1, digits:true, maxlength: 6},
			consumemoneycyclelow2: {consumemoneycyclelow2required: true, min: 1, digits:true, maxlength: 6, consumemoneycyclefull1check: true},
			consumemoneycyclefull1: {consumemoneycyclefull1required: true, min: 1, digits:true, maxlength: 6, consumemoneycyclefull1check: true},
			consumemoneycyclefull2: {consumemoneycyclefull2required: true, min: 1, digits:true, maxlength: 6},
			
			rechargemoney: {required: true, digits:true, min: 1, maxlength: 6}
		},
		messages: {
			name: {required: "请输入规则名称", maxlength: "最大长度不能超过20个字符"},
			ruletype: {required: "请选择派发类别"},
			consumetype: {required: "请选择规则类型"},
			cycleday1: {required: "请填写", digits:"请输入正整数", min: "请输入正整数", maxlength: "最大限为三位数"},
			consumelowtimes1: {consumelowtimes1required: "请填写", min: "请输入正整数", digits:"请输入正整数", maxlength: "最大限为三位数"},
			consumelowtimes2: {consumelowtimes2required: "请填写", min: "请输入正整数", digits:"请输入正整数", maxlength: "最大限为三位数", consumehightimes1check: "消费次数上限须大于下限"},
			consumehightimes1: {consumehightimes1required: "请填写", min: "请输入正整数", digits:"请输入正整数", maxlength: "最大限为三位数", consumehightimes1check: "消费次数上限须大于下限"},
			consumehightimes2: {consumehightimes2required: "请填写", min: "请输入正整数", digits:"请输入正整数", maxlength: "最大限为三位数"},
			
			consumemoneysingelfull: {consumemoneysingelfullrequired: "请填写", min: "请输入正整数", digits:"请输入正整数", maxlength: "最大限为六位数"},
			cycleday2: {cycleday2required: "请填写", digits:"请输入正整数", min: "请输入正整数", maxlength: "最大限为三位数"},
			consumemoneycyclelow1: {consumemoneycyclelow1required: "请填写", min: "请输入正整数", digits:"请输入正整数", maxlength: "最大限为六位数"},
			consumemoneycyclelow2: {consumemoneycyclelow2required: "请填写", min: "请输入正整数", digits:"请输入正整数", maxlength: "最大限为六位数", consumemoneycyclefull1check: "消费金额上限须大于下限"},
			consumemoneycyclefull1: {consumemoneycyclefull1required: "请填写", min: "请输入正整数", digits:"请输入正整数", maxlength: "最大限为六位数", consumemoneycyclefull1check: "消费金额上限须大于下限"},
			consumemoneycyclefull2: {consumemoneycyclefull2required: "请填写", min: "请输入正整数", digits:"请输入正整数", maxlength: "最大限为六位数"},
			
			rechargemoney: {required: "请填写", digits:"请输入正整数", min: "请输入正整数", maxlength: "最大限为六位数"}
		}
	})

}

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "PubCouponRule/GetPubCouponRuleByQuery",
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无发放规则信息"
        },
        columns: [
	        {
                    //自定义操作列
                    "mDataProp": "ZDY",
                    "sClass": "center",
                    "sTitle": "操作",
                    "sWidth": 80,
                    "bSearchable": false,
                    "sortable": false,
                    "mRender": function (data, type, full) {
                        var html = "";
                        html += '<button type="button" class="SSbtn green_q" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
                        //if (full.historycount > 0) {
                        	html += '&nbsp; <button type="button" class="SSbtn grey_q" onclick="historyquery(' +"'"+ full.id +"','"+ full.name +"'"+ ')"><i class="fa fa-times"></i>历史记录</button>';
                        //}
                        
                        return html;
                    }
            },
            {mDataProp: "name", sTitle: "规则名称", sClass: "center", sortable: true },
            {mDataProp: "ruletarget", sTitle: "规则对象", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
            		if (data != null) {
            			if (data == 1) {
            				return "机构客户";
            			} else if (data == 2) {
            				return "机构用户";
            			} else if (data == 3) {
            				return "个人用户";
            			}
            		} else {
            			return "";
            		}
				}
            },
            {mDataProp: "ruletype", sTitle: "派发类别", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
            		if (data != null) {
            			if (full.ruletype == 1 || full.ruletype == 5) {
            				return "注册返券";
            			} else if (full.ruletype == 2) {
            				return "充值返券";
            			} else if (full.ruletype == 3) {
            				return "消费返券";
            			} else if (full.ruletype == 4) {
            				return "活动返券";
            			}
            		} else {
            			return "";
            		}
				  }
            },
            {mDataProp: "rulecontent", sTitle: "规则内容", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
            		if (data != null && data != "") {
            			return showToolTips(data, 50);
            		} else {
            			return "/";
            		}
				  }
            },
	        {mDataProp: "createtime", sTitle: "创建时间", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
            		if (data != null) {
            			var createtime = timeStamp2String(data,'-');
            			return createtime.substring(0,createtime.length-3);
            		} else {
            			return "";
            		}
				}
	        },
	        {mDataProp: "updatetime", sTitle: "更新时间", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
            		if (data != null) {
            			var updatetime = timeStamp2String(data,'-');
            			return updatetime.substring(0,updatetime.length-3);
            		} else {
            			return "";
            		}
				}
	        }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "ruletype", "value": $("#ruletypeQuery").val() },
		{ "name": "ruletarget", "value": $("#ruletargetQuery").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 清空
 */
function clearParameter() {
	$("#ruletypeQuery").val("");
	$("#ruletargetQuery").val("");

	search();
}

/**
 * 新增
 */
function add() {
	$("#createFormDiv").show();
	showObjectOnForm("createForm", null);

	var createForm = $("#createForm").validate();
	createForm.resetForm();
	createForm.reset();
	
	$("#titleRule").text("新建发放规则");
	$("#ruletarget1").val("2");
	$("#ruletarget2").val("1");
	$("#consumefrequencytype1").val("1");
	$("#consumefrequencytype2").val("2");
	$("#consumefrequencytype3").val("3");
	$("#consumemoneycycletype1").val("1");
	$("#consumemoneycycletype2").val("2");
	$("#consumemoneycycletype3").val("3");
	
	$("#registerTypeDiv").hide();
	$("#rechargeDiv").hide();
	$("#expenseDiv").hide();
	$("#ruletarget1Div").show();
	$("#ruletarget2Div").hide();
	$("#registerTypeDiv").hide();
	$("#ruletarget1").prop("checked", true);
	$("#ruletarget2").prop("checked", false);
	
	$("#name").removeAttr("disabled");
	$("#ruletype").removeAttr("disabled");
	$("#ruletype11").removeAttr("disabled");
	$("#ruletype12").removeAttr("disabled");
	$("#consumetype").removeAttr("disabled");
}

/**
 * 选择派发类别
 */
function changeRuleType() {
	var ruletype = $("#ruletype").val();
	if (ruletype == "1") {
		$("#registerTypeDiv").show();
		$("#rechargeDiv").hide();
		$("#expenseDiv").hide();
		$("#ruletarget1Div").show();
		$("#ruletarget2Div").hide();
		$("#registerTypeDiv").show();
		
		// 值初始化
		$("#ruletarget1").prop("checked", true);
		$("#ruletarget2").prop("checked", false);
	} else if (ruletype == "2") {
		$("#rechargeDiv").show();
		$("#registerTypeDiv").hide();
		$("#expenseDiv").hide();
		$("#ruletarget1Div").show();
		$("#ruletarget2Div").hide();
		$("#registerTypeDiv").hide();
		
		// 值初始化
		$("#rechargemoney").val("");
		$("#ruletarget1").prop("checked", true);
		$("#ruletarget2").prop("checked", false);
	} else if (ruletype == "3") {
		$("#expenseDiv").show();
		$("#registerTypeDiv").hide();
		$("#rechargeDiv").hide();
		$("#ruletarget1Div").hide();
		$("#ruletarget2Div").show();
		$("#registerTypeDiv").hide();
		
		$("#frenquencyDiv").hide();
		$("#monetaryDiv").hide();
		
		// 值初始化
		$("#consumetype").val("");
		clearFrenquencyDiv();
		clearMonetaryDiv();
		
		
	} else if (ruletype == "4" || ruletype == "") {
		$("#expenseDiv").hide();
		$("#registerTypeDiv").hide();
		$("#rechargeDiv").hide();
		$("#ruletarget1Div").show();
		$("#ruletarget2Div").hide();
		$("#registerTypeDiv").hide();
		
		// 值初始化
		$("#ruletarget1").prop("checked", true);
		$("#ruletarget2").prop("checked", false);
	}
}

/**
 * 初始化按消费频次的值
 */
function clearFrenquencyDiv() {
	$("#cycleday1").val("");
	$("#consumefrequencytype1").prop("checked", true);
	$("#consumefrequencytype2").prop("checked", false);
	$("#consumefrequencytype3").prop("checked", false);
	$("#consumelowtimes1").val("");
	$("#consumelowtimes2").val("");
	$("#consumehightimes1").val("");
	$("#consumehightimes2").val("");
}

/**
 * 初始化按消费金额的值
 */
function clearMonetaryDiv() {
	$("#consumemoneysingleable").prop("checked", false);
	$("#consumemoneysingelfull").val("");
	$("#consumemoneycycleable").prop("checked", false);
	$("#cycleday2").val("");
	$("#consumemoneycycletype1").prop("checked", true);
	$("#consumemoneycycletype2").prop("checked", false);
	$("#consumemoneycycletype3").prop("checked", false);
	$("#consumemoneycyclelow1").val("");
	$("#consumemoneycyclelow2").val("");
	$("#consumemoneycyclefull1").val("");
	$("#consumemoneycyclefull2").val("");
}

/**
 * 选择规则类型
 */
function changeConsumeType() {
	var consumetype = $("#consumetype").val();
	if (consumetype == "1") {
		$("#frenquencyDiv").show();
		$("#monetaryDiv").hide();
		// 初始化值
		clearFrenquencyDiv();
	} else if (consumetype == "2") {
		$("#frenquencyDiv").hide();
		$("#monetaryDiv").show();
		// 初始化值
		clearMonetaryDiv();
	} else {
		$("#frenquencyDiv").hide();
		$("#monetaryDiv").hide();		
		//初始化值
		clearFrenquencyDiv();
		clearMonetaryDiv();
	}
}

/**
 * 新建发放规则-提交
 */
function save() {
	var form = $("#createForm");
	if(!form.valid()) return;
	
	if(!checkData()) {
		toastr.error("请输入完整信息", "提示");
		return;
	}
	
	var data = getData();
	
	var ruleid = $("#id").val();
	var url;
	if (ruleid == null || ruleid == "") {
		url = "PubCouponRule/CreatePubCouponRule";
	} else {
		url = "PubCouponRule/UpdatePubCouponRule";
	}
	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
				toastr.success(message, "提示"); 	
				$("#createFormDiv").hide();
				dataGrid._fnReDraw();
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
				toastr.error(message, "提示");
			}	
		}
	});

}

/**
 * 新建发放规则-提交时判空
 */
function checkData() {
	var ruletype = $("#ruletype").val();
	var consumetype = $("#consumetype").val();
	if (ruletype == "3" && consumetype == "2") {//消费返券       按消费金额
		if (!$("#consumemoneysingleable").prop("checked") && !$("#consumemoneycycleable").prop("checked")) {
			return false;
		}
	}
	return true;
}

/**
 * 新建发放规则-提交时数据处理
 */
function getData() {
	var data;
	var ruletypevalue;
	var ruletype = $("#ruletype").val();
	var id = $("#id").val();
	var ruletarget = $('input[name="ruletarget"]:checked').val();
	if (ruletype == "1") {
		data = {name:$("#name").val(),
				ruletype:ruletype,
				ruletarget:ruletarget,
				id:id
		       };		
	} else if (ruletype == "2") {
		data = {name:$("#name").val(),
				ruletype:ruletype,
				rechargemoney:$("#rechargemoney").val(),
				ruletarget:ruletarget,
				id:id
		       };	
	} else if (ruletype == "3") {
		
		var cycleday;
		var consumefrequencytype;
		var consumehightimes;
		var consumelowtimes;
		var consumemoneysingleable;
		var consumemoneysingelfull;
		var consumemoneycycleable;
		var consumemoneycycletype;
		var consumemoneycyclefull;
		var consumemoneycyclelow;
		
		//1-消费频次，2-消费金额
		var consumetype = $("#consumetype").val();
		if (consumetype == "1") {
			cycleday = $("#cycleday1").val();
			consumefrequencytype = $('input[name="consumefrequencytype"]:checked').val();
			if (consumefrequencytype == "1") {
				consumelowtimes = $("#consumelowtimes1").val();
			} else if (consumefrequencytype == "2") {
				consumelowtimes = $("#consumelowtimes2").val();
				consumehightimes = $("#consumehightimes1").val();
			} else if (consumefrequencytype == "3") {
				consumehightimes = $("#consumehightimes2").val();
			}
		} else if (consumetype == "2") {
			// 单次消费可用
			if ($("#consumemoneysingleable").prop("checked")) {
				consumemoneysingleable = "1";
				consumemoneysingelfull = $("#consumemoneysingelfull").val();
			} else {
				consumemoneysingleable = "0";
			}
			
			// 周期消费可用
			if ($("#consumemoneycycleable").prop("checked")) {
				consumemoneycycleable = "1";
				cycleday = $("#cycleday2").val();
				consumemoneycycletype = $('input[name="consumemoneycycletype"]:checked').val();
				if (consumemoneycycletype == "1") {
					consumemoneycyclelow = $("#consumemoneycyclelow1").val();
				} else if (consumemoneycycletype == "2") {
					consumemoneycyclelow = $("#consumemoneycyclelow2").val();
					consumemoneycyclefull = $("#consumemoneycyclefull1").val();
				} else if (consumemoneycycletype == "3") {
					consumemoneycyclefull = $("#consumemoneycyclefull2").val();
				}
			} else {
				consumemoneycycleable = "0";
			}
		}
		
		data = {name:$("#name").val(),
				ruletype:ruletype,
				ruletarget:2,
				consumetype:$("#consumetype").val(),
				cycleday:cycleday,
				consumefrequencytype:consumefrequencytype,
				consumehightimes:consumehightimes,
				consumelowtimes:consumelowtimes,
				consumemoneysingleable:consumemoneysingleable,
				consumemoneysingelfull:consumemoneysingelfull,
				consumemoneycycleable:consumemoneycycleable,
				consumemoneycycletype:consumemoneycycletype,
				consumemoneycyclefull:consumemoneycyclefull,
				consumemoneycyclelow:consumemoneycyclelow,
				id:id
		       };
		
	} else if (ruletype == "4") {
		data = {name:$("#name").val(),
				ruletype:ruletype,
				ruletarget:ruletarget,
				id:id
		       };
	}
	return data;
}


function getConsumefrequencytype() {
	var consumefrequencytype = $('input[name="consumefrequencytype"]:checked').val();
	return consumefrequencytype;
}

$.validator.addMethod("consumelowtimes1required", function(value, element, param) {
	var consumefrequencytype = getConsumefrequencytype();
	if (consumefrequencytype == "1") {
		var consumelowtimes1 = $("#consumelowtimes1").val();
		if (consumelowtimes1 == "") {
			return false;
		} 
	}
	return true;
}, "");

$.validator.addMethod("consumelowtimes2required", function(value, element, param) {
	var consumefrequencytype = getConsumefrequencytype();
	if (consumefrequencytype == "2") {
		var consumelowtimes2 = $("#consumelowtimes2").val();
		if (consumelowtimes2 == "") {
			return false;
		} 
	}
	return true;
}, "");

$.validator.addMethod("consumehightimes1required", function(value, element, param) {
	var consumefrequencytype = getConsumefrequencytype();
	if (consumefrequencytype == "2") {
		var consumehightimes1 = $("#consumehightimes1").val();
		if (consumehightimes1 == "") {
			return false;
		} 
	}
	return true;
}, "");

$.validator.addMethod("consumehightimes2required", function(value, element, param) {
	var consumefrequencytype = getConsumefrequencytype();
	if (consumefrequencytype == "3") {
		var consumehightimes2 = $("#consumehightimes2").val();
		if (consumehightimes2 == "") {
			return false;
		} 
	}
	return true;
}, "");

function getConsumemoneycycletype() {
	var consumemoneycycletype = $('input[name="consumemoneycycletype"]:checked').val();
	return consumemoneycycletype;
}

$.validator.addMethod("consumemoneycyclelow1required", function(value, element, param) {
	var consumemoneycycletype = getConsumemoneycycletype();
	if ($("#consumemoneycycleable").prop("checked") && consumemoneycycletype == "1") {
		var consumemoneycyclelow1 = $("#consumemoneycyclelow1").val();
		if (consumemoneycyclelow1 == "") {
			return false;
		} 
	}
	return true;
}, "");

$.validator.addMethod("consumemoneycyclelow2required", function(value, element, param) {
	var consumemoneycycletype = getConsumemoneycycletype();
	if ($("#consumemoneycycleable").prop("checked") && consumemoneycycletype == "2") {
		var consumemoneycyclelow2 = $("#consumemoneycyclelow2").val();
		if (consumemoneycyclelow2 == "") {
			return false;
		} 
	}
	return true;
}, "");

$.validator.addMethod("consumemoneycyclefull1required", function(value, element, param) {
	var consumemoneycycletype = getConsumemoneycycletype();
	if ($("#consumemoneycycleable").prop("checked") && consumemoneycycletype == "2") {
		var consumemoneycyclefull1 = $("#consumemoneycyclefull1").val();
		if (consumemoneycyclefull1 == "") {
			return false;
		} 
	}
	return true;
}, "");

$.validator.addMethod("consumemoneycyclefull2required", function(value, element, param) {
	var consumemoneycycletype = getConsumemoneycycletype();
	if ($("#consumemoneycycleable").prop("checked") && consumemoneycycletype == "3") {
		var consumemoneycyclefull2 = $("#consumemoneycyclefull2").val();
		if (consumemoneycyclefull2 == "") {
			return false;
		} 
	}
	return true;
}, "");



$.validator.addMethod("consumemoneysingelfullrequired", function(value, element, param) {
	if ($("#consumemoneysingleable").prop("checked")) {
		var consumemoneysingelfull = $("#consumemoneysingelfull").val();
		if (consumemoneysingelfull == "") {
			return false;
		} 
	}
	return true;
}, "");

$.validator.addMethod("cycleday2required", function(value, element, param) {
	if ($("#consumemoneycycleable").prop("checked")) {
		var cycleday2 = $("#cycleday2").val();
		if (cycleday2 == "") {
			return false;
		} 
	}
	return true;
}, "");

/**
 * 新建发放规则-关闭
 */
function canel() {
	$("#createFormDiv").hide();
}

/**
 * 修改
 */
function edit(id) {
	$("#createFormDiv").show();
	showObjectOnForm("createForm", null);

	var createForm = $("#createForm").validate();
	createForm.resetForm();
	createForm.reset();
	
	$("#ruletarget1").val("2");
	$("#ruletarget2").val("1");
	$("#consumefrequencytype1").val("1");
	$("#consumefrequencytype2").val("2");
	$("#consumefrequencytype3").val("3");
	$("#consumemoneycycletype1").val("1");
	$("#consumemoneycycletype2").val("2");
	$("#consumemoneycycletype3").val("3");
	
	$("#registerTypeDiv").hide();
	$("#rechargeDiv").hide();
	$("#expenseDiv").hide();
	
	$("#titleRule").text("修改发放规则");
	$("#id").val(id);
	
	// 赋值
	setData(id);

}

/**
 * 修改时根据id查找数据，将数据显示在弹框中
 */
function setData(id) {
	var url = "PubCouponRule/GetPubCouponRuleById/" + id + "?time=" + new Date();
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: url,
		data: null,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (json) {
			$("#name").val(json.name);
			$("#ruletype").val(json.ruletype);
			
			changeRuleType();
			
			if (json.ruletarget == "2") {
				$("#ruletarget1").prop("checked", true);
				$("#ruletarget2").prop("checked", false);
			} else if (json.ruletarget == "1") {
				$("#ruletarget1").prop("checked", false);
				$("#ruletarget2").prop("checked", true);
			}
			
			$("#consumetype").val(json.consumetype);
			changeConsumeType();
			
			$("#name").attr("disabled","disabled");
			$("#ruletype").attr("disabled","disabled");
			$("#consumetype").attr("disabled","disabled");

			$("#rechargemoney").val(json.rechargemoney);
			
			if (json.consumetype == "1") {// 1-消费频次，2-消费金额
				$("#cycleday1").val(json.cycleday);
				if (json.consumefrequencytype == "1") {
					$("#consumefrequencytype1").prop("checked", true);
					$("#consumefrequencytype2").prop("checked", false);
					$("#consumefrequencytype3").prop("checked", false);
					$("#consumelowtimes1").val(json.consumelowtimes);
				} else if (json.consumefrequencytype == "2") {
					$("#consumefrequencytype1").prop("checked", false);
					$("#consumefrequencytype2").prop("checked", true);
					$("#consumefrequencytype3").prop("checked", false);
					$("#consumelowtimes2").val(json.consumelowtimes);
					$("#consumehightimes1").val(json.consumehightimes);
				} else if (json.consumefrequencytype == "3") {
					$("#consumefrequencytype1").prop("checked", false);
					$("#consumefrequencytype2").prop("checked", false);
					$("#consumefrequencytype3").prop("checked", true);
					$("#consumehightimes2").val(json.consumehightimes);
				}
			} else if (json.consumetype == "2") {
				// 单次消费金额返券
				if (json.consumemoneysingleable == "1") {// 0-不可用，1-可用
					$("#consumemoneysingleable").prop("checked", true);
					$("#consumemoneysingelfull").val(json.consumemoneysingelfull);
				} else {
					$("#consumemoneysingleable").prop("checked", false);
				}
				
				// 周期消费总额返券
				if (json.consumemoneycycleable == "1") {// 0-不可用，1-可用
					$("#consumemoneycycleable").prop("checked", true);
					$("#cycleday2").val(json.cycleday);
					if (json.consumemoneycycletype == "1") {
						$("#consumemoneycycletype1").prop("checked", true);
						$("#consumemoneycycletype2").prop("checked", false);
						$("#consumemoneycycletype3").prop("checked", false);
						$("#consumemoneycyclelow1").val(json.consumemoneycyclelow);
					} else if (json.consumemoneycycletype == "2") {
						$("#consumemoneycycletype1").prop("checked", false);
						$("#consumemoneycycletype2").prop("checked", true);
						$("#consumemoneycycletype3").prop("checked", false);
						$("#consumemoneycyclelow2").val(json.consumemoneycyclelow);
						$("#consumemoneycyclefull1").val(json.consumemoneycyclefull);
					} else if (json.consumemoneycycletype == "3") {
						$("#consumemoneycycletype1").prop("checked", false);
						$("#consumemoneycycletype2").prop("checked", false);
						$("#consumemoneycycletype3").prop("checked", true);
						$("#consumemoneycyclefull2").val(json.consumemoneycyclefull);
					}
				} else {
					$("#consumemoneycycleable").prop("checked", false);
				}
			}
			
		}
	});
}

/**
 * 按消费频次，切换满低radio
 */
function changeConsumeFrequencyType() {
	var consumefrequencytype = $('input[name="consumefrequencytype"]:checked').val();
	if (consumefrequencytype == "1") {
		$("#consumelowtimes1").val("");
	} else if (consumefrequencytype == "2") {
		$("#consumelowtimes2").val("");
		$("#consumehightimes1").val("");
	} else if (consumefrequencytype == "3") {
		$("#consumehightimes2").val("");
	}
}

/**
 * 按消费金额，切换checkbox
 */
function changeCheckbox1() {
	// 单次消费金额返券
	if ($("#consumemoneysingleable").prop("checked")) {
		$("#consumemoneysingelfull").val("");
	}
}

/**
 * 按消费金额，切换checkbox
 */
function changeCheckbox2() {
	// 周期消费总额返券
	if ($("#consumemoneycycleable").prop("checked")) {
		$("#cycleday2").val("");
		$("#consumemoneycycletype1").prop("checked", true);
		$("#consumemoneycycletype2").prop("checked", false);
		$("#consumemoneycycletype3").prop("checked", false);
		$("#consumemoneycyclelow1").val("");
		$("#consumemoneycyclelow2").val("");
		$("#consumemoneycyclefull1").val("");
		$("#consumemoneycyclefull2").val("");
	}
}

/**
 * 按消费金额，切换满低radio
 */
function changeConsumeMoneyCycleType() {
	var consumemoneycycletype = $('input[name="consumemoneycycletype"]:checked').val();
	if (consumemoneycycletype == "1") {
		$("#consumemoneycyclelow1").val("");
	} else if (consumemoneycycletype == "2") {
		$("#consumemoneycyclelow2").val("");
		$("#consumemoneycyclefull1").val("");
	} else if (consumemoneycycletype == "3") {
		$("#consumemoneycyclefull2").val("");
	}
}

/**
 * 查询历史记录
 */
function historyquery(id,name) {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "PubCouponRule/HistoryIndex?id=" + id +"&name=" + name;
}

/**
 * 处理整数前面多余的0
 */
function overFormat(obj) {
	if(/^0+\d+\.?\d*.*$/.test(obj.value)){
		obj.value = obj.value.replace(/^0+(\d+\.?\d*).*$/, '$1');
	}
}


$.validator.addMethod("namecheck", function(value, element, param) {
	var reg = new RegExp("^[a-zA-Z0-9\u4e00-\u9fa5]+$");
	return reg.test(value);
}, "仅限数字、字母和汉字");

/**
 * 消费次数上限须大于下限
 */
$.validator.addMethod("consumehightimes1check", function(value, element, param) {
	var consumefrequencytype = getConsumefrequencytype();
	if (consumefrequencytype == "2") {
		var consumelowtimes2 = $("#consumelowtimes2").val();
		var consumehightimes1 = $("#consumehightimes1").val();
		if (consumelowtimes2 != "" && consumehightimes1 != "" && parseInt(consumelowtimes2) >= parseInt(consumehightimes1)) {
			return false;
		} 
	}
	return true;
}, "");

/**
 * 消费金额上限须大于下限
 */
$.validator.addMethod("consumemoneycyclefull1check", function(value, element, param) {
	var consumemoneycycletype = getConsumemoneycycletype();
	if ($("#consumemoneycycleable").prop("checked") && consumemoneycycletype == "2") {
		var consumemoneycyclelow2 = $("#consumemoneycyclelow2").val();
		var consumemoneycyclefull1 = $("#consumemoneycyclefull1").val();
		if (consumemoneycyclelow2 != "" && consumemoneycyclefull1 != "" && parseInt(consumemoneycyclelow2) >= parseInt(consumemoneycyclefull1)) {
			return false;
		} 
	}
	return true;
}, "");

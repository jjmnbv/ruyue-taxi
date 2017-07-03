var dataGrid;
var usertype;

/**
 * 页面初始化
 */
$(function () {
	initBtn();
	
	initGrid();
	validateForm();
	
	initSelectCity();
	initData();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: $("#baseUrl").val() + "OpAccountrules/GetOpAccountRulesByQuery",
        iLeftColumn: 1,
        scrollX: true,
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "city", sTitle: "城市", sClass: "center", visible: false },
	        {mDataProp: "vehiclemodelsid", sTitle: "服务车型", sClass: "center", visible: false },
	        {mDataProp: "rulestype", sTitle: "规则类型", sClass: "center", visible: false },
	        {mDataProp: "rulesstate", sTitle: "规则状态", sClass: "center", visible: false },
	        {mDataProp: "timetype", sTitle: "时间补贴类型", sClass: "center", visible: false },
	        {mDataProp: "perhour", sTitle: "时速", sClass: "center", visible: false },
	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 150,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    if (full.rulesstate == '0') {
                    	if(null != usertype && usertype != 1) {
                    		html += '<button type="button" class="SSbtn grey" disabled="disabled"><i class="fa fa-times"></i>禁用</button>';
                    	} else {
                    		html += '<button type="button" class="SSbtn red"  onclick="editUseable(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i>禁用</button>';
                    	}
                    	
                    } else if (full.rulesstate == '1') {
                    	if(null != usertype && usertype != 1) {
                    		html += '<button type="button" class="SSbtn grey" disabled="disabled"><i class="fa fa-times"></i>启用</button>';
                    	} else {
                    		html += '<button type="button" class="SSbtn blue"  onclick="editUse(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i>启用</button>';
                    	}
                    }
                    if(null != usertype && usertype != 1) {
                    	html += '&nbsp; <button type="button" class="SSbtn grey" disabled="disabled"><i class="fa fa-paste"></i>修改</button>';
                    } else {
                    	html += '&nbsp; <button type="button" class="SSbtn green_a" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
                    }
                    html += '&nbsp; <button type="button" class="SSbtn grey" onclick="'+
                    	'searchHistory(' +"'"+ full.id +"'"+ ',' +"'"+ full.cityName +"'"+ ',' +"'"+ full.rulestypeName +"'"+ ',' +"'"+ full.vehiclemodelsName +"'"+ ')"><i class="fa fa-paste"></i>历史记录</button>';
                    return html;
                }
            },

	        {mDataProp: "cityName", sTitle: "城市", sClass: "center", sortable: true },
	        {mDataProp: "vehiclemodelsName", sTitle: "服务车型", sClass: "center", sortable: true },
	        {mDataProp: "rulestypeName", sTitle: "规则类型", sClass: "center", sortable: true },
	        {mDataProp: "startprice", sTitle: "起步价(元)", sClass: "center", sortable: true },
	        {mDataProp: "rangeprice", sTitle: "里程价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "timeprice", sTitle: "时间补贴(元/分钟)", sClass: "center", sortable: true },
	        {mDataProp: "timetypeName", sTitle: "时间补贴类型", sClass: "center", sortable: true },
	        {mDataProp: "perhourVisual", sTitle: "时速(公里/小时)", sClass: "center", sortable: true },
	        //{mDataProp: "rulesstateName", sTitle: "规则状态", sClass: "center", sortable: true },
	        {mDataProp: "deadheadmileage", sTitle: "回空里程(公里)", sClass: "center", sortable: true },
	        {mDataProp: "deadheadprice", sTitle: "回空费价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "", sTitle: "夜间征收时段", sClass: "center", sortable: true ,
	        	mRender : function(data, type, full) {
	        		if(full.nightstarttime != null && full.nightstarttime != ''){
	        			return '<span>'+full.nightstarttime+'-'+full.nightendtime+'</span>';
	        		}else{
	        			return '/';
	        		}
	        	}
	        },
	        {mDataProp: "nighteprice", sTitle: "夜间费价(元/公里)", sClass: "center", sortable: true },
	        {
				mDataProp : "rulesstateName",
				sTitle : "规则状态",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						var html = "";
	                    if (full.rulesstate == '1') {//禁用
	                    	html += '<span class="font_red">' + full.rulesstateName + '</span>' ;
	                    } else {
	                    	html += '<span>' + full.rulesstateName + '</span>' ;
	                    }
						return html;
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "updatetime", sTitle: "更新时间", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 表单校验
 */
function validateForm() {
	$("#editForm").validate({
		rules: {
			editCity: {required: true},
	         perhour: {required: true, number:true, limitNum:[3, 1]} 
		},
		messages: {
			editCity: {required: "请选择需要更换的城市"},
		     perhour: {required: "请输入时速", number:"只能输入数字和小数点", limitNum:"最大3位整数，1位小数"}
		}
	})

	$("#disableForm").validate({
		rules: {
			reason: {required: true, maxlength: 200}
		},
		messages: {
			reason: {required: "请填写禁用原因", maxlength: "最大长度不能超过200个字符"}
		}
	})
}

function initSelectCity() {
	$("#city").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : $("#baseUrl").val() + "OpAccountrules/GetCityByList",
			dataType : 'json',
			data : function(term, page) {
				return {
					cityName: term
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
 * 初始化界面
 */
function initData() {
	$("#label1").html("");
	$("#label2").html("");
	$("#perhourDiv").hide();
}

/**
 * 一键更换
 */
function editOneKey() {
	$("#editFormDiv").show();
	showObjectOnForm("editForm", null);
	
	var editForm = $("#editForm").validate();
	editForm.resetForm();
	editForm.reset();
	
	$("#label1").html("");
	$("#label2").html("");
	$("#perhourDiv").hide();
}

/**
 * 禁用
 */
function editUseable(id) {
	$("#disableFormDiv").show();
	showObjectOnForm("disableForm", null);
	
	var editForm = $("#disableForm").validate();
	editForm.resetForm();
	editForm.reset();
	
	$("#id").val(id);
}

/**
 * 启用
 * @param {} id
 */
function editUse(id) {
	var data = { id: id, rulesstate: 0 };
	
	$.ajax({
		type: "POST",
		dataType: 'json',
		url:$("#baseUrl").val() + "OpAccountrules/EditOpAccountRulesState",
		cache: false,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			var message = status.MessageKey == null ? status : status.MessageKey;
			if (status.ResultSign == "Successful") {
            	toastr.success(message, "提示");
            	$("#id").val(id);
    			dataGrid._fnReDraw();
			} else {
            	toastr.error(message, "提示");
			}
		},
		error: function (xhr, status, error) {
			return;
		}
    });
}


/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "rulestype", "value": $("#rulestype").val() },
		{ "name": "city", "value": $("#city").val() },
		{ "name": "vehiclemodelsid", "value": $("#vehiclemodelsid").val() },
		{ "name": "rulesstate", "value": $("#rulesstate").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 清空查询条件
 */
function clearSearch(){
	$("#rulestype").val("");
	$("#city").val("");
	$("#vehiclemodelsid").val("");
	$("#rulesstate").val("");
	$(".select2-search-choice-close").mousedown();
	search();
}

/**
 * 禁用保存
 */
function saveDisable() {
	var form = $("#disableForm");
	if(!form.valid()) return;
	
	var url = $("#baseUrl").val() + "OpAccountrules/EditOpAccountRulesState";

	var data = form.serializeObject();
	data["rulesstate"] = "1";
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			var message = status.MessageKey == null ? status : status.MessageKey;
			if (status.ResultSign == "Successful") {
            	toastr.success(message, "提示");
				$("#disableFormDiv").hide();
				showObjectOnForm("disableForm", null);
				dataGrid._fnReDraw();
			} else {
				toastr.error(message, "提示")
			}	
		}
	});
}

/**
 * 新增
 */
function add() {
	window.location.href=$("#baseUrl").val() + "OpAccountrules/EditAccountRulesPage";
}

/**
 * 修改
 * @param {} id
 */
function edit(id) {
	window.location.href=$("#baseUrl").val() + "OpAccountrules/EditAccountRulesPage?id=" + id;
}

/**
 * 历史
 * @param {} id
 */
function searchHistory(id,cityName,rulestypeName,vehiclemodelsName) {
	window.location.href=$("#baseUrl").val() + "OpAccountrules/SearchHistory?id=" + id +"&cityName=" + cityName + "&rulestypeName=" + rulestypeName + "&vehiclemodelsName=" + vehiclemodelsName;
}

function changeTimeType() {
	var city = $("#editCity").val();
	if (city=="") {
		$("#label1").html("");
		$("#label2").html("");
		$("#perhourDiv").hide();
	} else {
		    var url = $("#baseUrl").val() + "OpAccountrules/GetOpAccountRulesByCity";
			var data = {city: city};
			$.ajax({
				type: 'POST',
				dataType: 'json',
				data: JSON.stringify(data),
				url: url,
				contentType: 'application/json; charset=utf-8',
				async: false,
				success: function (json) {
					var timetype = json.timetype;
					if(timetype=='0') {
						$("#label1").html("按 <b>总用时</b> 计费");
						$("#label2").html("按 <b>时速</b> 计费");
						$("#perhourDiv").show();
						$("#timetype").val("1");
					} else if(timetype=='1') {
						$("#label1").html("按 <b>时速</b> 计费");
						$("#label2").html("按 <b>总用时</b> 计费");
						$("#perhourDiv").hide();
						$("#timetype").val("0");
					}
				}
			});
	}
}

/**
 * 更换
 */
function save() {
	var form = $("#editForm");
	if(!form.valid()) {
		return;
	}
	var timetype = $("#timetype").val();
	var perhour = $("#perhour").val();
	if(timetype != "0" && (null == perhour || "" == perhour)) {
		return;
	}

	var url = $("#baseUrl").val() + "OpAccountrules/EditOpAccountRulesByCity";
	var data = {city:$("#editCity").val(),perhour:$("#perhour").val(),timetype:$("#timetype").val()};
	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			var message = status.MessageKey == null ? status : status.MessageKey;
			if (status.ResultSign == "Successful") {
            	toastr.success(message, "提示");
				$("#editFormDiv").hide();
				showObjectOnForm("editForm", null);
				dataGrid._fnReDraw();
			} else {
				toastr.error(message, "提示");
			}	
		}
	});
}

/**
 * 初始化按钮
 */
function initBtn() {
	usertype = $("#usertype").val();
	//禁用按钮
	if(null != usertype && usertype != 1) {
		$("#addBtn").attr("disabled", "disabled");
		$("#addBtn").removeClass("blue").addClass("grey");
		$("#editOneKeyBtn").attr("disabled", "disabled");
		$("#editOneKeyBtn").removeClass("orange").addClass("grey");
	}
}

/**
 * 取消
 */
function canel() {
	$("#editFormDiv").hide();
	$("#disableFormDiv").hide();
}

$.validator.addMethod("limitNum", function(value, element, param) {
	var rep = new RegExp("^\\d{0,"+param[0]+"}(\\.\\d){0,"+param[1]+"}$");
	return rep.test(value);
}, "数字格式不正确");

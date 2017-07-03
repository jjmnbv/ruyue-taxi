var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	validateForm();
	
	dateFormat();
	initSelectCity();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "StandardAccountRules/GetStandardAccountRulesByQuery",
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无计费规则信息"
        },
        columns: [
	        /*{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "city", sTitle: "城市", sClass: "center", visible: false },
	        {mDataProp: "carType", sTitle: "服务车型", sClass: "center", visible: false },
	        {mDataProp: "rulesType", sTitle: "规则类型", sClass: "center", visible: false },
	        {mDataProp: "rulesState", sTitle: "规则状态", sClass: "center", visible: false },
	        {mDataProp: "timeType", sTitle: "时间补贴类型", sClass: "center", visible: false },
	        {mDataProp: "perhour", sTitle: "时速", sClass: "center", visible: false },*/
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
                    if (full.rulesState == '0') {
                    	html += '<button type="button" class="SSbtn red"  onclick="editUseable(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i>禁用</button>';
                    } else if (full.rulesState == '1') {
                    	html += '<button type="button" class="SSbtn blue"  onclick="editUse(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i>启用</button>';
                    }
                    html += '&nbsp; <button type="button" class="SSbtn green_a" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
                    html += '&nbsp; <button type="button" class="SSbtn grey"  onclick="searchHistory(' +"'"+ full.id +"','" + full.cityName +"','" + full.rulesTypeName +"','" + full.carTypeName +"'"+ ')"><i class="fa fa-times"></i>历史规则</button>';
                    return html;
                }
            },

	        {mDataProp: "cityName", sTitle: "城市", sClass: "center", sortable: true },
	        {mDataProp: "carTypeName", sTitle: "服务车型", sClass: "center", sortable: true },
	        {mDataProp: "rulesTypeName", sTitle: "规则类型", sClass: "center", sortable: true },
	        {mDataProp: "startPrice", sTitle: "起步价(元)", sClass: "center", sortable: true },
	        {mDataProp: "rangePrice", sTitle: "里程价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "timePrice", sTitle: "时间补贴(元/分钟)", sClass: "center", sortable: true },
	        {mDataProp: "timeTypeName", sTitle: "时间补贴类型", sClass: "center", sortable: true },
	        {mDataProp: "perhourVisual", sTitle: "时速(公里/小时)", sClass: "center", sortable: true },
	        {mDataProp: "deadheadmileage", sTitle: "回空里程(公里)", sClass: "center", sortable: true },
	        {mDataProp: "deadheadprice", sTitle: "回空费价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "nighttimes", sTitle: "夜间征收时段", sClass: "center", sortable: true },
	        {mDataProp: "nighteprice", sTitle: "夜间费价(元/公里)", sClass: "center", sortable: true },
	        //{mDataProp: "rulesStateName", sTitle: "规则状态", sClass: "center", sortable: true },
	        {
				mDataProp : "rulesStateName",
				sTitle : "规则状态",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						var html = "";
	                    if (full.rulesState == '1') {//禁用
	                    	html += '<span class="font_red">' + full.rulesStateName + '</span>' ;
	                    } else {
	                    	html += '<span>' + full.rulesStateName + '</span>' ;
	                    }
						return html;
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "updateTime", sTitle: "更新时间", sClass: "center", sortable: true }
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

function dateFormat() {
	$('.disableDate').datetimepicker({
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
}

function initSelectCity() {
	$("#city").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "StandardAccountRules/GetExistCityList",
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
	var data = { id: id };
	
	$.ajax({
		type: "POST",
		dataType: 'json',
		url:"StandardAccountRules/UpdateStandardAccountRulesState?modiType=1",
		cache: false,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.success(message, "提示");
            
            	$("#id").val(id);
    			dataGrid._fnReDraw();
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
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
		{ "name": "rulesType", "value": $("#rulesType").val() },
		{ "name": "city", "value": $("#city").val() },
		{ "name": "carType", "value": $("#carType").val() },
		{ "name": "rulesState", "value": $("#rulesState").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 禁用保存
 */
function saveDisable() {
	var form = $("#disableForm");
	if(!form.valid()) return;
	
	var url = "StandardAccountRules/UpdateStandardAccountRulesState?modiType=2";

	var data = form.serializeObject();
	
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
            
				$("#disableFormDiv").hide();
				showObjectOnForm("disableForm", null);
				dataGrid._fnReDraw();
			} else {
				
			}	
		}
	});
}

/**
 * 新增
 */
function add() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "StandardAccountRules/EditRules";
}

/**
 * 修改
 * @param {} id
 */
function edit(id) {

	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "StandardAccountRules/EditRules?id=" + id;
}

/**
 * 历史
 * @param {} id
 */
function searchHistory(id,cityName,rulesTypeName,carTypeName) {

	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "StandardAccountRules/SearchHistory?id=" + id +"&cityName=" + cityName + "&rulesTypeName=" + rulesTypeName + "&carTypeName=" + carTypeName;
}

function changeTimeType() {
	var city = $("#editCity").val();
	if (city=="") {
		$("#label1").html("");
		$("#label2").html("");
		$("#perhourDiv").hide();
	} else {
		    var url = "StandardAccountRules/GetStandardAccountRulesByCity?city=" + city + "&datetime=" + new Date().getTime();
			
			$.ajax({
				type: 'GET',
				dataType: 'json',
				url: url,
				contentType: 'application/json; charset=utf-8',
				async: false,
				success: function (json) {
					var timeType = json.timeType;
					$("#timeType").val(timeType);
					if(timeType=='0') {
						$("#label1").html("按 <b>总用时</b> 计费");
						$("#label2").html("按 <b>时速</b> 计费");
						$("#perhourDiv").show();
					} else if(timeType=='1') {
						$("#label1").html("按 <b>时速</b> 计费");
						$("#label2").html("按 <b>总用时</b> 计费");
						$("#perhourDiv").hide();
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
	if(!form.valid()) return;
	
	var timeType = $("#timeType").val();
	if(timeType=='0') {
		var perhour = $("#perhour");
		if(!perhour.valid()) return;
	}

	var url = "StandardAccountRules/UpdateStandardAccountRulesOneKey";
	
	var data = {city:$("#editCity").val(),perhour:$("#perhour").val(),timeType:$("#timeType").val()};
	
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
            
				$("#editFormDiv").hide();
				showObjectOnForm("editForm", null);
				dataGrid._fnReDraw();
			} else {
				
			}	
		}
	});
}


/**
 * 取消
 */
function canel() {
	$("#editFormDiv").hide();
	$("#disableFormDiv").hide();
}

/**
 * value 验证的值
 * param[0] 最大整数位
 * param[1] 最大小数位
 */
$.validator.addMethod("limitNum", function(value, element, param) {
	var rep = new RegExp("^\\d{0,"+param[0]+"}(\\.\\d){0,"+param[1]+"}$");
	return rep.test(value);
}, "数字格式不正确");

var dataGrid;
var usertype;

/**
 * 页面初始化
 */
$(function () {
	initBtn();
	initSelectCity();
	initGrid();
	validateForm();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: $("#baseUrl").val() + "OpTaxiAccountRules/Search",
        iLeftColumn: 1,
        scrollX: true,
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
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
                    var btns = ["禁用","启用","修改","历史记录"];
                    var cols = ["red","blue","green_a","grey"];
                    var param = ['disable("'+full.id+'");','enable("'+full.id+'");','edit("'+full.id+'");','searchHistory("'+full.id+'","'+full.cityname+'");'];
                    for(var i in btns){
                    	var iTag = $("<i>").attr("class","fa fa-times");
                    	var button = $("<button>").attr("type","button").attr("class","SSbtn "+cols[i]).attr("onclick",param[i]).append(iTag).append(btns[i]);
//                    	button.click(
//	            			btns[i] == "禁用" ? function(){disable(full.id);} : 
//	        				btns[i] == "启用" ? function(){enable(full.id);} : 
//	    					btns[i] == "修改" ? function(){edit(full.id);} : 
//							btns[i] == "历史记录" ? function(){searchHistory(full.id,full.cityname);} : function(){}
//						);
                    	if(full.rulesstate == '1' && btns[i] == "禁用"){
                    		continue;
                    	}else if(full.rulesstate == '0' && btns[i] == "启用"){
                    		continue;
                    	}else{
                    		if(null != usertype && usertype != 1 && btns[i] != "历史记录"){
                    			button.attr("class","SSbtn grey").attr("disabled",true);
                    		}
                    		html += button[0].outerHTML + "&nbsp;";
                    	}
                    }
                    return html;
                }
            },
	        {mDataProp: "cityname", sTitle: "城市", sClass: "center", sortable: true },
	        {mDataProp: "startprice", sTitle: "起租价(元)", sClass: "center", sortable: true },
	        {mDataProp: "startrange", sTitle: "起租里程(公里)", sClass: "center", sortable: true },
	        {mDataProp: "renewalprice", sTitle: "续租价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "surcharge", sTitle: "附加费（元）", sClass: "center", sortable: true },
	        {mDataProp: "standardrange", sTitle: "标准里程（公里）", sClass: "center", sortable: true },
	        {mDataProp: "emptytravelrate", sTitle: "空驶费率", sClass: "center", sortable: true ,"mRender" : function(data,type,full){
            	return data+"%"
            }},
            {mDataProp: "rulesstate", sTitle: "规则状态", sClass: "center", sortable: true ,"mRender" : function(data,type,full){
            	return full.rulesstate == '0' ? "启用" : "禁用";
            }},
            {mDataProp: "updatetime", sTitle: "更新时间", sClass: "center", sortable: true ,"mRender" : function(data,type,full){
            	var time = timeStamp2String(data,"/");
            	time = time.substring(0,time.length - 3);  //去掉秒
            	return time;
            }},
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 表单校验
 */
function validateForm() {
	$.validator.addMethod("limitNum", function(value, element, param) {
		var rep = new RegExp("^\\d{0,"+param[0]+"}(\\.\\d){0,"+param[1]+"}$");
		return rep.test(value);
	}, "数字格式不正确");

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
			params: { //select2的contentType要这样写.否则无法使用requestBody接收参数
				contentType: "application/json; charset=utf-8"
			},
			type : 'POST',
			dataType : 'json',
			url : $("#baseUrl").val() + "OpTaxiAccountRules/GetCityListForSelect",
			data : function(term, page) {
            	//参数必须格式化
            	var data = {
    					sSearch : term
    			};
            	return JSON.stringify(data);
			},
			results : function(data, page) {
				return {
					results: data.list
				};
			}
		}
	});
}

/**
 * 禁用
 */
function disable(id) {
	var data = {
		id:id,
		rulesstate:1
	}
	var url = $("#baseUrl").val() + "OpTaxiAccountRules/DisableRule";
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (result) {
			if (result.status == 0) {
            	toastr.success("禁用成功", "提示");
				dataGrid._fnReDraw();
			} else {
				toastr.error(result.message, "提示")
			}	
		}
	});
}

/**
 * 启用
 * @param {} id
 */
function enable(id) {
	var data = { 
		id: id, 
		rulesstate: 0
	};
	var url = $("#baseUrl").val() + "OpTaxiAccountRules/EnableRule";
	$.ajax({
		type: "POST",
		dataType: 'json',
		url:url,
		cache: false,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (result) {
			if (result.status == 0) {
            	toastr.success("启用成功", "提示");
				dataGrid._fnReDraw();
			} else {
				toastr.error(result.message, "提示")
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
	$("#city").select2("val","");
	$("#rulesstate").val("");
	search();
}

/**
 * 禁用保存
 */
function saveDisable() {
	var form = $("#disableForm");
	if(!form.valid()) return;
	
	var url = $("#baseUrl").val() + "OpTaxiAccountRules/DisableRule";

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
	window.location.href=$("#baseUrl").val() + "OpTaxiAccountRules/Add";
}

/**
 * 修改
 * @param {} id
 */
function edit(id) {
	window.location.href=$("#baseUrl").val() + "OpTaxiAccountRules/Edit?id=" + id;
}

/**
 * 历史
 * @param {} id
 */
function searchHistory(id,cityname) {
	window.location.href=$("#baseUrl").val() + "OpTaxiAccountRules/History?id=" + id +"&cityname=" + cityname;
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

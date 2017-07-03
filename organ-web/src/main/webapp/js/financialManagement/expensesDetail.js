var dataGrid;
var num = 0;
/**
 * 页面初始化
 */
$(function () {
	initGrid();
	num++;
	dateFormat();
	getCurrentDate();
	//validateForm();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "FinancialManagement/GetOrganExpensesByQuery?leasesCompanyId=" + $("#leasesCompanyId").val() + "&organId=" + $("#organId").val(),
        iDisplayLength: 20,
        language: {
        	sEmptyTable: "暂无交易记录"
        },
        columns: [
	        {mDataProp: "expenseTime", sTitle: "时间", sClass: "center", sortable: true },
	        {mDataProp: "typeName", sTitle: "类型", sClass: "center", sortable: true },
	        //{mDataProp: "amountVisual", sTitle: "金额(元)", sClass: "center", sortable: true },
	        {
				mDataProp : "amountVisual",
				sTitle : "金额(元)",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						var html = "";
	                    if (full.type == '0' || (full.type == '1' && full.operateResult == '1')) {//0-充值   && 提现失败
	                    	html += '<span class="font-red">' + full.amountVisual + '</span>' ;
	                    } else {//1-提现   2-结算
	                    	html += '<span class="font-green">' + full.amountVisual + '</span>' ;
	                    }
						return html;
					} else {
						return "";
					}
				}
			},
	        {
				mDataProp : "operatorType",
				sTitle : "备注",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						if (full.operatorType == "0") {// 0-车企
							if (full.type == "0") {// 0-企业充值，1-企业提现，2-账单结算扣款
								return "车企代充值";
							} else if (full.type == "1") {
								return "车企代提现";
							}
						} else if (full.operatorType == "1") {// 1-机构
							if (full.type == "0") {// 0-企业充值，1-企业提现，2-账单结算扣款
								return "机构自充值";
							} else if (full.type == "1") {
								if (full.operateResult == '0') {// 0-成功,1-失败
									return "机构自提现";
								} else if (full.operateResult == '1') {
									return "提现失败";
								}
							}
						} 
						return "";
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
 * 表单校验
 */
function validateForm() {
	$("#searchForm").validate({
		rules: {
			startTime:{date: true,dateISO: true},
			endTime:{date: true,dateISO: true}
		},
		messages: {
			startTime: {date: "请输入合法的日期",dateISO: "请输入合法的日期 (ISO)"},
			endTime: {date: "请输入合法的日期",dateISO: "请输入合法的日期 (ISO)"}
		}
	})
}

/**
 * 毫秒转日期
 * 
 * @param data
 * @returns {String}
 */
function changeToDate(data) {
	var myDate = new Date(data);
	var month = "";
	var date = "";
	var hours = "";
	var minutes = "";
	var change = "";
	change += myDate.getFullYear() + "/";

	if (myDate.getMonth() < 9) {
		month = "0" + (myDate.getMonth() + 1);
	} else {
		month = (myDate.getMonth() + 1);
	}
	change += month + "/";
	if (myDate.getDate() < 10) {
		date = "0" + myDate.getDate();
	} else {
		date = myDate.getDate();
	}
	change += date;
	if (myDate.getHours() < 10) {
		hours = "0" + myDate.getHours();
	} else {
		hours = myDate.getHours();
	}
	change += " " + hours;
	if (myDate.getMinutes() < 10) {
		minutes = "0" + myDate.getMinutes();
	} else {
		minutes = myDate.getMinutes();
	}
	change += ":" + minutes;
	return change;
}

/**
 * 返回
 */
function back() {
	window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "FinancialManagement/Index";
}

function dateFormat() {
	$('.date').datetimepicker({
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
 * 获取当前日期
 * 
 * @param data
 * @returns {String}
 */
function getCurrentDate() {
	var myDate = new Date();
	var month = "";
	var date = "";
	var change = "";
	change += myDate.getFullYear() + "-";

	if (myDate.getMonth() < 9) {
		month = "0" + (myDate.getMonth() + 1);
	} else {
		month = (myDate.getMonth() + 1);
	}
	change += month + "-";
	if (myDate.getDate() < 10) {
		date = "0" + myDate.getDate();
	} else {
		date = myDate.getDate();
	}
	change += date;
	$("#startTime").val(change);
	$("#endTime").val(change);
}

/**
 * 查询
 */
function search() {
	if(!dateCheck()) return;
	
	var type = "";  
    $('input[name="type"]:checked').each(function(i){  
        if(0==i){  
        	type = $(this).val();  
        }else{  
        	type += (","+$(this).val());
        }  
    })  

	var conditionArr = [           
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() },
		{ "name": "type", "value": type },
		{ "name": "num", "value": num }
	];
    var language = "没有查询到相关交易数据";
	dataGrid.fnSearch(conditionArr, language);
}

/**
 * 有效期限的check
 */
function dateCheck() {
	var message = "";
	if ($("#startTime").val() > $("#endTime").val()) {
		message += "结束日期要大于等于开始日期";
	}
	if (message != "") {
		toastr.error(message, "提示");
		return false;
	} else {
		return true;
	}
}


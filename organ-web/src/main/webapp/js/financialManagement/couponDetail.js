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
        sAjaxSource: "FinancialManagement/GetPubCouponDetailByQuery",
        iDisplayLength: 20,
        language: {
        	sEmptyTable: "<div style='height:48px;'><img src='img/financialManagement/diyongjuan.png'/><div style='width:12%;height:20px;margin-left:53%;margin-top:-5%;'>暂无抵用券记录</div></div>"
        },
        columns: [
	        {mDataProp: "createtime", sTitle: "时间", sClass: "center", sortable: true,
	        	mRender : function(data, type, full) {
					var html = "";
                	html += '<span class="font-green">' + changeToDate(full.createtime) + '</span>' ;
					return html;
				}	
	        },
	        {mDataProp: "usetype", sTitle: "类型", sClass: "center", sortable: true,
	        	mRender : function(data, type, full) {
	        		var html = "";
	        		if(full.usetype == 1){
	        			html +="<span>充值返劵</span>";
	        		}else if(full.usetype == 2){
	        			html +="<span>账单结算扣款</span>";
	        		}else if(full.usetype == 3){
	        			html +="<span>注册返劵</span>";
	        		}else if(full.usetype == 4){
	        			html +="<span>活动返劵</span>";
	        		}else if(full.usetype == 5){
	        			html +="<span>违约清零</span>";
	        		}
					return html;
				}		
	        },
	        {mDataProp: "amount", sTitle: "金额（元）", sClass: "center", sortable: true,
	        	mRender : function(data, type, full) {
	        		var html = "";
	        		if(full.usetype == 1){
	        			html +="<span>+"+full.amount+"</span>";
	        		}else if(full.usetype == 2){
	        			html +="<span>-"+full.amount+"</span>";
	        		}else if(full.usetype == 3){
	        			html +="<span>+"+full.amount+"</span>";
	        		}else if(full.usetype == 4){
	        			html +="<span>+"+full.amount+"</span>";
	        		}else if(full.usetype == 5){
	        			html +="<span>-"+full.amount+"</span>";
	        		}
					return html;
				}		
	        },
	        {mDataProp: "balance", sTitle: "抵用券余额（元）", sClass: "center", sortable: true,
	        	mRender : function(data, type, full) {
	        		var html = "";
	        		if(full.usetype == 5){
	        			html +="<span>0</span>";
	        		}else{
	        			html +="<span>"+full.balance+"</span>";
	        		}
					return html;
				}		
	        },
	        {mDataProp: "remark", sTitle: "备注", sClass: "center", sortable: true }
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

	var conditionArr = [           
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() },
		{ "name": "type", "value": $("#type").attr("data-value") }
	];
    var language = "<div style='height:48px;'><img src='img/financialManagement/diyongjuan.png'/><div style='width:12%;height:20px;margin-left:53%;margin-top:-5%;'>暂无抵用券记录</div></div>";
	dataGrid.fnSearch(conditionArr, language);
}
//抵用券 页面取消
function cancels(){
	$("#startTime").val("");
	$("#endTime").val("");
	$("#type").attr("data-value","");
	$("#type").attr("value","");
	search();
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


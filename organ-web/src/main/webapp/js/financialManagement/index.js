var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initTab();
	initGrid();
	initSelect();
	initHref();
});

/**
 * tab初始化
 */
function initTab() {
	var tabnum = $("#tabnum").val();
	if (tabnum == "") {
		tabnum = 0;
	}
	$(".tabmenu>li:eq("+tabnum+")").addClass("on").siblings().removeClass("on");
	$(".tabbox>li:eq("+tabnum+")").show().siblings().hide();

	$(".tabmenu>li").click(function() {
        $(this).addClass("on").siblings().removeClass("on");
        var n=$(this).index();
        $("#tabnum").val(n);
        $(".tabbox>li:eq("+n+")").show().siblings().hide();
    });
}

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "FinancialManagement/GetOrganBillByQuery",
        iDisplayLength: 20,
        language: {
        	sEmptyTable: "暂无账单记录"
        },
        columns: [
	        {mDataProp: "id", sTitle: "账单编号", sClass: "center", sortable: true },
	        {mDataProp: "name", sTitle: "账单名称", sClass: "center", sortable: true },
	        //{mDataProp: "createTime", sTitle: "出账时间", sClass: "center", sortable: true },
	        {
				mDataProp : "createTime",
				sTitle : "出账时间",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						return changeToDate(data);
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "shortName", sTitle: "服务车企", sClass: "center", sortable: true },
	        {mDataProp: "money", sTitle: "账单金额", sClass: "center", sortable: true },
	        {mDataProp: "billStateName", sTitle: "账单状态", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
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
	return change;
}


var isclear = false;
/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "timeType", "value": $("#timeType").attr("data-value") },
		{ "name": "leasesCompanyId", "value": $("#leasesCompanyId").attr("data-value") },
		{ "name": "billState", "value": $("#billState").attr("data-value") }
	];
	var language = "没有查询到相关账单数据";
	if (isclear) {
		language = "暂无账单记录";
	}
	dataGrid.fnSearch(conditionArr, language);
}

/**
 * 点击下拉框时重新检索
 */
function initSelect() {
	$(".select_content li").live('click',function() {
		search();
	});
}

/**
 * 点击账单管理每行数据时，跳转到账单明细
 */
function initHref() {
	$("table > tbody > tr").live('click',function () {
		//alert( $(this).find("td:first").text());
		if ($(this).find("td").length > 1) {
			window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "FinancialManagement/BillDetail/" + $(this).find("td:first").text();
		}
    });
}

/**
 * 清空
 */
$(".btn_grey").click(function(){
	$("#timeType").val("");
	$("#leasesCompanyId").val("");
	$("#billState").val("");
	
	$("#timeType").attr("data-value","");
	$("#leasesCompanyId").attr("data-value","");
	$("#billState").attr("data-value","");
	
	isclear = true;
	search();
	isclear = false;
});

/**
 * 交易明细
 */
function searchExpensesDetail(leasesCompanyId,organId) {
	window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "FinancialManagement/ExpensesDetail?leasesCompanyId=" + leasesCompanyId + "&organId=" + organId;
}

/**
 * 充值
 */
function recharge(leasesCompanyId,organId) {

	$.ajax({
		type: "POST",
		dataType: 'json',
		url:"FinancialManagement/GetLeasesCompanyById/" + leasesCompanyId,
		cache: false,
		data: null,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "FinancialManagement/Recharge?leasesCompanyId=" + leasesCompanyId + "&organId=" + organId;
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
 * 提现
 */
function withdraw(leasesCompanyId,organId) {
	
	$.ajax({
		type: "POST",
		dataType: 'json',
		url:"FinancialManagement/JudgeOrganAccountStatus?leasesCompanyId=" + leasesCompanyId + "&organId=" + organId,
		cache: false,
		data: null,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "FinancialManagement/GetWithdrawInfo?leasesCompanyId=" + leasesCompanyId + "&organId=" + organId;
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	//toastr.error(message, "提示");
				$(".accountjudge").show();
				$("#zhanghutixing").text(message);
			}

		},
		error: function (xhr, status, error) {
			return;
		}
    });

}

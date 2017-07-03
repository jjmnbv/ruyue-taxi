var dataGrid;
var orderObj = {
	orderInfo: null
};

/**
 * 页面初始化
 */
$(function () {
	manualOrderdataGrid();
	initForm();
});

function initForm() {
	$("#account").select2({
        placeholder: "申请账户",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "LeCashManage/GetAccounts",
            dataType: 'json',
            data: function (term, page) {
            	$(".datetimepicker").hide();
                return {
                	account: term,
                	processstatus:"0"
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });

    $("#creditcardname").select2({
        placeholder: "账户名称",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "LeCashManage/GetNames",
            dataType: 'json',
            data: function (term, page) {
            	$(".datetimepicker").hide();
                return {
                	creditcardname: term,
                	processstatus:"0"
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
    
    $('.searchDate').datetimepicker({
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
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "grid",
        sAjaxSource: $("#baseUrl").val() + "LeCashManage/GetCashByQuery",
        iLeftColumn: 1,
        userQueryParam: [{name: "processstatus", value: "0"}],
        scrollX: true,
		language: {
			sEmptyTable: "暂无提现申请记录"
		},
        columns: [
        	{
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 150,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                	orderObj.orderInfo = full;
                    var html = [];
                    html.push("<button type=\"button\" class=\"SSbtn red\" onclick=\"cashok('" + full.id + "')\"><i class=\"fa fa-paste\"></i>确认打款</button>");
                    html.push("&nbsp;");
                    html.push("<button type=\"button\" class=\"SSbtn blue\" onclick=\"shownotcashdiv('" + full.id + "')\"><i class=\"fa fa-paste\"></i>不予提现</button>");
                    return html.join("");
                }
            },
            {mDataProp: "account", sTitle: "申请账户", sClass: "center", sortable: true },
	        {
                "mDataProp": "LX",
                "sClass": "center",
                "sTitle": "用户类型",
                "mRender": function (data, type, full) {
                   switch(full.usertype) {
                   	case "2": return "司机"; break;
                   	case "0": return "乘客"; break;
                   	case "3": return "机构"; break;
                   	default: return "";
                   }
                }
            },
            {mDataProp: "amount", sTitle: "提现金额(元)", sClass: "center", sortable: true },
            {mDataProp: "creditcardnum", sTitle: "银行账号", sClass: "center", sortable: true },
            {mDataProp: "creditcardname", sTitle: "账户名称", sClass: "center", sortable: true },
            {mDataProp: "bankname", sTitle: "开户银行", sClass: "center", sortable: true },
            {mDataProp: "applytime", sTitle: "申请时间", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}


/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "usertype", "value": $("#usertype").val()},
		{ "name": "account", "value": !!$("#account").select2('data')?$("#account").select2('data').text:""},
		{ "name": "creditcardname", "value": !!$("#creditcardname").select2('data')?$("#creditcardname").select2('data').text:"" },
		{ "name": "minUseTime", "value": $("#minUseTime").val() },
		{ "name": "maxUseTime", "value": $("#maxUseTime").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

function shownotcashdiv(id){
	$("#currentid").val(id);
	$("#reasonTextarea").val("");
	$("#notcashdiv").show();
}

/**
 * 清空
 */
function clearOptions(){
	$("#usertype").val("");
	$("#minUseTime").val("");
	$("#maxUseTime").val("");
	$("#account").select2("val", "");
	$("#creditcardname").select2("val", "");
	search();
}

/**
 * 导出
 */
function exportData(){
	var data = {
			usertype:$("#usertype").val(),
			account:!!$("#account").select2('data')?$("#account").select2('data').text:"",
			creditcardname:!!$("#creditcardname").select2('data')?$("#creditcardname").select2('data').text:"",
			minUseTime:$("#minUseTime").val(),
			maxUseTime:$("#maxUseTime").val()
	};
	var params = "usertype="+data.usertype+"&account="+data.account+"&creditcardname="+data.creditcardname+"&minUseTime="+data.minUseTime+"&maxUseTime="+data.maxUseTime;
	$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "LeCashManage/GetExportDataCount",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
				if(result.count>0){
					window.location.href = $("#baseUrl").val()+"LeCashManage/ExportData?"+params;
				}else{
					toastr.warning("当前条件没有查询出数据，不需要导出", "提示");
				}
			}else{
            	toastr.error(message, "提示");
			}
		}
	});
}

/**
 * 不予提现取消
 */
function cancelNotDiv(){
	$("#notcashdiv").hide();
}

/**
 * 确认打款
 * @param {} orderno
 */
function cashok(id) {
	var data = {id: id};
	Zconfirm("确定已支付提现款项？",function(){
		$.ajax({
			type: "POST",
			dataType: "json",
			url: $("#baseUrl").val() + "LeCashManage/CashOk",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			async: false,
			success: function (result) {
				var message = result.message == null ? result : result.message;
				if (result.status == "success") {
					toastr.success(message, "提示");
				} else {
	            	toastr.error(message, "提示");
				}
				dataGrid._fnReDraw();
			}
		});
	});
}

/**
 * 不打款
 */
function cashreject(){
	var reson = $("#reasonTextarea").val();
	if(!reson){
		toastr.warning("请输入不予提现的原因", "提示");
		return ;
	}
	var data = {id:$("#currentid").val(),reson:reson};
	$.ajax({
		type: "POST",
		dataType: "json",
		url: $("#baseUrl").val() + "LeCashManage/CashReject",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			$("#notcashdiv").hide();
			var message = result.message == null ? result : result.message;
			if (result.status == "success") {
				toastr.success(message, "提示");
			} else {
            	toastr.error(message, "提示");
			}
			dataGrid._fnReDraw();
		}
	});
}

/**
 * 显示长度限制
 * @param addr
 */
function limitLength(text) {
	if(null != text && text.length > 18) {
		return text.substr(0, 18) + "...";
	}
	return text;
}

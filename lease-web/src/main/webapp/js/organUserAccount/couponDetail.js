var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	initDateTimePicker();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "OrganUserAccount/GetOrganUserCouponInfoByQuery",
        iLeftColumn: 1,
        userQueryParam: [{name: "userid", value: $("#userid").val()}],
        scrollX: true,
        language: {
        	sEmptyTable: "暂无抵用券明细"
        },
        columns: [
	        {
				mDataProp : "sendtime",
				sTitle : "时间",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						return '<span class="font_green">' + full.sendtime + '</span>';
					} else {
						return "";
					}
				}
			},
			{mDataProp: "name", sTitle: "抵用券名称", sClass: "center", sortable: true },
	        {mDataProp: "couponstatus", sTitle: "状态", sClass: "center", sortable: true,
				mRender : function(data, type, full) {
					switch(full.couponstatus) {
					case 0: return "未使用"; break;
					case 1: return "已使用"; break;
					case 2: return "已过期"; break;
					default: return "/";
        	        }
				}
	        },
	        {mDataProp: "amount", sTitle: "金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "usestarttime", sTitle: "有效期", sClass: "center", sortable: true, 
	        	mRender : function(data, type, full) {
                         if(full.usestarttime==full.useendtime)
                        	 return full.usestarttime;
                          else
                        	return full.usestarttime+"-"+full.useendtime;	 

				}
	        },
	        {mDataProp: "usecity", sTitle: "限用地点", sClass: "center", sortable: true },
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

function initDateTimePicker() {
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
 * 查询
 */
function search() {
	$("#couponstatusExport").val($("#couponstatus").val());
	$("#starttimeExport").val($("#starttime").val());
	$("#endtimeExport").val($("#endtime").val());
	
	var conditionArr = [
		{ "name": "couponstatus", "value": $("#couponstatus").val() },
		{ "name": "starttime", "value": $("#starttime").val() },
		{ "name": "endtime", "value": $("#endtime").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

function exportExcel() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganUserAccount/ExportCouponData?userid="+$("#userid").val()+"&couponstatus="+$("#couponstatusExport").val()+"&starttime="+$("#starttimeExport").val()+"&endtime="+$("#endtimeExport").val()+"&account="+$("#account").val();;
	
	$("#couponstatus").blur();
	$("#starttime").blur();
	$("#endtime").blur();
}

/**
 * 清空
 */
function emptys() {
	$("#couponstatus").val("");
	$("#starttime").val("");
	$("#endtime").val("");
	
	search();
}

/**
 * 返回
 */
function callBack() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganUserAccount/Index";
}



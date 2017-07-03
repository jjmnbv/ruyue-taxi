var dataGridHis;

/**
 * 页面初始化
 */
$(function () {
	initGrid2();
	initSelectOrgan();
	dateFormat();
});

/**
 * 表格初始化
 */
function initGrid2() {
	var gridObj = {
		id: "dataGridHis",
        sAjaxSource: "OrganBill/GetCurOrganBillByQuery?billClass=1",
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无已处理账单信息"
        },
        columns: [
	        /*{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "billState", sTitle: "账单状态", sClass: "center", visible: false},
	        {mDataProp: "source", sTitle: "账单来源", sClass: "center", visible: false},
	        {mDataProp: "organId", sTitle: "机构Id", sClass: "center", visible: false},*/

	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 100,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    html += '<button type="button" class="SSbtn orange"  onclick="searchBillDetail(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>查看详情</button>';
                    html += '&nbsp; <button type="button" class="SSbtn orange" onclick="searchLog(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>日志</button>';
                    
                    return html;
                }
            },

	        {mDataProp: "id", sTitle: "账单编号", sClass: "center", sortable: true },
	        {mDataProp: "sourceName", sTitle: "账单来源", sClass: "center", sortable: true },
	        {mDataProp: "name", sTitle: "账单名称", sClass: "center", sortable: true },
	        {mDataProp: "shortName", sTitle: "机构", sClass: "center", sortable: true },
	        //{mDataProp: "billStateName", sTitle: "账单状态", sClass: "center", sortable: true },
	        {
				mDataProp : "billStateName",
				sTitle : "账单状态",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						var html = "";
	                    if (full.billState == '8') {//已作废
	                    	html += '<span class="font_red">' + full.billStateName + '</span>' ;
	                    } else {
	                    	html += '<span>' + full.billStateName + '</span>' ;
	                    }
						return html;
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "money", sTitle: "账单金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "operationTime", sTitle: "最后更新时间", sClass: "center", sortable: true },
	        {mDataProp: "createTime", sTitle: "账单生成时间", sClass: "center", sortable: true } 
        ]
    };
    
	dataGridHis = renderGrid(gridObj);
}

/**
 * 搜索下拉框
 */
function initSelectOrgan() {
	$("#organIdHis").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OrganBill/SelectOrganList",
			dataType : 'json',
			data : function(term, page) {
				$(".datetimepicker").hide();
				return {
					fullName: term
				};
			},
			results : function(data, page) {
				return {
					results: getDataVar(data)
				};
			}
		}
	});
}

/**
 * 对结果分组
 */
function getDataVar(data) {
	var datavar = [];
	var initials = "";
	var children = [];
	var dataend = [];
	for (var i=0;i<data.length;i++) {
		if (initials != data[i].initials) {
			
			if (dataend.children) {
				datavar.push(dataend);
			}

			initials = data[i].initials;
			children = [];
			children.push(data[i]);
			
			dataend = [];
			dataend.text = initials;
			dataend.disabled = "disabled";
			dataend.children = children;
		} else {
			children.push(data[i]);
			dataend.children = children;
		}

	}
	
	if (dataend.children) {
		datavar.push(dataend);
	}
	
	return datavar;
}

var isclear = false;
/**
 * 历史账单查询
 */
function searchHis() {
	
	$("#organIdHisExport").val($("#organIdHis").val());
	$("#startTimeHisExport").val($("#startTimeHis").val());
	$("#endTimeHisExport").val($("#endTimeHis").val());
	$("#billStateHisExport").val($("#billStateHis").val());
	
	var conditionArr = [
		{ "name": "organId", "value": $("#organIdHis").val() },
		{ "name": "startTime", "value": $("#startTimeHis").val() },
		{ "name": "endTime", "value": $("#endTimeHis").val() },
		{ "name": "billState", "value": $("#billStateHis").val() }
	];
	if (isclear) {
		dataGridHis.fnSearch(conditionArr,"暂无已处理账单信息");
	} else {
		dataGridHis.fnSearch(conditionArr);
	}
}

function dateFormat() {
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
 * 查看详情
 */
function searchBillDetail(id) {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganBill/BillDetail/" + id;
}

/**
 * 日志
 */
function searchLog(id) {
	var url = "OrganBill/GetOrganBillStateById/" + id + "?datetime=" + new Date().getTime();
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: url,
		data: null,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (json) {
			var html="";
			for(var i=0;i<json.length;i++) {
				html += '<li><div class="j_time">' + json[i].operationTime + '</div><div class="j_text"><b>' + json[i].billState + '</b></div></li>';
				if (json[i].comment) {
					html += '<li><div class="j_timeno"></div><div class="j_text">说明：' + json[i].comment + '</div></li>';
				} else {
					html += '<li><div class="j_timeno"></div><div class="j_text">' + json[i].comment + '</div></li>';
				}				
			}
			$("#jindu").html(html);
			$("#logDiv").show();
		}
	});
}

/**
 * 取消
 */
function canel() {
	$("#manualFormDiv").hide();
	$("#cancellationFormDiv").hide();
}

/**
 * 导出
 */
function exportExcelHis() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganBill/ExportData?organId="+$("#organIdHisExport").val()+"&billState="+$("#billStateHisExport").val()+"&startTime="+$("#startTimeHisExport").val()+"&endTime="+$("#endTimeHisExport").val()+"&billClass=1";
	
	$("#startTimeHis").blur();
	$("#endTimeHis").blur();
}

/**
 * 清空
 */
function clearParameter() {
	$("#organIdHis").select2("val","");
	$("#startTimeHis").val("");
	$("#endTimeHis").val("");
	$("#billStateHis").val("");

	isclear = true;
	searchHis();
	isclear = false;
}
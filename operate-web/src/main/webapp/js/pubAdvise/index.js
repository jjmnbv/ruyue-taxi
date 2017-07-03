var dataGrid;
var usertype;

/**
 * 页面初始化
 */
$(function() {
	initBtn();
	initGrid();
	
	dateFormat();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
		sAjaxSource: $("#baseUrl").val() + "PubAdimage/GetPubAdimageByQuery",
		iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无广告页信息"
        },
		columns: [
			{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
			{
				//自定义操作列
				mDataProp: "ZDY",
				sTitle: "操作",
				sClass: "center",
				sWidth: 150,
				bSeachable: false,
				sortable: false,
				mRender: function(data, type, full) {
					var html = '';
					if(full.isvalid == "1") {
						if(full.usestate == "0") {
							if(null != usertype && usertype != 1) {
								html += '<button type="button" class="SSbtn red" disabled="disabled" onclick="unuseable(' + "'" + full.id + "'" + ')"><i class="fa fa-paste"></i>禁用</button>';
							} else {
								html += '<button type="button" class="SSbtn red" onclick="unuseable(' + "'" + full.id + "'" + ')"><i class="fa fa-paste"></i>禁用</button>';
							}
						} else if(full.usestate == "1") {
							if(null != usertype && usertype != 1) {
								html += '<button type="button" class="SSbtn blue" disabled="disabled" onclick="useable(' + "'" + full.id + "'" + ')"><i class="fa fa-paste"></i>启用</button>';
							} else {
								html += '<button type="button" class="SSbtn blue" onclick="useable(' + "'" + full.id + "'" + ')"><i class="fa fa-paste"></i>启用</button>';
							}
						}
					}
					html += '&nbsp;';
					html += '<button type="button" class="SSbtn green_a" onClick="edit(' + "'" + full.id + "'" + ')"><i class="fa fa-paste"></i>修改</button>';
					return html;
				}
			},
			{mDataProp: "apptypeName", sTitle: "App类型", sClass: "center", sortable: true},
			{mDataProp: "name", sTitle: "名称", sClass: "center", sortable: true,
				mRender: function(data, type, full) {
					return '<a href="' + $("#baseUrl").val() + 'PubAdvise/GetPubAdviseByDetail/' + full.id + '" style="color:green;">' + full.name + '</a>';
				}
			},
			{mDataProp: "usetime", sTitle: "有效期", sClass: "center", sortable: true,
				mRender: function(data, type, full) {
					var html = full.starttime + '-' + full.endtime;
					return html;
				}
			},
			{mDataProp: "updatetime", sTitle: "更新时间", sClass: "center", sortable: true}
		],
		userQueryParam: [
			{"name": "imgtype", "value": "0"}
		]
	};
	dataGrid = renderGrid(gridObj);
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "apptype", "value": $("#apptype").val() },
		{ "name": "name", "value": $("#name").val() },
		{ "name": "starttime", "value": $("#startTime").val() },
		{ "name": "endtime", "value": $("#endTime").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 新增
 */
function add() {
	window.location.href = $("#baseUrl").val() + "PubAdvise/GetEditPubAdvisePage";
}

/**
 * 修改
 * @param id
 */
function edit(id) {
	window.location.href = $("#baseUrl").val() + "PubAdvise/GetEditPubAdvisePage?id=" + id;
}

/**
 * 禁用
 * @param id
 */
function unuseable(id) {
	editUse(id, "1");
}

/**
 * 启用
 * @param id
 */
function useable(id) {
	editUse(id, "0");
}

function editUse(id, usestate) {
	var data = { id: id, usestate: usestate };
	$.ajax({
		type: "POST",
		dataType: 'json',
		url:$("#baseUrl").val() + "PubAdimage/EditPubAdimageUsestate",
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

function dateFormat() {
	$('.searchDate').datetimepicker({
        format: "yyyy/mm/dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: true,
        clearBtn: true
    });
}

/**
 * 初始化按钮
 */
function initBtn() {
	usertype = $("#usertype").val();
	if(null != usertype && usertype != 1) {
		$("#addBtn").attr("dsiabled", "disabled");
	}
}


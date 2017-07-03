var dataGrid;
var usertype;

/**
 * 页面初始化
 */
$(function() {
	initBtn();
	
	initGrid();
	
	dateFormat();
	initCurversion();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
		sAjaxSource: $("#baseUrl").val() + "PubSysversion/GetPubSysversionByQuery",
		iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无版本说明信息"
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
					if(null != usertype && usertype != 1) {
						html += '<button type="button" class="SSbtn red" disabled="disabled" onclick="del(' + "'" + full.id + "'" + ')"><i class="fa fa-paste"></i>删除</button>';
					} else {
						html += '<button type="button" class="SSbtn red" onclick="del(' + "'" + full.id + "'" + ')"><i class="fa fa-paste"></i>删除</button>';
					}
					html += '&nbsp;';
					html += '<button type="button" class="SSbtn green_a" onClick="view(' + "'" + full.id + "'" + ')"><i class="fa fa-paste"></i>查看</button>';
					return html;
				}
			},
			{mDataProp: "platformtypeName", sTitle: "App类型", sClass: "center", sortable: true},
			{mDataProp: "curversion", sTitle: "版本号", sClass: "center", sortable: true},
			{mDataProp: "isForceUpdate", sTitle: "是否强制升级", sClass: "center", sortable: true,
				"mRender": function(data, type, full) {
					if(full.maxversionno > 0) {
						return "是";
					} else {
						return "否";
					}
				}
			},
			{mDataProp: "releasedate", sTitle: "版本发布日期", sClass: "center", sortable: true},
			{mDataProp: "systemtypeName", sTitle: "适用系统", sClass: "center", sortable: true},
			{mDataProp: "updatetime", sTitle: "更新时间", sClass: "center", sortable: true}
		]
	};
	dataGrid = renderGrid(gridObj);
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "platformtype", "value": $("#platformtype").val() },
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() },
		{ "name": "curversion", "value": $("#curversion").val() },
		{ "name": "systemtype", "value": $("#systemtype").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 新增
 */
function add() {
	window.location.href = $("#baseUrl").val() + "PubSysversion/GetEditPubSysversionPage";
}

/**
 * 修改
 * @param id
 */
function view(id) {
	window.location.href = $("#baseUrl").val() + "PubSysversion/GetEditPubSysversionPage?id=" + id;
}

/**
 * 删除
 * @param id
 */
function del(id) {
	var comfirmData={
		tittle:"提示",
		context:"是否删除该版本说明?",
		button_l:"否",
		button_r:"是",
		htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	Zconfirm(comfirmData, function() {
		deletePost(id);
	});
}
function deletePost(id) {
	var data = {id: id};
	$.post($("#baseUrl").val() + "PubSysversion/DeletePubSysversion", data, function (status) {
		if (status.ResultSign == "Successful") {
			var message = status.MessageKey == null ? status : status.MessageKey;
            toastr.success(message, "提示");
			dataGrid._fnReDraw();
		} else {
			var message = status.MessageKey == null ? status : status.MessageKey;
			toastr.error(message, "提示");
		}
	});
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
        forceParse: true,
        clearBtn: true
    });
}

function initCurversion() {
	$("#curversion").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : $("#baseUrl").val() + "PubSysversion/GetCurversionByList",
			dataType : 'json',
			data : function(term, page) {
				$(".datetimepicker").hide();
				return {
					curversion: term
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
 * 初始化按钮
 */
function initBtn() {
	usertype = $("#usertype").val();
	if(null != usertype && usertype != 1) {
		$("#addBtn").attr("disabled", "disabled");
	}
}
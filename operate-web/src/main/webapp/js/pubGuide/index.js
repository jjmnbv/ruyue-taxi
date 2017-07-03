var dataGrid;
var usertype;

/**
 * 页面初始化
 */
$(function() {
	initBtn();
	initGrid();
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
        	sEmptyTable: "暂无引导页信息"
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
						html += '<button type="button" class="SSbtn green_a" disabled="disabled" onClick="edit(' + "'" + full.id + "'" + ')"><i class="fa fa-paste"></i>修改</button>';
					} else {
						html += '<button type="button" class="SSbtn green_a" onClick="edit(' + "'" + full.id + "'" + ')"><i class="fa fa-paste"></i>修改</button>';
					}
					return html;
				}
			},
			{mDataProp: "apptypeName", sTitle: "App类型", sClass: "center", sortable: true},
			{mDataProp: "version", sTitle: "版本号", sClass: "center", sortable: true},
			{mDataProp: "updatetime", sTitle: "更新时间", sClass: "center", sortable: true}
		],
		userQueryParam: [
			{"name": "imgtype", "value": "1"}
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
		{ "name": "version", "value": $("#name").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 新增
 */
function add() {
	window.location.href = $("#baseUrl").val() + "PubGuide/GetEditPubGuidePage";
}

/**
 * 修改
 * @param id
 */
function edit(id) {
	window.location.href = $("#baseUrl").val() + "PubGuide/GetEditPubGuidePage?id=" + id;
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


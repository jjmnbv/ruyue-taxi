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
		sAjaxSource: $("#baseUrl").val() + "CompanyAgreement/GetCompanyAgreementByQuery",
		iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无服务车企协议"
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
						html += '&nbsp;';
						html += '<button type="button" class="SSbtn blue" disabled="disabled" onClick="edit(' + "'" + full.id + "'" + ')"><i class="fa fa-paste"></i>修改</button>';
					} else {
						html += '<button type="button" class="SSbtn red" onclick="del(' + "'" + full.id + "'" + ')"><i class="fa fa-paste"></i>删除</button>';
						html += '&nbsp;';
						html += '<button type="button" class="SSbtn blue" onClick="edit(' + "'" + full.id + "'" + ')"><i class="fa fa-paste"></i>修改</button>';
					}
					return html;
				}
			},
			{mDataProp: "leasecompanyName", sTitle: "客户名称", sClass: "center", sortable: true},
			{mDataProp: "shortname", sTitle: "协议名称", sClass: "center", sortable: true,
				mRender: function(data, type, full) {
					var html = '';
					html += '<a href="' + $("#baseUrl").val() + 'CompanyAgreement/GetCompanyAgreementByDetail/' + full.id + '" style="color:green;">' + full.shortname + '</a>';
					return html;
				}
			},
			{mDataProp: "updatetime", sTitle: "更新时间", sClass: "center", sortable: true},
		]
	};
	dataGrid = renderGrid(gridObj);
}

/**
 * 新增
 */
function add() {
	window.location.href = $("#baseUrl").val() + "CompanyAgreement/GetEditCompanyAgreementPage";
}

/**
 * 修改
 * @param id
 */
function edit(id) {
	window.location.href = $("#baseUrl").val() + "CompanyAgreement/GetEditCompanyAgreementPage?id=" + id;
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "leasecompanyName", "value": $("#leasecompanyName").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 删除
 * @param id
 */
function del(id) {
	var comfirmData={
		tittle:"提示",
		context:"是否删除该协议?",
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
	$.post($("#baseUrl").val() + "CompanyAgreement/DeleteCompanyAgreement", data, function (status) {
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

/**
 * 初始化按钮
 */
function initBtn() {
	usertype = $("#usertype").val();
	if(null != usertype && usertype != 1) {
		$("#addBtn").attr("disabled", "disabled");
	}
}
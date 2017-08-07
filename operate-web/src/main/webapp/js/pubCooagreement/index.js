var dataGrid;
var usertype;

/**
 * 页面初始化
 */
$(function() {
	initGrid();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
		sAjaxSource: $("#baseUrl").val() + "PubCooagreement/GetPubCooagreementByQuery",
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
			{mDataProp: "companyName", sTitle: "车企名称", sClass: "center", sortable: true},
			{mDataProp: "cooname", sTitle: "协议名称", sClass: "center", sortable: true,
				mRender: function(data, type, full) {
					var html = '';
					html += '<a href="' + $("#baseUrl").val() + 'PubCooagreement/DetailPubCooagreement/' + full.id + '" style="color:green;">' + full.cooname + '</a>';
					return html;
				}
			},
			{mDataProp: "servicetype", sTitle: "业务类型", sClass: "center", sortable: true,
				mRender: function(data, type, full) {
					var html = '';
					if(full.servicetype == 0){
						html += '<span>网约车</span>';
					}else{
						html += '<span>出租车</span>';
					}
					return html;
				}
			},
			{mDataProp: "operator", sTitle: "操作人", sClass: "center", sortable: true},
			{mDataProp: "updatetime", sTitle: "操作时间", sClass: "center", sortable: true,
				mRender: function(data, type, full) {
					return '<span>'+changeToDate(full.updatetime)+'</span>';
				}
			}
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
	var hours = "";
	var minutes = "";
	var second = "";
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
	if (myDate.getSeconds()<10){
		second = "0"+myDate.getSeconds();
	}else{
		second = myDate.getSeconds();
	}
	change += ":" + second;
	return change;
}

/**
 * 新增
 */
function add() {
	window.location.href = $("#baseUrl").val() + "PubCooagreement/Edit";
}

/**
 * 修改
 * @param id
 */
function edit(id) {
	window.location.href = $("#baseUrl").val() + "PubCooagreement/Edit?id=" + id;
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "queryCompanyname", "value": $("#queryCompanyname").val() },
		{ "name": "queryServicetype", "value": $("#queryServicetype").val() }
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
	$.post($("#baseUrl").val() + "PubCooagreement/DeletePubCooagreement", data, function (status) {
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

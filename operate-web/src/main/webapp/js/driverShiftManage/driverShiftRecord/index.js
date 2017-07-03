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
	$("#driverid").select2({
        placeholder: "当班司机",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
			url: $("#baseUrl").val() + "PubDriver/listPubDriverBySelect",
			dataType: 'json',
			data: function (term, page) {
				$(".datetimepicker").hide();

				var param = {
					queryText: term,
					vehicletype:"1",
					jobstatus:"0"
				}
				return param;
			},
            results: function (data, page) {
                return { results: data };
            }
        }
    });

}

/**
 * 表格初始化
 */
function manualOrderdataGrid() {
	var gridObj = {
		id: "grid",
        sAjaxSource: $("#baseUrl").val() + "/PubVehicle/bind/list",
        iLeftColumn: 1,
        userQueryParam: [{name: "vehicletype", value: "1"}],
        scrollX: true,
		language: {
			sEmptyTable: "暂无服务车辆信息"
		},
        columns: [
			//自定义操作列
			{mDataProp: "rownum", sClass: "center", sTitle: "序号", sWidth: 150, bSearchable: false, sortable: false},
            {mDataProp: "platenoStr1", sTitle: "车牌号", sClass: "center", sortable: true,
				mRender:function(data, type, full){
					var htmlArr = [];
					htmlArr.push("<span style='cursor:pointer;color: green;text-decoration:underline ' onclick=toDetail(\'"+full.id+"\',\'"+full.platenoStr+"\')>");
					htmlArr.push(full.platenoStr);
					htmlArr.push("</span>");
					htmlArr.push("</a>");
					return htmlArr.join("");

			}},
            {mDataProp: "cityStr", sTitle: "登记城市", sClass: "center", sortable: true },
			{mDataProp: "driverState", sTitle: "当前状态", sClass: "center", sortable: true },
            {mDataProp: "bindInfoStr", sTitle: "绑定司机", sClass: "center", sortable: true,
				mRender:function (data) {
					if (data != null&& data!="/") {
						var index = findIndex(data,"、",3);
						if (index >= 0) {
							return showToolTips(data,index);
						} else {
							return data;
						}
					} else {
						return "/";
					}
				}}
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}


/**
 * 查询
 */
function search() {
	var conditionArr = [

		{ "name": "queryPlateNo", "value": $("#plateNo").val()},
		{ "name": "driverState", "value": $("#driverState").val()},
		{ "name": "driverid", "value": !!$("#driverid").select2('data')?$("#driverid").select2('data').id:"" },
		{ "name": "vehicletype", "value": "1"}
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 车辆操作记录明细
 * @param id
 */
function toDetail(id,plateNo){
	window.location= $("#baseUrl").val() + "driverShift/record/vehicledetail?vehicleId="+id+"&plateNo="+plateNo;
}

/**
 * 长度显示控制
 */
function findIndex(str,cha,num) {
	var x=str.indexOf(cha);
	for(var i=0;i<num;i++) {
		if (x == -1) {
			return x;
		}
		x=str.indexOf(cha,x+1);
	}
	return x;
}

/**
 * 清空
 */
function clearOptions(){
	$("#plateNo").val("");
	$("#driverid").select2("val", "");
	$("#driverState option:first").prop("selected", 'selected');
	search();
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

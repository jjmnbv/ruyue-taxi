var dataGrid;
var dataList = [];
var defaultCity = {
//	id : 'A229B89C-F480-4F26-8A6F-29078B9E5B3B',
//	name : '锦州市',
//	markid : '0000020004'
};

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	initSelectCity();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "LeShiftRules/GetRules",
        iLeftColumn: 2,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无交接班规则信息"
        },
        userQueryParam:[],
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
                    var html = "";
                    dataList.push(full);
                    html += '<button type="button" class="SSbtn green_a" onclick="edit(' +"'"+ full.id+"'"   +');"><i class="fa fa-paste"></i>修改</button>';
                    return html;
                }
            },
            {mDataProp: "cityname", sTitle: "城市名称", sClass: "center", sortable: true },
            {mDataProp: "autoshifttime", sTitle: "自主交班时限（分钟）", sClass: "center", sortable: true },
            {mDataProp: "manualshifttime", sTitle: "人工指派时限（分钟）", sClass: "center", sortable: true },
            {mDataProp: "updatetime", sTitle: "更新时间", sClass: "center", sortable: true ,"mRender" : function(data,type,full){
            	var time = timeStamp2String(data,"/");
            	time = time.substring(0,time.length - 3);  //去掉秒
            	return time;
            }},
            
        ]
    };
	dataGrid = renderGrid(gridObj);
}

function initSelectCity() {
	$("#city").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			params: { //select2的contentType要这样写.否则无法使用requestBody接收参数
				contentType: "application/json; charset=utf-8"
			},
			type : 'POST',
			dataType : 'json',
			url : "LeShiftRules/GetCityListForSelect",
			data : function(term, page) {
            	//参数必须格式化
            	var data = {
    					sSearch : term
    			};
            	return JSON.stringify(data);
			},
			results : function(data, page) {
				var result = {
					results : []
				}
				if(data.status == 0){
					result.results = data.list
				}
				return result;
			}
		}
	});
}

/**
 * 查询
 */
function search() {
    dataList = [];
	var conditionArr = [
		{ "name": "city", "value": $("#city").val() },
		{ "name": "cityname", "value": $("#city").select2('data')?$("#city").select2('data').text:null},
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 新增
 */
function add() {
	$("#window").show();	
	$("input.error").attr("class","valid");
	$(".error").remove();
	$("#manualLabel").css("top","168px");
	//切换到默认城市
	changeCity1($("#cityname").parent(),defaultCity.markid,changeCityCallBack);
	$("#cityid").val(defaultCity.id);
	$("#cityname").val(defaultCity.name);
//	windowContent.active.changeCity(windowContent.active.opt.defaultCity);
	$("#title").text("新增交接班规则");
	$("#id").val("");
	$("#autoshifttime").val("");
	$("#manualshifttime").val("");
	$("#cityname").attr("disabled",false);
//	$("#cityname").next().attr("disabled",false);
}

/**
 * 修改
 * @param {} id
 */
function edit(id) {
	$("#window").show();	
	var selectedVal;
	for(var i in dataList){
		if(id == dataList[i].id){
			selectedVal = dataList[i];
			break;
		}
	}
	$("input.error").attr("class","valid");
	$(".error").remove();
	$("#manualLabel").css("top","168px");
	//切换到指定城市
	changeCity1($("#cityname").parent(),selectedVal.markid,changeCityCallBack);
	$("#cityid").val(selectedVal.city);
	$("#cityname").val(selectedVal.cityname.substring(0,selectedVal.cityname.indexOf("(")));
//	windowContent.active.changeCity(selectedVal.cityname.substring(0,selectedVal.cityname.indexOf("(")));
	$("#title").text("修改交接班规则");
	$("#id").val(selectedVal.id);
	$("#autoshifttime").val(selectedVal.autoshifttime);
	$("#manualshifttime").val(selectedVal.manualshifttime);
	$("#cityname").attr("disabled",true);
//	$("#cityname").next().attr("disabled",true);
}

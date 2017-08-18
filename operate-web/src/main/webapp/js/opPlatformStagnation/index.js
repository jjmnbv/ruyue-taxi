var base = document.getElementsByTagName("base")[0].getAttribute("href");
var dataGrid;
var usertype;

/**
 * 页面初始化
 */
$(function () {
	initGrid();

	initSelectCity();
	initData();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: $("#baseUrl").val() + "OpPlatformStagnation/Page",
        iLeftColumn: 1,
        scrollX: true,
        columns: [

            {mDataProp: "id", sTitle: "id", sClass: "center", visible: false},
            {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 150,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html="";
					html += '&nbsp; <button type="button" class="SSbtn green_a" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
					html += '&nbsp; <button type="button" class="SSbtn green_a" onclick="del(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>删除</button>';
                    return html;
                }
            },
	        {mDataProp: "city", sTitle: "驻点城市", sClass: "center", visible: true },
	        {mDataProp: "responsible", sTitle: "机构负责人", sClass: "center", visible: true },
	        {mDataProp: "responsibleway", sTitle: "负责人电话", sClass: "center", visible: true },
	        {mDataProp: "postcode", sTitle: "邮政编码", sClass: "center", visible: true },
	        {mDataProp: "", sTitle: "通信地址", sClass: "center", visible: true,
                mRender : function(data, type, full) {
                    return '<span>'+full.contactaddresscity+full.contactaddress+'</span>';
                }
            },
            {mDataProp: "parentcompany", sTitle: "母公司名称", sClass: "center", visible: true },
            {mDataProp: "", sTitle: "母公司通信地址", sClass: "center", visible: true,
                mRender : function(data, type, full) {
                                return '<span>'+full.parentadcity+full.parentad+'</span>';
                            }
            }

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
			url : $("#baseUrl").val() + "PubInfoApi/GetSearchCitySelect",
			dataType : 'json',
			data : function(term, page) {
				return {
					cityName: term
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
 * 初始化界面
 */
function initData() {
	$("#label1").html("");
	$("#label2").html("");
	$("#perhourDiv").hide();
}







/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "city", "value": $("#city").val() },
		{ "name": "responsible", "value": $("#responsible").val() },
		{ "name": "responsibleway", "value": $("#responsibleway").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 清空查询条件
 */
function clearSearch(){
	$("#city").val("");
	$("#responsible").val("");
	$("#responsibleway").val("");
	$(".select2-search-choice-close").mousedown();
	search();
}



/**
 * 新增
 */
function add() {
	window.location.href=$("#baseUrl").val() + "OpPlatformStagnation/Edit";
}

/**
 * 修改
 * @param {} id
 */
function edit(id) {
	window.location.href=$("#baseUrl").val() + "OpPlatformStagnation/Edit?id=" + id;
}

/**
 * 删除
 * @param {} id
 */
function del(id) {
    var comfirmData={
        tittle:"提示",
        context:"确定删除？",
        button_l:"否",
        button_r:"是",
        click: "deletePost('" + id + "')",
        htmltex:"<input type='hidden' placeholder='添加的html'> "
    };
    Zconfirm(comfirmData);
}
function deletePost(id){
    var data = {id: id};
    $.post("OpPlatformStagnation/Delete", data, function (status) {
        var message = status.message == null ? status : status.message;
        if (status.status == "success") {
            toastr.success(message, "提示");
            dataGrid._fnReDraw();
        } else {
            toastr.error(message, "提示");
        }
    });
}



/**
 * 取消
 */
function canel() {
	$("#editFormDiv").hide();
	$("#disableFormDiv").hide();
}
/**
 * 下载模板
 * */
function download(){
    window.location.href = base+"OpPlatformStagnation/DownLoad";
}

function exportExcel(){
    var city=$("#city").val();
    var responsible=$("#responsible").val();
    var responsibleway=$("#responsibleway").val();
    window.location.href = base+"OpPlatformStagnation/ExportData?city="+city+"&responsible="+responsible+"&responsibleway="+responsibleway;
}

function importExcel(){
    $("#importExcelDiv").show();
}


function canel1(){
    $("#importExcelDiv1").hide();

}
$('#importfile').fileupload({
    url:"OpPlatformStagnation/ImportExcel",
    dataType: 'json',
    done: function(e, data) {
        if (data.result.ResultSign == "Successful") {
            var message = data.result.MessageKey == null ? data : data.result.MessageKey;
            $("#importExcelDiv1").show();
            $("#importExcelDiv2").html("以下导入失败：<br>"+message);
            dataGrid._fnReDraw();
        }else if(data.result.ResultSign == "Error"){
            var message = data.result.MessageKey == null ? data : data.result.MessageKey;
            toastr.error(message, "提示");
            dataGrid._fnReDraw();
        }else{
            var message = data.result.MessageKey == null ? data : data.result.MessageKey;
            $("#importExcelDiv1").show();
            $("#importExcelDiv2").html("导入成功");
            dataGrid._fnReDraw();
        }
    }
});


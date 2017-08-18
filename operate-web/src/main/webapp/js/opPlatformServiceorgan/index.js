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
        sAjaxSource: $("#baseUrl").val() + "OpPlatformServiceorgan/Page",
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
	        {mDataProp: "servicename", sTitle: "服务机构名称", sClass: "center", visible: true },
	        {mDataProp: "serviceno", sTitle: "服务代码", sClass: "center", visible: true },
	        {mDataProp: "responsiblename", sTitle: "机构负责人", sClass: "center", visible: true },
	        {mDataProp: "responsiblephone", sTitle: "负责人电话", sClass: "center", visible: true },
	        {mDataProp: "managername", sTitle: "机构管理人", sClass: "center", visible: true },
	        {mDataProp: "managerphone", sTitle: "管理人电话", sClass: "center", visible: true },
            {mDataProp: "contactphone", sTitle: "紧急联系电话", sClass: "center", visible: true },
            {mDataProp: "createdate", sTitle: "机构设立日期", sClass: "center", visible: true },

        ]
    };
    
	dataGrid = renderGrid(gridObj);
}


function initSelectCity() {
	$("#address").select2({
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
		{ "name": "servicename", "value": $("#servicename").val() },
		{ "name": "address", "value": $("#address").val() },
		{ "name": "responsiblename", "value": $("#responsiblename").val() },
		{ "name": "responsiblephone", "value": $("#responsiblephone").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 清空查询条件
 */
function clearSearch(){
	$("#servicename").val("");
	$("#address").val("");
	$("#responsiblename").val("");
	$("#responsiblephone").val("");
	$(".select2-search-choice-close").mousedown();
	search();
}



/**
 * 新增
 */
function add() {
	window.location.href=$("#baseUrl").val() + "OpPlatformServiceorgan/Edit";
}

/**
 * 修改
 * @param {} id
 */
function edit(id) {
	window.location.href=$("#baseUrl").val() + "OpPlatformServiceorgan/Edit?id=" + id;
}

/**
 * 删除
 * @param {} id
 */
function del(id) {
    var comfirmData={
        tittle:"提示",
        context:"确定删除该机构？",
        button_l:"否",
        button_r:"是",
        click: "deletePost('" + id + "')",
        htmltex:"<input type='hidden' placeholder='添加的html'> "
    };
    Zconfirm(comfirmData);
}
function deletePost(id){
    var data = {id: id};
    $.post("OpPlatformServiceorgan/Delete", data, function (status) {
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
    window.location.href = base+"OpPlatformServiceorgan/DownLoad";
}

function exportExcel(){
    var servicename=$("#servicename").val();
    var address=$("#address").val();
    var responsiblename=$("#responsiblename").val();
    var responsiblephone=$("#responsiblephone").val();

    window.location.href = base+"OpPlatformServiceorgan/ExportData?servicename="+servicename+"&address="+address+"&responsiblename="+responsiblename+"&responsiblephone="+responsiblephone;
}

function importExcel(){
    $("#importExcelDiv").show();
}


function canel1(){
    $("#importExcelDiv1").hide();

}
$('#importfile').fileupload({
    url:"OpPlatformServiceorgan/ImportExcel",
    dataType: 'json',
    done: function(e, data) {
        if (data.result.ResultSign == "Successful") {
            var message = data.result.MessageKey == null ? data : data.result.MessageKey;
            //toastr.warning(message, "提示");
            //Zalert("提示","以下车辆导入失败：<br>"+message);
            $("#importExcelDiv1").show();
            $("#importExcelDiv2").html("以下车辆导入失败：<br>"+message);
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


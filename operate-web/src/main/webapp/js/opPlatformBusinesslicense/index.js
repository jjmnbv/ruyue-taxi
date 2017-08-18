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
        sAjaxSource: $("#baseUrl").val() + "OpPlatformBusinesslicense/Page",
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
	        {mDataProp: "addressName", sTitle: "经营许可地", sClass: "center", visible: true },
	        {mDataProp: "certificate", sTitle: "经营许可证号", sClass: "center", visible: true },
	        {mDataProp: "", sTitle: "经营区域", sClass: "center", visible: true ,
                mRender : function(data, type, full) {
	            console.log(full.scopes);
	            var operationareaHtml="";
	            $.each(full.scopes,function (i,scope) {
	                if(i>0)
                     operationareaHtml+=",";
                    operationareaHtml+=scope.operationarea;

                });
                return '<span>'+operationareaHtml+'</span>';
                }
            },
	        {mDataProp: "organization", sTitle: "发证机构", sClass: "center", visible: true },
	        {mDataProp: "", sTitle: "有效期限", sClass: "center", visible: true,
                mRender : function(data, type, full) {
                    return '<span>'+full.startdate.substring(0,10)+" ~ "+full.stopdate.substring(0,10)+'</span>';
                }
            },
            {mDataProp: "certifydate", sTitle: "初次发证日期", sClass: "center", visible: true,
                mRender : function(data, type, full) {
                    return '<span>'+full.certifydate.substring(0,10)+'</span>';
                }
            },
            {mDataProp: "", sTitle: "证照状态", sClass: "center", visible: true,
                mRender : function(data, type, full) {
                                if(full.state==1){
                                    return '<span>有效</span>';
                                }else {
                                    return '<span>无效</span>';
                                }

                            }
            }

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
		{ "name": "address", "value": $("#address").val() },
		{ "name": "certificate", "value": $("#certificate").val() },
		{ "name": "state", "value": $("#state").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 清空查询条件
 */
function clearSearch(){
	$("#address").val("");
	$("#certificate").val("");
	$("#state").val("");
	$(".select2-search-choice-close").mousedown();
	search();
}



/**
 * 新增
 */
function add() {
	window.location.href=$("#baseUrl").val() + "OpPlatformBusinesslicense/Edit";
}

/**
 * 修改
 * @param {} id
 */
function edit(id) {
	window.location.href=$("#baseUrl").val() + "OpPlatformBusinesslicense/Edit?id=" + id;
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
    $.post("OpPlatformBusinesslicense/Delete", data, function (status) {
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
    window.location.href = base+"OpPlatformBusinesslicense/DownLoad";
}

function exportExcel(){
    var address=$("#address").val();
    var certificate=$("#certificate").val();
    var state=$("#state").val();
    window.location.href = base+"OpPlatformBusinesslicense/ExportData?address="+address+"&certificate="+certificate+"&state="+state;
}

function importExcel(){
    $("#importExcelDiv").show();
}


function canel1(){
    $("#importExcelDiv1").hide();

}
$('#importfile').fileupload({
    url:"OpPlatformBusinesslicense/ImportExcel",
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


var base = document.getElementsByTagName("base")[0].getAttribute("href");
var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	$(".close").click(function(){
		$(".select2-drop-active").css("display","none");
	})
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "PubVehInsur/GetPubVehInsurByQuery",
        iLeftColumn: 3,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        language: {
        	sEmptyTable: "暂无车辆保险信息"
        },
        columns: [
	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 180,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                	var html = "";
        			html += '<button type="button" class="SSbtn green_q" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
                    html += '&nbsp; <button type="button" class="SSbtn red_q"  onclick="del(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 删 除</button>';
                    return html;
                }
            },
	        {mDataProp: "fullplateno", sTitle: "车牌号码", sClass: "center", sortable: true },
	        {mDataProp: "engineid", sTitle: "发动机号", sClass: "center", sortable: false},
	        {mDataProp: "vin", sTitle: "车辆识别码", sClass: "center", sortable: false },
	        {mDataProp: "insurcom", sTitle: "保险公司", sClass: "center", sortable: true },
	        {mDataProp: "insurtypename", sTitle: "保险类型", sClass: "center", sortable: true},
	        {mDataProp: "insurnum", sTitle: "保险号", sClass: "center", sortable: true},
	        {mDataProp: "insurcount", sTitle: "保险金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "insurvalidate", sTitle: "保险有效期", sClass: "center", sortable: true}
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "queryVehicleType", "value": $("#queryVehicleType").val() },
		{ "name": "queryinsurType", "value": $("#queryinsurType").val() },
		{ "name": "queryPlateNo", "value": $("#queryPlateNo").val() },
		{ "name": "queryVin", "value": $("#queryVin").val() },
		{ "name": "queryInsurNum", "value": $("#queryInsurNum").val() },
		{ "name": "queryInsurCom", "value": $("#queryInsurCom").val() }		
	];
	dataGrid.fnSearch(conditionArr);
	$("#queryVehicleTypes").val($("#queryVehicleType").val());
	$("#queryinsurTypes").val($("#queryinsurType").val());
	$("#queryPlateNos").val($("#queryPlateNo").val());
	$("#queryVins").val($("#queryVin").val());
	$("#queryInsurNums").val($("#queryInsurNum").val());
	$("#queryInsurComs").val($("#queryInsurCom").val());
}

/**
 * 修改
 * */
function edit(id){
	window.location.href=base+"PubVehInsur/EditIndex?id="+id;
};

/**
 * 删除
 * */
function del(id){
	var comfirmData={
			tittle:"提示",
			context:"车辆保险-删除后不可恢复，确定要删除吗？",
			button_l:"否",
			button_r:"是",
			click: "deletePost('" + id + "')",
			htmltex:"<input type='hidden' placeholder='添加的html'> "
		};
		Zconfirm(comfirmData);
};

function deletePost(id){
	var data = {id: id};
	$.post("PubVehInsur/DeletePubVehInsur", data, function (status) {
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
 * 取消
 */
function canel() {
	$("#editFormDiv").hide();
	$("#importExcelDiv").hide();
}


function exportData(){
	$("#queryVehicleType").val("");
	$("#queryinsurType").val("");
	$("#queryPlateNo").val("");
	$("#queryVin").val("");
	$("#queryInsurNum").val("");
	$("#queryInsurCom").val("");
	window.location.href = base+"PubDriver/ExportData?queryKeyword="+queryKeyword+"&queryWorkStatus="+queryWorkStatus+"&queryCity="+queryCity+"&queryJobStatus="+queryJobStatus+"&queryIdEntityType="+queryIdEntityType+"&queryServiceOrg="+queryServiceOrg
							+"&queryVehicleType="+queryVehicleType+"&queryBoundState="+queryBoundState+"&queryJobNum="+queryJobNum+"&belongleasecompanyQuery="+belongleasecompanyQuery;
}
/**
 * 新增的清空功能
 * 
 */
function emptys(){
	$("#queryVehicleType").val("");
	$("#queryinsurType").val("");
	$("#queryPlateNo").val("");
	$("#queryVin").val("");
	$("#queryInsurNum").val("");
	$("#queryInsurCom").val("");
	search();
}

function add(){
	window.location.href=base+"PubVehInsur/AddIndex";
}

/**
 * 下载模板
 * */
function download(){
	window.location.href = base+"PubVehInsur/DownLoad";
}

/**
 * 导出数据
 */
function exportExcel(){
	
	var queryVehicleType = $("#queryVehicleTypes").val();
	var queryinsurType = $("#queryinsurTypes").val();
	var queryPlateNo = $("#queryPlateNos").val();
	var queryVin = $("#queryVins").val();
	var queryInsurNum = $("#queryInsurNums").val();
	var queryInsurCom = $("#queryInsurComs").val();
	
	window.location.href = base+"PubVehInsur/ExportData?queryVehicleType="+queryVehicleType+"&queryinsurType="+queryinsurType+"&queryPlateNo="+queryPlateNo+"&queryVin="+queryVin+"&queryInsurNum="+queryInsurNum
							+"&queryInsurCom="+queryInsurCom;
}

/**
 * 
 */
function importExcel(){
	$("#importExcelDiv").show();
}

function canel1(){
	$("#importExcelDiv1").hide();
}

/**
 * 上传文件导入
 */
$('#importfile').fileupload({
	url:"PubVehInsur/ImportExcel",
    dataType: 'json',
    done: function(e, data) {
    	if (data.result.ResultSign == "Successful") {
			var message = data.result.MessageKey == null ? data : data.result.MessageKey;
        	$("#importExcelDiv1").show();
        	$("#importExcelDiv2").html("以下车辆保险导入失败：<br>"+message);
        	dataGrid._fnReDraw();
		}else if(data.result.ResultSign == "Error"){
			var message = data.result.MessageKey == null ? data : data.result.MessageKey;
        	toastr.error(message, "提示");
        	dataGrid._fnReDraw();
		}else{
			$("#importExcelDiv1").show();
        	$("#importExcelDiv2").html("导入成功");
        	dataGrid._fnReDraw();
		}
    }
});

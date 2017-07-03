var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	initSelectQueryCity();
	initSelectQueryShortName();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "OrgOrgan/GetOrgOrganByQuery",
        iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        language: {
        	sEmptyTable: "暂无任何客户信息"
        },
        columns: [
//	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 120,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    html += '<button type="button" class="SSbtn green_q" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
                    html += '&nbsp; <button type="button" class="SSbtn yellow_q"  onclick="resetPassword(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 重置密码</button>';
                    html += '&nbsp; <button type="button" class="SSbtn grey_q"  onclick="creditChangeRecord(' +"'"+ full.id + "','" + full.shortName +"'"+ ')"><i class="fa fa-times"></i> 信用额度</button>';
                    return html;
                }
            },
	        {mDataProp: "citycaption", sTitle: "所属城市", sClass: "center", sortable: true },
	        {mDataProp: "shortName", sTitle: "机构简称", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		var html = "";
	        		html+= '<a href="OrgOrgan/Detailed?id='+full.id+'" style="color:red;">'+""+full.shortName+""+'</a>';
	        		return html;
	        	}
	        },
	        {mDataProp: "fullName", sTitle: "机构全称", sClass: "center", sortable: true },
	        {mDataProp: "account", sTitle: "机构账号", sClass: "center", sortable: true },
	        {mDataProp: "customertype", sTitle: "客户类型", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.customertype == '1'){
	        			html+='<font>渠道客户</font>';
	        		}else if(full.customertype == '0'){
	        			html+='<font>非渠道客户</font>';
	        		}
	        		return html;
	        	}
	        },
	        {mDataProp: "cooperationStatus", sTitle: "合作状态", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.cooperationStatus == '1'){
	        			html+='<font>正常</font>';
	        		}else{
	        			html+='<font color="red">停止</font>';
	        		}
	        		return html;
	        	}
	        },
	        {mDataProp: "contacts", sTitle: "联系人", sClass: "center", sortable: true },
	        {mDataProp: "phone", sTitle: "联系方式", sClass: "center", sortable: true },
	        {mDataProp: "address", sTitle: "公司详细地址", sClass: "center", sortable: true },
	        {mDataProp: "billType", sTitle: "结算方式", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.billType == '季结'){
	        			html+='<font color="green">季结</font>';
	        		}else{
	        			html+='<font>月结</font>';
	        		}
	        		return html;
	        	}
	        },
	        {mDataProp: "billDateShow", sTitle: "账单生成日", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}
/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "queryShortName", "value": $("#queryShortName").val() },
		{ "name": "queryCity", "value": $("#queryCity").val() },
		{ "name": "cooperationStatus", "value": $("#cooperationStatus").val() },
		{ "name": "queryCustomertype", "value": $("#queryCustomertype").val() }
	];
	dataGrid.fnSearch(conditionArr);
	$("#queryShortNames").val($("#queryShortName").val());
	$("#queryCitys").val($("#queryCity").val());
	$("#cooperationStatuss").val($("#cooperationStatus").val());
	$("#queryCustomertypes").val($("#queryCustomertype").val());
}
/***
 * 
 * 初始化  搜索下拉 
 */
function initSelectQueryShortName() {
	$("#queryShortName").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OrgOrgan/GetOrgOrganShortName",
			dataType : 'json',
			data : function(term, page) {
				return {
					queryShortName : term
				};
			},
			results : function(data, page) {
				return {
					results : data
				};
			}
		}
	});
}
function initSelectQueryCity() {
	$("#queryCity").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OrgOrgan/GetOrgOrganCity",
			dataType : 'json',
			data : function(term, page) {
				return {
					queryCity : term
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

//  新增
function add(){
	window.location.href=base+"OrgOrgan/AddIndex";
}
//  修改
function edit(id){
	window.location.href=base+"OrgOrgan/AddIndex?id="+id;
}
//  重置密码
function resetPassword(id){
	var data = {id: id};
	$.post("OrgOrgan/ResetPassword", data, function (status) {
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
// 导出
function exportExcel(){
	var queryShortName = $("#queryShortNames").val();
	var queryCity = $("#queryCitys").val();
	var cooperationStatus = $("#cooperationStatuss").val();
	var queryCustomertype = $("#queryCustomertypes").val();
	window.location.href=base+"OrgOrgan/ExportData?queryShortName="+queryShortName+"&queryCity="+queryCity+"&cooperationStatus="+cooperationStatus+"&queryCustomertype="+queryCustomertype;
}

/**
 * 清空
 */
function clearParameter() {
	$("#queryShortName").select2("val","");
	$("#queryCity").select2("val","");
	$("#cooperationStatus").val("");
	$("#queryCustomertype").val("");
	search();
}

/**
 * 信用额度变更记录
 */
function creditChangeRecord(organId,shortName) {
	window.location.href = base + "OrgOrgan/OrganCreditRecordDetail?organId=" + organId + "&shortName=" + shortName;
}
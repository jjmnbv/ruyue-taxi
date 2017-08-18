var dataGrid;
/**
 * 页面初始化
 */
$(function () {
	initGrid();
	});
function initGrid() {
	var aaid = $("#aaid").val();
	var gridObj = {
		id: "dataGrid",
		iLeftColumn: 1,
		scrollX: true,
        sAjaxSource: "PubPremiumRule/GetHistoryData?aaid="+$("#aaid").val(),
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "premiumruleid", sTitle: "premiumruleid", sClass: "center", visible: false},
	        {
		        "mDataProp": "ZDY",
	            "sClass": "center",
	            "sTitle": "操作",
	            "sWidth": 100,
	            "bSearchable": false,
	            "sortable": false,
	            "mRender": function (data, type, full) {
	                var html = "";
	                  html += '<button type="button" class="SSbtn red_q"  onclick="check(' +"'"+ full.id +"'"+","+"'"+full.ruletype+"'"+","+"'"+full.operationtype+"'"+')"><i class="fa fa-paste"></i>查看</button>';
	                  return html;
	              }
	          },
	        {mDataProp: "ruletype", sTitle: "规则类型", sClass: "center", sortable: true },
	        {mDataProp: "operationtype", sTitle: "操作类型", sClass: "center", sortable: true },
	        {mDataProp: "creater", sTitle: "操作人", sClass: "center", sortable: true },
	        {mDataProp: "updatetime", sTitle: "操作时间", sClass: "center", sortable: true }
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}
function check(id,ruletype,operationtype){
	window.location.href = base + "PubPremiumRule/Historydetail?id="+id+"&ruletype="+ruletype+"&ruleId="+$("#aaid").val()+"&operationtype="+operationtype;
}
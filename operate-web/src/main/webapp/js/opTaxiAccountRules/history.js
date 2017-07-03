var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
});

/**
 * 表格初始化
 */
function initGrid() {
	
	var gridObj = {
		id: "dataGrid",
		userQueryParam:[
		    {name: "id", value: $("#ruleid").val()},
		],
        sAjaxSource: $("#baseUrl").val() + "OpTaxiAccountRules/SearchHistory",
        columns: [
            {mDataProp: "operatetype", sTitle: "操作类型", sClass: "center", sortable: true ,"mRender" : function(data,type,full){
            	return full.operatetype == '0' ? "启用操作" :
		            		full.operatetype == '1' ? "禁用操作" :
	            			full.operatetype == '2' ? "新增操作" :
            				full.operatetype == '3' ? "修改操作" : "";
            }},
            {mDataProp: "createtime", sTitle: "操作时间", sClass: "center", sortable: true ,"mRender" : function(data,type,full){
            	var time = timeStamp2String(data,"/");
            	time = time.substring(0,time.length - 3);  //去掉秒
            	return time;
            }},
	        {mDataProp: "creater", sTitle: "操作人", sClass: "center", sortable: true },
	        {mDataProp: "startprice", sTitle: "起租价(元)", sClass: "center", sortable: true },
	        {mDataProp: "startrange", sTitle: "起租里程(公里)", sClass: "center", sortable: true },
	        {mDataProp: "renewalprice", sTitle: "续租价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "surcharge", sTitle: "附加费（元）", sClass: "center", sortable: true },
	        {mDataProp: "standardrange", sTitle: "标准里程（公里）", sClass: "center", sortable: true },
	        {mDataProp: "emptytravelrate", sTitle: "空驶费率", sClass: "center", sortable: true },
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}


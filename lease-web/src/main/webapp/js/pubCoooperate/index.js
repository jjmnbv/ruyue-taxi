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
        sAjaxSource: "PubCoooperate/GetPubCoooperateByQuery",
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
                    if(full.servicetype == 0){
                    	html += '&nbsp; <button type="button" class="SSbtn grey_q"  onclick="see(' +"'"+ full.id +"','"+full.servicetype+"'"+ ')"><i class="fa fa-times"></i> 资源管理</button>';
					}else{
						html += '&nbsp; <button type="button" class="SSbtn grey_q"  onclick="resourceInformation(' +"'"+ full.id + "','"+full.companyid+"','"+full.leasecompanyid+"','"+full.servicetype+"'"+')"><i class="fa fa-times"></i> 资源信息</button>';
					}
                    return html;																			
                }
            },
	        {mDataProp: "coono", sTitle: "合作编号", sClass: "center", sortable: true },
	        {mDataProp: "companyName", sTitle: "战略伙伴", sClass: "center", sortable: true },
	        {mDataProp: "servicetype", sTitle: "加盟业务", sClass: "center", sortable: true,
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
	        {mDataProp: "allCount", sTitle: "开放资源数(个)", sClass: "center", sortable: true },
	        {mDataProp: "availableCount", sTitle: "投运资源数(个)", sClass: "center", sortable: true,
	        	mRender: function(data, type, full) {
					var html = '';
					if(full.allCount > full.availableCount){
						html += '<font color="red">'+full.availableCount+'</font>';
					}else{
						html += '<font>'+full.availableCount+'</font>';
					}
					return html;
				}	
	        }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}
/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "queryCoono", "value": $("#queryCoono").val() },
		{ "name": "queryCompanyname", "value": $("#queryCompanyname").val() },
		{ "name": "queryServicetype", "value": $("#queryServicetype").val() }
	];
	dataGrid.fnSearch(conditionArr);
}
/**
 * 清空
 */
function clearParameter() {
	$("#queryCoono").val("");
	$("#queryCompanyname").val("");
	$("#queryServicetype").val("");
	search();
}
/** 资源信息*/
function resourceInformation(id,companyid,leasecompanyid,servicetype){
	window.location.href = base+"PubCoooperate/ResourceInformation?id="+id+"&companyid="+companyid+"&leasecompanyid="+leasecompanyid+"&servicetype="+servicetype;
}
//查看
function see(id,servicetype){
	window.location.href=base+"PubCoooperate/DriverInformation?id="+id;
}
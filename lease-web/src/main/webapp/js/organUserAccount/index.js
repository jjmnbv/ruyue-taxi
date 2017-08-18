var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();

	initSelectOrgan();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "OrganUserAccount/GetOrganUserAccountInfoByQuery",
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无个人账户信息"
        },
        columns: [
	        /*{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},*/

	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 100,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    if (full.dealCount > 0) {
                    	html += '<button type="button" class="SSbtn pink"  onclick="searchDetail(' +"'"+ full.id + "','" + full.account +"'"+ ')"><i class="fa fa-times"></i>交易明细</button>';
                    }
                    if (html != "") {
                    	if (full.balanceCount > 0) {
                    		html += '&nbsp; ';
                    	}
                    }
                    if (full.balanceCount > 0) {
                    	html += '<button type="button" class="SSbtn pink"  onclick="searchBalanceDetail(' +"'"+ full.id + "','" + full.account +"'"+ ')"><i class="fa fa-times"></i>余额明细</button>';
                    }
                    if (html != "") {
                    	if (full.couponCount > 0) {
                    		html += '&nbsp; ';
                    	}
                    }
                    if (full.couponCount > 0) { 
                    	html += '<button type="button" class="SSbtn pink"  onclick="searchCouponDetail(' +"'"+ full.id + "','" + full.account +"'"+ ')"><i class="fa fa-times"></i>抵用券明细</button>';
                    }
                    return html;
                }
            },
	        {mDataProp: "account", sTitle: "账号", sClass: "center", sortable: true },
	        {mDataProp: "sex", sTitle: "性别", sClass: "center", sortable: true },
	        {mDataProp: "balance", sTitle: "账户余额(元)", sClass: "center", sortable: true },
	        {mDataProp: "validCoupon", sTitle: "抵用券(张)", sClass: "center", sortable: true },
	        {mDataProp: "shortName", sTitle: "机构名称", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

function initSelectOrgan() {
	$("#organId").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OrganUserAccount/GetExistOrganList",
			dataType : 'json',
			data : function(term, page) {
				return {
					shortName: term
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});
	
	$("#userId").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OrganUserAccount/GetExistUserList",
			dataType : 'json',
			data : function(term, page) {
				return {
					nameAccount: term
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

var isclear = false;
/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "organId", "value": $("#organId").val() },
		{ "name": "userId", "value": $("#userId").val() }
	];
	if (isclear) {
		dataGrid.fnSearch(conditionArr,"暂无个人账户信息");
	} else {
		dataGrid.fnSearch(conditionArr);
	}
}


/**
 * 交易明细
 * @param {} id
 */
function searchDetail(id,account) {

	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganUserAccount/AccountDetail?userId=" + id +"&account=" + account;
}

/**
 * 余额明细
 * @param {} id
 */
function searchBalanceDetail(id,account) {

	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganUserAccount/BalanceDetail?userId=" + id +"&account=" + account;
}

/**
 * 抵用券明细
 */
function searchCouponDetail(id,account){
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganUserAccount/CouponDetail?userid=" + id +"&account=" + account;
}

/**
 * 清空
 */
function clearParameter() {
	$("#organId").select2("val","");
	$("#userId").select2("val","");
	
	isclear = true;
	search();
	isclear = false;
}


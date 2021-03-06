var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();

	initSelectUser();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "OpUserAccount/GetOpUserAccountByQuery",
        iLeftColumn: 1,
        scrollX: true,
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
//                    html += '<button type="button" class="SSbtn pink"  onclick="searchDetail(' +"'"+ full.id + "','" + full.account + "','" + full.nickname +"'"+ ')"><i class="fa fa-times"></i>查看往来明细</button>';
//                    return html;
//                    var html = "";
                    if (full.dealCount > 0) {
                    	html += '<button type="button" class="SSbtn pink"  onclick="searchDetail(' +"'"+ full.id + "','" + full.account + "','" + full.nickname +"'"+ ')"><i class="fa fa-times"></i>交易明细</button>';
                    }
                    if (html != "") {
                    	if (full.balanceCount > 0) {
                    		html += '&nbsp; ';
                    	}
                    }
                    if (full.balanceCount > 0) {
                    	html += '<button type="button" class="SSbtn pink"  onclick="searchBalanceDetail(' +"'"+ full.id + "','" + full.account + "','" + full.nickname +"'"+ ')"><i class="fa fa-times"></i>余额明细</button>';
                    }
                    return html;
                }
            },

	        //{mDataProp: "nickname", sTitle: "昵称", sClass: "center", sortable: true },
	        {
				mDataProp : "nickname",
				sTitle : "称呼",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (full.nickname != null && full.nickname != '') {
						return '<span class="font_green">' + full.nickname + '</span>';
					} else {
						return '<span class="font_green">-</span>';
					}
				}
			},
	        {mDataProp: "account", sTitle: "账号", sClass: "center", sortable: true },
	        {mDataProp: "sex", sTitle: "性别", sClass: "center", sortable: true },
	        {mDataProp: "balance", sTitle: "账户余额(元)", sClass: "center", sortable: true },
	        //{mDataProp: "registertime", sTitle: "注册时间", sClass: "center", sortable: true }
	        {
				mDataProp : "registertime",
				sTitle : "注册时间",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						return changeToDate(data);
					} else {
						return "";
					}
				}
			}
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

function initSelectUser() {
	$("#userId").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OpUserAccount/GetExistUserList",
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

/**
 * 毫秒转日期
 * 
 * @param data
 * @returns {String}
 */
function changeToDate(data) {
	var myDate = new Date(data);
	var month = "";
	var date = "";
	var hours = "";
	var minutes = "";
	var seconds = "";
	var change = "";
	change += myDate.getFullYear() + "/";

	if (myDate.getMonth() < 9) {
		month = "0" + (myDate.getMonth() + 1);
	} else {
		month = (myDate.getMonth() + 1);
	}
	change += month + "/";
	if (myDate.getDate() < 10) {
		date = "0" + myDate.getDate();
	} else {
		date = myDate.getDate();
	}
	change += date;
	if (myDate.getHours() < 10) {
		hours = "0" + myDate.getHours();
	} else {
		hours = myDate.getHours();
	}
	change += " " + hours;
	if (myDate.getMinutes() < 10) {
		minutes = "0" + myDate.getMinutes();
	} else {
		minutes = myDate.getMinutes();
	}
	change += ":" + minutes;
	if (myDate.getSeconds() < 10) {
		seconds = "0" + myDate.getSeconds();
	} else {
		seconds = myDate.getSeconds();
	}
	change += ":" + seconds;
	
	return change;
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "userId", "value": $("#userId").val() }
	];
	dataGrid.fnSearch(conditionArr);
}


/**
 * 交易明细
 * @param {} id
 */
function searchDetail(id,account,nickName) {

	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OpUserAccount/AccountDetail?userId=" + id +"&account=" + account + "&nickName=" + nickName+"&detaills=jiaoyi";
}

/**
 * 余额明细
 * @param {} id
 */
function searchBalanceDetail(id,account,nickName) {

	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OpUserAccount/AccountDetail?userId=" + id +"&account=" + account + "&nickName=" + nickName+"&detaills=yue";
}

//取消
function cancel(){
	$("#userId").select2("val","");
	search();
}

var dataGrid;
var dataGridHis;

/**
 * 页面初始化
 */
$(function () {
	//initTab();
	initGrid();
	//initGrid2();
});

/**
 * tab初始化
 */
function initTab() {
	 $(".tabmenu>li").click(function() {
	        $(this).addClass("on").siblings().removeClass("on");
	        var n=$(this).index();
	        $(".tabbox>li:eq("+n+")").show().siblings().hide();
	 });
}

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "UserRefund/GetPeUserRefundByQuery?refundstatus=0",
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无退款申请记录"
        },
        columns: [
	        /*{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "userId", sTitle: "UserId", sClass: "center", visible: false},*/
	        
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
                    html += '<button type="button" class="SSbtn orange"  onclick="confirmAccount(' +"'"+ full.id +"','" + full.userId + "','" + full.amount + "'"+ ')"><i class="fa fa-paste"></i>确认退款</button>';
                    return html;
                }
            },

	        //{mDataProp: "userName", sTitle: "退款对象", sClass: "center", sortable: true },
	        {
				mDataProp : "userName",
				sTitle : "退款对象",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						return '<span class="font_green">' + full.userName + '</span>';
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "amount", sTitle: "退款金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "remark", sTitle: "退款原因", sClass: "center", sortable: true, 
	        	mRender : function(data, type, full) {
					return showToolTips(full.remark,40);
				}
	        },
	        {mDataProp: "commitTime", sTitle: "提交时间", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 表格初始化
 */
function initGrid2() {
	var gridObj = {
		id: "dataGridHis",
        sAjaxSource: "UserRefund/GetPeUserRefundByQuery?refundstatus=1",
        columns: [
	        //{mDataProp: "userName", sTitle: "退款对象", sClass: "center", sortable: true },
	        {
				mDataProp : "userName",
				sTitle : "退款对象",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						return '<span class="font_green">' + full.userName + '</span>';
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "amount", sTitle: "退款金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "commitTime", sTitle: "提交时间", sClass: "center", sortable: true },
	        {mDataProp: "updateTime", sTitle: "处理时间", sClass: "center", sortable: true }
        ]
    };
    
	dataGridHis = renderGrid(gridObj);
}

/**
 * 确认退款
 */
function confirmAccount(id,userId,amount) {
	
	var comfirmData={
			tittle:"提示",
			context:"确认退款后款项将退到该用户账户，确定提交？",
			button_l:"取消",
			button_r:"确定",
			click: "confirmPost(" + "'" + id +"','" + userId + "','" + amount +"'" +")",
			htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	Zconfirm(comfirmData);
}

function confirmPost(id,userId,amount) {
	var data = {id: id,userId: userId,amount: amount};
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: "UserRefund/ConfirmRefund",
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				dataGrid._fnReDraw();
				//dataGridHis._fnReDraw();
			} else if (status.ResultSign == "Error") {
				toastr.error("该退款记录已被处理，请确认", "提示");
			    return;
			} else {
				return;
			}	
		}
	});

}


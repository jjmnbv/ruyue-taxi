var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	initView();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "OrganBill/GetOrgOrderByQuery/" + $("#id").val(),
        /*iLeftColumn: 2,*/
        scrollX: true,
        language: {
        	sEmptyTable: "没有查询到未结算订单数据"
        },
        columns: [     
	        {mDataProp: "ordertype", sTitle: "类型", sClass: "center", sortable: true },
	        //{mDataProp: "orderno", sTitle: "订单号", sClass: "center", sortable: true },
	        {
                //自定义操作列
                "mDataProp": "ddh",
                "sClass": "center",
                "sTitle": "订单号",
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    html += '<a class="breadcrumb font_green" href="OrderManage/OrgOrderDetailIndex?orderno=' + full.orderno + '">' + full.orderno +'</a>';
                    return html;
                }
            },
	        //{mDataProp: "orderstatus", sTitle: "订单状态", sClass: "center", sortable: true },
	        {
				mDataProp : "orderstatus",
				sTitle : "订单状态",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						var html = "";
	                    if (full.orderstatus == '已复核') {//已复核
	                    	html += '<span class="font_red">' + full.orderstatus + '</span>' ;
	                    } else {
	                    	html += '<span>' + full.orderstatus + '</span>' ;
	                    }
						return html;
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "orderamount", sTitle: "订单金额(元)", sClass: "center", sortable: true },
	        {mDataProp: "mileage", sTitle: "里程(公里)", sClass: "center", sortable: true, 
	        	"mRender": function(data, type, full) {
	        		return (full.mileage/1000).toFixed(1);
	        	}
	        },
	        {mDataProp: "JFSC", sTitle: "计费时长(分钟)", sClass: "center", sortable: true, 
	        	mRender: function(data, type, full) {
	        		if(null != full.pricecopy) {
	        			var pricecopy = JSON.parse(full.pricecopy);
	        			if(pricecopy.timetype == 1) { //低速用时
	        				return pricecopy.slowtimes;
	        			} else if(pricecopy.timetype == 0) { //总用时
	        				var starttime = new Date(full.starttime);
	        				var endtime = new Date(full.endtime);
	        				return Math.ceil((endtime - starttime)/(1000*60));
	        			}
	        		}
	        		return "0";
	        	}
	        },
	        {mDataProp: "userid", sTitle: "下单人", sClass: "center", sortable: true },
	        {mDataProp: "passengers", sTitle: "乘车人", sClass: "center", sortable: true },
	        {mDataProp: "driverid", sTitle: "司机信息", sClass: "center", sortable: true },
	        //{mDataProp: "endtime", sTitle: "订单结束时间", sClass: "center", sortable: true }
	        {
				mDataProp : "endtime",
				sTitle : "订单结束时间",
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


/**
 * 时间处理
 * 
 * @param data
 * @returns {String}
 */
function changeTime(data) {
	var integer = parseInt(data/60);
	var remainder = data%60;
	
	var num = "";
	if (integer == 0 && remainder == 0) {
		return "0分钟";
	}

	if (integer != 0) {
		num += integer + "小时";
	}
	if (remainder != 0) {
		num += remainder + "分钟";
	}
	
	return num;
}

/**
 * 毫秒转日期
 * 
 * @param data
 * @returns {String}
 */
function changeToDate(data) {
	var myDate = new Date(data.replace(/-/g,"/"));
	var month = "";
	var date = "";
	var hours = "";
	var minutes = "";
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
	return change;
}

function initView() {
	$("#dataGrid_wrapper .col-sm-6").remove();
}

/**
 * 导出数据
 */
function exportExcel() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganBill/GetOrgOrderListExport?billsId="+$("#id").val()+"&billName="+$("#billName").val()+"&shortName="+$("#shortName").val()+"&remark="+$("#remark").val()+"&money="+$("#money").val();
}
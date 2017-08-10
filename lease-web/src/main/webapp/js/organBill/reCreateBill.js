var dataGrid;
var dataGridPreview;
//选择订单
var map = {};
var checknum = 0;
var checkamount = parseFloat("0").toFixed(1);
var pagemap = {};
/**
 * 页面初始化
 */
$(function () {
	initGrid();
	validateForm();
	
	map = {};
	checknum = 0;
	checkamount = parseFloat("0").toFixed(1);
	pagemap = {};
	$("#checkNum").text(checknum);
	$("#checkAmount").text(checkamount);
});

/**
 * 获取当前页的页数
 */
function getPage() {
	var page;
	var lis = $("#dataGrid_paginate>ul>li");
	for (var i=0;i<lis.length;i++) {
		if (lis[i].className == "paginate_button active") {
			page = lis[i].children[0].innerHTML;
			break;
		}
	}
	return page;
}

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "OrganBill/GetOrgOrderByQuery/" + $("#id").val(),
        userHandle: function(oSettings, result) {
        	for(var index in result.aaData) {
        		var orderResult = result.aaData[index];
	        	if(map.hasOwnProperty(orderResult.orderno)) {
	        		$("#checkOrder" + orderResult.orderno).attr("checked","true");
	        	}
        	}
        	
        	//var page = $("li.paginate_button.active")[0].children[0].innerHTML;
        	var page = getPage();
        	if (page) {
        		$("#checkAll").prop("disabled",false);
        	} else {
        		$("#checkAll").prop("disabled",true);
        	}
        	if (pagemap.hasOwnProperty(page)) {
        		$("#checkAll").prop("checked",true);
        	} else {
        		$("#checkAll").prop("checked",false);
        	}
        },
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "没有查询到未结算订单数据"
        },
        columns: [ 
            {mDataProp: "orderno", sTitle: "订单号", sClass: "center", visible: false },      
            {
				// 自定义操作列
				"mDataProp" : "ZDY",
				"sClass" : "center",
				"sTitle" : "<input type='checkbox' id='checkAll' name='checkAll' onclick='checkAllHander(this)'></input>全选",
				"sWidth" : 70,
				"bSearchable" : false,
				"sortable" : false,
				"mRender" : function(data, type, full) {
					var orderamount = 0;
					if (full.orderstatus == '7') {
						orderamount = full.orderamount;
					} else if (full.orderstatus == '8') {
						orderamount = full.cancelamount;
					}
					var html = "";
					html += '<input type="checkbox" id="checkOrder' + full.orderno + '" name="checkOrder" value="'
							+ full.orderno + '" amountvalue="' + orderamount
							+ '" onclick="onClickHander(this)"></input>';
					return html;
				}
			},
			//{mDataProp: "endtime", sTitle: "订单结束时间", sClass: "center", sortable: true },
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
			},
	        {mDataProp: "ordertype", sTitle: "订单类型", sClass: "center", sortable: true },
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
	        /*{
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
			},*/
            {mDataProp: "expensetype", sTitle: "费用类型", sClass: "center", sortable: true,
            	mRender : function(data, type, full) {
					if (data != null) {
						if (data == 1) {
							return "行程服务";
						} else if (data == 2) {
							return "取消处罚";
						}
					} else {
						return "";
					}
				}
            },
	        {mDataProp: "orderamount", sTitle: "订单金额(元)", sClass: "center", sortable: true, 
            	mRender : function(data, type, full) {
					if (data != null) {
						if (full.orderstatus == '7') {
							return full.orderamount;
						} else if (full.orderstatus == '8') {
							return "/";
						}
					} else {
						return "/";
					}
				}
	        },
	        {mDataProp: "mileage", sTitle: "里程(公里)", sClass: "center", sortable: true, 
	        	"mRender": function(data, type, full) {
	        		if (data != null) {
						if (full.orderstatus == '7') {
							return (full.mileage/1000).toFixed(1);
						} else if (full.orderstatus == '8') {
							return "/";
						}
					} else {
						return "/";
					}
	        	}
	        },
	        {mDataProp: "cancelamount", sTitle: "取消费用(元)", sClass: "center", sortable: true,
            	mRender : function(data, type, full) {
					if (data != null) {
						if (full.orderstatus == '7') {
							return "/";
						} else if (full.orderstatus == '8') {
							return full.cancelamount;
						}
					} else {
						return "/";
					}
				}
            },
	        {mDataProp: "userid", sTitle: "下单人", sClass: "center", sortable: true },
	        {mDataProp: "passengers", sTitle: "乘车人", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

function checkAllHander(obj) {
	var a = document.getElementsByTagName("input");
	var tempId = "";
	//var page = $("li.paginate_button.active")[0].children[0].innerHTML;
	var page = getPage();
	if (obj.checked) {
		for(var i = 0;i < a.length; i++) {//i=3
			if(a[i].name == "checkOrder" && a[i].type == "checkbox" && a[i].checked == false) {
				a[i].checked = true;
				tempId = a[i].value;
				map[tempId] = tempId;
				checknum++;
				checkamount = (checkamount*10 + parseFloat(a[i].attributes[4].value)*10)/10;
			}
		}
		
		pagemap[page] = page;
	} else {
		for(var i = 0;i < a.length; i++) {
			if(a[i].name == "checkOrder" && a[i].type == "checkbox" && a[i].checked == true) {
				a[i].checked = false;
				tempId = a[i].value;
				delete map[tempId];
				checknum--;
				checkamount = (checkamount*10 - parseFloat(a[i].attributes[4].value)*10)/10;
			}
		}
		
		delete pagemap[page];
	}
	$("#checkNum").text(checknum);
	$("#checkAmount").text(checkamount.toFixed(1));
}

function onClickHander(obj) {
	var tempId = "";
	if (obj.checked) {
		tempId = obj.value;
		map[tempId] = tempId;
		checknum++;
		checkamount = (checkamount*10 + parseFloat($(obj).attr("amountvalue"))*10)/10;
	} else {
		tempId = obj.value;
		delete map[tempId];
		checknum--;
		checkamount = (checkamount*10 - parseFloat($(obj).attr("amountvalue"))*10)/10;
		
		if ($("#checkAll").prop("checked")) {
			$("#checkAll").prop("checked",false);
			//var page = $("li.paginate_button.active")[0].children[0].innerHTML;
			var page = getPage();
			delete pagemap[page];
		}
	}
	$("#checkNum").text(checknum);
	$("#checkAmount").text(checkamount.toFixed(1));
}

/**
 * 表单校验
 */
function validateForm() {
	$("#formname").validate({
		rules: {
			name: {required: true, maxlength: 20},
			remark: {maxlength: 30}
		},
		messages: {
			name: {required: "请填写账单名称", maxlength: "最大长度不能超过20个字符"},
			remark: {maxlength: "最大长度不能超过30个字符"}
		}
	})
}

/**
 * 预览
 */
function searchPreview() {
	var form = $("#formname");
	if(!form.valid()) return;
	
	if (haspro(map)) {
		var orderId="";
		for ( var pro in map) {
			if (pro) {
				orderId += pro + ",";
			}
		}
		orderId = orderId.substring(0,orderId.lastIndexOf(","));
		
		$("#previewFormDiv").show();
		$("#billName").text($("#name").val());
		$("#billRemark").text($("#remark").val());
		$("#billMoney").text($("#checkAmount").text());
		if (!dataGridPreview) {
			initGridPreview(orderId);
		} else {
			var conditionArr = [
			    { "name": "orderNo", "value": orderId }
			];
			dataGridPreview.fnSearch(conditionArr);
		}

	} else {
		toastr.error("请至少勾选一条订单预览", "提示");
	}
}

function haspro(map) {
	if (!map) {
		return false;
	}
	for ( var pro in map) {
		if (!pro) {
			return false;
		}
		return true;
	}
}

/**
 * 预览表格初始化
 */
function initGridPreview(orderId) {
	var gridObj = {
		id: "dataGridPreview",
        sAjaxSource: "OrganBill/GetOrgOrderById/" + orderId,
        /*iLeftColumn: 1,*/
        scrollX: true,
        language: {
        	sEmptyTable: "没有查询到未结算订单数据"
        },
        columns: [     
			{mDataProp: "endtime", sTitle: "订单结束时间", sClass: "center", visible: false },
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
			},
	        {mDataProp: "ordertype", sTitle: "订单类型", sClass: "center", sortable: true },
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
	        /*{
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
			},*/
            {mDataProp: "expensetype", sTitle: "费用类型", sClass: "center", sortable: true,
            	mRender : function(data, type, full) {
					if (data != null) {
						if (data == 1) {
							return "行程服务";
						} else if (data == 2) {
							return "取消处罚";
						}
					} else {
						return "";
					}
				}
            },
	        {mDataProp: "orderamount", sTitle: "订单金额(元)", sClass: "center", sortable: true, 
            	mRender : function(data, type, full) {
					if (data != null) {
						if (full.orderstatus == '7') {
							return full.orderamount;
						} else if (full.orderstatus == '8') {
							return "/";
						}
					} else {
						return "/";
					}
				}
	        },
	        {mDataProp: "mileage", sTitle: "里程(公里)", sClass: "center", sortable: true, 
	        	"mRender": function(data, type, full) {
	        		if (data != null) {
						if (full.orderstatus == '7') {
							return (full.mileage/1000).toFixed(1);
						} else if (full.orderstatus == '8') {
							return "/";
						}
					} else {
						return "/";
					}
	        	}
	        },
	        {mDataProp: "cancelamount", sTitle: "取消费用(元)", sClass: "center", sortable: true,
            	mRender : function(data, type, full) {
					if (data != null) {
						if (full.orderstatus == '7') {
							return "/";
						} else if (full.orderstatus == '8') {
							return full.cancelamount;
						}
					} else {
						return "/";
					}
				}
            },
	        {mDataProp: "userid", sTitle: "下单人", sClass: "center", sortable: true },
	        {mDataProp: "passengers", sTitle: "乘车人", sClass: "center", sortable: true }
        ]
    };
    
	dataGridPreview = renderGrid(gridObj);
}

/**
 * 生成账单
 */
function save() {
	var form = $("#formname");
	if(!form.valid()) return;
	
	if (haspro(map)) {
		var orderId="";
		for ( var pro in map) {
			if (pro) {
				orderId += pro + ",";
			}
		}
		orderId = orderId.substring(0,orderId.lastIndexOf(","));
		
		var data = {
				id: $("#id").val(),
				name: $("#name").val(),
				remark: $("#remark").val(),
				money: $("#checkAmount").text(),
				organId: $("#organId").val(),
				source: $("#source").val(),
				orderId: orderId
				};
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: "OrganBill/ReCreateOrganbill",
			data: JSON.stringify(data),
			contentType: 'application/json; charset=utf-8',
			async: false,
			success: function (status) {
				if (status.ResultSign == "Successful") {
					var message = status.MessageKey == null ? status : status.MessageKey;
					toastr.options.onHidden = function() {
	            		window.location.href=$("#baseUrl").val()+"OrganBill/Index";
	            	}
	            	toastr.success(message, "提示");
				} else {
					
				}	
			}
		});
		
	} else {
		toastr.error("请至少勾选一条订单预览", "提示");
	}
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

var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	
	validateForm();
	buttonFormat();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "FinancialManagement/GetOrgOrderByQuery/" + $("#billsId").val() + "?organId=" + $("#organId").val(),
        iDisplayLength: 20,
        columns: [
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
                    html += '<a class="detailHref" href="OrgIndex/Details?bill=bill&id=' + full.orderno + '&billsId=' + $("#billsId").val() + '">' + full.orderno +'</a>';
                    return html;
                }
            },
	        {mDataProp: "userid", sTitle: "下单人信息", sClass: "center", sortable: true },
	        {mDataProp: "deptname", sTitle: "下单人部门", sClass: "center", sortable: true },
	        {mDataProp: "passengers", sTitle: "乘车人", sClass: "center", sortable: true },
	        //{mDataProp: "starttime", sTitle: "行程时间", sClass: "center", sortable: true },
	        //{mDataProp: "endtime", sTitle: "行程时间", sClass: "center", sortable: true },
	        {
				  "mDataProp": "xcsj",
				  "sClass": "center",
				  "sTitle": "行程时间",
				  "bSearchable": false,
				  "sortable": false,
				  "mRender": function (data, type, full) {
					  if (full.orderstatus == '7') {
						  var starttime = full.starttime;
						  var endtime = full.endtime;
						  var xcsj = "";
						  if (starttime) {
							  xcsj += "起：" + changeToDate(starttime) + "<br>";
						  }
						  if (endtime) {
							  xcsj += "终：" + changeToDate(endtime);
						  }
						  return xcsj;
					  } else {
						  return "/";
					  }
				  }
            },
	        //{mDataProp: "onaddress", sTitle: "行程起止", sClass: "center", sortable: true },
	        //{mDataProp: "offaddress", sTitle: "行程起止", sClass: "center", sortable: true },
	        {
				  "mDataProp": "xcqz",
				  "sClass": "center",
				  "sTitle": "行程起止",
				  "bSearchable": false,
				  "sortable": false,
				  "mRender": function (data, type, full) {
					  if (full.orderstatus == '7') {
						  var onaddress = full.onaddress;
						  var offaddress = full.offaddress;
						  var xcqz = "";
						  if (onaddress) {
							  xcqz += "起：" + showToolTips(onaddress,3) + "<br>";
						  }
						  if (offaddress) {
							  xcqz += "止：" + showToolTips(offaddress,3);
						  }
						  return xcqz;
					  } else {
						  return "/";
					  }
				  }
            },
	        {mDataProp: "orderamount", sTitle: "订单金额（元）", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
            		  if (full.orderstatus == '7') {
            			  return full.orderamount;
            		  } else {
            			  return "/";
            		  }
				 }
	        },
	        {mDataProp: "cancelamount", sTitle: "取消费用（元）", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
            		if (data != null) {
            			if (full.orderstatus == '8') {
            				return full.cancelamount;
            			} else {
            				return "/";
            			}
            		} else {
            			return "/";
            		}
				 }
	        },
	        {mDataProp: "vehiclessubjecttype", sTitle: "用车事由", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
					if (data != null) {
						return data;
					} else {
						return "/";
					}
				  }
	        },
	        {mDataProp: "vehiclessubject", sTitle: "事由说明", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
	        		if (data != null) {
						return showToolTips(data,4);
					} else {
						return "/";
					}
				  }
	        },
	        {mDataProp: "tripremark", sTitle: "行程备注", sClass: "center", sortable: true, 
	        	"mRender": function (data, type, full) {
					if (data != null) {
						return showToolTips(full.tripremark,4);
					} else {
						return "/";
					}
				}
	        }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
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

/**
 * 表单校验
 */
function validateForm() {
	$("#billForm").validate({
		rules: {
			comment:{required: true, maxlength: 100}
		},
		messages: {
			comment: {required: "请输入退回账单备注", maxlength: "最大长度不能超过100个字符"}
		}
	})
}

/**
 * 核对/去支付 按钮初始化
 */
function buttonFormat() {
	var billState = $("#billState").val();
	if (billState == "3") {
		$("#billButton").html("核对");
	} else if (billState == "4") {
		$("#billButton").html("去支付");
	} else {
		$("#billButton").hide();
	}
}

function back() {
	window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "FinancialManagement/Index?index=2";
}

$("#billButton").click(function(event){
	var billState = $("#billState").val();
	if (billState == "3") {
		$(".bill").show();
		$(window.parent.document).find(".pop_index").show();
	    
	    $('input:radio:first').attr('checked', 'checked');
	    radioSet();
	    $("#comment").val("");
		
		var billForm = $("#billForm").validate();
		billForm.resetForm();
		billForm.reset();
	    event.preventDefault();
	} else if (billState == "4") {
		var money = $("#money").val();
		var message = "确定支付账单<span class='font-red'>" + money + "元</span>？";
		
		var actualBalance = 0.0;
		var couponamount = 0.0;
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: "FinancialManagement/GetActualBalance?leasesCompanyId=" + $("#leasesCompanyId").val() + "&organId=" + $("#organId").val() + "&datetime=" + new Date().getTime(),
			data: null,
			contentType: 'application/json; charset=utf-8',
			async: false,
			success: function (json) {
				actualBalance = json.actualBalance;
				couponamount = json.couponamount;
			}
		});
		// 如果余额+抵用劵 不够支付 订单，则 不显示 支付提示信息；如果 够 支付，则显示 支付提示信息
		if (actualBalance < money && (actualBalance + couponamount >= money)) {
			message += "<br><span style='color:#888;'>(余额支付" + actualBalance.toFixed(1) + "元，抵用券支付" + (money - actualBalance).toFixed(1) + "元)</span>";
		}

		var comfirmData={
				tittle:"支付账单",
				context:message,
				button_l:"取消",
				button_r:"确定",
				click: "confirm('"+money+"','"+actualBalance+"','"+couponamount+"')",
				htmltex:"<input type='hidden' placeholder='添加的html'> "
		};
		confirmBill(comfirmData);
		
	    event.preventDefault();
	}
});

function confirm(money,actualBalance,couponamount) {
	
	if(parseFloat(actualBalance) + parseFloat(couponamount) >= parseFloat(money)) {

		confirmAccount();
		
	} else {
		$(".pop_box").hide();
		$(window.parent.document).find(".pop_index").hide();
		//toastr.error("账户余额不足，请充值", "提示");
		var comfirmData={
				tittle:"支付账单",
				context:"账户余额不足，请先充值，再进行支付",
				button_l:"取消",
				button_r:"确定",
				click: "",
				htmltex:"<input type='hidden' placeholder='添加的html'> "
		};
		confirmYuE(comfirmData);
	}
}

//confirm封装
function confirmYuE(comfirmData){
	var defaultValue=
	{   title:"支付账单",
		context:"",
		button_l:"取消",
		button_r:"确定",
		click:"",
		htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	if(comfirmData.constructor==String)
	{
		defaultValue.context=comfirmData || defaultValue.context;
	}
	else if(comfirmData.constructor==Object)
	{
		defaultValue.title=comfirmData.title || defaultValue.title;
		defaultValue.context=comfirmData.context || defaultValue.context;
		defaultValue.button_l=comfirmData.button_l || defaultValue.button_l;
		defaultValue.button_r=comfirmData.button_r || defaultValue.button_r;
		defaultValue.click=comfirmData.click || defaultValue.click;
		defaultValue.htmltex=comfirmData.htmltex || defaultValue.htmltex;
	}
	
	if ($("#yuebuzuDiv").length == 0) {
		var html='<div class="pop_box" id="yuebuzuDiv" style="display: block;"><div class="pop"><div class="head">'+defaultValue.title+'<img src="content/img/btn_close.png" alt="关闭" class="close"></div><div class="con_c"> '+defaultValue.context+'</div><br>'+defaultValue.htmltex+'<div class="foot"><button class="btn_red close"  id="button_r">'+defaultValue.button_r+'</button></div></div></div> ';
		$("body").append(html);
	} else {
		$("#yuebuzuDiv").show();
	}
	
	$(window.parent.document).find(".pop_index").show();
	//$("#button_r").live('click', function () { callback();})
}

/**
 * 支付
 */
function confirmAccount(){
	var data = {id: $("#billsId").val(),
			    leasesCompanyId: $("#leasesCompanyId").val(),
			    money: $("#money").val(),
			    organId: $("#organId").val(),
			    name: $("#name").val()
			   };
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: "FinancialManagement/ConfirmAccount",
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
				toastr.options.onHidden = function() {
	            	window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "FinancialManagement/BillDetail/" + $("#billsId").val();
            	}
				toastr.success(message, "提示");
				$("#billDiv").hide();
            	$(window.parent.document).find(".pop_index").hide();
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
				toastr.error(message, "提示");
				$("#billDiv").hide();
				$(window.parent.document).find(".pop_index").hide();
			}	
		}
	});
}

//confirm封装
function confirmBill(comfirmData){
	var defaultValue=
	{   title:"支付账单",
		context:"",
		button_l:"取消",
		button_r:"确定",
		click:"",
		htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	if(comfirmData.constructor==String)
	{
		defaultValue.context=comfirmData || defaultValue.context;
	}
	else if(comfirmData.constructor==Object)
	{
		defaultValue.title=comfirmData.title || defaultValue.title;
		defaultValue.context=comfirmData.context || defaultValue.context;
		defaultValue.button_l=comfirmData.button_l || defaultValue.button_l;
		defaultValue.button_r=comfirmData.button_r || defaultValue.button_r;
		defaultValue.click=comfirmData.click || defaultValue.click;
		defaultValue.htmltex=comfirmData.htmltex || defaultValue.htmltex;
	}
	
	if ($("#billDiv").length == 0) {
		var html='<div class="pop_box" id="billDiv" style="display: block;"><div class="pop"><div class="head">'+defaultValue.title+'<img src="content/img/btn_close.png" alt="关闭" class="close"></div><div class="con_c"> '+defaultValue.context+'</div><br>'+defaultValue.htmltex+'<div class="foot"><button class="btn_red"  id="button_r" onclick="'+defaultValue.click+'">'+defaultValue.button_r+'</button><button class="btn_grey close">'+defaultValue.button_l+'</button></div></div></div> ';
		$("body").append(html);
	} else {
		$("#billDiv").show();
	}
	
	$(window.parent.document).find(".pop_index").show();
	//$("#button_r").live('click', function () { callback();})
}


$(".billvalue").click(function(event){
	radioSet();
	$("#comment").val("");
	
	var billForm = $("#billForm").validate();
	billForm.resetForm();
	billForm.reset();
});

/**
 * 通过账单/退回账单 显示控制
 */
function radioSet() {
	var billValue = "";
	var obj = document.getElementsByName("bill");
	for(i=0;i<obj.length;i++) {
		if(obj[i].checked) {
			billValue = obj[i].value;
		}
	}
	
	if (billValue=="0") {
		$("#comment").hide();
	} else if (billValue=="1") {
		$("#comment").show();
	}
}

/**
 * 核对确定
 */
function save() {
	var form = $("#billForm");
	if(!form.valid()) return;
	
	var billValue = "";
	var obj = document.getElementsByName("bill");
	for(i=0;i<obj.length;i++) {
		if(obj[i].checked) {
			billValue = obj[i].value;
		}
	}
	
	var url = "FinancialManagement/UpdateOrganBillState?billsId=" + $("#billsId").val() + "&billValue=" + billValue + "&comment=" + $("#comment").val();
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: null,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
				toastr.options.onHidden = function() {
	            	window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "FinancialManagement/BillDetail/" + $("#billsId").val();
            	}
				toastr.success(message, "提示");
				$(".bill").hide();
				$(window.parent.document).find(".pop_index").hide();
			} else {
				
			}	
		}
	});
}

/**
 * 导出Excel
 */
function exportExcel() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "FinancialManagement/ExportData?billsId="+$("#billsId").val();
}



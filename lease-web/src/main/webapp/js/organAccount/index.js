var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	validateForm();
	
	initSelectOrgan();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "OrganAccount/GetOrganAccountInfoByQuery",
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无机构账户信息"
        },
        columns: [
	        /*{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "city", sTitle: "城市", sClass: "center", visible: false },
	        {mDataProp: "organId", sTitle: "所属机构", sClass: "center", visible: false },*/
	        
	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 150,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    html += '<button type="button" class="SSbtn blue"  onclick="searchDetail(' +"'"+ full.organId + "','" + full.fullName +"'"+ ')"><i class="fa fa-times"></i>查看往来明细</button>';
                    html += '&nbsp; <button type="button" class="SSbtn orange" onclick="editRecharge(' +"'"+ full.id + "','" + full.fullName + "','" + full.organId + "','" + full.balance + "','" + full.lineOfCredit + "','1'"+ ')"><i class="fa fa-paste"></i>充值</button>';
                    return html;
                }
            },

	        //{mDataProp: "shortName", sTitle: "机构简称", sClass: "center", sortable: true },
	        {
				mDataProp : "shortName",
				sTitle : "机构简称",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						return '<span class="font_green">' + full.shortName + '</span>';
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "cityName", sTitle: "所属城市", sClass: "center", sortable: true },
	        {mDataProp: "actualBalance", sTitle: "账户余额(元)", sClass: "center", sortable: true },
	        {mDataProp: "unbalance", sTitle: "未结算金额(元)", sClass: "center", sortable: true, 
	        	mRender : function(data, type, full) {
					if (data != null) {
						return '<span class="font_red">' + full.unbalance + '</span>';
					} else {
						return "";
					}
				}
	        },
	        {mDataProp: "couponamount", sTitle: "抵用券余额(元)", sClass: "center", sortable: true },
	        {mDataProp: "lineOfCredit", sTitle: "信用额度(元)", sClass: "center", sortable: true },
	        {mDataProp: "balance", sTitle: "可用额度(元)", sClass: "center", sortable: true },
	        {mDataProp: "fullName", sTitle: "机构全称", sClass: "center", sortable: true, 
	        	mRender : function(data, type, full) {
					return showToolTips(full.fullName,10);
				}
	        }
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 表单校验
 */
function validateForm() {
	$("#editRechargeForm").validate({
		rules: {
			amount: {required: true, number:true, limitNum:[7, 1]}
		},
		messages: {
			amount: {required: "请输入正确的金额", number:"只能输入数字和小数点", limitNum:"整数位最多7位，小数位最多1位"}
		}
	})
}

function pageOnlyNumber(evt){ 
	 evt = (evt) ? evt : window.event; 
	 keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);
	     if((keyCode < 48 || keyCode > 57) && keyCode != 8 && keyCode != 46)
	 {
	        if(window.event){
	         
	          window.event.returnValue = false; 
	        }
	         else{
	         evt.preventDefault(); 
	        }
	 } 
}

function clearNoNum(obj) {
	 obj.value = obj.value.replace(/[^\d.]/g, "");//清除“数字”和“.”以外的字符
	 obj.value = obj.value.replace(/^\./g, "");//验证第一个字符是数字而不是.
	 obj.value = obj.value.replace(/\.{2,}/g, ".");//只保留第一个. 清除多余的.
	 obj.value = obj.value.replace(".", "$#$").replace(/\./g,"").replace("$#$", ".");
}

function clearNoNum2(obj) {
	clearNoNum(obj);
	//只能输入1个小数
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d).*$/, '$1$2.$3');
}

function initSelectOrgan() {
	$("#organ").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OrganAccount/GetExistOrganList",
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
}

var isclear = false;
/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "city", "value": $("#city").val() },
		{ "name": "organId", "value": $("#organ").val() },
		{ "name": "unbalance", "value": $("#unbalance").val() }
	];
	if (isclear) {
		dataGrid.fnSearch(conditionArr,"暂无机构账户信息");
	} else {
		dataGrid.fnSearch(conditionArr);
	}
}


/**
 * 查看往来明细
 * @param {} id
 */
function searchDetail(organId,fullName) {

	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "OrganAccount/AccountDetail?organId=" + organId +"&fullName=" + fullName;
}

/**
 * 充值/提现
 */
function editRecharge(id,fullName,organid,balance,lineOfCredit,type) {
	$("#editRechargeFormDiv").show();
	showObjectOnForm("editRechargeForm", null);
	
	var editForm = $("#editRechargeForm").validate();
	editForm.resetForm();
	editForm.reset();
	$("#id").val(id);
	$("#organName").text(fullName);
	$("#organId").val(organid);
	$("#editType").val(type);
	if(type=='1') {
		$("#titleAmount").text("充值");
		$("#lableAmount").text("充值金额");
		
		$("#advanceDiv").hide();
	} else if(type=='2') {
		$("#titleAmount").text("提现");
		$("#lableAmount").text("提现金额");
		
		var advanceAmount = balance - lineOfCredit;
		advanceAmount = advanceAmount.toFixed(1);
		$("#advanceAmount").val(advanceAmount);
		$("#advanceVisual").html("可提现金额：<span class='font_red'>" + advanceAmount + "</span>元");
		$("#advanceDiv").show();
	}
}

/**
 * 充值/提现保存
 */
function saveRecharge() {
	var form = $("#editRechargeForm");
	if(!form.valid()) return;
	
	var url = "OrganAccount/RechargeOrganAccount";
	if($("#editType").val()=='2') {
		var advanceAmount = $("#advanceAmount").val();
		var amount = $("#amount").val();
		if (advanceAmount > 0) {
			if (advanceAmount - amount >= 0) {
				url = "OrganAccount/ReduceOrganAccount";
			} else {
				toastr.error("提现金额大于可提现金额，请重新输入", "提示");
				return;
			}
		} else {
			toastr.error("可提现金额：" + advanceAmount + "元", "提示");
			return;
		}
	}
	var data = form.serializeObject();
	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.success(message, "提示");
            
				$("#editRechargeFormDiv").hide();
				showObjectOnForm("editRechargeForm", null);
				dataGrid._fnReDraw();
			} else {
				
			}	
		}
	});
}

/**
 * 取消
 */
function canel() {
	$("#editRechargeFormDiv").hide();
}

/**
 * value 验证的值
 * param[0] 最大整数位
 * param[1] 最大小数位
 */
$.validator.addMethod("limitNum", function(value, element, param) {
	var rep = new RegExp("^\\d{0,"+param[0]+"}(\\.\\d){0,"+param[1]+"}$");
	return rep.test(value);
}, "数字格式不正确");

/**
 * 清空
 */
function clearParameter() {
	$("#city").val("");
	$("#organ").select2("val","");
	$("#unbalance").val("");
	
	isclear = true;
	search();
	isclear = false;
}

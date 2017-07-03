var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	validateForm();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "IndividualAccountRules/GetIndividualAccountRulesByOrgan?id=" + $("#rulesRefId").val(),
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无计费规则信息"
        },
        columns: [
            /*{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "city", sTitle: "城市", sClass: "center", visible: false },
	        {mDataProp: "carType", sTitle: "服务车型", sClass: "center", visible: false },
	        {mDataProp: "rulesType", sTitle: "规则类型", sClass: "center", visible: false },
	        {mDataProp: "timeType", sTitle: "时间补贴类型", sClass: "center", visible: false },
	        {mDataProp: "perhour", sTitle: "时速", sClass: "center", visible: false },*/
	        
	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 80,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    var obj = "{startPrice:'" + full.startPrice + "',rangePrice:'" + full.rangePrice + "',timePrice:'" + full.timePrice +"',deadheadmileage:'" + full.deadheadmileage +"',deadheadprice:'" + full.deadheadprice +"',nighteprice:'" + full.nighteprice +"',nightstarttime:'" + full.nightstarttime +"',nightendtime:'" + full.nightendtime + "',perhour:'" + full.perhourVisual + "'}";
                    html += '<button type="button" class="SSbtn green_a" onclick="edit(' +"'"+ full.id + "','" + full.timeType +"'," + obj + ')"><i class="fa fa-paste"></i>修改</button>';
                    html += '&nbsp; <button type="button" class="SSbtn red"  onclick="del(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i>删除</button>';
                    return html;
                }
            },


	        {mDataProp: "cityName", sTitle: "城市", sClass: "center", sortable: true },
	        {mDataProp: "carTypeName", sTitle: "服务车型", sClass: "center", sortable: true },
	        {mDataProp: "rulesTypeName", sTitle: "规则类型", sClass: "center", sortable: true },
	        {mDataProp: "startPrice", sTitle: "起步价(元)", sClass: "center", sortable: true },
	        {mDataProp: "rangePrice", sTitle: "里程价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "timePrice", sTitle: "时间补贴(元/分钟)", sClass: "center", sortable: true },
	        {mDataProp: "timeTypeName", sTitle: "时间补贴类型", sClass: "center", sortable: true },
	        {mDataProp: "perhourVisual", sTitle: "时速(公里/小时)", sClass: "center", sortable: true },
	        {mDataProp: "deadheadmileage", sTitle: "回空里程(公里)", sClass: "center", sortable: true },
	        {mDataProp: "deadheadprice", sTitle: "回空费价(元/公里)", sClass: "center", sortable: true },
	        {mDataProp: "nighttimes", sTitle: "夜间征收时段", sClass: "center", sortable: true },
	        {mDataProp: "nighteprice", sTitle: "夜间费价(元/公里)", sClass: "center", sortable: true },
	        //{mDataProp: "updateTime", sTitle: "更新时间", sClass: "center", sortable: true }
	        {
				mDataProp : "updateTime",
				sTitle : "更新时间",
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
 * 表单校验
 */
function validateForm() {
	$("#editForm").validate({
		rules: {
			startPrice: {required: true, number:true, limitNum:[4, 1]},
			rangePrice: {required: true, number:true, limitNum:[3, 1]},
			timePrice: {required: true, number:true, limitNum:[2, 1]},
			perhour: {required: true, number:true, limitNum:[3, 1]},
			deadheadmileage: {required: true, number:true, limitNum:[3, 1]},
			deadheadprice: {required: true, number:true, limitNum:[3, 1]},
			nighteprice: {required: true, number:true, limitNum:[3, 1]},
			nightstarttime: {required: true,timecheckstart:true},
			nightendtime: {required: true,timecheckend:true}
		},
		messages: {
			startPrice: {required: "请输入起步价", number:"只能输入数字和小数点", limitNum:"最大4位整数，1位小数"},
			rangePrice: {required: "请输入里程价", number:"只能输入数字和小数点", limitNum:"最大3位整数，1位小数"},
			timePrice: {required: "请输入时间价", number:"只能输入数字和小数点", limitNum:"最大2位整数，1位小数"},
			perhour: {required: "请输入时速", number:"只能输入数字和小数点", limitNum:"最大3位整数，1位小数"},
			deadheadmileage:{required: "请输入回空里程", number:"只能输入数字和小数点", limitNum:"最大3位整数，1位小数"},
			deadheadprice:{required: "请输入回空费价", number:"只能输入数字和小数点", limitNum:"最大3位整数，1位小数"},
			nighteprice:{required: "请输入夜间费价", number:"只能输入数字和小数点", limitNum:"最大3位整数，1位小数"},
			nightstarttime: {required: "请选择夜间征收时段",timecheckstart:"请选择夜间征收时段"},
			nightendtime: {required: "",timecheckend:""},
		}
	})
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
$.validator.addMethod("timecheckstart", function(value, element, param) {
     if($("#nightstarttime").val() != "" &&  $("#nightendtime").val() != ""){
    	 return true;
     }else{
    	 return false;
     }
}, "");
$.validator.addMethod("timecheckend", function(value, element, param) {
     if($("#nightstarttime").val() != "" &&  $("#nightendtime").val() != ""){
    	 return true;
     }else{
    	 return false;
     }
}, "");
/**
 * 修改
 */
function edit(id,timeType,obj) {
	$("#editFormDiv").show();
	showObjectOnForm("editForm", null);
	
	var editForm = $("#editForm").validate();
	editForm.resetForm();
	editForm.reset();
	
	$("#id").val(id);
	//赋值
	$("#startPrice").val(obj.startPrice);
	$("#rangePrice").val(obj.rangePrice);
	$("#timePrice").val(obj.timePrice);
	if(obj.deadheadmileage =="null"){
		$("#deadheadmileage").val("");
	}else{
		$("#deadheadmileage").val(obj.deadheadmileage);
	}
	if(obj.deadheadprice == "null"){
		$("#deadheadprice").val("");
	}else{
		$("#deadheadprice").val(obj.deadheadprice);
	}
	if(obj.nightstarttime == "null"){
		$("#nightstarttime").val("");
	}else{
		$("#nightstarttime").val(obj.nightstarttime);
	}
	if(obj.nightendtime == "null"){
		$("#nightendtime").val("");
	}else{
		$("#nightendtime").val(obj.nightendtime);
	}
	if(obj.nighteprice == "null"){
		$("#nighteprice").val("");
	}else{
		$("#nighteprice").val(obj.nighteprice);
	}	
	if (timeType == 0) {
		$("#perhourDiv").hide();
	} else if (timeType == 1) {
		$("#perhourDiv").show();
		$("#perhour").val(obj.perhour);
	}
}

/**
 * 确定
 */
function save() {
	var form = $("#editForm");
	if(!form.valid()) return;
	
	var url = "IndividualAccountRules/UpdateIndividualAccountRules";
	
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
            
				$("#editFormDiv").hide();
				showObjectOnForm("editForm", null);
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
	$("#editFormDiv").hide();
}

/**
 * 删除
 * @param {} id
 */
function del(id) {
	var comfirmData={
		tittle:"提示",
		context:"您确认要删除当前规则吗?",
		button_l:"否",
		button_r:"是",
		click: "deletePost('" + id + "')",
		htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	Zconfirm(comfirmData);
}

function deletePost(id){
	$.post("IndividualAccountRules/DeleteIndividualAccountRules/" + id, null, function (status) {
		if (status.ResultSign == "Successful") {
			var message = status.MessageKey == null ? status : status.MessageKey;
            toastr.success(message, "提示");
			dataGrid._fnReDraw();
		} else {
			
		}
	});
}

/**
 * 返回
 */
function back() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "IndividualAccountRules/Index"
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


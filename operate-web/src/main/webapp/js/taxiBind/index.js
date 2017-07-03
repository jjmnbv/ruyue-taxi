var dataGrid;
var bindingVelDataGrid;
var unBindingVelDataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	initSelect2();
	
	searchForm();
});

/**
 * 表单校验
 */
function searchForm() {
    $("#searchForm").validate({
    	rules: {
    		bindpersonnum: {digits: true, min: 1}
        },
        messages: {
        	bindpersonnum: {digits: "请输入正整数",min: "请输入正整数"}
        }
    })
}


/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "TaxiBind/GetVehicleInfoByQuery",
        iLeftColumn: 3,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无服务车辆信息"
        },
        columns: [
	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 350,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "<div style='text-align: left'>";
                    
                    var obj = "{name:'"+full.plateno+" "+full.cityvisual+"',id:'"+full.id+"',city:'"+full.city+"',plateno:'"+full.plateno+"'}";
                    if(full.vehiclestatus=="0"){
                        html += '&nbsp; <button type="button" class="SSbtn red"  onclick="bindingVel('+obj+')"><i class="fa fa-times"></i> 绑定</button>';
                    }
                    if(full.boundstate=="1"){
                        html += '&nbsp; <button type="button" class="SSbtn green_a"  onclick="unwrapVel('+obj+')"><i class="fa fa-times"></i> 解绑</button>';
                    }
                    if (full.bindcount > 0) {
                    	html += '&nbsp; <button type="button" class="SSbtn blue"  onclick="toRecord('+obj+')"><i class="fa fa-times"></i> 操作记录</button>';
                    }
                    if(full.vehiclestatus=="0" && full.boundstate=="1" && (full.driverid=="" || full.driverid!="" && full.passworkstatus=="5") && full.bindpersonnum >= 2){
                        html += '&nbsp; <button type="button" class="SSbtn purple"  onclick="shownotcashdiv(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 指派当班</button>';
                    }
                    html+="</div>";
                    return html;
                }
            },
            
            {mDataProp: "plateno", sTitle: "车牌号", sClass: "center", sortable: true },
            {mDataProp: "vehcbrandname", sTitle: "品牌车系", sClass: "center", sortable: true },
            {mDataProp: "color", sTitle: "颜色", sClass: "center", sortable: true },
            {mDataProp: "cityvisual", sTitle: "登记城市", sClass: "center", sortable: true },
            {mDataProp: "boundstate", sTitle: "绑定状态", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if (full.boundstate == 0) {
                    	return "未绑定";
                    } else if (full.boundstate == 1) {
                    	return "已绑定";
                    }
                }
            },
            {mDataProp: "vehiclestatus", sTitle: "营运状态", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if (full.vehiclestatus == 0) {
                    	return "营运中";
                    } else if (full.vehiclestatus == 1) {
                    	return "维修中";
                    }
                }
            },
            {mDataProp: "PBZT", sTitle: "排班状态", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if (full.boundstate == 0) {
                    	return "/";
                    } else if (full.boundstate == 1) {
                    	if (full.bindpersonnum >= 2) {
                    		if (full.driverid != null && full.driverid != "" && (full.passworkstatus == "1" || full.passworkstatus == "3")) {
                    			return "已排班";
                        	} else {
                        		return "未排班";
                        	}
                		} else {
                			return "/";
                		}
                    }
                }
            },
            {mDataProp: "workstatus", sTitle: "服务状态", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if (full.boundstate == 0) {
                    	return "/";
                    } else if (full.boundstate == 1) {
                    	if (full.workstatus == 0) {
                    		return '<span class="font_green">空闲</span>';
                    	} else if (full.workstatus == 1) {
                    		return '<span class="font_red">服务中</span>';
                    	} else {
                    		return "下线";
                    	}
                    }
                }
            },
            {mDataProp: "DBSJ", sTitle: "当班司机", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if (full.boundstate == 1 && full.bindpersonnum >= 2 && full.driverid != null && full.driverid != "" && (full.passworkstatus == "1" || full.passworkstatus == "3")) {
                    	if (full.name != null && full.name != "") {
                    		return full.name + " " + full.phone;
                    	} else {
                    		return full.phone;
                    	}
                    } else {
                    	return '/';
                    }
                }
            },
            {mDataProp: "bindpersonnum", sTitle: "绑定人数", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if (full.boundstate == 0) {
                    	return "/";
                    } else if (full.boundstate == 1) {
                    	return full.bindpersonnum;
                    }
                }
            },
            {mDataProp: "binddirverinfo", sTitle: "绑定司机信息", sClass: "center", sortable: true, 
            	"mRender": function (data, type, full) {
                    if (full.boundstate == 0) {
                    	return "/";
                    } else if (full.boundstate == 1) {
                    	var index = findIndex(full.binddirverinfo,"、",3);
                    	if (index >= 0) {
                    		return showToolTips(full.binddirverinfo,index);
                    	} else {
                    		return full.binddirverinfo;
                    	}
                    }
                }
            }
            
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 长度显示控制
 */
function findIndex(str,cha,num) {
    var x=str.indexOf(cha);
    for(var i=0;i<num;i++) {
    	if (x == -1) {
    		return x;
    	}
        x=str.indexOf(cha,x+1);
    }
    return x;
}

function initSelect2() {
	$("#driverid").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "TaxiBind/GetOndutyDriver",
			dataType : 'json',
			data : function(term, page) {
				return {
					driver: term
				};
			},
			results : function(data, page) {
				return {
					results: data
				};
			}
		}
	});
	
	$("#vehclineid").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "TaxiBind/GetVehcbrandVehcline",
			dataType : 'json',
			data : function(term, page) {
				return {
					vehcbrandname: term
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
	var form = $("#searchForm");
    if(!form.valid()) return;
	
	var conditionArr = [
		{ "name": "vehclineid", "value": $("#vehclineid").val() },
		{ "name": "plateno", "value": $("#plateno").val() },
		{ "name": "boundstate", "value": $("#boundstate").val() },
		{ "name": "workstatus", "value": $("#workstatus").val() },
		{ "name": "city", "value": $("#city").val() },
		{ "name": "bindpersonnum", "value": $("#bindpersonnum").val() },
		{ "name": "driverid", "value": $("#driverid").val() },
		{ "name": "ondutystatus", "value": $("#ondutystatus").val() },
		{ "name": "vehiclestatus", "value": $("#vehiclestatus").val() }
	];
	if (isclear) {
		dataGrid.fnSearch(conditionArr,"暂无服务车辆信息");
	} else {
		dataGrid.fnSearch(conditionArr);
	}
}

/**
 * 清空
 */
function clearSearch() {
	$("#vehclineid").select2("val","");
	$("#plateno").val("");
	$("#boundstate").val("");
	$("#workstatus").val("");
	$("#city").val("");
	$("#bindpersonnum").val("");
	$("#driverid").select2("val","");
	$("#ondutystatus").val("");
	$("#vehiclestatus").val("");
	
	isclear = true;
	search();
	isclear = false;
}


/**绑定相关 start**/

//绑定
function bindingVel(json){

    $("#vehcile").val(json.id);
    $("#cityId").val(json.city);
    $("#plate").val(json.plateno);
    
    $("#queryJobnum").select2("val","");
    $("#queryDriver").select2("val","");
    //$("#queryJobnum").blur();
    //$("#queryDriver").blur();

    //渲染显示标题
    $("#bindingVelTitleForm").html("绑定司机 <font color='orange'>【"+json.name+"】</font>");

    $("#queryDriver").select2({
        placeholder: "",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "TaxiBind/GetDriverByNameOrPhone",
            dataType: 'json',
            data: function (term, page) {
            	return {
            		driver: term,
            		city:$("#cityId").val()
				};
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });

    $("#queryJobnum").select2({
        placeholder: "",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: $("#baseUrl").val() + "TaxiBind/GetDriverByJobnum",
            dataType: 'json',
            data: function (term, page) {
            	return {
            		jobnum: term,
            		city:$("#cityId").val()
				};
            },
            results: function (data, page) {
            	return { results: data };
            }
        }
    });

    $("#bindingVel").show();

    if(!bindingVelDataGrid){
        bindingVelInitGrid(json);
    }else{
    	bindquery();
    }
    
    // 查找所有已绑定司机
    findBindDrivers(json.id);

}

function findBindDrivers(vehicleid) {
	$.ajax({
        type: 'POST',
        dataType: 'json',
        url: "TaxiBind/GetBindDriversByVehicleid/" + vehicleid,
        data: null,
        contentType: 'application/json; charset=utf-8',
        async: false,
        success: function (json) {
            var binddrivers = "";
        	if (json != null) {
            	for (var i=0;i<json.length;i++) {
            		if (i==0) {
            			binddrivers += json[i].name + ' ' + json[i].phone;
            		} else {
            			binddrivers += '、' + json[i].name + ' ' + json[i].phone;
            		}
            	}
            }
        	if (binddrivers) {
        		$("#addcboxId").text(binddrivers);
        	} else {
        		$("#addcboxId").text("暂无绑定司机");
        	}
        }
    });
	
}

/**
 * 查询
 */
function bindquery() {
    var conditionArr = [
        { "name": "driver", "value": $("#queryDriver").val()},
        { "name": "jobnum", "value": $("#queryJobnum").val()},
        { "name": "city", "value": $("#cityId").val()}
     ];
    bindingVelDataGrid.fnSearch(conditionArr,"暂无可绑定服务司机信息");
}

/**
 * 查询
 */
function query() {
    var conditionArr = [
        { "name": "driver", "value": $("#queryDriver").val()},
        { "name": "jobnum", "value": $("#queryJobnum").val()},
        { "name": "city", "value": $("#cityId").val()},
        { "name": "key", "value": "01"}
     ];
    bindingVelDataGrid.fnSearch(conditionArr);
}


/**
 * 添加绑定
 * @param velid
 */
function bindingVelAdd(driverId){
    var vehicleId = $("#vehcile").val();
    var url = "TaxiBind/CreatePubDriverVehicleRef";
    var driverArrya = new Array();
    driverArrya.push(driverId);

    var data = {
    	vehicleid : vehicleId,
        driverids : driverArrya,
        plateno : $("#plate").val()
    }
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
                bindingVelDataGrid._fnReDraw();
                dataGrid._fnReDraw();
                // 查找所有已绑定司机
                findBindDrivers(vehicleId);
                // 判断是否弹出人工指派
                isAssign(vehicleId);
            } else {
            	var message = status.MessageKey == null ? status : status.MessageKey;
                toastr.error(message, "提示");
                bindingVelDataGrid._fnReDraw();
                dataGrid._fnReDraw();
            }

            //$("#bindingVel").hide();
        }
    });
}

function isAssign(vehicleId){
    $.ajax({
        type: "POST",
        dataType: "json",
        url: $("#baseUrl").val() + "TaxiBind/IsAssign/"+vehicleId,
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (result) {
            var message = result.message == null ? result : result.message;
            if (result.status == "0") {
              if(result.data){
                  shownotcashdiv(vehicleId);
              }
            }
        }
    });
}

/**
 * 表格初始化
 */
function bindingVelInitGrid(obj) {

    var cityId = $("#cityId").val();
    var gridObj = {
        id: "bindingVelDataGrid",
        sAjaxSource: "TaxiBind/GetUnbindDriverByQuery",
        userQueryParam: [{ "name": "city", "value":cityId}],
        //iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        language: {
        	sEmptyTable: "暂无可绑定服务司机信息"
        },
        iDisplayLength:10,
        columns: [
            {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
            {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", sortable: true },
            {mDataProp: "name", sTitle: "姓名", sClass: "center", sortable: true },
            {mDataProp: "phone", sTitle: "手机号", sClass: "center", sortable: true },
            {mDataProp: "cityvisual", sTitle: "登记城市", sClass: "center", sortable: true },
            {mDataProp: "boundstate", sTitle: "绑定状态", sClass: "center", sortable: true,
            	"mRender": function (data, type, full) {
            		if (full.boundstate == 0) {
                    	return "未绑定";
                    } else if (full.boundstate == 1) {
                    	return "已绑定";
                    }
                }
            },
            {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 60,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    if (full.boundstate == 0) {
                    	html += '<button type="button" class="SSbtn orange"  onclick="bindingVelAdd(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 绑定</button>';
                    }
                    return html;
                }
            }
        ]
    };

    bindingVelDataGrid = renderGrid(gridObj);
    //清空初始化参数
    gridObj.userQueryParam="";
}

/**绑定相关 end**/


/**解绑相关 start**/

//解绑
function unwrapVel(bindObj){
    $("#unwrapVelTitleForm").html("解绑司机 <font color='orange'>【"+bindObj.name+"】</font>");

    $("#plate").val(bindObj.plateno);
    $("#unBindReason").val("");
    $('#vehicleId').val(bindObj.id);
    $("#checkAllManual").prop("checked",false);
    $("#unwrapVel").show();

    unwrapVelForm();

    if(!unBindingVelDataGrid){
        //初始化查询司机
        unBindingVelInitGrid(bindObj);
    } else{
        queryBindDriver(bindObj);
    }
    // queryBindDriver(bindObj);
    // 清除验证提示
    var editForm = $("#unwrapVelForm").validate();
    editForm.resetForm();
    editForm.reset();

}

/**
 * 取消
 */
function canel() {
    $("#unwrapVel").hide();
}

/**
 * 表单校验
 */
function unwrapVelForm() {
    $("#unwrapVelForm").validate({
        rules: {
            unBindReason: {required: true, maxlength: 100}
        },
        messages: {
            unBindReason: {required: "请输入解绑原因",maxlength: "最大长度不能超过100个字符"}
        }
    })
}
/**
 * 确定解绑
 */
function unwrapVelSave(){
    var form = $("#unwrapVelForm");
    if(!form.valid()) return;
    var url = "TaxiBind/UpdatePubDriverVehicleRef";
    var vehicleId = $('#vehicleId').val();
    var unBindReason = $("#unBindReason").val();
    var driverArray = new Array();
    var a = document.getElementsByName("checkOrderManual");
    for(var i = 0;i < a.length; i++) {
        if(a[i].checked == true) {
            driverArray.push(a[i].value);
        }
    }

    if(driverArray.length<1){
        toastr.error("请选择要解绑的司机", "提示");
        return;
    }


    var data = {
    	driverids : driverArray,
    	vehicleid : vehicleId,
    	unbindreason : unBindReason,
    	plateno : $("#plate").val()
    }

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
                $("#unwrapVel").hide();
                dataGrid._fnReDraw();
            } else {
            	var message = status.MessageKey == null ? status : status.MessageKey;
                toastr.error(message, "提示");
                
                var obj = {id:vehicleId};
                queryBindDriver(obj)
                $("#checkAllManual").prop("checked",false);
                dataGrid._fnReDraw();
            }
        }
    });
}


/**
 * 表格初始化
 */
function unBindingVelInitGrid(parmObj) {
    var gridObj = {
        id: "unBindingVelDataGrid",
        sAjaxSource: $("#baseUrl").val() +"TaxiBind/GetBindDriverByVehicleid",
        userQueryParam: [ { "name": "vehicleid", "value": parmObj.id}],
        //iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入纵向滚动条）
        columns: [
            {
                // 自定义操作列
                "mDataProp" : "ZDY",
                "sClass" : "center",
                "sTitle" : "<input type='checkbox' id='checkAllManual' name='checkAllManual' onclick='checkAllManualHander(this)'></input>全选",
                "sWidth" : 70,
                "bSearchable" : false,
                "sortable" : false,
                "mRender" : function(data, type, full) {
                    var html = "";
                    html += '<input type="checkbox" id="checkOrderManual" name="checkOrderManual" onclick="onClickHander(this)" value="'
                        + full.id + '"></input>';
                    return html;
                }
            },
            {mDataProp: "name", sTitle: "姓名", sClass: "center", sortable: true },
            {mDataProp: "sex", sTitle: "性别", sClass: "center", sortable: true ,mRender:function (data, type, full) {
                if (data == '0') {
                    return "男";
                } else if (data == '1') {
                	return "女";
                } else if (data == "男") {
                	return "男";
                } else {
                	return "女";
                }         
            }},
            {mDataProp: "phone", sTitle: "手机号", sClass: "center", sortable: true },
            {mDataProp: "passworkstatus", sTitle: "班次状态", sClass: "center", sortable: true, 
            	"mRender" : function(data, type, full) {
                    if (data == "1" || data == "3") {
                    	return "当班";
                    } else if (data == "2" || data == "4") {
                    	return "歇班";
                    } else {
                    	return "/";
                    }
                }
            },
            {mDataProp: "cityvisual", sTitle: "登记城市", sClass: "center", sortable: true }
        ]
    };

    unBindingVelDataGrid = renderGrid(gridObj);
    //清空初始化参数
    gridObj.userQueryParam="";
}

function checkAllManualHander(obj) {
    var a = document.getElementsByTagName("input");
    if (obj.checked) {
        for(var i = 0;i < a.length; i++) {
            if(a[i].name == "checkOrderManual" && a[i].type == "checkbox" && a[i].checked == false) {
                a[i].checked = true;
            }
        }
    } else {
        for(var i = 0;i < a.length; i++) {
            if(a[i].name == "checkOrderManual" && a[i].type == "checkbox" && a[i].checked == true) {
                a[i].checked = false;
            }
        }
    }
}

/**
 * 勾选解绑司机
 */
function onClickHander(obj) {
	var a = document.getElementsByName("checkOrderManual");
    var qx = true;
	for(var i = 0;i < a.length; i++) {
        if(a[i].checked == true) {
        	qx = true;
        } else {
        	qx = false;
        	break;
        }
    }
	if (qx) {
		$("#checkAllManual").prop("checked",true);
	} else {
		$("#checkAllManual").prop("checked",false);
	}
}

function queryBindDriver(parmObj){
    var conditionArr = [
        { "name": "vehicleid", "value": parmObj.id}

    ];
    unBindingVelDataGrid.fnSearch(conditionArr);
}

/**解绑相关 end**/



/**********指定交接班 start***************/


/**
 * 弹出选择接班司机框
 * @param id
 */
function shownotcashdiv(vehicleid){
    
    $('#vehcile').val(vehicleid);
    $("#notcashdiv").show();
    var selObj = $("#plateNoProvince");
    selObj.attr("disabled",false);

    $.ajax({
        type: "POST",
        dataType: "json",
        url: $("#baseUrl").val() + "TaxiBind/ListTaxiBindDriver/"+vehicleid,
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (result) {
        	selObj.find("option").remove();
            selObj.append( "<option value=''>请选择</option>" );
            for(var i=0;i<result.length;i++){
                var dataObj = result[i];

                if (dataObj.name != null && dataObj.name != "") {
                	selObj.append( "<option value='"+dataObj.id+"'>"+dataObj.name+" "+dataObj.phone+"</option>" );
                } else {
                	selObj.append( "<option value='"+dataObj.id+"'>"+dataObj.phone+"</option>" );
                }
                
                if (dataObj.uncompletecount != null && dataObj.uncompletecount != "" && dataObj.uncompletecount > 0) {
                	selObj.attr("disabled","disabled");
                    selObj.val(dataObj.id);
                }
            }

            if(result.length==1){
                selObj.attr("disabled","disabled");
                selObj.val(result[0].id);
            }
        }
    });
}


/**
 * 交接班操作
 * @param driverId
 * @param pendingId
 */
function processed(){
    debugger
    var vehicleId = $("#vehcile").val();
    var driverId = $("#plateNoProvince").val();
    var driverStr = $("#plateNoProvince").find("option:selected").text();
    //初始化参数
    var data = {vehicleId: vehicleId,relieveddriverid: driverId,relieveddriverInfo: driverStr};
    var str = '';

    if(!driverId){
        toastr.warning("请选择当班司机", "提示");
        return ;
    }

    $.ajax({
        type: "POST",
        dataType: "json",
        url: "TaxiBind/SaveAssign",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        async: false,
        success: function (result) {
            /*var message = result.message == null ? result : result.message;
            if (result.status == "0") {

                toastr.success(message, "提示");

            } else {
                toastr.error(message, "提示");
            }*/
            $("#notcashdiv").hide();
            search();
        }
    });

}

/**
 * 人工指派取消
 */
function cancelNotDiv(){
    $("#notcashdiv").hide();
}

/**********指定交接班 end***************/


/**
 * 操作记录
 */
function toRecord(bindObj) {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "TaxiBind/OperateDetail?vehicleid=" + bindObj.id + "&plateno=" + bindObj.plateno;
}

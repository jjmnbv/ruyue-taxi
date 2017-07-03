var dataGrid;
var usertype;

/**
 * 页面初始化
 */
$(function() {
	initBtn();
	initGrid();
	validateForm();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id : "dataGrid",
		sAjaxSource : $("#baseUrl").val() + "OpVehiclemodels/GetOpVehiclemodelsByQuery",
		iLeftColumn: 1,
        scrollX: true,
		columns : [
				{
					mDataProp : "id",
					sTitle : "id",
					sClass : "center",
					visible : false
				},
				{
					// 自定义操作列
					"mDataProp" : "ZDY",
					"sClass" : "center",
					"sTitle" : "操作",
					"sWidth" : 150,
					"bSearchable" : false,
					"sortable" : false,
					"mRender" : function(data, type, full) {
						var html ="";
							html += '<button type="button" class="SSbtn green_a" onclick="edit('+ "'"+ full.id+ "'"+ ')"><i class="fa fa-paste"></i>修改</button>';
							if(full.modelstatus=="0"){
								html += '&nbsp; <button type="button" class="SSbtn blue"  onclick="allocationLine('+ "'"+ full.id+ "'"+ ')"><i class="fa fa-times"></i>分配车系</button>';
								html += '&nbsp; <button type="button" class="SSbtn red"  onclick="disableState('+ "'"+ full.id+ "'"+ ')"><i class="fa fa-times"></i>禁用</button>';
							}else{
								html += '&nbsp; <button type="button" class="SSbtn blue"  onclick="allocationline('+ "'"+ full.id+ "'"+ ')"><i class="fa fa-times"></i>分配车系</button>';
								html += '&nbsp; <button type="button" class="SSbtn green_a"  onclick="ableState('+ "'"+ full.id+ "'"+ ')"><i class="fa fa-times"></i>启用</button>';
							}
						return html;
					}
				},
				{
					mDataProp : "logo",
					sTitle : "车型图标",
					sClass : "center",
					sortable : true,
					"mRender" : function(data, type, full) {
						var html = "";
						if (full.logo != null && full.logo != "") {
							html += '<img src="' + serviceAddress + '/'
									+ full.logo
									+ '" style="width:40px;height:40px;" />';
						} else {
							html += '<img src="" style="width:40px;height:40px;" />';
						}
						return html;
					}
				}, {
					mDataProp : "name",
					sTitle : "车型名称",
					sClass : "center",
					sortable : true
				}, {
					mDataProp : "level",
					sTitle : "车型级别",
					sClass : "center",
					sortable : true
				}, {
					mDataProp : "level",
					sTitle : "对应车系",
					sClass : "center",
					sortable : true,
					"mRender" : function(data, type, full) {
						if(full.vehclines&&full.vehclines.length>0){
							var vehclinesnames = [];
							for(var i=0;i<full.vehclines.length;i++){
								var vehcline = full.vehclines[i];
								var fullname = vehcline.vehcBrandName+"-"+vehcline.name;
								vehclinesnames.push(fullname);
							}
							return showToolTips(vehclinesnames.join(","),35);
						}
						return "";
					}
				} , {
					mDataProp : "level",
					sTitle : "状态",
					sClass : "center",
					sortable : true,
					"mRender" : function(data, type, full) {
						if(full.modelstatus=="0"){
							return "启用";
						}
						return "禁用";
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
		ignore:"",
		rules : {
			name : {
				required : true,
				maxlength : 6
			},
			level : {
				required : true,
				digits : true,
				maxlength : 2
			},
			logo:{
				required : true
			}
		},
		messages : {
			name : {
				required : "请填写车型名称",
				maxlength : "最大长度不能超过6个字符"
			},
			level : {
				required : "请填写车型级别",
				digits : "车型级别必须是整数",
				maxlength : "最大长度不能超过2个字符"
			},
			logo:{
				required : "请上传车型图片"
			}
		}
	})
}

/**
 * 新增
 */
function add() {
	$("#titleForm").html("新增服务车型");
	$("#editFormDiv").show();
	$("#imgback").attr("src","content/img/ing_tupian.png");
	$("#name").removeAttr("disabled");
	showObjectOnForm("editForm", null);
	$("#logo").val("");
	$("#imgShow").hide();
	var editForm = $("#editForm").validate();
	editForm.resetForm();
	editForm.reset();
}
/**
 * 修改
 * 
 * @param {}
 *            id
 */
function edit(id) {
	$("#titleForm").html("修改服务车型");
	$.ajax({
		type : "GET",
		url : $("#baseUrl").val() + "OpVehiclemodels/GetOpVehiclemodelsById?datetime=" + new Date().getTime(),
		cache : false,
		data : {
			id : id,
			datetime: new Date().getTime()
		},
		success : function(json) {
			$("#editFormDiv").show();
			$("#name").attr("disabled", "true");
			showObjectOnForm("editForm", json);
			if(null != json.logo && "" != json.logo) {
				$("#imgShow").show();
				$("#imgback").attr("src", serviceAddress + "/" + json.logo);
			} else {
				$("#imgShow").hide();
				$("#imgback").attr("src","content/img/ing_tupian.png");
			}
			$("#logo").val(json.logo);
			var editForm = $("#editForm").validate();
			editForm.resetForm();
			editForm.reset();
			$("#id").val(id);
			dataGrid._fnReDraw();
		},
		error : function(xhr, status, error) {
			$("#id").val("");
			return;
		}
	});
}
/**
 * 分配车系
 */
function allocationLine(id) {
	var data = {
		id : id
	};
	$.post($("#baseUrl").val() + "OpVehiclemodels/GetOpVehiclemodelsById", data, function(status) {
		$("#currentVel").html(status.name);
		allocationLineShow(id,status.name);
	});
}

/**
 * 不能分配车系
 */
function allocationline(id){
	toastr.warning("该服务车型已被禁用，请启用后再进行分配车系！", "提示");
}

/**
 * 删除
 * 
 * @param {}
 *            id
 */
function del(id) {
	var comfirmData = {
		tittle : "提示",
		context : "您确定要删除这条数据?",
		button_l : "否",
		button_r : "是",
		htmltex : "<input type='hidden' placeholder='添加的html'> "
	};
	Zconfirm(comfirmData, function(){deleteOpVehiclemodels(id);});
}

function deleteOpVehiclemodels(id) {
	var data = {
		id : id
	};
	$.post($("#baseUrl").val() + "OpVehiclemodels/DeleteOpVehiclemodels", data, function(status) {
		if (status.ResultSign == "Successful") {
			var message = status.MessageKey == null ? status
					: status.MessageKey;
			toastr.success(message, "提示");
			dataGrid._fnReDraw();
		} else {
			var message = status.MessageKey == null ? status
					: status.MessageKey;
			toastr.error(message, "提示");
		}
	});
}

/**
 * 保存
 */
function save() {
	var form = $("#editForm");
	if (!form.valid()) {
		return;
	}
	
	var url = $("#baseUrl").val() + "OpVehiclemodels/CreateOpVehiclemodels";
	if ($("#id").val()) {
		url = $("#baseUrl").val() + "OpVehiclemodels/UpdateOpVehiclemodels";
	}
	var data = form.serializeObject();
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : url,
		data : JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.success(message, "提示");

				$("#editFormDiv").hide();
				showObjectOnForm("editForm", null);
				dataGrid._fnReDraw();
			} else {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.error(message, "提示");
			}
		}
	});
}

$("#fileupload").fileupload({
	url:$("#baseUrl").val() + "OpVehiclemodels/UploadFile",
    dataType: 'json',
    done: function(e, data) {
    	var result = data.result;
        if(result.status=="success"){
        	$("#imgShow").show();
        	$("#imgback").attr("src", result.basepath+"/"+result.message[0]);
        	$("#logo").val(result.message[0]);
        	$("#logo").valid();
        } else {
        	var message = result.message?result.message:"上传失败";
			toastr.error(message, "提示");
        }
    }
});

$("#clear").click(function(){
	$("#fileupload").val("");
	$("#imgback").attr("src","content/img/ing_tupian.png");
	$("#imgShow").hide();
	$("#logo").val("");
});

/**
 * 取消
 */
function canelBtn() {
	$("#editFormDiv").hide();
	$("#allocationLineDiv").hide();
}
/**
 * 保存车型
 */
function saveLeVehiclemodels(jsonData,vehiclemodelsId) {
	var data = {
		id : vehiclemodelsId,
		jsonData : jsonData
	};
	
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : $("#baseUrl").val() + "OpVehiclemodels/SaveLeVehclineModelsRef",
		data : JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.success(message, "提示");

				dataGrid._fnReDraw();
			} else {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.error(message, "提示");
			}
		}
	});
};

/**
 * 初始化按钮
 */
function initBtn() {
	usertype = $("#usertype").val();
	if(null != usertype && usertype != 1) {
		$("#addBtn").attr("disabled", "disabled");
		$("#addBtn").removeClass("blue").addClass("grey");
	}
}

//禁用
function disableState(id){
	var data = {
		id : id
	};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : $("#baseUrl").val() + "OpVehiclemodels/HasBindLeaseCars",
		data : JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status.ResultSign == "Successful") {
				if(status.hasbindleasecars=="true"){
					//有绑定租赁端的车辆,弹窗确认
					var comfirmData={
						tittle:"提示",
						context:"当前服务车型已分配加入toC车辆，禁用后，此批车辆的服务车型将变为未分配，确定禁用该服务车型？",
						button_l:"否",
						button_r:"是",
						click: "postchangestate2('" +id+"','"+1+"')",
						htmltex:"<input type='hidden' placeholder='添加的html'> "
					};
					Zconfirm(comfirmData);
				}else{
					postchangestate2(id,"1");
				}
			}else {
				var message = status.MessageKey == null ? status: status.MessageKey;
				toastr.error(message, "提示");
			}
		}
	});
}

//启用
function ableState(id){
	var data = {
		id : id,
		state : "0"
	};
	postchangestate(data);
}

function postchangestate2(id,state){
	var data = {
		id : id,
		state : state
	};
	postchangestate(data);
}

function postchangestate(data){
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : $("#baseUrl").val() + "OpVehiclemodels/ChangeState",
		data : JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.success(message, "提示");

				dataGrid._fnReDraw();
			}else if(status.ResultSign == "Warning"){ 
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.warning(message, "提示");
			}else {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.error(message, "提示");
			}
		}
	});
}

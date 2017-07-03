var dataGrid;

/**
 * 页面初始化
 */
$(function() {
	initGrid();
	validateForm();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id : "dataGrid",
		sAjaxSource : "LeVehiclemodels/GetLeVehiclemodelsByQuery",
		iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        language: {
        	sEmptyTable: "暂无服务车型信息"
        },
		columns : [
//				{
//					mDataProp : "id",
//					sTitle : "Id",
//					sClass : "center",
//					visible : false
//				},
				{
					// 自定义操作列
					"mDataProp" : "ZDY",
					"sClass" : "center",
					"sTitle" : "操作",
					"sWidth" : 180,
					"bSearchable" : false,
					"sortable" : false,
					"mRender" : function(data, type, full) {
						var html = "";
						html += '<button type="button" class="SSbtn green_q" onclick="edit('
								+ "'"
								+ full.id
								+ "'"
								+ ')"><i class="fa fa-paste"></i>修改</button>';
						html += '&nbsp; <button type="button" class="SSbtn yellow_q" onclick="allocationLine('
								+ "'"
								+ full.id
								+ "'"
								+ ')"><i class="fa fa-paste"></i>分配车系</button>';
						if(full.modelstatus == 0){
							html += '&nbsp; <button type="button" class="SSbtn grey_q"  onclick="disable('
								+ "'"
								+ full.id
								+ "'"
								+ ')"><i class="fa fa-times"></i>禁用</button>';
						}else if(full.modelstatus == 1){
							html += '&nbsp; <button type="button" class="SSbtn red_q"  onclick="enable('
									+ "'"
									+ full.id
									+ "'"
									+ ')"><i class="fa fa-times"></i>启用</button>';
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
					mDataProp : "brandCars",
					sTitle : "对应车系",
					sClass : "center",
					sortable : true
				}, {
					mDataProp : "modelstatus",
					sTitle : "状态",
					sClass : "center",
					sortable : true,
					"mRender" : function(data, type, full) {
						var html = "";
						if (full.modelstatus == 0) {
							html += '<font>启用</font>';
						} else if(full.modelstatus == 1){
							html += '<font>禁用</font>';
						}
						return html;
					}
				}]
	};

	dataGrid = renderGrid(gridObj);
}

/**
 * 表单校验
 */
function validateForm() {
	$("#editForm").validate({
		rules : {
			name : {
				required : true,
				maxlength : 8
			},
			level : {
				required : true,
				maxlength : 6,
				digits : true
			},
		},
		messages : {
			name : {
				required : "请填写车型名称",
				maxlength : "最大长度不能超过8个字符"
			},
			level : {
				required : "请填写车型级别"
			},
		}
	})
}

/**
 * 新增
 */
function add() {
	$("#titleForm").html("新增服务车型");
	$("#editFormDiv").show();
//	$("#name").removeAttr("disabled");
	showObjectOnForm("editForm", null);

	$("#fileupload").val("");
	$("#imgback").attr("src","content/img/ing_tupian.png");
	$("#imgShow").hide();
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
		url : "LeVehiclemodels/GetById",
		cache : false,
		data : {
			id : id
		},
		success : function(json) {
			$("#editFormDiv").show();
//			$("#name").attr("disabled", "true");
			showObjectOnForm("editForm", json);
			$("#imgShow").show();
			if(json.logo != null && json.logo != ''){
				$("#imgback").attr("src", serviceAddress + "/" + json.logo);
			}else{
				$("#fileupload").val("");
				$("#imgback").attr("src","");
				$("#imgShow").hide();
			}
			$("#logo").val(json.logo);
			var editForm = $("#editForm").validate();
			editForm.resetForm();
			editForm.reset();
			$("#id").val(id);
//			dataGrid._fnReDraw();
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
	// toastr.error("维护中。。。", "提示");
	// $("#allocationLineDiv").show();
	var data = {
		id : id
	};
	$.post("LeVehiclemodels/GetById", data, function(status) {
		if(status.modelstatus == '1'){
			toastr.error("该服务车型已被禁用，请启用后再进行分配车系","提示");
		}else{
			$("#currentVel").html(status.name);
			// ajax(options);
			// document.getElementById("content").style.display = "block";
			allocationLineShow(id,status.name);
	//		$('#content').show();
	//			var carHandSelect = new carSelect({
	//				"url":"LeVehiclemodels/GetPubVehcline", //地址
	//				"callback":function (data) {debugger
	//					console.log(data)  //确定点击后的数据
	//				}
	//		});
		}
	});
	
}

/**
 * 保存
 */
function save() {
	var form = $("#editForm");
	if (!form.valid())
		return;

	var url = "LeVehiclemodels/CreateLeVehiclemodels";
	if ($("#id").val()) {
		url = "LeVehiclemodels/UpdateLeVehiclemodels";
	}

	var data = form.serializeObject();
	
	var id = $("#id").val();
	//验证机构全称
	var name = data.name;
	var flag = false;
	var data1 = {
		id : id,
		name : name
	}
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : "LeVehiclemodels/CheckLeVehiclemodelsName",
		data : data1,
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status > 0) {
				toastr.error("车型名称已存在", "提示");
				flag = true;
			}
		}
	});
	if(flag){
		return;
	}
	
	var logo = data.logo;
	if(logo==null || logo == ''){
		toastr.error("请上传车型图标", "提示");
		return;
	}
	
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

/**
 * 取消
 */
function canel() {
	$("#editFormDiv").hide();
	$("#allocationLineDiv").hide();
}
/**
 * 保存车型
 */
function saveLeVehiclemodels(jsonData,vehiclemodelsId) {
//	if(jsonData.vechileId.length == 0){
//		toastr.error("至少勾选一项", "提示");
//		return ;
//	}
	var data = {
		id : vehiclemodelsId,
		jsonData : jsonData
	};
	
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : "LeVehiclemodels/SaveLeVehclineModelsRefMapper",
		data : JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.success(message, "提示");
				document.getElementById("content").style.display = "none";
				dataGrid._fnReDraw();
			} else {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				document.getElementById("content").style.display = "none";
				toastr.error(message, "提示");
			}
		}
	});
};

/**
 * 删除
 * 
 * @param {}
 *            id
 */
//function del(id) {
//	var comfirmData = {
//		tittle : "提示",
//		context : "您确定要删除这条数据?",
//		button_l : "否",
//		button_r : "是",
//		click : "deletePost('" + id + "')",
//		htmltex : "<input type='hidden' placeholder='添加的html'> "
//	};
//	Zconfirm(comfirmData);
//}
//
//function deletePost(id) {
//	var data = {
//		id : id
//	};
//	$.post("LeVehiclemodels/DeleteLeVehiclemodels", data, function(status) {
//		if (status.ResultSign == "Successful") {
//			var message = status.MessageKey == null ? status
//					: status.MessageKey;
//			toastr.success(message, "提示");
//			dataGrid._fnReDraw();
//		} else {
//			var message = status.MessageKey == null ? status
//					: status.MessageKey;
//			toastr.error(message, "提示");
//		}
//	});
//}

//禁用
function disable(id){
	var data = {
			id : id
		};
		$.post("LeVehiclemodels/CheckDisable", data, function(status) {
			if (status > 0) {
				toastr.error("当前服务车型已分配品牌车系，请取消分配后再试", "提示");
			} else {
				var comfirmData = {
					tittle : "提示",
					context : "您确定要禁用这条数据?",
					button_l : "否",
					button_r : "是",
					click : "disableData('" + id + "')",
					htmltex : "<input type='hidden' placeholder='添加的html'> "
				};
				Zconfirm(comfirmData);
			}
		});
}

function disableData(id){
	var data = {
		id : id,
		modelstatus : '1'
	};
	$.post("LeVehiclemodels/UpdateEnableOrDisable", data, function(status) {
		if (status.ResultSign == "Successful") {
			var message = status.MessageKey == null ? status
					: status.MessageKey;
			toastr.success(message, "提示");
			dataGrid._fnReDraw();
		}
	});
}
//启用
function enable(id){
	var data = {
		id : id,
		modelstatus : '0'
	}
	$.post("LeVehiclemodels/UpdateEnableOrDisable", data, function(status) {
		if (status.ResultSign == "Successful") {
			var message = status.MessageKey == null ? status
					: status.MessageKey;
			toastr.success(message, "提示");
			dataGrid._fnReDraw();
		}
	});
}

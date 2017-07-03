var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	validateForm();
	//initSelectVehcBrandName();
	initSelectKey();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "PubVehcline/GetPubVehclineByQuery",
        iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        language: {
        	sEmptyTable: "暂无品牌车系信息"
        },
        columns: [
//	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
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
                    html += '<button type="button" class="SSbtn green_q" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
                    html += '&nbsp; <button type="button" class="SSbtn red_q"  onclick="del(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 删 除</button>';
                    return html;
                }
            },
	        {mDataProp: "name", sTitle: "车系名称", sClass: "center", sortable: true },
	        {mDataProp: "vehcBrandName", sTitle: "品牌归属", sClass: "center", sortable: true }
	       
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
			name: {required: true, maxlength: 8},
			vehcBrandName: {required: true}
		},
		messages: {
			name: {required: "请填写车系名称", maxlength: "最大长度不能超过8个字符"},
			vehcBrandName: {required: "请搜索选择品牌归属"}
		}
	})
}

/**
 * 新增
 */
function add() {
	$("#titleForm").html("新增车系");
	$("#editFormDiv").show();
	showObjectOnForm("editForm", null);
	$("#vehcBrandID").select2("val","");
	var editForm = $("#editForm").validate();
	editForm.resetForm();
	editForm.reset();
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "key", "value": $("#key").val() }
	];
	dataGrid.fnSearch(conditionArr);
}
/**
 * 修改
 * @param {} id
 */
function edit(id) {
	$("#titleForm").html("修改车系");
	$.ajax({
		type: "GET",
		url:"PubVehcline/GetById",
		cache: false,
		data: { id: id },
		success: function (json) {
			$("#editFormDiv").show();
			showObjectOnForm("editForm", json);
			/*$("#vehcBrandID").select2("data", {
				id : json.vehcBrandID,
				text : json.vehcBrandName
			});*/
			$("#vehcBrandID").val(json.vehcBrandID);
			$("#vehcBrandIDName").val(json.vehcBrandName);
			var editForm = $("#editForm").validate();
			editForm.resetForm();
			editForm.reset();
			$("#id").val(id);
//			dataGrid._fnReDraw();
		},
		error: function (xhr, status, error) {
			$("#id").val("");
			return;
		}
    });
}

/**
 * 删除
 * @param {} id
 */
function del(id) {
	var comfirmData={
		tittle:"提示",
		context:"删除后不可恢复，确定要删除吗？",
		button_l:"否",
		button_r:"是",
		click: "deletePost('" + id + "')",
		htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	Zconfirm(comfirmData);
}

function deletePost(id){
	var data = {id: id};
	$.post("PubVehcline/DeletePubVehcline", data, function (status) {
		if (status.ResultSign == "Successful") {
			var message = status.MessageKey == null ? status : status.MessageKey;
            toastr.success(message, "提示");
			dataGrid._fnReDraw();
		} else {
			var message = status.MessageKey == null ? status : status.MessageKey;
			toastr.error(message, "提示");
		}
	});
}

/**
 * 保存
 */
function save() {
	var form = $("#editForm");
	if(!form.valid()) return;
	
	var url = "PubVehcline/CreatePubVehcline";
	if($("#id").val()) {
		url = "PubVehcline/UpdatePubVehcline";
	}
	
	var data = form.serializeObject();
	
	var vehcBrandID = data.vehcBrandID;
	if(vehcBrandID == null || vehcBrandID == ''){
		toastr.error("请选择品牌归属", "提示");
		return;
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
            
				$("#editFormDiv").hide();
				showObjectOnForm("editForm", null);
				dataGrid._fnReDraw();
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
            	dataGrid._fnReDraw();
			}	
		}
	});
}

/**
 * 取消
 */
function canel() {
	$("#editFormDiv").hide();
	$("#importExcelDiv").hide();
}
/***
 * 
 * 初始化  搜索下拉 
 */
function initSelectVehcBrandName() {
	$("#vehcBrandID").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubVehcbrand/GetBrand",
			dataType : 'json',
			data : function(term, page) {
				return {
					id : term
				};
			},
			results : function(data, page) {
				return {
					results : data
				};
			}
		}
	});
}

function initSelectKey() {
	$("#key").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubVehcline/GetBrandCars",
			dataType : 'json',
			data : function(term, page) {
				return {
					id : term
				};
			},
			results : function(data, page) {
				return {
					results : data
				};
			}
		}
	});
}
/**
 * 初始化城市下拉框
 */
function getSelectCity() {
	var parent = document.getElementById("inp_box1");
	var city = document.getElementById("vehcBrandID");
	var cityName = document.getElementById("vehcBrandIDName");
	getData(parent, cityName, city, "PubVehcline/GetBrand", 48, 0);
}
//下载模板  、需要加入 序列  
function downLoad(){
	window.location.href=base+"PubVehcline/DownLoad";
}

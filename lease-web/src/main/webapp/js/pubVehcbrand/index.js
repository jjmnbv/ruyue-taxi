var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	validateForm();
	initSelect();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "PubVehcbrand/GetPubVehcbrandByQuery",
        iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
        language: {
        	sEmptyTable: "暂无车辆品牌信息"
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
	        {mDataProp: "logo", sTitle: "品牌图标", sClass: "center", sortable: true ,
	        	"mRender": function (data, type, full) {
                    var html = "";
                    if(full.logo != null && full.logo !=""){
                    	html += '<img src="'+serviceAddress+'/'+full.logo+'" style="width:40px;height:40px;" />';
                    }else{
                    	html += '<img src="" style="width:40px;height:40px;" />';
                    }
                    return html;
                }
            },
	        {mDataProp: "name", sTitle: "品牌名称", sClass: "center", sortable: true }
	       
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
			logo: {required: true, maxlength: 20}
		},
		messages: {
			name: {required: "请填写品牌名称", maxlength: "最大长度不能超过8个字符"},
		    logo: {required: "请上传品牌图标", maxlength: "最大长度不能超过20个字符"}
		}
	})
}

/**
 * 新增
 */
function add() {
	$("#titleForm").html("新增品牌");
	$("#editFormDiv").show();
	$("#name").removeAttr("disabled");
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
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "key", "value": $("#key").val() }
	];
	dataGrid.fnSearch(conditionArr);
	$("#keys").val($("#key").val()) ;
}
/**
 * 修改
 * @param {} id
 */
function edit(id) {
	$("#titleForm").html("修改品牌");
	$.ajax({
		type: "GET",
		url:"PubVehcbrand/GetById",
		cache: false,
		data: { id: id },
		success: function (json) {
			$("#editFormDiv").show();
			$("#name").attr("disabled","true");
			showObjectOnForm("editForm", json);
			$("#imgShow").show();
        	$("#imgback").attr("src",serviceAddress+"/"+json.logo);
        	$("#logo").val(json.logo);
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
	$.post("PubVehcbrand/DeletePubVehcbrand", data, function (status) {
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
	
	var url = "PubVehcbrand/CreatePubVehcbrand";
	if($("#id").val()) {
		url = "PubVehcbrand/UpdatePubVehcbrand";
	}
	
	var data = form.serializeObject();
	
	var logo = data.logo;
	if(logo==null || logo ==''){
		toastr.error("请上传品牌图标", "提示");
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

/***
 * 
 * 初始化  搜索下拉 
 */
function initSelect() {
	$("#key").select2({
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
function exportData(){
//	$.ajax({
//		type: "GET",
//		url:"PubVehcbrand/ExportData",
//		success: function (json) {
//		}
//	});
	var key = $("#keys").val();
	window.location.href = base+"PubVehcbrand/ExportData?key="+key;

}
function cancel(){
	$("#key").select2("val","");
	search();
}

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
        sAjaxSource: "Dictionary/GetDictionaryByQuery",
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "type", sTitle: "字典类型", sClass: "center", sortable: true },
	        {mDataProp: "value", sTitle: "字典值", sClass: "center", sortable: true },
	        {mDataProp: "text", sTitle: "字典文本", sClass: "center", sortable: true },
	        {mDataProp: "sort", sTitle: "排序", sClass: "center", sortable: true },
	        {mDataProp: "desc", sTitle: "描述", sClass: "center", sortable: true },
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
                        html += '<button type="button" class="SSbtn blue" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
                        html += '&nbsp; <button type="button" class="SSbtn red"  onclick="del(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 删 除</button>';
                        return html;
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
			type: {required: true, maxlength: 20}
		},
		messages: {
			type: {required: "请输入类型", maxlength: "最大长度不能超过20个字符"}
		}
	})
}

/**
 * 新增
 */
function add() {
	$("#editFormDiv").show();
	showObjectOnForm("editForm", null);
	
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
 * 重置
 */
function reset() {
	$("#key").val("");
	dataGrid._fnReDraw();
}

/**
 * 修改
 * @param {} id
 */
function edit(id) {
	$.ajax({
		type: "GET",
		url:"Dictionary/GetById",
		cache: false,
		data: { id: id },
		success: function (json) {
		$("#editFormDiv").show();
			showObjectOnForm("editForm", json);
			$("#id").val(id);
			dataGrid._fnReDraw();
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
		context:"您确定要删除这条数据?",
		button_l:"否",
		button_r:"是",
		click: "deletePost('" + id + "')",
		htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	Zconfirm(comfirmData);
}

function deletePost(id){
	var data = {id: id};
	$.post("Dictionary/Delete", data, function (status) {
		if (status.ResultSign == "Successful") {
			var message = status.MessageKey == null ? status : status.MessageKey;
            toastr.success(message, "提示");
			dataGrid._fnReDraw();
		} else {
			
		}
	});
}

/**
 * 保存
 */
function save() {
	var form = $("#editForm");
	if(!form.valid()) return;
	
	var url = "Dictionary/Create";
	if($("#id").val()) {
		url = "Dictionary/Update";
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

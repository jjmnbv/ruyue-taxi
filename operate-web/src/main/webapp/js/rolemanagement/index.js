var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	window.treeObj;
	initGrid();
	validateForm();
	initEvent();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "RoleManagement/GetRoleManagementByQuery",
        iLeftColumn: 1,
        scrollX: true,
        columns: [
					{
					  //自定义操作列
					  "mDataProp": "ZDY",
					  "sClass": "center",
					  "sTitle": "操作",
					  "sWidth": 420,
					  "bSearchable": false,
					  "sortable": false,
					  "mRender": function (data, type, full) {
					      var html = "";
					      html += '<button type="button" style="float:left;margin-left:5px;" class="SSbtn blue" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
					      html += '&nbsp; <button type="button" style="float:left;margin-left:5px;" class="SSbtn orange"  onclick="assigndataauthority(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 数据分配</button>';
					      html += '&nbsp; <button type="button" style="float:left;margin-left:5px;" class="SSbtn orange"  onclick="assignfunctionauthority(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 功能分配</button>';
					      html += '&nbsp; <button type="button" style="float:left;margin-left:5px;" class="SSbtn red"  onclick="del(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 删 除</button>';
					      return html;
					  }
					},
	        {mDataProp: "id", sTitle: "id", sClass: "center", visible: false},
	        {mDataProp: "rolename", sTitle: "角色名称",sClass: "center", sortable: false },
	        {mDataProp: "roletypecaption", sTitle: "角色类别", "sWidth": 150,sClass: "center", sortable: false },
	        {
					  "mDataProp": "gxqy",
					  "sClass": "center",
					  "sTitle": "负责区域",
					  "sWidth": 350,
					  "bSearchable": false,
					  "sortable": false,
					  "mRender": function (data, type, full) {
					  		var lecompanys = full.lecompany;
					  		if(lecompanys){
					  			var citys = [];
					  			for(var i=0;i<lecompanys.length;i++){
					  				var city = lecompanys[i].citycaption;
					  				if(citys.indexOf(city)<0){
					  					citys.push(city);
					  				}
					  			}
					  			return showToolTips(citys.join("、"),15);
					  		}
					  	 return "-"
					  }
	        },
	        {mDataProp: "roledesc",sTitle: "角色描述", sClass: "center", sortable: false, 
	        	mRender : function(data, type, full) {
	  					return showToolTips(full.roledesc,15);
	  				}
	        }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

function initEvent(){

	$("#qx").click(function(){
		checkAll();
	});
	$("#fx").click(function(){
		uncheckAll();
	});
}

function checkAll(){
	if(window.treeObj){
		window.treeObj.checkAllNodes(true);
	}
}

function uncheckAll(){
	if(window.treeObj){
		var nodes = window.treeObj.getCheckedNodes(false);
		window.treeObj.checkAllNodes(false);
		for (var i=0,l=nodes.length;i<l;i++) {
			window.treeObj.checkNode(nodes[i], true, false);
		}
	}
}

/**
 * 表单校验
 */
function validateForm() {
	$("#editForm").validate({
		rules: {
			roletype: {required: true},
			rolename:{required: true,maxlength:20},
			roledesc:{maxlength: 50}
		},
		messages: {
			roletype: {required: "请输入角色类型"},
			rolename: {required: "请输入角色名称",maxlength:"最大输入20个字符"},
			roledesc:{maxlength: "最大输入50个字符"}
		}
	})
}

/**
 * 新增
 */
function add() {
	$("#editFormDiv").show();
	$("#title").html("新增角色");
	$("#editFormDiv_type").html("请选择角色类型");
	$("#roletype").css("display","");
	$("#roletypecaption").css("display","none");
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
		{ "name": "Key", "value": $("#key").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 修改
 * @param {} id
 */
function edit(id) {
	$.ajax({
		type: "GET",
		url:"RoleManagement/GetById",
		cache: false,
		data: { id: id },
		success: function (json) {
			$("#editFormDiv").show();
			$("#title").html("修改角色");
			$("#editFormDiv_type").html("角色类型");
			$("#roletype").css("display","none");
			$("#roletypecaption").css("display","");
			$("#roletypecaption").html(json.roletypecaption);
			var editForm = $("#editForm").validate();
			editForm.resetForm();
			editForm.reset();
			showObjectOnForm("editForm", json);
			$("#id").val(id);
		},
		error: function (xhr, status, error) {
			$("#id").val("");
			return;
		}
    });
}

/**
 * 分配数据权限
 * @param id
 */
function assigndataauthority(id){
	$.ajax({
		type: "GET",
		url:"RoleManagement/GetDataTreeById",
		cache: false,
		data: { id: id },
		success: function (json) {
			$("#datatreeFormDiv").show();
			$("#saveauthority").attr("onclick","assigndata();");
			$("#roletitle").html("分配数据权限【角色名称："+showToolTips(json.rolename,6)+"】");
			$("#roledataroleid").val(json.roleid);
			$("#fx").removeAttr("checked");
			$("#qx").removeAttr("checked");
			initdatatree(json.treenodelist);
		},
		error: function (xhr, status, error) {
			$("#id").val("");
			return;
		}
    });
}

function assigndata(){
	if(window.treeObj){
		var nodes = window.treeObj.getCheckedNodes(true);
		var lecompanyids = [];
		for(var i=0;i<nodes.length;i++){
			var lecompany = nodes[i];
			if(lecompany.id){
				lecompanyids.push(lecompany.id);
			}
		}
		var data = {
				roleid:$("#roledataroleid").val(),
				lecompanyids:lecompanyids
		};
		$.ajax({
			type: "POST",
			url:"RoleManagement/AssignDataAuthority",
			cache: false,
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			data:JSON.stringify(data),
			success: function (response) {
				var message = response.message == null ? response : response.message;
				if (response.status == "success") {
		      toastr.success(message, "提示");
		      $("#datatreeFormDiv").hide();
					dataGrid._fnReDraw();
				} else {
					toastr.error(message, "提示");
				}
			},
			error: function (xhr, status, error) {
				return;
			}
	  });
	}
}

/**
 * 分配功能权限
 * @param id
 */
function assignfunctionauthority(id){
	$.ajax({
		type: "GET",
		url:"RoleManagement/GetMenuTreeById",
		cache: false,
		data: { id: id },
		success: function (json) {
			$("#datatreeFormDiv").show();
			$("#saveauthority").attr("onclick","assignfunction();");
			$("#roletitle").html("分配功能权限【角色名称："+showToolTips(json.rolename,6)+"】");
			$("#roledataroleid").val(json.roleid);
			$("#fx").removeAttr("checked");
			$("#qx").removeAttr("checked");
			initdatatree(json.treenodelist);
		},
		error: function (xhr, status, error) {
			$("#id").val("");
			return;
		}
    });
}

function assignfunction(){
	if(window.treeObj){
		var nodes = window.treeObj.getCheckedNodes(true);
		var functionids = [];
		for(var i=0;i<nodes.length;i++){
			var node = nodes[i];
			if(node.id){
				functionids.push(node.id);
			}
		}
		var data = {
				roleid:$("#roledataroleid").val(),
				functionids:functionids
		};
		$.ajax({
			type: "POST",
			url:"RoleManagement/AssignFunctionAuthority",
			cache: false,
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			data:JSON.stringify(data),
			success: function (response) {
				var message = response.message == null ? response : response.message;
				if (response.status == "success") {
		      toastr.success(message, "提示");
		      $("#datatreeFormDiv").hide();
					dataGrid._fnReDraw();
				} else {
					toastr.error(message, "提示");
				}
			},
			error: function (xhr, status, error) {
				return;
			}
	  });
	}
}


function initdatatree(treenodes){
	var setting = {
			check : {
				enable : true
			},
			callback : {
			}
		};
	window.treeObj = $.fn.zTree.init($("#ztree"), setting, treenodes);
}

/**
 * 删除
 * @param {} id
 */
function del(id) {
	var comfirmData={
		tittle:"提示",
		context:"确定删除该角色？",
		button_l:"否",
		button_r:"是",
		click: "deletePost('" + id + "')",
		htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	Zconfirm(comfirmData);
}

function deletePost(id){
	var data = {id: id};
	$.post("RoleManagement/Delete", data, function (status) {
		var message = status.message == null ? status : status.message;
		if (status.status == "success") {
       toastr.success(message, "提示");
       dataGrid._fnReDraw();
		} else {
			 toastr.error(message, "提示");
		}
	});
}

/**
 * 保存
 */
function save() {
	$("#save").attr({"disabled":"disabled"});
	var form = $("#editForm");
	if(!form.valid()){
		$("#save").removeAttr("disabled");
		return;
	} 
	
	var url = "RoleManagement/Create";
	if($("#id").val()) {
		url = "RoleManagement/Update";
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
			var message = status.message;
			if (status.status == "success") {
        toastr.success(message, "提示");
				$("#editFormDiv").hide();
				showObjectOnForm("editForm", null);
				dataGrid._fnReDraw();
			} else {
				toastr.error(message, "提示");
			}	
			$("#save").removeAttr("disabled");
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			$("#save").removeAttr("disabled");
		}
	});
}

/**
 * 取消
 */
function canel() {
	$("#editFormDiv").hide();
	$("#datatreeFormDiv").hide();
}

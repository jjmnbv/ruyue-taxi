var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initForm();
	initGrid();
	validateForm();
	initEvent();
});

function initForm() {
	$("#account_query").select2({
        placeholder: "账号",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: "User/GetLeUserAccountBySelect",
            dataType: 'json',
            data: function (term, page) {
                return {
                	account: term
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
	
	$("#nickname_query").select2({
        placeholder: "姓名",
        minimumInputLength: 0,
        allowClear: true,
        ajax: {
            url: "User/GetLeUserNicknameBySelect",
            dataType: 'json',
            data: function (term, page) {
                return {
                	nickname: term
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });
}

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "User/GetUserByQuery",
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无管理员账号信息"
        },
        columns: [
					{
					  //自定义操作列
					  "mDataProp": "ZDY",
					  "sClass": "center",
					  "sTitle": "操作",
					  "sWidth": 250,
					  "bSearchable": false,
					  "sortable": false,
					  "mRender": function (data, type, full) {
					      var html = "";
					      html += '<button type="button" style="float:left;margin-left:5px;" class="SSbtn blue" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
					      html += '&nbsp; <button type="button" style="float:left;margin-left:5px;" class="SSbtn orange"  onclick="assignrole(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 分配角色</button>';
					      html += '&nbsp; <button type="button" style="float:left;margin-left:5px;" class="SSbtn red"  onclick="del(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 删 除</button>';
					      return html;
					  }
					},
	        {mDataProp: "id", sTitle: "id", sClass: "center", visible: false},
	        {mDataProp: "account", sTitle: "管理员账号", sClass: "center", sortable: false },
	        {mDataProp: "nickname", sTitle: "姓名", sClass: "center", sortable: false },
	        {mDataProp: "rolename", sTitle: "角色名称", sClass: "center", sortable: false },
	        {mDataProp: "telphone", sTitle: "电话", sClass: "center", sortable: false },
	        {mDataProp: "email", sTitle: "邮箱地址", sClass: "center", sortable: false }
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
			account: {required: true,minlength:3,maxlength: 20,NkN:true},
			userpassword:{required: true,minlength:8,maxlength: 16,checkPassword:true},
			nickname:{required: true,maxlength: 20},
			telphone:{required: true,minlength:11,maxlength:11,phon:true},
			email:{email:true}
		},
		messages: {
			account: {required: "请填写正确的账号",minlength:"最小长度不能少于3个字符", maxlength: "最大长度不能超过20个字符",NkN:"只允许输入数字，字母，下划线"},
			userpassword: {required: "请输入正确长度的密码",minlength:"最小长度不能少于8个字符", maxlength: "最大长度不能超过16个字符",checkPassword:"必须为字母、数字和符号(!@#%&$()*+)组成"},
			nickname:{required: "请输入姓名",maxlength: "最大长度不能超过20个字符"},
			telphone:{required: "请输入正确的号码",minlength:"请输入正确的号码",maxlength:"请输入正确的号码",phon:"请输入正确的号码"},
			email:{email:"请输入正确的邮箱格式"}
		}
	})
}

/**
 * 初始化事件
 */
function initEvent(){
	  $("#rolelist").on("click", function(evt){
		  if(evt.target.tagName!="LI"){
		  	return ;
		  }
			$("#rolelist li").removeAttr("id");
			$("#rolelist li").css("background-color","");
			$(evt.target).attr("id","select");
			$(evt.target).css("background-color","blue");
		});
	  
	  $("#telphone").keypress(function(evt){
			var code = evt.which;
			if(!(code>=48&&code<=57||code==45||code==43||code==41||code==40)){
				return false;
			}
		});
	  
	  $("#userpassword").keypress(function(evt){
			var key = evt.key;
			var Regx = /[\W]*$/;
			if(!Regx.test(key)){
				return false;
			}
		});
	  
	  $("#account").keypress(function(evt){
			var key = evt.key;
			var Regx = /^[A-Za-z0-9_]*$/;
			if(!Regx.test(key)){
				return false;
			}
		});
}
/*
 * 管理员账户的验证
 */
$.validator.addMethod("NkN",function(value,element){
	return this.optional(element) || /^[A-Za-z0-9_]+$/.test(value);   
},"只允许输入数字，字母，下划线")

jQuery.validator.addMethod("phon", function(value, element){
	var length = value.length;
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]){1})+\d{8})$/;
	return this.optional(element) || (length == 11 && myreg.test(value));
}, "请输入正确的号码");

/**
 * 校验密码,密码必须为8到16位数字字母符号组合，!@#%&$()*+ 0-9A-Za-z
 */
$.validator.addMethod("checkPassword", function(value, element, param) {
    var reg=/(?=.*[a-z])(?=.*\d)(?=.*[!@#%&$()*+])[a-z\d!@#%&$()*+]{8,16}/i;
    return this.optional(element) || reg.test(value);
}, "密码格式不正确");

/**
 * 新增
 */
function add() {
	$("#editFormDiv").show();
	$("#title").html("新增账号");
	showObjectOnForm("editForm", null);
	$("#account").removeAttr("disabled");
	$("#account").css("cursor","default");
	var editForm = $("#editForm").validate();
	editForm.resetForm();
	editForm.reset();
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
    		{"name":"accountid", "value":$("#account_query").val()},
    		{"name":"nicknameid", "value":$("#nickname_query").val()},
    		{"name":"roleid", "value":$("#roleid").val()}
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
		url:"User/GetById",
		cache: false,
		data: { id: id },
		success: function (json) {
			$("#editFormDiv").show();
			$("#account").attr("disabled", "disabled");
			$("#account").css("cursor","not-allowed");
			$("#title").html("修改账号");
			var editForm = $("#editForm").validate();
			editForm.resetForm();
			editForm.reset();
			showObjectOnForm("editForm", json);
			$("#id").val(id);
			$("#userpassword").val("");
		},
		error: function (xhr, status, error) {
			$("#id").val("");
			return;
		}
    });
}

/**
 * 分配角色
 * @param {} id
 */
function assignrole(id) {
	$.ajax({
		type: "GET",
		url:"User/GetRolesInfo",
		cache: false,
		data: { id: id },
		success: function (json) {
			$("#assignrole").show();
			if(json.rolename){
				$("#titlerolename").html("【当前角色："+json.rolename+"】");
			}else{
				$("#titlerolename").html("");
			}
			if(json.rolelist){
				var $ul = $("#rolelist");
				$ul.empty();
				for(var i=0;i<json.rolelist.length;i++){
					var roleobj = json.rolelist[i];
					if(json.roleid&&json.roleid==roleobj.id){
						$ul.append("<li id='select' style='background-color:blue;text-align:left;' data-value='"+roleobj.id+"'>"+roleobj.rolename+"</li>");
					}else{
						$ul.append("<li style='text-align:left;' data-value='"+roleobj.id+"'>"+roleobj.rolename+"</li>");
					}
				}
			}
			$("#id4assignrole").val(id);
		},
		error: function (xhr, status, error) {
			$("#id4assignrole").val("");
			return;
		}
    });
}

function doassignrole(){
	var roleid = $("#rolelist #select").attr("data-value");
	if(!roleid){
		toastr.warning("请选择一个角色", "提示");
		return ;
	}
	var data = {
			userid:$("#id4assignrole").val(),
			roleid:roleid
	};
	$.ajax({
		type: "POST",
		url:"User/AssignRole",
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		data:JSON.stringify(data),
		success: function (response) {
			var message = response.message == null ? response : response.message;
			if (response.status == "success") {
	      toastr.success(message, "提示");
	      $("#assignrole").hide();
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
	$.post("User/Delete", data, function (status) {
		if (status.status == "success") {
			var message = status.message == null ? status : status.message;
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
	
	var url = "User/Create";
	if($("#id").val()) {
		url = "User/Update";
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
	$("#assignrole").hide();
}

/**
 * 初始化查询
 */
function initSearch() {
	$("#account_query").select2("val", "");
	$("#nickname_query").select2("val", "");
	$("#roleid").val("");
	search();
}

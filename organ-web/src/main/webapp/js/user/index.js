jQuery.validator.addMethod("isMobile", function(value, element){
	var length = value.length;
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]){1})+\d{8})$/;
	return this.optional(element) || (length == 11 && myreg.test(value));
}, "请正确填写您的手机号码");
$(function(){
	initPageDom();
	//加载树形结构
	initTree();
	initcombotree();
	initEvent();
	validateForm();
	//用车规则
	window.rules = {};
});

//是否是超管
issuper;
//员工列表页的表格对象
var usersgrid;

var userspagegrid;
//部门列表的表格对象
var deptsgrid;

var deptspagegrid;
/**
 * 树形结构的配置
 */
var setting={
		view:{
			selectedMulti:false,
			showIcon:false,
			showLine: false,
			fontCss:getFontCss 
		},
		check:{
			enable:true
		},
		data:{
			simpleData:{
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: "-1"
				}
		},
		callback:{
			//右键菜单
			onRightClick:function(event, treeId, treeNode){
					showRMenu(event.clientX, event.clientY,treeNode);
			},
			//节点创建以后的回调
			onNodeCreated:function( event, treeId, treeNode){
						var $p = $(".tree_box #"+treeNode.parentTId+"") ;
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					if(treeNode.type=="1"){
						//公司
					}else if(treeNode.type=="2"){
						//部门
						$p.find("#"+treeNode.tId+"_span").append("<span>("+treeNode.usercount+")</span>");
//						$p.find("ul li").eq( treeObj.getNodeIndex(treeNode) ).append("<i>("+treeNode.usercount+")</i>");
					}else{
						//员工
					}
			},
			//展开后的回调
			onExpand: function( event, treeId, treeNode){

			},
			//节点点击后的回调
			onClick:function(event, treeId, treeNode){
				if(treeNode.type=="1"){
					//公司
					$("#infopage").hide();
					$("#deptpage").hide();
					$("#userspage").hide();
					$("#companypage").show();
				}else if(treeNode.type=="2"){
					//部门
					showDeptPage(treeNode.id,treeNode.name);
				}else{
					//员工
					showInfoPage(treeNode.id);
				}
			}
		}
};

/**
 * 初始化界面dom
 */
function initPageDom(){
	if(!issuper){
		//不是超管的话，角色不可维护
		$("#addorupdateuser_role").attr("disabled","disabled");
		$("#addorupdateuser_role").css("border-color","beige");
	}
	var url = "User/GetRoles"
	$.ajax({
		type:"post",
		url:url,
		cache:false,
		data:"{}",
		contentType : "application/json",
		dataType : "json",
		success:function(json){
			if(json!=null){
				var $rolecon = $("#addorupdateuser_role");
				for(var i=0;i<json.length;i++){
					var roleobj = json[i];
					$rolecon.append($("<option value='"+roleobj.id+"'>"+roleobj.name+"</option>"));
				}
			}
		},
		error:function(xhr, status, error){
			toastr.error("后台处理异常", "提示");
			return;
		}
  });
}

/**
 * 初始化事件
 */
function initEvent(){
	
	//搜索框
	$("#searchbtn").click(function(){
		 var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		 if(treeObj){
			 changeColor('treeDemo','name',$("#searchword").val());
		 }
	});
	
	$("#searchword").keyup(function(){
		if($(this).val().length>0){
			$("#search_close").show();
		}else{
			$("#search_close").hide();
		}
	});
	
	$("#search_close").click(function(){
		$("#searchword").val("");
		$(this).hide();
	});
	
	//新增部门
	$("#m_addPatent").click(function(){
		//隐藏菜单
		$("#rMenu ul li").hide();
		clearValidate4Dept();
		$("#addorupdatedept_title").html("新增部门");
		$(".popup_box").show();
		$("#addorupdatedept").show();
		$("#addorupdateuser").hide();
		$("#m_del").hide();
		$("#resetpwd").hide();
		$("#batch_change_dept").hide();
		$("#import_dialog").hide();
		$("#import_progress_dialog").hide();
	});
	
	$("#m_editNode").click(function(){
		//隐藏菜单
		$("#rMenu ul li").hide();
		clearValidate4Dept();
		$("#addorupdatedept_title").html("编辑部门");
		initDeptInfo();
		$(".popup_box").show();
		$("#addorupdatedept").show();
		$("#addorupdateuser").hide();
		$("#m_del").hide();
		$("#resetpwd").hide();
		$("#batch_change_dept").hide();
		$("#import_dialog").hide();
		$("#import_progress_dialog").hide();
	});
	
	//新增员工
	$("#m_addChild").click(function(){
		//隐藏菜单
		$("#rMenu ul li").hide();
		clearValidate4User();
		$("#addorupdateuser_title").html("新增员工");
		$(".popup_box").show();
		$("#addorupdateuser").show();
		$("#addorupdatedept").hide();
		$("#delete_dlg").hide();
		$("#resetpwd").hide();
		$("#batch_change_dept").hide();
		$("#import_dialog").hide();
		$("#import_progress_dialog").hide();
	});
	
	//编辑员工
	$("#m_editNode_user").click(function(){
		//隐藏菜单
		$("#rMenu ul li").hide();
		clearValidate4User(true);
		$("#addorupdateuser_title").html("编辑员工");
		initUserInfo();
		$(".popup_box").show();
		$("#addorupdateuser").show();
		$("#delete_dlg").hide();
		$("#addorupdatedept").hide();
		$("#resetpwd").hide();
		$("#batch_change_dept").hide();
		$("#import_dialog").hide();
		$("#import_progress_dialog").hide();
	});
	//删除员工
	$("#m_del").click(function(){
		$("#rMenu ul li").hide();
		initDeleteInfo();
		$(".popup_box").show();
		$("#delete_dlg").show();
		$("#addorupdateuser").hide();
		$("#addorupdatedept").hide();
		$("#resetpwd").hide();
		$("#batch_change_dept").hide();
		$("#import_dialog").hide();
		$("#import_progress_dialog").hide();
	});
	
	//批量删除员工
	$("#m_allDel").click(function(){
		$("#rMenu ul li").hide();
		initDeleteInfos();
		$(".popup_box").show();
		$("#delete_dlg").show();
		$("#addorupdateuser").hide();
		$("#addorupdatedept").hide();
		$("#resetpwd").hide();
		$("#batch_change_dept").hide();
		$("#import_dialog").hide();
		$("#import_progress_dialog").hide();
	});
	
	//部门取消
	$("#deptaddorupdate_cancel").click(function(){
		$(".popup_box").hide();
		$("#addorupdatedept").hide();
	});
	
	//员工取消
	$("#addorupdateuser_cancel").click(function(){
		$(".popup_box").hide();
		$("#addorupdateuser").hide();
	});
	
	//批量更改部门
	$("#m_bumen").click(function(){
		$("#rMenu ul li").hide();
		$("#batch_change_dept4input").combotree("setValue",window.selecttreeNode.getParentNode());
		$(".popup_box").show();
		$("#batch_change_dept").show();
		$("#delete_dlg").hide();
		$("#addorupdateuser").hide();
		$("#addorupdatedept").hide();
		$("#resetpwd").hide();
	});
	
	$("#batch_change_dept .sure").click(function(){
		changeUsersDept();
	});
	
	//添加人员选择规则时事件
	$("#addorupdateuser_rules").click(function(){
		$("#addorupdateuser_selectrules").show();
		//请求加载用车规则
		initUseCarRules($("#select_rulecon"));
	});
	//添加人员选择无规则事件
	$("#addorupdateuser_norule").click(function(){
		$("#addorupdateuser_selectrules").hide();
		$("#select_rulecon").empty();
	});
	
	$("#import_rules").click(function(){
		$("#import_selectrules").show();
		//请求加载用车规则
		initUseCarRules($("#import_rulecon"));
	});
	
	$("#import_norule").click(function(){
		$("#import_selectrules").hide();
		$("#import_rulecon").empty();
	});
	
	//添加员工时选择特殊员工，显示特殊司机信息
	$("#addorupdateuser_specialstate").change(function(){
		if($(this).val()==1){
			//特殊员工
			$("#specialstate_driver").show();
			$("#driverlable").show();
			//todo请求加载特殊司机
			addDriverNode();
		}else{
			//普通员工
			$("#specialstate_driver").empty();
			$("#specialstate_driver").hide();
			$("#driverlable").hide();
		}
	});
	
	//用车规则选择
	$("#addorupdateuser_selectrules").on("click", "span", function(){ 
		if($(this).hasClass("span_active")){
			$(this).removeClass("span_active");
		}else if( $(".tr_active .span_active").length<5){
			$(this).addClass("span_active");
		}
	}); 
	
	//用车规则选择
	$("#import_selectrules").on("click", "span", function(){ 
		if($(this).hasClass("span_active")){
			$(this).removeClass("span_active");
		}else if( $(".tr_active .span_active").length<5){
			$(this).addClass("span_active");
		}
	}); 
	 
	//重置密码
	$("#m_pass").click(function(){
		if(!window.selecttreeNode){
			toastr.warning("暂时无法获取用户信息", "提示");
			return ;
		}
		$("#rMenu ul li").hide();
		$(".popup_box").show();
		$.ajax({
			type:"GET",
			url:"User/GetUserInfo",
			cache:false,
			data:{userid:window.selecttreeNode.id},
			success:function(json){
				if(json&&json.status=="success"){
					if(json.userinfo){
						$("#resetpwdcontent").html("确定要重置"+json.userinfo.account+"的密码?");
					}
				}
			},
			error:function(xhr, status, error){
				toastr.error("无法获取员工信息", "提示");
				return;
			}
	  });
		$("#resetpwd").show();
		$("#delete_dlg").hide();
		$("#addorupdateuser").hide();
		$("#addorupdatedept").hide();
	});
	
	//重置密码取消
	$("#resetpwd_cancel").click(function(){
		$(".popup_box").hide();
		$("#resetpwd").hide();
	});
	
	$("#batch_change_dept .cancel").click(function(){
		$(".popup_box").hide();
		$("#batch_change_dept").hide();
	});
	
	//确认重置密码
	$("#resetpwd_save").click(function(){
		if(!window.selecttreeNode){
			toastr.warning("暂时无法获取用户信息", "提示");
			return ;
		}
		var url = "User/ResetPwd";
		var data = {userid:window.selecttreeNode.id};
		$.ajax({
			type:"post",
			url:url,
			cache:false,
			data:JSON.stringify(data),
			contentType : "application/json",
			dataType : "json",
			success:function(json){
				if(json&&json.status=="success"){
					toastr.success("已重置成功");
					$(".popup_box").hide();
					$("#resetpwd").hide();
				}else{
					toastr.warning(json.message);
				}
			},
			error:function(xhr, status, error){
				toastr.error("后台处理异常", "提示");
				return;
			}
	  });
	});
	
	//添加员工上传图片
	$('#fileupload').fileupload({
		  url:"FileUpload/uploadFile_new",
	    dataType: 'text',
		  autoUpload: true,
	    add:function(e, data){
        data.submit();
      },
	    done: function(e, data) {
	    		if(data&&data.result){
	    			var result = JSON.parse(data.result);
	    			if(result.status=="success"){
	    				$("#imgback").attr("src",result.basepath+"/"+result.message[0]);
	    				$("#imgpath").val(result.message[0]);
	    			}else{
	    				toastr.warning("上传头像失败", "提示");
	    			}
	    		}else{
	    			toastr.warning("上传头像失败", "提示");
	    		}
	    }
	});

	$("#clear").click(function(){
		$("#fileupload").val("");
		$("#imgback").attr("src","content/img/ing_tupian.png");
		$("#imgpath").val("");
	});
	
	$("#delete_dlg .cancel").click(function(){
		$(".popup_box").hide();
		$("#delete_dlg").hide();
	});
	
	//批量导入模板
	$("#import").click(function(){
		clearImportForm();
		$(".popup_box").show();
		$("#import_dialog").show();
		$("#addorupdateuser").hide();
		$("#addorupdatedept").hide();
		$("#delete_dlg").hide();
		$("#resetpwd").hide();
		$("#batch_change_dept").hide();
		$("#import_progress_dialog").hide();
	});
	
	$("#import_cancel").click(function(){
		$(".popup_box").hide();
		$("#import_dialog").hide();
	});
	
	$("#import_do").click(function(){
		doImportUser();
	});
	
	//点击上传文件
	$("#xuanze").click(function(){
		$("#xuanze_value").html("");
		$("#file").val("");
		$("#import_form #file").trigger("click");
	});
	
	$("#file").change(function() {
		changeOptions();
	});
	
	//导入成功关闭
	$("#import_progress_dialog .import_progress_close").click(function(){
		$(".popup_box").hide();
		$("#import_progress_dialog").hide();
		initTree();
	});
}

/**
 * 文件改变之后的处理
 */
function changeOptions(){
	if(!$("#file").val()){
		return ;
	}
	var formData = new FormData($("#import_form")[0]);
	var url = "User/GetFileSize";
	$.ajax({
		type:"post",
		url:url,
		cache:false,
		data: formData,  
    async: true,  
    contentType: false,  
    processData: false,  
    success: function (returndata) {
    	if(returndata.status=="success"){
    		var sizekb = returndata.size;
    		if(sizekb>5*1024*1024){
    			toastr.warning("选择的文件大于5m,请重新选择", "提示");
    			$("#file").val("");
    			$("#xuanze_value").html("");
    		}else{
    			var file_value = $("#file").val();
    			if(file_value&&file_value.lastIndexOf("\\")>0&&file_value.lastIndexOf("\\")+1<file_value.length){
    				$("#xuanze_value").html(file_value.substring(file_value.lastIndexOf("\\")+1));
    			}else{
    				$("#xuanze_value").html(file_value);
    			}
    		}
    	}
    },  
    error: function (returndata) {
    	
    }
   });
}

///****************ie9下获取文件大小通过后台有兼容问题************************/
//var isIE = /msie/i.test(navigator.userAgent) && !window.opera; 
//function changeOptions(target) {
//	var fileSize = 0; 
//	var filetypes =[".xls",".xlsx"]; 
//	var filepath = target.value; 
//	var filemaxsize = 1024*5;//5M 
//	if(filepath){
//		var isnext = false; 
//		var fileend = filepath.substring(filepath.indexOf(".")); 
//		if(filetypes && filetypes.length>0){ 
//			for(var i =0; i<filetypes.length;i++){ 
//				if(filetypes[i]==fileend){ 
//					isnext = true; 
//					break; 
//				}
//			} 
//		}
//		if(!isnext){
//			toastr.warning("不接受此文件类型！", "提示"); 
//			target.value ="";
//			$("#xuanze_value").html("");
//			return false; 
//		}
//	}else{ 
//		return false; 
//	} 
//	if (isIE && !target.files){
//		var filePath = target.value; 
//		var fileSystem = new ActiveXObject("Scripting.FileSystemObject"); 
//		if(!fileSystem.FileExists(filePath)){
//			$("#xuanze_value").html("");
//			return false; 
//		}
//		var file = fileSystem.GetFile (filePath); 
//		fileSize = file.Size; 
//	} else {
//		fileSize = target.files[0].size; 
//	}
//	var size = fileSize / 1024; 
//	if(size>filemaxsize){
//		toastr.warning("选择的文件大于5m,请重新选择", "提示");
//		target.value ="";
//		$("#xuanze_value").html("");
//		return false; 
//	}
//	$("#xuanze_value").html($("#file").val());
//} 
//
///****************************************/



/**
 * 导入人员
 */
function doImportUser(){
	var url = "User/ImportUser";
	var selectdata = $("#import_dept").combotree("getValue");
	//参数校验	
	var file = $("#file").val();
	if(!file){
		toastr.warning("导入前请先选择导入文件", "提示");
		return ;
	}
	if(!selectdata||(selectdata&&!selectdata.id)){
		toastr.warning("导入前请先选择导入的部门信息", "提示");
		return ;
	}
	
	setTimeout(function(){
		$("#import_progress_title").html("批量导入中");
		$("#import_progress_btn").hide();
		$("#progress_c").show();
		$("#import_message").hide();
		$("#import_dialog").hide();
		$("#import_progress_dialog").show();
		getImportProgress(0);
	},0);
	
	var formData = new FormData($("#import_form")[0]);
	formData.append("deptid",selectdata.id);
	var rulesid = [];
	if($("#import_rules:checked")&&$("#import_rules:checked").length>0){
		//存在用车规则
		if($("#import_selectrules .span_active")&&$("#import_selectrules .span_active").length>0){
			$("#import_selectrules .span_active").each(function(){
				var rulename = $(this).text();
				if(rulename){
					var temprules = window.rules[rulename];
					if(temprules){
						rulesid = rulesid.concat(temprules);
					}
				}
			});
		}
	}
	if(rulesid&&rulesid.length>0){
		formData.append("rulesid",rulesid);
	}
	
	$.ajax({
		type:"post",
		url:url,
		cache:false,
		data: formData,  
    async: true,  
    contentType: false,  
    processData: false,  
    success: function (returndata) {
    	$("#import_progress_title").html("提示");
    	$(".popup_box").show();
  		$("#import_progress_dialog").show();
    	if(returndata.status=="success"){
    		$("#progress_c").hide();
    		$("#import_message").show();
    		$("#import_message").html("批量导入员工成功");
    		$("#import_progress_btn").show();
    	}else{
    		$("#progress_c").hide();
    		$("#import_message").show();
    		$("#import_message").html(returndata.message);
    		$("#import_progress_btn").show();
    	}
    },  
    error: function (returndata) {
    	$(".popup_box").show();
  		$("#import_progress_dialog").show();
    	$("#import_progress_title").html("提示");
    	toastr.warning("服务器异常", "提示");
    }
   });
}

/**
 * 获取导入的进度信息
 */
function getImportProgress(currentprogress){
	if(!currentprogress){
		currentprogress = 0;
	}
	$.ajax({
		type:"get",
		url:"User/GetImportUserProgress",
		cache:false,
		data:{"user_progress":currentprogress},
		contentType : "application/json",
		dataType : "json",
		success:function(json){
			if(json&&json.status=="success"){
				$("#progress .progress_v").css("width",json.user_progress+"%");
				if(json.user_progress<100){
					setTimeout(function(){
						getImportProgress(json.user_progress);
					},1*1000);
				}
			}else{
				//导入异常，此处不用处理
			}
		},
		error:function(xhr, status, error){
			toastr.error("获取导入进度出错", "提示");
			return;
		}
  });
}

/**
 * 清除导入人员的界面的样式
 */
function clearImportForm(){
	$("#import_norule").click();
	$("#import_dept").combotree("setValue",{id:"",name:""});
	$("#xuanze_value").html("");
	$("#file").val("");
}

/**
 * 批量修改密码
 */
function changeUsersDept(){
	var nodes = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
	if(!nodes||nodes.length<1){
		toastr.warning("暂时无法更新部门信息", "提示");
		return ;
	}
	var data = {};
	data.userids = [];
	for(var i=0;i<nodes.length;i++){
		var node = nodes[i];
		data.userids.push(node.id);
	}
	var selectdata = $("#batch_change_dept4input").combotree("getValue");
	data.deptid = selectdata.id;
	var url = "User/ChangeUsersDept";
	$.ajax({
		type:"post",
		url:url,
		cache:false,
		data:JSON.stringify(data),
		contentType : "application/json",
		dataType : "json",
		success:function(json){
			if(json&&json.status=="success"){
				toastr.success("更新成功");
				initTree();
				initcombotree();
				$(".popup_box").hide();
				$("#batch_change_dept").hide();
			}else{
				toastr.warning(json.message);
			}
		},
		error:function(xhr, status, error){
			toastr.error("后台处理异常", "提示");
			return;
		}
  });
}


/**
 * 初始化部门信息
 */
function initDeptInfo(){
	if(!window.selecttreeNode){
		toastr.warning("暂时无法获取部门信息", "提示");
		return ;
	}
	$("#parentid").combotree("setValue",window.selecttreeNode.getParentNode());
	$("#deptid").val(window.selecttreeNode.id);
	$("#saveorupdatedept_deptname").val(window.selecttreeNode.name);
}

/**
 * 初始化删除信息
 */
function initDeleteInfo(){
	if(!window.selecttreeNode){
		toastr.warning("暂时无法获取信息", "提示");
		return ;
	}
	
	if(window.selecttreeNode.type=='2'){
		//部门
		$("#delete_dlg .popup_content").html("确定要删除部门？");
		$("#delete_dlg .sure").attr("onclick","deleteDept(window.selecttreeNode.id)");
	}else if(window.selecttreeNode.type=='3'){
		//员工
		$("#delete_dlg .popup_content").html("确定要删除员工？");
		$("#delete_dlg .sure").attr("onclick","deleteUser(window.selecttreeNode.id)");
	}
}

/**
 * 初始化批量删除员工的信息
 */
function initDeleteInfos(){
	if(!window.selecttreeNode){
		toastr.warning("暂时无法获取信息", "提示");
		return ;
	}
	//员工
	$("#delete_dlg .popup_content").html("确定要删除员工？");
	$("#delete_dlg .sure").attr("onclick","deleteUsers()");
}


/**
 * 删除部门
 */
function deleteDept(deptid){
	if(!deptid){
		toastr.warning("无法获取部门信息", "提示");
		return ;
	}
	var url = "User/DeleteDept";
	var data = {deptid:deptid};
	$.ajax({
		type:"post",
		url:url,
		cache:false,
		data:JSON.stringify(data),
		contentType : "application/json",
		dataType : "json",
		success:function(json){
			if(json&&json.status=="success"){
				toastr.success("删除成功");
				initTree();
				initcombotree();
				$(".popup_box").hide();
				$("#delete_dlg").hide();
			}else{
				toastr.warning(json.message);
			}
		},
		error:function(xhr, status, error){
			toastr.error("后台处理异常", "提示");
			return;
		}
  });
}

/**
 * 批量删除用户
 */
function deleteUsers(){
	var nodes = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
	if(!nodes||nodes.length<1){
		toastr.warning("暂时无法获取用户信息", "提示");
		return ;
	}
	var data = {};
	data.userids = [];
	for(var i=0;i<nodes.length;i++){
		var node = nodes[i];
		data.userids.push(node.id);
	}
	var url = "User/DeleteUsers";
	$.ajax({
		type:"post",
		url:url,
		cache:false,
		data:JSON.stringify(data),
		contentType : "application/json",
		dataType : "json",
		success:function(json){
			if(json&&json.status=="success"){
				toastr.success("删除成功");
				initTree();
				$(".popup_box").hide();
				$("#delete_dlg").hide();
			}else{
				toastr.warning(json.message);
			}
		},
		error:function(xhr, status, error){
			toastr.error("后台处理异常", "提示");
			return;
		}
  });
}


/**
 * 删除用户
 * @param userid
 */
function deleteUser(userid){
	if(!userid){
		toastr.warning("无法获取员工信息", "提示");
		return ;
	}
	var url = "User/DeleteUser";
	var data = {userid:userid};
	$.ajax({
		type:"post",
		url:url,
		cache:false,
		data:JSON.stringify(data),
		contentType : "application/json",
		dataType : "json",
		success:function(json){
			if(json&&json.status=="success"){
				toastr.success("删除成功");
				initTree();
				$(".popup_box").hide();
				$("#delete_dlg").hide();
			}else{
				toastr.warning(json.message);
			}
		},
		error:function(xhr, status, error){
			toastr.error("后台处理异常", "提示");
			return;
		}
  });
}

/**
 * 初始化用户信息
 */
function initUserInfo(){
	if(!window.selecttreeNode){
		toastr.warning("无法获取员工信息", "提示");
		return ;
	}
	$.ajax({
		type:"GET",
		url:"User/GetUserInfo",
		cache:false,
		data:{userid:selecttreeNode.id},
		success:function(json){
			if(json&&json.status=="success"){
				if(json.userinfo){
					//显示用户信息
					$("#addorupdateuser_parentid").combotree("setValue",window.selecttreeNode.getParentNode());
					$("#addorupdateuser_nickname").val(json.userinfo.nickname);
				  $("#addorupdateuser_phone").val(json.userinfo.account);
				  $("#addorupdateuser_sex").val(json.userinfo.sex);
					$("#addorupdateuser_specialstate").val(json.userinfo.type);
				  $("#addorupdateuser_role").val(json.userinfo.roleid);
				  $("#imgpath").val(json.userinfo.imgpath);
				  if(json.userinfo.img){
				  	$("#imgback").attr({"src":json.userinfo.img});
				  }else{
				  	$("#imgback").attr("src","content/img/ing_tupian.png");
				  }
				  $("#userid").val(json.userinfo.userid);
				  
				  //用车规则
					var rulecaption = [];
					if(json.userinfo.rules){
						for(var i=0;i<json.userinfo.rules.length;i++){
							var rule = json.userinfo.rules[i];
							if(rulecaption.indexOf(rule.name)<0){
								rulecaption.push(rule.name);
							}
						}
						
						if(json.userinfo.rules&&json.userinfo.rules.length>0){
							$("#addorupdateuser_rules").attr("checked","checked");
							$("#addorupdateuser_selectrules").show();
							//请求加载用车规则
							initUseCarRules($("#select_rulecon"),function(){
								$("#select_rulecon span").each(function(){
									var currule = $(this).text();
									if($.inArray(currule, rulecaption)>=0){
										$(this).click();
									}
								});
							});
						}
					}
					
					//特殊司机
					if(json.userinfo&&json.userinfo.type=="1"){
						$("#specialstate_driver").show();
						$("#driverlable").show();
						initDrivers(json.userinfo.userid);
					}
				}
			}
		},
		error:function(xhr, status, error){
			toastr.error("无法获取员工信息", "提示");
			return;
		}
  });
}

/**
 * 初始化用户的特殊司机信息
 */
function initDrivers(userid){
	var url = "User/GetUserDrivers"
	$.ajax({
		type:"get",
		url:url,
		cache:false,
		data:{userid:userid},
		contentType : "application/json",
		dataType : "json",
		success:function(json){
			if(json.status=="success"){
				var driverinfos = json.driverinfos;
				var flag = true;
				for(var companyid in driverinfos){
					var driversinfo = driverinfos[companyid];
					var driverids = [];
					for(var i=0;i<driversinfo.length;i++){
						driverids.push(driversinfo[i].driverid);
					}
					flag = false;
					(function(cpid,drids){
						addDriverNode(function(drivernode){
							var $select = drivernode.find(".form-control");
							$select.val(cpid);
							getSpecialstateDrivers(cpid,function(drivers){
								if(drivers&&drivers.length>0){
									var trs = [];
									var count = drivers.length%4==0?drivers.length/4:parseInt(drivers.length/4)+1;
									for(var i=0;i<count;i++){
										trs.push($("<tr></tr>"));
										trs[i].append("<td></td>");
										drivernode.find("tbody").empty();
										drivernode.find("tbody").append(trs[i]);
										for(var j=i*4;j<(i+1)*4;j++){
											if(j<drivers.length){
												var $td = $("<td class='sdriver'><p data-value='"+drivers[j].id+"'>"+drivers[j].name+"<br>"+drivers[j].phone+"</p></td>");
												if(drids.indexOf(drivers[j].id)>=0){
													$td.find("p").addClass("p_active");
												}
												trs[i].append($td);
												$td.click(function(){
													if($(this).find("p").hasClass("p_active")){
														$(this).find("p").removeClass("p_active");
													}else{
														$(this).find("p").addClass("p_active");
													}
												});
											}else{
												//空置，占位用
												var $td = $("<td class='noborder'><p><br></p></td>");
												trs[i].append($td);
											}
										}
									}
								}
							});
						});
					})(companyid,driverids);
				}
				if(flag){
					//初始化一个空的司机列表
					addDriverNode();
				}
			}
		},
		error:function(xhr, status, error){
			toastr.error("后台处理异常", "提示");
			return;
		}
  });
}

/**
 * 初始化用车规则
 */
function initUseCarRules($parentdom,callback){
		window.rules = {};
		var url = "User/GetUseCarRules"
		$.ajax({
			type:"get",
			url:url,
			cache:false,
			data:{},
			contentType : "application/json",
			dataType : "json",
			success:function(json){
				$parentdom.empty();
				if(json){
					var rulenames = [];
					for(var i=0;i<json.length;i++){
						var ruleobj = json[i];
						if(!window.rules.hasOwnProperty(ruleobj.name)){
							rulenames.push(ruleobj.name);
							window.rules[ruleobj.name] = [];
							$parentdom.append("<span>"+ruleobj.name+"</span>");
						}
						window.rules[ruleobj.name].push(ruleobj.id);
					}
				}else{
					toastr.warning("没有可用用车规则");
				}
				if(callback){
					callback();
				}
			},
			error:function(xhr, status, error){
				toastr.error("后台处理异常", "提示");
				return;
			}
	  });
}

/**
 * 请求选择特殊司机dom
 */
function addDriverNode(callback){
	var url = "User/GetValiableCompanyInfo"
	$.ajax({
		type:"post",
		url:url,
		cache:false,
		data:{},
		contentType : "application/json",
		dataType : "json",
		success:function(json){
			if(json){
				addSpDriver($("#specialstate_driver"),json,callback);
			}else{
				toastr.warning("没有可用的服务车企,无法分配特殊司机");
			}
		},
		error:function(xhr, status, error){
			toastr.error("后台处理异常", "提示");
			return;
		}
  });
}

/**
 * 清楚验证样式
 */
function clearValidate4Dept(clear){
	var addorupdatedept_form = $("#addorupdatedept_form").validate();
	addorupdatedept_form.resetForm();
	addorupdatedept_form.reset();
	var selectdata = $("#parentid").combotree("getValue");
	showObjectOnForm("addorupdatedept_form", null);
	if(clear){
		$("#parentid").combotree("setValue",{});
	}else{
		$("#parentid").combotree("setValue",selectdata);
	}
}

function clearValidate4User(clear){
	var addorupdateuser_form = $("#addorupdateuser_form").validate();
	addorupdateuser_form.resetForm();
	addorupdateuser_form.reset();
	$("#specialstate_driver").empty();
	$("#specialstate_driver").hide();
	$("#driverlable").hide();
	$("#addorupdateuser_norule").click();
	$("#clear").click();
	
	var addorupdateuser_parentid_data = $("#addorupdateuser_parentid").combotree("getValue");
	showObjectOnForm("addorupdateuser_form", null);
	if(clear){
		$("#addorupdateuser_parentid").combotree("setValue",{});
	}else{
		$("#addorupdateuser_parentid").combotree("setValue",addorupdateuser_parentid_data);
	}
}


/**
 * 表单校验
 */
function validateForm() {
	$("#addorupdatedept_form").validate({
		ignore:'',
		rules: {
			parentid: {required: true},
			saveorupdatedept_deptname:{required: true,minlength:2,maxlength:15},
		},
		messages: {
			parentid: {required: "请选择上级部门或者公司"},
			saveorupdatedept_deptname: {required: "请输入部门名称",minlength:"最小长度不能少于2个字符", maxlength: "最大长度不能超过15个字符"}
		}
	});
	
	$("#addorupdateuser_form").validate({
		ignore:'',
		rules: {
			parentid: {required: true},
			nickname:{required: true,maxlength:15},
			phone:{required: true,isMobile:true},
			sex:{required: true},
			specialstate:{required: true},
			role:{required: true}
		},
		messages: {
			parentid: {required: "请选择所属部门"},
			nickname:{required: "请输入员工姓名",maxlength:"最大长度不能超过20个字符"},
			phone:{required: "请输入手机号码",isMobile:"手机号码格式不正确"},
			sex:{required: "请选择性别"},
			specialstate:{required: "请选择员工类型"},
			role:{required:"请选择员工角色"}
		}
	});
}

function saveUser(){
	var form = $("#addorupdateuser_form");
	if(!form.valid()){
		return;
	}
	$("#addorupdateuser_save").attr({"disabled":"disabled"});
	var data = {};
	var userid = $("#userid").val();
	var url = "User/AddUser";
	var successmessage = "新增员工成功";
	if(userid){
		url = "User/UpdateUser";
		data.userid = userid;
		successmessage = "更新员工成功";
	}
	var selectdata = $("#addorupdateuser_parentid").combotree("getValue");
	data.deptid = selectdata.id;
	data.nickname = $("#addorupdateuser_nickname").val();
	data.phone = $("#addorupdateuser_phone").val();
	data.sex = $("#addorupdateuser_sex").val();
	data.specialstate = $("#addorupdateuser_specialstate").val();
	data.role=$("#addorupdateuser_role").val();
	data.imgpath = $("#imgpath").val();
	var rulesid = [];
	if($("#addorupdateuser_rules:checked")&&$("#addorupdateuser_rules:checked").length>0){
		//存在用车规则
		if($("#addorupdateuser_selectrules .span_active")&&$("#addorupdateuser_selectrules .span_active").length>0){
			$("#addorupdateuser_selectrules .span_active").each(function(){
				var rulename = $(this).text();
				if(rulename){
					var temprules = window.rules[rulename];
					if(temprules){
						rulesid = rulesid.concat(temprules);
					}
				}
			});
		}else{
			//没有选择用车规则，或者没有用车规则,提示无法保存
			toastr.warning("没有选择用车规则！", "提示");
			return ;
		}
		data.rulesid = rulesid;
	}
	
	var drivers = [];
	if(data.specialstate=='1'&&$("#specialstate_driver td .p_active")&&$("#specialstate_driver td .p_active").length>0){
		//特殊员工才获取特殊司机
		$("#specialstate_driver td .p_active").each(function(){
			drivers.push($(this).attr("data-value"));
		});
		data.drivers=drivers;
	}
	
	$.ajax({
		type:"post",
		url:url,
		cache:false,
		data:JSON.stringify(data),
		contentType : "application/json",
		dataType : "json",
		success:function(json){
			if(json.status=="success"){
				toastr.success(successmessage,"");
				$(".popup_box").hide();
				$("#addorupdateuser").hide();
				initTree();
			}else{
				toastr.warning(json.message, "提示");
			}
			$("#addorupdateuser_save").removeAttr("disabled");
		},
		error:function(xhr, status, error){
			toastr.error("后台处理异常", "提示");
			$("#addorupdateuser_save").removeAttr("disabled");
			return;
		}
  });
	
}

function savedept(){
	//校验空值
	var form = $("#addorupdatedept_form");
	if(!form.valid()){
		return;
	}
	$("#deptaddorupdate_save").attr({"disabled":"disabled"});
	var data = {};
	var deptid = $("#deptid").val();
	var url = "User/AddDept";
	var successmessage = "新增部门成功";
	if(deptid){
		url = "User/UpdateDept";
		data.deptid = deptid;
		successmessage = "更新部门成功";
	}
	
	var selectdata = $("#parentid").combotree("getValue");
	if(selectdata&&selectdata.type=="1"){
		//公司节点
		data.companyname = selectdata.name;
	}else{
		data.parentid = selectdata.id;
	}
	data.level = 1+selectdata.level;
	data.type = selectdata.type;
	data.deptname = $("#saveorupdatedept_deptname").val();
	$.ajax({
		type:"post",
		url:url,
		cache:false,
		data:JSON.stringify(data),
		contentType : "application/json",
		dataType : "json",
		success:function(json){
			if(json.status=="success"){
				toastr.success(successmessage,"");
				$(".popup_box").hide();
				$("#addorupdatedept").hide();
				initTree();
				initcombotree();
			}else{
				toastr.warning(json.message, "提示");
			}
			$("#deptaddorupdate_save").removeAttr("disabled");
		},
		error:function(xhr, status, error){
			toastr.error("后台处理异常", "提示");
			$("#deptaddorupdate_save").removeAttr("disabled");
			return;
		}
  });
}

//初始化上级树
function initcombotree() {
	//绑定所属部门树
	$("#parentid").combotree({
		  async : {
			enable : true,
			required : true,
			url : "User/GetDeptComboTreeNodes",
			autoParam : [],
			contentType : "application/json",
			dataType : "json"
		},
		data:{
			simpleData:{
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: "-1"
				}
		},
		callback: {
       onClick:function (event, treeId, treeNode){
      	 //本部门的上级id和自己id相同	
       }
    }
	});
	
	$("#addorupdateuser_parentid").combotree({
		  async : {
			enable : true,
			required : true,
			url : "User/GetDeptComboTreeNodes",
			autoParam : [],
			contentType : "application/json",
			dataType : "json"
		},
		data:{
			simpleData:{
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: "-1"
				}
		},
		callback: {
       onClick:function (event, treeId, treeNode){
      	 //本部门的上级id和自己id相同	、
      	 if(treeNode.type=="1"){
      		 //选择的是公司
      		 toastr.warning("公司下面无法添加员工", "提示");
      		 $("#addorupdateuser_parentid").combotree("setValue",{id:"",name:""});
      	 }
       }
    }
	});
	
	$("#import_dept").combotree({
		  async : {
			enable : true,
			required : true,
			url : "User/GetDeptComboTreeNodes",
			autoParam : [],
			contentType : "application/json",
			dataType : "json"
		},
		data:{
			simpleData:{
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: "-1"
				}
		},
		callback: {
       onClick:function (event, treeId, treeNode){
      	 //本部门的上级id和自己id相同	、
      	 if(treeNode.type=="1"){
      		 //选择的是公司
      		 toastr.warning("公司下面无法添加员工", "提示");
      		 $("#import_dept").combotree("setValue",{id:"",name:""});
      	 }
       }
    }
	});
	
	$("#batch_change_dept4input").combotree({
		  async : {
			enable : true,
			required : true,
			url : "User/GetDeptComboTreeNodes",
			autoParam : [],
			contentType : "application/json",
			dataType : "json"
		},
		data:{
			simpleData:{
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: "-1"
				}
		},
		callback: {
	     onClick:function (event, treeId, treeNode){
	    	 //本部门的上级id和自己id相同
             if(treeNode.type=="1"){
                 //选择的是公司
                 toastr.warning("公司下面无法添加员工", "提示");
                 $("#batch_change_dept4input").combotree("setValue",{id:"",name:""});
             }
	     }
	  }
	});
}

/**
 * 树形搜索高亮显示
 */
function getFontCss(treeId, treeNode) {  
  return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};  
}

/**
 * 改变树形节点的颜色
 * @param id
 * @param key
 * @param value
 */
function changeColor(id,key,value){  
  treeId = id;  
  updateNodes(false,null,key);  
  if(value != ""){  
      var treeObj = $.fn.zTree.getZTreeObj(treeId);  
      var nodeList = treeObj.getNodesByParamFuzzy(key, value);  
      if(nodeList && nodeList.length>0){  
          updateNodes(true,nodeList,key);  
      }  
  }  
}  

/**
 * 更新树形节点
 * @param highlight
 */
function updateNodes(highlight,pnodeList,key) {  
  var treeObj = $.fn.zTree.getZTreeObj(treeId);  
  var nodeList = pnodeList||treeObj.getNodesByParamFuzzy(key,"");
  for( var i=0; i<nodeList.length;  i++){
  		//部门不高亮
      nodeList[i].highlight = nodeList[i].type=="2"?false:highlight;
      treeObj.updateNode(nodeList[i]);
      if(nodeList[i].type=="2"){
      	var $p = $(".tree_box #"+nodeList[i].parentTId+"");
        $p.find("#"+nodeList[i].tId+"_span").append("<span>("+nodeList[i].usercount+")</span>");
        //部门不展开
        continue;
      }
      if(highlight){
      	treeObj.expandNode(nodeList[i].getParentNode(), true, false, true);
      }
  }
}

/**
 * 显示部门信息
 * @param deptid
 */
function showDeptPage(deptid,deptname){
	$.ajax({
		type:"GET",
		url:"User/HasChildDept",
		cache:false,
		data:{deptid:deptid},
		success:function(json){
			if(json){
				//显示部门列表
				$("#infopage").hide();
				$("#deptpage").show();
				$("#userspage").hide();
				$("#companypage").hide();
				$("#deptpagetitle").html(deptname);
				if(!deptsgrid){
					initDeptsGrid(deptid);
				}else{
					deptspagegrid.userQueryParam = [];
					var searchparam = [{"name":"deptid","value":deptid}];
					deptsgrid.fnSearch(searchparam);
				}
			}else{
				//没有子部门
				//显示员工列表
				$("#infopage").hide();
				$("#deptpage").hide();
				$("#userspage").show();
				$("#companypage").hide();
				$("#userspagetitle").html(deptname);
				if(!usersgrid){
					initUsersPageGrid(deptid);
				}else{
					userspagegrid.userQueryParam = [];
					var searchparam = [{name:"deptid",value:deptid}];
					usersgrid.fnSearch(searchparam);
				}
			}
		},
		error:function(xhr, status, error){
			toastr.error("无法获取员工信息", "提示");
			return;
		}
  });
}

/**
 * 初始化部门列表表格
 * @param deptid
 */
function initDeptsGrid(deptid){
	deptspagegrid = {
			id:"deptspagetable",
      sAjaxSource: "User/GetDeptByQuery",
      columns: [
				{mDataProp: "deptname", sTitle: "部门",	width:"140px", sClass: "center", sortable: false },
        {mDataProp: "deptusercount", sTitle: "人数",width:"140px",  sClass: "center", sortable: false },
        {
				  //自定义操作列
				  "mDataProp": "ZDY",
				  "sClass": "center",
				  "sTitle": "管理员",
				  "width":"240px", 
				  "bSearchable": false,
				  "sortable": false,
				  "mRender": function (data, type, full) {
				  	var managernames = [];
						if(full.deptmanager){
							for(var i=0;i<full.deptmanager.length;i++){
								var manager = full.deptmanager[i];
								managernames.push(manager.nickname);
							}
						}
			      return managernames.join(",");
				  }
				}
      ]
	}
	//初始化参数
	deptspagegrid.userQueryParam = [{name:"deptid",value:deptid}];
	deptsgrid = renderGrid(deptspagegrid);
//	deptspagegrid.userQueryParam = [];
}

/**
 * 初始化用户列表表格
 * @param deptid
 */
function initUsersPageGrid(deptid){
	userspagegrid = {
					id:"userspagetable",
	        sAjaxSource: "User/GetUserByQuery",
	        columns: [
						{mDataProp: "nickname", sTitle: "员工",	width:"140px", sClass: "center", sortable: false },
		        {mDataProp: "typecaption", sTitle: "分类",width:"140px",  sClass: "center", sortable: false },
		        {mDataProp: "rolename", sTitle: "角色名称", width:"140px", sClass: "center", sortable: false },
		        {
						  //自定义操作列
						  "mDataProp": "ZDY",
						  "sClass": "center",
						  "sTitle": "规则",
						  "width":"140px", 
						  "bSearchable": false,
						  "sortable": false,
						  "mRender": function (data, type, full) {
						  	var rulecaption = [];
								if(full.rules){
									for(var i=0;i<full.rules.length;i++){
										var rule = full.rules[i];
										if(rulecaption.indexOf(rule.name)<0){
											rulecaption.push(rule.name);
										}
									}
								}
					      return rulecaption.join(",");
						  }
						}
	        ]
	    };
			//初始化参数
			userspagegrid.userQueryParam = [{name:"deptid",value:deptid}];
			usersgrid = renderGrid(userspagegrid);
//			userspagegrid.userQueryParam = [];
}

/**
 * 显示用户信息
 * @param userid
 */
function showInfoPage(userid){
	$("#infopage").show();
	$("#deptpage").hide();
	$("#userspage").hide();
	$("#companypage").hide();
	$.ajax({
		type:"GET",
		url:"User/GetUserInfo",
		cache:false,
		data:{userid:userid},
		success:function(json){
			if(json&&json.status=="success"){
				if(json.userinfo){
					//显示用户信息
					if(json.userinfo.nickname){
						$("#nickname").text(json.userinfo.nickname);
					}else{
						$("#nickname").text("");
					}
					if(json.userinfo.sexcaption){
						$("#sex").text(json.userinfo.sexcaption);
					}else{
						$("#sex").text("");
					}
					if(json.userinfo.typecaption){
						$("#type").text(json.userinfo.typecaption);
					}else{
						$("#type").text("");
					}
					if(json.userinfo.account){
						$("#account").text(json.userinfo.account);
					}else{
						$("#account").text("");
					}
					if(json.userinfo.deptname){
						$("#deptname").text(json.userinfo.deptname);
					}else{
						$("#deptname").text("");
					}
					if(json.userinfo.rolename){
						$("#rolename").text(json.userinfo.rolename);
					}else{
						$("#rolename").text("");
					}
					if(json.userinfo.img){
						$("#img").attr({"src":json.userinfo.img});
					}else{
						$("#img").attr({"src":"content/img/ing_tupian.png"});
					}
					var rulecaption = [];
					if(json.userinfo.rules){
						for(var i=0;i<json.userinfo.rules.length;i++){
							var rule = json.userinfo.rules[i];
							if(rulecaption.indexOf(rule.name)<0){
								rulecaption.push(rule.name);
							}
						}
					}
					$("#rules").text(rulecaption.join(","));
				}
			}
		},
		error:function(xhr, status, error){
			toastr.error("无法获取员工信息", "提示");
			return;
		}
  });
}

/**
 * 显示右键菜单
 * @param type
 * @param x
 * @param y
 * @param treeNode
 */
//点击鼠标右键出现菜单，然后点击菜单，需要知道点击的节点信息
//用此变量保存这个信息
window.selecttreeNode;
function showRMenu(x, y,treeNode) {
	var type = treeNode.type;
	$("#rMenu ul").show();
	$("#rMenu ul li").show();
	window.selecttreeNode = treeNode;
	if (type=="1") {
		//公司
		$("#parentid").combotree("setValue",treeNode);
		$("#m_addChild").hide();
		$("#m_editNode").hide();
		$("#m_del").hide();
		$("#m_pass").hide();
		$("#m_bumen").hide();
		$("#m_allDel").hide();
		$("#m_editNode_user").hide();
	}else if(type=="2"){
		//部门
		$("#addorupdateuser_parentid").combotree("setValue",treeNode);
		$("#parentid").combotree("setValue",treeNode);
		$("#m_pass").hide();
		$("#m_bumen").hide();
		$("#m_allDel").hide();
		$("#m_editNode_user").hide();
	}else{
		//员工
		$("#m_addPatent").hide();
		$("#m_addChild").hide();
		$("#m_bumen").hide();	
		$("#m_allDel").hide();
		$("#m_editNode").hide();
		var nodes = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
		if(nodes&&nodes.length>1&&nodes.indexOf(treeNode)>=0){
			//大于1个右键员工
			$("#m_bumen").show();	
			$("#m_allDel").show();
			$("#m_editNode_user").hide();
			$("#m_editNode").hide();
			$("#m_del").hide();
			$("#m_pass").hide();
		}
	}
	if(!issuper){
		$("#m_addPatent").hide();
	}
	$("#rMenu").css({"top":y+"px", "left":x+"px", "visibility":"visible"});
	$("body").bind("mousedown", onBodyMouseDown);
}

/**
 * 隐藏右键菜单
 */
function hideRMenu() {
	$("#rMenu").css({"visibility": "hidden"});
	$("body").unbind("mousedown", onBodyMouseDown);
}

/**
 * 事件绑定
 */
function onBodyMouseDown(event){
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
		$("#rMenu").css({"visibility" : "hidden"});
	}
}

/**
 * 初始化树形结构
 */
function initTree(){
	$.ajax({
		type:"GET",
		url:"User/GetDeptUserTreeNodes",
		cache:false,
		data:{},
		success:function(json){
			if(json&&json.status=="success"){
				if(json.deptuserinfio){
					//初始化主界面树
					$.fn.zTree.init($("#treeDemo"), setting, json.deptuserinfio);
					$("#companypage").show();
					$("#deptpage").hide();
					$("#userspage").hide();
					$("#infopage").hide();
				}
			}
		},
		error:function(xhr, status, error){
			toastr.error("无法获取部门及员工信息", "提示");
			return;
		}
  });
}

/**
 * 添加一个选择特殊司机的dom节点
 * @param companyobjs
 */
function addSpDriver(parentdom,companyobjs,callback){
	var $selecttr = $("<tr></tr>");
	var $nulltd = $("<td ></td>");
//	$nulltd.css({"width":"56px"});
	$selecttr.append($nulltd);
	var $companycontd = $("<td colspan='4' style='padding-right:51px;'></td>");
	$selecttr.append($companycontd);
	var $select = $("<select class='form-control' style='width:373px;height:33px;'></select>");
	$companycontd.append($select);
	var selectedid = [];
	parentdom.find("select option:selected").each(function(){
		var value = $(this).attr("value");
		if(selectedid.indexOf(value)<0){
			selectedid.push(value);
		}
	});
	var flag = true;
	var selectcompanyid;
	for(var i=0;i<companyobjs.length;i++){
		if(selectedid.indexOf(companyobjs[i].id)<0&&flag){
			$select.append("<option value='"+companyobjs[i].id+"' selected>"+companyobjs[i].name+"</option>");
			flag = false;
			selectcompanyid = companyobjs[i].id;
		}else{
			$select.append("<option value='"+companyobjs[i].id+"'>"+companyobjs[i].name+"</option>");
		}
	}
	if(!selectcompanyid&&companyobjs&&companyobjs.length>0){
		selectcompanyid = companyobjs[0].id;
	}
	
	var $addbtn = $("<td class='td_add'><span style='line-height: 33px;'>增加</span></td>");
	$addbtn.click(function(){
		addSpDriver(parentdom,companyobjs);
		/**
		 * 刷新按钮
		 */
		refreshBtn(parentdom,companyobjs);
	});
	$selecttr.append($addbtn);
	var $deletebtn = $("<td class='td_del'><span style='line-height: 33px;'>删除</span></td>");
	$selecttr.append($deletebtn);
	
	//特殊司机列表
	var $tbodytr = $("<tbody></tbody>");
	//初始化特殊司机列表
	if(companyobjs&&!callback){
		(function(tbody){
			getSpecialstateDrivers(selectcompanyid,function(drivers){
				tbody.empty();
				if(drivers&&drivers.length>0){
					var trs = [];
					var count = drivers.length%4==0?drivers.length/4:parseInt(drivers.length/4)+1;
					for(var i=0;i<count;i++){
						trs.push($("<tr></tr>"));
						trs[i].append("<td></td>");
						tbody.append(trs[i]);
						for(var j=i*4;j<(i+1)*4;j++){
							if(j<drivers.length){
								var $td = $("<td class='sdriver'><p data-value='"+drivers[j].id+"'>"+drivers[j].name+"<br>"+drivers[j].phone+"</p></td>");
								trs[i].append($td);
								$td.click(function(){
									if($(this).find("p").hasClass("p_active")){
										$(this).find("p").removeClass("p_active");
									}else{
										$(this).find("p").addClass("p_active");
									}
								});
							}else{
								//空置，占位用
								var $td = $("<td class='noborder'><p><br></p></td>");
								trs[i].append($td);
							}
						}
					}
				}
			});
		})($tbodytr);
	}
	
	//	$tbodytr.hide();
	//根据变化，改变tobodytr样式
	(function(tobodytr){
		$select.change(function(){
			var selectedid = [];
			var self = $(this).find(":selected")[0];
			parentdom.find("select option:selected").each(function(){
				var value = $(this).attr("value");
				if(self!=this){
					selectedid.push(value);
				}
			});
			
			if(selectedid.indexOf($(this).val())>=0){
				toastr.warning("该服务车企已选择", "提示");
				//已经选择
				for(var i=0;i<companyobjs.length;i++){
					var canselect = companyobjs[i].id;
					if(selectedid.indexOf(canselect)<0){
						$(this).val(canselect);
						break;
					}
				}
			}
			tobodytr.empty();
			if($(this).val()){
				tobodytr.show();
				getSpecialstateDrivers($(this).val(),function(drivers){
					if(drivers&&drivers.length>0){
						var trs = [];
						var count = drivers.length%4==0?drivers.length/4:parseInt(drivers.length/4)+1;
						for(var i=0;i<count;i++){
							trs.push($("<tr></tr>"));
							trs[i].append("<td></td>");
							tobodytr.append(trs[i]);
							for(var j=i*4;j<(i+1)*4;j++){
								if(j<drivers.length){
									var $td = $("<td class='sdriver'><p data-value='"+drivers[j].id+"'>"+drivers[j].name+"<br>"+drivers[j].phone+"</p></td>");
									trs[i].append($td);
									$td.click(function(){
										if($(this).find("p").hasClass("p_active")){
											$(this).find("p").removeClass("p_active");
										}else{
											$(this).find("p").addClass("p_active");
										}
									});
								}else{
									//空的占位用
									var $td = $("<td class='noborder'><p><br></p></td>");
									trs[i].append($td);
								}
							}
						}
					}
				});
			}else{
				tobodytr.hide();
			}
		});
	})($tbodytr);
	var domno = $("<div></div>");
	(function(child){
		$deletebtn.click(function(){
			parentdom[0].removeChild(child[0]);
			refreshBtn(parentdom,companyobjs);
		});
	})(domno);
	domno.append($selecttr);
	domno.append($tbodytr);
	parentdom.append(domno);
	refreshBtn(parentdom,companyobjs);
	if(callback){
		callback(domno);
	}
}

function refreshBtn(parentdom,companyobjs){
	var childrens = parentdom.children("div");
	if(childrens.length==1){
		//根据列表决定有没有新增按钮，删除不需要
		childrens.find(".td_del").hide();
		childrens.find(".td_add").hide();
		if(companyobjs&&companyobjs.length>childrens.length){
			childrens.find(".td_add").show();
		}
	}else if(childrens.length>1){
		//显示最后一个div的新增按钮,显示所有按钮的删除
		childrens.find(".td_add").hide();
		childrens.find(".td_del").show();
		if(companyobjs&&companyobjs.length>childrens.length){
			childrens.last().find(".td_add").show();
		}
	}
}

/**
 * 获取租赁公司的特殊司机
 * @param companyid
 * @param calback
 */
function getSpecialstateDrivers(companyid,calback){
	$.ajax({
		type:"GET",
		url:"User/GetSpecialstateDrivers",
		cache:false,
		data:{companyid:companyid},
		success:function(json){
			if(calback){
				//执行特殊司机添加到dom中
				calback(json);
			}
		},
		error:function(xhr, status, error){
			toastr.error("无法获取特殊司机信息", "提示");
			return;
		}
  });
}






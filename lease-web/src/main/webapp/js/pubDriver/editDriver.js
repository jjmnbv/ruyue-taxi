var addServiceOrgGrid;
var srctag1= "" ;
$(function() {
	validateForm();
	initSelectQueryServiceOrgFullName();
	initSelectQueryServiceOrgCity();
	getSelectCity();
	$(".close").click(function(){
		$(".select2-drop-active").css("display","none");
//		$("#select2-drop").remove();
	})
	if ($('input[name="idEntityType"]:checked').val() == '1') {
		$("#serviceOrg").show();
		$.ajax({
			type : "GET",
			url : "PubDriver/GetById",
			cache : false,
			data : {
				id : $("#id").val()
			},
			success : function(json) {
				var serviceOrgId =  json.serviceOrgId;
				var serviceOrgName = json.serviceOrgName;
				var html = "";
				var serviceOrgId1 = serviceOrgId.split(",");
				var serviceOrgName1 = serviceOrgName.split(",");
				for(var i=0;i<serviceOrgId1.length;i++){
					html+="<li data-value='"+serviceOrgId1[i]+"'>"+serviceOrgName1[i]+"</li>";
				}
				$("#serviceOrgName").html(html);
			}
		});
	}
	$("#serviceOrgName").click(function(e){
		var srctag =  e.target;
		if("LI"==srctag.tagName || "li"==srctag.tagName){
			var value = $(srctag).attr("data-value");
			$("#serviceOrgName li").css({"background-color":"white"});
			$(srctag).attr("style","background-color: #6495ED;");
			srctag1 = srctag;
			//alert(value);
		}
	});
});

function updateServiceOrg(){
	//alert(srctag1);
	if(srctag1 == ''){
		toastr.error("请选择一家机构", "提示");
	}else{
//		srctag1.parentNode.removeChild(srctag1);
		$(srctag1).remove();
	}
}

// 绑定事件，普通司机隐藏服务机构 特殊司机显示服务机构
function res(cs) {
	if (cs == 0) {
        $("#serviceOrgName").html("");
		$("#serviceOrg").hide();
	}
	if (cs == 1) {
		$("#serviceOrg").show();
	}
}
/**
 * 表格初始化
 */
function addServiceOrgInitGrid() {
	var gridObj = {
		id : "addServiceOrgGrid",
		sAjaxSource : "PubDriver/GetServiceOrgByQuery",
		iLeftColumn: 1,//（固定表头，1代表固定几列）
		userHandle: function(oSettings, result) {
			var flag = true;
        	for(var index in result.aaData) {
        		var orderResult = result.aaData[index];
	        	if(map.hasOwnProperty(orderResult.id)) {
	        		$("#checkFullName" + orderResult.id).attr("checked","true");
	        	}
	        	if(map[orderResult.id] != orderResult.fullName){
        			flag = false;
        		}
        	}
        	
        	var page = getPage();
        	if (map.hasOwnProperty(page)) {
        		$(".checkAll").prop("checked", true);
        	} else {
        		$(".checkAll").removeAttr("checked");
        	}
        	
        	if(flag){
        		$(".checkAll").prop("checked", true);
        	}
        },
        scrollX: true,//（加入横向滚动条）
		columns : [
//				{
//					mDataProp : "id",
//					sTitle : "Id",
//					sClass : "center",
//					visible : false
//				},
				{
					// 自定义操作列  <input type='checkbox' id='checkAll' name='checkAll' onclick='onClickAll(this)'></input>
					"mDataProp" : "ZDY",
					"sClass" : "center",
					"sTitle" : "<input type='checkbox' checked='checked' class='checkAll' name='checkAll' onclick='onClickAll(this)'></input>全选",
					"sWidth" : 100,
					"bSearchable" : false,
					"sortable" : false,
					"mRender" : function(data, type, full) {
						var html = "";
						if(map[full.id] == full.fullName){
							html += '<input type="checkbox" checked="checked" id="checkFullName' + full.id + '" name="checkFullName" showfullname="'
							+ full.fullName
							+ '" value="'
							+ full.id
							+ '" onclick="onClickHander(this)"></input>';
						}else{
							html += '<input type="checkbox" id="checkFullName' + full.id + '" name="checkFullName" showfullname="'
							+ full.fullName
							+ '" value="'
							+ full.id
							+ '" onclick="onClickHander(this)"></input>';
						}
						
						return html;
					}
				}, {
					mDataProp : "fullName",
					sTitle : "机构名称",
					sClass : "center",
					sortable : true
				}, {
					mDataProp : "citycaption",
					sTitle : "所属城市",
					sClass : "center",
					sortable : true
				}, {
					mDataProp : "billType",
					sTitle : "结算方式",
					sClass : "center",
					sortable : true
				} ]
	};

	addServiceOrgGrid = renderGrid(gridObj);
}

/**
 * 查询
 */
function query() {
	var conditionArr = [ {
		"name" : "queryServiceOrgFullName",
		"value" : $("#queryServiceOrgFullName").val()
	}, {
		"name" : "queryServiceOrgCity",
		"value" : $("#queryServiceOrgCity").val()
	} ];
	addServiceOrgGrid.fnSearch(conditionArr);
}
/**
 * 表单校验
 */
function validateForm() {
	$("#editDriverForm").validate({
		rules : {
			jobNum : {
				required : true,
				jobNumid : true
			},
			name : {
				required : true,
				maxlength : 20
			},
			phone : {
				required : true,
				digits : true,
				phone : true
			},
			driverYears : {
				required : true,
				driverYears : true,
				driverYearss : true
			},
			driversNum : {
				required : true,
				minlength: 18
			},
			idCardNum : {
				required : true,
				idCardNo : true
			},
			driversType :{
				required : true,
				driversType :true
			},
			cityName : {
				required : true
			},
			vehicleType : {
				required : true
			},
			belongleasecompany : {
				belongleasecompany : true
			}
		},
		messages : {
			jobNum : {
				required : "请输入正确的资格证号",
			},
			name : {
				required : "请输入司机姓名",
				maxlength : "最大长度不能超过20个字符"
			},
			phone : {
				required : "请输入手机号码"
			},
			driverYears : {
				required : "请输入驾驶工龄"
			},
			driversNum : {
				required : "请输入司机驾驶证",
				minlength : "长度必须为18个字符"
			},
			idCardNum : {
				required : "请填写正确格式的身份证号"
			},
			driversType :{
				required : "请输入驾驶类型"
			},
			cityName : {
				required : "请输入所属城市"
			},
			vehicleType : {
				required : "请选择司机类型"
			}
		}
	})
}
/**
 * 返回取消提示
 * */
function callBack(id){
	var comfirmData={
		tittle:"提示",
		context:"您当前未点击保存，确认放弃保存吗？",
		button_l:"否",
		button_r:"是",
		click: "callBack1()",
		htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	Zconfirm(comfirmData);
};
function callBack1() {
	// history.go(-1);
	// history.back();
	window.location.href = base+"PubDriver/Index";
}
// 1
$('#fileupload').fileupload(
		{
			url : "PubDriver/UploadFile",
			dataType : 'json',
			done : function(e, data) {
				if (data.result.status == "success") {
					$("#imgShow").show();
					$("#imgback")
							.attr(
									"src",
									data.result.basepath + "/"
											+ data.result.message[0]);
					$("#headPortraitMax").val(data.result.message[0]);
				}else{
		        	toastr.error(data.result.error, "提示");
		        }
			}
		});
$("#imgback").click(function() {
	$("#fileupload").click();
});
$("#clear").click(function() {
	$("#fileupload").val("");
	$("#imgback").attr("src", "img/pubDriver/zhengmian.jpg");
	// $("#imgShow").hide();
	$("#headPortraitMax").val("");
});
// 2
$('#fileupload2').fileupload(
		{
			url : "PubDriver/UploadFile",
			dataType : 'json',
			done : function(e, data) {
				if (data.result.status == "success") {
					$("#imgShow2").show();
					$("#imgback2")
							.attr(
									"src",
									data.result.basepath + "/"
											+ data.result.message[0]);
					$("#driverPhoto").val(data.result.message[0]);
				}else{
		        	toastr.error(data.result.error, "提示");
		        }
			}
		});
$("#imgback2").click(function() {
	$("#fileupload2").click();
});
$("#clear2").click(function() {
	$("#fileupload2").val("");
	$("#imgback2").attr("src", "img/pubDriver/zhengmian.jpg");
	// $("#imgShow2").hide();
	$("#driverPhoto").val("");
});
// 3
$('#fileupload3').fileupload(
		{
			url : "PubDriver/UploadFile",
			dataType : 'json',
			done : function(e, data) {
				if (data.result.status == "success") {
					$("#imgShow3").show();
					$("#imgback3")
							.attr(
									"src",
									data.result.basepath + "/"
											+ data.result.message[0]);
					$("#idCardFront").val(data.result.message[0]);
				}else{
		        	toastr.error(data.result.error, "提示");
		        }
			}
		});
$("#imgback3").click(function() {
	$("#fileupload3").click();
});
$("#clear3").click(function() {
	$("#fileupload3").val("");
	$("#imgback3").attr("src", "img/pubDriver/zhengmian.jpg");
	// $("#imgShow3").hide();
	$("#idCardFront").val("");
});
// 4
$('#fileupload4').fileupload(
		{
			url : "PubDriver/UploadFile",
			dataType : 'json',
			done : function(e, data) {
				if (data.result.status == "success") {
					$("#imgShow4").show();
					$("#imgback4")
							.attr(
									"src",
									data.result.basepath + "/"
											+ data.result.message[0]);
					$("#idCardBack").val(data.result.message[0]);
				}else{
		        	toastr.error(data.result.error, "提示");
		        }
			}
		});
$("#imgback4").click(function() {
	$("#fileupload4").click();
});
$("#clear4").click(function() {
	$("#fileupload4").val("");
	$("#imgback4").attr("src", "img/pubDriver/beimian.jpg");
	// $("#imgShow4").hide();
	$("#idCardBack").val("");
});
//
/**
 * 保存
 */
function save() {
	var values = [];
	$("#serviceOrgName li").each(function(){
		values.push($(this).attr("data-value"));
	});
	$("#serviceOrgId").val(values.join(","));
	
	var form = $("#editDriverForm");
	if (!form.valid())
		return;
	
	//身份证验证
	var idCardNum = $("#idCardNum").val();
//	if(!idCardNum || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(idCardNum)){
//		toastr.error("身份证号格式错误", "提示");
//		return;
//	}
	
	//联系方式
	var phone = $("#phone").val();
//	var message = "请输入有效的手机号码";
//	if(regPhone(phone)){
//		toastr.error(message, "提示");
//		return;
//	}
	var id = $("#id").val();
	//资格证号 验证
	var jobNum = $("#jobNum").val();
//	var re =  /^[0-9a-zA-Z]*$/g;
//	if (!re.test(jobNum)){  
//		toastr.error("资格证号只能是数字和字母", "提示");
//	    return;  
//	} 
	var flag = false;
	var data = {
		id : id,
		jobNum : jobNum
	}
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : "PubDriver/CheckPubDriverJobNum",
		data : data,
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status > 0) {
				toastr.error(jobNum+"资格证号已存在", "提示");
				flag = true;
			}
		}
	});
	if(flag){
		return;
	}
	//手机号 验证
	var flag1 = false;
	var data1 = {
		id : id,
		phone : phone
	}
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : "PubDriver/CheckPubDriverPhone",
		data : data1,
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status > 0) {
				toastr.error(phone+"手机号码已存在", "提示");
				flag1 = true;
			}
		}
	});
	if(flag1){
		return;
	}
	//驾驶证号码 验证
	var driversNum = $("#driversNum").val();
//	var re =  /^[0-9a-zA-Z]*$/g;
//	if (!re.test(driversNum)){  
//		toastr.error("请输入正确的驾驶证号码", "提示");
//	    return;  
//	} 
	
	var flag2 = false;
	var data2 = {
		id : id,
		driversNum : driversNum
	}
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : "PubDriver/CheckPubDriverDriversNum",
		data : data2,
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status > 0) {
				toastr.error(driversNum+"驾驶证号码已存在", "提示");
				flag2 = true;
			}
		}
	});
	if(flag2){
		return;
	}
	//身份证号码 验证
	var flag3 = false;
	var data3 = {
		id : id,
		idCardNum : idCardNum
	}
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : "PubDriver/CheckPubDriverIdCardNum",
		data : data3,
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status > 0) {
				toastr.error(idCardNum+"身份证号码已存在", "提示");
				flag3 = true;
			}
		}
	});
	if(flag3){
		return;
	}
	
	var url = "PubDriver/CreatePubDriver";
	if ($("#id").val()) {
		url = "PubDriver/UpdatePubDriver";
	}

	var data = form.serializeObject();
	var idEntityType = data.idEntityType;
	var vehicleType = data.vehicleType;
	if(vehicleType == '0'){
		if(idEntityType == '1'){
			if($("#serviceOrgId").val() == null || $("#serviceOrgId").val() == ''){
				toastr.error("请选择特殊司机的服务机构", "提示");
				return;
			}
		}
	}
	//驾驶工龄
//	var driverYears = data.driverYears;
//	var isNum = /^\d+(\.\d+)?$/;
//	if(!isNum.test(driverYears)){
//	    toastr.error("请输入正确的驾驶工龄", "提示");
//	    return;
//	}else{
//		if(url==='PubDriver/CreatePubDriver'){
//			if(driverYears.length>2){
//				toastr.error("驾驶工龄只能为2个字符", "提示");
//			    return;
//			}
//		}else{
//			if(driverYears.length>4){
//				toastr.error("驾驶工龄只能为2个字符", "提示");
//			    return;
//			}
//		}
//	}
	
	var city = data.city;
	if(city == null || city == ''){
		toastr.error("请选择所属城市", "提示");
		return;
	}
	
	//司机照片
	var headPortraitMax = data.headPortraitMax;
	if(headPortraitMax == null || headPortraitMax == ''){
		toastr.error("请上传司机照片", "提示");
		return;
	}
	
	//司机驾驶证
	var driverPhoto = data.driverPhoto;
	if(driverPhoto == null || driverPhoto == ''){
		toastr.error("请上传司机驾驶证", "提示");
		return;
	}
	
	//司机身份证
	var idCardFront = data.idCardFront;
	if(idCardFront == null || idCardFront == ''){
		toastr.error("请上传司机身份证正面", "提示");
		return;
	}
	
	//司机身份证
	var idCardBack = data.idCardBack;
	if(idCardBack == null || idCardBack == ''){
		toastr.error("请上传司机身份证反面", "提示");
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
				toastr.options.onHidden = function() {
					window.location.href = base+"PubDriver/Index";
				}
				toastr.success(message, "提示");

			} else {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.error(message, "提示");
			}
		}
	});
}
// 选择机构用户
var map = {};
//var pagemap = {};
function onClickHander(obj) {
	var tempId = "";
	var tempNmae = "";
	if (obj.checked) {
		tempId = obj.value;
		tempNmae = $(obj).attr("showfullname");
		map[tempId] = tempNmae;
	} else {
		tempId = obj.value;
		delete map[tempId];
	}

}
function onClickAll(obj){
	var a = document.getElementsByTagName("input");
	var tempId = "";
	//var page = $("li.paginate_button.active")[1].children[0].innerHTML;
	var page = getPage();
	if (obj.checked) {
		for(var i = 0;i < a.length; i++) {
			if(a[i].name == "checkFullName" && a[i].type == "checkbox" && a[i].checked == false) {
				a[i].checked = true;
				tempId = a[i].value;
				tempNmae = a[i].getAttribute("showfullname");
				map[tempId] = tempNmae;
				//pagemap[tempId] = tempId;
			}
		}
		
	} else {
		for(var i = 0;i < a.length; i++) {
			if(a[i].name == "checkFullName" && a[i].type == "checkbox" && a[i].checked == true) {
				a[i].checked = false;
				tempId = a[i].value;
				delete map[tempId];
				//delete pagemap[tempId];
			}
		}
	}
	
//	if(obj.checked){
//		$("input[name=checkFullName]").each(function() {  
//            $(this).prop("checked",true);
//            
//            var tempId = "";
//        	var tempNmae = "";
//        	if (obj.checked) {
//        		tempId = obj.value;
//        		tempNmae = $(obj).attr("showfullname");
//        		map[tempId] = tempNmae;
//        	} else {
//        		tempId = obj.value;
//        		delete map[tempId];
//        	}
//        	
//        });  
//	}else{
//		$("input[name=checkFullName]").each(function() {  
//            $(this).prop("checked",false);
//        }); 
//	}
	
//	var page = getPage();
//	if (page) {
//		$("#checkAllManual").prop("disabled",false);
//	} else {
//		$("#checkAllManual").prop("disabled",true);
//	}
//	if (pagemap.hasOwnProperty(page)) {
//		$("#checkAllManual").prop("checked",true);
//	} else {
//		$("#checkAllManual").prop("checked",false);
//	}
	
}
/**
 * 获取当前页的页数
 */
function getPage() {
	var page;
	var lis = $("#addServiceOrgGrid_paginate>ul>li");
	for (var i=0;i<lis.length;i++) {
		if (lis[i].className == "paginate_button active") {
			page = lis[i].children[0].innerHTML;
			break;
		}
	}
	return page;
}

function haspro(map) {
	if (!map) {
		return false
	}
	for ( var pro in map) {
		if (!pro) {
			return false
		}
		return true
	}
}
// 赋值到文本域
//var serviceOrgId = "";
//var serviceOrgName = "";
function saveOrg() {
	if (haspro(map)) {
		var html = "";
		for ( var i in map) {
//			console.dir(i);// key
//			console.dir(map[i]);// value
//			serviceOrgId += i + ",";
//			serviceOrgName += map[i] + ",";
			var flag = $("#serviceOrgName li:contains('"+map[i]+"')").length>0;
			/*$("#serviceOrgName li").each(function (){
				if($(this).text()==map[i]){
					flag = true;
					return;
				}
			});*/
			if(flag){
				
			}else{
				html+="<li data-value='"+i+"'>"+map[i]+"</li>"
			}
			
		}
//		$("#serviceOrgId").val(serviceOrgId.substring(0, (serviceOrgId.length - 1)));
//		$("#serviceOrgName").val(serviceOrgName.substring(0, (serviceOrgName.length - 1)));
		$("#serviceOrgName").append(html);
		$("#addServiceOrg").hide();
	} else {
		toastr.error("请选择机构", "提示");
	}
}

function addServiceOrg() {
	//pagemap = {};
	map = {};
	serviceOrgId = "";
	serviceOrgName = "";
	$("#serviceOrgId").val("");
	$("#serviceOrgName").val("");
	$("#addServiceOrg").show();
	if (!addServiceOrgGrid) {
		addServiceOrgInitGrid();
	}
	addServiceOrgGrid._fnReDraw();
}

function cancle() {
	$("#addServiceOrg").hide();
	$(".select2-drop-active").css("display","none");
}
/**
 * 修改
 * 
 * @param {}
 *            id
 */
function edit(id) {
	$("#titleForm").html("维护车辆信息");
	$.ajax({
		type : "GET",
		url : "PubVehicle/GetById",
		cache : false,
		data : {
			id : id
		},
		success : function(json) {
			checkUpdate(json.id, function() {
				$("#editFormDiv").show();
				showObjectOnForm("editForm", json);
				$("#vehclineId").select2("data", {
					id : json.vehclineId,
					text : json.brandCars
				});
				var editForm = $("#editForm").validate();
				editForm.resetForm();
				editForm.reset();
				$("#id").val(id);
				dataGrid._fnReDraw();
			}, function() {
				toastr.error("请先解绑，然后在做修改", "提示");
			});
		},
		error : function(xhr, status, error) {
			$("#id").val("");
			return;
		}
	});
}

/***
 * 
 * 初始化  搜索下拉 
 */
function initSelectQueryServiceOrgFullName() {
	$("#queryServiceOrgFullName").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OrgOrgan/GetOrgOrganShortName",
			dataType : 'json',
			data : function(term, page) {
				return {
					queryShortName : term
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
function initSelectQueryServiceOrgCity() {
	$("#queryServiceOrgCity").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OrgOrgan/GetOrgOrganCity",
			dataType : 'json',
			data : function(term, page) {
				return {
					queryCity : term
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

/**
 * 初始化城市下拉框
 */
function getSelectCity() {
//	var parent = document.getElementById("inp_box1");
//	var city = document.getElementById("city");
//	var cityName = document.getElementById("cityName");
//	getData(parent, cityName, city, "StandardAccountRules/GetPubCityAddrList", 30, 0);
	showCitySelect1(
		"#inp_box1", 
		"PubInfoApi/GetCitySelect1", 
		$("#citymarkId").val(),
		function(backVal, $obj) {
			$('#cityName').val($obj.text());
			$("#city").val($obj.data('id'));
		}
	);
}
//判断司机身份显示 。 隐藏  网约车显示，出租车隐藏
function getIdEntityTypeDiv(){
	var vehicleType = $("#vehicleType").val();
	if(vehicleType == 1){
		$("#idEntityTypeDiv").attr("style","display: none;");
        $("#serviceOrgName").html("");
		$("#serviceOrg").hide();
	}else{
		$("#idEntityTypeDiv").show();
		var val = $('input:radio[name="idEntityType"]:checked').val();
		if(val == 1){
			$("#serviceOrg").show();
        }
	}
}
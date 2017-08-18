var forTheCarBodyTable;
var srctag1= "" ;
var forTheCarBodyMap ={};
$(function() {
	validateForm();
	getSelectCity();
	initSelectQueryForTheCarBody();
	if ($("#id").val() != null && $("#id").val() != '') {
		var data = {
			id : $("#id").val()
		};
		$.post("OrgOrgan/GetFristTime", data, function(status) {
			if (status > 0) {
				$("#account").attr("disabled", "disabled");
				$("#phone").attr("disabled", "disabled");
			}
		});
		$.post("OrgOrgan/CheckOrgOrganAccout", data, function(status) {
			if (status > 0) {
				$("#fullName").attr("disabled", "disabled");
				$("#shortName").attr("disabled", "disabled");
				$("#creditCode").attr("disabled", "disabled");
				$("#customertype").attr("disabled", "disabled");
			}
		});
	}
	if($("#customertype").val() == '0'){
		$("#lineOfCreditDiv").show();
	}else{
		$("#lineOfCredit").attr("disabled","disabled");
	};
	$("#customertype").click(function(){
		if($("#customertype").val() == '0'){
			$("#lineOfCreditDiv").show();
			$("#lineOfCredit").removeAttr("disabled");
		}else{
			$("#lineOfCreditDiv").hide();
			$("#lineOfCredit").attr("disabled","disabled");
		}
	});
	$("#addressButton").click(function() {
		/*
		 * var selectIndex =
		 * document.getElementById("city").selectedIndex;//获得是第几个被选中了 var
		 * selectText =
		 * document.getElementById("city").options[selectIndex].text
		 * //获得被选中的项目的文本
		 */
		mapParam.cityName = $("#cityName").val();
		if(mapParam.cityName == null || mapParam.cityName == ''){
			toastr.error("请先选择一个机构地址的城市，在点击地图", "提示");
//			$("#map").hide();
			return;
		}
		moveMap(mapParam.cityName, $("#addressHidden").val());
		$("#map_confirm").attr("data-owner", "address");
		$("#map").show();
	});
	$("#map_cancel").click(function() {
//		$("#suggest").val("");
		$("#map").hide();
	});
	 

	$("#map_confirm").click(function() {
		var cityName = mapParam.cityName;
		var data = {
			cityName : cityName
		} 
		$.ajax({
			type : 'GET',
			dataType : 'json',
			url : "OrgOrgan/GetCityId",
			data : data,
			contentType : 'application/json; charset=utf-8',
			async : false,
			success : function(data) {
				$("#city").val(data.id);
				$("#cityName").val(data.city);
				changeCity(data.city);
			}
		});
		// onAddress.hide();
		$("#" + $(this).attr("data-owner")).val($("#suggest").val());
		mapParam.cityAddress.setInputValue($("#" + $(this).attr("data-owner")).val());
		$("#addressHidden").attr("value",$("#" + $(this).attr("data-owner")).val());
		$("#suggest").val("");
		$("#map").hide();
	});
	

	$("#address").blur(function() {
		$("#" + $(this).attr("data-owner")).val($(this).val());
	});

	$("#cityName").change(function() {
		changeCity($(this).val());
	});
	
	if($("#id").val() != null && $("#id").val() != ''){
		$.ajax({
			type : "GET",
			url : "OrgOrgan/GetPubLeaseOrganRelationById",
			cache : false,
			data : {
				id : $("#id").val()
			},
			success : function(json) {
				var html = "";
				for(var i=0;i<json.length;i++){
					html+='<li data-value="'+json[i].leasecompanyid+'">'+json[i].shortName+'</li>';
				}
				$("#forTheCarBody").html(html);
			}
		});
	}
	
	$("#forTheCarBody").click(function(e){
		var srctag =  e.target;
		if("LI"==srctag.tagName || "li"==srctag.tagName){
			var value = $(srctag).attr("data-value");
			$("#forTheCarBody li").css({"background-color":"white"});
			$(srctag).attr("style","background-color: #6495ED;");
			srctag1 = srctag;
			//alert(value);
		}
	});

});
/**
 * 表格初始化
 */
function forTheCarBodyTableInitGrid() {
	var id = "";
	if($("#id").val() != null && $("#id").val() != ''){
		 id = $("#id").val();
	}
	var gridObj = {
			id: "forTheCarBodyTable",
	        sAjaxSource: "OrgOrgan/GetPubCoooperateByQuery?id="+id,
	        iLeftColumn: 1,//（固定表头，1代表固定几列）
	        columns: [
//		        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
		        {
	                //自定义操作列
	                "mDataProp": "ZDY",
	                "sClass": "center",
	                "sTitle": "操作",
	                "sWidth": 120,
	                "bSearchable": false,
	                "sortable": false,
	                "mRender": function (data, type, full) {
	                	var html = "";
	                	if (haspro(forTheCarBodyMap)) {
		                	for ( var i in forTheCarBodyMap) {
		                		if(i==full.leasecompanyid){
		    	                    html += '<button type="button" class="SSbtn grey_w"><i class="fa fa-paste"></i>已添加</button>';
	
		                		}else{
		    	                    html += '<button type="button" class="SSbtn green_q" onclick="addFTCB(' +"'"+ full.leasecompanyid +"'"+",'"+full.companyName+"'"+')"><i class="fa fa-paste"></i>添加</button>';
		                		}
		            		}
	                	}else{
    	                    html += '<button type="button" class="SSbtn green_q" onclick="addFTCB(' +"'"+ full.leasecompanyid +"'"+",'"+full.companyName+"'"+')"><i class="fa fa-paste"></i>添加</button>';
	                	}
	                    return html;
	                }
	            },
		        {mDataProp: "companyName", sTitle: "战略伙伴", sClass: "center", sortable: true},
		        {mDataProp: "", sTitle: "合作业务", sClass: "center", sortable: true,
		        	mRender: function(data, type, full) {
						var html = '';
						if(full.servicetype == 0){
							html += '<span>网约车</span>';
						}else{
							html += '<span>出租车</span>';
						}
						return html;
					}
		        }
	        ]
	    };
	    
		forTheCarBodyTable = renderGrid(gridObj);
}
//返回   跟取消
function callBack(){
	window.location.href=base+"OrgOrgan/Index";
}
//1
$('#fileupload').fileupload(
		{
			url : "OrgOrgan/UploadFile",
			dataType : 'json',
			done : function(e, data) {
				if (data.result.status == "success") {
					$("#imgShow").show();
					$("#imgback")
							.attr(
									"src",
									data.result.basepath + "/"
											+ data.result.message[0]);
					$("#creditCodePic").val(data.result.message[0]);
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
	$("#creditCodePic").val("");
});
// 2
$('#fileupload2').fileupload(
		{
			url : "OrgOrgan/UploadFile",
			dataType : 'json',
			done : function(e, data) {
				if (data.result.status == "success") {
					$("#imgShow2").show();
					$("#imgback2")
							.attr(
									"src",
									data.result.basepath + "/"
											+ data.result.message[0]);
					$("#businessLicensePic").val(data.result.message[0]);
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
	$("#businessLicensePic").val("");
});
// 3
$('#fileupload3').fileupload(
		{
			url : "OrgOrgan/UploadFile",
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
			url : "OrgOrgan/UploadFile",
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

/**
 * 表单校验
 */
function validateForm() {
	$("#editOrgOrganForm").validate({
		rules : {
			fullName : {
				required : true,
				maxlength : 20
			},
			shortName : {
				required : true,
				maxlength : 6
			},
			cooperationStatus : {
				required : true
			},
			contacts : {
				required : true,
				maxlength : 20
			},
			phone : {
				required : true,
				minlength : 11,
				phone : true
			},
			email : {
				required : true,
				email : true
			},
			address : {
				required : true,
				maxlength : 200
			},
			lineOfCredit : {
				required : true,
				lineOfCredit : true
			},
			billType : {
				required : true
			},
			billDate : {
				required : true
			},
			account : {
				required : true,
				minlength : 3,
				account : true
			},
			creditCode : {
				required : true,
				maxlength : 18,
				digits : true,
				minlength : 18
			},
			idCardNo : {
				required : true,
				minlength : 18,
				idCardNo : true
			}
			
		},
		messages : {
			fullName : {
				required : "请输入机构全称",
				maxlength : "最大长度20个字符"
			},
			shortName : {
				required : "请输入机构简称",
				maxlength : "最大长度6个字符"
			},
			cooperationStatus : {
				required : ""
			},
			contacts : {
				required : "请输入联系人姓名",
				maxlength : "最大长度20个字符"
			},
			phone : {
				required : "请输入正确的手机号码",
				minlength : "最小长度11个字符"
			},
			email : {
				required : "请输入正确的邮箱"
			},
			address : {
				required : "请选择城市或选择详细地址",
				maxlength : "最大长度200个字符"
			},
			lineOfCredit : {
				required : "请输入正确的金额"
			},
			billType : {
				required : true
			},
			billDate : {
				required : true
			},
			account : {
				required : "请填写机构账号",
				minlength : "不能小于3个字符"
			},
			creditCode : {
				required : "请填写正确格式的机构信用代码编码",
				maxlength : "最大长度18个字符",
				minlength:"最小长度18个字符" 	
			},
			idCardNo : {
				required : "请填写正确格式的身份证号",
				minlength:"最小长度18个字符" 
			}
		}
	})
}

// 保存机构用户
function save(){
	var form = $("#editOrgOrganForm");
	if (!form.valid())
		return;
	var values = [];
	$("#forTheCarBody li").each(function(){
		values.push($(this).attr("data-value"));
	});
	$("#forTheCarBodyId").val(values.join(","));
	
    var id = $("#id").val();
	//验证机构全称
	var fullName = $("#fullName").val();
	var flag = false;
	var data = {
		id : id,
		fullName : fullName
	}
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : "OrgOrgan/CheckOrgOrganFullName",
		data : data,
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status > 0) {
				toastr.error("机构全称已存在", "提示");
				flag = true;
			}
		}
	});
	if(flag){
		return;
	}
	//验证机构简称
	var shortName = $("#shortName").val();
	var flag2 = false;
	var data2 = {
		id : id,
		shortName : shortName
	}
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : "OrgOrgan/CheckOrgOrganShortName",
		data : data2,
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status > 0) {
				toastr.error("机构简称已存在", "提示");
				flag2 = true;
			}
		}
	});
	if(flag2){
		return;
	}
//	//联系方式
//	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
//	if(!myreg.test($("#phone").val())){ 
//	    toastr.error("请输入有效的手机号码", "提示");
//	    return; 
//	}
	var account = $("#account").val();
	var flag3 = false;
	var data3 = {
		id : id,
		account : account
	}
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : "OrgOrgan/CheckAccount",
		data : data3,
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status > 0) {
				toastr.error("机构账号已存在", "提示");
				flag3 = true;
			}
		}
	});
	if(flag3){
		return;
	}
	//邮箱
	var email = $("#email").val();
	var flag4 = false;
	var data4 = {
		id : id,
		email : email
	}
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : "OrgOrgan/CheckOrgOrganEmail",
		data : data4,
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status > 0) {
				toastr.error("邮箱已存在", "提示");
				flag4 = true;
			}
		}
	});
	if(flag4){
		return;
	}
	//机构信用代码
	var creditCode = $("#creditCode").val();
	var flag5 = false;
	var data5 = {
		id : id,
		creditCode : creditCode
	}
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : "OrgOrgan/CheckOrgOrganCreditCode",
		data : data5,
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status > 0) {
				toastr.error("机构信用代码已存在", "提示");
				flag5 = true;
			}
		}
	});
	if(flag5){
		return;
	}
	//工商执照编码
	var businessLicense = $("#businessLicense").val();
	var flag6 = false;
	var data6 = {
		id : id,
		businessLicense : businessLicense
	}
	$.ajax({
		type : 'GET',
		dataType : 'json',
		url : "OrgOrgan/CheckOrgOrganBusinessLicense",
		data : data6,
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status > 0) {
				toastr.error("工商执照编码已存在", "提示");
				flag6 = true;
			}
		}
	});
	if(flag6){
		return;
	}
	var url = "OrgOrgan/CreateOrgOrgan";
	if ($("#id").val()) {
		url = "OrgOrgan/UpdateOrgOrgan";
	}

	var data = form.serializeObject();
	//机构地址的验证
	var address = data.address;
	if(address == null || address == ''){
		toastr.error("机构地址不能为空", "提示");
		return;
	}
	
	//机构信用代码证
	var creditCodePic = data.creditCodePic;
	if(creditCodePic == null || creditCodePic == ''){
		toastr.error("请上传机构信用代码证", "提示");
		return;
	}
	
	//工商执照
	var businessLicensePic = data.businessLicensePic;
	if(businessLicensePic == null || businessLicensePic == ''){
		toastr.error("请上传工商执照", "提示");
		return;
	}
	
	//法人身份证
	var idCardFront = data.idCardFront;
	if(idCardFront == null || idCardFront == ''){
		toastr.error("请上传法人身份证正面", "提示");
		return;
	}
	
	//法人身份证
	var idCardBack = data.idCardBack;
	if(idCardBack == null || idCardBack == ''){
		toastr.error("请上传法人身份证反面", "提示");
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
					window.location.href =base+"OrgOrgan/Index";
				}
				toastr.success(message, "提示");
			} else {
				var message = status.MessageKey == null ? status
						: status.MessageKey;
				toastr.error(message, "提示");
			}
		}
	});
};

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
			changeCity($obj.text());
		}
	);
}
function addForTheCarBody() {
	if (!forTheCarBodyTable) {
		forTheCarBodyTableInitGrid();
	}
	forTheCarBodyTable._fnReDraw();
	$("#addForTheCarBody").show();
}
function updateForTheCarBody(){
	//alert(srctag1);
	if(srctag1 == ''){
		toastr.error("请选择供车主体", "提示");
	}else{
//		srctag1.parentNode.removeChild(srctag1);
		var value = $(srctag1).attr("data-value");
		var nowleLeasescompanyid = $("#nowleLeasescompanyid").val();
		if(value == nowleLeasescompanyid){
			toastr.error("签约服务车企不可选择", "提示");
		}else{
			$(srctag1).remove();
		}
	}
}
function cancle(){
	$("#addForTheCarBody").hide();
}
function addFTCB(id,companyName){
	var html = "";
	html+="<li data-value='"+id+"'>"+companyName+"</li>"
	forTheCarBodyMap[id] = companyName;
	$("#forTheCarBody").append(html);
	toastr.options.onHidden = function() {
		$("#addForTheCarBody").hide();
	}
	toastr.success("添加成功", "提示");
	forTheCarBodyTable._fnReDraw();
}

function haspro(forTheCarBodyMap) {
	if (!forTheCarBodyMap) {
		return false
	}
	for ( var pro in forTheCarBodyMap) {
		if (!pro) {
			return false
		}
		return true
	}
}

/***
 * 
 * 初始化  搜索下拉 
 */
function initSelectQueryForTheCarBody() {
	var id = "";
	if($("#id").val() != null && $("#id").val() != ''){
		id = $("#id").val();
	}
	$("#queryForTheCarBody").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "OrgOrgan/GetPubCoooperateSelect?id="+id,
			dataType : 'json',
			data : function(term, page) {
				return {
					queryForTheCarBody : term
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
function loadForTheCarBody(){
	var queryForTheCarBody = $("#queryForTheCarBody").select2("val");
	var conditionArr = [
		{ "name": "queryForTheCarBody", "value": queryForTheCarBody }
	];
	forTheCarBodyTable.fnSearch(conditionArr);
}
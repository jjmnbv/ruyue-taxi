var dataGrid;
var addid ="";

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	validateForm();
	initSelectBrandCars();
	// initSelectQueryBrandCars();
	getSelectCitys();
	initSelectGetCity();
	$(".close").click(function(){
		$("#select2-drop").css("display","none");
//		$("#select2-drop").remove();
	})
	
});

/***
 *
 * 初始化 城市  搜索下拉
 */
function initSelectGetCity() {
	$("#queryCity").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubVehicle/getCity",
			dataType : 'json',
			data : function(term, page) {
				return {
					queryText : term
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
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "PubVehicle/GetPubVehicleByQuery",
        iLeftColumn: 1,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
		createdRow: function( row, data, dataIndex ) {
			// if (data.id==addid) {
			// 	$(row).css('background-color','green');
			// 	addid="";
			// }
			if(data.level == "3"){
				row.style.backgroundColor = "#F3DFDF";
			}
		},
        columns: [
	        // {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
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
                    html += '<button type="button" class="SSbtn blue" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
                    html += '&nbsp; <button type="button" class="SSbtn red"  onclick="del(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 删 除</button>';
                    return html;
                }
            },
            //新增的
            {mDataProp: "vehicletype", sTitle: "车辆类型", sClass: "center", sortable: true},
            {mDataProp: "serviceCars", sTitle: "服务车型", sClass: "center", sortable: true,
            	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.serviceCars == null){
	        			html+='<font>/</font>';
	        		}else{
	        			html+='<font>'+full.serviceCars+'</font>';
	        		}
	        		return html;
	        	}
            },
            {mDataProp: "workstatus", sTitle: "服务状态", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.workstatus == "空闲"){
	        			html+='<font color="green">'+full.workstatus+'</font>';
	        		}else if(full.workstatus == "服务中"){
	        			html+='<font color="red">'+full.workstatus+'</font>';
	        		}else if(full.workstatus == "下线"){
	        			html+='<font>'+full.workstatus+'</font>';
	        		}else{
	        			html+='<font>/</font>';
	        		}
	        		return html;
	        	}	
	        },
	        //新增的
	        {mDataProp: "boundstate", sTitle: "绑定状态", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		if(full.boundstate =="未绑定"){
	        			return '<font color="red">未绑定</font>';
	        		}
	        		return data;
	        	}		
	        },
	        //新增的
	        {mDataProp: "vehiclestatus", sTitle: "营运状态", sClass: "center", sortable: true},
	        {mDataProp: "platenoStr", sTitle: "车牌号", sClass: "center", sortable: true },
	        {mDataProp: "brandCars", sTitle: "品牌车系", sClass: "center", sortable: true },
	        {mDataProp: "vin", sTitle: "车架号", sClass: "center", sortable: true },
	        {mDataProp: "color", sTitle: "颜色", sClass: "center", sortable: true },
	        {mDataProp: "loads", sTitle: "荷载人数", sClass: "center", sortable: true },
	        {mDataProp: "cityStr", sTitle: "登记城市", sClass: "center", sortable: true },
	        //新增的
	        {mDataProp: "scopeStr", sTitle: "经营区域", sClass: "center", sortable: true ,
				mRender:function (data) {
					return showToolTips(data);
				}}
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
//			plateNoProvince: {required: true},
//			plateNoCity: {required: true},
			plateNo: {required: true,maxlength : 5},
			vin: {required: true, maxlength: 17},
			// brandCars: {required: true},
			color: {required: true, maxlength: 8},
			loads: {required: true, maxlength: 2,digits:true},
			businessScope:{required: true, maxlength:50},
			vehicleType : {required : true}
		},
		messages: {
//			plateNoProvince: {required: "请填写完整的车牌号"},
//			plateNoCity: {required: "请填写完整的车牌号"},
			plateNo: {required: "请填写完整的车牌号",maxlength: "车牌号最大长度不能超过5个字符"},
			vin: {required: "请输入车架号",maxlength: "最大长度不能超过17个字符"},
			// brandCars: {required: "请选择品牌车系"},
			color: {required: "请输入车颜色",maxlength: "最大长度不能超过8个字符"},
			loads: {required: "请输入荷载人数",maxlength: "最大长度不能超过2个字符"},
			businessScope:{required: "请输入经营区域", maxlength:50},
			vehicleType : {required : "请选择车辆类型"}
		}
	})
}

/**
 * 新增
 */
function add() {
	$("#vehicleStatusDiv").hide();
	$("#titleForm").html("新增车辆信息");
	$("#editFormDiv").show();
	showObjectOnForm("editForm", null);
	$(".addcitybtn").val("+添加城市");
	$("#vehicleType").val("0");
	$("#vehclineId").select2("val","");
	$("#vehicleType").prop("disabled",false);
	$(".addcbox").empty();
	
	var editForm = $("#editForm").validate();
	editForm.resetForm();
	editForm.reset();
	$("#citymarkid").val("");
	getSelectCity();
	$("#scopeDiv").show();
}

function editFrom(json){

	$("#vehicleStatusDiv").show();
	$("#editFormDiv").show();

	var data = {
		id : json.plateNoProvince
	};
	$.post("PubVehicle/GetPlateNoCity", data, function(ret) {
		redrawVersionList(ret);
		showObjectOnForm("editForm", json);
		$(".addcitybtn").val("+添加城市");
		$(".addcbox").empty();
		if (json.businessScope != null && json.businessScope != '') {
			var businessScopeArr = json.businessScope.split(",");
			var businessScopeTempArr = json.businessScopeTemp.split(",");

			for (var i in businessScopeArr) {
				var cityhtml = '<div class="added" id="' + businessScopeArr[i] + '">' + businessScopeTempArr[i] + '<em class="ico_x_a"></em></div>';
				$(".addcbox").append(cityhtml);
			}
		}

		$("#vehclineId").select2("data", {
			id : json.vehclineId,
			text : json.brandCars
		});
		$("#city").val(json.city);
		$("#citymarkid").val(json.citymarkid);
		$("#cityName").val(json.cityName);

		//车辆类型
		$("#vehicleType").val(json.vehicleType);
		//车辆类型不可修改
		$('#vehicleType').attr("disabled",true);


        //营运状态
        var html1 ="";
        html1 = "<label>营运状态<em class='asterisk'></em></label>";
        if(json.vehicleStatus == 0){
            html1 += "<input id='vehicleStatus' name='vehicleStatus' type='radio' value='0' checked='checked'/>营运中 ";
            html1 += "<input id='vehicleStatus' name='vehicleStatus' type='radio' value='1'/>维修中";
        }else{
            html1 += "<input id='vehicleStatus' name='vehicleStatus' type='radio' value='0'/>营运中 ";
            html1 += "<input id='vehicleStatus' name='vehicleStatus' type='radio' value='1' checked='checked'/>维修中";
        }
        $("#vehicleStatusDiv").html(html1);

		var editForm = $("#editForm").validate();
		editForm.resetForm();
		editForm.reset();
		$("#id").val(json.id);
		getSelectCity();
	});
}

/**
 * 查询
 */
function search(id) {
	var conditionArr = [
		{ "name": "brandCars", "value": $("#queryBrandCars").val()},
		{ "name": "platenoStr", "value": $("#queryPlateNo").val() },
		{ "name": "workstatus", "value": $("#queryWorkStatus").val() },
		{ "name": "city", "value": $("#queryCity").val() },
		{ "name": "serviceCars", "value": $("#queryServiceCars").val() },
		{ "name": "vehicletype", "value": $("#queryVehicleType").val() },
		{ "name": "vehiclestatus", "value": $("#queryVehicleStatus").val() },
		{ "name": "boundstate", "value": $("#queryBoundState").val() },
		{ "name": "id", "value": id }
	];
	dataGrid.fnSearch(conditionArr);

}
/**
 * 修改
 * @param {} id
 */
function edit(id) {
	$("#titleForm").html("修改车辆信息");
	$.ajax({
		type: "GET",
		url:"PubVehicle/getEditVehicle/"+id,
		cache: false,
		success: function (json) {
			 if(json.status=="0"){
				 editFrom(json.data);
			 }else{
				 toastr.error(json.message, "提示");
			 }
		},
		error: function (xhr, status, error) {
			$("#id").val("");
		}
    });
}

function checkUpdate(id,suceessCallback,failCalback){
	$.ajax({
		type: "GET",
		url:"PubVehicle/CheckDelete",
		cache: false,
		data: { id: id },
		success: function (json) {
			if(json > 0){
				failCalback();
			}else{
				suceessCallback();
			}
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
		context:"您确定要删除车辆吗？",
		button_l:"否",
		button_r:"是",
		click: "deletePost('" + id + "')",
		htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	Zconfirm(comfirmData);
}

function deletePost(id){
	var data = {id: id};
	$.post("PubVehicle/DeletePubVehicle", data, function (status) {
		if (status.status == "0") {
			var message = status.message == null ? status : status.message;
            toastr.success(message, "提示");
			dataGrid._fnReDraw();
		} else {
			var message = status.message == null ? status : status.message;
			toastr.error(message, "提示");
		}
	});
}


/**
 * 表单校验
 */
function validateForm() {
	$("#editForm").validate({
		ignore:[],
		rules : {
			vin : {
				required : true,
				minlength : 17,
				vinTest:true,
				vinExists:true
			},
			color:{
				required : true
			},
			vehclineId:{
				required : true
			},
			businessScopeMessage:{
				businessScopeEmpty:true
			},
			loads:{
				minLoads:true,
				required : true
			}
		},
		messages : {
			vin : {
				required : "请输入车架号",
				minlength : "车架号必须是17位",
				vinTest : "车架号只能为大写字母跟数字组合",
				vinExists:"车架号已存在"
			} ,
			color:{
				required : "请输入颜色"
			},
			vehclineId:{
				required : "请选择品牌车系"
			},
			businessScopeMessage:{
				businessScopeEmpty:"经营区域不能为空"
			},
			loads:{
				minLoads:"请输入正确核载人数",
				required : "请输入荷载人数"
			}
		}
	})
}

/**
 * 保存
 */
function save() {
	var form = $("#editForm");
	if (!form.valid())
		return;

	//车牌
	var plateNo = $("#plateNo").val();
	var Regx1 = /^[A-Za-z0-9]*$/;
	if (Regx1.test(plateNo)){
		if(plateNo.length != 5){
			toastr.error("车牌号必须是5位", "提示");
			return;
		}
	}else{
		toastr.error("请输入正确的车牌号", "提示");
		return;
	}

	var url = "PubVehicle/CreatePubVehicle";
	if($("#id").val()) {
		url = "PubVehicle/UpdatePubVehicle";
	}
	var data = form.serializeObject();

	// 车牌省
	var plateNoProvince = data.plateNoProvince;
	if(plateNoProvince == null || plateNoProvince == ''){
		toastr.error("请选择车牌省", "提示");
		return;
	}
	//车牌市
	var plateNoCity = data.plateNoCity;
	if(plateNoCity == null || plateNoCity == ''){
		toastr.error("请选择车牌市", "提示");
		return;
	}

	//城市
	var city = data.city;
	if(city == null || city == ''){
		toastr.error("请选择登记城市", "提示");
		return;
	}

	// 经营范围
	var businessScopeDiv = $(".addcbox").children();
	if(businessScopeDiv.length == 0 || document.getElementById("addcboxId").innerHTML == "" ||document.getElementById("addcboxId").innerHTML == null){
		toastr.error("经营区域不能为空", "提示");
		return;
	}else{
		var businessScopeArr = [];
		for(var i = 0; i < businessScopeDiv.length; i++) {
			businessScopeArr.push(businessScopeDiv[i].id);
		}

		data.businessScope = businessScopeArr.join(",");
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
				if($("#id").val()){
					dataGrid._fnReDraw();
				}else{
					addid = status.data;
					search(addid);
				}

			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
			}	
		}
	});
}

// function checkType(){
// 	var type = $("#vehicleType").val();
// 	if(type ==1){
// 		$("#scopeDiv").hide();
// 	}else{
// 		$("#scopeDiv").show();
// 	}
//
// }
/**
 * 取消
 */
function canel() {
	$("#editFormDiv").hide();
	$("#importExcelDiv").hide();
	$("#select2-drop").css("display","none");
}

/***
 * 
 * 初始化  搜索下拉 
 */
function initSelectBrandCars() {
	$("#vehclineId").select2({
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
				$("#vehclineIdtest").val(data);
				return {
					results : data
				};
			}
		}
	});
}

function initSelectQueryBrandCars() {
	$("#queryBrandCars").select2({
		placeholder : "请选择品牌车系",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubVehicle/GetBrandCars",
			dataType : 'json',
			data : function(term, page) {
				return {
					id : term
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
 * 新增的清空功能
 * 
 */
function emptys(){
	$("#queryBrandCars").val("");
	$("#queryPlateNo").val("");
	$("#queryWorkStatus").val("");
	$("#queryCity").select2("val","");
	$("#queryServiceCars").val("");
	$("#queryVehicleType").val("");
	$("#queryVehicleStatus").val("");
	$("#queryBoundState").val("");
	search();
}

/**
 * 下载模板
 * */
function download(){
	window.location.href = base+"PubVehicle/DownLoad";
}

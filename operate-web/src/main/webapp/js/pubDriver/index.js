var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	initSelectGetCity();
	initDriverId();
	// initSelectGetServiceOrg();
	initjobNumSelect();
	$(".close").click(function(){
		$(".select2-drop-active").css("display","none");
//		$("#select2-drop").remove();
	})
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "PubDriver/GetPubDriverByQuery",
        iLeftColumn: 3,//（固定表头，1代表固定几列）
        scrollX: true,//（加入横向滚动条）
		language: {
			sEmptyTable: "暂无服务司机信息"
		},
        columns: [
//	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {
                //自定义操作列
                "mDataProp": "ZDY",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 180,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    	html += '<button type="button" class="SSbtn blue" onclick="edit(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
                        html += '&nbsp; <button type="button" class="SSbtn red"  onclick="del(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 删 除</button>';
					if(full.jobStatus == '0'){

                        html += '&nbsp; <button type="button" class="SSbtn orange"  onclick="resetPassword(' +"'"+ full.id +"'"+ ')"><i class="fa fa-times"></i> 重置密码</button>';
                    }
                    return html;
                }
            },
	        {mDataProp: "jobNum", sTitle: "资格证号", sClass: "center", sortable: true ,
				mRender:function (data) {
					return showToolTips(data);
				}},
	        {mDataProp: "name", sTitle: "姓名", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		var html = "";
	        		html+= '<a href="PubDriver/AddIndex?id='+full.id+'&particulars=particulars" style="color:green;">'+""+full.name+""+'</a>';
	        		return html;
	        	}
	        },
	        {mDataProp: "sex", sTitle: "性别", sClass: "center", sortable: true },
	        {mDataProp: "phone", sTitle: "手机号", sClass: "center", sortable: true },
	        {mDataProp: "driverYears", sTitle: "驾驶工龄(年)", sClass: "center", sortable: true },
	        //新加的
	        {mDataProp: "vehicleType", sTitle: "司机类型", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.vehicleType == '0'){
	        			html+='<font>网约车</font>';
	        		}else if(full.vehicleType == '1'){
	        			html+='<font>出租车</font>';
	        		}
	        		return html;
	        	}		
	        },
	        {mDataProp: "cityName", sTitle: "登记城市", sClass: "center", sortable: true },
	        {mDataProp: "workStatusName", sTitle: "服务状态", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.workStatusName == '空闲'){
	        			html+='<font color="green">'+full.workStatusName+'</font>';
	        		}else if(full.workStatusName == '服务中'){
	        			html+='<font color="red">'+full.workStatusName+'</font>';
	        		}else if(full.workStatusName == '下线'){
	        			html+='<font>'+full.workStatusName+'</font>';
	        		}else{
	        			html+='<font>/</font>';
	        		}
	        		return html;
	        	}
	        },
	        //新加的
	        {mDataProp: "boundState", sTitle: "绑定状态", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.boundState == 1){
	        			html+='<font>已绑定</font>';
	        		}else{
	        			html+='<font color="red">未绑定</font>';
	        		}
	        		return html;
	        	}	
	        },
	        {mDataProp: "jobStatus", sTitle: "在职状态", sClass: "center", sortable: true,
	        	"mRender": function (data, type, full) {
	        		var html = "";
	        		if(full.jobStatus == '0'){
	        			html+='<font>在职</font>';
	        		}else{
	        			html+='<font color="red">离职</font>';
	        		}
	        		return html;
	        	}
	        }
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "driverId", "value": !!$("#queryKeyword").select2('data')?$("#queryKeyword").select2('data').id:""},
		{ "name": "queryWorkStatus", "value": $("#queryWorkStatus").val() },
		{ "name": "queryCity", "value": !!$("#queryCity").select2('data')?$("#queryCity").select2('data').id:""},
		{ "name": "queryJobStatus", "value": $("#queryJobStatus").val() },
		{ "name": "queryBoundState", "value": $("#queryBoundState").val() },
		{ "name": "jobNum", "value": !!$("#queryJobNum").select2('data')?$("#queryJobNum").select2('data').text:""},
		{ "name": "queryVehicleType", "value": $("#queryVehicleType").val() }
	];
	dataGrid.fnSearch(conditionArr);

}


/**
 * 修改 验证
 * */
function edit(id){
	var data = {
		id : id
	}
	$.post("PubDriver/CheckDelete", data, function (status) {
		if (status > 0) {
			toastr.error("请解绑后再修改司机信息", "提示");
		} else {
			window.location.href=base+"PubDriver/AddIndex?id="+id+"&title=维护司机信息";
		}
	});
};

/**
 * 删除 验证
 * */
function del(id){
	var data = {
		id : id
	}
	$.post("PubDriver/CheckDelete", data, function (status) {
		if (status > 0) {
			toastr.error("请解绑车辆再删除", "提示");
		} else {
			var comfirmData={
				tittle:"提示",
				context:"您确认要删除司机吗？",
				button_l:"否",
				button_r:"是",
				click: "deletePost('" + id + "')",
				htmltex:"<input type='hidden' placeholder='添加的html'> "
			};
			Zconfirm(comfirmData);
		}
	});
};

function deletePost(id){
	var data = {id: id};
	$.post("PubDriver/DeletePubDriver", data, function (status) {
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
 * 取消
 */
function canel() {
	$("#editFormDiv").hide();
	$("#unwrapVel").hide();
}
/**
 * 重置密码提示
 * */
function resetPassword(id){
	var comfirmData={
		tittle:"提示",
		context:"您确认需要重置密码吗？",
		button_l:"否",
		button_r:"是",
		click: "resetPassword1('" + id + "')",
		htmltex:"<input type='hidden' placeholder='添加的html'> "
	};
	Zconfirm(comfirmData);
};
function resetPassword1(id){
	var data = {id: id};
	$.post("PubDriver/ResetPassword", data, function (status) {
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
			url : "PubDriver/GetCity",
			dataType : 'json',
			data : function(term, page) {
				return {
					queryCity : term
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

function initjobNumSelect(){

	$("#queryJobNum").select2({
		placeholder : "",
		minimumInputLength : 0,
		multiple : false, //控制是否多选
		allowClear : true,
		ajax : {
			url : "PubDriver/listPubDriverBySelectJobNum",
			dataType : 'json',
			data : function(term, page) {
				return {
					queryText : term
				};
			},
			results : function(data, page) {
				for(var i=0;i<data.length;i++){
					data[i].text = data[i].jobnum;
				}
				return {
					results : data
				};
			}
		}
	});
}

function initDriverId(){
	$("#queryKeyword").select2({
		placeholder: "司机",
		minimumInputLength: 0,
		allowClear: true,
		ajax: {
			url: "PubDriver/listPubDriverBySelect",
			dataType: 'json',
			data: function (term, page) {
				$(".datetimepicker").hide();

				var param = {
					queryText: term,

				}
				return param;
			},
			results: function (data, page) {
				return { results: data };
			}
		}
	});
}





function exportData(){
	var queryVehicleType = $("#queryVehicleType").val();
	var jobNum = !!$("#queryJobNum").select2('data')?$("#queryJobNum").select2('data').text:"";
	var queryBoundState = $("#queryBoundState").val();
	var queryJobStatus = $("#queryJobStatus").val();
	var queryCity =  !!$("#queryCity").select2('data')?$("#queryCity").select2('data').id:"";
	var queryWorkStatus = $("#queryWorkStatus").val();
	var driverId = !!$("#queryKeyword").select2('data')?$("#queryKeyword").select2('data').id:"";

	window.location.href = base+"PubDriver/ExportData?" +
		"jobNum="+jobNum+"&queryWorkStatus="+queryWorkStatus+"" +
		"&queryCity="+queryCity+"&queryJobStatus="+queryJobStatus+"&driverId="+driverId +
		"&queryVehicleType="+queryVehicleType+"&queryBoundState="+queryBoundState;
}
/**
 * 新增的清空功能
 * 
 */
function emptys(){

	$("#queryCity").select2("val","");
	$("#queryKeyword").select2("val","");
	$("#queryJobNum").select2("val","");
	$("#queryWorkStatus").val("");
	$("#queryJobStatus").val("");
	$("#queryBoundState").val("");
	$("#queryVehicleType").val("");
	search();
}
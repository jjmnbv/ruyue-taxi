var dataGrid;
	//  新增
	function callBack(){
		window.location.href=base+"LeLeasescompany/Index";
	}
	/**
	 * 毫秒转日期
	 * 
	 * @param data
	 * @returns {String}
	 */
	function changeToDate(data) {
		var myDate = new Date(data);
		var month = "";
		var date = "";
		var hours = "";
		var minutes = "";
		var second = "";
		var change = "";
		change += myDate.getFullYear() + "/";
	
		if (myDate.getMonth() < 9) {
			month = "0" + (myDate.getMonth() + 1);
		} else {
			month = (myDate.getMonth() + 1);
		}
		change += month + "/";
		if (myDate.getDate() < 10) {
			date = "0" + myDate.getDate();
		} else {
			date = myDate.getDate();
		}
		change += date;
		if (myDate.getHours() < 10) {
			hours = "0" + myDate.getHours();
		} else {
			hours = myDate.getHours();
		}
		change += " " + hours;
		if (myDate.getMinutes() < 10) {
			minutes = "0" + myDate.getMinutes();
		} else {
			minutes = myDate.getMinutes();
		}
		change += ":" + minutes;
		if (myDate.getSeconds()<10){
			second = "0"+myDate.getSeconds();
		}else{
			second = myDate.getSeconds();
		}
		change += ":" + second;
		return change;
	}
	/**
	 * 页面初始化
	 */
	$(function () {
		initGrid();
		initSelectQueryKeyword();
	});

	/**
	 * 表格初始化
	 */
	function initGrid() {
		var id = $("#id").val();
		var gridObj = {
			id: "dataGrid",
	        sAjaxSource: "LeLeasescompany/GetPubDriverByQuery?id="+id,
	        iLeftColumn: 1,//（固定表头，1代表固定几列）
	        scrollX: true,//（加入横向滚动条）
	        language: {
	        	sEmptyTable: "暂无开放资源信息"
	        },
	        columns: [
//		        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
		        {
	                //自定义操作列
	                "mDataProp": "ZDY",
	                "sClass": "center",
	                "sTitle": "操作",
	                "sWidth": 80,
	                "bSearchable": false,
	                "sortable": false,
	                "mRender": function (data, type, full) {
	                    var html = "";
	                    if(full.vehicleType == '1'){
	                    	return html;
	                    }else{
		                    if(full.distributionVel == '未分配'){
		                    	html += '&nbsp;<button type="button" class="SSbtn blue" onclick="edit(' +"'"+ full.id +"'"+",'"+full.vehicleInfo+"'"+ ','+"'分配车型'"+')"><i class="fa fa-paste"></i>分配车型</button>';
		                    }else{
		                    	html += '&nbsp;<button type="button" class="SSbtn blue" onclick="edit(' +"'"+ full.id +"'"+",'"+full.vehicleInfo+"'"+ ','+"'修改车型'"+')"><i class="fa fa-paste"></i>修改车型</button>';
		                    }
	                    }
                    	return html;
	                }
	            },
//		        {mDataProp: "jobNum", sTitle: "资格证号", sClass: "center", sortable: true },
	            {mDataProp: "name", sTitle: "司机信息", sClass: "center", sortable: true},
	            {mDataProp: "belongleasecompanyName", sTitle: "归属车企", sClass: "center", sortable: true},
	            {mDataProp: "cityName", sTitle: "登记城市", sClass: "center", sortable: true },
	            {mDataProp: "pubVehicleScope", sTitle: "运营范围", sClass: "center", sortable: true,
	            	"mRender": function (data, type, full) {
	                    return showToolTips(full.pubVehicleScope);
	                }
	            },
//		        {mDataProp: "sex", sTitle: "性别", sClass: "center", sortable: true },
//		        {mDataProp: "phone", sTitle: "手机号", sClass: "center", sortable: true },
//		        {mDataProp: "driverYears", sTitle: "驾驶工龄(年)", sClass: "center", sortable: true },
	            {mDataProp: "vehicleType", sTitle: "车辆类型", sClass: "center", sortable: true,
	            	"mRender": function (data, type, full) {
	                    var html = "";
	                    if(full.vehicleType == '1'){
	                    	html+= '<font>出租车</font>';
	                    }else{
	                    	html+= '<font>网约车</font>';
	                    }
	                    return html;
	                }
	            },
		        {mDataProp: "workStatusName", sTitle: "服务状态", sClass: "center", sortable: true },
		        {mDataProp: "vehicleInfo", sTitle: "车辆信息", sClass: "center", sortable: true },
		        {mDataProp: "distributionVel", sTitle: "分配车型", sClass: "center", sortable: true },
		        {mDataProp: "distributionVelTime", sTitle: "修改时间", sClass: "center", sortable: true ,
		        	mRender : function(data, type, full) {
					if (data != null) {
						return changeToDate(data);
					} else {
						return "/";
					}
				}}
	        ]
	    };
	    
		dataGrid = renderGrid(gridObj);
	}

	/**
	 * 查询
	 */
	function search() {
		var conditionArr = [
			{ "name": "queryKeyword", "value": $("#queryKeyword").val() },
			{ "name": "queryWorkStatus", "value": $("#queryWorkStatus").val() },
			{ "name": "queryCity", "value": $("#queryCity").val() },
			{ "name": "queryVehicletype", "value": $("#queryVehicletype").val() },
			{ "name": "belongleasecompanyQuery", "value": $("#belongleasecompanyQuery").val() }
		];
		dataGrid.fnSearch(conditionArr);
	}
	/**
	 * 修改 验证
	 * */
	function edit(id,vehicleInfo,obj){
		$("#titleForm").html(obj);
		if(vehicleInfo != '---'){
			var data ={
				id :id
			}
			$.post("LeLeasescompany/GetByIdPubDriver", data, function (status) {
				$("#velInfo").html("【"+status.name+"】  "+status.vehicleInfo);
				$("#oldService").val(status.brandCars);
				$("#vehicleid").val(status.vehicleId);
				$.post("LeLeasescompany/GetOpVehiclemodels", function (data) {
					var html="<option value='' selected='selected'>未分配</option>";
					for(var i=0;i<data.length;i++){
						if(status.distributionVelId == data[i].id){
							html+="<option value='"+data[i].id+"' selected='selected'>"+data[i].name+"</option>";
						}else{
							html+="<option value='"+data[i].id+"'>"+data[i].name+"</option>";
						}
					}
					$("#vehiclemodelsid").html(html);
				});
//				$("#vehiclemodelsid").append("<option value='"+status.distributionVelId+"' selected='selected'>"+status.distributionVel+"</option>")
				$("#editFormDiv").show();
			});
		}else{
			toastr.error("该司机没有绑定车辆", "提示");
		}
	};

	//取消
	function canel(){
		$("#editFormDiv").hide();
	}
	function save(){
		var form = $("#editForm");

		var url = "LeLeasescompany/CreateOpVehclineModelsRef";

		var data = form.serializeObject();

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
					$("#editFormDiv").hide();
					dataGrid._fnReDraw();
					toastr.success(message, "提示");
				} else {
					var message = status.MessageKey == null ? status
							: status.MessageKey;
					$("#editFormDiv").hide();
					toastr.error(message, "提示");
				}
			}
		});
	}
	function emptys(){
		$("#queryKeyword").select2("val","");
		$("#queryWorkStatus").val("");
		$("#queryCity").val("");
		$("#queryVehicletype").val("");
		$("#belongleasecompanyQuery").val("");
		search();
	}
	/***
	 * 
	 * 初始化  服务机构 搜索下拉 
	 */
	function initSelectQueryKeyword() {
		var id = $("#id").val();
		$("#queryKeyword").select2({
			placeholder : "",
			minimumInputLength : 0,
			multiple : false, //控制是否多选
			allowClear : true,
			ajax : {
				url : "LeLeasescompany/GetQueryKeyword",
				dataType : 'json',
				data : function(term, page) {
					return {
						id : id,
						val : term
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
var dataGrid;
	//  返回
	function callBack(){
		window.location.href=base+"PubCoooperate/Index";
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
		var leasescompanyid = $("#leasescompanyid").val();
		var servicetype = $("#servicetype").val();
		var gridObj = {
			id: "dataGrid",
	        sAjaxSource: "PubCoooperate/GetDriverInformationByQuery?leasescompanyid="+leasescompanyid+"&servicetype="+servicetype,
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
	                    if(full.vehicletype == '1'){
	                    	return html;
	                    }else{
		                    if(full.distributionVel == '未分配'){
		                    	html += '&nbsp;<button type="button" class="SSbtn blue" onclick="edit(' +"'"+ full.id +"'"+",'"+full.leasescompanyid+"'"+ ','+"'分配车型'"+')"><i class="fa fa-paste"></i>分配车型</button>';
		                    }else{
		                    	html += '&nbsp;<button type="button" class="SSbtn blue" onclick="edit(' +"'"+ full.id +"'"+",'"+full.leasescompanyid+"'"+ ','+"'修改车型'"+')"><i class="fa fa-paste"></i>修改车型</button>';
		                    }
	                    }
                    	return html;
	                }
	            },
	            {mDataProp: "vehicleInfo", sTitle: "车辆信息", sClass: "center", sortable: true },
	            {mDataProp: "name", sTitle: "司机信息", sClass: "center", sortable: true},
	            {mDataProp: "jobnum", sTitle: "资格证号", sClass: "center", sortable: true },
		        {mDataProp: "distributionVel", sTitle: "服务车型", sClass: "center", sortable: true },
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
			{ "name": "queryPlateno", "value": $("#queryPlateno").val() },
			{ "name": "queryJobnum", "value": $("#queryJobnum").val() },
			{ "name": "queryDriverInformation", "value": $("#queryDriverInformation").val()},
			{ "name": "queryModels", "value": $("#queryModels").val()}
		];
		dataGrid.fnSearch(conditionArr);
	}
	/**
	 * 修改 验证
	 * */
	function edit(vehicleid,leasescompanyid,obj){
		$("#titleForm").html(obj);
		$("#id").val("");
		$("#vehicleid").val(vehicleid);
		$("#leasecompanyid").val(leasescompanyid);
		var data ={
			vehicleid :vehicleid,
			leasescompanyid : leasescompanyid
		}
		//加载原服务车型
		$.post("PubCoooperate/GetOriginalModels", data, function (status) {
			$("#oldService").val(status.originalName);
			if(status.id != null && status.id != ''){
				$("#id").val(status.id);
			}
			//加载现在的服务车型
			$.post("PubCoooperate/GetLeVehiclemodels", function (data) {
				var html="<option value='' selected='selected'>未分配</option>";
				for(var i=0;i<data.length;i++){
					if(status.nowName == data[i].name){
						html+="<option value='"+data[i].id+"' selected='selected'>"+data[i].name+"</option>";
					}else{
						html+="<option value='"+data[i].id+"'>"+data[i].name+"</option>";
					}
				}
				$("#vehiclemodelsid").html(html);
			});
			$("#editFormDiv").show();
		});
	};

	//取消
	function canel(){
		$("#editFormDiv").hide();
	}
	function save(){
		var vehicleid = $("#vehicleid").val();
		var leasecompanyid = $("#leasecompanyid").val();
		var vehiclemodelsid = $("#vehiclemodelsid").val();
		var id = $("#id").val();
		var data ={
				vehicleid :vehicleid,
				leasecompanyid : leasecompanyid,
				vehiclemodelsid : vehiclemodelsid,
				id : id
			}
		$.post("PubCoooperate/CreatePubVehicleModelsRef", data, function (status) {
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
		});
	}
	function emptys(){
		$("#queryPlateno").val("");
		$("#queryJobnum").val("");
		$("#queryDriverInformation").val("");
		$("#queryModels").val("");
		search();
	}

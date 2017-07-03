var dtGrid;
// var apikey="EFLc9FVyIHUWE4xKYFETDeF";
var params = {             //好约车参数
		isNullName:false,
		checkSwitch:true,
		isNewWord:false,
		passengers:null,
		passengerPhone:null,
		text:null,
		userid:null,
		organid:null,
		airports:null,
		cities:null,
		companies:null,
		onLat : 0,
		onLng : 0,
		onAddress: null,
		offLat : 0,
		offLng : 0,
		offAddress: null,
		cartypes:null,
		cartypeindex:0,
		cartypemin:4      //车型最小显示个数
	};
$(function () {
	
    //绑定控件
    bindControl();

    //初始化表格
    initGrid();

    //绑定查询事件
    $("#btnSearch").click(function () {
       dtGrid.fnSearch(getFnData());
    });
    //高级查询
    $("#btnAdvancedSearch").click(function () {
        $("#txtStartMileageSum").val("");
        $("#txtEndMileageSum").val("");
        $("#txtStartTimeSum").val("");
        $("#txtEndTimeSum").val("");
        $("#txtStartOilSum").val("");
        $("#txtEndOilSum").val("");
        $("#txtStartIdleTimeSum").val("");
        $("#txtEndIdleTimeSum").val("");
        $("#txtStartTrackSum").val("");
        $("#txtEndTrackSum").val("");
        $("#advancedSearchPanel").toggle(1000);
    });
    //导出
    
    $("#exportData").click(function(){
    	// var plate = $("#txtPlate").val();
    	// var apikey = apikey;
    	var relationType = 1;
        var keyword = $("#txtKeyword").val();
        var departmentId=$("#companyId").val();

    	window.location.href = basePath+"Track/ExportTrackData?" +
    		"keyword="+keyword+"&departmentId="+departmentId+"" +
    		"&relationType="+relationType;
    })
   // initCompanyList();
});
function getFnData() {
//     var selectedNode = $("#txtDept").combotree("getValue");
//        var rcId = -1;
//        var companydeptCode = "";
//
//        if (selectedNode) {
//            companydeptCode = selectedNode.tag;
//            rcId = selectedNode.tag1;
//        }
        var keyword = $("#txtKeyword").val();
        var departmentId=$("#companyId").val();
        var oData = [
			{ "name": "apikey", "value": apikey },
			{ "name": "keyword", "value": keyword },
			{ "name": "relationType", "value": 1 },
            { "name": "property", "value": $("#selProperty").val() },
            { "name": "departmentId", "value": departmentId },
//            { "name": "departmentId", "value": companydeptCode },
//            { "name": "rcId", "value": rcId },
            { "name": "plate", "value": $("#txtPlate").val() },
            { "name": "startMileageSum", "value": $("#txtStartMileageSum").val() },
            { "name": "endMileageSum", "value": $("#txtEndMileageSum").val() },
            { "name": "startTimeSum", "value": $("#txtStartTimeSum").val() },
            { "name": "endTimeSum", "value": $("#txtEndTimeSum").val() },
            { "name": "startOilSum", "value": $("#txtStartOilSum").val() },
            { "name": "endOilSum", "value": $("#txtEndOilSum").val() },
            { "name": "startIdleTimeSum", "value": $("#txtStartIdleTimeSum").val() },
            { "name": "endIdleTimeSum", "value": $("#txtEndIdleTimeSum").val() },
            { "name": "startTrackSum", "value": $("#txtStartTrackSum").val() },
            { "name": "endTrackSum", "value": $("#txtEndTrackSum").val() },
            { "name": "displacement", "value": $("#selDisplacement").val() },
            { "name": "vehcLine", "value": $("#selVehcLine").select2("val") }
        ];
        return oData;
}
//绑定控件数据
function bindControl() {

//    //绑定车辆所属下拉框
//    $.ajax({
//        //url: "@Url.Action("GetPropertyDict", "VehcTrack")",
//        cache: false,
//        success: function (data) {
//            var optStr = "<option value='-1' selected='selected'>请选择</option>";
//            for (var i in data) {
//                optStr += "<option value='" + data[i].D_VALUE + "'>" + data[i].D_TEXT + "</option>";
//            }
//            $("#selProperty").html(optStr);
//        },
//        error: function (xhr, status, error) {
//            showerror(xhr.responseText);
//        }
//    });

    //绑定部门树
//   $("#txtDept").combotree({
//        async: {
//            enable: true,
//            url: 'Track/GetAllDeptTree'
//        },
//        data: {
//            simpleData: {
//                enable: true,
//                idKey: "id",
//                pIdKey: "pId",
//                rootPId: ""
//            }
//        }
//    });
//    $("#txtDept").combotree("setValue", { id: "", name: "请选择", tag1: "" });
    
    //绑定车系
    $("#selVehcLine").select2({
        placeholder: "车系",
        minimumInputLength: 1,
        allowClear: true,
        ajax: {
            url: 'Vehcmodel/GetVehcLineByName',
            dataType: 'json',
            data: function (term, page) {
                return {
                    lineName: term
                };
            },
            results: function (data, page) {
                return { results: data };
            }
        }
    });

    //绑定车辆所属
//    $.ajax({
//        url: "@Url.Action("GetDict", "Common")",
//        cache: false,
//        data: { dictType: "汽车排量" },
//        success: function (data) {
//            var optStr = "<option value='' selected='selected'>请选择</option>";
//            for (var i in data) {
//                optStr += "<option value='" + data[i].D_VALUE + "'>" + data[i].D_TEXT + "</option>";
//            }
//            $("#selDisplacement").html(optStr);
//        },
//        error: function (xhr, status, error) {
//            showerror(xhr.responseText);
//        }
//    });
}

//初始化表格
function initGrid() {
	var gridObj = {
		id: "dtGrid",
		sAjaxSource: "Track/getTrackData",
        columns: [
            {
                "mDataProp": "plate", "sTitle": "车牌",
//                "mRender": function (data, type, full) {
//                    return "<a data-toggle='abstractpopover' data-plates='" + full.plate + "' rel='popover' data-placement='top' onmouseover='getAbstractByPlates(&#39;" + full.plate + "&#39;)'>" + full.plate + "</a>";
//                }
            },
            //{ "mDataProp": "V_VEHCLINE", "sTitle": "车系" },
            //{ "mDataProp": "V_DISPLACEMENT", "sTitle": "排量" },
            { "mDataProp": "imei", "sTitle": "IMEI" },
            //{ "mDataProp": "V_PROPERTY", "sTitle": "车辆所属" },
            { "mDataProp": "department", "sTitle": "所属部门" },
            { "mDataProp": "totalMileage", "sTitle": "总里程(km)" },
            { "mDataProp": "totalFuel", "sTitle": "总耗油量(L)" },
            { "mDataProp": "numberOfDays", "sTitle": "总行程数(次)" },
            { "mDataProp": "totalTrackTime", "sTitle": "总行程时间" },
            { "mDataProp": "totalIdleTime", "sTitle": "总怠速时间" },
            { "mDataProp": "finalTrackTime", "sTitle": "最后行驶时间" },
            {
                //自定义列sName
                "mDataProp": "zdy",
                "sClass": "center",
                "sTitle": "操作",
                "sWidth": 100,
                "bSearchable": false,
                "bStorable": false,
                "mRender": function (data, type, full) {
                    return '<a href='+ basePath +'Track/TrackRecord?imei=' + full.imei + ' class="SSbtn grey_q" style="text-decoration:none;">'
                    +'<img src="img/trafficflux/icon/travelInfo.png" alt=""/>行程记录</a>';
                }
            }
        ],
    };
	dtGrid = renderGrid(gridObj);
}
/**
 * 获取加入toC业务的租赁公司列表            好约车代码
 */
function initCompanyList(){
	$.ajax({
	    type: 'POST',
	    // url: "Order/GetCompanyList" ,
        url: "Order/GetBelongCompanyList" ,
	    dataType: "json",
	    success: function(data){
	    	if(data.status == 0 && data.count > 0){
	    		params.companies = data.lease;
	    		initCompanySelect();
	    	}else if(data.status != 0){
//	    		alert(data.message);
	    	}
	    },
	    contentType:"application/json"
	});
}
/**
 * 初始化租赁公司下拉组件         好约车代码
 */
function initCompanySelect(){
	$.each(params.companies,function(index,item){
		var option = $("<option>").val(item.id).text(item.shortName);
		$("#companyId").append(option);
	});
}
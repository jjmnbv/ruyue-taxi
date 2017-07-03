var dtGrid;
var vehcId;
var apikey="EFLc9FVyIHUWE4xKYFETDeF";

$(function () {
	var imei = $('#imei').val();
	var eqpId = $('#eqpId').val();
	var plate = $('#plate').val();
	
    var now = new Date();
    now.setDate(now.getDate() - 3);
    var startTime = now.format("yyyy-MM-dd hh:mm");
    var endTime = (new Date()).format("yyyy-MM-dd hh:mm");
    //绑定控件
    
    bindControl(startTime, endTime);

    //绑定查询事件
    $("#btnSearch").click(function () {                
        dtGrid.fnSearch(getFnData());
    });

    //初始化表格
    
    initGrid(startTime, endTime);
    if (imei) {
        $("#showli").show();
        $("#selPlates").val(eqpId);
        $("#s2id_selPlates .select2-chosen").text(plate + "|" + imei);
        dtGrid.fnSearch(getFnData());
    }

    //导出
    $("#btnExport").click(function () {
    	var startTime = $("#txtStartDate").val();
    	var endTime = $("#txtEndDate").val();
    	var eqpId = $("#selPlates").select2("val")
    	if(eqpId == "" || eqpId == null){
            toastr.warning("设备不能为空", "提示信息");
            return;
        }
        if (startTime == "" || typeof (startTime) == undefined) {
            toastr.warning("请选择开始时间", "提示信息");
            return;
        }
        if (endTime == "" || typeof (endTime) == undefined) {
            toastr.warning("请选择结束时间", "提示信息");
            return;
        }

    	window.location.href = basePath+"Track/ExportTrackRecord?" +
    		"startTime="+startTime+"&apikey="+apikey+"" +
    		"&endTime="+endTime+"&eqpId="+eqpId;
    });
});
function getFnData() {
    var startTime = $("#txtStartDate").val();
    var endTime = $("#txtEndDate").val();
    if (startTime == "" || typeof (startTime) == undefined) {
        toastr.warning("请选择开始时间", "提示信息");
        return;
    }
    if (endTime == "" || typeof (endTime) == undefined) {
        toastr.warning("请选择结束时间", "提示信息");
        return;
    }
    //查询行程信息
    var oData = [
        { "name": "eqpId", "value": $("#selPlates").select2("val") },
        { "name": "startTime", "value": startTime },
        { "name": "endTime", "value": endTime }
    ];
    return oData;
}
//绑定控件
function bindControl(startTime, endTime) {
    //绑定日期控件
	$('.searchDate').datetimepicker({
        format: "yyyy-mm-dd hh:ii", //选择日期后，文本框显示的日期格式
        language: 'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 0,
        forceParse: true,
        clearBtn: true,
        minuteStep: 10
    });
//    //绑定日期
//    $("#txtStartDate").datetimepicker({
//        format: 'yyyy-mm-dd hh:ii',
//        autoclose: true,
//        language: 'zh-CN',
//        minuteStep: 30
//    });
//
//    $("#txtEndDate").datetimepicker({
//        format: 'yyyy-mm-dd hh:ii',
//        autoclose: true,
//        language: 'zh-CN',
//        minuteStep: 30
//    });

    $("#txtStartDate").val(startTime);
    $("#txtEndDate").val(endTime);

    //绑定车牌
    $("#selPlates").select2({
        placeholder: "车牌",
        minimumInputLength: 3,
        allowClear: true,
        ajax: {
            url: 'Track/getVehclineByQuery',
            dataType: 'json',
            data: function (term, page) {
                return {
                    plate: term,
                    apikey:apikey
                };
            },
            results: function (data, page) {
            	var eqpList=[];
				if(data!=null){
					for(var i=0;i<data.vhecEqpList.length;i++){
						var person=new Object(); 
						person.id=data.vhecEqpList[i].eqpId;  
						person.text=data.vhecEqpList[i].plate +"|"+data.vhecEqpList[i].imei;
						eqpList.push(person);
					}
				}
                return { results: eqpList };
            }
        }
    });
}


//初始化表格
function initGrid(startTime, endTime) {
	
   var gridObj = {
		id: "dtGrid",
        sAjaxSource: "Track/getTrackRecord",
      
        userQueryParam: [
        //	{"name": "eqpId", "value": $("#selPlates").select2("val")},
        //	{"name": "startTime", "value": startTime },
        //	{"name": "endTime", "value": endTime },
        //	{"name": "iDisplayStart", "value": 0 },
        //	{"name": "iDisplayLength", "value": 10 }
        ],
        columns: [
            { "mDataProp": "startTime", "sTitle": "开始时间" },
            { "mDataProp": "endTime", "sTitle": "结束时间" },
            { "mDataProp": "runLength", "sTitle": "行程时长" },
            { "mDataProp": "mileage", "sTitle": "里程(km)" },
            { "mDataProp": "fuelConspt", "sTitle": "耗油量(L)" },
            { "mDataProp": "idleTime", "sTitle": "怠速时长" },
            { "mDataProp": "idleFuel", "sTitle": "怠速耗油量(L)" },
            { "mDataProp": "cumulativeOil", "sTitle": "油耗(L/100km)" },
            { "mDataProp": "avgSpeed", "sTitle": "平均车速(km/h)" },
            {
                //自定义列sName
                "mDataProp": "trackId",
                "sClass": "center",
                "sTitle": "详细信息",
                "bSearchable": false,
                "bStorable": false,
                "mRender": function (data, type, full) {
                    return '<a  href='+ basePath +'Track/trackRecordDetail?eqpId='+full.eqpId+'&trackId=' + full.trackId + ' class="btn default btn-xs blue"><img src="img/trafficflux/icon/checkDetail.png" alt=""/> 查看详情</a>';
                }
            }
        ],
        fnServerData222: function (sSource, aoData, fnCallback) {
        	
        	
            $.ajax({
            	"dataType": 'json',
                "type": "GET",
                "url": "Track/getTrackRecord",
                "data": aoData,
                "cache": false,
                "success": fnCallback,
               // "timeout": __Constant.ajaxTimeout,
            	"complete": function (obj) {
            		
                   	if(obj.responseJSON.aData!=null && obj.responseJSON.aData !=undefined){
                   		var data=obj.responseJSON.aData;
                   		$("#dTrackSum").text(data.strokeTimes);
                        $("#dMileageSum").text(data.mileage);
                        $("#dOilSum").text(data.totalFuel);
                        $("#dTimeSum").text(data.totalRunTime);
                        $("#dIdleTimeSum").text(data.totalIdleTime);
                        $("#dIdleFuelSum").text(data.totalIdleFuel);
                        $("#dAverageMileage").text(data.avgMileage);
                        var fuelConsumption = "0.00";
                        var idleFuelSum = data.totalIdleFuel;
                        fuelConsumption = (data.totalFuel - idleFuelSum) * 100 / data.mileage;
                        if(isNaN(fuelConsumption)){
                        	fuelConsumption = "0.00";
                        }
                        $("#dFuelConsumption").text(parseFloat(fuelConsumption).toFixed(2));
                   	}
                },
                "error": function (xhr, status, error) {
                    showerror(xhr.responseText);
                    dtGrid._fnProcessingDisplay(false);
                    return;
                }
            });
        }
    }
   
   dtGrid = renderGridTrafficflux(gridObj);
}
/**
 * 表格渲染
 * @param {} 
 */
function renderGridTrafficflux(gridObj) {
	var params = {
		bProcessing: gridObj.bProcessing ? gridObj.bProcessing : false,
		bServerSide: gridObj.bServerSide ? gridObj.bServerSide : true,
		lengthChange: gridObj.hasOwnProperty("lengthChange") ? gridObj.lengthChange : false,
		userQueryParam: gridObj.userQueryParam ? gridObj.userQueryParam : null,
		ordering: gridObj.ordering ? gridObj.ordering : false,
		searching: gridObj.searching ? gridObj.searching : false,
		bSort: gridObj.bSort ? gridObj.bSort : false,
		bInfo: undefined != gridObj.bInfo && null != gridObj.bInfo ? gridObj.bInfo : true,
		bFilter: gridObj.bFilter ? gridObj.bFilter : false,
		bAutoWidth: gridObj.bAutoWidth ? gridObj.bAutoWidth : true,
        iDisplayLength: gridObj.iDisplayLength ? gridObj.iDisplayLength : 10,
        sAjaxSource: gridObj.sAjaxSource,
        columns: gridObj.columns,
		createdRow:gridObj.createdRow? gridObj.createdRow : null,
        fnInitComplete: gridObj.fnInitComplete ? gridObj.fnInitComplete : null,
        language: {
			sProcessing: "处理中...",
			sLengthMenu: "显示 _MENU_ 项结果",
			sZeroRecords: "没有匹配结果",
			sInfo: "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
			sInfoEmpty: "显示第 0 至 0 项结果，共 0 项",
			sInfoFiltered: "(由 _MAX_ 项结果过滤)",
			sInfoPostFix: "",
			sSearch: "搜索:",
			sUrl: "",
			sEmptyTable: gridObj.language ? (gridObj.language.sEmptyTable ? gridObj.language.sEmptyTable : "表中数据为空") : "表中数据为空",
			sLoadingRecords: "载入中...",
			sInfoThousands: ",",
			oPaginate: {
				sFirst: "首页",
				sPrevious: "上页",
				sNext: "下页",
				sLast: "末页"
			},
			oAria: {
			sSortAscending: ": 以升序排列此列",
			sSortDescending: ": 以降序排列此列"
		    }
        },
       
            fnServerData: function(sSource, aoData, fnCallback) {
            	if(gridObj.userQueryParam != null) {
    	        	aoData = aoData.concat(gridObj.userQueryParam);
    	        	
            	}
            	if(gridObj.fnServerData222){
            	var a=gridObj.fnServerData222;
            	a(sSource, aoData, fnCallback);
            	}
            
//            	
//    			$.ajax({
//    	        	url: gridObj.sAjaxSource,
//    	            data: aoData,
//    	        	type: "GET",
//    				dataType: 'json',
//    	            contentType: 'application/json; charset=utf-8',
//    	            cache: false,
//    	            success: function (obj) {
//            		
//                   	if(obj.responseJSON.aData!=null && obj.responseJSON.aData !=undefined){
//                   		var data=obj.responseJSON.aData;
//                   		$("#dTrackSum").text(data.strokeTimes);
//                        $("#dMileageSum").text(data.mileage);
//                        $("#dOilSum").text(data.totalFuel);
//                        $("#dTimeSum").text(data.totalRunTime);
//                        $("#dIdleTimeSum").text(data.totalIdleTime);
//                        $("#dIdleFuelSum").text(data.totalIdleFuel);
//                        $("#dAverageMileage").text(data.avgMileage);
//                        var fuelConsumption = "0.00";
//                        var idleFuelSum = data.totalIdleFuel;
//                        fuelConsumption = (data.totalFuel - idleFuelSum) * 100 / data.mileage;
//                        if(isNaN(fuelConsumption)){
//                        	fuelConsumption = "0.00";
//                        }
//                        $("#dFuelConsumption").text(parseFloat(fuelConsumption).toFixed(2));
//                   	}
//                },
//    	            error: function(msg) {
//    	            	
//    	            }
//    			})
    		}
		
	}


	if(gridObj.scrollX) {
		params["scrollX"] = true;
		params["scrollCollapse"] = true;
	}
	var dataTable = $("#" + gridObj.id).dataTable(params);
	
	// 固定列属性设置
	if(gridObj.iLeftColumn) {
		new $.fn.dataTable.FixedColumns(dataTable,{"iLeftColumns": gridObj.iLeftColumn});
	}
	
	return dataTable;
}
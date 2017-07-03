/*$(function(){
	var resultObj = "";
	showAlarmprocessPop(resultObj)
})*/
/**人工处理交接班处理逻辑start**/

/**
 * 不予提现取消
 */
function cancelNotDiv(){
	$("#notcashdiv").hide();
}


function checkProcessing(id){
	var data = {'id':id};
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "driverShift/processing",
		data: data,
		async: false,
		success: function (result) {
			if(result){
				shownotcashdiv(id);
			}else{
				closePop(id);
				toastr.success("该交接班的人工指派已被其他客服处理！", "提示");

			}
		},
		fail:function(){
			shownotcashdiv(id);
		}
	});
}


/**
 * 弹出选择接班司机框
 * @param id
 */
function shownotcashdiv(id){
	$("#pendingId").val(id);
	$("#notcashdiv").show();
	var data = {'id':id};
	var selObj = $("#plateNoProvince");
	$.ajax({
		type: "POST",
		dataType: "json",
		url: "driverShift/listBindDrivers",
		data: data,
		// contentType: "application/json; charset=utf-8",
		async: false,
		success: function (result) {
			var message = result.message == null ? result : result.message;
			if (result.status == "0") {
				$("input[name='shftType']").get(0).click();
				selObj.find("option").remove();
				selObj.append( "<option value=''>请选择</option>" );
				for(var i=0;i<result.data.length;i++){
					var dataObj = result.data[i];
					selObj.append( "<option value='"+dataObj.driverID+"'>"+dataObj.text+"</option>" );
				}
			} else {
				$("#notcashdiv").hide()
				closePop(id);
				toastr.error(message, "提示");
			}
		}
	});
}


function showPendingPop(resultObj){
	var pop = new Pop(resultObj.title,
		"checkProcessing(\""+resultObj.id+"\")",
		"<table class=\"table_a\">" +
		"<tr style='height: 30px'><td class=\"grey_c\">车牌号：</td><td style='text-align: left'>"+resultObj.platenoStr+"</td></tr>" +
		"<tr style='height: 30px'><td class=\"grey_c\">当班司机：</td><td >"+resultObj.driverInfo+"</td></tr>" +
		"<tr style='height: 30px'><td class=\"grey_c\">在线时长：</td><td >"+resultObj.onlinetimeStr+"</td></tr></table>",
		resultObj.id,true,resultObj.extime);
	pop.showDiv();
	pop.closeDiv();
}


/**
 * 不予提现取消
 */
function cancelNotDiv(){
	$("#notcashdiv").hide();
}


/**
 * 交接班操作
 * @param driverId
 * @param pendingId
 */
function processed(){
	var pendingId = $('#pendingId').val();
	var driverId = $("#plateNoProvince").val();
	var driverStr = $("#plateNoProvince").find("option:selected").text();
	var type = $("input[name='shftType']:checked").val();
	//初始化参数
	var data = {pendingId: pendingId,shifttype:type};

	var str = '';
	if(!type){
		toastr.warning("请选择交班类型", "提示");
		return;
	}
	if(type==0 && !driverId){
		toastr.warning("请选择接班司机", "提示");
		return ;
	}
	//交接班
	if(type==0){
		str = "确定进行交接班？"
		data.relieveddriverInfo = driverStr;
		data.relieveddriverid=driverId;
	}else{
		//车辆回收
		str = "确定进行车辆回收？"
	}
	Zconfirm(str,function(){
		$.ajax({
			type: "POST",
			dataType: "json",
			url: "driverShift/processd",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			async: false,
			success: function (result) {
				var message = result.message == null ? result : result.message;
				if (result.status == "0") {
					toastr.success(message, "提示");
				} else {
					toastr.error(message, "提示");
				}
				$("#notcashdiv").hide();
				closePop(pendingId);
			}
		});
	});

}


$("input:radio[name='shftType']").click(function(){
	if($(this).val()=='0'){
		/**
		 * 选择交班司机处理
		 */
		$("#plateNoProvince").removeAttr("disabled");
	}else if($(this).val()=='1'){
		/**
		 * 选择车辆回收处理
		 */
		$("#plateNoProvince").val("");
		$("#plateNoProvince").attr("disabled","disabled")
	}

});


/**人工处理交接班处理逻辑end**/


/**报警弹窗 start**/
function showAlarmprocessPop(resultObj){
	var pop = new Pop(resultObj.title,
		"alarmprocess(\""+resultObj.id+"\")",
		"<table class=\"table_a\">" +
		"<tr style='height: 30px'><td class=\"grey_c\">报警来源：</td><td style='text-align: left'>"+resultObj.alarmsource+"</td></tr>" +
		"<tr style='height: 30px'><td class=\"grey_c\">报警类型：</td><td >"+resultObj.alarmtype+"</td></tr></table>",
		resultObj.id,true);
	pop.delay = 600000;
	pop.showDiv();
	pop.closeDiv();
}
function alarmprocess(id){
	var data = {id:id}
	$.ajax({
		type: "POST",
		url:"PubAlarmprocess/HandleOK",
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		data:JSON.stringify(data),
		success: function (result) {
			if(result){
				//跳转到已处理界面
				 window.open($("#baseUrl").val()+"PubAlarmprocessDaichuliOK/Index?id="+id,"iframe");
				 closePop(id);
			}else{
				closePop(id);
				toastr.success("该报警已经被其他客服处理！", "提示");

			}
		},
		fail:function(){
//			window.location.href = $("#baseUrl").val() + "PubAlarmprocessDaichuli/Index"
		}
 });
}
/**报警弹窗end**/

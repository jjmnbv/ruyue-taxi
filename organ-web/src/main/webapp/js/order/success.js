$(function(){
	initElement();
	checkOrderState();
});

/**
 * 初始化
 */
function initElement(){
	$(".btn_left").click(function(){
		window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + "Order/Details?id="+$("#orderno").val();
//		alert("这里应该跳转到[我的订单]页面");
	});
	
	$(".btn_right").click(function(){
		showCancelWindow();
	});
	$(".cancel,.close,.sure").click(function(){
		   $("#window").hide();
	});
	$("#cancel .popup_footer .sure").click(function(){
		cancelOrder($("#orderno").val());
	});
}

/**
 * 显示取消窗口
 */
function showCancelWindow(){
	$("#table").hide();
	$("#cancel").show();
	$("#window").show();
}

/**
 * 检查订单状态
 * @param result
 */
function checkOrderState(result){
	if(!result){
		result = {
				status:0,
				orderno:$("#orderno").val(),
				order:{
					driverid:'',
					orderstatus:0
				}
		};
    	$.ajax({
    	    type: 'GET',
    	    url: "Order/CheckOrderState/"+ result.orderno,
    	    dataType: "json",
    	    success: function(data){
    	    	checkOrderState(data);
    	    },
    	    complete: ajaxComplete
    	});
    	return;
	}
	if(result.status == 0){
    	$("#orderno").val(result.orderno);
		if(result.order){
			$("#ordertype").val(result.order.ordertype);
			$("#table .popup_content td").eq(1).text(result.order.passengers+"("+result.order.passengerphone+")");
			$("#table .popup_content td").eq(3).text(timeStamp2String(result.order.usetime));
			$("#table .popup_content td").eq(5).text(result.order.onaddress);
			$("#table .popup_content td").eq(7).text(result.order.offaddress);
			if(result.order.driverid != ""){
				$("#table .popup_title span").text("派单成功,有司机接单了.");
				$("#table").show();
				$("#cancel").hide();
				$("#window").show();
			}else if(result.order.orderstatus == "8"){
				$("#table .popup_title span").text("下单失败.");
				$("#table").show();
				$("#cancel").hide();
				$("#window").show();
//			}else if(result.order.orderstatus == "1"){
//				$("#table .popup_title span").text("订单已进入人工派单流程.");
//				$("#table").show();
//				$("#cancel").hide();
//				$("#window").show();
			}else{
				setTimeout(function(){
		        	$.ajax({
		        	    type: 'GET',
		        	    url: "Order/CheckOrderState/"+ result.orderno,
		        	    dataType: "json",
		        	    success: function(data){
		        	    	checkOrderState(data);
		        	    }
		        	});
				},3000);	
			}
		}
	}else{
		$("#table .popup_title span").text("派单失败.");
	}
}

/**
 * 取消订单
 * @param result
 */
function cancelOrder(orderno){
	$.ajax({
	    type: 'GET',
	    url: "Order/CancelOrder/" +  orderno + "?ordertype=" + $("#ordertype").val() + "?date=" + new Date(),
	    dataType: "json",
	    success: function(data){
	    	if(data.status == 0){
	    		switch ($("#ordertype").val()) {
				case "1":
					window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + "Order/yueche/"+orderno;
					break;
				case "2":
					window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + "Order/jieji/"+orderno;
					break;
				case "3":
					window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + "Order/songji/"+orderno;
					break;
				default:
					window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + "Order/yueche/"+orderno;
					break;
				}
	    	}else{
//	    		alert(data.message);
	    	}
	    }
	});
}

function timeStamp2String(time, format){
	var mark = "-";
	if(null != format && format.length > 0) {
		mark = format;
	}
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
	var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	return year + mark + month + mark + date+" "+hour+":"+minute;
}
$(function(){
	initElement();
//	checkOrderState();
});

/**
 * 初始化
 */
function initElement(){
	$(".btn_left").click(function(){
		if($("#paymethod").val() == "2"){
			window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + "OrderManage/OrgOrderDetailIndex?orderno="+$("#orderno").val();
		}else{
			window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + "OrderManage/PersonOrderDetailIndex?orderno="+$("#orderno").val();
		}
//		alert("这里应该跳转到[我的订单]页面");
	});
	
	$(".btn_right").click(function(){
		$("#cancel").show();
	});
	$(".cancel,.close,.sure").click(function(){
		   $(".popup_box").hide();
	});
	$("#cancel .popup_footer .sure").click(function(){
		cancelOrder($("#orderno").val());
	});
	$("#hint .popup_footer .sure").click(function(){
		dispatcher2OrderPage();
	});
}

/**
 * 跳转页面
 */
function dispatcher2OrderPage(){
	switch ($("#ordertype").val()) {
	case "1":
		window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + "Order/yueche";
		break;
	case "2":
		window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + "Order/jieji";
		break;
	case "3":
		window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + "Order/songji";
		break;
	default:
		window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + "Order/yueche";
		break;
	}
}

/**
 * 取消订单
 */
function cancelOrder(){
    var p = {"orderno":$("#orderno").val()};
   	$.ajax({
	    type: 'POST',
	    url: "Order/CancelOrder" ,
	    data: JSON.stringify(p),
	    dataType: "json",
	    contentType:"application/json",
	    success:function(data){
	    	if(data.status == 0 || data.status == 2003){
	    		dispatcher2OrderPage();
	    	}else if(data.status == 2007){
	    		$("#hint").show();
	    	}else{
//		    		alert(data.message);
	    	}
	    }
	});
}

$(function(){
	initElement();
//	checkOrderState();
});

/**
 * 初始化
 */
function initElement(){
	$(".btn_left").click(function(){
		manticOrder();
	});
	
	$(".btn_right").click(function(){
		showCancelWindow();
	});
	$(".cancel,.close,.sure").click(function(){
		   $(".popup_box").hide();
	});
	$("#cancel .popup_footer .sure").click(function(){
		cancelOrder($("#orderno").val());
	});
}

/**
 * 人工派单
 */
function manticOrder(){
	window.location.href=document.getElementsByTagName("base")[0].getAttribute("href") + 'OrderManage/ManualSendOrderIndex?orderno='+$("#orderno").val()+'&type=1';
}

/**
 * 显示取消窗口
 */
function showCancelWindow(){
	$(".popup_box").show();
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
	    	if(data.status == 0){
		    	if(data.status == 0){
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
		    	}else{
//		    		alert(data.message);
		    	}
	    	}
	    }
	});
}

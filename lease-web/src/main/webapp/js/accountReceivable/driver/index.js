function allocation(allocation,val){
	$.post("AccountReceivable/CheckAlipayOrWechat",function (status) {
		if((status.driveralipayaccount == null || status.driveralipayaccount == '') && val == '支付宝'){
			allocationData(allocation);
		}else if((status.driverwechatpayaccount == null || status.driverwechatpayaccount == '') && val == '微信'){
			allocationData(allocation);
		}else{
			var comfirmData={
				tittle:"提示",
				context:"<h4>确定修改"+val+"收款账户配置？</h4><br><font color='red'>收款账户涉及乘客、司机及机构客户的充值及收款，修改后，款项将进入新配置账户</font>",
				contextAlign:"left",
				button_r:"取消",
				button_l:"确定",
				click: "allocationData('"+allocation+"')",
				htmltex:"<input type='hidden' placeholder='添加的html'> "
			};
			ZconfirmLeft(comfirmData);
		}
	});
}
//配置账户
function allocationData(allocation){
   	window.location.href = base+"AccountReceivable/driver/AllocationIndex?allocation="+allocation;
}

//支付宝开通账户
function openAlipay(){
	$.post("AccountReceivable/CheckAlipayOrWechat",function (status) {debugger
		if (status.driveralipayaccount != null && status.driveralipayaccount != '') {
			var comfirmData={
				tittle:"提示",
				context:"确定开通支付宝收款功能？",
				button_r:"取消",
				button_l:"确定",
				click: "openAlipays('1')",
				htmltex:"<input type='hidden' placeholder='添加的html'> "
			};
			ZconfirmLeft(comfirmData);
		} else {
			Zalert("提示","请先配置支付宝收款账户");
		}
	});
}
function openAlipays(obj){
	var data = {
		obj : obj
	}
	$.post("AccountReceivable/driver/OpenOrCloseAlipay",data,function (status) {
		toastr.options.onHidden = function() {
			window.location.href = base+"AccountReceivable/driver/Index";
		}
		toastr.success(status.succ,"提示");
	});
}
//支付宝禁用账户
function closeAlipay(){
	$.post("AccountReceivable/CheckAlipayOrWechat", function (status) {
		if (status.driverwechatpayaccount != null && status.driverwechatpayaccount != '') {
			if(status.driverwechatstatus != '1'){
				Zalert("禁用失败","平台需支持至少一种在线支付渠道，当前账户为唯一在线收款账户，不可禁用");
			}else{
				var comfirmData={
					tittle:"提示",
					context:"<h4>确定关闭支付宝收款功能？</h4><br><font color='red'>关闭后，乘客端、司机端、机构端将不能使用支付宝进行支付或充值</font>",
					contextAlign:"left",
					button_r:"取消",
					button_l:"确定",
					click: "closeAlipays('0')",
					htmltex:"<input type='hidden' placeholder='添加的html'> "
				};
				ZconfirmLeft(comfirmData);
			}
		} else {
			Zalert("禁用失败","平台需支持至少一种在线支付渠道，当前账户为唯一在线收款账户，不可禁用");
		}
	});
}
function closeAlipays(obj){
	var data = {
		obj : obj
	}
	$.post("AccountReceivable/driver/OpenOrCloseAlipay",data,function (status) {
		toastr.options.onHidden = function() {
			window.location.href = base+"AccountReceivable/driver/Index";
		}
		toastr.success(status.succ,"提示");
	});
}
//微信开通账户
function openWechat(){
	$.post("AccountReceivable/CheckAlipayOrWechat", function (status) {
		if (status.driverwechatpayaccount != null && status.driverwechatpayaccount != '') {
			var comfirmData={
				tittle:"提示",
				context:"确定开通微信收款功能？",
				button_r:"取消",
				button_l:"确定",
				click: "openWechats('1')",
				htmltex:"<input type='hidden' placeholder='添加的html'> "
			};
			ZconfirmLeft(comfirmData);
		} else {
			Zalert("提示","请先配置微信收款账户");
		}
	});
}
function openWechats(obj){
	var data = {
		obj : obj
	}
	$.post("AccountReceivable/driver/OpenOrCloseWechat",data,function (status) {
		toastr.options.onHidden = function() {
			window.location.href = base+"AccountReceivable/driver/Index";
		}
		toastr.success(status.succ,"提示");
	});
}
//微信禁用账户
function closeWechat(){
	$.post("AccountReceivable/CheckAlipayOrWechat", function (status) {
		if (status.driveralipayaccount != null && status.driveralipayaccount != '') {
			if(status.driveralipaystatus != '1'){
				Zalert("禁用失败","平台需支持至少一种在线支付渠道，当前账户为唯一在线收款账户，不可禁用");
			}else{
				var comfirmData={
					tittle:"提示",
					context:"<h4>确定关闭微信收款功能？</h4><br><font color='red'>关闭后，乘客端、司机端、机构端将不能使用微信进行支付或充值</font>",
					contextAlign:"left",
					button_r:"取消",
					button_l:"确定",
					click: "closeWechats('0')",
					htmltex:"<input type='hidden' placeholder='添加的html'> "
				};
				ZconfirmLeft(comfirmData);
			}
		} else {
			
			Zalert("禁用失败","平台需支持至少一种在线支付渠道，当前账户为唯一在线收款账户，不可禁用");
		}
	});
}
function closeWechats(obj){
	var data = {
		obj : obj
	}
	$.post("AccountReceivable/driver/OpenOrCloseWechat",data,function (status) {
		toastr.options.onHidden = function() {
			window.location.href = base+"AccountReceivable/driver/Index";
		}
		toastr.success(status.succ,"提示");
	});
}

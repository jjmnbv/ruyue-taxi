	$(function() {
		initGrid();
		initHref();
		$('.date').datetimepicker({
			format : "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
			language : 'zh-CN', //汉化
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0,
			clearBtn: true
		});
		$(".queryOrder li").live("click", function(){ 
		/* var queryOrder = $("#queryOrder").attr("data-value");
		var conditionArr = [
			{ "name": "queryOrder", "value": queryOrder }
		];
		dataGrid.fnSearch(conditionArr); */
			search();
		});
		$(".queryVehicleMode li").live("click", function(){ 
			/* var queryVehicleMode = $("#queryVehicleMode").attr("data-value");
			var conditionArr = [
				{ "name": "queryVehicleMode", "value": queryVehicleMode }
			];
			dataGrid.fnSearch(conditionArr); */
			search();
		});
		$(".queryPaymentMethod li").live("click", function(){ 
			/* var queryPaymentMethod = $("#queryPaymentMethod").attr("data-value");
			var conditionArr = [
				{ "name": "queryPaymentMethod", "value": queryPaymentMethod }
			];
			dataGrid.fnSearch(conditionArr); */
			var queryPaymentMethod = $("#queryPaymentMethod").attr("data-value");
			var html = "";
			if('1' == queryPaymentMethod){
				html += '<li data-value="">全部订单状态</li>';
				html += '<li data-value="0,1">等待接单</li>';
				html += '<li data-value="2,3,4">等待服务</li>';
				html += '<li data-value="5,6">服务中</li>';
				html += '<li data-value="7-0">未支付</li>';
				html += '<li data-value="7-1">已支付</li>';
				html += '<li data-value="8">已取消</li>';
//				html += '<li data-value="7-4">未结算</li>';
//				html += '<li data-value="7-2">结算中</li>';
//				html += '<li data-value="7-3">已结算</li>';
			}else if('2' == queryPaymentMethod){
				html += '<li data-value="">全部订单状态</li>';
				html += '<li data-value="0,1">等待接单</li>';
				html += '<li data-value="2,3,4">等待服务</li>';
				html += '<li data-value="5,6">服务中</li>';
				html += '<li data-value="7-4">未结算</li>';
				html += '<li data-value="7-2">结算中</li>';
				html += '<li data-value="7-3">已结算</li>';
//				html += '<li data-value="7-0">未支付</li>';
//				html += '<li data-value="7-1">已支付</li>';
				html += '<li data-value="8">已取消</li>';
			}else{
				html += '<li data-value="">全部订单状态</li>';
				html += '<li data-value="0,1">等待接单</li>';
				html += '<li data-value="2,3,4">等待服务</li>';
				html += '<li data-value="5,6">服务中</li>';
				html += '<li data-value="7-0">未支付</li>';
				html += '<li data-value="7-1">已支付</li>';
				html += '<li data-value="7-4">未结算</li>';
				html += '<li data-value="7-2">结算中</li>';
				html += '<li data-value="7-3">已结算</li>';
				html += '<li data-value="8">已取消</li>';
			}
			$(".queryOrder .select_content").html(html);
			search();
		});
	});
	/**
	 * 点击账单管理每行数据时，跳转到账单明细
	 */
	function initHref() {
		$("table > tbody > tr").live('click',function () {
			//alert( $(this).find("td:first").text());
			if ($(this).find("td").length > 1) {
				window.location.href = base+"MyOrder/Details?id=" + $(this).find("td:first").text()+"&date="+new Date();
			}
	    });
	}
	/**
	 * 表格初始化
	 */
	function initGrid() {
		var gridObj = {
			id : "dataGrid",
			sAjaxSource : "MyOrder/GetOrgderByQuery",
			language: {
	        	sEmptyTable: "您还没有用车记录"
	        },
			columns : [ {
				mDataProp : "orderno",
				sTitle : "订单号",
				sClass : "center",
				sortable : true
			}, {
				mDataProp : "userMessage",
				sTitle : "乘车人信息",
				sClass : "center",
				sortable : true
//				,
//				mRender : function(data, type, full) {
//					var html = "";
//					html+= "<a href='MyOrder/Details?id="+full.orderno+"'>"+full.userMessage+"</a>";
//					return html;
//				}
			}, {
				mDataProp : "companyName",
				sTitle : "服务车企",
				sClass : "center",
				sortable : true
			}, {
				mDataProp : "ordertype",
				sTitle : "用车方式",
				sClass : "center",
				sortable : true
			}, {
				mDataProp : "usetime",
				sTitle : "用车时间",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						return changeToDate(data);
					} else {
						return "";
					}
				}
			}, {
				mDataProp : "orderStatusShow",
				sTitle : "订单状态",
				sClass : "center",
				sortable : true
			}, {
				mDataProp : "payTypeShow",
				sTitle : "支付方式",
				sClass : "center",
				sortable : true
			} , {
				mDataProp : "orderamount",
				sTitle : "金额（元）",
				sClass : "center",
				sortable : true
			}  ]
		};
	
		dataGrid = renderGrid(gridObj);
	}
	
	/**
	 * 查询
	 */
	function search() {
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var d1 = new Date(startTime.replace(/\-/g, "\/"));  
		var d2 = new Date(endTime.replace(/\-/g, "\/"));  
		if(startTime!="" && endTime!="" && d1 > d2){  
			alert("开始时间不能大于结束时间！");  
			return;  
		}else{
			var conditionArr = [
				{ "name": "startTime", "value": $("#startTime").val() },
				{ "name": "endTime", "value": addDate($("#endTime").val(),1) },
				{ "name": "queryUserMessage", "value": $("#queryUserMessage").val() },
				{ "name": "queryOrderTemp", "value": $("#queryOrder").attr("data-value") },
				{ "name": "queryVehicleMode", "value": $("#queryVehicleMode").attr("data-value") },
				{ "name": "queryPaymentMethod", "value": $("#queryPaymentMethod").attr("data-value") }
			];
			dataGrid.fnSearch(conditionArr,"没有查询到相关用车信息");
			$("#startTimes").val($("#startTime").val());
			$("#endTimes").val($("#endTime").val());
			$("#queryUserMessages").val($("#queryUserMessage").val());
			$("#queryOrders").val($("#queryOrder").attr("data-value"));
			$("#queryVehicleModes").val($("#queryVehicleMode").attr("data-value"));
			$("#queryPaymentMethods").val($("#queryPaymentMethod").attr("data-value"));
		}
	}
	
	function addDate(date, days) {
	    if (days == undefined || days == '') {
	        days = 1;
	    }
		if(date != '' && date != undefined && date != null){
	        var date = new Date(date);
	        date.setDate(date.getDate() + days);
	        var month = date.getMonth() + 1;
	        var day = date.getDate();
	        return date.getFullYear() + '-' + month + '-' + day;
		}else{
			return "";
		}	
	}
	/**
	 * 毫秒转日期
	 * 
	 * @param data
	 * @returns {String}
	 */
	function changeToDate(data) {//.replace(/-/g,"/")
		var myDate = new Date(data);
		var month = "";
		var date = "";
		var hours = "";
		var minutes = "";
		var change = "";
		change += myDate.getFullYear() + "-";
	
		if (myDate.getMonth() < 9) {
			month = "0" + (myDate.getMonth() + 1);
		} else {
			month = (myDate.getMonth() + 1);
		}
		change += month + "-";
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
		return change;
	}
	function empty(){
		$("#queryCompany").val("");
		$("#queryCompany").attr("data-value","");
		$("#queryOrderNo").val("");
		$("#startTime").val("");
		$("#endTime").val("");
		$("#queryUserMessage").val("");
		$("#queryOrder").val("");
		$("#queryOrder").attr("data-value","");
		$("#queryVehicleMode").val("");
		$("#queryVehicleMode").attr("data-value","");
		$("#queryPaymentMethod").val("");
		$("#queryPaymentMethod").attr("data-value","");
		
		var html = "";
		html += '<li data-value="">全部订单</li>';
		html += '<li data-value="0,1">等待接单</li>';
		html += '<li data-value="2,3,4">等待服务</li>';
		html += '<li data-value="5,6">服务中</li>';
		html += '<li data-value="7-0">未支付</li>';
		html += '<li data-value="7-1">已支付</li>';
		html += '<li data-value="7-4">未结算</li>';
		html += '<li data-value="7-2">结算中</li>';
		html += '<li data-value="7-3">已结算</li>';
		html += '<li data-value="8">已取消</li>';
		$(".queryOrder .select_content").html(html);
		emptySearch();
		dataGrid.fnSearch("","您还没有用车记录");
	};
	function empty1(){
		$("#queryOrder").val("");
		$("#queryOrder").attr("data-value","");
		$("#queryVehicleMode").val("");
		$("#queryVehicleMode").attr("data-value","");
		$("#queryPaymentMethod").val("");
		$("#queryPaymentMethod").attr("data-value","");
		
		var html = "";
		html += '<li data-value="">全部订单</li>';
		html += '<li data-value="0,1">等待接单</li>';
		html += '<li data-value="2,3,4">等待服务</li>';
		html += '<li data-value="5,6">服务中</li>';
		html += '<li data-value="7-0">未支付</li>';
		html += '<li data-value="7-1">已支付</li>';
		html += '<li data-value="7-4">未结算</li>';
		html += '<li data-value="7-2">结算中</li>';
		html += '<li data-value="7-3">已结算</li>';
		html += '<li data-value="8">已取消</li>';
		$(".queryOrder .select_content").html(html);
		emptySearch();
		dataGrid.fnSearch("","您还没有用车记录");
	};
	function emptySearch(){
		$("#startTimes").val("");
		$("#endTimes").val("");
		$("#queryUserMessages").val("");
		$("#queryOrders").val("");
		$("#queryVehicleModes").val("");
		$("#queryPaymentMethods").val("");
	}
	function exportExcel(){
		var startTime=$("#startTimes").val();
		var endTime=addDate($("#endTimes").val(),1);
		var queryUserMessage=$("#queryUserMessages").val();
		var queryOrderTemp=$("#queryOrders").val();
		var queryVehicleMode=$("#queryVehicleModes").val();
		var queryPaymentMethod=$("#queryPaymentMethods").val();
		window.location.href = base+'MyOrder/ExportExcel?startTime='+startTime+"&endTime="+endTime
		+"&queryUserMessage="+queryUserMessage+"&queryOrderTemp="+queryOrderTemp+"&queryVehicleMode="+queryVehicleMode+"&queryPaymentMethod="+queryPaymentMethod;
		
		$("#startTime").blur();
		$("#endTime").blur();
		$("#queryUserMessage").blur();
		$("#queryOrder").blur();
		$("#queryVehicleMode").blur();
		$("#queryPaymentMethod").blur();
	};

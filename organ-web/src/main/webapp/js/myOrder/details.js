 var points = [];
    //一些其余的本页js
    $(document).ready(function() {
        $(".font-blue").click(function(){
            $(".feiyon").show();
        });
        $(".btn_red").click(function(){
            $(".feiyon").hide();
        });
        $(".trail").click(function(){
            $(".ditu").show();
            var orderno = $("#orderno").val();
            var data = {
           		orderno:orderno
            }
           $.get("MyOrder/GetOrgOrderByOrderno",data,function(result){
        	   initTraceData(result);
           });
        });
    });
    /**
	 * 初始化订单数据
	 */
	function initTraceData(order) {
		// var data = {orderno: order.orderno, startDate: timeStamp2String(order.undertime)};
		var endTime = "";
		if(order.orderstatus == '7'){
			//结束时间 
			endTime = changeToDate(order.endtime);
		}else{
			//当前时间
			endTime = "";
		}
		var data = {
			orderno: order.orderno, 
//			orderno : 'YC11171446168870016',
//			startDate: "2016-11-17 14:46:49",
			startDate: changeToDate(order.starttime),
//			endDate: "2016-11-17 15:17:34"
			endDate: endTime
		};
		
		$.get("MyOrder/GetOrgOrderTraceData", data, function (result) {
			if (result && result.status =="0") {
				bindMap(result, order);
				bindGps(result, order);
			} else {
				bindMap(result, order);
//				bindGps(result, order);
			}
		});
	}
	/**
	 * 界面地图渲染
	 * @param {} order
	 */
	function bindMap(traceData, order) {
		map = new BMap.Map("map_canvas");
		var point = "";
		if(traceData != null && traceData != '' && traceData.points != null && traceData.points.length > 0){
			point = new BMap.Point(traceData.points[0].location[0], traceData.points[0].location[1]);
		}else{
			point = new BMap.Point(order.onaddrlng, order.onaddrlat);
		}
		map.centerAndZoom(point, 15);
		map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
		map.enableScrollWheelZoom();
	}
	 /**
	  * 行驶轨迹渲染
	  * @param {} gpsList
	  * @param {} order
	  */
	 function bindGps(traceData, order) {
		points = [];
	 	map.clearOverlays();
	 	
	 	if (traceData == null || traceData == undefined) {
	 		return;
	    }
    	gpsList = traceData.points;
		if(gpsList != null && gpsList.length > 0) {
			var point = new BMap.Point(gpsList[0].location[0], gpsList[0].location[1]);
			var icon = new BMap.Icon("img/orgordermanage/icon_shangche.png", new BMap.Size(48, 48));
			var marker = new BMap.Marker(point, { icon: icon });
		 	map.addOverlay(marker);
		 	
		 	var content = "<p><font style='font-weight:bold;'>&nbsp服务开始时间：" + order.starttime +
		             "</font></p><p>&nbsp上车地点：" + order.onaddress + "</p>";
			var contentObj = { content: content };
			marker.addEventListener("click", "hehe");
			map.panTo(point);
			
			if(order.orderstatus != '7'){
		 		icon = new BMap.Icon("img/orgordermanage/icon_zaixian.png", new BMap.Size(48, 48));
			}else{
				icon = new BMap.Icon("img/orgordermanage/icon_xiache.png", new BMap.Size(48, 48));
			}
			marker = new BMap.Marker(new BMap.Point(gpsList[gpsList.length - 1].location[0], gpsList[gpsList.length - 1].location[1]), { icon: icon });
			map.addOverlay(marker);
			content = "<p><font  style='font-weight:bold;'>服务结束时间：" + order.endtime +
			   "</font></p><p>&nbsp下车地点：" + order.offaddress + "</p>";
			contentObj = { content: content };
			marker.addEventListener("click", "hehe");
		 	
			for (var i = 0; i < gpsList.length; i++) {
				var point = new BMap.Point(gpsList[i].location[0], gpsList[i].location[1]);
				points.push(point);
			}
		}
	     
//	 	var iconSize = new BMap.Size(48, 48);
//	 	car = new BMap.Marker(points[0], { icon: icon = new BMap.Icon("img/orgordermanage/icon_zaixian.png", iconSize) });
//	 	map.addOverlay(car);
	     
	 	var polyline = new BMap.Polyline(points, { strokeColor: "#00FF7F", strokeWeight: 6, strokeOpacity: 0.5 });
	 	map.addOverlay(polyline);
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
			if (myDate.getSeconds()<10){
				second = "0"+myDate.getSeconds();
			}else{
				second = myDate.getSeconds();
			}
			change += ":" + second;
			return change;
		}

//--------------------------------------------------------
//returnVal为点击后的返回值
//--------------------------------------------------------
//在ie下获取类名
var carInfo = document.getElementById("car_info");
if(!carInfo.getElementsByClassName){ 
	carInfo.getElementsByClassName = function(className){ 
		var children = carInfo.getElementsByTagName('*'); 
		var elements = new Array(); 
		for (var i=0; i<children.length; i++){ 
			var child = children[i]; 
			var classNames = child.className.split(' '); 
			for (var j=0; j<classNames.length; j++){ 
				if (classNames[j] == className){ 
					elements.push(child); 
					break; 
				} 
			} 
		} 
		return elements; 
	}; 
}

//获取数据方法,默认为get
function ajax(options) { 
	options = options || {}; 
	options.type = (options.type || "GET").toUpperCase(); 
	options.dataType = options.dataType || "json"; 
	data = options.data;
//	格式化参数 
	function formatParams(data) { 
		var arr = []; 
		for (var name in data) { 
			arr.push(encodeURIComponent(name) + "=" + encodeURIComponent(data[name])); 
		} 
		arr.push(("v=" + Math.random()).replace(".","")); 
		return arr.join("&");
	}
	var params = formatParams(options.data); 
//	创建 - 非IE6 - 第一步 	
	if (window.XMLHttpRequest) {
		var xhr = new XMLHttpRequest(); 
	} else { 
//		IE6及其以下版本浏览器 
		var xhr = new ActiveXObject('Microsoft.XMLHTTP'); 
	}
//  接收 - 第三步 
	xhr.onreadystatechange = function () { 
		if (xhr.readyState == 4) { 
			var status = xhr.status; 
			if (status >= 200 && status < 300) {
				options.success && options.success(xhr.responseText, xhr.responseXML); 
			} else { 
				options.fail && options.fail(status); 
			} 
		} 
	}
//	连接 和 发送 - 第二步 
	if (options.type == "GET") { 
		xhr.open("GET", options.url + "?" + params, true); 
		xhr.send(null); 
	} else if (options.type == "POST") {
		xhr.open("POST", options.url, true); 
//		设置表单提交时的内容类型 
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
		xhr.send(params); 
	}
}	

/**
 * 车型初始化
 * @param {} params
 */
function initCarType(params) {
	ajax({
		url: "Order/GetCarTypes/", 
		type: "GET", 			
		dataType: "json", 
		data: params,
		success: function (res, xml) {
	//		数据转换成数组对象
			var res = eval(res) ;
	//		被克隆节点
			var node = carInfo.getElementsByClassName("item")[0];
	//		循环克隆
			for (var i = 0; i < res.length; i++) {
				var item = node.cloneNode(true);
				item.children[0].innerHTML = res[i].model;
				item.children[1].children[0].innerHTML = res[i].startprice;
				item.children[2].children[0].innerHTML = res[i].rangeprice;
				item.children[3].children[0].innerHTML = res[i].timeprice;
				item.children[4].setAttribute("src",res[i].img) ;
				carInfo.getElementsByClassName("center_box")[0].appendChild(item);
				
			}
			//获取所有的车型信息项
			var oItem = carInfo.getElementsByClassName("center_box")[0].children;
			//获取选中时显示的元素
			var itemActive = carInfo.getElementsByClassName("item_active");
			//遍历点击事件
			for (var i = 0; i < oItem.length; i++) {
				oItem[i].index = i ;
				oItem[i].onclick = function () {
					itemActive[this.index].style.display = "block";
					for (var i = 0; i < oItem.length; i++) {
						if( this.index != i ){
							itemActive[i].style.display = "none";
						}
					}
	//				点击后的返回值
					var returnVal = res[this.index];
				}
			}
			//动态偏移总次数
			sum = Math.ceil( oItem.length/4 ) ;
			//设置滚动宽度
			var centerBox = carInfo.getElementsByClassName("center_box")[0];
			centerBox.style.width = sum*760 + "px";
			//动态偏移总次数
			var sum ;
			//动态偏移计数
			var count  = 0 ;
			//当前偏移起始位置
			var startPosition = 0 ;
			//当前偏移结束位置
			var endPosition = 0 ;
			//标记
			var flag = true ;
			//左边按钮点击
			var oLeft = carInfo.getElementsByClassName("left")[0].children[0];
			oLeft.onclick = function () {
				count ++ ;	
				if( count >= sum){
					count = sum-1 ;
				}else{
					startPosition = 760*(count-1);
					endPosition = 760+760*(count-1);
					move(startPosition,endPosition,10,centerBox,"right")
				}
			}
			//右边按钮点击
			var oRight = carInfo.getElementsByClassName("right")[0].children[0];
			oRight.onclick = function () {
				count -- ;
				if( count < 0){
					count = 0 ;
				}else{
					startPosition = 760+760*count;
					endPosition = 760*count;
					move(startPosition,endPosition,10,centerBox,"right")
				}
			}
			//偏移滚动事件，参数：开始，结束，步长，对象，属性，回调
			function move(start,end,stepNum,obj,attr,fn){
				clearInterval(time)
				var step = (end-start)/stepNum;
				var time = setInterval(function(){
					start += step;
					if(start<=end&&step<0){
						clearInterval(time);
						start = end;
						if(fn){
							fn();
						}
					}else if(start>=end&&step>0){
						clearInterval(time);
						start = end;
						if(fn){
							fn();
						}
					}
					obj.style[attr] = start + "px";
				},5)
			}
		}, 
		fail: function (status) { 
	//		alert("error!未获取到数据")
	// 		此处放失败后执行的代码 
		} 
	});	
}
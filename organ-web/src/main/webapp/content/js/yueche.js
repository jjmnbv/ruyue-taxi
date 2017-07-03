

$(document).ready(function() {
//--------------------------------------------------------
//returnVal为点击后的返回值,车型选择
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
	//ajax数据请求,并且处理数据
	ajax({
		url: "content/js/car_info.json", 
		type: "GET", 			
		dataType: "json", 
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
					itemActive[this.index].parentNode.style.border = "1px solid red";
					itemActive[this.index].style.display = "block";
					for (var i = 0; i < oItem.length; i++) {
						if( this.index != i ){
							itemActive[i].style.display = "none";
							itemActive[i].parentNode.style.border = "1px solid #ccc";
						}
					}
	//				点击后的返回值
					var returnVal = res[this.index];
				}
			}
			//动态偏移总次数
			sum = Math.ceil( oItem.length/3 ) ;
			//设置滚动宽度
			var centerBox = carInfo.getElementsByClassName("center_box")[0];
			centerBox.style.width = sum*486 + "px";
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
					startPosition = 486*(count-1);
					endPosition = 486+486*(count-1);
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
					startPosition = 486+486*count;
					endPosition = 486*count;
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
//---------------------------------------------------------------------------
//参数说明：parent父级对象(父级为所要存放的值),child存放返回值的对象必须是input标签,
//top,left显示位置(以父级为偏移量,且设置了position：relative)
//---------------------------------------------------------------------------
	function getData (parent,backVal,url,top,left) { 
		//在ie下获取类名，car_list为父级id名字
		if(!parent.getElementsByClassName){ 
			parent.getElementsByClassName = function(className){ 
				var children = parent.getElementsByTagName('*'); 
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
	//	判断是否有控件在父级中
		if( parent.getElementsByClassName("kongjian_list")[0] ){
			parent.getElementsByClassName("kongjian_list")[0].style.display = "inline-block";
		}else{
		//	克隆控件，被克隆的控件为唯一id，
			var node = document.getElementById("kongjian_list").cloneNode(true);
		//	设置显示和位置
			node.style.display = "inline-block" ;
			node.style.top = 0 || top + "px";
			node.style.left = 0 || left + "px";
		//	清除id，克隆后的控件需要删除，确保唯一id
			node.id = "" ;
		//	添加到父级元素
			parent.appendChild(node);		
		//	tab切换
			var oSpan = parent.getElementsByClassName("kongjian_title")[0].children;
			var oCon = parent.getElementsByClassName("kongjian_con");
			for (var i = 0; i < oSpan.length; i++) {
				oSpan[i].index = i ;
				oSpan[i].onclick = function () {
					this.style.color = "#1a1a1a";
//					this.style.background = "#fff";
					oCon[this.index].style.display = "block" ;
					for (var i = 0; i < 6; i++) {
						if( this.index != i ){
							oCon[i].style.display = "none" ;
							oSpan[i].style.color = "#787878";
//							oSpan[i].style.background = "#cc9900";
						}
					}
				}
			}
			//打开品牌列表后执行
			oCon[0].style.display = "block" ;
			oSpan[0].style.color = "#1a1a1a";
//			oSpan[0].style.background = "#fff";
			
			//获取数据方法,默认为get
			function ajax(options) { 
				options = options || {}; 
				options.type = (options.type || "GET").toUpperCase(); 
				options.dataType = options.dataType || "json"; 
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
			//ajax数据请求
			ajax({
				url: url, 
				type: "GET", 			
				dataType: "json", 
				success: function (res, xml) {
					var res = JSON.parse(res);
			//		遍历对象,品牌根据首字母分类
					for( var i in res ){
			//			品牌根据首字母归为5类
						var code = i.charCodeAt(0) ;
						if(  i == "热门城市"  ){
							processData( 0 , res[i] )
						}else if( code >= 65 && code <= 69 ){
							processData( 1 , res[i] )
						}else if( code >= 70 && code <= 74 ){
							processData( 2 , res[i] )
						}else if( code >= 75 && code <= 79 ){
							processData( 3 , res[i] )
						}else if( code >= 80 && code <= 84 ){
							processData( 4 , res[i] )
						}else if( code >= 85 && code <= 90 ){
							processData( 5 , res[i] )
						}
					}
				}, 
				fail: function (status) { 
					alert("error!未获取到数据")
			// 		此处放失败后执行的代码 
				} 
			});
			//	获取到的数据处理函数
			function processData ( n , obj ) {
				for( var j in obj ){
			//		创建span标签
					var ele = document.createElement("span");
					ele.innerHTML = obj[j].brandName ;
					ele.index = j ;
			//		每个品牌点击事件,设置返回值
					ele.onclick = function () {
			//			此处设置返回的值
						backVal.value = this.innerHTML ;
						parent.getElementsByClassName("kongjian_list")[0].style.display = "none";
					}
					oCon[n].appendChild(ele); 
				}
			}
		}
	
	//	父级点击后也显示	
		parent.onclick = function () {
			parent.getElementsByClassName("kongjian_list")[0].style.display = "inline-block";
		}
//		var flag = true ;
//	//	失去焦点
//		backVal.onblur = function () {
//			if(flag){
//				parent.getElementsByClassName("kongjian_list")[0].style.display = "none";
//			}
//			return false;
//		}
//	//	鼠标悬停时不隐藏
//		parent.getElementsByClassName("kongjian_list")[0].onmouseenter = function () {
//			flag = false ;
//			//	鼠标移出隐藏
//			return false ;
//			
//		}
//	//	鼠标移出后隐藏
//		parent.getElementsByClassName("kongjian_list")[0].onmouseleave = function () {
//			if( !flag ){
//				parent.getElementsByClassName("kongjian_list")[0].style.display = "none";
//			}
//			return false ;
//		}
		parent.onmouseleave = function () {
			parent.getElementsByClassName("kongjian_list")[0].style.display = "none";
		}
	}
	//------------------------------------------------------
	//以下内容为调用方法，
	//------------------------------------------------------
	
	//地址1
	var address1 = document.getElementById("address_1");
	//存放返回值对象
	var backVal1 = address1.children[0];
	//数据地址，数据格式以data.json的为准
	var url = "../js/data.json" ;
	//鼠标移上
	address1.onmouseenter = function () {
		getData(address1,backVal1,url,40,-1);
	}	
	//地址2
	var address2 = document.getElementById("address_2");
	//存放返回值对象
	var backVal2 = address2.children[0];
	//鼠标移上
	address2.onmouseenter = function () {
		getData(address2,backVal2,url,40,-1);
	}	
});






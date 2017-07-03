//---------------------------------------------------------------------------
//参数说明：parent父级对象(父级为所要存放的值),child存放返回值的对象必须是input标签,
//top,left显示位置(以父级为偏移量,且设置了position：relative)
//---------------------------------------------------------------------------
function getData (parent,showVal, backVal,url,top,left) { 
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
		var oSpan = parent.getElementsByClassName("title")[0].children;
		var oCon = parent.getElementsByClassName("con");
		for (var i = 0; i < oSpan.length; i++) {
			oSpan[i].index = i ;
			oSpan[i].onclick = function () {
				this.style.color = "#000";
				this.style.background = "#fff";
				oCon[this.index].style.display = "block" ;
				for (var i = 0; i < 5; i++) {
					if( this.index != i ){
						oCon[i].style.display = "none" ;
						oSpan[i].style.color = "#fff";
						oSpan[i].style.background = "#f38a33";
					}
				}
			}
		}
		//打开品牌列表后执行
		oCon[0].style.display = "block" ;
		oSpan[0].style.color = "#000";
		oSpan[0].style.background = "#fff";
		
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
					if( code >= 65 && code <= 69 ){
						processData( 0 , res[i] )
					}else if( code >= 70 && code <= 74 ){
						processData( 1 , res[i] )
					}else if( code >= 75 && code <= 79 ){
						processData( 2 , res[i] )
					}else if( code >= 80 && code <= 84 ){
						processData( 3 , res[i] )
					}else if( code >= 85 && code <= 90 ){
						processData( 4 , res[i] )
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
				ele.innerHTML = obj[j].text ;
				ele.setAttribute("id", obj[j].id);
				ele.index = j ;
		//		每个品牌点击事件,设置返回值
				ele.onclick = function () {
		//			此处设置返回的值
					showVal.value = this.innerHTML ;
					backVal.value = this.getAttribute("id");
					try{
						$(showVal).change();
					}catch(e){}
					parent.getElementsByClassName("kongjian_list")[0].style.display = "none";
				}
				oCon[n].appendChild(ele); 
			}
		}
	}
	parent.getElementsByClassName("title")[0].children[0].click();
//	父级点击后也显示	
	parent.onclick = function () {
		parent.getElementsByClassName("kongjian_list")[0].style.display = "inline-block";
	}
	var flag = true ;
//	失去焦点
	showVal.onblur = function () {
		if(flag){
			parent.getElementsByClassName("kongjian_list")[0].style.display = "none";
		}
		return false;
	}
//	鼠标悬停时不隐藏
	parent.getElementsByClassName("kongjian_list")[0].onmouseenter = function () {
		flag = false ;
		//	鼠标移出隐藏
		return false ;
		
	}
//	鼠标移出后隐藏
	parent.getElementsByClassName("kongjian_list")[0].onmouseleave = function () {
		if( !flag ){
			parent.getElementsByClassName("kongjian_list")[0].style.display = "none";
		}
		return false ;
	}

}
//------------------------------------------------------
//以下内容为调用方法，
//------------------------------------------------------
/*
//父级，控件需要在哪里显示，以父级为基准
var inpBox1 = document.getElementById("inp_box1");
//存放返回值对象
var backVal1 = inpBox1.children[0];
//数据地址，数据格式以data.json的为准
var url = "data.json" ;
//获取焦点
backVal1.onfocus = function () {
	getData(inpBox1,backVal1,url,30,50);
}
//示例2
var inpBox2 = document.getElementById("inp_box2");
var backVal2 = inpBox2.children[0];
backVal2.onfocus = function () {
	getData(inpBox2,backVal2,url,30,50);
}
//示例3
var inpBox3 = document.getElementById("inp_box3");
var backVal3 = inpBox3.children[0];
backVal3.onfocus = function () {
	getData(inpBox3,backVal3,url,30,50);
}
*/
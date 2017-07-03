//----------------------------------------------------------
//说明：jsonData为弹窗点击确定后的json格式的返回值，
//----------------------------------------------------------
//在ie下获取类名，car_list为父级id名字
var parent_q = document.getElementById("content");
if (!parent_q.getElementsByClassName) {
	parent_q.getElementsByClassName = function(className) {
		var children = parent_q.getElementsByTagName('*');
		var elements = new Array();
		for (var i = 0; i < children.length; i++) {
			var child = children[i];
			var classNames = child.className.split(' ');
			for (var j = 0; j < classNames.length; j++) {
				if (classNames[j] == className) {
					elements.push(child);
					break;
				}
			}
		}
		return elements;
	};
}
// ajax数据请求方法
function ajax(options) {
	options = options || {};
	options.type = (options.type || "GET").toUpperCase();
	options.dataType = options.dataType || "json";
	// 格式化参数
	function formatParams(data) {
		var arr = [];
		for ( var name in data) {
			arr.push(encodeURIComponent(name) + "="
					+ encodeURIComponent(data[name]));
		}
		arr.push(("v=" + Math.random()).replace(".", ""));
		return arr.join("&");
	}
	var params = formatParams(options.data); // 创建 - 非IE6 - 第一步
	if (window.XMLHttpRequest) {
		var xhr = new XMLHttpRequest();
	} else {
		// IE6及其以下版本浏览器
		var xhr = new ActiveXObject('Microsoft.XMLHTTP');
	}
	// 接收 - 第三步
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4) {
			var status = xhr.status;
			if (status >= 200 && status < 300) {
				options.success
						&& options.success(xhr.responseText, xhr.responseXML);
			} else {
				options.fail && options.fail(status);
			}
		}
	}
	// 连接 和 发送 - 第二步
	if (options.type == "GET") {
		xhr.open("GET", options.url + "?" + params, true);
		xhr.send(null);
	} else if (options.type == "POST") {
		xhr.open("POST", options.url, true);
		// 设置表单提交时的内容类型
		xhr.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		xhr.send(params);
	}
}
// 标记
var flag = true;
var options = {
	url : "LeVehiclemodels/GetPubVehcline", // 请求地址
	type : "GET", // 请求方式
	dataType : "json",
	success : function(res, xml) {
		initData();
		flag = false;
		var res = JSON.parse(res);
		// 遍历对象,品牌根据首字母分类
		for ( var i in res) {
			// 品牌根据首字母归为5类，显示在左边的数据
			var code = i.charCodeAt(0);
			if (code >= 65 && code <= 69) {
				processData(0, res[i])
			} else if (code >= 70 && code <= 74) {
				processData(1, res[i])
			} else if (code >= 75 && code <= 79) {
				processData(2, res[i])
			} else if (code >= 80 && code <= 84) {
				processData(3, res[i])
			} else if (code >= 85 && code <= 90) {
				processData(4, res[i])
			}
			// 显示在右边的数据
			var data = res[i];
			rightProcessData(data);

			// 全部按钮点击
//			parent_q.getElementsByClassName("all_brand")[0].onclick = function() {
//				parent_q.getElementsByClassName("right_items")[0].innerHTML = "";
//				for ( var i in res) {
//					rightProcessData(res[i])
//				}
//			}
		}
	},
	fail : function(status) {
		alert("error")
		// 此处放失败后执行的代码
	}
}
// 左边数据显示处理函数
function processData(n, obj) {
	var data = obj;
	var count = {};
	var num = 0;
	for (var i = 0; i < data.length; i++) {
//		if (count[data[i].brandName]) {
//			count[data[i].brandName]++;
//		} else {
//			count[data[i].brandName] = 1;
//		}
		if (data[i].type != null && data[i].type != '') {
			count[data[i].brandName] = num++;
		}else{
			count[data[i].brandName] = 0;
		}
	}
	count[data[0].brandName] = num;
	for ( var i in count) {
		// 创建span标签
		var ele = document.createElement("span");
		ele.className = "item";
		if (count[i] > 0) {
			ele.innerHTML = "<span>" + i + "</span>" + "<span>(" + count[i]
					+ ")</span>";
		} else {
			ele.innerHTML = "<span>" + i + "</span>" + "<span>(0)</span>";
		}
		// 每个品牌点击事件,设置返回值
		ele.onclick = function() {
			$(this).parent().parent().find('.items>.item').css('color', '#3d3d3d');
			$(this).css('color', 'red');
			// 此处设置返回的值
//			parent_q.getElementsByClassName("right_items")[0].innerHTML = "";
//			var temp = new Array;
//			for ( var i in data) {
//				if (data[i].brandName == this.children[0].innerHTML) {
//					temp.push(data[i]);
//				}
//			}
//			rightProcessData(temp)
			var items = parent_q.getElementsByClassName("right_items")[0].childNodes;
			if(items&&items.length>0){
				var scrollheight = 40;
				var srctext = this.children[0].innerHTML;
				for(var i=0;i<items.length;i++){
					var item = items[i];
					if(item.className!="item_head"){
						scrollheight += 30;
						continue;
					}
					if(item.getElementsByTagName("span").length>1){
						var tagtext = item.getElementsByTagName("span")[1].innerText;
						if(srctext==tagtext){
							break;
						}else{
							scrollheight += 30;
						}
					}
				}
			}
			document.getElementById("right_content").scrollTop=scrollheight;
		}
		var oItem = parent_q.getElementsByClassName("items");
//		oItem[n + 1].appendChild(ele);
		//不需要全部不需要加1
		oItem[n].appendChild(ele);
	}
}

// 右边数据显示处理函数
function rightProcessData(obj) {
	var data = obj;
	var count = {};
	for (var i = 0; i < data.length; i++) {
		if (count[data[i].brandName]) {
			count[data[i].brandName]++;
		} else {
			count[data[i].brandName] = 1;
		}
	}
	for ( var i in count) {
		var head = parent_q.getElementsByClassName("right_list")[0].children[1];
		var node = head.cloneNode(true);
		node.children[3].innerHTML = i;
		node.style.display = "block";
		parent_q.getElementsByClassName("right_items")[0].appendChild(node);

		// 子节点
		for (var k = 0; k < data.length; k++) {
			if (data[k].brandName == i) {
				var item = parent_q.getElementsByClassName("right_list")[0].children[2];
				var childNode = item.cloneNode(true);
				childNode.children[0].children[2].innerHTML = i + " -";
				childNode.children[0].children[3].innerHTML = data[k].vechileName;
				childNode.children[0].children[0].value = data[k].vechileId;
				if (data[k].type) {
					childNode.children[1].innerHTML = data[k].type;
//					if(data[k].count == '0'){
//						childNode.children[1].innerHTML = data[k].type;
//					}else{
//						childNode.children[1].innerHTML = data[k].type;
//// 						childNode.children[1].innerHTML = data[k].type+" 该车系已绑定了司机";
//					}
					if(data[k].type==leVehiclemodelsName){
						childNode.children[0].children[0].checked="checked";
//						if(data[k].count == '0'){
//							childNode.children[0].className = "";
//							childNode.children[0].children[0].removeAttribute("disabled");
//						}
						childNode.children[0].className = "";
						childNode.children[0].children[0].removeAttribute("disabled");
					}
				} else {
					childNode.children[0].children[0].removeAttribute("disabled");
					childNode.children[0].className = "";
				}
				childNode.style.display = "block";
				parent_q.getElementsByClassName("right_items")[0]
						.appendChild(childNode);
			}
		}
	}
}
// 全选功能
function checkAll() {
	$("input[name='fanxuan']").removeAttr("checked");
	var checklist = document.getElementsByName("checkbox");
	if (document.getElementsByName("quanxuan")[0].checked) {
		for (var i = 0; i < checklist.length; i++) {
			if (!checklist[i].disabled){
				checklist[i].checked = 1;
			}
		}
	} else {
		for (var j = 0; j < checklist.length; j++) {
			if (!checklist[j].disabled){
				checklist[j].checked = 0;
			}
		}
	}
}
// 反选功能
function unCheckAll() {
	$("input[name='quanxuan']").removeAttr("checked");
	// var checklist =$("input[name='car_checkbox']");
	var checklist = $("input[name='checkbox']");
	if (document.getElementsByName("fanxuan")[0].checked) {
		for (var f = 0; f < checklist.length; f++) {
			if (!checklist.eq(f).prop("disabled")) {
				if (checklist.eq(f).is(":checked")) {
					checklist.eq(f).removeAttr("checked");
				} else {
					checklist.eq(f).prop("checked", 'true');
				}

			}
		}
	} else {
		for (var j = 0; j < checklist.length; j++) {
			if (!checklist.eq(j).prop("disabled")) {
				if (checklist.eq(j).is(":checked")) {
					checklist.eq(j).removeAttr("checked");
				} else {
					checklist.eq(j).prop("checked", 'true');
				}

			}
		}
	}
}
// 打开车型选择，初始化数据
function initData () {
	parent_q.getElementsByClassName("right_items")[0].innerHTML = "" ;
	for (var i = 0; i < parent_q.getElementsByClassName("items").length; i++) {
		parent_q.getElementsByClassName("items")[i].innerHTML = "" ;
	}
	document.getElementsByName("quanxuan")[0].checked = 0 ;
	document.getElementsByName("fanxuan")[0].checked = 0 ;
	for(var i=0;i<document.getElementsByName("car_checkbox").length;i++)
   	{
   		if( !document.getElementsByName("car_checkbox")[i].hasAttribute("disabled") ){
   			document.getElementsByName("car_checkbox")[i].checked = 0;
   		}
   	}
}

// 确定按钮点击，返回值，关闭弹窗
parent_q.getElementsByClassName("sure")[0].onclick = function() {
	// 检出所有选中的checkbox
	var checklist = parent_q.getElementsByClassName("right_item");
	var jsonData = {};
	jsonData["vechileId"] = [];
	for (var i = 0; i < checklist.length; i++) {
		if (checklist[i].getElementsByTagName("input")[0].checked) {
			//jsonData["brandName"] = (checklist[i].children[0].children[2].innerHTML)
			//		.replace(" -", "");
			//jsonData["vechileName"] = checklist[i].children[0].children[3].innerHTML;
			//jsonData["vechileId"] = checklist[i].children[0].children[0].value;
			jsonData["vechileId"].push(checklist[i].children[0].children[0].value);
			//console.log(jsonData);
		}
	}
	saveLeVehiclemodels(jsonData,leVehiclemodelsId);
	document.getElementById("content").style.display = "none";
	document.getElementById("allocationLinePop").style.display = "none";
}
// 取消按钮点击，关闭弹窗
parent_q.getElementsByClassName("cancel")[0].onclick = function() {
	document.getElementById("content").style.display = "none";
	document.getElementById("allocationLinePop").style.display = "none";
}
var leVehiclemodelsName ="";
var leVehiclemodelsId ="";
// 分配车系按钮点击
function allocationLineShow(ids,names) {
	// document.getElementById("click").onclick = function () {
	// 判断是否有数据
	
	//if(flag){
	ajax(options);
	//}
	leVehiclemodelsName = names;
	leVehiclemodelsId = ids;
	$("#all_brand").click();
	document.getElementById("content").style.display = "block";
	document.getElementById("allocationLinePop").style.display = "block";
}



$(document).ready(function() {
	// 折叠
	$('#right_content .right_items').on('click', '.item_head>.minus', function(){
		if( $(this).text() == '-' ){
			$(this).text('+');
			var brandName = $(this).parent().find('span:last').text();
			var $parents = $(this).parent().parent().find('.right_item');
			$parents.find('span:contains('+brandName+')').parent().parent().hide();
		}else{
			$(this).text('-');
			var brandName = $(this).parent().find('span:last').text();
			var $parents = $(this).parent().parent().find('.right_item');
			$parents.find('span:contains('+brandName+')').parent().parent().show();
		}
	});
	// 叉叉关闭
	$('#content .close').on('click', function(){
		$('#content ,#allocationLinePop').hide();
	});
	
	$(".right_list").on('click', '.item_head input', function() {
		var parent_q = $(this).parent().nextUntil(".item_head");
		var tinput = parent_q.find("input");
		var tl = tinput.length;
		var disabled = tinput.prop("disabled");
		//如果本身选中状态
		if ($(this).is(":checked")) {
			for (i = 0; i < tl; i++) {
				if (!tinput.eq(i).prop("disabled")) {
					tinput.eq(i).prop("checked", 'true');
				}
			}
		} else {
			for (i = 0; i < tl; i++) {
				if (!tinput.eq(i).prop("disabled")) {
					tinput.eq(i).removeAttr("checked");
				}
			}
		}
	});
	
})



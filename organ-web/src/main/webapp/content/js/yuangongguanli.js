$(document).ready(function(){
	
var firstFlag = true ;
var setting = {
	view: {
		selectedMulti: false
	},
	check: {
		enable: true
	},
	data: {
		key: {
			title:"t"
		},
		simpleData: { 
			enable: true
		}
	},
	callback: {
		onRightClick: OnRightClick,
//		节点创建以后的回调
		onNodeCreated: function ( event, treeId, treeNode ) {
			var $p = $(".tree_box #"+treeNode.parentTId+"") ;
//			一级菜单不需要处理checked
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			if( treeNode.num != undefined && $p.find("li .center_close,.bottom_close").length != 0 ){
				$p.find("ul li").eq( treeObj.getNodeIndex(treeNode) ).append("<i>("+treeNode.num+")</i>")
			}
			if( treeNode.parentTId == "treeDemo_1" ){
//				一级不操作
			}else{
				if( $p.find(".center_docu,.bottom_docu").length != 0 ){
					if( !treeNode.isParent ){
						$p.find("li").eq( treeObj.getNodeIndex(treeNode) ).prepend("<label><input type='checkbox' class='checkbox_ie'/><span class='span_ie'></span></label>")
						$p.find(".center_docu,.bottom_docu").css("display","none");
						$p.find("li").eq( treeObj.getNodeIndex(treeNode) ).css({"position":"relative","left":"-16px"})
						$p.find("li").eq( treeObj.getNodeIndex(treeNode) ).find(".span_ie").click(function () {
							if( !$(this).prev().is(":checked") ){
								$(this).css("background-image","url(../img/btn_yggldxk_pre.png)")
							}else{
								$(this).css("background-image","url(../img/btn_yggldxk.png)")
							}
						})
					}
					if( treeNode.isPrent ){
//						$p.find(".center_docu,.bottom_docu").css("display","inherits");
					}
				}
				//	树形菜单复选框点击事件
//				$(".tree_box input").flag = true ;
				var flag = true ;
				$(".tree_box input").click(function () {
//					console.log($(this).index)
//					if( flag ){
//						$(this).css("background-image","url(../img/btn_yggldxk_pre.png)");
//						flag = false ;
//					}else{
//						$(this).css("background-image","url(../img/btn_yggldxk.png)");
//						flag = true ;
//					}
				})
			}
					
		},
//		展开后的回调
		onExpand: function ( event, treeId, treeNode ) {

		},
//		节点点击后的回调
		onClick:function(event, treeId, treeNode){
			var reg2 = /^\d{2}$/;
			var reg3 = /^\d{3}$/;
			var reg4 = /^\d{4}/;
			if( treeNode.id == 1 ){
//				根节点点击事件
				$(".con_explain").css("display","block").siblings().css("display","none");
			}else if( treeNode.pId == 1 ){
//				一级节点点击事件
				if( treeNode.id == 11 ){
					bumen( treeNode , zNodes )	;
					$(".con_list_boss").css("display","block").siblings().css("display","none");
				}else{
					zhongxin ( treeNode , zNodes )
					$(".con_list_product").css("display","block").siblings().css("display","none");
				}
			}else if( reg2.test( treeNode.pId ) && reg3.test( treeNode.id ) && treeNode.pId != 11) {
				bumen ( treeNode , zNodes )	;
				$(".con_list_boss").css("display","block").siblings().css("display","none");
			}else if( treeNode.pId == 11 || reg4.test( treeNode.id ) ){
				$(".con_list_info").css("display","block").siblings().css("display","none");
				yuangong( treeNode );
			}
		}
	}
};
//	所有数据
	var zNodes =[
	{ id:1, pId:0, name:"武汉智驾科技股份有限公司", t:"", open:true},
	{ id:11, pId:1, name:"公司领导", t:"",num: 3},
	{ id:111, pId:11, name:"adfdf", t:""},
	{ id:113, pId:11, name:"sdfsdfsdfsdfdass", t:""},
	{ id:114, pId:11, name:"sdfsdf", t:""},
	{ id:12, pId:1, name:"产品中心", t:"",num:5},
	{ id:121, pId:12, name:"产品部", t:"",num:1},
	{ id:1211, pId:121, name:"kom", t:"", 
		tel:"15987896532",
		sex:"男",
		type:"普通",
		role:"普通员工",
		img:"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1889789971,2360758735&fm=58",
		bumen:"产品部",
		guize:"武汉加班",
	},
	{ id:122, pId:12, name:"市场部", t:"",num:2},
	{ id:1221, pId:122, name:"kom", t:""},
	{ id:1222, pId:122, name:"jim", t:""},
	{ id:123, pId:12, name:"项目部", t:"",num:2},
	{ id:1231, pId:123, name:"kom", t:""},
	{ id:1232, pId:123, name:"jim", t:""},
	{ id:13, pId:1, name:"研发中心", t:"", },
	{ id:14, pId:1, name:"财务中心", t:"", },
	{ id:15, pId:1, name:"资源中心", t:"", },
	{ id:16, pId:1, name:"运营中心", t:"", },
	{ id:17, pId:1, name:"销售中心", t:"", num:4},
	{ id:171, pId:17, name:"销售1部", t:"", num:3},
	{ id:1711, pId:171, name:"张三", t:"", },
	{ id:1712, pId:171, name:"李四", t:"", },
	{ id:1713, pId:171, name:"老五", t:"", },
	{ id:172, pId:17, name:"销售2部", t:"", num:1},
	{ id:1721, pId:172, name:"汤姆", t:""},
//	{ id:1731, pId:173, name:"刘氏", t:""},
];
//	初始化主界面树
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
//	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	
	
//	员工基本信息显示处理函数
	function yuangong ( treeNode ) {
		$parent = $(".con_list_info table");
		$parent.eq(0).find(".td_1").eq(0).text(treeNode.name);
		$parent.eq(0).find(".td_1").eq(1).text(treeNode.sex);
		$parent.eq(0).find(".td_1").eq(2).text(treeNode.type);
		$parent.eq(0).find(".td_1").eq(3).find("img").attr("src",treeNode.img);
		
		$parent.eq(1).find(".td_1").eq(0).text(treeNode.tel);
		$parent.eq(1).find(".td_1").eq(1).text(treeNode.bumen);
		$parent.eq(1).find(".td_1").eq(2).text(treeNode.role);
		
		$(".con_list_info .yongche span").eq(1).text(treeNode.guize)
	}
//	具体部门信息数据处理函数
	function bumen ( treeNode , obj ) {
		$(".con_list_boss table tr").eq(0).siblings().remove();
		for( var i in obj ){
			if( treeNode.id == obj[i].pId ){
				$(".con_list_boss .con_list_title").text(treeNode.name)
				$(".con_list_boss table").append("<tr><td>"+obj[i].name+"</td><td>"+obj[i].type+"</td><td>"+obj[i].role+"</td><td>"+obj[i].guize+"</td></tr>")
			}
		}
	}
//	各部门信息处理函数
	function zhongxin ( treeNode , obj ) {
		$(".con_list_product table tr").eq(0).siblings().remove();
		for( var i in obj ){
			if( treeNode.id == obj[i].pId ){
				$(".con_list_product .con_list_title").text(treeNode.name)
				$(".con_list_product table").append("<tr><td>"+obj[i].name+"</td><td>"+obj[i].num+"</td><td>"+obj[i].role+"</td></tr>")
			}
		}
	}
//	弹窗，用车规则，单选框点击事件
	function guize ( obj ) {
		obj.find(".td_yongche span").eq(0).click(function () {
			$(this).css("background-image","url(../img/btn_danxuan_pre.png)").siblings().css("background-image","url(../img/btn_danxuan.png)")
			$(".tr_active").removeClass("tr_active_c");
		})
		obj.find(".td_yongche span").eq(1).click(function () {
			$(this).css("background-image","url(../img/btn_danxuan_pre.png)").siblings().css("background-image","url(../img/btn_danxuan.png)")
			$(".tr_active").addClass("tr_active_c");
		})
	}
	guize( $(".batch_import") );
	guize( $(".add_staff") );
//	弹窗用车规则点击事件
	$(".tr_active").find("span").click(function () {
		if( $(this).hasClass("span_active") ){
			$(this).removeClass("span_active");
		}else if( $(".tr_active .span_active").length < 5 ){
			$(this).addClass("span_active");
		}
	})		
//	存放右键带入的数据，用于弹窗中
	var scope = {};
//	右键菜单处理	
	function OnRightClick(event, treeId, treeNode) {
		scope = treeNode;
		if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
			zTree.cancelSelectedNode();
			showRMenu("root", event.clientX, event.clientY,treeNode);
		} else if (treeNode && !treeNode.noR) {
			zTree.selectNode(treeNode);
			showRMenu("node", event.clientX, event.clientY,treeNode);
		}
	}
//	右键菜单显示判断，级别不同显示就不同
	function showRMenu(type, x, y,treeNode) {
		$("#rMenu ul").show();
			if (type=="root") {
				$("#m_del").hide();
				$("#m_check").hide();
				$("#m_unCheck").hide();
			} else {
				$("#m_del").show();
				$("#m_check").show();
				$("#m_unCheck").show();
			}
//		根目录右键，机构名字
		var reg2 = /^\d{2}$/;
		var reg3 = /^\d{3}$/;
		var reg4 = /^\d{4}/;
		if( treeNode.id == 1 ){
//			根节点点击后显示菜单
			$("#rMenu ul li").eq(0).show().siblings().hide();
		}else if( treeNode.pId == 1 ){
//			一级节点点击后显示菜单
			$("#rMenu ul li").eq(0).show();
			$("#rMenu ul li").eq(1).show();
			$("#rMenu ul li").eq(2).show();
			$("#rMenu ul li").eq(3).show();
			$("#rMenu ul li").eq(4).hide();
			$("#rMenu ul li").eq(5).hide();
			$("#rMenu ul li").eq(6).hide();
		}else if( reg2.test( treeNode.pId ) && reg3.test( treeNode.id ) && treeNode.pId != 11) {
//			二级节点点击后显示菜单
			$("#rMenu ul li").eq(0).hide();
			$("#rMenu ul li").eq(1).show();
			$("#rMenu ul li").eq(2).show();
			$("#rMenu ul li").eq(3).show();
			$("#rMenu ul li").eq(4).hide();
			$("#rMenu ul li").eq(5).hide();
			$("#rMenu ul li").eq(6).hide();			
		}else if( treeNode.pId == 11 || reg4.test( treeNode.id ) ){
//			三级节点点击后显示菜单
			$("#rMenu ul li").eq(0).hide();
			$("#rMenu ul li").eq(1).hide();
			$("#rMenu ul li").eq(2).hide();
			$("#rMenu ul li").eq(3).show();
			$("#rMenu ul li").eq(4).show();
			$("#rMenu ul li").eq(5).hide();
			$("#rMenu ul li").eq(6).hide();				
		}
		rMenu.css({"top":y+20+"px", "left":x+10+"px", "visibility":"visible"});
		$("body").bind("mousedown", onBodyMouseDown);
	}
	function hideRMenu() {
		if (rMenu) rMenu.css({"visibility": "hidden"});
		$("body").unbind("mousedown", onBodyMouseDown);
	}
	function onBodyMouseDown(event){
		if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
			rMenu.css({"visibility" : "hidden"});
		}
	}
	var addCount = 1;
	function addParent() {
		hideRMenu();
		var newNode = { name:"增加" + (addCount++)};
		if (zTree.getSelectedNodes()[0]) {
			newNode.checked = zTree.getSelectedNodes()[0].checked;
			zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
		} else {
			zTree.addNodes(null, newNode);
		}
	}
	function removeTreeNode() {
		hideRMenu();
		var nodes = zTree.getSelectedNodes();
		if (nodes && nodes.length>0) {
			if (nodes[0].children && nodes[0].children.length > 0) {
				var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
				if (confirm(msg)==true){
					zTree.removeNode(nodes[0]);
				}
			} else {
				zTree.removeNode(nodes[0]);
			}
		}
	}
	function checkTreeNode(checked) {
		var nodes = zTree.getSelectedNodes();
		if (nodes && nodes.length>0) {
			zTree.checkNode(nodes[0], checked, true);
		}
		hideRMenu();
	}
	function resetTree() {
		hideRMenu();
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	}
	var zTree, rMenu;
	$(document).ready(function(){
//		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		zTree = $.fn.zTree.getZTreeObj("treeDemo");
		rMenu = $("#rMenu");
	});
	
//	弹窗取消按钮点击事件
	$(".popup .cancel").click(function () {
		$(".popup_box").hide();
	})
	
//	新增部门弹窗树形菜单初始化

//	新增下级部门点击事件
	$("#m_addPatent").click(function(){
		popshow();
		$("#rMenu ul").hide();
		$(".popup_box").show();
		$(".popup_box .add_department").show().siblings().hide();
		$(".popup_box .add_department .popup_content").find("input").eq(0).val(scope.name)
//		弹窗中确定按钮的点击事件，把当前页面的数据存入zNodes中，刷新树
		$(".popup_box .add_department").find(".popup_footer .sure").one("click" , function () {
			var temp = 0 ;
			for(var i in zNodes ){
				var obj = zNodes[i] ;
				if( obj.pId == scope.id ){
					if( temp < obj.id ){
						temp = obj.id ;
					}
				}else if( obj.pId != scope.id ){
					temp = scope.id * 10 ;
				}
			}
			var newNode = {} ;
			newNode["id"] = temp + 1 ;
			newNode["pId"] = scope.id ;
			newNode["t"] = "" ;
			newNode["name"] = $(".popup_box .add_department .popup_content").find("input").eq(1).val() ;
			zNodes.push(newNode);
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
//			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
//			treeObj.updateNode(newNode);
//			treeObj.refresh();
			$(".popup_box").hide();
			
		})
//		placeholder属性的兼容性问题处理
		if( navigator.appName == "Microsoft Internet Explorer" ){
			placeholder( $(".inp_1").eq(0) , "低于10个文字" )
		}		
	});	
//	新增员工点击事件
	$("#m_addChild").click(function(){
		$("#rMenu ul").hide();
		$(".popup_box").show();
		$(".popup_box .add_staff").show().siblings().hide();
		$(".popup_box .add_staff .popup_content .add_staff_table1").find("input").eq(0).val(scope.name)
//		弹窗中确定按钮的点击事件，把当前页面的数据存入zNodes中，刷新树
		$(".popup_box .add_staff").find(".popup_footer .sure").one("click",function(){
			var temp = 0 ;
			for(var i in zNodes ){
				var obj = zNodes[i] ;
				if( obj.pId == scope.id ){
					if( temp < obj.id ){
						temp = obj.id ;
					}
				}else if( obj.pId != scope.id ){
					temp = scope.id * 10 ;
				}
				if( zNodes[i].id == scope.id ){
					zNodes[i].num = zNodes[i].num + 1 || 1 ;
				}
				if( zNodes[i].id == scope.pId ){
					zNodes[i].num = zNodes[i].num + 1 || 1 ;
				}
			}
			var newNode = {} ;
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");			
			newNode["id"] = temp + 1 ;
			newNode["pId"] = scope.id ;
			newNode["t"] = "" ;
			newNode["name"] = $(".popup_box .add_staff .popup_content").find(".inp_1 input").eq(0).val() ;
			newNode["tel"] = $(".popup_box .add_staff .popup_content").find(".inp_1 input").eq(1).val() ;
			newNode["sex"] = $(".popup_box .add_staff .popup_content").find(".select_val").eq(0).val() ;
			newNode["type"] = $(".popup_box .add_staff .popup_content").find(".select_val").eq(1).val() ;
			newNode["role"] = $(".popup_box .add_staff .popup_content").find(".inp_1 input").eq(2).val() ;
			newNode["img"] = $(".popup_box .add_staff .popup_content").find(".td_img img").eq(0).attr("src") ;
			zNodes.push(newNode);
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
//			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
//			treeObj.refresh();
			$(".popup_box").hide();
		})
//		上传头像点击事件
		$(".popup_box .add_staff .popup_content").find(".td_img").eq(1).click(function () {
			
		})
//		判断分类是否为特殊

//		placeholder属性的兼容性问题处理
		if( navigator.appName == "Microsoft Internet Explorer" ){
			placeholder( $(".inp_1").eq(1) , "必填项" )
			placeholder( $(".inp_1").eq(2) , "必填项" )
			placeholder( $(".inp_1").eq(3) , "必填项" )
		}
	});
//	编辑点击事件
	$("#m_editNode").click(function(){
		popshow();
		$("#rMenu ul").hide();
		$(".popup_box").show();
		$(".popup_box .change_department").show().siblings().hide();
	});
//	删除点击事件
	$("#m_del").click(function(){
		popshow();
		$("#rMenu ul").hide();
		$(".popup_box").show();
		$(".popup_box .hint_win").show().siblings().hide();
	});
//	重置密码点击事件
	$("#m_pass").click(function(){
		
	});
//	批量更改部门点击事件
	$("#m_bumen").click(function(){
		popshow();
		$("#rMenu ul").hide();
		$(".popup_box").show();
		$(".popup_box .batch_change").show().siblings().hide();
	});
//	批量删除点击事件
	$("#m_allDel").click(function(){
		
	});
//	批量导入点击事件
	$(".con_header .head_middle").click(function(){
		popshow();
		$("#rMenu ul").hide();
		$(".popup_box").show();
		$(".popup_box .batch_import").show().siblings().hide();
	});
	

});




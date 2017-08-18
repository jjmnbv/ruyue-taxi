var dataGrid;
/**
 * 页面初始化
 */
$(function () {
	initGrid();
	initData();
	});
/**
 * 初始化表格数据
 */
function initData() {
		showCitySelect1(
			".input_box2", 
			"PubInfoApi/GetCitySelect1", 
			$("#citymarkid").val(), 
			function(backVal, $obj) {
				$('#cityname').val($obj.text());
				$("#city").val($obj.data('id'));
				$('#cityname').valid();
			}
		);
}
function initGrid() {
	var gridObj = {
		id: "dataGrid",
		iLeftColumn: 1,
		language: {sEmptyTable: "暂无取消规则信息"},
		scrollX: true,
        sAjaxSource: "PubOrderCancelRule/GetPubOrderCancelRule",
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "ifhistory", sTitle: "ifhistory", sClass: "center", visible: false},
	        //自定义操作列
	        {
	        "mDataProp": "ZDY",
            "sClass": "center",
            "sTitle": "操作",
            "sWidth": 260,
            "bSearchable": false,
            "sortable": false,
            "mRender": function (data, type, full) {
                var html = "";
            	   if(full.rulestatus == "禁用"){
                       html += '<button type="button" class="SSbtn red_q"  onclick="disforbid(' +"'"+ full.id +"'"+","+"'"+full.rulename+"'"+')"><i class="fa fa-paste"></i>启用</button>';
                     }else{
                       html += '<button type="button" class="SSbtn grey_q"  onclick="forbid(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>禁用</button>';
                     }
                  html += '&nbsp; <button type="button" class="SSbtn green_q"  onclick="modify(' +"'"+ full.id +"'"+","+"'"+ full.citycode +"'"+","+"'"+ full.cancelcount +"'"+","+"'"+ full.latecount +"'"+","+"'"+ full.watingcount +"'"+","+"'"+ full.price +"'"+","+"'"+ full.cartype +"'"+ ')"><i class="fa fa-paste"></i>修改</button>';
                  if(full.ifhistory != 0){
                      html += '&nbsp; <button type="button" class="SSbtn grey_q"  onclick="history(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>历史记录</button>';
                    }
                  return html;
              }
          },
	        {mDataProp: "citycode", sTitle: "所属城市", sClass: "center", sortable: true },
	        {mDataProp: "cartype", sTitle: "服务业务", sClass: "center", sortable: true },
	        {mDataProp: "cancelcount", sTitle: "免责取消时限(分钟)", sClass: "center", sortable: true },
	        {mDataProp: "latecount", sTitle: "迟到免责时限(分钟)", sClass: "center", sortable: true },
	        {mDataProp: "watingcount", sTitle: "免责等待时限(分钟)", sClass: "center", sortable: true },
	        {mDataProp: "price", sTitle: "取消费用(元)", sClass: "center", sortable: true },
	        {mDataProp: "rulestatus", sTitle: "状态", sClass: "center", sortable: true },
	        {mDataProp: "createtime", sTitle: "创建时间", sClass: "center", sortable: true }
	        
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}
/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "citycode", "value": $("#citycode").val() },
		{ "name": "cartype", "value": $("#cartype").val() },
		
	];
	dataGrid.fnSearch(conditionArr);
}
/**
 * 重设
 */
function emptys(){
	$("#cartype").val("");
	$("#citycode").val("");
	search();
}
function forbid(id){
	var data = {
		id : id
	};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : "PubOrderCancelRule/ruleConflictOk;",
		data : JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status == 0) {
				toastr.options.onHidden = function() {
					window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") +"PubOrderCancelRule/Index";
	           	}
				toastr.success('禁用成功', "提示");
			}
		}
	});
}
//启用
function disforbid(id){
	var data = {
			id : id
		};
		$.ajax({
			type : 'POST',
			dataType : 'json',
			url : "PubOrderCancelRule/ruleConflict;",
			data : JSON.stringify(data),
			contentType : 'application/json; charset=utf-8',
			async : false,
			success : function(status) {
				if (status == 0) {
					toastr.options.onHidden = function() {
						window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") +"PubOrderCancelRule/Index";
		           	}
					toastr.success('启用成功', "提示");
				}
			}
		});
}
//新增
function addOn(){
	//判断是否为修改的添加如果不是初始化所有值
	   $("#editFormDiv").show();
	    validateForm();
		showObjectOnForm("editForm", null);
		var editForm = $("#editForm").validate();
		editForm.resetForm();
		editForm.reset();
	    
//	    $("input:radio:not([checked])").removeAttr("disabled","disabled");
	    $('#taxi').attr("disabled",false);
	    $('#net').attr("disabled",false);
		$("#popTitle")[0].innerText = "新增取消规则";
		$("#taxi").prop("checked","checked");
		$("#net").removeAttr("checked");
		$("#cityname").removeAttr("disabled");
		$("#cancelcount").val("");
		$("#cityname").val("");
		$("#latecount").val("");
		$("#watingcount").val("");
		$("#price").val("");
		$("#id").val("");
}
//取消
function canel(){
	$("#editFormDiv").hide();
}
//转跳历史记录
function history(id){
	window.location.href=base+"PubOrderCancelRule/HistoryIndex?id="+id;
}
//修改
function modify(id,citycode,cancelcount,latecount,watingcount,price,cartype){
	$("#popTitle")[0].innerText = "修改取消规则";
	$("#cityname").attr("disabled","disabled");
  if(cartype == "网约车"){
	  $("#net").prop("checked","checked"); 
	  $("#taxi").removeAttr("checked");
  }	else{
	  $("#taxi").prop("checked","checked"); 
	  $("#net").removeAttr("checked");
  }
	$("input:radio:not([checked])").attr("disabled","disabled");
	$("#editFormDiv").show();
	$("#cancelcount").val(cancelcount);
	$("#cityname").val(citycode);
	$("#latecount").val(latecount);
	$("#watingcount").val(watingcount);
	$("#price").val(price);
	$("#id").val(id);
	//验证页面数据
	validateForm();
	var form = $("#editForm");
	var a = !form.valid();
	if(!form.valid()) return;
	var editForm = $("#editForm").validate();
}
function validateForm() {
	$("#editForm").validate({
		ignore:'',
		rules: {
			cancelcount: {required: true},
			cityname: {required: true},
			latecount: {required: true},
			watingcount: {required: true},
			price: {required: true}
		},
		messages: {
			cancelcount: {required:  "请输入免责取消时限"},
			cityname: {required: "请选择城市"},
			latecount: {required: "请输入迟到免责时限"},
			watingcount: {required: "请输入免责等待时限"},
			price: {required: "请输入取消费用"}
		}
	})
}
//新增规则
function add(){
	//验证页面数据
	var form = $("#editForm");
	var a = !form.valid();
	if(!form.valid()) return;
	var editForm = $("#editForm").validate();
	//获取数据
	var id = $("#id").val();
	var cancelcount = $("#cancelcount").val();
	var cityname = $("#cityname").val();
	var latecount = $("#latecount").val();
	var watingcount = $("#watingcount").val();
	var price = $("#price").val();
	var cartype = $('input:radio[name="cartype"]:checked').val();
	var data = {
			id : id,
			cartype:cartype,
			cancelcount : cancelcount,
			citycode : cityname,
			latecount : latecount,
			watingcount : watingcount,
			price : price
	}
	$.ajax({
		type: "POST",
		url:"PubOrderCancelRule/PubOrderCancelRuleAdd",
		cache: false,
		dataType : 'json',
		contentType : 'application/json; charset=utf-8',
		data:JSON.stringify(data),
		success: function (response) {
			if(response.haveSame == "noOk"){
				toastr.options.onHidden = function() {
//					window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") +"PubOrderCancelRule/Index";
	           	}
				var car;
				if(cartype == "0"){
					car = "网约车"
				}else{
					car = "出租车"
				}
				toastr.error("【"+cityname+"】"+"已有"+"【"+car+"】"+"的取消规则", "提示");
				return; 
			}
			 toastr.options.onHidden = function() {
				 window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") +"PubOrderCancelRule/Index";
           	}
			 if(id == null || id == ""){
				  toastr.success("保存成功", "提示");
			  }else{
				  toastr.success("修改成功", "提示");  
			  }
		},
		error: function (xhr, status, error) {
			return;
		}
 });
}




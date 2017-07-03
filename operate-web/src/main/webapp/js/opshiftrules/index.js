var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	validateForm();
	initEvent();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
				id: "dataGrid",
        sAjaxSource: "OpShiftRules/GetShiftRulesByQuery",
        iLeftColumn: 1,
        language: {
        	sEmptyTable: "暂无交接班规则信息"
        },
        scrollX: true,
        columns: [
					{
					  //自定义操作列
					  "mDataProp": "ZDY",
					  "sClass": "center",
					  "sTitle": "操作",
					  "sWidth": 100,
					  "bSearchable": false,
					  "sortable": false,
					  "mRender": function (data, type, full) {
					      var html = "";
					      html += '<button type="button" class="SSbtn green_a"  onclick="edit(' +"'"+ full.id+"'"+')"><i class="fa fa-times"></i>修改</button>';
					      return html;
					  }
					},
	        {mDataProp: "id", sTitle: "id", sClass: "center", visible: false},
	        {mDataProp: "cityname", sTitle: "城市名称", sClass: "center", sortable: false},
	        {mDataProp: "autoshifttime", sTitle: "自主交班时限（分钟）", sClass: "center", sortable: false },
	        {mDataProp: "manualshifttime", sTitle: "人工指派时限（分钟）", sClass: "center", sortable: false },
	        {
			  "mDataProp": "ZDY",
			  "sClass": "center",
			  "sTitle": "更新时间",
			  "bSearchable": false,
			  "sortable": false,
			  "mRender": function (data, type, full) {
			  		if(full.updatetime){
			  			return getMyDate(full.updatetime);
			  		}
			  		return "";
			  }
			}
        ]
    };
	dataGrid = renderGrid(gridObj);
}

function getMyDate(str){  
  var oDate = new Date(str),  
  oYear = oDate.getFullYear(),  
  oMonth = oDate.getMonth()+1,  
  oDay = oDate.getDate(),  
  oHour = oDate.getHours(),  
  oMin = oDate.getMinutes(),  
  oSen = oDate.getSeconds(),  
  oTime = oYear +'/'+ getzf(oMonth) +'/'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间  
  return oTime;  
};  

//补0操作  
function getzf(num){  
  if(parseInt(num) < 10){  
      num = '0'+num;  
  }  
  return num;  
}  

//刷新城市列表
function refreshCitys(){
	$.ajax({
		type: "GET",
		url:"OpShiftRules/GetVailableCitys",
		cache: false,
		data: {},
		success: function (json) {
			if(json&&json.length>0){
				var options = '<option value="">全部</option>';
				for(var i=0;i<json.length;i++){
					var option = json[i];
					options += '<option value="'+option.cityid+'">'+option.cityname+'</option>';
				}
				$("#cityselect").html(options);
			}
		}
    });
}

/**
 * 初始化事件
 */
function initEvent(){
	  //查询
	  $("#searchbtn").click(function(){
	  	var conditionArr = [
    		{ "name":"city","value":$("#cityselect").val()},
    	];
    	dataGrid.fnSearch(conditionArr);
	  });
	  
	  $("#cityselect").select2({
	        placeholder: "全部",
	        minimumInputLength: 0,
	        allowClear: true,
	        ajax: {
	            url: $("#baseUrl").val() + "OpShiftRules/GetCitys",
	            dataType: 'json',
	            data: function (term, page) {
	            	$(".datetimepicker").hide();
	                return {
	                	cityname: term
	                };
	            },
	            results: function (data, page) {
	                return { results: data };
	            }
	        }
	    });
	  getSelectCity();
}

/**
 * 新增
 */
function add() {
	var editForm = $("#editForm").validate();
	editForm.resetForm();
	editForm.reset();
	$("#inp_box").hide();
	$("#input_box2").show();
	$("#editFormDiv").show();
	$("#title").html("新增交接班规则");
	showObjectOnForm("editForm", null);
}

/**
 * 维护
 */
function edit(id) {
	var editForm = $("#editForm").validate();
	editForm.resetForm();
	editForm.reset();
	$("#input_box2").hide();
	$("#inp_box").show();
	$("#editFormDiv").show();
	$("#title").html("修改交接班规则");
	$("#id").val(id);
	$.ajax({
		type: "GET",
		url:"OpShiftRules/GetShiftRules",
		cache: false,
		data: {id:id},
		success: function (json) {
			showObjectOnForm("editForm",json);
		}
    });
}

/**
 * 初始化城市下拉框
 */
function getSelectCity() {
//	var parent = document.getElementById("input_box2");
//	var city = document.getElementById("city");
//	var citynameshow = document.getElementById("citynameshow");
//	getData(parent, citynameshow, city, $("#baseUrl").val() + "OpAccountrules/GetPubCityAddrByList", 30, 0);
	showCitySelect1(
		"#input_box2", 
		"PubInfoApi/GetCitySelect1", 
		null, 
		function(backVal, $obj) {
			$('#citynameshow').val($obj.text());
			$("#city").val($obj.data('id'));
			$('#citynameshow').valid();
		}
	);
}

/**
 * 表单校验
 */
function validateForm() {
	$("#editForm").validate({
		ignore: ":hidden",//不验证的元素,修改规则时，citynamesshow元素影藏了，cityname不可能为空，所以可不做验证。
		rules: {
			citynameshow: {required: true},
			autoshifttime:{required: true},
			manualshifttime:{required: true}
		},
		messages: {
			citynameshow: {required: "请选择所属城市"},
			autoshifttime: {required: "请输入自主交班时限"},
			manualshifttime:{required: "请输入人工指派时限"}
		}
	})
}



/**
 * 保存
 */
function save() {
	$("#save").attr({"disabled":"disabled"});
	var form = $("#editForm");
	
	/*if($("#id").val()){
		form.validate({
			ignore:".citynameshow"
		})
	}else{//新增
		form.validate({
			ignore:""
		})
	}*/
	
	if(!form.valid()){
		$("#save").removeAttr("disabled");
		toastr.error("请输入完整信息", "提示");
		return;
	} 
	
	var url = "OpShiftRules/CreateShiftRules";
	if($("#id").val()) {
		url = "OpShiftRules/UpdateShiftRules";
	}
	
	var data = form.serializeObject();
	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			var message = status.message;
			if (status.status == "success") {
				toastr.success(message, "提示");
				$("#editFormDiv").hide();
				showObjectOnForm("editForm", null);
				dataGrid._fnReDraw();
				refreshCitys();
			} else {
				toastr.error(message, "提示");
			}
			$("#save").removeAttr("disabled");
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			$("#save").removeAttr("disabled");
		}
	});
}

/**
 * 取消
 */
function canel() {
	$("#editFormDiv").hide();
	$("#assignrole").hide();
}

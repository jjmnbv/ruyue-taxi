var dataGrid;

/**
 * 页面初始化
 */
$(function () {
	initGrid();
	
	dateFormat();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "dataGrid",
        sAjaxSource: "IndividualAccountRules/GetIndividualAccountRulesByQuery",
        iLeftColumn: 1,
        scrollX: true,
        language: {
        	sEmptyTable: "暂无计费规则信息"
        },
        columns: [
	        /*{mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "organId", sTitle: "机构Id", sClass: "center", visible: false},
	        {mDataProp: "startTime", sTitle: "开始时间", sClass: "center", visible: false},
	        {mDataProp: "endTime", sTitle: "结束时间", sClass: "center", visible: false},*/
	        {
                    //自定义操作列
                    "mDataProp": "ZDY",
                    "sClass": "center",
                    "sTitle": "操作",
                    "sWidth": 80,
                    "bSearchable": false,
                    "sortable": false,
                    "mRender": function (data, type, full) {
                        var html = "";
                        if (full.ruleState == '0') {// 0-草稿
                        	html += '<button type="button" class="SSbtn blue" onclick="editUse(' +"'"+ full.id + "','" + full.organId + "','" + full.startTime + "','" + full.endTime +"'"+ ')"><i class="fa fa-paste"></i>启用</button>';
                            html += '&nbsp; <button type="button" class="SSbtn green_a" onclick="edit(' +"'"+ full.id + "','" + full.shortName +"'"+ ')"><i class="fa fa-times"></i>修改</button>';
                        } else if (full.ruleState == '1') {// 1-启用
                        	html += '<button type="button" class="SSbtn red" onclick="editDisable(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>禁用</button>';
                        } else if (full.ruleState == '2') {// 2-禁用
                        	html += '<button type="button" class="SSbtn blue" onclick="editUse(' +"'"+ full.id + "','" + full.organId + "','" + full.startTime + "','" + full.endTime +"'"+ ')"><i class="fa fa-paste"></i>启用</button>';
                        } else if (full.ruleState == '3') {// 3-已过期
                        	
                        }

                        return html;
                    }
            },
            //{mDataProp: "shortName", sTitle: "机构名称", sClass: "center", sortable: true },
            {
                //自定义操作列
                "mDataProp": "JGMC",
                "sClass": "center",
                "sTitle": "机构名称",
                "sWidth": 200,
                "bSearchable": false,
                "sortable": false,
                "mRender": function (data, type, full) {
                    var html = "";
                    html += '<a class="breadcrumb font_green" href="IndividualAccountRules/RulesDetail?id=' + full.id + '&shortName=' + full.shortName + '">' + full.shortName +'</a>';
                    return html;
                }
            },
	        {
				  "mDataProp": "yxqx",
				  "sClass": "center",
				  "sTitle": "有效期限",
				  "sWidth": 200,
				  "bSearchable": false,
				  "sortable": false,
				  "mRender": function (data, type, full) {
					  var date="";
					  if (full.startTime) {
						  date += changeToDate(full.startTime);
					  }
					  date += "至"
					  if (full.endTime) {
						  date += changeToDate(full.endTime);
					  }  
					  return date;
				  }
            },
	        //{mDataProp: "ruleStateName", sTitle: "状态", sClass: "center", sortable: true },
	        {
				mDataProp : "ruleStateName",
				sTitle : "状态",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					if (data != null) {
						var html = "";
						if (full.ruleState == '0') {// 0-草稿
							html += '<span class="font_green">' + full.ruleStateName + '</span>' ;
                        } else if (full.ruleState == '1') {// 1-启用
                        	html += '<span>' + full.ruleStateName + '</span>' ;
                        } else if (full.ruleState == '2') {// 2-禁用
                        	html += '<span class="font_red">' + full.ruleStateName + '</span>' ;
                        } else if (full.ruleState == '3') {// 3-已过期
                        	html += '<span class="font_grey">' + full.ruleStateName + '</span>' ;
                        }
						return html;
					} else {
						return "";
					}
				}
			},
	        {mDataProp: "createTime", sTitle: "创建时间", sClass: "center", sortable: true },
	        {mDataProp: "createrVisual", sTitle: "创建人", sClass: "center", sortable: true }
        ]
    };
    
	dataGrid = renderGrid(gridObj);
}

function dateFormat() {
	$('.searchDate').datetimepicker({
        format: "yyyy/mm/dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        clearBtn: true
    });
}

/**
 * 查询
 */
function search() {
	var conditionArr = [
		{ "name": "organId", "value": $("#organId").val() },
		{ "name": "startTime", "value": $("#startTime").val() },
		{ "name": "endTime", "value": $("#endTime").val() },
		{ "name": "ruleState", "value": $("#ruleState").val() }
	];
	dataGrid.fnSearch(conditionArr);
}

/**
 * 毫秒转日期
 * 
 * @param data
 * @returns {String}
 */
function changeToDate(data) {
	return data.replace(/-/g,"/").substr(0,10);
	
	/*var myDate = new Date(data);
	var month = "";
	var date = "";
	var change = "";
	change += myDate.getFullYear() + "/";

	if (myDate.getMonth() < 9) {
		month = "0" + (myDate.getMonth() + 1);
	} else {
		month = (myDate.getMonth() + 1);
	}
	change += month + "/";
	if (myDate.getDate() < 10) {
		date = "0" + myDate.getDate();
	} else {
		date = myDate.getDate();
	}
	change += date;
	return change;*/
}

/**
 * 新增
 */
function add() {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "IndividualAccountRules/CreateRules";
}

/**
 * 修改
 */
function edit(id,shortName) {
	window.location.href= document.getElementsByTagName("base")[0].getAttribute("href") + "IndividualAccountRules/EditRules?id=" + id + "&shortName=" + shortName;
}

/**
 * 禁用
 */
function editDisable(id) {
    var data = {id:id};
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: "IndividualAccountRules/DisableIndividualAccountRules",
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.success(message, "提示");
  
            	dataGrid._fnReDraw();
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
			}	
		}
	});
}

/**
 * 启用
 */
function editUse(id,organId,startTime,endTime) {
	var data = {id:id,
			    organId:organId,
			    startTime:startTime,
			    endTime:endTime
			    };
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: "IndividualAccountRules/EnableIndividualAccountRules",
		data: JSON.stringify(data),
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.success(message, "提示");
  
            	dataGrid._fnReDraw();
			} else {
				var message = status.MessageKey == null ? status : status.MessageKey;
            	toastr.error(message, "提示");
			}	
		}
	});
}


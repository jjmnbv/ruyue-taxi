var dataGrid;
/**
 * 页面初始化
 */
$(function () {
	dateFormat();
	initGrid();
	});
function initGrid() {
	var gridObj = {
		id: "dataGrid",
		iLeftColumn: 1,
		scrollX: true,
		language: {sEmptyTable: "暂无溢价规则信息"},
        sAjaxSource: "PubPremiumRule/GetPubPremiumRule",
        columns: [
	        {mDataProp: "id", sTitle: "Id", sClass: "center", visible: false},
	        {mDataProp: "isoperated", sTitle: "isoperated", sClass: "center", visible: false},
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
               if(full.ruletype == "已过期"){
            	   html += '&nbsp; <button type="button" class="SSbtn grey_q"  onclick="history(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>历史记录</button>';
               }else{
            	   if(full.rulestatus == "禁用"){
                       html += '<button type="button" class="SSbtn red_q"  onclick="disforbid(' +"'"+ full.id +"'"+","+"'"+full.rulename+"'"+')"><i class="fa fa-paste"></i>启用</button>';
                     }else{
                       html += '<button type="button" class="SSbtn grey_q"  onclick="forbid(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>禁用</button>';
                     }
            	   html += '&nbsp; <button type="button" class="SSbtn green_q"  onclick="modify(' +"'"+ full.id +"'"+","+"'"+full.ruletype+"'"+","+"'"+full.rulestatus+"'"+')"><i class="fa fa-paste"></i>修改</button>';
                  if(full.isoperated == "1"){
                      html += '&nbsp; <button type="button" class="SSbtn grey_q"  onclick="history(' +"'"+ full.id +"'"+ ')"><i class="fa fa-paste"></i>历史记录</button>';
                    }
               }
                  return html;
              }
          },
	        {mDataProp: "citycode", sTitle: "城市", sClass: "center", sortable: true },
	        {
                "mDataProp": "DDH",
                "sClass": "center",
                "sTitle": "规则名称",
                "mRender": function (data, type, full) {
			        var html = "";
			    	html += '<a href="javascript:void(0);"onclick="detail(' +"'"+ full.id +"'"+","+"'"+full.ruletype+"'"+')">'+full.rulename+'</a>';
			    	return html;
			      }
            },
	        {mDataProp: "cartype", sTitle: "业务类型", sClass: "center", sortable: true },
	        {mDataProp: "ruletype", sTitle: "规则类型", sClass: "center", sortable: true },
	        {mDataProp: "rulestatus", sTitle: "规则状态", sClass: "center", sortable: true },
	        {mDataProp: "isperpetual", sTitle: "有效期", sClass: "center", sortable: true },
	        {mDataProp: "updater", sTitle: "操作人", sClass: "center", sortable: true }
	        
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
		{ "name": "rulename", "value": $("#rulename").val() },
		{ "name": "cartype", "value": $("#cartype").val() },
		{ "name": "ruletype", "value": $("#ruletype").val()},
		{ "name": "rulestatus", "value": $("#rulestatus").val()},
		{ "name": "startdt", "value": $("#startdt").val()},
		{ "name": "enddt", "value": $("#enddt").val()},
		
	];
	dataGrid.fnSearch(conditionArr);
}
/**
 * 重设
 */
function emptys(){
    $("#startdt").val("");
	$("#enddt").val("");
	$("#rulestatus").val("");
	$("#ruletype").val("");
	$("#cartype").val("");
	$("#rulename").val("");
	$("#citycode").val("");
	search();
}
/**
 * 时间设置
 * @param now
 * @returns
 */
function formatDate(now) { 
	var year=now.getYear(); 
	var month=now.getMonth()+1; 
	var date=now.getDate(); 
	var hour=now.getHours(); 
	var minute=now.getMinutes(); 
	var second=now.getSeconds(); 
	return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
} 

//日期设置
function dateFormat() {
	$('.searchDate').datetimepicker({
		 format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
	        language: 'zh-CN', //汉化
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	       forceParse: 0
    });
	Date.prototype.format =function(format)
	{
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
	}
}
//禁用
function disforbid(id,rulename){
	var data = {
		id : id
	};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : "PubPremiumRule/ruleConflict;",
		data : JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(result) {
			if (result.rulename == null || result.rulename == "") {
				toastr.success('已经启用', "提示");
				dataGrid._fnReDraw();
			}else {
				var allRulename = "";
				for(var i=0;i<result.rulename.length;i++){
					var rulename = result.rulename[i].rulename;
					allRulename = allRulename +rulename+",";
				}
				var comfirmData={
						tittle:"提示",
						context:"当前启用规则与已存在【"+allRulename+"】存在冲突,若要启用当前规则,请先禁用【"+allRulename+"】",
						button_l:"我知道了",
						click: "postchangestate2('" +id+"','"+1+"')",
						htmltex:"<input type='hidden' placeholder='添加的html'> "
					};
				    Zconfirm1(comfirmData);
			}
		}
	});
}
function forbid(id){
	var data = {
		id : id
	};
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : "PubPremiumRule/ruleConflictOk;",
		data : JSON.stringify(data),
		contentType : 'application/json; charset=utf-8',
		async : false,
		success : function(status) {
			if (status == 1) {
				toastr.success('禁用成功', "提示");
				dataGrid._fnReDraw();
			}
		}
	});
}
//新增
function addOn(){
	var id = "";
	window.location.href=base+"PubPremiumRule/AddIndex?id="+id;
}
//转跳详情界面
function detail(id,ruletype){
	if(ruletype == "按星期"){
		window.location.href = base + "PubPremiumRule/DetailIndex?id="+id
	}else{
		window.location.href = base + "PubPremiumRule/DetailIndexDate?id="+id
	}
}
//转跳历史记录
function history(id,ruletype){
	window.location.href=base+"PubPremiumRule/HistoryIndex?id="+id;
}
//修改
function modify(id,ruletype,rulestatus){
	window.location.href=base+"PubPremiumRule/AddIndex?id="+id+"&ruletype="+ruletype+"&rulestatus="+rulestatus;;
}

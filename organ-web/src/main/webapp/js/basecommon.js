/**
 * 将数组对象转换为JSON
 * @param {} form
 * @return {}
 */
var convertFormToJson = function(form){
	var o = {};
	for (var i = 0, len = form.length; i < len; i++) {
		if (form[i].value != "" && form[i].value != "NaN") {
			var value = form[i].value;
			o[form[i].name] = value;
		}
	}
	return o;
}

/**
 * 将数据对象渲染至指定的表单中
 * @param {} formId
 * @param {} obj
 */
function showObjectOnForm(formId, obj) {
	//文本框、下拉框、文本域
	var inputs = $("#" + formId + " input, #" + formId + " textarea,#" + formId + " select").not(function (i) {
	var selector = "#" + formId + " input[type=checkbox][name=" + $(this).attr("name") + "]";
		if ($(selector).length > 0) {
			return $(this);
		}
	});
	
	inputs.each(function (index) {
		var value = "";
		var current = $(this);
		// var isCombox = current.hasClass("ui-combobox-input");
		var isCombox = current.context.type == "select-one" ? true : false;
		if (obj != null && obj != undefined) {
			var tempValue;
			if (isCombox) {
				// tempValue = obj[current.prev().attr("name")]
				tempValue = obj[current.attr("name")];
			} else {
				tempValue = obj[current.attr("name")];
			}
			if (tempValue != undefined) {
				value = tempValue;
			}
		}
		//下拉框
		if (isCombox && value != "") {
			/*var comboboxEl = current.prev();
			if (comboboxEl.attr("length") > 0) {
				var options = comboboxEl.attr("options");
				if (options[options.length - 1].value.indexOf("@") > 0) {
					$(options).each(function (index, item) {
						if (item.value.split("@")[0] == value) {
							value = item.value;
						return false;
						}
					});
				}
			}
			comboboxEl.combobox("setValue", value);*/
			var options = current.find("option");
			options.each(function(i) {
				if ($(this).val() === value) {
					current.get(0).selectedIndex = i;
				}
			});
			
		} else {
			current.val(value);
		}
	});
    
	//复选框
	var checkboxs = $("#" + formId + " input[type=checkbox]");
	checkboxs.each(function (index) {
		var checked = false;
		var current = $(this);
		if (obj != null) {
			tempChecked = obj[current.attr("name")];
			if (tempChecked != undefined) {
				if (typeof (tempChecked) == "string") {
					checked = tempChecked.toLowerCase() == "true";
				} else if (typeof (tempChecked) == "boolean") {
					checked = tempChecked;
				} else {
					throw current.attr("name") + "的值" + tempChecked + "不是有效的booleen类型";
				}
			}
		}
		if (checked) {
			current.attr("checked", "checked");
		} else {
			current.removeAttr("checked");
		}
	});
}


/**
 * 将表单上的元素转换成json对象
 * @return {}
 */
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * 提示框全局配置
 * @type 
 */
toastr.options = {
	"closeButton": false,
	"debug": false,
	"newestOnTop": false,
	"progressBar": true,
	"positionClass": "toast-center-center",
	"preventDuplicates": false,
	"onclick": null,
	"showDuration": "300",
	"hideDuration": "2000",
	"timeOut": "2000",
	"extendedTimeOut": "1000",
	"showEasing": "swing",
	"hideEasing": "linear",
	"showMethod": "fadeIn",
	"hideMethod": "fadeOut"
}

/**
 * 表格渲染
 * @param {} gridObj
 */
function renderGrid(gridObj) {
	var params = {
		bProcessing: gridObj.bProcessing ? gridObj.bProcessing : false,
		bServerSide: gridObj.bServerSide ? gridObj.bServerSide : true,
		lengthChange: gridObj.hasOwnProperty("lengthChange") ? gridObj.lengthChange : false,
		userQueryParam: gridObj.userQueryParam ? gridObj.userQueryParam : null,
		ordering: gridObj.ordering ? gridObj.ordering : false,
		searching: gridObj.searching ? gridObj.searching : false,
		bSort: gridObj.bSort ? gridObj.bSort : false,
		bInfo: gridObj.bInfo ? gridObj.bInfo : true,
		bFilter: gridObj.bFilter ? gridObj.bFilter : false,
		bAutoWidth: gridObj.bAutoWidth ? gridObj.bAutoWidth : true,
        iDisplayLength: gridObj.iDisplayLength ? gridObj.iDisplayLength : 10,
        sAjaxSource: gridObj.sAjaxSource,
        columns: gridObj.columns,
        fnInitComplete: gridObj.fnInitComplete ? gridObj.fnInitComplete : null,
        language: {
			sProcessing: "处理中...",
			sLengthMenu: "显示 _MENU_ 项结果",
			sZeroRecords: "没有匹配结果",
			sInfo: "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
			sInfoEmpty: "显示第 0 至 0 项结果，共 0 项",
			sInfoFiltered: "(由 _MAX_ 项结果过滤)",
			sInfoPostFix: "",
			sSearch: "搜索:",
			sUrl: "",
			sEmptyTable: gridObj.language ? (gridObj.language.sEmptyTable ? gridObj.language.sEmptyTable : "表中数据为空") : "表中数据为空",
			sLoadingRecords: "载入中...",
			sInfoThousands: ",",
			oPaginate: {
				sFirst: "首页",
				sPrevious: "上页",
				sNext: "下页",
				sLast: "末页"
			},
			oAria: {
			sSortAscending: ": 以升序排列此列",
			sSortDescending: ": 以降序排列此列"
		    }
        },
        fnServerData: function(sSource, aoData, fnCallback, oSettings) {
        	if(gridObj.userQueryParam != null) {
	        	aoData = aoData.concat(gridObj.userQueryParam);
        	}
        	
			$.ajax({
	        	url: gridObj.sAjaxSource,
	            data: JSON.stringify(convertFormToJson(aoData)),
	        	type: "post",
				dataType: 'json',
	            contentType: 'application/json; charset=utf-8',
	            cache: false,
	            success: function(result) {
	            	fnCallback(result);
	            	
	            	if(gridObj.userHandle) {
	            		gridObj.userHandle(oSettings, result);
	            	}
	            },
	            error: function(msg) {
	            	
	            }
			})
		}
	}
	
	var dataTable = $("#" + gridObj.id).dataTable(params);
	return dataTable;
}

/**
 * 传入某格的内容和长度，
 * 	如果长度不为空，则按长度截取，如果为空，则默认按8个字符截取
 * 	如果传入内容data没有超过长度，则不显示tooltip
 * @param data
 * @param len 传入为中文字符的len
 * @returns {String}
 */
function showToolTips(data, len, placement){
	if(len == undefined || len == null || len == ""){
		len = 8;
	}
	if(placement == undefined || placement == null || placement == ""){
		placement = "auto left";
	}
	
	var content = data == null ? "" : data;
	if (content && content.length > len) {
		content = content.substring(0, len) + "...";
    }else{
    	return content;
    }
	return "<span title='" + data + "'>" + content + "</span>";
//	var title = '<span style=\'word-break:break-all;display:inline-block;\'>'+data+'</span>';
//	return "<div data-toggle=\"tooltip\" data-title=\""+title+"\" data-html=\"true\" data-placement=\""+placement+"\">"+content+"</div>";
}
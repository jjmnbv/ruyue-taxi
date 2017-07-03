/**
 * 将数组对象转换为JSON
 * @param {} form
 * @return {}
 */

var homehref;
$(function() {
	//获取当前用户首页的地址
	var label = $(window.top.document).find(".side_bar li a");
	if(null != label && label.length > 0) {
		homehref = $(window.top.document).find(".side_bar li a")[0] .href;
	} else {
		$.ajax({
        	url: "User/GetMenuByLoginName",
        	type: "post",
			dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            cache: false,
            success: function(result) {
            	if(null != result && undefined != result.menulist && null != result.menulist && result.menulist.length > 0) {
            		var menu;
            		var resultMenu = result.menulist[0];
            		if(undefined != resultMenu.children && null != resultMenu.children) {
            			var subMenu = resultMenu.children;
                		if(subMenu.length > 0) {
                			var subbMenu = subMenu[0];
                			if(undefined != subbMenu.children && null != subbMenu.children) {
                				if(subbMenu.children.length > 0) {
                					menu = subbMenu.children[0];
                				}
                    		} else {
                    			menu = subbMenu;
                    		}
                		}
            		} else {
            			menu = resultMenu;
            		}
            		if(null != menu) {
            			homehref = menu.applicationdomain + menu.url;
            		}
            	}
            },
            error: function(msg) {
            	
            }
		})
	}
});

/**
 * 跳转到首页
 */
function homeHref() {
	//去掉当前选择的栏目
	$(window.top.document).find(".side_bar li").removeClass("current");
	//选中首页的栏目
	$(window.top.document).find(".side_bar > li").eq(0).addClass("current");
	window.location.href = homehref;
}

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
		bInfo: undefined != gridObj.bInfo && null != gridObj.bInfo ? gridObj.bInfo : true,
		bFilter: gridObj.bFilter ? gridObj.bFilter : false,
		bAutoWidth: gridObj.bAutoWidth ? gridObj.bAutoWidth : true,
        iDisplayLength: gridObj.iDisplayLength ? gridObj.iDisplayLength : 10,
        sAjaxSource: gridObj.sAjaxSource,
        columns: gridObj.columns,
		createdRow:gridObj.createdRow? gridObj.createdRow : null,
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
	            	// $('[data-toggle="tooltip"]').tooltip();
	            },
	            error: function(msg) {
	            	
	            }
			})
		}
	}


	if(gridObj.scrollX) {
		params["scrollX"] = true;
		params["scrollCollapse"] = true;
	}
	var dataTable = $("#" + gridObj.id).dataTable(params);
	
	// 固定列属性设置
	if(gridObj.iLeftColumn) {
		new $.fn.dataTable.FixedColumns(dataTable,{"iLeftColumns": gridObj.iLeftColumn});
	}
	
	return dataTable;
}

/**
 * 日期格式化
 * @param {} time
 * @return {}
 */
function timeStamp2String(time, format){
	var mark = "-";
	if(null != format && format.length > 0) {
		mark = format;
	}
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
	var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	return year + mark + month + mark + date+" "+hour+":"+minute+":"+second;
}

/**
 * 日期格式化 MM/dd hh:mm
 * @param {} time
 * @return {}
 */
function formatTimeForDetail(time){
	var datetime = new Date();
	datetime.setTime(time);
	var year = datetime.getFullYear();
	var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
	var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	return month + "/" + date+" "+hour+":"+minute;
}

//手机号码验证
function regPhone(phone){
	var myreg = /^(((13[0-9]{1})|(15[0-3,5-9])|(18[0-9]{1})|(14[5,7,9])|(17[0,1,3,5-8]))+\d{8})$/; 
	return !myreg.test(phone);
}

/**
 * 将时间(秒)转换为xx小时xx分钟格式
 * @param second
 */
function convertSecond(second) {
	var minute = Math.ceil(second/60);
	if(minute < 60) {
		return minute + "分钟";
	} else {
		var hour = parseInt(minute/60) + "小时";
		if(minute%60 > 0) {
			hour = hour + parseInt(minute%60) + "分钟";
		}
		return hour;
	}
}

/**
 * 将时间(分)转换为xx小时xx分钟格式
 * @param minute
 */
function convertMinute(minute) {
	return convertSecond(minute * 60);
}


/**
 * 传入某格的内容和长度，
 * 	如果长度不为空，则按长度截取，如果为空，则默认按8个字符截取
 * 	如果传入内容data没有超过长度，则不显示tooltip
 * @param data
 * @param len 传入为中文字符的len
 * @returns {String}
 * @param dataOther 用于两行显示的时候
 */
function showToolTips(data, len, placement, dataOther){
	if(len == undefined || len == null || len == ""){
		len = 8;
	}
	if(placement == undefined || placement == null || placement == ""){
		placement = "";
	};
	// 第二行数据
	var contentOther = dataOther == null ? "" : dataOther;
	if (contentOther && contentOther.length > len) {
		contentOther = contentOther.substring(0, len) + "...";
    }
	var contentOtherStr = '<span style="color:red">' + contentOther + '</span>';
	// 第一行数据
	var content = data == null ? "" : data;
	if (content && content.length > len) {
		content = content.substring(0, len) + "...";
    }else if(contentOther){
    	if( (content && content.length <= len) && (contentOther && contentOther.length <= len) ){
    		return '<div style="text-align:left;">' + content + '</br>' + contentOtherStr + '</div>';
    	}
    }else{
    	return content;
    }
    $('body').css('overflow', 'hidden');
	// 用于两行显示的时候
	if(dataOther == undefined || dataOther == null || dataOther == ""){
		var bubbleStr = '<div class="bubbleData_box" style="color:initial;">'+ data +'<span class="bubbleData_box_span1"></span><span class="bubbleData_box_span2"></span><span class="bubbleData_box_span3"></span></div>';
		return '<span style="color:'+ placement +'" onmouseover="mouseenterFn(this)" onmouseout="mouseleaveFn(this)">' + content + bubbleStr + '</span>';
	}else if(dataOther){
		var bubbleStr = '<div class="bubbleData_box" style="color:initial;">'+ data + '</br>' + dataOther +'<span class="bubbleData_box_span1"></span><span class="bubbleData_box_span2"></span><span class="bubbleData_box_span3"></span></div>';
		return '<div style="color:'+ placement +'; text-align:left;" onmouseover="mouseenterFn(this)" onmouseout="mouseleaveFn(this)">' + content + '</br>' + contentOtherStr + bubbleStr + '</div>';
	}
}
/**
 * 内容显示不下时点点点的处理方式
 */
//鼠标移上后显示
function mouseenterFn(obj){
	// 计算显示窗在哪个位置，利用td的高度，宽度和所在位置设置显示窗的right，bottom的值
	var widthVal = 200;
	// 计算当前td之后还有几个td
	var residueTdNumber = $(obj).parent().siblings().length - $(obj).parent().index();
	// 计算td的宽度
	
	var scrollLeft = 0;
	if( $('.dataTables_scrollBody').scrollLeft() > 0 ){
		scrollLeft = $('.dataTables_scrollBody').scrollLeft();
	}
	
	if( residueTdNumber > 0 ){
		$(obj).find('.bubbleData_box').css({
			'left': $(obj).parent()[0].offsetLeft - scrollLeft + 10 + 'px',
			'right': 'initial',
		});	
		$(obj).find('.bubbleData_box_span1').css({
			'right': 'initial',
			'left': '40px'
		});	
	}else{
		$(obj).find('.bubbleData_box').css({
			'left': 'initial',
			'right': 10 + 'px',
		});
		$(obj).find('.bubbleData_box_span1').css({
			'right': '30px',
		});	
	}
	// 以下是处理bottom值
	var residueTrNumber = $(obj).parent().parent().siblings().length - $(obj).parent().parent().index();
	var parentHeight = $(obj).parent().parent().innerHeight();
	var temp = parentHeight;
	if($(obj).parent().parent().index() == 0){
		temp = parentHeight + 5;
	};
	// 如果有滚动条时，要加上滚动条高度
	var tempScroll = 0;
	if( $('.dataTables_scrollBody>table').width() - 4 > $('.dataTables_scrollBody').width() ){
		tempScroll = 14;
	}
	$(obj).find('.bubbleData_box').css('bottom', parentHeight*residueTrNumber + temp + tempScroll + 'px');
	
	// 当此方法需要在固定列使用时
	var $parentEle = $(obj).parent().parent().parent().parent().parent().parent();
	
	if( $parentEle.hasClass('DTFC_LeftBodyWrapper') ){
		$('.DTFC_LeftBodyLiner').css({
			'overflow': 'initial !important',
			'width': '100%',
			'position': 'initial'
		});
		$('.DTFC_LeftBodyWrapper').css({
			'overflow': 'initial',
			
		});
	}
	// 显示宽度大于默认宽度
	if($(obj).width() >= widthVal){
		$(obj).find('.bubbleData_box').css({
			'width': $(obj).width()+'px',
		    'max-width': 'initial',
			'white-space': 'normal',
		    'word-wrap': 'break-word',
	        'word-break': 'break-all'
		});
		$(obj).find('.bubbleData_box').css({
			'left': $(obj).parent()[0].offsetLeft+$(obj)[0].offsetLeft - scrollLeft + 10 + 'px',
			'right': 'initial',
		});	
		$(obj).find('.bubbleData_box_span1').css({
			'right': 'initial',
			'left': '40px;'
		});	
	}else if($(obj).find('.bubbleData_box').width() >= (widthVal-22) ){
		$(obj).find('.bubbleData_box').css({
			'width': widthVal + 'px',
			'white-space': 'normal'
		});
	};
	$(obj).find('.bubbleData_box').css('word-wrap', 'break-word');
	
	// 字数太多时导致高度太高被遮住问题
	if($(obj).find('.bubbleData_box').text().length > 52 && $(obj).find('.bubbleData_box').text().length <= 80){
		$(obj).find('.bubbleData_box').css({
			'max-width': 300 + 'px',
			'width': 300 + 'px'
		});
		if( residueTdNumber > 0 ){
			$(obj).find('.bubbleData_box').css({
				'left': $(obj).parent()[0].offsetLeft - scrollLeft + 10 - 100 + 'px',
				'right': 'initial',
			});	
			$(obj).find('.bubbleData_box_span1').css({
				'right': 'initial',
				'left': '140px'
			});	
		};
		if($(obj).parent().width() > 300 ){
			$(obj).find('.bubbleData_box').css({
				'left': 'initial',
				'right': 'initial',
			});	
			$(obj).find('.bubbleData_box_span1').css({
				'right': 'initial',
				'left': ( $(obj).parent().width() - $(obj).width() ) / 2 + 40 + 'px'
			});	
		};
	}else if( $(obj).find('.bubbleData_box').text().length > 80 && $(obj).find('.bubbleData_box').text().length <= 160 ){
		$(obj).find('.bubbleData_box').css({
			'max-width': 400 + 'px',
			'width': 400 + 'px'
		});
		if( residueTdNumber > 0 ){
			$(obj).find('.bubbleData_box').css({
				'left': $(obj).parent()[0].offsetLeft - scrollLeft + 10 - 200 + 'px',
				'right': 'initial',
			});	
			$(obj).find('.bubbleData_box_span1').css({
				'right': 'initial',
				'left': '240px'
			});	
		};
		if($(obj).parent().width() > 400 ){
			$(obj).find('.bubbleData_box').css({
				'left': 'initial',
				'right': 'initial',
			});	
			$(obj).find('.bubbleData_box_span1').css({
				'right': 'initial',
				'left': ( $(obj).parent().width() - $(obj).width() ) / 2 + 40 + 'px'
			});	
		};
	}else if( $(obj).find('.bubbleData_box').text().length > 160 ){
		$(obj).find('.bubbleData_box').css({
			'max-width': 600 + 'px',
			'width': 600 + 'px'
		});
		if( residueTdNumber > 0 ){
			$(obj).find('.bubbleData_box').css({
				'left': $(obj).parent()[0].offsetLeft - scrollLeft + 10 - 400 + 'px',
				'right': 'initial',
			});	
			$(obj).find('.bubbleData_box_span1').css({
				'right': 'initial',
				'left': '440px'
			});	
		};
		if($(obj).parent().width() > 600 ){
			$(obj).find('.bubbleData_box').css({
				'left': 'initial',
				'right': 'initial',
			});	
			$(obj).find('.bubbleData_box_span1').css({
				'right': 'initial',
				'left': ( $(obj).parent().width() - $(obj).width() ) / 2 + 40 + 'px'
			});	
		};
	};
	$(obj).find('.bubbleData_box').show();
};
function mouseleaveFn(obj){
	$(obj).find('.bubbleData_box').hide();
};

/**
 * 初始化多文本框
 * @param content
 */
function initUeditor(content, length) {
	var ue = UE.getEditor(content, {
		toolbars:[
			[
			 'undo', //撤销
			 'redo', //重做
			 'bold', //加粗
			 'indent', //首行缩进
			 'italic', //斜体
			 'underline', //下划线
			 'strikethrough', //删除线
			 'subscript', //下标
			 'fontborder', //字符边框
			 'superscript', //上标
			 'formatmatch', //格式刷
			 'source', //源代码
			 'pasteplain', //纯文本粘贴模式
			 'horizontal', //分隔线
			 'removeformat', //清除格式
			 'cleardoc', //清空文档
			 'fontfamily', //字体
			 'fontsize', //字号
			 'paragraph', //段落格式
			 'justifyleft', //居左对齐
			 'justifyright', //居右对齐
			 'justifycenter', //居中对齐
			 'justifyjustify', //两端对齐
			 'forecolor', //字体颜色
			 'insertorderedlist', //有序列表
			 'insertunorderedlist', //无序列表
			 'fullscreen', //全屏
			 'directionalityltr', //从左向右输入
			 'directionalityrtl', //从右向左输入
			 'rowspacingtop', //段前距
			 'rowspacingbottom', //段后距
			 'lineheight' //行间距
			]
		],
		initialFrameHeight: 400, //初始化编辑器宽度
		allHtmlEnabled: true, //提交到后台的数据是否包含整个html字符串
		maximumWords: length, //允许的最大字数
		elementPathEnabled: false, //是否启用元素路径
		wordOverFlowMsg: "协议内容最多只能输入" + length + "个字符" //超出字数限制提示
	});
	return ue;
}

/**
 * 限制订单号只能输入数字和字母
 */
function checkOrderno(orderno) {
	$(orderno).val($(orderno).val().replace(/[\W\_]/g, ""));
}

/**
 * 城市控件1
 * @param container
 * @param url
 * @param defValue
 * @param callbackFn
 */
var cityData = null;
function showCitySelect1(container, url, defValue, callbackFn) {
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: url,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (data) {
			if(null == cityData) {
				cityData = data;
			}
			changeCity1(container, defValue, callbackFn, data);
		}
	});
}

/**
 * 初始化城市控件
 * @param container
 * @param url
 * @param defValue
 * @param callbackFn
 */
function changeCity1(container, defValue, callbackFn, data) {
	$(container).find(".city_container").remove();
	if(null == data || undefined == data) {
		data = cityData;
	}
	citySelect({
		container: container,
		cityDatas: data,
		cityMarkid: defValue,
		callbackFn: callbackFn
	});
}

/**
 * 根据城市名称获取城市详情
 * @param cityname
 */
function getCity1(cityname) {
	if(null != cityData) {
		var result = {};
		for(var key in cityData) {
			var value = cityData[key];
			var citys = value.citys;
			for(var i = 0;i < citys.length;i++) {
				if(citys[i].text == cityname) {
					result.provincename = key;
					result.provinceId = value.id;
					result.provinceMarkid = value.markid;
					result.id = citys[i].id;
					result.markid = citys[i].markid;
					result.city = citys[i].text;
					return result;
				}
			}
		}
	} else {
		return null;
	}
}

/**
 * 只能输入数字
 */
function onlyInputNum(data) {
	$(data).val($(data).val().replace(/[\D]/g, ""));
}

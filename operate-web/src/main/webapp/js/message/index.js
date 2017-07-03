var example;
/**
 * 页面初始化
 */
$(function () {
	initGrid();
	initHref();
});

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "example",
        sAjaxSource: "Message/GetNewsByUserId",
        columns: [
	        {
				mDataProp : "qbbjyd",
				sTitle : "<span onclick='update();'><b>全部标记已读</b></span>",
				sClass : "center",
				sortable : true,
				mRender : function(data, type, full) {
					var html = "";
					if (full.content) {
						var messageContent = JSON.parse(full.content);
						if (messageContent.title) {
							html += "<b>" + messageContent.title + "</b>";
						}
						if (messageContent.content) {
							html += "<span class='span_1' style='width: 95%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;'>"+editMessage(messageContent.content)+"</span>";
						}
					}
					var createtime = timeStamp2String(full.createtime);
					html += "<span class='span_2'>"+createtime.substring(0,createtime.length-3)+"</span>";
					if( full.newsstate == "0" ){
						html += "<span class='span_3'></span>";
					}
					html += "<span style='display: none;'>" + full.id + "</span>";

					
					/*html += full.title;
					html += "<span class='span_1' style='width: 95%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;'>"+editMessage(full.content)+"</span>";
					html += "<span class='span_2'>"+full.createTime+"</span>";
					if( full.newsState == "0" ){
						html += "<span class='span_3'></span>";
					}
					html += "<span style='display: none;'>" + full.id + "</span>";*/

                    return html;
				}
			}
        ]
    };
    
	example = renderGrid(gridObj);
}

/**
 * 处理消息内容
 */
function editMessage(message) {
	if (message.indexOf("。") != -1) {
		message = message.substring(0,message.indexOf("。")+1);
	}
	
	return message;
}

/**
 * 添加live()这个方法
 */
jQuery.fn.extend({
    live: function (event, callback) {
       if (this.selector) {
            jQuery(document).on(event, this.selector, callback);
        }
    }
});

/**
 * 点击消息时，跳转到消息详情
 */
function initHref() {
	$("table > tbody > tr > td").live('click',function () {
		//alert( $(this).find("span:last").text());
		if ($(this).find("span:last").text() != "") {
			window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "Message/MessageDetail/" + $(this).find("span:last").text();
		}
    });
}

/**
 * 全部标记已读
 */
function update() {
	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: 'Message/UpdateOpUserNews',
		data: null,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			if (status.ResultSign == "Successful") {
            	example._fnReDraw();
            	//window.location.href = "Message/Index";
			}
		},
		error: function (status) {
			
		}
	});
}



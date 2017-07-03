var example;
/**
 * 页面初始化
 */
$(function () {
	initUnreadNum();
	initGrid();
	initHref();
});

/**
 * 初始化未读消息
 */
function initUnreadNum() {
	var unReadNum = $("#unReadNum").val();
	if (unReadNum > 0) {
		$("#num").addClass("num");
		$("#num").show();
	} else {
		$("#num").removeClass("num");
		$("#num").hide();
	}
}

/**
 * 表格初始化
 */
function initGrid() {
	var gridObj = {
		id: "example",
        sAjaxSource: "Message/GetNewsByUserId",
        iDisplayLength: 20,
        columns: [
	        {
				mDataProp : "qbbjyd",
				sTitle : "<span onclick='update();'>全部标记已读</span>",
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
					html += "<span class='span_2'>"+timeStamp2String(full.createtime)+"</span>";
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
	return year + mark + month + mark + date+" "+hour+":"+minute;
}

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
		url: 'Message/UpdateOrgUserNews',
		data: null,
		contentType: 'application/json; charset=utf-8',
		async: false,
		success: function (status) {
			window.location.href = document.getElementsByTagName("base")[0].getAttribute("href") + "Message/Index";
		}
	});
}

function showInfo(){
	$.ajax({
			type: "GET",
			url:"User/GetCurrentUserInfo",
			cache: false,
			data: null,
			success: function (json) {
				if(json.status=="success"){
					if(json.userinfo){
						$("#nickname_i").html(json.userinfo.nickname);
						$("#account_i").html(json.userinfo.account);
						
						$("#sex_i").html(json.userinfo.sex=="0"?"男":"女");
						$("#role_i").html(json.userinfo.rolename);
						$("#dept_i").html(json.userinfo.deptname);
						$("#img_i").attr("src",json.userinfo.img);
					}
					$("#userinfo").show();
				}else if(json.status=="super"){
					//超管不弹窗
				}
			},
			error: function (xhr, status, error) {
				return;
			}
	    });
	
}

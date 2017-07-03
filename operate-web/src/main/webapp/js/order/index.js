//前端补充
$(function() {
	//常用地址跟搜索地址切换
    $(".stab>div").click(function() {
        $(this).addClass("shen_on").siblings().removeClass("shen_on");
        var n=$(this).index();
        var x=$(this).parents(".dizhibox");
        x.find(".stabox:eq("+n+")").removeClass("bhide").siblings(".stabox").addClass("bhide");
    });
    //接机,只能下车地址搜索
    if($("#ordertype").val() == "2"){
    	//弹出窗口
        $("#offAddress").focus(function() {
        	if($.trim($(this).val()) == ""){
        		$(this).parent().find(".dizhibox").find(".bresult").empty();
        	}else{
        		$(this).val($.trim($(this).val())+" ");
        	}
            $(this).parent().find(".dizhibox").slideDown(500);
        });
        $("#offAddress").blur(function() {
            $(this).parent().find(".dizhibox").slideUp(500);
        });
        $(".dizhibox").mousedown(function(e){
        	return false;
        });
    //送机只能上车地址搜索
    }else if($("#ordertype").val() == "3"){
    	//弹出窗口
        $("#onAddress").focus(function() {
        	if($.trim($(this).val()) == ""){
        		$(this).parent().find(".dizhibox").find(".bresult").empty();
        	}else{
        		$(this).val($.trim($(this).val())+" ");
        	}
            $(this).parent().find(".dizhibox").slideDown(500);
        });
        $("#onAddress").blur(function() {
            $(this).parent().find(".dizhibox").slideUp(500);
        });
        $(".dizhibox").mousedown(function(e){
        	return false;
        });
    //其他都可以搜索
    }else{
    	//弹出窗口
        $("#onAddress,#offAddress").focus(function() {
        	if($.trim($(this).val()) == ""){
        		$(this).parent().find(".dizhibox").find(".bresult").empty();
        	}else{
        		$(this).val($.trim($(this).val())+" ");
        	}
            $(this).parent().find(".dizhibox").slideDown(500);
        });
        $("#onAddress,#offAddress").blur(function() {
            $(this).parent().find(".dizhibox").slideUp(500);
        });
        $(".dizhibox").mousedown(function(e){
        	return false;
        });
    }
	//常用地址输入
//    $(".stabox>li").click(function() {
//        var text=$(this).text();
//        var x=$(this).parents();
//        x.prev("input").val(text);
//    });
	//划开关闭弹窗
//    $(".dizhibox").mouseleave(function(){
//    	$(this).hide();
//    });
});
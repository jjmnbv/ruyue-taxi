//<!-- 前端补充  -->
$(document).ready(function() {
    $(".stab>div").click(function() {
        $(this).addClass("shen_on").siblings().removeClass("shen_on");
        var n=$(this).index();
        var x=$(this).parents(".dizhibox");
        x.find(".stabox:eq("+n+")").removeClass("bhide").siblings(".stabox").addClass("bhide");
    });
    $(".x").click(function() {
    	$(this).parents(".dizhibox").hide(); 
    }); 
    $("#onAddress,#offAddress").click(function() {
    	$(".bresult").html("");
        $(this).next(".dizhibox").slideDown(100);
    });
    $(".stabox>li").click(function() {
        var text=$(this).text();
        var x=$(this).parents();
        x.prev("input").val(text);
    });
})
//<!-- 前端补充  -->
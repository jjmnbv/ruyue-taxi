var _loading = function () {
    var loading = $('<div class="loadingdiv"><img src="content/img/ajax-modal-loading.gif" alt="图片加载中···" /></div>').appendTo($(document.body));;
    return {
        show: function () {
            //div占满整个页面
            loading.css("width", "100%");
            loading.css("display", "block");
            loading.css("height", $(window).height() + $(window).scrollTop());
            //设置图片居中
            $('img', loading).css("display", "block");
            $('img', loading).css("left", ($(window).width() - 88) / 2);
            $('img', loading).css("top", ($(window).height() + $(window).scrollTop()) / 2);
        },
        hide: function () {
            loading.css("width", "0");
            loading.css("display", "none");
            loading.css("height", "0");
            //设置图片隐藏
            $('img', loading).css("display", "none");
        }
    };
}();
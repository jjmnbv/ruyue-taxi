var citySelect = function(options){
	var defaults = {
		// 默认
	};
	var opts = $.extend(defaults, options);
	var cityFn = {
		// 初始化
		init : function(){ 
			var _this = this;
			this.renderProvince(); 
			// 默认打开显示省
			if( opts.cityMarkid ){
				var provinceMarkid = opts.cityMarkid.slice(0,7);
			}
			var provinceIndex = 0;
			for(var o in opts.cityDatas){
				if( opts.cityDatas[o].markid == provinceMarkid ){
					$(opts.container).find('.province_con>span').eq(provinceIndex).addClass('span_active').siblings().removeClass('span_active');
					this.renderCity(opts.cityDatas[o]);
					this.provinceIndex = provinceIndex;
				}else if( !opts.cityMarkid ){
					$(opts.container).find('.province_con>span').eq(0).addClass('span_active').siblings().removeClass('span_active');
					this.renderCity(opts.cityDatas[o]);
					break;
				}
				provinceIndex ++;
			};
			console.log(opts.container);
			// 容器点击后
			$(opts.container).find('input').on('click',function () {
                $(".city_container").hide();
				var provinceSpan = $(opts.container).find(".city_container").find(".province_con>span").length;
				if(provinceSpan == 0) {
					return;
				}
				var tnow=$(opts.container).find(".city_container").css("display");
				if(tnow==="none"){
                    _this.showPopup();
                    return false;
				}
			

			});


            $(opts.container).on('click','.city_con span',function () {
                    _this.hidePopup();
            });

          $('body').on('click',function (e) {
                var target = $(e.target);
                if(!target.is('.city_container *')) {
                    _this.hidePopup();
                }
            });

			// 省份点击后
			$(opts.container).find('.province_con>span').on('click',function () {
				var province = $(this).text();
				$(this).addClass('span_active').siblings().removeClass('span_active');
				for(var i in opts.cityDatas){
					if( province == i ){
						_this.renderCity(opts.cityDatas[i]);
					}
				}
			});
		},
		// html渲染省份
		renderProvince : function(){
			var html = '',
				provinceStr = '';
			// 循环省份
			for(var i in opts.cityDatas){
				provinceStr += '<span data-id="'+opts.cityDatas[i].id+'" data-markid="'+opts.cityDatas[i].markid+'">'+i+'</span>';
			}
			html  = '<div class="city_container">'
				  +	'<div class="province_con">'
				  + provinceStr
				  +	'</div>'
				  +	'<div class="city_con">'
				  +	'</div>'
				  +	'</div>';
			$(opts.container).append(html);
		},
		// 渲染城市
		renderCity : function(cityObj){
			var _this = this;
			$(opts.container).find('.city_con').html('');
			var cityStr = '';
			for(var i=0; i<cityObj.citys.length; i++){
				cityStr += '<span data-id="'+cityObj.citys[i].id+'" data-markid="'+cityObj.citys[i].markid+'">'+cityObj.citys[i].text+'</span>';
			}
			$(opts.container).find('.city_con').append(cityStr);
			// 城市点击后
			$(opts.container).find('.city_con>span').on('click',function () {
				var backObj = {},
					provinceVal = {},
					cityArr = [],
					cityObj = {},
					province = '';
				cityObj['text'] = $(this).text();
				cityObj['id'] = $(this).data('id');
				cityObj['markid'] = $(this).data('markid');
				cityArr.push(cityObj);
				provinceVal['citys'] = cityArr;
				provinceVal['id'] = $(opts.container).find('.province_con>.span_active').data('id');
				provinceVal['markid'] = $(opts.container).find('.province_con>.span_active').data('markid');
				province = $(opts.container).find('.province_con>.span_active').text();
				backObj[province] = provinceVal;
				// _this.hidePopup();
				opts.callbackFn(backObj, $(this));
			});
		},
		// 显示
		showPopup : function(){
			$(opts.container).find('.city_container').show();
			// 城市容器自适应
			var provinceContainerWidth = $(opts.container).find('.province_con').width();
			$(opts.container).find('.city_con').css('left',provinceContainerWidth);
			// 下拉框自适应
			var inpWidth = $(opts.container).find('input').get(0).clientWidth;
			$(opts.container).find('.city_container').width(inpWidth);
			// 处理滑动到默认省份
			var temp = 0;
			if( opts.cityMarkid && this.provinceIndex>=7 ){
				temp = this.provinceIndex-6;
			}
			$(opts.container).find('.province_con').scrollTop( temp*30 );
		},
		// 关闭
		hidePopup : function(){
			$(opts.container).find('.city_container').hide();
		},
	}
	cityFn.init();
	return cityFn;
}
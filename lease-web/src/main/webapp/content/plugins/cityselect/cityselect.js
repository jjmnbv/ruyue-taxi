var Vcity = {};

/* 城市HTML模板 */
Vcity._template = [
    '<p class="tip">直接输入可搜索城市(支持汉字/拼音)</p>',
    '<ul>',
    '<li class="on">热门城市</li>',
    '<li>ABCDE</li>',
    '<li>FGHJ</li>',
    '<li>KLMNO</li>',
    '<li>PQRST</li>',
    '<li>WXYZ</li>',
    '</ul>'
]

/* *
 * 静态方法集
 * @name _m
 * */
Vcity._m = {
    /* 选择元素 */
    $:function (arg, context) {
        var tagAll, n, eles = [], i, sub = arg.substring(1);
        context = context || document;
        if (typeof arg == 'string') {
            switch (arg.charAt(0)) {
                case '#':
                    return document.getElementById(sub);
                    break;
                case '.':
                    if (context.getElementsByClassName) return context.getElementsByClassName(sub);
                    tagAll = Vcity._m.$('*', context);
                    n = tagAll.length;
                    for (i = 0; i < n; i++) {
                        if (tagAll[i].className.indexOf(sub) > -1) eles.push(tagAll[i]);
                    }
                    return eles;
                    break;
                default:
                    return context.getElementsByTagName(arg);
                    break;
            }
        }
    },

    /* 绑定事件 */
    on:function (node, type, handler) {
        node.addEventListener ? node.addEventListener(type, handler, false) : node.attachEvent('on' + type, handler);
    },

    /* 获取事件 */
    getEvent:function(event){
        return event || window.event;
    },

    /* 获取事件目标 */
    getTarget:function(event){
        return event.target || event.srcElement;
    },

    /* 获取元素位置 */
    getPos:function (node) {
        var scrollx = document.documentElement.scrollLeft || document.body.scrollLeft,
                scrollt = document.documentElement.scrollTop || document.body.scrollTop;
        var pos = node.getBoundingClientRect();
        return {top:pos.top + scrollt, right:pos.right + scrollx, bottom:pos.bottom + scrollt, left:pos.left + scrollx }
    },

    /* 添加样式名 */
    addClass:function (c, node) {
        if(!node)return;
        node.className = Vcity._m.hasClass(c,node) ? node.className : node.className + ' ' + c ;
    },

    /* 移除样式名 */
    removeClass:function (c, node) {
        var reg = new RegExp("(^|\\s+)" + c + "(\\s+|$)", "g");
        if(!Vcity._m.hasClass(c,node))return;
        node.className = reg.test(node.className) ? node.className.replace(reg, '') : node.className;
    },

    /* 是否含有CLASS */
    hasClass:function (c, node) {
        if(!node || !node.className)return false;
        return node.className.indexOf(c)>-1;
    },

    /* 阻止冒泡 */
    stopPropagation:function (event) {
        event = event || window.event;
        event.stopPropagation ? event.stopPropagation() : event.cancelBubble = true;
    },
    /* 去除两端空格 */
    trim:function (str) {
        return str.replace(/^\s+|\s+$/g,'');
    }
};

/* *
 * 城市控件构造函数
 * @CitySelector
 * */
Vcity.CitySelector = function () {
    this.initialize.apply(this, arguments);
};

Vcity.CitySelector.prototype = {
    constructor:Vcity.CitySelector,


    /* 初始化 */
    initialize :function (options) {
    	this.opt = {
    			/* 控件ID */
    			id:null,
    			/* ajax请求地址 */
    			url : null,
    			/* 所有城市数据,可以按照格式自行添加（List<PubCityAddr>对象） */
    			allCity : null,
    			/* 隐藏域对象 */
    			hidden : null,
    			/* 输入框对象 */
    			input : null,
    			/* 默认选择城市 */
    			defaultCity : null,
    			/* 前n条为热门城市 */
    			hotCityCount : 16,
    			/* 是否需要搜索功能 */
    			needSearch : false,
    			/* 是否显示首字母 */
    			showInitcial : true,
    			/* 回调函数,注册后会回传被点击的PubCityAddr对象 */
    			fnCallBack : null,
    			/* 某些页面需要自动点击一次input框才能初始化 */
    			firstClick : true,
    			/* 城市列表DIV */
    			hotCityDiv : null,
    	};
    	$.extend(this.opt,options);
        this.opt.hidden = $('#'+options.id)[0];
        this.opt.input = $("<input>").attr("type","text").attr("name",$(this.opt.hidden).attr("name")).addClass("cityinput")[0];
        $(this.opt.input).css("width",$(this.opt.hidden).css("width"));
        $(this.opt.hidden).removeAttr("name");
        $(this.opt.hidden).after(this.opt.input);
    	this.getCityData();
    	if(null == this.opt.allCity || this.opt.allCity == "") {
    		$(this.opt.input).attr("readonly",true);
    		$(this.opt.hidden).val("");
			return;
    	}
    	this.convertCityData();
        this.inputEvent();
        if(this.opt.firstClick) $(this.opt.input).click();
    },

    changeCity : function(city){
    	var links = Vcity._m.$('a',this.opt.hotCityDiv);
    	for(var i in links){
    		if(city == links[i].innerHTML){
    			links[i].click();
    			break;
    		}
    	}	
    },
    
    getCityData:function(){
    	if(!this.opt.url) return;
    	var that = this;
    	$.ajax({
    		url : this.opt.url,
            async : false,
            success : function(result) {
    	        if(result.status == 0){
    	        	that.opt.allCity = result.cities;
    	       	}else{
    	       		that.opt.allCity = result;
    	       	}
    	        that.opt.input.value = that.opt.allCity[0].city;
    	        that.opt.hidden.value = that.opt.allCity[0].id;
            },
            error : function(msg) {
            }
    	});
    },
    
    /* *
     * 格式化城市数组为对象oCity，按照a-h,i-p,q-z,hot热门城市分组：
     * {HOT:{hot:[]},ABCDE:{a:[1,2,3],b:[1,2,3]},FGHIJ:{},KLMNO:{},PQRST:{},UVWXYZ:{}}
     * */
    convertCityData : function () {
//    	var patt = [/^A$/,/^B/,/^C$/,/^D$/,/^E$/,/^F$/,/^G$/,/^H$/,/^I$/,/^J$/,/^K$/,/^L$/,/^M$/,/^N$/,/^O$/,/^P$/,/^Q$/,/^R$/,/^S$/,/^T$/,/^U$/,/^V$/,/^W$/,/^X$/,/^Y$/,/^Z$/];
    	var patt = [/^[A-E]$/i,/^[F-J]$/i,/^[K-O]$/i,/^[P-T]$/i,/^[U-Z]$/i];
    	Vcity.oCity = {HOT:{hot:[]},ABCDE:{},FGHIJ:{},KLMNO:{},PQRST:{},UVWXYZ:{}};
//    	Vcity.oCity = {ABCDE:[],FGHIJ:[],KLMNO:[],PQRST:[],UVWXYZ:[]};
    	for ( var i in this.opt.allCity) {
    		var city =this.opt.allCity[i];
    		for ( var r in patt) {
    			var result = patt[r].test(city.cityInitials);
    			if (result) {
    				switch(parseInt(r)){
    				case 0:
    					if(!Vcity.oCity.ABCDE[city.cityInitials]) Vcity.oCity.ABCDE[city.cityInitials]=[];
    					Vcity.oCity.ABCDE[city.cityInitials].push(city);
    					break;
    				case 1:
    					if(!Vcity.oCity.FGHIJ[city.cityInitials]) Vcity.oCity.FGHIJ[city.cityInitials]=[];
    					Vcity.oCity.FGHIJ[city.cityInitials].push(city);
    					break;
    				case 2:
    					if(!Vcity.oCity.KLMNO[city.cityInitials]) Vcity.oCity.KLMNO[city.cityInitials]=[];
    					Vcity.oCity.KLMNO[city.cityInitials].push(city);
    					break;
    				case 3:
    					if(!Vcity.oCity.PQRST[city.cityInitials]) Vcity.oCity.PQRST[city.cityInitials]=[];
    					Vcity.oCity.PQRST[city.cityInitials].push(city);
    					break;
    				case 4:
    					if(!Vcity.oCity.UVWXYZ[city.cityInitials]) Vcity.oCity.UVWXYZ[city.cityInitials]=[];
    					Vcity.oCity.UVWXYZ[city.cityInitials].push(city);
    					break;
    				}
    			}
    		}
    	    /* 热门城市 */
    	    if(i<this.opt.hotCityCount){
    	        if(!Vcity.oCity.HOT['hot']) Vcity.oCity.HOT['hot'] = [];
    	        Vcity.oCity.HOT['hot'].push(city);
    	    }
    	}
    },
    
    /* *
     * @createWarp
     * 创建城市BOX HTML 框架
     * */
    createWarp:function(){
        var inputPos = Vcity._m.getPos(this.opt.input);
        var div = this.rootDiv = document.createElement('div');
        var that = this;

        // 设置DIV阻止冒泡
        Vcity._m.on(this.rootDiv,'click',function(event){
            Vcity._m.stopPropagation(event);
        });

        // 设置点击文档隐藏弹出的城市选择框
        Vcity._m.on(document, 'click', function (event) {
            event = Vcity._m.getEvent(event);
            var target = Vcity._m.getTarget(event);
            if(target == that.opt.input) return false;
            //console.log(target.className);
            if (that.cityBox)Vcity._m.addClass('hide', that.cityBox);
            if (that.ul)Vcity._m.addClass('hide', that.ul);
            if(that.myIframe)Vcity._m.addClass('hide',that.myIframe);
        });
        div.className = 'citySelector';
        div.style.position = 'absolute';
        div.style.left = inputPos.left + 'px';
        div.style.top = inputPos.bottom + 5 + 'px';
        div.style.zIndex = 999999;

        // 判断是否IE6，如果是IE6需要添加iframe才能遮住SELECT框
        var isIe = (document.all) ? true : false;
        var isIE6 = this.isIE6 = isIe && !window.XMLHttpRequest;
        if(isIE6){
            var myIframe = this.myIframe =  document.createElement('iframe');
            myIframe.frameborder = '0';
            myIframe.src = 'about:blank';
            myIframe.style.position = 'absolute';
            myIframe.style.zIndex = '-1';
            this.rootDiv.appendChild(this.myIframe);
        }

        var childdiv = this.cityBox = document.createElement('div');
        childdiv.className = 'cityBox';
        childdiv.id = 'cityBox';
        childdiv.innerHTML = Vcity._template.join('');
        var hotCity = this.opt.hotCityDiv = this.hotCity =  document.createElement('div');
        hotCity.className = 'hotCity';
        childdiv.appendChild(hotCity);
        div.appendChild(childdiv);
        if(!that.opt.needSearch) $(childdiv).find("p").addClass("hide");
        this.createHotCity();
    },

    /* *
     * @createHotCity
     * TAB下面DIV：hot,a-h,i-p,q-z 分类HTML生成，DOM操作
     * oCity = {HOT:{hot:[1,2,3]},ABCDE:{a:[1,2,3],b:[1,2,3]},FGHIJ:{},KLMNO:{},PQRST:{},UVWXYZ:{}}
     * initcialList = {'hot'} | {'A','B'...}
     **/
    createHotCity:function(){
        var odiv,odl,odt,odd,odda=[],cityTag,oCity = Vcity.oCity;
        for(var group in oCity){
        	odiv =  $('<div>').addClass(group + ' cityTab hide');
        	var initcialList=[];
            for(var initcial in oCity[group]){
            	initcialList.push(initcial);
            }
            // initcialList按照ABCDEDG顺序排序
            initcialList.sort();
            if(this.opt.showInitcial){
                for(var initcial in initcialList){
                	odl = $('<dl>');
                    odt = $('<dt>');
                    odd = $('<dd>');
                    odt.html(initcialList[initcial] == 'hot'?'&nbsp;':initcialList[initcial]);
                    odda = [];
                    var cities = oCity[group][initcialList[initcial]];
                    for(var i in cities){
                    	cityTag = '<a href="javascript:void(0);">' + cities[i].city + '</a>';
                        odda.push(cityTag);
                    }
                    odd.html(odda.join(''));
                    odl.append(odt);
                    odl.append(odd);
                    odiv.append(odl);
                }
            }else{
            	odl = $('<dl>');
            	odd = $('<dd>');
            	odda = [];
                for(var initcial in initcialList){
                    var cities = oCity[group][initcialList[initcial]];
                    for(var i in cities){
                    	cityTag = '<a href="javascript:void(0);">' + cities[i].city + '</a>';
                        odda.push(cityTag);
                    }
                }
                odd.html(odda.join(''));
                odl.append(odd);
                odiv.append(odl);
            }
            if(group == 'HOT'){
                // 移除热门城市的隐藏CSS
                Vcity._m.removeClass('hide',odiv[0]);
            }
            this.hotCity.appendChild(odiv[0]);
        }
        document.body.appendChild(this.rootDiv);
        /* IE6 */
        this.changeIframe();

        this.tabChange();
        this.linkEvent();
    },

    /* *
     *  tab按字母顺序切换
     *  @ tabChange
     * */
    tabChange:function(){
        var lis = Vcity._m.$('li',this.cityBox);
        var divs = Vcity._m.$('div',this.hotCity);
        var that = this;
        for(var i=0,n=lis.length;i<n;i++){
            lis[i].index = i;
            lis[i].onclick = function(){
                for(var j=0;j<n;j++){
                    Vcity._m.removeClass('on',lis[j]);
                    Vcity._m.addClass('hide',divs[j]);
                }
                Vcity._m.addClass('on',this);
                Vcity._m.removeClass('hide',divs[this.index]);
                /* IE6 改变TAB的时候 改变Iframe 大小*/
                that.changeIframe();
            };
        }
    },

    /* *
     * 城市LINK事件
     *  @linkEvent
     * */
    linkEvent:function(){
        var links = Vcity._m.$('a',this.hotCity);
        var that = this;
        var city;
        for(var i=0,n=links.length;i<n;i++){
            links[i].onclick = function(){
                Vcity._m.addClass('hide',that.cityBox);
                /* 点击城市名的时候隐藏myIframe */
                Vcity._m.addClass('hide',that.myIframe);
            	for(var i in that.opt.allCity){
            		if(that.opt.allCity[i].city == this.innerHTML){
            			city = that.opt.allCity[i];
            			break;
            		}
            	}
                that.opt.input.value = city.city;
                that.opt.hidden.value = city.id;
                /* 触发回调事件 */
                if(that.opt.fnCallBack) that.opt.fnCallBack(city);
            }
        }
        //选择默认城市
        if(that.opt.defaultCity){
        	for(var i in links){
        		if(that.opt.defaultCity == links[i].innerHTML){
        			links[i].click();
        			break;
        		}
        	}
        }else{
            //将第一个城市点击一次
        	if(links.length > 0) {
        		links[0].click();
        	}
        }
    },

    /* *
     * INPUT城市输入框事件
     * @inputEvent
     * */
    inputEvent:function(){
        var that = this;
        Vcity._m.on(this.opt.input,'click',function(event){
            event = event || window.event;
            if(!that.cityBox){
                that.createWarp();
            }else if(!!that.cityBox && Vcity._m.hasClass('hide',that.cityBox)){
                // slideul 不存在或者 slideul存在但是是隐藏的时候 两者不能共存
                if(!that.ul || (that.ul && Vcity._m.hasClass('hide',that.ul))){
                    Vcity._m.removeClass('hide',that.cityBox);

                    /* IE6 移除iframe 的hide 样式 */
                    //alert('click');
                    Vcity._m.removeClass('hide',that.myIframe);
                    that.changeIframe();
                }
            }
        });
        Vcity._m.on(this.opt.input,'blur',function(){
            var value = Vcity._m.trim(that.opt.input.value);
            if(value != ''){
                var reg = new RegExp('^' + value, 'i');
                var flag=0;
                if(null != that.opt.allCity) {
                	for (var i = 0, n = that.opt.allCity.length; i < n; i++) {
                        if (reg.test(that.opt.allCity[i]).city || reg.test(that.opt.allCity[i]).cityInitials) {
                            flag++;
                        }
                    }
                }
                if(flag==0){
//                	that.opt.input.value= '';
                }else{
                    var lis = Vcity._m.$('li',that.ul);
                    if(typeof lis == 'object' && lis['length'] > 0){
                        var li = lis[0];
                        var bs = li.children;
                        if(bs && bs['length'] > 1){
                        	that.opt.input.value = bs[0].innerHTML;
                        }
                    }else{
//                    	that.opt.input.value = '';
                    }
                }
            }

        });
		//如果不需要搜索则不注册搜索事件
		if(!that.opt.needSearch) {
			$(that.opt.input).attr("readonly",true);
			return;
		}
		//注册input输入时的搜索事件
        Vcity._m.on(this.opt.input,'keyup',function(event){
            event = event || window.event;
            var keycode = event.keyCode;
            Vcity._m.addClass('hide',that.cityBox);
            that.createUl();

            /* 移除iframe 的hide 样式 */
            Vcity._m.removeClass('hide',that.myIframe);
        });
    },

    /* *
     * 生成下拉选择列表
     * @ createUl
     * */
    createUl:function () {
        //console.log('createUL');
        var str;
        var value = Vcity._m.trim(this.opt.input.value);
        // 当value不等于空的时候执行
        if (value !== '') {
            var reg = new RegExp('^' + value, 'i');
            // 此处需设置中文输入法也可用onpropertychange
            var searchResult = [];
            for (var i = 0, n = this.opt.allCity.length; i < n; i++) {
                if (reg.test(this.opt.allCity[i]).city || reg.test(this.opt.allCity[i]).cityInitials) {
                	var cityname = reg.exec(this.opt.allCity[i].city);
                    var cityspell = reg.exec(this.opt.allCity[i].cityInitials);
                    if (searchResult.length !== 0) {
                        str = '<li><b class="cityname">' + cityname[1] + '</b><b class="cityspell">' + cityspell[1] + '</b></li>';
                    } else {
                        str = '<li class="on"><b class="cityname">' + cityname[1] + '</b><b class="cityspell">' + cityspell[1] + '</b></li>';
                    }
                    searchResult.push(str);
                }
            }
            this.isEmpty = false;
            // 如果搜索数据为空
            if (searchResult.length == 0) {
                this.isEmpty = true;
                str = '<li class="empty">对不起，没有找到 "<em>' + value + '</em>"</li>';
                searchResult.push(str);
            }
            // 如果slideul不存在则添加ul
            if (!this.ul) {
                var ul = this.ul = document.createElement('ul');
                ul.className = 'cityslide mCustomScrollbar';
                this.rootDiv && this.rootDiv.appendChild(ul);
                // 记录按键次数，方向键
                this.count = 0;
            } else if (this.ul && Vcity._m.hasClass('hide', this.ul)) {
                this.count = 0;
                Vcity._m.removeClass('hide', this.ul);
            }
            this.ul.innerHTML = searchResult.join('');

            /* IE6 */
            this.changeIframe();

            // 绑定Li事件
            this.liEvent();
        }else{
            Vcity._m.addClass('hide',this.ul);
            Vcity._m.removeClass('hide',this.cityBox);

            Vcity._m.removeClass('hide',this.myIframe);

            this.changeIframe();
        }
    },

    /* IE6的改变遮罩SELECT 的 IFRAME尺寸大小 */
    changeIframe:function(){
        if(!this.isIE6)return;
        this.myIframe.style.width = this.rootDiv.offsetWidth + 'px';
        this.myIframe.style.height = this.rootDiv.offsetHeight + 'px';
    },

    /* *
     * 下拉列表的li事件
     * @ liEvent
     * */
    liEvent:function(){
        var that = this;
        var lis = Vcity._m.$('li',this.ul);
        var city;
        for(var i = 0,n = lis.length;i < n;i++){
            Vcity._m.on(lis[i],'click',function(event){ 
                Vcity._m.addClass('hide',that.ul);
                /* IE6 下拉菜单点击事件 */
                Vcity._m.addClass('hide',that.myIframe);
            	for(var i in this.opt.allCity){
            		if(that.opt.allCity[i].city == this.innerHTML){
            			city = that.opt.allCity[i];
            			break;
            		}
            	}
                event = Vcity._m.getEvent(event);
                that.opt.input.value = city.city;
                that.opt.hidden.value = city.id;
                /* 触发回调事件 */
                if(that.opt.fnCallBack) that.opt.fnCallBack(city);
            });
            Vcity._m.on(lis[i],'mouseover',function(event){
                event = Vcity._m.getEvent(event);
                var target = Vcity._m.getTarget(event);
                Vcity._m.addClass('on',target);
            });
            Vcity._m.on(lis[i],'mouseout',function(event){
                event = Vcity._m.getEvent(event);
                var target = Vcity._m.getTarget(event);
                Vcity._m.removeClass('on',target);
            })
        }
    }
};	

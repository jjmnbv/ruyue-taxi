//兼容ie6的fixed代码 
//jQuery(function($j){
//	$j('#pop').positionFixed()
//})
(function($j){
    $j.positionFixed = function(el){
        $j(el).each(function(){
            new fixed(this)
        })
        return el;                  
    }
    $j.fn.positionFixed = function(){
        return $j.positionFixed(this)
    }
    var fixed = $j.positionFixed.impl = function(el){
        var o=this;
        o.sts={
            target : $j(el).css('position','fixed'),
            container : $j(window)
        }
        o.sts.currentCss = {
            top : o.sts.target.css('top'),              
            right : o.sts.target.css('right'),              
            bottom : o.sts.target.css('bottom'),                
            left : o.sts.target.css('left')             
        }
        if(!o.ie6)return;
        o.bindEvent();
    }
    $j.extend(fixed.prototype,{
        ie6 : $.browser.msie && $.browser.version < 7.0,
        bindEvent : function(){
            var o=this;
            o.sts.target.css('position','absolute')
            o.overRelative().initBasePos();
            o.sts.target.css(o.sts.basePos)
            o.sts.container.scroll(o.scrollEvent()).resize(o.resizeEvent());
            o.setPos();
        },
        overRelative : function(){
            var o=this;
            var relative = o.sts.target.parents().filter(function(){
                if($j(this).css('position')=='relative')return this;
            })
            if(relative.size()>0)relative.after(o.sts.target)
            return o;
        },
        initBasePos : function(){
            var o=this;
            o.sts.basePos = {
                top: o.sts.target.offset().top - (o.sts.currentCss.top=='auto'?o.sts.container.scrollTop():0),
                left: o.sts.target.offset().left - (o.sts.currentCss.left=='auto'?o.sts.container.scrollLeft():0)
            }
            return o;
        },
        setPos : function(){
            var o=this;
            o.sts.target.css({
                top: o.sts.container.scrollTop() + o.sts.basePos.top,
                left: o.sts.container.scrollLeft() + o.sts.basePos.left
            })
        },
        scrollEvent : function(){
            var o=this;
            return function(){
                o.setPos();
            }
        },
        resizeEvent : function(){
            var o=this;
            return function(){
                setTimeout(function(){
                    o.sts.target.css(o.sts.currentCss)      
                    o.initBasePos();
                    o.setPos()
                },1)    
            }           
        }
    })
})(jQuery)

jQuery(function($j){
	$j('#footer').positionFixed()
})

//pop右下角弹窗函数
//作者：yanue
function Pop(title,url,intro,id,mark,outtime){
    this.id = id;
    this.mark = mark;
    this.title=title;
    this.method=url;
    this.intro=intro;
    this.apearTime=1000;
    this.hideTime=500;
    this.delay=10000;
    this.outTime = (outtime)?outtime:5000;
	//添加信息
    this.innerHTML = function(){

    };
	//显示
    this.showDiv = function () {
        var html = "<div id='"+this.id+"' class=\"pop\" style=\"display:none;\">	<div class=\"popHead\">	<a class=\"popClose\" title=\"关闭\">关闭</a>	</div>	<div class=\"popContent\">	<dl>		<dt class=\"popTitle\"><a target=\"_blank\">" + this.title + "</a></dt>		<dd class=\"popIntro\">" + this.intro + "</dd>	</dl>	<div class=\"w400\" style='text-align: right' ><button  "+((this.mark==true)?"":"style='display:none';")+" class=\"Sbtn green_a\" onclick='"+this.method+";'  >人工处理</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button class=\"Sbtn red\" onclick='closePop(\""+this.id+"\")' >我知道了</button></div>	</div></div>";
        this.dom = $(html);
        $("body").append(this.dom);
        this.dom.slideDown(this.apearTime).delay(this.delay).fadeOut(this.outTime);

    };
	//关闭
    this.closeDiv = function () {
        var self = this;
        self.dom.find(".popClose").click(function(){
                self.dom.remove();
            }
        );
    };
    //关闭
    this.remove = function () {
        this.dom.remove();
    };
}
function closePop(id){
    $('#'+id).remove();
}
Pop.prototype={
  addInfo:function(){
    // $("#popTitle a").attr('href',this.url).html(this.title);
    // $("#popIntro").html(this.intro);
    // $("#popMore a").attr('href',this.url);
  },
  showDiv:function(){

  },
  closeDiv:function(){
	  var self = this;
  	self.dom.find(".popClose").click(function(){
  		  self.dom.remove();
  		}
    );
  }
}

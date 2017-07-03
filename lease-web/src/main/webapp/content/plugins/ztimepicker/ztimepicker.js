 $(document).ready(function() {
        //通用函数
        var F = {
            //计算某年某月有多少天
            getDaysInMonth : function(year, month){
                return new Date(year, month+1, 0).getDate();
            },
            //计算年某月的最后一天日期
            getLastDayInMonth : function(year, month){
                return new Date(year, month, this.getDaysInMonth(year, month));
            },
            //小于10的数字前加0
            preZero : function(num){
                num = parseInt(num);
                if(num<10){
                    return '0'+num;
                }
                else{
                    return num;
                }
            },
            //当前分转换为10分制
            minT : function minT(mi){
                var mi=(parseInt(dMinute/10)+1)*10;
                return mi;
            },
            formatDate : function(year, month, day, hour, minute, week){
                month = F.preZero(month+1);
                day = F.preZero(day);
                hour = F.preZero(hour);
                minute = F.preZero(minute);
                var result = '';
                    result += year+'-'+month+'-'+day+' '+week;
                    if(option.mode!=1 && hour){
                        result += ' '+hour+':'+minute+'';
                        if(option.mode!=2){
                            if(!isNaN(parseInt(second))){
                                result += ':'+second+'';
                            }
                            else{
                                result += ' '+second;
                            }
                        }
                    }

                return result;
            }
        }
        
        
        Tvar();
        //时间不断刷新
        function Tvar(){
    	   defaultDate = new Date(),
           dYear = defaultDate.getFullYear(),
           dMonth = defaultDate.getMonth(),
           dDate = defaultDate.getDate(),
           dHour = defaultDate.getHours(),
           //dHour = 23,
           dMinute = defaultDate.getMinutes(),
           //dMinute = 50,
           dSecond = defaultDate.getSeconds(),
           dweek = defaultDate.getDay();
        }

        //输入框默认显示当前时间值
        nowtime(0,1);
        function nowtime(x,from){
        	var tMinute=F.minT(tMinute);
        	var tHour=dHour;
        	if(dMinute>=50){var tMinute="00";var tHour=dHour+1;}
            var nowtime=dYear+"-"+F.preZero((dMonth+1))+"-"+F.preZero(dDate)+" "+F.preZero(tHour)+":"+tMinute+":00";
            if(from==1){
            	$(".ztimepicker_input").val(nowtime);}
            //点击现在用车，不同的输入不同的值
            if(from==0){
            	x.prev(".ztimepicker_input").val(nowtime);}
        };
        //点击现在、现在用车之类的执行值
        $(".znow").click(function () {
        	var x=$(this).parent(".ztimebox");
        	Tvar();
            nowtime(x,0);
        });


        //日期计算       
        day();
        function day(n){
	        var dayStr = '';
	        var defaultDays = F.getDaysInMonth(dYear, dMonth);
	        var aday=dDate+7;
	        //判断加7天之后是否跨月,如果不跨月
	        if(aday<=defaultDays){
	        	 for(var k=dDate; k<aday; k++){
	               var sel = k==dDate ? 'selected' : '';
	               dayStr += '<li class="'+sel+'"  data-month="'+dMonth+'" data-year="'+dYear+'" data-day="'+k+'" > '+(dMonth+1)+'-'+F.preZero(k)+'</li>';
	          }
	       	}
	        //如果跨月
	        if(aday>defaultDays){
	        	 for(var k=dDate; k<=defaultDays; k++){
	                 var sel = k==dDate ? 'selected' : '';
	                 dayStr += '<li class="'+sel+'"  data-month="'+dMonth+'" data-year="'+dYear+'" data-day="'+k+'"> '+(dMonth+1)+'-'+F.preZero(k)+'</li>';
	             }
	        	 //若是12月需要跨年
	        	 if(dMonth!==11){
	        		 for(var g=1; g<aday-defaultDays; g++){
	   	                dayStr += '<li class="" data-month="'+(dMonth+1)+'" data-year="'+dYear+'" data-day="'+g+'" > '+(dMonth+2)+'-'+F.preZero(g)+'</li>';
	   	             }
	        	 }else{
	        		 for(var g=1; g<aday-defaultDays; g++){
	   	                dayStr += '<li class="" data-month="0" data-year="'+(dYear+1)+'"  data-day="'+g+'" > 01-'+F.preZero(g)+'</li>';
	   	             }
	        	 }
	        }
	
	        $(".zday").html(dayStr);
	        $(".zday").find("li:eq(0)").prepend("今天 ");
	        $(".zday").find("li:eq(1)").prepend("明天 ");
	        $(".zday").find("li:eq(2)").prepend("后天 ");
	        //如果当前时间属于下面这个时间段，就去掉今天，直接显示明天
	      };

        //小时计算
        function fullhour(hour,n){
            var hourStr = '';
            for(var l= hour; l<24; l++){
                var sel = l==hour ? 'selected' : '';
                hourStr += '<li class="'+sel+'" data-hour="'+l+'">'+F.preZero(l)+'时</li>';
            }
            $(".zhour").eq(n).html(hourStr);
            return hourStr;
    	};



        //分钟计算
        function minute(mi,n){
            var minuteStr = '';
            for(var m=mi; m<60; m=10+m){
                var sel = m==mi ? 'selected' : '';
                minuteStr += '<li class="'+sel+'"  data-minute="'+m+'">'+F.preZero(m)+'分</li>';
            }
            $(".zmin").eq(n).html(minuteStr);
            return minuteStr;
        };



        //小时 与分钟 显示逻辑及初始数据
        function int(n){
	       if(dMinute<50){
	           var  mi=F.minT(dMinute);
	           minute(mi,n);
	           fullhour(dHour,n);
	       }
	       if(dMinute>=50){
	            minute(0,n);        
	            if(dHour==23){
	     		   if(dDate==F.getDaysInMonth(dYear, dMonth)){
	     			   dDate=1;
	     		   }else{
	     			   dDate=dDate+1;
	         		   day(n);
	         		   fullhour(0,n);   
	     		   }
	       }else{
     		   fullhour(dHour+1,n);
     		   }
	       }
        }

       //点击天数执行
        $(".zday>li").click(function () {
            $(this).addClass("selected").siblings().removeClass("selected");
        	var n=$(this).closest(".ztimepicker").index(".ztimepicker");
            var dn=$(this).index();
            if(dn==0){//点今天
                //如果处于当前小时
                fullhour(dHour,n);
                if($(".zhour>li:eq(0)").attr("class")=="selected"){
                    if(dMinute<50){
                        var  mi=F.minT(dMinute);
                        minute(mi,n);
                    }
                    if(dMinute>=50){
                        minute(0,n);
                        fullhour(dHour+1,n);
                    }
                }else{//如果不在当前小时
                    if( $(".zmin>li").length!==6){
                    minute(0,n);
                    }
                }

            }else{//非今天
                if( $(".zmin>li").length!==6){ minute(0,n);}
                if( $(".zhour>li").length!==24){fullhour(0,n)}
            }
        });

        //点击小时执行
        $(".zhour").on('click','li', function () {
            $(this).addClass("selected").siblings().removeClass("selected");
        	var n=$(this).closest(".ztimepicker").index(".ztimepicker");
            var hn=$(this).index();
            if(hn!==0){//不是当前小时
                if( $(".zmin>li").length!==6){
                    minute(0,n);
                }
            }else{
                if( $(".zday>li:eq(0)").attr("class")=="selected"){
                    if(dMinute<50){
                        var  mi=F.minT(dMinute);
                        minute(mi,n);
                      }
                    if(dMinute>=50){
                        minute(0,n);
                        fullhour(dHour+1,n);
                    }
                }
            }
        });

      //按产品指示，点击分得出最终结果
        $(".zmin").on('click','li', function () {
           	var n=$(this).closest(".ztimepicker").index(".ztimepicker");
            $(this).addClass("selected").siblings().removeClass("selected");
            var rhour= F.preZero( $(".zhour:eq("+n+") .selected").attr("data-hour"));
            var rmin= F.preZero($(".zmin:eq("+n+") .selected").attr("data-minute"));
            var ryear=$(".zday:eq("+n+") .selected").attr("data-year");
            var m=parseInt($(".zday:eq("+n+") .selected").attr("data-month"))+1;
            var rmon= F.preZero(m);
            var rday= F.preZero( $(".zday:eq("+n+") .selected").attr("data-day"));
            var result=ryear+"-"+rmon+"-"+rday+" "+rhour+":"+rmin+":00";
            $(".ztimepicker_input").eq(n).val(result);
            $(".ztimebox").eq(n).slideUp();
        });

        //弹窗操作逻辑
        $(".ztimepicker_input").blur(function(){
        	var n=$(this).parent(".ztimepicker").index(".ztimepicker");
            Tvar();
            int(n);
            $(".ztimebox").eq(n).slideUp(500);
        });
        $(".ztimepicker_input").focus(function(){
        	var n=$(this).parent(".ztimepicker").index(".ztimepicker");
            Tvar();
            int(n);
            $(".ztimebox").eq(n).slideDown(500);
        });
        $(".ztimepicker_input").click(function(){
        	$(this).focus();
        });
        $(".ztimebox").mousedown(function () {
        	return false;
        });
    })
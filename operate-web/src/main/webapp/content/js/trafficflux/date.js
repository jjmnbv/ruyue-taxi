Date.prototype.format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
var _Date=function(){
    return {
        //两个日期相减的天数
        getDays: function (day1, day2)
        {
            var arrDate, objDate1, objDate2, intDays;
            objDate1 = new Date();
            objDate2 = new Date();

            arrDate = day1.split("-");
            objDate1.setFullYear(arrDate[0], arrDate[1], arrDate[2]);
            
            arrDate = day2.split("-");
            objDate2.setFullYear(arrDate[0], arrDate[1], arrDate[2]);

            intDays = parseInt(Math.abs(objDate2 - objDate1) / 1000 / 60 / 60 / 24);
            return intDays+1;
        },
        getDaysq: function (startDate, endDate) {

            var mmSec = ((new Date(endDate).getTime() -((new Date( startDate)).getTime()))); //得到时间戳相减 得到以毫秒为单位的差    
            return (mmSec / 3600000 / 24)+1; //单位转换为天并返回    
        }
    }
}();


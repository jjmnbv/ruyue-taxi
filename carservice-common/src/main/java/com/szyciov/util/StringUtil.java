package com.szyciov.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.entity.OrderCost;

import net.sf.json.JSONObject;

public class StringUtil {
	/**
	 * 全大写
	 */
	public static int UPPERCASE = 0;
	/**
	 * 全小写
	 */
	public static int LOWERCASE = 1;
	/**
	 * 全数字
	 */
	public static int ONLYNUM = 2;
	/**
	 * 混合大小写
	 */
	public static int MIXCASE = 3;
	/**
	 * 大写字母含数字
	 */
	public static int UPPERCASE_HASNUM = 4;
	/**
	 * 小写字母含数字
	 */
	public static int LOWERCASE_HASNUM = 5;
	/**
	 * 混合大小写含数字
	 */
	public static int HASNUM = 6;
	/**
	 * 格式化时间到分钟
	 */
	public static String TIME_WITH_MINUTE = "yyyy-MM-dd HH:mm";
	/**
	 * 格式化时间到天
	 */
	public static String TIME_WITH_DAY = "yyyy-MM-dd";
	/**
	 * 格式化时间无-符号
	 */
	public static String TIME_WITH_DAY_NOSYMBOL = "yyyyMMdd";
	/**
	 * 格式化时间只要小时分钟
	 */
	public static String TIME_ONLY_HOUR_MINUTE = "HH:mm";

    /**
     * 格式化时间到秒
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String NOMAL_DATE_TIME_= "yyyy/MM/dd HH:mm";
	
	/**
	 * 生成随机字符
	 * @param len 长度
	 * @param strCase 模式
	 * @return
	 */
	public static String getRandomStr(int len,int strCase){
		String upStr = "QWERTYUIOPASDFGHJKLZXCVBNM";
		String lowStr = "qwertyuiopasdfghjklzxcvbnm";
		String numStr = "0123456789";
		StringBuffer sb = new StringBuffer();
		String ss = UPPERCASE == strCase ? upStr :
							LOWERCASE == strCase ? lowStr :
							ONLYNUM == strCase ? numStr :
							MIXCASE == strCase ? upStr + lowStr :
							UPPERCASE_HASNUM == strCase ? upStr + numStr :
							LOWERCASE_HASNUM == strCase ?  lowStr + numStr :
							HASNUM == strCase ? upStr + lowStr + numStr : upStr;
		for(int i=0;i<len;i++){
			sb.append(ss.charAt((int)(Math.random()*ss.length())));
		}
		return sb.toString();
	}
	
	/**
	 * 生成随机数字
	 * @param len 长度
	 * @return
	 */
	public static String getRandomNum(int len){
		String nums = "1234567890";
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<len;i++){
			sb.append(nums.charAt((int)(Math.random()*nums.length())));
		}
		return sb.toString();
	}
	
	/**
	 * 将字符串首字母大写
	 * @param str
	 * @return
	 */
    public static String upperHeader(String str){
    	str = str.substring(0,1).toUpperCase()+str.substring(1);
    	StringBuffer sb = new StringBuffer();
    	Pattern p = Pattern.compile("_(\\w{1})");
    	Matcher m = p.matcher(str);
    	while (m.find()){ 
	    	m.appendReplacement(sb, m.group(1).toUpperCase());
    	}
    	m.appendTail(sb);
    	return sb.toString();
    }
	
	/**
	 * 将花费时长格式化(75分钟=1小时15分钟)
	 * @param minutes 分钟数
	 * @return
	 */
	public static String formatCostTime(int minutes){
		long minTime    = 1000*60;
		long hourTime   = minTime*60;
		long dayTime     = hourTime*24;
		long localeTime = hourTime*8;  //时差
		long costTime    = minTime*minutes;
		String minStr   = "分钟";
		String hourStr = "小时";
		String dayStr   = "天";
		Date temp = new Date(costTime-localeTime);
		String format = "m"+minStr;
		if(costTime >= dayTime){
			temp = new Date(costTime-localeTime-dayTime);
			if(costTime%dayTime >= hourTime){
				format = "D"+dayStr+"H"+hourStr+"m"+minStr;
			}else{
				format = "D"+dayStr+"m"+minStr;
			}
		}else if(costTime >= hourTime){
			format = "H"+hourStr+"m"+minStr;
		}
		return formatDate(temp,format);
	}
	
	/**
	 * 将花费时长格式化(75分钟=1小时15分钟)
	 * @param second 秒
	 * @return
	 */
	public static String formatCostTimeInSecond(int second){
		//向上取整
		second = second % 60 > 0 ? second + 60 : second;
		int minutes = second / 60;
		return formatCostTime(minutes);
	}
	
	/**
	 * 格式化订单状态
	 * @param useTime
	 * @return
	 */
	public static String formatOrderStatus(Date useTime,String orderStatus){
		//如果是司机已出发,已抵达,服务中 显示行程中
		if(OrderState.START.state.equals(orderStatus) || 
			OrderState.ARRIVAL.state.equals(orderStatus) ||
			OrderState.INSERVICE.state.equals(orderStatus)
			){
			return "行程中";
		//如果是待接单,待出发,显示剩余时间,超过三小时显示待出发
		} else if(OrderState.WAITTAKE.state.equals(orderStatus) || 
					 OrderState.WAITSTART.state.equals(orderStatus)){
			String str = "剩余";
			//如果用车时间已经小于当前时间,返回空字符串
			if(useTime.before(new Date())) return "";
			long oneHour = 3600*1000;
			long twoHour = oneHour * 2;
			long threeHour = oneHour * 3;
			long lastTime = useTime.getTime() - System.currentTimeMillis();
			str = lastTime <= oneHour ? str +(formatCostTimeInSecond((int)(lastTime/1000))) : 
					lastTime <= twoHour ? str + "约2小时" :
					lastTime <= threeHour ? str + "约3小时" : "待出发";
			return str;
		//如果是待确费显示待确费
		} else if (OrderState.WAITMONEY.state.equals(orderStatus)) {
			return "待确费";
		//如果订单已取消,显示已取消
		} else if (OrderState.CANCEL.state.equals(orderStatus)) {
			return "已取消";
		//其他显示已完成
		} else{
			return "已完成";
		}
		
	}
	
	/**
	 * 格式化订单支付状态
	 * @param payStatus 支付状态
	 * @param orderStatus 订单状态
	 * @return
	 */
	public static String formatPayStatus(String payStatus,String orderStatus){
		String str = "";
		PayState payState = PayState.getPayStateByDB(payStatus);
		str = OrderState.CANCEL.state.equals(orderStatus) ? "已取消" :     //如果订单已取消,显示已取消
				PayState.MENTED.equals(payState) ? "" :                                //未结算订单在司机端不显示状态
				PayState.STATEMENTING.equals(payState) ? "" :                   //结算中订单在司机端不显示状态
				PayState.STATEMENTED.equals(payState) ? "" :                      //已结算订单在司机端不显示状态
				PayState.PASSENGERNOPAY.equals(payState) ? "已支付" :   //未付结订单在司机端显示已支付
				PayState.PAYOVER.equals(payState) ? "已支付" :                    //已付结订单在司机端显示已支付
				PayState.getPayStateByDB(payStatus).msg;                            //否则显示对应值
		return str;
	}
	
	/**
	 * 格式化出租车订单支付状态
	 * @param payStatus
	 * @param orderStatus
	 * @return
	 */
	public static String formatTaxiPayStatus(String payStatus,String orderStatus){
		String str = "";
		PayState payState = PayState.getPayStateByDB(payStatus);
		str = OrderState.CANCEL.state.equals(orderStatus) ? "已取消" :     //如果订单已取消,显示已取消
				!OrderState.SERVICEDONE.state.equals(orderStatus) ? "未支付" : 
				PayState.NOTPAY.equals(payState) ? "未支付" : "已支付" ;     //不为未支付的订单都显示已支付
//				PayState.MENTED.equals(payState) ? "已支付" :                                //未结算订单在司机端显示已支付
//				PayState.STATEMENTING.equals(payState) ? "已支付" :                   //结算中订单在司机端显示已支付
//				PayState.STATEMENTED.equals(payState) ? "已支付" :                      //已结算订单在司机端显示已支付
//				PayState.PASSENGERNOPAY.equals(payState) ? "已支付" :   //未付结订单在司机端显示已支付
//				PayState.PAYOVER.equals(payState) ? "已支付" :                    //已付结订单在司机端显示已支付
//				PayState.getPayStateByDB(payStatus).msg;                            //否则显示对应值
		return str;
	}
	
	/**
	 * 格式化日期
	 * @param src
	 * @param format
	 * @return
	 */
	public static String formatDate(Date src,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA);
		try {
			return sdf.format(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 时间字符串转换为Date
	 * @param src
	 * @param format
	 * @return
	 */
	public static Date parseDate(String src, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
		try {
			return sdf.parse(src);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 格式化小数
	 * @param src 
	 * @param len 要保留的位数(不能为0)
	 * @return
	 */
	public static double formatNum(double src,int len){
		StringBuffer sb = new StringBuffer();
		
		sb.append("#.");
		for(int i=0;i<len;i++){
			sb.append("0");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		double temp = Double.parseDouble(df.format(src));
		return temp;
	}
	
	/**
	 * 格式化计费时间类型
	 * @param oc
	 * @return
	 * @see {@linkplain OrderCost}
	 */
	public static String formatTimeType(OrderCost oc){
		String timetype;
		if(oc.getTimetype() == 0){
			timetype = "总用时收费";
		}else{
			timetype = "低速时长收费\n(低于@perhour公里/小时收费)".replace("@perhour", oc.getPerhour()+"");
		}
		return timetype;
	}
	
	/**
	 * 格式化数字,填充至指定长度
	 * @param num 数字
	 * @param len 长度
	 * @param sep 填充字符,默认0
	 * @return
	 */
	public static String formatNumToLength(int num,int len,String sep){
		if(null == sep) sep = "0";
		StringBuffer sb = new StringBuffer();
		String numStr = num+"";
		for(int i=numStr.length();i<len;i++){
			sb.append(sep);
		}
		sb.append(numStr);
		return sb.toString();
	}
	
	/**
	 * 将用车时间格式化为中文
	 * (今天显示今天,明天显示明天,后天显示后天,其他显示日期)
	 * @param usetime
	 * @return
	 */
	public static String formatUsetimeInChinese(Date usetime){
		Date today = new Date();
		Date beforeYesterday = getBeforeYesterday(today);
		Date yesterday = getYesterday(today);
		Date tomorrow = getTomorrow(today);
		Date afterTomorrow = getAfterTomorrow(today);
		int usetimeNum = Integer.valueOf(formatDate(usetime,TIME_WITH_DAY_NOSYMBOL));
		int beforeYesterdayNum = Integer.valueOf(formatDate(beforeYesterday,TIME_WITH_DAY_NOSYMBOL));
		int yesterdayNum = Integer.valueOf(formatDate(yesterday,TIME_WITH_DAY_NOSYMBOL));
		int todayNum = Integer.valueOf(formatDate(today,TIME_WITH_DAY_NOSYMBOL));
		int tomorrowNum = Integer.valueOf(formatDate(tomorrow, TIME_WITH_DAY_NOSYMBOL));
		int aftertomorrowNum = Integer.valueOf(formatDate(afterTomorrow, TIME_WITH_DAY_NOSYMBOL));
        if(usetimeNum == beforeYesterdayNum){  
            return "前天" + formatDate(usetime, TIME_ONLY_HOUR_MINUTE);
        }else if(usetimeNum == yesterdayNum){  
            return "昨天" + formatDate(usetime, TIME_ONLY_HOUR_MINUTE);
        }else if(usetimeNum == todayNum){  
            return "今天" + formatDate(usetime, TIME_ONLY_HOUR_MINUTE);
        }else if(usetimeNum == tomorrowNum){  
            return "明天" + formatDate(usetime, TIME_ONLY_HOUR_MINUTE);
        }else if(usetimeNum == aftertomorrowNum){  
            return "后天" + formatDate(usetime, TIME_ONLY_HOUR_MINUTE);
        }else{  
            return formatDate(usetime, TIME_WITH_MINUTE);
        }  
	}
	

    /** 
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前 
     *  
     * @param usetime
     * @return 
     */  
    public static String formatDateInChinese(Date usetime) {  
        long curTime =System.currentTimeMillis() / (long) 1000 ;  
        long time = curTime - usetime.getTime() / (long) 1000 ;  

        if (time < 60 && time >= 0) {  
            return "刚刚";  
        } else if (time >= 60 && time < 3600) {  
            return time / 60 + "分钟前";  
        } else if (time >= 3600 && time < 3600 * 24) {  
            return time / 3600 + "小时前";  
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {  
            return time / 3600 / 24 + "天前";  
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {  
            return time / 3600 / 24 / 30 + "个月前";  
        } else if (time >= 3600 * 24 * 30 * 12) {  
            return time / 3600 / 24 / 30 / 12 + "年前";  
        } else {  
            return "刚刚";  
        }  
    }
	
    /**
     * 获取前天(相对于传入时间的前二天)
     * @param when
     * @return
     */
    public static Date getBeforeYesterday(Date when){
    	when = when == null ? new Date() : when;
    	long oneday = 86400L * 1000 * 2;
    	Date beforeYesterday = new Date(when.getTime() - oneday);
    	return beforeYesterday;
    }
    
    /**
     * 获取昨天(相对于传入时间的前一天)
     * @param when
     * @return
     */
	public static Date getYesterday(Date when){
		when = when == null ? new Date() : when;
		long oneday = 86400L * 1000;
		Date yesterday = new Date(when.getTime() - oneday);
		return yesterday;
	}
	
	/**
	 * 获取今天(相对于传入时间的当天)
	 * @param when
	 * @return
	 */
	public static Date getToday(Date when){
		when = when == null ? new Date() : when;
		return parseDate(formatDate(when, TIME_WITH_DAY),TIME_WITH_DAY);
	}
	
	/**
	 * 获取明天(相对于传入时间的后一天)
	 * @param when
	 * @return
	 */
	public static Date getTomorrow(Date when){
		when = when == null ? new Date() : when;
		long oneday = 86400L * 1000;
		Date yesterday = new Date(when.getTime() + oneday);
		return yesterday;
	}
	
	/**
	 * 获取后天(相对于传入时间的后二天)
	 * @param when
	 * @return
	 */
	public static Date getAfterTomorrow(Date when){
		when = when == null ? new Date() : when;
		long oneday = 86400L * 1000;
		Date yesterday = new Date(when.getTime() + oneday);
		return yesterday;
	}
    
	/**
	 * 计算时间差(分钟),不足一分钟按一分钟计算
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public static int getTimeMinute(Date starttime, Date endtime) {
		long time = (endtime.getTime() - starttime.getTime())/1000;
		if(time <= 0) {
			return 0;
		}
		if(time%60 == 0) {
			return (int) (time/60);
		} else {
			return (int) (time/60 + 1);
		}
	}
	
	/**
	 * 比较两个时间的差值是否在范围内
	 * @param firstTime
	 * @param secondTime
	 * @param seconds  秒数
	 * @return
	 */
	public static boolean isTimeInBound(Date firstTime,Date secondTime,int seconds){
		int dValue = (int)(firstTime.getTime() - secondTime.getTime() / 1000);
		return 0 <=  dValue&& dValue <= seconds;
	}
	
	/**
	 * 比较某个时间是否在时间段内
	 * @param startTime  起始时间段
	 * @param endTime   结束时间段
	 * @param checkTime    要比较的时间
	 * @return checkTime >= startTime && checkTime <= endTime
	 */
	public static boolean isTimeInBound(String startTime,String endTime,Date checkTime){
		if(startTime == null || endTime == null || checkTime == null) return false;
		if(startTime.equals("") || endTime.equals("")) return false;
		int startInt = Integer.parseInt(startTime.replace(":", ""));
		int endInt = Integer.parseInt(endTime.replace(":", ""));
		int checkTimeInt = Integer.parseInt(formatDate(checkTime,"HHmm"));
		if(endInt < startInt){
			endInt += 2400; //跨天
			checkTimeInt += 2400;
		}
		return startInt <= checkTimeInt && checkTimeInt <= endInt;
	}
	
	public static boolean isToday(Date date){
	     SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
	     Date currentDate = new Date();
	     
	     return fmt.format(date).equalsIgnoreCase(fmt.format(currentDate));
	  }
	
	/**
	 * 时间累加
	 * @param source
	 * @param second 秒
	 * @return
	 */
	public static Date addDate(Date source, int second) {
		long courceTime = source.getTime();
		Date date = new Date();
		date.setTime(courceTime + second * 1000);
		return date;
	}

	/**
	 * 校验集合中是否存在此key的值
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean isBlank(Map<String, Object> map, String key) {
		if(!map.containsKey(key) || null == map.get(key) || StringUtils.isBlank(map.get(key).toString())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 校验集合中是否存在此key的值
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean isNotBlank(Map<String, Object> map, String key) {
		if(map.containsKey(key) && null != map.get(key) && StringUtils.isNotBlank(map.get(key).toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 *	<p>计算费用
	 *	<p>计费公式：起步价+里程费+时长费+（实际里程-回空里程）*回空费价+实际里程*夜间费价 
	 *	<p>其中：
	 *	<p>①实际里程指乘客上车地到下车地实际行驶的距离；
	 *	<p>②夜间征收时段，按【订单开始时间计收】，也即 订单开始时间 在夜间征收时段内，则收取夜间费；
	 *	<p>③费用预估时，夜间费不计入行程费用的预估。
	 *	<p>③费用预估时，夜间费不计入行程费用的预估。
	 *	@param oc               计费副本
	 *	@param startTime   订单开始时间(没有传null或空)
	 *	@param estimated  是否预估(true-预估 false-非预估)
	 */
	public static OrderCost countCost(OrderCost oc,Date startTime,boolean estimated){
		//预估费用计算方式
		if(estimated){
			//如果预估里程小于回空里程,不收空驶费
			double deadHeadMileadge = StringUtil.formatNum(oc.getMileage()/1000, 1) - oc.getDeadheadmileage();
			deadHeadMileadge = deadHeadMileadge < 0 ? 0 : deadHeadMileadge;
			oc.setRealdeadheadmileage(deadHeadMileadge);
			oc.setDeadheadcost(deadHeadMileadge * oc.getDeadheadprice());
			oc.setRangecost(StringUtil.formatNum(oc.getMileage()/1000, 1)*oc.getRangeprice());
			//低速模式计算累计时长(预估时仍按总时长计费)
			oc.setTimecost((oc.getTimes()%60>0?(oc.getTimes()/60+1):oc.getTimes()/60)*oc.getTimeprice());
			oc.setCost(oc.getStartprice()+oc.getRangecost()+oc.getTimecost() + oc.getDeadheadcost());
		//实时费用计算方式
		}else{
			boolean isNight = StringUtil.isTimeInBound(oc.getNightstarttime(), oc.getNightendtime(), startTime);
			double nightcost = isNight ? StringUtil.formatNum(oc.getMileage()/1000, 1) * oc.getNighteprice() : 0;
			double deadHeadMileadge = StringUtil.formatNum(oc.getMileage()/1000, 1) - oc.getDeadheadmileage();
			double mileage = StringUtil.formatNum(oc.getMileage()/1000, 1);
			//如果实际里程小于回空里程,不收空驶费
			deadHeadMileadge = deadHeadMileadge < 0 ? 0 : deadHeadMileadge;
			oc.setRealdeadheadmileage(deadHeadMileadge);
			oc.setDeadheadcost(StringUtil.formatNum(deadHeadMileadge * oc.getDeadheadprice(), 1));
			oc.setRangecost(StringUtil.formatNum(mileage * oc.getRangeprice(), 1));
			oc.setNightcost(StringUtil.formatNum(nightcost, 1));
			//低速模式计算累计时长(预估时仍按总时长计费)
			if(oc.getTimetype() == 1){
				oc.setTimecost(oc.getSlowtimes()*oc.getTimeprice());
			}else{  //非低速计算总时长(不足一分钟按一分钟算)
				oc.setTimecost((oc.getTimes()%60>0?(oc.getTimes()/60+1):oc.getTimes()/60)*oc.getTimeprice());
			}
			//行程未开始,不计算费用
			if(startTime != null){
				oc.setCost(oc.getStartprice()+oc.getRangecost()+oc.getTimecost()+oc.getDeadheadcost()+oc.getNightcost());
			}else{
				oc.setCost(0.0D);
			}
			oc.setTimecost(StringUtil.formatNum(oc.getTimecost(), 1));
			oc.setCost(StringUtil.formatNum(oc.getCost(), 1));
		}
		return oc;
	}

    /**
     * 对计算价格取整
     * @param orderCost
     */
	public static void formatOrderCost2Int(OrderCost orderCost) {
        orderCost.setCost(new BigDecimal(orderCost.getCost()).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
    }
	
	/**
	 * 将json转换为JavaBean
	 * @param json
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseJSONToBean(String json,Class<?> clazz){
		JSONObject obj = JSONObject.fromObject(json);
		return (T)JSONObject.toBean(obj, clazz);
//		return (T)GsonUtil.fromJson(json, clazz);
	}
	
	public static void main(String[] args) throws ParseException {
		String startTime = "22:00";
		String endTime = "05:50";
		Date usetime = parseDate("2017-05-22 05:50:17", TIME_WITH_MINUTE);
		String content = "开始时间@isNight夜间服务时段";
		System.out.println(usetime);
		System.out.println(content.replace("@isNight", isTimeInBound(startTime, endTime, usetime) ? "在" : "不在"));
	}
}

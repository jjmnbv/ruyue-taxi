package com.ry.taxi.Util.map;


import java.math.BigDecimal;


/**
 * @Title:GpsUtil.java
 * @Package com.ry.taxi.Util.map
 * @Description
 * @author zhangdd
 * @date 2017年7月19日 下午3:22:16
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
public class GpsUtil {
	
	private static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	
    /** 
     * 对double类型数据保留小数点后多少位 
     *  高德地图转码返回的就是 小数点后6位，为了统一封装一下 
     * @param digit 位数 
     * @param in 输入 
     * @return 保留小数位后的数 
     */  
     static double dataDigit(int digit,double in){  
        return  new   BigDecimal(in).setScale(6,   BigDecimal.ROUND_HALF_UP).doubleValue();  
  
    }  
	
    /**
     *  
     * @param  @param gd_lat
     * @param  @param gd_lon
     * @param  @return
     * @return Point
     * @throws 
     * @Title  bd_encrypt 
     * @author zhangdd
     * @Description 高德,谷歌等火星系坐标转百度坐标
     */
	public static Point bd_encrypt(double gd_lat, double gd_lon){  
		Point point=new Point();
	    double x = gd_lon, y = gd_lat;  
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);  
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x *  x_pi);  
	    double bd_lon = dataDigit(6,z * Math.cos(theta) + 0.0065);
		double bd_lat = dataDigit(6,z * Math.sin(theta) + 0.006);
		point.setLat(bd_lat);
		point.setLng(bd_lon);
		return point;
	} 
	
	

}

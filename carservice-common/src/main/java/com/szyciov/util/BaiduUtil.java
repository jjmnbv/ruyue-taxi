/**
 * 
 */
package com.szyciov.util;

/**
 * @ClassName BaiduUtil
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用)
 * @date 2016年10月8日 上午10:00:45
 */
public class BaiduUtil {
	private final static double DEF_PI = 3.14159265359; // PI
	private final static double DEF_PI180 = 0.01745329252; // PI/180.0
	private final static double DEF_R = 6370693.5; // radius of earth

	/**
	 * 根据圆心、半径算出经纬度范围
	 * 
	 * @param lng
	 *            圆心经度
	 * @param lat
	 *            圆心纬度
	 * @param r
	 *            半径（米）
	 * @return double[4] 南侧经度，北侧经度，西侧纬度，东侧纬度
	 */
	public static double[] getRange(double lng, double lat, double r) {
		double[] range = new double[4];
		// 角度转换为弧度
		double ns = lat * DEF_PI180;
		double sinNs = Math.sin(ns);
		double cosNs = Math.cos(ns);
		double cosTmp = Math.cos(r / DEF_R);
		// 经度的差值
		double lngDif = Math.acos((cosTmp - sinNs * sinNs) / (cosNs * cosNs)) / DEF_PI180;
		// 保存经度
		range[0] = lng - lngDif;
		range[1] = lng + lngDif;
		double m = 0 - 2 * cosTmp * sinNs;
		double n = cosTmp * cosTmp - cosNs * cosNs;
		double o1 = (0 - m - Math.sqrt(m * m - 4 * (n))) / 2;
		double o2 = (0 - m + Math.sqrt(m * m - 4 * (n))) / 2;
		// 纬度
		double lat1 = 180 / DEF_PI * Math.asin(o1);
		double lat2 = 180 / DEF_PI * Math.asin(o2);
		// 保存
		range[2] = lat1;
		range[3] = lat2;
		return range;
	}

	/**
	 * 计算两点间直线距离
	 * @param lon1
	 * @param lat1
	 * @param lon2
	 * @param lat2
	 * @return 距离(米)
	 */
	public static double getLongDistance(double lng1, double lat1, double lng2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double distance;
		// 角度转换为弧度
		ew1 = lng1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lng2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 求大圆劣弧与球心所夹的角(弧度)
		distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
		// 调整到[-1..1]范围内，避免溢出
		if (distance > 1.0)
			distance = 1.0;
		else if (distance < -1.0)
			distance = -1.0;
		// 求大圆劣弧长度
		distance = DEF_R * Math.acos(distance);
		return StringUtil.formatNum(distance, 1);
	}

	public static void main(String args[]) {
		double lng1 = 114.27889391269181;
		double lat1 = 30.59793562964675;
		double lng2 = 114.28096273290703;
		double lat2 = 30.59971637230293;
//		double r[] = getRange(lng, lat, 9000);
//		System.out.println(r[0]);
//		System.out.println(r[1]);
//		System.out.println(r[2]);
//		System.out.println(r[3]);
//		System.out.println(getLongDistance(lng, lat, r[0], lat));
//		System.out.println(getLongDistance(lng, lat, r[1], lat));
//		System.out.println(getLongDistance(lng, lat, lng, r[2]));
//		System.out.println(getLongDistance(lng, lat, lng, r[3]));
		System.out.println(getLongDistance(lng1, lat1, lng2, lat2));
	}
}

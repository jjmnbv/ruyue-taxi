package com.szyciov.operate.util;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * 百度经纬度解析及地址解析成经纬度
 * 
 * @author YuanJL
 *
 */
public class BaiduMap {

	/***
	 * 地址
	 */
	public static final String BAIDU_ADDRESS = "http://api.map.baidu.com/geocoder?address={0}&output=json&key=37492c0ee6f924cb5e934fa08c6b1676";
	/***
	 * 经纬度解析地址
	 */
	public static final String BAIDU_LOCATION = "http://api.map.baidu.com/geocoder/v2/?ak=d24GGQVHSfec4DgpQGffb9yUGWEjtZef&location=%s,%s&output=json&pois=0";
	/**
	 * 输入地址返回经纬度坐标 key lng(经度),lat(纬度)
	 */
	@RequestMapping(value = "/getXY")
	public static void getGeocoderLatitude(String address) {
		BufferedReader in = null;
		try {
			address = URLEncoder.encode(address, "UTF-8");
			String url = String.format(BAIDU_ADDRESS, address);
			URL tirc = new URL(url);
			in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			String str = sb.toString();
			if (StringUtils.isNotEmpty(str)) {
				int lngStart = str.indexOf("lng\":");
				int lngEnd = str.indexOf(",\"lat");
				int latEnd = str.indexOf("},\"precise");
				if (lngStart > 0 && lngEnd > 0 && latEnd > 0) {
//					String lng = str.substring(lngStart + 5, lngEnd);
//					String lat = str.substring(lngEnd + 7, latEnd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据经纬度解析地址<一句话功能简述> <功能详细描述>
	 * 
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws MalformedURLException
	 * @see [类、类#方法、类#成员]
	 */
	public static String getAdress(BigDecimal latitude, BigDecimal longitude) throws IOException {
		String url = String.format(BAIDU_LOCATION, latitude, longitude);
		String json = RequestTool.get(url, "");
		JSONObject jObject = JSON.parseObject(json);
		Object str= jObject.get("status");
		if (null != jObject.get("status") && (int) jObject.get("status") == 0) {
			if (jObject.getJSONObject("result") != null) {
				JSONObject jsonObjectResult=jObject.getJSONObject("result");
				return jsonObjectResult.getString("formatted_address");			
			}
		}
		return "";
	}

	/**
	 * 经纬度纠偏 <功能详细描述>
	 * 
	 * @param longitude
	 * @param latitude
	 * @return
	 * @throws IOException
	 * @see [类、类#方法、类#成员]
	 */
	public static Map<String, BigDecimal> convertBaiduCoord(BigDecimal longitude, BigDecimal latitude)
			throws IOException {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();

		String url = "http://api.map.baidu.com/ag/coord/convert?from=0&to=4";
		url += "&x=" + longitude + "&y=" + latitude;
		String json = RequestTool.get(url, "");

		JSONObject parseObject = JSON.parseObject(json);

		byte[] lng = Base64.getDecoder().decode(parseObject.getString("x"));
		byte[] lat = Base64.getDecoder().decode(parseObject.getString("y"));

		map.put("longitude", new BigDecimal(new String(lng)));
		map.put("latitude", new BigDecimal(new String(lat)));
		return map;
	}

	/***
	 * 判断行驶轨迹的点是否在 多边形电子围栏区域
	 * @param point
	 * @param polygon
	 * @return
	 */
	public static boolean Check(Point2D.Double point, List<Point2D.Double> polygon) {  
	    java.awt.Polygon p = new Polygon();  
	    // java.awt.geom.GeneralPath  
	    final int TIMES = 1000;  
	    for (Point2D.Double d : polygon) {  
	        int x = (int) d.x * TIMES;  
	        int y = (int) d.y * TIMES;  
	        p.addPoint(x, y);  
	    }  
	    int x = (int) point.x * TIMES;  
	    int y = (int) point.y * TIMES;  
	    return p.contains(x, y);  
	}
    
    /// <summary>
    /// 求 轨迹点 到圆形的距离
    /// </summary>
    /// <param name="gpsPoint"></param>
    /// <param name="points"></param>
    /// <returns></returns>
    public static double getDistance(String gpsPoint, String points)
    {
        double DEF_PI = 3.14159265359; // PI
        double DEF_2PI = 6.28318530712; // 2*PI
        double DEF_PI180 = 0.01745329252; // PI/180.0
        double DEF_R = 6370693.5; // radius of earth
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        String[] strGps = gpsPoint.split(",");
        double gpsLng = Double.parseDouble(strGps[0]);
        double gpsLat = Double.parseDouble(strGps[1]);
        String p1 = points.replace("(", " ");
        String p2 = p1.replace(")", " ");
        String[] strPoints = p2.split(",");
        double Lng = Double.parseDouble(strPoints[0]);
        double Lat = Double.parseDouble(strPoints[1]);

        // 角度转换为弧度
        ew1 = gpsLng * DEF_PI180;
        ns1 = gpsLat * DEF_PI180;
        ew2 = Lng * DEF_PI180;
        ns2 = Lat * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }
}

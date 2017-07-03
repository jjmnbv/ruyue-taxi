package com.szyciov.util;

/**
 * OBD里程算法常量
 * Created by shikang on 2017/5/19.
 */
public class OBDConstans {

    /**
     * 两点间的平均速度临界点(单位:km/h)
     */
    public static final int POINT_AVERAGE_SPEED_CRITICAL = 200;

    /**
     * 首个GPS点与订单开始时间/尾个GPS点与订单结束时间的时间差临界点(单位:秒)
     */
    public static final int ORDER_GPS_TIME_INTERVAL_CRITICAL = 2 * 60;

    /**
     * GPS首尾坐标点的平均速度临界点(单位:km/h)
     */
    public static final int START_END_AVERAGE_SPEED_CRITICAL = 150;

    /**
     * GPS的实际数量/预计数量
     */
    public static final double GPS_ACTUAL_ESTIMATE_RATIO_CRITICAL = 0.7;

    /**
     * 所有GPS点之间的最大时间间隔临界点(单位:秒)
     */
    public static final int GPS_POINT_TIME_INTERVAL_CRITICAL = 2 * 60;

    /**
     * GPS上传时间间隔(单位:秒)
     */
    public static final int GPS_UPLOAD_TIME_INTERVAL = 10;

    /**
     * GPS时间与服务器时间间隔临界点(单位:秒)
     */
    public static final int GPS_valid_TIME_INTERVAL = 5 * 60;

    /**
     * 中国范围经度开始点
     */
    public static final double CHINA_START_LNG = 73.66;

    /**
     * 中国范围经度结束点
     */
    public static final double CHINA_END_LNG = 135.05;

    /**
     * 中国范围纬度开始点
     */
    public static final double CHINA_START_LAT = 3.86;

    /**
     * 中国范围纬度结束点
     */
    public static final double CHINA_END_LAT = 53.55;

    /**
     * 查询车辆安装OBD设备返回状态码
     */
    public static final int VMS_API_OK = 0;

    /**
     * 订单轨迹所属表开始时间(单位:秒)
     */
    public static final long ORDER_GPS_START = 9 * 24 * 60 * 60;

    /**
     * 订单轨迹所属表结束时间(单位:秒)
     */
    public static final long ORDER_GPS_END = 10 * 24 * 60 * 60;

    /**
     * OBD设备标识
     */
    public static final String OBD_CATEGORY_NAME = "OBD";

    /**
     * OBDGPS设备标识
     */
    public static final String GPS_CATEGORY_NAME = "GPS";

    /**
     * APPGPS里程和鹰眼里程最小相差倍数
     */
    public static final double MIN_APP_LBS_MULTIPLE = 0.5;

    /**
     * APPGPS里程和鹰眼里程最大相差倍数
     */
    public static final double MAX_APP_LBS_MULTIPLE = 1.5;


}

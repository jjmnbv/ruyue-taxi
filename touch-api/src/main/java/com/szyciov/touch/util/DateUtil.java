package com.szyciov.touch.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * Created by shikang on 2017/5/10.
 */
public class DateUtil {

    /**
     * 时间格式
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式,没有符号连接
     */
    public static final String DATE_TIME_FORMAT_NOSYMBOL = "yyyyMMddHHmmss";

    /**
     * 时间转换成String类型
     * @param sourceDate
     * @return 转换后的格式为yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateToStr(Date sourceDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        return sdf.format(sourceDate);
    }

    /**
     * String类型转换成时间
     * @param sourceDate 时间参数格式必须为yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static Date formatStrToDate(String sourceDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        return sdf.parse(sourceDate);
    }

    /**
     * 计算时间差(返回结果单位为分钟,时间格式必须为yyyy-MM-dd HH:mm:ss)
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     * @throws ParseException
     */
    public static int calculateTime(String startDate, String endDate) throws ParseException {
        long starttime = formatStrToDate(startDate).getTime()/1000;
        long endtime = formatStrToDate(endDate).getTime()/1000;
        double time = Math.ceil((endtime - starttime)/60.0d);
        return (int) time;
    }

    /**
     * 将字符串格式的时间转换为yyyy-MM-dd HH:mm:ss格式
     * @param sourceDate
     * @param format
     * @return
     * @throws ParseException
     */
    public static String formatStrToStr(String sourceDate, String format) throws ParseException {
        SimpleDateFormat sourceFormat = new SimpleDateFormat(format);
        SimpleDateFormat targetFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        return targetFormat.format(sourceFormat.parse(sourceDate));
    }

    /**
     * 去掉yyyy-MM-dd HH:mm:ss格式后的毫秒
     * @param sourceDate
     * @return
     * @throws ParseException
     */
    public static String formatStrToStr(String sourceDate) throws ParseException {
        return formatStrToStr(sourceDate, DATE_TIME_FORMAT);
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getNowDate() {
        return formatDateToStr(new Date());
    }

    /**
     * 获取当前时间,yyyyMMddHHmmss格式
     * @return
     */
    public static String getNowDateNosymbol() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_NOSYMBOL);
        return sdf.format(new Date());
    }

}

package com.szyciov.util;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.szyciov.dto.coupon.CouponInfoDTO;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GSON操作工具类
 * Created by LC on 2016/11/22.
 */
public class GsonUtil {
    private static Logger logger = LoggerFactory.getLogger(GsonUtil.class);

    private GsonUtil(){}

    private static class  GsonHolder{

        private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    /**
     * 将对象转换成JSON字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        if(obj==null){
            return "";
        }
        return GsonHolder.gson.toJson(obj);
    }

    /**
     * 将json字符串转换成对象
     * @param json
     * @param clss
     * @param <T>
     * @return
     */
    public static  <T> T fromJson(String json, Class<T> clss){
        return GsonHolder.gson.fromJson(json,clss);
    }

    /**
     * 将json字符串转换成list对象
     * @param json
     * @param type
     * @param <T>
     * @return
     * @throws Exception
     */

    public static <T> List<T> fromJson2Array(String json,Type type) throws Exception{
        List<T> list  = GsonHolder.gson.fromJson(json,type);
        return list;
    }

}
 
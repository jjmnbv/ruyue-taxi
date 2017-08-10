package com.szyciov.coupon.util;

import org.slf4j.Logger;

/**
 * @author LC
 * @date 2017/8/8
 */
public class GenerateLogUtil {



    public  static void writeInfoLog(Logger logger,String logStr,Object... val){

        logger.info(logStr,val);
    }


    public  static void writeErroLog(Logger logger,String logStr,Exception e,Object... val){

        logger.error(logStr,val,e);
    }

}
 
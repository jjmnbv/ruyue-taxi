package com.szyciov.lease.utils;

import com.szyciov.util.DateUtil;

import java.util.Date;

/**
 * 合作编号：合作标识+合作日期+合作流水，示例：合计13位字符；
 * •	其中：
 * •	合作标识：B2B联盟：BB；B2C联营：BC
 * •	合作日期：年月日时，示例：16010118，即16年1月1日18时；
 * •	合作流水：三位数字，示例001；即第一个；
 * <p>
 * Created by ZF on 2017/8/8.
 */
public final class CoooperateNumUtils {
    private static int num = 1;
    private static String today = "";

    private CoooperateNumUtils() {

    }

    public static String getNum(int type) {
        String cooono = "";
        if (type == 0) {
            cooono = "BB";
        } else {
            cooono = "BC";
        }

        String date = DateUtil.format(new Date(), "yyMMddHH");
        if (!today.equals(date)) {
            today = date;
            num = 1;
        }


        return cooono + date + left0(num++);
    }


    private static String left0(int num) {
        if (num < 10) {
            return "00" + num;
        } else if (num < 100) {
            return "0" + num;
        } else if (num < 100) {
            return String.valueOf(num);
        }
        return String.valueOf(num);
    }

}

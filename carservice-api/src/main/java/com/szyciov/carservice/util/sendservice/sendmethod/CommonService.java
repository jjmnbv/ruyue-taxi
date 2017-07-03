package com.szyciov.carservice.util.sendservice.sendmethod;

import com.szyciov.util.JedisUtil;

import java.util.List;

/**
 * 消息推送公共业务
 * Created by ZF on 2017/5/3.
 */
public final class CommonService {


    private final static String HAD_SEND_DRIVER = "hadSendDriver_"; // 已经发送过即刻抢单的司机，存放到redis的key前缀

    private CommonService() {
    }

    /**
     * 过滤已经发送过即刻抢单的司机
     *
     * @param driverPhones 推送的司机手机集合
     * @param timeout      司机端抢单时间
     */
    public static void filterHadSendDriver(List<String> driverPhones, int timeout) {
        String key;
        for (int i = driverPhones.size() - 1; i >= 0; i--) {
            String phone = driverPhones.get(i);
            key = HAD_SEND_DRIVER + phone;
            if (JedisUtil.getString(key) != null) {
                driverPhones.remove(i);
            } else {
                JedisUtil.setString(key, timeout, phone);
            }
        }
    }


}

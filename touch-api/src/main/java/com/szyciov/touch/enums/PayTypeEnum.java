package com.szyciov.touch.enums;

import com.szyciov.enums.OrderEnum;
import org.apache.commons.lang.StringUtils;

/**
 * 支付渠道
 * Created by shikang on 2017/5/15.
 */
public enum  PayTypeEnum {

    ALIPAY("0",OrderEnum.PAYTYPE_ALIPAY.code, "1", "支付宝"),

    WECHAT("1", OrderEnum.PAYTYPE_WECHAT.code, "0", "微信");

    /**
     * 根据标准化接口订单支付渠道获取枚举类
     * @param paytype
     * @return
     */
    public static PayTypeEnum getPayType(String paytype) {
        if(StringUtils.isBlank(paytype)) {
            return null;
        }
        if(paytype.equals(PayTypeEnum.ALIPAY.state)) {
            return ALIPAY;
        } else if(paytype.equals(PayTypeEnum.WECHAT.state)) {
            return WECHAT;
        } else {
            return null;
        }
    }

    /**
     * 根据真实订单支付渠道获取枚举类
     * @param realPaytype
     * @return
     */
    public static PayTypeEnum getRealPayType(String realPaytype) {
        if(StringUtils.isBlank(realPaytype)) {
            return null;
        }
        if(realPaytype.equals(PayTypeEnum.ALIPAY.realstate)) {
            return ALIPAY;
        } else if(realPaytype.equals(PayTypeEnum.WECHAT.realstate)) {
            return WECHAT;
        } else {
            return null;
        }
    }

    /**
     * 标准化接口的订单支付渠道
     */
    public String state;

    /**
     * 订单的真实支付渠道
     */
    public String realstate;

    /**
     * 订单支付记录支付方式
     */
    public String recordstate;

    /**
     * 订单的支付渠道的描述
     */
    public String message;

    PayTypeEnum(String state, String realstate, String recordstate, String message) {
        this.state = state;
        this.realstate = realstate;
        this.recordstate = recordstate;
        this.message = message;
    }

}

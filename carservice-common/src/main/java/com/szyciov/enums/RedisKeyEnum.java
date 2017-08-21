package com.szyciov.enums;

/**
 * rediskey枚举，用于管理redisKey
 * Created by LC on 2017/3/14.
 */
public enum RedisKeyEnum {


    /*****数据类型类型*****/

    MESSAGE_LIST("list#","List集合数据"),



    /*****操作类型  不带入key中*****/
    MESSAGE_TYPE_MAKE("make#","操作类型"),
    MESSAGE_TYPE_READ("read#","只读类型"),


    /**角色相关key***/
    MESSAGE_OPERATE_ROLE_ADMIN("operate#admin#","运管端超管"),
    MESSAGE_LEASE_ROLE_ADMIN("lease#admin#","租赁端超管"),


    /***功能模块 start***/
    //首页弹窗
    MESSAGE_FUNCTION_POPUP("pop#","List集合数据"),


    /***功能模块 end***/


    /*************订单处理start********/
    MESSAGE_ORDER("order#","订单消息"),
    DRIVER_TRAVEL_REMINDER("DRIVER_TRAVEL_REMINDER_","最后提醒"),
    /*************订单处理End********/


    /*************交接班start********/

    MESSAGE_PENDING("pending#","待处理交接班"),


    /*************交接班End********/



    /************手机实名认证start************/

    PHONE_REAL_NAME("phone_real_name#","手机实名制认证"),

    PHONE_REAL_NAME_COUNT("phone_real_name_count#","访问次数"),


    /************手机实名认证end************/


    /*************告警处理start********/

	MESSAGE_ALARMPROCESS("alarmprocess#","报警管理"),



    /*************告警处理End********/


    /*************行程提醒start********/
	ORDER_FORCESEND_REMINDER("DRIVER_TRAVEL_REMINDER_","强派订单行程提醒"),
    MESSAGE_TRAVEL_REMINDER_PREFIX("TRAVEL_REMINDER_", "行程提醒消息前缀"),
    MESSAGE_TRAVEL_REMINDER_ORG("TRAVEL_REMINDER_ORG_", "机构网约车行程提醒"),
    MESSAGE_TRAVEL_REMINDER_OP("TRAVEL_REMINDER_OP_", "运管网约车行程提醒"),
    MESSAGE_TRAVEL_REMINDER_OPTAXI("TRAVEL_REMINDER_OPTAXI_", "运管出租车行程提醒"),

    /*************行程提醒End********/


	/***************司机坐标附带距离终点的信息start******************/
	MESSAGE_ORDER_TRAVEL_INFO("ORDER_TRAVEL_INFO_","司机距离终点的剩余信息"),
	
	
	/***************司机坐标附带距离终点的信息end******************/

	/**********************乘客端验证码验证信息start***************************/
	SMS_PASSENGER_LOGIN_ERRORTIMES("SMS_PASSENGER_LOGIN_ERRORTIMES_","登录验证码失败次数"),
    SMS_PASSENGER_LOGIN("SMS_PASSENGER_LOGIN_","登录短信验证码"),


    SMS_PASSENGER_CHANGEPWD_ERRORTIMES("SMS_PASSENGER_CHANGEPWD_ERRORTIMES_","更改密码验证码失败次数"),
    SMS_PASSENGER_CHANGEPWD("SMS_PASSENGER_CHANGEPWD_","更改密码短信验证码"),


    SMS_PASSENGER_REGISTER_ERRORTIMES("SMS_PASSENGER_REGISTER_ERRORTIMES_","注册验证码失败次数"),
    SMS_PASSENGER_REGISTER("SMS_PASSENGER_REGISTER_","注册短信验证码"),
	/**********************乘客端验证码验证信息end*************************/


    /**********************抵用券相关start***************************/



    /**
     * Map类型
     * COUPON_ACTIVY_租赁公司ID_规则对象(数字)_派发类别(数字)
     */
    COUPON_ACTIVY("COUPON_ACTIVY_","抵用券活动"),

    /**
     * 存储已经获取抵用券的用户ID
     * COUPON_HAVE_用户ID
     */
    COUPON_HAVE("COUPON_HAVE_","已经获得抵用券"),

    /**
     * 存储订单次数累计用户
     * MAP类型
     * COUPON_ORDER_COUNT_活动ID
     */
    COUPON_ORDER_COUNT("COUPON_ORDER_COUNT_","已经获得抵用券"),

    /**
     * 存储金额累计用户
     * Map类型
     * COUPON_ORDER_MONEY_活动ID
     */
    COUPON_ORDER_MONEY("COUPON_ORDER_MONEY_","已经获得抵用券"),

    /**
     * 待发送
     * String 类型
     * COUPON_STAY_SEND_用户ID
     */
    COUPON_STAY_SEND("COUPON_STAY_SEND_","待补发的抵用券"),
    /**********************抵用券相关end*************************/
    
    /**********************抵用券相关end*************************/
    /**
     * 密码验证成功凭证
     * String 类型
     * AUTHPASS_用户手机号_密码类型
     */
    AUTHPASS("AUTHPASS_","密码验证成功"),
    /**
     * 短信验证成功凭证
     * String 类型
     * AUTHCODE_用户手机号
     */
    AUTHCODE("AUTHCODE_","短信验证成功"),
    /**********************抵用券相关end*************************/


    /**********************司机端验证码验证信息start*************************/
	SMS_DRIVER_LOGIN_ERRORTIMES("SMS_DRIVER_LOGIN_ERRORTIMES_","登录验证码失败次数"),
	SMS_DRIVER_LOGIN("SMS_DRIVER_LOGIN_","登录短信验证码");
	/**********************司机端验证码验证信息end*************************/

    public String code;
    public String msg;

    RedisKeyEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}

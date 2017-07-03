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

    MESSAGE_TRAVEL_REMINDER_PREFIX("TRAVEL_REMINDER_", "行程提醒消息前缀"),
    MESSAGE_TRAVEL_REMINDER_ORG("TRAVEL_REMINDER_ORG_", "机构网约车行程提醒"),
    MESSAGE_TRAVEL_REMINDER_OP("TRAVEL_REMINDER_OP_", "运管网约车行程提醒"),
    MESSAGE_TRAVEL_REMINDER_OPTAXI("TRAVEL_REMINDER_OPTAXI_", "运管出租车行程提醒"),

    /*************行程提醒End********/


	/***************司机坐标附带距离终点的信息start******************/
	MESSAGE_ORDER_TRAVEL_INFO("ORDER_TRAVEL_INFO_","司机距离终点的剩余信息");
	
	
	/***************司机坐标附带距离终点的信息end******************/

    public String code;
    public String msg;

    RedisKeyEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }







}

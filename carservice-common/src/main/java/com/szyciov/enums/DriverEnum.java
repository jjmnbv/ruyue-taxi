package com.szyciov.enums;

/**
 * 司机状态
 * Created by LC on 2017/3/8.
 */
public enum DriverEnum {

    /**
     * 司机类型
     */
    DRIVER_TYPE_CAR("0","网约车"),
    DRIVER_TYPE_TAXI("1","出租车"),

    /**
     * 锁定状态
     */
    LOCK_STATUS_LOCKED("1","已锁定"),
    LOCK_STATUS_UNLOCK("0","未锁定"),


    /**
     * 交接班状态
     */
    PASS_WORK_STATUS_NO("0","无对班"),
    PASS_WORK_STATUS_ON("1","当班"),
    PASS_WORK_STATUS_OFF("2","歇班"),
    PASS_WORK_STATUS_PENDING("3","交班中"),
    PASS_WORK_STATUS_PROCESSIND("4","接班中"),
    PASS_WORK_STATUS_NOPLAN("5","未排班"),



    /**
     * 工作状态
     */
    WORK_STATUS_LEISURE("0","空闲"),
    WORK_STATUS_SERVICE("1","服务中"),
    WORK_STATUS_OFFLINE("2","下线"),
    WORK_STATUS_UNBIND("3","未绑定"),


    /**
     * 性别
     */
    SEX_MAN("0","男"),
    SEX_WOMAN("1","女"),

    /**
     * 在职状态
     */
    JOB_STATUS_DIMISSION("1","离职"),
    JOB_STATUS_JOB("0","在职"),
    
    /**
     * 提现密码-未修改
     */
    WDPASS_UNCHANGE("0","未修改"),
    /**
     * 提现密码-已修改
     */
    WDPASS_CHANGED("1","已修改"),
	
    /**
     * 校验|修改密码类型 - 登录密码
     */
	PASSWORD_TYPE_LOGINPASS("0","登录密码"),
	/**
	 * 校验|修改密码类型 - 提现密码
	 */
	PASSWORD_TYPE_WITHDRAWPASS("1","提现密码"),
	
	/**
	 * 发送短信类型 - 登录
	 */
	SMS_TYPE_LOGIN("0","登录"),
	/**
	 * 发送短信类型 - 改变密码
	 */
	SMS_TYPE_CHANGEPASS("1","改变密码"),
	/**
	 * 发送短信类型 - 忘记密码
	 */
	SMS_TYPE_FORGETPASS("2","忘记密码"),
	
	/**
	 * 交接班类型 - 自主
	 */
	SHIFT_TYPE_PERSON("0","自主交班"),
	/**
	 * 交接班类型 - 客服
	 */
	SHIFT_TYPE_SYSTEM("1","客服指派"),
	
	/**
	 * 司机上班
	 */
	ATWORK_TYPE_ON("0","上班"),
	/**
	 * 司机下班
	 */
	ATWORK_TYPE_OFF("1","下班"),
	
	/**
	 * 登录类型 - 密码
	 */
	LOGIN_TYPE_PASS("0","密码登录"),
	/**
	 * 登录类型 - 验证码
	 */
	LOGIN_TYPE_SMS("1","验证码登录"),
	
	/**
	 * 车辆绑定状态 - 已绑定
	 */
	BOUND_STATE_ON("1","已绑定"),
	/**
	 * 车辆绑定状态 - 未绑定
	 */
	BOUND_STATE_OFF("0","未绑定");
	

    public String code;
    public String msg;

    DriverEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 返回司机类型对应汉字
     * @param code  状态码
     * @return
     */
    public static String getDriverTypeStr(String code){
        switch (code) {
            case "0":
                return DriverEnum.DRIVER_TYPE_CAR.msg;
            case "1":
                return DriverEnum.DRIVER_TYPE_TAXI.msg;
            default:
                return "/";
        }
    }


    /**
     * 返回司机状态对应汉字，默认未绑定
     * @param code  状态码
     * @return
     */
    public static String getStatusStr(String code){
        switch (code) {
            case "0":
                return DriverEnum.WORK_STATUS_LEISURE.msg;
            case "1":
                return DriverEnum.WORK_STATUS_SERVICE.msg;
            case "2":
                return DriverEnum.WORK_STATUS_OFFLINE.msg;
            default:
                return DriverEnum.WORK_STATUS_UNBIND.msg;
        }
    }

    /**
     * 返回司机出租车状态对应汉字
     * @param code  状态码
     * @return
     */
    public static String getPassStatusStr(String code){
        switch (code) {
            case "0":
                return DriverEnum.PASS_WORK_STATUS_NO.msg;
            case "1":
                return DriverEnum.PASS_WORK_STATUS_ON.msg;
            case "2":
                return DriverEnum.PASS_WORK_STATUS_OFF.msg;
            case "3":
                return DriverEnum.PASS_WORK_STATUS_PENDING.msg;
            case "4":
                return DriverEnum.PASS_WORK_STATUS_PROCESSIND.msg;
            case "5":
                return DriverEnum.PASS_WORK_STATUS_NOPLAN.msg;
            default:
                return "/";
        }
    }

    /**
     * 返回性别信息
     * @param code
     * @return
     */
    public static String getSexStr(String code){
        switch (code) {
            case "0":
                return DriverEnum.SEX_MAN.msg;
            case "1":
                return DriverEnum.SEX_WOMAN.msg;
            default:
                return "/";
        }
    }

    /**
     * 返回在职状态信息
     * @param code
     * @return
     */
    public static String getJobStatusStr(String code){
        switch (code) {
            case "0":
                return DriverEnum.JOB_STATUS_JOB.msg;
            case "1":
                return DriverEnum.JOB_STATUS_DIMISSION.msg;
            default:
                return "/";
        }
    }

    /**
     * 返回上下班类型
     * @param type
     * @return
     */
    public static String getDriverAtWorkType(String type){
    	return ATWORK_TYPE_OFF.code.equals(type) ? ATWORK_TYPE_OFF.msg : 
    				ATWORK_TYPE_ON.code.equals(type) ? ATWORK_TYPE_ON.msg : "";
    }

}
 
/**
 * 
 */
package com.szyciov.enums;

/**
 * @ClassName SMSCodeEnum 
 * @author Efy Shu
 * @Description 短信验证码枚举类
 * 此类主要用来给短信验证码做校验时,提供redis-key的定义
 * @date 2017年7月11日 下午5:17:37 
 */
public enum SMSCodeEnum {
	/**
	 * 登陆
	 */
	LOGIN("登陆"),
	/**
	 * 修改密码
	 */
	CHANGEPASSWORD("修改密码"),
	/**
	 * 注册
	 */
	REGISTER("注册"),
	/**
	 * 失败次数
	 */
	ERRORTIMES("失败次数"),
	/**
	 * 司机端
	 */
	DRIVER("司机端"),
	/**
	 * 乘客端
	 */
	PASSENGER("乘客端");
	
    /**
     * 短信类型描述
     */
    public String msg;

    SMSCodeEnum(String msg) {
        this.msg = msg;
    }
}

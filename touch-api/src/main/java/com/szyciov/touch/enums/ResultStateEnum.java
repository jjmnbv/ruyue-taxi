package com.szyciov.touch.enums;

/**
 * 接口返回结果的枚举类
 * @author zhu
 *
 */
public enum ResultStateEnum {

/*****************************************************************************/
	
	/**
	 * 请求成功
	 */
	OK("0","请求成功,无异常"),
	
	/**
	 * 请求异常
	 */
	EXCEPTION("1","服务器异常"),
	
	/**
	 * 无效token
	 */
	INVALIDTOKEN("1000","无效token"),
	
	/**
	 * 此城市没有服务
	 */
	NOSERVICESINCITY("2000","此城市没有服务"),
	
	/**
	 * 订单已被抢
	 */
	ORDERISGONE("2002","订单已被抢"),
	
	/**
	 * 订单已取消
	 */
	ORDERISCANCEL("2003","订单已取消"),

	/**
	 * 您有订单未支付，现在不能下单，请完成支付后再进行下单。
	 */
	ORDERNOTPAY("2005", "您有订单未支付，现在不能下单，请完成支付后再进行下单。"),
	
	/**
	 * 订单不存在
	 */
	ORDERNOTEXIT("2006","订单不存在"),
	
	/**
	 * 订单状态不正确
	 */
	INVALIDORDERSTATUS("2007","订单状态不正确"),

	/**
	 * 未完成订单已超限
	 */
	ORDEROUTOFLIMIT("2008", "未完成订单已超限"),

	/**
	 * 用车时间失效，请重新选择
	 */
	BEFORENOWTIME("2020", "用车时间失效，请重新选择"),

	/**
	 * 请求参数不合法
	 */
	PARAMUNVALIABLE("1001","请求参数不合法"),
	
	/**
	 * 用户类型有误
	 */
	ILLEGALUSERTYPE("1002","用户类型有误"),
	
	/**
	 * 用户不存在
	 */
	USERNOTEXIST("1003","用户不存在"),
	
	/**
	 * 密码有误
	 */
	PASSWORDWRONG("1004","密码有误"),
	
	/**
	 * 短信验证码不正确
	 */
	SMSCODEINVALID("1005","短信验证码不正确"),
	
	/**
	 * 短信验证码
	 */
	SMSCODEOUTTIME("1006","短信验证码已过期"),
	
	/**
	 * 签名不合法
	 */
	INVALIDSIGN("4001","签名不合法"), 
	
	/**
	 * 未找到授权信息
	 */
	NOAUTHINFO("1007","未找到授权信息");
/****************************************************************************/
	/**
	 * 返回的结果状态
	 */
	public String state;
	
	/**
	 * 返回的结果的状态描述
	 */
	public String message;

	ResultStateEnum(String state, String message) {
        this.state = state;
        this.message = message;
	}
}

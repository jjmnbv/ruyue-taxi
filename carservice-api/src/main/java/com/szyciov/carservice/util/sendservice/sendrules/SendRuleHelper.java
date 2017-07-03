package com.szyciov.carservice.util.sendservice.sendrules;

/**
 * 派单规则的接口
 * @author zhu
 *
 */
public interface SendRuleHelper {
	
	/**
	 * 强派类型
	 */
	public static final String SEND_TYPE_FORCE = "0";
	
	/**
	 * 抢派类型
	 */
	public static final String SEND_TYPE_GRAB = "1";
	
	/**
	 * 抢单类型
	 */
	public static final String SEND_TYPE_GRABSINGLE = "2";
	
	/**
	 * 纯人工类型
	 */
	public static final String SEND_TYPE_SYSTEMSINGLE = "3";
	
	/**
	 * 即可用车
	 */
	public static final String USE_TYPE_USENOW = "1";
	
	/**
	 * 预约用车
	 */
	public static final String USE_TYPE_RESERVE = "0";
	
	/**
	 * 派单模式：系统
	 */
	public static final String SEND_MODEL_SYSTEM = "0";
	
	/**
	 * 派单模式：系统+人工
	 */
	public static final String SEND_MODEL_MANTICANDSYSTEM = "1";

	/**
	 * 始终弹窗
	 */
	public static final String ALWAYSPUSH = "1";
	
	/**
	 * 获取派单类型
	 * @return
	 */
	public String getSendtype();
}

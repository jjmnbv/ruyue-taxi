package com.szyciov.driver.enums;

import com.szyciov.op.entity.PubDriverExpenses;

/**
 * @ClassName PurseEnum 
 * @author Efy Shu
 * @Description 司机端钱包枚举类
 * @date 2017年3月25日 下午6:37:26 
 */
public enum PurseEnum {
	/**
	 * 交易类型-充值
	 */
	TRADETYPE_RECHARGE("0","充值"),
	/**
	 * 交易类型-订单结算
	 */
	TRADETYPE_SETTLE("1","订单结算"),
	/**
	 * 交易类型-退款
	 */
	TRADETYPE_REFUND("2","退款"),
	/**
	 * 交易类型-提现
	 */
	TRADETYPE_DEPOSIT("3","提现"),
	/**
	 * 交易类型-订单收入
	 */
	TRADETYPE_ORDER("4","订单收入"),
    /**
     * 交易类型-付现
     */
    TRADETYPE_CASH("5","付现"),
	
	/**
	 * 交易渠道-微信支付
	 */
	EXPENSETYPE_WEIXIN("1","微信"),
	/**
	 * 交易渠道-支付宝支付
	 */
	EXPENSETYPE_ALIPAY("2","支付宝"),
	/**
	 * 交易渠道-余额支付
	 */
	EXPENSETYPE_BALANCE("3","余额"),
	/**
	 * 交易渠道-提现
	 */
	EXPENSETYPE_WITHDRAW("4","提现"),
	/**
	 *  交易渠道-平台转入
	 */
	EXPENSETYPE_DRAWIN("5","平台转入"),
	/**
     * 交易渠道-机构支付
     */
	EXPENSETYPE_ORGBALANCE("6","机构支付"),
	/**
	 * 明细类型-余额明细
	 */
	DETAILTYPE_BALANCE("0","余额明细"),
	/**
	 * 明细类型-交易明细
	 */
	DETAILTYPE_ALLTRANS("1","交易明细"),
	
	/**
	 * 交易标识-收入
	 */
	TRANSSIGN_TAKEIN("0","收入"),
	/**
	 * 交易标识-支出
	 */
	TRANSSIGN_TAKEOUT("1","支出"),
	
	/**
	 * 操作结果-成功
	 */
	OPERATERESULT_SUCCESS("0","成功"),
	/**
	 * 操作结果-失败
	 */
	OPERATERESULT_FAILED("1","失败'"),
	/**
	 * 订单统计-按月
	 */
	ORDERSTATIS_MONTH("0","按月统计"),
	/**
	 * 订单统计-按年
	 */
	ORDERSTATIS_YEAR("1","按年统计"),
	
	/**
	 * 支付账户-未开通
	 */
	ACCOUNT_OFF("0","未开通"),
	/**
	 * 支付账户-已开通
	 */
	ACCOUNT_ON("1","已开通");
	
	/**
	 * 获取交易类型
	 * @param pde
	 * @return
	 */
	public static PurseEnum getTradeType(PubDriverExpenses pde){
		return TRADETYPE_RECHARGE.code.equals(pde.getTradetype())?TRADETYPE_RECHARGE :
					TRADETYPE_SETTLE.code.equals(pde.getTradetype())?TRADETYPE_SETTLE :
					TRADETYPE_REFUND.code.equals(pde.getTradetype())?TRADETYPE_REFUND :
					TRADETYPE_DEPOSIT.code.equals(pde.getTradetype())?TRADETYPE_DEPOSIT: 
					TRADETYPE_ORDER.code.equals(pde.getTradetype())?TRADETYPE_ORDER: 
					TRADETYPE_RECHARGE;
	}
	
	/**
	 * 获取交易渠道
	 * @param pde
	 * @return
	 */
	public static PurseEnum getExpenseType(PubDriverExpenses pde){
		return EXPENSETYPE_WEIXIN.code.equals(pde.getExpensetype())?EXPENSETYPE_WEIXIN  :
					EXPENSETYPE_ALIPAY.code.equals(pde.getExpensetype())?EXPENSETYPE_ALIPAY  :
					EXPENSETYPE_BALANCE.code.equals(pde.getExpensetype())?EXPENSETYPE_BALANCE  :
					EXPENSETYPE_WITHDRAW.code.equals(pde.getExpensetype())?EXPENSETYPE_WITHDRAW  : 
					EXPENSETYPE_DRAWIN.code.equals(pde.getExpensetype()) ? EXPENSETYPE_DRAWIN : 
					EXPENSETYPE_WEIXIN;
	}
	
	/**
	 * 获取明细类型
	 * @param pde
	 * @return
	 */
	public static PurseEnum getDetailType(PubDriverExpenses pde){
		return DETAILTYPE_BALANCE.code.equals(pde.getDetailtype())?DETAILTYPE_BALANCE :
					DETAILTYPE_ALLTRANS.code.equals(pde.getDetailtype())?DETAILTYPE_ALLTRANS :
					DETAILTYPE_BALANCE;
	}
	
	/**
	 * 获取交易标识
	 * @param pde
	 * @return
	 */
	public static PurseEnum getTransSign(PubDriverExpenses pde){
		return TRADETYPE_RECHARGE.code.equals(pde.getTradetype())?TRANSSIGN_TAKEIN :
					TRADETYPE_SETTLE.code.equals(pde.getTradetype())?TRANSSIGN_TAKEOUT :
					TRADETYPE_REFUND.code.equals(pde.getTradetype())?TRANSSIGN_TAKEIN :
					TRADETYPE_DEPOSIT.code.equals(pde.getTradetype())?TRANSSIGN_TAKEOUT : 
					TRANSSIGN_TAKEIN;
	}
	
	public String code;
	public String msg;
	PurseEnum(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
}

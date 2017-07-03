package com.szyciov.entity;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.szyciov.util.StringUtil;

/**
 * @ClassName WithDrawNO 
 * @author 
 * @Description 
 * <pre>
 * 提现编号类
 * 说明：
 * 提现编号规则：
 * 1、提现编号：申请来源+申请日期+申请流水，示例：J1601011800001，合计14位字符；
 * 2、申请来源：乘客端：C；司机端：S ；机构端：J；
 * 3、申请日期：年月日时，示例：16010118，即16年1月1日18时；
 * 4、申请流水：五位数字，示例00001，即第一单；
 * </pre>
 * @date 2017年03月17日 上午11:43:35
 */
public class WithDrawNO {
	private Logger logger = LoggerFactory.getLogger(WithDrawNO.class);
	/**
	 * 流水号长度
	 */
	private int FLOWNO_LEN = 5; 
	/**
	 * 生成日期(yyMMddHH)
	 */
	private String dateTime;
	/**
	 * 流水号
	 */
	private int flowNO;
	/**
	 * 订单来源
	 */
	private OrderSource4WithdrawNO orderSource;
	/**
	 * 单例对象
	 */
	private static WithDrawNO ins;
	/**
	 * 提现编号
	 */
	private String currentWithDrawNo;
	
	/**
	 * 默认构造
	 */
	private WithDrawNO() {}
	
	public static WithDrawNO getInstance(){
		if(ins == null){
			ins = new WithDrawNO();
		}
		return ins;
	}
	
	/**
	 * 判断是否已经初始化
	 * @return
	 */
	public static boolean hasInstance() {
		if(ins == null) {
			return false;	
		}
		return true;
	}
	
	/**
	 * 重置
	 */
	public static void reset(){
		ins = null;
	} 
	
	/**
	 * 初始化
	 * @param orderno
	 */
	public void init(String orderno){
		//如果最后一条记录不是当前日期,则重置为0;
		if(orderno == null || !StringUtil.formatDate(new Date(), "yyMMddHH").equals(orderno.substring(1,9))){
			flowNO = 0;
		}else{   
			flowNO = Integer.parseInt(orderno.substring(9,14));
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		//下单来源(0为乘客端，1为机构端，2为租赁端，3为运管端)
		sb.append(orderSource.code);
		//订单号生成时间(yyMMddHH)
		dateTime = StringUtil.formatDate(new Date(), "yyMMddHH");
		sb.append(dateTime);
		//流水号
		flowNO = flowNO+1;
		sb.append(StringUtil.formatNumToLength(flowNO, FLOWNO_LEN, null));
		currentWithDrawNo = sb.toString();
		return sb.toString();
	}

	/**
	 * 获取订单号
	 * @return
	 */
	public String getOrderNO(OrderSource4WithdrawNO orderSource){
		this.orderSource = orderSource;
		return ins.toString();
	}
	
	/**  
	 * 设置订单来源  
	 * @param orderSource 订单来源  
	 */
	public void setOrderSource(OrderSource4WithdrawNO orderSource) {
		this.orderSource = orderSource;
	}
	
	public String getCurrentWithDrawNo() {
		return currentWithDrawNo;
	}

	public void setCurrentWithDrawNo(String currentWithDrawNo) {
		this.currentWithDrawNo = currentWithDrawNo;
	}

	public static void main(String[] args) {
		String orderNO = "J1611290900001";
		WithDrawNO.getInstance().init(orderNO);
		orderNO = WithDrawNO.getInstance().getOrderNO(OrderSource4WithdrawNO.ORGAN);
		System.out.println(orderNO);
		orderNO = WithDrawNO.getInstance().getOrderNO(OrderSource4WithdrawNO.ORGAN);
		System.out.println(orderNO);
	}
	
}

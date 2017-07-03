package com.szyciov.entity;

import java.util.Date;

import org.springframework.http.HttpMethod;

import com.szyciov.enums.OrderVarietyEnum;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.StringUtil;
import com.szyciov.util.TemplateHelper4CarServiceApi;

import net.sf.json.JSONObject;

/**
 * @ClassName OrderNO 
 * @author Efy Shu
 * @Description 
 * <pre>
 * 订单号类
 * 说明：
 * 1、订单编号：下单来源+下单日期+订单流水，示例：BJ1601011800001，合计15位数字；
 * 2、下单来源：乘客端（机构用户：BC、个人用户：CC）；机构端：BJ；租赁端：BZ；运管端：CY
 * 3、下单日期：年月日时，示例：16010118，即16年1月1日18时；
 * 4、订单流水：五位数字，示例00001，即第一单；
 * 租赁端：ZA0120160810000012 
 * 运管端：YA0120160831000003
 * </pre>
 * @date 2016年11月26日 下午7:00:35
 */
public class OrderNO {
//	private Logger logger = LoggerFactory.getLogger(OrderNO.class);
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
	private OrderSource4OrderNO orderSource;
	/**
	 * 订单类型
	 */
	private OrderVarietyEnum orderVariety;
	
	public static OrderNO getInstance(){
		return new OrderNO();
	}
	
	/**
	 * 初始化
	 * @param orderno
	 */
	public void init(String orderno){
		//如果最后一条记录不是当前日期,则重置为0;
		if(orderno == null || !StringUtil.formatDate(new Date(), "yyMMddHH").equals(orderno.substring(3,11))){
			flowNO = 0;
		}else if(flowNO == 0){   //如果flowNO != 0 则表示已经初始化过,再次初始化将不起作用
			flowNO = Integer.parseInt(orderno.substring(11));
		}
	}
	
	/**
	 * 初始化redis中的订单号(已废弃)
	 * @deprecated
	 */
	public void initOrderNO(){
		TemplateHelper4CarServiceApi tp = new TemplateHelper4CarServiceApi();
		tp.dealRequestWithToken("/OrderApi/UpdateNewestOrderNO", HttpMethod.GET, null, null, JSONObject.class);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		//下单来源
		sb.append(orderSource.code + orderVariety.variety);
		//订单号生成时间(yyMMddHH)
		sb.append(dateTime);
		//流水号
		sb.append(StringUtil.formatNumToLength(flowNO, FLOWNO_LEN, null));
		return sb.toString();
	}
	
	/**
	 * 获取最新流水号
	 */
	private void getNewestOrderNO(){
		//生成当前时间(小时)段
		dateTime = StringUtil.formatDate(new Date(), "yyMMddHH");
		//获取redis中当前时间段的最新流水号(key : BJI17051610)
		String key = orderSource.code + orderVariety.variety + dateTime;
		flowNO = (int)JedisUtil.getFlowNO(key);
		//如果当前时间段的流水号为第一单,则初始化key的超时时间(1小时)
		if(flowNO == 1){
			JedisUtil.expire(key, 3600);
		}
	}
	
	/**
	 * 判断是否是出租车
	 * @param usetype
	 * @param ordertype
	 * @return
	 */
	public static boolean isTaxiOrder(String usetype, String ordertype){
		OrderVarietyEnum orderVariety = OrderVarietyEnum.getOrderVariety(usetype, ordertype);
		if(orderVariety == OrderVarietyEnum.OPERATING_TAXI) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取订单号
	 * @return
	 */
	public String getOrderNO(OrderSource4OrderNO orderSource, OrderVarietyEnum orderVariety){
		this.orderSource = orderSource;
		this.orderVariety = orderVariety;
		getNewestOrderNO();
		return this.toString();
	}
	
	/**  
	 * 设置订单来源  
	 * @param orderSource 订单来源  
	 */
	public void setOrderSource(OrderSource4OrderNO orderSource) {
		this.orderSource = orderSource;
	}

	public OrderVarietyEnum getOrderVariety() {
		return orderVariety;
	}

	public void setOrderVariety(OrderVarietyEnum orderVariety) {
		this.orderVariety = orderVariety;
	}
	
	public static void main(String[] args) {
		OrderNO no = new OrderNO();
		String orderno = no.getOrderNO(OrderSource4OrderNO.LEASE_PRI, OrderVarietyEnum.LEASE_NET);
		System.out.println(orderno);
	}
}

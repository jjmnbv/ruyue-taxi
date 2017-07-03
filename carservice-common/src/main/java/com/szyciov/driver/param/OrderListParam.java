package com.szyciov.driver.param;

import com.szyciov.annotation.SzycValid;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;

/**
 * 获取订单列表参数类
 * @ClassName OrderListParam
 * @author Efy Shu
 * @Description 获取订单列表参数类
 * @date 2016年9月26日 上午10:29:02
 */
public class OrderListParam extends BaseParam {
	/**
	 * 订单状态(1-当前 2-已完成 3-已取消 0-服务中)
	 */
	@SzycValid(rules={"checkOrderListType"})
	private int type;

	/**
	 * 司机ID
	 */
	private String driverid;

	/**
	 * 起始时间
	 */
	@SzycValid(rules={"checkNull","isDate"})
	private String starttime;

	/**
	 * 终止时间
	 */
	@SzycValid(rules={"checkNull","isDate"})
	private String endtime;

	/**
	 * 订单状态列表
	 */
	private String statuslist;

	/** 已完成订单状态 **/
    private String statusServiceDone = OrderState.SERVICEDONE.state;

    /** 已取消订单状态 **/
    private String statusServiceCancel = OrderState.CANCEL.state;

	/**
	 * 订单支付状态列表
	 */
	private String paystatuslist;
	/**
	 * 出租车订单状态列表
	 */
	private String taxistatuslist;

	/**
	 * 出租车支付状态列表
	 */
	private String taxipaystatuslist;
	/**
	 * 是否是获取今日订单
	 */
	private boolean today = false;

	/**
	 * 获取订单状态列表
	 * @return statuslist 订单状态列表
	 */
	public String getStatuslist() {
		StringBuffer sb = new StringBuffer();
		sb.append("'");
		switch (type) {
		case 1:
			sb.append(OrderState.WAITSTART.state).append("','");
			sb.append(OrderState.START.state).append("','");
			sb.append(OrderState.ARRIVAL.state).append("','");
			sb.append(OrderState.PICKUP.state).append("','");
			sb.append(OrderState.INSERVICE.state);
			break;
		case 2:
			sb.append(OrderState.SERVICEDONE.state);
			break;
		case 3:
			sb.append(OrderState.SERVICEDONE.state).append("','");
			sb.append(OrderState.CANCEL.state);
			break;
		default:
			sb.append(OrderState.START.state).append("','");
			sb.append(OrderState.ARRIVAL.state).append("','");
			sb.append(OrderState.PICKUP.state).append("','");
			sb.append(OrderState.INSERVICE.state);
			break;
		}
		sb.append("'");
		statuslist = sb.toString();
		return statuslist;
	}


	/**
	 * 获取订单支付状态列表
	 * @return paystatuslist 订单支付状态列表
	 */
	public String getPaystatuslist() {
		StringBuffer sb = new StringBuffer();
		sb.append("'");
		switch (type) {
		case 1: //当前(未支付)
			sb.append(PayState.MENTED.state).append("','");
			sb.append(PayState.NOTPAY.state);
			break;
		case 2: //待付结(未支付)
			if(today){  //如果是今日订单(2-表示已完成,含已支付订单)
				sb.append(PayState.PAYED.state).append("','");
				sb.append(PayState.MENTED.state).append("','");
				sb.append(PayState.STATEMENTING.state).append("','");
				sb.append(PayState.STATEMENTED.state).append("','");
			}
			/**
			 * 关于 待付结 的说明：
			 * 1、“未结算”、“结算中”订单状态不再显示
			 * 未支付：（1）网约车业务因公用车：“个人垫付”订单服务结束后，行程费未支付前
			 *               （2）网约车业务因私或toC用车：订单服务结束后，行程费未支付前
			 */
			sb.append(PayState.NOTPAY.state);
			break;
		case 3:  //已完成(已支付,未结算,结算中,已结算)
			sb.append(PayState.PAYED.state).append("','");
			sb.append(PayState.MENTED.state).append("','");
			sb.append(PayState.STATEMENTING.state).append("','");
			sb.append(PayState.STATEMENTED.state);
			break;
		default://默认服务中(未结算,未支付)
			sb.append(PayState.MENTED.state).append("','");
			sb.append(PayState.NOTPAY.state);
			break;
		}
		sb.append("'");
		paystatuslist = sb.toString();
		return paystatuslist;
	}




	/**
	 * 获取出租车支付状态列表
	 * @return taxipaystatuslist 出租车支付状态列表
	 */
	public String getTaxipaystatuslist() {
		StringBuffer sb = new StringBuffer();
		sb.append("'");
		switch (type) {
		case 1: //当前(未支付,未付结,未结算)
			sb.append(PayState.NOTPAY.state).append("','");
			sb.append(PayState.PASSENGERNOPAY.state).append("','");
			sb.append(PayState.MENTED.state);
			break;
		case 2: //待付结(未支付,未结算,乘客未付)
			if(today){  //如果是今日订单(2-表示已完成,含已支付,已付结,已结算订单)
				sb.append(PayState.PAYED.state).append("','");
				sb.append(PayState.PAYOVER.state).append("','");
				sb.append(PayState.STATEMENTED.state).append("','");
			}
			/**
			 *	关于 待付结 的说明：
			 *	1、订单来源：取所有行程费乘客未支付订单（含在线支付、线下付现）
			 *	2、订单状态：订单列表页、订单详情页，订单统一标记为“未支付”
			 */
			sb.append(PayState.NOTPAY.state).append("','");
			sb.append(PayState.MENTED.state);
			break;
		case 3:  //已完成(已支付,已付结,已结算),已取消(未支付,乘客未付)
			/**
			 * 关于 已完成 的说明：
			 * 1、订单来源：取所有行程费乘客已支付订单（含在线支付、线下付现）
			 * 2、订单状态：订单列表页、订单详情页，订单统一标记为“已支付”
			 */
//			sb.append(PayState.NOTPAY.state).append("','");
			sb.append(PayState.PASSENGERNOPAY.state).append("','");
			sb.append(PayState.PAYED.state).append("','");
			sb.append(PayState.PAYOVER.state).append("','");
			sb.append(PayState.STATEMENTED.state);
			break;
		default://默认服务中(未支付,未付结,未结算)
			sb.append(PayState.NOTPAY.state).append("','");
			sb.append(PayState.PASSENGERNOPAY.state).append("','");
			sb.append(PayState.MENTED.state);
			break;
		}
		sb.append("'");
		taxipaystatuslist = sb.toString();
		return taxipaystatuslist;
	}

	/**
	 * 获取出租车订单状态列表
	 * @return taxistatuslist 出租车订单状态列表
	 */
	public String getTaxistatuslist() {
		StringBuffer sb = new StringBuffer();
		sb.append("'");
		switch (type) {
		case 1:
			sb.append(OrderState.WAITSTART.state).append("','");
			sb.append(OrderState.START.state).append("','");
			sb.append(OrderState.ARRIVAL.state).append("','");
			sb.append(OrderState.PICKUP.state).append("','");
			sb.append(OrderState.INSERVICE.state);
			break;
		case 2:
			sb.append(OrderState.SERVICEDONE.state);
			break;
		case 3:
			sb.append(OrderState.SERVICEDONE.state).append("','");
			sb.append(OrderState.CANCEL.state);
			break;
		default:  //默认返回服务中(包含待确费)
			sb.append(OrderState.START.state).append("','");
			sb.append(OrderState.ARRIVAL.state).append("','");
			sb.append(OrderState.PICKUP.state).append("','");
			sb.append(OrderState.INSERVICE.state).append("','");
			sb.append(OrderState.WAITMONEY.state);
			break;
		}
		sb.append("'");
		taxistatuslist = sb.toString();
		return taxistatuslist;
	}


	/**
	 * 获取订单状态(1-当前 2-已完成 3-已取消 其他-服务中)
	 * @return type 订单状态(1-当前 2-已完成 3-已取消 其他-服务中)
	 */
	public int getType() {
		return type;
	}

	/**
	 * 设置订单状态(1-当前 2-已完成 3-已取消 其他-服务中)
	 * @param type 订单状态(1-当前 2-已完成 3-已取消 其他-服务中)
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 获取司机ID
	 * @return driverid 司机ID
	 */
	public String getDriverid() {
		return driverid;
	}

	/**
	 * 设置司机ID
	 * @param driverid 司机ID
	 */
	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	/**
	 * 获取起始时间
	 * @return starttime 起始时间
	 */
	public String getStarttime() {
		return starttime;
	}

	/**
	 * 设置起始时间
	 * @param starttime 起始时间
	 */
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}



	/**
	 * 获取终止时间
	 * @return endtime 终止时间
	 */
	public String getEndtime() {
		return endtime;
	}



	/**
	 * 设置终止时间
	 * @param endtime 终止时间
	 */
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	/**
	 * 设置订单状态列表
	 * @param statuslist 订单状态列表
	 */
	public void setStatuslist(String statuslist) {
		this.statuslist = statuslist;
	}


	/**
	 * 设置出租车订单状态列表
	 * @param taxistatuslist 出租车订单状态列表
	 */
	public void setTaxistatuslist(String taxistatuslist) {
		this.taxistatuslist = taxistatuslist;
	}


	/**
	 * 设置出租车支付状态列表
	 * @param taxipaystatuslist 出租车支付状态列表
	 */
	public void setTaxipaystatuslist(String taxipaystatuslist) {
		this.taxipaystatuslist = taxipaystatuslist;
	}


	/**
	 * 获取是否是获取今日订单
	 * @return today 是否是获取今日订单
	 */
	public boolean isToday() {
		return today;
	}

	/**
	 * 设置是否是获取今日订单
	 * @param today 是否是获取今日订单
	 */
	public void setToday(boolean today) {
		this.today = today;
	}

	/**
	 * 设置订单支付状态列表
	 * @param paystatuslist 订单支付状态列表
	 */
	public void setPaystatuslist(String paystatuslist) {
		this.paystatuslist = paystatuslist;
	}

    public String getStatusServiceDone() {
        return statusServiceDone;
    }

    public String getStatusServiceCancel() {
        return statusServiceCancel;
    }
}

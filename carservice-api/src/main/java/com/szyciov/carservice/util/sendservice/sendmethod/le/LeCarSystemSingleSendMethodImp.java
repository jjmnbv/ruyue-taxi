package com.szyciov.carservice.util.sendservice.sendmethod.le;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.szyciov.carservice.service.OrderApiService;
import com.szyciov.carservice.service.SendInfoService;
import com.szyciov.carservice.util.OrderRedisMessageFactory;
import com.szyciov.carservice.util.OrderRedisMessageFactory.RedisMessageType;
import com.szyciov.carservice.util.sendservice.sendmethod.SendMethodHelper;
import com.szyciov.carservice.util.sendservice.sendmethod.log.SendLogMessage;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.carservice.util.sendservice.sendrules.impl.le.LeCarSystemSingleSendRuleImp;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.PlatformType;
import com.szyciov.enums.VehicleEnum;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.util.ApiExceptionHandle;

/**
 * 纯人工派单实现类
 * @ClassName LeCarSystemSingleSendMethodImp 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2017年6月1日 下午6:22:36
 */
public class LeCarSystemSingleSendMethodImp extends ApiExceptionHandle implements SendMethodHelper{

	private static final Logger logger = LoggerFactory.getLogger(LeCarSystemSingleSendMethodImp.class);
	
	private SendInfoService sendInfoService;
	
	private OrderApiService orderApiService;

	public LeCarSystemSingleSendMethodImp(){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();  
		this.sendInfoService = (SendInfoService) wac.getBean("sendInfoService");
		this.orderApiService = (OrderApiService)wac.getBean("OrderApiService");
	}
	
	/**
	 * 抢单派单规则
	 */
	public void send(SendRuleHelper rules, AbstractOrder order) {
		if(rules==null||order==null){
			logger.info("信息不足，无法派单");
			return ;
		}
		LeCarSystemSingleSendRuleImp temprules = (LeCarSystemSingleSendRuleImp)rules;
		String usetype = temprules.getUsetype();
		if(SendRuleHelper.USE_TYPE_USENOW.equalsIgnoreCase(usetype)){
			//即可用车派单
			send_UseNow(temprules,order);
		}else if(SendRuleHelper.USE_TYPE_RESERVE.equalsIgnoreCase(usetype)){
			//预约用车派单
			send_Reserve(temprules,order);
		}
	}

	/**
	 * 纯人工模式即刻单派单
	 * @param temprules
	 * @param order
	 */
	private void send_UseNow(LeCarSystemSingleSendRuleImp rule, AbstractOrder orderinfo) {
		if(isOrderTakedOrCancel(orderinfo.getOrderno())) return;
		doNoMoreDriver(rule,orderinfo);
	}
	
	/**
	 * 纯人工模式预约单派单
	 */
	public void send_Reserve(LeCarSystemSingleSendRuleImp rule,AbstractOrder orderinfo){
		if(isOrderTakedOrCancel(orderinfo.getOrderno())) return;
		doNoMoreDriver(rule,orderinfo);
	}
	
	/**
	 * 订单是否被接走
	 * @param orderno
	 * @return
	 */
	private boolean isOrderTakedOrCancel(String orderno){
		//查看订单有没有司机接单或者取消掉
		AbstractOrder orderinfo = sendInfoService.getOrgOrderByNo(orderno);
		String orderstatus = orderinfo.getOrderstatus();
		if(OrderState.CANCEL.state.equals(orderstatus)){
			logger.info("订单已取消");
			return true;
		//不属于系统派单或者人工派单状态的订单就表示被接走了
		}else if(!(OrderState.WAITTAKE.state.equals(orderstatus) ||
				OrderState.MANTICSEND.state.equals(orderstatus))){
			logger.info("订单已被接走");
			return true;
		}
		return false;
	}
	
	/**
	 * 订单进入人工派单
	 * @param orderinfo
	 */
	private void go2Mantic(AbstractOrder orderinfo){
		sendInfoService.go2Mantic(orderinfo);
		sendRedisMessage(orderinfo,OrderState.MANTICSEND.state);
	}

	/**
	 * 添加弹窗消息，仅限租赁端网约车
	 * @param orderinfo
	 * @param state
	 */
	private void sendRedisMessage(AbstractOrder orderinfo,String state){
		String[] m = new String[]{orderinfo.getOrderno()};
		SendLogMessage.toRedisMessage(logger,this.getClass(),m);
		OrderInfoDetail order = convert2OrderInfoDetail(orderinfo);
		order.setStatus(state);
		OrderRedisMessageFactory rmf = new OrderRedisMessageFactory(RedisMessageType.MANTICORDER, PlatformType.LEASE);
		rmf.setOrder(order);
		rmf.createMessage(orderApiService);
		rmf.sendMessage();
	}
	
	/**
	 * 转换AbstractOrder成OrderInfoDetail
	 * @param orderinfo
	 * @return
	 */
	private OrderInfoDetail convert2OrderInfoDetail(AbstractOrder orderinfo){
		OrgUser orgUser = sendInfoService.getOrgUser(orderinfo.getUserid());
		OrderInfoDetail order = new OrderInfoDetail();
		order.setOrderno(orderinfo.getOrderno());
		order.setCartype(orderinfo.getSelectedmodelname());
		order.setType(orderinfo.getOrdertype());
		order.setStatus(orderinfo.getOrderstatus());
		order.setCityid(orderinfo.getOncity());
		order.setCompanyid(orderinfo.getCompanyid());
		order.setOrderprop(0);
		order.setPaymethod(((OrgOrder)orderinfo).getPaymethod());
		order.setUsetime(orderinfo.getUsetime());
		order.setPassengers(orderinfo.getPassengers());
		order.setPassengerphone(orderinfo.getPassengerphone());
		order.setOrganid(((OrgOrder)orderinfo).getOrganid());
		//车辆类型 网约车
		order.setOrderstyle(VehicleEnum.VEHICLE_TYPE_CAR.code);
		if(orgUser!=null) {
			order.setUsername(orgUser.getNickName());
			order.setUserphone(orgUser.getAccount());
		}
		return order;
	}
	
	/**
	 * 没有可用司机的处理逻辑
	 * @param rule
	 * @param orderinfo
	 */
	private void doNoMoreDriver(LeCarSystemSingleSendRuleImp rule, AbstractOrder orderinfo){
		//进入人工派单
		orderinfo.setOrderstatus(OrderState.MANTICSEND.state);
		orderinfo.setPushnumber(0);
		go2Mantic(orderinfo);
	}
	
}

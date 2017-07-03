package com.szyciov.carservice.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.carservice.mapper.SendInfoMapper;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubSendrules;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.PeUser;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.param.PubSendRuleQueryParam;
import com.szyciov.param.SendOrderDriverQueryParam;
import com.szyciov.param.TaxiDriverInBoundParam;
import com.szyciov.util.StringUtil;

@Repository("sendInfoDao")
public class SendInfoDao {
	
	private SendInfoMapper sendInfoMapper;

	@Resource
	public void setSendInfoMapper(SendInfoMapper sendInfoMapper) {
		this.sendInfoMapper = sendInfoMapper;
	}
	
	/**
	 * 获取出租车预约用车规则
	 * @param param
	 * @return
	 */
	public Map<String,Object> getReserveRuleInfo4OpTaxi(Map<String,Object> param){
		return sendInfoMapper.getReserveRuleInfo4OpTaxi(param);
	}
	
	/**
	 * 获取出租车即可用车规则
	 * @param param
	 * @return
	 */
	public Map<String,Object> getUseNowRuleInfo4OpTaxi(Map<String,Object> param){
		return sendInfoMapper.getUseNowRuleInfo4OpTaxi(param);
	}

	/**
	 * 获取网约车用车规则
	 * @param param
	 * @return
	 */
	public PubSendrules getReserveRuleInfo4LeCar(PubSendrules rule){
		return sendInfoMapper.getRuleInfo4Car(rule);
	}

	/**
	 * 获取出租车即可用车规则
	 * @param param
	 * @return
	 */
	public PubSendrules getUseNowRuleInfo4LeCar(PubSendrules rule){
		return sendInfoMapper.getRuleInfo4Car(rule);
	}

	/**
	 * 获取范围内所有的出租车司机
	 * @param param
	 * @return
	 */
	public List<PubDriver> getDriversInBound4Taxi(TaxiDriverInBoundParam param) {
		return sendInfoMapper.getDriversInBound4Taxi(param);
	}

	/**
	 * 获取运管订单司机
	 * @param param
	 * @return
	 */
	public List<PubDriver> listOpOrderDriver(SendOrderDriverQueryParam param){
		return sendInfoMapper.listOpOrderDriver(param);
	}

	/**
	 * 获取运管预约单订单司机
	 * @param param
	 * @return
	 */
	public List<PubDriver> listOpOrderDriver4Reverve(SendOrderDriverQueryParam param){
		return sendInfoMapper.listOpOrderDriver4Reverve(param);
	}


	/**
	 * 返回运管端用户信息
	 * @param userid
	 * @return
	 */
	public PeUser getPeUser(String userid){
		return sendInfoMapper.getPeUser(userid);
	}
	
	/**
	 * 返回机构端用户信息
	 * @param userid
	 * @return
	 */
	public OrgUser getOrgUser(String userid){
		return sendInfoMapper.getOrgUser(userid);
	}

	public void forceOrder4Driver(OpTaxiOrder orderinfo) {
		sendInfoMapper.forceOrder4Driver(orderinfo);
	}

	/**
	 * 运管端网约车强派订单给司机
	 * @param orderinfo
	 */
	public void opCarOrderForce2Driver(OpOrder orderinfo) {
		sendInfoMapper.opCarOrderForce2Driver(orderinfo);
	}
	/**
	 * 租赁端网约车强派订单给司机
	 * @param orderinfo
	 */
	public void leCarOrderForce2Driver(OrgOrder orderinfo) {
		sendInfoMapper.leCarOrderForce2Driver(orderinfo);
	}

	public void forceOrderCancel(OpTaxiOrder orderinfo) {
		sendInfoMapper.forceOrderCancel(orderinfo);
	}

	/**
	 * 运管端网约车取消订单(派单时取消)
	 * @param orderinfo
	 */
	public void opCarOrderCancel(OpOrder orderinfo) {
		sendInfoMapper.opCarOrderCancel(orderinfo);
	}

	/**
	 * 租赁端网约车取消订单(派单时取消)
	 * @param orderinfo
	 */
	public void leCarOrderCancel(OrgOrder orderinfo) {
		sendInfoMapper.leCarOrderCancel(orderinfo);
	}

	public void go2Mantic(OpTaxiOrder orderinfo) {
		sendInfoMapper.go2Mantic(orderinfo);
	}
	
	public void opGo2Mantic(OpOrder orderinfo) {
		sendInfoMapper.opGo2Mantic(orderinfo);
	}
	
	public void orgGo2Mantic(OrgOrder orderinfo) {
		sendInfoMapper.orgGo2Mantic(orderinfo);
	}

	public List<OpTaxiOrder> getInServiceOrUseNowOrder4OpTaxiDriver(String driverid) {
		return sendInfoMapper.getInServiceOrUseNowOrder4OpTaxiDriver(driverid);
	}

	public List<OrderInfoDetail> getCurrentOrderByDriver(PubDriver driver) {
		return sendInfoMapper.getCurrentOrderByDriver(driver);
	}

	public List<OpOrder> listInServiceOrUseNowCarDriver(String driverid){
		return sendInfoMapper.listInServiceOrUseNowCarDriver(driverid);
	}

	public List<OpOrder> listReverceOrders4CarDriver(String driverid){
		return sendInfoMapper.listReverceOrders4CarDriver(driverid);
	}

	public OpTaxiOrder getLastReverceOrder4OpTaxiDriver(String driverid) {
		return sendInfoMapper.getLastReverceOrder4OpTaxiDriver(driverid);
	}

	public OrgOrder getLastReverceOrgOrderByDriver(PubDriver driver) {
		return sendInfoMapper.getLastReverceOrgOrderByDriver(driver);
	}

	public AbstractOrder getOpTaxiOrderByNo(String orderno) {
		return sendInfoMapper.getOpTaxiOrderByNo(orderno);
	}

	public AbstractOrder getOrgOrderByNo(String orderno) {
		return sendInfoMapper.getOrgOrderByNo(orderno);
	}

	public String getOpCarOrderStatus(String orderno) {
		return sendInfoMapper.getOpCarOrderStatus(orderno);
	}

	public String getOpTaxiOrderStatus(String orderno) {
		return sendInfoMapper.getOpTaxiOrderStatus(orderno);
	}




	public List<PubDriver> getDriversInBound4TaxiKF(TaxiDriverInBoundParam param) {
		return sendInfoMapper.getDriversInBound4TaxiKF(param);
	}

	public List<PubDriver> getDriversInBound4TaxiKFL(TaxiDriverInBoundParam param) {
		return sendInfoMapper.getDriversInBound4TaxiKFL(param);
	}

	public List<OpTaxiOrder> getReverceOrders4OpTaxiDriver(String driverid) {
		return sendInfoMapper.getReverceOrders4OpTaxiDriver(driverid);
	}

	public List<OrgOrder> getReverceOrdersByDriver(PubDriver driver) {
		return sendInfoMapper.getReverceOrdersByDriver(driver);
	}

	public List<String> getDriverUnServiceTimesByDay(String driverid) {
		return sendInfoMapper.getDriverUnServiceTimesByDay(driverid);
	}

	public List<OrgOrder> getReverceOrdersInDay(Date usetime) {
		Date tomorrow = StringUtil.getTomorrow(usetime);
		return sendInfoMapper.getReverceOrdersInDay(usetime,tomorrow);
	}
	public List<String> listCarDriverUnServiceTimes(String driverid) {
		return sendInfoMapper.listCarDriverUnServiceTimes(driverid);
	}

	public PubSendrules getSendRule(PubSendRuleQueryParam param){
		return sendInfoMapper.getSendRule(param);
	}

	public List<String> listOpvehiclemodelId(String selectedModelId,Integer nextCount){
		return sendInfoMapper.listOpvehiclemodelId(selectedModelId,nextCount);
	}


	public void forceOpOrder4Driver(AbstractOrder orderinfo){
		sendInfoMapper.forceOpOrder4Driver(orderinfo);
	}

	public void forceOpOrderCancel(AbstractOrder orderinfo){
		sendInfoMapper.forceOpOrderCancel(orderinfo);
	}

	public void opOrder2Mantic(AbstractOrder orderinfo){
		sendInfoMapper.opOrder2Mantic(orderinfo);
	}

}

package com.szyciov.carservice.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.SendInfoDao;
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

/**
 * 派单服务的信息获取服务
 * @author zhu
 *
 */
@Service("sendInfoService")
public class SendInfoService {
	
	private SendInfoDao sendInfoDao;
	
	@Resource(name="sendInfoDao")
	public void setSendInfoDao(SendInfoDao sendInfoDao) {
		this.sendInfoDao = sendInfoDao;
	}
	
	/**
	 * 获取出租车预约用车规则
	 * @param param
	 * @return
	 */
	public Map<String,Object> getReserveRuleInfo4OpTaxi(Map<String,Object> param){
		return sendInfoDao.getReserveRuleInfo4OpTaxi(param);
	}

	/**
	 * 获取网约车预约用车规则
	 * @param param
	 * @return
	 */
	public PubSendrules getReserveRuleInfo4LeCar(PubSendrules rule){
		rule.setUsetype("0");
		return sendInfoDao.getReserveRuleInfo4LeCar(rule);
	}

	/**
	 * 获取网约车即可用车规则
	 * @param param
	 * @return
	 */
	public PubSendrules getUseNowRuleInfo4LeCar(PubSendrules rule){
		rule.setUsetype("1");
		return sendInfoDao.getUseNowRuleInfo4LeCar(rule);
	}

	/**
	 * 返回运管端用户信息
	 * @param userid
	 * @return
	 */
	public PeUser getPeUser(String userid){
		return sendInfoDao.getPeUser(userid);
	}
	
	/**
	 * 返回机构端用户信息
	 * @param userid
	 * @return
	 */
	public OrgUser getOrgUser(String userid){
		return sendInfoDao.getOrgUser(userid);
	}


	/**
	 * 获取出租车即可用车规则
	 * @param param
	 * @return
	 */
	public Map<String,Object> getUseNowRuleInfo4OpTaxi(Map<String,Object> param){
		return sendInfoDao.getUseNowRuleInfo4OpTaxi(param);
	}

	/**
	 * 获取范围内的所有的出租车司机信息
	 * @param param
	 * @return
	 */
	public List<PubDriver> getDriversInBound4Taxi(TaxiDriverInBoundParam param){
		return sendInfoDao.getDriversInBound4Taxi(param);
	}

	/**
	 * 获取运管订单司机
	 * @param param
	 * @return
	 */
	public List<PubDriver> listOpOrderDriver(SendOrderDriverQueryParam param){
		return sendInfoDao.listOpOrderDriver(param);
	}

	/**
	 * 获取运管预约单订单司机
	 * @param param
	 * @return
	 */
	public List<PubDriver> listOpOrderDriver4Reverve(SendOrderDriverQueryParam param){
		return sendInfoDao.listOpOrderDriver4Reverve(param);
	}

	/**
	 * 获取正在服务或者没开始的出租车即可订单
	 * @param id
	 * @return
	 */
	public List<OpTaxiOrder> getInServiceOrUseNowOrder4OpTaxiDriver(String driverid) {
		return sendInfoDao.getInServiceOrUseNowOrder4OpTaxiDriver(driverid);
	}
	/**
	 * 获取正在服务或者没开始的网约车即刻订单
	 * @param driverid 司机ID
	 * @return
	 */
	public List<OpOrder> listInServiceOrUseNow4CarDriver(String driverid){
		return sendInfoDao.listInServiceOrUseNowCarDriver(driverid);
	}

	/**
	 * 获取该司机待出发的预约订单
	 * @param driverid	司机ID
	 * @return
	 */
	public List<OpOrder> listReverceOrders4CarDriver(String driverid){
		return sendInfoDao.listReverceOrders4CarDriver(driverid);
	}


	/**
	 * 获取司机未服务订单
	 * @param id
	 * @return
	 */
	public List<OrderInfoDetail> getCurrentOrderByDriver(PubDriver driver) {
		return sendInfoDao.getCurrentOrderByDriver(driver);
	}

	/**
	 * 获取最近要开始的预约单
	 * @param id
	 * @return
	 */
	public OpTaxiOrder getLastReverceOrder4OpTaxiDriver(String driverid) {
		return sendInfoDao.getLastReverceOrder4OpTaxiDriver(driverid);
	}

	/**
	 * 获取最近要开始的预约单
	 * @param id
	 * @return
	 */
	public OrgOrder getLastReverceOrgOrderByDriver(PubDriver driver) {
		return sendInfoDao.getLastReverceOrgOrderByDriver(driver);
	}

	/**
	 * 强派订单给司机
	 * @param orderinfo
	 */
	public void forceOrder4Driver(AbstractOrder orderinfo) {
		if(orderinfo instanceof OpTaxiOrder){
			//运管端出租车订单
			sendInfoDao.forceOrder4Driver((OpTaxiOrder)orderinfo);
		}else if (orderinfo instanceof OpOrder) {
			//运管端网约车订单
			sendInfoDao.opCarOrderForce2Driver((OpOrder)orderinfo);
		}else if (orderinfo instanceof OrgOrder) {
			//租赁端网约车订单
			sendInfoDao.leCarOrderForce2Driver((OrgOrder)orderinfo);
		}
	}

	/**
	 * 强派订单取消
	 * @param orderinfo
	 */
	public void forceOrderCancel(AbstractOrder orderinfo) {
		if(orderinfo instanceof OpTaxiOrder){
			//运管端出租车订单
			sendInfoDao.forceOrderCancel((OpTaxiOrder)orderinfo);
		}else if (orderinfo instanceof OpOrder) {
			//运管端网约车订单
			sendInfoDao.opCarOrderCancel((OpOrder)orderinfo);
		}else if (orderinfo instanceof OrgOrder) {
			//租赁端网约车订单
			sendInfoDao.leCarOrderCancel((OrgOrder)orderinfo);
		}
	}

	/**
	 * 强派订单进入人工订单
	 * @param orderinfo
	 */
	public void go2Mantic(AbstractOrder orderinfo) {
		if(orderinfo instanceof OpTaxiOrder){
			//运管端出租车订单
			sendInfoDao.go2Mantic((OpTaxiOrder)orderinfo);
		}else if (orderinfo instanceof OrgOrder) {
			//租赁端网约车订单
			sendInfoDao.orgGo2Mantic((OrgOrder)orderinfo);
		}else if (orderinfo instanceof OpOrder) {
			//运管端网约车订单
			sendInfoDao.opGo2Mantic((OpOrder)orderinfo);
		}
	}

	/**
	 * 根据出租车订单
	 * @param orderno
	 * @return
	 */
	public AbstractOrder getOpTaxiOrderByNo(String orderno) {
		return sendInfoDao.getOpTaxiOrderByNo(orderno);
	}

	/**
	 * 根据出租车订单
	 * @param orderno
	 * @return
	 */
	public AbstractOrder getOrgOrderByNo(String orderno) {
		return sendInfoDao.getOrgOrderByNo(orderno);
	}

	public String getOpCarOrderStatus(String orderno) {
		return sendInfoDao.getOpCarOrderStatus(orderno);
	}


	public String getOpTaxiOrderStatus(String orderno) {
		return sendInfoDao.getOpTaxiOrderStatus(orderno);
	}

	public List<PubDriver> getDriversInBound4TaxiKF(TaxiDriverInBoundParam param) {
		return sendInfoDao.getDriversInBound4TaxiKF(param);
	}

	public List<PubDriver> getDriversInBound4TaxiKFL(TaxiDriverInBoundParam param) {
		return sendInfoDao.getDriversInBound4TaxiKFL(param);
	}

	public List<OpTaxiOrder> getReverceOrders4OpTaxiDriver(String driverid) {
		return sendInfoDao.getReverceOrders4OpTaxiDriver(driverid);
	}
	
	/**
	 * 租赁端网约车预约单
	 * @param driver
	 * @return
	 */
	public List<OrgOrder> getReverceOrdersByDriver(PubDriver driver) {
		return sendInfoDao.getReverceOrdersByDriver(driver);
	}

	/**
	 * 获取司机未服务的预约订单的使用时间集合
	 * @param driverid
	 * @return
	 */
	public List<String> getDriverUnServiceTimesByDay(String driverid){
		return sendInfoDao.getDriverUnServiceTimesByDay(driverid);
	}

	/**
	 * 获取司机当天的预约单
	 * @param driverid
	 * @return
	 */
	public List<OrgOrder> getReverceOrdersInDay(Date usetime){
		return sendInfoDao.getReverceOrdersInDay(usetime);
	}

	public List<String> listCarDriverUnServiceTimes(String driverid) {
		return sendInfoDao.listCarDriverUnServiceTimes(driverid);
	}

	public PubSendrules getSendRule(PubSendRuleQueryParam param){
		return sendInfoDao.getSendRule(param);
	}

	/**
	 * 返回运管端派单车型
	 * @param selectedModelId
	 * @param nextCount
	 * @return
	 */
	public List<String> listOpvehiclemodelId(String selectedModelId,Integer nextCount){
		return sendInfoDao.listOpvehiclemodelId(selectedModelId,nextCount);
	}



	public void forceOpOrder4Driver(AbstractOrder orderinfo){
		sendInfoDao.forceOpOrder4Driver(orderinfo);
	}

	public void forceOpOrderCancel(AbstractOrder orderinfo){
		sendInfoDao.forceOpOrderCancel(orderinfo);
	}

	public void opOrder2Mantic(AbstractOrder orderinfo){
		sendInfoDao.opOrder2Mantic(orderinfo);
	}

}

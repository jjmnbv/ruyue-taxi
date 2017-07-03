package com.szyciov.carservice.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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

public interface SendInfoMapper {
	
	PubSendrules getRuleInfo4Car(PubSendrules rule);

	Map<String, Object> getReserveRuleInfo4OpTaxi(Map<String, Object> param);

	Map<String, Object> getUseNowRuleInfo4OpTaxi(Map<String, Object> param);

	List<PubDriver> getDriversInBound4Taxi(TaxiDriverInBoundParam param);


	List<PubDriver> listOpOrderDriver4Reverve(SendOrderDriverQueryParam param);

	List<PubDriver> listOpOrderDriver(SendOrderDriverQueryParam param);

	void forceOrder4Driver(OpTaxiOrder orderinfo);

	void opCarOrderForce2Driver(OpOrder orderinfo);

	void leCarOrderForce2Driver(OrgOrder orderinfo);

	void forceOrderCancel(OpTaxiOrder orderinfo);

	void opCarOrderCancel(OpOrder orderinfo);

	void leCarOrderCancel(OrgOrder orderinfo);

	void go2Mantic(OpTaxiOrder orderinfo);
	
	void opGo2Mantic(OpOrder orderinfo);
	
	void orgGo2Mantic(OrgOrder orderinfo);

	List<OpTaxiOrder> getInServiceOrUseNowOrder4OpTaxiDriver(String driverid);

	List<OrderInfoDetail> getCurrentOrderByDriver(PubDriver driver);

	List<OpOrder> listInServiceOrUseNowCarDriver(String driverid);

	List<OpOrder> listReverceOrders4CarDriver(String driverid);

	OpTaxiOrder getLastReverceOrder4OpTaxiDriver(String driverid);

	OrgOrder getLastReverceOrgOrderByDriver(PubDriver driver);

	AbstractOrder getOpTaxiOrderByNo(String orderno);

	AbstractOrder getOrgOrderByNo(String orderno);

	String getOpCarOrderStatus(String orderno);

	String getOpTaxiOrderStatus(String orderno);




	List<PubDriver> getDriversInBound4TaxiKF(TaxiDriverInBoundParam param);

	List<PubDriver> getDriversInBound4TaxiKFL(TaxiDriverInBoundParam param);

	List<OpTaxiOrder> getReverceOrders4OpTaxiDriver(String driverid);

	List<OrgOrder> getReverceOrdersByDriver(PubDriver driver);

	List<String> getDriverUnServiceTimesByDay(String driverid);

	List<OrgOrder> getReverceOrdersInDay(@Param("usetime") Date usetime, @Param("tomorrow") Date tomorrow);

	List<String> listCarDriverUnServiceTimes(String driverid);


	PeUser getPeUser(String userid);
	
	OrgUser getOrgUser(String userid);

	PubSendrules getSendRule(PubSendRuleQueryParam param);

	List<String> listOpvehiclemodelId(@Param("selectedModelId") String selectedModelId,@Param("nextCount") Integer nextCount);


	void forceOpOrder4Driver(AbstractOrder orderinfo);

	void forceOpOrderCancel(AbstractOrder orderinfo);

	void opOrder2Mantic(AbstractOrder orderinfo);

}

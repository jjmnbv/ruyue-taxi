package com.szyciov.carservice.mapper;

import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.entity.OrderCost;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubJpushlog;
import com.szyciov.entity.PubSendrules;
import com.szyciov.entity.PubUserToken;
import com.szyciov.entity.TaxiOrderCost;
import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.OrgSendrecord;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.GetSendInfoParam;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpTaxisendrules;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.entity.PeUser;
import com.szyciov.op.entity.PeUserExpenses;
import com.szyciov.op.entity.PeUseraccount;
import com.szyciov.op.param.PeUserParam;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderReview;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.param.OrgUserParam;
import com.szyciov.param.OrderApiParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderApiMapper {
	LeLeasescompany getCompanyById(@Param("companyid") String companyid);
	
	OrderCost getOrgOrderCost(OrderCostParam param);

	OrderCost getOpOrderCost(OrderCostParam param);
	
	OrgOrder getOrgOrder(@Param("orderno") String orderno);
	
	OpOrder getOpOrder(@Param("orderno") String orderno);
	
	List<OrderInfoDetail> getOrderInfoList(OrderListParam param);

	List<OrderInfoDetail> listTaxiOrderInfo(OrderListParam param);

	OrderInfoDetail getOrgOrderInfoById(OrderApiParam param);
	
	OrderInfoDetail getOpOrderInfoById(OrderApiParam param);

	PubUserToken getUserTokenByToken(OrderApiParam param);
	
	List<OrgOrder> getOrgBeDepartureOrder(OrderApiParam param);

	List<OpOrder> getOpBeDepartureOrder(OrderApiParam param);

	List<OpTaxiOrder> getOpTaxiBeDepartureOrder(OrderApiParam param);

	PubSendrules getSendRule(PubSendrules param);
	
	OrgUser getOrgUserById(OrgUserParam param);
	
	PeUser getPeUserById(PeUserParam param);
	
	PeUser getPeUserByPhone(PeUser user);
	
	PubDriver getPubDriverById(@Param("id") String driverid);
	
	int cancelOverTimeOrgOrder(OrgOrder object);
	
	int cancelOverTimeOpOrder(OpOrder object);
	
	String getMaxOrderNO(OrderApiParam param);
	
	OrgOrganCompanyRef getOrgBalance(OrderCostParam param);
	
	void updatePubDriver(PubDriver param);
	
	void updateOrderInfo(OrderInfoDetail param);
	
	void updateOrgOrder(OrgOrder param);
	
	void updateOpOrder(OpOrder param);
	
	void orderReview(OrgOrderReview review);

	void createOrgOrder(OrgOrder order);

	void createOpOrder(OpOrder order);
	
	void updateOrgBalance(OrgOrganCompanyRef param);
	
	void updatePeBalance(PeUseraccount param);
	
	void savePubDriverNews(PubDriverNews param);
	
	List<OrgOrder> getOverTimeOrgOrderList();
	
	List<OpOrder> getOverTimeOpOrderList();
	
	int startPeUser();
	
	List<Map<String, String>> getOrganForBalance();
	
	List<String> getOrgUserListByOrgan(String organid);
	
	OrgOrganCompanyRef getOrgOrganCompanyRefByOrgCom(OrgOrganCompanyRef object);
	
	void updateOrgOrganCompanyRef(OrgOrganCompanyRef object);

	List<String> findOrgRoleId(@Param("dynamicid") String dynamicid,@Param("paymethod") String paymethod);

	List<String> findOpRoleId(@Param("cityCode")String cityCode,@Param("orderStyle")String orderStyle);
	
	TaxiOrderCost getOpTaxiAccountrulesByCity(OrderCostParam param);
	
	List<OpTaxisendrules> getOpTaxiSendrulesByCity(OpTaxisendrules object);
	
	PeUseraccount getPeUseraccount(PeUseraccount param);
	
	void insertOpTaxiOrder(OpTaxiOrder object);
	
	List<OpTaxiOrder> getOverTimeOpTaxiOrderList();
	
	void cancelOverTimeOpTaxiOrder(OpTaxiOrder object);
	
	OrderInfoDetail getOpTaxiOrderInfoById(OrderApiParam param);
	
	OpTaxiOrder getOpTaxiOrder(String orderno);
	
	OrgOrder getNotPayOrgOrder(OrgUserParam params);
	
	OpOrder getNotPayOpOrder(PeUserParam param);
	
	LeVehiclemodels getLeVehiclemodelsById(String id);
	
	OpVehiclemodels getOpVehiclemodelsById(String id);
	
	OpTaxisendrules getOpTaxiSendRule(GetSendInfoParam param);

    List<LeAccountRules> findModelPriceByModels(Map<String, Object> params);

    int manualSendOrder(OrgOrder orgOrder);

    com.szyciov.lease.entity.PubDriver getPubDriver(@Param("id") String id);

    Map<String, String> getOrgOrderVehicleByOrder(String orderno);

    void updateOrgOrderVehicleByOrderno(OrgOrder object);

    int insertOrgSendrecord(OrgSendrecord orgSendrecord);
    
    void savePubJpushlog(PubJpushlog log);
    
    void savePeUserExpenses(PeUserExpenses expenses);
    
    PubDriver getPubDriverByPhone(@Param("phone") String phone);
    
    List<PubDriver> getPubDriversByPhone(@Param("phones") String phones);
    
    OrgUser getOrgUserByUserId(@Param("id") String id);
    
    List<String> getValueByType(@Param("type") String type);
    
    PubDictionary getDicByType(PubDictionary dic);

    Map<String, Object> getOrgUsecarrulesByUser(Map<String, Object> param);

    List<Map<String, Object>> getLeCompanyRulesRefState(Map<String, Object> param);
    
    void createPeUser(PeUser user);
    
    void createPeUserAccount(PeUseraccount account);
    
}

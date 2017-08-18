package com.szyciov.carservice.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.carservice.mapper.OrderApiMapper;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.entity.OrderCost;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubJpushlog;
import com.szyciov.entity.PubOrderCancel;
import com.szyciov.entity.PubSendrules;
import com.szyciov.entity.PubUserToken;
import com.szyciov.entity.TaxiOrderCost;
import com.szyciov.enums.OrderVarietyEnum;
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

@Repository("OrderApiDao")
public class OrderApiDao {
	private OrderApiMapper mapper;

	@Resource
	public void setOrderApiMapper(OrderApiMapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * 获取租赁公司
	 * @param companyid
	 * @return {@linkplain LeLeasescompany}
	 */
	public LeLeasescompany getCompanyById(String companyid){
		return mapper.getCompanyById(companyid);
	}
	
	/**
	 * 获取机构订单计费规则
	 * @param param
	 * @return {@linkplain OrderCost}
	 * @see {@linkplain OrderCostParam}
	 */
	public OrderCost getOrgOrderCost(OrderCostParam param){
		return mapper.getOrgOrderCost(param);
	}
	
	/**
	 * 获取运管订单计费规则
	 * @param param
	 * @return {@linkplain OrderCost}
	 * @see {@linkplain OrderCostParam}
	 */
	public OrderCost getOpOrderCost(OrderCostParam param){
		return mapper.getOpOrderCost(param);
	}
	
	
	
	/**
	 * 订单复议
	 * @param review
	 * @see {@linkplain OrgOrderReview}
	 */
	public void orderReview(OrgOrderReview review){
		mapper.orderReview(review);
	}
	
	/**
	 * 获取订单列表(机构,运管通用)
	 * @param param
	 * @return {@linkplain OrderInfoDetail}
	 * @see {@linkplain OrderApiParam}
	 */
	public List<OrderInfoDetail> getOrderInfoList(OrderListParam param){
		return mapper.getOrderInfoList(param);
	}

	/**
	 * 获取出租车订单列表(运管端订单)
	 * @param param
	 * @return
	 */
	public List<OrderInfoDetail> listTaxiOrderInfo(OrderListParam param){
		return mapper.listTaxiOrderInfo(param);
	}
	
	/**
	 * 获取用户最后5单是否同一城市
	 * @param param
	 * @return
	 */
	public List<OrderInfoDetail> getLastOrders(String userphone){
		return mapper.getLastOrders(userphone);
	}
	
	/**
	 * 获取订单详情(机构,运管通用)
	 * @param param
	 * @return {@linkplain OrderInfoDetail}
	 * @see {@linkplain OrderApiParam}
	 */
	public OrderInfoDetail getOrderInfoById(OrderApiParam param) {
		if(OrderVarietyEnum.LEASE_NET.icode == param.getOrderprop()){ //机构网约车订单
			return mapper.getOrgOrderInfoById(param);
		} else if(OrderVarietyEnum.OPERATING_NET.icode == param.getOrderprop()){ //运管网约车订单
			return mapper.getOpOrderInfoById(param);
		} else { //运管出租车订单
			return mapper.getOpTaxiOrderInfoById(param);
		}
	}
	
	/**
	 * 根据token获取相应token对象
	 * @param param
	 * @return {@linkplain PubUserToken}
	 * @see {@linkplain OrderApiParam}
	 */
	public PubUserToken getUserTokenByToken(OrderApiParam param){
		return mapper.getUserTokenByToken(param);
	}
	
	/**
	 * 获取待出发订单(机构端)
	 * @param param
	 * @return {@linkplain OrgOrder}
	 * @see {@linkplain OrderApiParam}
	 */
	public List<OrgOrder> getOrgBeDepartureOrder(OrderApiParam param){
		return mapper.getOrgBeDepartureOrder(param);
	}
	
	
	/**
	 * 获取待出发订单(运管端)
	 * @param param
	 * @return {@linkplain OpOrder}
	 * @see {@linkplain OrderApiParam}
	 */
	public List<OpOrder> getOpBeDepartureOrder(OrderApiParam param){
		return mapper.getOpBeDepartureOrder(param);
	}

	/**
	 * 获取待出发订单(运管端出租车)
	 * @param param
	 * @return {@linkplain OpOrder}
	 * @see {@linkplain OrderApiParam}
	 */
	public List<OpTaxiOrder> getOpTaxiBeDepartureOrder(OrderApiParam param){
		return mapper.getOpTaxiBeDepartureOrder(param);
	}

	
	/**
	 * 获取派单规则
	 * @param param
	 * @return {@linkplain PubSendrules}
	 * @see {@linkplain GetSendInfoParam}
	 */
	public PubSendrules getSendRule(PubSendrules param){
		return mapper.getSendRule(param);
	}
	
	/**
	 * 创建机构端订单
	 * @param order
	 * @see {@linkplain OrgOrder}
	 */
	public void createOrgOrder(OrgOrder order){
		mapper.createOrgOrder(order);
	}
	
	/**
	 * 创建运管端订单
	 * @param order
	 * @see {@linkplain OpOrder}
	 */
	public void createOpOrder(OpOrder order){
		mapper.createOpOrder(order);
	}
	
	/**
	 * 获取机构用户
	 * @param userid id
	 * @return {@linkplain OrgUser}
	 */
	public OrgUser getOrgUserById(OrgUserParam oup){
		return mapper.getOrgUserById(oup);
	}
	
	/**
	 * 获取个人用户
	 * @param param userid
	 * @return {@linkplain PeUserParam}
	 */
	public PeUser getPeUserById(PeUserParam param){
		return mapper.getPeUserById(param);
	}
	
	/**
	 * 获取个人用户
	 * @param user 手机号
	 * @return {@linkplain PeUser}
	 */
	public PeUser getPeUserByPhone(PeUser user){
		return mapper.getPeUserByPhone(user);
	}
	
	/**
	 * 取消订单超时提醒(机构订单)
	 * @param param param
	 * @return
	 * @see {@linkplain OrderApiParam}
	 */
	public int cancelOverTimeOrgOrder(OrgOrder object){
		return mapper.cancelOverTimeOrgOrder(object);
	}
	
	/**
	 * 取消订单超时提醒(个人订单)
	 * @param param param
	 * @return
	 * @see {@linkplain OrderApiParam}
	 */
	public int cancelOverTimeOpOrder(OpOrder object){
		return mapper.cancelOverTimeOpOrder(object);
	}
	/**
	 * 获取机构可用额度
	 * @param param
	 * @return {@linkplain OrgOrganCompanyRef}
	 * @see {@linkplain OrderCostParam}
	 */
	public OrgOrganCompanyRef getOrgBalance(OrderCostParam param){
		return mapper.getOrgBalance(param);
	}
	
	/**
	 * 更新个人可用余额
	 * @param param
	 * @see {@linkplain OrderCostParam}
	 */
	public void updatePeBalance(PeUseraccount param){
		mapper.updatePeBalance(param);
	}
	/**
	 * 更新机构可用余额
	 * @param param
	 * @see {@linkplain OrderCostParam}
	 */
	public void updateOrgBalance(OrgOrganCompanyRef param){
		mapper.updateOrgBalance(param);;
	}
	
	/**
	 * 保存司机消息
	 * @param param
	 * @see {@linkplain PubDriverNews}
	 */
	public void savePubDriverNews(PubDriverNews param){
		mapper.savePubDriverNews(param);
	}
	
	/**
	 * 获取机构订单
	 * @param orderno
	 * @return {@link OrgOrder}
	 */
	public OrgOrder getOrgOrder(String orderno){
		return mapper.getOrgOrder(orderno);
	}
	
	/**
	 * 获取个人订单
	 * @param orderno
	 * @return {@link OpOrder}
	 */
	public OpOrder getOpOrder(String orderno){
		return mapper.getOpOrder(orderno);
	}
	/**
	 * 更新机构订单
	 * @param order
	 */
	public void updateOrgOrder(OrgOrder order){
		mapper.updateOrgOrder(order);
	}
	
	/**
	 * 更新个人订单
	 * @param order
	 */
	public void updateOpOrder(OpOrder order){
		mapper.updateOpOrder(order);
	}

	/**
	 * 更新订单(主要用来更新状态)
	 * @param param
	 * @see {@linkplain OrderInfoDetail}
	 */
	public void updateOrderInfo(OrderInfoDetail order){
		mapper.updateOrderInfo(order);
	}
	
	/**
	 * 更新司机(主要用来更新状态)
	 * @param driver
	 * @see {@linkplain PubDriver}
	 */
	public void updatePubDriver(PubDriver driver){
		mapper.updatePubDriver(driver);
	}
	
	/**
	 * 获取司机
	 * @param driverid
	 * @return {@linkplain PubDriver}
	 */
	public PubDriver getPubDriverById(String driverid){
		return mapper.getPubDriverById(driverid);
	}
	
	/**
	 * 查询人工派单超时的机构订单
	 * @return
	 */
	public List<OrgOrder> getOverTimeOrgOrderList() {
		return mapper.getOverTimeOrgOrderList();
	}
	
	/**
	 * 查询人工派单超时的个人订单
	 * @return
	 */
	public List<OpOrder> getOverTimeOpOrderList() {
		return mapper.getOverTimeOpOrderList();
	}
	
	/**
	 * 启用运营端用户
	 * @return
	 */
	public int startPeUser() {
		return mapper.startPeUser();
	}
	
	/**
	 * 查询可用额度不足100的机构信息
	 * @return
	 */
	public List<Map<String, String>> getOrganForBalance() {
		return mapper.getOrganForBalance();
	}
	
	/**
	 * 根据类型查询字典表
	 * @param dic
	 * @return
	 */
	public PubDictionary getDicByType(PubDictionary dic){
		return mapper.getDicByType(dic);
	}
	
	/**
	 * 根据机构查询对应的超管和财务管理员
	 * @param organid
	 * @return
	 */
	public List<String> getOrgUserListByOrgan(String organid) {
		return mapper.getOrgUserListByOrgan(organid);
	}
	
	/**
	 * 查出最大订单号
	 * @param order
	 * @return
	 */
	public String getMaxOrderNO(OrderApiParam param){
		return mapper.getMaxOrderNO(param);
	}
	
	/**
	 * 创建个人用户
	 * @param user
	 * @return
	 */
	public boolean createPeUser(PeUser user){
		mapper.createPeUser(user);
		return true;
	}
	
	/**
	 * 创建个人用户钱包
	 * @param account
	 * @return
	 */
	public boolean createPeUserAccount(PeUseraccount account){
		mapper.createPeUserAccount(account);
		return true;
	}
	
	public OrgOrganCompanyRef getOrgOrganCompanyRefByOrgCom(OrgOrganCompanyRef object) {
		return mapper.getOrgOrganCompanyRefByOrgCom(object);
	}
	
	public void updateOrgOrganCompanyRef(OrgOrganCompanyRef object) {
		mapper.updateOrgOrganCompanyRef(object);
	}
	

    /**
     * 返回租赁端机构有订单角色的ID
     * @param paymethod 支付类型
     * @param dynamicid 机构ID
     * @return
     */
    public List<String> findOrgRoleId(String paymethod,String dynamicid){
        return mapper.findOrgRoleId(dynamicid,paymethod);
    }

    /**
     * 返回运管端机构有订单角色的ID
     * @param cityId 上车城市ID
     * @return
     */
    public List<String> findOpRoleId(String cityId,String orderStyle){
        return mapper.findOpRoleId(cityId,orderStyle);
    }
    
    public TaxiOrderCost getOpTaxiAccountrulesByCity(OrderCostParam param) {
    	return mapper.getOpTaxiAccountrulesByCity(param);
    }
    
    public List<OpTaxisendrules> getOpTaxiSendrulesByCity(OpTaxisendrules object) {
    	return mapper.getOpTaxiSendrulesByCity(object);
    }
    
    /**
     * 获取个人用户账户
     * @param object
     * @return
     */
    public PeUseraccount getPeUseraccount(PeUseraccount object) {
    	return mapper.getPeUseraccount(object);
    }
    
    public void insertOpTaxiOrder(OpTaxiOrder object) {
    	mapper.insertOpTaxiOrder(object);
    }
    
    public List<OpTaxiOrder> getOverTimeOpTaxiOrderList() {
    	return mapper.getOverTimeOpTaxiOrderList();
    }
    
    public void cancelOverTimeOpTaxiOrder(OpTaxiOrder object) {
    	mapper.cancelOverTimeOpTaxiOrder(object);
    }
    
    public OpTaxiOrder getOpTaxiOrder(String orderno) {
    	return mapper.getOpTaxiOrder(orderno);
    }
    
    public OrgOrder getNotPayOrgOrder(OrgUserParam params) {
    	return mapper.getNotPayOrgOrder(params);
    }
    
    public OpOrder getNotPayOpOrder(PeUserParam param) {
    	return mapper.getNotPayOpOrder(param);
    }
    
    public LeVehiclemodels getLeVehiclemodelsById(String id) {
    	return mapper.getLeVehiclemodelsById(id);
    }
	
	public OpVehiclemodels getOpVehiclemodelsById(String id) {
		return mapper.getOpVehiclemodelsById(id);
	}
	
	public OpTaxisendrules getOpTaxiSendRule(GetSendInfoParam param) {
		return mapper.getOpTaxiSendRule(param);
	}

    public List<LeAccountRules> findModelPriceByModels(Map<String, Object> params) {
        return mapper.findModelPriceByModels(params);
    }

    public int manualSendOrder(OrgOrder orgOrder) {
        return mapper.manualSendOrder(orgOrder);
    }

    public com.szyciov.lease.entity.PubDriver getPubDriver(String id){
        return mapper.getPubDriver(id);
    }

    public void updateOrgOrderVehicleByOrderno(OrgOrder object) {
        mapper.updateOrgOrderVehicleByOrderno(object);
    }

    public Map<String, String> getOrgOrderVehicleByOrder(String orderno) {
        return mapper.getOrgOrderVehicleByOrder(orderno);
    }

    public int insertOrgSendrecord(OrgSendrecord orgSendrecord) {
        return mapper.insertOrgSendrecord(orgSendrecord);
    }
    
    public void savePubJpushlog(PubJpushlog log) {
    	mapper.savePubJpushlog(log);
    }
    
    public void savePeUserExpenses(PeUserExpenses expenses){
    	mapper.savePeUserExpenses(expenses);
    }
    
    public PubDriver getPubDriverByPhone(String phone){
    	return mapper.getPubDriverByPhone(phone);
    }
    
    public List<PubDriver> getPubDriversByPhone(String phones){
    	return mapper.getPubDriversByPhone(phones);
    }
    
    public OrgUser getOrgUserByUserId(String id) {
    	return mapper.getOrgUserByUserId(id);
    }
    
    public List<String> getValueByType(String type) {
    	return mapper.getValueByType(type);
    }

    public Map<String, Object> getOrgUsecarrulesByUser(Map<String, Object> param) {
        return mapper.getOrgUsecarrulesByUser(param);
    }

    public List<Map<String,Object>> getLeCompanyRulesRefState(Map<String, Object> param) {
        return mapper.getLeCompanyRulesRefState(param);
    }
    
    /**
     * 获取订单取消详情
     * @param orderno
     * @param ordertype 1-运管网约车单 2-机构网约车单 3-运管出租车单
     * @return
     */
    public PubOrderCancel getOrderCancelInfo(String orderno,int ordertype){
    	return mapper.getOrderCancelInfo(orderno,ordertype);
    }
    
    /**
     * 保存或更新取消详情
     * @param cancelInfo
     * @param ordertype 1-运管网约车单 2-机构网约车单 3-运管出租车单
     */
   public void saveOrUpdateOrderCancelInfo(PubOrderCancel cancelInfo){
	   mapper.saveOrUpdateOrderCancelInfo(cancelInfo);
   }
   
   /**
    * 更新个人用户的注册城市
    * @param user
    */
   public void updatePeUserCity(PeUser user){
	   mapper.updatePeUserCity(user);
   }
}

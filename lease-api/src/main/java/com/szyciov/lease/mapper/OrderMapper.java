package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.MinOrderInfo;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubSendrules;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.dto.request.GetFreeDriverRequest;
import com.szyciov.lease.dto.response.GetFreeDriverResponse;
import com.szyciov.lease.entity.FavUser;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.lease.param.OrgUserParam;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.org.entity.OrgMostAddress;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.param.BaseOrgParam;
import com.szyciov.param.Select2Param;

public interface OrderMapper {
	public List<String> getCities();
	
	public List<Dictionary> getUseCarReason();
	
	public List<OrgMostAddress> getMostAddress(BaseOrgParam param);
	
	public List<FavUser> getFavUser(Select2Param param);
	
	public int getFavUserCount(Select2Param param);
	
	public List<Select2Entity> getOrgUserForSelectInPub(Select2Param param);
	
	public List<Select2Entity> getOrgUserForSelectInPri(Select2Param param);
	
	public List<Select2Entity> getFavUserForSelect(Select2Param param);
	
	public List<OrgUser> getOrgUserInPub(Select2Param param);
	
	public int getOrgUserCountInPub(Select2Param param);
	
	public List<OrgUser> getOrgUserInPri(Select2Param param);
	
	public int getOrgUserCountInPri(Select2Param param);
	
	public List<OrgOrgan> getOrganList(@Param("companyid") String companyid);
	
	public OrgUser getOrgUserById(OrgUserParam param);
	
	public OrgOrgan getOrgByUser(@Param("userid") String userid,@Param("usetype") String usetype);
	
	public void createOrgOrder(OrgOrder orderInfo);
	
	public OrgOrder getOrgOrderById(@Param("orderno") String orderno);
	
	public MinOrderInfo getMinOrderInfo(@Param("orderno") String orderno);
	
	public int cancelOrder(OrgOrder order);
	
	public List<LeVehiclemodels> getCarTypes(GetCarTypesParam param);
	
	public PubSendrules getSendRule(PubSendrules rule);
	
	public List<PubDriver> getOrgDriversInBoundWithSimple(PubDriverInBoundParam param);
	
	public List<PubDriver> getOpDriversInBoundWithSimple(PubDriverInBoundParam param);
	
	public List<PubDriver> getTaxiInBoundWithSimple(PubDriverInBoundParam param);
	
	public List<PubDriver> getOpDriverInBound(PubDriverInBoundParam param);
	
	public List<PubDriver> getOrgDriverInBound(PubDriverInBoundParam param);
	
	public List<PubDriver> getSpecialDrivers(PubDriverInBoundParam param);
	
	public OrgOrganCompanyRef getOrgBalance(OrderCostParam param);
	
	public void updatePushNum(PubDriverInBoundParam param);
	
	public List<OrgOrgan> getPriOrganList(String companyid);
	
	public List<PubCityAddr> getOrgUserPubBusCity(Map<String, Object> param);
	
	public List<PubCityAddr> getOrgUserPriBusCity(Map<String, Object> param);

	List<GetFreeDriverResponse> getManualSelectDriver(GetFreeDriverRequest model);
}

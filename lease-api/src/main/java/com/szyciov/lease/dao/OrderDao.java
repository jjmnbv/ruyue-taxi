package com.szyciov.lease.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.MinOrderInfo;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubSendrules;
import com.szyciov.entity.Select2Entity;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.OrderEnum;
import com.szyciov.lease.dto.request.GetFreeDriverRequest;
import com.szyciov.lease.dto.response.GetFreeDriverResponse;
import com.szyciov.lease.entity.FavUser;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.mapper.OrderMapper;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.lease.param.OrgUserParam;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.org.entity.OrgMostAddress;
import com.szyciov.org.entity.OrgMostContact;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.param.BaseOrgParam;
import com.szyciov.param.Select2Param;

@Repository("OrderDao")
public class OrderDao {
	public OrderDao() {
	
	}
	
	private OrderMapper mapper;

	@Resource
	public void setMapper(OrderMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<String> getCities(){
		return mapper.getCities();
	}
	
	public List<Dictionary> getUseCarReason(){
		return mapper.getUseCarReason();
	}
	
	public List<OrgOrgan> getOrganList(String companyid){
		return mapper.getOrganList(companyid);
	}

	public int getFavUserCount(Select2Param param){
		int count = mapper.getFavUserCount(param);
		return count;
	}
	
	public List<PubDriver> getSpecialDrivers(PubDriverInBoundParam param){
		return mapper.getSpecialDrivers(param);
	}
	
	/**
	 * 获取范围内司机(运管端不填organid)
	 * @param param
	 * @return {@linkplain PubDriver}
	 * @see {@linkplain PubDriverInBoundParam}
	 */
	public List<PubDriver> getPubDriverInBound(PubDriverInBoundParam param){
		if(param.isSimple()){  //如果是简单模式,则只根据范围获取
			//获取出租车司机
			if(DriverEnum.DRIVER_TYPE_TAXI.code.equals(param.getVehicletype()+"")){
				return mapper.getTaxiInBoundWithSimple(param);
			}
			if(!param.isOrguser()){
				return mapper.getOpDriversInBoundWithSimple(param);
			}
			return mapper.getOrgDriversInBoundWithSimple(param);
		}else if(param.getOrganid() == null || param.getOrganid().isEmpty()){ //获取运管端司机
			return mapper.getOpDriverInBound(param);
		}else{  //获取租赁|机构端司机
			return mapper.getOrgDriverInBound(param);
		}
	}
	
	/**
	 * 获取机构常用联系人
	 * @param param
	 * @return {@linkplain FavUser}
	 * @see {@linkplain Select2Param}
	 */
	public List<FavUser> getFavUser(Select2Param param){
		List<FavUser> users = mapper.getFavUser(param);
		return users;
	}
	
	/**
	 * 获取常用联系人列表
	 * @param param
	 * @return {@linkplain OrgMostContact}
	 * @see {@linkplain GetCarTypesParam}
	 */
	public List<OrgMostAddress> getMostAddress(BaseOrgParam param){
		return mapper.getMostAddress(param);
	}
	
	public int getOrgUserCount(Select2Param param){
		int count = 0;
		if(OrderEnum.USETYPE_PUBLIC.code.equals(param.getUsetype())){
			count = mapper.getOrgUserCountInPub(param);
		}else{
			count = mapper.getOrgUserCountInPri(param);
		}
		return count;
//		return 0;
	}
	
	public List<Select2Entity> getOrgUserForSelect(Select2Param param){
		List<Select2Entity> list = new ArrayList<>();
		if(OrderEnum.USETYPE_PUBLIC.code.equals(param.getUsetype())){
			list = mapper.getOrgUserForSelectInPub(param);
		}else{
			list = mapper.getOrgUserForSelectInPri(param);
		}
		return list;
	}
	
	public List<Select2Entity> getFavUserForSelect(Select2Param param){
		return mapper.getFavUserForSelect(param);
	}
	
	public PubSendrules getSendRule(PubSendrules rule){
		return mapper.getSendRule(rule);
	}
	
	public OrgUser getOrgUserById(OrgUserParam param){
		return mapper.getOrgUserById(param);
	}
	
	public List<OrgUser> getOrgUser(Select2Param param){
		List<OrgUser> users = new ArrayList<>();
		if(OrderEnum.USETYPE_PUBLIC.code.equals(param.getUsetype())){
			users = mapper.getOrgUserInPub(param);
		}else{
			users = mapper.getOrgUserInPri(param);
		}
		return users;
	}
	
	public void createOrgOrder(OrgOrder orderInfo){
		mapper.createOrgOrder(orderInfo);
	}
	
	public OrgOrder checkOrderState(String orderno){
		return mapper.getOrgOrderById(orderno);
	}
	
	public MinOrderInfo getMinOrderInfo(String orderno){
		return mapper.getMinOrderInfo(orderno);
	}
	
	public List<LeVehiclemodels> getCarTypes(GetCarTypesParam param){
		return mapper.getCarTypes(param);
	}
	
	public OrgOrganCompanyRef getOrgBalance(OrderCostParam param){
		return mapper.getOrgBalance(param);
	}
	
	/**
	 * 更新订单司机推送数
	 * @param param
	 * @see {@linkplain PubDriverInBoundParam}
	 */
	public void updatePushNum(PubDriverInBoundParam param){
		mapper.updatePushNum(param);
	}
	
	public List<OrgOrgan> getPriOrganList(String companyid) {
		return mapper.getPriOrganList(companyid);
	}
	
	public List<PubCityAddr> getOrgUserPubBusCity(Map<String, Object> param) {
		return mapper.getOrgUserPubBusCity(param);
	}
	
	public List<PubCityAddr> getOrgUserPriBusCity(Map<String, Object> param) {
		return mapper.getOrgUserPriBusCity(param);
	}

    public List<GetFreeDriverResponse> getManualSelectDriver(GetFreeDriverRequest model){
        List<GetFreeDriverResponse> list = mapper.getManualSelectDriver(model);
        return list;
    }
}

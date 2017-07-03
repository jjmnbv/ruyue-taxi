/**
 * 
 */
package com.szyciov.organ.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.org.entity.OrgMostAddress;
import com.szyciov.org.entity.OrgMostContact;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.param.BaseOrgParam;
import com.szyciov.org.param.OrgUserParam;

/**
 * @ClassName OrderMapper
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用)
 * @date 2016年10月17日 下午4:06:12
 */
public interface OrderMapper {
	public OrgUser getOrgUserById(OrgUserParam param);
	
	public List<LeLeasescompany> getLeaseList(@Param("organid") String organid);

	public List<LeVehiclemodels> getCarTypes(GetCarTypesParam param);

	public List<OrgMostContact> getMostContact(BaseOrgParam param);
	
	public void addMostContact(OrgMostContact param);
	
	public void delMostContact(OrgMostContact param);

	public List<OrgMostAddress> getMostAddress(BaseOrgParam param);

	public OrgOrder getOrgOrder(@Param("orderno") String orderno);

	public void updateOrgOrder(OrgOrder order);

	public OrgOrganCompanyRef getOrgBalance(OrderCostParam param);
	
	public List<PubCityAddr> getOrgUserPubBusCity(Map<String, Object> param);
	
}

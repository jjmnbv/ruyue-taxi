/**
 * 
 */
package com.szyciov.organ.dao;

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
import com.szyciov.organ.mapper.OrderMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderDao 
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @date 2016年10月17日 下午4:06:26 
 */

@Repository("OrderDao")
public class OrderDao {
	private OrderMapper mapper;

	@Resource
	public void setMapper(OrderMapper mapper) {
		this.mapper = mapper;
	}
	
	public OrgUser getOrgUserById(OrgUserParam param){
		return mapper.getOrgUserById(param);
	}
	
	/**
	 * 获取租赁公司列表
	 * @param organid
	 * @return {@linkplain LeLeasescompany}
	 */
	public List<LeLeasescompany> getLeaseList(String organid){
		return mapper.getLeaseList(organid);
	}
	
	/**
	 * 获取车型列表
	 * @param param
	 * @return {@linkplain LeVehiclemodels}
	 * @see {@linkplain GetCarTypesParam}
	 */
	public List<LeVehiclemodels> getCarTypes(GetCarTypesParam param){
		return mapper.getCarTypes(param);
	}
	
	/**
	 * 添加常用联系人
	 * @param param
	 * @see {@linkplain OrgMostContact}
	 */
	public void addMostContact(OrgMostContact param){
		mapper.addMostContact(param);
	}
	
	/**
	 * 删除常用联系人
	 * @param param
	 * @see {@linkplain OrgMostContact}
	 */
	public void delMostContact(OrgMostContact param){
		mapper.delMostContact(param);
	}
	
	/**
	 * 获取常用联系人列表
	 * @param param
	 * @return {@linkplain OrgMostContact}
	 * @see {@linkplain GetCarTypesParam}
	 */
	public List<OrgMostContact> getMostContact(BaseOrgParam param){
		return mapper.getMostContact(param);
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
	
	/**
	 * 获取订单状态
	 * @param orderno
	 * @return {@linkplain OrgOrder}
	 */
	public OrgOrder getOrgOrder(String orderno){
		return mapper.getOrgOrder(orderno);
	}
	
	/**
	 * 更新OrgOrder
	 * @param order
	 * @see {@linkplain OrgOrder}
	 */
	public void updateOrgOrder(OrgOrder order){
		mapper.updateOrgOrder(order);
	}
	
	/**
	 * 获取机构账户余额
	 * @param param
	 * @return {@linkplain OrgOrganCompanyRef}
	 * @see {@linkplain OrderCostParam}
	 */
	public OrgOrganCompanyRef getOrgBalance(OrderCostParam param){
		return mapper.getOrgBalance(param);
	}
	
	public List<PubCityAddr> getOrgUserPubBusCity(Map<String, Object> param) {
		return mapper.getOrgUserPubBusCity(param);
	}

    public Map<String, Object> getRuyueCompany() {
        return mapper.getRuyueCompany();
    }
}

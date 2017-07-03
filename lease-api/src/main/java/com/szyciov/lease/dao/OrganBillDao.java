package com.szyciov.lease.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganBill;
import com.szyciov.lease.entity.OrgOrganBillState;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.mapper.OrganBillMapper;
import com.szyciov.lease.param.OrganBillQueryParam;
import com.szyciov.org.entity.OrgOrder;

@Repository("OrganBillDao")
public class OrganBillDao {
	public OrganBillDao() {
	}

	private OrganBillMapper mapper;

	@Resource
	public void setMapper(OrganBillMapper mapper) {
		this.mapper = mapper;
	}
	
    public List<OrgOrganBill> getCurOrganBillListByQuery(OrganBillQueryParam organBillQueryParam) {
    	return mapper.getCurOrganBillListByQuery(organBillQueryParam);
    }
	
	public int getCurOrganBillListCountByQuery(OrganBillQueryParam organBillQueryParam) {
		return mapper.getCurOrganBillListCountByQuery(organBillQueryParam);
	}
	
	public List<OrgOrganBill> getCurOrganBillListExport(OrganBillQueryParam organBillQueryParam) {
		return mapper.getCurOrganBillListExport(organBillQueryParam);
	}
	
	public List<OrgOrganBillState> getOrganBillStateById(String id) {
		return mapper.getOrganBillStateById(id);
	}
	
	public List<OrgOrder> getOrgOrderListByQuery(OrganBillQueryParam organBillQueryParam) {
		return mapper.getOrgOrderListByQuery(organBillQueryParam);
	}
	
	public int getOrgOrderListCountByQuery(OrganBillQueryParam organBillQueryParam) {
		return mapper.getOrgOrderListCountByQuery(organBillQueryParam);
	}
	
	public List<OrgOrder> getManualOrgOrderListByQuery(OrganBillQueryParam organBillQueryParam) {
		return mapper.getManualOrgOrderListByQuery(organBillQueryParam);
	}
	
	public int getManualOrgOrderListCountByQuery(OrganBillQueryParam organBillQueryParam) {
		return mapper.getManualOrgOrderListCountByQuery(organBillQueryParam);
	}
	
	public void createOrganbill(OrgOrganBill orgOrganBill) {
		mapper.createOrganbill(orgOrganBill);
	}
	
	public void createOrganBillState(Map<String, String> map) {
		mapper.createOrganBillState(map);
	}
	
	public void createOrganBillDetails(Map<String, String> map) {
		mapper.createOrganBillDetails(map);
	}
	
	public int getOrganBillStateCountById(String id) {
		return mapper.getOrganBillStateCountById(id);
	}
	
    public BigDecimal getActualBalanceById(Map<String, String> map) {
    	return mapper.getActualBalanceById(map);
    }
	
	public void reduceOrganAccount(Map<String, Object> map) {
		mapper.reduceOrganAccount(map);
	}
	
	public OrgOrganBill getOrganBillById(String id) {
		return mapper.getOrganBillById(id);
	}
	
	public void changeOrgOrderStatus(Map<String, String> map) {
		mapper.changeOrgOrderStatus(map);
	}
	
	public List<OrgOrgan> getOrganList(String leasesCompanyId) {
		return mapper.getOrganList(leasesCompanyId);
	}
	
    public List<OrgOrder> getOrgOrderListById(OrganBillQueryParam organBillQueryParam) {
    	return mapper.getOrgOrderListById(organBillQueryParam);
    }
	
	public int getOrgOrderListCountById(OrganBillQueryParam organBillQueryParam) {
		return mapper.getOrgOrderListCountById(organBillQueryParam);
	}
	
	public void changeOrderStatusToBalance(String orderId) {
		mapper.changeOrderStatusToBalance(orderId);
	}
	
    public BigDecimal getOrderAmountByQuery(OrgOrganBill orgOrganBill) {
    	return mapper.getOrderAmountByQuery(orgOrganBill);
    }
	
	public List<String> getOrderListByQuery(OrgOrganBill orgOrganBill) {
		return mapper.getOrderListByQuery(orgOrganBill);
	}
	
	public List<Map<String, Object>> selectOrganList(Map<String, String> map) {
		return mapper.selectOrganList(map);
	}
	
	public List<String> getOrganUserIdByType(String organId) {
		return mapper.getOrganUserIdByType(organId);
	}
	
    public int checkUnBalanceBillCount(Map<String, Object> map) {
    	return mapper.checkUnBalanceBillCount(map);
    }
	
	public OrgOrganCompanyRef getLineOfCredit(Map<String, Object> map) {
		return mapper.getLineOfCredit(map);
	}
	
	public void updateLineOfCredit(Map<String, Object> map) {
		mapper.updateLineOfCredit(map);
	}
	
	public List<OrgOrder> getOrgOrderListExport(OrganBillQueryParam organBillQueryParam) {
		return mapper.getOrgOrderListExport(organBillQueryParam);
	}
}

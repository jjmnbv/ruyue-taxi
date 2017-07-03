package com.szyciov.lease.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganBill;
import com.szyciov.lease.entity.OrgOrganBillState;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.param.OrganBillQueryParam;
import com.szyciov.org.entity.OrgOrder;

public interface OrganBillMapper {
	
	List<OrgOrganBill> getCurOrganBillListByQuery(OrganBillQueryParam organBillQueryParam);
	
	int getCurOrganBillListCountByQuery(OrganBillQueryParam organBillQueryParam);
	
	List<OrgOrganBill> getCurOrganBillListExport(OrganBillQueryParam organBillQueryParam);
	
	List<OrgOrganBillState> getOrganBillStateById(String id);
	
	List<OrgOrder> getOrgOrderListByQuery(OrganBillQueryParam organBillQueryParam);
	
	int getOrgOrderListCountByQuery(OrganBillQueryParam organBillQueryParam);
	
	List<OrgOrder> getManualOrgOrderListByQuery(OrganBillQueryParam organBillQueryParam);
	
	int getManualOrgOrderListCountByQuery(OrganBillQueryParam organBillQueryParam);
	
	void createOrganbill(OrgOrganBill orgOrganBill);
	
	void createOrganBillState(Map<String, String> map);
	
	void createOrganBillDetails(Map<String, String> map);
	
	int getOrganBillStateCountById(String id);
	
	BigDecimal getActualBalanceById(Map<String, String> map);
	
	void reduceOrganAccount(Map<String, Object> map);
	
	OrgOrganBill getOrganBillById(String id);
	
	void changeOrgOrderStatus(Map<String, String> map);
	
	List<OrgOrgan> getOrganList(String leasesCompanyId);
	
	List<OrgOrder> getOrgOrderListById(OrganBillQueryParam organBillQueryParam);
	
	int getOrgOrderListCountById(OrganBillQueryParam organBillQueryParam);
	
	void changeOrderStatusToBalance(String orderId);
	
	BigDecimal getOrderAmountByQuery(OrgOrganBill orgOrganBill);
	
	List<String> getOrderListByQuery(OrgOrganBill orgOrganBill);
	
	List<Map<String, Object>> selectOrganList(Map<String, String> map);
	
	List<String> getOrganUserIdByType(String organId);
	
	int checkUnBalanceBillCount(Map<String, Object> map);
	
	OrgOrganCompanyRef getLineOfCredit(Map<String, Object> map);
	
	void updateLineOfCredit(Map<String, Object> map);
	
	List<OrgOrder> getOrgOrderListExport(OrganBillQueryParam organBillQueryParam);
}

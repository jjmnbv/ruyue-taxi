package com.szyciov.carservice.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganBill;

public interface OrderBillMapper {
	public List<OrgOrgan> getBillOrgan(Map<String, Object> params);
	
	public BigDecimal getOrderAmountByQuery(OrgOrganBill orgOrganBill);
	
	public List<String> getOrderListByQuery(OrgOrganBill orgOrganBill);
	
	public void createOrganbill(OrgOrganBill orgOrganBill);
	
	public void createOrganBillState(Map<String, String> map);
	
	public void createOrganBillDetails(Map<String, String> map);
	
	public void changeOrderStatusToBalance(String orderId);
	
	public String getLeasesCompanyByOrgan(String organId);
	
	public String getMaxBillNo();
	
	public String getLeaseCompanySeq(String companyId);

}

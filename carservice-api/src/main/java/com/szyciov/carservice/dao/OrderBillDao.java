package com.szyciov.carservice.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.carservice.mapper.OrderBillMapper;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganBill;

@Repository("orderBillDao")
public class OrderBillDao {
	
	public OrderBillMapper mapper;

	@Resource
	public void setMapper(OrderBillMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<OrgOrgan> getBillOrgan(Map<String, Object> params) {
		return mapper.getBillOrgan(params);
	}
	
	public BigDecimal getOrderAmountByQuery(OrgOrganBill orgOrganBill) {
    	return mapper.getOrderAmountByQuery(orgOrganBill);
    }
	
	public List<String> getOrderListByQuery(OrgOrganBill orgOrganBill) {
		return mapper.getOrderListByQuery(orgOrganBill);
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
	
	public void changeOrderStatusToBalance(String orderId) {
		mapper.changeOrderStatusToBalance(orderId);
	}
	
	public String getLeasesCompanyByOrgan(String organId) {
		return mapper.getLeasesCompanyByOrgan(organId);
	}
	
	public String getMaxBillNo() {
		return mapper.getMaxBillNo();
	}
	
	public String getLeaseCompanySeq(String companyId) {
		return mapper.getLeaseCompanySeq(companyId);
	}

}

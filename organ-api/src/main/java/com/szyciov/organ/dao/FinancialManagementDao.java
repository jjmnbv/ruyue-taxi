package com.szyciov.organ.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.PubCouponDetail;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.OrgOrganBill;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.OrgOrganExpenses;
import com.szyciov.lease.param.OrganAccountQueryParam;
import com.szyciov.lease.param.OrganBillQueryParam;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrganPaymentRecord;
import com.szyciov.org.entity.PubWithdraw;
import com.szyciov.org.param.FinancialManagementQueryParam;
import com.szyciov.organ.mapper.FinancialManagementMapper;

@Repository("FinancialManagementDao")
public class FinancialManagementDao {
	public FinancialManagementDao() {
	}

	private FinancialManagementMapper mapper;

	@Resource
	public void setMapper(FinancialManagementMapper mapper) {
		this.mapper = mapper;
	}
	
    public List<OrgOrganCompanyRef> getAccountListByOrganId(String organId) {
    	return mapper.getAccountListByOrganId(organId);
    }
	
	public List<OrgOrganExpenses> getOrganExpensesListByQuery(OrganAccountQueryParam organAccountQueryParam) {
		return mapper.getOrganExpensesListByQuery(organAccountQueryParam);
	}
	
	public int getOrganExpensesListCountByQuery(OrganAccountQueryParam organAccountQueryParam) {
		return mapper.getOrganExpensesListCountByQuery(organAccountQueryParam);
	}
	
	public List<OrgOrganBill> getOrganBillListByQuery(FinancialManagementQueryParam financialManagementQueryParam) {
		return mapper.getOrganBillListByQuery(financialManagementQueryParam);
	}
	
	public int getOrganBillListCountByQuery(FinancialManagementQueryParam financialManagementQueryParam) {
		return mapper.getOrganBillListCountByQuery(financialManagementQueryParam);
	}
	
	public OrgOrganBill getOrganBillById(String id) {
		return mapper.getOrganBillById(id);
	}
	
	public List<OrgOrder> getOrgOrderListByQuery(OrganBillQueryParam organBillQueryParam) {
		return mapper.getOrgOrderListByQuery(organBillQueryParam);
	}
	
	public int getOrgOrderListCountByQuery(OrganBillQueryParam organBillQueryParam) {
		return mapper.getOrgOrderListCountByQuery(organBillQueryParam);
	}
	
	public List<OrgOrder> getOrgOrderListExport(Map<String, String> map) {
		return mapper.getOrgOrderListExport(map);
	}
	
	public List<LeLeasescompany> getLeasesCompanyListByOrgan(String organId) {
		return mapper.getLeasesCompanyListByOrgan(organId);
	}
	
    public void createOrganBillState(Map<String, String> map) {
    	mapper.createOrganBillState(map);
    }
	
	public void changeOrgOrderStatus(Map<String, String> map) {
		mapper.changeOrgOrderStatus(map);
	}
	
	public BigDecimal getActualBalanceById(Map<String, String> map) {
    	return mapper.getActualBalanceById(map);
    }
	
	public void reduceOrganAccount(Map<String, Object> map) {
		mapper.reduceOrganAccount(map);
	}
	
	public void createOrganExpenses(OrgOrganExpenses orgOrganExpenses) {
		mapper.createOrganExpenses(orgOrganExpenses);
	}
	
	public List<Map<String, Object>> getOrganBillStateById(String billsId) {
		return mapper.getOrganBillStateById(billsId);
	}
	
    public OrgOrganBill getBillById(String id) {
    	return mapper.getBillById(id);
    }
	
	public List<String> getLeasesCompanyUserIdById(String leasesCompanyId) {
		return mapper.getLeasesCompanyUserIdById(leasesCompanyId);
	}
	
	public void createBalanceOrganBillState(Map<String, String> map) {
		mapper.createBalanceOrganBillState(map);
	}
	
	public List<String> getOrganUserIdByType(String organId) {
		return mapper.getOrganUserIdByType(organId);
	}
	
	public OrgOrganCompanyRef getLineOfCredit(Map<String, Object> map) {
		return mapper.getLineOfCredit(map);
	}
	
	public void updateLineOfCredit(Map<String, Object> map) {
		mapper.updateLineOfCredit(map);
	}
	
	public void updateCreditEffectiveTime(Map<String, Object> map) {
		mapper.updateCreditEffectiveTime(map);
	}
	
	public LeLeasescompany getLeasesCompanyById(String leasesCompanyId) {
		return mapper.getLeasesCompanyById(leasesCompanyId);
	}
	
	public Map<String, Object> getPayInfo4LeByCompanyid(String companyid) {
		return mapper.getPayInfo4LeByCompanyid(companyid);
	}
	
	public void addTradeNo4Organ(Map<String, Object> rechargeinfo) {
		mapper.addTradeNo4Organ(rechargeinfo);
	}
	
	public void updateTradeInfo4Organ(Map<String, Object> tradeparam) {
		mapper.updateTradeInfo4Organ(tradeparam);
	}
	
	public void updateOrganCompanyRef(Map<String, Object> map) {
		mapper.updateOrganCompanyRef(map);
	}
	
	public OrgOrganPaymentRecord getOrganPaymentRecord(String outtradeno) {
		return mapper.getOrganPaymentRecord(outtradeno);
	}
	
	public Map<String, Object> getWithdrawInfo(Map<String, String> map) {
		return mapper.getWithdrawInfo(map);
	}
	
	public void withdrawOrganAccount(Map<String, Object> map) {
		mapper.withdrawOrganAccount(map);
	}
	
	public void addPubWithdraw(PubWithdraw pubWithdraw) {
		mapper.addPubWithdraw(pubWithdraw);
	}
	
	public Map<String, Object> getPubWithdraw(String id) {
		return mapper.getPubWithdraw(id);
	}
	
	public List<String> getLeasesCompanyUserById(String leasesCompanyId) {
		return mapper.getLeasesCompanyUserById(leasesCompanyId);
	}
	
	public List<PubCouponDetail> getPubCouponDetailList(OrganAccountQueryParam organAccountQueryParam){
		return mapper.getPubCouponDetailList(organAccountQueryParam);
	};
	
	public int getPubCouponDetailListCount(OrganAccountQueryParam organAccountQueryParam){
		return mapper.getPubCouponDetailListCount(organAccountQueryParam);
	};
}

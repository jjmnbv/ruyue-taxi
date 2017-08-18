package com.szyciov.organ.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

public interface FinancialManagementMapper {
	
	List<OrgOrganCompanyRef> getAccountListByOrganId(String organId);
	
	List<OrgOrganExpenses> getOrganExpensesListByQuery(OrganAccountQueryParam organAccountQueryParam);
	
	int getOrganExpensesListCountByQuery(OrganAccountQueryParam organAccountQueryParam);
	
	List<OrgOrganBill> getOrganBillListByQuery(FinancialManagementQueryParam financialManagementQueryParam);
	
	int getOrganBillListCountByQuery(FinancialManagementQueryParam financialManagementQueryParam);
	
	OrgOrganBill getOrganBillById(String id);
	
	List<OrgOrder> getOrgOrderListByQuery(OrganBillQueryParam organBillQueryParam);
	
	int getOrgOrderListCountByQuery(OrganBillQueryParam organBillQueryParam);
	
	List<OrgOrder> getOrgOrderListExport(Map<String, String> map);
	
	List<LeLeasescompany> getLeasesCompanyListByOrgan(String organId);
	
	void createOrganBillState(Map<String, String> map);
	
	void changeOrgOrderStatus(Map<String, String> map);
	
	BigDecimal getActualBalanceById(Map<String, String> map);
	
	Map<String, Object> getActualBalanceAndCouponamountById(Map<String, Object> map);
	
	void reduceOrganAccount(Map<String, Object> map);
	
	void createOrganExpenses(OrgOrganExpenses orgOrganExpenses);
	
	List<Map<String, Object>> getOrganBillStateById(String billsId);
	
	OrgOrganBill getBillById(String id);
	
	List<String> getLeasesCompanyUserIdById(String leasesCompanyId);
	
	void createBalanceOrganBillState(Map<String, String> map);
	
	List<String> getOrganUserIdByType(String organId);
	
	OrgOrganCompanyRef getLineOfCredit(Map<String, Object> map);
	
	void updateLineOfCredit(Map<String, Object> map);
	
	void updateCreditEffectiveTime(Map<String, Object> map);
	
	LeLeasescompany getLeasesCompanyById(String leasesCompanyId);
	
	Map<String, Object> getPayInfo4LeByCompanyid(String companyid);
	
	void addTradeNo4Organ(Map<String, Object> rechargeinfo);
	
	void updateTradeInfo4Organ(Map<String, Object> tradeparam);
	
	void updateOrganCompanyRef(Map<String, Object> map);
	
	OrgOrganPaymentRecord getOrganPaymentRecord(String outtradeno);
	
	Map<String, Object> getWithdrawInfo(Map<String, String> map);
	
	void withdrawOrganAccount(Map<String, Object> map);
	
	void addPubWithdraw(PubWithdraw pubWithdraw);
	
	Map<String, Object> getPubWithdraw(String id);
	
	List<String> getLeasesCompanyUserById(String leasesCompanyId);
	
	List<PubCouponDetail> getPubCouponDetailList(OrganAccountQueryParam organAccountQueryParam);
	
	int getPubCouponDetailListCount(OrganAccountQueryParam organAccountQueryParam);
	
	void addCouponDetail(Map<String, Object> map);
	
	void addOrganCouponValue(Map<String, Object> map);
	
	String getCityByOrganid(String id);
}

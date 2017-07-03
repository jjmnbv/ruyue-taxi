package com.szyciov.organ.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderDetails;
import com.szyciov.org.param.OrgOrderQueryParam;

public interface MyOrderMapper {

	List<OrgOrder> getOrgderList(OrgOrderQueryParam orgOrderQueryParam);
	
	int getOrgderListCount(OrgOrderQueryParam orgOrderQueryParam);
	
	List<LeLeasescompany> getQueryCompany();
	
	OrgOrderDetails getById(String id);
	
	List<OrgOrder> exportExcel(OrgOrderQueryParam orgOrderQueryParam);
	
	public Map<String, Object> getOrgOrderByOrderno(String orderno);
	
	void cancelOrder(String id);
	
	void updatePaytype(OrgOrder orgOrder);
	
	void updateOrderstatus(String id);
	
	void updatePaymentstatus(String id);
	
	void updateUserrate(OrgOrder orgOrder);

	void addTradeNo4Order(Map<String, String> orderinfo);

	void payed4OrgOrder(Map<String, Object> orderparam);

	void updateTradeInfo4OrgOrder(Map<String, Object> tradeparam);

	Map<String, Object> getPayInfo4LeByCompanyid(String companyid);

	String getOrdernoByOutno(String out_trade_no);
	
	LeLeasescompany getOrdernoByLeasescompany(String id);
	
	void addExpenses4OrgSec(Map<String, Object> param);
}
package com.szyciov.organ.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderDetails;
import com.szyciov.org.param.OrgOrderQueryParam;
import com.szyciov.organ.mapper.MyOrderMapper;

@Repository("MyOrderDao")
public class MyOrderDao {
	public MyOrderDao() {
	}

	private MyOrderMapper mapper;

	@Resource
	public void setMapper(MyOrderMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<OrgOrder> getOrgderList(OrgOrderQueryParam orgOrderQueryParam){
		return mapper.getOrgderList(orgOrderQueryParam);
	};
	
	public int getOrgderListCount(OrgOrderQueryParam orgOrderQueryParam){
		return mapper.getOrgderListCount(orgOrderQueryParam);
	};
	
	public List<LeLeasescompany> getQueryCompany(){
		return mapper.getQueryCompany();
	};
	
	public OrgOrderDetails getById(String id){
		return mapper.getById(id);
	};
	
	public List<OrgOrder> exportExcel(OrgOrderQueryParam orgOrderQueryParam){
		return mapper.exportExcel(orgOrderQueryParam);
	};
	
	public Map<String, Object> getOrgOrderByOrderno(String orderno) {
		return mapper.getOrgOrderByOrderno(orderno);
	}
	
	public void cancelOrder(String id){
		mapper.cancelOrder(id);
	};
	
	public void updatePaytype(OrgOrder orgOrder){
		mapper.updatePaytype(orgOrder);
	};
	
	public void updateOrderstatus(String id){
		mapper.updateOrderstatus(id);
	};
	
	public void updatePaymentstatus(String id){
		mapper.updatePaymentstatus(id);
	};
	
	public void updateUserrate(OrgOrder orgOrder){
		mapper.updateUserrate(orgOrder);
	}

	public void addTradeNo4Order(Map<String, String> orderinfo) {
		mapper.addTradeNo4Order(orderinfo);
	}

	public void payed4OrgOrder(Map<String, Object> orderparam) {
		mapper.payed4OrgOrder(orderparam);
	}

	public void updateTradeInfo4OrgOrder(Map<String, Object> tradeparam) {
		mapper.updateTradeInfo4OrgOrder(tradeparam);
	}

	public Map<String, Object> getPayInfo4LeByCompanyid(String companyid) {
		return mapper.getPayInfo4LeByCompanyid(companyid);
	}

	public String getOrdernoByOutno(String out_trade_no) {
		return mapper.getOrdernoByOutno(out_trade_no);
	};
	
	public LeLeasescompany getOrdernoByLeasescompany(String id){
		return mapper.getOrdernoByLeasescompany(id);
	};
	
	public void addExpenses4OrgSec(Map<String, Object> param) {
		mapper.addExpenses4OrgSec(param);
	}
}

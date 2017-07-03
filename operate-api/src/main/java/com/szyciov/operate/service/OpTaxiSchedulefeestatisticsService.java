package com.szyciov.operate.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.LeDriverorderstatisticsParam;
import com.szyciov.op.entity.OpTaxiSchedulefeestatistics;
import com.szyciov.operate.dao.OpTaxiSchedulefeestatisticsDao;
import com.szyciov.util.PageBean;

@Service("opTaxiSchedulefeestatisticsService")
public class OpTaxiSchedulefeestatisticsService {
	private OpTaxiSchedulefeestatisticsDao dao;
	@Resource(name = "OpTaxiSchedulefeestatisticsDao")
	public void setDao(OpTaxiSchedulefeestatisticsDao dao) {
		this.dao = dao;
	}
	public List<PubDictionary> getCustomer(){
		return dao.getCustomer();
	}
	public PageBean getDate(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(opTaxiSchedulefeestatistics.getsEcho()); 
		List<OpTaxiSchedulefeestatistics> list = getDateQuery(opTaxiSchedulefeestatistics);
		int iTotalRecords = getDateQueryCount(opTaxiSchedulefeestatistics);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<OpTaxiSchedulefeestatistics> getDateQuery(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return dao.getDateQuery(opTaxiSchedulefeestatistics);
	}
	public int getDateQueryCount(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return dao.getDateQueryCount(opTaxiSchedulefeestatistics);
		
	}
	public List<Map<String, Object>> getDriver(LeDriverorderstatisticsParam leDriverorderstatisticsParam){
		return dao.getDriver(leDriverorderstatisticsParam);
	}
	public PageBean getDateDriver(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(opTaxiSchedulefeestatistics.getsEcho()); 
		List<OpTaxiSchedulefeestatistics> list = getDateDriverQuery(opTaxiSchedulefeestatistics);
		int iTotalRecords = getDateDriverQueryCount(opTaxiSchedulefeestatistics);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<OpTaxiSchedulefeestatistics> getDateDriverQuery(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return dao.getDateDriverQuery(opTaxiSchedulefeestatistics);
	}
	public int getDateDriverQueryCount(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return dao.getDateDriverQueryCount(opTaxiSchedulefeestatistics);
		
	}
	public List<OpTaxiSchedulefeestatistics> opTaxiSchedulefeesExport(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return dao.opTaxiSchedulefeesExport(opTaxiSchedulefeestatistics);
	}
	public List<OpTaxiSchedulefeestatistics> opTaxiSchedulefeesExport1(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return dao.opTaxiSchedulefeesExport1(opTaxiSchedulefeestatistics);
	}

}

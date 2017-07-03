package com.szyciov.operate.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.szyciov.lease.param.LeOrgorderstatisticsParam;
import com.szyciov.op.param.OpOrderstatisticsParam;
import com.szyciov.operate.dao.OpOrderstatisticsDao;
import com.szyciov.util.PageBean;
@Service("OpOrderstatisticsService")
public class OpOrderstatisticsService {
	private OpOrderstatisticsDao dao;

	@Resource(name = "OpOrderstatisticsDao")
	public void setDao(OpOrderstatisticsDao dao) {
		this.dao = dao;
	}
	public PageBean getMonthDataQuery(OpOrderstatisticsParam opOrderstatisticsParam){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(opOrderstatisticsParam.getsEcho());
		List<OpOrderstatisticsParam> list = getMonthData(opOrderstatisticsParam);
		int iTotalRecords = getMonthDataCount(opOrderstatisticsParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<OpOrderstatisticsParam> getMonthData(OpOrderstatisticsParam opOrderstatisticsParam) {
		List<OpOrderstatisticsParam> list = dao.getMonthData(opOrderstatisticsParam);
		return list;
	}

	public int getMonthDataCount(OpOrderstatisticsParam opOrderstatisticsParam) {
		return dao.getMonthDataCount(opOrderstatisticsParam);
	}
	public OpOrderstatisticsParam getAllData(OpOrderstatisticsParam opOrderstatisticsParam) {
		return dao.getAllData(opOrderstatisticsParam);
	}
	public List<OpOrderstatisticsParam> exportData(OpOrderstatisticsParam opOrderstatisticsParam) {
		return dao.exportData(opOrderstatisticsParam);
	}
	public List<OpOrderstatisticsParam> getCustom(String userid) {
		return dao.getCustom(userid);
	}

}

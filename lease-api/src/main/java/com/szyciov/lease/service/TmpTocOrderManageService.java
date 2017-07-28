package com.szyciov.lease.service;

import com.szyciov.lease.dao.TmpTocOrderManageDao;
import com.szyciov.lease.param.TocOrderManageQueryParam;
import com.szyciov.util.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("TmpTocOrderManageService")
public class TmpTocOrderManageService {

    @Resource(name = "TmpTocOrderManageDao")
	private TmpTocOrderManageDao dao;
	
	public PageBean getNetAboutCarOrderByQuery(TocOrderManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getNetAboutCarOrderListByQuery(queryParam);
		int iTotalRecords = getNetAboutCarOrderListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}

	public List<Map<String, Object>> getNetAboutCarOrderListByQuery(TocOrderManageQueryParam queryParam) {
    	return dao.getNetAboutCarOrderListByQuery(queryParam);
    }
	
	public int getNetAboutCarOrderListCountByQuery(TocOrderManageQueryParam queryParam) {
		return dao.getNetAboutCarOrderListCountByQuery(queryParam);
	}
	
	public PageBean getTaxiOrderByQuery(TocOrderManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getTaxiOrderListByQuery(queryParam);
		int iTotalRecords = getTaxiOrderListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<Map<String, Object>> getTaxiOrderListByQuery(TocOrderManageQueryParam queryParam) {
    	return dao.getTaxiOrderListByQuery(queryParam);
    }
	
	public int getTaxiOrderListCountByQuery(TocOrderManageQueryParam queryParam) {
		return dao.getTaxiOrderListCountByQuery(queryParam);
	}
}

package com.szyciov.operate.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.op.param.TourFeeManagementQueryParam;
import com.szyciov.operate.dao.TourFeeManagementDao;
import com.szyciov.util.PageBean;

@Service("TourFeeManagementService")
public class TourFeeManagementService {
	private TourFeeManagementDao dao;

	@Resource(name = "TourFeeManagementDao")
	public void setDao(TourFeeManagementDao dao) {
		this.dao = dao;
	}
	
	public PageBean getTourFeeByQuery(TourFeeManagementQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getTourFeeListByQuery(queryParam);
		int iTotalRecords = getTourFeeListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<Map<String, Object>> getTourFeeListByQuery(TourFeeManagementQueryParam queryParam) {
    	return dao.getTourFeeListByQuery(queryParam);
    }
	
    public int getTourFeeListCountByQuery(TourFeeManagementQueryParam queryParam) {
    	return dao.getTourFeeListCountByQuery(queryParam);
    }
	
    public List<Map<String, Object>> getOrderNo(String orderno) {
    	return dao.getOrderNo(orderno);
    }
	
    public List<Map<String, Object>> getDriverByNameOrPhone(String driver) {
    	return dao.getDriverByNameOrPhone(driver);
    }
	
    public List<Map<String, Object>> getCompanyNameById() {
    	return dao.getCompanyNameById();
    }
    
    public List<Map<String, Object>> getJobnumByJobnum(String jobnum) {
    	return dao.getJobnumByJobnum(jobnum);
    }
    
    public List<Map<String, Object>> getTourFeeListExport(TourFeeManagementQueryParam queryParam) {
    	return dao.getTourFeeListExport(queryParam);
    }
}

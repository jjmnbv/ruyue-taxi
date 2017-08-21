package com.szyciov.operate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.szyciov.lease.param.TobOrderManageQueryParam;
import com.szyciov.op.param.PlatformOrderManagementParam;
import com.szyciov.operate.dao.PlatformOrderManagementDao;
import com.szyciov.util.PageBean;

@Service("PlatformOrderManagementService")
public class PlatformOrderManagementService {
	private PlatformOrderManagementDao dao;

	@Resource(name = "PlatformOrderManagementDao")
	public void setDao(PlatformOrderManagementDao dao) {
		this.dao = dao;
	}
	public List<Map<String, Object>> getNetAboutCarOrderUserByQuery(String orderPerson) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderPerson", orderPerson);
		return dao.getNetAboutCarOrderUserByQuery(map);
	}
	public List<Map<String, Object>> getNetAboutCarOrderDriverByQuery(String driver,String vehicleType) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("driver", driver);
		map.put("vehicleType", vehicleType);
		return dao.getNetAboutCarOrderDriverByQuery(map);
	}
	public PageBean getNetAboutCarOrderByQuery(PlatformOrderManagementParam queryParam) {
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

	public List<Map<String, Object>> getNetAboutCarOrderListByQuery(PlatformOrderManagementParam queryParam) {
    	return dao.getNetAboutCarOrderListByQuery(queryParam);
    }
	
	public int getNetAboutCarOrderListCountByQuery(PlatformOrderManagementParam queryParam) {
		return dao.getNetAboutCarOrderListCountByQuery(queryParam);
	}
	public List<Map<String, Object>> getPartnerCompanySelect(Map<String,Object> params) {
		return dao.getPartnerCompanySelect(params);
	}
	public List<Map<String, Object>> getNetAboutCarOrderExport(PlatformOrderManagementParam queryParam) {
		return dao.getNetAboutCarOrderExport(queryParam);
	}

}

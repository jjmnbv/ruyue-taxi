package com.szyciov.lease.dao;

import com.szyciov.lease.mapper.TmpTocOrderManageMapper;
import com.szyciov.lease.param.TocOrderManageQueryParam;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository("TmpTocOrderManageDao")
public class TmpTocOrderManageDao {

	private TmpTocOrderManageMapper mapper;
	@Resource
	public void setMapper(TmpTocOrderManageMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<Map<String, Object>> getNetAboutCarOrderListByQuery(TocOrderManageQueryParam queryParam) {
    	return mapper.getNetAboutCarOrderListByQuery(queryParam);
    }
	
	public int getNetAboutCarOrderListCountByQuery(TocOrderManageQueryParam queryParam) {
		return mapper.getNetAboutCarOrderListCountByQuery(queryParam);
	}
	
    public List<Map<String, Object>> getTaxiOrderListByQuery(TocOrderManageQueryParam queryParam) {
    	return mapper.getTaxiOrderListByQuery(queryParam);
    }
	
	public int getTaxiOrderListCountByQuery(TocOrderManageQueryParam queryParam) {
		return mapper.getTaxiOrderListCountByQuery(queryParam);
	}
	
}

package com.szyciov.carservice.dao;

import com.szyciov.carservice.mapper.BaiduApiMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

@Repository("baiduApiDao")
public class BaiduApiDao {
	
	private BaiduApiMapper mapper;
	
	@Resource
	public void setBaiduApiMapper(BaiduApiMapper mapper) {
		this.mapper = mapper;
	}

	public Map<String, Object> getOrderByOrderno(String orderno) {
		return mapper.getOrderByOrderno(orderno);
	}

}

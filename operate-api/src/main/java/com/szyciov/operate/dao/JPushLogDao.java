package com.szyciov.operate.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.PubJpushlog;
import com.szyciov.op.param.JPushLogQueryParam;
import com.szyciov.operate.mapper.JPushLogMapper;

@Repository("JPushLogDao")
public class JPushLogDao {

	@Resource
	public JPushLogMapper mapper;
	
	public List<PubJpushlog> queryJPushLog(JPushLogQueryParam param) {
		return mapper.queryJPushLog(param);
	}

	public int queryJPushLogCount(JPushLogQueryParam param) {
		return mapper.queryJPushLogCount(param);
	}

	public List<PubJpushlog> getAllJPushLog(JPushLogQueryParam param) {
		return mapper.getAllJPushLog(param);
	}

}

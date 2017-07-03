package com.szyciov.operate.mapper;

import java.util.List;

import com.szyciov.entity.PubJpushlog;
import com.szyciov.op.param.JPushLogQueryParam;

public interface JPushLogMapper {

	List<PubJpushlog> queryJPushLog(JPushLogQueryParam param);

	int queryJPushLogCount(JPushLogQueryParam param);

	List<PubJpushlog> getAllJPushLog(JPushLogQueryParam param);

}

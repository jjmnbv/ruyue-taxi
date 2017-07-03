package com.szyciov.lease.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.dto.pubAlarmprocess.PubAlarmprocessSaveDto;
import com.szyciov.lease.entity.PubRoleId;
import com.szyciov.lease.param.PubAlarmprocessParam;

public interface PubAlarmprocessMapper {
	List<PubAlarmprocessParam> getPubAlarmprocessByQueryList(PubAlarmprocessParam pubAlarmprocessParam);
	int getPubAlarmprocessByQueryCount(PubAlarmprocessParam pubAlarmprocessParam);
	PubAlarmprocessParam getPubAlarmprocessDetail(String id);
    int updataDetail(PubAlarmprocessParam pubAlarmprocessParam);
    List<Map<String, Object>> getPubAlarmprocessDriver(PubAlarmprocessParam pubAlarmprocessParam);
    List<Map<String, Object>> getPubAlarmprocessPassenger(PubAlarmprocessParam pubAlarmprocessParam);
    List<Map<String, Object>> getPubAlarmprocessPlateno(PubAlarmprocessParam pubAlarmprocessParam);
    int ordernoOK(PubRoleId pubRoleId);
    int handleOK(PubAlarmprocessParam pubAlarmprocessParam);
    void save(PubAlarmprocessSaveDto saveDto);
}

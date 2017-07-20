/**
 * 
 */
package com.ry.taxi.sync.mapper;

import java.util.List;

import com.ry.taxi.sync.domain.GciSyncLog;
import com.ry.taxi.sync.query.RealTimeGps;


/**
 * @Title:GciVehicle.java
 * @Package com.ry.taxi.sync.mapper
 * @Description
 * @author zhangdd
 * @date 2017年7月19日 下午4:34:10
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
 */
public interface GciVehicleMapper {
	
	int insertBathGps(List<RealTimeGps> gpslist);
	
	GciSyncLog getLastTrace();
	
	int insertTraceLog(GciSyncLog log);
}

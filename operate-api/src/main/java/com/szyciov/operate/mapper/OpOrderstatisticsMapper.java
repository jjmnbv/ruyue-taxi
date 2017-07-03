package com.szyciov.operate.mapper;

import java.util.List;
import com.szyciov.op.param.OpOrderstatisticsParam;

public interface OpOrderstatisticsMapper {
	 List<OpOrderstatisticsParam> getMonthData(OpOrderstatisticsParam opOrderstatisticsParam);
	 int getMonthDataCount(OpOrderstatisticsParam opOrderstatisticsParam);
	 OpOrderstatisticsParam getAllData(OpOrderstatisticsParam opOrderstatisticsParam);
	 List<OpOrderstatisticsParam> exportData(OpOrderstatisticsParam opOrderstatisticsParam);
	 List<OpOrderstatisticsParam> getCustom(String userTheId);
}

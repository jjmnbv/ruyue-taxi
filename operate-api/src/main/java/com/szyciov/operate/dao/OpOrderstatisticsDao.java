package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.szyciov.op.param.OpOrderstatisticsParam;
import com.szyciov.operate.mapper.OpOrderstatisticsMapper;

@Repository("OpOrderstatisticsDao")
public class OpOrderstatisticsDao {
	private OpOrderstatisticsMapper mapper;

	@Resource
	public void setMapper(OpOrderstatisticsMapper mapper) {
		this.mapper = mapper;
	}
	public List<OpOrderstatisticsParam> getMonthData(OpOrderstatisticsParam opOrderstatisticsParam) {
		return mapper.getMonthData(opOrderstatisticsParam);
	}
	public int getMonthDataCount(OpOrderstatisticsParam opOrderstatisticsParam) {
		return mapper.getMonthDataCount(opOrderstatisticsParam);
	}
	public OpOrderstatisticsParam getAllData(OpOrderstatisticsParam opOrderstatisticsParam) {
		return mapper.getAllData(opOrderstatisticsParam);
	}
	public List<OpOrderstatisticsParam> exportData(OpOrderstatisticsParam opOrderstatisticsParam) {
		return mapper.exportData(opOrderstatisticsParam);
	}
	public List<OpOrderstatisticsParam> getCustom(String userid) {
		List<OpOrderstatisticsParam> aaa= mapper.getCustom(userid);
		return mapper.getCustom(userid);
	}

}

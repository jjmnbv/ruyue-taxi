package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.LeDriverorderstatisticsParam;
import com.szyciov.op.entity.OpTaxiSchedulefeestatistics;
import com.szyciov.operate.mapper.OpTaxiSchedulefeestatisticsMapper;

@Repository("OpTaxiSchedulefeestatisticsDao")
public class OpTaxiSchedulefeestatisticsDao {
	public OpTaxiSchedulefeestatisticsDao(){
	}
     private OpTaxiSchedulefeestatisticsMapper mapper;
 	@Resource
 	public void setMapper(OpTaxiSchedulefeestatisticsMapper mapper) {
 		this.mapper = mapper;
 	}
 	public List<PubDictionary> getCustomer(){
		return mapper.getCustomer();
	}
 	public List<OpTaxiSchedulefeestatistics> getDateQuery(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return mapper.getDateQuery(opTaxiSchedulefeestatistics);
	}
 	public int getDateQueryCount(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return mapper.getDateQueryCount(opTaxiSchedulefeestatistics);
		
	}
 	public  List<Map<String, Object>> getDriver(LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
		return mapper.getDriver(leDriverorderstatisticsParam);
	}
 	public List<OpTaxiSchedulefeestatistics> getDateDriverQuery(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return mapper.getDateDriverQuery(opTaxiSchedulefeestatistics);
	}
 	public int getDateDriverQueryCount(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return mapper.getDateDriverQueryCount(opTaxiSchedulefeestatistics);
		
	}
 	public List<OpTaxiSchedulefeestatistics> opTaxiSchedulefeesExport(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return mapper.opTaxiSchedulefeesExport(opTaxiSchedulefeestatistics);
	}
 	public List<OpTaxiSchedulefeestatistics> opTaxiSchedulefeesExport1(OpTaxiSchedulefeestatistics opTaxiSchedulefeestatistics){
		return mapper.opTaxiSchedulefeesExport1(opTaxiSchedulefeestatistics);
	}


}

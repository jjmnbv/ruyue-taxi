package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.szyciov.lease.param.LeDriverorderstatisticsParam;
import com.szyciov.operate.mapper.LeDriverorderstatisticsMapper;
@Repository("LeDriverorderstatisticsDao")
public class LeDriverorderstatisticsDao {
	 public LeDriverorderstatisticsDao(){
		}
	     private LeDriverorderstatisticsMapper mapper;
	 	@Resource
	 	public void setMapper(LeDriverorderstatisticsMapper mapper) {
	 		this.mapper = mapper;
	 	}
	 	
	 	public List<LeDriverorderstatisticsParam> getDriverCountListByQueryToC(LeDriverorderstatisticsParam leDriverorderstatisticsParam) {
	 		return mapper.getDriverCountListByQueryToC(leDriverorderstatisticsParam);
	 	}

	 	public int getDriverCountListCountByQueryToC(LeDriverorderstatisticsParam leDriverorderstatisticsParam) {
	 		return mapper.getDriverCountListCountByQueryToC(leDriverorderstatisticsParam);
	 	}
	 	public List<LeDriverorderstatisticsParam> getcartypeId(String leasesCompanyId)  {
			return mapper.getcartypeId(leasesCompanyId);
		}
	 	public List<Map<String, Object>> getVehcBrand(@RequestBody LeDriverorderstatisticsParam leDriverorderstatisticsParam) {
			return mapper.getVehcBrand(leDriverorderstatisticsParam);
		}
	 	public List<LeDriverorderstatisticsParam> getVehcBrandAll2(LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
			return mapper.getVehcBrandAll2(leDriverorderstatisticsParam);
		}
	 	public LeDriverorderstatisticsParam getVehcBrandAllToC(LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
			return mapper.getVehcBrandAllToC(leDriverorderstatisticsParam);
		}
	 	public  List<Map<String, Object>> getPlateno(LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
			return mapper.getPlateno(leDriverorderstatisticsParam);
		}
	 	public  List<Map<String, Object>> getDriver(LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
			return mapper.getDriver(leDriverorderstatisticsParam);
		}
	 	public  List<Map<String, Object>> getJobnum(LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
			return mapper.getJobnum(leDriverorderstatisticsParam);
		}

}

package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.szyciov.lease.mapper.LeDriverorderstatisticsMapper;
import com.szyciov.lease.param.LeDriverorderstatisticsParam;
@Repository("LeDriverorderstatisticsDao")
public class LeDriverorderstatisticsDao {
	 public LeDriverorderstatisticsDao(){
		}
	     private LeDriverorderstatisticsMapper mapper;
	 	@Resource
	 	public void setMapper(LeDriverorderstatisticsMapper mapper) {
	 		this.mapper = mapper;
	 	}
	 	
	 	public List<LeDriverorderstatisticsParam> getDriverCountListByQuery(LeDriverorderstatisticsParam leDriverorderstatisticsParam) {
	 		return mapper.getDriverCountListByQuery(leDriverorderstatisticsParam);
	 	}

	 	public int getDriverCountListCountByQuery(LeDriverorderstatisticsParam leDriverorderstatisticsParam) {
	 		return mapper.getDriverCountListCountByQuery(leDriverorderstatisticsParam);
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
	 	public List<LeDriverorderstatisticsParam> getVehcBrandAll(LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
			return mapper.getVehcBrandAll(leDriverorderstatisticsParam);
		}
	 	public List<LeDriverorderstatisticsParam> getVehcBrandAll1(LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
			return mapper.getVehcBrandAll1(leDriverorderstatisticsParam);
		}
	 	public List<LeDriverorderstatisticsParam> getVehcBrandAll2(LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
			return mapper.getVehcBrandAll2(leDriverorderstatisticsParam);
		}
	 	public List<LeDriverorderstatisticsParam> getVehcBrandAllToC(LeDriverorderstatisticsParam leDriverorderstatisticsParam)  {
			return mapper.getVehcBrandAllToC(leDriverorderstatisticsParam);
		}

}

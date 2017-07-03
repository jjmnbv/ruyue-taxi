package com.szyciov.lease.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.mapper.LePersonalorderstatisticsMapper;
import com.szyciov.lease.param.LePersonalorderstatisticsParam;

@Repository("LePersonalorderstatisticsDao")
public class LePersonalorderstatisticsDao {
	 public LePersonalorderstatisticsDao(){
		}
	     private LePersonalorderstatisticsMapper mapper;
	 	@Resource
	 	public void setMapper(LePersonalorderstatisticsMapper mapper) {
	 		this.mapper = mapper;
	 	}
	 	public List<LePersonalorderstatisticsParam> getPersonalCountListByQuery(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
			return  mapper.getPersonalCountListByQuery(lePersonalorderstatisticsParam);
		}

		public int getPersonalCountListCountByQuery(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
			return mapper.getPersonalCountListCountByQuery(lePersonalorderstatisticsParam);
		}
		public List<LePersonalorderstatisticsParam> getPersonalCountListByQuery1(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
			return  mapper.getPersonalCountListByQuery1(lePersonalorderstatisticsParam);
		}

		public int getPersonalCountListCountByQuery1(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
			return mapper.getPersonalCountListCountByQuery1(lePersonalorderstatisticsParam);
		}
		public List<LePersonalorderstatisticsParam> getPersonalAll(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
			return mapper.getPersonalAll(lePersonalorderstatisticsParam);
		}
		public List<LePersonalorderstatisticsParam> getPersonalAll1(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
			return mapper.getPersonalAll1(lePersonalorderstatisticsParam);
		}
		public List<LePersonalorderstatisticsParam> getPersonalAll2(LePersonalorderstatisticsParam lePersonalorderstatisticsParam) {
			return mapper.getPersonalAll2(lePersonalorderstatisticsParam);
		}
		public List<PubDictionary> getordertype(String leasesCompanyId)  {
			return mapper.getordertype(leasesCompanyId);
		}
		public List<PubDictionary> getPaymethod(String leasesCompanyId)  {
			return mapper.getPaymethod(leasesCompanyId);
		}
		public LePersonalorderstatisticsParam getTheUser(String leasesCompanyId)  {
			return mapper.getTheUser(leasesCompanyId);
		}

}

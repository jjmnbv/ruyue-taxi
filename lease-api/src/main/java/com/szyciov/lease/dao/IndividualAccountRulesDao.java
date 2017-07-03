package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeCompanyRulesRef;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.mapper.IndividualAccountRulesMapper;
import com.szyciov.lease.param.IndividualAccountRulesQueryParam;

@Repository("IndividualAccountRulesDao")
public class IndividualAccountRulesDao {
	public IndividualAccountRulesDao() {
	}

	private IndividualAccountRulesMapper mapper;

	@Resource
	public void setMapper(IndividualAccountRulesMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<LeCompanyRulesRef> getIndividualAccountRulesListByQuery(IndividualAccountRulesQueryParam queryParam) {
		return mapper.getIndividualAccountRulesListByQuery(queryParam);
	}
	
	public int getIndividualAccountRulesListCountByQuery(IndividualAccountRulesQueryParam queryParam) {
		return mapper.getIndividualAccountRulesListCountByQuery(queryParam);
	}
	
	public List<OrgOrgan> getOrganList(Map<String, String> map) {
		return mapper.getOrganList(map);
	}
	
	public void disableIndividualLeCompanyRulesRef(LeCompanyRulesRef leCompanyRulesRef) {
    	mapper.disableIndividualLeCompanyRulesRef(leCompanyRulesRef);
    }
	
	public int getIndividualRulesCountById(String id) {
		return mapper.getIndividualRulesCountById(id);
	}

    public List<OrgOrgan> getInsertOrganList(Map<String, String> map) {
    	return mapper.getInsertOrganList(map);
    }
	
	public List<Map<String, Object>> getInsertCityList(Map<String, String> map) {
		return mapper.getInsertCityList(map);
	}
	
	public List<LeAccountRules> getIndividualAccountRulesListByOrgan(IndividualAccountRulesQueryParam queryParam) {
    	return mapper.getIndividualAccountRulesListByOrgan(queryParam);
    }
	
	public int getIndividualAccountRulesListCountByOrgan(IndividualAccountRulesQueryParam queryParam) {
		return mapper.getIndividualAccountRulesListCountByOrgan(queryParam);
	}
	
	public void updateIndividualAccountRules(LeAccountRules leAccountRules) {
    	mapper.updateIndividualAccountRules(leAccountRules);
    }
	
	public int getAccountRulesCountById(Map<String, String> map) {
		return mapper.getAccountRulesCountById(map);
	}
	
	public void deleteIndividualAccountRules(Map<String, String> map) {
		mapper.deleteIndividualAccountRules(map);
	}
	
	public List<Map<String,Object>> getRulesDateByOrgan(Map<String,String> map) {
		return mapper.getRulesDateByOrgan(map);
	}
	
	public List<LeAccountRules> getIndividualAccountRulesListByCity(Map<String,String> map) {
		return mapper.getIndividualAccountRulesListByCity(map);
	}
	
	public String getRulesIdByDate(LeCompanyRulesRef rulesRef) {
		return mapper.getRulesIdByDate(rulesRef);
	}

	public int getRulesCountById(LeAccountRules leAccountRules) {
		return mapper.getRulesCountById(leAccountRules);
	}
	
	public void createIndividualLeCompanyRulesRef(LeCompanyRulesRef leCompanyRulesRef) {
    	mapper.createIndividualLeCompanyRulesRef(leCompanyRulesRef);
    }
	
	public void createIndividualLeAccountRules(LeAccountRules leAccountRules) {
		mapper.createIndividualLeAccountRules(leAccountRules);
	}
	
    public int getRulesCountByOrgan(LeCompanyRulesRef leCompanyRulesRef) {
    	return mapper.getRulesCountByOrgan(leCompanyRulesRef);
    }
	
	public int getRulesCountByQuery(LeCompanyRulesRef leCompanyRulesRef) {
		return mapper.getRulesCountByQuery(leCompanyRulesRef);
	}
	
	public int getRulesCountByRulesRefId(LeCompanyRulesRef leCompanyRulesRef) {
		return mapper.getRulesCountByRulesRefId(leCompanyRulesRef);
	}
	
	public Map<String, String> getAccountStatusByLeasesCompanyId(String leasesCompanyId) {
		return mapper.getAccountStatusByLeasesCompanyId(leasesCompanyId);
	}

}

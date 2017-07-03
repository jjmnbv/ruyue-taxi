package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.IndividualAccountRulesDao;
import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeCompanyRulesRef;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.param.IndividualAccountRulesQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("individualAccountRulesService")
public class IndividualAccountRulesService {
	private IndividualAccountRulesDao dao;

	@Resource(name = "IndividualAccountRulesDao")
	public void setDao(IndividualAccountRulesDao dao) {
		this.dao = dao;
	}
	
	public PageBean getIndividualAccountRulesByQuery(IndividualAccountRulesQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<LeCompanyRulesRef> list = getIndividualAccountRulesListByQuery(queryParam);
		int iTotalRecords = getIndividualAccountRulesListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<LeCompanyRulesRef> getIndividualAccountRulesListByQuery(IndividualAccountRulesQueryParam queryParam) {
		return dao.getIndividualAccountRulesListByQuery(queryParam);
	}
	
	public int getIndividualAccountRulesListCountByQuery(IndividualAccountRulesQueryParam queryParam) {
		return dao.getIndividualAccountRulesListCountByQuery(queryParam);
	}
	
	public List<OrgOrgan> getOrganList(String leasesCompanyId, String specialState, String account) {
		Map<String, String> map = new HashMap<String, String>();
    	map.put("leasesCompanyId", leasesCompanyId);
    	map.put("specialState", specialState);
    	map.put("account", account);
		return dao.getOrganList(map);
	}
	
	public Map<String, String> disableIndividualAccountRules(LeCompanyRulesRef leCompanyRulesRef) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "禁用成功");
		
		if (existCheck(leCompanyRulesRef.getId()) == 0) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "禁用失败");
		} else {
			// 2-禁用
			leCompanyRulesRef.setRuleState("2");
			disableIndividualLeCompanyRulesRef(leCompanyRulesRef);
		}

		return ret;
	}

	public void disableIndividualLeCompanyRulesRef(LeCompanyRulesRef leCompanyRulesRef) {
		dao.disableIndividualLeCompanyRulesRef(leCompanyRulesRef);
    }
	
	public int existCheck(String id) {
		return dao.getIndividualRulesCountById(id);
	}
	
	public List<OrgOrgan> getInsertOrganList(String leasesCompanyId, String specialState, String account) {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("leasesCompanyId", leasesCompanyId);
    	map.put("specialState", specialState);
    	map.put("account", account);
		return dao.getInsertOrganList(map);
    }
	
	public List<Map<String, Object>> getInsertCityList(String leasesCompanyId, String cityName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("leasesCompanyId", leasesCompanyId);
		map.put("cityName", cityName);
		return dao.getInsertCityList(map);
	}

	public PageBean getIndividualAccountRulesByOrgan(IndividualAccountRulesQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<LeAccountRules> list = getIndividualAccountRulesListByOrgan(queryParam);
		int iTotalRecords = getIndividualAccountRulesListCountByOrgan(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}

	public List<LeAccountRules> getIndividualAccountRulesListByOrgan(IndividualAccountRulesQueryParam queryParam) {
    	return dao.getIndividualAccountRulesListByOrgan(queryParam);
    }
	
	public int getIndividualAccountRulesListCountByOrgan(IndividualAccountRulesQueryParam queryParam) {
		return dao.getIndividualAccountRulesListCountByOrgan(queryParam);
	}
	
	public Map<String, String> updateIndividualAccountRules(LeAccountRules leAccountRules) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		
		if (updateCheck(leAccountRules) == 0) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "保存失败");
		} else {
			dao.updateIndividualAccountRules(leAccountRules);
		}

		return ret;
	}
	
	public int updateCheck(LeAccountRules leAccountRules) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("leasesCompanyId", leAccountRules.getLeasesCompanyId());
		map.put("id", leAccountRules.getId());
		return dao.getAccountRulesCountById(map);
	}

	public Map<String, String> deleteIndividualAccountRules(String id, String leasesCompanyId) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "删除成功");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("leasesCompanyId", leasesCompanyId);
		dao.deleteIndividualAccountRules(map);
		
		return ret;
	}
	
	public List<Map<String,Object>> getRulesDateByOrgan(String organId, String leasesCompanyId) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("organId", organId);
		parameterMap.put("leasesCompanyId", leasesCompanyId);

		return dao.getRulesDateByOrgan(parameterMap);
	}
	
	public List<LeAccountRules> getIndividualAccountRulesListByCity(String city, String leasesCompanyId) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("city", city);
		parameterMap.put("leasesCompanyId", leasesCompanyId);
		
		return dao.getIndividualAccountRulesListByCity(parameterMap);
	}
	
	public Map<String, String> createIndividualAccountRules(LeCompanyRulesRef leCompanyRulesRef) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		
		LeCompanyRulesRef rulesRef = new LeCompanyRulesRef();
		rulesRef.setLeasesCompanyId(leCompanyRulesRef.getLeasesCompanyId());
		rulesRef.setOrganId(leCompanyRulesRef.getOrganId());
		// 草稿状态
		rulesRef.setRuleState("0");
		// 有效期
		rulesRef.setStartTime(leCompanyRulesRef.getStartTime());
		rulesRef.setEndTime(leCompanyRulesRef.getEndTime());
		String rulesRefId = getRulesIdByDate(rulesRef);
		if (rulesRefId == null) {
			//exist = false;
			// 直接创建
			String uuid = GUIDGenerator.newGUID();
			leCompanyRulesRef.setId(uuid);
			// 创建个性化计费规则
			createIndividualLeCompanyRulesRef(leCompanyRulesRef);

			LeAccountRules leAccountRules = new LeAccountRules();
			leAccountRules.setLeasesCompanyId(leCompanyRulesRef.getLeasesCompanyId());
			leAccountRules.setCreater(leCompanyRulesRef.getCreater());
			leAccountRules.setUpdater(leCompanyRulesRef.getUpdater());
			leAccountRules.setRulesRefId(uuid);
			
			List<String> ruleList = leCompanyRulesRef.getRuleList();
			for (String ruleId : ruleList) {
				uuid = GUIDGenerator.newGUID();
				leAccountRules.setId(uuid);
				leAccountRules.setRulesId(ruleId);
				// 创建租赁计费规则
				createIndividualLeAccountRules(leAccountRules);
			}
		} else {
			LeAccountRules leAccountRules = new LeAccountRules();
			leAccountRules.setLeasesCompanyId(leCompanyRulesRef.getLeasesCompanyId());
			leAccountRules.setRulesRefId(rulesRefId);
			List<String> ruleList = leCompanyRulesRef.getRuleList();
			for (String ruleId : ruleList) {
				leAccountRules.setRulesId(ruleId);
				if (getRulesCountById(leAccountRules) == 0) {
					String uuid = GUIDGenerator.newGUID();
					leAccountRules.setId(uuid);
					leAccountRules.setCreater(leCompanyRulesRef.getCreater());
					leAccountRules.setUpdater(leCompanyRulesRef.getUpdater());
					// 创建租赁计费规则
					createIndividualLeAccountRules(leAccountRules);
				}
			}
		}

		return ret;
	}
	
	public String getRulesIdByDate(LeCompanyRulesRef rulesRef) {
		return dao.getRulesIdByDate(rulesRef);
	}

	public int getRulesCountById(LeAccountRules leAccountRules) {
		return dao.getRulesCountById(leAccountRules);
	}
	
	public void createIndividualLeCompanyRulesRef(LeCompanyRulesRef leCompanyRulesRef) {
		dao.createIndividualLeCompanyRulesRef(leCompanyRulesRef);
    }
	
	public void createIndividualLeAccountRules(LeAccountRules leAccountRules) {
		dao.createIndividualLeAccountRules(leAccountRules);
	}

	public Map<String, String> enableIndividualAccountRules(LeCompanyRulesRef leCompanyRulesRef) {
		Map<String, String> ret = new HashMap<String, String>();

		Map<String, String> accountStatus = dao.getAccountStatusByLeasesCompanyId(leCompanyRulesRef.getLeasesCompanyId());
		if (accountStatus != null
				&& (accountStatus.get("wechatstatus") != null && "1".equals(accountStatus.get("wechatstatus"))
						|| accountStatus.get("alipaystatus") != null && "1".equals(accountStatus.get("alipaystatus")))
				&& (accountStatus.get("driverwechatstatus") != null
						&& "1".equals(accountStatus.get("driverwechatstatus"))
						|| accountStatus.get("driveralipaystatus") != null
								&& "1".equals(accountStatus.get("driveralipaystatus")))) {
			
			boolean exist = true;
			if (getRulesCountByOrgan(leCompanyRulesRef) == 0) {
				// 相同机构不存在启用状态的规则
				exist = false;
			} else {
				// 有效期不存在包含和交叉关系
				if (getRulesCountByQuery(leCompanyRulesRef) == 0) {
					exist = false;
				} else {
					// 不存在业务上的部分重合或全部重合关系
					if (getRulesCountByRulesRefId(leCompanyRulesRef) == 0) {
						exist = false;
					}
				}
			}
			
			if (!exist) {
				ret.put("ResultSign", "Successful");
				ret.put("MessageKey", "启用成功");
				
				// 1-启用
				leCompanyRulesRef.setRuleState("1");
				disableIndividualLeCompanyRulesRef(leCompanyRulesRef);
			} else {
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "启用失败，请检查和该机构已启用规则的有效期及业务是否存在冲突");
			}
		} else {
			if (accountStatus == null || accountStatus != null
					&& (accountStatus.get("wechatstatus") == null || "0".equals(accountStatus.get("wechatstatus")))
					&& (accountStatus.get("alipaystatus") == null || "0".equals(accountStatus.get("alipaystatus")))
					&& (accountStatus.get("driverwechatstatus") == null
							|| "0".equals(accountStatus.get("driverwechatstatus")))
					&& (accountStatus.get("driveralipaystatus") == null
							|| "0".equals(accountStatus.get("driveralipaystatus")))) {
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "启用失败，乘客和司机交易账户当前没有启用的收款账户，设置后才可启用");
			} else if (accountStatus != null
					&& (accountStatus.get("wechatstatus") == null || "0".equals(accountStatus.get("wechatstatus")))
					&& (accountStatus.get("alipaystatus") == null || "0".equals(accountStatus.get("alipaystatus")))) {// 乘客账户
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "启用失败，乘客交易账户当前没有启用的收款账户，设置后才可启用");
			} else if (accountStatus != null
					&& (accountStatus.get("driverwechatstatus") == null
							|| "0".equals(accountStatus.get("driverwechatstatus")))
					&& (accountStatus.get("driveralipaystatus") == null
							|| "0".equals(accountStatus.get("driveralipaystatus")))) {// 司机账户
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "启用失败，司机交易账户当前没有启用的收款账户，设置后才可启用");
			}
		}

		return ret;
	}
	
	public int getRulesCountByOrgan(LeCompanyRulesRef leCompanyRulesRef) {
    	return dao.getRulesCountByOrgan(leCompanyRulesRef);
    }
	
	public int getRulesCountByQuery(LeCompanyRulesRef leCompanyRulesRef) {
		return dao.getRulesCountByQuery(leCompanyRulesRef);
	}
	
	public int getRulesCountByRulesRefId(LeCompanyRulesRef leCompanyRulesRef) {
		return dao.getRulesCountByRulesRefId(leCompanyRulesRef);
	}

}

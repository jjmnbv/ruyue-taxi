package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.StandardAccountRulesDao;
import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.lease.entity.LeAccountRulesModiLog;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.LeAccountRulesModiLogQueryParam;
import com.szyciov.lease.param.LeAccountRulesQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("standardAccountRulesService")
public class StandardAccountRulesService {
	private StandardAccountRulesDao dao;

	@Resource(name = "StandardAccountRulesDao")
	public void setDao(StandardAccountRulesDao dao) {
		this.dao = dao;
	}
	
	public PageBean getStandardAccountRulesByQuery(LeAccountRulesQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<LeAccountRules> list = getStandardAccountRulesListByQuery(queryParam);
		int iTotalRecords = getStandardAccountRulesListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<LeAccountRules> getStandardAccountRulesListByQuery(LeAccountRulesQueryParam queryParam) {
		return dao.getStandardAccountRulesListByQuery(queryParam);
	}
	
	public int getStandardAccountRulesListCountByQuery(LeAccountRulesQueryParam queryParam) {
		return dao.getStandardAccountRulesListCountByQuery(queryParam);
	}
	
	public Map<String, String> createStandardAccountRules(LeAccountRules leAccountRules) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		// 添加前检查数据是否已存在
		if (checkIsExist(leAccountRules) > 0) {
			LeAccountRules messageInfo = getMessageInfoByEqualQuery(leAccountRules);
			
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", messageInfo.getCityName() + "城市、" + messageInfo.getRulesTypeName() + "规则、"
					+ messageInfo.getCarTypeName() + "服务车型已存在");
		} else {
			List<LeAccountRules> leAccountRulesList = getStandardAccountRulesByCity(leAccountRules.getLeasesCompanyId(), leAccountRules.getCity());
			if (leAccountRulesList != null && leAccountRulesList.size() > 0 && checkIsExist2(leAccountRules) == 0) {
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "一座城市只能有一种时间类型");
			} else {
				String uuid = GUIDGenerator.newGUID();
				leAccountRules.setId(uuid);
				// 启用状态   0-启用
				leAccountRules.setRulesState("1");
				dao.createStandardAccountRules(leAccountRules);
			}
		}
		return ret;
	}
	
	public int checkIsExist(LeAccountRules leAccountRules){
		return dao.getStandardAccountRulesListCountByEqualQuery(leAccountRules);
	}
	
	public int checkIsExist2(LeAccountRules leAccountRules){
		return dao.getStandardAccountRulesListCountByEqualQuery2(leAccountRules);
	}
	
	public LeAccountRules getMessageInfoByEqualQuery(LeAccountRules leAccountRules) {
		return dao.getMessageInfoByEqualQuery(leAccountRules);
	}
	
	public Map<String, String> updateStandardAccountRules(LeAccountRules leAccountRules) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		List<LeAccountRules> leAccountRulesList = getStandardAccountRulesByCity(leAccountRules.getLeasesCompanyId(), leAccountRules.getCity());
		if(checkIsExist(leAccountRules) == 0){
			LeAccountRules messageInfo = getMessageInfoByEqualQuery(leAccountRules);
			
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", messageInfo.getCityName() + "城市、" + messageInfo.getRulesTypeName() + "规则、"
					+ messageInfo.getCarTypeName() + "服务车型不存在");
		} else if(leAccountRulesList != null && leAccountRulesList.size() > 1 && checkIsExist2(leAccountRules) == 0) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "一座城市只能有一种时间类型");
		} else {
	       LeAccountRulesModiLog leAccountRulesModiLog = copyLeAccountRulesToLeAccountRulesModiLog(leAccountRules);
			
			// 修改操作
			leAccountRulesModiLog.setModiType("0");
			// 把数据加入租赁计费规则修改历史
			insertDataIntoLeAccountRulesModiLog(leAccountRulesModiLog);
			dao.updateStandardAccountRules(leAccountRules);
			
		}
		return ret;
	}

	public void insertDataIntoLeAccountRulesModiLog(LeAccountRulesModiLog leAccountRulesModiLog) {
		dao.createStandardAccountRulesModiLog(leAccountRulesModiLog);
	}
	
	public LeAccountRulesModiLog copyLeAccountRulesToLeAccountRulesModiLog(LeAccountRules leaccountrules) {
		LeAccountRules leAccountRules = dao.getStandardAccountRulesById(leaccountrules.getId());
		LeAccountRulesModiLog leAccountRulesModiLog = new LeAccountRulesModiLog();
		BeanUtils.copyProperties(leAccountRules, leAccountRulesModiLog);
		leAccountRulesModiLog.setAccountRulesId(leAccountRules.getId());
		String uuid = GUIDGenerator.newGUID();
		leAccountRulesModiLog.setId(uuid);
		// 操作人
		leAccountRulesModiLog.setCreater(leaccountrules.getUpdater());
		leAccountRulesModiLog.setUpdater(leaccountrules.getUpdater());
		return leAccountRulesModiLog;
	}

	public Map<String, String> updateStandardAccountRulesState(LeAccountRules leAccountRules,String modiType) {
		Map<String, String> ret = new HashMap<String, String>();
		
		if(checkIsExist3(leAccountRules) == 0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "该规则不存在");
		} else {
			// 启用
			if ("1".equalsIgnoreCase(modiType)) {
				Map<String, String> accountStatus = dao.getAccountStatusByLeasesCompanyId(leAccountRules.getLeasesCompanyId());
				if (accountStatus != null
						&& (accountStatus.get("wechatstatus") != null && "1".equals(accountStatus.get("wechatstatus"))
								|| accountStatus.get("alipaystatus") != null
										&& "1".equals(accountStatus.get("alipaystatus")))
						&& (accountStatus.get("driverwechatstatus") != null
								&& "1".equals(accountStatus.get("driverwechatstatus"))
								|| accountStatus.get("driveralipaystatus") != null
										&& "1".equals(accountStatus.get("driveralipaystatus")))) {
					leAccountRules.setRulesState("0");
					ret.put("ResultSign", "Successful");
					ret.put("MessageKey", "启用成功");
				} else {
					if (accountStatus == null || accountStatus != null
							&& (accountStatus.get("wechatstatus") == null
									|| "0".equals(accountStatus.get("wechatstatus")))
							&& (accountStatus.get("alipaystatus") == null
									|| "0".equals(accountStatus.get("alipaystatus")))
							&& (accountStatus.get("driverwechatstatus") == null
									|| "0".equals(accountStatus.get("driverwechatstatus")))
							&& (accountStatus.get("driveralipaystatus") == null
									|| "0".equals(accountStatus.get("driveralipaystatus")))) {
						ret.put("ResultSign", "Error");
						ret.put("MessageKey", "启用失败，乘客和司机交易账户当前没有启用的收款账户，设置后才可启用");
					} else if (accountStatus != null
							&& (accountStatus.get("wechatstatus") == null
									|| "0".equals(accountStatus.get("wechatstatus")))
							&& (accountStatus.get("alipaystatus") == null
									|| "0".equals(accountStatus.get("alipaystatus")))) {// 乘客账户
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
					return ret;
				}
			} else if ("2".equalsIgnoreCase(modiType)) {
				// 禁用
				leAccountRules.setRulesState("1");
				ret.put("ResultSign", "Successful");
				ret.put("MessageKey", "禁用成功");
			}
			LeAccountRulesModiLog leAccountRulesModiLog = copyLeAccountRulesToLeAccountRulesModiLog(leAccountRules);
			// 启用 或 禁用
			leAccountRulesModiLog.setModiType(modiType);
			// 把数据加入租赁计费规则修改历史
			insertDataIntoLeAccountRulesModiLog(leAccountRulesModiLog);
			dao.updateStandardAccountRulesState(leAccountRules);
			
		}
		return ret;
	}
	
	public int checkIsExist3(LeAccountRules leAccountRules) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("leasesCompanyId", leAccountRules.getLeasesCompanyId());
		map.put("id", leAccountRules.getId());
		return dao.getLeAccountRulesCountById(map);
	}
	
	public Map<String, String> updateStandardAccountRulesOneKey(LeAccountRules leAccountRules) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		// 0-总用时，1-低速用时
		if ("0".equals(leAccountRules.getTimeType())) {
			// 总用时--低速用时
			leAccountRules.setTimeType("1");
		} else if ("1".equals(leAccountRules.getTimeType())) {
			// 低速用时--总用时
			leAccountRules.setTimeType("0");
		}

		if (checkIsExist2(leAccountRules) > 0) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "一座城市只能有一种时间类型");
		} else {
			dao.updateStandardAccountRulesOneKey(leAccountRules);

			// 把数据加入租赁计费规则修改历史
			insertDataIntoLeAccountRulesModiLogOneKey(leAccountRules);
		}
		return ret;
	}
	
	public void insertDataIntoLeAccountRulesModiLogOneKey(LeAccountRules leaccountrules) {
		List<LeAccountRules> leAccountRulesList = getStandardAccountRulesByCity(leaccountrules.getLeasesCompanyId(), leaccountrules.getCity());
		for (LeAccountRules leAccountRules : leAccountRulesList) {
			LeAccountRulesModiLog leAccountRulesModiLog = new LeAccountRulesModiLog();
			BeanUtils.copyProperties(leAccountRules, leAccountRulesModiLog);
			leAccountRulesModiLog.setAccountRulesId(leAccountRules.getId());
			String uuid = GUIDGenerator.newGUID();
			leAccountRulesModiLog.setId(uuid);
			// 一键更换
			leAccountRulesModiLog.setModiType("3");
			// 操作人
			leAccountRulesModiLog.setCreater(leaccountrules.getUpdater());
			leAccountRulesModiLog.setUpdater(leaccountrules.getUpdater());
			// 把数据加入租赁计费规则修改历史
			insertDataIntoLeAccountRulesModiLog(leAccountRulesModiLog);
		}
	}
	
	public PageBean getStandardAccountRulesModiLogByQuery(LeAccountRulesModiLogQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<LeAccountRulesModiLog> list = getStandardAccountRulesModiLogListByQuery(queryParam);
		int iTotalRecords = getStandardAccountRulesModiLogListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<LeAccountRulesModiLog> getStandardAccountRulesModiLogListByQuery(LeAccountRulesModiLogQueryParam queryParam) {
		return dao.getStandardAccountRulesModiLogListByQuery(queryParam);
	}
	
	public int getStandardAccountRulesModiLogListCountByQuery(LeAccountRulesModiLogQueryParam queryParam) {
		return dao.getStandardAccountRulesModiLogListCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getStandardAccountRulesExistCityList(String leasesCompanyId, String cityName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("leasesCompanyId", leasesCompanyId);
		map.put("cityName", cityName);
		return dao.getStandardAccountRulesExistCityList(map);
    }
	
	public List<PubDictionary> getPubDictionaryByType(String type) {
		return dao.getPubDictionaryByType(type);
	}
	
	public List<LeVehiclemodels> getStandardAccountRulesExistCarTypeList(String leasesCompanyId) {
		return dao.getStandardAccountRulesExistCarTypeList(leasesCompanyId);
	}
	
	public List<LeVehiclemodels> getCarTypeList(String leasesCompanyId) {
		return dao.getCarTypeList(leasesCompanyId);
	}
	
	public LeAccountRules getStandardAccountRulesById(String id) {
		return dao.getStandardAccountRulesById(id);
	}
	
	public List<LeAccountRules> getStandardAccountRulesByCity(String leasesCompanyId, String city) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("leasesCompanyId", leasesCompanyId);
		map.put("city", city);
		List<LeAccountRules> leAccountRulesList = dao.getStandardAccountRulesByCity(map);
		return leAccountRulesList;
	}
	
	public LeAccountRules getOneStandardAccountRulesByCity(String leasesCompanyId, String city) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("leasesCompanyId", leasesCompanyId);
		map.put("city", city);
		LeAccountRules leAccountRules = dao.getOneStandardAccountRulesByCity(map);
		return leAccountRules;
	}
	
	public JSONObject getPubCityAddrList() {
		JSONObject ret = new JSONObject();
		
		List<PubCityAddr> cityAddrList = dao.getPubCityAddrList();
		if(null != cityAddrList && !cityAddrList.isEmpty()) {
			Iterator<PubCityAddr> iterator = cityAddrList.iterator();
			while(iterator.hasNext()) {
				PubCityAddr cityAddr = iterator.next();
				String id = cityAddr.getId();
				String city = cityAddr.getCity();
				String initials = cityAddr.getCityInitials();
				if(StringUtils.isBlank(id) || StringUtils.isBlank(city) || StringUtils.isBlank(initials)) {
					continue;
				}
				//根据字母对城市进行分类
				JSONObject json = new JSONObject();
				json.put("id", id);
				json.put("text", city);
				if(ret.containsKey(initials)) {
					ret.getJSONArray(initials).add(json);
				} else {
					JSONArray arr = new JSONArray();
					arr.add(json);
					ret.put(initials, arr);
				}
			}
		}
		return ret;
	}
	
}

package com.szyciov.operate.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.szyciov.enums.PayAccountStatusEnum;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.OpAccountrules;
import com.szyciov.op.entity.OpAccountrulesModilog;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.param.OpAccountruleQueryParam;
import com.szyciov.op.param.OpAccountrulesModilogQueryParam;
import com.szyciov.operate.dao.OpAccountrulesDao;
import com.szyciov.operate.dao.OpAccountrulesModilogDao;
import com.szyciov.operate.dao.OpInformationSetDao;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("OpAccountrulesService")
public class OpAccountrulesService {
	
	private OpAccountrulesDao dao;
	@Resource(name = "OpAccountrulesDao")
	public void setDao(OpAccountrulesDao dao) {
		this.dao = dao;
	}
	
	private OpAccountrulesModilogDao modilogDao;
	@Resource(name = "OpAccountrulesModilogDao")
	public void setModilogDao(OpAccountrulesModilogDao modilogDao) {
		this.modilogDao = modilogDao;
	}
	
	private OpInformationSetDao informationSetDao;
	@Resource(name = "OpInformationSetDao")
	public void setInformationSetDao(OpInformationSetDao informationSetDao) {
		this.informationSetDao = informationSetDao;
	}
	
	public PageBean getOpAccountRulesByQuery(OpAccountruleQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		int recordCount = getOpAccountRulesCountByQuery(queryParam);
		pageBean.setiTotalDisplayRecords(recordCount);
		pageBean.setiTotalRecords(recordCount);
		pageBean.setAaData(getOpAccountRulesListByQuery(queryParam));
		return pageBean;
	}
	
	public Map<String, String> createOpAccountRules(OpAccountrules object) {
		Map<String, String> ret = new HashMap<String, String>();
		//验证城市名称、规则类型、服务车型联合主键是否唯一
		OpAccountrules accountrulesKey = new OpAccountrules();
		accountrulesKey.setCity(object.getCity());
		accountrulesKey.setRulestype(object.getRulestype());
		accountrulesKey.setVehiclemodelsid(object.getVehiclemodelsid());
		List<OpAccountrules> accountrulesKeyList = getOpAccountrulesByList(accountrulesKey);
		if(null != accountrulesKeyList && !accountrulesKeyList.isEmpty()) {
			accountrulesKey = accountrulesKeyList.get(0);
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", accountrulesKey.getCityName() + "城市、" + accountrulesKey.getRulestypeName() + "规则、"
					+ accountrulesKey.getVehiclemodelsName() + "服务车型已存在");
			return ret;
		}
		
		//验证城市名称只能有一种时间类型
		OpAccountrules accountrules = new OpAccountrules();
		accountrules.setCity(object.getCity());
		if(object.getTimetype().equals("0")) {
			accountrules.setTimetype("1");
		} else {
			accountrules.setTimetype("0");
		}
		List<OpAccountrules> accountrulesList = getOpAccountrulesByList(accountrules);
		if(null != accountrulesList && !accountrulesList.isEmpty()) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "一座城市只能有一种时间类型");
			return ret;
		}
		
		//添加计费规则
		Date currentTime = new Date();
		object.setId(GUIDGenerator.newGUID());
		OpPlatformInfo platformInfo = informationSetDao.getOpPlatformInfo();
		if(checkPlatformStatusUser(platformInfo) && checkPlatformStatusDriver(platformInfo)) {
			object.setRulesstate("0");
		} else {
			object.setRulesstate("1");
		}
		object.setCreatetime(currentTime);
		object.setUpdatetime(currentTime);
		object.setStatus(1);
		insertOpAccountrules(object);
		//添加计费规则历史记录
		createOpAccountrulesModilog(object, "4");
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		return ret;
	}
	
	public Map<String, String > editOpAccountRules(OpAccountrules object) {
		Map<String, String> ret = new HashMap<String, String>();
		
		//验证城市名称、规则类型、服务车型联合主键是否唯一
		OpAccountrules accountrulesKey = new OpAccountrules();
		accountrulesKey.setCity(object.getCity());
		accountrulesKey.setRulestype(object.getRulestype());
		accountrulesKey.setVehiclemodelsid(object.getVehiclemodelsid());
		List<OpAccountrules> accountrulesKeyList = getOpAccountrulesByList(accountrulesKey);
		if (null != accountrulesKeyList && !accountrulesKeyList.isEmpty()) {
			for (OpAccountrules opAccountrules : accountrulesKeyList) {
				if(!opAccountrules.getId().equals(object.getId())) {					
					ret.put("ResultSign", "Error");
					ret.put("MessageKey", opAccountrules.getCityName() + "城市、" + opAccountrules.getRulestypeName() + "规则、"
							+ opAccountrules.getVehiclemodelsName() + "服务车型已存在");
					return ret;
				}
			}			
		}
		
		//验证城市名称只能有一种时间类型
		OpAccountrules accountrules = new OpAccountrules();
		accountrules.setCity(object.getCity());
		if(object.getTimetype().equals("0")) {
			accountrules.setTimetype("1");
		} else {
			accountrules.setTimetype("0");
		}
		List<OpAccountrules> accountrulesList = getOpAccountrulesByList(accountrules);
		if(null != accountrulesList && !accountrulesList.isEmpty()) {
			for (OpAccountrules opAccountrules : accountrulesList) {
				if(!opAccountrules.getId().equals(object.getId())) {
					ret.put("ResultSign", "Error");
					ret.put("MessageKey", "一座城市只能有一种时间类型");
					return ret;
				}
			}
		}
		//修改计费规则
		Date currentTime = new Date();
		object.setUpdatetime(currentTime);
		//添加计费规则历史记录
		createOpAccountrulesModilog(object, "0");
		updateOpAccountrules(object);
		
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "修改成功");
		return ret;
	}
	
	public Map<String, String> editOpAccountRulesState(OpAccountrules object) {
		Map<String, String> ret = new HashMap<String, String>();
		String moditype = "0";
		if(object.getRulesstate().equals("0")) { //启用
			OpPlatformInfo platformInfo = informationSetDao.getOpPlatformInfo();
			//查询平台账户信息
			if (!checkPlatformStatusUser(platformInfo) && !checkPlatformStatusDriver(platformInfo)) {
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "启用失败，乘客和司机交易账户当前没有启用的收款账户，设置后才可启用");
				return ret;
			}
			if (!checkPlatformStatusUser(platformInfo) && checkPlatformStatusDriver(platformInfo)) {
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "启用失败，乘客交易账户当前没有启用的收款账户，设置后才可启用");
				return ret;
			}
			if (checkPlatformStatusUser(platformInfo) && !checkPlatformStatusDriver(platformInfo)) {
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "启用失败，司机交易账户当前没有启用的收款账户，设置后才可启用");
				return ret;
			}
			moditype = "1";
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "启用成功");
		} else if(object.getRulesstate().equals("1")) { //禁用
			moditype = "2";
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "禁用成功");
		}
		//修改计费规则状态
		Date currentTime = new Date();
		object.setUpdatetime(currentTime);
		updateOpAccountrules(object);
		//添加计费规则历史记录
		createOpAccountrulesModilog(object, moditype);
		return ret;
	}
	
	public PageBean getOpAccountRulesModiLogByQuery(OpAccountrulesModilogQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		int recordCount = getOpAccountRulesModiLogCountByQuery(queryParam);
		pageBean.setiTotalDisplayRecords(recordCount);
		pageBean.setiTotalRecords(recordCount);
		pageBean.setAaData(getOpAccountRulesModiLogListByQuery(queryParam));
		return pageBean;
	}
	
	public Map<String, String> editOpAccountRulesByCity(OpAccountrules object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "更换成功");
		Date currentTime = new Date();
		object.setUpdatetime(currentTime);
		OpAccountrules accountrules = new OpAccountrules();
		accountrules.setCity(object.getCity());
		List<OpAccountrules> opAccountrulesList = getOpAccountrulesByList(accountrules);
		if(null != opAccountrulesList && !opAccountrulesList.isEmpty()) {
			for (OpAccountrules opAccountrules : opAccountrulesList) {
				createOpAccountrulesModilog(opAccountrules, "3");
			}
		}
		updateOpAccountRulesByCity(object);
		
		return ret;
	}
	
	private void createOpAccountrulesModilog(OpAccountrules object, String moditype) {
		object = getOpAccountrulesById(object.getId());
		OpAccountrulesModilog accountrulesModilog = new OpAccountrulesModilog();
		BeanUtils.copyProperties(object, accountrulesModilog);
		accountrulesModilog.setAccountrulesid(object.getId());
		accountrulesModilog.setId(GUIDGenerator.newGUID());
		accountrulesModilog.setCartype(object.getVehiclemodelsid());
		accountrulesModilog.setModitype(moditype);
		accountrulesModilog.setCreatetime(object.getUpdatetime());
		accountrulesModilog.setCreater(object.getUpdater());
		accountrulesModilog.setStatus(1);
		modilogDao.insertOpAccountrulesModilog(accountrulesModilog);
	}
	
	/**
	 * 查询运管端乘客收款账户状态
	 * @return
	 */
	private boolean checkPlatformStatusUser(OpPlatformInfo platformInfo) {
		//查询平台账户信息
		if (null == platformInfo || ((null == platformInfo.getAlipaystatus()
				|| PayAccountStatusEnum.FORBIDDEN.code.equals(platformInfo.getAlipaystatus()))
				&& (null == platformInfo.getWechatstatus()
						|| PayAccountStatusEnum.FORBIDDEN.code.equals(platformInfo.getWechatstatus())))) {
			return false;
		}
		return true;
	}
	/**
	 * 查询运管端司机收款账户状态
	 * @return
	 */
	private boolean checkPlatformStatusDriver(OpPlatformInfo platformInfo) {
		//查询平台账户信息
		if (null == platformInfo || ((null == platformInfo.getDriveralipaystatus()
				|| PayAccountStatusEnum.FORBIDDEN.code.equals(platformInfo.getDriveralipaystatus()))
				&& (null == platformInfo.getDriverwechatstatus()
						|| PayAccountStatusEnum.FORBIDDEN.code.equals(platformInfo.getDriverwechatstatus())))) {
			return false;
		}
		return true;
	}
	public List<OpVehiclemodels> getModelsByList() {
		return dao.getModelsByList();
	}
	
	public List<Map<String, String>> getCityByList(PubCityAddr object) {
		return dao.getCityByList(object);
	}
	
	public List<OpAccountrules> getOpAccountRulesListByQuery(OpAccountruleQueryParam queryParam) {
		return dao.getOpAccountRulesListByQuery(queryParam);
	}
	
	public int getOpAccountRulesCountByQuery(OpAccountruleQueryParam queryParam) {
		return dao.getOpAccountRulesCountByQuery(queryParam);
	}
	
	public OpAccountrules getOpAccountrulesById(String id) {
		return dao.getOpAccountrulesById(id);
	}
	
	public List<OpAccountrules> getOpAccountrulesByList(OpAccountrules object) {
		return dao.getOpAccountrulesByList(object);
	}
	
	public void insertOpAccountrules(OpAccountrules object) {
		dao.insertOpAccountrules(object);
	}
	
	public void updateOpAccountrules(OpAccountrules object) {
		dao.updateOpAccountrules(object);
	}
	
	public List<OpAccountrulesModilog> getOpAccountRulesModiLogListByQuery(OpAccountrulesModilogQueryParam queryParam) {
		return modilogDao.getOpAccountRulesModiLogListByQuery(queryParam);
	}
	
	public int getOpAccountRulesModiLogCountByQuery(OpAccountrulesModilogQueryParam queryParam) {
		return modilogDao.getOpAccountRulesModiLogCountByQuery(queryParam);
	}
	
	public void updateOpAccountRulesByCity(OpAccountrules object) {
		dao.updateOpAccountRulesByCity(object);
	}
	
}

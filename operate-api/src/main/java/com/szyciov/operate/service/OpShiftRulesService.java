package com.szyciov.operate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.op.entity.OpShiftRules;
import com.szyciov.op.param.OpShiftRulesQueryParam;
import com.szyciov.operate.dao.OpShiftRulesDao;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("opShiftRulesService")
public class OpShiftRulesService {
	
	private OpShiftRulesDao dao;

	@Resource(name = "opShiftRulesDao")
	public void setDao(OpShiftRulesDao dao) {
		this.dao = dao;
	}
	
	public List<Map<String, String>> getVailableCitys(Map<String, Object> params) {
		return dao.getVailableCitys(params);
	}

	public PageBean getShiftRulesByQuery(OpShiftRulesQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OpShiftRules> list = getShiftRulesListByQuery(queryParam);
		int iTotalRecords = getShiftRulesListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}

	private int getShiftRulesListCountByQuery(OpShiftRulesQueryParam queryParam) {
		return dao.getShiftRulesListCountByQuery(queryParam);
	}

	private List<OpShiftRules> getShiftRulesListByQuery(OpShiftRulesQueryParam queryParam) {
		return dao.getShiftRulesListByQuery(queryParam);
	}

	public OpShiftRules getShiftRules(Map<String, Object> params) {
		return dao.getShiftRules(params);
	}

	public Map<String, Object> createShiftRules(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("status", "success");
		res.put("message", "新增规则成功");
		if(dao.hasCity(params)){
			res.put("status", "fail");
			res.put("message","当前城市已有交接班规则");
		}else{
			params.put("id",GUIDGenerator.newGUID());
			dao.createShiftRules(params);
		}
		return res;
	}

	public Map<String, Object> updateShiftRules(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("status", "success");
		res.put("message", "维护规则成功");
		if(dao.hasCity(params)){
			res.put("status", "fail");
			res.put("message","当前城市已有交接班规则");
		}else{
			dao.updateShiftRules(params);
		}
		return res;
	}

	public List<Map<String, String>> getCitys(Map<String, Object> params) {
		return dao.getCitys(params);
	}

	/**
	 * 返回交接班规则
	 * @param city			城市
	 * @return
	 */
	public OpShiftRules getRules( String city){
		return dao.getRules(city);
	}
}

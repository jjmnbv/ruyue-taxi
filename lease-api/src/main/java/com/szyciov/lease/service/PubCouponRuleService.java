package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.entity.coupon.PubCouponRuleHistory;
import com.szyciov.enums.CouponRuleTypeEnum;
import com.szyciov.lease.dao.PubCouponRuleDao;
import com.szyciov.lease.param.PubCouponRuleQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("pubCouponRuleService")
public class PubCouponRuleService {
	private PubCouponRuleDao dao;

	@Resource(name = "PubCouponRuleDao")
	public void setDao(PubCouponRuleDao dao) {
		this.dao = dao;
	}
	
	// 查询发放规则信息
	public PageBean getPubCouponRuleByQuery(PubCouponRuleQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PubCouponRule> list = getPubCouponRuleListByQuery(queryParam);
		int iTotalRecords = getPubCouponRuleListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<PubCouponRule> getPubCouponRuleListByQuery(PubCouponRuleQueryParam queryParam) {
    	return dao.getPubCouponRuleListByQuery(queryParam);
    }
	
    public int getPubCouponRuleListCountByQuery(PubCouponRuleQueryParam queryParam) {
    	return dao.getPubCouponRuleListCountByQuery(queryParam);
    }
	
    public Map<String, String> createPubCouponRule(PubCouponRule pubCouponRule) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "规则新增成功");
		// 查找规则名称是否已存在
		if (checkRuleNameExist(pubCouponRule.getName()) > 0) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "规则名称已存在");
		} else {
			pubCouponRule.setId(GUIDGenerator.newGUID());
			// 个人用户
			//pubCouponRule.setRuletarget(3);
			// 获取规则内容
			String ruleContent = getRuleContent(pubCouponRule);
			pubCouponRule.setRulecontent(ruleContent);
			// 保存新增规则
			dao.createPubCouponRule(pubCouponRule);
		}
		return ret;
    }
    
    public int checkRuleNameExist(String name) {
    	return dao.checkRuleNameExist(name);
    }
    
    public String getRuleContent(PubCouponRule pubCouponRule) {
    	StringBuffer ruleContent = new StringBuffer();
    	// 消费返券
    	if (CouponRuleTypeEnum.EXPENSE.value.equals(pubCouponRule.getRuletype())) {
    		// 按消费频次
    		if (CouponRuleTypeEnum.FRENQUENCY.value.equals(pubCouponRule.getConsumetype())) {
    			ruleContent.append("周期").append(pubCouponRule.getCycleday()).append("天   ");
    			if (CouponRuleTypeEnum.FULL.value.equals(pubCouponRule.getConsumefrequencytype())) {
    				// 1-满
    				ruleContent.append("累计消费次数满").append(pubCouponRule.getConsumelowtimes()).append("次");
    			} else if (CouponRuleTypeEnum.FULL_LOW.value.equals(pubCouponRule.getConsumefrequencytype())) {
    				// 2-满低
    				ruleContent.append("累计消费次数满").append(pubCouponRule.getConsumelowtimes()).append("次低于").append(pubCouponRule.getConsumehightimes()).append("次");
    			} else if (CouponRuleTypeEnum.LOW.value.equals(pubCouponRule.getConsumefrequencytype())) {
    				// 3-低
    				ruleContent.append("累计消费次数低于").append(pubCouponRule.getConsumehightimes()).append("次");
    			}
    		} else if (CouponRuleTypeEnum.MONETARY.value.equals(pubCouponRule.getConsumetype())) {
    			// 按消费金额
    			// 单次消费可用
    			if (CouponRuleTypeEnum.USABLE.value.equals(pubCouponRule.getConsumemoneysingleable())) {
    				ruleContent.append("单次消费金额满").append(pubCouponRule.getConsumemoneysingelfull()).append("元   ");
    			}
    			// 周期消费可用
    			if (CouponRuleTypeEnum.USABLE.value.equals(pubCouponRule.getConsumemoneycycleable())) {
    				ruleContent.append("周期").append(pubCouponRule.getCycleday()).append("天   ");
    				if (CouponRuleTypeEnum.FULL.value.equals(pubCouponRule.getConsumemoneycycletype())) {
    					// 1-满
    					ruleContent.append("累计消费金额满").append(pubCouponRule.getConsumemoneycyclelow()).append("元");
    				} else if (CouponRuleTypeEnum.FULL_LOW.value.equals(pubCouponRule.getConsumemoneycycletype())) {
    					// 2-满低
    					ruleContent.append("累计消费金额满").append(pubCouponRule.getConsumemoneycyclelow()).append("元低于").append(pubCouponRule.getConsumemoneycyclefull()).append("元");
    				} else if (CouponRuleTypeEnum.LOW.value.equals(pubCouponRule.getConsumemoneycycletype())) {
    					// 3-低
    					ruleContent.append("累计消费金额低于").append(pubCouponRule.getConsumemoneycyclefull()).append("元");
    				}
    			}
    		}
    	} else if (CouponRuleTypeEnum.RECHARGE.value.equals(pubCouponRule.getRuletype())) {
    		// 充值返券
    		ruleContent.append("充值金额满").append(pubCouponRule.getRechargemoney()).append("元");
    	}
    	return ruleContent.toString();
    }
    
    public Map<String, String> updatePubCouponRule(PubCouponRule pubCouponRule) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "规则修改成功");
		
		// 新增一条历史记录
		createPubCouponRuleHistory(pubCouponRule);
		
		// 获取规则内容
		String ruleContent = getRuleContent(pubCouponRule);
		pubCouponRule.setRulecontent(ruleContent);
		// 修改规则
		dao.updatePubCouponRule(pubCouponRule);
		
		return ret;
    }

    public void createPubCouponRuleHistory(PubCouponRule pubCouponRule) {
    	PubCouponRuleHistory pubCouponRuleHistory = new PubCouponRuleHistory();
    	pubCouponRuleHistory.setId(GUIDGenerator.newGUID());
    	pubCouponRuleHistory.setUpdater(pubCouponRule.getUpdater());
    	pubCouponRuleHistory.setRuleidref(pubCouponRule.getId());
    	dao.createPubCouponRuleHistory(pubCouponRuleHistory);
    }
	
    // 查询历史规则记录
 	public PageBean getPubCouponRuleHistoryByQuery(PubCouponRuleQueryParam queryParam) {
 		PageBean pageBean = new PageBean();
 		pageBean.setsEcho(queryParam.getsEcho());
 		List<PubCouponRuleHistory> list = getPubCouponRuleHistoryListByQuery(queryParam);
 		int iTotalRecords = getPubCouponRuleHistoryListCountByQuery(queryParam);
 		int iTotalDisplayRecords = iTotalRecords;
 		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
 		pageBean.setiTotalRecords(iTotalRecords);
 		pageBean.setAaData(list);
 		return pageBean;
 	}
    
    public List<PubCouponRuleHistory> getPubCouponRuleHistoryListByQuery(PubCouponRuleQueryParam queryParam) {
    	return dao.getPubCouponRuleHistoryListByQuery(queryParam);
    }
	
    public int getPubCouponRuleHistoryListCountByQuery(PubCouponRuleQueryParam queryParam) {
    	return dao.getPubCouponRuleHistoryListCountByQuery(queryParam);
    }
    
    public PubCouponRule getPubCouponRuleById(String id) {
    	return dao.getPubCouponRuleById(id);
    }
}

package com.szyciov.lease.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.szyciov.dto.pubOrderCancelRule.PubOrderCancelRule;
import com.szyciov.dto.pubOrderCancelRule.PubOrderCancelRuleHistory;
import com.szyciov.lease.dao.PubOrderCancelRuleDao;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.PageBean;

import net.sf.json.JSONObject;

@Service("pubOrderCancelRuleService")
public class PubOrderCancelRuleService {
	private PubOrderCancelRuleDao dao;
	@Resource(name = "PubOrderCancelRuleDao")
	public void setDao(PubOrderCancelRuleDao dao) {
		this.dao = dao;
	}
	public List<PubDictionary> getCity()  {
		return dao.getCity();
	}
	public PageBean getPubOrderCancelRule(PubOrderCancelRule pubOrderCancelRule){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(pubOrderCancelRule.getsEcho());  
		List<PubOrderCancelRule> list = getPubOrderCancelRuleList(pubOrderCancelRule);
		int iTotalRecords = getPubOrderCancelRuleCount(pubOrderCancelRule);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<PubOrderCancelRule> getPubOrderCancelRuleList(PubOrderCancelRule pubOrderCancelRule){
		return dao.getPubOrderCancelRuleList(pubOrderCancelRule);
	}
	public int getPubOrderCancelRuleCount(PubOrderCancelRule pubOrderCancelRule){
		return dao.getPubOrderCancelRuleCount(pubOrderCancelRule);
	}
	public Map<String, String> aadPubOrderCancelRule(PubOrderCancelRule pubOrderCancelRule){
		//通过城市名称获取城市id
		String cityId = dao.getCityId(pubOrderCancelRule.getCitycode());
		pubOrderCancelRule.setCitycode(cityId);
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		//判断是新增还是修改
		if(pubOrderCancelRule.getId() == ""){
			//新增判断一个城市，一种业务类型只能有一个取消规则
			int count = dao.ifHaveRule(pubOrderCancelRule);
			if(count == 0){
				String id = GUIDGenerator.newGUID();
				pubOrderCancelRule.setId(id);
			    dao.aadPubOrderCancelRule(pubOrderCancelRule);
			}else{
				ret.put("haveSame", "noOk");
				return ret; 
			}
		}else{
		 PubOrderCancelRule searchData = dao.searchRule(pubOrderCancelRule);
		 searchData.setOperationtype("2");
		  addHisrory(searchData);
		 dao.modifyPubOrderCancelRule(pubOrderCancelRule);
		 //添加缓存
		 insetRedis(pubOrderCancelRule.getId(),2);
		}
		return ret;
	}
	//禁用规则
	public int ruleConflictOk(PubOrderCancelRule pubOrderCancelRule){
		 PubOrderCancelRule searchData = dao.searchRule(pubOrderCancelRule);
		  searchData.setOperationtype("1");
		  addHisrory(searchData);
		 dao.ruleConflictOk(pubOrderCancelRule);
		 //添加缓存
		 insetRedis(pubOrderCancelRule.getId(),1);
		 return 0;
	}
	//启用规则
		public int ruleConflict(PubOrderCancelRule pubOrderCancelRule){
			//查询规则
			  PubOrderCancelRule searchData = dao.searchRule(pubOrderCancelRule);
			  searchData.setOperationtype("0");
			  addHisrory(searchData);
			 dao.ruleConflict(pubOrderCancelRule);
			 //添加缓存
			 insetRedis(pubOrderCancelRule.getId(),0);
			 //存入历史记录
			 return 0;
		}
	//存入历史记录
	public void addHisrory(PubOrderCancelRule pubOrderCancelRule){
		PubOrderCancelRuleHistory history = new PubOrderCancelRuleHistory();
		String id = GUIDGenerator.newGUID();
		history.setId(id);
		history.setCancelruleid(pubOrderCancelRule.getId());
//		history.setOperationtype("");
		history.setCitycode(pubOrderCancelRule.getCitycode());
		history.setCartype(pubOrderCancelRule.getCartype());
		history.setCancelcount(pubOrderCancelRule.getCancelcount());
		history.setLatecount(pubOrderCancelRule.getLatecount());
		history.setWatingcount(pubOrderCancelRule.getWatingcount());
		history.setPrice(pubOrderCancelRule.getPrice());
		history.setPlatformtype("1");
		history.setOperationtype(pubOrderCancelRule.getOperationtype());
		history.setLeasescompanyid(pubOrderCancelRule.getLeasescompanyid());
		history.setCreater(pubOrderCancelRule.getCreater());
		history.setUpdater(pubOrderCancelRule.getUpdater());
		history.setStatus("1");
		dao.addHistory(history);
	}
	public PageBean getHistoryData(PubOrderCancelRuleHistory history){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(history.getsEcho());  
		List<PubOrderCancelRuleHistory> list = getHistoryDataList(history);
		int iTotalRecords = getHistoryDataCount(history);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<PubOrderCancelRuleHistory> getHistoryDataList(PubOrderCancelRuleHistory history){
		return dao.getHistoryDataList(history);
	}
	public int getHistoryDataCount(PubOrderCancelRuleHistory history){
		return dao.getHistoryDataCount(history);
	}
	public PubOrderCancelRule getRulename(String id)  {
		return dao.getRulename(id);
	}
	//存入redis
		//对修改，禁用，启用存入redis
		public void insetRedis(String id,int type){
			//通过id获取取消规则信息
			PubOrderCancelRule pubOrderCancelRule = dao.getRuleById(id);
			//拼接key
			String theKey = "ORDERCANCELRULE"+"_"+pubOrderCancelRule.getCitycode()+"_"+pubOrderCancelRule.getCartype()+"_"+pubOrderCancelRule.getPlatformtype()+"_"+pubOrderCancelRule.getLeasescompanyid()+"_"+pubOrderCancelRule.getId();
			if(type == 0){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				com.szyciov.entity.PubOrderCancelRule rule = new com.szyciov.entity.PubOrderCancelRule();
				rule.setCancelcount(Integer.parseInt(pubOrderCancelRule.getCancelcount()));
				rule.setCartype(Integer.parseInt(pubOrderCancelRule.getCartype()));
				rule.setCitycode(pubOrderCancelRule.getCitycode());
				rule.setCreater(pubOrderCancelRule.getCreater());
				try {
					rule.setCreatetime(sdf.parse(pubOrderCancelRule.getCreatetime()));
					rule.setUpdatetime(sdf.parse(pubOrderCancelRule.getUpdatetime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				rule.setId(pubOrderCancelRule.getId());
				rule.setLatecount(Integer.parseInt(pubOrderCancelRule.getLatecount()));
				rule.setPlatformtype(Integer.parseInt(pubOrderCancelRule.getPlatformtype()));
				rule.setPrice(Integer.parseInt(pubOrderCancelRule.getPrice()));
				rule.setStatus(Integer.parseInt(pubOrderCancelRule.getStatus()));
				rule.setUpdater(pubOrderCancelRule.getUpdater());
				rule.setWatingcount(Integer.parseInt(pubOrderCancelRule.getWatingcount()));
				JSONObject jsonRule = JSONObject.fromObject(rule);
				JedisUtil.setString(theKey,jsonRule.toString());
			}else if(type == 1){
				JedisUtil.delKey(theKey);
			}else{
				JedisUtil.delKey(theKey);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				com.szyciov.entity.PubOrderCancelRule rule = new com.szyciov.entity.PubOrderCancelRule();
				rule.setCancelcount(Integer.parseInt(pubOrderCancelRule.getCancelcount()));
				rule.setCartype(Integer.parseInt(pubOrderCancelRule.getCartype()));
				rule.setCitycode(pubOrderCancelRule.getCitycode());
				rule.setCreater(pubOrderCancelRule.getCreater());
				try {
					rule.setCreatetime(sdf.parse(pubOrderCancelRule.getCreatetime()));
					rule.setUpdatetime(sdf.parse(pubOrderCancelRule.getUpdatetime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				rule.setId(pubOrderCancelRule.getId());
				rule.setLatecount(Integer.parseInt(pubOrderCancelRule.getLatecount()));
				rule.setPlatformtype(Integer.parseInt(pubOrderCancelRule.getPlatformtype()));
				rule.setPrice(Integer.parseInt(pubOrderCancelRule.getPrice()));
				rule.setStatus(Integer.parseInt(pubOrderCancelRule.getStatus()));
				rule.setUpdater(pubOrderCancelRule.getUpdater());
				rule.setWatingcount(Integer.parseInt(pubOrderCancelRule.getWatingcount()));
				JSONObject jsonRule = JSONObject.fromObject(rule);
				JedisUtil.setString(theKey,jsonRule.toString());
			}
		}
}

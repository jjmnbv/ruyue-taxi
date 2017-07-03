package com.szyciov.lease.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.szyciov.entity.DataStatus;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.dao.LeShiftRulesDao;
import com.szyciov.lease.entity.LeShiftRule;
import com.szyciov.param.Select2Param;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
/**
  * @ClassName LeShiftRulesService
  * @author Efy
  * @Description 交接班规则Service
  * @date 2017年3月2日 14:07:02
  */ 
@Service("LeShiftRulesService")
public class LeShiftRulesService{
	private static final Logger logger = Logger.getLogger(LeShiftRulesService.class);
	
	/**
	  *依赖
	  */
	private LeShiftRulesDao leshiftrulesdao;

	/**
	  *依赖注入
	  */
	@Resource(name="LeShiftRulesDao")
	public void setLeShiftRulesDao(LeShiftRulesDao leshiftrulesdao){
		this.leshiftrulesdao=leshiftrulesdao;
	}

	public List<Select2Entity> getCityListForSelect(Select2Param param){
		List<Select2Entity> list = leshiftrulesdao.citySelect(param);
		return list;
	}
	
	public PageBean getLeShiftRules(LeShiftRule rule){
		logger.info(rule.getLeasescompanyid() + "检索交接班规则:" + rule.getCityname()==null?"全部":rule.getCityname());
		int totalCount = leshiftrulesdao.getTotalCount(rule);
		logger.info(rule.getLeasescompanyid() + "检索到的总条目:" + totalCount);
		List<LeShiftRule> list = leshiftrulesdao.search(rule);
		logger.info(rule.getLeasescompanyid() + "当前页个数:" + list.size());
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(rule.getsEcho()+"");
		pageBean.setiTotalDisplayRecords(totalCount);
		pageBean.setiTotalRecords(totalCount);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public boolean duplicateCheck(LeShiftRule rule){
		return leshiftrulesdao.searchByCity(rule) == null;
	}
	
	public boolean addRules(LeShiftRule rule){
		boolean  exits = duplicateCheck(rule);
		if(!exits) {
			logger.info(rule.getLeasescompanyid() + "同一城市只能有一条交接班规则:" + rule.getCityname());
			return false;
		}
		rule.setId(GUIDGenerator.newGUID());
		rule.setStatus(DataStatus.OK.code);
		rule.setCreatetime(new Date());
		rule.setUpdatetime(new Date());
		leshiftrulesdao.saveOrUpdate(rule);
		return true;
	}
	
	public boolean modifyRules(LeShiftRule rule){
		logger.info(rule.getLeasescompanyid() + "修改交接班规则:" + rule.getCityname());
		rule.setUpdatetime(new Date());
		leshiftrulesdao.saveOrUpdate(rule);
		return true;
	}


	/**
	 * 返回交接班规则
	 * @param leaseId		租赁公司ID
	 * @param city			城市
	 * @return
	 */
	public LeShiftRule getRules(String leaseId,String city){
		return leshiftrulesdao.getRules(leaseId,city);
	}
}

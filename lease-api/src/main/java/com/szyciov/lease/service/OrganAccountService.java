package com.szyciov.lease.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.szyciov.entity.PubCouponDetail;
import com.szyciov.enums.CouponRuleTypeEnum;
import com.szyciov.enums.coupon.CouponUsetypeEnum;
import com.szyciov.lease.dao.OrganAccountDao;
import com.szyciov.lease.entity.OrgOrganExpenses;
import com.szyciov.lease.entity.OrganAccountInfo;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.OrganAccountQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

@Service("organAccountService")
public class OrganAccountService {
	private OrganAccountDao dao;
	private TemplateHelper templateHelper = new TemplateHelper();
	private static final Logger logger = Logger.getLogger(OrganAccountService.class);

	@Resource(name = "OrganAccountDao")
	public void setDao(OrganAccountDao dao) {
		this.dao = dao;
	}
	
	public PageBean getOrganAccountInfoByQuery(OrganAccountQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrganAccountInfo> list = getOrganAccountInfoListByQuery(queryParam);
		int iTotalRecords = getOrganAccountInfoListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<OrganAccountInfo> getOrganAccountInfoListByQuery(OrganAccountQueryParam organAccountQueryParam) {
    	return dao.getOrganAccountInfoListByQuery(organAccountQueryParam);
    }
	
	public int getOrganAccountInfoListCountByQuery(OrganAccountQueryParam organAccountQueryParam) {
		return dao.getOrganAccountInfoListCountByQuery(organAccountQueryParam);
	}
	
	public PageBean getOrganExpensesByQuery(OrganAccountQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrgOrganExpenses> list = getOrganExpensesListByQuery(queryParam);
		int iTotalRecords = getOrganExpensesListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<OrgOrganExpenses> getOrganExpensesListByQuery(OrganAccountQueryParam organAccountQueryParam) {
		return dao.getOrganExpensesListByQuery(organAccountQueryParam);
	}
	
	public int getOrganExpensesListCountByQuery(OrganAccountQueryParam organAccountQueryParam) {
		return dao.getOrganExpensesListCountByQuery(organAccountQueryParam);
	}
	
	public Map<String, String> rechargeOrganAccount(OrgOrganExpenses orgOrganExpenses) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");

		Map<String, Object> map = new HashMap<String, Object>();
		// 机构、人员、租赁公司关联 id
		map.put("id", orgOrganExpenses.getId());
		// 充值金额
		map.put("value", orgOrganExpenses.getAmount());
		// 机构、人员、租赁公司关联
		dao.rechargeOrganAccount(map);
		
		orgOrganExpenses.setType("0");
		String uuid = GUIDGenerator.newGUID();
		orgOrganExpenses.setId(uuid);
		createOrganExpenses(orgOrganExpenses);
		
		// 充值发券 机构客户返券
		rechargeBackCoupon(orgOrganExpenses);
		
		return ret;
    }
	
	public void rechargeBackCoupon(OrgOrganExpenses orgOrganExpenses) {
		// 获取机构城市信息
		String organcity = dao.getCityByOrganid(orgOrganExpenses.getOrganId());
		// 充值发券接口调用 机构客户发券
		Map<String, Object> rechargecouponparam = new HashMap<String, Object>();
		rechargecouponparam.put("type", CouponRuleTypeEnum.RECHARGE.value);
		rechargecouponparam.put("userType", CouponRuleTypeEnum.ORGAN_CONSUMER.value);
		rechargecouponparam.put("companyId", orgOrganExpenses.getLeasesCompanyId());
		rechargecouponparam.put("cityCode", organcity);
		rechargecouponparam.put("userId", orgOrganExpenses.getOrganId());
		rechargecouponparam.put("money", orgOrganExpenses.getAmount());
		rechargecouponparam.put("version", "V4.0.0");
		try {
			String paramstr = GsonUtil.toJson(rechargecouponparam);
			String result = templateHelper.dealRequestWithFullUrlToken(
					SystemConfig.getSystemProperty("couponapi") + "/coupon/sysn/generate", HttpMethod.POST, null,
					paramstr, String.class);
			if (JSON.parseObject(result).get("status") != null
					&& "0".equals(JSON.parseObject(result).get("status").toString())
					&& JSON.parseObject(result).get("data") != null) {
				List<Map> data = JSON.parseArray(JSON.parseObject(result).get("data").toString(), Map.class);
				for (Map d : data) {
					this.addOrganCouponValue(String.valueOf(d.get("userId")), String.valueOf(d.get("companyid")),
							((BigDecimal) d.get("money")).doubleValue(), String.valueOf(d.get("name")),
							CouponUsetypeEnum.RECHARGE.code.toString());
				}
			}
		} catch (Exception e) {
			logger.error("优惠券触发出错", e);
			logger.error("机构客户发券userid:" + orgOrganExpenses.getOrganId());
		}
	}
	
	public Map<String, String> reduceOrganAccount(OrgOrganExpenses orgOrganExpenses) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");

		Map<String, Object> map = new HashMap<String, Object>();
		// 机构、人员、租赁公司关联 id
		map.put("id", orgOrganExpenses.getId());
		// 提现金额
		map.put("value", orgOrganExpenses.getAmount());
		// 机构、人员、租赁公司关联
		dao.reduceOrganAccount(map);
		
		orgOrganExpenses.setType("1");
		String uuid = GUIDGenerator.newGUID();
		orgOrganExpenses.setId(uuid);
		// 提现成功
		orgOrganExpenses.setOperateResult("0");
		orgOrganExpenses.setRemark("申请提现成功，扣减余额");
		createOrganExpenses(orgOrganExpenses);
		
		return ret;
	}

	public void createOrganExpenses(OrgOrganExpenses orgOrganExpenses) {
		dao.createOrganExpenses(orgOrganExpenses);
	}
	
	public List<PubCityAddr> getExistCityList(String leasesCompanyId, String specialState, String account) {
		Map<String, String> map = new HashMap<String, String>();
    	map.put("leasesCompanyId", leasesCompanyId);
    	map.put("specialState", specialState);
    	map.put("account", account);
    	return dao.getExistCityList(map);
    }
	
	public List<Map<String, Object>> getExistOrganList(String leasesCompanyId, String shortName, String specialState, String account) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("leasesCompanyId", leasesCompanyId);
		map.put("shortName", shortName);
		map.put("specialState", specialState);
    	map.put("account", account);
		return dao.getExistOrganList(map);
	}
	
	public List<OrgOrganExpenses> getOrganExpensesListExport(OrganAccountQueryParam organAccountQueryParam) {
		return dao.getOrganExpensesListExport(organAccountQueryParam);
	}
	
	public PageBean getPubCouponDetailByQuery(OrganAccountQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PubCouponDetail> list = getPubCouponDetailListByQuery(queryParam);
		int iTotalRecords = getPubCouponDetailListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<PubCouponDetail> getPubCouponDetailListByQuery(OrganAccountQueryParam organAccountQueryParam) {
    	return dao.getPubCouponDetailListByQuery(organAccountQueryParam);
    }
	
	public int getPubCouponDetailListCountByQuery(OrganAccountQueryParam organAccountQueryParam) {
		return dao.getPubCouponDetailListCountByQuery(organAccountQueryParam);
	}
	
	public List<PubCouponDetail> getPubCouponDetailExport(OrganAccountQueryParam organAccountQueryParam) {
		return dao.getPubCouponDetailExport(organAccountQueryParam);
	}
	
	@Transactional
	public void addOrganCouponValue(String organid, String companyid, double money, String remark, String usetype) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 机构id
		map.put("organid", organid);
		// 租赁公司id
		map.put("companyid", companyid);
		// 优惠券金额
		map.put("money", money);
		// 加入优惠券金额和可用额度
		dao.addOrganCouponValue(map);
		
		map.put("id", GUIDGenerator.newGUID());
		map.put("usetype", usetype);
		map.put("remark", remark);
		// 插入数据到抵用券明细表
		dao.addCouponDetail(map);
	}
	
	@Transactional
	public void clearOrganCouponValue(String organid, String companyid) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 机构id
		map.put("organid", organid);
		// 租赁公司id
		map.put("companyid", companyid);
		double couponamount = dao.getOrganCouponValue(map);
		if (couponamount > 0) {
			map.put("id", GUIDGenerator.newGUID());
			map.put("usetype", 5);
			map.put("remark", "提现");
			// 插入数据到抵用券明细表
			dao.clearCouponDetail(map);
			
			// 清空抵用券余额
			dao.clearOrganCouponValue(map);
		}
	}
}

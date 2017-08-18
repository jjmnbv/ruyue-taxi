package com.szyciov.lease.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.szyciov.dto.coupon.PubCouponActivityDto;
import com.szyciov.dto.coupon.PubCouponReceiveDto;
import com.szyciov.entity.coupon.PubCouponActivity;
import com.szyciov.entity.coupon.PubCouponActivityUseCity;
import com.szyciov.entity.coupon.PubCouponRule;
import com.szyciov.entity.coupon.PubCouponSendUser;
import com.szyciov.enums.coupon.CouponActivityEnum;
import com.szyciov.lease.dao.CouponDistributeDao;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.op.entity.PeUser;
import com.szyciov.param.PubCouponActivityQueryParam;
import com.szyciov.param.PubReceivedCouponQueryParam;
import com.szyciov.util.DateUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.GsonUtil;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("couponDistributeService")
public class CouponDistritubeService {

	@Resource(name = "couponDistributeDao")
	private CouponDistributeDao dao;

	public CouponDistributeDao getDao() {
		return dao;
	}

	public void setDao(CouponDistributeDao dao) {
		this.dao = dao;
	}

	// 新增抵扣券
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> addCouponActivity(PubCouponActivityDto activityDto) {
		Map<String, Object> result = new HashMap<>();

		if(activityDto.getSendmoneytype()==CouponActivityEnum.MONEY_TYPE_RANDOM.code && activityDto.getSendlowmoney()>=activityDto.getSendhighmoney()){
			result.put("ResultSign", "fault");
			result.put("MessageKey", "随机派发金额参数错误，随机上限须大于下限");
			return result;
		}
		
		if(activityDto.getSendstarttime().compareTo(DateUtil.format(new Date(), "yyyy-MM-dd"))<0){
			result.put("ResultSign", "fault");
			result.put("MessageKey", "发放时间的开始时间须大于当前时间");
			return result;
		}
		// 检测优惠券活动名称(重复)
		if (dao.checkCouponActivity(activityDto.getName(), activityDto.getLecompanyid())) {
			result.put("ResultSign", "fault");
			result.put("MessageKey", "抵用券名称已存在");
			return result;
		}

		String id = GUIDGenerator.newGUID();
		PubCouponRule couponRule = null;
		activityDto.setId(id);
		PubCouponActivity activity = JSON.parseObject(JSON.toJSONString(activityDto), PubCouponActivity.class);
		activity.setActivystate(CouponActivityEnum.STATE_START_NOT.code); // 待发放
		if (activity.getSendruletype() == null && activity.getSendruleidref() != null) {
			couponRule = dao.getPubCouponRuleById(activity.getSendruleidref());
			activity.setSendruletype(couponRule.getRuletype());
			activity.setCouponrule(GsonUtil.toJson(couponRule));
		}
		dao.addCouponActivity(activity);
		// 插入优惠卷活动发放区域
		if (activityDto.getCitys() != null) {
			String[] citys = activityDto.getCitys().split(",");
			List<PubCouponActivityUseCity> cityList = null;
			PubCouponActivityUseCity activyCity = null;
			if (citys != null && citys.length > 0) {
				cityList = new ArrayList<>(citys.length);
				for (String city : citys) {
					activyCity = new PubCouponActivityUseCity();
					activyCity.setId(GUIDGenerator.newGUID());
					activyCity.setCouponactivityidref(id);
					activyCity.setCity(city);
					activyCity.setCreater(activityDto.getCreater());
					cityList.add(activyCity);
				}
			}
			dao.addCouponActivityCitys(cityList);
		} else {
			result.put("ResultSign", "fault");
			result.put("MessageKey", "请选择发放城市");
			return result;
		}

		result.put("ResultSign", "Successful");
		result.put("MessageKey", "规则新增成功");

		return result;
	}

	// 删除抵扣券活动
	public Map delCouponActivity(String id) {
		Map<String, Object> result = new HashMap<>();
		try {
			dao.delCouponActivity(id);
			// 删除抵扣券的开放城市
			dao.delCouponActivityCity(id);
		} catch (Exception e) {
			result.put("ResultSign", "fault");
			result.put("MessageKey", "删除失败");
		}
		result.put("ResultSign", "Successful");
		result.put("MessageKey", "删除成功");
		return result;
	}

	// 作废抵扣券活动
	public Map invalidCouponActivity(PubCouponActivity activy) {
		Map<String, Object> result = new HashMap<>();
		activy.setActivystate(CouponActivityEnum.STATE_CANCEL.code); // 已作废
		try {
			dao.invalidCouponActivity(activy);
		} catch (Exception e) {
			result.put("ResultSign", "fault");
			result.put("MessageKey", "操作失败");
		}
		result.put("ResultSign", "Successful");
		result.put("MessageKey", "操作成功");
		return result;
	}

	// 根据参数查询抵扣券活动
	public PageBean getCouponActivityByParam(PubCouponActivityQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PubCouponActivityDto> list = getCouponActivityByQuery(queryParam);
		int iTotalRecords = getCouponActivityListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}

	private int getCouponActivityListCountByQuery(PubCouponActivityQueryParam queryParam) {
		return dao.getCouponActivityListCountByQuery(queryParam);
	}

	private List<PubCouponActivityDto> getCouponActivityByQuery(PubCouponActivityQueryParam queryParam) {
		return dao.getCouponActivityByQuery(queryParam);
	}

	// 获取租赁端所有抵扣券活动名称
	public List<Object> getCouponActivityNames(String lecompanyid, String name) {
		return dao.getCouponActivityNames(lecompanyid, name);
	}

	// 获取租赁端已存在的优惠券规则名称
	public List<Object> getAlreadyCouponRuleNames(String lecompanyid, String rulename) {
		return dao.getAlreadyCouponRuleNames(lecompanyid, rulename);
	}

	// 获取租赁端左右业务城市
	public JSONObject getBusinessCitys(String lecompanyid, String sendservicetype) {
		JSONObject ret = new JSONObject();
        String accountRule="";
        String sendRule="";
		if(sendservicetype==null || sendservicetype.equals(""))
			return ret;
		 else if(sendservicetype.equals("2")){//网约车,计费规则和派单规则表名需变化
			sendservicetype="0";
			accountRule="le_accountrules";
			sendRule="pub_sendrules";
		}else if(sendservicetype.equals("1")){//出租车
			//accountRule="op_taxiaccountrules";
			//sendRule="op_taxisendrules";
			return ret;
		}else{
			return ret;
		}
		lecompanyid="'"+lecompanyid+"'";
		List<PubCityAddr> cityAddrList = dao.getBusinessCitys(lecompanyid,sendservicetype,accountRule,sendRule);
		if (null != cityAddrList && !cityAddrList.isEmpty()) {
			Iterator<PubCityAddr> iterator = cityAddrList.iterator();
			while (iterator.hasNext()) {
				PubCityAddr cityAddr = iterator.next();
				String id = cityAddr.getId();
				String city = cityAddr.getCity();
				String initials = cityAddr.getCityInitials();
				if (StringUtils.isBlank(id) || StringUtils.isBlank(city) || StringUtils.isBlank(initials)) {
					continue;
				}
				// 根据字母对城市进行分类
				JSONObject json = new JSONObject();
				json.put("id", id);
				json.put("text", city);
				if (ret.containsKey(initials)) {
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

	// 更新抵扣券活动
	public Map<String, Object> updateCouponActivity(PubCouponActivityDto activityDto) {
		Map<String, Object> result = new HashMap<>();
		PubCouponActivity activity = JSON.parseObject(JSON.toJSONString(activityDto), PubCouponActivity.class);
		dao.updateCouponActivity(activity);
		// 删除优惠卷的发放区域
		dao.delCouponActivityCity(activityDto.getId());
		// 重新插入优惠券的发放区域
		if (activityDto.getCitys() != null) {
			String[] citys = activityDto.getCitys().split(",");
			List<PubCouponActivityUseCity> cityList = null;
			PubCouponActivityUseCity activyCity = null;
			if (citys != null && citys.length > 0) {
				cityList = new ArrayList<>(citys.length);
				for (String city : citys) {
					activyCity = new PubCouponActivityUseCity();
					activyCity.setId(GUIDGenerator.newGUID());
					activyCity.setCouponactivityidref(activityDto.getId());
					activyCity.setCity(city);
					activyCity.setCreater(activityDto.getCreater());
					cityList.add(activyCity);
				}
			}
			dao.addCouponActivityCitys(cityList);
		}
		result.put("ResultSign", "Successful");
		result.put("MessageKey", "修改成功");
		return result;
	}

	// 获取抵扣券活动详情
	public Map<String, Object> editCoupon(String id) {
		Map<String, Object> result = new HashMap<>();
		PubCouponActivityDto activity = dao.editCoupon(id);
		// 获取抵扣券活动发放城市
		List<PubCityAddr> citys = dao.getCouponActivityCitys(id);
		result.put("ResultSign", "Successful");
		result.put("MessageKey", "保存成功");
		result.put("activity", activity);
		result.put("citys", citys);
		return result;
	}

	// 根据发放对象获取所有优惠券规则
	public Map<String, Object> getCouponRuleNames(String lecompanyid, String sendruletarget) {
		Map<String, Object> result = new HashMap<>();
		List<Object> data = dao.getCouponRuleNames(lecompanyid, sendruletarget);
		result.put("ResultSign", "Successful");
		result.put("MessageKey", "获取成功");
		result.put("data", data);
		return result;
	}

	private int GetPubReceivedCouponListCount(PubReceivedCouponQueryParam queryParam) {
		return dao.GetPubReceivedCouponListCount(queryParam);
	}

	private List<PubCouponReceiveDto> GetPubReceivedCouponList(PubReceivedCouponQueryParam queryParam) {
		return dao.GetPubReceivedCouponList(queryParam);
	}

	// 机构客户发放记录信息
	public Map<String, Object> organRecordCoupon(String id) {
		PubCouponActivityDto actDto = dao.editCoupon(id);
		Map<String, Object> result = new HashMap<>();
		result.put("id", id);
		result.put("name", actDto.getRulename());
		return result;
	}

	// 根据参数查询机构客户领取抵扣券信息
	public PageBean GetOrganReceivedCoupon(PubReceivedCouponQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PubCouponReceiveDto> list = GetOrganReceivedCouponList(queryParam);
		int iTotalRecords = GetOrganReceivedCouponListCount(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}

	private int GetOrganReceivedCouponListCount(PubReceivedCouponQueryParam queryParam) {
		return dao.GetOrganReceivedCouponListCount(queryParam);
	}

	private List<PubCouponReceiveDto> GetOrganReceivedCouponList(PubReceivedCouponQueryParam queryParam) {
		return dao.GetOrganReceivedCouponList(queryParam);
	}

	// 机构客户发放记录信息
	public Map<String, Object> organUserRecordCoupon(String id) {
		List<Map<String, Object>> list = dao.organUserRecord(id);
		PubCouponActivityDto activity = dao.editCoupon(id);
		int totalCount = 0; // 领取的抵扣券数量
		double totalMoney = 0; // 领取的抵扣券总金额
		double totalUsedMoney = 0; // 使用的抵扣券金额
		int totalUnused = 0; // 未使用的抵扣券数量
		int totalUsed = 0; // 使用的抵扣券数量
		int totalAbondaned = 0; // 过期的抵扣券数量
		double usedPercent = 0; // 使用率
		double abandonedPercent = 0; // 废弃率

		for (Map<String, Object> m : list) {
			if (m.get("couponstatus") != null && m.get("totalcount") != null) {
				if (m.get("couponstatus").toString().equals("0"))
					totalUnused = Integer.parseInt(m.get("totalcount").toString());
				if (m.get("couponstatus").toString().equals("1")) {
					totalUsed = Integer.parseInt(m.get("totalcount").toString());
					totalUsedMoney = Double.parseDouble(m.get("totalmoney").toString());
				}
				if (m.get("couponstatus").toString().equals("2"))
					totalAbondaned = Integer.parseInt(m.get("totalcount").toString());

				totalMoney += Double.parseDouble(m.get("totalmoney").toString());
			}
		}
		totalCount = totalUnused + totalUsed + totalAbondaned;
		if (totalUsed != 0)
			usedPercent = new BigDecimal(totalUsed).divide(new BigDecimal(totalCount), 3, RoundingMode.HALF_UP)
					.multiply(new BigDecimal(100)).doubleValue();
		if (totalAbondaned != 0)
			abandonedPercent = 100 - usedPercent;
		if (totalUsed == 0 && totalAbondaned == 0) {
			usedPercent = 0;
			abandonedPercent = 0;
		}
		Map<String, Object> result = new HashMap<>();
		result.put("total", totalCount);
		result.put("totalmoney", totalMoney);
		result.put("usedtotalmoney", totalUsedMoney);
		result.put("usedpercent", usedPercent);
		result.put("abandonedpercent", abandonedPercent);
		result.put("name", activity.getName());
		result.put("id", id);

		return result;
	}

	// 根据参数查询机构用户优惠券领取情况
	public PageBean GetOrganUserReceivedCoupon(PubReceivedCouponQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PubCouponReceiveDto> list = GetOrganUserReceivedCouponList(queryParam);
		int iTotalRecords = GetOrganUserReceivedCouponListCount(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}

	private int GetOrganUserReceivedCouponListCount(PubReceivedCouponQueryParam queryParam) {
		return dao.GetOrganUserReceivedCouponListCount(queryParam);
	}

	private List<PubCouponReceiveDto> GetOrganUserReceivedCouponList(PubReceivedCouponQueryParam queryParam) {
		return dao.GetOrganUserReceivedCouponList(queryParam);
	}

}

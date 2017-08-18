package com.szyciov.operate.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.szyciov.dto.coupon.CouponUsageDto;
import com.szyciov.operate.dao.EffectAnalysisDao;
import com.szyciov.param.CouponUsageQueryParam;
import com.szyciov.param.UserRechargeQueryParam;
import com.szyciov.util.DateUtil;
import com.szyciov.util.PageBean;
import com.szyciov.util.StringUtil;

@Service("effectAnalysisService")
public class EffectAnalysisService {

	@Resource(name="effectAnalysisDao")
	private EffectAnalysisDao dao;

	public EffectAnalysisDao getDao() {
		return dao;
	}

	public void setDao(EffectAnalysisDao dao) {
		this.dao = dao;
	}
	
	public PageBean queryCouponUsageByParam(CouponUsageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<CouponUsageDto> list = this.queryCouponUsageList(queryParam);
		int iTotalRecords = this.queryCouponUsageListCount(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}

	private int queryCouponUsageListCount(CouponUsageQueryParam queryParam) {
		if(!StringUtils.isBlank(queryParam.getCdatetype()) && !StringUtils.isBlank(queryParam.getUsedstarttime()) && !StringUtils.isBlank(queryParam.getUsedendtime())){
			/*if("0".equals(queryParam.getCdatetype())){//按日
				String endtime=DateUtil.format(new Date(), "yyyy-MM-dd");
				queryParam.setUsedstarttime(endtime.substring(0, endtime.length()-2)+"01");//本月一号
				 queryParam.setUsedendtime(endtime);
			}else */
			if("1".equals(queryParam.getCdatetype())){
				//String endtime=DateUtil.format(new Date(), "yyyy-MM-dd");
				 queryParam.setUsedstarttime(queryParam.getUsedstarttime()+"-01");// 
				 queryParam.setUsedendtime(queryParam.getUsedendtime()+"-31");   // 
			}
		}else{
			String endtime=DateUtil.format(new Date(), "yyyy-MM-dd");
			queryParam.setUsedstarttime(endtime.substring(0, endtime.length()-2)+"01");//本月一号
			queryParam.setUsedendtime(endtime);
		}
		return dao.queryCouponUsageListCount(queryParam);
	}

	private List<CouponUsageDto> queryCouponUsageList(CouponUsageQueryParam queryParam) {
		//分别按网约车和车租车订单，统计发放了抵扣券的城市未使用的情况，（无去重）
		List<Map> unusedCoupon=dao.queryUsedCouponList();
		Map<String,Integer> map=new HashMap<>();
		for(Map m:unusedCoupon){
			//因为是网约车和出租车，且无去重，对于相同的城市要叠加
			if(map.containsKey(m.get("city")))
				map.put(m.get("city").toString(), Integer.valueOf(m.get("total").toString())+map.get(m.get("city")));
			else
			    map.put(m.get("city").toString(), Integer.valueOf(m.get("total").toString()));
		}
		
		if(!StringUtils.isBlank(queryParam.getCdatetype()) && !StringUtils.isBlank(queryParam.getUsedstarttime()) && !StringUtils.isBlank(queryParam.getUsedendtime())){
				/*if("0".equals(queryParam.getCdatetype())){
				String endtime=DateUtil.format(new Date(), "yyyy-MM-dd");
				queryParam.setUsedstarttime(endtime.substring(0, endtime.length()-2)+"01");//本月一号
				 queryParam.setUsedendtime(endtime);
			}else*/ 
			if("1".equals(queryParam.getCdatetype())){
				//String endtime=DateUtil.format(new Date(), "yyyy-MM-dd");
				queryParam.setUsedstarttime(queryParam.getUsedstarttime()+"-01");//本月一号
				queryParam.setUsedendtime(queryParam.getUsedendtime()+"-31");   //本月最后一天
			}
		}else{
			String endtime=DateUtil.format(new Date(), "yyyy-MM-dd");
			queryParam.setUsedstarttime(endtime.substring(0, endtime.length()-2)+"01");//本月一号
			queryParam.setUsedendtime(endtime);
		}
		
		List<CouponUsageDto> result=dao.queryCouponUsageList(queryParam);
		//补齐以使用数量和已使用率
		for(CouponUsageDto dto:result){
			if(map.containsKey(dto.getId())){
				dto.setUsedcount(map.get(dto.getId()));
				if(dto.getTotalcount()==0 || dto.getUsedcount()==0)
					dto.setUsedpercent(0.0);
				else{
					//四舍五入保留一位 小数
					dto.setUsedpercent(new BigDecimal(dto.getUsedcount()).divide(new BigDecimal(dto.getTotalcount()), 3, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).doubleValue());
				}
			}else{
				dto.setUsedcount(0);
				dto.setUsedpercent(0.0);
			}
		}
		
		return result;
	}

	public List<Object> getCouponUsageSendCitys(String city) {
		return dao.getCouponUsageSendCitys(city);
	}

	public PageBean queryUserRechargePercent(UserRechargeQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		Map<String,Object> result=new HashMap<>();
		
		if(!StringUtils.isBlank(queryParam.getStarttime()) && !StringUtils.isBlank(queryParam.getEndtime()) && !StringUtils.isBlank(queryParam.getRdatetype())){
			/*if(queryParam.getRdatetype()==null || "0".equals(queryParam.getRdatetype())){
				String endtime=DateUtil.format(new Date(), "yyyy-MM-dd");
				queryParam.setStarttime(endtime.substring(0, endtime.length()-2)+"01");//本月一号
				queryParam.setEndtime(endtime);
			}else*/ 
			if("1".equals(queryParam.getRdatetype())){
				 //String endtime=DateUtil.format(new Date(), "yyyy-MM-dd");
				 queryParam.setStarttime(queryParam.getStarttime()+"-01");//本月一号
				 queryParam.setEndtime(queryParam.getEndtime()+"-31");   //本月最后一天
			}
		}else{
			String endtime=DateUtil.format(new Date(), "yyyy-MM-dd");
			queryParam.setStarttime(endtime.substring(0, endtime.length()-2)+"01");//本月一号
			queryParam.setEndtime(endtime);
		}
		
		Integer registerCount=dao.queryRegisterCount(queryParam);
		Integer rechargeCount=dao.queryChargeCount(queryParam);
		result.put("registerCount", registerCount);//注册数
		result.put("rechargeCount", rechargeCount);//充值数
		if(rechargeCount==0 || registerCount==0)
			result.put("rechargePercent", 0);//充值率
		else
			result.put("rechargePercent", new BigDecimal(rechargeCount).divide(new BigDecimal(registerCount), 3, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).doubleValue()+"%");
		
		List<Object> list=new ArrayList<>();
		if(!result.isEmpty())
		    list.add(result);
		 
		pageBean.setsEcho(queryParam.getsEcho());
		pageBean.setiTotalDisplayRecords(list.size());
		pageBean.setiTotalRecords(list.size());
		pageBean.setAaData(list);
		return pageBean;
	}
	
	
}

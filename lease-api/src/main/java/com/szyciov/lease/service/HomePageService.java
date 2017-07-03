package com.szyciov.lease.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.HomePageDao;

@Service("HomePageService")
public class HomePageService {
	private HomePageDao dao;

	@Resource(name = "HomePageDao")
	public void setDao(HomePageDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 获取首页信息
	 * @param companyid
	 * @return
	 */
	public Map<String,Object> getHomePageInfo(String companyid){
		Map<String,Object> info = new HashMap<String,Object>();
		//获取全部已支付订单数和已支付订单金额
		addPayedInfo(companyid, info);
		//获取全部未支付的订单数和未支付的订单金额
		addUnPayedInfo(companyid, info);
		//获取订单总金额
		addTotalInfo(companyid, info);
		//获取差异金额
		addCYInfo(companyid, info);
		//今日订单信息
		addTodayInfo(companyid, info);
		//昨日订单信息
		addTomorrowInfo(companyid, info);
		//获取这个月截止昨天的订单信息
		addMonthInfo(companyid, info);
		//获取接机、送机、约车
		addYCInfo(companyid, info);
		addJJInfo(companyid, info);
		addSJInfo(companyid, info);
		return info;
	}

	/**
	 * 添加今天的订单信息
	 * @param companyid
	 * @param info
	 */
	private void addTodayInfo(String companyid, Map<String, Object> info) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar todaytime = Calendar.getInstance();
		String endtodaytime = dateformat.format(todaytime.getTime());
		String starttodaytime = endtodaytime;
		
		Map<String,String> todayparam = new HashMap<String,String>();
		todayparam.put("companyid", companyid);
		todayparam.put("starttime", starttodaytime);
		todayparam.put("endtime", endtodaytime);
		Map<String,Object> todayinfo = dao.getInfoByTime(todayparam);
		if(todayinfo!=null){
			info.put("todaymoney", todayinfo.get("money"));
			info.put("todaycount", todayinfo.get("count"));
		}
	}

	/**
	 * 添加差异金额信息
	 * @param companyid
	 * @param info
	 */
	private void addCYInfo(String companyid, Map<String, Object> info) {
		Object cytotalmoney = dao.getCYTotalMoney(companyid);
		info.put("cytotalmoney", cytotalmoney);
	}

	/**
	 * 添加总订单信息
	 * @param companyid
	 * @param info
	 */
	private void addTotalInfo(String companyid, Map<String, Object> info) {
		Object money = dao.getTotalMoney(companyid);
		info.put("totalmoney", money);
	}

	/**
	 * 添加未支付的订单信息
	 * @param companyid
	 * @param info
	 */
	private void addUnPayedInfo(String companyid, Map<String, Object> info) {
		Map<String,Object> unpayedinfo = dao.getTotalUnPayedInfo(companyid);
		if(unpayedinfo!=null){
			info.put("unpayedcount", unpayedinfo.get("unpayedcount"));
			info.put("unpayedmoney", unpayedinfo.get("unpayedmoney"));
		}
	}

	private void addPayedInfo(String companyid, Map<String, Object> info) {
		Map<String,Object> payedinfo = dao.getTotalPayedInfo(companyid);
		if(payedinfo!=null){
			info.put("payedcount", payedinfo.get("payedcount"));
			info.put("payedmoney", payedinfo.get("payedmoney"));
		}
	}

	/**
	 * 添加昨天的订单信息
	 * @param companyid
	 * @param info
	 */
	private void addTomorrowInfo(String companyid, Map<String, Object> info) {
		SimpleDateFormat tomorrowformat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar tomorrowtime = Calendar.getInstance();
		tomorrowtime.add(Calendar.DATE, -1);
		String starttomorrowtime = tomorrowformat.format(tomorrowtime.getTime());
		String endtomorrowtime = starttomorrowtime;
		Map<String,String> tomorrowparam = new HashMap<String,String>();
		tomorrowparam.put("companyid", companyid);
		tomorrowparam.put("starttime", starttomorrowtime);
		tomorrowparam.put("endtime", endtomorrowtime);
		Map<String,Object> tomorrowinfo = dao.getInfoByTime(tomorrowparam);
		if(tomorrowinfo!=null){
			info.put("tomorrowmoney", tomorrowinfo.get("money"));
			info.put("tomorrowcount", tomorrowinfo.get("count"));
		}
	}

	/**
	 * 添加本月的订单信息
	 * @param companyid
	 * @param info
	 */
	private void addMonthInfo(String companyid, Map<String, Object> info) {
		SimpleDateFormat monthformat = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		Calendar themonthtime = Calendar.getInstance();
		String startthemonthtimestr = monthformat.format(themonthtime.getTime());
		SimpleDateFormat monthtomorrowformat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String endthemonthtimestr = monthtomorrowformat.format(themonthtime.getTime());
		
		Map<String,String> themonthparam = new HashMap<String,String>();
		themonthparam.put("companyid", companyid);
		themonthparam.put("starttime", startthemonthtimestr);
		themonthparam.put("endtime", endthemonthtimestr);
		Map<String,Object> themonthinfo = dao.getInfoByTime(themonthparam);
		if(themonthinfo!=null){
			info.put("themonthmoney", themonthinfo.get("money"));
			info.put("themonthcount", themonthinfo.get("count"));
		}
	}

	/**
	 * 添加送机订单信息
	 * @param companyid
	 * @param info
	 */
	private void addSJInfo(String companyid, Map<String, Object> info) {
		Map<String,String> sjparam = new HashMap<String,String>();
		sjparam.put("companyid", companyid);
		sjparam.put("usetype", "3");
		Map<String,Object> sjinfo = dao.getTotalInfoByUseType(sjparam);
		if(sjinfo!=null){
			info.put("sjmoney", sjinfo.get("money"));
			info.put("sjcount", sjinfo.get("count"));
		}
	}

	/**
	 * 添加接机订单信息
	 * @param companyid
	 * @param info
	 */
	private void addJJInfo(String companyid, Map<String, Object> info) {
		Map<String,String> jjparam = new HashMap<String,String>();
		jjparam.put("companyid", companyid);
		jjparam.put("usetype", "2");
		Map<String,Object> jjinfo = dao.getTotalInfoByUseType(jjparam);
		if(jjinfo!=null){
			info.put("jjmoney", jjinfo.get("money"));
			info.put("jjcount", jjinfo.get("count"));
		}
	}

	/**
	 * 添加约车订单信息
	 * @param companyid
	 * @param info
	 */
	private void addYCInfo(String companyid, Map<String, Object> info) {
		Map<String,String> ycparam = new HashMap<String,String>();
		ycparam.put("companyid", companyid);
		ycparam.put("usetype", "1");
		Map<String,Object> ycinfo = dao.getTotalInfoByUseType(ycparam);
		if(ycinfo!=null){
			info.put("ycmoney", ycinfo.get("money"));
			info.put("yccount", ycinfo.get("count"));
		}
	}
	public static void main(String[] args){
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar tomorrowtime = Calendar.getInstance();
		String endtomorrowtime = dateformat.format(tomorrowtime.getTime());
		tomorrowtime.add(Calendar.DATE, -1);
		String starttomorrowtime = dateformat.format(tomorrowtime.getTime());
		System.out.println(starttomorrowtime);
		System.out.println(endtomorrowtime);
		
		SimpleDateFormat monthformat = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		Calendar themonthtime = Calendar.getInstance();
		String startthemonthtimestr = monthformat.format(themonthtime.getTime());
		SimpleDateFormat tomorrowformat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String endthemonthtimestr = tomorrowformat.format(themonthtime.getTime());
		System.out.println(startthemonthtimestr);
		System.out.println(endthemonthtimestr);
		System.out.println(Integer.MAX_VALUE);
	}
}

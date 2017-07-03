package com.szyciov.operate.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.szyciov.operate.dao.HomePageDao;

@Service("HomePageService")
public class HomePageService {
	private HomePageDao dao;

	@Resource(name = "HomePageDao")
	public void setDao(HomePageDao dao) {
		this.dao = dao;
	}
	/**
	 * 运管端首页信息
	 * @param companyid
	 * @return
	 */
	public Map<String,Object> getHomeAllData(){
		Map<String,Object> info = new HashMap<String,Object>();
		//租赁公司订单统计
		getCompayData(info);
	    //租赁公司昨日订单统计
		getCompayDataY(info);
		//租赁公司今日订单统计
		getCompayDataT(info);
		//租赁公司本月订单统计
		getCompayDataM(info);
		//租赁公司订单类型统计
		getCompayDataType(info);
		
		//个人公司订单统计
		getPersonalData(info);
		//个人公司昨日订单统计
		getPersonalDataY(info);
		//个人公司今日订单统计
		getPersonalDataT(info);
		//个人公司本月订单统计
		getPersonalDataM(info);
		//个人订单类型统计
		getPersonalDataType(info);
		return info;
	}
	/**
	 * 租赁公司订单统计
	 */
    public void getCompayData(Map<String, Object> info){
    	Map<String,Object> homeDataParam = new HashMap<String,Object>();
    	homeDataParam.put("type","0");
    	Map<String,Object> compayData = dao.getCompayData(homeDataParam);
    	if(compayData!=null){
			info.put("ordersCom", compayData.get("orders"));
			info.put("ordersmoneyCom", compayData.get("ordersmoney"));
		}
    }
    /**
     * 租赁公司昨日订单统计
     */
    public void getCompayDataY(Map<String, Object> info){
    	Map<String,Object> homeDataParam = new HashMap<String,Object>();
    	SimpleDateFormat tomorrowformat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar tomorrowtime = Calendar.getInstance();
		tomorrowtime.add(Calendar.DATE, -1);
		String starttomorrowtime = tomorrowformat.format(tomorrowtime.getTime());
		String endtomorrowtime = starttomorrowtime;
    	homeDataParam.put("type","0");
    	homeDataParam.put("starttime", starttomorrowtime);
    	homeDataParam.put("endtime", endtomorrowtime);
    	Map<String,Object> compayData = dao.getDataByTime(homeDataParam);
    	if(compayData!=null){
			info.put("ordersComY", compayData.get("orders"));
			info.put("ordersmoneyComY", compayData.get("ordersmoney"));
		}
    	
    }
    /**
     * 租赁公司今日订单统计
     */
    public void getCompayDataT(Map<String, Object> info){
    	Map<String,Object> homeDataParam = new HashMap<String,Object>();
    	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    	Calendar todaytime = Calendar.getInstance();
		String endtodaytime = dateformat.format(todaytime.getTime());
		String starttodaytime = endtodaytime;
    	homeDataParam.put("type","0");
    	homeDataParam.put("starttime", starttodaytime);
    	homeDataParam.put("endtime", endtodaytime);
    	Map<String,Object> compayData = dao.getDataByTime(homeDataParam);
    	if(compayData!=null){
			info.put("ordersComT", compayData.get("orders"));
			info.put("ordersmoneyComT", compayData.get("ordersmoney"));
		}
    	
    }
    /**
     * 租赁公司本月订单统计
     */
    public void getCompayDataM(Map<String, Object> info){
    	Map<String,Object> homeDataParam = new HashMap<String,Object>();
    	SimpleDateFormat monthformat = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		Calendar themonthtime = Calendar.getInstance();
		String startthemonthtimestr = monthformat.format(themonthtime.getTime());
		SimpleDateFormat monthtomorrowformat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String endthemonthtimestr = monthtomorrowformat.format(themonthtime.getTime());
    	homeDataParam.put("type","0");
    	homeDataParam.put("starttime", startthemonthtimestr);
    	homeDataParam.put("endtime", endthemonthtimestr);
    	Map<String,Object> compayData = dao.getDataByTime(homeDataParam);
    	if(compayData!=null){
			info.put("ordersComM", compayData.get("orders"));
			info.put("ordersmoneyComM", compayData.get("ordersmoney"));
		}
    	
    }
    /**
     * 租赁公司类型统计
     */
    public void getCompayDataType(Map<String, Object> info){
    	Map<String,Object> homeDataParam = new HashMap<String,Object>();
    	homeDataParam.put("type","0");
    	homeDataParam.put("ordertype","1");
    	Map<String,Object> homeDataParam1 = new HashMap<String,Object>();
    	homeDataParam1.put("type","0");
    	homeDataParam1.put("ordertype","2");
    	Map<String,Object> homeDataParam2 = new HashMap<String,Object>();
    	homeDataParam2.put("type","0");
    	homeDataParam2.put("ordertype","3");
    	Map<String,Object> compayData = dao.getCompayData(homeDataParam);
    	Map<String,Object> compayData1 = dao.getCompayData(homeDataParam1);
    	Map<String,Object> compayData2 = dao.getCompayData(homeDataParam2);
    	if(compayData!=null){
    		info.put("carordersC", compayData.get("orders"));
			info.put("carordersMoneyC", compayData.get("ordersmoney"));
		}
    	if(compayData1!=null){
			info.put("pickupordersC", compayData1.get("orders"));
			info.put("pickupordersMoneyC", compayData1.get("ordersmoney"));
		}
    	if(compayData2!=null){
			info.put("dropoffordersC", compayData2.get("orders"));
			info.put("dropoffordersMoneyC", compayData2.get("ordersmoney"));
		}
    }
    /**
	 * 个人订单统计
	 */
    public void getPersonalData(Map<String, Object> info){
    	Map<String,Object> homeDataParam = new HashMap<String,Object>();
    	homeDataParam.put("type","1");
    	Map<String,Object> compayData = dao.getCompayData(homeDataParam);
    	if(compayData!=null){
			info.put("ordersComP", compayData.get("orders"));
			info.put("ordersmoneyComP", compayData.get("ordersmoney"));
			info.put("bepaidorders", compayData.get("unpayedcount"));
			info.put("paidorders", compayData.get("payedcount"));
			info.put("bepaidmoney", compayData.get("bepaidmoney"));
			info.put("payedmoney", compayData.get("payedmoney"));
			info.put("incomemoney", compayData.get("incomemoney"));
			info.put("diffmoney", compayData.get("diffmoney"));
		}
    }
    /**
     * 个人昨日订单统计
     */
    public void getPersonalDataY(Map<String, Object> info){
    	Map<String,Object> homeDataParam = new HashMap<String,Object>();
    	SimpleDateFormat tomorrowformat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar tomorrowtime = Calendar.getInstance();
		tomorrowtime.add(Calendar.DATE, -1);
		String starttomorrowtime = tomorrowformat.format(tomorrowtime.getTime());
		String endtomorrowtime = starttomorrowtime;
    	homeDataParam.put("type","1");
    	homeDataParam.put("starttime", starttomorrowtime);
    	homeDataParam.put("endtime", endtomorrowtime);
    	Map<String,Object> compayData = dao.getDataByTime(homeDataParam);
    	if(compayData!=null){
			info.put("ordersComYP", compayData.get("orders"));
			info.put("ordersmoneyComYP", compayData.get("ordersmoney"));
		}
    }
    /**
     * 个人今日订单统计
     */
    public void getPersonalDataT(Map<String, Object> info){
    	Map<String,Object> homeDataParam = new HashMap<String,Object>();
    	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    	Calendar todaytime = Calendar.getInstance();
		String endtodaytime = dateformat.format(todaytime.getTime());
		String starttodaytime = endtodaytime;
    	homeDataParam.put("type","1");
    	homeDataParam.put("starttime", starttodaytime);
    	homeDataParam.put("endtime", endtodaytime);
    	Map<String,Object> compayData = dao.getDataByTime(homeDataParam);
    	if(compayData!=null){
			info.put("ordersComTP", compayData.get("orders"));
			info.put("ordersmoneyComTP", compayData.get("ordersmoney"));
		}
    }
    /**
     * 个人本月订单统计
     */
    public void getPersonalDataM(Map<String, Object> info){
    	Map<String,Object> homeDataParam = new HashMap<String,Object>();
    	SimpleDateFormat monthformat = new SimpleDateFormat("yyyy-MM-01 00:00:00");
		Calendar themonthtime = Calendar.getInstance();
		String startthemonthtimestr = monthformat.format(themonthtime.getTime());
		SimpleDateFormat monthtomorrowformat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String endthemonthtimestr = monthtomorrowformat.format(themonthtime.getTime());
    	homeDataParam.put("type","1");
    	homeDataParam.put("starttime", startthemonthtimestr);
    	homeDataParam.put("endtime", endthemonthtimestr);
    	Map<String,Object> compayData = dao.getDataByTime(homeDataParam);
    	if(compayData!=null){
			info.put("ordersComMP", compayData.get("orders"));
			info.put("ordersmoneyComMP", compayData.get("ordersmoney"));
		};
    }
    /**
     * 个人订单类型统计
     */
    public void getPersonalDataType(Map<String, Object> info){
    	Map<String,Object> homeDataParam = new HashMap<String,Object>();
    	homeDataParam.put("type","1");
    	homeDataParam.put("ordertype","1");
    	Map<String,Object> homeDataParam1 = new HashMap<String,Object>();
    	homeDataParam1.put("type","1");
    	homeDataParam1.put("ordertype","2");
    	Map<String,Object> homeDataParam2 = new HashMap<String,Object>();
    	homeDataParam2.put("type","1");
    	homeDataParam2.put("ordertype","3");
    	Map<String,Object> homeDataParam3 = new HashMap<String,Object>();
    	homeDataParam3.put("type","1");
    	homeDataParam3.put("ordertype","4");
    	Map<String,Object> compayData = dao.getCompayData(homeDataParam);
    	Map<String,Object> compayData1 = dao.getCompayData(homeDataParam1);
    	Map<String,Object> compayData2 = dao.getCompayData(homeDataParam2);
    	Map<String,Object> compayData3 = dao.getCompayData(homeDataParam3);
    	if(compayData!=null){
    		info.put("carorders", compayData.get("orders"));
			info.put("carordersMoney", compayData.get("ordersmoney"));
		}
    	if(compayData1!=null){
			info.put("pickuporders", compayData1.get("orders"));
			info.put("pickupordersMoney", compayData1.get("ordersmoney"));
		}
    	if(compayData2!=null){
			info.put("dropofforders", compayData2.get("orders"));
			info.put("dropoffordersMoney", compayData2.get("ordersmoney"));
		}
    	if(compayData3!=null){
			info.put("taxiorders", compayData3.get("orders"));
			info.put("taxiordersMoney", compayData3.get("ordersmoney"));
		}
    }
}

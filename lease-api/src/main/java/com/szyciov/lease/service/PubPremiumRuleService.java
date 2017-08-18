package com.szyciov.lease.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.dto.pubPremiumRule.PubPremiumAdd;
import com.szyciov.dto.pubPremiumRule.PubPremiumDetail;
import com.szyciov.dto.pubPremiumRule.PubPremiumHistory;
import com.szyciov.dto.pubPremiumRule.PubPremiumModify;
import com.szyciov.dto.pubPremiumRule.PubPremiumParam;
import com.szyciov.entity.PubPremiumRule;
import com.szyciov.entity.PubPremiumRuleDatedetail;
import com.szyciov.entity.PubPremiumRuleWeekdetail;
import com.szyciov.lease.dao.PubPremiumRuleDao;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.PageBean;

import net.sf.json.JSONObject;

@Service("pubPremiumRuleService")
public class PubPremiumRuleService {
	private PubPremiumRuleDao dao;
	@Resource(name = "PubPremiumRuleDao")
	public void setDao(PubPremiumRuleDao dao) {
		this.dao = dao;
	}
	public PageBean getPubPremiumRuleByQuery(PubPremiumParam pubPremiumParam){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(pubPremiumParam.getsEcho());  
		List<PubPremiumParam> list = getPubPremiumRuleByQueryList(pubPremiumParam);
		int iTotalRecords = getPubPremiumRuleByQueryCount(pubPremiumParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<PubPremiumParam> getPubPremiumRuleByQueryList(PubPremiumParam pubPremiumParam){
		return dao.getPubPremiumRuleByQueryList(pubPremiumParam);
	}
	public int getPubPremiumRuleByQueryCount(PubPremiumParam pubPremiumParam){
		return dao.getPubPremiumRuleByQueryCount(pubPremiumParam);
	}
	public List<PubDictionary> getCity(){
		return dao.getCity();
	}
	public List<PubDictionary> getWeeks(String id){
		return dao.getWeeks(id);
	}
	public PubPremiumParam getRulename(String id)  {
		return dao.getRulename(id);
	}
	public Map<String,Object> ruleConflict(PubPremiumParam pubPremiumParam)  {
		//通过id查询出这条规则,返回 0表示可以 启用规则，返回1表示不能启用规则
		PubPremiumParam pubPremiumParamId = dao.getById(pubPremiumParam);
		Map<String, Object> ret = new HashMap<String, Object>();
			if(pubPremiumParamId.getRuletype().equals("0")){
				dao.updateStatus(pubPremiumParam);
				//存入缓存
				insetRedis(pubPremiumParamId,0);
				//存入历史记录
				   PubPremiumHistory pubPremiumHistory = new PubPremiumHistory();
				   pubPremiumHistory.setId(GUIDGenerator.newGUID());
				   pubPremiumHistory.setPremiumruleid(pubPremiumParamId.getId());
				   pubPremiumHistory.setOperationtype("1");
				   pubPremiumHistory.setRuletype(pubPremiumParamId.getRuletype());
				   pubPremiumHistory.setCreater(pubPremiumParam.getCreater());
				   pubPremiumHistory.setUpdater(pubPremiumParam.getCreater());
				   dao.insertIntoHistory(pubPremiumHistory);
				   return ret;
			}else{
				List<PubPremiumParam> theResult  = dao.ruleConflict(pubPremiumParamId);
				if(theResult == null || theResult.size() == 0){
					dao.updateStatus(pubPremiumParam);
					//存入缓存
					insetRedis(pubPremiumParamId,0);
					//存入历史记录
					   PubPremiumHistory pubPremiumHistory = new PubPremiumHistory();
					   pubPremiumHistory.setId(GUIDGenerator.newGUID());
					   pubPremiumHistory.setPremiumruleid(pubPremiumParamId.getId());
					   pubPremiumHistory.setOperationtype("1");
					   pubPremiumHistory.setRuletype(pubPremiumParamId.getRuletype());
					   pubPremiumHistory.setCreater(pubPremiumParam.getCreater());
					   pubPremiumHistory.setUpdater(pubPremiumParam.getCreater());
					   dao.insertIntoHistory(pubPremiumHistory);
					   return ret;
				}else{
					ret.put("rulename", theResult);
					   return ret;
				}
			}
	}
	public void ruleConflictOk(PubPremiumParam pubPremiumParam)  {
		 PubPremiumParam returnResult = dao.getById(pubPremiumParam);
		// 修改状态
		   dao.ruleConflictOk(pubPremiumParam);
		   insetRedis(returnResult,1);
		   PubPremiumHistory pubPremiumHistory = new PubPremiumHistory();
		   pubPremiumHistory.setId(GUIDGenerator.newGUID());
		   pubPremiumHistory.setPremiumruleid(returnResult.getId());
		   pubPremiumHistory.setOperationtype("0");
		   pubPremiumHistory.setRuletype(returnResult.getRuletype());
		   pubPremiumHistory.setCreater(pubPremiumParam.getCreater());
		   pubPremiumHistory.setUpdater(pubPremiumParam.getCreater());
		   dao.insertIntoHistory(pubPremiumHistory);
		//存入一条历史数据
	}
	public Map<String, String> addPuPremiumRule(PubPremiumAdd pubPremiumAdd){
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		ret.put("MessagError", "请输入完整信息");
		ret.put("ifHaveNull", "ok");
		ret.put("ifHavePremiumRule", "ok");
		ret.put("ifHaveOverlying", "ok");
		ret.put("ifDateOk", "ok");
		ret.put("ifHaveOverlyingWeek", "ok");
		//开始时间大于结束时间
		ret.put("ifTimeOk", "ok");
		//获取传过来的值
		String tableInfo = pubPremiumAdd.getTableInfo();
		String checkVal = pubPremiumAdd.getCheckVal();
		String weekOne = tableInfo.substring(tableInfo.indexOf("one")+4,tableInfo.indexOf("tow")-1);
		String weekTow= tableInfo.substring(tableInfo.indexOf("tow")+4,tableInfo.indexOf("three")-1);
		String weekThree = tableInfo.substring(tableInfo.indexOf("three")+6,tableInfo.indexOf("four")-1);
		String weekFour = tableInfo.substring(tableInfo.indexOf("four")+5,tableInfo.indexOf("five")-1);
		String weekFive = tableInfo.substring(tableInfo.indexOf("five")+5,tableInfo.indexOf("six")-1);
		String weekSix = tableInfo.substring(tableInfo.indexOf("six")+4,tableInfo.indexOf("seven")-1);
		String weekSeven = tableInfo.substring(tableInfo.indexOf("seven")+6,tableInfo.indexOf("ok")-1);
		String dates = tableInfo.substring(tableInfo.indexOf("date")+5,tableInfo.indexOf("hello")-1);
		String [] weekOnes = weekOne.split(",");
		String [] weekTows = weekTow.split(",");
		String [] weekThrees = weekThree.split(",");
		String [] weekFours = weekFour.split(",");
		String [] weekFives = weekFive.split(",");
		String [] weekSixss = weekSix.split(",");
		String [] weekSevens = weekSeven.split(",");
		String [] datess  = dates.split(",");
		String [] checkVals = repeatArray(checkVal.split(","));
		//转换为集合
		List<String> checkValslist= new ArrayList<String>();
		for(int i=0;i<checkVals.length;i++){
			checkValslist.add(checkVals[i]);
		}
		Map<String,String> weekData = new HashMap<String,String>(); 
		//循环获取table里面的值
		//判断为什么保存 星期，或者日期
		String id = GUIDGenerator.newGUID();
		if(pubPremiumAdd.getRuletype().equals("week")){
			pubPremiumAdd.setRuletype("0");
		//当不是修改的时候就走这条判断
		if(pubPremiumAdd.getId() == null || pubPremiumAdd.getId() == ""){
			if(ifHavePremiumRule(pubPremiumAdd)){
				ret.put("ifHavePremiumRule", "noOk");
				return ret;
			};
		}
		//判断星期
			/*ret.put("weekIsNull", "请勾选星期选项");
			return ret;*/
			for(int i=0;i<checkValslist.size();i++){
				if(checkValslist.get(i).equals("undefined")){
					checkValslist.remove(i);
				}
			}
				//判断是否所选项都有值
				int count = 0;
				if(checkValslist.contains("one")){
					if(isTheNUll(weekOne,weekOnes)){
						count = count+1;
					};
				}
				if(checkValslist.contains("tow")){
					if(isTheNUll(weekTow,weekTows)){
						count = count+1;
					};
				}
				if(checkValslist.contains("three")){
					if(isTheNUll(weekThree,weekThrees)){
						count = count+1;
					};
				}
				if(checkValslist.contains("four")){
					if(isTheNUll(weekFour,weekFours)){
						count = count+1;
					};
				}
				if(checkValslist.contains("five")){
					if(isTheNUll(weekFive,weekFives)){
						count = count+1;
					};
				}
				if(checkValslist.contains("six")){
					if(isTheNUll(weekSix,weekSixss)){
						count = count+1;
					};
				}
				if(checkValslist.contains("seven")){
					if(isTheNUll(weekSeven,weekSevens)){
						count = count+1;
					};
				}
				//里面的值都不为空,判断时间有没有重叠
				if(checkValslist.size() == count){
					// 返回true是存在叠加的情况
					//开始时间比结束时间大返回0有重叠时间返回1，没有返回2，没有被勾选返回3
					int a = timeOverlying(weekOnes,checkValslist,"one"); 
					int b = timeOverlying(weekTows,checkValslist,"tow"); 
					int c = timeOverlying(weekThrees,checkValslist,"three");
					int d = timeOverlying(weekFours,checkValslist,"four");
					int e = timeOverlying(weekFives,checkValslist,"five");
					int f = timeOverlying(weekSixss,checkValslist,"six");
					int g = timeOverlying(weekSevens,checkValslist,"seven");
					if((a==0 || a==1) || (b==0 || b==1) || (c==0 || c==1) || (d==0 || d==1)||(e==0 || e==1)||(f==0 || f==1)||(g==0 || g==1)){
						if(a == 0){
							ret.put("ifTimeOk", "noOk");
							return ret;
						}
						if(b == 0){
							ret.put("ifTimeOk", "noOk");
							return ret;
						}
						if(c == 0){
							ret.put("ifTimeOk", "noOk");
							return ret;
						}
						if(d == 0){
							ret.put("ifTimeOk", "noOk");
							return ret;
						}
						if(e == 0){
							ret.put("ifTimeOk", "noOk");
							return ret;
						}
						if(f == 0){
							ret.put("ifTimeOk", "noOk");
							return ret;
						}
						if(g == 0){
							ret.put("ifTimeOk", "noOk");
							return ret;
						}
						if(a == 1){
							ret.put("ifHaveOverlyingWeek", "noOk1");
							return ret;
						}
						if(b == 1){
							ret.put("ifHaveOverlyingWeek", "noOk2");
							return ret;
						}
						if(c == 1){
							ret.put("ifHaveOverlyingWeek", "noOk3");
							return ret;
						}
						if(d == 1){
							ret.put("ifHaveOverlyingWeek", "noOk4");
							return ret;
						}
						if(e == 1){
							ret.put("ifHaveOverlyingWeek", "noOk5");
							return ret;
						}
						if(f == 1){
							ret.put("ifHaveOverlyingWeek", "noOk6");
							return ret;
						}
						if(g == 1){
							ret.put("ifHaveOverlyingWeek", "noOk7");
							return ret;
						}
					}else{
						//插入数据
						if(pubPremiumAdd.getId() == null || pubPremiumAdd.getId() == ""){
							pubPremiumAdd.setId(id);
							pubPremiumAdd.setIsoperated("0");
							pubPremiumAdd.setRulestatus("0");
							dao.insertPubPremiumRule(insertPubPremiumRule(pubPremiumAdd));
							insertAllWeek(checkValslist,weekOnes,weekTows,weekThrees,weekFours,weekFives,weekSixss,weekSevens,id);
					  }else{
						  //插入历史数据
						    PubPremiumParam pubPremiumParam = new PubPremiumParam();
							pubPremiumParam.setId(pubPremiumAdd.getId());
							pubPremiumParam.setRuletype(pubPremiumAdd.getRuletype());
							pubPremiumParam.setCreater(pubPremiumAdd.getCreater());
							pubPremiumParam.setUpdater(pubPremiumAdd.getCreater());
							insertModifyHistory(pubPremiumParam);
							//删除原有数据
							dao.deleteWeek(pubPremiumAdd.getId());
							dao.deletWeekDetail(pubPremiumAdd.getId());
							pubPremiumAdd.setIsoperated("1");
							if(pubPremiumAdd.getRulestatus().equals("启用")){
								pubPremiumAdd.setRulestatus("1");
							}else{
								pubPremiumAdd.setRulestatus("0");
							}
							dao.insertPubPremiumRule(insertPubPremiumRule(pubPremiumAdd));
							insertAllWeek(checkValslist,weekOnes,weekTows,weekThrees,weekFours,weekFives,weekSixss,weekSevens,pubPremiumAdd.getId());
							PubPremiumParam pubPremiumParamId = dao.getById(pubPremiumParam);
							insetRedis(pubPremiumParamId,2);
					  }
					}
				}else{
					ret.put("ifHaveNull", "noOk");
					return ret;
				}
		}else{
			//插入日期
			//获取日期开始结束时间
			Date startdate = null;
			Date enddate = null; 
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			try {
				startdate = sdf.parse(datess[0]);
				enddate = sdf.parse(datess[1]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			pubPremiumAdd.setStartdate(startdate);
			pubPremiumAdd.setEnddate(enddate);
			//对日期的插入限制判断
			String newDates = dates.substring(dates.indexOf(",",dates.indexOf(",")+1 )+1,dates.length());
			//转换为数组
			String[] arr = newDates.split(",");
			//判断是否为修改,如果为新增
			if(pubPremiumAdd.getId() == null || pubPremiumAdd.getId() == ""){
				//空值判断
				if(!isTheNUll(newDates,arr)){
					ret.put("ifHaveNull", "noOk");
					return ret;
				};
				//是否存在选城市、所选业务类型、所填日期范围完全相同
				//通过城市名称获取城市id
				int abc = dao.getDateSame(pubPremiumAdd);
				if(abc != 0){
					ret.put("ifDateOk", "noOk");
					return ret;
				}
				//开始时间结束时间判断
				if(theTimeIsOk(arr) == 0){
					ret.put("ifTimeOk", "noOk");
					return ret;
				};
				//时间重叠判断
				if(theTimeIsOk(arr) == 1){
					ret.put("ifHaveOverlying", "noOk");
					return ret;
				};
				pubPremiumAdd.setId(id);
				pubPremiumAdd.setIsoperated("0");
				pubPremiumAdd.setRulestatus("0");
				dao.insertPubPremiumRule(insertPubPremiumRule(pubPremiumAdd));
				insertDate(datess,id);
			}else{
				//为修改
				//空值判断
				//通过城市名称获取城市id
				if(!isTheNUll(newDates,arr)){
					ret.put("ifHaveNull", "noOk");
					return ret;
				};
				/*//是否存在选城市、所选业务类型、所填日期范围完全相同
				int abc = dao.getDateSame(pubPremiumAdd);
				if(abc != 0){
					ret.put("ifDateOk", "noOk");
					return ret;
				}*/
				if(theTimeIsOk(arr) == 0){
					ret.put("ifTimeOk", "noOk");
					return ret;
				};
				//时间重叠判断
				if(theTimeIsOk(arr) == 1){
					ret.put("ifHaveOverlying", "noOk");
					return ret;
				};
				//插入历史数据
				PubPremiumParam pubPremiumParam = new PubPremiumParam();
				pubPremiumParam.setId(pubPremiumAdd.getId());
				pubPremiumParam.setRuletype(pubPremiumAdd.getRuletype());
				pubPremiumParam.setCreater(pubPremiumAdd.getCreater());
				pubPremiumParam.setUpdater(pubPremiumAdd.getCreater());
				insertModifyHistory(pubPremiumParam);
				//删除原有数据
				dao.deleteDate(pubPremiumAdd.getId());
				dao.deletDateDetail(pubPremiumAdd.getId());
				//插入原有id的数据
				pubPremiumAdd.setIsoperated("1");
				if(pubPremiumAdd.getRulestatus().equals("启用")){
					pubPremiumAdd.setRulestatus("1");
				}else{
					pubPremiumAdd.setRulestatus("0");
				}
				dao.insertPubPremiumRule(insertPubPremiumRule(pubPremiumAdd));
				insertDate(datess,pubPremiumAdd.getId());
				PubPremiumParam pubPremiumParamId = dao.getById(pubPremiumParam);
				insetRedis(pubPremiumParamId,2);
		  }
		}
		//判断是什么保存
		return ret;
	}
	//判断星期的插入
	public void insertAllWeek(List<String> checkValslist,String [] weekOnes,String [] weekTows,String [] weekThrees,String [] weekFours,String [] weekFives,String [] weekSixss,String [] weekSevens,String id){
		if(checkValslist.contains("one")){
			//三条数组一组循环插入
			insertWeek(weekOnes,id,1);
			//可以插入星期二的数据
		}
		if(checkValslist.contains("tow")){
			insertWeek(weekTows,id,2);
			//可以插入星期三的数据
		}
		if(checkValslist.contains("three")){
			insertWeek(weekThrees,id,3);
			//可以插入星期四的数据
		}
		if(checkValslist.contains("four")){
			insertWeek(weekFours,id,4);
			//可以插入星期五的数据
		}
		if(checkValslist.contains("five")){
			insertWeek(weekFives,id,5);
			//可以插入星期六的数据
		}
		if(checkValslist.contains("six")){
			insertWeek(weekSixss,id,6);
			//可以插入星期日的数据
		}
		if(checkValslist.contains("seven")){
			insertWeek(weekSevens,id,7);
			
		}
	}
	//出入主表的方法
	public PubPremiumRule insertPubPremiumRule(PubPremiumAdd pubPremiumAdd){
		//先存入主表数据
		PubPremiumRule pubPremiumRule = new PubPremiumRule();
		//如果为星期默认为永久
		if(pubPremiumAdd.getRuletype().equals("0")){
			pubPremiumRule.setIsperpetual(1);
		}else{
			pubPremiumRule.setIsperpetual(0);
		}
				//规则名称转换
				if(pubPremiumAdd.getRuletype().equals("0")){
					pubPremiumRule.setRuletype(0);
				}else{
					pubPremiumRule.setRuletype(1);
				}
				//通过城市名称获取城市id
				String cityId = dao.getCityId(pubPremiumAdd.getCityname());
				//设置存入的值
				//城市
				pubPremiumRule.setId(pubPremiumAdd.getId());
				pubPremiumRule.setCitycode(cityId);
				pubPremiumRule.setRulename(pubPremiumAdd.getRulename());
				pubPremiumRule.setCartype(Integer.parseInt(pubPremiumAdd.getCartype()));
				pubPremiumRule.setIsoperated(Integer.parseInt(pubPremiumAdd.getIsoperated()));
				pubPremiumRule.setPlatformtype(1);
				pubPremiumRule.setLeasescompanyid(pubPremiumAdd.getLeasescompanyid());
				pubPremiumRule.setStatus(1);
				pubPremiumRule.setRulestatus(Integer.parseInt(pubPremiumAdd.getRulestatus()));
				pubPremiumRule.setStartdt(pubPremiumAdd.getStartdate());
				pubPremiumRule.setEnddt(pubPremiumAdd.getEnddate());
				pubPremiumRule.setCreater(pubPremiumAdd.getCreater());
				pubPremiumRule.setUpdater(pubPremiumAdd.getCreater());
		return pubPremiumRule;
	}
	//星期数据的插入
	public void insertWeek(String[] weekdate,String premiumruleid,Integer weekday){
		//循环去除存入的数据
		for(int i=0;i<weekdate.length;i=i+3){
			String id = GUIDGenerator.newGUID();
			String startdt = weekdate[i];
			String enddt = weekdate[i+1];
			Double premiumrate = Double.parseDouble(weekdate[i+2]);
			PubPremiumRuleWeekdetail weekdetail = new PubPremiumRuleWeekdetail();
			weekdetail.setId(id);
			weekdetail.setPremiumruleid(premiumruleid);
			weekdetail.setWeekday(weekday);
			weekdetail.setStartdt(startdt);
			weekdetail.setEnddt(enddt);
			weekdetail.setPremiumrate(premiumrate);
			weekdetail.setStatus(1);
			dao.inserWeek(weekdetail);
		}
	}
	//日期插入
	public void insertDate(String[] dates,String premiumruleid){
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		//循环存入数据
		for(int i=2;i<dates.length;i=i+3){
			String id = GUIDGenerator.newGUID();
			Date startdate = null;
			Date enddate = null;
				try {
					startdate = sdf.parse( dates[0] );
					enddate = sdf.parse(dates[1]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String starttime = dates[i];
				String endtime = dates[i+1];
				Double premiumrate = Double.parseDouble(dates[i+2]);
			PubPremiumRuleDatedetail datedetail = new PubPremiumRuleDatedetail();
			datedetail.setId(id);
			datedetail.setPremiumruleid(premiumruleid);
			datedetail.setStartdate(startdate);
			datedetail.setEnddate(enddate);
			datedetail.setStarttime(starttime);
			datedetail.setEndtime(endtime);
			datedetail.setPremiumrate(premiumrate);
			dao.insertDate(datedetail);
		}
	}
	//是否包含星期
	public boolean ifHaveTheWeek(List<String> checkValslist,String number){
		if(checkValslist.contains(number)){
			return true;
		} 
		if(checkValslist.contains(number)){
			return true;
		} if(checkValslist.contains(number)){
			return true;
		} if(checkValslist.contains(number)){
			return true;
		} if(checkValslist.contains(number)){
			return true;
		} if(checkValslist.contains(number)){
			return true;
		} if (checkValslist.contains(number)){
			return true;
		}
		return false;
	}
	//判断开始时间比结束时间大返回0
	public int theTimeIsOk(String [] week){
		List<Integer> weekList = new ArrayList<Integer>();
		for(int i=0;i<week.length;i=i+3){
			String startdt = week[i].replaceAll(":","");
			String enddt = week[i+1].replaceAll(":","");
			int start = Integer.parseInt(startdt);
			int end = Integer.parseInt(enddt);
			if(start>end){
				return 0;
			}else{
				weekList.add(start);
				weekList.add(end);
			}
		}
		//对时间进行判断有没有叠加的情况
		//循环比较list里面的数据小的比大的大或者大的比小的小
		for(int m = 0;m<weekList.size();m = m+2){
			for(int n = 0;n<weekList.size()-3-m;n=n+2){
				int small = weekList.get(m);
				int big   = weekList.get(m+1);
				int nextSmall = weekList.get(n+2+m);
				int nextBig = weekList.get(n+3+m);
				if(!(small>nextBig || big<nextSmall)){
					return 1;
				}
			}
		}
		return 2;
	}
	public int timeOverlying(String [] week,List<String> checkValslist,String number){
		/*List<Integer> weekListSmall = new ArrayList<Integer>();
		List<Integer> weekIntListBig = new ArrayList<Integer>();*/
		//首先判断此星期有没有被勾选 没有被勾选返回 3
		if(!ifHaveTheWeek(checkValslist,number)){;
		   return 3;
		}
		return theTimeIsOk(week);
	}
	//判断是否已有溢价规则
	public boolean ifHavePremiumRule(PubPremiumAdd pubPremiumAdd){
		int count = dao.ifHavePremiumRule(pubPremiumAdd);
		if(count == 0){
			return false;
		}
		return true;
	}
	//判断是否有空值
	public boolean isTheNUll(String week,String[]weeks){
		int b = week.length()-week.replaceAll(",","").length();
		int c = weeks.length;
		if(c == 0){
			return false;
		}
		//循环数组判断里面是否有空字符串
		for(int i=0;i<weeks.length;i++){
			if(weeks[i] .equals("")){
				return false;
			}
		}
		if(c != (b+1)){
			return false;
		}
		return true;
	}
	//数组去重
	public String[] repeatArray(String[] array){
		List<String> result = new ArrayList<>();  
		boolean flag;  
		for(int i=0;i<array.length;i++){  
		    flag = false;  
		    for(int j=0;j<result.size();j++){  
		        if(array[i].equals(result.get(j))){  
		            flag = true;  
		            break;  
		        }  
		    }  
		    if(!flag){  
		        result.add(array[i]);  
		    }  
		}  
		String[] arrayResult = (String[]) result.toArray(new String[result.size()]);
		return arrayResult;
	}
	public PageBean detailIndex(PubPremiumDetail pubPremiumDetail){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(pubPremiumDetail.getsEcho());
		List<PubPremiumDetail> list = getPubPremiumRuleDetail(pubPremiumDetail);
		int iTotalRecords = getPubPremiumRuleDetailCount(pubPremiumDetail);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<PubPremiumDetail> getPubPremiumRuleDetail(PubPremiumDetail pubPremiumDetail){
		return dao.getPubPremiumRuleDetail(pubPremiumDetail);
	}
	public int getPubPremiumRuleDetailCount(PubPremiumDetail pubPremiumDetail){
		return dao.getPubPremiumRuleDetailCount(pubPremiumDetail);
	}
	public PageBean getDetailDateData(PubPremiumDetail pubPremiumDetail){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(pubPremiumDetail.getsEcho());
		List<PubPremiumDetail> list = getDetailDateDataList(pubPremiumDetail);
		int iTotalRecords = getDetailDateDataCount(pubPremiumDetail);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<PubPremiumDetail> getDetailDateDataList(PubPremiumDetail pubPremiumDetail){
		return dao.getDetailDateDataList(pubPremiumDetail);
	}
	public int getDetailDateDataCount(PubPremiumDetail pubPremiumDetail){
		return dao.getDetailDateDataCount(pubPremiumDetail);
	}
	public PageBean getHistoryData(PubPremiumHistory pubPremiumHistory){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(pubPremiumHistory.getsEcho());
		List<PubPremiumHistory> list = getHistoryDataList(pubPremiumHistory);
		int iTotalRecords = getHistoryDataCount(pubPremiumHistory);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	public List<PubPremiumHistory> getHistoryDataList(PubPremiumHistory pubPremiumHistory){
		return dao.getHistoryDataList(pubPremiumHistory);
	}
	public int getHistoryDataCount(PubPremiumHistory pubPremiumHistory){
		return dao.getHistoryDataCount(pubPremiumHistory);
	}
	//历史数据详情
	public PageBean getHistorydetail(PubPremiumHistory pubPremiumHistory){
		PageBean pageBean = new PageBean();
		List<PubPremiumHistory> list;
		List<PubPremiumDetail> list2;
		int iTotalRecords;
		//判断是不是修改
	 if(pubPremiumHistory.getOperationtype().equals("修改")){
		//判断是按星期还是按日期
		if(pubPremiumHistory.getRuletype().equals("按星期")){
		  list = getHistorydetailList(pubPremiumHistory);
		  iTotalRecords = getHistorydetailCount(pubPremiumHistory);
		}else{
		  list = getHistorydetailDateList(pubPremiumHistory);
		  iTotalRecords = getHistorydetaiDatelCount(pubPremiumHistory);
		}
		 pageBean.setsEcho(pubPremiumHistory.getsEcho());
			int iTotalDisplayRecords = iTotalRecords;
			pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
			pageBean.setiTotalRecords(iTotalRecords);
			pageBean.setAaData(list);
			return pageBean;
	 }else{
		 if(pubPremiumHistory.getRuletype().equals("按星期")){
			 list2 = getdetailList(pubPremiumHistory);
			 iTotalRecords = getdetailCount(pubPremiumHistory); 
		 }else{
			 list2 = getdetailDateList(pubPremiumHistory);
			 iTotalRecords = getdetaiDatelCount(pubPremiumHistory);
		 }
		 pageBean.setsEcho(pubPremiumHistory.getsEcho());
			int iTotalDisplayRecords = iTotalRecords;
			pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
			pageBean.setiTotalRecords(iTotalRecords);
			pageBean.setAaData(list2);
			return pageBean;
	 }
	
	}
	public List<PubPremiumHistory> getHistorydetailList(PubPremiumHistory pubPremiumHistory){
		return dao.getHistorydetailList(pubPremiumHistory);
	}
	public int getHistorydetailCount(PubPremiumHistory pubPremiumHistory){
		return dao.getHistorydetailCount(pubPremiumHistory);
	}
	public List<PubPremiumHistory> getHistorydetailDateList(PubPremiumHistory pubPremiumHistory){
		return dao.getHistorydetailDateList(pubPremiumHistory);
	}
	public int getHistorydetaiDatelCount(PubPremiumHistory pubPremiumHistory){
		return dao.getHistorydetaiDatelCount(pubPremiumHistory);
	}
	public List<PubPremiumDetail> getdetailList(PubPremiumHistory pubPremiumHistory){
		return dao.getdetailList(pubPremiumHistory);
	}
	public int getdetailCount(PubPremiumHistory pubPremiumHistory){
		return dao.getdetailCount(pubPremiumHistory);
	}
	public List<PubPremiumDetail> getdetailDateList(PubPremiumHistory pubPremiumHistory){
		return dao.getdetailDateList(pubPremiumHistory);
	}
	public int getdetaiDatelCount(PubPremiumHistory pubPremiumHistory){
		return dao.getdetaiDatelCount(pubPremiumHistory);
	}
	//修改
	public PubPremiumModify modify(PubPremiumParam pubPremiumParam)  {
		//获取规则信息
		PubPremiumModify pubPremiumModify = dao.modify(pubPremiumParam);
		//判断是星期还是日期修改
		String weekOrDate = pubPremiumParam.getRuletype();
		if(weekOrDate.equals("按星期")){
		List<PubPremiumDetail> week = dao.getWeek(pubPremiumParam.getId());
		 pubPremiumModify.setWeek(week);
		}else{
		List<PubPremiumDetail> date = dao.getDate(pubPremiumParam.getId());
		 pubPremiumModify.setDate(date);
		}
		return pubPremiumModify;
	}
	//修改插入历史数据
	public void insertModifyHistory(PubPremiumParam pubPremiumParam){
		if(pubPremiumParam.getRuletype().equals("0")){
			pubPremiumParam.setRuletype("0");
		}else{
			pubPremiumParam.setRuletype("1");
		}
		//通过id获取规则信息
		 PubPremiumHistory pubPremiumHistory = new PubPremiumHistory();
		   String id = GUIDGenerator.newGUID();
		   pubPremiumHistory.setId(id);
		   pubPremiumHistory.setPremiumruleid(pubPremiumParam.getId());
		   pubPremiumHistory.setOperationtype("2");
		   pubPremiumHistory.setRuletype(pubPremiumParam.getRuletype());
		   pubPremiumHistory.setCreater(pubPremiumParam.getCreater());
		   dao.insertIntoHistory(pubPremiumHistory);
		//插入week历史详情
		   //通过id找出所有之前的记录
		   if(pubPremiumParam.getRuletype().equals("0")){
			   //按星期
			   List<PubPremiumDetail> weekList = dao.getWeek(pubPremiumParam.getId());
			   for(int i=0;i<weekList.size();i++){
				   weekList.get(i).setId(id);
			    dao.insertWeekHistory(weekList.get(i));
			   }
		   }else{
			   List<PubPremiumDetail> dateList = dao.getDate(pubPremiumParam.getId());
			   for(int i=0;i<dateList.size();i++){
				   dateList.get(i).setId(id);
				    dao.insertDateHistory(dateList.get(i));
				   }
		   }
	}
	//对修改，禁用，启用存入redis
	public void insetRedis(PubPremiumParam pubPremiumParam,int operationtype){
		//拼接key
				String theKey ="PREMIUMRULE"+"_"+pubPremiumParam.getCitycode()+"_"+pubPremiumParam.getCartype()+"_"+pubPremiumParam.getPlatformtype()+"_"+pubPremiumParam.getLeasescompanyid()+"_"+pubPremiumParam.getId();
		if(operationtype == 1){
			JedisUtil.delKey(theKey);
		}else if(operationtype ==0){
			//获取规则信息
			PubPremiumRule pubPremiumRule= dao.getRule(pubPremiumParam.getId());
			List<PubPremiumRuleWeekdetail> weekList = dao.getWeekRule(pubPremiumParam.getId());
			List<PubPremiumRuleDatedetail> dateList = dao.getDateRule(pubPremiumParam.getId());
/*			pubPremiumRule.setWeekdetails(weekList);
			pubPremiumRule.setDatedetails(dateList);*/
			//拼接value
//			JSONObject jsonRule = JSONObject.fromObject(pubPremiumRule);
			JSONObject jsonRule = new JSONObject();
			jsonRule.put("premiumrule", pubPremiumRule);
			jsonRule.put("weekdetails", weekList);
			jsonRule.put("datedetails", dateList);
			JedisUtil.setString(theKey,jsonRule.toString());
		}else{
			JedisUtil.delKey(theKey);
			//获取规则信息
			PubPremiumRule pubPremiumRule= dao.getRule(pubPremiumParam.getId());
			List<PubPremiumRuleWeekdetail> weekList = dao.getWeekRule(pubPremiumParam.getId());
			List<PubPremiumRuleDatedetail> dateList = dao.getDateRule(pubPremiumParam.getId());
/*			pubPremiumRule.setWeekdetails(weekList);
			pubPremiumRule.setDatedetails(dateList);
			JSONObject jsonRule = JSONObject.fromObject(pubPremiumRule);*/
			JSONObject jsonRule = new JSONObject();
			jsonRule.put("premiumrule", pubPremiumRule);
			jsonRule.put("weekdetails", weekList);
			jsonRule.put("datedetails", dateList);
			JedisUtil.setString(theKey,jsonRule.toString());
		}
	}
}

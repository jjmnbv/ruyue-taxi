package com.szyciov.carservice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.OrderStatisticsDao;
import com.szyciov.entity.Retcode;
import com.szyciov.param.OrderStatisticsQueryParam;
import com.szyciov.util.GUIDGenerator;

@Service("orderStatisticsService")
public class OrderStatisticsService {
	
	private OrderStatisticsDao orderStatisticsDao;

	@Resource(name="orderStatisticsDao")
	public void setOrderStatisticsDao(OrderStatisticsDao orderStatisticsDao) {
		this.orderStatisticsDao = orderStatisticsDao;
	}
	/**
	 * 租赁端首页
	 * @param queryParam
	 * @param userToken
	 * @return
	 */
	public Map<String, Object> leIndexOrderStatistics(OrderStatisticsQueryParam queryParam, String userToken) {
//		insertleIndexorderstatistics();
//		insertleOrgOrderStatistics();
//		insertlePersonalOrderStatistics();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currendDate = dateFormat.format(new Date());
		if(userToken != null){
			if(userToken.equals("1")){
				Calendar cal = Calendar.getInstance();
			    cal.setTime(new Date());
				cal.add(Calendar.DATE,-1);
				currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			}
		}
		queryParam.setTableName("le_indexorderstatistics");
//		queryParam.setId(GUIDGenerator.newGUID());;
		if(queryParam.getStartDate() == null) {
			queryParam.setStartDate(currendDate);
		}
		
		if(queryParam.getEndDate() == null) {
			queryParam.setEndDate(currendDate);
		}
		
//		boolean hasRepeatData = validateDataExists(queryParam);
//		if(hasRepeatData) {
			deleteRepeatData(queryParam);
//		}
		
		int result = orderStatisticsDao.leIndexOrderStatistics(queryParam);
		int result1 = orderStatisticsDao.leIndexOrderStatisticsok(queryParam);
		
		  if((result) > 0 || (result1>0)) {
				resultMap.put("status", Retcode.OK.code);
				resultMap.put("message", "统计成功");
			} else {
				resultMap.put("status", Retcode.FAILED.code);
				resultMap.put("message", "统计失败|无数据");
			}
		
		return resultMap;
	}
	
	/**
	 * 租赁端机构统计
	 * @param queryParam
	 * @param userToken
	 * @return
	 */
	public Map<String, Object> leOrgOrderStatistics(OrderStatisticsQueryParam queryParam, String userToken) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currendDate = dateFormat.format(new Date());
		if(userToken != null){
			if(userToken.equals("1")){
				Calendar cal = Calendar.getInstance();
			    cal.setTime(new Date());
				cal.add(Calendar.DATE,-1);
				currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			}
		}
		queryParam.setTableName("le_orgorderstatistics");
		queryParam.setId(GUIDGenerator.newGUID());;
		if(queryParam.getStartDate() == null) {
			queryParam.setStartDate(currendDate);
		}
		
		if(queryParam.getEndDate() == null) {
			queryParam.setEndDate(currendDate);
		}
		
//		boolean hasRepeatData = validateDataExists(queryParam);
//		if(hasRepeatData) {
			deleteRepeatData(queryParam);
//		}
		
		int result = orderStatisticsDao.leOrgOrderStatistics(queryParam);
		
		if(result > 0) {
			resultMap.put("status", Retcode.OK.code);
			resultMap.put("message", "统计成功");
		} else {
			resultMap.put("status", Retcode.FAILED.code);
			resultMap.put("message", "统计失败|无数据");
		}
		
		return resultMap;
	}
	
	/**
	 * 租赁端个人订单统计
	 * @param queryParam
	 * @param userToken
	 * @return
	 */
	public Map<String, Object> lePersonalOrderStatistics(OrderStatisticsQueryParam queryParam, String userToken) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currendDate = dateFormat.format(new Date());
		if(userToken != null){
			if(userToken.equals("1")){
				Calendar cal = Calendar.getInstance();
			    cal.setTime(new Date());
				cal.add(Calendar.DATE,-1);
				currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			}
		}
		queryParam.setTableName("le_personalorderstatistics");
		queryParam.setId(GUIDGenerator.newGUID());;
		if(queryParam.getStartDate() == null) {
			queryParam.setStartDate(currendDate);
		}
		
		if(queryParam.getEndDate() == null) {
			queryParam.setEndDate(currendDate);
		}
		
//		boolean hasRepeatData = validateDataExists(queryParam);
//		if(hasRepeatData) {
			deleteRepeatData(queryParam);
//		}
		
		int result = orderStatisticsDao.lePersonalOrderStatistics(queryParam);
		int result1 = orderStatisticsDao.lePersonalOrderStatisticsToC(queryParam);
		int result2 = orderStatisticsDao.lePersonalOrderStatisticsTaxi(queryParam);
		
		if((result) > 0 || (result1>0) || (result2>0)) {
			resultMap.put("status", Retcode.OK.code);
			resultMap.put("message", "统计成功");
		} else {
			resultMap.put("status", Retcode.FAILED.code);
			resultMap.put("message", "统计失败|无数据");
		}
		
		return resultMap;
	}
	/**
	 * 运管端首页统计
	 * @param queryParam
	 * @param userToken
	 * @return
	 */
	public Map<String, Object> opIndexorderstatistics(OrderStatisticsQueryParam queryParam, String userToken) {
//		insertopIndexorderstatistics();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currendDate = dateFormat.format(new Date());
		if(userToken != null){
			if(userToken.equals("1")){
				Calendar cal = Calendar.getInstance();
			    cal.setTime(new Date());
				cal.add(Calendar.DATE,-1);
				currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			}
		}
		queryParam.setTableName("op_indexorderstatistics");
		queryParam.setId(GUIDGenerator.newGUID());;
		if(queryParam.getStartDate() == null) {
			queryParam.setStartDate(currendDate);
		}
		
		if(queryParam.getEndDate() == null) {
			queryParam.setEndDate(currendDate);
		}
		
//		boolean hasRepeatData = validateDataExists(queryParam);
//		if(hasRepeatData) {
			deleteRepeatData(queryParam);
//		}
		
		int result = orderStatisticsDao.opIndexorderstatistics(queryParam);
		int result1 =  orderStatisticsDao.opIndexorderstatisticsP(queryParam);
		int result2 =  orderStatisticsDao.opIndexorderstatisticsPB(queryParam);
		int result3 =  orderStatisticsDao.opIndexorderstatisticsTaxi(queryParam);
		
		  if((result) > 0 || (result1>0)|| (result2>0) || (result3>0)) {
				resultMap.put("status", Retcode.OK.code);
				resultMap.put("message", "统计成功");
			} else {
				resultMap.put("status", Retcode.FAILED.code);
				resultMap.put("message", "统计失败|无数据");
			}
		
		return resultMap;
	}
	/**
	 * 运管端销售统计
	 * @param queryParam
	 * @param userToken
	 * @return
	 */
	public Map<String, Object> opOrderstatistics(OrderStatisticsQueryParam queryParam, String userToken) {
//		insertopOrderstatistics();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currendDate = dateFormat.format(new Date());
		if(userToken != null){
			if(userToken.equals("1")){
				Calendar cal = Calendar.getInstance();
			    cal.setTime(new Date());
				cal.add(Calendar.DATE,-1);
				currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			}
		}
		queryParam.setTableName("op_orderstatistics");
		queryParam.setId(GUIDGenerator.newGUID());;
		if(queryParam.getStartDate() == null) {
			queryParam.setStartDate(currendDate);
		}
		
		if(queryParam.getEndDate() == null) {
			queryParam.setEndDate(currendDate);
		}
		
//		boolean hasRepeatData = validateDataExists(queryParam);
//		if(hasRepeatData) {
			deleteRepeatData(queryParam);
//		}
		
		int result = orderStatisticsDao.opOrderstatistics(queryParam);
		int result1 = orderStatisticsDao.opOrderstatisticsP(queryParam);
		
		if((result) > 0 || (result1>0)) {
			resultMap.put("status", Retcode.OK.code);
			resultMap.put("message", "统计成功");
		} else {
			resultMap.put("status", Retcode.FAILED.code);
			resultMap.put("message", "统计失败|无数据");
		}
		
		return resultMap;
	}
	/**
	 * 机构端报表管理公司
	 * @param queryParam
	 * @param userToken
	 * @return
	 */
	public Map<String, Object> orgCompanystatistics(OrderStatisticsQueryParam queryParam, String userToken) {
//		insertorgCompanystatistics();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currendDate = dateFormat.format(new Date());
		if(userToken != null){
			if(userToken.equals("1")){
				Calendar cal = Calendar.getInstance();
			    cal.setTime(new Date());
				cal.add(Calendar.DATE,-1);
				currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			}
		}
		queryParam.setTableName("org_companystatistics");
		queryParam.setId(GUIDGenerator.newGUID());;
		if(queryParam.getStartDate() == null) {
			queryParam.setStartDate(currendDate);
		}
		
		if(queryParam.getEndDate() == null) {
			queryParam.setEndDate(currendDate);
		}
		
//		boolean hasRepeatData = validateDataExists(queryParam);
//		if(hasRepeatData) {
			deleteRepeatData(queryParam);
//		}
		
		int result = orderStatisticsDao.orgCompanystatistics(queryParam);
		
		if(result > 0) {
			resultMap.put("status", Retcode.OK.code);
			resultMap.put("message", "统计成功");
		} else {
			resultMap.put("status", Retcode.FAILED.code);
			resultMap.put("message", "统计失败|无数据");
		}
		
		return resultMap;
	}
	/**
	 * 机构端报表管理部门
	 * @param queryParam
	 * @param userToken
	 * @return
	 */
	public Map<String, Object> orgDeptstatistics(OrderStatisticsQueryParam queryParam, String userToken) {
//		insertorgDeptstatistics();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currendDate = dateFormat.format(new Date());
		if(userToken != null){
			if(userToken.equals("1")){
				Calendar cal = Calendar.getInstance();
			    cal.setTime(new Date());
				cal.add(Calendar.DATE,-1);
				currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			}
		}
		queryParam.setTableName("org_deptstatistics");
		queryParam.setId(GUIDGenerator.newGUID());;
		if(queryParam.getStartDate() == null) {
			queryParam.setStartDate(currendDate);
		}
		
		if(queryParam.getEndDate() == null) {
			queryParam.setEndDate(currendDate);
		}
		
//		boolean hasRepeatData = validateDataExists(queryParam);
//		if(hasRepeatData) {
			deleteRepeatData(queryParam);
//		}
		
		int result = orderStatisticsDao.orgDeptstatistics(queryParam);
		
		if(result > 0) {
			resultMap.put("status", Retcode.OK.code);
			resultMap.put("message", "统计成功");
		} else {
			resultMap.put("status", Retcode.FAILED.code);
			resultMap.put("message", "统计失败|无数据");
		}
		
		return resultMap;
	}
	private boolean validateDataExists(OrderStatisticsQueryParam queryParam) {

		int result = orderStatisticsDao.validateDataExists(queryParam);
		
		if(result > 0) return true;
		return false;
	}
	
	private boolean deleteRepeatData(OrderStatisticsQueryParam queryParam) {
		int result = orderStatisticsDao.deleteRepeatData(queryParam);
		
		if(result > 0) return true;
		return false;
	}
	/**
	 * 租赁首页
	 * @param queryParam
	 * @param userToken
	 * @return
	 */
	public void insertleIndexorderstatistics() {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		queryParam.setTableName("le_indexorderstatistics");
		orderStatisticsDao.deleteData(queryParam);
		Date date = null;
		String currendDate;
		try {
			date = (new SimpleDateFormat("yyyy-MM-dd")).parse("2016-10-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		for(int i=1;;i++){
		    cal.setTime(date);
			cal.add(Calendar.DATE,i);
			currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			queryParam.setId(GUIDGenerator.newGUID());
			queryParam.setTableName("le_indexorderstatistics");
			queryParam.setStartDate(currendDate);
			queryParam.setEndDate(currendDate);
			boolean hasRepeatData = validateDataExists(queryParam);
			if(hasRepeatData) {
				deleteRepeatData(queryParam);
			}
			 orderStatisticsDao.leIndexOrderStatistics(queryParam);
			 orderStatisticsDao.leIndexOrderStatisticsok(queryParam);
			 String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			 if(currendDate.equals(today)){
				 break;
			 }
		}
	}
	/**
	 * 机构订单统计
	 */
	public void insertleOrgOrderStatistics() {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		queryParam.setTableName("le_orgorderstatistics");
		orderStatisticsDao.deleteData(queryParam);
		Date date = null;
		String currendDate;
		try {
			date = (new SimpleDateFormat("yyyy-MM-dd")).parse("2016-10-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		for(int i=1;;i++){
		    cal.setTime(date);
			cal.add(Calendar.DATE,i);
			currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			queryParam.setId(GUIDGenerator.newGUID());
			queryParam.setTableName("le_orgorderstatistics");
			queryParam.setStartDate(currendDate);
			queryParam.setEndDate(currendDate);
			boolean hasRepeatData = validateDataExists(queryParam);
			if(hasRepeatData) {
				deleteRepeatData(queryParam);
			}
			 orderStatisticsDao.leOrgOrderStatistics(queryParam);
			 String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			 if(currendDate.equals(today)){
				 break;
			 }
		}
	}
	/**
	 * 个人订单统计
	 */
	public void insertlePersonalOrderStatistics() {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		queryParam.setTableName("le_personalorderstatistics");
		orderStatisticsDao.deleteData(queryParam);
		Date date = null;
		String currendDate;
		try {
			date = (new SimpleDateFormat("yyyy-MM-dd")).parse("2016-10-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		for(int i=1;;i++){
		    cal.setTime(date);
			cal.add(Calendar.DATE,i);
			currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			queryParam.setId(GUIDGenerator.newGUID());
			queryParam.setTableName("le_personalorderstatistics");
			queryParam.setStartDate(currendDate);
			queryParam.setEndDate(currendDate);
			boolean hasRepeatData = validateDataExists(queryParam);
			if(hasRepeatData) {
				deleteRepeatData(queryParam);
			}
			 orderStatisticsDao.lePersonalOrderStatistics(queryParam);
			 orderStatisticsDao.lePersonalOrderStatisticsToC(queryParam);
			 orderStatisticsDao.lePersonalOrderStatisticsTaxi(queryParam);
			 String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			 if(currendDate.equals(today)){
				 break;
			 }
		}
	}
	/**
	 * 运管首页
	 */
	public void insertopIndexorderstatistics() {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		queryParam.setTableName("op_indexorderstatistics");
		orderStatisticsDao.deleteData(queryParam);
		Date date = null;
		String currendDate;
		try {
			date = (new SimpleDateFormat("yyyy-MM-dd")).parse("2016-10-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		for(int i=1;;i++){
		    cal.setTime(date);
			cal.add(Calendar.DATE,i);
			currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			queryParam.setId(GUIDGenerator.newGUID());
			queryParam.setTableName("op_indexorderstatistics");
			queryParam.setStartDate(currendDate);
			queryParam.setEndDate(currendDate);
//			boolean hasRepeatData = validateDataExists(queryParam);
//			if(hasRepeatData) {
				deleteRepeatData(queryParam);
//			}
			 orderStatisticsDao.opIndexorderstatistics(queryParam);
			 orderStatisticsDao.opIndexorderstatisticsP(queryParam);
			 orderStatisticsDao.opIndexorderstatisticsPB(queryParam);
			 orderStatisticsDao.opIndexorderstatisticsTaxi(queryParam);
			 String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			 if(currendDate.equals(today)){
				 break;
			 }
		}
	}
	/**
	 * 运管销售统计
	 */
	public void insertopOrderstatistics() {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		queryParam.setTableName("op_orderstatistics");
		orderStatisticsDao.deleteData(queryParam);
		Date date = null;
		String currendDate;
		try {
			date = (new SimpleDateFormat("yyyy-MM-dd")).parse("2016-10-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		for(int i=1;;i++){
		    cal.setTime(date);
			cal.add(Calendar.DATE,i);
			currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			queryParam.setId(GUIDGenerator.newGUID());
			queryParam.setTableName("op_orderstatistics");
			queryParam.setStartDate(currendDate);
			queryParam.setEndDate(currendDate);
//			boolean hasRepeatData = validateDataExists(queryParam);
//			if(hasRepeatData) {
			deleteRepeatData(queryParam);
//			}
			 orderStatisticsDao.opOrderstatistics(queryParam);
			 orderStatisticsDao.opOrderstatisticsP(queryParam);
			 String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			 if(currendDate.equals(today)){
				 break;
			 }
		}
	}
	/**
	 * 机构公司统计
	 */
	public void insertorgCompanystatistics() {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		queryParam.setTableName("org_companystatistics");
		orderStatisticsDao.deleteData(queryParam);
		Date date = null;
		String currendDate;
		try {
			date = (new SimpleDateFormat("yyyy-MM-dd")).parse("2016-10-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		for(int i=1;;i++){
		    cal.setTime(date);
			cal.add(Calendar.DATE,i);
			currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			queryParam.setId(GUIDGenerator.newGUID());
			queryParam.setTableName("org_companystatistics");
			queryParam.setStartDate(currendDate);
			queryParam.setEndDate(currendDate);
//			boolean hasRepeatData = validateDataExists(queryParam);
//			if(hasRepeatData) {
				deleteRepeatData(queryParam);
//			}
			 orderStatisticsDao.orgCompanystatistics(queryParam);
			 String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			 if(currendDate.equals(today)){
				 break;
			 }
		}
	}
	/**
	 * 机构部门统计
	 */
	public void insertorgDeptstatistics() {
		OrderStatisticsQueryParam queryParam = new OrderStatisticsQueryParam();
		queryParam.setTableName("org_deptstatistics");
		orderStatisticsDao.deleteData(queryParam);
		Date date = null;
		String currendDate;
		try {
			date = (new SimpleDateFormat("yyyy-MM-dd")).parse("2016-10-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		for(int i=1;;i++){
		    cal.setTime(date);
			cal.add(Calendar.DATE,i);
			currendDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			queryParam.setId(GUIDGenerator.newGUID());
			queryParam.setTableName("org_deptstatistics");
			queryParam.setStartDate(currendDate);
			queryParam.setEndDate(currendDate);
//			boolean hasRepeatData = validateDataExists(queryParam);
//			if(hasRepeatData) {
				deleteRepeatData(queryParam);
//			}
			 orderStatisticsDao.orgDeptstatistics(queryParam);
			 String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			 if(currendDate.equals(today)){
				 break;
			 }
		}
	}
	public void getAllDatas() {
		   insertleIndexorderstatistics();
		   insertleOrgOrderStatistics();
		   insertlePersonalOrderStatistics();
		   insertopIndexorderstatistics();
		   insertopOrderstatistics();
		   insertorgCompanystatistics();
		   insertorgDeptstatistics();
	}
}
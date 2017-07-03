package com.szyciov.lease.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.entity.UserNews;
import com.szyciov.lease.dao.OrgOrganDao;
import com.szyciov.lease.dao.OrganBillDao;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganBill;
import com.szyciov.lease.entity.OrgOrganBillState;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.param.OrganBillQueryParam;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.param.UserNewsParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Service("organBillService")
public class OrganBillService {
	private OrganBillDao dao;
	private OrgOrganDao organDao;
	private TemplateHelper templateHelper = new TemplateHelper();

	@Resource(name = "OrganBillDao")
	public void setDao(OrganBillDao dao) {
		this.dao = dao;
	}
	
	@Resource(name="OrgOrganDao")
	public void setOrganDao(OrgOrganDao organDao) {
		this.organDao = organDao;
	}
	
	public PageBean getCurOrganBillByQuery(OrganBillQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrgOrganBill> list = getCurOrganBillListByQuery(queryParam);
		int iTotalRecords = getCurOrganBillListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<OrgOrganBill> getCurOrganBillListByQuery(OrganBillQueryParam organBillQueryParam) {
    	return dao.getCurOrganBillListByQuery(organBillQueryParam);
    }
	
	public int getCurOrganBillListCountByQuery(OrganBillQueryParam organBillQueryParam) {
		return dao.getCurOrganBillListCountByQuery(organBillQueryParam);
	}
	
	public List<OrgOrganBillState> getOrganBillStateById(String id) {
		return dao.getOrganBillStateById(id);
	}
	
	public PageBean getOrgOrderByQuery(OrganBillQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrgOrder> list = getOrgOrderListByQuery(queryParam);
		int iTotalRecords = getOrgOrderListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}

	public List<OrgOrder> getOrgOrderListByQuery(OrganBillQueryParam organBillQueryParam) {
		return dao.getOrgOrderListByQuery(organBillQueryParam);
	}
	
	public int getOrgOrderListCountByQuery(OrganBillQueryParam organBillQueryParam) {
		return dao.getOrgOrderListCountByQuery(organBillQueryParam);
	}

	public PageBean getManualOrgOrderByQuery(OrganBillQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrgOrder> list = getManualOrgOrderListByQuery(queryParam);
		int iTotalRecords = getManualOrgOrderListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}

	public List<OrgOrder> getManualOrgOrderListByQuery(OrganBillQueryParam organBillQueryParam) {
		return dao.getManualOrgOrderListByQuery(organBillQueryParam);
	}
	
	public int getManualOrgOrderListCountByQuery(OrganBillQueryParam organBillQueryParam) {
		return dao.getManualOrgOrderListCountByQuery(organBillQueryParam);
	}
	
	public Map<String, String> createOrganbill(OrgOrganBill orgOrganBill) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "已生成账单");
		
		OrgOrgan organ = organDao.getByOrgOrganId(orgOrganBill.getOrganId());
		// String uuid = GUIDGenerator.newGUID();
		Map<String, Object> resultMap = templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApi") + "/OrderBill/GetBillNo", 
				HttpMethod.POST, null, organ, Map.class);
		String uuid = (String) resultMap.get("billNo");
		orgOrganBill.setId(uuid);
		
		// 0-月结，1-季结，2-手动结算
		orgOrganBill.setSource("2");
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		
		orgOrganBill.setCreateTime(date);
		orgOrganBill.setUpdateTime(date);
		// 插入数据到机构账单表
		dao.createOrganbill(orgOrganBill);
		
		// 插入数据到机构账单状态记录表
		// 1-生成账单
		String billState = "1";
		createOrganBillState(orgOrganBill.getRemark(), uuid, billState, time);
		// 新生成（2-待核对）   重新生成（9-重新生成）
		billState = "2";
		date.setSeconds(date.getSeconds() + 1);
		time=format.format(date);
		createOrganBillState(orgOrganBill.getRemark(), uuid, billState, time);
		
		// 插入数据到机构账单明细表
		createOrganBillDetails(orgOrganBill.getOrderId(), uuid);
		
		return ret;
	}
	
	public String getNowDate() {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		return time;
	}
	
	public Map<String, String> reCreateOrganbill(OrgOrganBill orgOrganBill) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "账单重新生成成功");
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		// 增加重新生成状态
		// 9-重新生成
		String billState = "9";
		createOrganBillState(orgOrganBill.getRemark(), orgOrganBill.getId(), billState, time);
		// 增加已作废状态
		// 8-已作废
		billState = "8";
		date.setSeconds(date.getSeconds() + 1);
		time=format.format(date);
		createOrganBillState(orgOrganBill.getRemark(), orgOrganBill.getId(), billState, time);
	
		// 0-月结，1-季结，2-手动结算
		//orgOrganBill.setSource("2");

		OrgOrgan organ = organDao.getByOrgOrganId(orgOrganBill.getOrganId());
		Map<String, Object> resultMap = templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApi") + "/OrderBill/GetBillNo", 
				HttpMethod.POST, null, organ, Map.class);
		String uuid = (String) resultMap.get("billNo");
		//String uuid = GUIDGenerator.newGUID();
		orgOrganBill.setId(uuid);
		
		orgOrganBill.setCreateTime(date);
		orgOrganBill.setUpdateTime(date);
		// 插入数据到机构账单表
		dao.createOrganbill(orgOrganBill);
		
		// 插入数据到机构账单状态记录表
		// 1-生成账单
		billState = "1";
		createOrganBillState(orgOrganBill.getRemark(), uuid, billState, time);
		// 新生成（2-待核对）   
		billState = "2";
		date.setSeconds(date.getSeconds() + 1);
		time=format.format(date);
		createOrganBillState(orgOrganBill.getRemark(), uuid, billState, time);
		
		// 插入数据到机构账单明细表
		createOrganBillDetails(orgOrganBill.getOrderId(), uuid);
		
		return ret;
	}
	
	public void createOrganBillState(String comment, String billsId, String billState, String time) {
		String uuid = GUIDGenerator.newGUID();
		Map<String, String> organBillState = new HashMap<String, String>();
		organBillState.put("id", uuid);
		organBillState.put("billState", billState);
		organBillState.put("billsId", billsId);
		organBillState.put("comment", comment);
		organBillState.put("operationTime", time);
		organBillState.put("createTime", time);
		organBillState.put("updateTime", time);
		dao.createOrganBillState(organBillState);
	}
	
	public void createOrganBillDetails(String orderIds, String billsId) {
		String[] orderId = orderIds.split(",");
		for (int i = 0; i < orderId.length; i++) {
			Map<String, String> organBillDetails = new HashMap<String, String>();
			String uuid = GUIDGenerator.newGUID();
			organBillDetails.put("id", uuid);
			organBillDetails.put("billsId", billsId);
			organBillDetails.put("orderId", orderId[i]);
			dao.createOrganBillDetails(organBillDetails);
			
			// 改变订单状态  2-结算中
			dao.changeOrderStatusToBalance(orderId[i]);
		}
	}
	
	public Map<String, String> deleteOrganbill(String id, String remark) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "作废成功");
		// 作废前检查数据是否已存在
		if (checkIsExist(id) == 0) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "作废失败");
		} else {
			// 插入数据到机构账单状态记录表
			// 获取当前时间
			String time = getNowDate();
			// 8-已作废
			String billState = "8";
			createOrganBillState(remark, id, billState, time);
			
			/**订单的状态回滚到生成账单前的状态 */
			// 1-已支付  4-未结算
			changeOrgOrderStatus("4", id);
			
		}
		return ret;
	}
	
	public int checkIsExist(String id){
		return dao.getOrganBillStateCountById(id);
	}

	public Map<String, String> confirmAccount(OrgOrganBill orgOrganBill) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "确认");
		
		//Map<String, Object> map = new HashMap<String, Object>();
		//map.put("money", orgOrganBill.getMoney());
		//map.put("organId", orgOrganBill.getOrganId());
		//map.put("leasesCompanyId", orgOrganBill.getLeasesCompanyId());
		//dao.reduceOrganAccount(map);
		
		// 获取当前时间
		String time = getNowDate();
		// 7-已结算
		createOrganBillState("", orgOrganBill.getId(), "7", time);
		
		// 账单关联的订单状态都变为   3、已结算
		changeOrgOrderStatus("3", orgOrganBill.getId());
		
		// 给机构端超管、财务管理员发送消息
		confirmNews(orgOrganBill.getId());
		
		// 根据未结算账单的数量判断是否要更新信用额度和可用余额
		changeLineOfCredit(orgOrganBill);
		
		return ret;
	}
	
	public void confirmNews(String billsId) {
		OrgOrganBill orgOrganBill = getOrganBillById(billsId);
		StringBuffer content = new StringBuffer();
		content.append("（");
		content.append(orgOrganBill.getLeasesCompanyShortName());
		content.append("）");
		content.append(orgOrganBill.getName());
		content.append("租赁端确认账单已收款，请知悉");
		
		List<String> userList = dao.getOrganUserIdByType(orgOrganBill.getOrganId());
		for (String userId : userList) {
			UserNewsParam userNewsParam = new UserNewsParam();
			userNewsParam.setUserNewsTbName(UserNewsParam.ORG_USERNEWS_TABNAME);
			UserNews userNews = new UserNews();
			String uuid = GUIDGenerator.newGUID();
			userNews.setId(uuid);
			userNews.setUserid(userId);
			userNews.setType(userNews.USER_NEWS_TYPE_OTHER);
			JSONObject json = new JSONObject();
			json.put("type", "7");
			json.put("title", "账单确认收款");
			json.put("content", content.toString());
			userNews.setContent(json.toString());
			userNews.setNewsstate(userNews.USER_NEWS_STATE_UNREAD);
			userNewsParam.setUserNews(userNews);
			createNews(userNewsParam);
		}
	}
	
	public void changeLineOfCredit(OrgOrganBill orgOrganBill) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("leasesCompanyId", orgOrganBill.getLeasesCompanyId());
		map.put("organId", orgOrganBill.getOrganId());
		OrgOrganCompanyRef orgOrganCompanyRef = dao.getLineOfCredit(map);
		// 如果可用余额大于当前生效的信用额度，则恢复信用额度（如信用额度发生变更则启用变更后信用额度），否则不恢复
		if (orgOrganCompanyRef.getBalance() > orgOrganCompanyRef.getLineOfCredit()) {
			if (orgOrganCompanyRef.getNextLineOfCredit() != null && StringUtils.isNotBlank(orgOrganCompanyRef.getNextLineOfCredit().toString())) {
				map.put("lineOfCredit", orgOrganCompanyRef.getNextLineOfCredit());
				map.put("balance", orgOrganCompanyRef.getBalance() + (orgOrganCompanyRef.getNextLineOfCredit() - orgOrganCompanyRef.getLineOfCredit()));
				dao.updateLineOfCredit(map);
			}
		}

		/*if (dao.checkUnBalanceBillCount(map) > 0) {
			return;
		} else {
			OrgOrganCompanyRef orgOrganCompanyRef = dao.getLineOfCredit(map);
			if (orgOrganCompanyRef.getNextLineOfCredit() != null && StringUtils.isNotBlank(orgOrganCompanyRef.getNextLineOfCredit().toString())) {
				map.put("lineOfCredit", orgOrganCompanyRef.getNextLineOfCredit());
				map.put("balance", orgOrganCompanyRef.getBalance() + (orgOrganCompanyRef.getNextLineOfCredit() - orgOrganCompanyRef.getLineOfCredit()));
				dao.updateLineOfCredit(map);
			}
		}*/
	}
	
	public void changeOrgOrderStatus(String paymentStatus, String billsId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("paymentStatus", paymentStatus);
		map.put("billsId", billsId);
		dao.changeOrgOrderStatus(map);
	}

	public Map<String, Object> getActualBalance(String organId, String leasesCompanyId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("organId", organId);
		map.put("leasesCompanyId", leasesCompanyId);
		BigDecimal actualBalance = dao.getActualBalanceById(map);
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		jsonObject.put("actualBalance", actualBalance);
		return jsonObject;
	}
	
	public OrgOrganBill getOrganBillById(String id) {
		return dao.getOrganBillById(id);
	}
	
	public Map<String, String> createCheckOrganBillState(String billsId) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "核对完成，等待机构核对");
		
		// 获取当前时间
		String time = getNowDate();
		// 3-待机构核对
		createOrganBillState("", billsId, "3", time);
		
		// 给机构端超管、财务管理员发送消息
		checkNews(billsId);
		
		return ret;
	}
	
	public void checkNews(String billsId) {
		OrgOrganBill orgOrganBill = getOrganBillById(billsId);
		StringBuffer content = new StringBuffer();
		content.append("（");
		content.append(orgOrganBill.getLeasesCompanyShortName());
		content.append("）");
		content.append(orgOrganBill.getName());
		content.append("已核对完成，请审核");
		
		List<String> userList = dao.getOrganUserIdByType(orgOrganBill.getOrganId());
		for (String userId : userList) {
			UserNewsParam userNewsParam = new UserNewsParam();
			userNewsParam.setUserNewsTbName(UserNewsParam.ORG_USERNEWS_TABNAME);
			UserNews userNews = new UserNews();
			String uuid = GUIDGenerator.newGUID();
			userNews.setId(uuid);
			userNews.setUserid(userId);
			userNews.setType(userNews.USER_NEWS_TYPE_OTHER);
			JSONObject json = new JSONObject();
			json.put("type", "6");
			json.put("title", "账单审核");
			json.put("content", content.toString());
			userNews.setContent(json.toString());
			userNews.setNewsstate(userNews.USER_NEWS_STATE_UNREAD);
			userNewsParam.setUserNews(userNews);
			createNews(userNewsParam);
		}
	}
	
	public void createNews(UserNewsParam userNewsParam) {
		templateHelper.dealRequestWithTokenCarserviceApiUrl("/UserNews/addUserNews", HttpMethod.POST, null,
				userNewsParam, JSONObject.class);
	}
	
	public List<OrgOrgan> getOrganList(String leasesCompanyId) {
		return dao.getOrganList(leasesCompanyId);
	}
	
	public PageBean getOrgOrderById(OrganBillQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrgOrder> list = getOrgOrderListById(queryParam);
		int iTotalRecords = getOrgOrderListCountById(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<OrgOrder> getOrgOrderListById(OrganBillQueryParam organBillQueryParam) {
    	return dao.getOrgOrderListById(organBillQueryParam);
    }
	
	public int getOrgOrderListCountById(OrganBillQueryParam organBillQueryParam) {
		return dao.getOrgOrderListCountById(organBillQueryParam);
	}
	
	public List<OrgOrganBill> getCurOrganBillListExport(OrganBillQueryParam organBillQueryParam) {
		return dao.getCurOrganBillListExport(organBillQueryParam);
	}

	public Map<String, String> createOrganBillAuto(OrgOrganBill orgOrganBill) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "已生成账单");
		
		String uuid = GUIDGenerator.newGUID();
		orgOrganBill.setId(uuid);
		
		// 0-月结，1-季结，2-手动结算
		//orgOrganBill.setSource("2");
		
		// 账单总额
		BigDecimal orderAmount = getOrderAmountByQuery(orgOrganBill);
		orgOrganBill.setMoney(orderAmount);
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		
		orgOrganBill.setCreateTime(date);
		orgOrganBill.setUpdateTime(date);
		// 插入数据到机构账单表
		dao.createOrganbill(orgOrganBill);
		
		// 插入数据到机构账单状态记录表
		// 1-生成账单
		String billState = "1";
		createOrganBillState(orgOrganBill.getRemark(), uuid, billState, time);
		// 新生成（2-待核对）   重新生成（9-重新生成）
		billState = "2";
		date.setSeconds(date.getSeconds() + 1);
		time=format.format(date);
		createOrganBillState(orgOrganBill.getRemark(), uuid, billState, time);
		
		// 插入数据到机构账单明细表
		createOrganBillDetailsAuto(orgOrganBill, uuid);
		
		return ret;
	}
	
	public void createOrganBillDetailsAuto(OrgOrganBill orgOrganBill, String billsId) {
		List<String> orderList = getOrderListByQuery(orgOrganBill);
		if (orderList != null && orderList.size() > 0) {
			for (String order : orderList) {
				Map<String, String> organBillDetails = new HashMap<String, String>();
				String uuid = GUIDGenerator.newGUID();
				organBillDetails.put("id", uuid);
				organBillDetails.put("billsId", billsId);
				organBillDetails.put("orderId", order);
				dao.createOrganBillDetails(organBillDetails);
				
				// 改变订单状态  2-结算中
				dao.changeOrderStatusToBalance(order);
			}
		}
	}
	
	public BigDecimal getOrderAmountByQuery(OrgOrganBill orgOrganBill) {
    	return dao.getOrderAmountByQuery(orgOrganBill);
    }
	
	public List<String> getOrderListByQuery(OrgOrganBill orgOrganBill) {
		return dao.getOrderListByQuery(orgOrganBill);
	}
	
	public List<Map<String, Object>> selectOrganList(String leasesCompanyId, String fullName, String specialState, String account) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("leasesCompanyId", leasesCompanyId);
		map.put("fullName", fullName);
		map.put("specialState", specialState);
    	map.put("account", account);
		
		return dao.selectOrganList(map);
	}
	
	public List<OrgOrder> getOrgOrderListExport(OrganBillQueryParam organBillQueryParam) {
		return dao.getOrgOrderListExport(organBillQueryParam);
	}

}

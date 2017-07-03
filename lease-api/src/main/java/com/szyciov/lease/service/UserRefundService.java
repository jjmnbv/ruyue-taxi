package com.szyciov.lease.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.entity.UserNews;
import com.szyciov.lease.dao.UserRefundDao;
import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.lease.entity.OrgUserRefund;
import com.szyciov.lease.param.UserRefundQueryParam;
import com.szyciov.message.UserMessage;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Service("userRefundService")
public class UserRefundService {
	private UserRefundDao dao;
	private TemplateHelper templateHelper = new TemplateHelper();

	@Resource(name = "UserRefundDao")
	public void setDao(UserRefundDao dao) {
		this.dao = dao;
	}
	
	public PageBean getOrgUserRefundByQuery(UserRefundQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrgUserRefund> list = getOrgUserRefundListByQuery(queryParam);
		int iTotalRecords = getOrgUserRefundListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<OrgUserRefund> getOrgUserRefundListByQuery(UserRefundQueryParam queryParam) {
    	return dao.getOrgUserRefundListByQuery(queryParam);
    }
	
	public int getOrgUserRefundListCountByQuery(UserRefundQueryParam queryParam) {
		return dao.getOrgUserRefundListCountByQuery(queryParam);
	}
	
	public void confirmRefund(OrgUserRefund orgUserRefund) {
		// 改变退款状态  0-待处理,1-已处理
		updateOrgUserRefund(orgUserRefund);
		
		// 根据退款记录中的userId获取该用户的最新userId
		String userID = getUserIdByUserId(orgUserRefund.getUserId());
		if (StringUtils.isNotBlank(userID) && !orgUserRefund.getUserId().equals(userID)) {
			orgUserRefund.setUserId(userID);
		}
		
		// 更改账户余额
		updateOrgUserAccount(orgUserRefund);
		
		// 添加用户消费记录
		createOrgUserExpenses(orgUserRefund);
	}
	
	public String getUserIdByUserId(String userId) {
		return dao.getUserIdByUserId(userId);
	}
	
	public void updateOrgUserRefund(OrgUserRefund orgUserRefund) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", orgUserRefund.getId());
		map.put("updater", orgUserRefund.getUpdater());
		dao.updateOrgUserRefund(map);
	}
	
	public void updateOrgUserAccount(OrgUserRefund orgUserRefund) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", orgUserRefund.getAmount());
		map.put("userId", orgUserRefund.getUserId());
		map.put("leasesCompanyId", orgUserRefund.getLeasesCompanyId());
		// 判断是否已经创建了账户
		if (checkOrgUserAccountExist(orgUserRefund) > 0) {
			dao.updateOrgUserAccount(map);
		} else {
			String uuid = GUIDGenerator.newGUID();
			map.put("id", uuid);
			dao.createOrgUserAccount(map);
		}
	}
	
	public int checkOrgUserAccountExist(OrgUserRefund orgUserRefund) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", orgUserRefund.getUserId());
		map.put("leasesCompanyId", orgUserRefund.getLeasesCompanyId());
    	return dao.checkOrgUserAccountExist(map);
    }

	public void createOrgUserExpenses(OrgUserRefund orgUserRefund) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", orgUserRefund.getUserId());
		map.put("leasesCompanyId", orgUserRefund.getLeasesCompanyId());
		BigDecimal balance = dao.getBalanceByUserId(map);

		OrgUserRefund userRefund = dao.getOrgUserRefundById(orgUserRefund.getId());
		String remark = "订单退款 订单号【" + userRefund.getOrderNo() + "】，退款原因：" + userRefund.getRemark();
		
		String uuid = GUIDGenerator.newGUID();
		OrgUserExpenses orgUserExpenses = new OrgUserExpenses();
		orgUserExpenses.setId(uuid);
		orgUserExpenses.setUserId(orgUserRefund.getUserId());
		orgUserExpenses.setLeasesCompanyId(orgUserRefund.getLeasesCompanyId());
		orgUserExpenses.setAmount(String.valueOf(orgUserRefund.getAmount()));
		orgUserExpenses.setBalance(String.valueOf(balance));
		orgUserExpenses.setRemark(remark);
		orgUserExpenses.setUpdater(orgUserRefund.getUpdater());
		// 交易类型(0-充值、1-订单支付、2-系统退款、3-提现)
		orgUserExpenses.setTradeType("2");
		// 明细类型(0-余额明细、1-交易明细)
		orgUserExpenses.setDetailType("0");
		
		// 添加用户消费记录
		dao.createOrgUserExpenses(orgUserExpenses);
		
		/*OrgUserNews orgUserNews = new OrgUserNews();
		uuid = GUIDGenerator.newGUID();
		orgUserNews.setId(uuid);
		orgUserNews.setUserId(orgUserRefund.getUserId());
		orgUserNews.setOrderNo(userRefund.getOrderNo());
		orgUserNews.setType("0");
		orgUserNews.setTitle("系统退款");
		JSONObject json = new JSONObject();
		json.put("type", "0");
		json.put("amount", orgUserRefund.getAmount());
		json.put("remark", userRefund.getRemark());
		json.put("orderno", userRefund.getOrderNo());
		orgUserNews.setContent(json.toString());
		orgUserNews.setNewsState("0");
		
		// 添加机构用户消息
		dao.createOrgUserNews(orgUserNews);*/

		UserNewsParam userNewsParam = new UserNewsParam();
		userNewsParam.setUserNewsTbName(UserNewsParam.ORG_USERNEWS_TABNAME);
		UserNews userNews = new UserNews();
		uuid = GUIDGenerator.newGUID();
		userNews.setId(uuid);
		userNews.setUserid(orgUserRefund.getUserId());
		userNews.setType(userNews.USER_NEWS_TYPE_ORDER);
		JSONObject json = new JSONObject();
		json.put("type", "26");
		json.put("title", "退款通知");
		json.put("content", "您收到一笔退款，请前往账户余额中查看！");
		json.put("amount", orgUserRefund.getAmount());
		json.put("remark", userRefund.getRemark());
		json.put("orderno", userRefund.getOrderNo());
		// 根据订单号获取订单信息
		OrgOrder orgOrder = getOrgOrderById(userRefund.getOrderNo());
		json.put("orderstatus", orgOrder.getOrderstatus());
		json.put("paymentstatus", orgOrder.getPaymentstatus());
		if (StringUtils.isNotBlank(orgOrder.getUserrate()) || StringUtils.isNotBlank(orgOrder.getUsercomment())) {
			json.put("hascomment", true);
		} else {
			json.put("hascomment", false);
		}
		userNews.setContent(json.toString());
		userNews.setNewsstate(userNews.USER_NEWS_STATE_UNREAD);
		userNewsParam.setUserNews(userNews);
		
		createNews(userNewsParam);
		
		// 订单复核后退款，发送短信
		// 获取平台信息
		Map<String, Object> leasesCompanyInfo = dao.getLeasesCompanyInfo(orgUserRefund.getLeasesCompanyId());
		// 获取下单人信息
		Map<String, Object> orgUserInfo = dao.getOrgUserInfo(orgUserRefund.getUserId());
		List<String> list = new ArrayList<String>();
		list.add(orgUserInfo.get("account").toString());
		//String content = SMSTempPropertyConfigurer.getSMSTemplate(
		//		"com.szyciov.lease.service.userrefundservice.reviewbackmoney", orgUserRefund.getAmount(),
		//		String.valueOf(leasesCompanyInfo.get("servicesphone")),
		//		String.valueOf(leasesCompanyInfo.get("shortname")));
		String content = SMSTempPropertyConfigurer.getSMSTemplate(
				"com.szyciov.lease.service.userrefundservice.reviewbackmoney", orgUserRefund.getAmount(),
				String.valueOf(leasesCompanyInfo.get("servicesphone")));
		UserMessage um = new UserMessage(list, content, UserMessage.ADDUSER);
		MessageUtil.sendMessage(um);
    }
	
	public void createNews(UserNewsParam userNewsParam) {
		templateHelper.dealRequestWithTokenCarserviceApiUrl("/UserNews/addUserNews", HttpMethod.POST, null,
				userNewsParam, JSONObject.class);
	}
	
	public OrgUserRefund getOrgUserRefundById(String id) {
		return dao.getOrgUserRefundById(id);
	}
	
	public OrgOrder getOrgOrderById(String orderNo) {
		return dao.getOrgOrderById(orderNo);
	}

}

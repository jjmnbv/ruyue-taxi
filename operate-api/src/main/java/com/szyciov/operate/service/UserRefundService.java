package com.szyciov.operate.service;

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
import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.PeUserRefund;
import com.szyciov.operate.dao.UserRefundDao;
import com.szyciov.param.QueryParam;
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
	
	public PageBean getPeUserRefundByQuery(QueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PeUserRefund> list = getPeUserRefundListByQuery(queryParam);
		int iTotalRecords = getPeUserRefundListCountByQuery(queryParam.getKey());
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<PeUserRefund> getPeUserRefundListByQuery(QueryParam queryParam) {
    	return dao.getPeUserRefundListByQuery(queryParam);
    }
	
	public int getPeUserRefundListCountByQuery(String key) {
		return dao.getPeUserRefundListCountByQuery(key);
	}
	
	public void confirmRefund(PeUserRefund peUserRefund) {
		// 改变退款状态  0-待处理,1-已处理
		updatePeUserRefund(peUserRefund);
		
		// 更改账户余额
		updateOpUserAccount(peUserRefund);
		
		// 添加用户消费记录
		createOpUserExpenses(peUserRefund);
	}
	
	public void updatePeUserRefund(PeUserRefund peUserRefund) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", peUserRefund.getId());
		map.put("updater", peUserRefund.getUpdater());
		dao.updatePeUserRefund(map);
	}
	
	public void updateOpUserAccount(PeUserRefund peUserRefund) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("amount", peUserRefund.getAmount());
		map.put("userId", peUserRefund.getUserId());
		// 判断是否已经创建了账户
		if (checkPeUserAccountExist(peUserRefund.getUserId()) > 0) {
			dao.updateOpUserAccount(map);
		} else {
			String uuid = GUIDGenerator.newGUID();
			map.put("id", uuid);
			dao.createPeUserAccount(map);
		}
	}
	
	public int checkPeUserAccountExist(String userId) {
    	return dao.checkPeUserAccountExist(userId);
    }

	public void createOpUserExpenses(PeUserRefund peUserRefund) {
		String uuid = GUIDGenerator.newGUID();
		BigDecimal balance = dao.getBalanceByUserId(peUserRefund.getUserId());

		PeUserRefund userRefund = dao.getPeUserRefundById(peUserRefund.getId());
		String remark = "订单退款 订单号【" + userRefund.getOrderNo() + "】，退款原因：" + userRefund.getRemark();
		
		OrgUserExpenses orgUserExpenses = new OrgUserExpenses();
		orgUserExpenses.setId(uuid);
		orgUserExpenses.setUserId(peUserRefund.getUserId());
		orgUserExpenses.setAmount(String.valueOf(peUserRefund.getAmount()));
		orgUserExpenses.setBalance(String.valueOf(balance));
		orgUserExpenses.setRemark(remark);
		orgUserExpenses.setUpdater(peUserRefund.getUpdater());
		// 交易类型(0-充值、1-订单支付、2-系统退款、3-提现)
		orgUserExpenses.setTradeType("2");
		// 明细类型(0-余额明细、1-交易明细)
		orgUserExpenses.setDetailType("0");
		
		// 添加用户消费记录
		dao.createOpUserExpenses(orgUserExpenses);
		
		/*OrgUserNews orgUserNews = new OrgUserNews();
		uuid = GUIDGenerator.newGUID();
		orgUserNews.setId(uuid);
		orgUserNews.setUserId(peUserRefund.getUserId());
		orgUserNews.setType("0");
		orgUserNews.setTitle("系统退款");
		JSONObject json = new JSONObject();
		json.put("type", "0");
		json.put("amount", peUserRefund.getAmount());
		json.put("remark", userRefund.getRemark());
		json.put("orderno", userRefund.getOrderNo());
		orgUserNews.setContent(json.toString());
		orgUserNews.setNewsState("0");
		
		// 添加机构用户消息
		dao.createPeUserNews(orgUserNews);*/
		
		UserNewsParam userNewsParam = new UserNewsParam();
		userNewsParam.setUserNewsTbName(UserNewsParam.PE_USERNEW_TABNAME);
		UserNews userNews = new UserNews();
		uuid = GUIDGenerator.newGUID();
		userNews.setId(uuid);
		userNews.setUserid(peUserRefund.getUserId());
		userNews.setType(userNews.USER_NEWS_TYPE_ORDER);
		JSONObject json = new JSONObject();
		json.put("type", "26");
		json.put("title", "退款通知");
		json.put("content", "您收到一笔退款，请前往账户余额中查看！");
		json.put("amount", peUserRefund.getAmount());
		json.put("remark", userRefund.getRemark());
		json.put("orderno", userRefund.getOrderNo());
		// 根据订单号获取订单信息
		OpOrder opOrder = getOpOrderById(userRefund.getOrderNo());
		json.put("orderstatus", opOrder.getOrderstatus());
		json.put("paymentstatus", opOrder.getPaymentstatus());
		if (StringUtils.isNotBlank(opOrder.getUserrate()) || StringUtils.isNotBlank(opOrder.getUsercomment())) {
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
		Map<String, Object> opPlatformInfo = dao.getOpPlatformInfo();
		// 获取下单人信息
		Map<String, Object> peUserInfo = dao.getPeUserInfo(peUserRefund.getUserId());
		List<String> list = new ArrayList<String>();
		list.add(peUserInfo.get("account").toString());
		//String content = SMSTempPropertyConfigurer.getSMSTemplate(
		//		"com.szyciov.operate.service.userrefundservice.reviewbackmoney", peUserRefund.getAmount(),
		//		String.valueOf(opPlatformInfo.get("servcietel")),
		//		String.valueOf(opPlatformInfo.get("companyshortname")));
		String content = SMSTempPropertyConfigurer.getSMSTemplate(
				"com.szyciov.operate.service.userrefundservice.reviewbackmoney", peUserRefund.getAmount(),
				String.valueOf(opPlatformInfo.get("servcietel")));
		UserMessage um = new UserMessage(list, content, UserMessage.ADDUSER);
		MessageUtil.sendMessage(um);
    }
	
	public void createNews(UserNewsParam userNewsParam) {
		templateHelper.dealRequestWithTokenCarserviceApiUrl("/UserNews/addUserNews", HttpMethod.POST, null,
				userNewsParam, JSONObject.class);
	}
	
	public PeUserRefund getPeUserRefundById(String id) {
		return dao.getPeUserRefundById(id);
	}
	
	public OpOrder getOpOrderById(String orderNo) {
		return dao.getOpOrderById(orderNo);
	}
}

package com.szyciov.organ.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import com.szyciov.entity.OrderSource4WithdrawNO;
import com.szyciov.entity.PubCouponDetail;
import com.szyciov.entity.UserNews;
import com.szyciov.enums.MessageTypeEnum;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.OrgOrganBill;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.OrgOrganExpenses;
import com.szyciov.lease.param.OrganAccountQueryParam;
import com.szyciov.lease.param.OrganBillQueryParam;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrganPaymentRecord;
import com.szyciov.org.entity.PubWithdraw;
import com.szyciov.org.param.FinancialManagementQueryParam;
import com.szyciov.organ.dao.FinancialManagementDao;
import com.szyciov.param.UserNewsParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.UNID;
import com.wx.DocFunc;
import com.wx.WXOrderUtil;

import net.sf.json.JSONObject;

@Service("FinancialManagementService")
public class FinancialManagementService {
	private FinancialManagementDao dao;
	private TemplateHelper templateHelper = new TemplateHelper();

	@Resource(name = "FinancialManagementDao")
	public void setDao(FinancialManagementDao dao) {
		this.dao = dao;
	}
	
	public List<OrgOrganCompanyRef> getAccountListByOrganId(String organId) {
    	return dao.getAccountListByOrganId(organId);
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
	
	public PageBean getOrganBillByQuery(FinancialManagementQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrgOrganBill> list = getOrganBillListByQuery(queryParam);
		int iTotalRecords = getOrganBillListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<OrgOrganBill> getOrganBillListByQuery(FinancialManagementQueryParam financialManagementQueryParam) {
		return dao.getOrganBillListByQuery(financialManagementQueryParam);
	}
	
	public int getOrganBillListCountByQuery(FinancialManagementQueryParam financialManagementQueryParam) {
		return dao.getOrganBillListCountByQuery(financialManagementQueryParam);
	}
	
	public OrgOrganBill getOrganBillById(String id) {
		return dao.getOrganBillById(id);
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
	
	public List<OrgOrder> getOrgOrderListExport(String billsId, String organId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("billsId", billsId);
		map.put("organId", organId);
		return dao.getOrgOrderListExport(map);
	}
	
	public List<LeLeasescompany> getLeasesCompanyListByOrgan(String organId) {
		return dao.getLeasesCompanyListByOrgan(organId);
	}
	
	public Map<String, String> updateOrganBillState(String billsId, String billValue, String comment) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "提交成功");
		
		Map<String, String> billMap = new HashMap<String, String>();
		String uuid = GUIDGenerator.newGUID();
		billMap.put("id", uuid);
		billMap.put("billsId", billsId);
		billMap.put("comment", comment);
		
		if ("0".equals(billValue)) {// 通过账单
			// 4-待机构付款
			billMap.put("billState", "4");
			createOrganBillState(billMap);
		} else if ("1".equals(billValue)) {// 退回账单
			// 5-机构退回账单
			billMap.put("billState", "5");
			createOrganBillState(billMap);
			
			Map<String, String> orderMap = new HashMap<String, String>();
			// 1-已支付  4-未结算
			orderMap.put("paymentStatus", "4");
			orderMap.put("billsId", billsId);
			changeOrgOrderStatus(orderMap);
			
			// 给租赁端财务管理员发送消息
			sendBackNews(billsId);
		}
		
		return ret;
	}

	public void createOrganBillState(Map<String, String> map) {
    	dao.createOrganBillState(map);
    }
	
	public void changeOrgOrderStatus(Map<String, String> map) {
		dao.changeOrgOrderStatus(map);
	}
	
	public void sendBackNews(String billsId) {
		OrgOrganBill orgOrganBill = dao.getBillById(billsId);
		StringBuffer content = new StringBuffer();
		content.append("（");
		content.append(orgOrganBill.getShortName());
		content.append("）");
		content.append(orgOrganBill.getName());
		content.append("有误已退回，请处理");
		
		List<String> userList = dao.getLeasesCompanyUserIdById(orgOrganBill.getLeasesCompanyId());
		for (String userId : userList) {
			UserNewsParam userNewsParam = new UserNewsParam();
			userNewsParam.setUserNewsTbName(UserNewsParam.LE_USERNEWS_TABNAME);
			UserNews userNews = new UserNews();
			String uuid = GUIDGenerator.newGUID();
			userNews.setId(uuid);
			userNews.setUserid(userId);
			userNews.setType(userNews.USER_NEWS_TYPE_OTHER);
			JSONObject json = new JSONObject();
			json.put("type", "9");
			json.put("title", "账单退回");
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
	
	public Map<String, Object> getActualBalance(String organId, String leasesCompanyId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("organId", organId);
		map.put("leasesCompanyId", leasesCompanyId);
		BigDecimal actualBalance = dao.getActualBalanceById(map);
		Map<String, Object> jsonObject = new HashMap<String, Object>();
		jsonObject.put("actualBalance", actualBalance);
		return jsonObject;
	}
	
	public Map<String, String> confirmAccount(OrgOrganBill orgOrganBill) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "提交成功");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("money", orgOrganBill.getMoney());
		map.put("organId", orgOrganBill.getOrganId());
		map.put("leasesCompanyId", orgOrganBill.getLeasesCompanyId());
		dao.reduceOrganAccount(map);

		Map<String, String> billMap = new HashMap<String, String>();
		String uuid = GUIDGenerator.newGUID();
		billMap.put("id", uuid);
		billMap.put("billsId", orgOrganBill.getId());
		billMap.put("comment", "");
		// 6-机构已付款
		billMap.put("billState", "6");
		// 获取当前时间
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		billMap.put("operationTime", time);
		billMap.put("createTime", time);
		billMap.put("updateTime", time);
		dao.createBalanceOrganBillState(billMap);
		//createOrganBillState(billMap);
		
		
		OrgOrganExpenses orgOrganExpenses = new OrgOrganExpenses();
		uuid = GUIDGenerator.newGUID();
		orgOrganExpenses.setId(uuid);
		orgOrganExpenses.setOrganId(orgOrganBill.getOrganId());
		orgOrganExpenses.setLeasesCompanyId(orgOrganBill.getLeasesCompanyId());
		// 2-账单结算扣款
		orgOrganExpenses.setType("2");
		orgOrganExpenses.setAmount(orgOrganBill.getMoney());
		orgOrganExpenses.setRemark("");
		orgOrganExpenses.setCreater(orgOrganBill.getUpdater());
		orgOrganExpenses.setUpdater(orgOrganBill.getUpdater());
		
		// 增加机构消费记录
		createOrganExpenses(orgOrganExpenses);
		
		// 给租赁端财务管理员发送消息
		confirmNews(orgOrganBill.getId());
		
		// 确认收款的操作
		confirmAccountBalance(orgOrganBill);
		
		return ret;
	}
	
	public void createOrganExpenses(OrgOrganExpenses orgOrganExpenses) {
		dao.createOrganExpenses(orgOrganExpenses);
	}
	
	public void confirmNews(String billsId) {
		OrgOrganBill orgOrganBill = dao.getBillById(billsId);
		StringBuffer content = new StringBuffer();
		content.append("（");
		content.append(orgOrganBill.getShortName());
		content.append("）");
		content.append(orgOrganBill.getName());
		content.append("无误已付款，请确认");
		
		List<String> userList = dao.getLeasesCompanyUserIdById(orgOrganBill.getLeasesCompanyId());
		for (String userId : userList) {
			UserNewsParam userNewsParam = new UserNewsParam();
			userNewsParam.setUserNewsTbName(UserNewsParam.LE_USERNEWS_TABNAME);
			UserNews userNews = new UserNews();
			String uuid = GUIDGenerator.newGUID();
			userNews.setId(uuid);
			userNews.setUserid(userId);
			userNews.setType(userNews.USER_NEWS_TYPE_OTHER);
			JSONObject json = new JSONObject();
			json.put("type", "10");
			json.put("title", "机构已付款");
			json.put("content", content.toString());
			userNews.setContent(json.toString());
			userNews.setNewsstate(userNews.USER_NEWS_STATE_UNREAD);
			userNewsParam.setUserNews(userNews);
			createNews(userNewsParam);
		}
	}
	
	public void confirmAccountBalance(OrgOrganBill orgOrganBill) {
		
		// 获取当前时间 + 1秒
		String time = getNowDate();
		// 7-已结算
		createBalanceOrganBillState("", orgOrganBill.getId(), "7", time);
		
		// 账单关联的订单状态都变为   3、已结算
		Map<String, String> orderMap = new HashMap<String, String>();
		// 3-已结算
		orderMap.put("paymentStatus", "3");
		orderMap.put("billsId", orgOrganBill.getId());
		changeOrgOrderStatus(orderMap);
		
		// 给机构端超管、财务管理员发送消息
		confirmNews2(orgOrganBill.getId());
		
		// 根据未结算账单的数量判断是否要更新信用额度和可用余额
		changeLineOfCredit(orgOrganBill);
	}
	
	public String getNowDate() {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date.setSeconds(date.getSeconds() + 1);
		String time=format.format(date);
		return time;
	}
	
	public void createBalanceOrganBillState(String comment, String billsId, String billState, String time) {
		String uuid = GUIDGenerator.newGUID();
		Map<String, String> organBillState = new HashMap<String, String>();
		organBillState.put("id", uuid);
		organBillState.put("billState", billState);
		organBillState.put("billsId", billsId);
		organBillState.put("comment", comment);
		organBillState.put("operationTime", time);
		organBillState.put("createTime", time);
		organBillState.put("updateTime", time);
		dao.createBalanceOrganBillState(organBillState);
	}
	
	public void confirmNews2(String billsId) {
		OrgOrganBill orgOrganBill = getOrganBillById(billsId);
		StringBuffer content = new StringBuffer();
		content.append("（");
		content.append(orgOrganBill.getLeasesCompanyShortName());
		content.append("）");
		content.append(orgOrganBill.getName());
		content.append("，已结清，请知悉");
		
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
			json.put("title", "账单结清确认");
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
				
				// 修改信用额度变更的生效时间
				updateCreditEffectiveTime(orgOrganBill, orgOrganCompanyRef);
			}
		}
	}
	
	public List<Map<String, Object>> getOrganBillStateById(String billsId) {
		List<Map<String, Object>> list = dao.getOrganBillStateById(billsId);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				Object operationTime = map.get("operationTime");
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
				operationTime = format.format(operationTime);
				map.replace("operationTime", operationTime);
			}
		}
		
		return list;
	}
	
	public void updateCreditEffectiveTime(OrgOrganBill orgOrganBill, OrgOrganCompanyRef orgOrganCompanyRef) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("leasesCompanyId", orgOrganBill.getLeasesCompanyId());
		map.put("organId", orgOrganBill.getOrganId());
		map.put("oldCredit", orgOrganCompanyRef.getLineOfCredit());
		map.put("currentCredit", orgOrganCompanyRef.getNextLineOfCredit());
		dao.updateCreditEffectiveTime(map);
	}
	
	public LeLeasescompany getLeasesCompanyById(String leasesCompanyId) {
		return dao.getLeasesCompanyById(leasesCompanyId);
	}
	
	public Map<String, Object> updatePaytype(Map<String,Object> payparam){
		Map<String, Object> map = new HashMap<String, Object>();
		String leasesCompanyId = (String) payparam.get("leasesCompanyId");
		String organId = (String) payparam.get("organId");
		double rechargeAmount = Double.parseDouble(payparam.get("rechargeAmount").toString());
		String paytype = (String) payparam.get("paytype");
		map.put("status", "fail");
		try{
			
			Map<String,Object> payinfo = dao.getPayInfo4LeByCompanyid(leasesCompanyId);
			if(payinfo==null){
				map.put("message", "收款方信息不存在");
				return map;
			}
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			//获取订单的交易号 (时间加上5位随机码)
			String out_trade_no = format.format(date)+UNID.getUNID();
			Map<String,Object> rechargeinfo = new HashMap<String,Object>();
			rechargeinfo.put("out_trade_no", out_trade_no);
			rechargeinfo.put("leasesCompanyId", leasesCompanyId);
			rechargeinfo.put("organId", organId);
			//微信是2，支付宝是1
			rechargeinfo.put("paymenttype", paytype);
			double orderamount = (double)rechargeAmount;
			rechargeinfo.put("amount", orderamount);
			if("2".equalsIgnoreCase(paytype)){
				String appid = (String) payinfo.get("wechatappid");
				String shno = (String) payinfo.get("wechatmerchantno");
				String md5key = (String) payinfo.get("wechatsecretkey");
				if(StringUtils.isBlank(appid)||StringUtils.isBlank(shno)||StringUtils.isBlank(md5key)){
					map.put("status", "fail");
					map.put("message","收款方信息不完整");
					return map;
				}

				String wxbody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxbody"))?"如约的士——机构充值":SystemConfig.getSystemProperty("wxbody");
//				String ordermoney = new java.text.DecimalFormat("#").format(orderamount*100);
				String ordermoney = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?"1":new java.text.DecimalFormat("#").format(orderamount*100);
				String ipadd = payparam.get("ipadd")!=null?(payparam.get("ipadd").toString().indexOf(':')>-1?"":(String)payparam.get("ipadd")):"";
				Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, shno, wxbody, out_trade_no, ordermoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/organ-api/FinancialManagement/DillWXPayed", "NATIVE",ipadd);
				Document doc = WXOrderUtil.createPreOrderInfo(prepayorderparam, WXOrderUtil.getSign(prepayorderparam, md5key));
				System.out.println(DocFunc.doc2String(doc));
				Map<String,String> res = WXOrderUtil.getPreOrderCodeUrl(DocFunc.doc2String(doc), "https://api.mch.weixin.qq.com/pay/unifiedorder");
				if(res!=null&&"0".equalsIgnoreCase(res.get("status"))){
					String code_url = res.get("code_url");
					map.put("code_url", code_url);
					map.put("status", "success");
					
					map.put("out_trade_no", out_trade_no);
				}
				
				rechargeinfo.put("privatekey", md5key);
			}else{
				String zfbsubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbsubject"))?"如约的士——机构充值":SystemConfig.getSystemProperty("zfbsubject");
				map.put("status", "success");
				map.put("out_trade_no", out_trade_no);
				map.put("notify_url", SystemConfig.getSystemProperty("paynotifyaddr")+"/organ-api/FinancialManagement/DillZFBPayed");
				map.put("partner", payinfo.get("alipaypartnerid"));
				map.put("subject", zfbsubject);
				orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
				map.put("total_fee", orderamount);
				map.put("partner_private_key", payinfo.get("alipaypartnerprivatekey"));
				
				rechargeinfo.put("privatekey", payinfo.get("alipaypartnerpublickey"));
			}
			rechargeinfo.put("operator", (String) payparam.get("operator"));
			//添加记录到数据库
			dao.addTradeNo4Organ(rechargeinfo);
		}catch(Exception e){
			map.put("message","支付异常");
			e.printStackTrace();
		}
		return map;
	};

	/**
	 * 微信支付结果通知
	 * @param req
	 * @param res
	 */
	public void dillWXPayed(HttpServletRequest request,HttpServletResponse response){
		//根据支付结果，更改订单状态，并且返回微信“success”
		response.setContentType("application/xml");
		String res = "SUCCESS";
		try{
			 try {
		            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		            DocumentBuilder builder=factory.newDocumentBuilder();  
		            Document doc = builder.parse(request.getInputStream()); 
	            	Node return_code = doc.getElementsByTagName("return_code").item(0);
	                if(return_code!=null){
	                    if("SUCCESS".equalsIgnoreCase(return_code.getFirstChild().getNodeValue())){
	                    	Node result_code = doc.getElementsByTagName("result_code").item(0);
	                    	if (result_code!=null) {
	                    		if ("SUCCESS".equalsIgnoreCase(result_code.getFirstChild().getNodeValue())) {
	                    			//检查签名是否有效
	    	    		            Node sign = doc.getElementsByTagName("sign").item(0);
	    	    		            boolean flag= isWXSignValid(sign.getFirstChild().getNodeValue());
	    	    		            if(flag){
	    	    		            	//attach存储订单号，根据订单号修改订单状态
	    				            	Node out_trade_no = doc.getElementsByTagName("out_trade_no").item(0);
	    		                    	String outtradeno = out_trade_no.getFirstChild().getNodeValue();
	    		                    	
	    		                    	// 根据outtradeno查找机构支付信息
	    	    		        		OrgOrganPaymentRecord orgOrganPaymentRecord = dao.getOrganPaymentRecord(outtradeno);
	    	    		            	// 0-成功，1-失败    判断是否已支付成功过
	    	    		        		if ("0".equals(orgOrganPaymentRecord.getOperateresult())) {
	    	    		        			return;
	    	    		        		}

	    		                    	Map<String,Object> tradeparam = new HashMap<String,Object>();
	    		                    	tradeparam.put("outtradeno", outtradeno);
	    		                    	tradeparam.put("tradeno", doc.getElementsByTagName("transaction_id").item(0).getFirstChild().getNodeValue());
	    		                    	tradeparam.put("paymenttype", "1");
	    		                    	dao.updateTradeInfo4Organ(tradeparam);
	    		                    	
	    		                    	Node total_fee = doc.getElementsByTagName("total_fee").item(0);
	    		                    	String totalfee = total_fee.getFirstChild().getNodeValue();
	    		                    	// 更改账户余额
	    		                    	updateOrganCompanyRef(outtradeno,"1",orgOrganPaymentRecord.getAmount());// 支付金额
	    	    		            }else{
	    	    		                //签名失败记录日志并且返回失败
	    	    		            	res = "FAIL";
	    	    		            }
	                    		} else {
	                    			//支付失败
	    	                        res = "FAIL";
	                    		}
	                    	} else {
	                    		//解析参数格式失败
	    	                    res = "FAIL";
	                    	}
	                    }else{
	                        //支付失败
	                        res = "FAIL";
	                    }
	                }else{
	                	//解析参数格式失败
	                    res = "FAIL";
	                }
		        } catch (Exception e) {
		        	//异常记录日志
		        	 res = "FAIL";
		        }
			 	writeMessge4WX(response, res);
		}catch(Exception e){
			//记录日志
			e.printStackTrace();
		}
    }
	
	/**
	 * 微信签名是否正确
	 * @param nodeValue
	 * @return
	 */
	private boolean isWXSignValid(String nodeValue) {
		//do校验签名
		return true;
	}
	
	private void writeMessge4WX(HttpServletResponse response, String res)
			throws ParserConfigurationException, IOException, Exception {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		DocumentBuilder builder=factory.newDocumentBuilder();  
		Document doc = builder.newDocument();
		Element root = doc.createElement("xml");
		Element return_code = doc.createElement("return_code");
		CDATASection return_codedata = doc.createCDATASection(res);
		return_code.appendChild(return_codedata);
		root.appendChild(return_code);
		Element return_msg = doc.createElement("return_msg");
		String msg = res.equalsIgnoreCase("SUCCESS")?"OK":"FAIL";
		CDATASection return_msgdata = doc.createCDATASection(msg);
		return_msg.appendChild(return_msgdata);
		root.appendChild(return_msg);
		doc.appendChild(root);
		response.getWriter().write(doc2String(doc));
	}
	
	private String doc2String(Document doc) throws Exception{
		TransformerFactory  tf  =  TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();    
        ByteArrayOutputStream  bos  =  new  ByteArrayOutputStream();   
        t.transform(new DOMSource(doc), new StreamResult(bos));    
        return bos.toString();
	}
	
	public void updateOrganCompanyRef(String outtradeno,String type,double total_fee) {
		// 根据outtradeno查找机构支付信息
		OrgOrganPaymentRecord orgOrganPaymentRecord = dao.getOrganPaymentRecord(outtradeno);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organId", orgOrganPaymentRecord.getOrganid());
		map.put("companyId", orgOrganPaymentRecord.getLeasescompanyid());
		map.put("rechargeAmount", total_fee);
		// 充值后改变余额
		dao.updateOrganCompanyRef(map);

		OrgOrganExpenses orgOrganExpenses = new OrgOrganExpenses();
		String uuid = GUIDGenerator.newGUID();
		orgOrganExpenses.setId(uuid);
		orgOrganExpenses.setOrganId(orgOrganPaymentRecord.getOrganid());
		orgOrganExpenses.setLeasesCompanyId(orgOrganPaymentRecord.getLeasescompanyid());
		// 0-企业充值，1-企业提现，2-账单结算扣款
		orgOrganExpenses.setType("0");
		orgOrganExpenses.setAmount(new BigDecimal(total_fee).setScale(2, BigDecimal.ROUND_DOWN));
		orgOrganExpenses.setRemark("");
		orgOrganExpenses.setCreater(orgOrganPaymentRecord.getCreater());
		orgOrganExpenses.setUpdater(orgOrganPaymentRecord.getCreater());
		// 0-车企，1-机构
		orgOrganExpenses.setOperatorType("1");
		
		// 增加机构消费记录
		createOrganExpenses(orgOrganExpenses);
		
		// 推送消息到租赁端的财务管理员和超管
		sendRechargeNews(total_fee,orgOrganPaymentRecord.getLeasescompanyid(),orgOrganPaymentRecord.getOrganid(),type);
	}
	
	public void sendRechargeNews(double amount,String leasesCompanyId,String organId,String type) {
		
		Map<String, Object> withdrawInfo = getWithdrawInfo(organId, leasesCompanyId);
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time=format.format(date);
		
		StringBuffer content = new StringBuffer();
		content.append("充值机构：");
		content.append(withdrawInfo.get("organshortname").toString());
		content.append("，充值金额：");
		content.append(amount);
		content.append("元，支付渠道：");
		if ("0".equals(type)) {
			content.append("支付宝支付");
		} else {
			content.append("微信支付");
		}
		content.append("，充值时间：");
		content.append(time);
		content.append("。");
		
		List<String> userList = dao.getLeasesCompanyUserById(leasesCompanyId);
		for (String userId : userList) {
			UserNewsParam userNewsParam = new UserNewsParam();
			userNewsParam.setUserNewsTbName(UserNewsParam.LE_USERNEWS_TABNAME);
			UserNews userNews = new UserNews();
			String uuid = GUIDGenerator.newGUID();
			userNews.setId(uuid);
			userNews.setUserid(userId);
			userNews.setType(userNews.USER_NEWS_TYPE_OTHER);
			JSONObject json = new JSONObject();
			json.put("type", MessageTypeEnum.FINANCIALMANAGEMENT_RECHARGE.code);
			json.put("title", "机构充值成功");
			json.put("content", content.toString());
			userNews.setContent(json.toString());
			userNews.setNewsstate(userNews.USER_NEWS_STATE_UNREAD);
			userNewsParam.setUserNews(userNews);
			createNews(userNewsParam);
		}
	}

	public void dillZFBPayed(HttpServletRequest request, HttpServletResponse response) {
		//根据支付结果，更改订单状态，并且返回支付宝“success”
		String res = "success";
		try{
			String result_code = request.getParameter("trade_status");
			if("TRADE_SUCCESS".equalsIgnoreCase(result_code)){
				//机构订单
				if(isZFBSignValid(request)){
					String out_trade_no = request.getParameter("out_trade_no");
					// 根据outtradeno查找机构支付信息
	        		OrgOrganPaymentRecord orgOrganPaymentRecord = dao.getOrganPaymentRecord(out_trade_no);
	            	// 0-成功，1-失败    判断是否已支付成功过
	        		if ("0".equals(orgOrganPaymentRecord.getOperateresult())) {
	        			return;
	        		}

					// 根据out_trade_no，修改支付状态和流水号
                	Map<String,Object> tradeparam = new HashMap<String,Object>();
                	tradeparam.put("outtradeno", out_trade_no);
                	tradeparam.put("tradeno", request.getParameter("trade_no"));
                	tradeparam.put("paymenttype", "0");
                	dao.updateTradeInfo4Organ(tradeparam);
                	
                	// 获取支付金额
                	String total_fee = request.getParameter("total_fee");
                	// 更改账户余额
                	updateOrganCompanyRef(out_trade_no,"0",orgOrganPaymentRecord.getAmount());//支付金额
				}else{
					//签名失败
					res = "failure";
				}
			}else{
				//交易失败，返回失败
				res = "failure";
			}
			response.getWriter().write(res);
		}catch(Exception e){
			//记录日志
		}
	}
	
	/**
	 * 验证支付宝签名正确与否
	 * @param type 0-"运管端","1"-租赁端
	 * @param sign
	 * @return
	 * @throws AlipayApiException 
	 */
	private boolean isZFBSignValid(HttpServletRequest request) throws AlipayApiException {
		Map<String,String> pp = new HashMap<String,String>();
		Enumeration<String> pnames = request.getParameterNames();
		while(pnames.hasMoreElements()){
			String pname = pnames.nextElement();
			pp.put(pname, request.getParameter(pname));
		}
		String out_trade_no = request.getParameter("out_trade_no");
		
		// 根据outtradeno查找机构支付信息
		OrgOrganPaymentRecord orgOrganPaymentRecord = dao.getOrganPaymentRecord(out_trade_no);
		String publickey = orgOrganPaymentRecord.getPrivatekey();
		return AlipaySignature.rsaCheckV1(pp, publickey, AlipayConfig.input_charset);
	}
	
	/**
	 * 检查订单的状态是否已支付
	 * @param params
	 * @return
	 */
	public Map<String, Object> checkPayState(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		String out_trade_no = (String) params.get("out_trade_no");
		// 从支付记录表中查找数据
		OrgOrganPaymentRecord orgOrganPaymentRecord = dao.getOrganPaymentRecord(out_trade_no);
		res.put("status", "success");
		if (orgOrganPaymentRecord != null) {
			if ("0".equals(orgOrganPaymentRecord.getOperateresult())) {
				res.put("payed", true);
			} else {
				res.put("payed", false);
			}
		} else {
			res.put("status", "success");
			res.put("payed", false);
		}
		return res;
	}
	
	public Map<String, Object> getWithdrawInfo(String organId, String leasesCompanyId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("organId", organId);
		map.put("leasesCompanyId", leasesCompanyId);
		return dao.getWithdrawInfo(map);
	}
	
	public Map<String, String> withdrawOrganAccount(OrgOrganExpenses org_organexpenses) {
		Map<String, String> ret = new HashMap<String, String>();
		
		Map<String, Object> withdrawInfo = getWithdrawInfo(org_organexpenses.getOrganId(), org_organexpenses.getLeasesCompanyId());
		if (withdrawInfo.get("creditcardnum") == null || StringUtils.isBlank(withdrawInfo.get("creditcardnum").toString()) || withdrawInfo.get("creditcardname") == null || StringUtils.isBlank(withdrawInfo.get("creditcardname").toString()) || withdrawInfo.get("bankname") == null || StringUtils.isBlank(withdrawInfo.get("bankname").toString())) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "提交失败");
			return ret;
		}

		if (Double.parseDouble(withdrawInfo.get("withdrawalamount").toString()) < org_organexpenses.getAmount().doubleValue()) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "提交失败");
			return ret;
		} 
		
		// 产生提现记录
		PubWithdraw pubWithdraw = new PubWithdraw();
		String withdrawid = GUIDGenerator.newGUID();
		pubWithdraw.setId(withdrawid);
		// 0-运管端，1-租赁端
		pubWithdraw.setPlatformtype("1");
		pubWithdraw.setLeasescompanyid(org_organexpenses.getLeasesCompanyId());
		// 0-乘客，1-司机，2-机构----0-机构用户，1-个人用户，2-司机，3-机构
		pubWithdraw.setUsertype("3");
		pubWithdraw.setUserid(org_organexpenses.getUpdater());
		pubWithdraw.setAmount(org_organexpenses.getAmount());
		// 设备唯一标识 ---提现编号
		//String withdrawno = GUIDGenerator.newGUID();
		String withdrawno = this.getPubWithDrawNo();
		pubWithdraw.setUuid(withdrawno);
		pubWithdraw.setCreditcardnum((String)withdrawInfo.get("creditcardnum"));
		pubWithdraw.setCreditcardname((String)withdrawInfo.get("creditcardname"));
		pubWithdraw.setBankname((String)withdrawInfo.get("bankname"));
		pubWithdraw.setCreater(org_organexpenses.getUpdater());
		pubWithdraw.setUpdater(org_organexpenses.getUpdater());
		dao.addPubWithdraw(pubWithdraw);

		// 修改账户余额
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organId", org_organexpenses.getOrganId());
		map.put("leasesCompanyId", org_organexpenses.getLeasesCompanyId());
		map.put("amount", org_organexpenses.getAmount());
		dao.withdrawOrganAccount(map);
		
		// 产生交易明细记录
		OrgOrganExpenses orgOrganExpenses = new OrgOrganExpenses();
		String uuid = GUIDGenerator.newGUID();
		orgOrganExpenses.setId(uuid);
		orgOrganExpenses.setOrganId(org_organexpenses.getOrganId());
		orgOrganExpenses.setLeasesCompanyId(org_organexpenses.getLeasesCompanyId());
		// 0-企业充值，1-企业提现，2-账单结算扣款
		orgOrganExpenses.setType("1");
		orgOrganExpenses.setAmount(org_organexpenses.getAmount());
		orgOrganExpenses.setRemark("提现申请成功，扣减余额");
		orgOrganExpenses.setCreater(org_organexpenses.getUpdater());
		orgOrganExpenses.setUpdater(org_organexpenses.getUpdater());
		// 0-车企，1-机构
		orgOrganExpenses.setOperatorType("1");
		// 0-成功，1-失败
		orgOrganExpenses.setOperateResult("0");
		// 增加机构消费记录
		createOrganExpenses(orgOrganExpenses);
		
		// 推送消息到租赁端的财务管理员和超管
		sendWithdrawNews(withdrawInfo.get("organshortname").toString(),org_organexpenses.getAmount(),org_organexpenses.getLeasesCompanyId());
		
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "提交成功");
		ret.put("WithdrawNo", withdrawid);
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public String getPubWithDrawNo() {
		Map<String, Object> resultMap = templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApi") + "/PubWithDraw/GetPubWithDrawNo", 
				HttpMethod.POST, null, OrderSource4WithdrawNO.ORGAN, Map.class);
		String uuid = (String) resultMap.get("pubWithDrawNo");
		return uuid;
	}
	
	public void sendWithdrawNews(String shortName,BigDecimal amount,String leasesCompanyId) {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time=format.format(date);
		
		StringBuffer content = new StringBuffer();
		content.append("申请机构：");
		content.append(shortName);
		content.append("，申请提现金额：");
		content.append(amount);
		content.append("元，申请提交时间：");
		content.append(time);
		content.append("，请尽快处理。");
		
		List<String> userList = dao.getLeasesCompanyUserById(leasesCompanyId);
		for (String userId : userList) {
			UserNewsParam userNewsParam = new UserNewsParam();
			userNewsParam.setUserNewsTbName(UserNewsParam.LE_USERNEWS_TABNAME);
			UserNews userNews = new UserNews();
			String uuid = GUIDGenerator.newGUID();
			userNews.setId(uuid);
			userNews.setUserid(userId);
			userNews.setType(userNews.USER_NEWS_TYPE_OTHER);
			JSONObject json = new JSONObject();
			json.put("type", MessageTypeEnum.FINANCIALMANAGEMENT_WITHDRAW.code);
			json.put("title", "机构提现申请");
			json.put("content", content.toString());
			userNews.setContent(json.toString());
			userNews.setNewsstate(userNews.USER_NEWS_STATE_UNREAD);
			userNewsParam.setUserNews(userNews);
			createNews(userNewsParam);
		}
	}
	
	public Map<String, Object> getPubWithdraw(String id) {
		return dao.getPubWithdraw(id);
	}
	public PageBean getPubCouponDetailByQuery(OrganAccountQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PubCouponDetail> list = dao.getPubCouponDetailList(queryParam);
		int iTotalRecords = dao.getPubCouponDetailListCount(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
}

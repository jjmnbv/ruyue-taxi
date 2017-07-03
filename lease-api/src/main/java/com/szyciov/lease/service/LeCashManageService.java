package com.szyciov.lease.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szyciov.lease.dao.LeCashManageDao;
import com.szyciov.lease.entity.LeCashManage;
import com.szyciov.lease.param.CashManageQueryParam;
import com.szyciov.message.CashMessage;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.AppMessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.PushObjFactory;
import com.szyciov.util.SMSTempPropertyConfigurer;

import cn.jpush.api.push.model.PushPayload;
import net.sf.json.JSONObject;

@Service("leCashManageService")
public class LeCashManageService {
	
	private static final Logger logger = Logger.getLogger(LeCashManageService.class);
	
	private LeCashManageDao leCashManageDao;
	@Resource(name="leCashManageDao")
	public void setLeCashManageDao(LeCashManageDao leCashManageDao) {
		this.leCashManageDao = leCashManageDao;
	}

	public List<Map<String, Object>> getAccounts(Map<String,String> params){
		return leCashManageDao.getAccounts(params);
	}

	public List<Map<String, Object>> getNames(Map<String, String> params) {
		return leCashManageDao.getNames(params);
	}

	public PageBean getCashByQuery(CashManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<LeCashManage> list = getCashListByQuery(queryParam);
		int iTotalRecords = getCashListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}

	private int getCashListCountByQuery(CashManageQueryParam queryParam) {
		return leCashManageDao.getCashListCountByQuery(queryParam);
	}

	private List<LeCashManage> getCashListByQuery(CashManageQueryParam queryParam) {
		return leCashManageDao.getCashListByQuery(queryParam);
	}

	@Transactional
	public synchronized Map<String, Object> cashReject(Map<String, String> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("status", "success");
		res.put("message", "更新数据成功");
		String processid = params.get("id");
		Map<String,Object> userinfo = leCashManageDao.getUserInfoByProcessId(processid);
		String processstatus = (String) userinfo.get("processstatus");
		if("1".equals(processstatus)){
			//已处理了，不用再处理，不然金额老是增加
			return res;
		}
		leCashManageDao.cashReject(params);
		String reson = params.get("reson");
		userinfo.put("oper", params.get("processuserid"));
		if(userinfo!=null){
			String usertype = (String) userinfo.get("usertype");
			String account = (String) userinfo.get("account");
			String userid = (String) userinfo.get("userid");
			String companyid = (String) userinfo.get("leasescompanyid");
			Map<String,Object> companyinfo = leCashManageDao.getCompanyById(companyid);
			String companyname = (String) companyinfo.get("shortname");
			String servicesphone = (String) companyinfo.get("servicesphone");
			String bankname = (String) userinfo.get("bankname");
			String creditcardnum = (String) userinfo.get("creditcardnum");
			Date applytimeobj = (Date)userinfo.get("applytime");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String applytime = format.format(applytimeobj);
			double amount = Double.parseDouble(String.valueOf(userinfo.get("amount")));
			userinfo.put("recodeid", GUIDGenerator.newGUID());
			userinfo.put("remark", "提现受理失败，返还提现申请金额");
			userinfo.put("detailtype", "0");
			userinfo.put("platformtype", "1");
			//添加系统消息
			Map<String,Object> newsparams = new HashMap<String,Object>();
			newsparams.put("id", GUIDGenerator.newGUID());
			newsparams.put("userid", userid);
			newsparams.put("type", "1");
			newsparams.put("newsstate","0");
			if("0".equalsIgnoreCase(usertype)){
				userinfo.put("expensetype", null);
				userinfo.put("tradetype", "3");
				userinfo.put("operateresult", "1");
				//乘客机构用户
				if(StringUtils.isNotBlank(account)){
					//恢复金额，添加记录
					leCashManageDao.recoverMoney4OrgUser(userinfo);
					leCashManageDao.addRecord4OrgUser(userinfo);
					//添加系统消息
					JSONObject newscontent = new JSONObject();
					newscontent.put("type", "32");
					newscontent.put("account4company", companyname);
					newscontent.put("amount", amount);
					newscontent.put("bankname", bankname);
					newscontent.put("creditcardnum", creditcardnum);
					newscontent.put("applytime", applytime);
					String processreason = "提现不予受理，如有疑问，请联系"+servicesphone;
					newscontent.put("processreason", processreason);
					newsparams.put("content",newscontent.toString());
					leCashManageDao.addNews4OrgUser(newsparams);
					//发送短信
					CashMessage message = new CashMessage();
					List<String> userids = new ArrayList<String>();
					userids.add(account);
					//String content = "【好约车】您的提现申请被驳回，提现金额：200元，申请时间：2017-3-9 15:20。如有疑问，请联系027-89655698（云创租赁）";
					//String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.send2passenger4Reject",amount+"元",applytime,servicesphone,companyname);
					String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.send2passenger4Reject",amount+"",applytime,servicesphone);
					message.setSMSParam(content);
					//发送系统推送
					List<String> tag_ands = new ArrayList<String>();
					tag_ands.add("0");
					message.setPushParam(AppMessageUtil.APPTYPE_PASSENGER, content, tag_ands, userids, PushObjFactory.HINT_CASHREJECT);
					MessageUtil.sendMessage(message);
				}
			}else if("2".equalsIgnoreCase(usertype)){
				userinfo.put("expensetype", "4");
				userinfo.put("tradetype", "2");
				userinfo.put("operateresult", "1");
				//司机
				if(StringUtils.isNotBlank(account)){
					//恢复金额，添加记录
					leCashManageDao.recoverMoney4Driver(userinfo);
					leCashManageDao.addRecord4Driver(userinfo);
					//添加系统消息
					JSONObject newscontent = new JSONObject();
					newscontent.put("type", "31");
					newscontent.put("account4company", companyname);
					newscontent.put("amount", amount);
					newscontent.put("bankname", bankname);
					newscontent.put("creditcardnum", creditcardnum);
					newscontent.put("applytime", applytime);
					newscontent.put("title", "提现通知");
					String processreason = "提现不予受理，如有疑问，请联系"+servicesphone;
					newscontent.put("remark", processreason);
					newsparams.put("content",newscontent.toString());
					leCashManageDao.addNews4Driver(newsparams);
					//发送短信
					CashMessage message = new CashMessage();
					List<String> userids = new ArrayList<String>();
					userids.add(account);
					//String content = "【好约车】您的提现申请被驳回，提现金额：200元，申请时间：2017-3-9 15:20。如有疑问，请联系027-89655698（云创租赁）";
					String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.send2driver4Reject",amount+"元",applytime,servicesphone);
					message.setSMSParam(content);
					//发送推送消息
					message.setPushParam(AppMessageUtil.APPTYPE_DRIVER, content, null, userids, PushObjFactory.HINT_CASHREJECT);
					MessageUtil.sendMessage(message);
				}
			}else if("3".equalsIgnoreCase(usertype)){
				Map<String,Object> rightorganid2companyid = leCashManageDao.getOrgan4Company(userinfo);
				if(rightorganid2companyid!=null){
					String organid = (String) rightorganid2companyid.get("organid");
					userinfo.put("leasescompanyid",rightorganid2companyid.get("companyid"));
					userinfo.put("organid",organid);
					userinfo.put("operateresult", "1");
					//恢复金额，添加记录
					leCashManageDao.recoverMoney4Org(userinfo);
					leCashManageDao.addRecord4Org(userinfo);
					//机构
					try{
						List<OrgUser> users = leCashManageDao.getSAC(organid);
						String content = "贵公司的提现申请被驳回，提现金额："+amount+"元，申请时间："+applytime+"，请注意查收。如有疑问，请联系"+servicesphone+"("+companyname+")。";
						//发送系统消息给超管和财务
						for(int i=0;i<users.size();i++){
							OrgUser user = users.get(i);
							Map<String,Object> param = new HashMap<String,Object>();
							param.put("id", GUIDGenerator.newGUID());
							param.put("userid", user.getId());
							param.put("type", "1");
							JSONObject jsoncontent = new JSONObject();
							jsoncontent.put("type", "33");
							jsoncontent.put("title", "提现驳回");
							jsoncontent.put("content", content);
							param.put("content", jsoncontent.toString());
							param.put("newsstate", "0");
							leCashManageDao.addNews2Org(param);
						}
					}catch(Exception e){
						logger.error(e);
					}
				}
			}
		}
		return res;
	}
	
	public Map<String, Object> cashOk(Map<String, String> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("status", "success");
		res.put("message", "更新数据成功");
		try{
			leCashManageDao.cashOk(params);
			//确认提现操作发送短信和推送给提现的人
			String processid = params.get("id");
			Map<String,Object> userinfo = leCashManageDao.getUserInfoByProcessId(processid);
			if(userinfo!=null){
				String usertype = (String) userinfo.get("usertype");
				String account = (String) userinfo.get("account");
				String userid = (String) userinfo.get("userid");
				String companyid = (String) userinfo.get("leasescompanyid");
				Map<String,Object> companyinfo = leCashManageDao.getCompanyById(companyid);
				String companyname = (String) companyinfo.get("shortname");
				String servicesphone = (String) companyinfo.get("servicesphone");
				String bankname = (String) userinfo.get("bankname");
				String creditcardnum = (String) userinfo.get("creditcardnum");
				Date applytimeobj = (Date)userinfo.get("applytime");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String applytime = format.format(applytimeobj);
				double amount = Double.parseDouble(String.valueOf(userinfo.get("amount")));
				//添加系统消息
				Map<String,Object> newsparams = new HashMap<String,Object>();
				newsparams.put("id", GUIDGenerator.newGUID());
				newsparams.put("userid", userid);
				newsparams.put("type", "1");
				newsparams.put("newsstate","0");
				if("0".equalsIgnoreCase(usertype)){
					//乘客机构用户
					if(StringUtils.isNotBlank(account)){
						//添加系统消息
						JSONObject newscontent = new JSONObject();
						newscontent.put("type", "29");
						newscontent.put("account4company", companyname);
						newscontent.put("amount", amount);
						newscontent.put("bankname", bankname);
						newscontent.put("creditcardnum", creditcardnum);
						newscontent.put("applytime", applytime);
						newsparams.put("content",newscontent.toString());
						leCashManageDao.addNews4OrgUser(newsparams);
						//发送短信
						CashMessage message = new CashMessage();
						List<String> userids = new ArrayList<String>();
						userids.add(account);
						//String content = "【好约车】您的提现申请被驳回，提现金额：200元，申请时间：2017-3-9 15:20。如有疑问，请联系027-89655698（云创租赁）";
						//String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.send2passenger4OK",amount+"",applytime,servicesphone,companyname);
						String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.send2passenger4OK",amount+"",applytime,servicesphone);
						message.setSMSParam(content);
						//发送系统推送
						List<String> tag_ands = new ArrayList<String>();
						tag_ands.add("0");
						message.setPushParam(AppMessageUtil.APPTYPE_PASSENGER, content, tag_ands, userids, PushObjFactory.HINT_CASHOK);
						MessageUtil.sendMessage(message);
					}
				}else if("2".equalsIgnoreCase(usertype)){
					//司机
					if(StringUtils.isNotBlank(account)){
						//添加系统消息
						JSONObject newscontent = new JSONObject();
						newscontent.put("type", "28");
						newscontent.put("account4company", companyname);
						newscontent.put("amount", amount);
						newscontent.put("bankname", bankname);
						newscontent.put("creditcardnum", creditcardnum);
						newscontent.put("applytime", applytime);
						newscontent.put("title", "提现通知");
						newscontent.put("remark", "提现成功");
						newsparams.put("content",newscontent.toString());
						leCashManageDao.addNews4Driver(newsparams);
						//发送短信
						CashMessage message = new CashMessage();
						List<String> userids = new ArrayList<String>();
						userids.add(account);
						//String content = "【好约车】您的提现申请已打款，提现金额：200元，申请时间：2017-3-9 15:20，请注意查收。如有疑问，请联系027-89655698（云创租赁）";
						//String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.send2driver4OK",amount+"元",applytime,servicesphone,companyname);
						String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.lease.service.send2driver4OK",amount+"元",applytime,servicesphone);
						message.setSMSParam(content);
						//发送系统推送
						message.setPushParam(AppMessageUtil.APPTYPE_DRIVER, content, null, userids, PushObjFactory.HINT_CASHOK);
						MessageUtil.sendMessage(message);
					}
				}else if("3".equalsIgnoreCase(usertype)){
					//机构
					try{
						Map<String,Object> rightorganid2companyid = leCashManageDao.getOrgan4Company(userinfo);
						String organid = (String) rightorganid2companyid.get("organid");
						String content = "贵单位的提现申请已打款，提现金额："+amount+"元，申请时间："+applytime+"，请注意查收。如有疑问，请联系"+servicesphone+"("+companyname+")。";
						List<OrgUser> users = leCashManageDao.getSAC(organid);
						//发送系统消息给超管和财务
						for(int i=0;i<users.size();i++){
							OrgUser user = users.get(i);
							Map<String,Object> param = new HashMap<String,Object>();
							param.put("id", GUIDGenerator.newGUID());
							param.put("userid", user.getId());
							param.put("type", "1");
							JSONObject jsoncontent = new JSONObject();
							jsoncontent.put("type", "30");
							jsoncontent.put("title", "提现成功");
							jsoncontent.put("content", content);
							param.put("content", jsoncontent.toString());
							param.put("newsstate", "0");
							leCashManageDao.addNews2Org(param);
						}
					}catch(Exception e){
						logger.error(e);
					}
				}
			}
		}catch(Exception e){
			res.put("status", "fail");
			res.put("message","更新数据库时异常");
		}
		return res;
	}
	
	public List<LeCashManage> getAllUnderCashData(Map<String, String> params) {
		return leCashManageDao.getAllUnderCashData(params);
	}

	public Map<String, Object> getExportDataCount(Map<String, String> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("status", "success");
		try{
			res.put("count",leCashManageDao.getExportDataCount(params));
		}catch(Exception e){
			res.put("status", "fail");
			res.put("message","判断导出数据时异常");
		}
		return res;
	}

}

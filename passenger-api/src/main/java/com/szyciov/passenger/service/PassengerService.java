package com.szyciov.passenger.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

import com.szyciov.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import com.alipay.util.OrderInfoUtil;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.entity.CancelParty;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.OrderCost;
import com.szyciov.entity.PayMethod;
import com.szyciov.entity.PlatformType;
import com.szyciov.entity.PubAdImage;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.CouponRuleTypeEnum;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.PeUser;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.passenger.Const;
import com.szyciov.passenger.dao.DictionaryDao;
import com.szyciov.passenger.dao.OpDao;
import com.szyciov.passenger.dao.OrderDao;
import com.szyciov.passenger.dao.OrgDao;
import com.szyciov.passenger.dao.UserDao;
import com.szyciov.passenger.entity.AccountRules;
import com.szyciov.passenger.entity.AirportAddr;
import com.szyciov.passenger.entity.DriverInfo;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.MostAddress;
import com.szyciov.passenger.entity.MostContact;
import com.szyciov.passenger.entity.Order4List;
import com.szyciov.passenger.entity.PassengerOrder;
import com.szyciov.passenger.entity.SysVersion;
import com.szyciov.passenger.entity.VehicleModels;
import com.szyciov.passenger.param.LoginParam;
import com.szyciov.passenger.param.RegisterParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.passenger.util.VelocityUtil;
import com.wx.DocFunc;
import com.wx.WXOrderUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("PassengerService")
public class PassengerService {
	private static final Logger logger = Logger.getLogger(PassengerService.class);
	
	private TemplateHelper4OrgApi orgapi = new TemplateHelper4OrgApi();
	
	private TemplateHelper4leaseApi leaseapi = new TemplateHelper4leaseApi();
	
	private TemplateHelper4CarServiceApi carserviceapi = new TemplateHelper4CarServiceApi();
	
	public final TemplateHelper templateHelper = new TemplateHelper();
	
	/**
	 * 字典相关的dao
	 */
	private DictionaryDao dicdao;
	
	/**
	 * 用户相关的dao
	 */
	private UserDao userdao;
	
	/**
	 * 订单相关dao
	 */
	private OrderDao orderdao;
	
	/**
	 * 机构相关的dao
	 */
	private OrgDao orgdao;
	
	/**
	 * 运营相关的dao
	 */
	private OpDao opdao;
	
	@Resource(name = "DictionaryDao")
	public void setDictionaryDao(DictionaryDao dicdao) {
		this.dicdao = dicdao;
	}
	
	@Resource(name = "UserDao")
	public void setUserDao(UserDao userdao) {
		this.userdao = userdao;
	}
	
	@Resource(name = "OrderDao")
	public void setOrderDao(OrderDao orderdao) {
		this.orderdao = orderdao;
	}
	
	@Resource(name = "OrgDao")
	public void setOrgDao(OrgDao orgdao) {
		this.orgdao = orgdao;
	}
	
	@Resource(name = "OpDao")
	public void setOpDao(OpDao opdao) {
		this.opdao = opdao;
	}
	
	/**
	 * 为返回结果加入统一的属性
	 * @param res
	 * @return
	 */
	private void addPubInfos(Map<String,Object> res){
		if(res==null){
			return ;
		}
		res.put("servertime", (new Date()).getTime());
	}
	
	/**
	 * 是否是机构用户
	 * @param usertoken
	 * @return
	 */
	private boolean isOrgUser(String usertoken){
		return UserTokenManager.ORGUSERTYPE.equals(Const.getUserInfo(usertoken).get("usertype"));
	}
	
	/**
	 * 获取广告页的地址
	 * @return
	 */
	public Map<String,Object> getAdvertisementPath(Map<String,Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			PubAdImage param = new PubAdImage();
			param.setApptype(PlatformType.PASSENGER.code);
			param.setImgtype("0");
			Object version = params.get("version");
			if(version!=null){
				param.setVersion((String)version);
			}
			
			JSONObject jsonobj = carserviceapi.dealRequestWithToken("/PubInfoApi/GetPubAdImage", HttpMethod.POST, null, param, JSONObject.class);
			if(jsonobj!=null&&jsonobj.getInt("status")==0){
				PubAdImage pubimginfo = StringUtil.parseJSONToBean(jsonobj.getJSONObject("adimage").toString(), PubAdImage.class);
				String guidepathstr = pubimginfo.getImgaddr();
				if(StringUtils.isNotBlank(guidepathstr)){
					String fileserver = SystemConfig.getSystemProperty("fileserver")+File.separator;
					res.put("adpath", fileserver+guidepathstr);
				}else{
					res.put("adpath", "");
				}
			}else{
				res.put("adpath", "");
			}
		}catch(Exception e){
			logger.error("获取引导页出错");
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		return res;
	}
	
	public Map<String,Object> getGuidePath(Map<String,Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			PubAdImage param = new PubAdImage();
			param.setApptype(PlatformType.PASSENGER.code);
			param.setImgtype("1");
			Object version = params.get("version");
			if(version!=null){
				param.setVersion((String)version);
			}
			List<String> guidepath = new ArrayList<String>();
			JSONObject jsonobj = carserviceapi.dealRequestWithToken("/PubInfoApi/GetPubAdImage", HttpMethod.POST, null, param, JSONObject.class);
			if(jsonobj!=null&&jsonobj.getInt("status")==0){
				PubAdImage pubimginfo = StringUtil.parseJSONToBean(jsonobj.getJSONObject("adimage").toString(), PubAdImage.class);
				String guidepathstr = pubimginfo.getImgaddr();
				if(StringUtils.isNotBlank(guidepathstr)){
					String[] tempguidepath =  guidepathstr.split(",");
					String fileserver = SystemConfig.getSystemProperty("fileserver")+File.separator;
					for(int i=0;i<tempguidepath.length;i++){
						guidepath.add(fileserver+tempguidepath[i]);
					}
				}
			}
			res.put("guidepath", guidepath);
		}catch(Exception e){
			logger.error("获取引导页出错");
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 获取验证码
	 * @param phone
	 * @param usertype 用户类型（0-机构用户，1-个人用户）
	 * @param smstype 短信类型（0-登录，1-修改密码，2-注册用户）
	 * @return
	 */
	public Map<String, Object> getVerificationCode(String phone,String usertype,String smstype) {
		Map<String, Object> res = new HashMap<String,Object>();
		if(phone==null||"".equalsIgnoreCase(phone)||usertype==null||"".equalsIgnoreCase(usertype)||smstype==null||"".equalsIgnoreCase(smstype)){
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", "参数不完整");
			return res;
		}
		try{
			String smscodeouttimestr = SystemConfig.getSystemProperty("smscodeouttime");
			int smscodeouttime = parseInt(smscodeouttimestr)<=0?5:parseInt(smscodeouttimestr);

			String smscodetimesintimestr = SystemConfig.getSystemProperty("smscodetimesintime");
			int smscodetimesintime = parseInt(smscodetimesintimestr)<=0?5:parseInt(smscodetimesintimestr);
			if("0".equals(smstype)){
				String value = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_LOGIN.code+phone);
				double gettimes = parseDouble(value);
				if(gettimes>=smscodetimesintime){
					//获取次数超限
					res.put("status", Retcode.EXCEPTION.code);
					res.put("message", "获取验证码次数超限");
					return res;
				}else{
					//没有超限要递增
					JedisUtil.getFlowNO(RedisKeyEnum.SMS_PASSENGER_LOGIN.code+phone);
					if(gettimes<=0){
						JedisUtil.expire(RedisKeyEnum.SMS_PASSENGER_LOGIN.code+phone,smscodeouttime*60);
					}
				}
			}else if("1".equals(smstype)){
				String value = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_CHANGEPWD.code+phone);
				double gettimes = parseDouble(value);
				if(gettimes>=smscodetimesintime){
					//获取次数超限
					res.put("status", Retcode.EXCEPTION.code);
					res.put("message", "获取验证码次数超限");
					return res;
				}else{
					//没有超限要递增
					JedisUtil.getFlowNO(RedisKeyEnum.SMS_PASSENGER_CHANGEPWD.code+phone);
					if(gettimes<=0){
						JedisUtil.expire(RedisKeyEnum.SMS_PASSENGER_CHANGEPWD.code+phone,smscodeouttime*60);
					}
				}
			}else{
				String value = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_REGISTER.code+phone);
				double gettimes = parseDouble(value);
				if(gettimes>=smscodetimesintime){
					//获取次数超限
					res.put("status", Retcode.EXCEPTION.code);
					res.put("message", "获取验证码次数超限");
					return res;
				}else{
					//没有超限要递增
					JedisUtil.getFlowNO(RedisKeyEnum.SMS_PASSENGER_REGISTER.code+phone);
					if(gettimes<=0){
						JedisUtil.expire(RedisKeyEnum.SMS_PASSENGER_REGISTER.code+phone,smscodeouttime*60);
					}
				}
			}
		}catch (Exception e){
			logger.error("redis控制获取验证码次数失败了",e);
		}


		//获取一串随机的短信验证码
		String smscode = SMSCodeUtil.getRandCode();
		Map<String,String> smscodeobj = new HashMap<String,String>();
		smscodeobj.put("smstype", smstype);
		smscodeobj.put("usertype", usertype);
		smscodeobj.put("username", phone);
		smscodeobj.put("smscode", smscode);
		if(userdao.hasSMSCode(smscodeobj)){
			userdao.updateSMSCode(smscodeobj);
		}else{
			smscodeobj.put("id", GUIDGenerator.newGUID());
			userdao.saveSMSCode(smscodeobj);
		}
		//发送短信到手机
		
//		String content = "验证码："+smscode+"，请在10分钟内输入，("+PlatformType.PASSENGER.msg+")";
		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.passenger.service.passengerservice.smscode", smscode);
		List<String> userids = new ArrayList<String>();
		userids.add(phone);
		UserMessage usermessage = new UserMessage(userids, content, UserMessage.GETSMSCODE);
		MessageUtil.sendMessage(usermessage);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

	private int parseInt(Object value){
		if(value==null||"".equalsIgnoreCase(String.valueOf(value))){
			return 0;
		}
		return Integer.parseInt(String.valueOf(value));
	}

	/**
	 * 验证短信验证码
	 * @param params
	 * @return
	 */
	public Map<String, Object> validateCode(Map<String,Object> params) {
		Map<String, Object> res = new HashMap<String,Object>();
		if(params==null){
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", "参数不完整");
			return res;
		}
		String phone = (String) params.get("phone");
		String verificationcode = (String) params.get("verificationcode");
		String usertype = (String) params.get("usertype");
		String smstype = (String) params.get("smstype");
		if(StringUtils.isBlank(phone)||StringUtils.isBlank(verificationcode)||StringUtils.isBlank(usertype)||StringUtils.isBlank(smstype)){
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", "参数不完整");
			return res;
		}
		try{
			String smscodeerrortimesstr = SystemConfig.getSystemProperty("smscodeerrortimes");
			int smscodeerrortimes = parseInt(smscodeerrortimesstr)<=0?5:parseInt(smscodeerrortimesstr);
			if("0".equals(smstype)){
				String errortimes = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code+phone);
				//redis错误次数是5，就直接返回false
				if(parseDouble(errortimes)>=smscodeerrortimes){
					res.put("status", Retcode.EXCEPTION.code);
					res.put("message", "错误次数超限!");
					return res;
				}
			}else if("1".equals(smstype)){
				String errortimes = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_CHANGEPWD_ERRORTIMES.code+phone);
				//redis错误次数是5，就直接返回false
				if(parseDouble(errortimes)>=smscodeerrortimes){
					res.put("status", Retcode.EXCEPTION.code);
					res.put("message", "错误次数超限!");
					return res;
				}
			}else{
				String errortimes = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone);
				//redis错误次数是5，就直接返回false
				if(parseDouble(errortimes)>=smscodeerrortimes){
					res.put("status", Retcode.EXCEPTION.code);
					res.put("message", "错误次数超限!");
					return res;
				}
			}
		}catch (Exception e){
			logger.error("获取错误次数",e);
		}
		//验证验证码的有效性
		Map<String,Object> dbsmsinfo = userdao.getSMSInfo(params);
		if(dbsmsinfo!=null){
			Date savetime = (Date)dbsmsinfo.get("updatetime");
			Date temptime = new Date(savetime.getTime() + (long)Const.SMSCODEVALITIME * 60 * 1000);
			Date currentime = new Date();
			if(currentime.after(temptime)){
				res.put("status", Retcode.SMSCODEOUTTIME.code);
				res.put("message", "验证码已失效，请重新获取验证码");
				return res;
			}
			if(!verificationcode.equals(dbsmsinfo.get("smscode"))){
				res.put("status", Retcode.SMSCODEINVALID.code);
				res.put("message", "请输入正确的验证码");
				try{
					String smscodeerrorouttimestr = SystemConfig.getSystemProperty("smscodeerrorouttime");
					int smscodeerrorouttime = parseInt(smscodeerrorouttimestr)<=0?5:parseInt(smscodeerrorouttimestr);
					if("0".equals(smstype)){
						String errortimes = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code+phone);
						JedisUtil.getFlowNO(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code+phone);
						if(parseDouble(errortimes)<=0){
							JedisUtil.expire(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code+phone,smscodeerrorouttime*60);
						}
					}else if("1".equals(smstype)){
						String errortimes = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_CHANGEPWD_ERRORTIMES.code+phone);
						JedisUtil.getFlowNO(RedisKeyEnum.SMS_PASSENGER_CHANGEPWD_ERRORTIMES.code+phone);
						if(parseDouble(errortimes)<=0){
							JedisUtil.expire(RedisKeyEnum.SMS_PASSENGER_CHANGEPWD_ERRORTIMES.code+phone,smscodeerrorouttime*60);
						}
					}else{
						String errortimes = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone);
						JedisUtil.getFlowNO(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone);
						if(parseDouble(errortimes)<=0){
							JedisUtil.expire(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone,smscodeerrorouttime*60);
						}
					}
				}catch (Exception e){
					logger.error("获取错误次数",e);
				}
				return res;
			}
			res.put("status",  Retcode.OK.code);
			res.put("message", Retcode.OK.msg);
			deleteSMSCode(phone, usertype, smstype);
			try{
				if("0".equals(smstype)){
					JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code+phone);
					JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_LOGIN.code+phone);
				}else if("1".equals(smstype)){
					JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_CHANGEPWD_ERRORTIMES.code+phone);
					JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_CHANGEPWD.code+phone);
				}else{
					JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone);
					JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_REGISTER.code+phone);
				}
			}catch (Exception e){
				logger.error("清除redis数据",e);
			}
		}else{
			res.put("status", Retcode.SMSCODEINVALID.code);
			res.put("message", "请输入正确的验证码");
		}
		
		return res;
	}

	/**
	 * 乘客端登录
	 * @param loginparam
	 * @return
	 */
	public Map<String, Object> login(LoginParam loginparam) {
		Map<String, Object> res = new HashMap<String,Object>();
		if(loginparam==null||loginparam.getLogintype()==null){
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", "参数不完整");
			return res;
		}
		if(Const.ORGUSER.equalsIgnoreCase(loginparam.getUsertype())){
			//处理机构用户登录
			return login4Org(loginparam);
		}else{
			//处理个人用户登录
			return login4Pe(loginparam);
		}
	}
	
	/**
	 * 机构用户登录
	 * @param loginparam
	 * @return
	 */
	private Map<String,Object> login4Org(LoginParam loginparam){
		Map<String, Object> res = new HashMap<String,Object>();
		//登录日志信息
		Map<String,Object> loginfo = getLogObj(loginparam);
		String account = loginparam.getPhone();
		String uuid = loginparam.getUuid();
		OrgUser orguser = userdao.getUser4Org(account);//orgapi.dealRequestWithToken("User/GetUserByAccount", HttpMethod.GET, null, loginparam, OrgUser.class);
		if(orguser==null){
			loginfo.put("loginstatus", Const.LOGINSTATUS_ERROR);
			//添加日志
			userdao.addLog4Org(loginfo);
			//机构用户不存在
			res.put("status", Retcode.USERNOTEXIST.code);
			res.put("message", Retcode.USERNOTEXIST.msg);
			return res;
		}
		loginfo.put("userid", orguser.getId());
		if(Const.PWDLOGIN.equalsIgnoreCase(loginparam.getLogintype())){
			//密码登录判断
			String dbpwd = orguser.getUserPassword();
			//密码解密
			String parampwd = loginparam.getValidatecode();
			if(Const.INTERFACE_V3_0_2.equals(loginparam.getVversion())){
				parampwd = RSAUtil.RSADecode(loginparam.getValidatecode());
			}
			if(!StringUtils.isNotBlank(dbpwd)||!PasswordEncoder.matches(parampwd, dbpwd)){
				//密码错误
				res.put("status", Retcode.PASSWORDWRONG.code);
				res.put("message", "手机号码或者密码错误");
				loginfo.put("loginstatus", Const.LOGINSTATUS_FAIL);
			}else{
				try{
					res.put("status", Retcode.OK.code);
					res.put("message", Retcode.OK.msg);
					addUserInfo(res,uuid,orguser);
					loginfo.put("loginstatus", Const.LOGINSTATUS_OK);
				}catch(Exception e){
					res.put("status", Retcode.EXCEPTION.code);
					res.put("message", Retcode.EXCEPTION.msg);
					logger.error("乘客端异常",e);
				}
				//触发登录优惠券发放
				try{
					//机构端一定有个人用户，所以先给运管登录触发然后在触发机构租赁公司的活动
					String city = loginparam.getCity();
					String citycode = dicdao.getCityNo(city);
					//触发优惠券
					//1）运管端登录触发优惠券发放活动
					Map<String,Object> opinfo = dicdao.getPayInfo4Op();
					String companyid = (String) opinfo.get("id");
					PeUser user = userdao.getUser4Op(account);
					if(user!=null){
						Map<String,Object> couponparams = new HashMap<String,Object>();
						couponparams.put("type", CouponRuleTypeEnum.ACTIVITY.value);
						couponparams.put("userType", CouponRuleTypeEnum.PERSONAL_USER.value);
						couponparams.put("companyId", companyid);
						couponparams.put("cityCode", citycode);
						couponparams.put("userId", user.getId());
						couponparams.put("version", "v3.0.1");
						couponparams.put("userPhone",user.getAccount());
						Const.grenerateCoupon(templateHelper, couponparams);
					}
					//2）租赁优惠券发放活动
					Map<String,Object> pp = new HashMap<String,Object>();
	    			pp.put("organid", orguser.getOrganId());
	    			List<String> tempcompanyids = userdao.getValiableCompanys(pp);
					if(tempcompanyids!=null){
						for(int i=0;i<tempcompanyids.size();i++){
							String lecompanyid = tempcompanyids.get(i);
							Map<String,Object> couponparams = new HashMap<String,Object>();
							couponparams.put("type", CouponRuleTypeEnum.ACTIVITY.value);
							couponparams.put("userType", CouponRuleTypeEnum.ORGAN_USER.value);
							couponparams.put("companyId", lecompanyid);
							couponparams.put("cityCode", citycode);
							couponparams.put("userId", orguser.getId());
							couponparams.put("version", "v3.0.1");
							Const.grenerateCoupon(templateHelper, couponparams);
						}
					}
				}catch(Exception e){
					logger.error("触发优惠券出错",e);
				}
			}
		}else{
			try{
				String smscodeerrortimesstr = SystemConfig.getSystemProperty("smscodeerrortimes");
				int smscodeerrortimes = parseInt(smscodeerrortimesstr)<=0?5:parseInt(smscodeerrortimesstr);
				String errortimes = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code+account);
				if(StringUtils.isNotBlank(errortimes)){
					//redis错误次数是5，就直接返回false
					if(parseDouble(errortimes)>=smscodeerrortimes){
						res.put("status", Retcode.FAILED.code);
						res.put("message", "错误次数超限!");
						return res;
					}
				}
			}catch (Exception e){
				logger.error("获取redis错误次数失败",e);
			}

			//验证码登录
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("phone", loginparam.getPhone());
			params.put("usertype", Const.USERTOKENTYPE_ORGUSER);
			params.put("smstype", Const.SMSTYPE_LOGIN);
			//验证码登录判断
			Map<String, Object> smsobj = userdao.getSMSInfo(params);
			if(smsobj==null){
				res.put("status", Retcode.SMSCODEINVALID.code);
				res.put("message", "请输入正确的验证码");
				loginfo.put("loginstatus", Const.LOGINSTATUS_FAIL);
			}else{
				String smscode = (String) smsobj.get("smscode");
				Date savetime = (Date)smsobj.get("updatetime");
				Date temptime = new Date(savetime.getTime() + (long)Const.SMSCODEVALITIME * 60 * 1000);
				Date currentime = new Date();
				if(smscode==null||!smscode.equals(loginparam.getValidatecode())){
					res.put("status", Retcode.SMSCODEINVALID.code);
					res.put("message", "请输入正确的验证码");
					loginfo.put("loginstatus", Const.LOGINSTATUS_FAIL);
					try{
						boolean shouldsetexpire = false;
						if(StringUtils.isBlank(JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code+account))){
							shouldsetexpire = true;
						}
						JedisUtil.getFlowNO(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code+account);
						if(shouldsetexpire){
							String smscodeerrorouttimestr = SystemConfig.getSystemProperty("smscodeerrorouttime");
							int smscodeerrorouttime = parseInt(smscodeerrorouttimestr)<=0?5:parseInt(smscodeerrorouttimestr);
							JedisUtil.expire(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code+account,smscodeerrorouttime*60);
						}
					}catch (Exception e){
						logger.error("记录redis错误次数失败",e);
					}
				}else if(currentime.after(temptime)){
					res.put("status", Retcode.SMSCODEOUTTIME.code);
					res.put("message", "验证码已失效，请重新获取验证码");
					loginfo.put("loginstatus", Const.LOGINSTATUS_FAIL);
				}else{
					try{
						res.put("status", Retcode.OK.code);
						res.put("message", Retcode.OK.msg);
						addUserInfo(res,uuid,orguser);
						deleteSMSCode(loginparam.getPhone(), Const.USERTOKENTYPE_ORGUSER, Const.SMSTYPE_LOGIN);
						loginfo.put("loginstatus", Const.LOGINSTATUS_OK);
						try{
							JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code+account);
							JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_LOGIN.code+account);
						}catch (Exception e){
							logger.error("清除redis数据",e);
						}
					}catch(Exception e){
						res.put("status", Retcode.EXCEPTION.code);
						res.put("message", Retcode.EXCEPTION.msg);
						logger.error("乘客端异常",e);
					}
					
					//触发登录优惠券发放
					try{
						//机构端一定有个人用户，所以先给运管登录触发然后在触发机构租赁公司的活动
						String city = loginparam.getCity();
						String citycode = dicdao.getCityNo(city);
						//触发优惠券
						//1）运管端登录触发优惠券发放活动
						Map<String,Object> opinfo = dicdao.getPayInfo4Op();
						String companyid = (String) opinfo.get("id");
						PeUser user = userdao.getUser4Op(account);
						if(user!=null){
							Map<String,Object> couponparams = new HashMap<String,Object>();
							couponparams.put("type", CouponRuleTypeEnum.ACTIVITY.value);
							couponparams.put("userType", CouponRuleTypeEnum.PERSONAL_USER.value);
							couponparams.put("companyId", companyid);
							couponparams.put("cityCode", citycode);
							couponparams.put("userId", user.getId());
							couponparams.put("version", "v3.0.1");
							Const.grenerateCoupon(templateHelper, couponparams);
						}
						//2）租赁优惠券发放活动
						Map<String,Object> pp = new HashMap<String,Object>();
		    			pp.put("organid", orguser.getOrganId());
		    			List<String> tempcompanyids = userdao.getValiableCompanys(pp);
						if(tempcompanyids!=null){
							for(int i=0;i<tempcompanyids.size();i++){
								String lecompanyid = tempcompanyids.get(i);
								Map<String,Object> couponparams = new HashMap<String,Object>();
								couponparams.put("type", CouponRuleTypeEnum.ACTIVITY.value);
								couponparams.put("userType", CouponRuleTypeEnum.ORGAN_USER.value);
								couponparams.put("companyId", lecompanyid);
								couponparams.put("cityCode", citycode);
								couponparams.put("userId", orguser.getId());
								couponparams.put("version", "v3.0.1");
								Const.grenerateCoupon(templateHelper, couponparams);
							}
						}
					}catch(Exception e){
						logger.error("触发优惠券出错",e);
					}
				}
			}
		}
		//记录日志
		userdao.addLog4Org(loginfo);
		return res;
	}
	
	/**
	 * 验证码使用完成后删除
	 * @param phone
	 * @param usertype
	 * @param smstype
	 */
	private void deleteSMSCode(String phone,String usertype,String smstype){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("phone", phone);
		params.put("usertype", usertype);
		params.put("smstype", smstype);
		userdao.deleteSMSCode(params);
	}

	private void addUserInfo(Map<String, Object> res,String uuid, OrgUser orguser) throws NoSuchAlgorithmException {
		String usertoken = UserTokenManager.createUserToken(UserTokenManager.ORGUSERTYPE,orguser.getAccount(), SystemConfig.getSystemProperty("securityKey"));
		Map<String,Object> pp = new HashMap<String,Object>();
		pp.put("userid", orguser.getId());
		pp.put("usertype", Const.USERTOKENTYPE_ORGUSER);
		Map<String,Object> dbusertokeninfo = userdao.getUserTokenByUserId(pp);
		Map<String,Object> tokeninfo = new HashMap<String,Object>();
		String shouldpushdow = null;
		if(dbusertokeninfo==null){
			tokeninfo.put("id", GUIDGenerator.newGUID());
		}else{
			String dbuuid = (String) dbusertokeninfo.get("uuid");
			if(StringUtils.isNotBlank(dbuuid)&&!dbuuid.equalsIgnoreCase(uuid)){
				shouldpushdow = (String) dbusertokeninfo.get("usertoken");
			}
		}
		
		tokeninfo.put("usertoken", usertoken);
		tokeninfo.put("userid", orguser.getId());
		tokeninfo.put("usertype", Const.USERTOKENTYPE_ORGUSER);
		tokeninfo.put("uuid", uuid);
		userdao.createOrUpdateUsertoken(tokeninfo);
		res.put("nickname", orguser.getNickName());
		//同步token到个人用户的token信息
		try{
			Map<String,Object> pep = new HashMap<String,Object>();
			PeUser peuser = userdao.getUser4Op(orguser.getAccount());
			pep.put("userid", peuser.getId());
			pep.put("usertype", Const.USERTOKENTYPE_PEUSER);
			Map<String,Object> dbpeusertokeninfo = userdao.getUserTokenByUserId(pep);
			Map<String,Object> petokeninfo = new HashMap<String,Object>();
			if(dbpeusertokeninfo==null){
				petokeninfo.put("id", GUIDGenerator.newGUID());
			}
			petokeninfo.put("usertoken", usertoken);
			petokeninfo.put("userid", peuser.getId());
			petokeninfo.put("usertype", Const.USERTOKENTYPE_PEUSER);
			petokeninfo.put("uuid", uuid);
			userdao.createOrUpdateUsertoken(petokeninfo);
			res.put("nickname", peuser.getNickname());
		}catch (Exception e){}

		byte[] usertokencode4 = Base64.decodeBase64(usertoken);
		String decodetoken = new String(usertokencode4);
		String timetag = decodetoken.substring(32, 49);
		List<String> tags = new ArrayList<String>();
		tags.add(timetag);
//		tags.add("0");
		res.put("tags", tags);
		res.put("usertoken", usertoken);
		res.put("name", orguser.getNickName());
		res.put("telphone", orguser.getAccount());
		res.put("sex", orguser.getSex());
		String imgpath = orguser.getHeadPortraitMax();
		if(StringUtils.isNotBlank(imgpath)){
			res.put("imgpath",SystemConfig.getSystemProperty("fileserver")+File.separator+imgpath);
		}
		//挤下线
		if(StringUtils.isNotBlank(shouldpushdow)){
			UserMessage pushdow = new UserMessage(shouldpushdow);
			MessageUtil.sendMessage(pushdow);
		}
	}
	
	private Map<String,Object> login4Pe(LoginParam loginparam){
		Map<String, Object> res = new HashMap<String,Object>();
		//登录日志信息
		Map<String,Object> loginfo = getLogObj(loginparam);
		String account = loginparam.getPhone();
		String uuid = loginparam.getUuid();
		PeUser peuser = userdao.getUser4Op(account);//opapi.dealRequestWithToken("User/GetUserByAccount", HttpMethod.GET, null, loginparam, PeUser.class);
		if(peuser==null){
			loginfo.put("loginstatus", Const.LOGINSTATUS_ERROR);
			//添加日志
			userdao.addLog4Pe(loginfo);
			//用户不存在
			res.put("status", Retcode.USERNOTEXIST.code);
			res.put("message", Retcode.USERNOTEXIST.msg);
			return res;
		}
		loginfo.put("userid", peuser.getId());
		if(Const.PWDLOGIN.equalsIgnoreCase(loginparam.getLogintype())){
			//密码登录判断
			String dbpwd = peuser.getUserpassword();
			String parampwd = loginparam.getValidatecode();
			if(Const.INTERFACE_V3_0_2.equals(loginparam.getVversion())){
				parampwd = RSAUtil.RSADecode(loginparam.getValidatecode());
			}
			if(!PasswordEncoder.matches(parampwd, dbpwd)){
				//密码错误
				res.put("status", Retcode.PASSWORDWRONG.code);
				res.put("message", "手机号码或者密码错误");
				loginfo.put("loginstatus", Const.LOGINSTATUS_FAIL);
			}else{
				try{
					res.put("status", Retcode.OK.code);
					res.put("message", Retcode.OK.msg);
					addUserInfo(res,uuid,peuser);
					loginfo.put("loginstatus", Const.LOGINSTATUS_OK);
				}catch(Exception e){
					res.put("status", Retcode.EXCEPTION.code);
					res.put("message", Retcode.EXCEPTION.msg);
					logger.error("乘客端异常",e);
				}
				try{
					Map<String,Object> opinfo = dicdao.getPayInfo4Op();
					String companyid = (String) opinfo.get("id");
					String city = loginparam.getCity();
					String citycode = dicdao.getCityNo(city);
					//触发优惠券
					Map<String,Object> couponparams = new HashMap<String,Object>();
					couponparams.put("type", CouponRuleTypeEnum.ACTIVITY.value);
					couponparams.put("userType", CouponRuleTypeEnum.PERSONAL_USER.value);
					couponparams.put("companyId", companyid);
					couponparams.put("cityCode", citycode);
					couponparams.put("userId", peuser.getId());
					couponparams.put("version", "v3.0.1");
					couponparams.put("userPhone",account);
					Const.grenerateCoupon(templateHelper, couponparams);
				}catch(Exception e){
					logger.error("触发优惠券出错",e);
				}
			}
		}else{
			try{
				String smscodeerrortimesstr = SystemConfig.getSystemProperty("smscodeerrortimes");
				int smscodeerrortimes = parseInt(smscodeerrortimesstr)<=0?5:parseInt(smscodeerrortimesstr);
				String errortimes = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code);
				if(StringUtils.isNotBlank(errortimes)){
					//redis错误次数是5，就直接返回false
					if(parseDouble(errortimes)>=smscodeerrortimes){
						res.put("status", Retcode.FAILED.code);
						res.put("message", "错误次数超限!");
						return res;
					}
				}
			}catch (Exception e){
				logger.error("获取redis错误次数失败",e);
			}
			//验证码登录
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("phone", account);
			params.put("usertype", Const.USERTOKENTYPE_PEUSER);
			params.put("smstype", Const.SMSTYPE_LOGIN);
			//验证码登录判断
			Map<String, Object> smsobj = userdao.getSMSInfo(params);
			if(smsobj==null){
				res.put("status", Retcode.SMSCODEINVALID.code);
				res.put("message", "请输入正确的验证码");
				loginfo.put("loginstatus", Const.LOGINSTATUS_FAIL);
			}else{
				String smscode = (String) smsobj.get("smscode");
				Date savetime = (Date)smsobj.get("updatetime");
				Date temptime = new Date(savetime.getTime() + (long)Const.SMSCODEVALITIME * 60 * 1000);
				Date currentime = new Date();
				if(smscode==null||!smscode.equals(loginparam.getValidatecode())){
					res.put("status", Retcode.SMSCODEINVALID.code);
					res.put("message", "请输入正确的验证码");
					loginfo.put("loginstatus", Const.LOGINSTATUS_FAIL);
					try{
						boolean shouldsetexpire = false;
						if(StringUtils.isBlank(JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code))){
							shouldsetexpire = true;
						}
						JedisUtil.getFlowNO(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code);
						if(shouldsetexpire){
							String smscodeerrorouttimestr = SystemConfig.getSystemProperty("smscodeerrorouttime");
							int smscodeerrorouttime = parseInt(smscodeerrorouttimestr)<=0?5:parseInt(smscodeerrorouttimestr);
							JedisUtil.expire(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code,smscodeerrorouttime*60);
						}
					}catch (Exception e){
						logger.error("记录redis错误次数失败",e);
					}
				}else if(currentime.after(temptime)){
					res.put("status", Retcode.SMSCODEOUTTIME.code);
					res.put("message", "验证码已失效，请重新获取验证码");
					loginfo.put("loginstatus", Const.LOGINSTATUS_FAIL);
				}else{
					try{
						res.put("status", Retcode.OK.code);
						res.put("message", Retcode.OK.msg);
						addUserInfo(res,uuid,peuser);
						deleteSMSCode(account, Const.USERTOKENTYPE_PEUSER, Const.SMSTYPE_LOGIN);
						loginfo.put("loginstatus", Const.LOGINSTATUS_OK);
						try{
							JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_LOGIN_ERRORTIMES.code+account);
							JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_LOGIN.code+account);
						}catch (Exception e){
							logger.error("清除redis数据",e);
						}
					}catch(Exception e){
						res.put("status", Retcode.EXCEPTION.code);
						res.put("message", Retcode.EXCEPTION.msg);
						logger.error("乘客端异常",e);
					}
					//触发登录优惠券发放
					try{
						Map<String,Object> opinfo = dicdao.getPayInfo4Op();
						String companyid = (String) opinfo.get("id");
						String city = loginparam.getCity();
						String citycode = dicdao.getCityNo(city);
						//触发优惠券
						Map<String,Object> couponparams = new HashMap<String,Object>();
						couponparams.put("type", CouponRuleTypeEnum.ACTIVITY.value);
						couponparams.put("userType", CouponRuleTypeEnum.PERSONAL_USER.value);
						couponparams.put("companyId", companyid);
						couponparams.put("cityCode", citycode);
						couponparams.put("userId", peuser.getId());
						couponparams.put("version", "v3.0.1");
						Const.grenerateCoupon(templateHelper, couponparams);
					}catch(Exception e){
						logger.error("触发优惠券出错",e);
					}
				}
			}
		}
		//记录日志
		userdao.addLog4Pe(loginfo);
		return res;
	}
	
	private void addUserInfo(Map<String, Object> res,String uuid, PeUser peuser) throws NoSuchAlgorithmException {
		String usertoken = UserTokenManager.createUserToken(UserTokenManager.PERSONNALUSER,peuser.getAccount(), SystemConfig.getSystemProperty("securityKey"));
		Map<String,Object> pp = new HashMap<String,Object>();
		pp.put("userid", peuser.getId());
		pp.put("usertype", Const.USERTOKENTYPE_PEUSER);
		Map<String,Object> dbusertokeninfo = userdao.getUserTokenByUserId(pp);
		Map<String,Object> tokeninfo = new HashMap<String,Object>();
		String shouldpushdow = null;
		if(dbusertokeninfo==null){
			tokeninfo.put("id", GUIDGenerator.newGUID());
		}else{
			String dbuuid = (String) dbusertokeninfo.get("uuid");
			if(StringUtils.isNotBlank(dbuuid)&&!dbuuid.equalsIgnoreCase(uuid)){
				//挤下线
				shouldpushdow =  (String) dbusertokeninfo.get("usertoken");
			}
		}
		tokeninfo.put("usertoken", usertoken);
		tokeninfo.put("userid", peuser.getId());
		tokeninfo.put("uuid", uuid);
		tokeninfo.put("usertype", Const.USERTOKENTYPE_PEUSER);
		res.put("name", peuser.getNickname());
		userdao.createOrUpdateUsertoken(tokeninfo);
		//同步token到机构用户的token信息
		try{
			Map<String,Object> pep = new HashMap<String,Object>();
			OrgUser orguser = userdao.getUser4Org(peuser.getAccount());
			if(orguser!=null){
				pep.put("userid", orguser.getId());
				pep.put("usertype", Const.USERTOKENTYPE_ORGUSER);
				Map<String,Object> dborgusertokeninfo = userdao.getUserTokenByUserId(pep);
				Map<String,Object> orgtokeninfo = new HashMap<String,Object>();
				if(dborgusertokeninfo==null){
					orgtokeninfo.put("id", GUIDGenerator.newGUID());
				}
				orgtokeninfo.put("usertoken", usertoken);
				orgtokeninfo.put("userid", orguser.getId());
				orgtokeninfo.put("usertype", Const.USERTOKENTYPE_ORGUSER);
				orgtokeninfo.put("uuid", uuid);
				res.put("name", orguser.getNickName());
				userdao.createOrUpdateUsertoken(orgtokeninfo);
			}
		}catch (Exception e){}

		byte[] usertokencode4 = Base64.decodeBase64(usertoken);
		String decodetoken = new String(usertokencode4);
		String timetag = decodetoken.substring(32, 49);
		List<String> tags = new ArrayList<String>();
		tags.add(timetag);
//		tags.add("1");
		res.put("tags", tags);
		res.put("usertoken", usertoken);
		res.put("nickname", peuser.getNickname());
		res.put("telphone", peuser.getAccount());
		res.put("sex", peuser.getSex());
		String imgpath = peuser.getHeadportraitmin();
		if(StringUtils.isNotBlank(imgpath)){
			res.put("imgpath",SystemConfig.getSystemProperty("fileserver")+File.separator+imgpath);
		}
		
		//挤下线
		if(StringUtils.isNotBlank(shouldpushdow)){
			UserMessage pushdow = new UserMessage(shouldpushdow);
			MessageUtil.sendMessage(pushdow);
		}
	}
	
	
	private Map<String,Object> getLogObj(LoginParam loginparam){
		Map<String,Object> loginlog = new HashMap<String,Object>();
		loginlog.put("id", GUIDGenerator.newGUID());
		loginlog.put("device", loginparam.getDevice());
		loginlog.put("version", loginparam.getVersion());
		loginlog.put("appversion", loginparam.getAppversion());
		loginlog.put("phonebrand", loginparam.getPhonebrand());
		loginlog.put("phonemodel", loginparam.getPhonemodel());
		loginlog.put("browserversion", loginparam.getBrowserversion());
		loginlog.put("browsertype", loginparam.getBrowsertype());
		return loginlog;
	}

	/**
	 * 注册验证手机号验证码
	 * @param params
	 * @return
	 */
	public Map<String, Object> register(Map<String,Object> params) {
		Map<String, Object> res = new HashMap<String,Object>();
		String phone = (String) params.get("phone");
		boolean hasregister = userdao.hasRegister(phone);
		if(hasregister){
			res.put("status", Retcode.USERALREADYEXIST.code);
			res.put("message", "该号码已注册");
			return res;
		}
		try{
			String smscodeerrortimesstr = SystemConfig.getSystemProperty("smscodeerrortimes");
			int smscodeerrortimes = parseInt(smscodeerrortimesstr)<=0?5:parseInt(smscodeerrortimesstr);
			String errortimes = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone);
			//redis错误次数是5，就直接返回false
			if(parseDouble(errortimes)>=smscodeerrortimes){
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message", "错误次数超限!");
				return res;
			}
		}catch (Exception e){
			logger.error("获取redis信息出错",e);
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("phone", phone);
		param.put("usertype", Const.USERTOKENTYPE_PEUSER);
		param.put("smstype", Const.SMSTYPE_REGISTER);
		Map<String, Object> smsobj = userdao.getSMSInfo(param);
		if(smsobj==null){
			res.put("status", Retcode.SMSCODEINVALID.code);
			res.put("message", Retcode.SMSCODEINVALID.msg);
		}else{
			String verificationcode = (String) params.get("verificationcode");
			String smscode = (String) smsobj.get("smscode");
			Date savetime = (Date)smsobj.get("updatetime");
			Date temptime = new Date(savetime.getTime() + (long)Const.SMSCODEVALITIME * 60 * 1000);
			Date currentime = new Date();
			if(currentime.after(temptime)){
				res.put("status", Retcode.SMSCODEOUTTIME.code);
				res.put("message", Retcode.SMSCODEOUTTIME.msg);
			}else if(smscode==null||!smscode.equals(verificationcode)){
				res.put("status", Retcode.SMSCODEINVALID.code);
				res.put("message", Retcode.SMSCODEINVALID.msg);
				try{
					String errortimes = JedisUtil.getString(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone);
					JedisUtil.getFlowNO(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone);
					if(parseDouble(errortimes)<=0){
						String smscodeerrorouttimestr = SystemConfig.getSystemProperty("smscodeerrorouttime");
						int smscodeerrorouttime = parseInt(smscodeerrorouttimestr)<=0?5:parseInt(smscodeerrorouttimestr);
						JedisUtil.expire(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone,smscodeerrorouttime*60);
					}
				}catch (Exception e){
					logger.error("获取redis出错");
				}
			}else{
				try{
					JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_REGISTER_ERRORTIMES.code+phone);
					JedisUtil.delKey(RedisKeyEnum.SMS_PASSENGER_REGISTER.code+phone);
				}catch (Exception e){
					logger.error("清除redis出错",e);
				}
				res.put("status", Retcode.OK.code);
				res.put("message", Retcode.OK.msg);
			}
		}
		return res;
	}

	/**
	 * 注册个人用户账户
	 * @param registerparam
	 * @return
	 */
	public Map<String, Object> doRegister(RegisterParam registerparam) {
		Map<String, Object> res = new HashMap<String,Object>();
		Map<String,Object> loginlog = new HashMap<String,Object>();
		loginlog.put("id", GUIDGenerator.newGUID());
		loginlog.put("device", registerparam.getDevice());
		loginlog.put("version", registerparam.getVersion());
		loginlog.put("appversion", registerparam.getAppversion());
		loginlog.put("phonebrand", registerparam.getPhonebrand());
		loginlog.put("phonemodel", registerparam.getPhonemodel());
		loginlog.put("browserversion", registerparam.getBrowserversion());
		loginlog.put("browsertype", registerparam.getBrowsertype());

		PeUser peuser = new PeUser();
		String phone = registerparam.getPhone();

		PeUser pp = userdao.getUser4Op(phone);
		if(pp!=null){
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", "此号码已注册");
			return res;
		}

		peuser.setAccount(phone);
		String userid = GUIDGenerator.newGUID();
		loginlog.put("userid", userid);
		peuser.setId(userid);
		String city = registerparam.getCity();
		String citycode = dicdao.getCityNo(city);
		peuser.setRegistercity(citycode);
		peuser.setUserpassword(registerparam.getPassword());
		encodePwd(peuser);
		try{
			peuser.setNickname("");
			userdao.registerPeUser(peuser);
			loginlog.put("loginstatus", Const.LOGINSTATUS_OK);
			res.put("status", Retcode.OK.code);
			res.put("message", Retcode.OK.msg);
//			String usertoken = UserTokenManager.createUserToken(UserTokenManager.PERSONNALUSER, phone, SystemConfig.getSystemProperty("securityKey"));
//			res.put("usertoken", usertoken);
//			res.put("nickname", peuser.getNickname());
//			res.put("telphone", peuser.getAccount());
//			String imgpath = peuser.getHeadportraitmin();
//			if(StringUtils.isNotBlank(imgpath)){
//				res.put("imgpath",SystemConfig.getSystemProperty("fileserver")+File.separator+imgpath);
//			}
//			byte[] usertokencode4 = Base64.decodeBase64(usertoken);
//			String decodetoken = new String(usertokencode4);
//			String timetag = decodetoken.substring(32, 49);
//			List<String> tags = new ArrayList<String>();
//			tags.add(timetag);
//			tags.add("1");
//			res.put("tags", tags);
			addUserInfo(res,registerparam.getUuid(), peuser);
		}catch(Exception e){
			loginlog.put("loginstatus", Const.LOGINSTATUS_ERROR);
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		userdao.addLog4Pe(loginlog);
		return res;
	}
	
	private void encodePwd(PeUser peuser){
		if(peuser==null){
			return ;
		}
		String pwd = peuser.getUserpassword();
		if(pwd==null||"".equalsIgnoreCase(pwd)){
			return ;
		}
		peuser.setUserpassword(PasswordEncoder.encode(pwd));
	}

	/**
	 * 获取正在服务中的订单
	 * @param usertoken
	 * @return
	 */
	public Map<String,Object> getServiceOder(String usertoken) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		Map<String,String> userinfo = Const.getUserInfo(usertoken);
		String account = userinfo.get("account");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account",account);
		params.put("orderstatus", OrderState.INSERVICE.state);
		PassengerOrder serviceorder = null;
		if(isOrgUser(usertoken)){
			//机构用户
			serviceorder = orderdao.getOrder4Org(params);
		}else{
			//个人用户
			serviceorder = orderdao.getOrder4Op(params);
			if(serviceorder!=null){
				if(serviceorder.getUsetype()==null){
					serviceorder.setUsetype("");
				}
			}
		}
		try{
			dillWithOrderInfo(serviceorder,usertoken);
		}catch(Exception e){
			logger.error(e.getMessage());
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			res.put("order", null);
			return res;
		}
		res.put("order", serviceorder);
		return res;
	}
	
	/**
	 * 获取正在服务中的订单
	 * @return
	 */
	private PassengerOrder getServiceOrder4Org(Map<String,Object> params){
		return orderdao.getServiceOrder4Org(params);
	}

	/**
	 * 获取可用的租赁公司
	 * @param usertoken
	 * @param city
	 * @return
	 */
	public Map<String,Object> getCompanys(String usertoken, String city) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		Map<String,String> userinfo = Const.getUserInfo(usertoken);
		String account = userinfo.get("account");
//		if(!isOrgUser(usertoken)){
//			//不是机构app用户，是没有租赁公司可以用的
//			res.put("status", Retcode.NOTORGANIZEDUSER.code);
//			res.put("message", Retcode.NOTORGANIZEDUSER.msg);
//			return res;
//		}
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("account", account);
//		params.put("city", city);
//		List<LeasesCompany> companys = userdao.getValiableLeasesCompanys(params);
//		res.put("status", Retcode.OK.code);
//		res.put("message", Retcode.OK.msg);
//		res.put("companys", companys);
		List<LeasesCompany> companys = null;
		if(isOrgUser(usertoken)){
			//机构用户获取租赁公司列表
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("account", account);
			params.put("city", city);
			companys = userdao.getValiableLeasesCompanys(params);
		}else{
			//个人用户获取加入toc的租赁公司列表
			companys = new ArrayList<LeasesCompany>();
			LeasesCompany all = new LeasesCompany();
			all.setId("");
			all.setName("全部");
			companys.add(all);
			List<LeasesCompany> tempcompanys = userdao.getTocCompanys();
			if(tempcompanys!=null){
				companys.addAll(tempcompanys);
			}
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("companys", companys);
		return res;
	}

	/**
	 * 获取未支付的订单信息
	 * @param usertoken
	 * @param companyid
	 * @return
	 */
	public Map<String,Object> getUnpayOders(String usertoken, String companyid) {
		long start = System.currentTimeMillis();
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		Map<String,String> userinfo = Const.getUserInfo(usertoken);
		String account = userinfo.get("account");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("orderstatus",OrderState.SERVICEDONE.state);
		params.put("paymentstatus",PayState.NOTPAY.state);
		PassengerOrder unpayorder = null;
		if(isOrgUser(usertoken)){
			//机构用户
//			params.put("companyid", companyid);
			unpayorder = orderdao.getOrder4Org(params);
		}else{
			//个人用户
			unpayorder = orderdao.getOrder4Op(params);
		}
		try{
			dillWithOrderInfo(unpayorder,usertoken);
		}catch(Exception e){
			logger.error(e.getMessage());
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			res.put("order", null);
			return res;
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("order", unpayorder);
		long end = System.currentTimeMillis();
		System.out.println("*******************************************************"+(end-start));
		return res;
	}

	/**
	 * 获取当前城市的租赁公司提供的业务
	 * @param params
	 * @return
	 */
	public Map<String,Object> getBusiness(Map<String,Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String usertoken = (String) params.get("usertoken");
		String companyid = (String) params.get("companyid");
		String city = (String) params.get("city");
		List<Map<String,Object>> business = null;
		Map<String,Object> sparam = new HashMap<String,Object>();
		sparam.put("city", city);
		if(isOrgUser(usertoken)){
			String usetype = (String) params.get("usetype");
			sparam.put("companyid", companyid);
			//机构用户用车
			if(Const.USETYPE_PRIVATE.equals(usetype)){
				//因私用车
				sparam.put("type", "0");
				business = orgdao.getCompanyCityBusiness(sparam);
			}else{
				//因公用车
				String account = Const.getUserInfo(usertoken).get("account");
				OrgOrgan organ = orgdao.getOrgInfo(account);
				if(organ!=null&&organ.getStatus()!=0){
					sparam.put("type", "1");
					sparam.put("account", account);
					List<Map<String,Object>> tempbusiness1 = orgdao.getCompanyCityBusiness(sparam);
					//在根据个人用车规则过滤
					Map<String,Object> ruleparam = new HashMap<String,Object>();
					ruleparam.put("account", account);
					ruleparam.put("organid", organ.getId());
					ruleparam.put("companyid", companyid);
					List<Map<String,Object>> tempbusiness2 = orgdao.getCCBusiness4UserRules(ruleparam);
					//取交集
					business = new ArrayList<Map<String,Object>>();
					List<String> tempkey = new ArrayList<String>();
					if(tempbusiness1!=null){
						for(int i=0;i<tempbusiness1.size();i++){
							tempkey.add((String) tempbusiness1.get(i).get("type"));
						}
						for(int i=0;i<tempbusiness2.size();i++){
							Map<String,Object> busobj = tempbusiness2.get(i);
							if(tempkey.contains(busobj.get("type"))){
								business.add(busobj);
							}
						}
					}
				}
			}
		}else{
			//个人用户用车
			business = opdao.getBusiness(sparam);
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("business", business);
		return res;
	}

	/**
	 * 获取车型信息
	 * @param params
	 * @return
	 */
	public Map<String,Object> getCarMoudels(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		addPubInfos(res);
		String usertoken = (String) params.get("usertoken");
		String city = (String) params.get("city");
		//规则类型就是订单中的订单类型1约车2接机3送机
		String ordertype = (String) params.get("ordertype");
		List<VehicleModels> carmodels = null;
		if(isOrgUser(usertoken)){
			String companyid = (String) params.get("companyid");
			//机构用户
			String usetype = (String) params.get("usetype");
			//机构用户用车
			if(Const.USETYPE_PRIVATE.equals(usetype)){
				//因私用车
				Map<String,Object> sparam = new HashMap<String,Object>();
				sparam.put("companyid", companyid);
				sparam.put("city", city);
				sparam.put("type", "0");
				sparam.put("ordertype", ordertype);
				carmodels = orgdao.getCarMoudels(sparam);
				if(carmodels==null||carmodels.size()<=0){
					//没有服务
					res.put("message","当前租赁公司当前城市没有用车服务");
				}
			}else{
				//因公用车
				String account = Const.getUserInfo(usertoken).get("account");
				OrgOrgan organ = orgdao.getOrgInfo(account);
				if(organ!=null&&organ.getStatus()!=0){
					Map<String,Object> sparam = new HashMap<String,Object>();
					sparam.put("organid", organ.getId());
					sparam.put("companyid", companyid);
					sparam.put("city", city);
					sparam.put("type", "1");
					sparam.put("ordertype", ordertype);
					List<VehicleModels> tempcarmodels = orgdao.getCarMoudels(sparam);
					if(tempcarmodels==null||tempcarmodels.size()<=0){
						//没有服务
						res.put("message","当前租赁公司当前城市没有用车服务");
					}else{
						//在根据个人用车规则过滤
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("companyid", companyid);
						param.put("ordertype", ordertype);
						param.put("organid", organ.getId());
						param.put("account", Const.getUserInfo(usertoken).get("account"));
						List<String> vehiclemodels = orgdao.getOrgUsercar4Rules(param);
						if(tempcarmodels!=null){
							carmodels = new ArrayList<VehicleModels>();
							for(int i=0;i<tempcarmodels.size();i++){
								VehicleModels ve = tempcarmodels.get(i);
								if(vehiclemodels.contains(ve.getId())){
									carmodels.add(ve);
								}
							}
							if(carmodels==null||carmodels.size()<=0){
								//没有权限
								res.put("message","您在当前城市、当前租赁公司没有用车权限");
							}
						}
					}
				}
			}
		}else{
			//个人用户
			Map<String,Object> sparam = new HashMap<String,Object>();
			sparam.put("city", city);
			sparam.put("ordertype", ordertype);
			carmodels = opdao.getCarMoudels(sparam);
			if(carmodels==null||carmodels.size()<=0){
				//没有服务
				res.put("message","当前城市没有用车服务");
			}
		}
		dillWithCarMoudelsLogo(carmodels);
		res.put("carmodels", carmodels);
		return res;
	}
	
	/**
	 * 为车型logo加上绝对地址
	 * @param carmodels
	 */
	private void dillWithCarMoudelsLogo(List<VehicleModels> carmodels){
		if(carmodels==null){
			return ;
		}
		for(int i=0;i<carmodels.size();i++){
			VehicleModels ve = carmodels.get(i);
			String logo = ve.getLogo();
			if(StringUtils.isNotBlank(logo)){
				ve.setLogo(SystemConfig.getSystemProperty("fileserver")+File.separator+logo);
			}
		}
	}

	/**
	 * 获取常用联系人
	 * @param usertoken
	 * @return
	 */
	public Map<String,Object> getMostContact(String usertoken) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String account = Const.getUserInfo(usertoken).get("account");
		List<MostContact> mostcontact = null;
		if(isOrgUser(usertoken)){
			//机构用户
			mostcontact = userdao.getMostContact4Org(account);
		}else{
			//个人用户
			mostcontact = userdao.getMostContact4Op(account);
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("mostcontact", mostcontact);
		return res;
	}

	/**
	 * 获取预估费用
	 * @param params
	 * @return
	 */
	public Map<String,Object> getEstimatedCost(Map<String, Object> params) {
		Map<String,Object> ress = new HashMap<String,Object>();
		JSONObject obj = new JSONObject();
		ress.put("costinfo", obj);
		String usertoken = (String) params.get("usertoken");
		String companyid = (String) params.get("companyid");
		String cityvalue = dicdao.getCityNo((String) params.get("city"));
		params.put("city", cityvalue);
		JSONObject costinfo = null;
		double cost = 0;
		if(isOrgUser(usertoken)){
			//机构用户的订单
			params.put("orderprop", "0");
			//获取预估费用的时候，预估费用需要传递rulestype 0标准1个性化
			if(Const.USETYPE_PUBLIC.equalsIgnoreCase((String)params.get("usetype"))){
				params.put("rulestype", "1");
			}else{
				params.put("rulestype", "0");
			}
			
			//现获取机构在租赁公司的可用额度
			OrgUser orguser = userdao.getUser4Org(Const.getUserInfo(usertoken).get("account"));
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("companyid",companyid);
			param.put("orgid", orguser.getOrganId());
			double balance = (double) userdao.getOrgBalance(param);
			try{
				params.put("userid", orguser.getId());
				costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, usertoken, params, JSONObject.class);
			}catch(Exception e){
				logger.error("乘客端异常",e);
			}
			if(Const.USETYPE_PUBLIC.equalsIgnoreCase((String)params.get("usetype"))&&costinfo!=null&&costinfo.getInt("status")==0){
				//判断支付方式有哪些可用
				JSONArray paytypes = new JSONArray();
				JSONObject obj1 = new JSONObject();
				obj1.put("paymethod", "1");
				obj1.put("payname", "个人垫付");
				paytypes.add(obj1);
				
				String coststr = (String) costinfo.get("cost");
				cost = Double.parseDouble(coststr.substring(0, coststr.length()-1));
				JSONObject obj2 = new JSONObject();
				obj2.put("paymethod", 2);
				obj2.put("payname", "机构支付");
				if(balance<cost){
					//不能机构支付
					obj2.put("able", "false");
				}else{
					obj2.put("able", "true");
				}
				paytypes.add(obj2);
				obj.put("paytypes",paytypes);
			}
		}else{
			//个人用户的订单
			params.put("orderprop", "1");
			//因私用车
			params.put("rulestype", "0");
			PeUser peuser = userdao.getUser4Op(Const.getUserInfo(usertoken).get("account"));
			try{
				params.put("userid", peuser.getId());
				costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, usertoken, params, JSONObject.class);
			}catch(Exception e){
				logger.error("乘客端异常",e);
			}
		}
		//估价明细
		if(costinfo!=null&&costinfo.getInt("status")==0){
			String coststr = (String) costinfo.get("cost");
			cost = Double.parseDouble(coststr.substring(0, coststr.length()-1));
			obj.put("cost", cost);
			
			JSONArray paydetail = new JSONArray();
			JSONObject obj3 = new JSONObject();
			obj3.put("name", "起步价");
			obj3.put("price", costinfo.get("startprice"));
			JSONObject obj4 = new JSONObject();
			obj4.put("name", "里程("+costinfo.get("mileage")+")");
			obj4.put("price", costinfo.get("rangecost"));
			JSONObject obj5 = new JSONObject();
			obj5.put("name", "时长费("+costinfo.get("times")+")");
			obj5.put("price", costinfo.get("timecost"));
			paydetail.add(obj3);
			paydetail.add(obj4);
			paydetail.add(obj5);
			obj.put("paydetail",paydetail);
			ress.put("status", Retcode.OK.code);
			ress.put("message", Retcode.OK.msg);
		}else{
			return costinfo;
		}
		return ress;
	}

	/**
	 * 获取机场地址
	 * @param usertoken
	 * @param city
	 * @return
	 */
	public Map<String,Object> getAirportAddrt(String usertoken, String city) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		List<AirportAddr> airportaddrs = dicdao.getAirportAddrt(city);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("airportaddrs", airportaddrs);
		return res;
	}

	/**
	 * 添加常用联系人
	 * @param params
	 * @return
	 */
	public Map<String, Object> addMostContact(Map<String, Object> params) {
		Map<String, Object> res = new HashMap<String,Object>();
		String usertoken = (String) params.get("usertoken");
		String account = Const.getUserInfo(usertoken).get("account");
		params.put("id", GUIDGenerator.newGUID());
		if(isOrgUser(usertoken)){
			//机构用户
			OrgUser  orguer= userdao.getUser4Org(account);
			if(orguer!=null){
				params.put("userid", orguer.getId());
			}
			boolean flag = userdao.hasContact4Org(params);
			if(!flag){
				//不存在添加
				userdao.addMostContact4Org(params);
			}
		}else{
			//个人用户
			PeUser peuser = userdao.getUser4Op(account);
			if(peuser!=null){
				params.put("userid", peuser.getId());
			}
			boolean flag = userdao.hasContact4Op(params);
			if(!flag){
				//不存在添加
				userdao.addMostContact4Op(params);
			}
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

	/**
	 * 删除常用联系人
	 * @param params
	 * @return
	 */
	public Map<String, Object> deleteMostContact(Map<String, Object> params) {
		Map<String, Object> res = new HashMap<String,Object>();
		String usertoken = (String) params.get("usertoken");
		String contactid = (String) params.get("contactid");
		
		if(isOrgUser(usertoken)){
			//机构用户
			userdao.deleteMostContact4Org(contactid);
		}else{
			//个人用户
			userdao.deleteMostContact4Op(contactid);
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

	/**
	 * 获取用车备注和事由
	 * @param usertoken
	 * @return
	 */
	public Map<String, Object> getUseResonAndRemark(String usertoken) {
		Map<String, Object> res = new HashMap<String,Object>();
		Map<String, List<String>> resoninfo = new HashMap<String, List<String>>();
		List<String> usereson = dicdao.getUseReson();
		List<String> remark = dicdao.getRemark();
		resoninfo.put("usereson", usereson);
		resoninfo.put("remark", remark);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("resoninfo", resoninfo);
		return res;
	}

	/**
	 * 获取租赁公司的计费规则
	 * @param param
	 * @param res
	 */
	public void getAccountRules(Map<String,Object> param, HttpServletResponse res) {
		res.setContentType("text/html; charset=utf-8");
//		String usertoken = req.getParameter("usertoken");
//		String city = req.getParameter("city");
//		String rulestype = req.getParameter("rulestype");
//		String usetype = req.getParameter("usetype");
		String usertoken = (String) param.get("usertoken");
		String city =  (String) param.get("city");
		String rulestype =  (String) param.get("rulestype");
		String usetype =  (String) param.get("usetype");
		String companyname = null;
		List<AccountRules> rules = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("city", city);
		params.put("rulestype", rulestype);
		if(isOrgUser(usertoken)){
			//机构用户
			String companyid =   (String) param.get("companyid");
			params.put("companyid", companyid);
			if(Const.USETYPE_PUBLIC.equals(usetype)){
				OrgOrgan organ = orgdao.getOrgInfo(Const.getUserInfo(usertoken).get("account"));
				params.put("organid", organ.getId());
			}
			//因公用车获取个性化计费规则，因私获取标准计费规则
			rules = orgdao.getAccountRules(params);
			LeasesCompany company = orgdao.getLeasesCompanyById(companyid);
			if(company!=null){
				companyname = company.getName();
			}
		}else{
			//个人用户
			//获取运营端的计费规则
			rules = opdao.getAccountRules(params);
//			companyname = "运营端";
		}
		if(rules!=null){
			for(int i=0;i<rules.size();i++){
				AccountRules rule = rules.get(i);
				String logo = rule.getLogo();
				if(StringUtils.isNotBlank(logo)){
					rule.setLogo(SystemConfig.getSystemProperty("fileserver")+File.separator+logo);
				}
			}
		}
		VelocityContext context = new VelocityContext();
		context.put("rules", rules);
		context.put("company", companyname);
		String vmpath = PassengerService.class.getClassLoader().getResource("accountrules.vm").getPath();
		try {
			VelocityUtil.createTemplate(vmpath, res.getWriter(), context);
		} catch (Exception e) {
			logger.error("乘客端异常",e);
		}
	}

	public static void main(String[] args) throws Exception{
//		FileInputStream fin  = new FileInputStream("E:/aa.jpg");
//		Map<String, Object> res = FileUtil.upload2FileServer(fin,"passengerimg.jpg");
//		System.out.println(((List)res.get("message")).get(0));
		String u = UserTokenManager.createUserToken(UserTokenManager.ORGUSERTYPE,"18825287005", "yunchuang@");
		System.out.println(u);
//		byte[] usertokencode = Base64.decodeBase64(u);
//		String ss1 = new String(usertokencode);
//		System.out.println("--------------------------------");
//		System.out.println(ss1);
//		System.out.println(ss1.substring(32, 49));
//		System.out.println("--------------------------------");
//		String u1 = UserTokenManager.createUserToken(UserTokenManager.PERSONNALUSER,"18825288888", "yunchuang@");
//		System.out.println(u1);
//		byte[] usertokencode1 = Base64.decodeBase64(u);
//		String ss = new String(usertokencode1);
//		System.out.println("--------------------------------");
//		System.out.println(ss);
//		System.out.println(ss.substring(32, 49));
//		System.out.println("--------------------------------");
//		
//		String u2 = UserTokenManager.createUserToken(UserTokenManager.ORGUSERTYPE,"18825288888", "yunchuang@");
//		System.out.println(u2);
//		byte[] usertokencode3 = Base64.decodeBase64(u2);
//		String ss3 = new String(usertokencode3);
//		System.out.println("--------------------------------");
//		System.out.println(ss3);
//		System.out.println(ss3.substring(32, 49));
//		System.out.println("--------------------------------");
//		String u21 = UserTokenManager.createUserToken(UserTokenManager.PERSONNALUSER,"18825288888", "yunchuang@");
//		System.out.println(u21);
//		
//		byte[] usertokencode4 = Base64.decodeBase64(u21);
//		String ss4 = new String(usertokencode4);
//		System.out.println("--------------------------------");
//		System.out.println(ss4);
//		System.out.println(ss4.substring(32, 49));
//		System.out.println("--------------------------------");
//		
//		String u3 = UserTokenManager.createUserToken(UserTokenManager.ORGUSERTYPE,"32123", "yunchuang@");
//		System.out.println(u3);
//		String u31 = UserTokenManager.createUserToken(UserTokenManager.PERSONNALUSER,"32123", "yunchuang@");
//		System.out.println(u31);
//		String s  = "13714193654";
//		System.out.println(PasswordEncoder.encode(s));
//		
//		byte[] usertokencode5 = Base64.decodeBase64("ZTAzNjVkMzc5NDJmNDhlODY0ODVhOGI5MTJiNTFhYjkyMDE2MTEwNzA5NTczMTc3NjAxMzg3NDM4NDIzNA==");
//		String ss5 = new String(usertokencode5);
//		System.out.println(ss5);
//		String ss = "Z75U/s3aLPQSyyy/zcCTYiQinnjc+ISEFhsHcXVaz3so0eroHIwIyjVRyNs5JLgjmeM1S/LAlpV5N+DvuZIeYP66jxRJShvHYL5BnMFYugOKzn7dzoCThAfw0Uywsbz1Ac6aUNt358FlugE15wHR5OhYd3UUX+Zn0SWe5+ZMwH8=";
//		System.out.println(URLEncoder.encode(ss, "utf-8"));
		
		/***
		 * {gmt_create=2016-11-17 17:07:47, 
		 * buyer_email=13266581262, 
		 * notify_time=2016-11-17 17:07:48, 
		 * gmt_payment=2016-11-17 17:07:48,
		 * seller_email=wangjianing@szyciov.com, 
		 * quantity=1, 
		 * subject=好约车, 
		 * 
		 * use_coupon=N, 
		 * sign=aqzELOPhMe0mZG47dsCdjzpThs3w/EjoyjR2f5xsWW9HCq5GRmOTJaCveO7QFFgphfQao7bJuOBBRgQcAXoobfkjon9QGVXd4285yeRVpRex2EadbdC6YaS0BWajBk7JQQ5rJLW/YmNC0kuj42Rn0aQfPld3WPwfCKrzxds3mGE=, 
		 * discount=0.00, 
		 * body=好约车付款, 
		 * buyer_id=2088802800191142, 
		 * 
		 * notify_id=8075a5306fd0e77f89c461d3893fca5h2y, 
		 * notify_type=trade_status_sync, 
		 * payment_type=1, 
		 * out_trade_no=20161117170710968NVDF4, 
		 * price=0.01, 
		 * 
		 * trade_status=TRADE_SUCCESS, 
		 * total_fee=0.01, 
		 * trade_no=2016111721001004140218727920, 
		 * sign_type=RSA, 
		 * seller_id=2088521158240961, 
		 * is_total_fee_adjust=N
		 * }
		 */
//		Map<String,String> params = new HashMap<String,String>();
//		params.put("gmt_create", "2016-11-17 17:07:47");
//		params.put("buyer_email", "13266581262");
//		params.put("notify_time", "2016-11-17 17:07:48");
//		params.put("gmt_payment", "2016-11-17 17:07:48");
//		params.put("seller_email", "wangjianing@szyciov.com");
//		params.put("quantity", "1");
//		params.put("subject", "好约车");
//		params.put("use_coupon", "N");
//		params.put("sign", "aqzELOPhMe0mZG47dsCdjzpThs3w/EjoyjR2f5xsWW9HCq5GRmOTJaCveO7QFFgphfQao7bJuOBBRgQcAXoobfkjon9QGVXd4285yeRVpRex2EadbdC6YaS0BWajBk7JQQ5rJLW/YmNC0kuj42Rn0aQfPld3WPwfCKrzxds3mGE=");
//		params.put("discount", "0.00");
//		params.put("body", "好约车付款");
//		params.put("buyer_id", "2088802800191142");
//		params.put("notify_id", "8075a5306fd0e77f89c461d3893fca5h2y");
//		params.put("notify_type", "trade_status_sync");
//		params.put("payment_type", "1");
//		params.put("out_trade_no", "20161117170710968NVDF4");
//		params.put("price", "0.01");
//		params.put("trade_status", "TRADE_SUCCESS");
//		params.put("total_fee", "0.01");
//		params.put("trade_no", "2016111721001004140218727920");
//		params.put("sign_type", "RSA");
//		params.put("seller_id", "2088521158240961");
//		params.put("is_total_fee_adjust", "N");
//		System.out.println(AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.input_charset));
//		Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap("2015052600090779", "30m", 0.01, "1", "我是测试数据","IQJZSRC1YMQB5HU","http://domain.merchant.com/payment_notify");
//		String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
//		String sign = OrderInfoUtil.getSign(payorderparam, AlipayConfig.private_key);
//		String payorderinfo = payorderstr + "&" + sign;
//		System.out.println(payorderinfo);
		
//		Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam("wx946621226aba6ffc", "1416436502", "好约车——订单付款", "20161129155812307A42ML", "1", "http://113.57.162.106:8002/passenger-api/Passenger/DillWXPayed4Org", "NATIVE");
//		Document doc = WXOrderUtil.createPreOrderInfo(prepayorderparam, WXOrderUtil.getSign(prepayorderparam, "shenzhenyunchuang2015lishuhui038"));
//		System.out.println(DocFunc.doc2String(doc));
//		Map<String,String> res = WXOrderUtil.getPreOrderId(DocFunc.doc2String(doc), "https://api.mch.weixin.qq.com/pay/unifiedorder");
//		if(res!=null&&"0".equalsIgnoreCase(res.get("status"))){
//			String prepay_id = res.get("prepay_id");
//			Map<String,String> payorderparam = WXOrderUtil.createPayOrderParam("wx946621226aba6ffc", "1416436502", prepay_id);
//			Document paydoc = WXOrderUtil.createPayOrderInfo(payorderparam, WXOrderUtil.getSign(payorderparam, "shenzhenyunchuang2015lishuhui038"));
//			System.out.println("-------------------支付-------------------");
//			System.out.println(DocFunc.doc2String(paydoc));
//		}
//		String newavgratestr = new java.text.DecimalFormat("#.0").format(12.2222);
//		System.out.println(newavgratestr);
		Date servertime = new Date();
		Calendar calender = Calendar.getInstance();
		calender.setTime(servertime);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
		System.out.println(format2.format(calender.getTime()));
		//今天---》2017-04-09
		System.out.println("今天---》"+format.format(calender.getTime()));
		calender.add(Calendar.DATE, 1);
		//明天---》2017-04-10
		System.out.println("明天---》"+format.format(calender.getTime()));
		calender.add(Calendar.DATE, 1);
		//后天---》2017-04-11
		System.out.println("后天---》"+format.format(calender.getTime()));
	}

	/**
	 * 获取可用的城市列表
	 * @param usertoken
	 * @param companyid
	 * @return
	 */
	public Map<String, Object> getValidCity(String usertoken, String companyid) {
		Map<String, Object> res = new HashMap<String,Object>();
		List<String> citys = null;
		if(isOrgUser(usertoken)){
			citys = orgdao.getValidCity(companyid);
		}else{
			citys = opdao.getValidCity();
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("citys", citys);
		return res;
	}

	/**
	 * 获取城市列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getCity(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String usertoken = (String) params.get("usertoken");
		String cityword = (String) params.get("cityword");
		List<Map<String,List<String>>> citys = new ArrayList<Map<String,List<String>>>();
		List<Map<String,Object>> citysinfo = null;
		if(isOrgUser(usertoken)){
			Map<String,Object> pprm = new HashMap<String,Object>();
			pprm.put("account", Const.getUserInfo(usertoken).get("account"));
			List<LeasesCompany> companys = userdao.getValiableLeasesCompanys(pprm);
			List<LeasesCompany> selfcompanys = userdao.getSelfCompanys(pprm);
			List<String> tempcpid = new ArrayList<String>();
			//机构用户
			if((companys!=null&&companys.size()>0)||(selfcompanys!=null&&selfcompanys.size()>0)){
				int companycount = 0;
				if(companys!=null&&companys.size()>0){
					Map<String,Object> sp = new HashMap<String,Object>();
					sp.put("companys", companys);
					sp.put("cityword", cityword);
					sp.put("account", Const.getUserInfo(usertoken).get("account"));
					citysinfo = orgdao.getCity(sp);
					if(citysinfo!=null&&citysinfo.size()>0){
						companycount=companys.size();
						for(int i=0;i<companys.size();i++){
							tempcpid.add(companys.get(i).getId());
						}
					}
				}
				if(selfcompanys!=null&&selfcompanys.size()>0){
					Map<String,Object> sp = new HashMap<String,Object>();
					sp.put("companys", selfcompanys);
					sp.put("cityword", cityword);
					sp.put("account", Const.getUserInfo(usertoken).get("account"));
					List<Map<String,Object>> selfcitysinfo = orgdao.getSelfCompanyCity(sp);
					if(citysinfo==null){
						citysinfo = new ArrayList<Map<String,Object>>();
					}
					citysinfo.addAll(selfcitysinfo);
					if(selfcitysinfo!=null&&selfcitysinfo.size()>0){
						for(int i=0;i<selfcompanys.size();i++){
							LeasesCompany comp = selfcompanys.get(i);
							if(!tempcpid.contains(comp.getId())){
								companycount ++;
								tempcpid.add(comp.getId());
							}
						}
					}
				}
				if(citysinfo!=null&&citysinfo.size()>0){
					if(companycount==1){
						res.put("onlyone",true);
					}else{
						res.put("onlyone",false);
					}
				}
			}
		}else{
			//个人用户
			citysinfo = opdao.getCity(params);
		}
		//处理
		dillCityinfos(citys,citysinfo);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		res.put("citys",citys);
		return res;
	}
	
	/**
	 * 获取上车城市可以选择的城市列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getGetOnCitys(Map<String, Object> params){
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String usertoken = (String) params.get("usertoken");
		List<Map<String,List<String>>> citys = new ArrayList<Map<String,List<String>>>();
		List<Map<String,Object>> citysinfo = new ArrayList<Map<String,Object>>();
		params.put("account",Const.getUserInfo(usertoken).get("account"));
		if(isOrgUser(usertoken)){
			//机构用户
			params.put("account", Const.getUserInfo(usertoken).get("account"));
			String usetype = (String) params.get("usetype");
			if(Const.USETYPE_PUBLIC.equalsIgnoreCase(usetype)){
				//个性化计费规则
				params.put("ruletype", "1");
			}else{
				//标准计费规则
				params.put("ruletype", "0");
			}
			List<Map<String,Object>> tempcitysinfo = orgdao.getGetOnCitys(params);
			if(tempcitysinfo!=null&&tempcitysinfo.size()>0){
				//获取有派单规则的城市集合
				List<String> sendrulecitys = orgdao.getSendRuleCitys(params);
				getValiableCitys(citysinfo,tempcitysinfo,sendrulecitys);
			}
		}else{
			//个人用户,个人用户获取城市和首页的获取城市一样
			List<Map<String,Object>> tempcitysinfo = opdao.getCity(params);
			if(tempcitysinfo!=null&&tempcitysinfo.size()>0){
				//获取有派单规则的城市集合
				List<String> sendrulecitys = opdao.getSendRuleCitys();
				getValiableCitys(citysinfo,tempcitysinfo,sendrulecitys);
			}
		}
		//处理
		dillCityinfos(citys,citysinfo);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("citys", citys);
		return res;
	}
	
	
	/**
	 * 取派单和计费都有的城市集合
	 * @param citysinfo
	 * @param tempcitysinfo
	 * @param sendrulecitys
	 */
	private void getValiableCitys(List<Map<String, Object>> citysinfo, List<Map<String, Object>> tempcitysinfo,List<String> sendrulecitys) {
		if(tempcitysinfo==null||tempcitysinfo.size()<=0||sendrulecitys==null||sendrulecitys.size()<=0){
			return;
		}
		for(int i=0;i<tempcitysinfo.size();i++){
			Map<String,Object> cityinfo = tempcitysinfo.get(i);
			String city = (String) cityinfo.get("city");
			if(sendrulecitys.contains(city)){
				citysinfo.add(cityinfo);
			}
		}
	}

	/**
	 * 获取下车城市列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getGetOffCitys(Map<String, Object> params){
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		List<Map<String,List<String>>> citys = new ArrayList<Map<String,List<String>>>();
		List<Map<String,Object>> citysinfo = dicdao.getAllCitys(params);
		//处理
		dillCityinfos(citys,citysinfo);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("citys", citys);
		return res;
	}
	
	/**
	 * 处理城市信息
	 * @param citys
	 * @param citysinfo
	 */
	private void dillCityinfos(List<Map<String,List<String>>> citys,List<Map<String,Object>> citysinfo){
		if(citysinfo==null){
			return ;
		}
		Map<String,List<String>> temp = new LinkedHashMap<String,List<String>>();
		for(int i=0;i<citysinfo.size();i++){
			Map<String,Object> cityinfo = citysinfo.get(i);
			String citypy = (String) cityinfo.get("cityinitials");
			String cityvalue = (String) cityinfo.get("city");
			List<String> cityvalues = temp.get(citypy);
			if(cityvalues==null){
				cityvalues = new ArrayList<String>();
				temp.put(citypy, cityvalues);
			}
			if(!cityvalues.contains(cityvalue)){
				cityvalues.add(cityvalue);
			}
		}
		Iterator<String> it = temp.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			List<String> value = temp.get(key);
			Map<String,List<String>> lv = new HashMap<String,List<String>>();
			lv.put(key, value);
			citys.add(lv);
		}
	}

	/**
	 * 下单
	 * @param order
	 * @return
	 */
	public Map<String, Object> addOder(PassengerOrder order,HttpServletRequest request) {
		//调用下单接口
		Map<String,Object> res = null;
		order.setOncity(dicdao.getCityNo(order.getOncity()));
		order.setOffcity(dicdao.getCityNo(order.getOffcity()));
		String usertoken = request.getParameter("usertoken");
		if(!StringUtils.isNotBlank(usertoken)){
			usertoken = request.getHeader("usertoken");
		}
		if(StringUtils.isNotBlank(order.getOrdersource())){
			order.setOrdersource(order.getOrdersource());
		}
		if(isOrgUser(usertoken)){
			//存储的是用车事由在字典表中的id
			Map<String,String> infoparams = new HashMap<String,String>();
			infoparams.put("type", "用车事由");
			infoparams.put("value", order.getVehiclessubjecttype());
			Dictionary vehiclessubjecttypeinfo = dicdao.getDictionaryByTypeValue(infoparams);
			if(vehiclessubjecttypeinfo!=null){
				order.setVehiclessubjecttype(vehiclessubjecttypeinfo.getId());
			}
			//因私用车，支付类型是“0”
			if(Const.USETYPE_PRIVATE.equalsIgnoreCase(order.getUsetype())){
				order.setPaymethod(PayMethod.PERSONAL.code);
			}
			//机构订单
			String account = Const.getUserInfo(usertoken).get("account");
			OrgUser user = userdao.getUser4Org(account);
			order.setUserid(user.getId());
			order.setOrganid(user.getOrganId());
			res = carserviceapi.dealRequestWithToken("/OrderApi/CreateOrgOrder", HttpMethod.POST, null, order, Map.class);
		}else{
			//个人订单
			String account = Const.getUserInfo(usertoken).get("account");
			PeUser user = userdao.getUser4Op(account);
			if("1".equalsIgnoreCase(user.getDisablestate())){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("action", "0");
				params.put("userid", user.getId());
				Map<String,Object> disableinfo = userdao.getUserLastDisableInfo4Op(params);
				Date starttime = (Date) disableinfo.get("starttime");
				Date current = new Date();
				if(current.after(starttime)){
					Date endtime = (Date) disableinfo.get("endtime");
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					if(res==null){
						res = new HashMap<String,Object>();
					}
					res.put("status", Retcode.USERNOTSUPPORT.code);
					res.put("message", "您已被禁止下单，禁止时间为"+format.format(starttime)+"至"+format.format(endtime));
					return res;
				}
			}
			order.setUserid(user.getId());
			res = carserviceapi.dealRequestWithToken("/OrderApi/CreateOpOrder", HttpMethod.POST, null, order, Map.class);
			if(order.isSavepassenger()){
				try{
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("userid", user.getId());
					params.put("name", order.getPassengers());
					params.put("phone", order.getPassengerphone());
					if(!userdao.hasContact4Op(params)){
						//不存在添加
						params.put("id", GUIDGenerator.newGUID());
						userdao.addMostContact4Op(params);
					}
				}catch(Exception e){
					logger.error("保存乘车人失败", e);
				}
			}
		}
		if(res==null){
			res = new HashMap<String,Object>();
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 获取用户信息
	 * @param usertoken
	 * @return
	 */
	public Map<String, Object> getPassengerInfo(String usertoken) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String account = Const.getUserInfo(usertoken).get("account");
		if(isOrgUser(usertoken)){
			//机构用户
			OrgUser userinfo = userdao.getUser4Org(account);
			if(userinfo!=null){
				Map<String,Object> result = new HashMap<String,Object>();
				String img = userinfo.getHeadPortraitMin();
				if(StringUtils.isNotBlank(img)){
					result.put("headportraitmin", SystemConfig.getSystemProperty("fileserver")+File.separator+img);
				}else{
					result.put("headportraitmin","");
				}
				result.put("account", userinfo.getAccount());
				result.put("nickname", userinfo.getNickName());
				result.put("sex", userinfo.getSex());
				result.put("company",userinfo.getOrgcaption());
				result.put("dpet",userinfo.getDeptcation());
				res.put("userinfo",result);
			}
		}else{
			//个人用户
			PeUser userinfo = userdao.getUser4Op(account);
			Map<String,Object> result = new HashMap<String,Object>();
			String img = userinfo.getHeadportraitmin();
			if(StringUtils.isNotBlank(img)){
				result.put("headportraitmin", SystemConfig.getSystemProperty("fileserver")+File.separator+img);
			}else{
				result.put("headportraitmin","");
			}
			result.put("account", userinfo.getAccount());
			result.put("nickname", userinfo.getNickname());
			result.put("sex", userinfo.getSex());
			res.put("userinfo",result);
		}
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		return res;
	}

	/**
	 * 上传用户头像
	 * @param param
	 * @return
	 */
	public Map<String, Object> uploadPassengerImg(Map<String,Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		Map<String,Object> result = null;
		try{
			String img = (String) param.get("img");
			byte[] imgbyte = Base64.decodeBase64(img);
			ByteArrayInputStream inputstream = new ByteArrayInputStream(imgbyte);
			try{
				result = FileUtil.upload2FileServer(inputstream,"passengerimg.jpg");
			}finally {
				inputstream.close();
			}
		}catch(Exception e){}
		if(result!=null&&result.get("message")!=null){
			res.put("status", Retcode.OK.code);
			res.put("message", Retcode.OK.msg);
			String path = (String) ((List)result.get("message")).get(0);
			// 更新用户头像
			String usertoken = (String) param.get("usertoken");
			res.put("Headportraitmin", SystemConfig.getSystemProperty("fileserver")+File.separator+path);
			Map<String,Object> userinfo = new HashMap<String,Object>();
			userinfo.put("path", path);
			String account = Const.getUserInfo(usertoken).get("account");
//			if(isOrgUser(usertoken)){
//				//机构用户
//				OrgUser user = userdao.getUser4Org(account);
//				if(user!=null&&StringUtils.isNotBlank(user.getId())){
//					userinfo.put("userid", user.getId());
//					userdao.updateUser4Org(userinfo);
//				}
//			}else{
				//个人用户
				PeUser user = userdao.getUser4Op(account);
				if(user!=null&&StringUtils.isNotBlank(user.getId())){
					userinfo.put("userid", user.getId());
					userdao.updateUser4Op(userinfo);
				}
//			}
		}else{
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 更新用户信息
	 * @param userinfo
	 * @return
	 */
	public Map<String, Object> updatePassengerInfo(Map<String,Object> userinfo) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String usertoken = (String) userinfo.get("usertoken");
		String account = Const.getUserInfo(usertoken).get("account");
		if(isOrgUser(usertoken)){
			//机构用户
			OrgUser orguser = userdao.getUser4Org(account);
			userinfo.put("userid", orguser.getId());
			userdao.updateUser4Org(userinfo);
		}else{
			//个人用户
			PeUser peuser = userdao.getUser4Op(account);
			userinfo.put("userid", peuser.getId());
			userdao.updateUser4Op(userinfo);
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

	/**
	 * 获取订单列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getOders(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String usertoken = (String) params.get("usertoken");
		String account = Const.getUserInfo(usertoken).get("account");
		params.put("account", account);
		List<Order4List> orders = null;
		if(isOrgUser(usertoken)){
			//机构用户
			orders = orderdao.getOrders4Org(params);
		}else{
			//个人用户
			orders = orderdao.getOrders4Op(params);
		}
		dillOrdersInfo(orders);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("orders", orders);
		return res;
	}
	
	private void dillOrdersInfo(List<Order4List> orders){
		if(orders==null||orders.size()<=0){
			return ;
		}
		for(int i=0;i<orders.size();i++){
			dillWithOrderInfo(orders.get(i));
		}
	}

	/**
	 * 更新订单状态
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateOderState_old(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		String usertoken = (String) params.get("usertoken");
		String param = (String) params.get("param");
		if("cancel".equals(param)){
			params.put("orderstate", OrderState.CANCEL.state);
			params.put("cancelparty", CancelParty.PASSENGER.code);
		}else{
			params.put("userhidden", Const.USER_ORDERSTATUS_USERHIDDEN);
		}
		if(isOrgUser(usertoken)){
			//机构用户
			if("cancel".equals(param)){
				PassengerOrder order = orderdao.getOrderByOrderno4Org((String)params.get("orderno"));
				String orderstatus = order.getOrderstatus();
				if(OrderState.INSERVICE.state.equals(orderstatus)||OrderState.SERVICEDONE.state.equals(orderstatus)){
					res.put("status", Retcode.OK.code);
					res.put("message", "订单已经不能取消！");
					return res;
				}
				OrderApiParam orderparam = new OrderApiParam();
				orderparam.setOrderid(order.getOrderno());
				orderparam.setOrderstate(OrderState.CANCEL.state);
				orderparam.setReqsrc(CancelParty.PASSENGER.code);
				JSONObject jsonres = carserviceapi.dealRequestWithToken("/OrderApi/ChangeOrderState", HttpMethod.POST, usertoken, orderparam, JSONObject.class);
				res.put("status", jsonres.get("status"));
				res.put("message", jsonres.get("message"));
			}else{
				try{
					orderdao.updateOrderState4Org(params);
				}catch(Exception e){
					logger.error("乘客端更改订单状态",e);
					res.put("status", Retcode.FAILED.code);
					res.put("message", "订单状态更改失败！");
				}
			}
		}else{
			//个人用户
			if("cancel".equals(param)){
				PassengerOrder order = orderdao.getOrderByOrderno4Op((String)params.get("orderno"));
				String orderstatus = order.getOrderstatus();
				if(OrderState.INSERVICE.state.equals(orderstatus)||OrderState.SERVICEDONE.state.equals(orderstatus)){
					res.put("status", Retcode.OK.code);
					res.put("message", "订单已经不能取消！");
					return res;
				}
				OrderApiParam orderparam = new OrderApiParam();
				orderparam.setOrderid(order.getOrderno());
				orderparam.setOrderstate(OrderState.CANCEL.state);
				orderparam.setReqsrc(CancelParty.PASSENGER.code);
				JSONObject jsonres = carserviceapi.dealRequestWithToken("/OrderApi/ChangeOrderState", HttpMethod.POST, usertoken, orderparam, JSONObject.class);
				res.put("status", jsonres.get("status"));
				res.put("message", jsonres.get("message"));
			}else{
				try{
					orderdao.updateOrderState4Op(params);
				}catch(Exception e){
					logger.error("乘客端更改订单状态",e);
					res.put("status", Retcode.FAILED.code);
					res.put("message", "订单状态更改失败！");
				}
			}
		}
		return res;
	}
	
	/**
	 * 更新订单状态
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateOderState(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			String usertoken = (String) params.get("usertoken");
			String param = (String) params.get("param");
			String orderno = (String)params.get("orderno");
			String ordertype = (String)params.get("ordertype");
			String usetype = (String)params.get("usetype");
			if(StringUtils.isBlank(orderno)||StringUtils.isBlank(usertoken)||StringUtils.isBlank(param)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				return res;
			}
			Map<String,Object> updateparam = new HashMap<String,Object>();
			updateparam.put("orderno", orderno);
			if("cancel".equals(param)){
				updateparam.put("orderstate", OrderState.CANCEL.state);
				updateparam.put("cancelparty", CancelParty.PASSENGER.code);
			}else{
				updateparam.put("userhidden", Const.USER_ORDERSTATUS_USERHIDDEN);
			}
			if(isOrgUser(usertoken)){
				//机构用户
				if("cancel".equals(param)){
					PassengerOrder order = orderdao.getOrderByOrderno4Org(orderno);
					String orderstatus = order.getOrderstatus();
					if(OrderState.INSERVICE.state.equals(orderstatus)||OrderState.SERVICEDONE.state.equals(orderstatus)){
						res.put("status", Retcode.OK.code);
						res.put("message", "订单已经不能取消！");
						return res;
					}
					OrderApiParam orderparam = new OrderApiParam();
					orderparam.setOrderid(order.getOrderno());
					orderparam.setOrderstate(OrderState.CANCEL.state);
					orderparam.setReqsrc(CancelParty.PASSENGER.code);
					orderparam.setOrdertype(ordertype);
					orderparam.setUsetype(usetype);
					JSONObject jsonres = carserviceapi.dealRequestWithToken("/OrderApi/ChangeOrderState", HttpMethod.POST, usertoken, orderparam, JSONObject.class);
					res.put("status", jsonres.get("status"));
					res.put("message", jsonres.get("message"));
				}else{
					try{
						orderdao.updateOrderState4Org(updateparam);
					}catch(Exception e){
						logger.error("乘客端更改订单状态",e);
						res.put("status", Retcode.FAILED.code);
						res.put("message", "订单状态更改失败！");
					}
				}
			}else{
				//个人用户
				boolean istaxiorder = isTaxiOrder(ordertype);
				if("cancel".equals(param)){
					//订单取消的时候在changeOrderState时已经判断了，此处分为出租车订单和网约车订单所以不需要判断
//					PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
//					String orderstatus = order.getOrderstatus();
//					if(OrderState.INSERVICE.state.equals(orderstatus)||OrderState.SERVICEDONE.state.equals(orderstatus)){
//						res.put("status", Retcode.OK.code);
//						res.put("message", "订单已经不能取消！");
//						return res;
//					}
					OrderApiParam orderparam = new OrderApiParam();
					orderparam.setOrderid(orderno);
					orderparam.setOrderstate(OrderState.CANCEL.state);
					orderparam.setReqsrc(CancelParty.PASSENGER.code);
					orderparam.setOrdertype(ordertype);
					orderparam.setUsetype(usetype);
					JSONObject jsonres = carserviceapi.dealRequestWithToken("/OrderApi/ChangeOrderState", HttpMethod.POST, usertoken, orderparam, JSONObject.class);
					res.put("status", jsonres.get("status"));
					res.put("message", jsonres.get("message"));
				}else{
					try{
						if(istaxiorder){
							//个人用户出租车状态变更
							orderdao.updateOrderState4OpTaxi(updateparam);
						}else{
							//网约车之前的不变
							orderdao.updateOrderState4Op(updateparam);
						}
					}catch(Exception e){
						logger.error("乘客端更改订单状态",e);
						res.put("status", Retcode.EXCEPTION.code);
						res.put("message", Retcode.EXCEPTION.msg);
					}
				}
			}
		}catch(Exception e){
			logger.error("乘客端更改订单状态",e);
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		return res;
	}
	
	/**
	 * 判断是否是taxiorder
	 * @param ordertype
	 * @return
	 */
	private boolean isTaxiOrder(String ordertype){
		return OrderEnum.ORDERTYPE_TAXI.code.equals(ordertype);
	}

	/**
	 * 获取司机信息
	 * @param usertoken
	 * @param driverid
	 * @return
	 */
	public Map<String, Object> getDriverInfo(String usertoken, String driverid) {
		Map<String, Object> res = new HashMap<String,Object>();
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		DriverInfo driverinfo = orderdao.getDriverInfo(driverid);
		if(driverinfo!=null){
			String img = driverinfo.getHeadportraitmax();
			if(StringUtils.isNotBlank(img)){
				driverinfo.setDriverimg(SystemConfig.getSystemProperty("fileserver")+File.separator+img);
			}
			String avgrate = driverinfo.getAvgrate();
			if(StringUtils.isNotBlank(avgrate)&&avgrate.indexOf(".")>=0&&(avgrate.indexOf(".")+2)<=avgrate.length()){
				driverinfo.setAvgrate(avgrate.substring(0, avgrate.indexOf(".")+2));
			}else if(StringUtils.isBlank(avgrate)){
				driverinfo.setAvgrate("0.0");
			}
		}
		res.put("driverinfo", driverinfo);
		return res;
	}
	
	/**
	 * 获取司机的位置信息
	 * @param usertoken
	 * @param driverid
	 * @return
	 */
	public Map<String, Object> getDriverPosition(String usertoken, String driverid,String orderid) {
		Map<String,Object> res = dicdao.getDriverPosition(driverid);
		if(res==null){
			res = new HashMap<String,Object>();
		}else{
			//获取实时费用
			OrderCostParam params = new OrderCostParam();
			params.setHasunit(true);
			params.setOrderid(orderid);
			try{
				JSONObject costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, usertoken, params, JSONObject.class);
				if(costinfo!=null&&costinfo.getInt("status")==Retcode.OK.code){
					costinfo.remove("status");
					costinfo.remove("message");
					String amount = costinfo.getString("cost");
					res.put("orderamount", amount);
				}else{
					res.put("orderamount", "0.00元");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//需要根据订单信息查出正在服务中的订单，如果没有就不显示下面的信息
			PassengerOrder order = null;
			if(isOrgUser(usertoken)){
				//机构用户
				//获取订单相关的消费信息
				order = orderdao.getOrderByOrderno4Org(orderid);
			}else{
				//个人用户
				order = orderdao.getOrderByOrderno4Op(orderid);
			}
			if(order!=null){
				res.put("orderstatus", order.getOrderstatus());
				if(OrderState.INSERVICE.state.equalsIgnoreCase(order.getOrderstatus())){
					String orderinfo = JedisUtil.getString(RedisKeyEnum.MESSAGE_ORDER_TRAVEL_INFO.code+orderid);
					if(StringUtils.isNotBlank(orderinfo)){
						JSONObject json = JSONObject.fromObject(orderinfo);
						int lefttime = json.getInt("lefttime");
						int leftkm = json.getInt("leftkm");
						double dis =  ((Integer)leftkm).doubleValue()/1000;
						dis = StringUtil.formatNum(dis, 1);
						String durstr = StringUtil.formatCostTimeInSecond(lefttime);
						res.put("hintmessage", "距离终点 "+dis+"公里预计"+durstr);
					}else{
						//调用接口
						BaiduApiQueryParam baiduparam = new BaiduApiQueryParam();
						baiduparam.setOrderStartLat((double) res.get("lat"));
						baiduparam.setOrderStartLng((double) res.get("lng"));
						baiduparam.setOrderEndLat(order.getOffaddrlat());
						baiduparam.setOrderEndLng(order.getOffaddrlng());
						Map<String,Object> hintinfo = carserviceapi.dealRequestWithToken("/BaiduApi/GetMileageInfo", HttpMethod.POST, usertoken, baiduparam, Map.class);
						if(hintinfo!=null&&(Integer)hintinfo.get("status")==Retcode.OK.code){
							Object distance = hintinfo.get("distance");
							double dis = 0;
							if(distance!=null){
								dis =  ((Integer)distance).doubleValue()/1000;
								dis = StringUtil.formatNum(dis, 1);
							}
							Object duration = hintinfo.get("duration");
							String durstr = "0";
							if(duration!=null){
								durstr = StringUtil.formatCostTimeInSecond((int)duration);
							}
							res.put("hintmessage", "距离终点 "+dis+"公里预计"+durstr);
						}
					}
				}else if(OrderState.WAITSTART.state.equalsIgnoreCase(order.getOrderstatus())){
					//带出发给出带出发的
					Date usetime = order.getUsetime();
					Date current = new Date();
					int lefttime = (int)((usetime.getTime() - current.getTime())/(1000*60));
					String lefttimestr = StringUtil.formatCostTime(lefttime>0?lefttime:0);
					res.put("lefttime", lefttimestr);
				}
			}
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

	/**
	 * 获取账户的支付信息
	 * @param params
	 * @return
	 */
	public Map<String, Object> getPayAccounts_old(Map<String,Object> params) {
		Map<String, Object> res = new HashMap<String,Object>();
		String usertoken = (String) params.get("usertoken");
		String account = Const.getUserInfo(usertoken).get("account");
		List<Map<String, Object>> payinfo = new ArrayList<Map<String, Object>>();
		List<Map<String,Object>> infos = null;
		String orderno = (String) params.get("orderno");
		//兼容之前没有传递orderno，完善后去除
		Map<String,Object> validinfo = null;
		PassengerOrder order = null;
		if(StringUtils.isNotBlank(orderno)){
			validinfo = checkOrderValid(usertoken, orderno, "1");
			if(validinfo!=null&&(int)validinfo.get("status")!=Retcode.OK.code){
				return validinfo;
			}
			order = (PassengerOrder) validinfo.get("order");
		}
		if(isOrgUser(usertoken)){
			//机构用户
			OrgUser orguser = userdao.getUser4Org(account);
			Map<String,Object> param = new HashMap<String,Object>();
			String companyid = (String) params.get("companyid");
			if(order!=null){
				companyid = order.getCompanyid();
			}
			param.put("companyid", companyid);
			param.put("userid", orguser.getId());
			double balance = userdao.getUserBalance4Org(param);
			Map<String,Object> balanceinfo = new HashMap<String,Object>();
			balanceinfo.put("type", "使用账户余额支付");
			balanceinfo.put("id", "3");
			if(order!=null){
				double amount = order.getOrderamount();
				if(amount<=balance){
					payinfo.add(balanceinfo);
				}
			}
			Map<String,Object> tempinfo = userdao.getPayAccounts4Org(companyid);
			if(tempinfo!=null){
				infos = new ArrayList<Map<String,Object>>();
				Map<String,Object> wxinfo = new HashMap<String,Object>();
				wxinfo.put("type","微信支付");
				wxinfo.put("id","2");
				wxinfo.put("payaccount",tempinfo.get("wechataccount"));
				infos.add(wxinfo);
				Map<String,Object> alinfo = new HashMap<String,Object>();
				alinfo.put("type","支付宝支付");
				alinfo.put("id","1");
				alinfo.put("payaccount",tempinfo.get("alipayaccount"));
				infos.add(alinfo);
			}
		}else{
			//个人用户
			PeUser peuser = userdao.getUser4Op(account);
			double balance = userdao.getUserBalance4Op(peuser.getId());
			Map<String,Object> balanceinfo = new HashMap<String,Object>();
			balanceinfo.put("type", "使用账户余额支付");
			balanceinfo.put("id", "3");
			balanceinfo.put("validmoney", balance);
			if(order!=null){
				double amount = order.getOrderamount();
				if(amount<=balance){
					payinfo.add(balanceinfo);
				}
			}
			Map<String,Object> tempinfo = userdao.getPayAccounts4Op();
			if(tempinfo!=null){
				infos = new ArrayList<Map<String,Object>>();
				Map<String,Object> wxinfo = new HashMap<String,Object>();
				wxinfo.put("type","微信支付");
				wxinfo.put("id","2");
				wxinfo.put("payaccount",tempinfo.get("wechatpayaccount"));
				infos.add(wxinfo);
				Map<String,Object> alinfo = new HashMap<String,Object>();
				alinfo.put("type","支付宝支付");
				alinfo.put("id","1");
				alinfo.put("payaccount",tempinfo.get("alipayaccount"));
				infos.add(alinfo);
			}
		}
		if(infos!=null){
			for(int i=0;i<infos.size();i++){
				payinfo.add(infos.get(i));
			}
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("payinfo", payinfo);
		return res;
	}
	
	/**
	 * 获取订单支付信息
	 * @param params
	 * @return
	 */
	public Map<String, Object> getPayAccounts(Map<String,Object> params) {
		Map<String, Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			String usertoken = (String) params.get("usertoken");
			String account = Const.getUserInfo(usertoken).get("account");
			String orderno = (String) params.get("orderno");
			
			String ordertype = (String) params.get("ordertype");
			String usetype = (String) params.get("usetype");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
				res.put("status", Retcode.FAILED.code);
				res.put("message", "参数不全！");
				return res;
			}
			
			List<Map<String, Object>> payinfo = new ArrayList<Map<String, Object>>();
			List<Map<String,Object>> infos = null;
			if(isOrgUser(usertoken)){
				//兼容之前没有传递orderno，完善后去除
				Map<String,Object> validinfo = checkOrderValid(usertoken, orderno, ordertype);
				if(validinfo!=null&&(int)validinfo.get("status")!=Retcode.OK.code){
					return validinfo;
				}
				PassengerOrder order = (PassengerOrder) validinfo.get("order");
				//机构用户
				OrgUser orguser = userdao.getUser4Org(account);
				Map<String,Object> param = new HashMap<String,Object>();
				String companyid = (String) params.get("companyid");
				if(order!=null){
					companyid = order.getCompanyid();
				}
				param.put("companyid", companyid);
				param.put("userid", orguser.getId());
				double balance = userdao.getUserBalance4Org(param);
				Map<String,Object> balanceinfo = new HashMap<String,Object>();
				balanceinfo.put("type", "余额支付");
				balanceinfo.put("id", "3");
				if(order!=null){
					double amount = order.getOrderamount();
					if(amount<=balance){
						balanceinfo.put("validmoney", true);
					}else{
						balanceinfo.put("validmoney", false);
					}
					payinfo.add(balanceinfo);
				}
				Map<String,Object> tempinfo = userdao.getPayAccounts4Org(companyid);
				if(tempinfo!=null){
					infos = new ArrayList<Map<String,Object>>();
					String wechatstatus = (String) tempinfo.get("wechatstatus");
					if("1".equals(wechatstatus)){
						Map<String,Object> wxinfo = new HashMap<String,Object>();
						wxinfo.put("type","微信支付");
						wxinfo.put("id","2");
						wxinfo.put("payaccount",tempinfo.get("wechataccount"));
//						wxinfo.put("validmoney", true);
						infos.add(wxinfo);
					}
					String alipaystatus = (String) tempinfo.get("alipaystatus");
					if("1".equals(alipaystatus)){
						Map<String,Object> alinfo = new HashMap<String,Object>();
						alinfo.put("type","支付宝支付");
						alinfo.put("id","1");
						alinfo.put("payaccount",tempinfo.get("alipayaccount"));
//						alinfo.put("validmoney", true);
						infos.add(alinfo);
					}
				}
			}else{
				//兼容之前没有传递orderno，完善后去除
				Map<String,Object> validinfo = checkOrderValid(usertoken, orderno, ordertype);
				if(validinfo!=null&&(int)validinfo.get("status")!=Retcode.OK.code){
					return validinfo;
				}
				Object order = validinfo.get("order");
				//个人用户
				PeUser peuser = userdao.getUser4Op(account);
				double balance = userdao.getUserBalance4Op(peuser.getId());
				Map<String,Object> balanceinfo = new HashMap<String,Object>();
				balanceinfo.put("type", "使用账户余额支付");
				balanceinfo.put("id", "3");
				if(order!=null){
					double amount = 0;
					if(isTaxiOrder(ordertype)){
						//出租车
						Map<String,Object> optaxiorder = (Map<String, Object>) order;
	                	amount = parseDouble(optaxiorder.get("schedulefee"));
	    				String paymentmethod = (String) optaxiorder.get("paymentmethod");
	    				if("0".equalsIgnoreCase(paymentmethod)){
	    					//线上支付
	    					amount += parseDouble(optaxiorder.get("orderamount"));
	    				}
					}else{
						//网约车
						amount = ((PassengerOrder)order).getOrderamount();
					}
					if(amount<=balance){
						balanceinfo.put("validmoney", true);
					}else{
						balanceinfo.put("validmoney", false);
					}
					payinfo.add(balanceinfo);
				}
				Map<String,Object> tempinfo = userdao.getPayAccounts4Op();
				if(tempinfo!=null){
					infos = new ArrayList<Map<String,Object>>();
					String wechatstatus = (String) tempinfo.get("wechatstatus");
					if("1".equals(wechatstatus)){
						Map<String,Object> wxinfo = new HashMap<String,Object>();
						wxinfo.put("type","微信支付");
						wxinfo.put("id","2");
						wxinfo.put("payaccount",tempinfo.get("wechatpayaccount"));
//						wxinfo.put("validmoney", true);
						infos.add(wxinfo);
					}
					String alipaystatus = (String) tempinfo.get("alipaystatus");
					if("1".equals(alipaystatus)){
						Map<String,Object> alinfo = new HashMap<String,Object>();
						alinfo.put("type","支付宝支付");
						alinfo.put("id","1");
						alinfo.put("payaccount",tempinfo.get("alipayaccount"));
//						alinfo.put("validmoney", true);
						infos.add(alinfo);
					}
				}
			}
			if(infos!=null){
				for(int i=0;i<infos.size();i++){
					payinfo.add(infos.get(i));
				}
			}
			res.put("payinfo", payinfo);
		}catch(Exception e){
			logger.error("获取支付方式出错", e);
			
		}
		return res;
	}

	/**
	 * 使用账户余额支付订单
	 * @param params
	 * @return
	 */
	public Map<String, Object> payOrderByBalance(Map<String,Object> params){
		Map<String, Object> res = new HashMap<String, Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			String usertoken = (String)params.get("usertoken");
			String orderno = (String) params.get("orderno");
			String ordertype = (String) params.get("ordertype");
			String usetype = (String) params.get("usetype");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
				res.put("status", Retcode.FAILED.code);
				res.put("message", "支付失败，参数不全");
			}
			String account = Const.getUserInfo(usertoken).get("account");
			if(isOrgUser(usertoken)){
				PassengerOrder order = orderdao.getOrderByOrderno4Org(orderno);
				double orderamount = order.getOrderamount();
				//机构用户
				OrgUser orguser = userdao.getUser4Org(account);
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("companyid", order.getCompanyid());
				param.put("userid", orguser.getId());
				double balance = userdao.getUserBalance4Org(param);
				if(balance<orderamount){
					//不够支付
					res.put("status", Retcode.FAILED.code);
					res.put("message", "余额不足！");
				}else{
					//更新钱包
					Map<String,Object> updatebalancep = new HashMap<String,Object>();
					updatebalancep.put("uid", GUIDGenerator.newGUID());
					updatebalancep.put("money", orderamount);
					updatebalancep.put("companyid", order.getCompanyid());
					updatebalancep.put("userid", orguser.getId());
					updatebalancep.put("isout", 1);
					userdao.updateUserBalance4OrgSec(updatebalancep);
					//添加支付记录
					Map<String,Object> expenses = new HashMap<String,Object>();
					expenses.put("logid", GUIDGenerator.newGUID());
					expenses.put("userid",orguser.getId());
					expenses.put("companyid",order.getCompanyid());
					expenses.put("expensetype","4");
					expenses.put("money",orderamount);
					expenses.put("remark","余额支付");
					expenses.put("tradetype","1");
					expenses.put("detailtype","0");
					expenses.put("operateresult","0");
					userdao.addExpenses4OrgSec(expenses);
					//更新订单状态
	            	Map<String, Object> orderparam = new HashMap<String,Object>();
	            	orderparam.put("paymentstatus", "1");
	            	orderparam.put("orderno", orderno);
	            	orderparam.put("paytype", "1");
	            	orderdao.payed4OrgOrder(orderparam);
				}
			}else{
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("orderno", orderno);
				//个人用户
				PeUser peuser = userdao.getUser4Op(account);
				double balance = userdao.getUserBalance4Op(peuser.getId());
				if(isTaxiOrder(ordertype)){
					Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(param);
					return payOptaxiOrderByBalance(optaxiorder,peuser,balance);
				}
				PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
				double orderamount = order.getOrderamount();
				if(balance<orderamount){
					//不够支付
					res.put("status", Retcode.FAILED.code);
					res.put("message", "余额不足！");
				}else{
					//更新钱包
					Map<String,Object> updatebalancep = new HashMap<String,Object>();
					updatebalancep.put("uid", GUIDGenerator.newGUID());
					updatebalancep.put("money", orderamount);
					updatebalancep.put("userid", peuser.getId());
					updatebalancep.put("isout", 1);
					userdao.updateUserBalance4OpSec(updatebalancep);
					//添加支付记录
					Map<String,Object> expenses = new HashMap<String,Object>();
					expenses.put("logid", GUIDGenerator.newGUID());
					expenses.put("userid",peuser.getId());
					expenses.put("expensetype","4");
					expenses.put("money",orderamount);
					expenses.put("remark","余额支付");
					expenses.put("tradetype","1");
					expenses.put("detailtype","0");
					expenses.put("operateresult","0");
					userdao.addExpenses4OpSec(expenses);
					//更新订单状态
	            	Map<String, Object> orderparam = new HashMap<String,Object>();
	            	orderparam.put("paymentstatus", "1");
	            	orderparam.put("orderno", orderno);
	            	orderparam.put("paytype", "1");
	            	orderdao.payed4OpOrder(orderparam);
	            	//使用优惠券
	            	dillCouponUseInfo4Op(order);
				}
			}
		}catch(Exception e){
			logger.error("钱包支付报错", e);
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		return res;
	}
	
	/**
	 * 转化obj成value，空值就为0
	 * @param value
	 * @return
	 */
	private double parseDouble(Object value){
		if(value==null||"".equalsIgnoreCase(String.valueOf(value))){
			return 0;
		}
		return Double.parseDouble(String.valueOf(value));
	}
	
	/**
	 * 使用钱包支付出租车订单
	 * @param optaxiorder
	 * @param peuser
	 * @param balance
	 * @return
	 */
	private Map<String, Object> payOptaxiOrderByBalance(Map<String, Object> optaxiorder, PeUser peuser, double balance) {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			String orderno = (String) optaxiorder.get("orderno");
			double orderamount = parseDouble(optaxiorder.get("schedulefee"));
			String paymentmethod = (String) optaxiorder.get("paymentmethod");
			if("0".equalsIgnoreCase(paymentmethod)){
				//线上支付
				orderamount += parseDouble(optaxiorder.get("orderamount"));
			}
			if(balance<orderamount){
				//不够支付
				res.put("status", Retcode.FAILED.code);
				res.put("message", "余额不足！");
			}else{
				//更新钱包
				Map<String,Object> updatebalancep = new HashMap<String,Object>();
				updatebalancep.put("uid", GUIDGenerator.newGUID());
				updatebalancep.put("money", orderamount);
				updatebalancep.put("userid", peuser.getId());
				updatebalancep.put("isout", 1);
				userdao.updateUserBalance4OpSec(updatebalancep);
				//添加支付记录
				Map<String,Object> expenses = new HashMap<String,Object>();
				expenses.put("logid", GUIDGenerator.newGUID());
				expenses.put("userid",peuser.getId());
				expenses.put("expensetype","4");
				expenses.put("money",orderamount);
				expenses.put("remark","余额支付");
				expenses.put("tradetype","1");
				expenses.put("detailtype","0");
				expenses.put("operateresult","0");
				userdao.addExpenses4OpSec(expenses);
				//更新订单状态
	        	Map<String, Object> orderparam = new HashMap<String,Object>();
	        	orderparam.put("paymentstatus", "1");
	        	orderparam.put("orderno", orderno);
	        	orderparam.put("paytype", "1");
	        	orderdao.payed4OpTaxiOrder(orderparam);
	        	
	        	//优惠券处理
				dillCouponUseInfo4Op(optaxiorder);
			}
		}catch(Exception e){
			logger.error("钱包支付出租车订单", e);
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 支付接口
	 * @param params
	 * @return
	 */
	public Map<String, Object> payOder_old(Map<String, Object> params,HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		String usertoken = (String) params.get("usertoken");
		String orderno = (String) params.get("orderno");
		String paytype = (String) params.get("paytype");
		Map<String,Object> validinfo = checkOrderValid(usertoken, orderno, paytype);
		if(validinfo!=null&&(int)validinfo.get("status")!=Retcode.OK.code){
			return validinfo;
		}
		if("3".equalsIgnoreCase(paytype)){
			//钱包支付
			return payOrderByBalance(params);
		}
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		//获取订单的交易号 (时间加上5位随机码)
		String out_trade_no = format.format(date)+UNID.getUNID();
		Map<String,String> orderinfo = new HashMap<String,String>();
		orderinfo.put("out_trade_no", out_trade_no);
		orderinfo.put("orderno", orderno);
		//微信是2，支付宝是1
		orderinfo.put("paymenttype", "1".equalsIgnoreCase(paytype)?"0":"1");
		PassengerOrder order = (PassengerOrder) validinfo.get("order");
		double orderamount = order.getOrderamount();
		Map<String,Object> payinfo = null;
		Map<String,String> tempp = new HashMap<String,String>();
		if(isOrgUser(usertoken)){
			//添加记录到数据库
			orderdao.addTradeNo4OrgOrder(orderinfo);
			tempp.put("wxnotifyurl", "Passenger/DillWXPayed4Org");
			tempp.put("zfbnotifyurl", "Passenger/DillZFBPayed4Org");
			payinfo = getPayInfo("1", order.getCompanyid());
		}else{
			orderdao.addTradeNo4OpOrder(orderinfo);
			tempp.put("wxnotifyurl", "Passenger/DillWXPayed4Op");
			tempp.put("zfbnotifyurl", "Passenger/DillZFBPayed4Op");
			payinfo = getPayInfo("0", null);
		}
		if(payinfo==null){
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message","没有收款方");
			return res;
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		if("1".equalsIgnoreCase(paytype)){
			String appid = (String) payinfo.get("alipayappid");
			String privatekey = (String) payinfo.get("alipayprivatekey");
			String pubkey = (String) payinfo.get("alipaypublickey");
			if(StringUtils.isBlank(appid)||StringUtils.isBlank(pubkey)||StringUtils.isBlank(privatekey)){
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message","收款方信息不完整");
				return res;
			}
			res.put("out_trade_no", out_trade_no);
			//支付宝订单信息，带签
			String zfbsubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbsubject"))?"好约车":SystemConfig.getSystemProperty("zfbsubject");
			String zfbbody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbbody"))?"好约车付款":SystemConfig.getSystemProperty("zfbbody");
			orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
			Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", orderamount, zfbsubject, zfbbody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/"+tempp.get("zfbnotifyurl"));
//			Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap("2016110502553505", "30m", 0.01, "好约车", "好约车付款", out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/"+tempp.get("zfbnotifyurl"));
			String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
//			String sign = OrderInfoUtil.getSign(payorderparam, AlipayConfig.private_key);
			String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
			String payorderinfo = payorderstr + "&" + sign;
			res.put("payorderinfo", payorderinfo);
		}else if("2".equalsIgnoreCase(paytype)){
			String appid = (String) payinfo.get("wechatappid");
			String shno = (String) payinfo.get("wechatmerchantno");
			String md5key = (String) payinfo.get("wechatsecretkey");
			if(StringUtils.isBlank(appid)||StringUtils.isBlank(shno)||StringUtils.isBlank(md5key)){
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message","收款方信息不完整");
				return res;
			}
			//微信订单
			try{
				String ipadd = request.getRemoteAddr();
				String wxbody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxbody"))?"好约车——订单付款":SystemConfig.getSystemProperty("wxbody");
				String ordermoney = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?"1":new java.text.DecimalFormat("#").format(orderamount*100);
				Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, shno, wxbody, out_trade_no, ordermoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/"+tempp.get("wxnotifyurl"), "APP",ipadd);
				Document doc = WXOrderUtil.createPreOrderInfo(prepayorderparam, WXOrderUtil.getSign(prepayorderparam, md5key));
//				Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam("wx946621226aba6ffc", "1416436502", "好约车——订单付款", out_trade_no, "1", SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/"+tempp.get("wxnotifyurl"), "APP");
//				Document doc = WXOrderUtil.createPreOrderInfo(prepayorderparam, WXOrderUtil.getSign(prepayorderparam, "shenzhenyunchuang2015lishuhui038"));
				Map<String,String> preres = WXOrderUtil.getPreOrderId(DocFunc.doc2String(doc), "https://api.mch.weixin.qq.com/pay/unifiedorder");
				if(preres!=null&&"0".equalsIgnoreCase(preres.get("status"))){
					String prepay_id = preres.get("prepay_id");
					Map<String,String> payorderparam = WXOrderUtil.createPayOrderParam(appid, shno, prepay_id);
					String sign = WXOrderUtil.getSign(payorderparam, md5key);
//					Map<String,String> payorderparam = WXOrderUtil.createPayOrderParam("wx946621226aba6ffc", "1416436502", prepay_id);
//					String sign = WXOrderUtil.getSign(payorderparam, "shenzhenyunchuang2015lishuhui038");
					payorderparam.put("sign", sign);
					res.put("out_trade_no", out_trade_no);
					res.put("payorderinfo", payorderparam);
				}else{
					res.put("status", Retcode.EXCEPTION.code);
					res.put("message", "支付异常");
				}
			}catch(Exception e){
				e.printStackTrace();
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message", "支付异常");
			}
		}
		return res;
	}
	
	/**
	 * 支付接口
	 * @param params
	 * @return
	 */
	public Map<String, Object> payOder(Map<String, Object> params,HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		addPubInfos(res);
		try{
			String usertoken = (String) params.get("usertoken");
			String orderno = (String) params.get("orderno");
			String paytype = (String) params.get("paytype");
			String ordertype = (String) params.get("ordertype");
			String usetype = (String) params.get("usetype");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)||StringUtils.isBlank(paytype)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
				res.put("status", Retcode.FAILED.code);
				res.put("message", "支付失败，参数不全");
			}
			Map<String,Object> validinfo = checkOrderValid(usertoken, orderno, ordertype);
			if(validinfo!=null&&(int)validinfo.get("status")!=Retcode.OK.code){
				return validinfo;
			}
			if("3".equalsIgnoreCase(paytype)){
				//钱包支付
				return payOrderByBalance(params);
			}
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			//获取订单的交易号 (时间加上5位随机码)
			String out_trade_no = format.format(date)+UNID.getUNID();
			if(isOrgUser(usertoken)){
				//机构用户
				PassengerOrder order = orderdao.getOrderByOrderno4Org(orderno);
				String companyid = order.getCompanyid();
				double orderamount = order.getOrderamount();
				LeasesCompany company = orgdao.getLeasesCompanyById(companyid);
				if(company==null){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","账户信息不全");
					res.put("payorderinfo","");
					return res;
				}
				if("1".equalsIgnoreCase(paytype)){
					//支付宝充值
					String alipaystatus = company.getAlipaystatus();
					if(!"1".equals(alipaystatus)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","暂不支持支付宝支付");
						res.put("payorderinfo","");
						return res;
					}
					String appid = company.getAlipayappid();
					String alipubkey = company.getAlipaypublickey();
					String privatekey = company.getAlipayprivatekey();
					if(StringUtils.isBlank(appid)||StringUtils.isBlank(alipubkey)||StringUtils.isBlank(privatekey)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","账户信息不全");
						res.put("payorderinfo","");
						return res;
					}
					//添加交易号记录流水
					Map<String,String> orderinfo = new HashMap<String,String>();
					orderinfo.put("out_trade_no", out_trade_no);
					orderinfo.put("orderno", orderno);
					orderinfo.put("paymenttype", "0");
					orderinfo.put("validatekey", alipubkey);
					orderdao.addTradeNo4OrgOrder(orderinfo);
					orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
					String zfbsubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbsubject"))?"好约车":SystemConfig.getSystemProperty("zfbsubject");
					zfbsubject += orderno;
					String zfbbody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbbody"))?"好约车付款":SystemConfig.getSystemProperty("zfbbody");
					Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", orderamount, zfbsubject, zfbbody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillZFBPayed4Org");
					String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
					String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
					String payorderinfo = payorderstr + "&" + sign;
					res.put("payorderinfo", payorderinfo);
				}else if("2".equalsIgnoreCase(paytype)){
					//微信充值
					String wechatstatus = company.getWechatstatus();
					if(!"1".equals(wechatstatus)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","暂不支持微信支付");
						res.put("payorderinfo","");
						return res;
					}
					String appid = company.getWechatappid();
					String chantno = company.getWechatmerchantno();
					String secretkey = company.getWechatsecretkey();
					if(StringUtils.isBlank(appid)||StringUtils.isBlank(chantno)||StringUtils.isBlank(secretkey)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","账户信息不全");
						res.put("payorderinfo","");
						return res;
					}
					//添加交易号记录流水
					Map<String,String> orderinfo = new HashMap<String,String>();
					orderinfo.put("out_trade_no", out_trade_no);
					orderinfo.put("orderno", orderno);
					orderinfo.put("paymenttype", "1");
					orderinfo.put("validatekey", secretkey);
					orderdao.addTradeNo4OrgOrder(orderinfo);
					
					String tempipadd = request.getRemoteAddr();
					String ipadd = tempipadd.indexOf(":")>=0?"0.0.0.0":tempipadd;
					orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
					String ordermoney = new java.text.DecimalFormat("#").format(orderamount*100);
					String wxbody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxbody"))?"好约车——订单付款":SystemConfig.getSystemProperty("wxbody");
					Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, chantno, wxbody, out_trade_no, ordermoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillWXPayed4Org", "APP",ipadd);
					Document doc = WXOrderUtil.createPreOrderInfo(prepayorderparam, WXOrderUtil.getSign(prepayorderparam, secretkey));
					Map<String,String> preres = WXOrderUtil.getPreOrderId(DocFunc.doc2String(doc), "https://api.mch.weixin.qq.com/pay/unifiedorder");
					if(preres!=null&&"0".equalsIgnoreCase(preres.get("status"))){
						String prepay_id = preres.get("prepay_id");
						Map<String,String> payorderparam = WXOrderUtil.createPayOrderParam(appid, chantno, prepay_id);
						String sign = WXOrderUtil.getSign(payorderparam, secretkey);
						payorderparam.put("sign", sign);
						payorderparam.put("out_trade_no", out_trade_no);
						res.put("payorderinfo", payorderparam);
					}else{
						res.put("status", Retcode.EXCEPTION.code);
						res.put("message", "支付异常");
						res.put("payorderinfo","");
					}
				}
			}else{
				//个人用户
				boolean isoptaxiorder = isTaxiOrder(ordertype);
				if(isoptaxiorder){
					//出租车支付
					return payOpTaxiOrder(orderno, paytype, request);
				}else{
					Map<String,Object> payinfo = dicdao.getPayInfo4Op();
					if(payinfo==null){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","账户信息不全");
						res.put("payorderinfo","");
						return res;
					}
					//网约车支付
					if("1".equalsIgnoreCase(paytype)){
						//支付宝充值
						String alipaystatus = (String) payinfo.get("alipaystatus");
						if(!"1".equals(alipaystatus)){
							res.put("status",Retcode.EXCEPTION.code);
							res.put("message","暂不支持支付宝支付");
							res.put("payorderinfo","");
							return res;
						}
						String appid = (String) payinfo.get("alipayappid");
						String alipubkey = (String) payinfo.get("alipaypublickey");
						String privatekey = (String) payinfo.get("alipayprivatekey");
						if(StringUtils.isBlank(appid)||StringUtils.isBlank(alipubkey)||StringUtils.isBlank(privatekey)){
							res.put("status",Retcode.EXCEPTION.code);
							res.put("message","账户信息不全");
							res.put("payorderinfo","");
							return res;
						}
						//添加交易号记录流水
						Map<String,String> orderinfo = new HashMap<String,String>();
						orderinfo.put("out_trade_no", out_trade_no);
						orderinfo.put("orderno", orderno);
						orderinfo.put("paymenttype", "0");
						orderinfo.put("validatekey", alipubkey);
						orderdao.addTradeNo4OpOrder(orderinfo);
						//创建支付信息
						PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
						double orderamount = order.getOrderamount();
						orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
						String zfbsubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbsubject"))?"好约车":SystemConfig.getSystemProperty("zfbsubject");
						zfbsubject += orderno;
						String zfbbody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbbody"))?"好约车付款":SystemConfig.getSystemProperty("zfbbody");
						Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", orderamount, zfbsubject, zfbbody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillZFBPayed4Op");
						String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
						String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
						String payorderinfo = payorderstr + "&" + sign;
						res.put("payorderinfo", payorderinfo);
					}else{
						//微信充值
						String wechatstatus = (String) payinfo.get("wechatstatus");
						if(!"1".equals(wechatstatus)){
							res.put("status",Retcode.EXCEPTION.code);
							res.put("message","暂不支持微信支付");
							res.put("payorderinfo","");
							return res;
						}
						String appid = (String) payinfo.get("wechatappid");
						String chantno = (String) payinfo.get("wechatmerchantno");
						String secretkey = (String) payinfo.get("wechatsecretkey");
						if(StringUtils.isBlank(appid)||StringUtils.isBlank(chantno)||StringUtils.isBlank(secretkey)){
							res.put("status",Retcode.EXCEPTION.code);
							res.put("message","账户信息不全");
							res.put("payorderinfo","");
							return res;
						}
						//添加交易流水记录
						Map<String,String> orderinfo = new HashMap<String,String>();
						orderinfo.put("out_trade_no", out_trade_no);
						orderinfo.put("orderno", orderno);
						orderinfo.put("paymenttype", "1");
						orderinfo.put("validatekey", secretkey);
						orderdao.addTradeNo4OpOrder(orderinfo);
						
						//创建支付信息
						String tempipadd = request.getRemoteAddr();
						String ipadd = tempipadd.indexOf(":")>=0?"0.0.0.0":tempipadd;
						PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
						double orderamount = order.getOrderamount();
						orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
						String ordermoney = new java.text.DecimalFormat("#").format(orderamount*100);
						String wxbody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxbody"))?"好约车——订单付款":SystemConfig.getSystemProperty("wxbody");
						Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, chantno, wxbody, out_trade_no, ordermoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillWXPayed4Op", "APP",ipadd);
						Document doc = WXOrderUtil.createPreOrderInfo(prepayorderparam, WXOrderUtil.getSign(prepayorderparam, secretkey));
						Map<String,String> preres = WXOrderUtil.getPreOrderId(DocFunc.doc2String(doc), "https://api.mch.weixin.qq.com/pay/unifiedorder");
						if(preres!=null&&"0".equalsIgnoreCase(preres.get("status"))){
							String prepay_id = preres.get("prepay_id");
							Map<String,String> payorderparam = WXOrderUtil.createPayOrderParam(appid, chantno, prepay_id);
							String sign = WXOrderUtil.getSign(payorderparam, secretkey);
							payorderparam.put("sign", sign);
							payorderparam.put("out_trade_no", out_trade_no);
							res.put("payorderinfo", payorderparam);
						}else{
							res.put("status", Retcode.EXCEPTION.code);
							res.put("message", "支付异常");
							res.put("payorderinfo","");
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("订单支付",e);
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", "支付异常");
		}
		return res;
	}

	/**
	 * 个人用户出租车订单微信和支付宝支付
	 * @param orderno
	 * @param paytype
	 * @return
	 */
	private Map<String, Object> payOpTaxiOrder(String orderno,String paytype,HttpServletRequest request) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			Map<String,Object> payinfo = dicdao.getPayInfo4Op();
			if(payinfo==null){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","账户信息不全");
				res.put("payorderinfo","");
				return res;
			}
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			//获取订单的交易号 (时间加上5位随机码)
			String out_trade_no = format.format(date)+UNID.getUNID();
			if("1".equalsIgnoreCase(paytype)){
				//支付宝
				String alipaystatus = (String) payinfo.get("alipaystatus");
				if(!"1".equals(alipaystatus)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","暂不支持支付宝支付");
					res.put("payorderinfo","");
					return res;
				}
				String appid = (String) payinfo.get("alipayappid");
				String alipubkey = (String) payinfo.get("alipaypublickey");
				String privatekey = (String) payinfo.get("alipayprivatekey");
				if(StringUtils.isBlank(appid)||StringUtils.isBlank(alipubkey)||StringUtils.isBlank(privatekey)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","账户信息不全");
					res.put("payorderinfo","");
					return res;
				}
				//添加交易号记录流水
				Map<String,String> orderinfo = new HashMap<String,String>();
				orderinfo.put("out_trade_no", out_trade_no);
				orderinfo.put("orderno", orderno);
				orderinfo.put("paymenttype", "0");
				orderinfo.put("validatekey", alipubkey);
				orderdao.addTradeNo4OpTaxiOrder(orderinfo);
				//创建支付信息
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("orderno", orderno);
				Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(param);
				double orderamount = parseDouble(optaxiorder.get("schedulefee"));
				String paymentmethod = (String) optaxiorder.get("paymentmethod");
				if("0".equalsIgnoreCase(paymentmethod)){
					//线上支付
					orderamount += parseDouble(optaxiorder.get("orderamount"));
				}
				orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
				String zfbsubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbsubject"))?"好约车":SystemConfig.getSystemProperty("zfbsubject");
				zfbsubject += orderno;
				String zfbbody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbbody"))?"好约车付款":SystemConfig.getSystemProperty("zfbbody");
				Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", orderamount, zfbsubject, zfbbody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillZFBPayed4Op");
				String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
				String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
				String payorderinfo = payorderstr + "&" + sign;
				res.put("payorderinfo", payorderinfo);
			}else{
				//微信支付
				String wechatstatus = (String) payinfo.get("wechatstatus");
				if(!"1".equals(wechatstatus)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","暂不支持微信支付");
					res.put("payorderinfo","");
					return res;
				}
				String appid = (String) payinfo.get("wechatappid");
				String chantno = (String) payinfo.get("wechatmerchantno");
				String secretkey = (String) payinfo.get("wechatsecretkey");
				if(StringUtils.isBlank(appid)||StringUtils.isBlank(chantno)||StringUtils.isBlank(secretkey)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","账户信息不全");
					res.put("payorderinfo","");
					return res;
				}
				//添加交易流水记录
				Map<String,String> orderinfo = new HashMap<String,String>();
				orderinfo.put("out_trade_no", out_trade_no);
				orderinfo.put("orderno", orderno);
				orderinfo.put("paymenttype", "1");
				orderinfo.put("validatekey", secretkey);
				orderdao.addTradeNo4OpTaxiOrder(orderinfo);
				//创建支付信息
				String tempipadd = request.getRemoteAddr();
				String ipadd = tempipadd.indexOf(":")>=0?"0.0.0.0":tempipadd;
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("orderno", orderno);
				Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(param);
				double orderamount = parseDouble(optaxiorder.get("schedulefee"));
				String paymentmethod = (String) optaxiorder.get("paymentmethod");
				if("0".equalsIgnoreCase(paymentmethod)){
					//线上支付
					orderamount += parseDouble(optaxiorder.get("orderamount"));
				}
				orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
				String ordermoney = new java.text.DecimalFormat("#").format(orderamount*100);
				String wxbody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxbody"))?"好约车——订单付款":SystemConfig.getSystemProperty("wxbody");
				Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, chantno, wxbody, out_trade_no, ordermoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillWXPayed4Op", "APP",ipadd);
				Document doc = WXOrderUtil.createPreOrderInfo(prepayorderparam, WXOrderUtil.getSign(prepayorderparam, secretkey));
				Map<String,String> preres = WXOrderUtil.getPreOrderId(DocFunc.doc2String(doc), "https://api.mch.weixin.qq.com/pay/unifiedorder");
				if(preres!=null&&"0".equalsIgnoreCase(preres.get("status"))){
					String prepay_id = preres.get("prepay_id");
					Map<String,String> payorderparam = WXOrderUtil.createPayOrderParam(appid, chantno, prepay_id);
					String sign = WXOrderUtil.getSign(payorderparam, secretkey);
					payorderparam.put("sign", sign);
					payorderparam.put("out_trade_no", out_trade_no);
					res.put("payorderinfo", payorderparam);
				}else{
					res.put("status", Retcode.EXCEPTION.code);
					res.put("message", "支付异常");
					res.put("payorderinfo","");
				}
			}
		}catch(Exception e){
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		return res;
	}

	private Map<String, Object> checkOrderValid(String usertoken, String orderno, String ordertype) {
		Map<String, Object> res = new HashMap<String,Object>();
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			if(StringUtils.isBlank(orderno)||StringUtils.isBlank(ordertype)){
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message", "参数不完整");
				return res;
			}
			PassengerOrder order = null;
			if(isOrgUser(usertoken)){
				order = orderdao.getOrderByOrderno4Org(orderno);
			}else{
				if(isTaxiOrder(ordertype)){
					//出租车订单
					return checkOpOrderValid(orderno);
				}
				order = orderdao.getOrderByOrderno4Op(orderno);
			}
			if(order==null){
				res.put("status", Retcode.ORDERNOTEXIT.code);
				res.put("message", Retcode.ORDERNOTEXIT.msg);
				return res;
			}
			if(!OrderState.SERVICEDONE.state.equalsIgnoreCase(order.getOrderstatus())){
				res.put("status", Retcode.INVALIDORDERSTATUS.code);
				res.put("message", "订单行程还未结束");
				return res;
			}
			if(PayState.PAYED.state.equalsIgnoreCase(order.getPaymentstatus())){
				res.put("status", Retcode.INVALIDORDERSTATUS.code);
				res.put("message", "订单已经支付过");
				return res;
			}
			res.put("order", order);
		}catch(Exception e){
			logger.error("检查订单状态出错", e);
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		return res;
	}
	
	
	/**
	 * 检查出租车订单是否可以支付
	 * @param orderno
	 * @return
	 */
	private Map<String, Object> checkOpOrderValid(String orderno) {
		Map<String, Object> res = new HashMap<String,Object>();
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("orderno", orderno);
			Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(param);
			if(optaxiorder!=null){
				String orderstatus = (String) optaxiorder.get("orderstatus");
				if(!OrderState.SERVICEDONE.state.equalsIgnoreCase(orderstatus)){
					res.put("status", Retcode.INVALIDORDERSTATUS.code);
					res.put("message", "订单行程还未结束");
					return res;
				}
				String paymentstatus = (String) optaxiorder.get("paymentstatus");
				if(PayState.PAYED.state.equalsIgnoreCase(paymentstatus)||PayState.DRIVERNOPAY.state.equalsIgnoreCase(paymentstatus)||PayState.PAYOVER.state.equalsIgnoreCase(paymentstatus)){
					res.put("status", Retcode.INVALIDORDERSTATUS.code);
					res.put("message", "订单已经支付过");
					return res;
				}
				res.put("order", optaxiorder);
			}else{
				res.put("status", Retcode.ORDERNOTEXIT.code);
				res.put("message", Retcode.ORDERNOTEXIT.msg);
				return res;
			}
		}catch(Exception e){
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 获取收款方的信息，主要是微信和支付宝信息
	 * @param type 0-运管方，1-租赁方
	 * @param companyid 租赁方时候的租赁公司id
	 * @return
	 */
	private Map<String,Object> getPayInfo(String type,String companyid){
		Map<String,Object> payinfo = null;
		if("1".equalsIgnoreCase(type)){
			//租赁方
			payinfo = dicdao.getPayInfo4LeByCompanyid(companyid);
		}else{
			//运管方
			payinfo = dicdao.getPayInfo4Op();
		}
		return payinfo;
	}
	
	/**
	 * 支付宝支付结果通知
	 * @param request
	 * @param response
	 */
	public void dillZFBPayed4Org(HttpServletRequest request,HttpServletResponse response){
		//根据支付结果，更改订单状态，并且返回支付宝“success”
		String res = "success";
		try{
			String result_code = request.getParameter("trade_status");
			if("TRADE_SUCCESS".equalsIgnoreCase(result_code)){
				//机构订单
				if(isZFBSignValid(request,"1")){
					String out_trade_no = request.getParameter("out_trade_no");
					//根据out_trade_no查询订单号，并且修改订单状态
                	Map<String, Object> orderparam = new HashMap<String,Object>();
                	orderparam.put("paymentstatus", "1");
                	orderparam.put("outtradeno", out_trade_no);
                	orderparam.put("paytype", "3");
                	orderdao.payed4OrgOrder(orderparam);
                	//更改交易流水
                	Map<String,Object> tradeparam = new HashMap<String,Object>();
                	tradeparam.put("outtradeno", out_trade_no);
                	tradeparam.put("tradeno", request.getParameter("trade_no"));
                	tradeparam.put("paymenttype", "0");
                	orderdao.updateTradeInfo4OrgOrder(tradeparam);
                	//3、记录
                	Map<String,Object> tradeinfo = orderdao.getPayTradeRecord4Org(out_trade_no);
                	String orderno = (String) tradeinfo.get("orderno");
                	PassengerOrder order = orderdao.getOrderByOrderno4Org(orderno);
            		Map<String,Object> expenses = new HashMap<String,Object>();
            		expenses.put("logid", GUIDGenerator.newGUID());
            		expenses.put("userid",order.getUserid());
            		expenses.put("companyid",order.getCompanyid());
            		expenses.put("expensetype","2");
            		expenses.put("money",order.getOrderamount());
            		expenses.put("remark","订单支付");
            		expenses.put("tradetype","1");
            		expenses.put("detailtype","1");
            		expenses.put("operateresult","0");
            		userdao.addExpenses4OrgSec(expenses);
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
	 * 支付宝支付结果通知
	 * @param request
	 * @param response
	 */
	public void dillZFBPayed4Op(HttpServletRequest request,HttpServletResponse response){
		//根据支付结果，更改订单状态，并且返回支付宝“success”
		String res = "success";
		try{
			String result_code = request.getParameter("trade_status");
			if("TRADE_SUCCESS".equalsIgnoreCase(result_code)){
				if(isZFBSignValid(request,"0")){
					//个人订单
					String out_trade_no = request.getParameter("out_trade_no");
					Map<String,Object> tradeinfo = orderdao.getPayTradeRecord4OpTaxi(out_trade_no);
					String userid = null;
					double amount = 0;
					if(tradeinfo!=null){
						//出租车订单
						//更改订单状态
						String orderno = (String) tradeinfo.get("orderno");
						Map<String, Object> orderparam = new HashMap<String,Object>();
	                	orderparam.put("paymentstatus", "1");
	                	orderparam.put("orderno", orderno);
	                	orderparam.put("paytype", "3");
	                	orderdao.payed4OpTaxiOrder(orderparam);
	                	//更改交易流水
	                	Map<String,Object> tradeparam = new HashMap<String,Object>();
	                	tradeparam.put("outtradeno", out_trade_no);
	                	tradeparam.put("tradeno", request.getParameter("trade_no"));
	                	tradeparam.put("paymenttype", "0");
	                	orderdao.updateTradeInfo4OpTaxiOrder(tradeparam);
	                	Map<String,Object> param = new HashMap<String,Object>();
	                	param.put("orderno", orderno);
	                	Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(param);
	                	userid = (String) optaxiorder.get("userid");
	                	amount = parseDouble(optaxiorder.get("schedulefee"));
	    				String paymentmethod = (String) optaxiorder.get("paymentmethod");
	    				if("0".equalsIgnoreCase(paymentmethod)){
	    					//线上支付
	    					amount += parseDouble(optaxiorder.get("orderamount"));
	    					//给司机钱包加钱并且加收入明细
							addMoney4Driver(optaxiorder);

							//添加记录
							Map<String,Object> expenses = new HashMap<String,Object>();
							expenses.put("logid", GUIDGenerator.newGUID());
							expenses.put("userid",userid);
							expenses.put("expensetype",2);
							expenses.put("money",amount);
							expenses.put("remark","支付宝支付");
							expenses.put("tradetype","1");
							expenses.put("detailtype","1");
							expenses.put("operateresult","0");
							userdao.addExpenses4OpSec(expenses);

//							//出租车返现
//							try{
//								Map<String,Object> awardparams = new HashMap<String,Object>();
//								awardparams.put("usertype","1");
//								awardparams.put("passengerphone",optaxiorder.get("passengerphone"));
//								awardparams.put("money",amount);
//								PeUser peuser = userdao.getPeUserById((String) optaxiorder.get("userid"));
//								awardparams.put("userphone",peuser.getAccount());
//								carserviceapi.dealRequestWithToken("/AwardPassenger/AwardPoint", HttpMethod.POST, null, awardparams, JSONObject.class);
//							}catch (Exception e){
//								logger.error("返现出错了",e);
//							}
	    				}else{
							//添加记录
							Map<String,Object> expenses = new HashMap<String,Object>();
							expenses.put("logid", GUIDGenerator.newGUID());
							expenses.put("userid",userid);
							expenses.put("expensetype",2);
							expenses.put("money",amount);
							expenses.put("remark","支付宝支付");
							expenses.put("tradetype","1");
							expenses.put("detailtype","1");
							expenses.put("operateresult","0");
							userdao.addExpenses4OpSec(expenses);
						}
	    				//优惠券处理
	    				dillCouponUseInfo4Op(optaxiorder);
	    				
//	    				//消费返券触发
//	    				Map<String,Object> opinfo = dicdao.getPayInfo4Op();
//						String companyid = (String) opinfo.get("id");
//						Map<String,Object> couponparams = new HashMap<String,Object>();
//						couponparams.put("type", CouponRuleTypeEnum.EXPENSE.value);
//						couponparams.put("userType", CouponRuleTypeEnum.PERSONAL_USER.value);
//						couponparams.put("companyId", companyid);
//						couponparams.put("cityCode", optaxiorder.get("oncity"));
//						couponparams.put("userId", userid);
//						couponparams.put("money", amount);
//						couponparams.put("version", "v3.0.1");
//						Const.grenerateCoupon(templateHelper, couponparams);
					}else{
						//网约车订单
						tradeinfo = orderdao.getPayTradeRecord4OpNetCar(out_trade_no);
						//根据out_trade_no查询订单号，并且修改订单状态
	                	Map<String, Object> orderparam = new HashMap<String,Object>();
	                	orderparam.put("paymentstatus", "1");
	                	orderparam.put("outtradeno", out_trade_no);
	                	orderparam.put("paytype", "3");
	                	orderdao.payed4OpOrder(orderparam);
	                	//更改交易流水
						Map<String,Object> tradeparam = new HashMap<String,Object>();
	                	tradeparam.put("outtradeno", out_trade_no);
	                	tradeparam.put("tradeno", request.getParameter("trade_no"));
	                	tradeparam.put("paymenttype", "0");
	                	orderdao.updateTradeInfo4OpOrder(tradeparam);
	                	
	                	String orderno = (String) tradeinfo.get("orderno");
	                	PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
	                	userid = order.getUserid();
	                	amount = parseDouble(order.getOrderamount());

						//添加记录
						Map<String,Object> expenses = new HashMap<String,Object>();
						expenses.put("logid", GUIDGenerator.newGUID());
						expenses.put("userid",userid);
						expenses.put("expensetype",2);
						expenses.put("money",amount);
						expenses.put("remark","支付宝支付");
						expenses.put("tradetype","1");
						expenses.put("detailtype","1");
						expenses.put("operateresult","0");
						userdao.addExpenses4OpSec(expenses);

						//优惠券处理
	    				dillCouponUseInfo4Op(order);
//						//网约车返现
//						try{
//							Map<String,Object> awardparams = new HashMap<String,Object>();
//							awardparams.put("usertype","1");
//							awardparams.put("passengerphone",order.getPassengerphone());
//							awardparams.put("money",amount);
//							PeUser peuser = userdao.getPeUserById(userid);
//							awardparams.put("userphone",peuser.getAccount());
//							carserviceapi.dealRequestWithToken("/AwardPassenger/AwardPoint", HttpMethod.POST, null, awardparams, JSONObject.class);
//						}catch (Exception e){
//							logger.error("返现出错了",e);
//						}
//	    				//消费返券触发
//	    				Map<String,Object> opinfo = dicdao.getPayInfo4Op();
//						String companyid = (String) opinfo.get("id");
//						Map<String,Object> couponparams = new HashMap<String,Object>();
//						couponparams.put("type", CouponRuleTypeEnum.EXPENSE.value);
//						couponparams.put("userType", CouponRuleTypeEnum.PERSONAL_USER.value);
//						couponparams.put("companyId", companyid);
//						couponparams.put("cityCode", order.getOncity());
//						couponparams.put("userId", userid);
//						couponparams.put("money", amount);
//						couponparams.put("version", "v3.0.1");
//						Const.grenerateCoupon(templateHelper, couponparams);
					}
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
	 * 给司机添加订单金额到钱包并且添加明细
	 * @param optaxiorder
	 */
	private void addMoney4Driver(Map<String, Object> optaxiorder) {
		if(optaxiorder==null){
			return ;
		}
		try{
			String paymentmethod = (String) optaxiorder.get("paymentmethod");
			if("0".equalsIgnoreCase(paymentmethod)){
				//线上支付
				double orderamount = parseDouble(optaxiorder.get("orderamount"));
				//给司机钱包加钱并且加收入明细
				String driverid = (String) optaxiorder.get("driverid");
				String companyid = (String) optaxiorder.get("companyid");
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("driverid",driverid);
				params.put("companyid",companyid);
				Map<String, Object> platinfo = dicdao.getPayInfo4Op();
				String platid = (String) platinfo.get("id");
				if(platid==null||platid.equals(companyid)){
					params.put("platformtype",0);
				}else{
					params.put("platformtype",1);
				}
				params.put("orderamount",orderamount);
				Map<String,Object> balanceinfo = userdao.getDriverBalance(params);
				if(balanceinfo==null){
					//司机不存在钱包，创建
					params.put("id",GUIDGenerator.newGUID());
					userdao.createDriverBalanceInfo(params);
				}else{
					//更新钱包
					userdao.updateDriverBalanceInfo(params);
				}
				Map<String,Object> infolog = new HashMap<String,Object>();
				infolog.put("id",GUIDGenerator.newGUID());
				infolog.put("companyid",companyid);
				infolog.put("driverid",driverid);
				infolog.put("tradetype","4");
				infolog.put("remark","");
				infolog.put("expensetype","5");
				infolog.put("amount",orderamount);
				infolog.put("detailtype","0");
				infolog.put("operateresult","0");
				infolog.put("platformtype",params.get("platformtype"));
				userdao.addDriverExpenses(infolog);
			}
		}catch (Exception e){
			logger.error("给司机添加钱包钱时出错",e);
		}
	}

	/**
	 * 验证支付宝签名正确与否
	 * @param type 0-"运管端","1"-租赁端
	 * @return
	 * @throws AlipayApiException 
	 */
	private boolean isZFBSignValid(HttpServletRequest request, String type) throws AlipayApiException {
		Map<String,String> pp = new HashMap<String,String>();
		Enumeration<String> pnames = request.getParameterNames();
		while(pnames.hasMoreElements()){
			String pname = pnames.nextElement();
			pp.put(pname, request.getParameter(pname));
		}
		String out_trade_no = request.getParameter("out_trade_no");
		Map<String,Object> tradeinfo = null;
		if("1".equalsIgnoreCase(type)){
			//给租赁端付钱
			tradeinfo = orderdao.getPayTradeRecord4Org(out_trade_no);
		}else{
			//给运管端付钱
			tradeinfo = orderdao.getPayTradeRecord4OpNetCar(out_trade_no);
			if(tradeinfo==null){
				tradeinfo = orderdao.getPayTradeRecord4OpTaxi(out_trade_no);
			}
		}
		if(tradeinfo==null){
			return false;
		}
		String publickey = (String) tradeinfo.get("privatekey");
		return AlipaySignature.rsaCheckV1(pp, publickey, AlipayConfig.input_charset);
	}

	/**
	 * 交易号
	 * @return
	 */
	private boolean isOrgOrder4ZFB(String outtradeno){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("outtradeno", outtradeno);
		params.put("paymenttype", "0");
		return orgdao.hasThisTradeNo(params);
	}
	
	/**
	 * 微信支付结果通知
	 * @param request
	 * @param response
	 */
	public void dillWXPayed4Org(HttpServletRequest request,HttpServletResponse response){
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
	    		            if(result_code!=null&&"SUCCESS".equalsIgnoreCase(result_code.getFirstChild().getNodeValue())){
	    		            	//attach存储订单号，根据订单号修改订单状态
				            	Node out_trade_no = doc.getElementsByTagName("out_trade_no").item(0);
		                    	String outtradeno = out_trade_no.getFirstChild().getNodeValue();
	                    		//更新订单状态
		                    	Map<String, Object> orderparam = new HashMap<String,Object>();
		                    	orderparam.put("paymentstatus", "1");
		                    	orderparam.put("outtradeno", outtradeno);
		                    	orderparam.put("paytype", "2");
		                    	orderdao.payed4OrgOrder(orderparam);
		                    	//更改订单流水
		                    	Map<String,Object> tradeparam = new HashMap<String,Object>();
		                    	tradeparam.put("outtradeno", outtradeno);
		                    	tradeparam.put("tradeno", doc.getElementsByTagName("transaction_id").item(0).getFirstChild().getNodeValue());
		                    	tradeparam.put("paymenttype", "1");
		                    	orderdao.updateTradeInfo4OrgOrder(tradeparam);
		                    	
		                    	//添加记录
		                    	Map<String,Object> tradeinfo = orderdao.getPayTradeRecord4Org(outtradeno);
		                    	String orderno = (String) tradeinfo.get("orderno");
		                    	PassengerOrder order = orderdao.getOrderByOrderno4Org(orderno);
		                		Map<String,Object> expenses = new HashMap<String,Object>();
		                		expenses.put("logid", GUIDGenerator.newGUID());
		                		expenses.put("userid",order.getUserid());
		                		expenses.put("companyid",order.getCompanyid());
		                		expenses.put("expensetype","1");
		                		expenses.put("money",order.getOrderamount());
		                		expenses.put("remark","订单支付");
		                		expenses.put("tradetype","1");
		                		expenses.put("detailtype","1");
		                		expenses.put("operateresult","0");
		                		userdao.addExpenses4OrgSec(expenses);
	    		            }else{
	    		                //签名失败记录日志并且返回失败
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
	
	/**
	 * 微信支付结果通知
	 * @param request
	 * @param response
	 */
	public void dillWXPayed4Op(HttpServletRequest request,HttpServletResponse response){
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
	    		            if(result_code!=null&&"SUCCESS".equalsIgnoreCase(result_code.getFirstChild().getNodeValue())){
	    		            	//attach存储订单号，根据订单号修改订单状态
				            	Node out_trade_no = doc.getElementsByTagName("out_trade_no").item(0);
		                    	String outtradeno = out_trade_no.getFirstChild().getNodeValue();
		                    	Map<String,Object> tradeinfo = orderdao.getPayTradeRecord4OpTaxi(outtradeno);
		    					String userid = null;
		    					double amount = 0;
		    					if(tradeinfo!=null){
		    						//出租车订单
		    						//更改订单状态
		    						String orderno = (String) tradeinfo.get("orderno");
		    						Map<String, Object> orderparam = new HashMap<String,Object>();
		    	                	orderparam.put("paymentstatus", "1");
		    	                	orderparam.put("orderno", orderno);
		    	                	orderparam.put("paytype", "2");
		    	                	orderdao.payed4OpTaxiOrder(orderparam);
		    	                	//更改交易流水
		    	                	Map<String,Object> tradeparam = new HashMap<String,Object>();
		    	                	String tradeno = doc.getElementsByTagName("transaction_id").item(0).getFirstChild().getNodeValue();
		    	                	tradeparam.put("outtradeno", outtradeno);
		    	                	tradeparam.put("tradeno", tradeno);
		    	                	tradeparam.put("paymenttype", "1");
		    	                	orderdao.updateTradeInfo4OpTaxiOrder(tradeparam);
		    	                	Map<String,Object> param = new HashMap<String,Object>();
		    	                	param.put("orderno", orderno);
		    	                	Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(param);
		    	                	userid = (String) optaxiorder.get("userid");
		    	                	amount = parseDouble(optaxiorder.get("schedulefee"));
		    	    				String paymentmethod = (String) optaxiorder.get("paymentmethod");
		    	    				if("0".equalsIgnoreCase(paymentmethod)){
		    	    					//线上支付
		    	    					amount += parseDouble(optaxiorder.get("orderamount"));
										//给司机钱包加钱并且加收入明细
										addMoney4Driver(optaxiorder);

										//添加记录
										Map<String,Object> expenses = new HashMap<String,Object>();
										expenses.put("logid", GUIDGenerator.newGUID());
										expenses.put("userid",userid);
										expenses.put("expensetype",1);
										expenses.put("money",amount);
										expenses.put("remark","微信支付");
										expenses.put("tradetype","1");
										expenses.put("detailtype","1");
										expenses.put("operateresult","0");
										userdao.addExpenses4OpSec(expenses);

//										//出租车返现
//										try{
//											Map<String,Object> awardparams = new HashMap<String,Object>();
//											awardparams.put("usertype","1");
//											awardparams.put("passengerphone",optaxiorder.get("passengerphone"));
//											awardparams.put("money",amount);
//											PeUser peuser = userdao.getPeUserById((String) optaxiorder.get("userid"));
//											awardparams.put("userphone",peuser.getAccount());
//											carserviceapi.dealRequestWithToken("/AwardPassenger/AwardPoint", HttpMethod.POST, null, awardparams, JSONObject.class);
//										}catch (Exception e){
//											logger.error("返现出错了",e);
//										}
		    	    				}else{
										//添加记录
										Map<String,Object> expenses = new HashMap<String,Object>();
										expenses.put("logid", GUIDGenerator.newGUID());
										expenses.put("userid",userid);
										expenses.put("expensetype",1);
										expenses.put("money",amount);
										expenses.put("remark","微信支付");
										expenses.put("tradetype","1");
										expenses.put("detailtype","1");
										expenses.put("operateresult","0");
										userdao.addExpenses4OpSec(expenses);
									}
		    	    				//优惠券使用
		    	    				dillCouponUseInfo4Op(optaxiorder);
		    	    				
//		    	    				//消费返券触发
//		    	    				Map<String,Object> opinfo = dicdao.getPayInfo4Op();
//		    						String companyid = (String) opinfo.get("id");
//	    							Map<String,Object> couponparams = new HashMap<String,Object>();
//	    							couponparams.put("type", CouponRuleTypeEnum.EXPENSE.value);
//	    							couponparams.put("userType", CouponRuleTypeEnum.PERSONAL_USER.value);
//	    							couponparams.put("companyId", companyid);
//	    							couponparams.put("cityCode", optaxiorder.get("oncity"));
//	    							couponparams.put("userId", userid);
//	    							couponparams.put("money", amount);
//	    							couponparams.put("version", "v3.0.1");
//	    							Const.grenerateCoupon(templateHelper, couponparams);
		    					}else{
		    						//网约车订单
		    						tradeinfo = orderdao.getPayTradeRecord4OpNetCar(outtradeno);
		    						//根据out_trade_no查询订单号，并且修改订单状态
		    	                	Map<String, Object> orderparam = new HashMap<String,Object>();
		    	                	orderparam.put("paymentstatus", "1");
		    	                	orderparam.put("outtradeno", outtradeno);
		    	                	orderparam.put("paytype", "2");
		    	                	orderdao.payed4OpOrder(orderparam);
		    	                	//更改交易流水
		    						Map<String,Object> tradeparam = new HashMap<String,Object>();
		    						String tradeno = doc.getElementsByTagName("transaction_id").item(0).getFirstChild().getNodeValue();
		    	                	tradeparam.put("outtradeno", outtradeno);
		    	                	tradeparam.put("tradeno", tradeno);
		    	                	tradeparam.put("paymenttype", "1");
		    	                	orderdao.updateTradeInfo4OpOrder(tradeparam);
		    	                	
		    	                	String orderno = (String) tradeinfo.get("orderno");
		    	                	PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
		    	                	userid = order.getUserid();
		    	                	amount = parseDouble(order.getOrderamount());

									//添加记录
									Map<String,Object> expenses = new HashMap<String,Object>();
									expenses.put("logid", GUIDGenerator.newGUID());
									expenses.put("userid",userid);
									expenses.put("expensetype",1);
									expenses.put("money",amount);
									expenses.put("remark","微信支付");
									expenses.put("tradetype","1");
									expenses.put("detailtype","1");
									expenses.put("operateresult","0");
									userdao.addExpenses4OpSec(expenses);

									//优惠券处理
									//优惠券处理
				    				dillCouponUseInfo4Op(order);
									//网约车返现
//									try{
//										Map<String,Object> awardparams = new HashMap<String,Object>();
//										awardparams.put("usertype","1");
//										awardparams.put("passengerphone",order.getPassengerphone());
//										awardparams.put("money",amount);
//										PeUser peuser = userdao.getPeUserById(userid);
//										awardparams.put("userphone",peuser.getAccount());
//										carserviceapi.dealRequestWithToken("/AwardPassenger/AwardPoint", HttpMethod.POST, null, awardparams, JSONObject.class);
//									}catch (Exception e){
//										logger.error("返现出错了",e);
//									}
//				    				//消费返券触发
//		    	    				Map<String,Object> opinfo = dicdao.getPayInfo4Op();
//		    						String companyid = (String) opinfo.get("id");
//	    							Map<String,Object> couponparams = new HashMap<String,Object>();
//	    							couponparams.put("type", CouponRuleTypeEnum.EXPENSE.value);
//	    							couponparams.put("userType", CouponRuleTypeEnum.PERSONAL_USER.value);
//	    							couponparams.put("companyId", companyid);
//	    							couponparams.put("cityCode", order.getOncity());
//	    							couponparams.put("userId", userid);
//	    							couponparams.put("money", amount);
//	    							couponparams.put("version", "v3.0.1");
//	    							Const.grenerateCoupon(templateHelper, couponparams);
		    					}
	    		            }else{
	    		            	//签名失败记录日志并且返回失败
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
		}
    }

	private boolean isOrgOrder4WX(String outtradeno){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("outtradeno", outtradeno);
		params.put("paymenttype", "1");
		return orgdao.hasThisTradeNo(params);
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

	/**
	 * 获取订单详细信息
	 * @param params
	 * @return
	 */
	public Map<String, Object> getOder(Map<String,Object> params) {
		Map<String, Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			String usertoken = (String) params.get("usertoken");
			String orderno = (String) params.get("orderno");
			String ordertype = (String) params.get("ordertype");
			String usetype = (String) params.get("usetype");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)){
				res.put("status", Retcode.FAILED.code);
				res.put("message", "参数不全");
				res.put("order", "");
				return res;
			}
			PassengerOrder orderinfo = null;
			if(isOrgUser(usertoken)){
				//机构用户
				orderinfo = orderdao.getOrderByOrderno4Org(orderno);
			}else{
				//个人用户
				orderinfo = orderdao.getOrderByOrderno4Op(orderno);
			}
			try{
				dillWithOrderInfo(orderinfo,usertoken);
			}catch(Exception e){
				logger.error("乘客端异常",e);
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message", Retcode.EXCEPTION.msg);
				res.put("order", "");
				return res;
			}
			res.put("order", orderinfo==null?"":orderinfo);
		}catch(Exception e){
			logger.error("获取订单信息出错", e);
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			res.put("order", "");
		}
		return res;
	}

	/**
	 * 获取司机评价
	 * @param usertoken
	 * @return
	 */
	public Map<String, Object> getComments(String usertoken) {
		Map<String, Object> res = new HashMap<String,Object>();
		List<String> info = dicdao.getComments();
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("info", info);
		return res;
	}

	/**
	 * 评价司机
	 * @param params
	 * @return
	 */
	public Map<String, Object> doComment(Map<String, Object> params) {
		Map<String, Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			String usertoken = (String) params.get("usertoken");
			String ordertype = (String) params.get("ordertype");
			String usetype = (String) params.get("usetype");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
				res.put("status", Retcode.FAILED.code);
				res.put("message", "参数不全");
				return res;
			}
			String driverid = null;
			if(isOrgUser(usertoken)){
				//机构用户
				orderdao.doComment4Org(params);
				PassengerOrder order = orderdao.getOrderByOrderno4Org((String)params.get("orderno"));
				if(order!=null){
					driverid = order.getDriverid();
				}
			}else{
				//个人用户
				if(isTaxiOrder(ordertype)){
					//出租车订单
					orderdao.doComment4OpTaxi(params);
					Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(params);
					if(optaxiorder!=null){
						driverid = (String) optaxiorder.get("driverid");
					}
				}else{
					//网约车订单
					orderdao.doComment4Op(params);
					String orderno = (String) params.get("orderno");
					PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
					if(order!=null){
						driverid = order.getDriverid();
					}
				}
			}
			if(StringUtils.isNotBlank(driverid)){
				Object userrateobj = params.get("userrate");
				int userrate =  (userrateobj instanceof String)?Integer.parseInt((String)userrateobj):(int)userrateobj;
				Map<String,Object> driverrateinfo = dicdao.getDriverRateInfo(driverid);
				Object avgrateobj = driverrateinfo.get("avgrate");
				Object ordercountobj = driverrateinfo.get("ordercount");
				double avgrate = 4.5;
				int ordercount = 1;
				if(avgrateobj!=null){
					avgrate = (double)avgrateobj;
				}
				if(ordercountobj!=null){
					ordercount = (int)ordercountobj;
				}
				if(ordercount<=0){
					ordercount = 1;
				}
				double newavgrate = (avgrate*(ordercount-1)+userrate)/ordercount;
				Map<String,Object> newrateparam = new HashMap<String,Object>();
				String newavgratestr = new java.text.DecimalFormat("#.000000").format(newavgrate);
				newrateparam.put("avgrate", newavgratestr);
				newrateparam.put("driverid", driverid);
				dicdao.updateDriverRate(newrateparam);
			}
		}catch(Exception e){
			logger.error("评价司机报错", e);
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		
		return res;
	}

	/**
	 * 查看轨迹
	 * @param params
	 * @return
	 */
	public Map<String,Object> getOrbit(Map<String,Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		try{
			PassengerOrder order = null;
			String usertoken = (String) params.get("usertoken");
			String orderno = (String) params.get("orderno");
			String ordertype = (String) params.get("ordertype");
			String usetype = (String) params.get("usetype");
			if(StringUtils.isBlank(orderno)||StringUtils.isBlank(usertoken)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
				res.put("status", Retcode.FAILED.code);
				res.put("message", "参数不全");
				return res;
			}
			if(isOrgUser(usertoken)){
				order = orderdao.getOrderByOrderno4Org(orderno);
			}else{
				if(isTaxiOrder(ordertype)){
					return getOrbit4OpTaxi(orderno,ordertype,usetype);
				}
				order = orderdao.getOrderByOrderno4Op(orderno);
			}
			if(order==null){
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message", "无法获取到订单信息");
				return res;
			}
			res = carserviceapi.dealRequestWithToken("/BaiduApi/GetTraceData/?orderno={orderno}&ordertype={ordertype}&usetype={usetype}", HttpMethod.GET, usertoken, null, Map.class, orderno,ordertype,usetype);
			if(res==null){
				res = new HashMap<String,Object>();
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message", "无法获取到轨迹信息");
				return res;
			}
		}catch(Exception e){
			logger.error("获取轨迹出错", e);
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", "无法获取到轨迹信息");
		}
		return res;
	}

	/**
	 * 获取个人出租车订单的轨迹
	 * @param orderno
	 * @return
	 */
	private Map<String, Object> getOrbit4OpTaxi(String orderno,String ordertype,String usetype) {
		Map<String,Object> res = new HashMap<String,Object>();
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("orderno", orderno);
			Map<String,Object> order = orderdao.getOrder4OpTaxi(param);
			if(order==null){
				res.put("status", Retcode.EXCEPTION.code);
				res.put("status", "无法获取到订单信息");
				return res;
			}
			String starttime = (String) order.get("starttime");
			if(StringUtils.isNotBlank(starttime)&&starttime.length()>19){
				starttime = starttime.substring(0, starttime.length()-2);
			}
			String endtime = (String) order.get("endtime");
			if(StringUtils.isNotBlank(endtime)&&endtime.length()>19){
				endtime = endtime.substring(0, endtime.length()-2);
			}
			res = carserviceapi.dealRequestWithToken("/BaiduApi/GetTraceData/?orderno={orderno}&ordertype={ordertype}&usetype={usetype}", HttpMethod.GET, null, null, Map.class, orderno,ordertype,usetype);
		}catch(Exception e){
			logger.error("获取个人出租车订单轨迹出错", e);
			res.put("status", Retcode.EXCEPTION.code);
			res.put("status", "无法获取到轨迹信息");
		}
		return res;
	}

	/**
	 * 查看用车规则
	 * @param usertoken
	 * @param city
	 * @return
	 */
	public Map<String,Object> getUseCarRules(String usertoken, String city) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		if(isOrgUser(usertoken)){
			//机构用户
			res.put("status", Retcode.OK.code);
			res.put("message", Retcode.OK.msg);
			//接机
			Map<String,Object> carserviceparams = new HashMap<String,Object>();
			carserviceparams.put("account", Const.getUserInfo(usertoken).get("account"));
			carserviceparams.put("city",city);
			carserviceparams.put("type",Const.ORDERTYPE_YUECHE);
			List<Map<String,Object>> carservice = new ArrayList<Map<String,Object>>();
			dillWithCarRules(carservice,orgdao.getUseCarRules(carserviceparams));
			Map<String,Object> pickupserviceparams = new HashMap<String,Object>();
			pickupserviceparams.put("account", Const.getUserInfo(usertoken).get("account"));
			pickupserviceparams.put("city",city);
			pickupserviceparams.put("type",Const.ORDERTYPE_JIEJI);
			List<Map<String,Object>> pickupservice = new ArrayList<Map<String,Object>>();
			dillWithCarRules(pickupservice,orgdao.getUseCarRules(pickupserviceparams));
			Map<String,Object> dropoffserviceparams = new HashMap<String,Object>();
			dropoffserviceparams.put("account", Const.getUserInfo(usertoken).get("account"));
			dropoffserviceparams.put("city",city);
			dropoffserviceparams.put("type",Const.ORDERTYPE_SONGJI);
			List<Map<String,Object>> dropoffservice = new ArrayList<Map<String,Object>>();
			dillWithCarRules(dropoffservice,orgdao.getUseCarRules(dropoffserviceparams));
			Map<String,Object> rules = new HashMap<String,Object>();
			rules.put("carservice", carservice);
			rules.put("pickupservice", pickupservice);
			rules.put("dropoffservice", dropoffservice);
			res.put("rules", rules);
		}else{
			//个人用户
			res.put("status", Retcode.INVALIDTOKEN.code);
			res.put("message", Retcode.INVALIDTOKEN.msg);
		}
		return res;
	}
	
	//结果处理
	private void dillWithCarRules(List<Map<String,Object>> targetinfo,List<Map<String,Object>> srcinfo){
		if(srcinfo==null||srcinfo.size()<=0){
			return ;
		}
		Map<String,Map<String,Object>> tempmap = new HashMap<String,Map<String,Object>>();
		for(int i=0;i<srcinfo.size();i++){
			Map<String,Object> tempobj = srcinfo.get(i);
			Map<String,Object> objinfo = tempmap.get(tempobj.get("companyid"));
			if(objinfo==null){
				objinfo = new HashMap<String,Object>();
				objinfo.put("logo", "现在没有这个东东");
				objinfo.put("companyname",tempobj.get("companyname"));
				List<String> names = new ArrayList<String>();
				objinfo.put("vehiclemodelnames", names);
				tempmap.put((String) tempobj.get("companyid"), objinfo);
			}
			List<String> names = (List<String>) objinfo.get("vehiclemodelnames");
			Object vehiname = tempobj.get("vehiclemodelname");
			if(!names.contains(vehiname)&&vehiname!=null){
				names.add((String) vehiname);
			}
		}
		targetinfo.addAll(tempmap.values());
	}

	/**
	 * 查看钱包信息
	 * @param params
	 * @return
	 */
	public Map<String, Object> getBalance(Map<String, Object> params) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		String usertoken = (String) params.get("usertoken");
		int iDisplayStart =  Integer.parseInt((String) params.get("iDisplayStart"));
		int iDisplayLength = Integer.parseInt((String)params.get("iDisplayLength"));
		if(isOrgUser(usertoken)){
			//机构用户
			String companyid = (String) params.get("companyid");
			Map<String,Object> param = new HashMap<String,Object>();
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("companyid", companyid);
			param.put("account", account);
			double totalmoney = userdao.getBalanceMoney4Org(param);
			param.put("iDisplayStart", iDisplayStart);
			param.put("iDisplayLength", iDisplayLength);
			List<Map<String,Object>> details = userdao.getBalanceDetail4Org(param);
			res.put("totalmoney", totalmoney);
			res.put("details", details);
		}else{
			//个人用户
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("account", Const.getUserInfo(usertoken).get("account"));
			param.put("iDisplayStart", iDisplayStart);
			param.put("iDisplayLength", iDisplayLength);
			double totalmoney = userdao.getBalanceMoney4Op(param);
			List<Map<String,Object>> details = userdao.getBalanceDetail4Op(param);
			res.put("totalmoney", totalmoney);
			res.put("details", details);
		}
		return res;
	}

	/**
	 * 获取消息列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getMessages(Map<String, Object> params) {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		String usertoken = (String) params.get("usertoken");
		String account =Const.getUserInfo(usertoken).get("account");
		params.put("account", account);
		if(!params.containsKey("aboveread")){
			params.put("aboveread", true);
		}
		Object aboveread = params.get("aboveread");
		if(aboveread instanceof String&&"false".equalsIgnoreCase((String)aboveread)){
			params.put("aboveread", false);
		}else if(aboveread instanceof String&&"true".equalsIgnoreCase((String)aboveread)){
			params.put("aboveread", true);
		}
		List<Map<String,Object>> messages = null;
		if(isOrgUser(usertoken)){
			//机构用户
			messages = userdao.getMessages4Org(params);
		}else{
			//个人用户
			messages = userdao.getMessages4Op(params);
		}
		List<Map<String,Object>> newmessages = dillWithMessages(messages);
		res.put("messages", newmessages);
		return res;
	}

	private List<Map<String, Object>> dillWithMessages(List<Map<String, Object>> messages) {
		if(messages==null||messages.size()<=0){
			return messages;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
		Date date = new Date();
		String todystr = format.format(date);
		List<Map<String, Object>> newmessages = new ArrayList<Map<String, Object>>();
		for(int i=0;i<messages.size();i++){
			Map<String, Object> message = messages.get(i);
			try{
				String timebef = (String) message.get("time");
				message.put("time", timebef.replaceAll(todystr, ""));
				message.put("content", JSONObject.fromObject(message.get("content")));
				newmessages.add(message);
			}catch(Exception e){
				logger.error("乘客端异常",e);
			}
		}
		return newmessages;
	}

	/**
	 * 更新用户消息状态
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateMessageState(Map<String, Object> params) {
		Map<String, Object> res = new HashMap<String, Object>();
		String usertoken = (String) params.get("usertoken");
		if(isOrgUser(usertoken)){
			//机构用户
			userdao.updateMessageState4Org(params);
		}else{
			//个人用户
			userdao.updateMessageStateOp(params);
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

	/**
	 * 获取常用地址
	 * @param params
	 * @return
	 */
	public Map<String,Object> getMostAddress(Map<String,Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String usertoken = (String) params.get("usertoken");
		String account = Const.getUserInfo(usertoken).get("account");
		params.put("account", account);
		List<MostAddress> mostaddrs = null;
//		if(!params.containsKey("iDisplayStart")){
//			//没传分页就显示100条
//			params.put("iDisplayStart", 0);
//			params.put("iDisplayLength", 100);
//		}
		if(isOrgUser(usertoken)){
			//机构用户
			mostaddrs = userdao.getMostAddress4Org(params);
		}else{
			//个人用户
			mostaddrs = userdao.getMostAddress4Op(params);
		}
		if(mostaddrs!=null&&mostaddrs.size()>0){
			for(int i=0;i<mostaddrs.size();i++){
				MostAddress maddress = mostaddrs.get(i);
				String city = maddress.getCity();
				String citycaption = dicdao.getCityCaption(city);
				if(StringUtils.isNotBlank(citycaption)){
					maddress.setCity(citycaption);	
				}
			}
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("mostaddrs", mostaddrs);
		return res;
	}
	
	/**
	 * 删除常用地址
	 * @param params
	 * @return
	 */
	public Map<String, Object> deleteMostAddress(Map<String, Object> params) {
		Map<String, Object> res = new HashMap<String,Object>();
		String usertoken = (String) params.get("usertoken");
		String addressid = (String) params.get("addressid");
		
		if(isOrgUser(usertoken)){
			//机构用户
			userdao.deleteMostAddress4Org(addressid);
		}else{
			//个人用户
			userdao.deleteMostAddress4Op(addressid);
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

	/**
	 * 添加常用地址
	 * @return
	 */
	public Map<String, Object> addMostAddress(Map<String,Object> params) {
		Map<String, Object> res = new HashMap<String,Object>();
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		String usertoken = (String) params.get("usertoken");
		String account = Const.getUserInfo(usertoken).get("account");
		String citycaption = (String) params.get("city");
		String cityno = dicdao.getCityNo(citycaption);
		if(StringUtils.isNotBlank(cityno)){
			params.put("city", cityno);
		}
		params.put("id", GUIDGenerator.newGUID());
		if(isOrgUser(usertoken)){
			//机构用户
			OrgUser  orguer= userdao.getUser4Org(account);
			if(orguer!=null){
				params.put("userid", orguer.getId());
			}
			boolean flag = userdao.hasAddress4Org(params);
			if(!flag){
				//不存在才添加
				userdao.addMostAddress4Org(params);
			}else{
				//已存在
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message", "该地址已被添加过！");
			}
		}else{
			//个人用户
			PeUser peuser = userdao.getUser4Op(account);
			if(peuser!=null){
				params.put("userid", peuser.getId());
			}
			boolean flag = userdao.hasAddress4Op(params);
			if(!flag){
				//不存在才添加
				userdao.addMostAddress4Op(params);
			}else{
				//已存在
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message", "该地址已被添加过！");
			}
		}
		return res;
	}

	/**
	 * 验证密码
	 * @param params
	 * @return
	 */
	public Map<String, Object> validatePwd(Map<String, Object> params) {
		Map<String, Object> res = new HashMap<String, Object>();
		String usertoken = (String) params.get("usertoken");
		String account = Const.getUserInfo(usertoken).get("account");
		String pwd = (String) params.get("oldpwd");
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		if(isOrgUser(usertoken)){
			//机构用户
			OrgUser  orguer= userdao.getUser4Org(account);
			if(!PasswordEncoder.matches(pwd, orguer.getUserPassword())){
				res.put("status", Retcode.PASSWORDWRONG.code);
				res.put("message", Retcode.PASSWORDWRONG.msg);
			}
		}else{
			//个人用户
			PeUser peuser = userdao.getUser4Op(account);
			if(!PasswordEncoder.matches(pwd, peuser.getUserpassword())){
				res.put("status", Retcode.PASSWORDWRONG.code);
				res.put("message", Retcode.PASSWORDWRONG.msg);
			}
		}
		return res;
	}

	/**
	 * 更新用户密码
	 * @param params
	 * @return
	 */
	public Map<String, Object> updatePwd(Map<String, Object> params) {
		Map<String, Object> res = new HashMap<String, Object>();
		String usertoken = (String) params.get("usertoken");
		String account = Const.getUserInfo(usertoken).get("account");
		params.put("account", account);
		params.put("pwd", PasswordEncoder.encode((String)params.get("pwd")));
		if(isOrgUser(usertoken)){
			//机构用户
			OrgUser user =  userdao.getUser4Org(account);
			params.put("userid",user.getId());
//			if(1==user.getWdpwdchangestate()){
//				//修改过提现密码只更改登录密码
			userdao.updatePwd4Org(params);
//			}else{
//				//没有修改过，那么提现密码也更改
//				userdao.updatePwdAWd4Org(params);
//			}
		}else{
			//个人用户
			PeUser user = userdao.getUser4Op(account);
			params.put("userid", user.getId());
//			if(1==user.getWdpwdchangestate()){
//				//修改过提现密码后只更改登录密码
			userdao.updatePwd4Op(params);
//			}else{
//				//没有修改过，那么提现密码也更改
//				userdao.updatePwdAWd4Op(params);
//			}
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

	/**
	 * 获取最新的版本信息
	 * @param params
	 * @return
	 */
	public Map<String, Object> getNewVersion(Map<String,Object> params){
		Map<String, Object> res = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("systemtype", params.get("systemtype"));
		SysVersion lastversion = dicdao.getNewVersion(param);
		if(lastversion!=null&&StringUtils.isNotBlank(lastversion.getPublishtime())){
			String timestr = lastversion.getPublishtime();
			if(timestr.length()>10){
				lastversion.setPublishtime(timestr.substring(0, 10));
			}
		}
		Map<String,Object> newinfo = new HashMap<String,Object>();
		if(lastversion!=null){
			newinfo.put("curversion",lastversion.getCurversion());
			newinfo.put("versionno",lastversion.getVersionno());
			newinfo.put("maxversionno",lastversion.getMaxversionno());
			newinfo.put("changelog",lastversion.getChangelog());
			newinfo.put("publishtime",lastversion.getPublishtime());
			Object curversion = params.get("curversion");
			if(curversion!=null){
				SysVersion currentversion = dicdao.getNewVersion(params);
				//在版本号前面加上小写v
				if(currentversion==null){
					params.put("curversion", "v"+curversion);
					currentversion = dicdao.getNewVersion(params);
				}
				//在版本号前面加上大写v
				if(currentversion==null){
					params.put("curversion", "V"+curversion);
					currentversion = dicdao.getNewVersion(params);
				}
				if(currentversion!=null){
					int cversionno = currentversion.getVersionno();
					int newmversionno = lastversion.getMaxversionno();
					int newversionno = lastversion.getVersionno();
					if(newversionno==cversionno){
						//最新版不需要更新
						newinfo.put("versionstatus",1);
					}else if(newmversionno>cversionno){
						//需要强制更新
						newinfo.put("versionstatus",3);
						String allpath = lastversion.getDownloadhref();
						if(allpath!=null&&!allpath.startsWith("http")){
							allpath = SystemConfig.getSystemProperty("fileserver")+File.separator+lastversion.getDownloadhref();
						}
						newinfo.put("downloadhref",allpath);
					}else{
						String allpath = lastversion.getDownloadhref();
						if(allpath!=null&&!allpath.startsWith("http")){
							allpath = SystemConfig.getSystemProperty("fileserver")+File.separator+lastversion.getDownloadhref();
						}
						//可以更新，可以不更新
						newinfo.put("versionstatus",2);
						newinfo.put("downloadhref",allpath);
					}
					//当前版本发布时间
					newinfo.put("curversion_current", currentversion.getCurversion());
					String timestr = currentversion.getPublishtime();
					if(timestr.length()>10){
						newinfo.put("publishtime_current", timestr.substring(0, 10));
					}
				}else{
					String allpath = lastversion.getDownloadhref();
					if(allpath!=null&&!allpath.startsWith("http")){
						allpath = SystemConfig.getSystemProperty("fileserver")+File.separator+lastversion.getDownloadhref();
					}
					//不知道当前版本，后台没有维护当前版本的app
					newinfo.put("versionstatus",0);
					newinfo.put("downloadhref",allpath);
				}
			}
		}else{
			//没有维护app信息
			newinfo.put("versionstatus",0);
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("newinfo", newinfo);
		return res;
	}
	
	public void getAgreement(HttpServletRequest req,HttpServletResponse res) {
		res.setContentType("text/html; charset=utf-8");
		String content = dicdao.getAgreement();
		try {
			if(StringUtils.isNotBlank(content)){
				res.getWriter().write(content);
			}else{
				res.getWriter().write("开发完成中。。。");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//		VelocityContext context = new VelocityContext();
//		StringBuffer content = new StringBuffer();
//		content.append("<b>导言</b><br/>");
//		content.append("欢迎使用XXXX好约车软件及服务！<br/>");
//		content.append("为使用XXXX软件（以下简称“本软件”）及服务，你应当阅读并遵守《XXX软件许可及服务协议》（以下简称“本协议”），以及《xx服务协议》和《xxx规则》。请你务必审慎阅读、充分理解各条款内容，特别是免除或者限制责任的条款，以及开通或使用某项服务的单独协议，并选择接受或不接受。限制、免责条款可能以加粗形式提示你注意。<br/>");
//		content.append("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx<br/>");
//		context.put("shortname", "XXXX软件许可及服务协议");
//		context.put("content", content.toString());
//		String vmpath = PassengerService.class.getClassLoader().getResource("agreement.vm").getPath();
//		try {
//			VelocityUtil.createTemplate(vmpath, res.getWriter(), context);
//		} catch (Exception e) {
//			logger.error("乘客端异常",e);
//		}
	}
	
	/**
	 * 订单额外显示信息
	 */
	private void dillWithOrderInfo(PassengerOrder order,String usertoken){
		if(order==null||usertoken==null){
			return ;
		}
		if(OrderEnum.ORDERTYPE_TAXI.code.equals(order.getOrdertype())){
			order.setOrderstyle("1");
		}else{
			order.setOrderstyle("0");
		}
		double amount = order.getOrderamount();
		String orderstatus = order.getOrderstatus();
		//行程结束但是钱是0就表明还未真正结束
//		if(OrderState.SERVICEDONE.state.equalsIgnoreCase(orderstatus)&&amount<=0){
//			order.setOrderstatus(OrderState.INSERVICE.state);
//		}
		//添加订单状态显示值
		addOrderStatusCaption4Order(order);
		//用户是否评论
		String userrate = order.getUserrate();
		if(StringUtils.isNotBlank(userrate)){
			order.setHasComment(true);
		}
		
		try{
			String oncity = order.getOncity();
			String oncitycaption = dicdao.getCityCaption(oncity);
			order.setOncity(oncitycaption);
			
			String offcity = order.getOffcity();
			String offcitycaption = dicdao.getCityCaption(offcity);
			order.setOffcity(offcitycaption);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//订单的计费信息
			addCostInfo(order, usertoken);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//待出发的距离出发时间
			addRemainTime(order, usertoken);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//添加取消订单的信息
			addCancelOrderInfo(order,usertoken);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//添加订单的客服信息
			addOrderServiceInfo(order,usertoken);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//订单车型logo
			addOrderCarLogoInfo(order,usertoken);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//添加司机信息
			addDriverPosition(usertoken,order);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			Date autocanceltime = order.getAutocanceltime();
			if(autocanceltime!=null){
				Date currenttime = new Date();
				int lefttime = (int) ((autocanceltime.getTime()-currenttime.getTime())/1000);
				order.setLefttime(lefttime<0?0:lefttime);
			}else{
				order.setLefttime(0);
			}
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
	}

	
	private void addRemainTime(PassengerOrder order, String usertoken) {
		if(OrderState.WAITSTART.state.equalsIgnoreCase(order.getOrderstatus())){
			Date usertime = order.getUsetime();
			long currenttime = System.currentTimeMillis();
			if(usertime!=null){
				int remain = (int)((usertime.getTime()-currenttime)/(1000*60));
				remain = remain<0?0:remain;
				order.setRemaintime(StringUtil.formatCostTime(remain));
			}
		}
	}

	private void addDriverPosition(String usertoken,PassengerOrder order) {
		String driverid = order.getDriverid();
		if(StringUtils.isNotBlank(driverid)){
			Map<String,Object> driverinfo = getDriverPosition(usertoken, driverid, order.getOrderno());
			if(driverinfo!=null){
				driverinfo.remove("status");
				driverinfo.remove("message");
				//将订单中的实时金额放入信息中
				driverinfo.put("orderamount", order.getOrderamount()+"元");
				order.setDriverposition(driverinfo);
			}
		}
	}

	private void addOrderCarLogoInfo(PassengerOrder order, String usertoken) {
		String logo = order.getSelectedmodellogo();
		if(StringUtils.isNotBlank(logo)){
			order.setSelectedmodellogo(SystemConfig.getSystemProperty("fileserver")+File.separator+logo);
		}
	}

	/**
	 * 添加订单的客服信息
	 * @param order
	 * @param usertoken
	 */
	private void addOrderServiceInfo(PassengerOrder order, String usertoken) {
		if(isOrgUser(usertoken)){
			//机构订单，查询每个租赁公司的客服信息
			String companyid = order.getCompanyid();
			Map<String,Object> companyinfo = orderdao.getLeCompanyInfo(companyid);
			order.setServicephone((String) companyinfo.get("servicesphone"));
		}else{
			//查询运管客服信息
			Map<String,Object> opinfo = orderdao.getOpInfo();
			order.setServicephone((String) opinfo.get("servcietel"));
		}
	}

	/**
	 * 添加取消订单的信息
	 * @param order
	 */
	private void addCancelOrderInfo(PassengerOrder order,String usertoken) {
		String cancelparty = order.getCancelparty();
		//取消订单才有取消人和取消时间
		if(OrderState.CANCEL.state.equalsIgnoreCase(order.getOrderstatus())&&StringUtils.isNotBlank(cancelparty)){
			switch (cancelparty) {
			case "0":
				//租赁端
				order.setCancelname("租赁公司管理员");
				break;
			case "1":
				//运管取消
				order.setCancelname("运管端管理员");
				break;
			case "2":
				//机构取消
				order.setCancelname("您");
				break;
			case "3":
				//app取消
				order.setCancelname("您");
				break;
			default:
				order.setCancelname("系统派单失败");
				break;
			}
		}
	}
	
	/**
	 * 添加司机信息
	 * @param order
	 */
	private void addDriverInfo(PassengerOrder order){
		//有司机接单
		String driverid = order.getDriverid();
		if(StringUtils.isNotBlank(driverid)){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("orderno",order.getOrderno());
			params.put("usetype",order.getUsetype());
			params.put("ordertype",order.getOrdertype());
			DriverInfo driverinfo = orderdao.getDriverInfo4New(params);
			order.setCancelname(driverinfo.getName());
		}
	}

	/**
	 * 添加取消方人名
	 * @param order
	 * @param usertoken
	 */
	private void addUserName(PassengerOrder order,String usertoken){
		String account = Const.getUserInfo(usertoken).get("account");
		if(isOrgUser(usertoken)){
			OrgUser user = userdao.getUser4Org(account);
			String calname = user.getNickName()!=null?user.getNickName():account;
			order.setCancelname(calname);
		}else{
			PeUser user = userdao.getUser4Op(account);
			String calname = user.getNickname()!=null?user.getNickname():account;
			order.setCancelname(calname);
		}
	}
	
	/**
	 * 添加订单的消费信息
	 * @param order
	 * @param usertoken
	 */
	private void addCostInfo(PassengerOrder order, String usertoken) {
		OrderCostParam params = new OrderCostParam();
		params.setHasunit(true);
		params.setOrderid(order.getOrderno());
		JSONObject costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, usertoken, params, JSONObject.class);
		if(costinfo!=null&&costinfo.getInt("status")==Retcode.OK.code){
			costinfo.remove("status");
			costinfo.remove("message");
			String amount = costinfo.getString("cost");
			if(StringUtils.isNotBlank(amount)){
				order.setOrderamount(StringUtil.formatNum(parseDouble(amount.replaceAll("元", "")), 1));
			}
			order.setStartprice(costinfo.getString("startprice"));
			order.setRangeprice(costinfo.getString("rangeprice"));
			order.setTimeprice(costinfo.getString("timeprice"));
			order.setRangecost(costinfo.getString("rangecost"));
			order.setTimecost(costinfo.getString("timecost"));
			order.setTimes(costinfo.getString("times"));
			order.setMileagestr(costinfo.getString("mileage"));
			String copyprice = order.getPricecopy();
			if(StringUtils.isNotBlank(copyprice)){
				OrderCost cost = StringUtil.parseJSONToBean(copyprice, OrderCost.class);
				String timetype = StringUtil.formatTimeType(cost);
				order.setTimetype(timetype);
				if(cost.getTimetype()!=0){
					//低速用时
					order.setTimes(StringUtil.formatCostTime(cost.getSlowtimes()));
				}
			}
			
		}
	}
	
	/**
	 * 添加订单状态的显示值
	 * @param order
	 */
	private void addOrderStatusCaption4Order(PassengerOrder order){
		switch (order.getOrderstatus()) {
		case "0":
			order.setOrderstatuscaption("待接单");	
			break;
		case "1":
			order.setOrderstatuscaption("待接单");	
			break;
		case "2":
			order.setOrderstatuscaption("待出发");	
			break;
		case "3":
			order.setOrderstatuscaption("行程中");	
			break;
		case "4":
			order.setOrderstatuscaption("行程中");	
			break;
		case "5":
			order.setOrderstatuscaption("行程中");	
			break;
		case "6":
			order.setOrderstatuscaption("行程中");	
			break;
		case "8":
			order.setOrderstatuscaption("已取消");	
			break;
		case "7":
			switch (order.getPaymentstatus()) {
			case "0":
				order.setOrderstatuscaption("待付款");
				break;
			default:
				order.setOrderstatuscaption("已完成");
				break;
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 添加订单列表中订单状态的显示值
	 * @param order
	 */
	private void dillWithOrderInfo(Order4List order){
		if(order==null){
			return ;
		}
		if(OrderEnum.ORDERTYPE_TAXI.code.equals(order.getOrdertype())){
			order.setOrderstyle("1");
		}else{
			order.setOrderstyle("0");
		}
		switch (order.getOrderstatus()) {
		case "0":
			order.setOrderstatuscaption("待接单");	
			break;
		case "1":
			order.setOrderstatuscaption("待接单");	
			break;
		case "2":
			order.setOrderstatuscaption("待出发");	
			break;
		case "3":
			order.setOrderstatuscaption("行程中");	
			break;
		case "4":
			order.setOrderstatuscaption("行程中");
			break;
		case "5":
			order.setOrderstatuscaption("行程中");	
			break;
		case "6":
			order.setOrderstatuscaption("行程中");	
			break;
		case "8":
			order.setOrderstatuscaption("已取消");
			String canceltime = order.getCanceltime();
			if(StringUtils.isNotBlank(canceltime)&&canceltime.length()>10){
				order.setCanceltime(canceltime.substring(0,canceltime.length()-5));
			}
			break;
		case "7":
			switch(order.getPaymentstatus()) {
				case "0":
					order.setOrderstatuscaption("待付款");
					break;
				default:
					order.setOrderstatuscaption("已完成");
					break;
			}
			break;
		default:
			break;
		}
		
		try{
			String oncity = order.getOncity();
			String oncitycaption = dicdao.getCityCaption(oncity);
			order.setOncity(oncitycaption);
			
			String offcity = order.getOffcity();
			String offcitycaption = dicdao.getCityCaption(offcity);
			order.setOffcity(offcitycaption);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		//用户是否评论
		String userrate = order.getUserrate();
		if(StringUtils.isNotBlank(userrate)){
			order.setHasComment(true);
		}
		
		String driverimg = order.getDriverimg();
		if(StringUtils.isNotBlank(driverimg)){
			order.setDriverimg(SystemConfig.getSystemProperty("fileserver")+File.separator+driverimg);
		}
		
		String usetype = order.getUsetype();
		String ordertype = order.getOrdertype();
		String ordertypecaption = "";
		if("0".equalsIgnoreCase(usetype)){
			ordertypecaption += "因公用车·";
		}else if("1".equalsIgnoreCase(usetype)){
			ordertypecaption += "因私用车·";
		}
		if("1".equalsIgnoreCase(ordertype)){
			ordertypecaption += "约车";
		}else if("2".equalsIgnoreCase(ordertype)){
			ordertypecaption += "接机";
		}else{
			ordertypecaption += "送机";
		}
		order.setOrdertypecaption(ordertypecaption);
		double mileage = order.getMileage();
		String mileagestr = StringUtil.formatNum(mileage/1000,1)+"公里";
		order.setMileagestr(mileagestr);
		String orderstatus = order.getOrderstatus();
//		if(OrderState.WAITTAKE.state.equals(orderstatus)||OrderState.MANTICSEND.state.equals(orderstatus)){
			Date autocanceltime = order.getAutocanceltime();
			if(autocanceltime!=null){
				Date currenttime = new Date();
				int lefttime = (int) ((autocanceltime.getTime()-currenttime.getTime())/1000);
				order.setLefttime(lefttime<0?0:lefttime);
			}else{
				order.setLefttime(0);
			}
//		}
//		try{
//			//界面显示的费用时长里程等显示一位小数
//			dillNumShow(order);
//		}catch(Exception e){
//			logger.error("乘客端异常",e);
//		}
	}

	/**
	 * 数字显示一位小数
	 * @param order
	 */
	private void dillNumShow(Order4List order) {
		double mileage = order.getMileage();
		order.setMileage(StringUtil.formatNum(mileage,1));
		String mileagestr = StringUtil.formatNum(mileage/1000,1)+"公里";
		order.setMileagestr(mileagestr);
		order.setEstimatedcost(StringUtil.formatNum(order.getEstimatedcost(),1));
		order.setEstimatedtime(StringUtil.formatNum(order.getEstimatedtime(),1));
		order.setEstimatedmileage(StringUtil.formatNum(order.getEstimatedmileage(),1));
		order.setOrderamount(StringUtil.formatNum(order.getOrderamount(),1));
	}

	/**
	 * 退出登录
	 * @param params
	 */
	public Map<String,Object> logout(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String usertoken = (String) params.get("usertoken");
		userdao.updateUserToken(usertoken);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

	/**
	 * 获取服务信息
	 * @param params
	 */
	public Map<String,Object> getServiceInfo(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		Map<String,Object> serviceinfo = new HashMap<String,Object>();
		res.put("serviceinfo", serviceinfo);
		String usertoken = (String) params.get("usertoken");
		if(isOrgUser(usertoken)){
			//机构用户
			String companyid = (String) params.get("companyid");
			Map<String,Object> companyinfo = orderdao.getLeCompanyInfo(companyid);
			if(companyinfo!=null){
				serviceinfo.put("servicephone", companyinfo.get("servicesphone")==null?"":companyinfo.get("servicesphone"));
			}else{
				serviceinfo.put("servicephone","");
			}
		}else{
			//个人用户
			Map<String,Object> opinfo = orderdao.getOpInfo();
			if(opinfo!=null){
				serviceinfo.put("servicephone", opinfo.get("servcietel")==null?"":opinfo.get("servcietel"));
			}else{
				serviceinfo.put("servicephone","");
			}
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}
	
//	/**
//	 * 获取登录前的公用服务信息
//	 * @param params
//	 * @return
//	 */
//	public Map<String,Object> getPubServiceInfo(Map<String, Object> params) {
//		Map<String,Object> res = new HashMap<String,Object>();addPubInfos(res)
//		Map<String,Object> serviceinfo = new HashMap<String,Object>();
//		res.put("serviceinfo", serviceinfo);
//		String usertype = (String) params.get("usertype");
//		if(USETYPE_PUBLIC.equalsIgnoreCase(usertype)){
//			//机构用户
//			String phone = (String) params.get("phone");
//			OrgUser orguser = userdao.getUser4Org(phone);
//			if("1".equalsIgnoreCase(orguser.getStatus())){
//				
//			}
//		}else{
//			//个人用户
//			Map<String,Object> opinfo = orderdao.getOpInfo();
//			if(opinfo!=null){
//				serviceinfo.put("servicephone", opinfo.get("servcietel")==null?"":opinfo.get("servcietel"));
//			}else{
//				serviceinfo.put("servicephone","");
//			}
//		}
//		res.put("status", Retcode.OK.code);
//		res.put("message", Retcode.OK.msg);
//		return res;
//	}

	/**
	 * 设置消息全部已读
	 * @param params
	 * @return
	 */
	public Map<String, Object> readMessageAll(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String usertoken = (String) params.get("usertoken");
		Map<String,String> userinfo = Const.getUserInfo(usertoken);
		String account = userinfo.get("account");
		if(isOrgUser(usertoken)){
			//机构用户
			userdao.readMessageAll4Org(account);
		}else{
			//个人用户
			userdao.readMessageAll4Op(account);
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

	/**
	 * 获取正在派单的订单
	 * @param usertoken
	 * @return
	 */
	public Map<String, Object> getUnderTakeOrder(String usertoken) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		Map<String,String> userinfo = Const.getUserInfo(usertoken);
		String account = userinfo.get("account");
		PassengerOrder orderinfo = null;
		Map<String,Object> params = new HashMap<String,Object>();
		if(isOrgUser(usertoken)){
			//机构用户
			 orderinfo = orderdao.getUnderTakeOrder4Org(account);
			 params.put("orderprop", "0");
		}else{
			//个人用户
			 orderinfo = orderdao.getUnderTakeOrder4Op(account);
			 params.put("orderprop", "1");
		}
		if(orderinfo!=null){
			params.put("orderno", orderinfo.getOrderno());
			JSONObject result = carserviceapi.dealRequestWithToken("/OrderApi/GetSendTime", HttpMethod.POST, usertoken, params, JSONObject.class);
			if(result.getInt("status")==0){
				Date createtime = orderinfo.getCreatetime();
				int sendinterval = result.getInt("sendinterval");
				//还有时限
				orderinfo.setSendinterval(sendinterval);
				Map<String,Object> orderobj = new HashMap<String,Object>();
				orderobj.put("orderno", orderinfo.getOrderno());
				orderobj.put("onaddress", orderinfo.getOnaddress());
				orderobj.put("onaddrlat", orderinfo.getOnaddrlat());
				orderobj.put("onaddrlng", orderinfo.getOnaddrlng());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				orderobj.put("createtime", format.format(createtime));
				orderobj.put("sendinterval", sendinterval);
				Date current = new Date();
				orderobj.put("currenttime", format.format(current));
				orderobj.put("waittime", (current.getTime()-createtime.getTime())/1000);
				res.put("order", orderobj);
			}
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

	/**
	 * 派单失败调用
	 * @param params
	 * @return
	 */
	public Map<String, Object> addOrderFail(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String usertoken = (String) params.get("usertoken");
		params.put("orderstate", OrderState.CANCEL.state);
		params.put("cancelparty", "3");
		if(isOrgUser(usertoken)){
			orderdao.updateOrderState4Org(params);
		}else{
			orderdao.updateOrderState4Op(params);
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

	public Map<String, Object> getNearDrivers(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		List<Map<String,Object>> dd = new ArrayList<Map<String,Object>>();
		res.put("drivers",dd);
		String usertoken = (String) params.get("usertoken");
		String city = (String) params.get("city");
		String companyid = (String) params.get("companyid"); 
		String cityno = dicdao.getCityNo(city);
		double lat = parseDouble(params.get("lat"));
		double lng = parseDouble(params.get("lng"));
		String orderstyle = (String) params.get("orderstyle");
		if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(city)||StringUtils.isBlank(orderstyle)||StringUtils.isBlank(companyid)){
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", "参数不完整");
			return res;
		}
		try{
			int radius = 5000;
			double[] rangeinfo = BaiduUtil.getRange(lng, lat, radius);
			PubDriverInBoundParam pubparam = new PubDriverInBoundParam(rangeinfo);
			pubparam.setCity(cityno);
			pubparam.setCompanyid(companyid);
			pubparam.setSimple(true);
			pubparam.setVehicletype(Integer.parseInt(orderstyle));
			if(isOrgUser(usertoken)){
				pubparam.setOrguser(true);
			}else{
				pubparam.setOrguser(false);
			}
			JSONObject driverinfos = carserviceapi.dealRequestWithToken("/PubDriver/GetLeDriverInBound", HttpMethod.POST, usertoken, pubparam,JSONObject.class);
			if(driverinfos!=null&&driverinfos.getInt("status")==0){
				int count = driverinfos.getInt("count");
				if(count>0){
					JSONArray drivers = driverinfos.getJSONArray("list");
					for(int i=0;i<drivers.size();i++){
						JSONObject drinfo = drivers.getJSONObject(i);
						Map<String,Object> d = new HashMap<String,Object>();
						d.put("lng", drinfo.get("lng"));
						d.put("lat", drinfo.get("lat"));
						d.put("gpsspeed", drinfo.get("gpsspeed"));
						d.put("gpsdirection", drinfo.get("gpsdirection"));
						dd.add(d);
					}
					res.put("drivers", dd);
				}
			}
		}catch(Exception e){
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", "获取司机时出错");
			return res;
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}

	/**
	 * 静默登录
	 * @param loginparam
	 * @return
	 */
	public Map<String, Object> defLogin(Map<String, Object> loginparam) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String usertoken = (String) loginparam.get("usertoken");
		if(StringUtils.isBlank(usertoken)){
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", "参数不完整");
		}
		Map<String,String> userinfo = Const.getUserInfo(usertoken);
		if(userinfo==null){
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", "参数不合法");
		}
		String useraccount = userinfo.get("account");
		String userid = null;
		String usertype = null;
		if(isOrgUser(usertoken)){
			OrgUser orguser = userdao.getUser4Org(useraccount);
			userid = orguser.getId();
			usertype = Const.USERTOKENTYPE_ORGUSER;
		}else{
			PeUser peuser = userdao.getUser4Op(useraccount);
			userid = peuser.getId();
			usertype = Const.USERTOKENTYPE_PEUSER;
		}
		
		String shouldpushdow = null;
		//更新或者创建usertoken
		Map<String,Object> pp = new HashMap<String,Object>();
		pp.put("userid", userid);
		pp.put("usertype", usertype);
		Map<String,Object> dbusertokeninfo = userdao.getUserTokenByUserId(pp);
		Map<String,Object> tokeninfo = new HashMap<String,Object>();
		if(dbusertokeninfo==null){
			tokeninfo.put("id", GUIDGenerator.newGUID());
		}else{
			String dbusertoken = (String) dbusertokeninfo.get("usertoken");
			if(StringUtils.isNotBlank(dbusertoken)&&!usertoken.equalsIgnoreCase(dbusertoken)){
				shouldpushdow = dbusertoken;
			}
		}
		tokeninfo.put("usertoken", usertoken);
		tokeninfo.put("userid", userid);
		tokeninfo.put("usertype", usertype);
		userdao.createOrUpdateUsertoken(tokeninfo);
		
		//挤下线
		if(StringUtils.isNotBlank(shouldpushdow)){
			UserMessage pushdow = new UserMessage(shouldpushdow);
			MessageUtil.sendMessage(pushdow);
		}
		
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}
	
	public Map<String, Object> getSign4ZFB(Map<String,Object> params){
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String signstr = (String) params.get("signstr");
		try{
			String sign = AlipaySignature.rsaSign(signstr, AlipayConfig.private_key , AlipayConfig.input_charset);
			res.put("sign", URLEncoder.encode(sign, "utf-8"));
			res.put("status", Retcode.OK.code);
			res.put("message", Retcode.OK.msg);
		}catch(Exception e){
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		
		return res;
	}
	
	public Map<String,Object> getSign4WX(Map<String,Object> params){
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		String signstr = (String) params.get("signstr");
		String[] signtemp = signstr.split("&");
		Map<String,String> Params = new HashMap<String,String>();
		for(int i=0;i<signtemp.length;i++){
			String pstr = signtemp[i];
			String[] values = pstr.split("=");
			if(values.length==2){
				Params.put(values[0], values[1]);
			}else{
				Params.put(values[0], "");
			}
		}
		//过滤空值、sign与sign_type参数,微信也需要排序可统一使用支付宝提供的工具类
    	Map<String, String> sParaNew = NotifyUtil.paraFilter(Params);
        //获取待签名字符串
        String preSignStr = NotifyUtil.createLinkString(sParaNew);
        String key = "192006250b4c09247ec02edce69f6a2d";
        String sign = MD5.MD5Encode(preSignStr+"&key="+key).toUpperCase();
		res.put("sign", sign);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}
	
	/**
	 * 获取qa
	 * @param req
	 * @param res
	 */
	public void getCommonQA(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/html; charset=utf-8");
		String usertoken = req.getParameter("usertoken");
		try {
			String qaid = null;
			if(isOrgUser(usertoken)){
				//机构用户
				qaid = "organ_agreement";
			}else{
				//个人用户
				qaid = "operate_agreement";
			}
			String commonqa = dicdao.getCommonQA(qaid);
			if(StringUtils.isNotBlank(commonqa)){
				res.getWriter().write(commonqa);
			}else{
				res.getWriter().write("开发完成中。。。");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取司机信息（新）
	 * @param params
	 * @return
	 */
	public Map<String,Object> getDriverInfo(Map<String, Object> params) {
		Map<String, Object> res = new HashMap<String,Object>();
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		String orderno = (String) params.get("orderno");
		String ordertype = (String) params.get("ordertype");
		String usetype = (String) params.get("usetype");
		if(StringUtils.isBlank(orderno)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message","参数不全");
			return res;
		}

		Map<String,Object> pp = new HashMap<String,Object>();
		if(!"2".equals(usetype)){
			//机构用户
			PassengerOrder orderinfo = orderdao.getOrderByOrderno4Org(orderno);
			pp.put("driverid",orderinfo.getDriverid());
			pp.put("vehicleid",orderinfo.getVehicleid());
		}else{
			Map<String,Object> orderinfo = null;
			if(isTaxiOrder(ordertype)){
				orderinfo = orderdao.getOrder4OpTaxi(params);
			}else{
				orderinfo = orderdao.getOrder4OpNetCar(params);
			}
			if(orderinfo!=null){
				pp.put("driverid",(String) orderinfo.get("driverid"));
				pp.put("vehicleid",(String) orderinfo.get("vehicleid"));
			}
		}
		DriverInfo driverinfo = orderdao.getDriverInfo4New(pp);
		if(driverinfo!=null){
			String img = driverinfo.getHeadportraitmax();
			if(StringUtils.isNotBlank(img)){
				driverinfo.setDriverimg(SystemConfig.getSystemProperty("fileserver")+File.separator+img);
			}
			String avgrate = driverinfo.getAvgrate();
			if(StringUtils.isNotBlank(avgrate)&&avgrate.indexOf(".")>=0&&(avgrate.indexOf(".")+2)<=avgrate.length()){
				driverinfo.setAvgrate(avgrate.substring(0, avgrate.indexOf(".")+2));
			}else if(StringUtils.isBlank(avgrate)){
				driverinfo.setAvgrate("0.0");
			}
		}
		res.put("driverinfo", driverinfo);
		return res;
	}
	
	/**
	 * 处理优惠券的使用信息
	 * @param userid
	 * @param orderno
	 */
	private void dillCouponUseInfo4Op(Map<String,Object> orderinfo){
		if(orderinfo==null){
			return ;
		}
		String orderno = (String) orderinfo.get("orderno");
		Map<String,Object> pp = new HashMap<String,Object>();
		pp.put("orderno", orderno);
		Map<String,Object> useinfo = userdao.getOrderCouponUseInfo(pp);
		if(useinfo!=null){
			String userid = (String) orderinfo.get("userid");
			double amount = parseDouble(orderinfo.get("orderamount"));
			String ordertype = (String) orderinfo.get("ordertype");
			double couponmoney = parseDouble(useinfo.get("couponmoney"));
			if(isTaxiOrder(ordertype)){
				//出租车
				String paymentmethod = (String) orderinfo.get("paymentmethod");
				if("0".equalsIgnoreCase(paymentmethod)){
					//线上支付
					amount += parseDouble(orderinfo.get("schedulefee"));
				}
			}
			if(couponmoney<=amount){
				useinfo.put("discountamount", couponmoney);
			}else{
				useinfo.put("discountamount", amount);
			}
			//使用优惠券
			userdao.useCoupon(useinfo);
			//更新优惠券的实际抵用金额
			userdao.updateCouponDiscountamount(useinfo);
			//优惠券使用明细
			String couponid = (String) useinfo.get("couponidref");
			Map<String,Object> couponinfo = userdao.getCouponInfo(couponid);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("id", GUIDGenerator.newGUID());
			params.put("userid", userid);
			params.put("couponidref", couponid);
			params.put("usetype", 2);
			params.put("amount", couponmoney);
			params.put("remark", couponinfo!=null?couponinfo.get("name"):"");
			params.put("lecompanyid", couponinfo.get("couponinfo"));
			params.put("platformtype", couponinfo.get("platformtype"));
			userdao.addCouponDetail(params);
		}
	}
	
	/**
	 * 处理优惠券的使用信息
	 * @param userid
	 * @param orderno
	 */
	private void dillCouponUseInfo4Op(PassengerOrder orderinfo){
		if(orderinfo==null){
			return ;
		}
		String orderno = orderinfo.getOrderno();
		Map<String,Object> pp = new HashMap<String,Object>();
		pp.put("orderno", orderno);
		Map<String,Object> useinfo = userdao.getOrderCouponUseInfo(pp);
		if(useinfo!=null){
			String userid = orderinfo.getUserid();
			double amount = parseDouble(orderinfo.getOrderamount());
			double couponmoney = parseDouble(useinfo.get("couponmoney"));
			if(couponmoney<=amount){
				useinfo.put("discountamount", couponmoney);
			}else{
				useinfo.put("discountamount", amount);
			}
			//使用优惠券
			userdao.useCoupon(useinfo);
			//更新优惠券的实际抵用金额
			userdao.updateCouponDiscountamount(useinfo);
			//优惠券使用明细
			String couponid = (String) useinfo.get("couponidref");
			Map<String,Object> couponinfo = userdao.getCouponInfo(couponid);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("id", GUIDGenerator.newGUID());
			params.put("userid", userid);
			params.put("couponidref", couponid);
			params.put("usetype", 2);
			params.put("amount", couponmoney);
			params.put("remark", couponinfo!=null?couponinfo.get("name"):"");
			params.put("lecompanyid", couponinfo.get("couponinfo"));
			params.put("platformtype", couponinfo.get("platformtype"));
			userdao.addCouponDetail(params);
		}
	}

	public void dillZFBPayed4OrgCancel(HttpServletRequest request, HttpServletResponse response) {
		//根据支付结果，更改订单状态，并且返回支付宝“success”
		String res = "success";
		try{
			String result_code = request.getParameter("trade_status");
			if("TRADE_SUCCESS".equalsIgnoreCase(result_code)){
				//机构订单
				if(isZFBSignValid(request,"1")){
					String out_trade_no = request.getParameter("out_trade_no");
					//根据out_trade_no查询订单号，并且修改订单状态
                	Map<String, Object> orderparam = new HashMap<String,Object>();
                	orderparam.put("paymentstatus", "1");
                	orderparam.put("outtradeno", out_trade_no);
                	orderparam.put("paytype", "3");
                	orderdao.payed4OrgOrder(orderparam);
                	//更改交易流水
                	Map<String,Object> tradeparam = new HashMap<String,Object>();
                	tradeparam.put("outtradeno", out_trade_no);
                	tradeparam.put("tradeno", request.getParameter("trade_no"));
                	tradeparam.put("paymenttype", "0");
                	orderdao.updateTradeInfo4OrgOrder(tradeparam);
                	//3、记录
                	Map<String,Object> tradeinfo = orderdao.getPayTradeRecord4Org(out_trade_no);
                	String orderno = (String) tradeinfo.get("orderno");
                	PassengerOrder order = orderdao.getOrderByOrderno4Org(orderno);
                	double amount = 0;
                	Map<String,Object> cancelinfo = orderdao.getCancelInfo4Org(orderno);
                	if(cancelinfo!=null){
                		amount = parseDouble(cancelinfo.get("cancelamount"));
                	}
            		Map<String,Object> expenses = new HashMap<String,Object>();
            		expenses.put("logid", GUIDGenerator.newGUID());
            		expenses.put("userid",order.getUserid());
            		expenses.put("companyid",order.getCompanyid());
            		expenses.put("expensetype","2");
            		expenses.put("money",amount);
            		expenses.put("remark","订单支付");
            		expenses.put("tradetype","1");
            		expenses.put("detailtype","1");
            		expenses.put("operateresult","0");
            		userdao.addExpenses4OrgSec(expenses);
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

	public void dillWXPayed4OrgCancel(HttpServletRequest request, HttpServletResponse response) {
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
	    		            if(result_code!=null&&"SUCCESS".equalsIgnoreCase(result_code.getFirstChild().getNodeValue())){
	    		            	//attach存储订单号，根据订单号修改订单状态
				            	Node out_trade_no = doc.getElementsByTagName("out_trade_no").item(0);
		                    	String outtradeno = out_trade_no.getFirstChild().getNodeValue();
	                    		//更新订单状态
		                    	Map<String, Object> orderparam = new HashMap<String,Object>();
		                    	orderparam.put("paymentstatus", "1");
		                    	orderparam.put("outtradeno", outtradeno);
		                    	orderparam.put("paytype", "2");
		                    	orderdao.payed4OrgOrder(orderparam);
		                    	//更改订单流水
		                    	Map<String,Object> tradeparam = new HashMap<String,Object>();
		                    	tradeparam.put("outtradeno", outtradeno);
		                    	tradeparam.put("tradeno", doc.getElementsByTagName("transaction_id").item(0).getFirstChild().getNodeValue());
		                    	tradeparam.put("paymenttype", "1");
		                    	orderdao.updateTradeInfo4OrgOrder(tradeparam);
		                    	
		                    	//添加记录
		                    	Map<String,Object> tradeinfo = orderdao.getPayTradeRecord4Org(outtradeno);
		                    	String orderno = (String) tradeinfo.get("orderno");
		                    	PassengerOrder order = orderdao.getOrderByOrderno4Org(orderno);
		                    	double amount = 0;
		                    	Map<String,Object> cancelinfo = orderdao.getCancelInfo4Org(orderno);
		                    	if(cancelinfo!=null){
		                    		amount = parseDouble(cancelinfo.get("cancelamount"));
		                    	}
		                		Map<String,Object> expenses = new HashMap<String,Object>();
		                		expenses.put("logid", GUIDGenerator.newGUID());
		                		expenses.put("userid",order.getUserid());
		                		expenses.put("companyid",order.getCompanyid());
		                		expenses.put("expensetype","1");
		                		expenses.put("money",amount);
		                		expenses.put("remark","订单支付");
		                		expenses.put("tradetype","1");
		                		expenses.put("detailtype","1");
		                		expenses.put("operateresult","0");
		                		userdao.addExpenses4OrgSec(expenses);
	    		            }else{
	    		                //签名失败记录日志并且返回失败
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

	public void dillZFBPayed4OpCancel(HttpServletRequest request, HttpServletResponse response) {
		//根据支付结果，更改订单状态，并且返回支付宝“success”
		String res = "success";
		try{
			String result_code = request.getParameter("trade_status");
			if("TRADE_SUCCESS".equalsIgnoreCase(result_code)){
				if(isZFBSignValid(request,"0")){
					//个人订单
					String out_trade_no = request.getParameter("out_trade_no");
					Map<String,Object> tradeinfo = orderdao.getPayTradeRecord4OpTaxi(out_trade_no);
					String userid = null;
					double amount = 0;
					if(tradeinfo!=null){
						//出租车订单
						//更改订单状态
						String orderno = (String) tradeinfo.get("orderno");
						Map<String, Object> orderparam = new HashMap<String,Object>();
	                	orderparam.put("paymentstatus", "1");
	                	orderparam.put("orderno", orderno);
	                	orderparam.put("paytype", "3");
	                	orderdao.payed4OpTaxiOrder(orderparam);
	                	//更改交易流水
	                	Map<String,Object> tradeparam = new HashMap<String,Object>();
	                	tradeparam.put("outtradeno", out_trade_no);
	                	tradeparam.put("tradeno", request.getParameter("trade_no"));
	                	tradeparam.put("paymenttype", "0");
	                	orderdao.updateTradeInfo4OpTaxiOrder(tradeparam);
	                	Map<String,Object> param = new HashMap<String,Object>();
	                	param.put("orderno", orderno);
	                	Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(param);
	                	userid = (String) optaxiorder.get("userid");
	                	Map<String,Object> cancelfee = orderdao.getCancelInfo4Optaxi(orderno);
	                	if(cancelfee!=null){
	                		amount = parseDouble(cancelfee.get("cancelamount"));
	                	}
						//添加记录
						Map<String,Object> expenses = new HashMap<String,Object>();
						expenses.put("logid", GUIDGenerator.newGUID());
						expenses.put("userid",userid);
						expenses.put("expensetype",2);
						expenses.put("money",amount);
						expenses.put("remark","支付宝支付");
						expenses.put("tradetype","1");
						expenses.put("detailtype","1");
						expenses.put("operateresult","0");
						userdao.addExpenses4OpSec(expenses);
					}else{
						//网约车订单
						tradeinfo = orderdao.getPayTradeRecord4OpNetCar(out_trade_no);
						//根据out_trade_no查询订单号，并且修改订单状态
	                	Map<String, Object> orderparam = new HashMap<String,Object>();
	                	orderparam.put("paymentstatus", "1");
	                	orderparam.put("outtradeno", out_trade_no);
	                	orderparam.put("paytype", "3");
	                	orderdao.payed4OpOrder(orderparam);
	                	//更改交易流水
						Map<String,Object> tradeparam = new HashMap<String,Object>();
	                	tradeparam.put("outtradeno", out_trade_no);
	                	tradeparam.put("tradeno", request.getParameter("trade_no"));
	                	tradeparam.put("paymenttype", "0");
	                	orderdao.updateTradeInfo4OpOrder(tradeparam);

	                	String orderno = (String) tradeinfo.get("orderno");
	                	PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
	                	userid = order.getUserid();
	                	Map<String,Object> cancelfee = orderdao.getCancelInfo4Opnetcar(orderno);
	                	if(cancelfee!=null){
	                		amount = parseDouble(cancelfee.get("cancelamount"));
	                	}
						//添加记录
						Map<String,Object> expenses = new HashMap<String,Object>();
						expenses.put("logid", GUIDGenerator.newGUID());
						expenses.put("userid",userid);
						expenses.put("expensetype",2);
						expenses.put("money",amount);
						expenses.put("remark","支付宝支付");
						expenses.put("tradetype","1");
						expenses.put("detailtype","1");
						expenses.put("operateresult","0");
						userdao.addExpenses4OpSec(expenses);
					}
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

	public void dillWXPayed4OpCancel(HttpServletRequest request, HttpServletResponse response) {
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
	    		            if(result_code!=null&&"SUCCESS".equalsIgnoreCase(result_code.getFirstChild().getNodeValue())){
	    		            	//attach存储订单号，根据订单号修改订单状态
				            	Node out_trade_no = doc.getElementsByTagName("out_trade_no").item(0);
		                    	String outtradeno = out_trade_no.getFirstChild().getNodeValue();
		                    	Map<String,Object> tradeinfo = orderdao.getPayTradeRecord4OpTaxi(outtradeno);
		    					String userid = null;
		    					double amount = 0;
		    					if(tradeinfo!=null){
		    						//出租车订单
		    						//更改订单状态
		    						String orderno = (String) tradeinfo.get("orderno");
		    						Map<String, Object> orderparam = new HashMap<String,Object>();
		    	                	orderparam.put("paymentstatus", "1");
		    	                	orderparam.put("orderno", orderno);
		    	                	orderparam.put("paytype", "2");
		    	                	orderdao.payed4OpTaxiOrder(orderparam);
		    	                	//更改交易流水
		    	                	Map<String,Object> tradeparam = new HashMap<String,Object>();
		    	                	String tradeno = doc.getElementsByTagName("transaction_id").item(0).getFirstChild().getNodeValue();
		    	                	tradeparam.put("outtradeno", outtradeno);
		    	                	tradeparam.put("tradeno", tradeno);
		    	                	tradeparam.put("paymenttype", "1");
		    	                	orderdao.updateTradeInfo4OpTaxiOrder(tradeparam);
		    	                	Map<String,Object> param = new HashMap<String,Object>();
		    	                	param.put("orderno", orderno);
		    	                	Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(param);
		    	                	userid = (String) optaxiorder.get("userid");
		    	                	Map<String,Object> cancelfee = orderdao.getCancelInfo4Optaxi(orderno);
		    	                	if(cancelfee!=null){
		    	                		amount = parseDouble(cancelfee.get("cancelamount"));
		    	                	}
									//添加记录
									Map<String,Object> expenses = new HashMap<String,Object>();
									expenses.put("logid", GUIDGenerator.newGUID());
									expenses.put("userid",userid);
									expenses.put("expensetype",1);
									expenses.put("money",amount);
									expenses.put("remark","微信支付");
									expenses.put("tradetype","1");
									expenses.put("detailtype","1");
									expenses.put("operateresult","0");
									userdao.addExpenses4OpSec(expenses);
		    					}else{
		    						//网约车订单
		    						tradeinfo = orderdao.getPayTradeRecord4OpNetCar(outtradeno);
		    						//根据out_trade_no查询订单号，并且修改订单状态
		    	                	Map<String, Object> orderparam = new HashMap<String,Object>();
		    	                	orderparam.put("paymentstatus", "1");
		    	                	orderparam.put("outtradeno", outtradeno);
		    	                	orderparam.put("paytype", "2");
		    	                	orderdao.payed4OpOrder(orderparam);
		    	                	//更改交易流水
		    						Map<String,Object> tradeparam = new HashMap<String,Object>();
		    						String tradeno = doc.getElementsByTagName("transaction_id").item(0).getFirstChild().getNodeValue();
		    	                	tradeparam.put("outtradeno", outtradeno);
		    	                	tradeparam.put("tradeno", tradeno);
		    	                	tradeparam.put("paymenttype", "1");
		    	                	orderdao.updateTradeInfo4OpOrder(tradeparam);
		    	                	
		    	                	String orderno = (String) tradeinfo.get("orderno");
		    	                	PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
		    	                	userid = order.getUserid();
		    	                	Map<String,Object> cancelfee = orderdao.getCancelInfo4Opnetcar(orderno);
		    	                	if(cancelfee!=null){
		    	                		amount = parseDouble(cancelfee.get("cancelamount"));
		    	                	}
									//添加记录
									Map<String,Object> expenses = new HashMap<String,Object>();
									expenses.put("logid", GUIDGenerator.newGUID());
									expenses.put("userid",userid);
									expenses.put("expensetype",1);
									expenses.put("money",amount);
									expenses.put("remark","微信支付");
									expenses.put("tradetype","1");
									expenses.put("detailtype","1");
									expenses.put("operateresult","0");
									userdao.addExpenses4OpSec(expenses);
		    					}
	    		            }else{
	    		            	//签名失败记录日志并且返回失败
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
		}
	}
	
}

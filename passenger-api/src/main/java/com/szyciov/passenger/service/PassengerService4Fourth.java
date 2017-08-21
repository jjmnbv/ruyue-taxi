package com.szyciov.passenger.service;

import java.io.File;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.alipay.util.OrderInfoUtil;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.entity.OrderCost;
import com.szyciov.entity.PubOrderCancelRule;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.CouponRuleTypeEnum;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.PubOrdercancelEnum;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.enums.coupon.CouponActivityEnum;
import com.szyciov.enums.coupon.CouponEnum;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.PeUser;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.passenger.Const;
import com.szyciov.passenger.dao.DictionaryDao;
import com.szyciov.passenger.dao.OpDao;
import com.szyciov.passenger.dao.OrderDao;
import com.szyciov.passenger.dao.OrgDao;
import com.szyciov.passenger.dao.UserDao;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.Order4List;
import com.szyciov.passenger.entity.PassengerOrder;
import com.szyciov.passenger.param.RegisterParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.passenger.util.VelocityUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.TemplateHelper4CarServiceApi;
import com.szyciov.util.TemplateHelper4OrgApi;
import com.szyciov.util.TemplateHelper4leaseApi;
import com.szyciov.util.UNID;
import com.szyciov.util.UserTokenManager;
import com.wx.DocFunc;
import com.wx.WXOrderUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("PassengerService4Fourth")
public class PassengerService4Fourth {
private static final Logger logger = Logger.getLogger(PassengerService4Sec.class);
	
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
	
	private int parseInt(Object value){
		if(value==null){
			return 0;
		}
		return Integer.parseInt(String.valueOf(value));
	}
	
	private double parseDouble(Object value){
		if(value==null){
			return 0;
		}
		return Double.parseDouble(String.valueOf(value));
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
	 * 获取钱包和优惠券的数目
	 * @param params
	 * @return
	 */
	public Map<String, Object> getAmountInfo(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
	    res.put("status", Retcode.OK.code);
	    res.put("message",Retcode.OK.msg);
	    String usertoken = (String) params.get("usertoken");
	    String city = (String) params.get("city");
		if(StringUtils.isBlank(usertoken)){
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message","参数不全");
			res.put("totalmoney",0);
			return res;
		}
		String account = Const.getUserInfo(usertoken).get("account");
        //用户余额（查询的是个人的）
		Map<String,Object> param = new HashMap<String,Object>();
        param.put("account", account);
        double totalmoney = userdao.getBalanceMoney4Op(param);
        res.put("totalmoney",totalmoney);
        //优惠券数目
        PeUser user = userdao.getUser4Op(account);
        Map<String,Object> couponparams = new HashMap<String,Object>();
        couponparams.put("userid", user.getId());
        if(StringUtils.isBlank(city)){
	    	params.put("city", dicdao.getCityNo(city));
		}
        int totalcount = userdao.getCouponCount(couponparams);
        res.put("totalcount",totalcount);
		return res;
	}

	/**
	 * 获取优惠券明细
	 * @param params
	 * @return
	 */
	public Map<String, Object> getCouponDetail(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
	    res.put("status", Retcode.OK.code);
	    res.put("message",Retcode.OK.msg);
	    String iDisplayStart = (String) params.get("iDisplayStart");
        String iDisplayLength = (String) params.get("iDisplayLength");
	    String usertoken = (String) params.get("usertoken");
	    String city = (String) params.get("city");
		if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(iDisplayStart)||StringUtils.isBlank(iDisplayLength)){
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message","参数不全");
			res.put("totalmoney",0);
			return res;
		}
	    //优惠券明细目前是针对的是个人用户
	    String account = Const.getUserInfo(usertoken).get("account");
	    PeUser user = userdao.getUser4Op(account);
	    params.put("userid", user.getId());
	    if(StringUtils.isBlank(city)){
	    	params.put("city", dicdao.getCityNo(city));
		}
	    List<Map<String,Object>> details = userdao.getCouponDetail(params);
        List<Map<String,Object>> coupons = dillWithCoupons(details);
        res.put("details",coupons);
        res.put("currentcount",coupons.size());
		return res;
	}

	/**
	 * 处理优惠券信息
	 * @param details
	 * @return
	 */
	private List<Map<String, Object>> dillWithCoupons(List<Map<String, Object>> details) {
		List<Map<String,Object>> coupons = new ArrayList<Map<String,Object>>();
		if(details==null||details.size()<=0){
			return coupons;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<details.size();i++){
			Map<String,Object> couponinfo = details.get(i);
			Map<String,Object> couponobj = new HashMap<String,Object>();
			couponobj.put("couponid", couponinfo.get("id"));
			couponobj.put("couponname", couponinfo.get("name"));
			int servicetype = parseInt(couponinfo.get("servicetype"));
			couponobj.put("coupontype", servicetype==1?"出租车":"网约车");
			couponobj.put("couponmoney", parseDouble(couponinfo.get("money")));
			Date starttime = (Date) couponinfo.get("outimestart");
			Date endtime = (Date) couponinfo.get("outtimeend");
			couponobj.put("coupontime", "有效期 "+format.format(starttime)+"  至 "+format.format(endtime));
			int usetype = parseInt(couponinfo.get("usetype"));
			if(usetype==2){
				//不限制城市
				couponobj.put("couponusedec", "限开通业务城市可用");
			}else{
				//处理限制使用的城市
				processUseCitys(couponobj,(String)couponinfo.get("id"));
			}
			//如果有默认选项就添加
			if(couponinfo.containsKey("isselect")){
				couponobj.put("isselect", couponinfo.get("isselect"));
			}
			coupons.add(couponobj);
		}
		return coupons;
	}

	/**
	 * 处理限制使用的城市
	 * @param couponobj
	 * @param string
	 */
	private void processUseCitys(Map<String, Object> couponobj, String couponid) {
		List<Map<String,Object>> cityinfos = userdao.getCouponUseCitys(couponid);
		if(cityinfos==null||cityinfos.size()<=0){
			couponobj.put("couponusedec", "没有可用的城市");
		}
		StringBuilder citybuilder = new StringBuilder();
		for(int i=0;i<cityinfos.size();i++){
			Map<String,Object> cityobj = cityinfos.get(i);
			String cityname = (String) cityobj.get("city");
			citybuilder.append(cityname);
			if(i!=cityinfos.size()-1){
				citybuilder.append("、");
			}
		}
		couponobj.put("couponusedec", "限"+citybuilder.toString()+"可用");
	}

	/**
	 * 呼气投诉列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getComplaints(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
	    res.put("status", Retcode.OK.code);
	    res.put("message",Retcode.OK.msg);
	    String usertoken = (String) params.get("usertoken");
		if(StringUtils.isBlank(usertoken)){
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message","参数不全");
			return res;
		}
		List<String> complaints = dicdao.getComplaints();
		if(complaints==null){
			complaints = new ArrayList<String>();
		}
		res.put("complaints", complaints);
		return res;
	}

	/**
	 * 投诉
	 * @param params
	 * @return
	 */
	public Map<String, Object> doComplaint(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
	    res.put("status", Retcode.OK.code);
	    res.put("message",Retcode.OK.msg);
	    String usertoken = (String) params.get("usertoken");
	    String complaints = (String) params.get("complaints");
	    String orderno = (String) params.get("orderno");
	    String ordertype = (String) params.get("ordertype");
	    String usetype = (String) params.get("usetype");
		if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(complaints)||StringUtils.isBlank(orderno)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message","参数不全");
			return res;
		}
		Map<String,Object> complaintobj = userdao.getComplaintbyOrderno(orderno);
		if(complaintobj==null){
			Map<String,Object> pp = new HashMap<String,Object>();
			pp.put("id", GUIDGenerator.newGUID());
			pp.put("orderno", orderno);
			pp.put("complaintcontent", complaints);
			pp.put("processstatus", 0);
			if(!"2".equals(usetype)){
				//机构订单
				Map<String,Object> orderinfo = orderdao.getOrder4OrgNetCar(params);
				pp.put("platformtype", 1);
				pp.put("leasecompanyid", orderinfo.get("companyid"));
				pp.put("usertype", 0);
				pp.put("ordertype", 0);
				pp.put("userid", orderinfo.get("userid"));
			}else{
				pp.put("platformtype", 0);
				pp.put("usertype", 1);
				//个人订单
				if(isTaxiOrder(ordertype)){
					//出租车
					Map<String,Object> orderinfo = orderdao.getOrder4OpTaxi(params);
					pp.put("leasecompanyid", orderinfo.get("companyid"));
					pp.put("ordertype", 1);
					pp.put("userid", orderinfo.get("userid"));
				}else{
					//网约车
					Map<String,Object> orderinfo = orderdao.getOrder4OpNetCar(params);
					pp.put("leasecompanyid", orderinfo.get("companyid"));
					pp.put("ordertype", 0);
					pp.put("userid", orderinfo.get("userid"));
				}
			}
			userdao.doComplaint(pp);
		}
		return res;
	}

	/**
	 * 邀请注册
	 * @param params
	 * @return
	 */
	public Map<String, Object> inviteUser(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
	    res.put("status", Retcode.OK.code);
	    res.put("message",Retcode.OK.msg);
	    String usertoken = (String) params.get("usertoken");
	    String inviteephone = (String) params.get("phone");
		if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(inviteephone)){
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message","参数不全");
			return res;
		}
		String inviterphone = Const.getUserInfo(usertoken).get("account");
		Map<String,Object> pp = new HashMap<String,Object>();
		pp.put("inviteephone", inviteephone);
		pp.put("inviterphone", inviterphone);
		PeUser peuser = userdao.getUser4Op(inviteephone);
		//因为机构用户和个人用户统一了，只要有个人用户就可以知道用户是否注册
		if(peuser!=null){
			res.put("status", Retcode.FAILED.code);
			res.put("message", "当前用户已注册，请邀请其他用户");
			return res;
		}
		//将已过期的数据置为已过期
		userdao.updateExpireInviteInfos();
		//获取未注册的邀请信息
		Map<String,Object> inviteinfo = userdao.getInviteInfoByInvitee(inviteephone);
		if(inviteinfo!=null){
			res.put("status", Retcode.FAILED.code);
			res.put("message", "当前用户已被邀请，请邀请其他用户");
			return res;
		}
		String invitecode = UNID.getUNID4SZ(null, 4);
		pp.put("invitecode", invitecode);
		pp.put("id", GUIDGenerator.newGUID());
		//添加邀请信息
		userdao.addInviteInfo(pp);
		//发送邀请信息
		String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.passenger.service.PassengerService4Fourth.inviteuser", invitecode);
		List<String> userids = new ArrayList<String>();
		userids.add(inviteephone);
		UserMessage usermessage = new UserMessage(userids, content, UserMessage.GETSMSCODE);
		MessageUtil.sendMessage(usermessage);
		return res;
	}

	/**
	 * 4.0.1注册邀请注册和发券
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
			addUserInfo(res,registerparam.getUuid(), peuser);
			try{
				Map<String,Object> opinfo = dicdao.getPayInfo4Op();
				String companyid = (String) opinfo.get("id");
				//1.注册发券接口调用
				Map<String,Object> registcouponparam = new HashMap<String,Object>();
				registcouponparam.put("type", CouponRuleTypeEnum.REGISTER.value);
				registcouponparam.put("userType", CouponRuleTypeEnum.PERSONAL_USER.value);
				registcouponparam.put("companyId", companyid);
				registcouponparam.put("cityCode", citycode);
				registcouponparam.put("userId", userid);
				registcouponparam.put("version", "v3.0.1");
				registcouponparam.put("userPhone",phone);
				Const.grenerateCoupon(templateHelper, registcouponparam);
				//邀请人已注册
				//将已过期的数据置为已过期
				//2.邀请注册接口调用
				userdao.updateExpireInviteInfos();
				Map<String,Object> invitinfo = userdao.getInviteInfoByInvitee(phone);
				if(invitinfo!=null){
					Map<String,Object> inviteparam = new HashMap<String,Object>();
					inviteparam.put("inviteephone", phone);
					inviteparam.put("invitecode", registerparam.getInvitecode());
					inviteparam.put("invitestate", 1);
					userdao.updateInviteState(inviteparam);
					//判断邀请用户是否可以发券
					String inviterphone = (String) invitinfo.get("inviterphone");
					PeUser user = userdao.getUser4Op(inviterphone);
					if(user!=null){
						Map<String,Object> inviteregistcouponparam = new HashMap<String,Object>();
						inviteregistcouponparam.put("type", CouponRuleTypeEnum.INVITE.value);
						inviteregistcouponparam.put("userType", CouponRuleTypeEnum.PERSONAL_USER.value);
						inviteregistcouponparam.put("companyId", companyid);
						inviteregistcouponparam.put("cityCode", citycode);
						inviteregistcouponparam.put("userId", user.getId());
						inviteregistcouponparam.put("version", "v3.0.1");
						registcouponparam.put("userPhone",inviterphone);
						Const.grenerateCoupon(templateHelper, inviteregistcouponparam);
					}
				}
			}catch(Exception e){
				logger.error("注册优惠券地方出差错",e);
			}
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
		//tags.add("1");
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

	/**
	 * 获取个人订单详情
	 * @param param
	 * @return
	 */
	public Map<String, Object> getOder4Op(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			String orderno = (String) param.get("orderno");
			String ordertype = (String) param.get("ordertype");
			String usetype = (String) param.get("usetype");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)||StringUtils.isBlank(usetype)||StringUtils.isBlank(ordertype)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("order",new HashMap<>());
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("account", account);
			if(!"2".equals(usetype)){
				//机构用户
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","这个接口是给个人端用的");
				res.put("order",new HashMap<>());
			}else if("2".equals(usetype)){
				//个人用户
				Map<String,Object> orderinfo = null;
				if(isTaxiOrder(ordertype)){
					orderinfo = orderdao.getOrder4OpTaxi(param);
				}else{
					orderinfo = orderdao.getOrder4OpNetCar(param);
				}
				if(orderinfo==null){
					orderinfo = new HashMap<>();
				}else{
					dillOrder(orderinfo,usertoken,true);
				}
				res.put("order",orderinfo);
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("order",new HashMap<>());
		}
		return res;
	}
	
	/**
	 * 判断是否是taxiorder
	 * @param orderno
	 * @return
	 */
	private boolean isTaxiOrder(String ordertype){
		return OrderEnum.ORDERTYPE_TAXI.code.equals(ordertype);
	}
	
	/**
	 * 处理订单信息
	 * @param order
	 */
	private void dillOrder(Map<String, Object> orderinfo,String usertoken,boolean isdetail){
		try{
			double mileage = parseDouble(orderinfo.get("mileage"));
			orderinfo.put("mileagestr", StringUtil.formatNum(mileage/1000,1)+"公里");
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		if(isdetail){
			try{
				//订单的计费信息
				addCostInfo(orderinfo, usertoken);
			}catch(Exception e){
				logger.error("乘客端异常",e);
			}
			
			//详情的时候才加入的属性
			try{
				//添加司机信息
				addDriverPosition(orderinfo,usertoken);
			}catch(Exception e){
				logger.error("乘客端异常",e);
			}
			
			try{
				//添加取消订单的信息
				addCancelOrderInfo(orderinfo,usertoken);
			}catch(Exception e){
				logger.error("乘客端异常",e);
			}
			
			try{
				//待出发的距离出发时间
				addRemainTime(orderinfo, usertoken);
			}catch(Exception e){
				logger.error("乘客端异常",e);
			}
		}
		try{
			//用户是否评论
			String userrate = (String) orderinfo.get("userrate");
			if(StringUtils.isNotBlank(userrate)){
				orderinfo.put("hasComment", true);
			}else{
				orderinfo.put("hasComment", false);
			}
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			//上下车城市显示值
			String oncity = (String) orderinfo.get("oncity");
			String oncitycaption = dicdao.getCityCaption(oncity);
			orderinfo.put("oncity", oncitycaption==null?"":oncitycaption);
			
			String offcity = (String) orderinfo.get("offcity");
			String offcitycaption = dicdao.getCityCaption(offcity);
			orderinfo.put("offcity", offcitycaption==null?"":offcitycaption);
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		try{
			String orderstatus = (String) orderinfo.get("orderstatus");
			if(OrderState.CANCEL.state.equals(orderstatus)){
				String ordertype = (String) orderinfo.get("ordertype");
				if(isTaxiOrder(ordertype)){
					//出租车
					//取消就去查看这个订单有没有取消费
					Map<String,Object> cancelinfo = orderdao.getCancelInfo4Optaxi((String)orderinfo.get("orderno"));
					if(cancelinfo!=null){
						orderinfo.put("cancelfee",parseDouble(cancelinfo.get("cancelamount")));
						String cancelrule = (String) cancelinfo.get("cancelrule");
						if(StringUtils.isBlank(cancelrule)){
							orderinfo.put("hascancelrule", false);
						}else{
							orderinfo.put("hascancelrule", true);
						}
					}else{
						orderinfo.put("hascancelrule", false);
					}
				}else{
					//个人网约车
					//取消就去查看这个订单有没有取消费
					Map<String,Object> cancelinfo = orderdao.getCancelInfo4Opnetcar((String)orderinfo.get("orderno"));
					if(cancelinfo!=null){
						orderinfo.put("cancelfee",parseDouble(cancelinfo.get("cancelamount")));
						String cancelrule = (String) cancelinfo.get("cancelrule");
						if(StringUtils.isBlank(cancelrule)){
							orderinfo.put("hascancelrule", false);
						}else{
							orderinfo.put("hascancelrule", true);
						}
					}else{
						orderinfo.put("hascancelrule", false);
					}
				}
			}
		}catch(Exception e){
			logger.error("乘客端异常",e);
		}
		
		//获取实际支付金额
		try{
			String orderno = (String) orderinfo.get("orderno");
			String paymentstatus = (String) orderinfo.get("paymentstatus");
			if(PayState.PAYED.state.equals(paymentstatus)||PayState.DRIVERNOPAY.state.equals(paymentstatus)){
				//都支付完成的才有realpayamount
				String orderstatus = (String) orderinfo.get("orderstatus");
				if(OrderState.SERVICEDONE.state.equals(orderstatus)){
					//行程结束的
					Map<String,Object> cpinfo = orderdao.getOrderCouponInfo(orderno);
					if(cpinfo!=null&&cpinfo.size()>0){
						String ordertype = (String) orderinfo.get("ordertype");
						double orderamount = parseDouble(orderinfo.get("orderamount"));
						if(isTaxiOrder(ordertype)){
							//出租车
							String paymentmethod = (String) orderinfo.get("paymentmethod");
							if("0".equals(paymentmethod)){
								//线上支付
								orderamount += parseDouble(orderinfo.get("schedulefee"));
							}
						}
						double discountamount = parseDouble(cpinfo.get("discountamount"));
						if(orderamount<=discountamount){
							orderinfo.put("realpayamount", 0.0);
						}else{
							orderinfo.put("realpayamount", new BigDecimal(orderamount).subtract(new BigDecimal(discountamount)).doubleValue());
						}
					}else{
						orderinfo.put("realpayamount", orderinfo.get("orderamount"));
					}
				}else{
					orderinfo.put("realpayamount", orderinfo.get("cancelfee"));
				}
			}
		}catch(Exception e){
			logger.error("获取实际支付金额",e);
		}
		
		//地址路径加上绝对路径
		addAbsolutPath(orderinfo);
		//添加订单的显示类型
		addOrderTypeCaption(orderinfo);
		//处理时间类型的最后的点
		dillTimeField(orderinfo);
		//处理待接单的剩余时间
		dillLeftTimes(orderinfo);
		//特殊字段处理
		dillSpecailField(orderinfo);
		//处理空值
		dillNull2BlankStr(orderinfo);
	}
	
	/**
	 * 添加订单的消费信息
	 * @param order
	 * @param usertoken
	 */
	private void addCostInfo(Map<String, Object> orderinfo, String usertoken) {
		if(orderinfo==null){
			return ;
		}
		String ordertype = (String) orderinfo.get("ordertype");
		if(isTaxiOrder(ordertype)){
			//出租车
			 String orderstatus = (String) orderinfo.get("orderstatus");
			if(OrderState.SERVICEDONE.state.equals(orderstatus)){
				//行程结束的字段信息
				orderinfo.put("costinfo", addCostInfo4Taxi(orderinfo));
			}
		}else{
			//网约车
			String orderno = (String) orderinfo.get("orderno");
			OrderCostParam params = new OrderCostParam();
			params.setHasunit(true);
			params.setOrderid(orderno);
			params.setOrdertype(ordertype);
			params.setUsetype((String) orderinfo.get("usetype"));
			JSONObject costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, usertoken, params, JSONObject.class);
			if(costinfo!=null&&costinfo.getInt("status")==Retcode.OK.code){
				costinfo.remove("status");
				costinfo.remove("message");
				String amount = costinfo.getString("cost");
	            String orderstatus = (String) orderinfo.get("orderstatus");
				if(StringUtils.isNotBlank(amount)){
				    if(OrderState.SERVICEDONE.state.equals(orderstatus)){
	                    //行程结束
	                    JSONObject costinfoobj = addCostInfo4NetCar(costinfo,orderinfo);
	    				orderinfo.put("costinfo", costinfoobj);
	                }else{
				        //行程未结束
	                    orderinfo.put("orderamount", StringUtil.formatNum(parseDouble(amount.replaceAll("元","")),1));
	                }
				}else{
	                orderinfo.put("orderamount", 0);
	                orderinfo.put("orderamountint", 0);
	            }
			}
		}
	}

	/**
	 * 为出租车添加消费信息
	 * @param orderinfo
	 * @return
	 */
    private Map<String,Object> addCostInfo4Taxi(Map<String, Object> orderinfo) {
    	Map<String,Object> costinfo = new HashMap<String,Object>();
    	String copyprice = (String) orderinfo.get("pricecopy");
		OrderCost cost = (OrderCost)JSONObject.toBean(JSONObject.fromObject(copyprice), OrderCost.class);
		double premium = cost.getPremiumrate();
		double orderamount = parseDouble(orderinfo.get("orderamount"));
		double schedulefee = parseDouble(orderinfo.get("schedulefee"));
    	String paymentmethod = (String) orderinfo.get("paymentmethod");
    	//添加溢价倍数
    	costinfo.put("premium", premium);
    	//添加行程费
    	costinfo.put("orderamount", orderamount);
    	//添加调度费
		costinfo.put("schedulefee", schedulefee);
    	//线上线下支付
    	costinfo.put("paymentmethod", paymentmethod);
    	//添加抵用券信息
		Map<String,Object> couponinfo = new HashMap<String,Object>();
		String orderno = (String) orderinfo.get("orderno");
		Map<String,Object> cpinfo = orderdao.getOrderCouponInfo(orderno);
		if(cpinfo!=null&&cpinfo.size()>0){
			couponinfo.put("id", cpinfo.get("couponidref"));
			couponinfo.put("couponmoney", parseDouble(cpinfo.get("couponmoney")));
			couponinfo.put("discountamount", parseDouble(cpinfo.get("discountamount")));
		}
		costinfo.put("couponinfo", couponinfo);
		return costinfo;
	}

	/**
     * 网约车添加计费信息
     * @param costinfo
     * @return
     */
	private JSONObject addCostInfo4NetCar(JSONObject costinfo,Map<String,Object> orderinfo) {
		JSONObject obj = getCostDetails(costinfo);
		
		//添加溢价倍数
		String copyprice = (String) orderinfo.get("pricecopy");
		OrderCost cost = (OrderCost)JSONObject.toBean(JSONObject.fromObject(copyprice), OrderCost.class);
		double premium = cost.getPremiumrate();
		obj.put("premium", premium);
		
		//订单的金额信息
		String paymentstatus = (String) orderinfo.get("paymentstatus");
		double orderamount = parseDouble(orderinfo.get("orderamount"));
		double srcamount = getSrcOrderAmount(copyprice);
		obj.put("srcamount", srcamount);//原始
		if(PayState.NOTPAY.state.equals(paymentstatus)||PayState.ALLNOPAY.state.equals(paymentstatus)){
			//未支付
			obj.put("orderamount", orderamount);//取整后没有减优惠券
			//添加抵用券信息
			Map<String,Object> couponinfo = new HashMap<String,Object>();
			String orderno = (String) orderinfo.get("orderno");
			Map<String,Object> cpinfo = orderdao.getOrderCouponInfo(orderno);
			if(cpinfo!=null&&cpinfo.size()>0){
				couponinfo.put("id", cpinfo.get("couponidref"));
				couponinfo.put("couponmoney", parseDouble(cpinfo.get("couponmoney")));
			}
			obj.put("couponinfo", couponinfo);
		}else{
			//添加抵用券信息
			Map<String,Object> couponinfo = new HashMap<String,Object>();
			String orderno = (String) orderinfo.get("orderno");
			Map<String,Object> cpinfo = orderdao.getOrderCouponInfo(orderno);
			if(cpinfo!=null&&cpinfo.size()>0){
				couponinfo.put("id", cpinfo.get("couponidref"));
				couponinfo.put("couponmoney", parseDouble(cpinfo.get("couponmoney")));
				couponinfo.put("discountamount", parseDouble(cpinfo.get("discountamount")));
			}
			obj.put("couponinfo", couponinfo);
			//已支付
			obj.put("orderamount", orderamount);//取整后没有减优惠券
		}
		
//		obj.put("orderamount", orderamount);
//		//这个字段还是不返回了，app会随时更改优惠券信息，减
////		obj.put("payamount", Math.abs(orderamount-couponmoney));
//		obj.put("srcamount", orderinfo.get("originalorderamount"));
		return obj;
	}
	
	/**
	 * 获取订单原始的金额（取整前）
	 * @param copyprice
	 * @return
	 */
	private double getSrcOrderAmount(String copyprice){
        if(StringUtils.isBlank(copyprice)){
            return 0;
        }
        OrderCost cost = (OrderCost)JSONObject.toBean(JSONObject.fromObject(copyprice), OrderCost.class);
        return cost.getCost();
	 }
	
	private String parseTime(Object value) {
		if(value==null||"".equalsIgnoreCase(String.valueOf(value))){
			return "00:00";
		}
		return String.valueOf(value);
	}
	
	private void addDriverPosition(Map<String, Object> orderinfo,String usertoken) {
		if(orderinfo==null){
			return ;
		}
		String driverid = (String) orderinfo.get("driverid");
		if(StringUtils.isNotBlank(driverid)){
			Map<String,Object> driverinfo = getDriverPosition(usertoken,orderinfo);
			if(driverinfo!=null){
				driverinfo.remove("status");
				driverinfo.remove("message");
				//将订单中的实时金额放入信息中
				double orderamount = parseDouble(orderinfo.get("orderamount"));
				driverinfo.put("orderamount",orderamount+"元");
				orderinfo.put("driverposition", driverinfo);
			}
		}
	}
	
	/**
	 * 获取司机的位置信息
	 * @param usertoken
	 * @param driverid
	 * @return
	 */
	public Map<String, Object> getDriverPosition(String usertoken,Map<String, Object> orderinfo) {
		String driverid = (String) orderinfo.get("driverid");
		String orderid = (String) orderinfo.get("orderno");
		String ordertype = (String) orderinfo.get("ordertype");
		String usetype = (String) orderinfo.get("usetype");
		Map<String,Object> res = dicdao.getDriverPosition(driverid);
		if(res==null||StringUtils.isBlank(orderid)||StringUtils.isBlank(ordertype)){
			if(res==null){
				res = new HashMap<String,Object>();
			}
			res.put("hintmessage", "");
			res.put("orderamount", "");
			res.put("lefttime", "");
		}else{
			//需要根据订单信息查出正在服务中的订单，如果没有就不显示下面的信息
			String orderstatus = null;
			double offaddrlat = 0;
			double offaddrlng = 0;
			Date usetime = null;
			if(!"2".equals(usetype)){
				//机构用户
				//获取订单相关的消费信息
				try{
					PassengerOrder order = orderdao.getOrderByOrderno4Org(orderid);
					orderstatus = order.getOrderstatus();
					offaddrlat = order.getOffaddrlat();
					offaddrlng = order.getOffaddrlng();
					usetime = order.getUsetime();
					OrderCostParam params = new OrderCostParam();
					params.setHasunit(true);
					params.setOrderid(orderid);
					params.setOrdertype(order.getOrdertype());
					params.setUsetype(order.getUsetype());
					JSONObject costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, null, params, JSONObject.class);
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
			}else{
				//个人用户
				if(isTaxiOrder(ordertype)){
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("orderno", orderid);
					Map<String,Object> order = orderdao.getOrder4OpTaxi(param);
					orderstatus = (String) order.get("orderstatus");
					offaddrlat = parseDouble(order.get("offaddrlat"));
					offaddrlng = parseDouble(order.get("offaddrlng"));
					usetime = (Date) order.get("usetime");
				}else{
					//获取实时费用
					try{
						PassengerOrder order = orderdao.getOrderByOrderno4Op(orderid);
						orderstatus = order.getOrderstatus();
						offaddrlat = order.getOffaddrlat();
						offaddrlng = order.getOffaddrlng();
						usetime = order.getUsetime();
						OrderCostParam params = new OrderCostParam();
						params.setHasunit(true);
						params.setOrderid(orderid);
						params.setOrdertype(order.getOrdertype());
						params.setUsetype(order.getUsetype());
						JSONObject costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, null, params, JSONObject.class);
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
				}
			}
			res.put("orderstatus", orderstatus);
			if(OrderState.INSERVICE.state.equalsIgnoreCase(orderstatus)||OrderState.WAITMONEY.state.equalsIgnoreCase(orderstatus)){
				String dorderinfo = JedisUtil.getString(RedisKeyEnum.MESSAGE_ORDER_TRAVEL_INFO.code+orderid);
				if(StringUtils.isNotBlank(dorderinfo)){
					JSONObject json = JSONObject.fromObject(dorderinfo);
					int lefttime = json.getInt("lefttime");
					int leftkm = json.getInt("leftkm");
					double dis =  ((Integer)leftkm).doubleValue()/1000;
					dis = StringUtil.formatNum(dis, 1);
                    if(dis<=0){
                        dis=0.1;
                    }
					String durstr = StringUtil.formatCostTimeInSecond(lefttime);
					res.put("hintmessage", "距离终点 "+dis+"公里预计"+durstr);
				}else{
					//调用接口
					BaiduApiQueryParam baiduparam = new BaiduApiQueryParam();
					baiduparam.setOrderStartLat((double) res.get("lat"));
					baiduparam.setOrderStartLng((double) res.get("lng"));
					baiduparam.setOrderEndLat(offaddrlat);
					baiduparam.setOrderEndLng(offaddrlng);
					Map<String,Object> hintinfo = carserviceapi.dealRequestWithToken("/BaiduApi/GetMileageInfo", HttpMethod.POST, null, baiduparam, Map.class);
					if(hintinfo!=null&&(Integer)hintinfo.get("status")==Retcode.OK.code){
						Object distance = hintinfo.get("distance");
						double dis = 0;
						if(distance!=null){
							dis =  ((Integer)distance).doubleValue()/1000;
							dis = StringUtil.formatNum(dis, 1);
						}
                        if(dis<=0){
                            dis=0.1;
                        }
						Object duration = hintinfo.get("duration");
						String durstr = "1分钟";
						if(duration!=null){
							durstr = StringUtil.formatCostTimeInSecond((int) duration);
						}
						res.put("hintmessage", "距离终点 "+dis+"公里预计"+durstr);
					}else{
						res.put("hintmessage", "正在获取信息中。。。");
					}
				}
			}else if(OrderState.WAITSTART.state.equalsIgnoreCase(orderstatus)){
				//带出发给出带出发的
				if(usetime!=null){
					Date current = new Date();
					int lefttime = (int)((usetime.getTime() - current.getTime())/(1000*60));
					String lefttimestr = StringUtil.formatCostTime(lefttime<=0?1:lefttime);
					res.put("lefttime", lefttimestr);
				}else{
					res.put("lefttime", "");
				}
			}
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}
	
	/**
	 * 添加取消订单的信息
	 * @param order
	 */
	private void addCancelOrderInfo(Map<String, Object> orderinfo,String usertoken) {
		if(orderinfo==null){
			return ;
		}
		String cancelparty = (String) orderinfo.get("cancelparty");
		String orderstatus = (String) orderinfo.get("orderstatus");
		//取消订单才有取消人和取消时间
		if(OrderState.CANCEL.state.equalsIgnoreCase(orderstatus)&&StringUtils.isNotBlank(cancelparty)){
			switch (cancelparty) {
			case "0":
				//租赁端
				orderinfo.put("cancelname", "租赁公司管理员");
				break;
			case "1":
				//运管取消
				orderinfo.put("cancelname", "运管端管理员");
				break;
			case "2":
				//机构取消
				orderinfo.put("cancelname", "您");
				break;
			case "3":
				//app取消
				orderinfo.put("cancelname", "您");
				break;
			default:
				orderinfo.put("cancelname", "系统派单失败");
				break;
			}
		}
	}
	
	private void addRemainTime(Map<String, Object> orderinfo, String usertoken) {
		if(orderinfo==null){
			return ;
		}
		String orderstatus = (String) orderinfo.get("orderstatus");
		if(OrderState.WAITSTART.state.equalsIgnoreCase(orderstatus)){
			Date usertime = (Date) orderinfo.get("usetime");
			long currenttime = System.currentTimeMillis();
			if(usertime!=null){
				int remain = (int)((usertime.getTime()-currenttime)/(1000*60));
				remain = remain<=0?1:remain;
				orderinfo.put("remaintime", StringUtil.formatCostTime(remain));
			}
		}
	}
	
	
	/**
	 * 添加绝对地址
	 * @param orders
	 */
	private void addAbsolutPath(Map<String, Object> orderinfo) {
		if(orderinfo==null){
			return ;
		}
		
		//车型路径
		String selectedmodelpath = (String) orderinfo.get("selectedmodelpath");
		if(StringUtils.isNotBlank(selectedmodelpath)){
			orderinfo.put("selectedmodellogo", SystemConfig.getSystemProperty("fileserver")+File.separator+selectedmodelpath);
		}else{
			orderinfo.put("selectedmodellogo","");
		}
		orderinfo.remove("selectedmodelpath");
		//司机图片
		String driverimg = (String) orderinfo.get("driverimg");
		if(StringUtils.isNotBlank(driverimg)){
			orderinfo.put("driverimg", SystemConfig.getSystemProperty("fileserver")+File.separator+driverimg);
		}
	}
	
	/**
	 * 添加订单的显示类型
	 * @param orderinfo
	 */
	private void addOrderTypeCaption(Map<String, Object> orderinfo) {
		if(orderinfo==null){
			return ;
		}
		boolean isusenow = (boolean)orderinfo.get("isusenow");
		String ordertypecaption = "";
		if(isusenow){
			ordertypecaption += "即刻用车·";
		}else{
			//预约
			ordertypecaption += "预约用车·";
		}
		String orderstyle = (String) orderinfo.get("orderstyle");
		if("1".equals(orderstyle)){
			//出租车
			ordertypecaption += "出租车";
		}else{
			//网约车
			String ordertype = (String)orderinfo.get("ordertype");
			if("1".equalsIgnoreCase(ordertype)){
				ordertypecaption += "约车";
			}else if("2".equalsIgnoreCase(ordertype)){
				ordertypecaption += "接机";
			}else{
				ordertypecaption += "送机";
			}
		}
		orderinfo.put("ordertypecaption",ordertypecaption);
	}
	
	/**
	 * 处理时间字段最后的时间点
	 * @param orderinfo
	 */
	private void dillTimeField(Map<String, Object> orderinfo) {
		if(orderinfo==null){
			return ;
		}
//		String usetime = (String) orderinfo.get("usetime");
//		if(StringUtils.isNotBlank(usetime)&&usetime.length()>19){
//			orderinfo.put("usetime", usetime.substring(0, usetime.length()-2));
//		}
		String undertime = (String) orderinfo.get("undertime");
		if(StringUtils.isNotBlank(undertime)&&undertime.length()>19){
			orderinfo.put("undertime", undertime.substring(0, undertime.length()-2));
		}
		String ordertime = (String) orderinfo.get("ordertime");
		if(StringUtils.isNotBlank(ordertime)&&ordertime.length()>19){
			orderinfo.put("ordertime", ordertime.substring(0, ordertime.length()-2));
		}
		String starttime = (String) orderinfo.get("starttime");
		if(StringUtils.isNotBlank(starttime)&&starttime.length()>19){
			orderinfo.put("starttime", starttime.substring(0, starttime.length()-2));
		}
		String endtime = (String) orderinfo.get("endtime");
		if(StringUtils.isNotBlank(endtime)&&endtime.length()>19){
			orderinfo.put("endtime", endtime.substring(0, endtime.length()-2));
		}
		String canceltime = (String) orderinfo.get("canceltime");
		if(StringUtils.isNotBlank(canceltime)&&canceltime.length()>19){
			orderinfo.put("canceltime", canceltime.substring(0, canceltime.length()-2));
		}
	}
	
	/**
	 * 处理待接单剩余时间
	 * @param orderinfo
	 */
	private void dillLeftTimes(Map<String, Object> orderinfo) {
		if(orderinfo==null){
			return ;
		}
		String orderstatus = (String) orderinfo.get("orderstatus");
//		if(OrderState.WAITTAKE.state.equals(orderstatus)||OrderState.MANTICSEND.state.equals(orderstatus)){
			Date autocanceltime = (Date) orderinfo.get("autocanceltime");
			if(autocanceltime!=null){
				Date currenttime = new Date();
				int lefttime = (int) ((autocanceltime.getTime()-currenttime.getTime())/1000);
				orderinfo.put("lefttime", lefttime<0?0:lefttime);
			}else{
				orderinfo.put("lefttime", 0);
			}
//		}
	}
	
	/**
	 * 处理特殊字段
	 * @param orderinfo
	 */
	private void dillSpecailField(Map<String, Object> orderinfo) {
		Object value = orderinfo.get("mileage");
		if(value==null||((value instanceof String) &&StringUtils.isBlank((String)value))){
			orderinfo.put("mileage", 0);
		}
		Object orderamount = orderinfo.get("orderamount");
		if(orderamount==null||((orderamount instanceof String) &&StringUtils.isBlank((String)orderamount))){
			orderinfo.put("orderamount", 0);
		}
		Object estimatedtime = orderinfo.get("estimatedtime");
		if(estimatedtime==null||((estimatedtime instanceof String) &&StringUtils.isBlank((String)estimatedtime))){
			orderinfo.put("estimatedtime", 0);
		}
		Object estimatedmileage = orderinfo.get("estimatedmileage");
		if(estimatedmileage==null||((estimatedmileage instanceof String) &&StringUtils.isBlank((String)estimatedmileage))){
			orderinfo.put("estimatedmileage", 0);
		}
		Object estimatedcost = orderinfo.get("estimatedcost");
		if(estimatedcost==null||((estimatedcost instanceof String) &&StringUtils.isBlank((String)estimatedcost))){
			orderinfo.put("estimatedcost", 0);
		}
		Object lefttime = orderinfo.get("lefttime");
		if(lefttime==null||((lefttime instanceof String) &&StringUtils.isBlank((String)lefttime))){
			orderinfo.put("lefttime", 0);
		}
	}
	
	/**
	 * 处理map的空值问题
	 * @param valueinfo
	 */
	private void dillNull2BlankStr(Map<String, Object> valueinfo){
		if(valueinfo==null){
			return ;
		}
		Iterator<String> keyit = valueinfo.keySet().iterator();
		while(keyit.hasNext()){
			String key = keyit.next();
			Object value = valueinfo.get(key);
			if(value!=null){
				continue ;
			}
			valueinfo.put(key,"");
		}
	}

	/**
	 * 机构订单获取订单详情
	 * @param params
	 * @return
	 */
	public Map<String, Object> getOrder(Map<String, Object> params) {
		Map<String, Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			String usertoken = (String) params.get("usertoken");
			String orderno = (String) params.get("orderno");
			String ordertype = (String) params.get("ordertype");
			String usetype = (String) params.get("usetype");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)||StringUtils.isBlank(usetype)){
				res.put("status", Retcode.FAILED.code);
				res.put("message", "参数不全");
				res.put("order", "");
				return res;
			}
			PassengerOrder orderinfo = null;
			if(!"2".equals(usetype)){
				//机构用户
				orderinfo = orderdao.getOrderByOrderno4Org(orderno);
			}else{
				//个人用户
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","这个接口是给机构端用的");
				res.put("order",new HashMap<>());
				return res;
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
		
		try{
			//如果是取消还要加上取消费
			//取消处罚费用
			if(OrderState.CANCEL.state.equals(order.getOrderstatus())){
				//取消就去查看这个订单有没有取消费
				Map<String,Object> cancelinfo = orderdao.getCancelInfo4Org(order.getOrderno());
				if(cancelinfo!=null){
					order.setCancelfee(parseDouble(cancelinfo.get("cancelamount")));
					String cancelrule = (String) cancelinfo.get("cancelrule");
					if(StringUtils.isBlank(cancelrule)){
						order.setHascancelrule(false);
					}else{
						order.setHascancelrule(true);
					}
				}else{
					order.setHascancelrule(false);
				}
			}
		}catch(Exception e){
			logger.error(e);
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
	 * 添加订单的消费信息
	 * @param order
	 * @param usertoken
	 */
	private void addCostInfo(PassengerOrder order, String usertoken) {
		OrderCostParam params = new OrderCostParam();
		params.setHasunit(true);
        params.setCompanyid(order.getCompanyid());
		params.setOrderid(order.getOrderno());
		JSONObject costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, usertoken, params, JSONObject.class);
		if(costinfo!=null&&costinfo.getInt("status")==Retcode.OK.code){
			costinfo.remove("status");
			costinfo.remove("message");
			String amount = costinfo.getString("cost");
			if(StringUtils.isNotBlank(amount)){
			    if(OrderState.SERVICEDONE.state.equals(order.getOrderstatus())){
			        //行程结束
                    JSONObject costinfoobj = addCostInfo(costinfo,order);
        			order.setCostinfo(costinfoobj);
                }else{
			        //行程未结束
                    order.setOrderamount(StringUtil.formatNum(parseDouble(amount.replaceAll("元","")),1));
                }
			}
		}
	}
	
	/**
	 * 机构订单添加计费信息
	 * @param costinfo
	 * @param order
	 * @return
	 */
	private JSONObject addCostInfo(JSONObject costinfo, PassengerOrder order) {
		JSONObject obj = getCostDetails(costinfo);
		//添加溢价倍数
//		String value = (String) costinfo.get("premiumrate");
//		double premium = (value==null?1:parseDouble(value.replace("倍", "")));
//		obj.put("premium", premium);
		//机构溢价是1
		obj.put("premium", 1);
		//添加抵用券信息
		Map<String,Object> couponinfo = new HashMap<String,Object>();
		String orderno = order.getOrderno();
		Map<String,Object> cpinfo = orderdao.getOrderCouponInfo(orderno);
		if(cpinfo!=null&&cpinfo.size()>0){
			couponinfo.put("id", cpinfo.get("couponidref"));
			couponinfo.put("couponmoney", parseDouble(cpinfo.get("couponmoney")));
			couponinfo.put("discountamount", parseDouble(cpinfo.get("discountamount")));
			
		}
		obj.put("couponinfo", couponinfo);
		//订单的金额信息
		double orderamount = parseDouble(order.getOrderamount());
		double couponmoney = parseDouble(couponinfo.get("couponmoney"));
		obj.put("orderamount", orderamount);
		//这个字段还是不返回了，app会随时更改优惠券信息，减
//		obj.put("payamount", Math.abs(orderamount-couponmoney));
		obj.put("srcamount", order.getOriginalorderamount());
		return obj;
	}

	/**
	 * 消费的计价明细
	 * @param costinfo
	 * @return
	 */
	private JSONObject getCostDetails(JSONObject costinfo) {
		JSONArray detail = new JSONArray();
		JSONObject obj1 = new JSONObject();
		obj1.put("name", "起步价");
		obj1.put("value", costinfo.getString("startprice"));
		JSONObject obj2 = new JSONObject();
		obj2.put("name", "里程费("+costinfo.getString("mileage")+")");
		obj2.put("value", costinfo.getString("rangecost"));
		JSONObject obj3 = new JSONObject();
		obj3.put("name", "时长费("+costinfo.get("times")+")");
		obj3.put("value", costinfo.get("timecost"));
		detail.add(obj1);
		detail.add(obj2);
		detail.add(obj3);
		if (costinfo.get("deadheadcost") != null && parseDouble(costinfo.get("deadheadcost").toString().replace("元", "")) != 0) {
			JSONObject obj4 = new JSONObject();
			obj4.put("name", "空驶费("+costinfo.get("realdeadheadmileage")+")");
			obj4.put("value", costinfo.get("deadheadcost"));
			detail.add(obj4);
		}
		if (costinfo.get("nightcost") != null && parseDouble(costinfo.get("nightcost").toString().replace("元", "")) != 0) {
			JSONObject obj5 = new JSONObject();
			obj5.put("name", "夜间费("+costinfo.get("mileage")+")");
			obj5.put("value", costinfo.get("nightcost"));
			detail.add(obj5);
		}
		
		JSONArray amountdetail = new JSONArray();
		JSONObject obj6 = new JSONObject();
		obj6.put("name", "起步价");
		obj6.put("value", costinfo.getString("startprice"));
		JSONObject obj7 = new JSONObject();
		obj7.put("name", "里程费");
		obj7.put("value", costinfo.getString("rangeprice"));
		JSONObject obj8 = new JSONObject();
		obj8.put("name", "时长费");
		obj8.put("value", costinfo.getString("timeprice"));
		JSONObject obj9 = new JSONObject();
		obj9.put("name", "回空里程");
		obj9.put("value", costinfo.getString("deadheadmileage"));
		JSONObject obj10 = new JSONObject();
		obj10.put("name", "回空费价");
		obj10.put("value", costinfo.getString("deadheadprice"));
		JSONObject obj11 = new JSONObject();
		obj11.put("name", "夜间征收时段");
		obj11.put("value", parseTime(costinfo.get("nightstarttime")) + "-" + parseTime(costinfo.get("nightendtime")));
		JSONObject obj12 = new JSONObject();
		obj12.put("name", "夜间费价");
		obj12.put("value", costinfo.getString("nighteprice"));		
		amountdetail.add(obj6);
		amountdetail.add(obj7);
		amountdetail.add(obj8);
		amountdetail.add(obj9);
		amountdetail.add(obj10);
		amountdetail.add(obj11);
		amountdetail.add(obj12);

		JSONObject obj = new JSONObject();
		obj.put("detail", detail);
		obj.put("amountdetail", amountdetail);
		return obj;
	}

	private void addRemainTime(PassengerOrder order, String usertoken) {
		if(OrderState.WAITSTART.state.equalsIgnoreCase(order.getOrderstatus())){
			Date usertime = order.getUsetime();
			long currenttime = System.currentTimeMillis();
			if(usertime!=null){
				int remain = (int)((usertime.getTime()-currenttime)/(1000*60));
				remain = remain<=0?1:remain;
				order.setRemaintime(StringUtil.formatCostTime(remain));
			}
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
	 * 添加订单的客服信息
	 * @param order
	 * @param usertoken
	 */
	private void addOrderServiceInfo(PassengerOrder order, String usertoken) {
		//机构订单，查询每个租赁公司的客服信息
		String companyid = order.getCompanyid();
		Map<String,Object> companyinfo = orderdao.getLeCompanyInfo(companyid);
		order.setServicephone((String) companyinfo.get("servicesphone"));
	}
	
	private void addOrderCarLogoInfo(PassengerOrder order, String usertoken) {
		String logo = order.getSelectedmodellogo();
		if(StringUtils.isNotBlank(logo)){
			order.setSelectedmodellogo(SystemConfig.getSystemProperty("fileserver")+File.separator+logo);
		}
	}
	
	private void addDriverPosition(String usertoken,PassengerOrder order) {
		String driverid = order.getDriverid();
		if(StringUtils.isNotBlank(driverid)){
			Map<String,Object> orderinfo = new HashMap<String,Object>();
			orderinfo.put("driverid", order.getDriverid());
			orderinfo.put("orderno", order.getOrderno());
			orderinfo.put("ordertype", order.getOrdertype());
			orderinfo.put("usetype", order.getUsetype());
			Map<String,Object> driverinfo = getDriverPosition(usertoken,orderinfo);
			if(driverinfo!=null){
				driverinfo.remove("status");
				driverinfo.remove("message");
				//将订单中的实时金额放入信息中
				driverinfo.put("orderamount", order.getOrderamount()+"元");
				order.setDriverposition(driverinfo);
			}
		}
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
		String iDisplayStart = (String) params.get("iDisplayStart");
		String iDisplayLength = (String) params.get("iDisplayLength");
		if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(iDisplayStart)||StringUtils.isBlank(iDisplayLength)){
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message","参数不全");
			res.put("currentcount",0);
			res.put("orders",new ArrayList<>());
			return res;
		}
		String account = Const.getUserInfo(usertoken).get("account");
		params.put("account", account);
		//“0”表示机构订单，“1”表示个人订单
		if(UserTokenManager.ORGUSERTYPE.equals((String) params.get("type"))){
			//机构用户
			List<Order4List> orders = orderdao.getOrders4Org(params);
			dillOrdersInfo(orders);
			if(orders==null){
				orders = new ArrayList<>();
			}
			res.put("currentcount",orders.size());
			res.put("orders", orders);
		}else{
			//个人用户
			List<Map<String,Object>> orders = orderdao.getOrders4OpSec(params);
			dillOrders(orders,usertoken);
			if(orders==null){
				orders = new ArrayList<>();
			}
			res.put("currentcount",orders.size());
			res.put("orders",orders);
		}
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		return res;
	}
	
	/**
	 * 处理查询出来的订单信息
	 * @param orders
	 */
	private void dillOrders(List<Map<String, Object>> orders,String usertoken) {
		if(orders==null){
			return ;
		}
		for(int i=0;i<orders.size();i++){
			Map<String,Object> orderinfo = orders.get(i);
			dillOrder(orderinfo,usertoken,false);
		}
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
		Date autocanceltime = order.getAutocanceltime();
		if(autocanceltime!=null){
			Date currenttime = new Date();
			int lefttime = (int) ((autocanceltime.getTime()-currenttime.getTime())/1000);
			order.setLefttime(lefttime<0?0:lefttime);
		}else{
			order.setLefttime(0);
		}
		
		//如果是取消还要加上取消费
		//取消处罚费用
		if(OrderState.CANCEL.state.equals(order.getOrderstatus())){
			//取消就去查看这个订单有没有取消费
			Map<String,Object> cancelinfo = orderdao.getCancelInfo4Org(order.getOrderno());
			if(cancelinfo!=null){
				order.setCancelfee(parseDouble(cancelinfo.get("cancelamount")));
				String cancelrule = (String) cancelinfo.get("cancelrule");
				if(StringUtils.isBlank(cancelrule)){
					order.setHascancelrule(false);
				}else{
					order.setHascancelrule(true);
				}
			}else{
				order.setHascancelrule(false);
			}
		}
		
		//获取实际支付金额
		try{
			String orderno = order.getOrderno();
			String paymentstatus = order.getPaymentstatus();
			if(!(PayState.NOTPAY.state.equals(paymentstatus))){
				//都支付完成的才有realpayamount
				String orderstatus = order.getOrderstatus();
				if(OrderState.SERVICEDONE.state.equals(orderstatus)){
					//行程结束的
					Map<String,Object> cpinfo = orderdao.getOrderCouponInfo(orderno);
					if(cpinfo!=null&&cpinfo.size()>0){
						double orderamount = parseDouble(order.getOrderamount());
						double discountamount = parseDouble(cpinfo.get("discountamount"));
						if(orderamount<=discountamount){
							order.setRealpayamount(0.0);
						}else{
							order.setRealpayamount(new BigDecimal(orderamount).subtract(new BigDecimal(discountamount)).doubleValue());
						}
					}else{
						order.setRealpayamount(order.getOrderamount());
					}
				}else{
					order.setRealpayamount(order.getCancelfee());
				}
			}
		}catch(Exception e){
			logger.error("获取实际支付金额",e);
		}
	}

	/**
	 * 获取当前城市提供服务的租赁公司列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getServiceCompanys(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
        addPubInfos(res);
        res.put("status", Retcode.OK.code);
        res.put("message",Retcode.OK.msg);
        
        String type = (String) params.get("type");
        String usertoken = (String) params.get("usertoken");
        if(StringUtils.isBlank(type)||StringUtils.isBlank(usertoken)){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", "参数不完整");
            return res;
        }
        List<Map<String,Object>> companys = new ArrayList<Map<String,Object>>();
        res.put("companys",companys);
        if(Const.ORDERTYPE_ORG.equals(type)){
            //机构订单(获取机构服务的租赁公司)
        	Map<String,String> userinfo = Const.getUserInfo(usertoken);
    		String account = userinfo.get("account");
    		OrgUser user = userdao.getUser4Org(account);
    		if(user!=null&&user.getStatus()==1){
    			//现在只有因公用车，所以只有没有离职才有租赁公司可用
    			Map<String,Object> pp = new HashMap<String,Object>();
    			pp.put("organid", user.getOrganId());
    			List<String> tempcompanyids = userdao.getValiableCompanys(pp);
    			if(tempcompanyids!=null){
    				params.put("organid", user.getOrganId());
    				List<Map<String, Object>> organservicecompanyinfos = userdao.getOrganServiceCompany(params);
    				if(organservicecompanyinfos!=null&&organservicecompanyinfos.size()>0){
    					for(int i=0;i<organservicecompanyinfos.size();i++){
    						Map<String, Object> companyobj = organservicecompanyinfos.get(i);
    						if(tempcompanyids.contains(companyobj.get("id"))){
    							companys.add(companyobj);
    						}
    					}
    				}
    			}
    		}
        }else{
        	//个人用户获取加入toc的租赁公司列表
        	Map<String,Object> all = new HashMap<String,Object>();
        	all.put("id","");
        	all.put("name","如约的士");
        	companys.add(all);
        	List<Map<String,Object>> tempcompanys = userdao.getTocCompanysNew();
			if(tempcompanys!=null){
				companys.addAll(tempcompanys);
			}
        }
        return res;
	}

	/**
	 * 获取网约车预估费用
	 * @param params
	 * @return
	 */
	public Map<String, Object> getEstimatedCost4NetCar(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
        try{
            addPubInfos(res);
            res.put("status", Retcode.OK.code);
            res.put("message",Retcode.OK.msg);
            String type = (String) params.get("type");
            if(StringUtils.isBlank(type)){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不完整");
                return res;
            }
            if(Const.ORDERTYPE_ORG.equals(type)){
                //机构订单
                res = getEstimatedCost4OrgNetCar(params);
            }else{
                //个人订单
                res = getEstimatedCost4OpNetCar(params);
            }
        }catch (Exception e){
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message",Retcode.EXCEPTION.msg);
        }
        return res;
	}
	
	/**
	 * 个人用户获取网约车的预估费用
	 * @param param
	 * @return
	 */
	public Map<String, Object> getEstimatedCost4OpNetCar(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			JSONObject obj = new JSONObject();
			res.put("costinfo", obj);
			String cityvalue = dicdao.getCityNo((String) params.get("city"));
			params.put("city", cityvalue);
			Map<String,Object> escostinfo = getEstimatedCost4Op(params,false);
			if((int)escostinfo.get("status")!=Retcode.OK.code){
				//预估失败
				escostinfo.put("costinfo",new HashMap<>());
				return escostinfo;
			}
			Map<String,Object> costinfo = (Map<String, Object>)escostinfo.get("costinfo");
			double cost = parseDouble(costinfo.get("cost"));
            obj.put("awardpint", 0);
			JSONArray paydetail = new JSONArray();
			JSONObject obj3 = new JSONObject();
			obj3.put("name", "起步价");
			obj3.put("price", costinfo.get("startprice")+"元");
			JSONObject obj4 = new JSONObject();
			obj4.put("name", "里程费("+StringUtil.formatNum(((int)costinfo.get("mileage"))/1000, 1)+"公里"+")");
			obj4.put("price", costinfo.get("rangecost")+"元");
			JSONObject obj5 = new JSONObject();
			obj5.put("name", "时长费("+StringUtil.formatCostTimeInSecond((int)costinfo.get("times"))+")");
			obj5.put("price", costinfo.get("timecost")+"元");
			paydetail.add(obj3);
			paydetail.add(obj4);
			paydetail.add(obj5);
			double mileage = StringUtil.formatNum(((int)costinfo.get("mileage"))/1000, 1);
			double deadheadmileage = parseDouble(costinfo.get("deadheadmileage"));
			if (mileage > deadheadmileage && parseDouble(costinfo.get("deadheadcost")) != 0) {
				JSONObject obj6 = new JSONObject();
				obj6.put("name", "空驶费("+StringUtil.formatNum(mileage-deadheadmileage,1)+"公里)");
				obj6.put("price", costinfo.get("deadheadcost")+"元");
				paydetail.add(obj6);
			}
			obj.put("paydetail",paydetail);
			//添加优惠券
			params.put("servicetype", 2);
            dillCoupons4OpUser(obj,params);
			//添加溢价倍数
            params.put("orderstyle", "0");
            dillPremium(obj,params);
            double premium = (double)obj.get("premium");
            cost = StringUtil.formatNum(new BigDecimal(cost).multiply(new BigDecimal(premium)).doubleValue(), 1);
            obj.put("cost", cost);
            //添加实际费用
            double actualcost = 0;
            double couponmoney = parseDouble(obj.get("couponmoney"));
            if(cost>couponmoney){
            	actualcost = new BigDecimal(cost).subtract(new BigDecimal(couponmoney)).doubleValue();
            }
            obj.put("actualcost",StringUtil.formatNum(actualcost,1));
		}catch(Exception e){
			logger.error("乘客端异常",e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("costinfo",new HashMap<>());
		}
		return res;
	}
	
	/**
	 * 添加个人用户下单的时候选择优惠券
	 * @param obj
	 * @param params
	 */
	private void dillCoupons4OpUser(JSONObject obj, Map<String, Object> params) {
		try{
			String usertoken = (String) params.get("usertoken");
			String city = (String) params.get("city");
			int servicetype = (int) params.get("servicetype");
			String opid = (String) dicdao.getPayInfo4Op().get("id");
			Map<String,String> accountinfo = Const.getUserInfo(usertoken);
			String account = accountinfo.get("account");
			PeUser user = userdao.getUser4Op(account);
			Map<String,Object> couponp = new HashMap<String,Object>();
			couponp.put("userid", user.getId());
			couponp.put("servicetype", servicetype);
			couponp.put("lecompanyid", opid);
			couponp.put("platformtype", 0);
			couponp.put("city", city);
			Map<String,Object> couponobj = userdao.getHighestAbleCoupon(couponp);
			if(couponobj!=null){
				obj.put("couponid", couponobj.get("id"));
		    	obj.put("couponmoney", parseDouble(couponobj.get("money")));
			}else{
				obj.put("couponid", "");
		    	obj.put("couponmoney", 0);
			}
		}catch(Exception e){
			logger.error("个人用户下单添加优惠券出错，预估费用",e);
			obj.put("couponid", "");
	    	obj.put("couponmoney", 0);
		}
	}

	/**
     * 获取预估费用
     * @param params
     * @return
     */
    public Map<String,Object> getEstimatedCost4OrgNetCar(Map<String, Object> params) {
        Map<String,Object> ress = new HashMap<String,Object>();
        JSONObject obj = new JSONObject();
        ress.put("costinfo", obj);
        String usertoken = (String) params.get("usertoken");
        String companyid = (String) params.get("companyid");
        String cityvalue = dicdao.getCityNo((String) params.get("city"));
        params.put("city", cityvalue);
        JSONObject costinfo = null;
        double cost = 0;
        //机构用户的订单
        params.put("orderprop", "0");
        //获取预估费用的时候，预估费用需要传递rulestype 0标准1个性化
        if(Const.USETYPE_PUBLIC.equalsIgnoreCase((String)params.get("usetype"))){
            params.put("rulestype", "1");
        }else{
            if("0".equalsIgnoreCase((String)params.get("type"))){
                params.put("rulestype", "1");
                params.put("usetype", Const.USETYPE_PUBLIC);
            }else{
                params.put("rulestype", "0");
            }
        }

        //现获取机构在租赁公司的可用额度
        OrgUser orguser = userdao.getUser4Org(Const.getUserInfo(usertoken).get("account"));
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("companyid",companyid);
        param.put("orgid", orguser.getOrganId());
        double balance = (double) userdao.getOrgBalance(param);
        try{
        	if("0".equals(params.get("isusenow"))){
        		params.put("isusenow", false);
        	}else{
        		params.put("isusenow", true);
        	}
            params.put("userid", orguser.getId());
            costinfo = carserviceapi.dealRequestWithToken("/OrderApi/GetOrderCost", HttpMethod.POST, usertoken, params, JSONObject.class);
        }catch(Exception e){
            logger.error("乘客端异常",e);
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
            obj4.put("name", "里程费("+costinfo.get("mileage")+")");
            obj4.put("price", costinfo.get("rangecost"));
            JSONObject obj5 = new JSONObject();
            obj5.put("name", "时长费("+costinfo.get("times")+")");
            obj5.put("price", costinfo.get("timecost"));
            paydetail.add(obj3);
            paydetail.add(obj4);
            paydetail.add(obj5);
            if (costinfo.get("deadheadcost") != null && StringUtils.isNotBlank(String.valueOf(costinfo.get("deadheadcost")))) {
                String pricestr = (String) costinfo.get("deadheadcost");
                if(parseDouble(pricestr.replace("元",""))>0){
                    JSONObject obj6 = new JSONObject();
                    obj6.put("name", "空驶费("+costinfo.get("realdeadheadmileage")+")");
                    obj6.put("price", pricestr);
                    paydetail.add(obj6);
                }
            }
            obj.put("paydetail",paydetail);
            //添加优惠券
            dillCoupons4OrgUser(obj);
            //添加溢价倍数
            params.put("orderstyle", "0");
            dillPremium(obj,params);
            //添加实际费用
            double actualcost = 0;
            double couponmoney = parseDouble(obj.get("couponmoney"));
            if(cost>couponmoney){
            	actualcost = new BigDecimal(cost).subtract(new BigDecimal(couponmoney)).doubleValue();
            }
            obj.put("actualcost",actualcost);
            ress.put("status", Retcode.OK.code);
            ress.put("message", Retcode.OK.msg);
        }else{
            return costinfo;
        }
        return ress;
    }
    
    /**
     * 添加溢价信息
     * @param obj
     * @param params
     */
    private void dillPremium(JSONObject obj, Map<String, Object> params) {
    	try{
    		String usertimestr = (String) params.get("usertime");
        	String city = (String) params.get("city");
        	String orderstyle = (String) params.get("orderstyle");
        	String companyid = (String) params.get("companyid");
        	Date usertimeobj = new Date();
        	if(StringUtils.isNotBlank(usertimestr)){
        		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		usertimeobj = format.parse(usertimestr);
        	}
        	Map<String,Object> premiump = new HashMap<String,Object>();
    		premiump.put("usetime", usertimeobj.getTime());
    		premiump.put("citycode", city);
    		premiump.put("cartype", "1".equals(orderstyle)?1:0);
    		premiump.put("leasescompanyid", companyid);
    		if(StringUtils.isBlank(companyid)){
    			//个人订单，运管端
    			premiump.put("platformtype", 0);
    			Map<String,Object> result = carserviceapi.dealRequestWithToken("/PremiumRule/GetPremiumrate", HttpMethod.POST, null, premiump, Map.class);
        		if(result!=null&&(int)result.get("status")==0){
        			obj.put("premium", result.get("premiumrate"));
        		}else{
        			obj.put("premium", 1);
        		}
    		}else{
    			//机构订单，租赁端
    			premiump.put("platformtype", 1);
    			obj.put("premium", 1);
    		}
    		
    	}catch(Exception e){
    		obj.put("premium", 1);
    		logger.error("添加溢价信息出错，溢价回归1倍", e);
    	}
	}

	/**
     * 机构用户添加优惠券信息
     * （机构用户现在没有用优惠券的地方因为不用自己支付，所以用不了）
     * @param obj
     */
    private void dillCoupons4OrgUser(JSONObject obj) {
    	obj.put("couponid", "");
    	obj.put("couponmoney", 0);
	}

	/**
	 * 个人用户获取预估费用
	 * @param params
	 * @return
	 */
	private Map<String,Object> getEstimatedCost4Op(Map<String,Object> params,boolean istaxi){
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			if(istaxi){
				//出租车的预估
				Map<String,Object> accountrules = opdao.getOpOrderAccountRules4Taxi(params);
				if(accountrules==null){
					res.put("status", Retcode.NOSERVICESINCITY.code);
					res.put("message", Retcode.NOSERVICESINCITY.msg);
					return res;
				}
				BaiduApiQueryParam baqp = new BaiduApiQueryParam();
				baqp.setOrderStartLng(parseDouble(params.get("onaddrlng")));
				baqp.setOrderStartLat(parseDouble(params.get("onaddrlat")));
				baqp.setOrderEndLng(parseDouble(params.get("offaddrlng")));
				baqp.setOrderEndLat(parseDouble(params.get("offaddrlat")));
				Map<String,Object> direc = carserviceapi.dealRequestWithToken("/BaiduApi/GetMileageInfo", HttpMethod.POST, null, baqp, Map.class);
				if(direc==null||((int)direc.get("status")) != Retcode.OK.code){
					res.put("status", Retcode.FAILED.code);
					res.put("message", Retcode.FAILED.msg);
					logger.error("获取预估里程失败！");
					return res;
				}
                //约车时限
                double tempprice = 0;
				try{
                    Map<String,Object> carsintervalinfo = opdao.getSendRule4ReverceTaxi(params);
                    String usetime = (String) params.get("usetime");
                    String isusenow = (String) params.get("isusenow");
                    if(carsintervalinfo!=null&&(StringUtils.isNotBlank(usetime)||"0".equals(isusenow))){
                        int carsinterval = parseInt(carsintervalinfo.get("carsinterval"));
                        if(StringUtils.isNotBlank(usetime)){
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date current = new Date();
                            Date yydate = new Date(current.getTime()+carsinterval*60*1000);
                            Date usetimeobj = format.parse(usetime);
                            if(usetimeobj.after(yydate)){
                                //预约时间
//                                Map<String,Object> yyparams = new HashMap<String,Object>();
//                                yyparams.put("type","个人用户出租车下单预约附加费用");
//                                tempprice += parseDouble(getYYFJF(yyparams));
                            }
                        }else{
                            //预约时间
//                            Map<String,Object> yyparams = new HashMap<String,Object>();
//                            yyparams.put("type","个人用户出租车下单预约附加费用");
//                            tempprice += parseDouble(getYYFJF(yyparams));
                        }
                    }
                }catch (Exception e){
				    logger.error("个人出租车预约用车计费规则加上预约费异常",e);
                }

				int mileage = (int)direc.get("distance");
				//打表来接的里程
				mileage = parseInt(params.get("meterrange"))*1000+mileage;
				int times = (int)direc.get("duration");
				accountrules.put("mileage", mileage);
				accountrules.put("times", times);
				//计算费用
				double startprice = parseDouble(accountrules.get("startprice"))+tempprice;
                accountrules.put("startprice",startprice);
				double startrange = parseDouble(accountrules.get("startrange"));
				double surcharge = parseDouble(accountrules.get("surcharge"));
				double emptytravelrate = parseDouble(accountrules.get("emptytravelrate"));
				double standardrange = parseDouble(accountrules.get("standardrange"));
				double renewalprice = parseDouble(accountrules.get("renewalprice"));
				
				double schedulefee = parseDouble(params.get("schedulefee"));
				double cost = 0;
				if((mileage/1000)<=startrange){
					//预估里程≤起租里程 --->预估费用=起租价+附加费+调度费
					cost = startprice+surcharge+schedulefee;
				}else if((mileage/1000)<=standardrange){
					//起租里程<预估里程≤标准里程--->预估费用=起租价+(预估里程-起租里程)*续租价+附加费+调度费
					cost = startprice+((mileage/1000)-startrange)*renewalprice+surcharge+schedulefee;
				}else{
					//预估里程>标准里程--->起租价+(预估里程-起租里程)*续租价+(预估里程-标准里程)*续租价*空驶费率+附加费+调度费
					cost = startprice+((mileage/1000)-startrange)*renewalprice+((mileage/1000)-standardrange)*renewalprice*(emptytravelrate/100)+surcharge+schedulefee;
				}
				accountrules.put("cost",StringUtil.formatNum(cost, 1));
				accountrules.put("schedulefee", schedulefee);
				accountrules.put("tourfee", StringUtil.formatNum(cost-schedulefee, 1));
				res.put("costinfo", accountrules);
			}else{
				//网约车的预估
				Map<String,Object> accountrules = opdao.getOpOrderAccountRules4NetCar(params);
				if(accountrules==null){
					res.put("status", Retcode.NOSERVICESINCITY.code);
					res.put("message", Retcode.NOSERVICESINCITY.msg);
					return res;
				}
				BaiduApiQueryParam baqp = new BaiduApiQueryParam();
				baqp.setOrderStartLng(parseDouble(params.get("onaddrlng")));
				baqp.setOrderStartLat(parseDouble(params.get("onaddrlat")));
				baqp.setOrderEndLng(parseDouble(params.get("offaddrlng")));
				baqp.setOrderEndLat(parseDouble(params.get("offaddrlat")));
				Map<String,Object> direc = carserviceapi.dealRequestWithToken("/BaiduApi/GetMileageInfo", HttpMethod.POST, null, baqp, Map.class);
				if(direc==null||((int)direc.get("status")) != Retcode.OK.code){
					res.put("status", Retcode.FAILED.code);
					res.put("message", Retcode.FAILED.msg);
					logger.error("获取预估里程失败！");
					return res;
				}

				//约车时限
                double tempprice = 0;
                try{
                    Map<String,Object> carsintervalinfo = opdao.getSendRuleByCity4ReverceNetCar(params);
                    String usetime = (String) params.get("usetime");
                    String isusenow = (String) params.get("isusenow");
                    if(carsintervalinfo!=null&&(StringUtils.isNotBlank(usetime)||"0".equals(isusenow))){
                        int carsinterval = parseInt(carsintervalinfo.get("carsinterval"));
                        if(StringUtils.isNotBlank(usetime)){
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date current = new Date();
                            Date yydate = new Date(current.getTime()+carsinterval*60*1000);
                            Date usetimeobj = format.parse(usetime);
                            if(usetimeobj.after(yydate)){
                                //预约时间
//                                Map<String,Object> yyparams = new HashMap<String,Object>();
//                                yyparams.put("type","个人用户下单预约附加费用");
//                                tempprice += parseDouble(getYYFJF(yyparams));
                            	tempprice += parseDouble(accountrules.get("reservationpricep"));
                            }
                        }else{
                            //预约时间
//                            Map<String,Object> yyparams = new HashMap<String,Object>();
//                            yyparams.put("type","个人用户下单预约附加费用");
//                            tempprice += parseDouble(getYYFJF(yyparams));
                            tempprice += parseDouble(accountrules.get("reservationpricep"));
                        }
                    }
                }catch (Exception e){
				    logger.error("个人用户网约车添加预约用车预约费失败",e);
                }

				int mileage = (int)direc.get("distance");
				int times = (int)direc.get("duration");
				accountrules.put("mileage", mileage);
				accountrules.put("times", times);
				//计算价格
				double startprice = parseDouble(accountrules.get("startprice"))+tempprice;
                accountrules.put("startprice",startprice);
				double rangeprice = parseDouble(accountrules.get("rangeprice"));
				double timeprice = parseDouble(accountrules.get("timeprice"));
				String timetype = (String) accountrules.get("timetype");
				double perhour = parseDouble(accountrules.get("perhour"));
				double deadheadmileage = parseDouble(accountrules.get("deadheadmileage"));
				double deadheadprice = parseDouble(accountrules.get("deadheadprice"));
				double rangecost = StringUtil.formatNum(mileage/1000, 1)*rangeprice;
				accountrules.put("rangecost", StringUtil.formatNum(rangecost,1));
				//低速模式计算累计时长(预估时仍按总时长计费)
				double timecost = (times%60>0?(times/60+1):times/60)*timeprice;
				accountrules.put("timecost",StringUtil.formatNum(timecost,1));				
				double deadheadcost = 0;
				if (StringUtil.formatNum(mileage/1000, 1) > deadheadmileage) {
					deadheadcost = (StringUtil.formatNum(mileage/1000, 1) - deadheadmileage)*deadheadprice;
					if (deadheadcost != 0) {
						accountrules.put("deadheadcost", StringUtil.formatNum(deadheadcost,1));
					}
				}
				accountrules.put("cost",StringUtil.formatNum(startprice+rangecost+timecost+deadheadcost,1));
				res.put("costinfo", accountrules);
			}
		}catch(Exception e){
			logger.error("乘客端异常",e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
		}
		return res;
	}

	private long parseLong(Object value){
		if(value==null){
			return 0;
		}
		return Long.parseLong(String.valueOf(value));
	}
	
	/**
	 * 获取溢价倍数
	 * @param params
	 * @return
	 */
	public Map<String, Object> getPremium(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		String city = (String) params.get("city");
		String orderstyle = (String) params.get("orderstyle");
		String companyid = (String) params.get("companyid");
		long usetime = parseLong(params.get("usetime"));
		if(StringUtils.isBlank(city)||StringUtils.isBlank(orderstyle)){
			res.put("status", Retcode.FAILED.code);
			res.put("message", "参数不全");
			return res;
		}
		if(usetime<=0){
			usetime = (new Date()).getTime();
		}
		Map<String,Object> premiump = new HashMap<String,Object>();
		premiump.put("usetime", usetime);
		premiump.put("citycode", dicdao.getCityNo(city));
		premiump.put("cartype", "1".equals(orderstyle)?1:0);
		premiump.put("leasescompanyid", companyid);
		if(StringUtils.isBlank(companyid)){
			//个人订单，运管端
			premiump.put("platformtype", 0);
			Map<String,Object> result = carserviceapi.dealRequestWithToken("/PremiumRule/GetPremiumrate", HttpMethod.POST, null, premiump, Map.class);
			if(result!=null&&(int)result.get("status")==0){
				res.put("premium", result.get("premiumrate")==null?1:result.get("premiumrate"));
			}else{
				res.put("premium", 1);
			}
		}else{
			//机构订单，租赁端机构订单没有溢价倍数
//			premiump.put("platformtype", 1);
			res.put("premium", 1);
		}
		return res;
	}

	/**
	 * 取消前的判断
	 * @param params
	 * @return
	 */
	public Map<String, Object> preCancel(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		String orderno = (String) params.get("orderno");
		String ordertype = (String) params.get("ordertype");
		String usetype = (String) params.get("usetype");
		if(StringUtils.isBlank(orderno)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
			res.put("status", Retcode.FAILED.code);
			res.put("message", "参数不全");
			return res;
		}
		
		Map<String,Object> info = new HashMap<String,Object>();
		res.put("info", info);
		//取消的费用和原因
		Map<String,Object> result = carserviceapi.dealRequestWithToken("/OrderCancelRule/GetCancelPrice", HttpMethod.POST, null, params, Map.class);
		if(result!=null&&(int)result.get("status")==0){
			int pricereason = parseInt(result.get("pricereason"));
			int price = parseInt(result.get("price"));
			info.put("pricereason", pricereason);
			info.put("price", price);
		}else{
			info.put("pricereason", 1);
			info.put("price", 0);
			res.put("status", result.get("status"));
			res.put("message", result.get("message"));
		}
		//取消的原因列表
		List<String> resons = dicdao.getCancelResons();
		if(resons==null){
			resons = new ArrayList<String>();
		}
		info.put("resons", resons);
		return res;
	}

	/**
	 * 获取取消规则
	 * @param params
	 * @param res
	 */
	public void getCancelRules(Map<String, Object> params, HttpServletResponse res) {
		res.setContentType("text/html; charset=utf-8");
		String orderno = (String) params.get("orderno");
		String ordertype = (String) params.get("ordertype");
		String usetype = (String) params.get("usetype");
		Map<String,Object> orderinfo = null;
		if(!"2".equals(usetype)){
			//机构订单
			orderinfo = orderdao.getOrder4OrgNetCar(params);
		}else{
			if(isTaxiOrder(ordertype)){
				//出租车
				orderinfo = orderdao.getOrder4OpTaxi(params);
			}else{
				//网约车
				orderinfo = orderdao.getOrder4OpNetCar(params);
			}
		}
		if(orderinfo!=null){
			String cancelrule = null;
			String citycaption = dicdao.getCityCaption((String)orderinfo.get("oncity"));
			Map<String,Object> cancelinfo = new HashMap<String,Object>();
			//订单不为空的时候，查询取消规则
			if(!"2".equals(usetype)){
				//机构网约车
				cancelinfo.put("title", (String)orderinfo.get("selectedmodelcaption")+"▪"+citycaption);
				Map<String,Object> canceldetail = orderdao.getCancelInfo4Org(orderno);
				if(canceldetail!=null){
					cancelrule = (String) canceldetail.get("cancelrule");
				}
				cancelinfo.put("img",  SystemConfig.getSystemProperty("fileserver")+File.separator+orderinfo.get("selectedmodelpath"));
			}else{
				if(isTaxiOrder(ordertype)){
					//个人出租车
					cancelinfo.put("title", "出租车▪"+citycaption);
					Map<String,Object> canceldetail = orderdao.getCancelInfo4Optaxi(orderno);
					if(canceldetail!=null){
						cancelrule = (String) canceldetail.get("cancelrule");
					}
					cancelinfo.put("img",  "../img/taxicar.png");
				}else{
					//个人网约车
					cancelinfo.put("title", (String)orderinfo.get("selectedmodelcaption")+"▪"+citycaption);
					Map<String,Object> canceldetail = orderdao.getCancelInfo4Opnetcar(orderno);
					if(canceldetail!=null){
						cancelrule = (String) canceldetail.get("cancelrule");
					}
					cancelinfo.put("img",  SystemConfig.getSystemProperty("fileserver")+File.separator+orderinfo.get("selectedmodelpath"));
				}
			}
			if(StringUtils.isNotBlank(cancelrule)){
				try{
					PubOrderCancelRule cost = (PubOrderCancelRule)JSONObject.toBean(JSONObject.fromObject(cancelrule), PubOrderCancelRule.class);
					cancelinfo.put("cancelcount", cost.getCancelcount());
					cancelinfo.put("watingcount", cost.getWatingcount());
					cancelinfo.put("price", cost.getPrice());
				}catch(Exception e){
					logger.error("转换取消规则",e);
				}
			}
			VelocityContext context = new VelocityContext();
	        context.put("cancelinfo", cancelinfo);
	        String vmpath = PassengerService.class.getClassLoader().getResource("cancelrule.vm").getPath();
	        try {
	            VelocityUtil.createTemplate(vmpath, res.getWriter(), context);
	        } catch (Exception e) {
	            logger.error("乘客端异常",e);
	        }
		}
	}

	/**
	 * 获取可用优惠券列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getAbleCoupons(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		String orderno = (String) params.get("orderno");
		String ordertype = (String) params.get("ordertype");
		String usetype = (String) params.get("usetype");
		String usertoken = (String) params.get("usertoken");
		if(StringUtils.isBlank(orderno)||StringUtils.isBlank(usertoken)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
			res.put("status", Retcode.FAILED.code);
			res.put("message", "参数不全");
			return res;
		}
		if(!"2".equals(usetype)){
			//机构订单，机构订单没有优惠券的可能
			res.put("currentcount",0);
			res.put("details", new ArrayList<>());
		}else{
			Map<String,String> userinfo = Const.getUserInfo(usertoken);
		    String account = userinfo.get("account");
		    PeUser user = userdao.getUser4Op(account);
			Map<String,Object> selectcouponp = new HashMap<String,Object>();
			selectcouponp.put("orderno", orderno);
			selectcouponp.put("userid", user.getId());
			if(isTaxiOrder(ordertype)){
				//出租车
				Map<String,Object> orderinfo = orderdao.getOrder4OpTaxi(params);
				selectcouponp.put("servicetype", 1);
				selectcouponp.put("city", orderinfo.get("oncity"));
			}else{
				//网约车
				Map<String,Object> orderinfo = orderdao.getOrder4OpNetCar(params);
				selectcouponp.put("servicetype", 2);
				selectcouponp.put("city", orderinfo.get("oncity"));
			}
			List<Map<String,Object>> tempcoupons = userdao.getAbleCoupons(selectcouponp);
			List<Map<String,Object>> coupons = dillWithCoupons(tempcoupons);
	        res.put("details",coupons);
	        res.put("currentcount",coupons.size());
		}
		return res;
	}

	/**
	 * 使用锁定优惠券
	 * @param params
	 * @return
	 */
	public Map<String, Object> useCoupon(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		String orderno = (String) params.get("orderno");
		String ordertype = (String) params.get("ordertype");
		String usetype = (String) params.get("usetype");
		String couponid = (String) params.get("couponid");
		String usertoken = (String) params.get("usertoken");
		if(StringUtils.isBlank(orderno)||StringUtils.isBlank(usertoken)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
			res.put("status", Retcode.FAILED.code);
			res.put("message", "参数不全");
			return res;
		}
		if(!"2".equals(usetype)){
			//机构订单，机构订单没有优惠券的可能
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message", "机构订单不需要优惠券");
		}else{
			Map<String,String> userinfo = Const.getUserInfo(usertoken);
		    String account = userinfo.get("account");
		    PeUser user = userdao.getUser4Op(account);
			Map<String,Object> couponobj = userdao.getCouponInfo(couponid);
			Map<String,Object> usecouponp = new HashMap<String,Object>();
			usecouponp.put("couponidref", couponid);
			usecouponp.put("billingorderid", orderno);
			usecouponp.put("couponmoney", couponobj.get("money"));
			usecouponp.put("usetype", 2);
			usecouponp.put("discountamount", null);
			usecouponp.put("usestate", 0);
			usecouponp.put("userid", user==null?"":user.getId());
			if(StringUtils.isBlank(couponid)){
				//没有优惠券不使用
				userdao.deleteUseCouponOrder(usecouponp);
			}else{
				Map<String,Object> couponuseinfo = userdao.getOrderCouponUseInfo(params);
				if(couponuseinfo==null){
					//没有使用过优惠券，从新添加
					usecouponp.put("id", GUIDGenerator.newGUID());
					userdao.addCouponUseOrder(usecouponp);
				}else{
					//有优惠券，直接更新
					userdao.updateCouponUseOrder(usecouponp);
				}
			}
			
		}
		return res;
	}

	/**
	 * 获取出租车预估费用
	 * @param params
	 * @return
	 */
	public Map<String, Object> getEstimatedCost4Taxi(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		try{
			JSONObject obj = new JSONObject();
			String cityvalue = dicdao.getCityNo((String) params.get("city"));
	        params.put("city", cityvalue);
			res = getEstimatedCost4Op(params,true);
			if((int)res.get("status")!=Retcode.OK.code){
				//预估失败
				res.put("costinfo",new HashMap<>());
				return res;
			}
			Map<String,Object> costinfo = (Map<String, Object>)res.get("costinfo");
			double cost = parseDouble(costinfo.get("cost"));
			
            obj.put("awardpint", 0);
			JSONArray paydetail = new JSONArray();
			JSONObject obj1 = new JSONObject();
			obj1.put("name", "行程费");
			obj1.put("price", costinfo.get("tourfee")+"元");
			JSONObject obj2 = new JSONObject();
			obj2.put("name", "调度费");
			obj2.put("price", costinfo.get("schedulefee")+"元");
			paydetail.add(obj1);
			paydetail.add(obj2);
			obj.put("paydetail",paydetail);
			//添加优惠券
			params.put("servicetype", 1);
            dillCoupons4OpUser(obj,params);
			//添加溢价倍数
            params.put("orderstyle", "1");
            dillPremium(obj,params);
            double premium = (double)obj.get("premium");
            double schedulefee = parseDouble(costinfo.get("schedulefee"));
            cost = StringUtil.formatNum(((new BigDecimal(cost).subtract(new BigDecimal(schedulefee))).multiply(new BigDecimal(premium))).add(new BigDecimal(schedulefee)).doubleValue(), 1);
            obj.put("cost", cost);
            //添加实际费用
            double actualcost = 0;
            double couponmoney = parseDouble(obj.get("couponmoney"));
            if(cost>couponmoney){
            	actualcost = new BigDecimal(cost).subtract(new BigDecimal(couponmoney)).doubleValue();
            }
            obj.put("actualcost",actualcost);
			res.put("costinfo", obj);
		}catch(Exception e){
			logger.error("乘客端异常",e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("costinfo",new HashMap<>());
		}
		return res;
	}

	public Map<String, Object> payOder(Map<String, Object> params, HttpServletRequest request) {
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
                return res;
            }
            //检查订单信息是否可以支付
            Map<String,Object> validinfo = checkOrderValid(orderno,ordertype,usetype);
            if(Retcode.OK.code!=(int)validinfo.get("status")){
            	return validinfo;
            }
            
            //可以支付
            Map<String,Object> orderinfo = (Map<String, Object>)validinfo.get("order");
            if(!"2".equals(usetype)){
            	//机构订单
//            	payOrgOrder(orderinfo,params,res,request);
            	res.put("status", Retcode.INVALIDORDERSTATUS.code);
                res.put("message", "订单不需要支付");
                return res;
            }else{
            	//个人订单
            	if(isTaxiOrder(ordertype)){
            		//出租车订单
            		payOpTaxi(orderinfo,params,res,request);
            	}else{
            		//网约车订单
            		payOpNetCar(orderinfo,params,res,request);
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
	 * 支付机构订单
	 * @param orderinfo
	 * @param params
	 * @param res
	 * @param request
	 */
	private void payOrgOrder(Map<String, Object> orderinfo, Map<String, Object> params, Map<String, Object> res, HttpServletRequest request) {
		try{
			String orderstatus = (String) orderinfo.get("orderstatus");
			if(!OrderState.CANCEL.state.equals(orderstatus)){
				//取消支付
				String paytype = (String) params.get("paytype");
				String companyid = (String) orderinfo.get("companyid");
				LeasesCompany company = orgdao.getLeasesCompanyById(companyid);
				if(company==null){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","账户信息不全");
					res.put("payorderinfo","");
					return ;
				}
				double orderamount = parseDouble(orderinfo.get("cancelfee"));
				String orderno = (String) orderinfo.get("orderno");
				if("1".equalsIgnoreCase(paytype)){
					//支付宝充值
					String alipaystatus = company.getAlipaystatus();
					if(!"1".equals(alipaystatus)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","暂不支持支付宝支付");
						res.put("payorderinfo","");
						return ;
					}
					String appid = company.getAlipayappid();
					String alipubkey = company.getAlipaypublickey();
					String privatekey = company.getAlipayprivatekey();
					if(StringUtils.isBlank(appid)||StringUtils.isBlank(alipubkey)||StringUtils.isBlank(privatekey)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","账户信息不全");
						res.put("payorderinfo","");
						return ;
					}
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					//获取订单的交易号 (时间加上5位随机码)
					String out_trade_no = format.format(date)+UNID.getUNID();
					//添加交易号记录流水
					Map<String,String> orderinfo2 = new HashMap<String,String>();
					orderinfo2.put("out_trade_no", out_trade_no);
					orderinfo2.put("orderno", orderno);
					orderinfo2.put("paymenttype", "0");
					orderinfo2.put("validatekey", alipubkey);
					orderdao.addTradeNo4OrgOrder(orderinfo2);
					orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
					String zfbsubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbsubject"))?"好约车":SystemConfig.getSystemProperty("zfbsubject");
					zfbsubject += orderno;
					String zfbbody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbbody"))?"好约车付款":SystemConfig.getSystemProperty("zfbbody");
					Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", orderamount, zfbsubject, zfbbody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillZFBPayed4OrgCancel");
					String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
					String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
					String payorderinfo = payorderstr + "&" + sign;
					res.put("payorderinfo", payorderinfo);
				}else{
					//微信支付
					String wechatstatus = company.getWechatstatus();
					if(!"1".equals(wechatstatus)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","暂不支持微信支付");
						res.put("payorderinfo","");
						return ;
					}
					String appid = company.getWechatappid();
					String chantno = company.getWechatmerchantno();
					String secretkey = company.getWechatsecretkey();
					if(StringUtils.isBlank(appid)||StringUtils.isBlank(chantno)||StringUtils.isBlank(secretkey)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","账户信息不全");
						res.put("payorderinfo","");
						return ;
					}
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					//获取订单的交易号 (时间加上5位随机码)
					String out_trade_no = format.format(date)+UNID.getUNID();
					//添加交易号记录流水
					Map<String,String> orderinfo2 = new HashMap<String,String>();
					orderinfo2.put("out_trade_no", out_trade_no);
					orderinfo2.put("orderno", orderno);
					orderinfo2.put("paymenttype", "1");
					orderinfo2.put("validatekey", secretkey);
					orderdao.addTradeNo4OrgOrder(orderinfo2);
					String tempipadd = request.getRemoteAddr();
					String ipadd = tempipadd.indexOf(":")>=0?"0.0.0.0":tempipadd;
					orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
					String ordermoney = new java.text.DecimalFormat("#").format(orderamount*100);
					String wxbody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxbody"))?"好约车——订单付款":SystemConfig.getSystemProperty("wxbody");
					Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, chantno, wxbody, out_trade_no, ordermoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillWXPayed4OrgCancel", "APP",ipadd);
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
				res.put("status", Retcode.INVALIDORDERSTATUS.code);
	            res.put("message", "订单状态不对");
			}
		}catch(Exception e){
			logger.error("机构订单支付失败",e);
			res.put("status", Retcode.EXCEPTION.code);
            res.put("message", "支付异常");
		}
	}

	/**
	 * 个人出租车支付
	 * @param orderinfo
	 * @param params
	 * @param res
	 * @param request
	 */
	private void payOpTaxi(Map<String, Object> orderinfo, Map<String, Object> params, Map<String, Object> res,HttpServletRequest request) {
		String orderstatus = (String) orderinfo.get("orderstatus");
		if(OrderState.SERVICEDONE.state.equals(orderstatus)){
			try{
				//行程结束（长是不是不需要支付就可以直接使用优惠券关闭的）
				double orderamount = parseDouble(orderinfo.get("schedulefee"));
				String paymentmethod = (String) orderinfo.get("paymentmethod");
				if("0".equalsIgnoreCase(paymentmethod)){
					//线上支付
                    orderamount += parseDouble(orderinfo.get("orderamount"));
				}
	            Map<String,Object> ordercouponinfo = userdao.getOrderCouponUseInfo(params);
	            if(ordercouponinfo!=null){
	            	 double couponmoney = parseDouble(ordercouponinfo.get("couponmoney"));
	            	 if(couponmoney>=orderamount){
	 	            	//全部使用抵用券支付了
	 	            	//更新订单状态
	 					Map<String, Object> orderparam = new HashMap<String,Object>();
	                 	orderparam.put("paymentstatus", "1");
	                 	orderparam.put("orderno", orderinfo.get("orderno"));
	                 	orderparam.put("paytype", "2");
	                 	orderdao.payed4OpTaxiOrder(orderparam);
	 	            	//更新优惠券的使用
	 	                dillCouponUseInfo4Op(orderinfo);
	 	                res.put("needpay", false);
		 	            return ;
	 	            }
	            }
			}catch(Exception e){
				logger.error("支付异常",e);
			}
		}
		
		String paytype = (String) params.get("paytype");
		//行程费用支付
		if("3".equalsIgnoreCase(paytype)){
            //钱包支付
            payOpTaxiByBalance4Op(orderinfo,params,res);
            res.put("needpay", false);
        }else{
        	//第三方支付
        	payOpTaxiByOther(orderinfo,params,res,request);
        	res.put("needpay", true);
        }
	}

	/**
	 * 第三方支付出租车
	 * @param orderinfo
	 * @param params
	 * @param res
	 * @param request
	 */
	private void payOpTaxiByOther(Map<String, Object> orderinfo, Map<String, Object> params, Map<String, Object> res,HttpServletRequest request) {
		try{
			Map<String,Object> payinfo = dicdao.getPayInfo4Op();
			if(payinfo==null){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","账户信息不全");
				res.put("payorderinfo","");
				return ;
			}
			String paytype = (String) params.get("paytype");
			//网约车支付
			if("1".equalsIgnoreCase(paytype)){
				//支付宝充值
				String alipaystatus = (String) payinfo.get("alipaystatus");
				if(!"1".equals(alipaystatus)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","暂不支持支付宝支付");
					res.put("payorderinfo","");
					return ;
				}
				String appid = (String) payinfo.get("alipayappid");
				String alipubkey = (String) payinfo.get("alipaypublickey");
				String privatekey = (String) payinfo.get("alipayprivatekey");
				if(StringUtils.isBlank(appid)||StringUtils.isBlank(alipubkey)||StringUtils.isBlank(privatekey)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","账户信息不全");
					res.put("payorderinfo","");
					return ;
				}
				String orderno = (String) orderinfo.get("orderno");
				String orderstatus = (String) orderinfo.get("orderstatus");
				//添加交易号记录流水
				Date date = new Date();
	            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	            //获取订单的交易号 (时间加上5位随机码)
	            String out_trade_no = format.format(date)+UNID.getUNID();
	            Map<String,String> orderinfo2 = new HashMap<String,String>();
	            orderinfo2.put("out_trade_no", out_trade_no);
	            orderinfo2.put("orderno", orderno);
	            orderinfo2.put("paymenttype", "0");
	            orderinfo2.put("validatekey", alipubkey);
                orderdao.addTradeNo4OpTaxiOrder(orderinfo2);
				if(OrderState.CANCEL.state.equals(orderstatus)){
					//取消的订单
					double orderamount = parseDouble(orderinfo.get("cancelfee"));
					orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
	                String zfbsubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbsubject"))?"好约车":SystemConfig.getSystemProperty("zfbsubject");
	                zfbsubject += orderno;
	                String zfbbody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbbody"))?"好约车付款":SystemConfig.getSystemProperty("zfbbody");
	                Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", orderamount, zfbsubject, zfbbody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillZFBPayed4OpCancel");
	                String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
	                String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
	                String payorderinfo = payorderstr + "&" + sign;
	                res.put("payorderinfo", payorderinfo);
				}else{
					//行程结束的订单
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
	                //减去优惠券之后的支付金额
	                double couponmoney = 0;
	                Map<String,Object> ordercouponinfo = userdao.getOrderCouponUseInfo(params);
	                if(ordercouponinfo!=null){
	                	couponmoney = parseDouble(ordercouponinfo.get("couponmoney"));
	                }
					orderamount = orderamount-couponmoney;
	                orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
	                String zfbsubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbsubject"))?"好约车":SystemConfig.getSystemProperty("zfbsubject");
	                zfbsubject += orderno;
	                String zfbbody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbbody"))?"好约车付款":SystemConfig.getSystemProperty("zfbbody");
	                Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", orderamount, zfbsubject, zfbbody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillZFBPayed4Op");
	                String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
	                String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
	                String payorderinfo = payorderstr + "&" + sign;
	                res.put("payorderinfo", payorderinfo);
				}
			}else{
				//微信充值
				String wechatstatus = (String) payinfo.get("wechatstatus");
				if(!"1".equals(wechatstatus)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","暂不支持微信支付");
					res.put("payorderinfo","");
					return ;
				}
				String appid = (String) payinfo.get("wechatappid");
				String chantno = (String) payinfo.get("wechatmerchantno");
				String secretkey = (String) payinfo.get("wechatsecretkey");
				if(StringUtils.isBlank(appid)||StringUtils.isBlank(chantno)||StringUtils.isBlank(secretkey)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","账户信息不全");
					res.put("payorderinfo","");
					return ;
				}
				String orderno = (String) orderinfo.get("orderno");
				String orderstatus = (String) orderinfo.get("orderstatus");
				Date date = new Date();
	            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	            //获取订单的交易号 (时间加上5位随机码)
	            String out_trade_no = format.format(date)+UNID.getUNID();
				//添加交易流水记录
	            Map<String,String> orderinfo2 = new HashMap<String,String>();
	            orderinfo2.put("out_trade_no", out_trade_no);
	            orderinfo2.put("orderno", orderno);
	            orderinfo2.put("paymenttype", "1");
	            orderinfo2.put("validatekey", secretkey);
                orderdao.addTradeNo4OpTaxiOrder(orderinfo2);
                String tempipadd = request.getRemoteAddr();
                String ipadd = tempipadd.indexOf(":")>=0?"0.0.0.0":tempipadd;
				if(OrderState.CANCEL.state.equals(orderstatus)){
					//取消的订单
					double orderamount = parseDouble(orderinfo.get("cancelfee"));
					orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
	                String ordermoney = new java.text.DecimalFormat("#").format(orderamount*100);
	                String wxbody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxbody"))?"好约车——订单付款":SystemConfig.getSystemProperty("wxbody");
	                Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, chantno, wxbody, out_trade_no, ordermoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillWXPayed4OpCancel", "APP",ipadd);
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
				}else{
					//行程结束的订单
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
	                //减去优惠券之后的支付金额
	                double couponmoney = 0;
	                Map<String,Object> ordercouponinfo = userdao.getOrderCouponUseInfo(params);
	                if(ordercouponinfo!=null){
	                	couponmoney = parseDouble(ordercouponinfo.get("couponmoney"));
	                }
					orderamount = orderamount-couponmoney;
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
		}catch(Exception e){
			logger.error("第三方支付失败,异常",e);
		}
	}

	/**
	 * 余额支付出租车
	 * @param orderinfo
	 * @param params
	 * @param res
	 */
	private void payOpTaxiByBalance4Op(Map<String, Object> orderinfo, Map<String, Object> params,Map<String, Object> res) {
		try{
			String orderstatus = (String) orderinfo.get("orderstatus");
			String usertoken = (String) params.get("usertoken");
            String account = Const.getUserInfo(usertoken).get("account");
            String orderno = (String) params.get("orderno");
            PeUser peuser = userdao.getUser4Op(account);
            double balance = userdao.getUserBalance4Op(peuser.getId());
            if(OrderState.CANCEL.state.equals(orderstatus)){
            	//取消的支付
            	double cancelfee = parseDouble(orderinfo.get("cancelfee"));
            	if(balance < cancelfee){
            		//不够支付
                    res.put("status", Retcode.NOTENOUGHBALANCE.code);
                    res.put("message", "余额不足！");
            	}else{
            		//更新支付的金额
            		//更新钱包
                    Map<String,Object> updatebalancep = new HashMap<String,Object>();
                    updatebalancep.put("uid", GUIDGenerator.newGUID());
                    updatebalancep.put("money", cancelfee);
                    updatebalancep.put("userid", peuser.getId());
                    updatebalancep.put("isout", 1);
                    userdao.updateUserBalance4OpSec(updatebalancep);
                    //添加支付记录
                    Map<String,Object> expenses = new HashMap<String,Object>();
                    expenses.put("logid", GUIDGenerator.newGUID());
                    expenses.put("userid",peuser.getId());
                    expenses.put("expensetype","4");
                    expenses.put("money",cancelfee);
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
            	}
            }else{
                //行程的支付
            	double orderamount = parseDouble(orderinfo.get("schedulefee"));
                String paymentmethod = (String) orderinfo.get("paymentmethod");
                if("0".equalsIgnoreCase(paymentmethod)){
                    //线上支付
                    orderamount += parseDouble(orderinfo.get("orderamount"));
                }
                double couponmoney = 0;
                Map<String,Object> ordercouponinfo = userdao.getOrderCouponUseInfo(params);
                if(ordercouponinfo!=null){
                	couponmoney = parseDouble(ordercouponinfo.get("couponmoney"));
                }
            	//部分使用抵用券支付了
                if(balance<orderamount-couponmoney){
                    //不够支付
                    res.put("status", Retcode.NOTENOUGHBALANCE.code);
                    res.put("message", "余额不足！");
                }else{
                    //更新钱包
                	orderamount = orderamount-couponmoney;
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
                    if("0".equalsIgnoreCase(paymentmethod)){
                        //线上支付
                        //给司机钱包加钱并且加收入明细
                        addMoney4Driver(orderinfo);
                    }
                }
            }
        }catch(Exception e){
            logger.error("钱包支付出租车订单", e);
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
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
	 * 个人网约车支付
	 * @param orderinfo
	 * @param res
	 * @return
	 */
	private void payOpNetCar(Map<String, Object> orderinfo,Map<String, Object> params,Map<String,Object> res,HttpServletRequest request) {
		String orderstatus = (String) orderinfo.get("orderstatus");
		if(OrderState.SERVICEDONE.state.equals(orderstatus)){
			try{
				//行程结束（长是不是不需要支付就可以直接使用优惠券关闭的）
				double orderamount = parseDouble(orderinfo.get("orderamount"));
	            Map<String,Object> ordercouponinfo = userdao.getOrderCouponUseInfo(params);
	            if(ordercouponinfo!=null){
	            	double couponmoney = parseDouble(ordercouponinfo.get("couponmoney"));
		            if(couponmoney>=orderamount){
		            	//全部使用抵用券支付了
		            	//更新订单状态
		            	 Map<String, Object> orderparam = new HashMap<String, Object>();
		                 orderparam.put("paymentstatus", "1");
		                 orderparam.put("orderno", orderinfo.get("orderno"));
		                 orderparam.put("paytype", "1");
		                 orderdao.payed4OpOrder(orderparam);
		            	//更新优惠券的使用
		                dillCouponUseInfo4Op(orderinfo);
		                res.put("needpay", false);
			            return ;
		            }
	            }
			}catch(Exception e){
				logger.error("支付异常",e);
			}
		}
		String paytype = (String) params.get("paytype");
		//行程费用支付
		if("3".equalsIgnoreCase(paytype)){
            //钱包支付
            payOpNetCarByBalance4Op(orderinfo,params,res);
            res.put("needpay", false);
        }else{
        	//第三方支付
        	payOpNetCarByOther(orderinfo,params,res,request);
        	res.put("needpay", true);
        }
	}

	/**
	 * 个人用户网约车
	 * @param orderinfo
	 * @param params
	 * @param res
	 */
	private void payOpNetCarByOther(Map<String, Object> orderinfo, Map<String, Object> params,Map<String, Object> res,HttpServletRequest request) {
		try{
			Map<String,Object> payinfo = dicdao.getPayInfo4Op();
			if(payinfo==null){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","账户信息不全");
				res.put("payorderinfo","");
				return ;
			}
			String paytype = (String) params.get("paytype");
			//网约车支付
			if("1".equalsIgnoreCase(paytype)){
				//支付宝充值
				String alipaystatus = (String) payinfo.get("alipaystatus");
				if(!"1".equals(alipaystatus)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","暂不支持支付宝支付");
					res.put("payorderinfo","");
					return ;
				}
				String appid = (String) payinfo.get("alipayappid");
				String alipubkey = (String) payinfo.get("alipaypublickey");
				String privatekey = (String) payinfo.get("alipayprivatekey");
				if(StringUtils.isBlank(appid)||StringUtils.isBlank(alipubkey)||StringUtils.isBlank(privatekey)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","账户信息不全");
					res.put("payorderinfo","");
					return ;
				}
				String orderno = (String) orderinfo.get("orderno");
				String orderstatus = (String) orderinfo.get("orderstatus");
				//添加交易号记录流水
				Date date = new Date();
	            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	            //获取订单的交易号 (时间加上5位随机码)
	            String out_trade_no = format.format(date)+UNID.getUNID();
				Map<String,String> orderinfo2 = new HashMap<String,String>();
				orderinfo2.put("out_trade_no", out_trade_no);
				orderinfo2.put("orderno", orderno);
				orderinfo2.put("paymenttype", "0");
				orderinfo2.put("validatekey", alipubkey);
				orderdao.addTradeNo4OpOrder(orderinfo2);
				if(OrderState.CANCEL.state.equals(orderstatus)){
					//取消的订单
					double orderamount = parseDouble(orderinfo.get("cancelfee"));
					orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
					String zfbsubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbsubject"))?"好约车":SystemConfig.getSystemProperty("zfbsubject");
					zfbsubject += orderno;
					String zfbbody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbbody"))?"好约车付款":SystemConfig.getSystemProperty("zfbbody");
					Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", orderamount, zfbsubject, zfbbody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillZFBPayed4OpCancel");
					String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
					String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
					String payorderinfo = payorderstr + "&" + sign;
					res.put("payorderinfo", payorderinfo);
				}else{
					//行程结束的订单
					//创建支付信息
					double couponmoney = 0;
					Map<String,Object> ordercouponinfo = userdao.getOrderCouponUseInfo(params);
		            if(ordercouponinfo!=null){
	                	couponmoney = parseDouble(ordercouponinfo.get("couponmoney"));
	                }
					double orderamount = parseDouble(orderinfo.get("orderamount")); 
					orderamount = orderamount-couponmoney;
					orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
					String zfbsubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbsubject"))?"好约车":SystemConfig.getSystemProperty("zfbsubject");
					zfbsubject += orderno;
					String zfbbody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbbody"))?"好约车付款":SystemConfig.getSystemProperty("zfbbody");
					Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", orderamount, zfbsubject, zfbbody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillZFBPayed4Op");
					String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
					String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
					String payorderinfo = payorderstr + "&" + sign;
					res.put("payorderinfo", payorderinfo);
				}
			}else{
				//微信充值
				String wechatstatus = (String) payinfo.get("wechatstatus");
				if(!"1".equals(wechatstatus)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","暂不支持微信支付");
					res.put("payorderinfo","");
					return ;
				}
				String appid = (String) payinfo.get("wechatappid");
				String chantno = (String) payinfo.get("wechatmerchantno");
				String secretkey = (String) payinfo.get("wechatsecretkey");
				if(StringUtils.isBlank(appid)||StringUtils.isBlank(chantno)||StringUtils.isBlank(secretkey)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","账户信息不全");
					res.put("payorderinfo","");
					return ;
				}
				String orderno = (String) orderinfo.get("orderno");
				String orderstatus = (String) orderinfo.get("orderstatus");
				Date date = new Date();
	            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	            //获取订单的交易号 (时间加上5位随机码)
	            String out_trade_no = format.format(date)+UNID.getUNID();
				//添加交易流水记录
				Map<String,String> orderinfo2 = new HashMap<String,String>();
				orderinfo2.put("out_trade_no", out_trade_no);
				orderinfo2.put("orderno", orderno);
				orderinfo2.put("paymenttype", "1");
				orderinfo2.put("validatekey", secretkey);
				orderdao.addTradeNo4OpOrder(orderinfo2);
				if(OrderState.CANCEL.state.equals(orderstatus)){
					//取消的订单
					double orderamount = parseDouble(orderinfo.get("cancelfee"));
					String tempipadd = request.getRemoteAddr();
					String ipadd = tempipadd.indexOf(":")>=0?"0.0.0.0":tempipadd;
					orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
					String ordermoney = new java.text.DecimalFormat("#").format(orderamount*100);
					String wxbody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxbody"))?"好约车——订单付款":SystemConfig.getSystemProperty("wxbody");
					Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, chantno, wxbody, out_trade_no, ordermoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillWXPayed4OpCancel", "APP",ipadd);
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
				}else{
					//行程结束的订单
					//创建支付信息
					double couponmoney = 0;
					Map<String,Object> ordercouponinfo = userdao.getOrderCouponUseInfo(params);
		            if(ordercouponinfo!=null){
	                	couponmoney = parseDouble(ordercouponinfo.get("couponmoney"));
	                }
					double orderamount = parseDouble(orderinfo.get("orderamount")); 
					orderamount = orderamount-couponmoney;
					String tempipadd = request.getRemoteAddr();
					String ipadd = tempipadd.indexOf(":")>=0?"0.0.0.0":tempipadd;
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
		}catch(Exception e){
			logger.error("第三方支付失败,异常",e);
		}
	}

	/**
	 * 个人用户网约车钱包支付
	 * @param params
	 * @param res
	 */
	private void payOpNetCarByBalance4Op(Map<String, Object> orderinfo,Map<String, Object> params,Map<String,Object> res) {
		 try {
            String usertoken = (String) params.get("usertoken");
            String orderno = (String) params.get("orderno");
            String ordertype = (String) params.get("ordertype");
            String usetype = (String) params.get("usetype");
            if (StringUtils.isBlank(usertoken) || StringUtils.isBlank(orderno) || StringUtils.isBlank(ordertype) || StringUtils.isBlank(usetype)) {
                res.put("status", Retcode.FAILED.code);
                res.put("message", "支付失败，参数不全");
            }
            
            String orderstatus = (String) orderinfo.get("orderstatus");
            String account = Const.getUserInfo(usertoken).get("account");
            PeUser peuser = userdao.getUser4Op(account);
            double balance = userdao.getUserBalance4Op(peuser.getId());
            if(OrderState.CANCEL.state.equals(orderstatus)){
            	//取消的支付
            	double cancelfee = parseDouble(orderinfo.get("cancelfee"));
            	if(balance < cancelfee){
            		//不够支付
                    res.put("status", Retcode.NOTENOUGHBALANCE.code);
                    res.put("message", "余额不足！");
            	}else{
            		//更新支付的金额
            		 //更新钱包
                    Map<String, Object> updatebalancep = new HashMap<String, Object>();
                    updatebalancep.put("uid", GUIDGenerator.newGUID());
                    updatebalancep.put("money", cancelfee);
                    updatebalancep.put("userid", peuser.getId());
                    updatebalancep.put("isout", 1);
                    userdao.updateUserBalance4OpSec(updatebalancep);
                    //添加支付记录
                    Map<String, Object> expenses = new HashMap<String, Object>();
                    expenses.put("logid", GUIDGenerator.newGUID());
                    expenses.put("userid", peuser.getId());
                    expenses.put("expensetype", "4");
                    expenses.put("money", cancelfee);
                    expenses.put("remark", "余额支付");
                    expenses.put("tradetype", "1");
                    expenses.put("detailtype", "0");
                    expenses.put("operateresult", "0");
                    userdao.addExpenses4OpSec(expenses);
                    //更新订单状态
                    Map<String, Object> orderparam = new HashMap<String, Object>();
                    orderparam.put("paymentstatus", "1");
                    orderparam.put("orderno", orderno);
                    orderparam.put("paytype", "1");
                    orderdao.payed4OpOrder(orderparam);
            	}
            }else{
                //行程的支付
                double orderamount = parseDouble(orderinfo.get("orderamount"));
                double couponmoney = 0;
                Map<String,Object> ordercouponinfo = userdao.getOrderCouponUseInfo(params);
                if(ordercouponinfo!=null){
                	couponmoney = parseDouble(ordercouponinfo.get("couponmoney"));
                }
            	//部分使用抵用券支付了
            	if(balance < orderamount-couponmoney){
            		//不够支付
                    res.put("status", Retcode.NOTENOUGHBALANCE.code);
                    res.put("message", "余额不足！");
            	}else{
            		//更新支付的金额
            		orderamount = orderamount-couponmoney;
            		 //更新钱包
                    Map<String, Object> updatebalancep = new HashMap<String, Object>();
                    updatebalancep.put("uid", GUIDGenerator.newGUID());
                    updatebalancep.put("money", orderamount);
                    updatebalancep.put("userid", peuser.getId());
                    updatebalancep.put("isout", 1);
                    userdao.updateUserBalance4OpSec(updatebalancep);
                    //添加支付记录
                    Map<String, Object> expenses = new HashMap<String, Object>();
                    expenses.put("logid", GUIDGenerator.newGUID());
                    expenses.put("userid", peuser.getId());
                    expenses.put("expensetype", "4");
                    expenses.put("money", orderamount);
                    expenses.put("remark", "余额支付");
                    expenses.put("tradetype", "1");
                    expenses.put("detailtype", "0");
                    expenses.put("operateresult", "0");
                    userdao.addExpenses4OpSec(expenses);
                    //更新订单状态
                    Map<String, Object> orderparam = new HashMap<String, Object>();
                    orderparam.put("paymentstatus", "1");
                    orderparam.put("orderno", orderno);
                    orderparam.put("paytype", "1");
                    orderdao.payed4OpOrder(orderparam);
                    //更新优惠券的使用
                    dillCouponUseInfo4Op(orderinfo);
            	}
            }
        } catch (Exception e) {
            logger.error("钱包支付网约车报错", e);
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
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

	/**
     * 检查订单状态是否可以支付
     * @param orderno
     * @param ordertype
     * @return
     */
    private Map<String, Object> checkOrderValid(String orderno, String ordertype,String usetype) {
        Map<String, Object> res = new HashMap<String,Object>();
        res.put("status", Retcode.OK.code);
        res.put("message", Retcode.OK.msg);
        try{
            if(StringUtils.isBlank(orderno)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
                res.put("status", Retcode.EXCEPTION.code);
                res.put("message", "参数不完整");
                return res;
            }
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("orderno", orderno);
            if(!"2".equals(usetype)){
            	//机构端
//            	Map<String,Object> orderinfo = orderdao.getOrder4OrgNetCar(params);
//            	res.put("order", orderinfo);
//        		if(orderinfo==null){
//        			 res.put("status", Retcode.ORDERNOTEXIT.code);
//                     res.put("message", Retcode.ORDERNOTEXIT.msg);
//                     return res;
//        		}
//        		String orderstatus = (String) orderinfo.get("orderstatus");
//        		String paymentstatus = (String) orderinfo.get("paymentstatus");
//        		//订单状态查看
//        		if(OrderState.CANCEL.state.equals(orderstatus)){
//        			if(OrderState.CANCEL.state.equals(orderstatus)){
//        				//取消查看有没有取消费
//        				//取消就去查看这个订单有没有取消费
//    					Map<String,Object> cancelinfo = orderdao.getCancelInfo4Org((String)orderinfo.get("orderno"));
//    					if(PayState.NOTPAY.state.equalsIgnoreCase(paymentstatus)&&cancelinfo!=null&&parseDouble(cancelinfo.get("cancelamount"))>0){
//    						orderinfo.put("cancelfee",parseDouble(cancelinfo.get("cancelamount")));
//    					}else{
//    						res.put("status", Retcode.INVALIDORDERSTATUS.code);
//                            res.put("message", "订单不需要支付");
//                            return res;
//    					}
//        			}
//        		}else{
        			res.put("status", Retcode.INVALIDORDERSTATUS.code);
                    res.put("message", "订单不需要支付");
                    return res;
//        		}
//        		//订单支付状态
//        		if(PayState.PAYED.state.equalsIgnoreCase(paymentstatus)){
//                    res.put("status", Retcode.INVALIDORDERSTATUS.code);
//                    res.put("message", "订单已经支付过");
//                    return res;
//                }
            }else{
            	//个人端
            	if(isTaxiOrder(ordertype)){
            		//出租车
            		Map<String,Object> orderinfo = orderdao.getOrder4OpTaxi(params);
            		res.put("order", orderinfo);
            		if(orderinfo==null){
            			 res.put("status", Retcode.ORDERNOTEXIT.code);
                         res.put("message", Retcode.ORDERNOTEXIT.msg);
                         return res;
            		}
            		String orderstatus = (String) orderinfo.get("orderstatus");
            		String paymentstatus = (String) orderinfo.get("paymentstatus");
            		//订单状态查看
            		if(OrderState.SERVICEDONE.state.equals(orderstatus)||OrderState.CANCEL.state.equals(orderstatus)){
            			if(OrderState.CANCEL.state.equals(orderstatus)){
            				//取消查看有没有取消费
            				//取消就去查看这个订单有没有取消费
        					Map<String,Object> cancelinfo = orderdao.getCancelInfo4Optaxi((String)orderinfo.get("orderno"));
        					if(PayState.NOTPAY.state.equalsIgnoreCase(paymentstatus)&&cancelinfo!=null&&parseDouble(cancelinfo.get("cancelamount"))>0){
        						orderinfo.put("cancelfee",parseDouble(cancelinfo.get("cancelamount")));
        					}else{
        						res.put("status", Retcode.INVALIDORDERSTATUS.code);
                                res.put("message", "订单不需要支付");
                                return res;
        					}
            			}
            		}else{
            			res.put("status", Retcode.INVALIDORDERSTATUS.code);
                        res.put("message", "订单不需要支付");
                        return res;
            		}
            		//订单支付状态
            		if(!(PayState.NOTPAY.state.equalsIgnoreCase(paymentstatus)||PayState.ALLNOPAY.state.equalsIgnoreCase(paymentstatus)||PayState.PASSENGERNOPAY.state.equalsIgnoreCase(paymentstatus))){
                        res.put("status", Retcode.INVALIDORDERSTATUS.code);
                        res.put("message", "订单已经支付过");
                        return res;
                    }
            	}else{
            		//网约车
            		Map<String,Object> orderinfo = orderdao.getOrder4OpNetCar(params);
            		res.put("order", orderinfo);
            		if(orderinfo==null){
            			 res.put("status", Retcode.ORDERNOTEXIT.code);
                         res.put("message", Retcode.ORDERNOTEXIT.msg);
                         return res;
            		}
            		String orderstatus = (String) orderinfo.get("orderstatus");
            		String paymentstatus = (String) orderinfo.get("paymentstatus");
            		//订单状态查看
            		if(OrderState.SERVICEDONE.state.equals(orderstatus)||OrderState.CANCEL.state.equals(orderstatus)){
            			if(OrderState.CANCEL.state.equals(orderstatus)){
            				//取消查看有没有取消费
            				//取消就去查看这个订单有没有取消费
        					Map<String,Object> cancelinfo = orderdao.getCancelInfo4Opnetcar((String)orderinfo.get("orderno"));
        					if(PayState.NOTPAY.state.equalsIgnoreCase(paymentstatus)&&cancelinfo!=null&&parseDouble(cancelinfo.get("cancelamount"))>0){
        						orderinfo.put("cancelfee",parseDouble(cancelinfo.get("cancelamount")));
        					}else{
        						res.put("status", Retcode.INVALIDORDERSTATUS.code);
                                res.put("message", "订单不需要支付");
                                return res;
        					}
            			}
            		}else{
            			res.put("status", Retcode.INVALIDORDERSTATUS.code);
                        res.put("message", "订单不需要支付");
                        return res;
            		}
            		//订单支付状态
            		if(PayState.PAYED.state.equalsIgnoreCase(paymentstatus)){
                        res.put("status", Retcode.INVALIDORDERSTATUS.code);
                        res.put("message", "订单已经支付过");
                        return res;
                    }
            	}
            }
        }catch(Exception e){
            logger.error("检查订单状态出错", e);
            res.put("status", Retcode.EXCEPTION.code);
            res.put("message", Retcode.EXCEPTION.msg);
        }
        return res;
    }

    /**
     * 提交取消原因
     * @param params
     * @return
     */
	public Map<String, Object> submitCancelReson(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
	    res.put("status", Retcode.OK.code);
	    res.put("message",Retcode.OK.msg);
	    String usertoken = (String) params.get("usertoken");
	    String orderno = (String) params.get("orderno");
	    String ordertype = (String) params.get("ordertype");
	    String usetype = (String) params.get("usetype");
	    String reson = (String) params.get("reson");
		if(StringUtils.isBlank(reson)||StringUtils.isBlank(usetype)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)){
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message","参数不全");
			return res;
		}
		Map<String,Object> pp = new HashMap<String,Object>();
		pp.put("orderno", orderno);
		if(PubOrdercancelEnum.CANCELREASON_PASSENGER_CHANGETRAVEL.msg.equals(reson)){
			pp.put("cancelreason", PubOrdercancelEnum.CANCELREASON_PASSENGER_CHANGETRAVEL.code);
		}else if(PubOrdercancelEnum.CANCELREASON_DRIVER_CANNOTCONTACT.msg.equals(reson)){
			pp.put("cancelreason", PubOrdercancelEnum.CANCELREASON_DRIVER_CANNOTCONTACT.code);
		}
		if(!"2".equals(usetype)){
			//机构订单
			orderdao.updateCancelReson4OrgNetCar(pp);
		}else{
			//个人订单
			if(isTaxiOrder(ordertype)){
				//出租车
				orderdao.updateCancelReson4OpTaxi(pp);
			}else{
				//网约车
				orderdao.updateCancelReson4OpNetCar(pp);
			}
		}
		return res;
	}

	public Map<String, Object> getUnpayOders(String usertoken, String companyid) {
		long start = System.currentTimeMillis();
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		Map<String,String> userinfo = Const.getUserInfo(usertoken);
		String account = userinfo.get("account");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account", account);
		params.put("orderstatus",OrderState.SERVICEDONE.state);
		params.put("paymentstatus",PayState.NOTPAY.state);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		res.put("order", null);
		try{
			// 机构用户机构订单时直接机构支付了，只有个人订单存在未支付
			// 个人用户
			// 获取未支付的网约车订单
			Map<String, Object> netcarorderinfo = orderdao.getUnpayOrders4OpNetCar(params);
			if (netcarorderinfo != null) {
				res.put("order", netcarorderinfo);
				return res;
			}
			// 获取未支付的出租车订单
			Map<String, Object> taxiorderinfo = orderdao.getUnpayOrders4OpTaxi(params);
			if(taxiorderinfo!=null){
				res.put("order", taxiorderinfo);
				return res;
			}
//			//机构取消未支付订单
//			Map<String,Object> nectcarorderinfo4orgcancel = orderdao.getUnpayOrder4OrgNetCar_cancel(params);
//			if(nectcarorderinfo4orgcancel!=null){
//				res.put("order", nectcarorderinfo4orgcancel);
//				return res;
//			}
			//个人网约车未支付
			Map<String,Object> nectcarorderinfo4opcancel = orderdao.getUnpayOrder4OpNetCar_cancel(params);
			if(nectcarorderinfo4opcancel!=null){
				res.put("order", nectcarorderinfo4opcancel);
				return res;
			}
			//出租车取消未支付
			Map<String,Object> taxiorderinfo4opcancel = orderdao.getUnpayOrder4OpTaxi_cancel(params);
			if(taxiorderinfo4opcancel!=null){
				res.put("order", taxiorderinfo4opcancel);
				return res;
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
			res.put("order", null);
			return res;
		}
		long end = System.currentTimeMillis();
		System.out.println("*******************************************************"+(end-start));
		return res;
	}

	public Map<String, Object> getPayAccounts(Map<String, Object> params) {
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
                Map<String,Object> validinfo = checkOrderValid(orderno, ordertype,usetype);
                if(validinfo!=null&&(int)validinfo.get("status")!=Retcode.OK.code){
                    return validinfo;
                }
                //订单信息
                Map<String,Object> orderinfo = (Map<String, Object>) validinfo.get("order");
	            List<Map<String, Object>> payinfo = new ArrayList<Map<String, Object>>();
	            List<Map<String,Object>> infos = null;
	            if(!"2".equals(usetype)){
	                //机构现在不用支付
	            	//机构用户
					String companyid = (String) orderinfo.get("companyid");
					LeasesCompany company = orgdao.getLeasesCompanyById(companyid);
					if(company!=null){
						infos = new ArrayList<Map<String,Object>>();
						if("1".equals(company.getWechatstatus())&&StringUtils.isNotBlank(company.getWechatappid())&&StringUtils.isNotBlank(company.getWechatmerchantno())&&StringUtils.isNotBlank(company.getWechatsecretkey())){
							//微信可用
							Map<String,Object> wxinfo = new HashMap<String,Object>();
	                        wxinfo.put("type","微信支付");
	                        wxinfo.put("id","2");
	                        infos.add(wxinfo);
						}
						if("1".equals(company.getAlipaystatus())&&StringUtils.isNotBlank(company.getAlipayappid())&&StringUtils.isNotBlank(company.getAlipaypublickey())&&StringUtils.isNotBlank(company.getAlipayprivatekey())){
							 //支付宝可用
							 Map<String,Object> alinfo = new HashMap<String,Object>();
	                         alinfo.put("type","支付宝支付");
	                         alinfo.put("id","1");
	                         infos.add(alinfo);
						}
					}
	            }else{
	                //个人用户
	                PeUser peuser = userdao.getUser4Op(account);
	                double balance = userdao.getUserBalance4Op(peuser.getId());
	                Map<String,Object> balanceinfo = new HashMap<String,Object>();
	                balanceinfo.put("type", "使用账户余额支付");
	                balanceinfo.put("id", "3");
	                balanceinfo.put("balanceamount", balance);
	                if(orderinfo!=null){
	                    double amount = 0;
	                    if(isTaxiOrder(ordertype)){
	                        //出租车
	                        amount = parseDouble(orderinfo.get("schedulefee"));
	                        String paymentmethod = (String) orderinfo.get("paymentmethod");
	                        if("0".equalsIgnoreCase(paymentmethod)){
	                            //线上支付
	                            amount += parseDouble(orderinfo.get("orderamount"));
	                        }
	                    }else{
	                        //网约车
	                        amount = parseDouble(orderinfo.get("orderamount"));
	                    }
	                    if(amount<=balance){
	                        balanceinfo.put("validmoney", true);
	                    }else{
	                        balanceinfo.put("validmoney", false);
	                    }
	                    payinfo.add(balanceinfo);
	                }
	                Map<String,Object> opinfo = orderdao.getOpInfo();
	                if(opinfo!=null){
	                	infos = new ArrayList<Map<String,Object>>();
	                    String wechatstatus = (String) opinfo.get("wechatstatus");
	                    if("1".equals(wechatstatus)
	                            &&StringUtils.isNotBlank((String)opinfo.get("wechatappid"))
	                            &&StringUtils.isNotBlank((String)opinfo.get("wechatmerchantno"))
	                            &&StringUtils.isNotBlank((String)opinfo.get("wechatsecretkey"))){
	                        //微信可用
	                    	Map<String,Object> wxinfo = new HashMap<String,Object>();
	                        wxinfo.put("type","微信支付");
	                        wxinfo.put("id","2");
	                        infos.add(wxinfo);
	                    }
	                    String alipaystatus = (String) opinfo.get("alipaystatus");
	                    if("1".equals(alipaystatus)
	                            &&StringUtils.isNotBlank((String)opinfo.get("alipayappid"))
	                            &&StringUtils.isNotBlank((String)opinfo.get("alipayprivatekey"))
	                            &&StringUtils.isNotBlank((String)opinfo.get("alipaypublickey"))){
	                        //支付宝可用
	                    	 Map<String,Object> alinfo = new HashMap<String,Object>();
	                         alinfo.put("type","支付宝支付");
	                         alinfo.put("id","1");
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
}

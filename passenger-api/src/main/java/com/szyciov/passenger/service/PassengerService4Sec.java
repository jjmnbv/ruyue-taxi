package com.szyciov.passenger.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.szyciov.driver.enums.AlarmProcessEnum;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.dto.pubAlarmprocess.SavePubAlarmprocessDto;
import com.szyciov.entity.CancelParty;
import com.szyciov.entity.OrderCost;
import com.szyciov.entity.OrderSource4WithdrawNO;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.UserType;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.PeUser;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.PhoneAuthenticationParam;
import com.szyciov.passenger.Const;
import com.szyciov.passenger.dao.DictionaryDao;
import com.szyciov.passenger.dao.OpDao;
import com.szyciov.passenger.dao.OrderDao;
import com.szyciov.passenger.dao.OrgDao;
import com.szyciov.passenger.dao.UserDao;
import com.szyciov.passenger.entity.AccountRules;
import com.szyciov.passenger.entity.AirportAddr;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.PassengerOrder;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.passenger.util.VelocityUtil;
import com.szyciov.util.BaiduUtil;
import com.szyciov.util.BankUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.RedisUtil;
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

@Service("PassengerService4Sec")
public class PassengerService4Sec {
	
	
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
	 * 个人用户登录前判断
	 * @param params
	 * @return
	 */
	public Map<String, Object> preLogin4Op(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String phone = (String) params.get("phone");
			if(StringUtils.isBlank(phone)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				//ios说这个必须给
				res.put("info","");
				return res;
			}
			//获取用户对象
			PeUser userinfo = userdao.getUser4Op(phone);
			if(userinfo==null){
				//校验实名制认证 
				try{
					PhoneAuthenticationParam pp = new PhoneAuthenticationParam();
					pp.setMobile(phone);
					JSONObject result = carserviceapi.dealRequestWithToken("/XunChengApi/phoneAuthentication", HttpMethod.POST, null, pp,JSONObject.class);
					if(result.getInt("status")==Retcode.OK.code){
						boolean isreal = result.getBoolean("data");
						if(!isreal){
							//未实名
							res.put("info","-1");
							return res;
						}
					}
				}catch(Exception e){
					logger.error("乘客端异常",e);
				}
				//用户未注册
				res.put("info","0");
			}else if(userinfo.getUserpassword()!=null&&!"".equalsIgnoreCase(userinfo.getUserpassword())){
				//设置了登录密码
				res.put("info","2");
			}else{
				//没有设置密码
				res.put("info","1");
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			//ios说这个必须给
			res.put("info","");
		}
		return res;
	}

	/**
	 * 个人用户获取加入toc的租赁公司列表
	 * @return
	 */
	public Map<String, Object> getCompanys4Op() {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		List<Map<String,Object>> companys = new ArrayList<Map<String,Object>>();
		res.put("companys",companys);
		try{
			Map<String,Object> all = new HashMap<String,Object>();
			all.put("id", "");
			all.put("name", "全部");
			companys.add(all);
			List<Map<String,Object>> tempcompanys = userdao.getTocCompanys4Op();
			if(tempcompanys!=null){
				companys.addAll(tempcompanys);
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 个人用户获取提供的服务
	 * @param params
	 * @return
	 */
	public Map<String, Object> getBusiness4Op(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String city = (String) params.get("city");
			if(StringUtils.isBlank(city)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				//ios说这个必须给
				res.put("business",new ArrayList<>());
				return res;
			}
			List<Map<String,Object>> business = opdao.getBusiness(params);
			if(business==null){
				business = new ArrayList<Map<String,Object>>();
			}
			res.put("business",business);
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("business",new ArrayList<>());
		}
		return res;
	}

	/**
	 * 个人用户获取附件的车辆信息
	 * @param params
	 * @return
	 */
	public Map<String, Object> getNearDrivers4Op(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		List<Map<String,Object>> drivers = new ArrayList<Map<String,Object>>();
		res.put("drivers",drivers);
		try{
			String city = (String) params.get("city");
			String companyid = (String) params.get("companyid"); 
			double lat = parseDouble(params.get("lat"));
			double lng = parseDouble(params.get("lng"));
			String orderstyle = (String) params.get("orderstyle");
			if(StringUtils.isBlank(city)||StringUtils.isBlank(orderstyle)){
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message", "参数不完整");
				return res;
			}
			String cityno = dicdao.getCityNo(city);
			int radius = 5000;
			double[] rangeinfo = BaiduUtil.getRange(lng, lat, radius);
			PubDriverInBoundParam pubparam = new PubDriverInBoundParam(rangeinfo);
			pubparam.setCity(cityno);
			pubparam.setCompanyid(companyid);
			pubparam.setSimple(true);
			pubparam.setOrguser(false);
			pubparam.setVehicletype(Integer.parseInt(orderstyle));
			JSONObject driverinfos = carserviceapi.dealRequestWithToken("/PubDriver/GetOpDriverInBound", HttpMethod.POST, null, pubparam,JSONObject.class);
			if(driverinfos!=null&&driverinfos.getInt("status")==0){
				int count = driverinfos.getInt("count");
				if(count>0){
					JSONArray driversj = driverinfos.getJSONArray("list");
					for(int i=0;i<driversj.size();i++){
						JSONObject drinfo = driversj.getJSONObject(i);
						Map<String,Object> d = new HashMap<String,Object>();
						d.put("lng", drinfo.get("lng"));
						d.put("lat", drinfo.get("lat"));
						d.put("gpsspeed", drinfo.get("gpsspeed"));
						d.put("gpsdirection", drinfo.get("gpsdirection"));
						drivers.add(d);
					}
				}
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 判断个人用户是否在某城市提供出租车服务
	 * @param params
	 * @return
	 */
	public Map<String, Object> cityHasService4OpTaxi(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		res.put("hasservice", false);
		try{
			//获取某个城市的计费规则
			String city = (String) params.get("city");
			if(StringUtils.isBlank(city)){
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message", "参数不完整");
				return res;
			}
			Map<String,Object> rule = opdao.getAccountRules4OpTaxi(params);
			if(rule!=null){
				//获取某个城市的派单规则
				Map<String,Object> sendrule = opdao.getSendRules4OpTaxi(params);
				if(sendrule!=null){
					res.put("hasservice", true);
				}
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
		}
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
	 * 个人用户获取上车
	 * @param params
	 * @return
	 */
	public Map<String, Object> getGetOnCitys4Op(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			//获取上车城市列表
			String type = (String) params.get("type");
			if(StringUtils.isBlank(type)){
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message", "参数不完整");
				return res;
			}
			List<Map<String,Object>> citysinfo = new ArrayList<Map<String,Object>>();
			if("0".equalsIgnoreCase(type)){
				//网约车
				List<Map<String,Object>> tempcitysinfo = opdao.getCity(params);
				List<String> sendrulecitys = opdao.getSendRuleCitys();
				getValiableCitys(citysinfo, tempcitysinfo, sendrulecitys);
			}else if("1".equalsIgnoreCase(type)){
				//出租车
				List<Map<String,Object>> tempcitysinfo = opdao.getGetOnCitys4Op(params);
				List<String> sendrulecitys = opdao.getSendRuleCitys4Taxi();
				getValiableCitys(citysinfo, tempcitysinfo, sendrulecitys);
			}
			List<Map<String,List<String>>> citys = new ArrayList<Map<String,List<String>>>();
			dillCityinfos(citys,citysinfo);
			res.put("citys", citys);
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("citys",new ArrayList<>());
		}
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
	 * 获取个人用户下车城市列表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getGetOffCitys4Op(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			//获取上车城市列表
			List<Map<String,Object>> citysinfo = dicdao.getAllCitys(params);
			List<Map<String,List<String>>> citys = new ArrayList<Map<String,List<String>>>();
			dillCityinfos(citys,citysinfo);
			res.put("citys", citys);
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("citys",new ArrayList<>());
		}
		return res;
	}
	
	/**
	 * 为车型logo加上绝对地址
	 * @param carmodels
	 */
	private void dillWithCarMoudelsLogo(List<Map<String,Object>> carmodels){
		if(carmodels==null){
			return ;
		}
		for(int i=0;i<carmodels.size();i++){
			Map<String,Object> ve = carmodels.get(i);
			String logo = (String) ve.get("logo");
			if(StringUtils.isNotBlank(logo)){
				ve.put("logo", SystemConfig.getSystemProperty("fileserver")+File.separator+logo);
			}
		}
	}

	/**
	 * 个人用户获取网约车车型
	 * @param params
	 * @return
	 */
	public Map<String, Object> getCarMoudels4OpNetCar(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String city = (String) params.get("city");
			String ordertype = (String) params.get("ordertype");
			if(StringUtils.isBlank(city)||StringUtils.isBlank(ordertype)){
				res.put("status", Retcode.EXCEPTION.code);
				res.put("message", "参数不完整");
				res.put("carmodels",new ArrayList<>());
				return res;
			}
			List<Map<String,Object>> carmodels = opdao.getCarMoudels4OpNetCar(params);
			if(carmodels==null){
				carmodels = new ArrayList<>();
			}else{
				dillWithCarMoudelsLogo(carmodels);
			}
			if(carmodels==null||carmodels.size()<=0){
				//没有服务
				res.put("message","当前城市没有用车服务");
			}
			res.put("carmodels",carmodels);
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("carmodels",new ArrayList<>());
		}
		return res;
	}

	/**
	 * 获取用车事由和用车备注
	 * @return
	 */
	public Map<String, Object> getUseResonAndRemark4Sec() {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		Map<String, List<String>> resoninfo = new HashMap<String, List<String>>();
		try{
			List<String> usereson = dicdao.getUseReson();
			if(usereson==null){
				usereson = new ArrayList<>();
			}
			resoninfo.put("usereson",usereson);
			List<String> remark = dicdao.getRemark();
			if(remark==null){
				remark = new ArrayList<>();
			}
			resoninfo.put("remark",remark);
			res.put("resoninfo",resoninfo);
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			resoninfo.put("usereson",new ArrayList<>());
			resoninfo.put("remark",new ArrayList<>());
			res.put("resoninfo",resoninfo);
		}
		return res;
	}

	/**
	 * 获取运管端网约车的计费规则
	 * @param param
	 * @param res
	 */
	public void getAccountRules4OpNetCar(Map<String, Object> param, HttpServletResponse res) {
		res.setContentType("text/html; charset=utf-8");
		List<AccountRules> rules = null;
		try{
			rules = opdao.getAccountRules(param);
			if(rules!=null){
				for(int i=0;i<rules.size();i++){
					AccountRules rule = rules.get(i);
					String logo = rule.getLogo();
					if(StringUtils.isNotBlank(logo)){
						rule.setLogo(SystemConfig.getSystemProperty("fileserver")+File.separator+logo);
					}
				}
			}else{
				rules = new ArrayList<AccountRules>();
			}
		}catch(Exception e){
			logger.error("乘客端异常",e);
			rules = new ArrayList<AccountRules>();
		}
		try {
			VelocityContext context = new VelocityContext();
			context.put("rules", rules);
			context.put("company", null);
			String vmpath = PassengerService.class.getClassLoader().getResource("accountrules.vm").getPath();
			VelocityUtil.createTemplate(vmpath, res.getWriter(), context);
		} catch (Exception e) {
			logger.error("乘客端异常",e);
		}
	}

	/**
	 * 是否含有登录密码
	 * @return
	 */
	public Map<String, Object> hasPassword(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		res.put("haspassword",false);
		try{
			String usertoken = (String) param.get("usertoken");
			if(StringUtils.isBlank(usertoken)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			if(isOrgUser(usertoken)){
				//机构用户
				OrgUser orguser = userdao.getUser4Org(account);
				if(StringUtils.isNotBlank(orguser.getUserPassword())){
					res.put("haspassword",true);
				}
			}else{
				//个人用户
				PeUser peuser = userdao.getUser4Op(account);
				if(StringUtils.isNotBlank(peuser.getUserpassword())){
					res.put("haspassword",true);
				}
			}
			
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 是否修改过提现密码
	 * @param param
	 * @return
	 */
	public Map<String, Object> everChangeCashPwd(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		res.put("everchange",false);
		res.put("isorguser",false);
		res.put("isleave",false);
		try{
			String usertoken = (String) param.get("usertoken");
			if(StringUtils.isBlank(usertoken)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("servicephone","");
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			if(isOrgUser(usertoken)){
				//机构用户
				res.put("isorguser",true);
				OrgUser orguser = userdao.getUser4Org(account);
				if(orguser.getStatus()!=1){
					//离职
					res.put("isleave",true);
					res.put("servicephone","");
				}else{
					//未离职
					Map<String,Object> supertelphone = orgdao.getSuperTelPhone(orguser.getOrganId());
					res.put("servicephone", supertelphone.get("phone")==null?"":supertelphone.get("phone"));
				}
				if(orguser.getWdpwdchangestate()==1){
					res.put("everchange",true);
				}
			}else{
				//个人用户
				PeUser peuser = userdao.getUser4Op(account);
				if(peuser.getWdpwdchangestate()==1){
					res.put("everchange",true);
				}
				Map<String,Object> opinfo = orderdao.getOpInfo();
				if(opinfo!=null){
					res.put("servicephone", opinfo.get("servcietel")==null?"":opinfo.get("servcietel"));
				}else{
					res.put("servicephone","");
				}
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("servicephone","");
		}
		return res;
	}

	/**
	 * 验证提现密码
	 * @param param
	 * @return
	 */
	public Map<String, Object> validateCashPwd(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			String oldpwd = (String) param.get("oldpwd");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(oldpwd)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			if(isOrgUser(usertoken)){
				//机构用户
				OrgUser user = userdao.getUser4Org(account);
				int wdstate = user.getWdpwdchangestate();
				String dbpwd = user.getWithdrawpwd();
				if(wdstate==0){
					dbpwd = user.getUserPassword();
				}
				if(!PasswordEncoder.matches(oldpwd,dbpwd)){
					res.put("status", Retcode.PASSWORDWRONG.code);
					res.put("message", Retcode.PASSWORDWRONG.msg);
				}
			}else{
				//个人用户
				PeUser user = userdao.getUser4Op(account);
				int wdstate = user.getWdpwdchangestate();
				String dbpwd = user.getWithdrawpwd();
				if(wdstate==0){
					dbpwd = user.getUserpassword();
				}
				if(!PasswordEncoder.matches(oldpwd,dbpwd)){
					res.put("status", Retcode.PASSWORDWRONG.code);
					res.put("message", Retcode.PASSWORDWRONG.msg);
				}
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 更改提现密码
	 * @param param
	 * @return
	 */
	public Map<String, Object> updateCashPwd(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			String pwd = (String) param.get("pwd");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(pwd)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("pwd", PasswordEncoder.encode((String)param.get("pwd")));
			if(isOrgUser(usertoken)){
				//机构用户
				param.put("userid", userdao.getUser4Org(account).getId());
				//机构用户
				userdao.updateCashPwd4Org(param);
			}else{
				//个人用户
				param.put("userid", userdao.getUser4Op(account).getId());
				//机构用户
				userdao.updateCashPwd4Op(param);
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 重置提现密码
	 * @param param
	 * @return
	 */
	public Map<String, Object> resetCashPwd(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			String pwd = (String) param.get("pwd");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(pwd)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			String loginpwd = (String)param.get("pwd");
			String cashpwd = UNID.getUNID(null,6);
			String content = null;
			if(isOrgUser(usertoken)){
				//机构用户
				OrgUser user = userdao.getUser4Org(account);
				if(!PasswordEncoder.matches(loginpwd,user.getUserPassword())){
					res.put("status", Retcode.PASSWORDWRONG.code);
					res.put("message", Retcode.PASSWORDWRONG.msg);
					return res;
				}
				if(user.getStatus()!=1){
					content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.passenger.service.send.newcashpwd.org4leave",cashpwd);
				}else{
					OrgOrgan organ = orgdao.getOrgInfo(account);
					//content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.passenger.service.send.newcashpwd.org",cashpwd,organ.getShortName());
					content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.passenger.service.send.newcashpwd.org",cashpwd);
				}
				param.put("pwd", PasswordEncoder.encode(cashpwd));
				param.put("userid", user.getId());
				userdao.updateCashPwd4Org(param);
			}else{
				//个人用户
				PeUser user = userdao.getUser4Op(account);
				if(!PasswordEncoder.matches(loginpwd,user.getUserpassword())){
					res.put("status", Retcode.PASSWORDWRONG.code);
					res.put("message", Retcode.PASSWORDWRONG.msg);
					return res;
				}
				Map<String,Object> opinfo = orderdao.getOpInfo();
				String servcietel = (String) opinfo.get("servcietel");
				String companyshortname = (String) opinfo.get("companyshortname");
				//content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.passenger.service.send.newcashpwd.op",cashpwd,servcietel,companyshortname);
				content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.passenger.service.send.newcashpwd.op",cashpwd,servcietel);
				param.put("pwd", PasswordEncoder.encode(cashpwd));
				param.put("userid", user.getId());
				userdao.updateCashPwd4Op(param);
			}
			List<String> userids = new ArrayList<String>();
			userids.add(account);
			UserMessage message = new UserMessage(userids,content,UserMessage.RESETCASHPWD);
			MessageUtil.sendMessage(message);
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 获取钱包余额
	 * @param param
	 * @return
	 */
	public Map<String, Object> getBalance4Sec(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			if(StringUtils.isBlank(usertoken)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("totalmoney",0);
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			if(isOrgUser(usertoken)){
				//机构用户
				param.put("account", account);
				double totalmoney = userdao.getBalanceMoney4Org(param);
				res.put("totalmoney",totalmoney);
			}else{
				//个人用户
				param.put("account", account);
				double totalmoney = userdao.getBalanceMoney4Op(param);
				res.put("totalmoney",totalmoney);
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("totalmoney",0);
		}
		return res;
	}

	/**
	 * 获取银行卡信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> getBankInfos(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		List<Map<String,Object>> banks = new ArrayList<Map<String,Object>>();
		res.put("banks",banks);
		try{
			String usertoken = (String) param.get("usertoken");
			if(StringUtils.isBlank(usertoken)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("account", account);
			if(isOrgUser(usertoken)){
				//机构用户
				OrgUser user = userdao.getUser4Org(account);
				param.put("userid", user.getId());
				Map<String,Object> bankinfo = userdao.getLastBankInfo4Org(param);
				if(bankinfo!=null){
					banks.add(bankinfo);
				}
			}else{
				//个人用户
				PeUser user = userdao.getUser4Op(account);
				param.put("userid", user.getId());
				Map<String,Object> bankinfo = userdao.getLastBankInfo4Op(param);
				if(bankinfo!=null){
					banks.add(bankinfo);
				}
			}
			Map<String,Object> addbtn = new HashMap<String,Object>();
			addbtn.put("type", "1");
			addbtn.put("caption", "其他银行卡");
			addbtn.put("cardno", "");
			addbtn.put("name", "");
			banks.add(addbtn);
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 获取交易明细
	 * @param param
	 * @return
	 */
	public Map<String, Object> getDealDetail(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			String iDisplayStart = (String) param.get("iDisplayStart");
			String iDisplayLength = (String) param.get("iDisplayLength");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(iDisplayStart)||StringUtils.isBlank(iDisplayLength)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("details",new ArrayList<>());
				res.put("currentcount",0);
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("account", account);
			if(isOrgUser(usertoken)){
				//机构用户
				List<Map<String,Object>> details = userdao.getDealDetail4Org(param);
				if(details==null){
					details = new ArrayList<>();
				}
				res.put("details",details);
				res.put("currentcount",details.size());
			}else{
				//个人用户
				List<Map<String,Object>> details = userdao.getDealDetail4Op(param);
				if(details==null){
					details = new ArrayList<>();
				}
				res.put("details",details);
				res.put("currentcount",details.size());
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("details",new ArrayList<>());
			res.put("currentcount",0);
		}
		return res;
	}

	/**
	 * 获取钱包的余额明细
	 * @param param
	 * @return
	 */
	public Map<String, Object> getBalanceDetail(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			String iDisplayStart = (String) param.get("iDisplayStart");
			String iDisplayLength = (String) param.get("iDisplayLength");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(iDisplayStart)||StringUtils.isBlank(iDisplayLength)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("details",new ArrayList<>());
				res.put("currentcount",0);
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("account", account);
			if(isOrgUser(usertoken)){
				//机构用户
				List<Map<String,Object>> details = userdao.getBalanceDetail4OrgSec(param);
				if(details==null){
					details = new ArrayList<>();
				}
				res.put("details",details);
				res.put("currentcount",details.size());
			}else{
				//个人用户
				List<Map<String,Object>> details = userdao.getBalanceDetail4OpSec(param);
				if(details==null){
					details = new ArrayList<>();
				}
				res.put("details",details);
				res.put("currentcount",details.size());
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("details",new ArrayList<>());
			res.put("currentcount",0);
		}
		return res;
	}

	/**
	 * 添加银行卡信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> addBankCard(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			String name = (String) param.get("name");
			String cardno = (String) param.get("cardno");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(name)||StringUtils.isBlank(cardno)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			if(isOrgUser(usertoken)){
				//机构用户
				
			}else{
				//个人用户
				
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 验证银行卡信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> validateBankInfo(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			String name = (String) param.get("name");
			String cardno = (String) param.get("cardno");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(cardno)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("bankname","");
				return res;
			}
			String bankname = BankUtil.getBankName(cardno);
			if(StringUtils.isBlank(bankname)){
				res.put("status",Retcode.FAILED.code);
				res.put("message",Retcode.FAILED.msg);
				res.put("bankname","");
				return res;
			}
			res.put("bankname",bankname);
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("bankname","");
		}
		return res;
	}

	/**
	 * 提现金额
	 * @param param
	 * @return
	 */
	public Map<String, Object> applyCash(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		param.put("isout", 1);
		try{
			String usertoken = (String) param.get("usertoken");
			String companyid = (String) param.get("companyid");
			String pwd = (String) param.get("pwd");
			double money = Double.valueOf(String.valueOf(param.get("money")));
			String bankno = (String) param.get("bankno");
			String bankuser = (String) param.get("bankuser");
			String bankname = (String) param.get("bankname");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(pwd)||
					money<=0||StringUtils.isBlank(bankno)||StringUtils.isBlank(bankuser)||
					StringUtils.isBlank(bankname)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("account", account);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if(isOrgUser(usertoken)){
				//机构用户
				OrgUser user = userdao.getUser4Org(account);
				int wdstate = user.getWdpwdchangestate();
				String dbpwd = user.getWithdrawpwd();
				if(wdstate==0){
					dbpwd = user.getUserPassword();
				}
				if(!PasswordEncoder.matches(pwd,dbpwd)){
					res.put("status", Retcode.PASSWORDWRONG.code);
					res.put("message", Retcode.PASSWORDWRONG.msg);
					return res;
				}
				param.put("userid", user.getId());
				double todaymoney = userdao.getApplyCash4OrgToday(param);
				double lefttodaymoney = 500-todaymoney;
				if(lefttodaymoney<money){
					res.put("status", Retcode.FAILED.code);
					res.put("message","今日提现超限！");
					return res;
				}
				double totalmoney = userdao.getBalanceMoney4Org(param);
				if(totalmoney<money){
					res.put("status", Retcode.NOTENOUGHBALANCE.code);
					res.put("message", Retcode.NOTENOUGHBALANCE.msg);
					return res;
				}
				param.put("uid", GUIDGenerator.newGUID());
				userdao.updateUserBalance4OrgSec(param);
				param.put("id", GUIDGenerator.newGUID());
				//提现编号
				param.put("uuid", getPubWithDrawNo());
				userdao.applyCash4Org(param);
				param.put("logid", GUIDGenerator.newGUID());
				param.put("expensetype", null);
				param.put("remark", "提现申请");
				param.put("tradetype", "3");
				param.put("detailtype", "0");
				param.put("operateresult", "0");
				userdao.addExpenses4OrgSec(param);
				//租赁财务人员消息
				JSONObject json = new JSONObject();
				json.put("type", "52");
				json.put("title", "乘客提现申请");
				String content = "乘客："+account+"，申请提现："+money+"元，申请时间："+format.format(new Date())+"，请尽快处理。";
				json.put("content", content);
				addNews4LeaseUsers(companyid,json);
			}else{
				//个人用户
				PeUser user = userdao.getUser4Op(account);
				int wdstate = user.getWdpwdchangestate();
				String dbpwd = user.getWithdrawpwd();
				if(wdstate==0){
					dbpwd = user.getUserpassword();
				}
				if(!PasswordEncoder.matches(pwd,dbpwd)){
					res.put("status", Retcode.PASSWORDWRONG.code);
					res.put("message", Retcode.PASSWORDWRONG.msg);
					return res;
				}
				param.put("userid", user.getId());
				double todaymoney = userdao.getApplyCash4OpToday(param);
				double lefttodaymoney = 500-todaymoney;
				if(lefttodaymoney<money){
					res.put("status", Retcode.FAILED.code);
					res.put("message","今日提现超限！");
					return res;
				}
				double totalmoney = userdao.getBalanceMoney4Op(param);
				if(totalmoney<money){
					res.put("status", Retcode.NOTENOUGHBALANCE.code);
					res.put("message", Retcode.NOTENOUGHBALANCE.msg);
					return res;
				}
				param.put("uid", GUIDGenerator.newGUID());
				userdao.updateUserBalance4OpSec(param);
				param.put("id", GUIDGenerator.newGUID());
				//提现编号
				param.put("uuid", getPubWithDrawNo());
				userdao.applyCash4Op(param);
				param.put("logid", GUIDGenerator.newGUID());
				param.put("expensetype", null);
				param.put("remark", "提现申请");
				param.put("tradetype", "3");
				param.put("detailtype", "0");
				param.put("operateresult", "0");
				userdao.addExpenses4OpSec(param);
				//运管财务人员消息
				try{
					JSONObject json = new JSONObject();
					json.put("type", "53");
					json.put("title", "乘客提现申请");
					String content = "乘客："+account+"，申请提现："+money+"元，申请时间："+format.format(new Date())+"，请尽快处理。";
					json.put("content", content);
					addNews4OpUsers(json);
				}catch(Exception e){
					logger.error("乘客端异常",e);
				}
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public String getPubWithDrawNo() {
		String uuid = null;
		try{
			Map<String, Object> resultMap = carserviceapi.dealRequestWithToken("/PubWithDraw/GetPubWithDrawNo",HttpMethod.POST, null, OrderSource4WithdrawNO.PASSENGER, Map.class);
			uuid = (String) resultMap.get("pubWithDrawNo");
		}catch(Exception e){
			logger.error("乘客端异常",e);
			uuid = GUIDGenerator.newGUID();
		}
		return uuid;
	}

	/**
	 * 给运管端超管和财务人员发送消息（存库）
	 * @param json
	 */
	private void addNews4OpUsers(JSONObject json) {
		List<Map<String,Object>> opsacs = opdao.getSuperAndCaiWu();
		if(opsacs==null||opsacs.size()<=0){
			return ;
		}
		for(int i=0;i<opsacs.size();i++){
			Map<String,Object> userinfo = opsacs.get(i);
			Map<String,Object> news = new HashMap<String,Object>();
			news.put("id", GUIDGenerator.newGUID());
			news.put("type", "1");
			news.put("userid", userinfo.get("id"));
			news.put("content", json.toString());
			news.put("newsstate", "0");
			opdao.addNews4Op(news);
		}
	}

	/**
	 * 给租赁端超管和财务人员发送消息（存库）
	 * @param companyid
	 * @param json
	 */
	private void addNews4LeaseUsers(String companyid, JSONObject json) {
		List<Map<String,Object>> leasacs = orgdao.getSuperAndCaiWu(companyid);
		if(leasacs==null||leasacs.size()<=0){
			return ;
		}
		for(int i=0;i<leasacs.size();i++){
			Map<String,Object> userinfo = leasacs.get(i);
			Map<String,Object> news = new HashMap<String,Object>();
			news.put("id", GUIDGenerator.newGUID());
			news.put("type", "1");
			news.put("userid", userinfo.get("id"));
			news.put("content", json.toString());
			news.put("newsstate", "0");
			orgdao.addNews4LeaseUser(news);
		}
	}

	/**
	 * 获取充值的方式有哪些
	 * @param param
	 * @return
	 */
	public Map<String, Object> getRechargeTypes(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		List<Map<String,Object>> rechargeinfo = new ArrayList<Map<String,Object>>();
		res.put("rechargeinfo",rechargeinfo);
		try{
			String usertoken = (String) param.get("usertoken");
			if(StringUtils.isBlank(usertoken)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				return res;
			}
			if(isOrgUser(usertoken)){
				//机构用户
				String companyid = (String) param.get("companyid");
				LeasesCompany company = orgdao.getLeasesCompanyById(companyid);
				if(company!=null){
					if("1".equals(company.getWechatstatus())&&StringUtils.isNotBlank(company.getWechatappid())&&StringUtils.isNotBlank(company.getWechatmerchantno())&&StringUtils.isNotBlank(company.getWechatsecretkey())){
						//微信可用
						Map<String,Object> info = new HashMap<String,Object>();
						info.put("type", "2");
						info.put("account", StringUtils.isNotBlank(company.getWechataccount())?company.getWechataccount():"");
						rechargeinfo.add(info);
					}
					if("1".equals(company.getAlipaystatus())&&StringUtils.isNotBlank(company.getAlipayappid())&&StringUtils.isNotBlank(company.getAlipaypublickey())&&StringUtils.isNotBlank(company.getAlipayprivatekey())){
						//支付宝可用
						Map<String,Object> info = new HashMap<String,Object>();
						info.put("type", "1");
						info.put("account", StringUtils.isNotBlank(company.getAlipayaccount())?company.getAlipayaccount():"");
						rechargeinfo.add(info);
					}
				}
			}else{
				//个人用户
				Map<String,Object> opinfo = orderdao.getOpInfo();
				if(opinfo!=null){
					String wechatstatus = (String) opinfo.get("wechatstatus");
					if("1".equals(wechatstatus)
					   &&StringUtils.isNotBlank((String)opinfo.get("wechatappid"))
					   &&StringUtils.isNotBlank((String)opinfo.get("wechatmerchantno"))
					   &&StringUtils.isNotBlank((String)opinfo.get("wechatsecretkey"))){
						//微信可用
						Map<String,Object> info = new HashMap<String,Object>();
						info.put("type", "2");
						String wechataccount = (String)opinfo.get("wechataccount");
						info.put("account", StringUtils.isNotBlank(wechataccount)?wechataccount:"");
						rechargeinfo.add(info);
					}
					String alipaystatus = (String) opinfo.get("alipaystatus");
					if("1".equals(alipaystatus)
					   &&StringUtils.isNotBlank((String)opinfo.get("alipayappid"))
					   &&StringUtils.isNotBlank((String)opinfo.get("alipayprivatekey"))
					   &&StringUtils.isNotBlank((String)opinfo.get("alipaypublickey"))){
						//支付宝可用
						Map<String,Object> info = new HashMap<String,Object>();
						info.put("type", "1");
						String alipayaccount = (String)opinfo.get("alipayaccount");
						info.put("account", StringUtils.isNotBlank(alipayaccount)?alipayaccount:"");
						rechargeinfo.add(info);
					}
				}
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
		}
		return res;
	}

	/**
	 * 充值
	 * @param param
	 * @return
	 */
	public Map<String, Object> recharge(Map<String, Object> param,HttpServletRequest request) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			double amount =  parseDouble(param.get("amount"));
			String paytype = (String) param.get("paytype");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(paytype)||amount<=0){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("payorderinfo","");
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("account", account);
			if(isOrgUser(usertoken)){
				//机构用户
				String companyid = (String) param.get("companyid");
				if(StringUtils.isBlank(companyid)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","参数不全");
					res.put("payorderinfo","");
					return res;
				}
				LeasesCompany company = orgdao.getLeasesCompanyById(companyid);
				if(company==null){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","充值信息不全");
					res.put("payorderinfo","");
					return res;
				}
				if("1".equalsIgnoreCase(paytype)){
					//支付宝充值
					if(!"1".equals(company.getAlipaystatus())){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","暂不支持支付宝充值");
						res.put("payorderinfo","");
						return res;
					}
					String appid = company.getAlipayappid();
					String alipubkey = company.getAlipaypublickey();
					String privatekey = company.getAlipayprivatekey();
					if(StringUtils.isBlank(appid)||StringUtils.isBlank(alipubkey)||StringUtils.isBlank(privatekey)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","充值信息不全");
						res.put("payorderinfo","");
						return res;
					}
					OrgUser user = userdao.getUser4Org(account);
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					//获取订单的交易号 (时间加上5位随机码)
					String out_trade_no = format.format(date)+UNID.getUNID();
					Map<String,Object> tradeparam = new HashMap<String,Object>();
					tradeparam.put("out_trade_no", out_trade_no);
					tradeparam.put("companyid", companyid);
					tradeparam.put("organid", user.getOrganId());
					tradeparam.put("userid", user.getId());
					tradeparam.put("type","0");
					tradeparam.put("paymenttype","0");
					tradeparam.put("validatekey", alipubkey);
					tradeparam.put("tradingstatus","0");
					tradeparam.put("amount",amount);
					//添加交易号记录流水
					userdao.addTradingrecord4Org(tradeparam);
					String zfbchargesubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbchargesubject"))?"账户充值":SystemConfig.getSystemProperty("zfbchargesubject");
					String zfbchargebody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbchargebody"))?"充值交易":SystemConfig.getSystemProperty("zfbchargebody");
					amount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:amount;
					Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", amount, zfbchargesubject, zfbchargebody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillZFBCharge4Org");
					String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
					String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
					String payorderinfo = payorderstr + "&" + sign;
					res.put("payorderinfo", payorderinfo);
				}else if("2".equalsIgnoreCase(paytype)){
					//微信充值
					if(!"1".equals(company.getWechatstatus())){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","暂不支持微信充值");
						res.put("payorderinfo","");
						return res;
					}
					String appid = company.getWechatappid();
					String chantno = company.getWechatmerchantno();
					String secretkey = company.getWechatsecretkey();
					if(StringUtils.isBlank(appid)||StringUtils.isBlank(chantno)||StringUtils.isBlank(secretkey)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","充值信息不全");
						res.put("payorderinfo","");
						return res;
					}
					OrgUser user = userdao.getUser4Org(account);
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					//获取订单的交易号 (时间加上5位随机码)
					String out_trade_no = format.format(date)+UNID.getUNID();
					Map<String,Object> tradeparam = new HashMap<String,Object>();
					tradeparam.put("out_trade_no", out_trade_no);
					tradeparam.put("companyid", companyid);
					tradeparam.put("organid", user.getOrganId());
					tradeparam.put("userid", user.getId());
					tradeparam.put("type","0");
					tradeparam.put("paymenttype","1");
					tradeparam.put("validatekey", secretkey);
					tradeparam.put("tradingstatus","0");
					tradeparam.put("amount",amount);
					//添加交易号记录流水
					userdao.addTradingrecord4Org(tradeparam);
					String tempipadd = request.getRemoteAddr();
					String ipadd = tempipadd.indexOf(":")>=0?"0.0.0.0":tempipadd;
					amount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:amount;
					String chargemoney = new java.text.DecimalFormat("#").format(amount*100);
					String wxchargebody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxchargebody"))?"账户充值":SystemConfig.getSystemProperty("wxchargebody");
					Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, chantno, wxchargebody, out_trade_no, chargemoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillWXCharge4Org", "APP",ipadd);
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
						res.put("message", "充值异常");
						res.put("payorderinfo","");
					}
				}
			}else{
				//个人用户
				Map<String,Object> payinfo = dicdao.getPayInfo4Op();
				if(payinfo==null){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message","充值信息不全");
					res.put("payorderinfo","");
					return res;
				}
				if("1".equalsIgnoreCase(paytype)){
					//支付宝充值
					String alipaystatus = (String) payinfo.get("alipaystatus");
					if(!"1".equals(alipaystatus)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","暂不支持支付宝充值");
						res.put("payorderinfo","");
						return res;
					}
					String appid = (String) payinfo.get("alipayappid");
					String alipubkey = (String) payinfo.get("alipaypublickey");
					String privatekey = (String) payinfo.get("alipayprivatekey");
					if(StringUtils.isBlank(appid)||StringUtils.isBlank(alipubkey)||StringUtils.isBlank(privatekey)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","充值信息不全");
						res.put("payorderinfo","");
						return res;
					}
					PeUser user = userdao.getUser4Op(account);
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					//获取订单的交易号 (时间加上5位随机码)
					String out_trade_no = format.format(date)+UNID.getUNID();
					Map<String,Object> tradeparam = new HashMap<String,Object>();
					tradeparam.put("out_trade_no", out_trade_no);
					tradeparam.put("userid", user.getId());
					tradeparam.put("type","0");
					tradeparam.put("paymenttype","0");
					tradeparam.put("validatekey", alipubkey);
					tradeparam.put("tradingstatus","0");
					tradeparam.put("amount",amount);
					//添加交易号记录流水
					userdao.addTradingrecord4Op(tradeparam);
					String zfbchargesubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbchargesubject"))?"账户充值":SystemConfig.getSystemProperty("zfbchargesubject");
					String zfbchargebody = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbchargebody"))?"充值交易":SystemConfig.getSystemProperty("zfbchargebody");
					amount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:amount;
					Map<String,String> payorderparam = OrderInfoUtil.buildOrderParamMap(appid, "30m", amount, zfbchargesubject, zfbchargebody, out_trade_no,SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillZFBCharge4Op");
					String payorderstr = OrderInfoUtil.buildOrderParam(payorderparam);
					String sign = OrderInfoUtil.getSign(payorderparam, privatekey);
					String payorderinfo = payorderstr + "&" + sign;
					res.put("payorderinfo", payorderinfo);
				}else{
					//微信充值
					String wechatstatus = (String) payinfo.get("wechatstatus");
					if(!"1".equals(wechatstatus)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","暂不支持微信充值");
						res.put("payorderinfo","");
						return res;
					}
					String appid = (String) payinfo.get("wechatappid");
					String chantno = (String) payinfo.get("wechatmerchantno");
					String secretkey = (String) payinfo.get("wechatsecretkey");
					if(StringUtils.isBlank(appid)||StringUtils.isBlank(chantno)||StringUtils.isBlank(secretkey)){
						res.put("status",Retcode.EXCEPTION.code);
						res.put("message","充值信息不全");
						res.put("payorderinfo","");
						return res;
					}
					PeUser user = userdao.getUser4Op(account);
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					//获取订单的交易号 (时间加上5位随机码)
					String out_trade_no = format.format(date)+UNID.getUNID();
					Map<String,Object> tradeparam = new HashMap<String,Object>();
					tradeparam.put("out_trade_no", out_trade_no);
					tradeparam.put("userid", user.getId());
					tradeparam.put("type","0");
					tradeparam.put("paymenttype","1");
					tradeparam.put("validatekey", secretkey);
					tradeparam.put("tradingstatus","0");
					tradeparam.put("amount",amount);
					//添加交易号记录流水
					userdao.addTradingrecord4Op(tradeparam);
					String tempipadd = request.getRemoteAddr();
					String ipadd = tempipadd.indexOf(":")>=0?"0.0.0.0":tempipadd;
					amount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:amount;
					String chargemoney = new java.text.DecimalFormat("#").format(amount*100);
					String wxchargebody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxchargebody"))?"账户充值":SystemConfig.getSystemProperty("wxchargebody");
					Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, chantno, wxchargebody, out_trade_no, chargemoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/Passenger/DillWXCharge4Op", "APP",ipadd);
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
						res.put("message", "充值异常");
						res.put("payorderinfo","");
					}
				}
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("payorderinfo","");
		}
		return res;
	}

	/**
	 * 获取订单列表
	 * @param param
	 * @return
	 */
	public Map<String, Object> getOders4Op(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			String iDisplayStart = (String) param.get("iDisplayStart");
			String iDisplayLength = (String) param.get("iDisplayLength");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(iDisplayStart)||StringUtils.isBlank(iDisplayLength)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("currentcount",0);
				res.put("orders",new ArrayList<>());
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("account", account);
			if(isOrgUser(usertoken)){
				//机构用户
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","这个接口是给个人端用的");
				res.put("currentcount",0);
				res.put("orders",new ArrayList<>());
			}else{
				//个人用户
				List<Map<String,Object>> orders = orderdao.getOrders4OpSec(param);
				dillOrders(orders,usertoken);
				if(orders==null){
					orders = new ArrayList<>();
				}
				res.put("currentcount",orders.size());
				res.put("orders",orders);
			}
		}catch(Exception e){
			logger.error("乘客端异常",e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("currentcount",0);
			res.put("orders",new ArrayList<>());
		}
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
	
	/**
	 * 处理订单信息
	 * @param order
	 */
	private void dillOrder(Map<String, Object> orderinfo,String usertoken,boolean isdetail){
		//数字显示一位小数
//		dillNumShow(orderinfo);
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
	 * 数字显示一位小数
	 * @param order
	 */
	private void dillNumShow(Map<String, Object> orderinfo) {
		if(orderinfo==null){
			return ;
		}
		try{
			double mileage = (double) orderinfo.get("mileage");
			orderinfo.put("mileage", StringUtil.formatNum(mileage,1));
			orderinfo.put("mileagestr", StringUtil.formatNum(mileage/1000,1)+"公里");
			double estimatedcost = parseDouble(orderinfo.get("estimatedcost"));
			orderinfo.put("estimatedcost",StringUtil.formatNum(estimatedcost,1));
			double estimatedtime = parseDouble(orderinfo.get("estimatedtime"));
			orderinfo.put("estimatedtime",StringUtil.formatNum(estimatedtime,1));
			double estimatedmileage = parseDouble(orderinfo.get("estimatedmileage"));
			orderinfo.put("estimatedmileage",StringUtil.formatNum(estimatedmileage,1));
			double orderamount = parseDouble(orderinfo.get("orderamount"));
			orderinfo.put("orderamount",StringUtil.formatNum(orderamount,1));
		}catch(Exception e){
			logger.error("乘客端异常",e);
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
				remain = remain<0?0:remain;
				orderinfo.put("remaintime", StringUtil.formatCostTime(remain));
			}
		}
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
			orderinfo.put("startprice", 0);
			orderinfo.put("rangeprice", 0);
			orderinfo.put("timeprice", 0);
			orderinfo.put("rangecost", 0);
			orderinfo.put("timecost", 0);
			orderinfo.put("times", 0);
			orderinfo.put("mileage",0);
			return ;
		}
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
			if(StringUtils.isNotBlank(amount)){
				orderinfo.put("orderamount", Double.parseDouble(amount.replaceAll("元", "")));
			}
			String mileagestr = costinfo.getString("mileage");
			orderinfo.put("startprice", costinfo.getString("startprice"));
			orderinfo.put("rangeprice", costinfo.getString("rangeprice"));
			orderinfo.put("timeprice", costinfo.getString("timeprice"));
			orderinfo.put("rangecost", costinfo.getString("rangecost"));
			orderinfo.put("timecost", costinfo.getString("timecost"));
			orderinfo.put("times", costinfo.getString("times"));
			orderinfo.put("mileagestr", StringUtils.isBlank(mileagestr)?"":mileagestr);
			orderinfo.put("mileage", parseDouble(StringUtils.isBlank(mileagestr)?"":mileagestr.replaceAll("公里", "")));
			String copyprice = (String) orderinfo.get("pricecopy");
			if(StringUtils.isNotBlank(copyprice)){
				OrderCost cost = StringUtil.parseJSONToBean(copyprice, OrderCost.class);
				String timetype = StringUtil.formatTimeType(cost);
				orderinfo.put("timetype", timetype);
				if(cost.getTimetype()!=0){
					//低速用时
					orderinfo.put("times", StringUtil.formatCostTime(cost.getSlowtimes()));
				}
			}
		}
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
			if(isOrgUser(usertoken)){
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
				String dorderinfo = RedisUtil.getString(RedisKeyEnum.MESSAGE_ORDER_TRAVEL_INFO.code+orderid);
				if(StringUtils.isNotBlank(dorderinfo)){
					JSONObject json = JSONObject.fromObject(dorderinfo);
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
						Object duration = hintinfo.get("duration");
						String durstr = "0";
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
					String lefttimestr = StringUtil.formatCostTime(lefttime>0?lefttime:0);
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
	 * 获取订单详细信息
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
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("order",new HashMap<>());
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("account", account);
			if(isOrgUser(usertoken)){
				//机构用户
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","这个接口是给个人端用的");
				res.put("order",new HashMap<>());
			}else{
				//个人用户
				Map<String,Object> orderinfo = null;
				if(StringUtils.isBlank(ordertype)){
					//ordertype没有传递，兼容之前的
					orderinfo = orderdao.getOrder4OpTaxi(param);
					if(orderinfo==null){
						orderinfo = orderdao.getOrder4OpNetCar(param);
					}
				}else{
					//有ordertype,根据ordertype区分订单类型
					if(isTaxiOrder(ordertype)){
						orderinfo = orderdao.getOrder4OpTaxi(param);
					}else{
						orderinfo = orderdao.getOrder4OpNetCar(param);
					}
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
	 * 获取正在服务中的订单
	 * @param param
	 * @return
	 */
	public Map<String, Object> getServiceOder4Op(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			if(StringUtils.isBlank(usertoken)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("order",null);
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("account", account);
			if(isOrgUser(usertoken)){
				//机构用户
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","这个接口是给个人端用的");
				res.put("order",null);
			}else{
				//个人用户
				//获取正在服务中的网约车订单
				Map<String,Object> netcarorderinfo = orderdao.getServiceOder4OpNetCar(param);
				if(netcarorderinfo!=null){
					res.put("order",netcarorderinfo);
					return res;
				}
				//获取正在服务中的出租车订单
				Map<String,Object> taxiorderinfo = orderdao.getServiceOder4OpTaxi(param);
				res.put("order",taxiorderinfo);
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("order",null);
		}
		return res;
	}

	/**
	 * 是否有未支付的订单
	 * @param param
	 * @return
	 */
	public Map<String, Object> getUnpayOders4Op(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			if(StringUtils.isBlank(usertoken)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("order",null);
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("account", account);
			if(isOrgUser(usertoken)){
				//机构用户
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","这个接口是给个人端用的");
				res.put("order",null);
			}else{
				//个人用户
				//获取未支付的网约车订单
				Map<String,Object> netcarorderinfo = orderdao.getUnpayOrders4OpNetCar(param);
				if(netcarorderinfo!=null){
					res.put("order",netcarorderinfo);
					return res;
				}
				//获取未支付的出租车订单
				Map<String,Object> taxiorderinfo = orderdao.getUnpayOrders4OpTaxi(param);
				res.put("order",taxiorderinfo);
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("order",null);
		}
		return res;
	}

	/**
	 * 一键报警
	 * @param param
	 * @return
	 */
	public Map<String, Object> registerAlarm(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String usertoken = (String) param.get("usertoken");
			String orderno = (String) param.get("orderno");
			Object lng = param.get("lng");
			Object lat = param.get("lat");
			String ordertype = (String) param.get("ordertype");
			String usetype = (String) param.get("usetype");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(orderno)||lng==null||lat==null||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("account", account);
			
			SavePubAlarmprocessDto dto = new SavePubAlarmprocessDto();
			dto.setAlarmsource(AlarmProcessEnum.ALARMSOURCE_PASSENGER.code);
			dto.setAlarmtime(StringUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			dto.setAlarmtype(AlarmProcessEnum.ALARMTYPE_INSERVICE.code);
			dto.setLat(lat+"");
			dto.setLng(lng+"");
			dto.setOrderno(orderno);
			boolean istaxuorder = isTaxiOrder(ordertype);
//			if(istaxuorder){
//				dto.setOrdertype("1");
//			}else{
//				dto.setOrdertype("0");
//			}
			dto.setOrdertype(ordertype);
			String apiurl = null;
			if(isOrgUser(usertoken)){
				//机构用户
				dto.setUsertype(UserType.ORGUSER.code);
				dto.setPlatformtype(PlatformTypeByDb.LEASE.code);
				OrgUser user = userdao.getUser4Org(account);
				if(user!=null){
					dto.setUserid(user.getId());
					dto.setCreater(user.getId());
				}
				PassengerOrder order = orderdao.getOrderByOrderno4Org(orderno);
				if(order!=null){
					dto.setLeasecompanyid(order.getCompanyid());
					dto.setDriverid(order.getDriverid());
				}
				apiurl = SystemConfig.getSystemProperty("leaseApi");
			}else{
				//个人用户
				dto.setUsertype(UserType.PERSONAL.code);
				dto.setPlatformtype(PlatformTypeByDb.OPERATING.code);
				PeUser user = userdao.getUser4Op(account);
				if(user!=null){
					dto.setUserid(user.getId());
				}
				if(istaxuorder){
					//出租车
					Map<String,Object> orderinfo = orderdao.getOrder4OpTaxi(param);
					if(orderinfo!=null){
						dto.setDriverid((String)orderinfo.get("driverid"));
					}
				}else{
					//网约车
					Map<String,Object> orderinfo = orderdao.getOrder4OpNetCar(param);
					if(orderinfo!=null){
						dto.setDriverid((String)orderinfo.get("driverid"));
					}
				}
				Map<String,Object> opinfo = orderdao.getOpInfo();
				if(opinfo!=null){
					dto.setLeasecompanyid((String) opinfo.get("id"));
				}
				apiurl = SystemConfig.getSystemProperty("operateApiUrl");
			}
			JSONObject result = templateHelper.dealRequestWithFullUrlToken(apiurl+"/pubAlarmprocess/apply", 
				HttpMethod.POST, 
				usertoken, 
				dto, 
				JSONObject.class
			);
			if(result.getInt("status") != Retcode.OK.code){
				logger.error("报警申请失败:"+result);
				res.put("status", Retcode.FAILED.code);
				res.put("message", "报警申请失败");
				return res;
			}
			logger.info("报警申请完成");
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
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
			obj.put("cost", costinfo.get("cost"));
			JSONArray paydetail = new JSONArray();
			JSONObject obj3 = new JSONObject();
			obj3.put("name", "起步价");
			obj3.put("price", costinfo.get("startprice")+"元");
			JSONObject obj4 = new JSONObject();
			obj4.put("name", "里程("+StringUtil.formatNum(((int)costinfo.get("mileage"))/1000, 1)+"公里"+")");
			obj4.put("price", costinfo.get("rangecost")+"元");
			JSONObject obj5 = new JSONObject();
			obj5.put("name", "时长费("+StringUtil.formatCostTimeInSecond((int)costinfo.get("times"))+")");
			obj5.put("price", costinfo.get("timecost")+"元");
			paydetail.add(obj3);
			paydetail.add(obj4);
			paydetail.add(obj5);
			obj.put("paydetail",paydetail);
		}catch(Exception e){
			logger.error("乘客端异常",e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("costinfo",new HashMap<>());
		}
		return res;
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
				int mileage = (int)direc.get("distance");
				//打表来接的里程
				mileage = parseInt(params.get("meterrange"))*1000+mileage;
				int times = (int)direc.get("duration");
				accountrules.put("mileage", mileage);
				accountrules.put("times", times);
				//计算费用
				double startprice = parseDouble(accountrules.get("startprice"));
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
				
				int mileage = (int)direc.get("distance");
				int times = (int)direc.get("duration");
				accountrules.put("mileage", mileage);
				accountrules.put("times", times);
				//计算价格
				double startprice = parseDouble(accountrules.get("startprice"));
				double rangeprice = parseDouble(accountrules.get("rangeprice"));
				double timeprice = parseDouble(accountrules.get("timeprice"));
				String timetype = (String) accountrules.get("timetype");
				double perhour = parseDouble(accountrules.get("perhour"));
				double rangecost = StringUtil.formatNum(mileage/1000, 1)*rangeprice;
				accountrules.put("rangecost", StringUtil.formatNum(rangecost,1));
				//低速模式计算累计时长(预估时仍按总时长计费)
				double timecost = (times%60>0?(times/60+1):times/60)*timeprice;
				accountrules.put("timecost",StringUtil.formatNum(timecost,1));
				accountrules.put("cost",StringUtil.formatNum(startprice+rangecost+timecost,1));
				res.put("costinfo", accountrules);
			}
		}catch(Exception e){
			logger.error("乘客端异常",e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
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

	private int parseInt(Object value){
		if(value==null||"".equalsIgnoreCase(String.valueOf(value))){
			return 0;
		}
		return Integer.parseInt(String.valueOf(value));
	}
	
	/**
	 * 个人用户获取出租车预估费用
	 * @param param
	 * @return
	 */
	public Map<String, Object> getEstimatedCost4OpTaxi(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			String cityvalue = dicdao.getCityNo((String) params.get("city"));
			params.put("city", cityvalue);
			Map<String,Object> escostinfo = getEstimatedCost4Op(params,true);
			if((int)escostinfo.get("status")!=Retcode.OK.code){
				//预估失败
				escostinfo.put("cost",0);
				return escostinfo;
			}
			Map<String,Object> costinfo = (Map<String, Object>)escostinfo.get("costinfo");
			res.put("cost", costinfo.get("cost"));
		}catch(Exception e){
			logger.error("乘客端异常",e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("cost",0);
		}
		return res;
	}

	/**
	 * 行程分享
	 * @param param
	 * @return
	 */
	public Map<String, Object> itinerarySharing(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		String orderno = (String) param.get("orderno");
		String ordertype = (String) param.get("ordertype");
		String usetype = (String) param.get("usetype");
		if(StringUtils.isBlank(orderno)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(usetype)){
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message","参数不全");
			return res;
		}
		res.put("url", SystemConfig.getSystemProperty("paynotifyaddr")+"/passenger-api/itinerary.jsp?orderno="+orderno+"&ordertype="+ordertype+"&usetype="+usetype);
		return res;
	}

	/**
	 * 获取订单详情
	 * @param param
	 * @return
	 */
	public Map<String, Object> getOrder(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			Map<String,Object> orderinfo = null;
			orderinfo = orderdao.getOrder4OrgNetCar(param);
			if(orderinfo==null){
				orderinfo = orderdao.getOrder4OpNetCar(param);
			}
			if(orderinfo==null){
				orderinfo = orderdao.getOrder4OpTaxi(param);
			}
			String usertoken = (String) param.get("usertoken");
			dillOrder(orderinfo,usertoken,true);
			if(orderinfo==null){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message",Retcode.EXCEPTION.msg);
				res.put("order",new HashMap<>());
				return res;
			}else{
				String orderstatus = (String) orderinfo.get("orderstatus");
				if(OrderState.WAITTAKE.state.equals(orderstatus)||OrderState.MANTICSEND.state.equals(orderstatus)||OrderState.CANCEL.state.equals(orderstatus)){
					res.put("status",Retcode.EXCEPTION.code);
					res.put("message",Retcode.EXCEPTION.msg);
					res.put("order",new HashMap<>());
					return res;
				}
				if(OrderState.SERVICEDONE.state.equalsIgnoreCase(orderstatus)){
					String endtime = (String) orderinfo.get("endtime");
					if(StringUtils.isNotBlank(endtime)){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date endtimeobj = format.parse(endtime);
						//30分钟之后的时间
						Date temptime = new Date(endtimeobj.getTime() + 30 * 60 * 1000);
						Date currentobj = new Date();
						if(currentobj.after(temptime)){
							//当前时间在结束时间30分钟之后
							res.put("status",Retcode.EXCEPTION.code);
							res.put("message",Retcode.EXCEPTION.msg);
							res.put("order",new HashMap<>());
							return res;
						}
					}
				}
			}
			res.put("order", orderinfo);
		}catch(Exception e){
			logger.error("乘客端异常",e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("order",new HashMap<>());
		}
		return res;
	}

	/**
	 * 获取订单的行程轨迹
	 * @param param
	 * @return
	 */
	public Map<String, Object> getOrderTraceData(Map<String, Object> param) {
		String orderno = (String) param.get("orderno");
		String ordertype = (String) param.get("ordertype");
		String usetype = (String) param.get("usetype");
		return carserviceapi.dealRequestWithToken(
				"/BaiduApi/GetTraceData/?orderno={orderno}&usetype={usetype}&ordertype={ordertype}",
				HttpMethod.GET, null, 
				null, Map.class, orderno, usetype, ordertype);
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
	 * 个人微信充值回调处理
	 * @param req
	 * @param res
	 */
	public void dillWXCharge4Op(HttpServletRequest request, HttpServletResponse response) {
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
		                    	//交易流水信息
		                    	Map<String,Object> tradeinfo = userdao.getTradeRecord4Op(outtradeno);
		                    	if(tradeinfo==null){
		                    		res = "FAIL";
		                    	}else{
			                    	String tradeno = doc.getElementsByTagName("transaction_id").item(0).getFirstChild().getNodeValue();
		                    		//个人充值
		    						recharge4Op(tradeinfo, tradeno,"1");
		    						try{
		    							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			    						//运管财务人员消息
			    						String userid = (String) tradeinfo.get("userid");
			    						PeUser user = userdao.getPeUserById(userid);
			    						JSONObject json = new JSONObject();
			    						json.put("type", "51");
			    						json.put("title", "乘客充值成功");
			    						String content = "乘客："+user.getAccount()+"，充值："+parseDouble(tradeinfo.get("amount"))+"元，支付渠道：微信支付，充值时间："+format.format(new Date())+"。";
			    						json.put("content", content);
			    						addNews4OpUsers(json);
		    						}catch(Exception e){
		    							logger.error("乘客端异常",e);
		    						}
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
			logger.error("乘客端异常",e);
		}
	}

	/**
	 * 个人用户充值
	 * @param outtradeno
	 * @param tradeinfo
	 * @param tradeno
	 */
	private void recharge4Op(Map<String, Object> tradeinfo, String tradeno, String expensetype) {
		String userid = (String) tradeinfo.get("userid");
		double amount = parseDouble(tradeinfo.get("amount"));
		String outtradeno = (String) tradeinfo.get("outtradeno");
		//1、充值
		Map<String,Object> amountparam = new HashMap<String,Object>();
		amountparam.put("isout", 0);
		amountparam.put("money", amount);
		amountparam.put("userid", userid);
		amountparam.put("uid", GUIDGenerator.newGUID());
		userdao.updateUserBalance4OpSec(amountparam);
		//2、更新流水状态
		Map<String,Object> tradeparam = new HashMap<String,Object>();
		tradeparam.put("outtradeno", outtradeno);
		tradeparam.put("tradingstatus", "1");
		tradeparam.put("tradeno", tradeno);
		userdao.updateUserTradeRecord4Op(tradeparam);
		//3、记录
		Map<String,Object> expenses = new HashMap<String,Object>();
		expenses.put("logid", GUIDGenerator.newGUID());
		expenses.put("userid",userid);
		expenses.put("expensetype",expensetype);
		expenses.put("money",amount);
		expenses.put("remark","账户充值");
		expenses.put("tradetype","0");
		expenses.put("detailtype","0");
		expenses.put("operateresult","0");
		userdao.addExpenses4OpSec(expenses);
	}

	/**
	 * 机构微信充值回调处理
	 * @param req
	 * @param res
	 */
	public void dillWXCharge4Org(HttpServletRequest request, HttpServletResponse response) {
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
		                    	//交易流水信息
		                    	Map<String,Object> tradeinfo = userdao.getTradeRecord4Org(outtradeno);
		                    	if(tradeinfo==null){
		                    		res = "FAIL";
		                    	}else{
		                    		String tradeno = doc.getElementsByTagName("transaction_id").item(0).getFirstChild().getNodeValue();
		                    		//机构用户充值
		    						recharge4Org(tradeinfo, tradeno,"1");
		    						try{
		    							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		    							String userid = (String) tradeinfo.get("userid");
		    							OrgUser user = userdao.getOrgUserById(userid);
		    							JSONObject json = new JSONObject();
		    							json.put("type", "50");
		    							json.put("title", "乘客充值成功");
		    							String content = "乘客："+user.getAccount()+"，充值："+parseDouble(tradeinfo.get("amount"))+"元，支付渠道：微信支付，充值时间："+format.format(new Date())+"。";
		    							json.put("content", content);
		    							addNews4OpUsers(json);
		    						}catch(Exception e){
		    							logger.error("乘客端异常",e);
		    						}
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
			logger.error("乘客端异常",e);
		}
	}

	/**
	 * 机构充值
	 * @param tradeinfo
	 * @param tradeno
	 */
	private void recharge4Org(Map<String, Object> tradeinfo, String tradeno,String expensetype) {
		String userid = (String) tradeinfo.get("userid");
		double amount = parseDouble(tradeinfo.get("amount"));
		String companyid = (String) tradeinfo.get("leasescompanyid");
		String outtradeno = (String) tradeinfo.get("outtradeno");
		//1、充值
		Map<String,Object> amountparam = new HashMap<String,Object>();
		amountparam.put("isout", 0);
		amountparam.put("money", amount);
		amountparam.put("userid", userid);
		amountparam.put("companyid", companyid);
		amountparam.put("uid", GUIDGenerator.newGUID());
		userdao.updateUserBalance4OrgSec(amountparam);
		//2、更新流水状态
		Map<String,Object> tradeparam = new HashMap<String,Object>();
		tradeparam.put("outtradeno", outtradeno);
		tradeparam.put("tradingstatus", "1");
		tradeparam.put("tradeno", tradeno);
		userdao.updateUserTradeRecord4Org(tradeparam);
		//3、记录
		Map<String,Object> expenses = new HashMap<String,Object>();
		expenses.put("logid", GUIDGenerator.newGUID());
		expenses.put("userid",userid);
		expenses.put("companyid",companyid);
		expenses.put("expensetype",expensetype);
		expenses.put("money",amount);
		expenses.put("remark","账户充值");
		expenses.put("tradetype","0");
		expenses.put("detailtype","0");
		expenses.put("operateresult","0");
		userdao.addExpenses4OrgSec(expenses);
	}

	/**
	 * 支付宝充值验签方法
	 * @param request
	 * @param alipubkey
	 * @return
	 * @throws AlipayApiException
	 */
	private boolean isZFBSignValid(HttpServletRequest request, String alipubkey) throws AlipayApiException {
		Map<String,String> pp = new HashMap<String,String>();
		Enumeration<String> pnames = request.getParameterNames();
		while(pnames.hasMoreElements()){
			String pname = pnames.nextElement();
			pp.put(pname, request.getParameter(pname));
		}
		return AlipaySignature.rsaCheckV1(pp, alipubkey, AlipayConfig.input_charset);
	}
	
	/**
	 * 个人支付宝充值回调
	 * @param req
	 * @param res
	 */
	public void dillZFBCharge4Op(HttpServletRequest request, HttpServletResponse response) {
		//根据支付结果，更改订单状态，并且返回支付宝“success”
		String res = "success";
		try{
			String result_code = request.getParameter("trade_status");
			if("TRADE_SUCCESS".equalsIgnoreCase(result_code)){
				String out_trade_no = request.getParameter("out_trade_no");
				Map<String,Object> tradeinfo = userdao.getTradeRecord4Op(out_trade_no);
				if(tradeinfo==null){
					res = "failure";
				}else{
					String alipubkey = (String) tradeinfo.get("validatekey");
					if(isZFBSignValid(request,alipubkey)){
						//个人充值
						String tradeno = request.getParameter("trade_no");
						recharge4Op(tradeinfo, tradeno,"2");
						//运管财务人员消息
						try{
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							String userid = (String) tradeinfo.get("userid");
							PeUser user = userdao.getPeUserById(userid);
							JSONObject json = new JSONObject();
							json.put("type", "51");
							json.put("title", "乘客充值成功");
							String content = "乘客："+user.getAccount()+"，充值："+parseDouble(tradeinfo.get("amount"))+"元，支付渠道：支付宝支付，充值时间："+format.format(new Date())+"。";
							json.put("content", content);
							addNews4OpUsers(json);
						}catch(Exception e){
							logger.error("乘客端异常",e);
						}
					}else{
						//签名失败
						res = "failure";
					}
				}
			}else{
				//交易失败，返回失败
				res = "failure";
			}
			response.getWriter().write(res);
		}catch(Exception e){
			//记录日志
			logger.error("乘客端异常",e);
		}
	}

	/**
	 * 机构支付宝充值回调
	 * @param req
	 * @param res
	 */
	public void dillZFBCharge4Org(HttpServletRequest request, HttpServletResponse response) {
		//根据支付结果，更改订单状态，并且返回支付宝“success”
		String res = "success";
		try{
			String result_code = request.getParameter("trade_status");
			if("TRADE_SUCCESS".equalsIgnoreCase(result_code)){
				String out_trade_no = request.getParameter("out_trade_no");
				Map<String,Object> tradeinfo = userdao.getTradeRecord4Org(out_trade_no);
				if(tradeinfo==null){
					res = "failure";
				}else{
					String alipubkey = (String) tradeinfo.get("validatekey");
					if(isZFBSignValid(request,alipubkey)){
						//机构用户充值
						String tradeno = request.getParameter("trade_no");
						recharge4Org(tradeinfo, tradeno,"2");
						try{
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
							String userid = (String) tradeinfo.get("userid");
							OrgUser user = userdao.getOrgUserById(userid);
							JSONObject json = new JSONObject();
							json.put("type", "50");
							json.put("title", "乘客充值成功");
							String content = "乘客："+user.getAccount()+"，充值："+parseDouble(tradeinfo.get("amount"))+"元，支付渠道：支付宝支付，充值时间："+format.format(new Date())+"。";
							json.put("content", content);
							addNews4OpUsers(json);
						}catch(Exception e){
							logger.error("乘客端异常",e);
						}
					}else{
						//签名失败
						res = "failure";
					}
				}
			}else{
				//交易失败，返回失败
				res = "failure";
			}
			response.getWriter().write(res);
		}catch(Exception e){
			//记录日志
			logger.error("乘客端异常",e);
		}
	}

	/**
	 * 个人用户获取机场地址
	 * @param param
	 * @return
	 */
	public Map<String, Object> getAirportAddrt(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String city = (String) param.get("city");
			if(StringUtils.isBlank(city)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("airportaddrs",new ArrayList<>());
				return res;
			}
			List<AirportAddr> airportaddrs = dicdao.getAirportAddrt(city);
			if(airportaddrs==null){
				airportaddrs = new ArrayList<>();
			}
			res.put("airportaddrs", airportaddrs);
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("airportaddrs",new ArrayList<>());
		}
		return res;
	}

	/**
	 * 个人用车订单是否是即可单
	 * @param param
	 * @return
	 */
	public Map<String, Object> isUseNowOrder4OpTaxi(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		try{
			String city = (String) param.get("city");
			String usetime = (String) param.get("usetime");
			if(StringUtils.isBlank(city)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("isusenow",false);
				return res;
			}
			Map<String,Object> reserveinfo = orderdao.getReserveInfo4OpTaxi(param);
			if(reserveinfo==null){
				//当前城市没有预约用车的派单规则，所有下单都是即可单
				res.put("isusenow",true);
				return res;
			}
			int carsinterval = (int)reserveinfo.get("carsinterval");
			Date usetimeobj = new Date();
			if(StringUtils.isNotBlank(usetime)){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				usetimeobj = format.parse(usetime);
			}
			Date currentobj = new Date();
			Date temptime = new Date(currentobj.getTime() + carsinterval * 60 * 1000);
			if(usetimeobj.after(temptime)){
				//用车时间大,预约用车
				res.put("isusenow",false);
			}else{
				res.put("isusenow",true);
			}
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("isusenow",false);
		}
		return res;
	}
	
	public static void main(String[] args) throws ParseException {
		String s = "2017-03-30 16:22";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date d = format.parse(s);
		System.out.println();
	}

	/**
	 * 获取消息列表
	 * @param param
	 * @return
	 */
	public Map<String, Object> getMessages4Sec(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		res.put("currentcount",0);
		try{
			String usertoken = (String) param.get("usertoken");
			String iDisplayStart = (String) param.get("iDisplayStart");
			String iDisplayLength = (String) param.get("iDisplayLength");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(iDisplayStart)||StringUtils.isBlank(iDisplayLength)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("messages",new ArrayList<>());
				return res;
			}
			String account = Const.getUserInfo(usertoken).get("account");
			param.put("account", account);
			if(!param.containsKey("aboveread")){
				param.put("aboveread", true);
			}
			Object aboveread = param.get("aboveread");
			if(aboveread instanceof String&&"false".equalsIgnoreCase((String)aboveread)){
				param.put("aboveread", false);
			}else if(aboveread instanceof String&&"true".equalsIgnoreCase((String)aboveread)){
				param.put("aboveread", true);
			}
			List<Map<String,Object>> messages = null;
			if(isOrgUser(usertoken)){
				//机构用户
				messages = userdao.getMessages4OrgSec(param);
			}else{
				//个人用户
				messages = userdao.getMessages4OpSec(param);
			}
			if(messages==null){
				messages = new ArrayList<>();
			}
			res.put("messages",dillWithMessages(messages));
			res.put("currentcount", messages.size());
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("messages",new ArrayList<>());
		}
		return res;
	}
	
	private List<Map<String, Object>> dillWithMessages(List<Map<String, Object>> messages) {
		if(messages==null||messages.size()<=0){
			return messages;
		}
		List<Map<String, Object>> newmessages = new ArrayList<Map<String, Object>>();
		for(int i=0;i<messages.size();i++){
			Map<String, Object> message = messages.get(i);
			try{
				message.put("content", JSONObject.fromObject(message.get("content")));
				newmessages.add(message);
			}catch(Exception e){
				logger.error("乘客端异常",e);
			}
		}
		return newmessages;
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public Map<String, Object> getCurrentTime() {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		return res;
	}

	/**
	 * 个人端下单接口
	 * @param taxiorder
	 * @param request
	 * @return
	 */
	public Map<String, Object> addOder4OpTaxi(OpTaxiOrder taxiorder,HttpServletRequest request) {
		Map<String,Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		if(StringUtils.isNotBlank(taxiorder.getOrdersource())){
			taxiorder.setOrdersource(taxiorder.getOrdersource());
		}
		try{
			String usertoken = request.getParameter("usertoken");
			if(!StringUtils.isNotBlank(usertoken)){
				usertoken = request.getHeader("usertoken");
			}
			if(StringUtils.isBlank(usertoken)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				res.put("orderno","");
				res.put("sendinterval",0);
				res.put("isusenow",false);
				return res;
			}
			if(isOrgUser(usertoken)){
				//机构用户
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","这个接口是给个人端用的");
				res.put("orderno","");
				res.put("sendinterval",0);
				res.put("isusenow",false);
			}else{
				//个人用户
				taxiorder.setOncity(dicdao.getCityNo(taxiorder.getOncity()));
				taxiorder.setOffcity(dicdao.getCityNo(taxiorder.getOffcity()));
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
						res.put("status", Retcode.USERNOTSUPPORT.code);
						res.put("message", "您已被禁止下单，禁止时间为"+format.format(starttime)+"至"+format.format(endtime));
						res.put("orderno","");
						res.put("sendinterval",0);
						res.put("isusenow",false);
						return res;
					}
				}
				taxiorder.setUserid(user.getId());
				Map<String,Object> tempres = carserviceapi.dealRequestWithToken("/OrderApi/CreateOpTaxiOrder", HttpMethod.POST, usertoken, taxiorder, Map.class);
				if((int)tempres.get("status")!=0){
					res.put("sendinterval",0);
					res.put("isusenow",false);
				}
				if(!res.containsKey("orderno")){
					//下单错误。没有orderno时ios说要给
					res.put("orderno","");
				}
				res.putAll(tempres);
				if(taxiorder.isSavepassenger()){
					try{
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("userid", user.getId());
						params.put("name", taxiorder.getPassengers());
						params.put("phone", taxiorder.getPassengerphone());
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
		}catch(Exception e){
			logger.equals(e);
			res.put("status",Retcode.EXCEPTION.code);
			res.put("message",Retcode.EXCEPTION.msg);
			res.put("orderno","");
			res.put("sendinterval",0);
			res.put("isusenow",false);
		}
		return res;
	}

	/**
	 * 个人用户获取城市列表
	 * @param usertoken
	 * @param companyid
	 * @return
	 */
	public Map<String, Object> getValidCity4Op() {
		Map<String, Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			List<String> citys = opdao.getValidCity();
			if(citys==null){
				citys = new ArrayList<String>();
			}
			res.put("citys", citys);
		}catch(Exception e){
			res.put("citys", new ArrayList<String>());
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		return res;
	}
	
	public Map<String,Object> setOrderFail(Map<String,Object> params){
		Map<String, Object> res = new HashMap<String,Object>();
		addPubInfos(res);
		res.put("status", Retcode.OK.code);
		res.put("message", Retcode.OK.msg);
		try{
			String usertoken = (String) params.get("usertoken");
			String usetype = (String) params.get("usetype");
			String ordertype = (String) params.get("ordertype");
			String orderno = (String) params.get("orderno");
			if(StringUtils.isBlank(usertoken)||StringUtils.isBlank(ordertype)||StringUtils.isBlank(orderno)){
				res.put("status",Retcode.EXCEPTION.code);
				res.put("message","参数不全");
				return res;
			}
			params.put("cancelparty", CancelParty.SYSTEM.code);
			params.put("orderstatus", OrderState.CANCEL.state);
			if(isOrgUser(usertoken)){
				orderdao.setOrderFail4Org(params);
			}else{
				if(isTaxiOrder(ordertype)){
					//出租车
					orderdao.setOrderdao4OpTaxi(params);
				}else{
					//网约车
					orderdao.setOrderdao4OpNetCar(params);
				}
			}
		}catch(Exception e){
			res.put("citys", new ArrayList<String>());
			res.put("status", Retcode.EXCEPTION.code);
			res.put("message", Retcode.EXCEPTION.msg);
		}
		return res;
	}
}

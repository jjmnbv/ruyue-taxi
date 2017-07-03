package com.szyciov.driver.service;

import java.io.ByteArrayInputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.annotation.NeedRelease;
import com.szyciov.annotation.ValidateRule;
import com.szyciov.driver.base.BaseService;
import com.szyciov.driver.dao.AccountDao;
import com.szyciov.driver.dao.OrderDao;
import com.szyciov.driver.dao.TaxiOrderFuncDao;
import com.szyciov.driver.entity.DriverInfo;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.entity.PubDriverLoginLog;
import com.szyciov.driver.enums.AlarmProcessEnum;
import com.szyciov.driver.enums.DriverState;
import com.szyciov.driver.enums.OrderListEnum;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.SystemType;
import com.szyciov.driver.param.AtworkParam;
import com.szyciov.driver.param.BaseParam;
import com.szyciov.driver.param.ChangeDriverIconParam;
import com.szyciov.driver.param.ChangePasswordParam;
import com.szyciov.driver.param.GetCommonInfoParam;
import com.szyciov.driver.param.LoginParam;
import com.szyciov.driver.param.LogoutParam;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.driver.param.SendSMSParam;
import com.szyciov.driver.param.UploadOrdinatesParam;
import com.szyciov.dto.pubAlarmprocess.SavePubAlarmprocessDto;
import com.szyciov.entity.AbstractOrder;
import com.szyciov.entity.DataStatus;
import com.szyciov.entity.LoginStatus;
import com.szyciov.entity.PlatformType;
import com.szyciov.entity.PubAdImage;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubDrivertrack;
import com.szyciov.entity.PubJpushRegidlog;
import com.szyciov.entity.PubOrdergpsdata;
import com.szyciov.entity.PubSmsToken;
import com.szyciov.entity.PubSysVersion;
import com.szyciov.entity.PubUserToken;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.UserType;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.GpsSourceEnum;
import com.szyciov.enums.GpsStatusEnum;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.enums.PubJpushLogEnum;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.FileUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.JedisUtil;
import com.szyciov.util.PasswordEncoder;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.UserTokenManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("AccountService")
public class AccountService extends BaseService{
	/**
	 * 短信验证码超时时间(10分钟)
	 */
	private static long SMS_CODE_OVER_TIME = 60*1000*10;
	
	/**当前订单*/
	@NeedRelease
	private ThreadLocal<AbstractOrder> order = new ThreadLocal<AbstractOrder>();
	/**当前订单用车类型*/
	@NeedRelease
	private ThreadLocal<OrderEnum> usetype = new ThreadLocal<OrderEnum>();
	/**当前订单订单类型*/
	@NeedRelease
	private ThreadLocal<OrderEnum> ordertype = new ThreadLocal<OrderEnum>();
	
	private AccountDao dao;
	private OrderDao ordDao;
	private TaxiOrderFuncDao taxiOrdDao;
	
	@Resource(name = "AccountDao")
	public void setDao(AccountDao dao) {
		this.dao = dao;
	}
	
	@Resource(name = "OrderDao")
	public void setOrdDao(OrderDao ordDao) {
		this.ordDao = ordDao;
	}
	
	@Resource(name = "TaxiOrderFuncDao")
	public void setTaxiOrdDao(TaxiOrderFuncDao taxiOrdDao) {
		this.taxiOrdDao = taxiOrdDao;
	}
	
	/**
	 * 司机登入
	 * @param param
	 * @return
	 */
	public JSONObject login(LoginParam param) {
		String[] require = new String[]{"mobile","password","logintype"};
		if(!checkeParam(param,getExceptElement(param, require, false))) {
			return errorResult.get();
		}
		//如果使用短信验证码登录,并且验证不通过
		if(DriverEnum.LOGIN_TYPE_SMS.code.equals(param.getLogintype() + "") && !doCompairSMSCode(param)){
			return errorResult.get();
		//如果使用密码登录,并且验证不通过
		}else if (DriverEnum.LOGIN_TYPE_PASS.code.equals(param.getLogintype() + "") && !checkLoginPass(param)) {
			return errorResult.get();
		}
		JSONObject result = new JSONObject();
		
		result = doLogin(param);
		
		return result;
	}

	/**
	 * 司机登出
	 * @param param
	 * @return
	 */
	public JSONObject logout(LogoutParam param) {
		String[] require = new String[]{ };
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
//		param.setDriverid(driver.get().getId());
		if(!doLogout(param)) return errorResult.get();
		JSONObject result = new JSONObject();
		
		return result;
	}
	
	/**
	 * 司机上下班
	 * @param param
	 * @return
	 */
	public JSONObject atwork(AtworkParam param) {
		String[] require = new String[]{"type"};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		param.setId(driver.get().getId());
		if(!doAtWork(param)) return errorResult.get();
		
		updateUptimeOrOnlinetime(param);
		JSONObject result = new JSONObject();
		
		return result;
	}

	/**
	 * 发送短信验证码
	 * @param param
	 * @return
	 */
	public JSONObject sendSMS(SendSMSParam param) {
		String[] require = new String[]{"type","mobile"};
		String[] exc = new String[]{};
		//如果是登录发送短信去掉token验证
		if(DriverEnum.SMS_TYPE_LOGIN.code.equals(param.getType())){
			exc = getExceptElement(param, require, false);
		}else{
			exc = getExceptElement(param, require);
		}
		if(!checkeParam(param,exc)) return errorResult.get();
		//发送短信验证码
		if(!doSendSMS(param)) return errorResult.get();
		
		JSONObject result = new JSONObject();
		
		return result;
	}

	/**
	 * 获取司机基础信息
	 * @param param
	 * @return
	 */
	public JSONObject getDriverInfo(BaseParam param) {
		String[] require = new String[]{ };
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		JSONObject result = new JSONObject();
		result.put("info", doGetDriverInfo(param));
		return result;
	}
	
	/**
	 * 上传头像
	 * @param param
	 * @return
	 */
	public JSONObject changeDriverIcon(ChangeDriverIconParam param) {
		String[] require = new String[]{"icon"};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		param.setDrverid(driver.get().getId());
		if(!doChangeDriverIcon(param)) return errorResult.get();
		JSONObject result = new JSONObject();
		result.put("message", "修改成功");
		return result;
	}
	
	/**
	 * 变更司机密码
	 * @param param
	 * @return
	 */
	public JSONObject changePassword(ChangePasswordParam param) {
		String[] require = new String[]{"password","type"};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		param.setId(driver.get().getId());
		if(!doChagnePassword(param)) return errorResult.get();
		JSONObject result = new JSONObject();
		result.put("message", "密码修改成功");
		return result;
	}

	/**
	 * 验证原密码是否正确
	 * @param param
	 * @return
	 */
	public JSONObject authOldPass(ChangePasswordParam param) {
		String[] require = new String[]{"password","type"};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		param.setId(driver.get().getId());
		if(!doAuthOldPass(param)) return errorResult.get();
		JSONObject result = new JSONObject();

		return result;
	}
	
	/**
	 * 司机上传坐标
	 * @param param
	 * @return
	 */
	public JSONObject uploadOrdinates(UploadOrdinatesParam param) {
		String[] require = new String[]{"lat","lng"};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		String orderno = param.getOrderno();
		if(StringUtils.isNotBlank(orderno)){
			int leftime = param.getLefttime();
			int leftkm = param.getLeftkm();
			JSONObject json = new JSONObject();
			json.put("lefttime", leftime);
			json.put("leftkm", leftkm);
			JedisUtil.setString(RedisKeyEnum.MESSAGE_ORDER_TRAVEL_INFO.code+orderno, json.toString());
		}
		
		param.setId(driver.get().getId());
		if(!doUploadOrdinates(param)) return errorResult.get();
		
		JSONObject result = new JSONObject();
		
		return result;
	}
	
	/**
	 * 检查版本信息
	 * @param param
	 * @return
	 */
	public JSONObject checkAPPVersion(GetCommonInfoParam param){
		String[] require = new String[]{"systemtype"};
		if(!checkeParam(param,getExceptElement(param, require,false))) {
			return errorResult.get();
		}
		
		JSONObject result = doCheckAPPVersion(param);
		
		return result;
	}
	
	/**
	 * 获取广告页
	 * @param param
	 * @return
	 */
	public JSONObject getADPage(GetCommonInfoParam param){
		String[] require = new String[]{"systemtype"};
		if(!checkeParam(param,getExceptElement(param, require,false))) {
			return errorResult.get();
		}
		
		JSONObject result = doGetADPage(param);
		return result;
	}
	
	/**
	 * 获取引导页
	 * @param param
	 * @return
	 */
	public JSONObject getGuidePage(GetCommonInfoParam param){
		String[] require = new String[]{"systemtype"};
		if(!checkeParam(param,getExceptElement(param, require,false))) {
			return errorResult.get();
		}
		
		JSONObject result = doGetGuidePage(param);
		return result;
	}
	
	/**
	 * 一键报警
	 * @param param
	 * @return
	 */
	public JSONObject callPolice(UploadOrdinatesParam param){
		//如果是候客报警,不检查订单号
		String[] require = new String[]{ "lat","lng","type" };
		//如果是行程中报警,检查订单号
		if(!AlarmProcessEnum.ALARMTYPE_WAITING.code.equals(param.getType()+"")){
			require = new String[]{ "lat","lng","type","usetype","ordertype","orderno"};
		}
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		
		JSONObject result = new JSONObject();
		if(!doCallPolice(param)) {
			return errorResult.get();
		}
		
		return result;
	}
	
	/**
	 * 保存极光推送ID
	 * @param param
	 * @return
	 */
	public JSONObject saveRegID(LoginParam param){
		//如果是候客报警,不检查订单号
		String[] require = new String[]{ "regid"};
		if(!checkeParam(param,getExceptElement(param, require))) return errorResult.get();
		JSONObject result = new JSONObject();
		if(!doSaveRegID(param)) {
			return errorResult.get();
		}
		return result;
	}
	
	/**
	 * 保存极光推送ID
	 * @param param
	 * @return
	 */
	public JSONObject removeOBD(){
    	String key = "OBD_MILGES_CURRENT_ORDER_*";
		Set<String> keys = JedisUtil.getKeys(key);
		for(String k : keys){
			JedisUtil.delKey(k);
		}
		logger.info("删除obd轨迹信息成功");
        return new JSONObject();
	}
	
	/**********************************************************内部方法***************************************************************/
	/**
	 * 保存极光推送ID逻辑
	 * @param param
	 * @return
	 */
	private boolean doSaveRegID(LoginParam param){
		logger.info("保存极光推送ID...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		PubDriver pd = driver.get();
		//与原值不同,保存历史
		if(pd.getRegistrationid() == null || pd.getRegistrationid().isEmpty()){
			pd.setRegistrationid(param.getRegid());
			accdao.updatePubDriver(pd);
		}else if(!param.getRegid().equals(pd.getRegistrationid())){
			//先保存原值再更新新值
			PubJpushRegidlog regidlog = new PubJpushRegidlog();
			regidlog.setId(GUIDGenerator.newGUID());
			regidlog.setRegistrationid(pd.getRegistrationid());
			regidlog.setUserid(pd.getId());
			regidlog.setUsertype(Integer.valueOf(PubJpushLogEnum.DRIVER_USERTYPE_PUBJPUSHREGIDLOG.code));
			regidlog.setStatus(DataStatus.OK.code);
			accdao.saveRegID(regidlog);
			pd.setRegistrationid(param.getRegid());
			accdao.updatePubDriver(pd);
		}else{
			logger.info("极光推送ID相同,不做更改.");
		}
		logger.info("保存极光推送ID完成...");
		return true;
	}
	
	/**
	 * 司机登出逻辑
	 * @param param
	 * @return
	 */
	private boolean doLogout(LogoutParam param){
		logger.info("司机登出...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		PubDriver pd = driver.get();
		PubUserToken token = accdao.getUserTokenByToken(param.getToken());
		//异常退出不做判断 直接退出
		if(param.isException()){
			pd.setWorkstatus(DriverState.OFFLINE.code);
			dao.updatePubDriver(pd);
			token.setUsertoken(null);
			token.setUpdatetime(new Date());
			dao.saveOrUpdateUserToken(token);
			logger.info("司机异常登出完成");
			return true;
		}
		//处于服务中的司机,不允许退出
		if(!checkInserviceOrder()){
			logger.info("司机登出失败,存在未完成订单");
			return false;
		//处于上班状态的司机不允许退出
		}else if (!checkDriverCanLogout()){
			return false;
		}else{
			//退出清空token
			token.setUsertoken(null);
			token.setUpdatetime(new Date());
			dao.saveOrUpdateUserToken(token);
			logger.info("司机登出完成");
			return true;
		}
	}
	
	/**
	 * 司机上下班逻辑
	 * @param param
	 * @return
	 */
	private boolean doAtWork(AtworkParam param){
		logger.info("司机{}...",DriverEnum.getDriverAtWorkType(param.getType()));
		logger.info("使用参数:" + JSONObject.fromObject(param));
		PubDriver pd =  driver.get();
		//如果请求上班并且存在服务中订单(需要特别返回OK)
		if (DriverEnum.ATWORK_TYPE_ON.code.equals(param.getType()) && !checkInserviceOrder()) {
			errorResult.get().put("status", Retcode.OK.code);
			errorResult.get().put("message", Retcode.OK.msg);
			//设置司机状态为服务中
			pd.setWorkstatus(DriverState.INSERVICE.code);
			dao.updatePubDriver(pd);
			logger.info("司机上班,并且存在服务中订单");
			updateUptimeOrOnlinetime(param);
			return false;
		}
		//上班设置为空闲,下班设置为离线
		if(DriverEnum.ATWORK_TYPE_ON.code.equals(param.getType())){
			pd.setWorkstatus(DriverState.IDLE.code);
		}else{
			pd.setWorkstatus(DriverState.OFFLINE.code);
		}
		dao.updatePubDriver(pd);
		logger.info("司机{}完成",DriverEnum.getDriverAtWorkType(param.getType()));
		return true;
	}
	
	/**
	 * 校验短信验证码
	 * @param param
	 * @return
	 */
	private boolean doCompairSMSCode(LoginParam param){
		logger.info("校验短信验证码开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		SendSMSParam ssp = new SendSMSParam();
		ssp.setMobile(param.getMobile());
		ssp.setType(DriverEnum.SMS_TYPE_LOGIN.code);
		ssp.setUsertype(UserTokenManager.DRIVERUSER);
		PubSmsToken smsToken = dao.getPubSmsTokenByUserPhone(ssp);
	    //检查验证码
		if(smsToken == null || !param.getPassword().equals(smsToken.getSmscode())){
			errorResult.get().put("status", Retcode.SMSCODEINVALID.code);
			errorResult.get().put("message", Retcode.SMSCODEINVALID.msg);
		//如果验证码超时则提示超时
		}else if (System.currentTimeMillis() - smsToken.getUpdatetime().getTime() > SMS_CODE_OVER_TIME) {
			errorResult.get().put("status", Retcode.SMSCODEOUTTIME.code);
			errorResult.get().put("message", Retcode.SMSCODEOUTTIME.msg);
		}else{
			//验证码登录成功之后,清空验证码
			smsToken.setSmscode(null);
			dao.saveOrUpdateSmsToken(smsToken);
			logger.info("校验短信验证码完成");
			return true;
		}
		logger.info("校验短信验证码失败");
		return false;
	}
	
	/**
	 * 发送短信验证码逻辑
	 * @param param
	 * @return
	 */
	private boolean doSendSMS(SendSMSParam param){
		logger.info("发送短信验证码开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		//先查是否存在,存在则更新,不存在则新插入
		param.setUsertype(UserTokenManager.DRIVERUSER);
		PubSmsToken smsToken = getSMSToken(param);
		
		List<String> userids = new ArrayList<>();
		userids.add(param.getMobile());
		//是否是登录验证码
		if(DriverEnum.SMS_TYPE_LOGIN.code.equals(param.getType())){
			smsToken.setSmscode(StringUtil.getRandomNum(4));
			String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.driver.service.accountservice.sendsms", smsToken.getSmscode());
			UserMessage um = new UserMessage(userids,content,UserMessage.GETSMSCODE);
			MessageUtil.sendMessage(um);
		}else{
			smsToken.setSmscode(StringUtil.getRandomStr(6, StringUtil.HASNUM));
			String content = SMSTempPropertyConfigurer.getSMSTemplate("com.szyciov.driver.service.accountservice.sendsms", smsToken.getSmscode());
			UserMessage um = new UserMessage(userids,content,UserMessage.CHANGEPASSWORD);
			MessageUtil.sendMessage(um);
		}
		//保存数据
		dao.saveOrUpdateSmsToken(smsToken);
		logger.info("发送短信验证码完成");
		return true;
	}
	
	/**
	 * 获取司机基础信息逻辑
	 * @param param
	 * @return
	 */
	private DriverInfo doGetDriverInfo(BaseParam param){
		logger.info("获取司机基础信息开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		DriverInfo di = dao.getDriverInfoById(driver.get().getId());
		String fileserver = SystemConfig.getSystemProperty("fileserver")+"/";
		if(di.getHeadportraitmax() != null && !di.getHeadportraitmax().trim().isEmpty()){
			di.setHeadportraitmax(fileserver+di.getHeadportraitmax());
		}
		if(di.getHeadportraitmin() != null && !di.getHeadportraitmin().trim().isEmpty()){
			di.setHeadportraitmin(fileserver+di.getHeadportraitmin());
		}
//		di.setWorkstatus(driver.get().getWorkstatus());
		logger.info("获取司机基础信息完成");
		return di;
	}
	
	/**
	 * 司机上传头像逻辑
	 * @param param
	 * @return
	 */
	private boolean doChangeDriverIcon(ChangeDriverIconParam param){
		logger.info("司机上传头像开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		PubDriver pd =  driver.get();
		Map<String, Object> fileResult = new HashMap<String, Object>();
		try{
			String img = param.getIcon();
			byte[] imgbyte = Base64.decodeBase64(img);
			ByteArrayInputStream inputstream = new ByteArrayInputStream(imgbyte);
			try{
				fileResult = FileUtil.upload2FileServer(inputstream,"driverimg.jpg");
			}finally {
				inputstream.close();
			}
		}catch(Exception e){}
		if(fileResult!=null&&fileResult.get("message")!=null){
			String path = (String) ((List<?>)fileResult.get("message")).get(0);
			// 更新用户头像
			pd.setHeadportraitmax(path);
			pd.setHeadportraitmin(path);
			dao.updatePubDriver(pd);  //更新司机头像地址
		}else{
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", "修改失败");
			return false;
		}
		logger.info("司机上传头像完成");
		return true;
	}
	
	/**
	 * 变更司机密码逻辑
	 * @param param
	 * @return
	 */
	private boolean doChagnePassword(ChangePasswordParam param){
		logger.info("变更司机密码开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		PubDriver pd =  driver.get();
		//加密后
		String encodePass= PasswordEncoder.encode(param.getPassword());
		//根据类型改变对应密码
		if(DriverEnum.PASSWORD_TYPE_LOGINPASS.code.equals(param.getType()+"")){
			pd.setUserpassword(encodePass);
		}else{
			pd.setWithdrawpwd(encodePass);
			pd.setWdpwdchangestate(DriverEnum.WDPASS_CHANGED.code);
		}
		dao.updatePubDriver(pd);
		logger.info("变更司机密码完成");
		return true; 
	}
	
	/**
	 * 比对原密码
	 * @param param
	 * @return
	 */
	private boolean doAuthOldPass(ChangePasswordParam param){
		logger.info("比对原密码开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		boolean valid = false;
		PubDriver pd =  driver.get();
		String encodePass= PasswordEncoder.encode(param.getPassword());
		//根据类型校验对应密码,如果提现密码未修改过,当做登录密码校验
		if(DriverEnum.PASSWORD_TYPE_LOGINPASS.code.equals(param.getType()+"")){
			valid = pd.getUserpassword().equals(encodePass);
		//如果校验提现密码,更改提现密码状态不为已修改
		}else if (DriverEnum.PASSWORD_TYPE_WITHDRAWPASS.code.equals(param.getType()+"")){
			if(DriverEnum.WDPASS_CHANGED.code.equals(pd.getWdpwdchangestate())) {
				valid = pd.getWithdrawpwd().equals(encodePass);
			}else{
				valid = pd.getUserpassword().equals(encodePass);
			}
		}
		if(!valid){
			errorResult.get().put("status", Retcode.PASSWORDWRONG.code);
			errorResult.get().put("message", Retcode.PASSWORDWRONG.msg);
			logger.info("比对原密码失败,密码错误");
		}
		logger.info("比对原密码完成");
		return valid;
	}
	
	/**
	 * 上传司机坐标逻辑
	 * @param param
	 * @return
	 */
	private boolean doUploadOrdinates(UploadOrdinatesParam param){
		logger.info("上传司机坐标开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		PubDriver pd =  driver.get();
        PubOrdergpsdata ordergpsdata = new PubOrdergpsdata();
        ordergpsdata.setDriverid(pd.getId());
        ordergpsdata.setLng(param.getLng());
        ordergpsdata.setLat(param.getLat());
        ordergpsdata.setGpsspeed(param.getSpeed());
        ordergpsdata.setGpsdirection(param.getFacing());
        ordergpsdata.setGpssource(GpsSourceEnum.APP_GPS.code);
        ordergpsdata.setGpsstatus(GpsStatusEnum.VALID.code);
        ordergpsdata.setGpstime(new Date());
        JSONObject result = templateHelper.dealRequestWithToken("/MileageApi/UploadGps", HttpMethod.POST, null, ordergpsdata, JSONObject.class);
        if(null != result && result.containsKey("status") || result.getInt("status") == Retcode.OK.code) {
            return true;
        }
        logger.info("上传司机坐标完成");
		return false;
	}
	
	/**
	 * 保存司机轨迹逻辑
	 * @param pd
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean doSaveDriverTrack(PubDriver pd){
		try {
			logger.info("保存司机轨迹...");
			PubDrivertrack drivertrack = new PubDrivertrack();
			drivertrack.setId(GUIDGenerator.newGUID());
			drivertrack.setLat(pd.getLat());
			drivertrack.setLng(pd.getLng());
			drivertrack.setDriverid(pd.getId());
			drivertrack.setGpsdirection(pd.getGpsdirection());
			drivertrack.setGpsspeed(pd.getGpsspeed());
			drivertrack.setCreatetime(new Date());
			drivertrack.setStatus(DataStatus.OK.code);
			dao.saveDriverTrack(drivertrack);
		} catch (Exception e) {
			logger.error("保存司机轨迹失败.",e);
			return false;
		}
		return true;
	}
	
	/**
	 * 检查版本信息逻辑
	 * @param param
	 * @return
	 */
	private JSONObject doCheckAPPVersion(GetCommonInfoParam param){
		logger.info("检查版本信息开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		JSONObject result = new JSONObject();
		//获取最新版本
		PubSysVersion curv = new PubSysVersion();
		curv.setPlatformtype(PlatformType.DRIVER.code);
		curv.setSystemtype(param.getSystemtype());
		result = getVersion(curv);
		if(result.getInt("status") == Retcode.OK.code){
			curv = StringUtil.parseJSONToBean(result.getJSONObject("version").toString(), PubSysVersion.class);
			curv.setCreatetime(new Date(result.getJSONObject("version").getLong("createtime")));
			curv.setUpdatetime(new Date(result.getJSONObject("version").getLong("updatetime")));
			curv.setReleasedate(new Date(result.getJSONObject("version").getLong("releasedate")));
		}else{
			curv = null;
		}
		
		//获取本地版本
		PubSysVersion locv = new PubSysVersion();
		locv.setPlatformtype(PlatformType.DRIVER.code);
		locv.setSystemtype(param.getSystemtype());
		locv.setVersionno(param.getAppversion());
		result = getVersion(locv);
		if(result.getInt("status") == Retcode.OK.code){
			locv = StringUtil.parseJSONToBean(result.getJSONObject("version").toString(), PubSysVersion.class);
			locv.setCreatetime(new Date(result.getJSONObject("version").getLong("createtime")));
			locv.setUpdatetime(new Date(result.getJSONObject("version").getLong("updatetime")));
			locv.setReleasedate(new Date(result.getJSONObject("version").getLong("releasedate")));
		}else{
			locv = null;
		}
		result.clear();
		//比对版本信息
		result.put("versioninfo", doCompairVersion(curv,locv));
		
		logger.info("检查版本信息完成");
		return result;
	}
	
	/**
	 * 比较版本信息
	 * @param curv
	 * @param locv
	 * @return
	 */
	private JSONObject doCompairVersion(PubSysVersion curv,PubSysVersion locv){
		JSONObject versioninfo = new JSONObject();
		//如果最新版本获取本地版本为空
		if(curv == null || locv == null){
			versioninfo.put("version", "");
			versioninfo.put("versionno", 0);
			versioninfo.put("maxversionno", 0);
			versioninfo.put("changelog", "");
			versioninfo.put("downloadhref", "");
			versioninfo.put("releasetime", 0);
			versioninfo.put("localreleasetime", 0);
			versioninfo.put("versionstatus", "0");
			logger.info("比对版本信息失败");
			return versioninfo;
		}
		//下载地址需要加上服务器路径
		String href = (curv.getDownloadhref().startsWith("http")?"":fileServer) + curv.getDownloadhref();
		//版本状态,是否需要强制更新
		int versionstatus = curv.getVersionno() == locv.getVersionno() ? 1 : 
										  curv.getMaxversionno() > locv.getVersionno() ? 3 : 2;
		versioninfo.put("version", curv.getCurversion());
		versioninfo.put("versionno", curv.getVersionno());
		versioninfo.put("maxversionno", curv.getMaxversionno());
		versioninfo.put("changelog", curv.getChangelog());
		versioninfo.put("downloadhref", href);
		versioninfo.put("releasetime", curv.getReleasedate());
		versioninfo.put("localreleasetime", locv.getReleasedate());
		versioninfo.put("versionstatus", versionstatus);
		return versioninfo;
	}
	
	/**
	 * 获取广告页逻辑
	 * @param param
	 * @return
	 */
	private JSONObject doGetADPage(GetCommonInfoParam param){
		logger.info("获取广告页开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		JSONObject result = new JSONObject();
		//获取广告页
		PubAdImage adpage = new PubAdImage();
		adpage.setApptype(PlatformType.DRIVER.code);
		adpage.setImgtype("0");
		result = getAdImage(adpage);
		if(result.getInt("status") == Retcode.OK.code){
			adpage = StringUtil.parseJSONToBean(result.getJSONObject("adimage").toString(), PubAdImage.class);
		}else{
			adpage = null;
		}
		result.clear();
		String fileserver = SystemConfig.getSystemProperty("fileserver");
		if(adpage != null && adpage.getImgaddr() != null){
			String href = (adpage.getImgaddr().startsWith("http")?"":fileserver) + adpage.getImgaddr();
			result.put("adpage", href);
		}else{
			result.put("adpage", "");
		}
		logger.info("获取广告页完成");
		return result;
	}

	/**
	 * 获取引导页逻辑
	 * @param param
	 * @return
	 */
	private JSONObject doGetGuidePage(GetCommonInfoParam param){
		logger.info("获取引导页开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		JSONObject result = new JSONObject();
		//获取引导页
		PubAdImage guidepage = new PubAdImage();
		guidepage.setApptype(PlatformType.DRIVER.code);
		guidepage.setImgtype("1");
		guidepage.setVersion(param.getVersion());
		result = getAdImage(guidepage);
		if(result.getInt("status") == Retcode.OK.code){
			guidepage = StringUtil.parseJSONToBean(result.getJSONObject("adimage").toString(), PubAdImage.class);
		}else{
			guidepage = null;
		}
		result.clear();
		String fileserver = SystemConfig.getSystemProperty("fileserver");
		if(guidepage != null && guidepage.getImgaddr() != null){
			String[] strs = guidepage.getImgaddr().split(",");
			JSONArray arr = new JSONArray();
			for(String s : strs){
				String href = (s.startsWith("http")?"":fileserver) + s;
				arr.add(href);
			}
			result.put("guidepage", arr);
		}else{
			result.put("guidepage", new JSONArray());
		}
		logger.info("获取引导页完成");
		return result;
	}
	/**
	 * 一键报警处理逻辑
	 * @param param
	 * @return
	 */
	private boolean doCallPolice(UploadOrdinatesParam param){
		logger.info("报警申请开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		//如果是个人用车就是运管平台,非个人用车就是租赁平台
		PlatformTypeByDb platform = OrderEnum.USETYPE_PERSONAL.equals(usetype.get()) ?PlatformTypeByDb.OPERATING:PlatformTypeByDb.LEASE;
		UserType usertype = OrderEnum.USETYPE_PERSONAL.equals(usetype.get()) ?UserType.PERSONAL:UserType.ORGUSER;
		SavePubAlarmprocessDto dto = new SavePubAlarmprocessDto();
		dto.setAlarmsource(AlarmProcessEnum.ALARMSOURCE_DRIVER.code);
		dto.setAlarmtime(StringUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		dto.setAlarmtype(param.getType()+"");
		dto.setCreater(driver.get().getId());
		dto.setDriverid(driver.get().getId());
		dto.setLat(param.getLat()+"");
		dto.setLng(param.getLng()+"");
		dto.setLeasecompanyid(driver.get().getLeasescompanyid());
		dto.setOrderno(order.get()==null?null:param.getOrderno());
		dto.setPlatformtype(order.get()==null?driver.get().getPlatformtype():platform.code);
		dto.setUserid(order.get()==null?null:order.get().getUserid());
		dto.setOrdertype(order.get()==null?null:ordertype.get().code);
		dto.setUsertype(order.get()==null?null:usertype.code);
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
			(PlatformTypeByDb.OPERATING.equals(platform) ? operateApiUrl : leaseApiUrl) + "/pubAlarmprocess/apply", 
			HttpMethod.POST, 
			param.getToken(), 
			dto, 
			JSONObject.class
		);
		if(result.getInt("status") != Retcode.OK.code){
			logger.error("报警申请失败:"+result);
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", "报警申请失败");
			return false;
		}
		logger.info("报警申请完成");
		return true;
	}
	
	/**
	 * 获取当前|本地版本信息
	 * @param param
	 * @return
	 */
	private JSONObject getVersion(PubSysVersion param){
		JSONObject result = new JSONObject();
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		result = templateHelper.dealRequestWithFullUrl(
				carserviceApiUrl+"/PubInfoApi/GetPubSysVersion", 
				HttpMethod.POST, 
				param, 
				JSONObject.class,
				new HashMap<>());
		if(result.getInt("status") != Retcode.OK.code) {
			if(param.getVersionno() == 0){
				logger.info("获取最新版本信息失败.");
			}else {
				logger.info("获取本地版本信息失败.");
			}
		}
		return result;
	}
	
	/**
	 * 获取广告|引导页页信息
	 * @param param
	 * @return
	 */
	private JSONObject getAdImage(PubAdImage param){
		JSONObject result = new JSONObject();
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		result = templateHelper.dealRequestWithFullUrl(
				carserviceApiUrl+"/PubInfoApi/GetPubAdImage", 
				HttpMethod.POST, 
				param, 
				JSONObject.class,
				new HashMap<>());
		if(result.getInt("status") != Retcode.OK.code) {
			if(param.getImgtype().equals("0")){
				logger.info("获取广告页信息失败.");
			}else {
				logger.info("获取引导页信息失败.");
			}
		}
		return result;
	}

	/**
	 * 司机登入逻辑
	 * @param param
	 * @return
	 */
	private JSONObject doLogin(LoginParam param){
		JSONObject result = new JSONObject();
		logger.info("司机登入...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		String usertoken = doSaveUserToken(param);
		if(usertoken == null) return errorResult.get();
		//app需要将tags设置为别名
		List<String> tags = new ArrayList<String>();
		byte[] usertokencode = Base64.decodeBase64(usertoken);
		String decodetoken = new String(usertokencode);
		tags.add(decodetoken.substring(32, 49));
		//返回tags和token
		result.put("tags", tags);
		result.put("token", usertoken);
		//保存司机登录日志
		doSaveLoginLog(param,LoginStatus.OK);
		//返回司机信息,app需要展示不同界面
		DriverInfo driverInfo = dao.getDriverInfoById(driver.get().getId());
		result.put("hasworkmate", driverInfo.isHasworkmate());
		result.put("drivertype", driverInfo.getDrivertype());
		result.put("contact", driverInfo.getContact());
		result.put("wdpchanged", driverInfo.isWdpchanged());
		logger.info("司机登入完成");
		return result;
	}
	
	/**
	 * 获取usertoken,如果没有则新建
	 * @return
	 */
	private PubUserToken getToken(){
		//先检查是否存在token,不存在则新插入
		PubUserToken token = dao.getUserTokenByUser(driver.get());
		if(token == null){
			token = new PubUserToken();
			token.setId(GUIDGenerator.newGUID());
			token.setUserid(driver.get().getId());
			token.setUsertype(UserType.DRVER.code);
			token.setCreatetime(new Date());
			token.setStatus(DataStatus.OK.code);
		}
		return token;
	}
	
	/**
	 * 获取smstoken,如果没有则新建
	 * @return
	 */
	private PubSmsToken getSMSToken(SendSMSParam param){
		//先检查是否存在token,不存在则新插入
		PubSmsToken smsToken = dao.getPubSmsTokenByUserPhone(param);
		if(smsToken == null){
			smsToken = new PubSmsToken();
			smsToken.setId(GUIDGenerator.newGUID());
			smsToken.setSmstype(param.getType());
			smsToken.setUsertype(UserTokenManager.DRIVERUSER);
			smsToken.setUsername(param.getMobile());             //username存手机号
			smsToken.setCreatetime(new Date());
			smsToken.setStatus(DataStatus.OK.code);
		}
		smsToken.setUpdatetime(new Date());
		return smsToken;
	}
	
	/**
	 * 保存用户令牌逻辑
	 * @param param
	 * @return
	 */
	private String doSaveUserToken(LoginParam param){
		logger.info("保存用户令牌...");
		String usertoken = null;
		try {
			usertoken = UserTokenManager.createUserToken(
				UserTokenManager.DRIVERUSER,                                //司机类型
				driver.get().getPhone(),                                                    //登录账号
				SystemConfig.getSystemProperty("securityKey")          //默认秘钥
			);
		} catch (NoSuchAlgorithmException e) {
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", "token创建失败");
			logger.info("用户令牌创建失败");
			return null;
		}
		//先检查是否存在token,不存在则新插入
		PubUserToken token = getToken();
		//检查是否挤下线登录
		checkAnotherLogin(token,param.getUuid());
		//设置新token,uuid&更新时间
		token.setUuid(param.getUuid());
		token.setUsertoken(usertoken);
		token.setUpdatetime(new Date());
		dao.saveOrUpdateUserToken(token);
		logger.info("保存用户令牌完成");
		return usertoken;
	}
	
	/**
	 * 保存司机登录日志
	 * @param param
	 * @param loginstatus
	 * @return
	 */
	private boolean doSaveLoginLog(LoginParam param,LoginStatus loginstatus){
		logger.info("保存司机登入日志...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		PubDriverLoginLog log = new PubDriverLoginLog();
		log.setId(GUIDGenerator.newGUID());
		//这里待确认,如果登录失败是获取不到司机ID的
		if(driver.get() == null){
//			json.put("hasworkmate", "");
//			json.put("drivertype", "");
//			json.put("contact", "");
//			json.put("wdpchanged", "");
			log.setDriverid(param.getMobile());
		}else{
			log.setDriverid(driver.get().getId());
		}
		log.setIpaddr(param.getIpaddr());
		log.setDevice(param.getDevice()+"");
		log.setPhonebrand(param.getPhonebrand());
		log.setPhonemodel(param.getPhonemodel());
		log.setVersion(param.getDeviceversion());
		log.setAppversion(param.getAppversion());
		log.setCreatetime(new Date());
		log.setLogintime(new Date());
		log.setUpdatetime(new Date());
		log.setStatus(DataStatus.OK.code);
		log.setLoginstatus(loginstatus.code);
		dao.saveLoginLog(log);
		logger.info("保存司机登入日志完成");
		return true;
	}
	
	/**
	 * 更新司机在线时长
	 * @param param
	 */
   private void updateUptimeOrOnlinetime(AtworkParam param){
		//上下班存上线时间、在线时长 工作状态(0-上班;1-下班)
		PubDriver pd = driver.get();
		if(DriverEnum.ATWORK_TYPE_ON.code.equals(param.getType())){
			pd.setUptime(new Date());
			pd.setType(DriverEnum.ATWORK_TYPE_ON.code);
		}else{
			if(pd.getUptime() != null){
				Date d1 = new Date();
				Date d2 = pd.getUptime();
				int days = (int) (d1.getTime() - d2.getTime());
				pd.setOnlinetime((days / 1000 )+pd.getOnlinetime());
				pd.setType(DriverEnum.ATWORK_TYPE_OFF.code);
			}
		}
    	dao.updateUptimeOrOnlinetime(pd);
	}
	/**********************************************************校验方法***************************************************************/
	/**
	 * 检查用户是否允许登录
	 * @param param
	 * @return
	 */
	@ValidateRule(msg="账户状态不正确,不允许登录")
	private boolean checkUserCanLogin(String mobile){
		logger.info("检查用户是否允许登录...");
		PubDriver pd = dao.getPubDriverByPhone(mobile);
		//离职状态不允许登录提示账号不存在
		if(pd == null || DriverEnum.JOB_STATUS_DIMISSION.code.equals(pd.getJobstatus())){
			 //如果不在删除列表里就表示真的不存在
			if(dao.getPubDriverInDel(mobile) == null){
				errorResult.get().put("status", Retcode.USERNOTEXIST.code);
				errorResult.get().put("message", Retcode.USERNOTEXIST.msg);
				logger.info("用户不存在");
			}else{   //否则表示该账户被删除
				errorResult.get().put("status", Retcode.USERSIGNOFF.code);
				errorResult.get().put("message", Retcode.USERSIGNOFF.msg);
				logger.info("用户已注销");
			}
			return false;
		}
		driver.set(pd);
		logger.info("检查用户是否允许登录完成");
		return true;
	}
	
	/**
	 * 是否挤下线
	 * @param token
	 * @param uuid
	 * @return
	 */
	private boolean checkAnotherLogin(PubUserToken token,String uuid){
		//如果非首次登陆,并且uuid与数据库不匹配,则推送挤下线通知
		if(uuid != null &&  token.getUuid() != null && !token.getUuid().equals(uuid)){
			//挤下线时,要把司机状态更改为离线
			driver.get().setWorkstatus(DriverState.OFFLINE.code);
			driver.get().setUpdateTime(new Date());
			dao.updatePubDriver(driver.get());
			UserMessage uu = new UserMessage(token.getUsertoken());
			MessageUtil.sendMessage(uu);
			//挤下线时更新在线时长
			AtworkParam ap = new AtworkParam();
			ap.setType(DriverEnum.ATWORK_TYPE_OFF.code);
			updateUptimeOrOnlinetime(ap);
			return true;
		}
		return false;
	}
	
	/**
	 * 校验登录密码
	 * @param param
	 * @return
	 */
	private boolean checkLoginPass(LoginParam param){
		logger.info("检查用户密码是否正确...");
		//加密后
		String encodePass= PasswordEncoder.encode(param.getPassword());
		if(!driver.get().getUserpassword().equals(encodePass)){
			errorResult.get().put("status", Retcode.PASSWORDWRONG.code);
			errorResult.get().put("message", Retcode.PASSWORDWRONG.msg);
			logger.info("用户密码不正确");
			return false;
		}
		logger.info("检查用户密码是否正确完成");
		return true;
	}
	
	/**
	 * 检查未付结订单(出租车需要检查)
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean checkNotPayOrder(){
		PubDriver pd = driver.get();
		//网约车不检查
		if(!DriverEnum.DRIVER_TYPE_TAXI.code.equals(pd.getVehicletype() + "")) return true;
		logger.info("检查是否有未付结订单...");
		OrderListParam olp = new OrderListParam();
		olp.setType(2);
		olp.setDriverid(pd.getId());
		List<OpTaxiOrder> list = taxiOrdDao.getTaxiOrderList(olp);
		if(list != null && !list.isEmpty()){
			double totalAmount = 0;
			for(OpTaxiOrder oo : list){
				totalAmount += oo.getOrderamount();
			}
			//如果金额>=200或者未付订单数>=10则提示超限,否则提示存在
			if(totalAmount >= 200 || list.size() >= 10){
				logger.info("未付结订单超限");
				errorResult.get().put("status", Retcode.NOPAYORDEROUTOFLIMIT.code);
				errorResult.get().put("message", Retcode.NOPAYORDEROUTOFLIMIT.msg);
			}else{
				errorResult.get().put("status", Retcode.DRIVERNOPAYORDER.code);
				errorResult.get().put("message", Retcode.DRIVERNOPAYORDER.msg);
			}
			Collections.reverse(list);
			OpTaxiOrder o = list.get(0);
			logger.info("存在未付结订单:" + o.getOrderno());
			errorResult.get().put("orderno", o.getOrderno());
			errorResult.get().put("usetype", o.getUsetype());
			errorResult.get().put("ordertype", o.getOrdertype());
			return false;
		}
		logger.info("检查是否有未付结订单完成");
		return true;
	}
	
	/**
	 * 检查司机是否能够退出登录
	 * @return
	 */
	private boolean checkDriverCanLogout(){
		logger.info("检查司机是否能够退出...");
		PubDriver pd = driver.get();
		//如果是出租车司机还要校验交接班状态
		if(DriverEnum.DRIVER_TYPE_TAXI.code.equals(pd.getVehicletype()+"")){ 
			//(无对班司机 || 当班司机) && 空闲状态,不允许退出
			if((DriverEnum.PASS_WORK_STATUS_NO.code.equals(pd.getPassworkstatus()) || 
				DriverEnum.PASS_WORK_STATUS_ON.code.equals(pd.getPassworkstatus())) && 
				DriverEnum.WORK_STATUS_LEISURE.code.equals(pd.getWorkstatus())){
				errorResult.get().put("status", Retcode.DRIVERATWORK.code);
				errorResult.get().put("message", Retcode.DRIVERATWORK.msg);
				logger.info("司机登出失败,司机处于上班状态");
				return false;
			}
		//网约车司机只校验是否处于空闲
		}else{
			if(DriverEnum.WORK_STATUS_LEISURE.code.equals(pd.getWorkstatus())){
				errorResult.get().put("status", Retcode.DRIVERATWORK.code);
				errorResult.get().put("message", Retcode.DRIVERATWORK.msg);
				logger.info("司机登出失败,司机处于上班状态");
				return false;
			}
		}
		logger.info("检查司机是否能够退出完成");
		return true;
	}
	
	/**
	 * 检查司机是否有服务中订单
	 * @return
	 */
	private boolean checkInserviceOrder(){
		logger.info("检查司机是否有服务中订单...");
		//判断是否存在服务中订单(如果存在服务中订单,则切换到服务中状态而不是空闲状态)
		OrderListParam olpParam = new OrderListParam();
		olpParam.setDriverid(driver.get().getId());
		olpParam.setType(OrderListEnum.DEFAULT.state); //只取服务中订单
		boolean flag = false;
		//网约车类型检查网约车服务中订单
		if(DriverEnum.DRIVER_TYPE_CAR.code.equals(driver.get().getVehicletype()+"")){
			flag = onlineCarCheckInserviceOrder(olpParam);
		//出租车类型检查出租车服务中订单
		}else{
			flag = taxiCheckInserviceOrder(olpParam);
		}
		logger.info("检查司机是否有服务中订单完成");
		return flag;
	}
	
	/**
	 * 检查司机是否有未出行的即刻订单
	 * @return
	 */
	private boolean checkUseNowOrder(){
		logger.info("检查司机是否有未出行的即刻订单...");
		//判断是否存在即刻订单(如果存在即刻订单,不允许下班)
		OrderListParam olpParam = new OrderListParam();
		olpParam.setDriverid(driver.get().getId());
		olpParam.setType(OrderListEnum.CURRENT.state);   //只取当前订单
		boolean flag = false;
		//网约车类型检查网约车即刻单
		if(DriverEnum.DRIVER_TYPE_CAR.code.equals(driver.get().getVehicletype()+"")){
			flag = onlineCarCheckUseNowOrder(olpParam);
		//出租车类型检查出租车即刻单
		}else{
			flag = taxiCheckUseNowOrder(olpParam);
		}
		logger.info("检查司机是否有未出行的即刻订单完成");
		return flag;
	}
	
	/**
	 * 检查网约车服务中订单
	 * @param olpParam
	 * @return
	 */
	private boolean onlineCarCheckInserviceOrder(OrderListParam olpParam){
		List<OrderInfoDetail> list = ordDao.getOrderList(olpParam);
		if(list != null && !list.isEmpty()){
			errorResult.get().put("status", Retcode.ORDERNOTDONE.code);
			errorResult.get().put("message", Retcode.ORDERNOTDONE.msg);
			errorResult.get().put("orderno", list.get(0).getOrderno());
			errorResult.get().put("usetype", list.get(0).getUsetype());
			errorResult.get().put("ordertype", list.get(0).getType());
			logger.info("存在服务中订单:" + list.get(0).getOrderno());
			return false;
		}
		return true;
	}
	
	/**
	 * 检查出租车服务中订单
	 * @param olpParam
	 * @return
	 */
	private boolean taxiCheckInserviceOrder(OrderListParam olpParam){
		List<OpTaxiOrder> list = taxiOrdDao.getTaxiOrderList(olpParam);
		if(list != null && !list.isEmpty()){
			errorResult.get().put("status", Retcode.ORDERNOTDONE.code);
			errorResult.get().put("message", Retcode.ORDERNOTDONE.msg);
			errorResult.get().put("orderno", list.get(0).getOrderno());
			errorResult.get().put("usetype", list.get(0).getUsetype());
			errorResult.get().put("ordertype", list.get(0).getOrdertype());
			logger.info("存在服务中订单:" + list.get(0).getOrderno());
			return false;
		}
		return true;
	}
	
	/**
	 * 检查网约车即刻单
	 * @param olpParam
	 * @return
	 */
	private boolean onlineCarCheckUseNowOrder(OrderListParam olpParam){
		List<OrderInfoDetail> list = ordDao.getOrderList(olpParam);
		if(list != null && !list.isEmpty()){
			for(OrderInfoDetail o : list){
				if(OrderState.WAITSTART.state.equals(o.getStatus()) && o.isIsusenow()){
					errorResult.get().put("status", Retcode.HASUSENOWORDER.code);
					errorResult.get().put("message", Retcode.HASUSENOWORDER.msg);
					errorResult.get().put("orderno", o.getOrderno());
					errorResult.get().put("usetype", o.getUsetype());
					errorResult.get().put("ordertype", o.getType());
					logger.info("存在未出行的即刻订单:" + o.getOrderno());
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 检查出租车即刻单
	 * @param olpParam
	 * @return
	 */
	private boolean taxiCheckUseNowOrder(OrderListParam olpParam){
		List<OpTaxiOrder> list = taxiOrdDao.getTaxiOrderList(olpParam);
		if(list != null && !list.isEmpty()){
			for(OpTaxiOrder o : list){
				if(OrderState.WAITSTART.state.equals(o.getOrderstatus()) && o.isIsusenow()){
					errorResult.get().put("status", Retcode.HASUSENOWORDER.code);
					errorResult.get().put("message", Retcode.HASUSENOWORDER.msg);
					errorResult.get().put("orderno", o.getOrderno());
					errorResult.get().put("usetype", o.getUsetype());
					errorResult.get().put("ordertype", o.getOrdertype());
					logger.info("存在未出行的即刻订单:" + o.getOrderno());
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 校验上下班类型
	 * @param type
	 * @return
	 */
	@ValidateRule(msg="上下班类型不正确")
	private boolean checkAtWorkType(String type){
		return DriverEnum.ATWORK_TYPE_ON.code.equals(type) ? true :
					DriverEnum.ATWORK_TYPE_OFF.code.equals(type) ? true : false;
	}
	
	/**
	 * 检查司机是否可以上下班
	 * @param type t
	 * @return
	 */
	@ValidateRule(msg="司机状态不正确,不允许上下班")
	private boolean checkCanAtWork(String type){
		//如果司机是未绑定状态,不能上班
		if(DriverEnum.ATWORK_TYPE_ON.code.equals(type) && 
			DriverEnum.BOUND_STATE_OFF.code.equals(driver.get().getBoundstate()+"")){
			errorResult.get().put("status", Retcode.DRIVERNOCAR.code);
			errorResult.get().put("message", Retcode.DRIVERNOCAR.msg);
			return false;
		//未排班,歇班司机,不能上班
		}else if(DriverEnum.ATWORK_TYPE_ON.code.equals(type) && 
			(DriverEnum.PASS_WORK_STATUS_OFF.code.equals(driver.get().getPassworkstatus()) ||
			DriverEnum.PASS_WORK_STATUS_NOPLAN.code.equals(driver.get().getPassworkstatus()))){
            errorResult.get().put("status", Retcode.DRIVER_AT_NO_WORK.code);
            errorResult.get().put("message", Retcode.DRIVER_AT_NO_WORK.msg);
            return false;
		//下班检查是否存在服务中订单或未出行的即刻订单
		}else if(DriverEnum.DRIVER_TYPE_TAXI.code.equals(driver.get().getVehicletype() + "") && 
				DriverEnum.ATWORK_TYPE_OFF.code.equals(type)){
			if(!checkInserviceOrder() || !checkUseNowOrder()){
				return false;
			}
        }
        return true;
	}
	
	
	/**
	 * 校验订单号
	 * @param orderno
	 * @return
	 */
	@ValidateRule(msg="订单号格式不正确")
	private boolean checkOrderNO(String orderno){
		if(orderno == null || orderno.trim().isEmpty()) return false;
		//判断是否传入了用车类型和订单类型
		if(usetype.get() == null || ordertype.get() == null) {
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", "订单信息不足");
			return false;
		}
		//根据平台不同获取不同平台订单
		if(OrderEnum.USETYPE_PERSONAL.equals(usetype.get())){
			order.set(ordDao.getOpOrder(orderno));
		}else{
			order.set(ordDao.getOrgOrder(orderno));
		}
		if(order.get() == null){
			errorResult.get().put("status", Retcode.ORDERNOTEXIT.code);
			errorResult.get().put("message", Retcode.ORDERNOTEXIT.msg);
		}
		return order.get() != null;
	}
	
	/**
	 * 校验系统类型
	 * @param orderno
	 * @return
	 */
	@ValidateRule(msg="系统类型不正确")
	private boolean checkSystemType(String systemtype){
		return SystemType.ANDROID.code.equals(systemtype) ? true : 
					SystemType.IOS.code.equals(systemtype) ? true : false;
	}

	/**
	 * 校验坐标值
	 * @param latOrLng
	 * @return
	 */
	@ValidateRule(msg="坐标值不正确")
	private boolean checkLatLng(double latOrLng){
		if(latOrLng <= 0){
			logger.info("==========================================");
			logger.info("上传的坐标不正确:                                                         " + latOrLng);
			logger.info("==========================================");
			return false;
		}
		return true;
	}
	
	/**
	 * 校验密码类型
	 * @param latOrLng
	 * @return
	 */
	@ValidateRule(msg="密码类型不正确")
	private boolean checkPasswordType(String type){
		return DriverEnum.PASSWORD_TYPE_LOGINPASS.code.equals(type) ? true : 
					DriverEnum.PASSWORD_TYPE_WITHDRAWPASS.code.equals(type) ? true : false;
	}
	
	/**
	 * 校验申请发送短信的类型
	 * @param type
	 * @return
	 */
	@ValidateRule(msg="申请短信类型不正确")
	private boolean checkSMSType(String type){
		return DriverEnum.SMS_TYPE_LOGIN.code.equals(type) ? true : 
					DriverEnum.SMS_TYPE_CHANGEPASS.code.equals(type) ? true : false;
	}
	
	/**
	 * 校验登录类型
	 * @return
	 */
	@ValidateRule(msg="登录类型不正确")
	private boolean checkLoginType(int logintype){
		return DriverEnum.LOGIN_TYPE_PASS.code.equals(logintype + "") ? true : 
					DriverEnum.LOGIN_TYPE_SMS.code.equals(logintype + "") ? true : false;
	}
	
    /**
     * 校验usetype
     * @param usetype
     * @return
     */
    @ValidateRule(msg="用车类型错误")
    private boolean checkUseType(String usetype){
    	this.usetype.set(OrderEnum.getUseType(usetype));
    	return this.usetype.get() != null;
    }
    
    /**
     * 校验ordertype
     * @param ordertype
     * @return
     */
    @ValidateRule(msg="订单类型错误")
    private boolean checkOrderType(String ordertype){
    	this.ordertype.set(OrderEnum.getOrderType(ordertype));
    	return this.ordertype.get() != null;
    }
}

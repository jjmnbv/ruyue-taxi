package com.szyciov.driver.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.driver.entity.DriverInfo;
import com.szyciov.driver.entity.PubDriverLoginLog;
import com.szyciov.driver.mapper.AccountMapper;
import com.szyciov.driver.param.LoginParam;
import com.szyciov.driver.param.SendSMSParam;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubDrivertrack;
import com.szyciov.entity.PubJpushRegidlog;
import com.szyciov.entity.PubSmsToken;
import com.szyciov.entity.PubUserToken;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.entity.PubDriverAccount;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.PeUser;
import com.szyciov.org.entity.OrgUser;

@Repository("AccountDao")
public class AccountDao {
	public AccountDao() {
	}
	
	private AccountMapper mapper;

	@Resource
	public void setMapper(AccountMapper mapper) {
		this.mapper = mapper;
	}
	
	/**
	 * 根据账号密码获取司机
	 * @param param
	 * @return {@link PubDriver}
	 * @see {@link LoginParam}
	 */
	public PubDriver getPubDriverByPass(LoginParam param){
		return mapper.getPubDriverByPass(param);
	}
	
	/**
	 * 根据id获取司机
	 * @param id
	 * @return {@link PubDriver}
	 */
	public PubDriver getPubDriverById(String id){
		return mapper.getPubDriverById(id);
	}
	
	/**
	 * 根据手机号获取司机
	 * @param phone
	 * @return {@link PubDriver}
	 */
	public PubDriver getPubDriverByPhone(String phone){
		return mapper.getPubDriverByPhone(phone);
	}
	
	/**
	 * 查司机账号是否被删除
	 * @param phone
	 * @return
	 */
	public PubDriver getPubDriverInDel(String phone){
		return mapper.getPubDriverInDel(phone);
	}
	
	/**
	 * 根据token获取司机
	 * @param token
	 * @return {@link PubDriver}
	 */
	public PubDriver getPubDriverByToken(String token){
		return mapper.getPubDriverByToken(token);
	}
	
	/**
	 * 根据ID获取司机司机信息(格式化后)
	 * @param param
	 * @return {@link DriverInfo}
	 */
	public DriverInfo getDriverInfoById(String id){
		return mapper.getDriverInfoById(id);
	}
	
	/**
	 * 根据司机手机号获取短信验证码
	 * @param param
	 * @return {@link PubSmsToken}
	 * @see {@linkplain SendSMSParam}
	 */
	public PubSmsToken getPubSmsTokenByUserPhone(SendSMSParam param){
		return mapper.getPubSmsTokenByUserPhone(param);
	}
	
	/**
	 * 根据司机获取PubUserToken实体
	 * @param driver
	 * @return {@link PubUserToken}
	 * @see {@link PubDriver}
	 */
	public PubUserToken getUserTokenByUser(PubDriver driver){
		return mapper.getUserTokenByUser(driver);
	}
	
	/**
	 * 根据token获取PubUserToken实体
	 * @param userToken
	 * @return {@link PubUserToken}
	 */
	public PubUserToken getUserTokenByToken(String userToken){
		return mapper.getUserTokenByToken(userToken);
	}
	
	/**
	 * 根据id获取最近一次登录日志
	 * @param userid
	 * @return {@link PubDriverLoginLog}
	 */
	public PubDriverLoginLog getPubDriverLoginLog(String userid){
		return mapper.getPubDriverLoginLog(userid);
	}
	
	/**
	 * 获取公共信息
	 * @param param
	 * @return {@link PubDictionary}
	 */
	public List<PubDictionary> getCommonInfo(){
		return mapper.getCommonInfo();
	}

	/**
	 * 根据ID获取租赁公司
	 * @param companyId
	 * @return
	 */
	public LeLeasescompany getCompanyById(String companyId){
		return mapper.getCompanyById(companyId);
	}
	
	/**
	 * 保存登录日志
	 * @param log
	 * @see {@link PubDriverLoginLog}
	 */
	public void saveLoginLog(PubDriverLoginLog log){
		mapper.saveLoginLog(log);
	}
	
	/**
	 * 新增或更新token
	 * @param token
	 * @see {@link PubUserToken}
	 */
	public void saveOrUpdateUserToken(PubUserToken token){
		mapper.saveOrUpdateUserToken(token);
	}
	
	/**
	 * 新增或更新短信验证码
	 * @param smsToken
	 * @see {@link PubSmsToken}
	 */
	public void saveOrUpdateSmsToken(PubSmsToken smsToken){
		mapper.saveOrUpdateSmsToken(smsToken);
	}

	/**
	 * 更新司机信息
	 * @param driver
	 * @see {@link PubDriver}
	 */
	public void updatePubDriver(PubDriver driver){
		mapper.updatePubDriver(driver);
	}
	
	/**
	 * 更新司机坐标
	 * @param driver
	 * @see {@link PubDriver}
	 */
	public void updateLocation(PubDriver driver){
		mapper.updateLocation(driver);
	}
	
	/**
	 * 获取司机钱包账户
	 * @param driver
	 * @see {@link PubDriver}
	 */
	PubDriverAccount getDriverAccount(PubDriver driver){
		return mapper.getDriverAccount(driver);
	}
	
	/**
	 * 保存司机轨迹
	 * @param drivertrack
	 * @return
	 */
	public boolean saveDriverTrack(PubDrivertrack drivertrack){
		mapper.saveDriverTrack(drivertrack);
		return true;
	}
	
	/**
	 * 获取运管平台信息
	 * @return
	 */
	public OpPlatformInfo getPlatformInfo(){
		return mapper.getOpPlatform();
	}
	
	/**
	 * 获取个人用户
	 * @param userId
	 * @return
	 */
	public PeUser getPeUserById(String userid){
		return mapper.getPeUserById(userid);
	}
	
	/**
	 * 获取机构用户
	 * @param userId
	 * @return
	 */
	public OrgUser getOrgUserById(String userid){
		return mapper.getOrgUserById(userid);
	}
	
	/**
	 * 更新司机在线时长
	 * @param pubDriver
	 */
	public void updateUptimeOrOnlinetime(PubDriver pubDriver){
		mapper.updateUptimeOrOnlinetime(pubDriver);
	}
	
	/**
	 * 保存极光推送ID
	 * @param regidlog
	 */
	public void saveRegID(PubJpushRegidlog regidlog){
		mapper.saveRegID(regidlog);
	}
}


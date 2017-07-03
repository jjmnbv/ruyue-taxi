package com.szyciov.driver.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.szyciov.driver.entity.DriverInfo;
import com.szyciov.driver.entity.PubDriverLoginLog;
import com.szyciov.driver.param.AtworkParam;
import com.szyciov.driver.param.ChangePasswordParam;
import com.szyciov.driver.param.LoginParam;
import com.szyciov.driver.param.SendSMSParam;
import com.szyciov.driver.param.UploadOrdinatesParam;
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

public interface AccountMapper {
	/**
	 * 根据账户密码查询司机
	 * @param param
	 * @return
	 */
	PubDriver getPubDriverByPass(LoginParam param);
	/**
	 * 在删除列表中查询司机(查是重复账户还是账户不存在)
	 * @param phone
	 * @return
	 */
	PubDriver getPubDriverInDel(@Param("phone") String phone);
	/**
	 * 根据ID查询司机
	 * @param id
	 * @return
	 */
	PubDriver getPubDriverById(@Param("id") String id);
	/**
	 * 根据手机号查询司机
	 * @param phone
	 * @return
	 */
	PubDriver getPubDriverByPhone(@Param("phone") String phone);
	/**
	 * 根据token查询司机
	 * @param usertoken
	 * @return
	 */
	PubDriver getPubDriverByToken(@Param("usertoken") String usertoken);
	/**
	 * 查询短信验证码
	 * @param param
	 * @return
	 */
	PubSmsToken getPubSmsTokenByUserPhone(SendSMSParam param);
	/**
	 * 查询司机信息(app显示,格式化后的简要信息)
	 * @param userid
	 * @return
	 */
	DriverInfo getDriverInfoById(@Param("userid") String userid);
	/**
	 * 根据ID查询个人用户
	 * @param userid
	 * @return
	 */
	PeUser getPeUserById(@Param("userid") String userid);
	/**
	 * 根据ID查询机构用户
	 * @param userid
	 * @return
	 */
	OrgUser getOrgUserById(@Param("userid") String userid);
	/**
	 * 查询司机钱包账户
	 * @param driver
	 * @return
	 */
	PubDriverAccount getDriverAccount(PubDriver driver);
	/**
	 * 根据token返回token对象
	 * @param usertoken
	 * @return
	 */
	PubUserToken getUserTokenByToken(@Param("usertoken") String usertoken);
	/**
	 * 根据user返回token对象
	 * @param driver
	 * @return
	 */
	PubUserToken getUserTokenByUser(PubDriver driver);
	/**
	 * 获取司机登录日志
	 * @param userid
	 * @return
	 */
	PubDriverLoginLog getPubDriverLoginLog(@Param("userid") String userid);
	/**
	 * 获取公共信息
	 * @return
	 */
	List<PubDictionary> getCommonInfo();
	/**
	 * 根据ID获取租赁公司信息
	 * @param companyid
	 * @return
	 */
	LeLeasescompany getCompanyById(@Param("companyid") String companyid);
	/**
	 * 获取运管平台信息
	 * @return
	 */
	OpPlatformInfo getOpPlatform();
	/**
	 * 验证原密码
	 * @param param
	 * @return
	 */
	boolean authOldPass(ChangePasswordParam param);
	/**
	 * 更改密码
	 * @param param
	 */
	void changePassword(ChangePasswordParam param);
	/**
	 * 保存登录日志
	 * @param log
	 */
	void saveLoginLog(PubDriverLoginLog log);
	/**
	 * 保存或更新短信验证码
	 * @param sms
	 */
	void saveOrUpdateSmsToken(PubSmsToken sms);
	/**
	 * 保存或更新token
	 * @param token
	 */
	void saveOrUpdateUserToken(PubUserToken token);
	/**
	 * 改变司机工作状态
	 * @param driver
	 */
	void changeWorkStatus(AtworkParam driver);
	/**
	 * 上传司机坐标
	 * @param param
	 */
	void uploadOrdinates(UploadOrdinatesParam param);
	/**
	 * 更新司机信息
	 * @param driver
	 */
	void updatePubDriver(PubDriver driver);
	/**
	 * 更新坐标点
	 * @param driver
	 */
	void updateLocation(PubDriver driver);
	/**
	 * 保存坐标轨迹
	 * @param drivertrack
	 */
	void saveDriverTrack(PubDrivertrack drivertrack);
	/**
	 * 更新司机在线时长
	 * @param pubDriver
	 */
	void updateUptimeOrOnlinetime(PubDriver pubDriver);
	/**
	 * 保存极光推送ID
	 * @param regidlog
	 */
	void saveRegID(PubJpushRegidlog regidlog);
}


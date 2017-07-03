package com.szyciov.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.szyciov.enums.WindowVisualEnum;
import com.szyciov.util.AppMessageUtil;
import com.szyciov.util.PushObjFactory;
import com.szyciov.util.SMMessageUtil;
import com.szyciov.util.SMSTempPropertyConfigurer;
import com.szyciov.util.UserTokenManager;

import cn.jpush.api.push.model.PushPayload;

/**
 * 人员消息对象
 * @author admin
 *
 */
public class UserMessage extends BaseMessage {
	
	public static final String ADDUSER = "添加人员";
	
	public static final String DELETEUSER = "删除人员";
	
	public static final String CHANGEPASSWORD = "更改密码";
	
	public static final String PUSHDOWN = "挤下线";
	
	public static final String GETSMSCODE = "获取验证码";
	
	public static final String CHANGEPHONE = "机构联系方式变更";
	
	public static final String UNBINDCAR = "解绑车辆";
	
	public static final String DELETEDRIVER = "删除司机";
	
	public static final String CASHOK = "确认提现";
	
	public static final String CASHREJECT = "拒绝提现";
	
	public static final String RESETCASHPWD = "重置提现密码";
	
	public static final String SHIFTOFF = "发起交班";
	
	public static final String SHIFTON = "发起接班";
	
	public static final String SHIFTCANCEL = "取消交班";
	
	private List<String> userids;
	
	private String content;
	
	private String messagetype;

	private String oldusertoken;
	
	private String drivername;
	
	private String applytype;
	
	private long applystarttime;
	
	private long applyendtime;
	
	private boolean shifton;
	/**
	 * 司机电话
	 */
	private String driverphone;
	/**
	 * 客服电话
	 */
	private String servicephone;
	/**
	 * 其他消息的信息
	 */
	
	//接受注入参数用
	public UserMessage(){}

	public UserMessage(List<String> userids,String content,String messagetype){
		this.userids = userids;
		this.content = content;
		this.messagetype = messagetype;		
	}
	
	/**
	 * 挤下线用
	 * @param oldusertoken
	 */
	public UserMessage(String oldusertoken){
		this.oldusertoken = oldusertoken;
		this.messagetype = PUSHDOWN;
	}
	
	/**
	 * 交接班用
	 * @param userids
	 * @param content
	 * @param messagetype
	 * @param applytype
	 * @param applyendtime
	 */
	public UserMessage(List<String> userids,String messagetype,String drivername,String applytype,long applystarttime,long applyendtime){
		this.userids = userids;
		this.drivername = drivername;
		this.messagetype = messagetype;
		this.applystarttime = applystarttime;
		this.applyendtime = applyendtime;
		this.applytype = applytype;
	}
	
	/**
	 * 交接班用
	 * @param userids
	 * @param content
	 * @param messagetype
	 * @param shifton
	 */
	public UserMessage(List<String> userids,String messagetype,boolean shifton){
		this.userids = userids;
		this.messagetype = messagetype;
		this.shifton = shifton;
	}
	
	@Override
	public void send() {
		if(ADDUSER.equalsIgnoreCase(messagetype)){
			//添加人员
			SMMessageUtil.send(userids, content);
		}else if(DELETEUSER.equalsIgnoreCase(messagetype)){
			//删除人员
			SMMessageUtil.send(userids, content);
		}else if(CHANGEPASSWORD.equalsIgnoreCase(messagetype)){
			//更改密码
			SMMessageUtil.send(userids, content);
		}else if(PUSHDOWN.equalsIgnoreCase(messagetype)){
			//挤下线 需要其他的参数需要另外定义
			try {
				pushDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(GETSMSCODE.equalsIgnoreCase(messagetype)){
			//获取验证码
			getSMSCode(userids, content);
//			System.out.println("短信已发出："+content);
		}else if(CHANGEPHONE.equalsIgnoreCase(messagetype)){
			SMMessageUtil.send(userids, content);
		}else if(UNBINDCAR.equalsIgnoreCase(messagetype)){
			//解绑车辆
			unBindCar();
		}else if(DELETEDRIVER.equalsIgnoreCase(messagetype)){
			//删除司机
			deleteDriver();
		}else if(CASHOK.equalsIgnoreCase(messagetype)){
			//确认提现
			cashOk();
		}else if(CASHREJECT.equalsIgnoreCase(messagetype)){
			//拒绝提现
			cashReject();
		}else if(RESETCASHPWD.equalsIgnoreCase(messagetype)){
			//重置提现密码
			resetCashPwd();
		}else if(SHIFTOFF.equalsIgnoreCase(messagetype)){
			shiftOFF();
		}else if (SHIFTON.equalsIgnoreCase(messagetype)) {
			shiftON();
		}else if (SHIFTCANCEL.equalsIgnoreCase(messagetype)) {
			shiftCancel();
		}
	}
	
	/**
	 * 发起交班推送给接班司机
	 */
	private void shiftOFF(){
		content = "您的对班司机{name}{phone}发起交班，请尽快完成接班。如有疑问，请联系：{servicephone}"
				.replace("{name}", drivername)
				.replace("phone", driverphone)
				.replace("servicephone",servicephone);
		String smscontent = SMSTempPropertyConfigurer.getSMSTemplate(
				"com.szyciov.driver.service.accountservice.shiftworkoff", 
				drivername,driverphone,servicephone);
		PushPayload pushload4ios = PushObjFactory.creatShiftObj4IOS(content,drivername,applytype,applystarttime,applyendtime,PushObjFactory.Hint_SHIFT_PROCESSING,userids);
		PushPayload pushload4android = PushObjFactory.creatShiftObj4Android(content,drivername,applytype,applystarttime,applyendtime,PushObjFactory.Hint_SHIFT_PROCESSING,userids);
		AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_DRIVER);
		SMMessageUtil.send(userids, smscontent);
	}
	
	/**
	 * 发起接班推送给交班司机
	 */
	private void shiftON(){
		//content = "";
		String type = shifton ? PushObjFactory.HINT_SHIFT_PROCESSED : PushObjFactory.HINT_SHIFT_FAILED;
		PushPayload pushload4ios = PushObjFactory.creatHintObj4IOS(content,type,userids,null);
		PushPayload pushload4android = PushObjFactory.creatHintObj4Android(content,type,userids,null);
		AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_DRIVER);
	}
	
	/**
	 * 发起取消交班推送给接班司机
	 */
	private void shiftCancel(){
		String type = PushObjFactory.HINT_SHIFT_CANCEL;
		PushPayload pushload4ios = PushObjFactory.creatHintObj4IOS(content,type,userids,null);
		PushPayload pushload4android = PushObjFactory.creatHintObj4Android(content,type,userids,null);
		AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_DRIVER);
	}
	
	/**
	 * 重置提现密码
	 */
	private void resetCashPwd() {
		SMMessageUtil.send(userids, content);
	}

	/**
	 * 拒绝提现
	 */
	private void cashReject() {
		SMMessageUtil.send(userids, content);
	}

	/**
	 * 确认提现
	 */
	private void cashOk() {
		SMMessageUtil.send(userids, content);
	}

	/**
	 * 删除司机
	 */
	private void deleteDriver(){
		PushPayload pushload4ios = PushObjFactory.createDeleteDriver4IOS(userids,content);
		PushPayload pushload4android = PushObjFactory.createDeleteDriver4Android(userids,content);
		AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_DRIVER);
	}
	/**
	 * 解绑车辆
	 */
	private void unBindCar(){
		PushPayload pushload4ios = PushObjFactory.createUnBindCarObj4IOS(userids,content,WindowVisualEnum.TANCHUANG.code);
		PushPayload pushload4android = PushObjFactory.createUnBindCarObj4Android(userids,content,WindowVisualEnum.TANCHUANG.code);
		AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_DRIVER);
	}

	/**
	 * 挤下线推送
	 * @throws Exception
	 */
	private void pushDown() throws Exception {
		if(StringUtils.isBlank(oldusertoken)){
			return ;
		}
		List<String> userids = new ArrayList<String>();
		String type = UserTokenManager.getUserTypeFromToken(oldusertoken);
		String account = UserTokenManager.getUserNameFromToken(oldusertoken, type);
		userids.add(account);
		String msgcontent = "你的账号已在其他地方登陆";
		List<String> tag_ands = new ArrayList<String>(); 
		byte[] usertokencode = Base64.decodeBase64(oldusertoken);
		String decodetoken = new String(usertokencode);
//		System.out.println(decodetoken.substring(32, 49));
		tag_ands.add(decodetoken.substring(32, 49));
		if(UserTokenManager.DRIVERUSER.equalsIgnoreCase(type)){
			//司机端
			PushPayload pushload4ios = PushObjFactory.createOfflineObj4IOS(msgcontent,userids,PushObjFactory.OFFLINE_DRIVER,tag_ands);
			PushPayload pushload4android = PushObjFactory.createOfflineObj4Android(msgcontent,userids,PushObjFactory.OFFLINE_DRIVER,tag_ands);
			AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_DRIVER);
		}else{
			//乘客端
			if(UserTokenManager.ORGUSERTYPE.equalsIgnoreCase(type)){
				//org用户
				tag_ands.add("0");
				PushPayload pushload4ios = PushObjFactory.createOfflineObj4IOS(msgcontent,userids,PushObjFactory.OFFLINE_ORG,tag_ands);
				PushPayload pushload4andorid = PushObjFactory.createOfflineObj4Android(msgcontent,userids,PushObjFactory.OFFLINE_ORG,tag_ands);
				AppMessageUtil.send(pushload4ios,pushload4andorid,AppMessageUtil.APPTYPE_PASSENGER);
			}else if(UserTokenManager.PERSONNALUSER.equalsIgnoreCase(type)){
				//个人用户
				tag_ands.add("1");
				PushPayload pushload4ios = PushObjFactory.createOfflineObj4IOS(msgcontent,userids,PushObjFactory.OFFLINE_PERSON,tag_ands);
				PushPayload pushload4android = PushObjFactory.createOfflineObj4Android(msgcontent,userids,PushObjFactory.OFFLINE_PERSON,tag_ands);
				AppMessageUtil.send(pushload4ios,pushload4android,AppMessageUtil.APPTYPE_PASSENGER);
			}
		}
	}

	public List<String> getUserids() {
		return userids;
	}

	public void setUserids(List<String> userids) {
		this.userids = userids;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}
	
	
	/**
	 * 获取验证码
	 * @param cont 
	 * @param phones 
	 */
	private void getSMSCode(List<String> phones, String cont){
		SMMessageUtil.send(phones, cont);
	}

	public String getOldusertoken() {
		return oldusertoken;
	}

	public void setOldusertoken(String oldusertoken) {
		this.oldusertoken = oldusertoken;
	}

	/**  
	 * 获取drivername  
	 * @return drivername drivername  
	 */
	public String getDrivername() {
		return drivername;
	}
	

	/**  
	 * 设置drivername  
	 * @param drivername drivername  
	 */
	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}
	
	/**  
	 * 获取applytype  
	 * @return applytype applytype  
	 */
	public String getApplytype() {
		return applytype;
	}
	
	/**  
	 * 设置applytype  
	 * @param applytype applytype  
	 */
	public void setApplytype(String applytype) {
		this.applytype = applytype;
	}

	/**  
	 * 获取applyendtime  
	 * @return applyendtime applyendtime  
	 */
	public long getApplyendtime() {
		return applyendtime;
	}

	/**  
	 * 设置applyendtime  
	 * @param applyendtime applyendtime  
	 */
	public void setApplyendtime(long applyendtime) {
		this.applyendtime = applyendtime;
	}

	/**  
	 * 获取applystarttime  
	 * @return applystarttime applystarttime  
	 */
	public long getApplystarttime() {
		return applystarttime;
	}

	/**  
	 * 设置applystarttime  
	 * @param applystarttime applystarttime  
	 */
	public void setApplystarttime(long applystarttime) {
		this.applystarttime = applystarttime;
	}

	/**  
	 * 获取shifton  
	 * @return shifton shifton  
	 */
	public boolean isShifton() {
		return shifton;
	}
	
	/**  
	 * 设置shifton  
	 * @param shifton shifton  
	 */
	public void setShifton(boolean shifton) {
		this.shifton = shifton;
	}

	/**  
	 * 获取司机电话  
	 * @return driverphone 司机电话  
	 */
	public String getDriverphone() {
		return driverphone;
	}
	

	/**  
	 * 设置司机电话  
	 * @param driverphone 司机电话  
	 */
	public void setDriverphone(String driverphone) {
		this.driverphone = driverphone;
	}
	

	/**  
	 * 获取客服电话  
	 * @return servicephone 客服电话  
	 */
	public String getServicephone() {
		return servicephone;
	}
	
	/**  
	 * 设置客服电话  
	 * @param servicephone 客服电话  
	 */
	public void setServicephone(String servicephone) {
		this.servicephone = servicephone;
	}
	
}

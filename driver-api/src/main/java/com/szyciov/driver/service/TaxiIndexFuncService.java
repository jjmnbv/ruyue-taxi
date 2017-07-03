package com.szyciov.driver.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.annotation.ValidateRule;
import com.szyciov.driver.base.BaseService;
import com.szyciov.driver.dao.TaxiIndexFuncDao;
import com.szyciov.driver.dao.TaxiOrderFuncDao;
import com.szyciov.driver.enums.DriverState;
import com.szyciov.driver.enums.OrderListEnum;
import com.szyciov.driver.param.BaseParam;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.driver.param.ShiftWorkParam;
import com.szyciov.dto.driverShiftManagent.PendingSaveDto;
import com.szyciov.dto.driverShiftManagent.ProcessedSaveDto;
import com.szyciov.entity.PeDrivershiftPending;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.PeDrivershiftEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.lease.entity.LeShiftRule;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.OpShiftRules;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.passenger.util.MessageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
  * @ClassName TaxiIndexFuncService
  * @author Efy Shu
  * @Description 出租车首页功能Service
  * @date 2017年3月20日 09:47:20
  */ 
@Service("TaxiIndexFuncService")
public class TaxiIndexFuncService extends BaseService{
	/**
	  *依赖
	  */
	private TaxiIndexFuncDao taxiindexfuncdao;
	private TaxiOrderFuncDao taxiorderfuncdao;
	/**
	  *依赖注入
	  */
	@Resource(name="TaxiIndexFuncDao")
	public void setTaxiIndexFuncDao(TaxiIndexFuncDao taxiindexfuncdao){
		this.taxiindexfuncdao=taxiindexfuncdao;
	}
	@Resource(name="TaxiOrderFuncDao")
	public void setTaxiOrderFuncDao(TaxiOrderFuncDao taxiorderfuncdao){
		this.taxiorderfuncdao=taxiorderfuncdao;
	}

	/**
	 * 获取对班司机列表
	 * @param param
	 * @return
	 */
	public JSONObject getWorkmates(ShiftWorkParam param){
		String[] require = new String[]{"hasShiftRuleSign","shiftOFFSign","hasWaitStartOrder"};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		//保存司机ID,方便日志记录
		param.setDriverid(driver.get().getId());
		JSONObject result = doGetWorkmates(param);
		
		return result;
	}
	
	/**
	 * 交班申请
	 * @param param
	 * @return
	 */
	public JSONObject shiftWorkOFF(ShiftWorkParam param){
		String[] require = new String[]{
				"hasWaitStartOrder","shiftOFFSign","hasShiftRuleSign"
			};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		//保存司机ID,方便日志记录
		param.setDriverid(driver.get().getId());
		JSONObject result = doShiftWorkOFF(param);

		return result;
	}
	
	/**
	 * 接班申请
	 * @param param
	 * @return
	 */
	public JSONObject shiftWorkON(ShiftWorkParam param){
		String[] require = new String[]{"shiftONSign"};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		//保存司机ID,方便日志记录
		param.setDriverid(driver.get().getId());
		JSONObject result = new JSONObject();
		if(!doShiftWorkON(param)){ //接班成功,改变司机状态
			return errorResult.get();
		}
		if(param.isConfirm() && DriverEnum.SHIFT_TYPE_PERSON.code.equals(errorResult.get().getString("applytype"))){
			result.put("message", "今日由您当班，请尽快打卡上班，努力加油哦！");
		}else if (param.isConfirm() && DriverEnum.SHIFT_TYPE_SYSTEM.code.equals(errorResult.get().getString("applytype"))) {
			result.put("message", "客服已指派您接班，请尽快打卡上班，努力加油哦！");
		}
		return result;
	}
	
	/**
	 * 取消交班申请
	 * @return
	 */
	public JSONObject cancelShiftWork(ShiftWorkParam param){
		String[] require = new String[]{"cancelShiftSign"};
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		//保存司机ID,方便日志记录
		param.setDriverid(driver.get().getId());
		JSONObject result = new JSONObject();
		PeDrivershiftPending pending = getShiftWorkApply(driver.get());
		//未找到交班申请
		if(pending == null) {
			logger.error("取消交班失败,未找到交班申请");
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", "取消交班失败,未找到交班申请");
			
			return errorResult.get();
		}
		PubDriver receiveDriver = getReceivedDriver(param);
		//主动取消交班申请
		if(!doShiftWorkCancel(param,pending.getId())){
			return errorResult.get();
		}
		//发送取消交班通知给接班司机
		sendShiftCancelMessage(receiveDriver);
		return result;
	}
	
	/**
	 * 是否存在交接班申请
	 * @param param
	 * @return
	 */
	public JSONObject hasShiftWorkApply(ShiftWorkParam param){
		String[] require = new String[]{ };
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		//保存司机ID,方便日志记录
		param.setDriverid(driver.get().getId());
		JSONObject result = doHasShiftWorkApply(param);

		return result;
	}
	
	/**
	 * 心跳包
	 * @return
	 */
	public JSONObject heartPack(BaseParam param){
		String[] require = new String[]{ };
		if(!checkeParam(param,getExceptElement(param, require))) {
			return errorResult.get();
		}
		driver.get().setHeartbeattime(new Date());
		accdao.updatePubDriver(driver.get());
		JSONObject result = new JSONObject();

		return result;
	}
	
	/**********************************************************内部方法***************************************************************/
	/**
	 * 获取对班司机列表逻辑
	 * @param param
	 * @return
	 */
	private JSONObject doGetWorkmates(ShiftWorkParam param){
		logger.info("获取对班司机列表...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		JSONObject result = new JSONObject();
		List<PubDriver> list = taxiindexfuncdao.getWorkmates(driver.get());
		//日志输出
		StringBuffer sb = new StringBuffer();
		JSONObject pdd = new JSONObject();
		JSONArray arrlist = new JSONArray();
		for(PubDriver pd : list) {
			sb.append(pd.getName()).append(",");
			pdd.put("name", pd.getName());
			pdd.put("mobile", pd.getPhone());
			arrlist.add(pdd);
		}
		logger.info("{} 获取到的对班司机 {}",driver.get().getName(),sb.toString());
		//拼装返回
		result.put("count", arrlist.size());
		result.put("list", arrlist);
		logger.info("获取对班司机列表完成");
		return result;
	}
	
	/**
	 * 检查是否存在交接班申请逻辑
	 * @param param
	 * @return
	 */
	private JSONObject doHasShiftWorkApply(ShiftWorkParam param){
		logger.info("检查是否存在交接班申请...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		JSONObject result = new JSONObject();
		//非交接班司机直接返回false
		if(!DriverState.SHIFTING_SEND.code.equals(driver.get().getPassworkstatus()) &&
			!DriverState.SHIFTING_TAKE.code.equals(driver.get().getPassworkstatus())){
			logger.info("当前司机非交接班司机");
			result.put("applyalive", false);
			result.put("applyinfo", new JSONObject());
			return result;
		}
		param.setName(driver.get().getName());
		param.setMobile(driver.get().getPhone());
		PubDriver sendDriver = getSendedDriver(param);
		if(sendDriver == null) return errorResult.get();
		PeDrivershiftPending pending = getShiftWorkApply(sendDriver);
		
		if(pending == null){
			result.put("applyalive", false);
			result.put("applyinfo", new JSONObject());
		}else if (pending.getOvertimetime().before(new Date())) {
			PubDriver receiveDriver = getReceivedDriver(param);
			if(receiveDriver == null) return errorResult.get();
			//查询到已超时申请要主动取消
			doShiftWorkCancel(param,pending.getId());
			//如果当前司机就是发起交班的司机,返回交班失败
			if(pending.getDriverid().equals(driver.get().getId())){
				result.put("status", Retcode.SHIFTOFFFAILED.code);
				result.put("message", Retcode.SHIFTOFFFAILED.msg);
				result.put("snddrivername", receiveDriver.getName());
				result.put("snddriverphone", receiveDriver.getPhone());
				
			}else{
				result.put("applyalive", false);
				result.put("applyinfo", new JSONObject());
			}
		}else{
			PubDriver receiveDriver = getReceivedDriver(param);
			if(receiveDriver == null) return errorResult.get();
			JSONObject applyinfo = new JSONObject();
			applyinfo.put("applytype", pending.getRelievedtype());
			applyinfo.put("subtime", pending.getApplytime());
			applyinfo.put("endtime", pending.getOvertimetime());
			applyinfo.put("fstdrivername", sendDriver.getName());
			applyinfo.put("fstdriverphone", sendDriver.getPhone());
			applyinfo.put("snddrivername", receiveDriver.getName());
			applyinfo.put("snddriverphone", receiveDriver.getPhone());
			result.put("applyalive", true);
			result.put("applyinfo", applyinfo);
		}
		logger.info("检查是否存在交接班申请完成");
		return result;
	}
	
	/**
	 * 申请交班逻辑
	 * @param param
	 * @return
	 */
	private JSONObject doShiftWorkOFF(ShiftWorkParam param) {
		logger.info("申请交班开始...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		JSONObject result = new JSONObject();
		//如果当前司机已经处于交班中状态,则直接返回存在的交班申请
		if(DriverState.SHIFTING_SEND.code.equals(driver.get().getPassworkstatus())){
			PeDrivershiftPending pending = getShiftWorkApply(driver.get());
			//查询到已超时申请要主动取消
			if(pending.getOvertimetime().before(new Date())){
				if(pending != null){
					doShiftWorkCancel(param,pending.getId());	
				}
			}else{
				Date applytime = pending.getApplytime();
				Date endtime = pending.getOvertimetime();
				result.put("applytime", applytime);
				result.put("endtime", endtime);
				return result;
			}
		//如果是客服指派申请,则先取消当前申请,再做新申请
		}else if(param.isIsmantic()){
			PeDrivershiftPending pending = getShiftWorkApply(driver.get());
			if(pending != null){
				//如果是客服指派申请要主动取消
				doShiftWorkCancel(param,pending.getId());
			}
				
		}

		//获取接班司机
		PubDriver receiveDriver = getReceivedDriver(param);
		if(receiveDriver == null) return errorResult.get();
		
		//交班申请
		if(doShiftWorkSend(receiveDriver,param)){ //交班成功,获取申请截止时间
			//更新司机状态,交班司机改为交班中,接班司机改为接班中
			driver.get().setPassworkstatus(DriverState.SHIFTING_SEND.code);
			accdao.updatePubDriver(driver.get());
			receiveDriver.setPassworkstatus(DriverState.SHIFTING_TAKE.code);
			accdao.updatePubDriver(receiveDriver);
			PeDrivershiftPending pending = getShiftWorkApply(driver.get());
			Date applytime = pending.getApplytime();
			Date endtime = pending.getOvertimetime();
			result.put("applytime", applytime);
			result.put("endtime", endtime);
			if(!param.isIsmantic()) sendShiftOFFMessage(receiveDriver,pending);
		}else{
			return errorResult.get();
		}
		logger.info("申请交班完成");
		return result;
	}
	
	/**
	 * 取消交班逻辑
	 * @param param
	 * @param pendingId
	 * @return
	 */
	private boolean doShiftWorkCancel(ShiftWorkParam param,String pendingId){
		logger.info("发起取消交班申请...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
			(isOpDriver.get() ? operateApiUrl : leaseApiUrl)  + "/driverShift/removeApply?pendingId={pendingId}", 
			HttpMethod.POST, 
			param.getToken(), 
			null, 
			JSONObject.class,
			pendingId
		);
		if(result.getInt("status") != Retcode.OK.code){
			logger.error("取消交班失败:" + result);
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", "取消交班失败");
			return false;
		}
		//更新司机状态,交班司机改为当班,接班司机改为歇班
		PubDriver sendDriver = getSendedDriver(param);
		PubDriver receiveDriver = getReceivedDriver(param);
		//如果不存在交接班司机,说明已经超时,或申请已经处理
		if(sendDriver == null || receiveDriver == null) return true;
		sendDriver.setPassworkstatus(DriverState.ONSHIFT.code);
		accdao.updatePubDriver(sendDriver);
		receiveDriver.setPassworkstatus(DriverState.OFFSHIFT.code);
		accdao.updatePubDriver(receiveDriver);
		logger.info("取消交班申请成功.");
		return true;
	}
	/**
	 * 交班申请处理逻辑
	 * @param receiveDriver
	 * @param param
	 * @return
	 */
	private boolean doShiftWorkSend(PubDriver receiveDriver,ShiftWorkParam param){
		logger.info("发起交班申请...");
		PendingSaveDto dto = new PendingSaveDto();
		dto.setCreater(driver.get().getId());
		dto.setDriverid(driver.get().getId());
		dto.setDriverinfo(driver.get().getName()+" "+driver.get().getPhone());
//		dto.setOnlinetime(60L);
		dto.setLeasescompanyid(driver.get().getLeasescompanyid());
		dto.setPlatenoStr(driver.get().getPlateno());
		dto.setPlatformtype(driver.get().getPlatformtype());
		dto.setRelieveddriverid(receiveDriver.getId());
		dto.setRelievedtype(param.isIsmantic()?"1":"0");
		dto.setVehicleid(driver.get().getVehicleid());
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
			(isOpDriver.get() ? operateApiUrl : leaseApiUrl)  + "/driverShift/apply", 
			HttpMethod.POST, 
			param.getToken(), 
			dto, 
			JSONObject.class
		);
		if(result.getInt("status") == Retcode.OK.code){
			/** {@linkplain PendInfoDto} */
			int state = result.getJSONObject("data").getInt("state");
			if(state != 0){
				String errMsg = -1 == state?"无交接班规则":
											 1 == state?"交接失败":
											 2 == state?"无交班申请":
											 3 == state?"无绑定信息": "";
				if(state == -1){
					errorResult.get().put("status", Retcode.NOSHIFTRULE.code);
					errorResult.get().put("message", Retcode.NOSHIFTRULE.msg);
				}else if (state == 3) {
					errorResult.get().put("status", Retcode.NOWORKMATE.code);
					errorResult.get().put("message", Retcode.NOWORKMATE.msg);
				}else{
					errorResult.get().put("status", Retcode.FAILED.code);
					errorResult.get().put("message", "交班申请失败:" + errMsg);
				}
				return false;
			}
		}else{
			logger.error("交班申请失败:" + result);
			errorResult.set(result);
			return false;
		}
		logger.info("交班申请成功.");
		return true;
	}
	
	/**
	 * 接班处理逻辑
	 * @param sendDriver
	 * @param param
	 * @return
	 */
	private boolean doShiftWorkON(ShiftWorkParam param){
		logger.info("发起接班申请...");
		logger.info("使用参数:" + JSONObject.fromObject(param));
		//获取交班司机
		PubDriver sendDriver = getSendedDriver(param);
		if(sendDriver == null) return false;
		//接班申请
		PeDrivershiftPending pending = getShiftWorkApply(sendDriver);
		if(!param.isConfirm()){  //不接班=取消本次交班
			if(!doShiftWorkCancel(param, pending.getId())){
				errorResult.get().put("status", Retcode.FAILED.code);
				errorResult.get().put("message", "拒绝接班失败");
				return false;
			}
			//发送通知给交班司机
			sendShiftONMessage(sendDriver,false);
			logger.info("拒绝接班成功..");
			return true;
		}
		//确认接班
		if(!confirmShiftTake(pending,param)) return false;
//		//发送通知给交班司机
//		sendShiftONMessage(sendDriver,true);
		//更新司机状态,交班司机改为离线歇班,接班司机改为当班
		//确认接班需要返回交接班类型
		errorResult.get().put("applytype", pending.getRelievedtype());
		driver.get().setPassworkstatus(DriverState.ONSHIFT.code);
		accdao.updatePubDriver(driver.get());
		sendDriver.setWorkstatus(DriverState.OFFLINE.code);
		sendDriver.setPassworkstatus(DriverState.OFFSHIFT.code);
		accdao.updatePubDriver(sendDriver);
		logger.info("确认接班成功..");
		return true;
	}
	
	/**
	 * 确认接班逻辑
	 * @param pending
	 * @param param
	 * @return
	 */
	private boolean confirmShiftTake(PeDrivershiftPending pending,ShiftWorkParam param){
		ProcessedSaveDto dto = new ProcessedSaveDto();
		dto.setPendingId(pending.getId());
		dto.setProcessperson(driver.get().getId());
		dto.setProcesspersonname(driver.get().getName());
		dto.setRelieveddriverid(driver.get().getId());
		dto.setRelieveddriverInfo(driver.get().getName() + " " + driver.get().getPhone());
		dto.setRelievedtype(PeDrivershiftEnum.RELIVED_TYPE_AUTONOMOUSLY.code);
		dto.setShifttype(PeDrivershiftEnum.SHIFT_TYPE_PROCESSED.code);
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
				(isOpDriver.get() ? operateApiUrl : leaseApiUrl)  + "/driverShift/processd", 
			HttpMethod.POST, 
			param.getToken(), 
			dto, 
			JSONObject.class
		);
		if(result.getInt("status") != Retcode.OK.code){
			logger.error("确认接班失败："+ result);
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", "确认接班失败");
			return false;
		}
		return true;
	}
	
	/**
	 * 查询交接班申请
	 * @param sendDriver
	 * @return
	 */
	private PeDrivershiftPending getShiftWorkApply(PubDriver sendDriver){
		logger.info("查询交班申请...");
		PeDrivershiftPending pending = new PeDrivershiftPending();
		pending.setDriverid(sendDriver.getId());
		pending.setPlatformtype(sendDriver.getPlatformtype());
		pending.setShifttype(PeDrivershiftEnum.SHIFT_TYPE_PENDING.code);
		//如果当前司机是接班司机则只查自主交班申请,否则不区分
		if(DriverState.SHIFTING_TAKE.code.equals(driver.get().getPassworkstatus())){
			pending.setRelievedtype(PeDrivershiftEnum.RELIVED_TYPE_AUTONOMOUSLY.code);
		}
		pending.setLeasescompanyid(sendDriver.getLeasescompanyid());
		pending = taxiindexfuncdao.getShiftWorkApply(pending);
		if(pending == null) return null;
		//测试,暂时没有设置overtime
		Date overtime = pending.getOvertimetime();
		if(overtime == null){
			overtime = new Date(pending.getApplytime().getTime() + (5*60*1000));
		}
		pending.setOvertimetime(overtime);
		//测试end
		logger.info("查询交班申请成功.");
		return pending;
	}
	
	/**
	 * 获取交班司机
	 * @return
	 */
	private PubDriver getSendedDriver(ShiftWorkParam param){
		//如果当前登录司机就是交班司机
		if(DriverState.SHIFTING_SEND.code.equals(driver.get().getPassworkstatus())){
			return driver.get();
		}
		PubDriver pd = null;
		List<PubDriver> list = taxiindexfuncdao.getWorkmates(driver.get());
		for(PubDriver pdd : list){
			//找出交班中司机(只有当班司机才能发起交班)
			if(DriverState.SHIFTING_SEND.code.equals(pdd.getPassworkstatus())){
//				pd = accdao.getPubDriverById(pdd.getId());
				pd = pdd;
				break;
			}
		}
		if(pd == null){
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", (param.getName()==null?"":param.getName()) + " " + param.getMobile()+"不是你的对班司机");
		}
		return pd;
	}
	
	/**
	 * 获取接班司机
	 * @param param
	 * @return
	 */
	private PubDriver getReceivedDriver(ShiftWorkParam param){
		//如果当前登录司机就是接班司机
		if(DriverState.SHIFTING_TAKE.code.equals(driver.get().getPassworkstatus())){
			return driver.get();
		}
		PubDriver pd = null;
		List<PubDriver> list = taxiindexfuncdao.getWorkmates(driver.get());
		for(PubDriver pdd : list){
			if(DriverState.SHIFTING_TAKE.code.equals(pdd.getPassworkstatus()) ||
				pdd.getPhone().equals(param.getMobile())){
//				pd = accdao.getPubDriverById(pdd.getId());
				pd = pdd;
				break;
			}
		}
		if(pd == null && list != null && list.size() > 1){
//			errorResult.get().put("status", Retcode.FAILED.code);
//			errorResult.get().put("message", (param.getName()==null?"":param.getName()) + " " + param.getMobile()+"不是你的对班司机");
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", "交班失败");
		}else if(pd == null){
			errorResult.get().put("status", Retcode.NOWORKMATE.code);
			errorResult.get().put("message", Retcode.NOWORKMATE.msg);
		}
		return pd;
	}

	/**
	 * 发送取消交班通知(待确认是否通知)
	 * @param receiver 接班司机
	 * @return
	 */
	private boolean sendShiftCancelMessage(PubDriver receiver){
		String content = "取消交班";
		List<String> userids = new ArrayList<>();
		userids.add(receiver.getPhone());
		UserMessage message = new UserMessage(userids, UserMessage.SHIFTCANCEL,content);
		MessageUtil.sendMessage(message);
		return true;
	}
	
	/**
	 * 发送交班通知
	 * @param receiver 接班司机
	 * @param pending 交接班申请
	 * @return
	 */
	private boolean sendShiftOFFMessage(PubDriver receiver,PeDrivershiftPending pending){
		PubDriver sender = driver.get();
		String drivername = sender.getName();
		String applytype = pending.getRelievedtype();
		long applystarttime = pending.getApplytime().getTime();
		long applyendtime = pending.getOvertimetime().getTime();
		List<String> userids = new ArrayList<>();
		userids.add(receiver.getPhone());
		UserMessage message = new UserMessage(userids, UserMessage.SHIFTOFF,drivername,applytype,applystarttime,applyendtime);
		message.setDriverphone(sender.getPhone());
		message.setServicephone(sender.getServicephone());
		MessageUtil.sendMessage(message);
		return true;
	}
	
	/**
	 * 发送接班通知(确认接班或拒绝接班都发)
	 * @param sender 交班发起人
	 * @param confirm true - 确认交班 | false - 拒绝交班
	 * @return
	 */
	private boolean sendShiftONMessage(PubDriver sender,boolean confirm){
//		String content = "交班成功";
		List<String> userids = new ArrayList<>();
		userids.add(sender.getPhone());
		UserMessage message = new UserMessage(userids, UserMessage.SHIFTON,confirm);
		MessageUtil.sendMessage(message);
		return true;
	}
	/*********************************************************校验方法****************************************************************/
	/**
	 * 检查是否有交接班规则
	 * @param hasShiftRuleSign
	 * @return
	 */
	@ValidateRule(msg="交班申请失败,无交接班规则")
	private boolean checkShiftRule(boolean hasShiftRuleSign){
		boolean flag = false;
		if(PlatformTypeByDb.OPERATING.code.equals(driver.get().getPlatformtype())){
			OpShiftRules rule = taxiindexfuncdao.getOpShiftWorkRule(driver.get());
			flag = rule != null;
		}else{
			LeShiftRule rule = taxiindexfuncdao.getLeShiftWorkRule(driver.get());
			flag = rule != null;
		}
		if(!flag){
			errorResult.get().put("status", Retcode.NOSHIFTRULE.code);
			errorResult.get().put("message", Retcode.NOSHIFTRULE.msg);
		}
		return flag; 
	}
	
	/**
	 * 检查司机是否可以交班
	 * @return
	 */
	@ValidateRule(msg="交班申请失败,司机状态不正确")
	private boolean checkDriverCanShiftOFF(boolean shiftOFFSign){
		//司机被解绑车辆,不允许交班,不允许接班
		if(DriverState.UNBINDCAR.code.equals(driver.get().getBoundstate())){
			errorResult.get().put("status", Retcode.DRIVERUNBIND.code);
			errorResult.get().put("message", Retcode.DRIVERUNBIND.msg);
			return false;
		}
		//司机处于服务中状态不允许交班
		if(DriverState.INSERVICE.code.equals(driver.get().getWorkstatus())){
			errorResult.get().put("status", Retcode.ORDERNOTDONE.code);
			errorResult.get().put("message", Retcode.ORDERNOTDONE.msg);
			return false;
		}
		//如果司机变成了无对班状态,则直接返回
		if(DriverState.NOSHIFTDRIVER.code.equals(driver.get().getPassworkstatus())){
			errorResult.get().put("status", Retcode.NOWORKMATE.code);
			errorResult.get().put("message", Retcode.NOWORKMATE.msg);
			return false;
		}
		//如果当前司机不是当班司机或者交班中司机,则打回
		if(!DriverState.ONSHIFT.code.equals(driver.get().getPassworkstatus()) && 
			!DriverState.SHIFTING_SEND.code.equals(driver.get().getPassworkstatus())){
			errorResult.get().put("status", Retcode.DRIVERNOTSHIFTSTATUS.code);
			errorResult.get().put("message", Retcode.DRIVERNOTSHIFTSTATUS.msg);
			return false;
		}
		return true;
	}
	
	/**
	 * 检查司机是否可以接班
	 * @return
	 */
	@ValidateRule(msg="接班申请失败,司机状态不正确")
	private boolean checkDriverCanShiftON(boolean shiftONSign){
		//司机被解绑车辆,不允许交班,不允许接班
		if(DriverState.UNBINDCAR.code.equals(driver.get().getBoundstate())){
			errorResult.get().put("status", Retcode.DRIVERUNBIND.code);
			errorResult.get().put("message", Retcode.DRIVERUNBIND.msg);
			return false;
		}
		//如果当前司机不是接班司机,则打回
		if(!DriverState.SHIFTING_TAKE.code.equals(driver.get().getPassworkstatus())){
			errorResult.get().put("status", Retcode.DRIVERNOTSHIFTSTATUS.code);
			errorResult.get().put("message", Retcode.DRIVERNOTSHIFTSTATUS.msg);
			return false;
		}
		return true;
	}
	
	/**
	 * 检查司机是否可以取消交班
	 * @return
	 */

	@ValidateRule(msg="取消交班失败,司机状态不正确")
	private boolean checkDriverCanCancelShift(boolean cancelShiftSign){
		//如果当前司机不是交班中司机,不允许取消交班
		if(!DriverState.SHIFTING_SEND.code.equals(driver.get().getPassworkstatus())){
			errorResult.get().put("status", Retcode.FAILED.code);
			errorResult.get().put("message", "当前司机不是交班司机,不允许发起取消交班");
			return false;
		}
		return true;
	}
	
	/**
	 * 检查未付结订单(出租车需要检查)
	 * @param pd
	 * @return
	 */
	@ValidateRule(msg="存在未付结订单或未付结订单超限")
	private boolean checkNotPayOrder(boolean hasNotPayOrder){
		logger.info("检查司机是否存在未付结订单...");
		//网约车不检查
		if(!DriverEnum.DRIVER_TYPE_TAXI.code.equals(driver.get().getVehicletype() + "")) return true;
		OrderListParam olp = new OrderListParam();
		olp.setType(OrderListEnum.WAITPAY.state);
		olp.setDriverid(driver.get().getId());
		List<OpTaxiOrder> list = taxiorderfuncdao.getTaxiOrderList(olp);
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
				logger.info("存在未付结订单:" + list.get(0).getOrderno());
				errorResult.get().put("status", Retcode.DRIVERNOPAYORDER.code);
				errorResult.get().put("message", Retcode.DRIVERNOPAYORDER.msg);
			}
			Collections.reverse(list);
			OpTaxiOrder o = list.get(0);
			errorResult.get().put("orderno", o.getOrderno());
			errorResult.get().put("usetype", o.getUsetype());
			errorResult.get().put("ordertype", o.getOrdertype());
			return false;
		}
		logger.info("检查司机是否存在未付结订单完成");
		return true;
	}
	
	/**
	 * 检查是否存在未开始的即刻单
	 * @return
	 */
	@ValidateRule(msg="存在未出行的即刻单")
	private boolean checkWaitStartOrder(boolean hasWaitStartOrder){
		logger.info("检查司机是否存在未出行的即刻单...");
		OrderListParam olp = new OrderListParam();
		olp.setType(OrderListEnum.CURRENT.state);
		olp.setDriverid(driver.get().getId());
		List<OpTaxiOrder> list = taxiorderfuncdao.getTaxiOrderList(olp);
		if(list == null) return true;
		for(OpTaxiOrder o : list){
			if(o.isIsusenow()){
				errorResult.get().put("status", Retcode.HASUSENOWORDER.code);
				errorResult.get().put("message", Retcode.HASUSENOWORDER.msg);
				errorResult.get().put("orderno", o.getOrderno());
				errorResult.get().put("usetype", o.getUsetype());
				errorResult.get().put("ordertype", o.getOrdertype());
				logger.info("存在即刻单:" + o.getOrderno());
				return false;
			}
		}
		logger.info("检查司机是否存在未出行的即刻单完成");
		return true;
	}
}

package com.szyciov.driver.base;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.szyciov.annotation.NeedRelease;
import com.szyciov.annotation.ValidateRule;
import com.szyciov.driver.dao.AccountDao;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.exception.EmptyException;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.UserTokenManager;
import com.szyciov.util.ValidateUtil;

import net.sf.json.JSONObject;

/**
 * @ClassName BaseService 
 * @author Efy Shu
 * @Description Service基类
 * @date 2017年3月20日 上午11:35:40 
 */
public class BaseService {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	public final String operateApiUrl = SystemConfig.getSystemProperty("operateApiUrl");
	public final String leaseApiUrl = SystemConfig.getSystemProperty("leaseApiUrl");
	public final String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
	public final String fileServer = SystemConfig.getSystemProperty("fileserver");
	public final TemplateHelper templateHelper = new TemplateHelper();
	
	//使用ThreadLocal,避免并发时出现数据被篡改的问题
	@NeedRelease
	public ThreadLocal<JSONObject> errorResult = new ThreadLocal<JSONObject>(){
		public JSONObject initialValue(){
			return new JSONObject();
		}
	};
	
	@NeedRelease
	public ThreadLocal<PubDriver> driver = new ThreadLocal<PubDriver>();
	@NeedRelease
	public ThreadLocal<Boolean> isOpDriver = new ThreadLocal<Boolean>();
	
	/**
	  *依赖
	  */
	public AccountDao accdao;

	/**
	  *依赖注入
	  */
	@Resource(name="AccountDao")
	public void setAccountDao(AccountDao accdao){
		this.accdao=accdao;
	}
	
	@ValidateRule(msg="token校验失败")
	public boolean checkToken(String token){
		String phone = null;
		try {
			phone = UserTokenManager.getUserNameFromToken(token, UserTokenManager.DRIVERUSER);
			if(phone == null) throw new NoSuchAlgorithmException();
			driver.set(accdao.getPubDriverByToken(token));
			if (driver.get() == null) {  //如果根据token查不到说明token被改了
				throw new EmptyException();
			}else if(DriverEnum.JOB_STATUS_DIMISSION.code.equals(driver.get().getJobstatus())){   //离职状态不允许登录提示账号不存在
				if(accdao.getPubDriverInDel(phone) == null){           //如果不在删除列表里就表示真的不存在
					errorResult.get().put("status", Retcode.USERNOTEXIST.code);
					errorResult.get().put("message", Retcode.USERNOTEXIST.msg);
				}else{   //否则表示该账户被删除
					errorResult.get().put("status", Retcode.USERSIGNOFF.code);
					errorResult.get().put("message", Retcode.USERSIGNOFF.msg);
				}
				return false;
			}
			isOpDriver.set(PlatformTypeByDb.OPERATING.code.equals(driver.get().getPlatformtype()));
		} catch (NoSuchAlgorithmException | ParseException e) {
			errorResult.get().put("status", Retcode.INVALIDTOKEN.code);
			errorResult.get().put("message", Retcode.INVALIDTOKEN.msg);
			return false;
		} catch (EmptyException e) {
			errorResult.get().put("status", Retcode.ANOTHERLOGIN.code);
			errorResult.get().put("message", Retcode.ANOTHERLOGIN.msg);
			return false;
		}
		return driver.get() != null;
	}
	
	/**
	 * 统一参数校验
	 * @param param 参数
	 * @param requreParam 需要校验的参数(无则全部校验)
	 * @return
	 */
	public boolean checkeParam(Object param,String... requreParam){
		logger.info("检查参数...");
		errorResult.set(new JSONObject());
		ValidateUtil valid = new ValidateUtil();
		valid.addRuleClass(this);
		valid.loadRules();
		if(valid.valid(param,requreParam)) {
			logger.info("参数校验完成");
			return true;
		}
		for(String field : valid.getErrorFields()){
			logger.info("参数校验不通过:" + field + "->" + valid.getError(field));
		}
		//如果存在其他类型返回码则直接返回
		if(!errorResult.get().isEmpty() && errorResult.get().getInt("status") != Retcode.FAILED.code) {
			return false;
		}
		errorResult.get().put("status", Retcode.FAILED.code);
		errorResult.get().put("message", valid.getError(valid.getErrorFields()[0]));
		return false;
	}
	
	/**
	 * 整理请求参数的顺序
	 * @param param            参数实体
	 * @param requreParam 本方法需要的参数列表
	 * @param checkToken  是否校验token
	 * @return
	 */
	public String[] getExceptElement(Object param,String[] requreParam,boolean checkToken){
		List<String> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		//优先检测token
		if(checkToken){
			sb.append("token");
			list.add("token");
		}
		for(String p : requreParam) {
			//token参数要被剔除
			if(p.equals("token")) continue;
			sb.append(",").append(p);
			list.add(p);
		}
		logger.debug("当前请求需校验参数:" + sb.toString());
		String[] a = new String[list.size()];
		return list.toArray(a);
	}
	
	/**
	 * 整理请求参数的顺序
	 * @param param            参数实体
	 * @param requreParam 本方法需要的参数列表
	 * @return
	 */
	public String[] getExceptElement(Object param,String[] requreParam){
		return getExceptElement(param,requreParam,true);
	}
}

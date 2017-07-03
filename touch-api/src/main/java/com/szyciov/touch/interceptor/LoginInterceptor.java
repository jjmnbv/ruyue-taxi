package com.szyciov.touch.interceptor;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.touch.enums.ResultStateEnum;
import com.szyciov.touch.service.PubInfoService;
import com.szyciov.touch.util.InterfaceTokenManager;
import com.szyciov.touch.util.ResultUtil;
import com.szyciov.touch.util.SignUtil;
import com.szyciov.util.GUIDGenerator;

/**
 * 标准化接口拦截器
 * @author zhu
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	Logger logger = Logger.getLogger(LoginInterceptor.class);
	
	/**
	 * 签名参数字段
	 */
	private static final String SIGN_PARAMNAME = "sign";
	
	private static final String CHANNELKEY_PARAMNAME = "channelKey";
	
	private static final String CHANNELID_PARAMNAME = "channelId";
	
	private static final String SIGN_DBNAME = "signprivatekey";
	
	private static final String STARTTIME_PARAMNAME = "startTime";
	
	private static final String TOKEN_PARAMNAME = "token";
	
	private static final String AUTHOR_URL = "partner/auth";
	
	public PubInfoService pubInfoService;

	@Resource(name = "PubInfoService")
	public void setPubInfoService(PubInfoService pubInfoService) {
		this.pubInfoService = pubInfoService;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception arg3) throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view) throws Exception {
		//添加请求记录
		String errormessage = (String) request.getAttribute("errormessage");
		addRequestRecord(request,response,errormessage);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try{
			long startTime = System.currentTimeMillis();
		    request.setAttribute("startTime", startTime);
		    
			response.setContentType("application/json;charset=utf-8");
			//1.签名的合法性校验
			if(!validSign(request,response)){
				Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.INVALIDSIGN);
				result.put("errmsg","参数签名校验失败");
				//添加请求记录
				addRequestRecord(request, response, "参数签名校验失败");
				response.getWriter().write(ResultUtil.convertMap2Json(result).toString());
				return false;
			}
			//2.token的合法性校验
			if(!validToken(request,response)){
				Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.INVALIDTOKEN);
				result.put("errmsg","token令牌超时或者不合法");
				//添加请求记录
				addRequestRecord(request, response, "token令牌超时或者不合法");
				response.getWriter().write(ResultUtil.convertMap2Json(result).toString());
				return false;
			}
		}catch(Exception e){
			Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.EXCEPTION);
			//添加请求记录
			addRequestRecord(request, response, StringUtils.isBlank(e.getMessage())?"请求拦击处理异常":e.getMessage());
			response.getWriter().write(ResultUtil.convertMap2Json(result).toString());
			return false;
		}
		return true;
	}
	
	/**
	 * 校验token合法性
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException 
	 * @throws NoSuchAlgorithmException 
	 */
	private boolean validToken(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, ParseException {
		String interfacename = request.getRequestURI();
		//授权接口不需要校验
		if(StringUtils.isBlank(interfacename)||interfacename.indexOf(AUTHOR_URL)>=0){
			return true;
		}
		//1.校验token的有效性
		String token = request.getParameter(TOKEN_PARAMNAME);
		if(StringUtils.isBlank(token)){
			return false;
		}
		String channelkey = InterfaceTokenManager.getChannelKeyFromToken(token);
		if(StringUtils.isBlank(channelkey)){
			return false;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("channelkey", channelkey);
		Map<String,Object> channelinfo = pubInfoService.getChannelInfo(params);
		if(channelinfo==null||channelinfo.size()<=0){
			return false;
		}
		String dbtoken = (String) channelinfo.get("token");
		if(StringUtils.isBlank(dbtoken)||!dbtoken.equals(token)){
			//token和数据库中的不一样
			return false;
		}
		Date tokenovertime = (Date) channelinfo.get("tokenovertime");
		Date current = new Date();
		if(current.after(tokenovertime)){
			//已过期
			return false;
		}
		return true;
	}

	/**
	 * 添加请求记录
	 * @param request
	 * @param response
	 */
	private void addRequestRecord(HttpServletRequest request, HttpServletResponse response,String errormessage){
		try{
			long startTime = (Long) request.getAttribute(STARTTIME_PARAMNAME);
		    long endTime = System.currentTimeMillis();
		    long responsetime = endTime - startTime;
		    String interfacename = request.getRequestURI();
		    String ipaddr = request.getRemoteAddr();
		    Map<String,Object> record = new HashMap<String,Object>();
		    record.put("id", GUIDGenerator.newGUID());
		    record.put("interfacename", interfacename);
		    if(StringUtils.isBlank(errormessage)){
		    	 record.put("requeststate", 0);
		    }else{
		    	record.put("requeststate",1);
		    	String errormsg = errormessage.length()>4000?errormessage.substring(0, 3800):errormessage;
		    	record.put("errormessage", errormsg);
		    }
		    record.put("requestaddr", ipaddr);
		    record.put("responsetime", responsetime);
		    pubInfoService.addRequestRecord(record);
		}catch(Exception e){
			logger.info("添加请求标准化接口记录出错",e);
		}
		
	}
	
	/**
	 * 校验签名合法性
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean validSign(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> reqparamsinfo = getRequestParams(request,response);
		if(reqparamsinfo!=null&&reqparamsinfo.size()>0){
			String signstr = SignUtil.buildParamStr(reqparamsinfo);
			String securitykey = getSecuritykey(request,response);
			String sign = SignUtil.getSignStr(signstr, securitykey);
			String paramsign = request.getParameter(SIGN_PARAMNAME);
			if(StringUtils.isBlank(sign)||!sign.equals(paramsign)){
				//签名不正确
				return true;
			}
		}
		return true;
	}
	
	/**
	 * 获取渠道客户的加密密钥
	 * @param request
	 * @param response
	 * @return
	 */
	private String getSecuritykey(HttpServletRequest request, HttpServletResponse response){
		String channelKey = request.getParameter(CHANNELKEY_PARAMNAME);
		String channelId = request.getParameter(CHANNELID_PARAMNAME);
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isBlank(channelId)&&StringUtils.isBlank(channelKey)){
			return null;
		}
		if(StringUtils.isNotBlank(channelId)){
			params.put("channelid", channelId);
		}
		if(StringUtils.isNotBlank(channelKey)){
			params.put("channelkey", channelKey);
		}
		Map<String,Object> channelinfo = pubInfoService.getChannelInfo(params);
		if(channelinfo==null||channelinfo.size()<=0){
			return null;
		}
		return (String) channelinfo.get(SIGN_DBNAME);
	}
	
	/**
	 * 获取请求的参数信息
	 * @param request
	 * @param response
	 * @return
	 */
	private Map<String,String> getRequestParams(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> paramsinfo = new HashMap<String,String>();
		Enumeration<String> pnames = request.getParameterNames();
		while(pnames.hasMoreElements()){
			String pname = pnames.nextElement();
			if(SIGN_PARAMNAME.equals(pname)){
				continue;
			}
			paramsinfo.put(pname, request.getParameter(pname));
		}
		return paramsinfo;
	}
}

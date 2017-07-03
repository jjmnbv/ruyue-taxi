package com.szyciov.passenger.interceptor;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.Retcode;
import com.szyciov.util.AuthorityManager;
import com.szyciov.util.CommonUtils;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.UserTokenManager;

import net.sf.json.JSONObject;

public class LoginInterceptor implements HandlerInterceptor {
	Logger logger = Logger.getLogger(LoginInterceptor.class);
	
	AuthorityManager authorityManager = new AuthorityManager();

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setContentType("application/json; charset=utf-8");
		// 检测开关设置，如果不拦截，则直接返回   
//		if (StringUtils.equalsIgnoreCase(SystemConfig.getSystemProperty("SZYCIOV_AUTHORITY_STWICH"), "off")) {
//			return true;
//		}
		
		//白名单过滤出来
		Map<String, String> operation = CommonUtils.getOperationByURL(request.getRequestURI());
		if(CommonUtils.checkWhiteList(operation.get("controller") + "/" + operation.get("action"),SystemConfig.getSystemProperty("webIgnoreFeatureUrl"))){
			return true;
		}
		
		//usertoken校验
		String usertoken = request.getHeader("usertoken");
		if(StringUtils.isBlank(usertoken)){
			JSONObject json = new JSONObject();
			json.put("status", Retcode.INVALIDTOKEN.code);
			json.put("message", Retcode.INVALIDTOKEN.msg);
			json.put("servertime", (new Date()).getTime());
			response.getWriter().write(json.toString());
			return false;
		}
		
		String usertype = null;
		try{
			usertype = UserTokenManager.getUserTypeFromToken(usertoken);
			if(!UserTokenManager.validateUserTicket(usertoken, usertype)){
				JSONObject json = new JSONObject();
				json.put("status", Retcode.INVALIDTOKEN.code);
				json.put("message", Retcode.INVALIDTOKEN.msg);
				json.put("servertime", (new Date()).getTime());
				response.getWriter().write(json.toString());
				return false;
			}
		}catch(Exception e){
			JSONObject json = new JSONObject();
			json.put("status", Retcode.INVALIDTOKEN.code);
			json.put("message", Retcode.INVALIDTOKEN.msg);
			json.put("servertime", (new Date()).getTime());
			response.getWriter().write(json.toString());
			return false;
		}
		
		//更新了usertoken，挤下线
		if(!UserTokenManager.validUserToken4Db(usertype, usertoken)){
			JSONObject json = new JSONObject();
			json.put("status", Retcode.ANOTHERLOGIN.code);
			json.put("message", Retcode.ANOTHERLOGIN.msg);
			json.put("servertime", (new Date()).getTime());
			response.getWriter().write(json.toString());
			return false;
		}
		return true;
	}
}

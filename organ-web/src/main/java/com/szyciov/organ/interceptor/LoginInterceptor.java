package com.szyciov.organ.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.util.AuthorityManager;
import com.szyciov.util.CommonUtils;
import com.szyciov.util.Constants;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.UserTokenManager;

public class LoginInterceptor implements HandlerInterceptor {
	private static final String REDIRECT_PAGE = "/login.jsp";
	Logger logger = Logger.getLogger(LoginInterceptor.class);
	AuthorityManager authorityManager = new AuthorityManager();

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 如果是登录，则不拦截
		String path = request.getServletPath();
		if (StringUtils.contains(path, "Login")||StringUtils.contains(path, "GetImgCode")) {
			return true;
		}

		//没有session
		HttpSession session = request.getSession(false);
		if(session==null){
			logger.log(Level.INFO, "没有登录：session is blank!");
			response.setHeader("timeout", "true");
			request.setAttribute("message", "抱歉，您的操作超时，请重新登录。");
			request.getRequestDispatcher(REDIRECT_PAGE).forward(request, response);
//			response.sendRedirect(request.getContextPath() + REDIRECT_PAGE);
			return false;
		}
		String usertoken = (String) session.getAttribute(Constants.REQUEST_USER_TOKEN);
		if(StringUtils.isBlank(usertoken)){
			logger.log(Level.INFO, "没有登录：usertoken is blank!");
			response.setHeader("timeout", "true");
			request.setAttribute("message", "抱歉，您的操作超时，请重新登录。");
			request.getRequestDispatcher(REDIRECT_PAGE).forward(request, response);
//			response.sendRedirect(request.getContextPath() + REDIRECT_PAGE);
			return false;
		}

		// 检测开关设置，如果不拦截，则直接返回   
		if (StringUtils.equalsIgnoreCase(SystemConfig.getSystemProperty("SZYCIOV_AUTHORITY_STWICH"), "off")) {
			return true;
		}
		
		//usertoken有效性验证
		String userName = "";
		try {
			userName = UserTokenManager.getUserNameFromToken(usertoken,UserTokenManager.ORGWEB);
			if (StringUtils.isBlank(userName)) {
				logger.log(Level.INFO, "usertoken校验失败：loginName is blank!");
				response.sendRedirect(request.getContextPath() + REDIRECT_PAGE);
				return false;
			}
		} catch (Exception e) {
			logger.log(Level.INFO, "解析userToken出现异常");
			response.sendRedirect(request.getContextPath() + REDIRECT_PAGE);
			return false;
		}

		Map<String, String> operation = CommonUtils.getOperationByURL(request.getRequestURI());
		if (!authorityManager.ValidateUserFeatureAuthority(operation, userName)) {
			response.sendRedirect(request.getContextPath() + REDIRECT_PAGE);
			logger.info("权限校验失败:后台获取没有权限！");
			return false;
		}
		return true;
	}
}

package com.szyciov.operate.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import com.szyciov.util.CommonUtils;
import com.szyciov.util.Constants;
import com.szyciov.util.UserTokenManager;

public class loginFilter extends OncePerRequestFilter {
	private static final String UNCHEKED_URL = "/login.jsp,/login,/doLogin";
	private static final String REDIRECT_PAGE = "/login.jsp";
	Logger logger = Logger.getLogger(loginFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String path = request.getServletPath();
		request.setAttribute("message", request.getParameter("message"));
		// 如果是属于白名单中的url，无需权限验证
		if (CommonUtils.checkWhiteList(path, UNCHEKED_URL)) {
			filterChain.doFilter(request, response);
		} else {
			//没有session
			HttpSession session = request.getSession(false);
			if(session==null){
				logger.log(Level.INFO,"没有登录：session is blank!");
				response.sendRedirect(request.getContextPath() + REDIRECT_PAGE);
				return ;
			}
			// 已登录无需处理
			String usertoken = (String) session.getAttribute(Constants.REQUEST_USER_TOKEN);
			String userName = "";
			try {
				userName = UserTokenManager.getUserNameFromToken(usertoken,UserTokenManager.LEASEUSER);
				if (StringUtils.isNotBlank(userName)) {
					filterChain.doFilter(request, response);
					return;
				}
			} catch (Exception e) {
				response.sendRedirect(request.getContextPath() + REDIRECT_PAGE);
				return;
			}
			response.sendRedirect(request.getContextPath() + REDIRECT_PAGE);
		}
	}
}

package com.szyciov.driver.interceptor;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.driver.dao.AccountDao;
import com.szyciov.util.AuthorityManager;
import com.szyciov.util.CommonUtils;
import com.szyciov.util.SystemConfig;

public class TokenInterceptor implements HandlerInterceptor {
	private static final String REDIRECT_PAGE = "/login.jsp";
	private Logger logger = Logger.getLogger(TokenInterceptor.class);
	AuthorityManager authorityManager = new AuthorityManager();
	
	private AccountDao dao;

	@Resource(name = "AccountDao")
	public void setDao(AccountDao dao) {
		this.dao = dao;
	}

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
//		String userToken = RequestUtil.getAjaxData("token", request);
		String userToken = request.getHeader("token");
		System.out.println("api userToken:" + userToken);
		if (StringUtils.isNotBlank(userToken) && (!"null".equalsIgnoreCase(userToken))) {
//			dao.getUserTokenByToken(userToken);
//			String loginName = UserTokenManager.getUserNameFromToken(userToken, Property.securityKey);
//			boolean b = getJedisAuthority(loginName, CommonUtils.getApiOperationByURL(request.getRequestURI()));
//
//			if (b) {
//				if (UserTokenManager.validateUserTicket(userToken, request)) {
//					System.out.println("api 权限验证通过");
					return true;
//				}
//			} else {
//				return false;
//			}
		} else {
			// 检测开关设置，如果不拦截，则直接返回
			if (StringUtils.equalsIgnoreCase(SystemConfig.getSystemProperty("SZYCIOV_AUTHORITY_API_STWICH"), "off")) {
//				System.out.println("api 权限验证通过");
				return true;
			}

			Map<String, String> operation = CommonUtils.getApiOperationByURL(request.getRequestURI());

			// 是否允许匿名调用
			if (CommonUtils.checkWhiteList(operation.get("controller") + "/" + operation.get("action"),
					SystemConfig.getSystemProperty("webApiAnonymousUrl"))) {
//				System.out.println("api 权限验证通过");
				return true;
			}

		}
		// 权限拦截
//		System.out.println("api 权限验证失败");
		response.sendRedirect(request.getContextPath() + REDIRECT_PAGE);
		return false;
	}

	/**
	 * 从jedis获取权限并校验是否匹配
	 * @author luxiangda
	 * @date 2016年5月31日
	 * @param loginName
	 * @param operation
	 * @return
	 */
	public boolean getJedisAuthority(String loginName, Map<String, String> operation) {
		/*boolean b = true;
		// 根据用户获取角色列表
		Jedis jedis = MyJedis.getInstance();
		// 根据用户登录名去缓存里找权限
		List<String> list = new ArrayList<String>();
		if (jedis.hmget("userRole", loginName).get(0) == null) {
			User user = userService.getByName(loginName);
			User cuser = userService.getUserRoleList(user);// 根据id获取用户角色列表
			StringBuffer role = new StringBuffer();
			list = cuser.getUserRole();
			if (list.size() == 0) {
				System.out.println("未查询到用户角色列表--------");
				return false;
			} else {
				for (String i : list) {
					role.append(i).append(",");
				}
				String roles = role.toString();
				Map<String, String> map = new HashMap<String, String>();
				map.put(loginName, roles);
				jedis.hmset("userRole", map);// 把用户角色存到缓存里
			}
		} else {
			String[] srole = jedis.hmget("userRole", loginName).get(0).split(",");
			for (String s : srole) {
				list.add(s);
			}
		}
		String url = operation.get("controller") + "/" + operation.get("action");
		if (jedis.hmget("contollerRole", url).get(0) != null) {// 从缓存里取url的权限列表
			if (!"pass".equals(jedis.hmget("contollerRole", url).get(0))) {// 如果值为pass，数据库没配置权限，不需要验证
				String[] urlRoles = jedis.hmget("contollerRole", url).get(0).split(",");
				int count = 0;
				for (String s : urlRoles) {
					if (list.contains(Integer.parseInt(s))) {
						System.out.println("缓存里有权限，并且匹配权限--------");
						break;
					}
					count++;
				}
				if (count == urlRoles.length) {
					System.out.println("缓存里有权限，但没有匹配权限--------");
					return false;
				}
			} else {
				System.out.println("缓存值为pass,数据库没配置权限，不需要验证--------");
				return b;
			}
		} else {// 如果缓存列表里没url的记录，则查询并添加到缓存
			String roleFromUrl = "";
			Map<String, String> rmap = new HashMap<String, String>();
			rmap.put("controller", operation.get("controller"));
			rmap.put("action", operation.get("action"));
			roleFromUrl = roleService.getRoleFromButton(rmap);// 先从button表查询是否有该url
			if (!StringUtils.isEmpty(roleFromUrl)) {
				int count = 0;// 保存权限比较次数
				String[] urlRoles = roleFromUrl.split(",");
				for (String s : urlRoles) {
					if (list.contains(Integer.parseInt(s))) {
						System.out.println("缓存列表没有权限，从数据库Button表查询结果，匹配权限----------");
						break;
					}
					count++;
				}
				Map<String, String> temp = new HashMap<String, String>();
				temp.put(url, roleFromUrl);
				jedis.hmset("contollerRole", temp);// 把权限补充到缓存里
				if (count == urlRoles.length) {// 查询出权限不为空，比较结束仍未匹配，则拒绝访问
					System.out.println("缓存列表没有权限，从数据库Button表查询结果，但没有匹配权限--------");
					return false;
				}
			} else {// 如果button表没有，再从function表查询
				roleFromUrl = roleService.getRoleFromFunction(rmap);
				if (!StringUtils.isEmpty(roleFromUrl)) {
					int count = 0;
					String[] urlRoles = roleFromUrl.split(",");
					for (String s : urlRoles) {
						if (list.contains(Integer.parseInt(s))) {
							System.out.println("缓存列表没有权限，从数据库Function表查询结果，匹配权限----------");
							break;
						}
						count++;
					}
					Map<String, String> temp = new HashMap<String, String>();
					temp.put(url, roleFromUrl);
					jedis.hmset("contollerRole", temp);// 把权限补充到缓存里
					if (count == urlRoles.length) {// 查询出权限不为空，比较结束仍未匹配，则拒绝访问
						System.out.println("缓存列表没有权限，从数据库Function表查询结果，但没有匹配权限--------");
						return false;
					}
				} else {
					System.out.println("缓存列表没有权限，数据库也没设置权限，不需验证----------");
					Map<String, String> temp = new HashMap<String, String>();
					temp.put(url, "pass");
					jedis.hmset("contollerRole", temp);// 把权限补充到缓存里，以后直接跳过
				}
			}
		}*/
		return true;
	}
}

package com.szyciov.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;

public class AuthorityManager {
	Logger logger = Logger.getLogger(AuthorityManager.class);
	private TemplateHelper templateHelper = new TemplateHelper();

	/**
	 * 检查用户是否有操作权限
	 */
	public boolean ValidateUserFeatureAuthority(Map<String, String> operation, String userName) {
		String controller = operation.get("controller");
		String actionName = operation.get("action");
		// 检查是否免登陆

		// 检查是否已经登陆
		if (StringUtils.isBlank(userName)) {
			return false;
		}
		// 是否免功能检查
		if (CommonUtils.checkWhiteList(operation.get("controller") + "/" + operation.get("action"),
				SystemConfig.getSystemProperty("webIgnoreFeatureUrl"))) {
			logger.info("api 权限验证通过");
			return true;
		}

		// 是否与另外一个功能相同
		String actionSame = Constants.authMap.get(controller + "/" + actionName);
		if (StringUtils.isBlank(actionSame)) {
			actionName = "Index";
		}
		// 验证功能
		return hasFeature(userName, controller, actionName);
	}

	public boolean hasFeature(String loginName, String controllerName, String actionName) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("loginName", loginName);
		param.put("actionName", actionName);
		param.put("controllerName", controllerName);
		return templateHelper.dealRequestWithToken("/RoleManagement/HasFeature", HttpMethod.POST, null, param, Boolean.class);
	}
}

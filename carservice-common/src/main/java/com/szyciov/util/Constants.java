package com.szyciov.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static final String USER_NAME = "userName";

	// 来源类型
	public static final int SOURCE_TYPE_USER = 1;
	public static final int SOURCE_TYPE_ROLE = 2;

	// 功能权限类型
	public static final int ROLE_TYPE_FUNCTION = 1;
	public static final int ROLE_TYPE_BUTTON = 2;

	// request attributes
	public static final String REQUEST_USER_NAME = "userName";
	public static final String REQUEST_USER_TOKEN = "userToken";
	public static final String REQUEST_AUTH_SAME = "authSame";

	@SuppressWarnings("serial")
	public static final Map<String, String> authMap = new HashMap<String, String>() {
		{
			// put("User/Create", "Index");
			// put("User/GetById", "GetById");
		}
	};
}

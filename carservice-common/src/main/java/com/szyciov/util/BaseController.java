package com.szyciov.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpMethod;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.param.LeBaseQueryParam;
import com.szyciov.op.entity.OpUser;
import com.szyciov.org.entity.OrgUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BaseController {
	/*
	 public RestTemplate template = new RestTemplate();
	
	 public <T> T dealByWepApi(String url, HttpMethod method,
		 HttpServletRequest request, Object entity,
		 Class<T> responseType) throws RestClientException {
		
		 // String userToken = request.getHeader("userToken");
		 String userToken = (String)
		 request.getAttribute(Constants.REQUEST_USER_TOKEN);
		 MultiValueMap<String, String> headers = new LinkedMultiValueMap<String,
		 String>();
		 headers.add(Constants.REQUEST_USER_TOKEN, userToken);
		 HttpEntity<Object> requestEntity = new HttpEntity<Object>(entity,
		 headers);
		
		 ResponseEntity<T> responseEntity = template.exchange(Property.webApiUrl +
		 url, method, requestEntity,
		 responseType, entity);
		 return responseEntity.getBody();
	 }
	 */

	/** 
	 * <p>将数据转为json输出</p>
	 *
	 * @param response
	 * @param object
	 * @return
	 */
	public boolean jsonOut(HttpServletResponse response, Object object) {
		String json = null;
		try {
			json = JSONUtil.serialize(object);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		try {
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean jsonOutFromMap(HttpServletResponse response, Map<String, Object> model) {
		String json = null;
		try {
			json = JSONUtil.serialize(model);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		try {
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void jsonOutFromObject(HttpServletResponse response, Object object) {
		String json = null;
		try {
			json = JSONUtil.serialize(object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void jsonOutFromString(HttpServletResponse response, String string) {
		try {
			PrintWriter out = response.getWriter();
			out.print(string);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 将根据token获取到的用户信息放入到queryparam对象中
	 * @param request
	 * @param queryParam
	 * @return
	 */
	public boolean initLeUserInfo(HttpServletRequest request, LeBaseQueryParam queryParam) {
		User user = getLoginLeUser(request);
		if(user != null) {
			queryParam.setAccount(user.getAccount());
			queryParam.setLeasescompanyid(user.getLeasescompanyid());
			queryParam.setNickname(user.getNickname());
			queryParam.setUserid(user.getId());
			queryParam.setSpecialstate(user.getSpecialstate());
			return true;
		}
		return false;
	}
	
	/**
	 * <p>根据token获取当前租赁端登录用户信息</p>
	 * @param request
	 * @return
	 */
	public User getLoginLeUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session==null){
			return null;
		}
		User user =  (User) session.getAttribute("user");
		if(user!=null){
			return user;
		}
		String userToken = (String) request.getSession().getAttribute(Constants.REQUEST_USER_TOKEN);
		if(userToken==null||"".equalsIgnoreCase(userToken)){
			return null;
		}
		String loginName = null;
		try {
			loginName = UserTokenManager.getUserNameFromToken(userToken,UserTokenManager.LEASEUSER);
			TemplateHelper templateHelper = new TemplateHelper();
			user = templateHelper.dealRequestWithToken("/User/GetByLoginName/{loginName}", HttpMethod.GET, userToken, null, User.class, loginName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user; 
	}
	
	/**
	 * <p>根据token获取当前租赁端登录用户信息</p>
	 * @param request
	 * @return
	 */
	public OrgUser getLoginOrgUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session==null){
			return null;
		}
		OrgUser user =  (OrgUser) session.getAttribute("user");
		if(user!=null){
			return user;
		}
		String userToken = (String) request.getSession().getAttribute(Constants.REQUEST_USER_TOKEN);
		if(userToken==null||"".equalsIgnoreCase(userToken)){
			return null;
		}
		String loginName = null;
		try {
			loginName = UserTokenManager.getUserNameFromToken(userToken,UserTokenManager.ORGWEB);
			TemplateHelper templateHelper = new TemplateHelper();
			user = templateHelper.dealRequestWithToken("/User/GetByLoginName/{loginName}", HttpMethod.GET, userToken, null, OrgUser.class, loginName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user; 
	}
	
	/**
	 * <p>获取运管登录用户信息</p>
	 * @param request
	 * @return
	 */
	public OpUser getLoginOpUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session==null){
			return null;
		}
		OpUser user =  (OpUser) session.getAttribute("user");
		if(user!=null){
			return user;
		}
		String userToken = (String) request.getSession().getAttribute(Constants.REQUEST_USER_TOKEN);
		if(userToken==null||"".equalsIgnoreCase(userToken)){
			return null;
		}
		String loginName = null;
		try {
			loginName = UserTokenManager.getUserNameFromToken(userToken,UserTokenManager.OPWEB);
			TemplateHelper templateHelper = new TemplateHelper();
			user = templateHelper.dealRequestWithToken("/User/GetByLoginName/{loginName}", HttpMethod.GET, userToken, null, OpUser.class, loginName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user; 
	}
	
	/**
	 * 获取usertoken的通用方法
	 * app端用户主要是用户请求中传递usertoken
	 * web端用户主要是从session中获取
	 * @param request
	 * @return
	 */
	public String getUserToken(HttpServletRequest request){
		String usertoken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		if(usertoken==null||"".equalsIgnoreCase(usertoken)){
			HttpSession session = request.getSession();
			if(session==null){
				return usertoken;
			}
			usertoken = (String) session.getAttribute(Constants.REQUEST_USER_TOKEN);
		}
		return usertoken;
	}

    /**
     * 获取usertoken的通用方法
     * app端用户主要是用户请求中传递usertoken
     * web端用户主要是从session中获取
     * @return
     */
    public String getUserToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String usertoken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
        if(usertoken==null||"".equalsIgnoreCase(usertoken)){
            HttpSession session = request.getSession();
            if(session==null){
                return usertoken;
            }
            usertoken = (String) session.getAttribute(Constants.REQUEST_USER_TOKEN);
        }
        return usertoken;
    }

    /**
     * <p>根据token获取当前租赁端登录用户信息</p>
     * @return
     */
    public User getLoginLeUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if(session==null){
            return null;
        }
        User user =  (User) session.getAttribute("user");
        if(user!=null){
            return user;
        }
        String userToken = (String) request.getSession().getAttribute(Constants.REQUEST_USER_TOKEN);
        if(userToken==null||"".equalsIgnoreCase(userToken)){
            return null;
        }
        String loginName = null;
        try {
            loginName = UserTokenManager.getUserNameFromToken(userToken,UserTokenManager.LEASEUSER);
            TemplateHelper templateHelper = new TemplateHelper();
            user = templateHelper.dealRequestWithToken("/User/GetByLoginName/{loginName}", HttpMethod.GET, userToken, null, User.class, loginName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    /**
     * 获取用户的所属单位<一句话功能简述>
     * <功能详细描述>
     * @param request
     * @param userToken
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
	public List<Dictionary> getOpUserCompany(HttpServletRequest request, String userToken,boolean isAll) {
		OpUser loginUser = getLoginOpUser(request);
		String account = loginUser.getAccount();
		List<Dictionary> dictionary = new ArrayList<>();
		TemplateHelper templateHelper = new TemplateHelper();
		if ("obdgjcz".equals(account)) {
			dictionary = templateHelper.dealRequestWithToken("/Dictionary/getDictionaryByTypeText?type=租赁公司&text=如约交通",
					HttpMethod.GET, userToken, null, List.class);
		} else if ("obdbaiyun".equals(account)) {
			dictionary = templateHelper.dealRequestWithToken("/Dictionary/getDictionaryByTypeText?type=租赁公司&text=如约白云",
					HttpMethod.GET, userToken, null, List.class);
		} else if ("obdlixin".equals(account)) {
			dictionary = templateHelper.dealRequestWithToken("/Dictionary/getDictionaryByTypeText?type=租赁公司&text=如约丽新",
					HttpMethod.GET, userToken, null, List.class);
		} else if ("obdguangjun".equals(account)) {
			dictionary = templateHelper.dealRequestWithToken("/Dictionary/getDictionaryByTypeText?type=租赁公司&text=如约广骏",
					HttpMethod.GET, userToken, null, List.class);
		}else if(isAll){
			dictionary = templateHelper.dealRequestWithToken("/Dictionary/GetDictionaryByType?type=租赁公司",
					HttpMethod.GET, userToken, null, List.class);
		}
		return dictionary;
	}
}

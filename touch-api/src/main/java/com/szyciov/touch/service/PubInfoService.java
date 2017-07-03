package com.szyciov.touch.service;

import com.szyciov.touch.dao.PubInfoDao;
import com.szyciov.touch.dto.HelloWordUser;
import com.szyciov.touch.enums.ResultStateEnum;
import com.szyciov.touch.util.InterfaceTokenManager;
import com.szyciov.touch.util.ResultUtil;
import com.szyciov.util.UserTokenManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service("PubInfoService")
public class PubInfoService {
	
	private PubInfoDao pubInfodao;
	
	@Resource(name = "PubInfoDao")
	public void setPubInfoDao(PubInfoDao pubInfodao) {
		this.pubInfodao = pubInfodao;
	}

	/**
	 * 根据用户账号获取用户信息
	 * @param req
	 * @param res
	 * @return
	 */
	public Map<String, Object> getOrgUserInfo(HttpServletRequest req, HttpServletResponse res) {
		Map<String,Object> result = ResultUtil.getResultMapInfo(ResultStateEnum.OK);
		
		//参数校验
		String account = req.getParameter("account");
		if(StringUtils.isBlank(account)){
			return ResultUtil.getResultMapInfo(ResultStateEnum.PARAMUNVALIABLE);
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("account",account);
		
		//业务方法
		HelloWordUser userinfo = pubInfodao.getOrgUserInfo(account);
		result.put("data", userinfo);
		return result;
	}

	/**
	 * 通过channelkey和channelid获取加密字段
	 * @param params
	 * @return
	 */
	public Map<String,Object> getChannelInfo(Map<String, Object> params) {
		return pubInfodao.getChannelInfo(params);
	}

	/**
	 * 添加请求标准化接口记录
	 * @param record
	 */
	public void addRequestRecord(Map<String, Object> record) {
		pubInfodao.addRequestRecord(record);
	}

	/**
	 * 根据渠道查询租赁公司
	 * @param req
	 * @return
	 */
	public Map<String, Object> getCompanyByChannel(HttpServletRequest req) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelkey", InterfaceTokenManager.getChannelKeyFromToken(req.getParameter("token")));
		return pubInfodao.getCompanyByChannel(params);
	}

	/**
	 * 根据渠道查询机构用户
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOrgUserByChannel(HttpServletRequest req) throws Exception {
		String account = UserTokenManager.getUserNameFromToken(req.getParameter("usertoken"), UserTokenManager.ORGUSERTYPE);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelkey", InterfaceTokenManager.getChannelKeyFromToken(req.getParameter("token")));
		params.put("account", account);
		return pubInfodao.getOrgUserByChannel(params);
	}

	/**
	 * 根据手机号码查询机构用户
	 * @param account
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOrgUserByChannel(String account, HttpServletRequest req) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelkey", InterfaceTokenManager.getChannelKeyFromToken(req.getParameter("token")));
		params.put("account", account);
		return pubInfodao.getOrgUserByChannel(params);
	}

}

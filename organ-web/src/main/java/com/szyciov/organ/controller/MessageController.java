package com.szyciov.organ.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.szyciov.entity.UserNews;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.entity.OrgUserNews;
import com.szyciov.org.param.FinancialManagementQueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Controller
public class MessageController extends BaseController {
	private static final Logger logger = Logger.getLogger(MessageController.class);
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@RequestMapping("Message/Index")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ModelAndView getIndex(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser orgUser = getLoginOrgUser(request);
		String userId = orgUser.getId();
		
		// 未读消息数量
		int unReadNum = templateHelper.dealRequestWithToken(
				"/Message/GetUnReadNewsCountByUserId?userId={userId}", HttpMethod.GET, userToken,
				null, Integer.class, userId);
		
		// 获取导航菜单信息
		List<Map<String, Object>> navinfo = templateHelper.dealRequestWithToken("/User/GetNavInfo", HttpMethod.POST,
				userToken, orgUser, List.class);

		mav.addObject("unReadNum", unReadNum);
		mav.addObject("navinfo", navinfo);
		mav.addObject("username", orgUser.getAccount());
		mav.setViewName("resource/message/index");
		return mav;
	}

	@RequestMapping("Message/GetNewsByUserId")
	@ResponseBody
	public PageBean getNewsByUserId(@RequestBody FinancialManagementQueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser orgUser = getLoginOrgUser(request);
		queryParam.setKey(orgUser.getId());
		queryParam.setiDisplayLength(20);
		return templateHelper.dealRequestWithToken("/Message/GetNewsByUserId", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("Message/UpdateOrgUserNews")
	@ResponseBody
	public void updateOrgUserNews(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser orgUser = getLoginOrgUser(request);
		String userId = orgUser.getId();
		
		templateHelper.dealRequestWithToken("/Message/UpdateOrgUserNews?userId={userId}", HttpMethod.POST, userToken,
				null, null, userId);
	}
	
	@RequestMapping("Message/MessageDetail/{id}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public ModelAndView getMessageDetail(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		UserNews userNews = templateHelper.dealRequestWithToken("/Message/GetOrgUserNewsById/{id}",
				HttpMethod.GET, userToken, null, UserNews.class, id);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
        String createTime = dateFormat.format(userNews.getCreatetime());
		
        JSONObject jsonObject = JSONObject.fromObject(userNews.getContent());
        OrgUserNews orgUserNews = new OrgUserNews();
        if (jsonObject.has("title")) {
        	orgUserNews.setTitle(jsonObject.getString("title"));
        } else {
        	orgUserNews.setTitle("");
        }
        if (jsonObject.has("content")) {
        	orgUserNews.setContent(jsonObject.getString("content"));
        } else {
        	orgUserNews.setContent("");
        }
        
		OrgUser orgUser = getLoginOrgUser(request);
		// 获取导航菜单信息
		List<Map<String, Object>> navinfo = templateHelper.dealRequestWithToken("/User/GetNavInfo", HttpMethod.POST,
				userToken, orgUser, List.class);
		
		String userId = orgUser.getId();
		// 未读消息数量
		int unReadNum = templateHelper.dealRequestWithToken(
				"/Message/GetUnReadNewsCountByUserId?userId={userId}", HttpMethod.GET, userToken,
				null, Integer.class, userId);

		mav.addObject("orgUserNews", orgUserNews);
		mav.addObject("createTime", createTime);
		mav.addObject("navinfo", navinfo);
		mav.addObject("unReadNum", unReadNum);
		mav.addObject("username", orgUser.getAccount());
		mav.setViewName("resource/message/messageDetail");
		return mav;
	}
	
	@RequestMapping("Message/GetUnReadNewsCount")
	@ResponseBody
	public int getUnReadNewsCount(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		OrgUser orgUser = getLoginOrgUser(request);
		String userId = orgUser.getId();
		return templateHelper.dealRequestWithToken("/Message/GetUnReadNewsCountByUserId?userId={userId}",
				HttpMethod.GET, userToken, null, Integer.class, userId);
	}
	
	
}

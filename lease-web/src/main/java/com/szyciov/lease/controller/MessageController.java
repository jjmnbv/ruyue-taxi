package com.szyciov.lease.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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
import com.szyciov.lease.entity.LeUserNews;
import com.szyciov.lease.entity.User;
import com.szyciov.param.QueryParam;
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
	public ModelAndView getIndex(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String userId = user.getId();
		
		// 未读消息数量
		int unReadNum = templateHelper.dealRequestWithToken(
				"/Message/GetUnReadNewsCountByUserId?userId={userId}", HttpMethod.GET, userToken,
				null, Integer.class, userId);

		mav.addObject("unReadNum", unReadNum);
		mav.setViewName("resource/message/index");
		return mav;
	}
	
	@RequestMapping("Message/GetNewsByUserId")
	@ResponseBody
	public PageBean getNewsByUserId(@RequestBody QueryParam queryParam, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		// userId
		queryParam.setKey(user.getId());
		
		return templateHelper.dealRequestWithToken("/Message/GetNewsByUserId", HttpMethod.POST, userToken,
				queryParam,PageBean.class);
	}
	
	@RequestMapping("Message/UpdateLeUserNews")
	@ResponseBody
	public Map<String, String> updateLeUserNews(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "标记成功");
		
		User user = getLoginLeUser(request);
		String userId = user.getId();
		
		templateHelper.dealRequestWithToken("/Message/UpdateLeUserNews?userId={userId}", HttpMethod.POST, userToken,
				null, null, userId);
		
		return ret;
	}
	
	@RequestMapping("Message/MessageDetail/{id}")
	@ResponseBody
	public ModelAndView getMessageDetail(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);

		UserNews userNews = templateHelper.dealRequestWithToken("/Message/GetLeUserNewsById/{id}",
				HttpMethod.GET, userToken, null, UserNews.class, id);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
        String createTime = dateFormat.format(userNews.getCreatetime());
        
		JSONObject jsonObject = JSONObject.fromObject(userNews.getContent());
		LeUserNews leUserNews = new LeUserNews();
		if (jsonObject.has("title")) {
			leUserNews.setTitle(jsonObject.getString("title"));
		} else {
			leUserNews.setTitle("");
		}
		if (jsonObject.has("content")) {
			leUserNews.setContent(jsonObject.getString("content"));
		} else {
			leUserNews.setContent("");
		}
		
		mav.addObject("leUserNews", leUserNews);
		mav.addObject("createTime", createTime);
		mav.setViewName("resource/message/messageDetail");
		return mav;
	}
	
	@RequestMapping("Message/GetUnReadNewsCount")
	@ResponseBody
	public int getUnReadNewsCount(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		User user = getLoginLeUser(request);
		String userId = user.getId();
		return templateHelper.dealRequestWithToken("/Message/GetUnReadNewsCountByUserId?userId={userId}",
				HttpMethod.GET, userToken, null, Integer.class, userId);
	}
}

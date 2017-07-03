package com.szyciov.organ.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.entity.UserNews;
import com.szyciov.org.entity.OrgUserNews;
import com.szyciov.organ.service.MessageService;
import com.szyciov.param.QueryParam;
import com.szyciov.util.BaseController;
import com.szyciov.util.PageBean;

/**
 * 消息模块控制器
 */
@Controller
public class MessageController extends BaseController {
	private static final Logger logger = Logger.getLogger(MessageController.class);

	public MessageService messageService;

	@Resource(name = "MessageService")
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
	/** 
	 * <p>分页查询  消息列表</p>
	 *
	 * @param queryParam 查询请求对象，封装需要查询的key和页码等信息
	 * @return 返回一页查询结果
	 */
	@RequestMapping(value = "api/Message/GetNewsByUserId", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgUserNewsByUserId(@RequestBody QueryParam queryParam) {
		return messageService.getOrgUserNewsByUserId(queryParam);
	}
	
	/** 
	 * <p>查询未读消息数量</p>
	 *
	 * @param userId 用户Id
	 * @return
	 */
	@RequestMapping(value = "api/Message/GetUnReadNewsCountByUserId", method = RequestMethod.GET)
	@ResponseBody
	public int getOrgUserNewsUnReadCountByUserId(@RequestParam(value = "userId", required = true) String userId) {
		logger.log(Level.INFO, "api getOrgUserNewsUnReadCountByUserId userId:" + userId);
		return messageService.getOrgUserNewsUnReadCountByUserId(userId);
	}
	
	/** 
	 * <p>查询消息详情</p>
	 *
	 * @param id 消息主键
	 * @return
	 */
	@RequestMapping(value = "api/Message/GetOrgUserNewsById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public UserNews getOrgUserNewsById(@PathVariable String id) {
		logger.log(Level.INFO, "id:" + id);
		return messageService.getOrgUserNewsById(id);
	}
	
	/** 
	 * <p>全部标记已读</p>
	 *
	 * @param userId 用户Id
	 * @return
	 */
	@RequestMapping(value = "api/Message/UpdateOrgUserNews", method = RequestMethod.POST)
	@ResponseBody
	public void updateOrgUserNews(@RequestParam(value = "userId", required = true) String userId) {
		logger.log(Level.INFO, "userId:" + userId);
		messageService.updateOrgUserNews(userId);
	}
	
}

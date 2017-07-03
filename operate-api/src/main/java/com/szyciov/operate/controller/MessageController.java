package com.szyciov.operate.controller;

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
import com.szyciov.operate.service.MessageService;
import com.szyciov.org.entity.OrgUserNews;
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
	public PageBean getOpUserNewsByUserId(@RequestBody QueryParam queryParam) {
		return messageService.getOpUserNewsByUserId(queryParam);
	}
	
	/** 
	 * <p>查询未读消息数量</p>
	 *
	 * @param userId 用户Id
	 * @return
	 */
	@RequestMapping(value = "api/Message/GetUnReadNewsCountByUserId", method = RequestMethod.GET)
	@ResponseBody
	public int getOpUserNewsUnReadCountByUserId(@RequestParam(value = "userId", required = true) String userId) {
		logger.log(Level.INFO, "api getOpUserNewsUnReadCountByUserId userId:" + userId);
		return messageService.getOpUserNewsUnReadCountByUserId(userId);
	}
	
	/** 
	 * <p>查询消息详情</p>
	 *
	 * @param id 消息主键
	 * @return
	 */
	@RequestMapping(value = "api/Message/GetOpUserNewsById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public UserNews getOpUserNewsById(@PathVariable String id) {
		logger.log(Level.INFO, "id:" + id);
		return messageService.getOpUserNewsById(id);
	}
	
	/** 
	 * <p>全部标记已读</p>
	 *
	 * @param userId 用户Id
	 * @return
	 */
	@RequestMapping(value = "api/Message/UpdateOpUserNews", method = RequestMethod.POST)
	@ResponseBody
	public void updateOpUserNews(@RequestParam(value = "userId", required = true) String userId) {
		logger.log(Level.INFO, "userId:" + userId);
		messageService.updateOpUserNews(userId);
	}
}

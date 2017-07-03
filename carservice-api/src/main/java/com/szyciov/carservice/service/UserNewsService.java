package com.szyciov.carservice.service;

import com.szyciov.carservice.dao.UserNewsApiDao;
import com.szyciov.entity.UserNews;
import com.szyciov.param.UserNewsParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户消息处理类
 * @author LC
 */
@Service
public class UserNewsService {

	@Resource
	private UserNewsApiDao userNewsApiDao;

	private Logger logger = LoggerFactory.getLogger(UserNewsService.class);


	/**
	 *	保存用户消息
	 * @param userNewsParam		用户消息参数对象
	 * @return
	 */
	public boolean saveUserNews(UserNewsParam userNewsParam){
		//获取影响条数
		Integer count = 0;
		try {
			userNewsParam.getUserNews().setId(GUIDGenerator.newGUID());
			userNewsParam.getUserNews().setNewsstate(UserNews.USER_NEWS_STATE_UNREAD);
			count = userNewsApiDao.saveUserNews(userNewsParam);
		} catch (Exception e) {
			logger.error("保存用户消息出错：{}", GsonUtil.toJson(userNewsParam),e);
			return false;
		}
		if(count==1){
			return true;
		}
		return false;
	}




}

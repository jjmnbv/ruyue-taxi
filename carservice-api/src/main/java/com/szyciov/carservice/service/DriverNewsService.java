package com.szyciov.carservice.service;

import com.szyciov.carservice.dao.DriverNewsApiDao;
import com.szyciov.driver.entity.PubDriverNews;
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
public class DriverNewsService {

	@Resource
	private DriverNewsApiDao driverNewsApiDao;

	private Logger logger = LoggerFactory.getLogger(DriverNewsService.class);


	/**
	 *	保存司机消息
	 * @param driverNews		用户消息参数对象
	 * @return
	 */
	public boolean saveDriverNews(PubDriverNews driverNews){
		//获取影响条数
		Integer count = 0;
		try {
			driverNews.setId(GUIDGenerator.newGUID());
			count = driverNewsApiDao.saveDriverNews(driverNews);
		} catch (Exception e) {
			logger.error("保存用户消息出错：{}", GsonUtil.toJson(driverNews),e);
			return false;
		}
		if(count==1){
			return true;
		}
		return false;
	}




}

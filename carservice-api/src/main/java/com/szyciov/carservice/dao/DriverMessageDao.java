package com.szyciov.carservice.dao;


import javax.annotation.Resource;
import com.szyciov.carservice.mapper.DriverMessageMapper;
import com.szyciov.driver.entity.PubDriverNews;

import org.springframework.stereotype.Repository;



/**
  * @ClassName DriverMessageDao
  * @author Efy Shu
  * @Description 出租车消息功能Dao
  * @date 2017年4月11日 20:50:11
  */ 
@Repository("DriverMessageDao")
public class DriverMessageDao{

	/**
	  *依赖
	  */
	private DriverMessageMapper drivermessagemapper;

	/**
	  *依赖注入
	  */
	@Resource
	public void setDriverMessageMapper(DriverMessageMapper drivermessagemapper){
		this.drivermessagemapper=drivermessagemapper;
	}

	/**
	 * 新增司机消息
	 * @param news
	 * @return
	 */
	public boolean addDriverMessage(PubDriverNews news){
		drivermessagemapper.addDriverMessage(news);
		return true;
	}
}

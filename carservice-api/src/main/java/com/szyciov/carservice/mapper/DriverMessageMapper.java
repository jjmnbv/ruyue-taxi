package com.szyciov.carservice.mapper;

import com.szyciov.driver.entity.PubDriverNews;

/**
  * @ClassName DriverMessageMapper
  * @author Efy Shu
  * @Description 出租车消息功能Mapper
  * @date 2017年4月11日 20:50:11
  */ 
public interface DriverMessageMapper{
	public void addDriverMessage(PubDriverNews news);
}

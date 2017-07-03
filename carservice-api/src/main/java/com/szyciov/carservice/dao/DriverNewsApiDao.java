package com.szyciov.carservice.dao;

import com.szyciov.carservice.mapper.DriverNewsApiMapper;
import com.szyciov.driver.entity.PubDriverNews;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 *	用户消息DAO
 */
@Repository
public class DriverNewsApiDao {

	@Resource
	private DriverNewsApiMapper driverNewsApiMapper;
	
	public Integer saveDriverNews(PubDriverNews driverNews) throws Exception{
		return driverNewsApiMapper.saveDriverNews(driverNews);
	}

}

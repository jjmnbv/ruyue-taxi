package com.szyciov.carservice.mapper;

import com.szyciov.driver.entity.PubDriverNews;

/**
 * 用户消息Mapper
 */
public interface DriverNewsApiMapper {

	Integer saveDriverNews(PubDriverNews driverNews) throws Exception;
}
package com.szyciov.carservice.mapper;

import com.szyciov.param.UserNewsParam;

/**
 * 用户消息Mapper
 */
public interface UserNewsApiMapper {

	public Integer saveUserNews(UserNewsParam userNewsParam) throws Exception;
}
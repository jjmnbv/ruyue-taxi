package com.szyciov.carservice.dao;

import com.szyciov.carservice.mapper.UserNewsApiMapper;
import com.szyciov.param.UserNewsParam;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 *	用户消息DAO
 */
@Repository
public class UserNewsApiDao {

	@Resource
	private UserNewsApiMapper userNewsApiMapper;
	
	public Integer saveUserNews(UserNewsParam userNewsParam) throws Exception{
		return userNewsApiMapper.saveUserNews(userNewsParam);
	}

}

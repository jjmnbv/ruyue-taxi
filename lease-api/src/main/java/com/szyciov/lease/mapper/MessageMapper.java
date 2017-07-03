package com.szyciov.lease.mapper;

import java.util.List;

import com.szyciov.entity.UserNews;
import com.szyciov.lease.entity.LeUserNews;
import com.szyciov.param.QueryParam;

public interface MessageMapper {
    
	List<UserNews> getLeUserNewsListByUserId(QueryParam queryParam);
	
	int getLeUserNewsListCountByUserId(String userId);
	
	int getLeUserNewsUnReadCountByUserId(String userId);
	
	UserNews getLeUserNewsById(String id);
	
	void updateLeUserNews(String userId);
	
	void updateReadNews(String id);
}

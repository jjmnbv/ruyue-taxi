package com.szyciov.operate.mapper;

import java.util.List;

import com.szyciov.entity.UserNews;
import com.szyciov.org.entity.OrgUserNews;
import com.szyciov.param.QueryParam;

public interface MessageMapper {

	List<UserNews> getOpUserNewsListByUserId(QueryParam queryParam);
	
	int getOpUserNewsListCountByUserId(String userId);
	
	int getOpUserNewsUnReadCountByUserId(String userId);
	
	UserNews getOpUserNewsById(String id);
	
	void updateOpUserNews(String userId);
	
	void updateReadNews(String id);
}

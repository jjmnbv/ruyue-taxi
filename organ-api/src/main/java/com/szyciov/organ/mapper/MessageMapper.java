package com.szyciov.organ.mapper;

import java.util.List;

import com.szyciov.entity.UserNews;
import com.szyciov.org.entity.OrgUserNews;
import com.szyciov.param.QueryParam;

public interface MessageMapper {
	
	List<UserNews> getOrgUserNewsListByUserId(QueryParam queryParam);
	
	int getOrgUserNewsListCountByUserId(String userId);
	
	int getOrgUserNewsUnReadCountByUserId(String userId);
	
	UserNews getOrgUserNewsById(String id);
	
	void updateOrgUserNews(String userId);
	
	void updateReadNews(String id);
}

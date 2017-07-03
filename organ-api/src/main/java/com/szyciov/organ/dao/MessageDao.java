package com.szyciov.organ.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.UserNews;
import com.szyciov.org.entity.OrgUserNews;
import com.szyciov.organ.mapper.MessageMapper;
import com.szyciov.param.QueryParam;

@Repository("MessageDao")
public class MessageDao {
	public MessageDao() {
	}

	private MessageMapper mapper;

	@Resource
	public void setMapper(MessageMapper mapper) {
		this.mapper = mapper;
	}
	
    public List<UserNews> getOrgUserNewsListByUserId(QueryParam queryParam) {
    	return mapper.getOrgUserNewsListByUserId(queryParam);
    }
	
	public int getOrgUserNewsListCountByUserId(String userId) {
		return mapper.getOrgUserNewsListCountByUserId(userId);
	}
	
	public int getOrgUserNewsUnReadCountByUserId(String userId) {
		return mapper.getOrgUserNewsUnReadCountByUserId(userId);
	}
	
	public UserNews getOrgUserNewsById(String id) {
		return mapper.getOrgUserNewsById(id);
	}
	
	public void updateOrgUserNews(String userId) {
		mapper.updateOrgUserNews(userId);
	}
	
	public void updateReadNews(String id) {
		mapper.updateReadNews(id);
	}
}

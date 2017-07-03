package com.szyciov.lease.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.UserNews;
import com.szyciov.lease.entity.LeUserNews;
import com.szyciov.lease.mapper.MessageMapper;
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
	
    public List<UserNews> getLeUserNewsListByUserId(QueryParam queryParam) {
    	return mapper.getLeUserNewsListByUserId(queryParam);
    }
	
    public int getLeUserNewsListCountByUserId(String userId) {
    	return mapper.getLeUserNewsListCountByUserId(userId);
    }
	
    public int getLeUserNewsUnReadCountByUserId(String userId) {
    	return mapper.getLeUserNewsUnReadCountByUserId(userId);
    }
	
    public UserNews getLeUserNewsById(String id) {
    	return mapper.getLeUserNewsById(id);
    }
	
    public void updateLeUserNews(String userId) {
    	mapper.updateLeUserNews(userId);
    }
	
    public void updateReadNews(String id) {
    	mapper.updateReadNews(id);
    }
}

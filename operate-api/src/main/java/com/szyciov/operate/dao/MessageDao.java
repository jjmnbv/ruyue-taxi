package com.szyciov.operate.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.UserNews;
import com.szyciov.operate.mapper.MessageMapper;
import com.szyciov.org.entity.OrgUserNews;
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
	
    public List<UserNews> getOpUserNewsListByUserId(QueryParam queryParam) {
    	return mapper.getOpUserNewsListByUserId(queryParam);
    }
	
	public int getOpUserNewsListCountByUserId(String userId) {
		return mapper.getOpUserNewsListCountByUserId(userId);
	}
	
	public int getOpUserNewsUnReadCountByUserId(String userId) {
		return mapper.getOpUserNewsUnReadCountByUserId(userId);
	}
	
	public UserNews getOpUserNewsById(String id) {
		return mapper.getOpUserNewsById(id);
	}
	
	public void updateOpUserNews(String userId) {
		mapper.updateOpUserNews(userId);
	}
	
	public void updateReadNews(String id) {
		mapper.updateReadNews(id);
	}
}

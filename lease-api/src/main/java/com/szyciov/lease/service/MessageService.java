package com.szyciov.lease.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.entity.UserNews;
import com.szyciov.lease.dao.MessageDao;
import com.szyciov.lease.entity.LeUserNews;
import com.szyciov.param.QueryParam;
import com.szyciov.util.PageBean;

@Service("MessageService")
public class MessageService {
	private MessageDao dao;

	@Resource(name = "MessageDao")
	public void setDao(MessageDao dao) {
		this.dao = dao;
	}
	
	public PageBean getLeUserNewsByUserId(QueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<UserNews> list = getLeUserNewsListByUserId(queryParam);
		int iTotalRecords = getLeUserNewsListCountByUserId(queryParam.getKey());
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<UserNews> getLeUserNewsListByUserId(QueryParam queryParam) {
    	return dao.getLeUserNewsListByUserId(queryParam);
    }
	
    public int getLeUserNewsListCountByUserId(String userId) {
    	return dao.getLeUserNewsListCountByUserId(userId);
    }
	
    public int getLeUserNewsUnReadCountByUserId(String userId) {
    	return dao.getLeUserNewsUnReadCountByUserId(userId);
    }
	
    public UserNews getLeUserNewsById(String id) {
		// 将消息标记为已读
		dao.updateReadNews(id);
		
		return dao.getLeUserNewsById(id);
	}

    public void updateLeUserNews(String userId) {
    	dao.updateLeUserNews(userId);
    }

}

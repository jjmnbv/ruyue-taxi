package com.szyciov.operate.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.entity.UserNews;
import com.szyciov.operate.dao.MessageDao;
import com.szyciov.org.entity.OrgUserNews;
import com.szyciov.param.QueryParam;
import com.szyciov.util.PageBean;

@Service("MessageService")
public class MessageService {
	private MessageDao dao;

	@Resource(name = "MessageDao")
	public void setDao(MessageDao dao) {
		this.dao = dao;
	}
	
	public PageBean getOpUserNewsByUserId(QueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<UserNews> list = getOpUserNewsListByUserId(queryParam);
		int iTotalRecords = getOpUserNewsListCountByUserId(queryParam.getKey());
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<UserNews> getOpUserNewsListByUserId(QueryParam queryParam) {
    	return dao.getOpUserNewsListByUserId(queryParam);
    }
	
	public int getOpUserNewsListCountByUserId(String userId) {
		return dao.getOpUserNewsListCountByUserId(userId);
	}
	
	public int getOpUserNewsUnReadCountByUserId(String userId) {
		return dao.getOpUserNewsUnReadCountByUserId(userId);
	}
	
	public UserNews getOpUserNewsById(String id) {
		// 将消息标记为已读
		dao.updateReadNews(id);
		
		return dao.getOpUserNewsById(id);
	}
	
	public void updateOpUserNews(String userId) {
		dao.updateOpUserNews(userId);
	}

}

package com.szyciov.organ.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.entity.UserNews;
import com.szyciov.org.entity.OrgUserNews;
import com.szyciov.organ.dao.MessageDao;
import com.szyciov.param.QueryParam;
import com.szyciov.util.PageBean;

@Service("MessageService")
public class MessageService {
	private MessageDao dao;

	@Resource(name = "MessageDao")
	public void setDao(MessageDao dao) {
		this.dao = dao;
	}
	
	public PageBean getOrgUserNewsByUserId(QueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<UserNews> list = getOrgUserNewsListByUserId(queryParam);
		int iTotalRecords = getOrgUserNewsListCountByUserId(queryParam.getKey());
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<UserNews> getOrgUserNewsListByUserId(QueryParam queryParam) {
    	return dao.getOrgUserNewsListByUserId(queryParam);
    }
	
	public int getOrgUserNewsListCountByUserId(String userId) {
		return dao.getOrgUserNewsListCountByUserId(userId);
	}
	
	public int getOrgUserNewsUnReadCountByUserId(String userId) {
		return dao.getOrgUserNewsUnReadCountByUserId(userId);
	}
	
	public UserNews getOrgUserNewsById(String id) {
		
		// 将消息标记为已读
		dao.updateReadNews(id);
		
		return dao.getOrgUserNewsById(id);
	}
	
	public void updateOrgUserNews(String userId) {
		dao.updateOrgUserNews(userId);
	}
}

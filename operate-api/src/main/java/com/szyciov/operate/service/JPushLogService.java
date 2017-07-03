package com.szyciov.operate.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.entity.PubJpushlog;
import com.szyciov.op.param.JPushLogQueryParam;
import com.szyciov.operate.dao.JPushLogDao;
import com.szyciov.util.PageBean;

@Service("JPushLogService")
public class JPushLogService {

	@Resource(name="JPushLogDao")
	public JPushLogDao dao; 
	
	public PageBean queryJPushLogByParam(JPushLogQueryParam param) {
		PageBean page=new PageBean();
		page.setsEcho(param.getsEcho());
		int totalRecords=queryJPushLogCount(param);
		List<PubJpushlog> list=queryJPushLog(param);
		page.setiTotalDisplayRecords(totalRecords);
		page.setiTotalDisplayRecords(totalRecords);
		page.setAaData(list);
		return page;
	}

	private List<PubJpushlog> queryJPushLog(JPushLogQueryParam param) {
		return dao.queryJPushLog(param);
	}

	private int queryJPushLogCount(JPushLogQueryParam param) {
		return dao.queryJPushLogCount(param);
	}

	public List<PubJpushlog> expoerExcel(JPushLogQueryParam param) {
		return dao.getAllJPushLog(param);
	}

}

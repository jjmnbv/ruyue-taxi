package com.szyciov.operate.service;

import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.operate.dao.TmpOrderManageDao;
import com.szyciov.util.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("TmpOrderManageService")
public class TmpOrderManageService {
	
    @Resource(name = "TmpOrderManageDao")
	private TmpOrderManageDao orderManageDao;

	public PageBean getOpOrderByQuery(OrderManageQueryParam queryParam) {
		String minUseTime = queryParam.getMinUseTime();
		if(StringUtils.isNotBlank(minUseTime)) {
			queryParam.setMinUseTime(minUseTime + ":00");
		}
		String maxUseTime = queryParam.getMaxUseTime();
		if(StringUtils.isNotBlank(maxUseTime)) {
			queryParam.setMaxUseTime(maxUseTime + ":59");
		}
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = null;
		int iTotalRecords = 0;
		switch (Integer.valueOf(queryParam.getType()).intValue()) {
			case 2:
				list = getOpCurrentOrderListByQuery(queryParam);
				iTotalRecords = getOpCurrentOrderCountByQuery(queryParam);
				break;
			case 3:
				list = getOpAbnormalOrderListByQuery(queryParam);
				iTotalRecords = getOpAbnormalOrderCountByQuery(queryParam);
				break;
			case 4:
				list = getOpWasabnormalOrderListByQuery(queryParam);
				iTotalRecords = getOpWasabnormalOrderCountByQuery(queryParam);
				break;
			case 5:
				list = getOpCompleteOrderListByQuery(queryParam);
				iTotalRecords = getOpCompleteOrderCountByQuery(queryParam);
				break;
			case 6:
				list = getOpWaitgatheringOrderListByQuery(queryParam);
				iTotalRecords = getOpWaitgatheringOrderCountByQuery(queryParam);
				break;
		}
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}

	public List<Map<String, Object>> getOpCurrentOrderListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpCurrentOrderListByQuery(queryParam);
	}

	public int getOpCurrentOrderCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpCurrentOrderCountByQuery(queryParam);
	}

	public List<Map<String, Object>> getOpAbnormalOrderListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpAbnormalOrderListByQuery(queryParam);
	}

	public int getOpAbnormalOrderCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpAbnormalOrderCountByQuery(queryParam);
	}

	public List<Map<String, Object>> getOpWasabnormalOrderListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpWasabnormalOrderListByQuery(queryParam);
	}

	public int getOpWasabnormalOrderCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpWasabnormalOrderCountByQuery(queryParam);
	}

	public List<Map<String, Object>> getOpCompleteOrderListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpCompleteOrderListByQuery(queryParam);
	}

	public int getOpCompleteOrderCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpCompleteOrderCountByQuery(queryParam);
	}

	public List<Map<String, Object>> getOpWaitgatheringOrderListByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpWaitgatheringOrderListByQuery(queryParam);
	}

	public int getOpWaitgatheringOrderCountByQuery(OrderManageQueryParam queryParam) {
		return orderManageDao.getOpWaitgatheringOrderCountByQuery(queryParam);
	}

}

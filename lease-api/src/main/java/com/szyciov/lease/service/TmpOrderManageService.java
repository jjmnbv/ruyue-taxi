package com.szyciov.lease.service;

import com.szyciov.lease.dao.TmpOrderManageDao;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.util.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("TmpOrderManageService")
public class TmpOrderManageService {

    @Resource(name = "TmpOrderManageDao")
	private TmpOrderManageDao tmpOrderManageDao;

	/**
	 * 查询订单列表数据
	 * @param queryParam
	 * @return
	 */
	public PageBean getOrgOrderByQuery(OrderManageQueryParam queryParam) {
		String minUseTime = queryParam.getMinUseTime();
		if (StringUtils.isNotBlank(minUseTime)) {
			queryParam.setMinUseTime(minUseTime + ":00");
		}
		String maxUseTime = queryParam.getMaxUseTime();
		if (StringUtils.isNotBlank(maxUseTime)) {
			queryParam.setMaxUseTime(maxUseTime + ":59");
		}
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = null;
		int iTotalRecords = 0;
		switch (Integer.valueOf(queryParam.getType()).intValue()) {
			case 2:
				list = getOrgCurrentOrderListByQuery(queryParam);
				iTotalRecords = getOrgCurrentOrderCountByQuery(queryParam);
				break;
			case 3:
				list = getOrgAbnormalOrderListByQuery(queryParam);
				iTotalRecords = getOrgAbnormalOrderCountByQuery(queryParam);
				break;
			case 4:
				list = getOrgWasabnormalOrderListByQuery(queryParam);
				iTotalRecords = getOrgWasabnormalOrderCountByQuery(queryParam);
				break;
			case 5:
				list = getOrgCompleteOrderListByQuery(queryParam);
				iTotalRecords = getOrgCompleteOrderCountByQuery(queryParam);
				break;
			case 6:
				list = getOrgWaitgatheringOrderListByQuery(queryParam);
				iTotalRecords = getOrgWaitgatheringOrderCountByQuery(queryParam);
				break;
		}
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}

	public List<Map<String, Object>> getOrgCurrentOrderListByQuery(OrderManageQueryParam queryParam) {
		return this.tmpOrderManageDao.getOrgCurrentOrderListByQuery(queryParam);
	}
	
	public int getOrgCurrentOrderCountByQuery(OrderManageQueryParam queryParam) {
		return this.tmpOrderManageDao.getOrgCurrentOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgAbnormalOrderListByQuery(OrderManageQueryParam queryParam) {
		return this.tmpOrderManageDao.getOrgAbnormalOrderListByQuery(queryParam);
	}
	
	public int getOrgAbnormalOrderCountByQuery(OrderManageQueryParam queryParam) {
		return this.tmpOrderManageDao.getOrgAbnormalOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgWasabnormalOrderListByQuery(OrderManageQueryParam queryParam) {
		return this.tmpOrderManageDao.getOrgWasabnormalOrderListByQuery(queryParam);
	}
	
	public int getOrgWasabnormalOrderCountByQuery(OrderManageQueryParam queryParam) {
		return this.tmpOrderManageDao.getOrgWasabnormalOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgCompleteOrderListByQuery(OrderManageQueryParam queryParam) {
		return this.tmpOrderManageDao.getOrgCompleteOrderListByQuery(queryParam);
	}
	
	public int getOrgCompleteOrderCountByQuery(OrderManageQueryParam queryParam) {
		return this.tmpOrderManageDao.getOrgCompleteOrderCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getOrgWaitgatheringOrderListByQuery(OrderManageQueryParam queryParam) {
		return this.tmpOrderManageDao.getOrgWaitgatheringOrderListByQuery(queryParam);
	}
	
	public int getOrgWaitgatheringOrderCountByQuery(OrderManageQueryParam queryParam) {
		return this.tmpOrderManageDao.getOrgWaitgatheringOrderCountByQuery(queryParam);
	}

}

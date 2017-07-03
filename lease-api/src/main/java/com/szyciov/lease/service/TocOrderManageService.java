package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.lease.dao.TocOrderManageDao;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.param.TocOrderManageQueryParam;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.util.PageBean;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONObject;

@Service("tocOrderManageService")
public class TocOrderManageService {
	private TocOrderManageDao dao;
	
	private TemplateHelper templateHelper = new TemplateHelper();

	@Resource(name = "TocOrderManageDao")
	public void setDao(TocOrderManageDao dao) {
		this.dao = dao;
	}
	
	public PageBean getNetAboutCarOrderByQuery(TocOrderManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getNetAboutCarOrderListByQuery(queryParam);
		int iTotalRecords = getNetAboutCarOrderListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}

	public List<Map<String, Object>> getNetAboutCarOrderListByQuery(TocOrderManageQueryParam queryParam) {
    	return dao.getNetAboutCarOrderListByQuery(queryParam);
    }
	
	public int getNetAboutCarOrderListCountByQuery(TocOrderManageQueryParam queryParam) {
		return dao.getNetAboutCarOrderListCountByQuery(queryParam);
	}
	
	public PageBean getTaxiOrderByQuery(TocOrderManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getTaxiOrderListByQuery(queryParam);
		int iTotalRecords = getTaxiOrderListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<Map<String, Object>> getTaxiOrderListByQuery(TocOrderManageQueryParam queryParam) {
    	return dao.getTaxiOrderListByQuery(queryParam);
    }
	
	public int getTaxiOrderListCountByQuery(TocOrderManageQueryParam queryParam) {
		return dao.getTaxiOrderListCountByQuery(queryParam);
	}
	
	public List<Map<String, Object>> getNetAboutCarOrderNOByQuery(String companyId, String orderNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("companyId", companyId);
		map.put("orderNo", orderNo);
		return dao.getNetAboutCarOrderNOByQuery(map);
    }
	
	public List<Map<String, Object>> getNetAboutCarOrderUserByQuery(String orderPerson) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderPerson", orderPerson);
		return dao.getNetAboutCarOrderUserByQuery(map);
	}
	
	public List<Map<String, Object>> getNetAboutCarOrderDriverByQuery(String companyId, String driver, String vehicleType) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("companyId", companyId);
		map.put("driver", driver);
		// 0-网约车，1-出租车
		map.put("vehicleType", vehicleType);
		return dao.getNetAboutCarOrderDriverByQuery(map);
	}
	
	public List<Map<String, Object>> getNetAboutCarOrderExport(TocOrderManageQueryParam queryParam) {
    	return dao.getNetAboutCarOrderExport(queryParam);
    }
	
	public List<Map<String, Object>> getTaxiOrderExport(TocOrderManageQueryParam queryParam) {
		return dao.getTaxiOrderExport(queryParam);
	}

	// 网约车详情
	public Map<String, Object> getOpOrderByOrderno(String orderno) {
		Map<String, Object> ret = dao.getOpOrderByOrderno(orderno);
		OrderCostParam param = new OrderCostParam();
		param.setOrderno(orderno);
		param.setUsetype(ret.get("usetype").toString());
		param.setOrdertype(ret.get("ordertype").toString());
		param.setHasunit(false);
		JSONObject json = templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApi") + "/OrderApi/GetOrderCost", HttpMethod.POST, null, param, JSONObject.class);
		ret.put("cost", (json.containsKey("cost")) ? json.get("cost") : 0);
		ret.put("rangecost", (json.containsKey("rangecost")) ? json.get("rangecost") : 0);
		ret.put("timecost", (json.containsKey("timecost")) ? json.get("timecost") : 0);
		ret.put("times", (json.containsKey("times")) ? (int)Math.ceil((int)json.get("times")/60.0) : 0);
		ret.put("slowtimes", (json.containsKey("slowtimes")) ? json.get("slowtimes") : 0);
		ret.put("mileage", (json.containsKey("mileage")) ? StringUtil.formatNum((double)json.get("mileage")/1000, 1) : 0);
		ret.put("startprice", (json.containsKey("startprice")) ? json.get("startprice") : 0);
		ret.put("timeprice", (json.containsKey("timeprice")) ? json.get("timeprice") : 0);
		ret.put("rangeprice", (json.containsKey("rangeprice")) ? json.get("rangeprice") : 0);
		ret.put("timetype", (json.containsKey("timetype")) ? json.get("timetype") : 0);

        ret.put("deadheadcost", json.containsKey("deadheadcost") ? json.get("deadheadcost") : 0); // 空驶费
        ret.put("nightcost", json.containsKey("nightcost") ? json.get("nightcost") : 0); // 夜间费
        ret.put("startprice", json.containsKey("startprice") ? json.get("startprice") : 0); // 起步价

		return ret;
	}
	
	public Map<String, Object> getFirstOpOrderByOrderno(String orderno) {
		return dao.getFirstOpOrderByOrderno(orderno);
	}
	
	public Map<String, Object> getOpSendOrderRecord(String orderno) {
		return dao.getOpSendOrderRecord(orderno);
	}
	
	public PageBean getOpChangeDriverByQuery(OrderManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getOpChangeDriverListByQuery(queryParam);
		int iTotalRecords = getOpChangeDriverCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<Map<String, Object>> getOpChangeDriverListByQuery(OrderManageQueryParam queryParam) {
		return dao.getOpChangeDriverListByQuery(queryParam);
	}
	
	public int getOpChangeDriverCountByQuery(OrderManageQueryParam queryParam) {
		return dao.getOpChangeDriverCountByQuery(queryParam);
	}
	
	public PageBean getOpOrderReviewByQuery(OrderManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getOpOrderReviewListByQuery(queryParam);
		int iTotalRecords = getOpOrderReviewCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<Map<String, Object>> getOpOrderReviewListByQuery(OrderManageQueryParam queryParam) {
		return dao.getOpOrderReviewListByQuery(queryParam);
	}
	
	public int getOpOrderReviewCountByQuery(OrderManageQueryParam queryParam) {
		return dao.getOpOrderReviewCountByQuery(queryParam);
	}
	
	/**
	 * 分页查询客服备注列表
	 * @param queryParam
	 * @return
	 */
	public PageBean getOpOrderCommentByQuery(OrdercommentQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = getOpOrderCommentListByQuery(queryParam);
		int iTotalRecords = getOpOrderCommentCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<Map<String, Object>> getOpOrderCommentListByQuery(OrdercommentQueryParam queryParam) {
		return dao.getOpOrderCommentListByQuery(queryParam);
	}
	
	public int getOpOrderCommentCountByQuery(OrdercommentQueryParam queryParam) {
		return dao.getOpOrderCommentCountByQuery(queryParam);
	}
	
	
	// 出租车详情
	/**
	 * 查询订单详情
	 * @param orderno
	 * @return
	 */
	public Map<String, Object> getOpTaxiOrderByOrderno(String orderno) {
		return dao.getOpTaxiOrderByOrderno(orderno);
	}
	
	/**
	 * 查询原始订单数据(第一条复核记录)
	 * @param orderno
	 * @return
	 */
	public Map<String, Object> getFirstTaxiOrderByOrderno(String orderno) {
		return dao.getFirstTaxiOrderByOrderno(orderno);
	}
	
	/**
	 * 查询人工派单记录
	 * @param orderno
	 * @return
	 */
	public Map<String, Object> getOpSendTaxiOrderRecord(String orderno) {
		return dao.getOpSendTaxiOrderRecord(orderno);
	}
	
	/**
	 * 查询更换司机记录
	 * @param queryParam
	 * @return
	 */
	public PageBean getOpChangeDriverList(OrderManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = dao.getOpChangeDriverList(queryParam);
		int iTotalRecords = list.size();
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	/**
	 * 查询更换车辆记录
	 * @param queryParam
	 * @return
	 */
	public PageBean getOpChangeVehicleList(OrderManageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = dao.getOpChangeVehicleList(queryParam);
		int iTotalRecords = list.size();
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	/**
	 * 分页查询订单复核记录
	 * @param object
	 * @return
	 */
	public PageBean getOpTaxiOrderReviewByQuery(OpTaxiOrderReview object) {
		PageBean pageBean = new PageBean();
		List<Map<String, Object>> list = dao.getOpTaxiOrderReviewListByQuery(object);
		int iTotalRecords = dao.getOpTaxiOrderReviewCountByQuery(object);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	/**
	 * 查询客服备注列表
	 * @param queryParam
	 * @return
	 */
	public PageBean getOpTaxiOrderCommentByQuery(OrdercommentQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = dao.getOpTaxiOrderCommentListByQuery(queryParam);
		int iTotalRecords = dao.getOpTaxiOrderCommentCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	
}

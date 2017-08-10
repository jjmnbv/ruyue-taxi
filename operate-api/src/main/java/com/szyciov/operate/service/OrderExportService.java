package com.szyciov.operate.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.szyciov.op.entity.OrderExportEntity;
import com.szyciov.op.param.OrderExportParam;
import com.szyciov.operate.dao.OrderExportDao;
import com.szyciov.util.PageBean;

@Service("OrderExportService")
public class OrderExportService {
	private OrderExportDao dao;

	@Resource(name = "OrderExportDao")
	public void setDao(OrderExportDao dao) {
		this.dao = dao;
	}
	public PageBean getOrderExportData(OrderExportEntity queryParam){
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrderExportParam> exportDataList = getOrderExportDataByQuery(queryParam);
		int iTotalRecords = getOrderExportDataCount(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(exportDataList);
		return pageBean;
	}
	public List<OrderExportParam> getOrderExportDataByQuery(OrderExportEntity queryParam) {
		return dao.getOrderExportDataByQuery(queryParam);
	}
	
	public int getOrderExportDataCount(OrderExportEntity queryParam) {
		return dao.getOrderExportDataCount(queryParam);
	}
	public List<Map<String, Object>> getDriver(OrderExportEntity orderExportEntity){
		return dao.getDriver(orderExportEntity);
	}
	public List<Map<String, Object>> getPassage(OrderExportEntity orderExportEntity){
		return dao.getPassage(orderExportEntity);
	}
	public List<Map<String, Object>> getAllOrganid(OrderExportEntity orderExportEntity){
		return dao.getAllOrganid(orderExportEntity);
	}
	public List<OrderExportParam> exportOrders(OrderExportEntity orderExportEntity)  {
		return dao.exportOrders(orderExportEntity);
	}
	public List<Map<String, Object>> getLeasescompany(OrderExportEntity orderExportEntity)  {
		return dao.getLeasescompany(orderExportEntity);
	}

}

package com.szyciov.operate.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.param.LeDriverorderstatisticsParam;
import com.szyciov.op.entity.OrderExportEntity;
import com.szyciov.op.param.OrderExportParam;
import com.szyciov.operate.mapper.OrderExportMapper;

@Repository("OrderExportDao")
public class OrderExportDao {
	private OrderExportMapper mapper;
	@Resource
	public void setMapper(OrderExportMapper mapper) {
		this.mapper = mapper;
	}
	public List<OrderExportParam> getOrderExportDataByQuery(OrderExportEntity queryParam){
		return mapper.getOrderExportDataByQuery(queryParam);
	}
	public int getOrderExportDataCount(OrderExportEntity queryParam){
		return mapper.getOrderExportDataCount(queryParam);
	}
	public  List<Map<String, Object>> getDriver(OrderExportEntity orderExportEntity)  {
		return mapper.getDriver(orderExportEntity);
	}
	public  List<Map<String, Object>> getPassage(OrderExportEntity orderExportEntity)  {
		return mapper.getPassage(orderExportEntity);
	}
	public  List<Map<String, Object>> getAllOrganid(OrderExportEntity orderExportEntity)  {
		return mapper.getAllOrganid(orderExportEntity);
	}
	public List<OrderExportParam> exportOrders(OrderExportEntity orderExportEntity)  {
		return mapper.exportOrders(orderExportEntity);
	}

}

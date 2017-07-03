package com.szyciov.operate.mapper;

import java.util.List;
import java.util.Map;
import com.szyciov.op.entity.OrderExportEntity;
import com.szyciov.op.param.OrderExportParam;

public interface OrderExportMapper {
	List<OrderExportParam> getOrderExportDataByQuery(OrderExportEntity queryParam);
	int getOrderExportDataCount(OrderExportEntity queryParam);
	List<Map<String, Object>> getDriver(OrderExportEntity orderExportEntity);
	List<Map<String, Object>> getPassage(OrderExportEntity orderExportEntity);
	List<Map<String, Object>> getAllOrganid(OrderExportEntity orderExportEntity);
	List<OrderExportParam> exportOrders(OrderExportEntity orderExportEntity);
}

package com.szyciov.driver.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.entity.OrderStatistics;
import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.driver.param.NewsParam;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.driver.param.OrderStatisticsParam;
import com.szyciov.entity.PubJpushlog;
import com.szyciov.entity.PubSendrules;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.GetSendInfoParam;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.PubPushLogParam;

public interface OrderMapper {
	List<PubDriverNews> getNewsByType(NewsParam param);
	PubDriverNews getNewsById(NewsParam param);
	List<OrderInfoDetail> getOrderInfoList(OrderListParam param);
	int getOrderTotalCount(OrderListParam param);
	OrderInfoDetail getOrgOrderInfoById(OrderApiParam param);
	OrderInfoDetail getOpOrderInfoById(OrderApiParam param);
	PubSendrules getSendRule(GetSendInfoParam param);
	PubSendrules getOpSendRule(GetSendInfoParam param);
	List<OrderStatistics> opOrderStatistics(OrderStatisticsParam param);
	List<OrderStatistics> orgOrderStatistics(OrderStatisticsParam param);
	OrgOrder getOrgOrder(@Param("orderno") String orderno);
	OpOrder getOpOrder(@Param("orderno") String orderno);
	OpTaxiOrder getOpTaxiOrder(@Param("orderno") String orderno);
	OrgOrganCompanyRef getOrgBalance(OrderCostParam param);
	PubCityAddr getPubCity(@Param("city") String city);
	PubDictionary getPubDictionaryById(@Param("id") String id);
	PubJpushlog getPubJpushlog(PubPushLogParam param);
	void updateOrgBalance(OrgOrganCompanyRef param);
	void updateOrder(OrderInfoDetail param);
	void updateOrgOrder(OrgOrder param);
	void updateOpOrder(OpOrder param);
	void updateNews(PubDriverNews param);
	void updateNewsAllRead(PubDriverNews param);
	void updatePubJpushlog(PubJpushlog param);
}


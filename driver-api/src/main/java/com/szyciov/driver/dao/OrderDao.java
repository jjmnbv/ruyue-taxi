package com.szyciov.driver.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.driver.entity.OrderStatistics;
import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.driver.mapper.OrderMapper;
import com.szyciov.driver.param.NewsParam;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.driver.param.OrderListParam;
import com.szyciov.driver.param.OrderStatisticsParam;
import com.szyciov.entity.PubJpushlog;
import com.szyciov.entity.PubSendrules;
import com.szyciov.lease.entity.LeCompanyRulesRef;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.param.GetSendInfoParam;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.param.OrderApiParam;
import com.szyciov.param.PubPushLogParam;

@Repository("OrderDao")
public class OrderDao {
	public OrderDao() {
	}
	
	private OrderMapper mapper;

	@Resource
	public void setMapper(OrderMapper mapper) {
		this.mapper = mapper;
	}
	
	/**
	 * 根据类型获取司机消息列表
	 * @param param
	 * @return {@link PubDriverNews}
	 * @see {@link PollMessageParam}
	 */
	public List<PubDriverNews> getNewsByType(NewsParam param){
		return mapper.getNewsByType(param);
	}
	
	/**
	 * 获取订单列表(可以根据状态或时间段或二者兼有)
	 * @param param
	 * @return {@link OrderInfoDetail}
	 * @see {@link OrderListParam}
	 */
	public List<OrderInfoDetail> getOrderList(OrderListParam param){
		return mapper.getOrderInfoList(param);
	}
	
	/**
	 * 获取订单列表总数(可以根据状态或时间段或二者兼有)
	 * @param param
	 * @return
	 * @see {@link OrderListParam}
	 */
	public int getOrderTotalCount(OrderListParam param){
		return mapper.getOrderTotalCount(param);
	}
	
	/**
	 * 通过ID获取订单信息
	 * @param param
	 * @return {@link OrderInfoDetail}
	 * @see {@link OrderApiParam}
	 */
	public OrderInfoDetail getOrderInfoById(OrderApiParam param){
		if(0==param.getOrderprop()){
			return mapper.getOrgOrderInfoById(param);
		}else{
			return mapper.getOpOrderInfoById(param);
		}
	}
	
	/**
	 * 变更订单状态
	 * @param param
	 * @see {@link OrderInfoDetail}
	 */
	public void updateOrder(OrderInfoDetail param){
		mapper.updateOrder(param);
	}
	
	/**
	 * 订单统计(会根据参数查询个人或机构订单表)
	 * @param param
	 * @return {@link OrderStatistics}
	 * @see {@link OrderStatisticsParam}
	 */
	public List<OrderStatistics> orderStatistics(OrderStatisticsParam param){
		if(param.getType() == 0){
			return mapper.orgOrderStatistics(param);
		}else{
			return mapper.opOrderStatistics(param);
		}
	}
	
	/**
	 * 获取租赁端派单规则(标记是否即刻用车)
	 * @param param
	 * @return {@link LeSendRules}
	 * @see {@link GetSendInfoParam}
	 */
	public PubSendrules getSendRule(GetSendInfoParam param){
		return mapper.getSendRule(param);
	}
	
	/**
	 * 获取运管端派单规则(标记是否即刻用车)
	 * @param param
	 * @return {@link OpSendRules}
	 * @see {@link GetSendInfoParam}
	 */
	public PubSendrules getOpSendRule(GetSendInfoParam param){
		return mapper.getOpSendRule(param);
	}
	
	/**
	 * 根据ID获取司机消息
	 * @param param
	 * @return  {@link PubDriverNews}
	 * @see {@link NewsParam}
	 */
	public PubDriverNews getNewsById(NewsParam param){
		return mapper.getNewsById(param);
	}
	
	/**
	 * 更新司机消息(单条)
	 * @param news
	 * @see {@link PubDriverNews}
	 */
	public void updateNews(PubDriverNews news){
		mapper.updateNews(news);
	}
	
	/**
	 * 更新司机消息(所有)
	 * @param news
	 * @see {@link PubDriverNews}
	 */
	public void updateNewsAllRead(PubDriverNews news){
		mapper.updateNewsAllRead(news);
	}
	
	/**
	 * 获取机构订单
	 * @param orderno
	 * @return {@link OrgOrder}
	 */
	public OrgOrder getOrgOrder(String orderno){
		return mapper.getOrgOrder(orderno);
	}
	
	/**
	 * 获取个人订单
	 * @param orderno
	 * @return {@link OpOrder}
	 */
	public OpOrder getOpOrder(String orderno){
		return mapper.getOpOrder(orderno);
	}
	
	/**
	 * 获取机构余额
	 * @param param
	 * @return {@link LeCompanyRulesRef}
	 * @see {@link OrderCostParam}
	 */
	public OrgOrganCompanyRef getOrgBalance(OrderCostParam param){
		return mapper.getOrgBalance(param);
	}
	
	/**
	 * 更新机构余额
	 * @param param
	 * @see {@link OrgOrganCompanyRef}
	 */
	public void updateOrgBalance(OrgOrganCompanyRef param){
		mapper.updateOrgBalance(param);
	}
	
	/**
	 * 更新机构订单
	 * @param order
	 * @see {@linkplain OrgOrder}
	 */
	public void updateOrgOrder(OrgOrder order){
		mapper.updateOrgOrder(order);
	}
	
	/**
	 * 更新个人订单
	 * @param order
	 * @see {@linkplain OpOrder}
	 */
	public void updateOpOrder(OpOrder order){
		mapper.updateOpOrder(order);
	}
	
	/**
	 * 根据城市名获取城市对象
	 * @param city
	 * @return {@linkplain PubCityAddr}
	 */
	public PubCityAddr getPubCity(String city){
		return mapper.getPubCity(city);
	}
	
	/**
	 * 获取运管端出租车订单
	 * @param orderno
	 * @return
	 */
	public OpTaxiOrder getOpTaxiOrder(String orderno){
		return mapper.getOpTaxiOrder(orderno);
	}
	
	/**
	 * 根据ID获取字典信息
	 * @param id
	 * @return
	 */
	public PubDictionary getPubDictionaryById(String id){
		return mapper.getPubDictionaryById(id); 
	}
	
	/**
	 * 根据司机ID或手机号和订单号获取推送日志
	 * @param param
	 * @return
	 */
	public PubJpushlog getPubJpushlog(PubPushLogParam param){
		return mapper.getPubJpushlog(param);
	}
	
	/**
	 * 保存司机举手记录
	 * @param param
	 * @return
	 */
	public void saveDriverHansUp(PubJpushlog jpushlog){
		mapper.updatePubJpushlog(jpushlog);
	}
}

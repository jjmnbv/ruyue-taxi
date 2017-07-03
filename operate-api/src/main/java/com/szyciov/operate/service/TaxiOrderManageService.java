package com.szyciov.operate.service;

import com.szyciov.driver.entity.OrderInfoMessage;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.OrdersortColumn;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.enums.ReviewState;
import com.szyciov.entity.CancelParty;
import com.szyciov.entity.OrderMessageFactory;
import com.szyciov.entity.OrderMessageFactory.OrderMessageType;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.UserNews;
import com.szyciov.enums.DriverEnum;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.message.TaxiOrderMessage;
import com.szyciov.op.entity.OpTaxiOrder;
import com.szyciov.op.entity.OpTaxiOrderReview;
import com.szyciov.op.entity.OpTaxidriverchanges;
import com.szyciov.op.entity.OpTaxiordercomment;
import com.szyciov.op.entity.OpTaxisendrecord;
import com.szyciov.op.entity.OpTaxivehiclechanges;
import com.szyciov.op.entity.OpUser;
import com.szyciov.op.entity.PeUserRefund;
import com.szyciov.op.entity.PubDriver;
import com.szyciov.op.entity.PubVehicle;
import com.szyciov.operate.dao.PubDriverDao;
import com.szyciov.operate.dao.PubVehicleDao;
import com.szyciov.operate.dao.TaxiOrderManageDao;
import com.szyciov.operate.dao.TaxiSendrulesDao;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrdercommentQueryParam;
import com.szyciov.param.UserNewsParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.StringUtil;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;
import com.szyciov.util.latlon.LatLonUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("TaxiOrderManageService")
public class TaxiOrderManageService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	private TaxiOrderManageDao dao;
	@Resource(name = "TaxiOrderManageDao")
	public void setDao(TaxiOrderManageDao dao) {
		this.dao = dao;
	}
	
	private PubVehicleDao vehicleDao;
	@Resource(name = "PubVehicleDao")
	public void setVehicleDao(PubVehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}
	
	private TaxiSendrulesDao sendrulesDao;
	@Resource(name = "TaxiSendrulesDao")
	public void setSendrulesDao(TaxiSendrulesDao sendrulesDao) {
		this.sendrulesDao = sendrulesDao;
	}
	
	private PubDriverDao driverDao;
	@Resource(name = "PubDriverDao")
	public void setDriverDao(PubDriverDao driverDao) {
		this.driverDao = driverDao;
	}
	
	/**
	 * 分页查询订单列表
	 * @param queryParam
	 * @return
	 */
	public PageBean getOpTaxiOrderByQuery(OrderManageQueryParam queryParam) {
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
			case 0:
				list = dao.getOpMissTaxiOrderListByQuery(queryParam);
				iTotalRecords = dao.getOpMissTaxiOrderCountByQuery(queryParam);
				break;
			case 1:
				list = dao.getOpLabourTaxiOrderListByQuery(queryParam);
				iTotalRecords = dao.getOpLabourTaxiOrderCountByQuery(queryParam);
				break;
			case 2:
				list = dao.getOpCurrentTaxiOrderListByQuery(queryParam);
				iTotalRecords = dao.getOpCurrentTaxiOrderCountByQuery(queryParam);
				break;
			case 3:
				list = dao.getOpAbnormalTaxiOrderListByQuery(queryParam);
				iTotalRecords = dao.getOpAbnormalTaxiOrderCountByQuery(queryParam);
				break;
			case 4:
				list = dao.getOpWasabnormalTaxiOrderListByQuery(queryParam);
				iTotalRecords = dao.getOpWasabnormalTaxiOrderCountByQuery(queryParam);
				break;
			case 5:
				list = dao.getOpCompleteTaxiOrderListByQuery(queryParam);
				iTotalRecords = dao.getOpCompleteTaxiOrderCountByQuery(queryParam);
				break;
			case 6:
				list = dao.getOpWaitgatheringTaxiOrderListByQuery(queryParam);
				iTotalRecords = dao.getOpWaitgatheringTaxiOrderCountByQuery(queryParam);
				break;
		}
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	/**
	 * 查询订单详情
	 * @param orderno
	 * @return
	 */
	public Map<String, Object> getOpTaxiOrderByOrderno(String orderno) {
		return dao.getOpTaxiOrderByOrderno(orderno);
	}
	
	/**
	 * 拒绝复核
	 * @param params
	 * @return
	 */
	public Map<String, Object> opTaxiOrderReject(String orderno) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "success");
		result.put("message", "操作成功");
		//修改订单复核状态
		OpTaxiOrder order = new OpTaxiOrder();
		order.setOrderno(orderno);
		order.setReviewstatus(ReviewState.NOTREVIEW.state);
		dao.orderReject(order);
		//发送消息
		order = dao.getOpTaxiOrder(orderno);
		//司机申请复核时，添加司机消息
		if("1".equals(order.getReviewperson())) {
			createDriverNews(order, 0, OrderMessageType.REVIEWORDER);
		}
		TaxiOrderMessage message = new TaxiOrderMessage(order, TaxiOrderMessage.TAXI_NOTREVIEW);
		MessageUtil.sendMessage(message);
		return result;
	}
	
	/**
	 * 申请复核
	 * @param params
	 * @return
	 */
	public Map<String, Object> applyRecheckTaxiOrder(OpTaxiOrder object) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "success");
		result.put("message", "操作成功");
		
		//验证当前订单状态
		OpTaxiOrder order = dao.getOpTaxiOrder(object.getOrderno());
		if (!OrderState.SERVICEDONE.state.equals(order.getOrderstatus())
				|| ReviewState.WAITFORREVIEW.state.equals(order.getReviewstatus())) {
			result.put("status", "fail");
			result.put("message", "当前订单状态不允许申请复核");
			return result;
		}
		
		//更新订单
		object.setReviewstatus(ReviewState.WAITFORREVIEW.state);
		dao.updateOpTaxiOrderByOrderno(object);
		return result;
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
	 * 订单复核
	 * @param object
	 * @return
	 */
	public Map<String, Object> opTaxiOrderReview(OpTaxiOrderReview object) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		OpTaxiOrder order = dao.getOpTaxiOrder(object.getOrderno());
		//判断当前订单状态
		if(!ReviewState.WAITFORREVIEW.state.equals(order.getReviewstatus())) {
			result.put("status", "fail");
			result.put("message", "当前订单状态无法复核");
			return result;
		}
		int schedulefee = order.getSchedulefee(); //调度费
		//订单实际行程费用金额
		Double actualpayamount = order.getActualpayamount();
		if(null == actualpayamount) {
			actualpayamount = order.getOrderamount();
		}
		//订单应付行程费用金额
		Double shouldpayamount = order.getShouldpayamount();
		if(null == shouldpayamount) {
			shouldpayamount = order.getOrderamount();
		}
		//复核后的金额比实际支付金额小时，需要给乘客退款，此时实际金额不一定与订单金额相等
		if(object.getReviewedprice() < actualpayamount) {
			order.setActualpayamount(object.getReviewedprice());
		}
		//复核后的金额就是应付金额
		order.setShouldpayamount(object.getReviewedprice());
		//实际差异金额
		double differPrice = actualpayamount - object.getReviewedprice();
		//订单未支付时修改订单数据
		if(StringUtils.isBlank(order.getPaymentstatus()) || PayState.NOTPAY.state.equals(order.getPaymentstatus())) {
			order.setOrderamount(object.getReviewedprice());
			order.setActualpayamount(object.getReviewedprice());
			//此时订单总金额为0时，修改当前订单状态未已支付
			if(order.getOrderamount() == 0 && schedulefee == 0) {
				order.setPaymentstatus(PayState.PAYED.state);
			}
		}
		order.setReviewstatus(ReviewState.REVIEWED.state);
		//更新订单复核记录数据
		object.setId(GUIDGenerator.newGUID());
		object.setStarttime(order.getStarttime());
		object.setEndtime(order.getEndtime());
		int times = (int) Math.ceil((order.getEndtime().getTime() - order.getStarttime().getTime())/1000.0/60); //服务时长
		object.setTimes((double) times * 60);
		object.setMileage(order.getMileage());
		object.setReviewperson(order.getReviewperson());
		object.setReason(order.getOrderreason());
		//更新数据库
		dao.updateOpTaxiorderReview(order);
		//查询最近一次复核记录
		OpTaxiOrderReview orderReview = dao.getOpTaxiOrderreviewLastByOrderno(object.getOrderno());
		if(null != orderReview) {
			object.setRaworderamount(orderReview.getReviewedprice());
		} else {
			object.setRaworderamount(order.getOriginalorderamount());
		}
		object.setPrice(StringUtil.formatNum(order.getShouldpayamount() - order.getActualpayamount(), 1));
		dao.insertOpTaxiOrderReview(object);
		
		//添加司机消息类
		if("1".equals(object.getReviewperson())) {
			createDriverNews(order, differPrice, OrderMessageType.REVIEWORDER);
		}
		
		//已支付且乘客多给钱才需要添加退款信息
		if(!PayState.NOTPAY.state.equals(order.getPaymentstatus()) && differPrice > 0) {
			//添加退款信息
			PeUserRefund userRefund = new PeUserRefund();
			userRefund.setId(GUIDGenerator.newGUID());
			userRefund.setUserId(order.getUserid());
			userRefund.setOrderNo(object.getOrderno());
			userRefund.setAmount(BigDecimal.valueOf(differPrice));
			userRefund.setRefundStatus("0");
			userRefund.setRemark(object.getOpinion());
			userRefund.setCreater(object.getOperator());
			userRefund.setUpdater(object.getOperator());
			userRefund.setStatus(1);
			dao.insertPeUserRefund(userRefund);
		}
		
		//复核短信额外参数
		Map<String, Object> smsParams = new HashMap<String, Object>();
		smsParams.put("price", differPrice);
		order.setOrderamount(object.getReviewedprice());
		TaxiOrderMessage message = new TaxiOrderMessage(order, TaxiOrderMessage.TAXI_EXCEPTIONORDER, smsParams);
		MessageUtil.sendMessage(message);
		
		result.put("status", "success");
		result.put("message", "操作成功");
		return result;
	}
	
	/**
	 * 查询可派单司机
	 * @param queryParam
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageBean getDriverByQuery(OrderManageQueryParam queryParam) {
		if(queryParam.getDistance() > 0) {
			double[] around = LatLonUtil.getAround(queryParam.getOrderLat(), queryParam.getOrderLon(), queryParam.getDistance());
			queryParam.setMinLat(around[0]);
			queryParam.setMinLon(around[1]);
			queryParam.setMaxLat(around[2]);
			queryParam.setMaxLon(around[3]);
		}
		
		OpTaxiOrder order = dao.getOpTaxiOrder(queryParam.getOrderNo());
		queryParam.setIsusenow(order.isIsusenow() ? 1 : 0);
		queryParam.setUsetime(order.getUsetime());
		queryParam.setEstimatedEndtime(StringUtil.addDate(order.getUsetime(), (order.getEstimatedtime() + 60) * 60));
		queryParam.setUserId(order.getUserid());
		
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<Map<String, Object>> list = dao.getDriverListByQuery(queryParam);
		
		if(null != list && !list.isEmpty()) {
			Iterator<Map<String, Object>> ite = list.iterator();
			while(ite.hasNext()) {
				Map<String, Object> map = ite.next();
				
				BaiduApiQueryParam baiduparam = new BaiduApiQueryParam();
				baiduparam.setOrderStartLat(queryParam.getOrderLat());
				baiduparam.setOrderStartLng(queryParam.getOrderLon());
				if(null != map.get("lat")) {
					baiduparam.setOrderEndLat((double) map.get("lat"));
				} else {
					baiduparam.setOrderEndLat(0);
				}
				if(null != map.get("lng")) {
					baiduparam.setOrderEndLng((double) map.get("lng"));
				} else {
					baiduparam.setOrderEndLng(0);
				}
				
				Map<String,Object> hintMap = templateHelper.dealRequestWithFullUrlToken(
						SystemConfig.getSystemProperty("carserviceApi") +"/BaiduApi/GetMileageInfo", 
						HttpMethod.POST, null, baiduparam, Map.class);
				
				if(hintMap != null && (Integer)hintMap.get("status") == Retcode.OK.code){
					map.put("distance", hintMap.get("distance"));
					map.put("duration", hintMap.get("duration"));
				} else {
					map.put("distance", 0);
					map.put("duration", 0);
				}
				
			}
		}
		
		int iTotalRecords = dao.getDriverCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	/**
	 * 人工派单/更换车辆车牌联想下拉框(select)
	 * @param params
	 * @return
	 */
	public List<Map<String, String>> getTaxiPlatonoBySelect(Map<String, String> params) {
		return dao.getTaxiPlatonoBySelect(params);
	}
	
	/**
	 * 人工派单
     * 已于2017/05/27转移到carservice-api工程下
     * 路径：api/TaxiOrderManage/ManualSendOrder
	 * @param object
	 * @return
	 */
	@Deprecated
	public Map<String, String> manualSendOrder(OpTaxiOrder object) {
		Map<String, String> result = new HashMap<String, String>();
		
		OpTaxiOrder order = dao.getOpTaxiOrder(object.getOrderno());
		//验证订单是否已取消
		if(OrderState.CANCEL.state.equals(order.getOrderstatus())) {
			result.put("status", "fail");
			result.put("message", "人工派单超时，订单已取消");
			return result;
		}
		//验证当前是否有系统+人工模式
		OpUser user = new OpUser();
		user.setId(object.getOperator());
		int ruleCount = sendrulesDao.getOpSendmodelCountByUser(user);
		if(ruleCount == 0) {
			result.put("status", "fail");
			result.put("message", "人工派单超时，订单已取消");
			return result;
		}
		//验证当前订单是否已被其他客服处理
		if(!OrderState.MANTICSEND.state.equals(order.getOrderstatus())) {
			result.put("status", "fail");
			result.put("message", "该订单已被其他客服处理");
			return result;
		}
		//验证当前司机是否符合条件
		if(!checkDriver(order, object.getVehicleid())) {
			result.put("status", "fail");
			result.put("message", "该司机已接其他订单，请更换司机指派");
			return result;
		}
		PubVehicle pubVehicle = vehicleDao.getById(object.getVehicleid());
		//更新订单数据
		updateOrder(object, order, pubVehicle);
		//添加人工派单记录
		OpTaxisendrecord taxisendrecord = new OpTaxisendrecord();
		taxisendrecord.setId(GUIDGenerator.newGUID());
		taxisendrecord.setOrderno(object.getOrderno());
		taxisendrecord.setDriverid(order.getDriverid());
		taxisendrecord.setOperator(object.getOperator());
		taxisendrecord.setReason(object.getOrderreason());
		dao.insertOpTaxisendrecord(taxisendrecord);
		
		//发送消息
		createDriverNews(order, 0, OrderMessageType.MANTICORDER);
		TaxiOrderMessage message = new TaxiOrderMessage(order, TaxiOrderMessage.TAXI_MANTICORDER);
		if(!order.isIsusenow()) { //预约订单查询司机是否正在服务
			message.setRunTime(getDriverEndtime(order.getDriverid()));
		}
		MessageUtil.sendMessage(message);
		result.put("status", "success");
		result.put("message", "派单成功");
		return result;
	}
	
	/**
	 * 派单失败
	 * @param orderno
	 * @return
	 */
	public Map<String, String> sendFail(String orderno) {
		Map<String, String> result = new HashMap<String, String>();
		
		OpTaxiOrder order = dao.getOpTaxiOrder(orderno);
		//验证订单是否已取消
		if(OrderState.CANCEL.state.equals(order.getOrderstatus())) {
			result.put("status", "fail");
			result.put("message", "人工派单超时，订单已取消");
			return result;
		}
		//验证订单是否已被指派
		if(!OrderState.MANTICSEND.state.equals(order.getOrderstatus())) {
			result.put("status", "fail");
			result.put("message", "该订单已被其他客服处理");
			return result;
		}
		//取消订单
		order.setOrderstatus(OrderState.CANCEL.state);
		order.setCancelparty(CancelParty.OPERATOR.code);
		order.setCanceltime(new Date());
		order.setOrdersortcolumn(OrdersortColumn.CANCEL.state);
		dao.updateOpTaxiOrderByOrderno(order);
		
		TaxiOrderMessage message = new TaxiOrderMessage(order, TaxiOrderMessage.TAXI_SENDORDERFAIL);
		MessageUtil.sendMessage(message);
		result.put("status", "success");
		result.put("message", "取消成功");
		return result;
	}
	
	/**
	 * 更换车辆
	 * @param object
	 * @return
	 */
	public Map<String, String> changeVehicle(OpTaxiOrder object) {
		Map<String, String> result = new HashMap<String, String>();
		
		OpTaxiOrder order = dao.getOpTaxiOrder(object.getOrderno());
		String beforeVehicle = order.getVehicleid();
		String beforeplateno = order.getPlateno();
		String beforeDriverid = order.getDriverid();
		//验证当前订单状态
		if(!OrderState.WAITSTART.state.equals(order.getOrderstatus())) {
			result.put("status", "fail");
			result.put("message", "该订单当前状态不可更换车辆");
			return result;
		}
		//验证司机是否满足条件
		if(!checkDriver(order, object.getVehicleid())) {
			result.put("status", "fail");
			result.put("message", "该司机已接其他订单，请更换司机指派");
			return result;
		}
		//查询车辆详细信息
		PubVehicle pubVehicle = vehicleDao.getById(object.getVehicleid());
		//更新订单数据
		updateOrder(object, order, pubVehicle);
		//添加更换车辆信息
		OpTaxivehiclechanges taxivehiclechanges = new OpTaxivehiclechanges();
		taxivehiclechanges.setId(GUIDGenerator.newGUID());
		taxivehiclechanges.setOrderno(order.getOrderno());
		taxivehiclechanges.setBeforevehicleid(beforeVehicle);
		taxivehiclechanges.setBeforeplateno(beforeplateno);
		taxivehiclechanges.setAftervehicleid(order.getVehicleid());
		taxivehiclechanges.setAfterplateno(order.getPlateno());
		taxivehiclechanges.setOrderdriverid(order.getDriverid());
		taxivehiclechanges.setReason(object.getOrderreason());
		taxivehiclechanges.setOperator(object.getOperator());
		dao.insertOpTaxivehiclechanges(taxivehiclechanges);
		//添加更换司机记录
		OpTaxidriverchanges taxidriverchanges = new OpTaxidriverchanges();
		taxidriverchanges.setId(GUIDGenerator.newGUID());
		taxidriverchanges.setOrderno(object.getOrderno());
		taxidriverchanges.setBeforedriverid(beforeDriverid);
		taxidriverchanges.setAfterdriverid(order.getDriverid());
		taxidriverchanges.setReason("更换车辆");
		taxidriverchanges.setOperator(object.getOperator());
		dao.insertOpTaxidriverchanges(taxidriverchanges);
		
		//发送消息
		createDriverNews(order, 0, OrderMessageType.MANTICORDER);
		
		PubDriver pubDriver = driverDao.getByPubDriverId(beforeDriverid);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("olddrivername", pubDriver.getName());
		params.put("olddriverid", beforeDriverid);
		TaxiOrderMessage message = new TaxiOrderMessage(order, TaxiOrderMessage.TAXI_CHANGEVEHICLE, params);
		if(!order.isIsusenow()) { //预约订单查询司机是否正在服务
			message.setRunTime(getDriverEndtime(order.getDriverid()));
		}
		MessageUtil.sendMessage(message);
		result.put("status", "success");
		result.put("message", "派单成功");
		return result;
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
	
	/**
	 * 添加客服备注
	 * @param object
	 * @return
	 */
	public Map<String, String> addOpTaxiordercomment(OpTaxiordercomment object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("status", "success");
		ret.put("message", "操作成功");
		
		object.setId(GUIDGenerator.newGUID());
		dao.insertOpTaxiordercomment(object);
		
		return ret;
	}
	
	/**
	 * 订单导出
	 * @param queryParam
	 * @return
	 */
	public List<Map<String, Object>> exportOrder(OrderManageQueryParam queryParam) {
		//初始化参数
		queryParam.setiDisplayStart(0);
		queryParam.setiDisplayLength(60000);
		String minUseTime = queryParam.getMinUseTime();
		if (StringUtils.isNotBlank(minUseTime)) {
			queryParam.setMinUseTime(minUseTime + ":00");
		}
		String maxUseTime = queryParam.getMaxUseTime();
		if (StringUtils.isNotBlank(maxUseTime)) {
			queryParam.setMaxUseTime(maxUseTime + ":59");
		}
		//查询需要导出的订单数据
		List<Map<String, Object>> list = null;
		switch (Integer.valueOf(queryParam.getType()).intValue()) {
			case 3:
				list = dao.getOpAbnormalTaxiOrderListByQuery(queryParam);
				break;
			case 4:
				list = dao.getOpWasabnormalTaxiOrderListByQuery(queryParam);
				break;
			case 5:
				list = dao.getOpCompleteTaxiOrderListByQuery(queryParam);
				break;
			case 6:
				list = dao.getOpWaitgatheringTaxiOrderListByQuery(queryParam);
				break;
		}
		return list;
	}

	/**
	 * 更新订单数据
	 * @param object
	 * @param order
	 */
	private void updateOrder(OpTaxiOrder object, OpTaxiOrder order, PubVehicle pubVehicle) {
		//更新订单数据
		order.setOrderstatus(OrderState.WAITSTART.state);
		order.setOrdersortcolumn(OrdersortColumn.WAITSTART.state);
		order.setCompanyid(pubVehicle.getLeasesCompanyId());
		order.setVehicleid(object.getVehicleid());
		order.setDriverid(pubVehicle.getDriverId());
		order.setPlateno(pubVehicle.getPlateNoProvinceName() + pubVehicle.getPlateNoCityName() + pubVehicle.getPlateNo());
		String[] brandCars = pubVehicle.getBrandCars().split(" ");
		if(brandCars.length == 2) {
			order.setVehcbrandname(brandCars[0]);
			order.setVehclinename(brandCars[1]);
		}
		order.setOrdertime(new Date());
		dao.updateOpTaxiOrderByOrderno(order);
	}
	
	/**
	 * 验证司机是否符合派单条件
	 * @param order
	 * @param vehicleid
	 * @return
	 */
	private boolean checkDriver(OpTaxiOrder order, String vehicleid) {
		OrderManageQueryParam queryParam = new OrderManageQueryParam();
		queryParam.setOrderLon(order.getOnaddrlng());
		queryParam.setOrderLat(order.getOnaddrlat());
		queryParam.setOncity(order.getOncity());
		queryParam.setOffcity(order.getOffcity());
		queryParam.setOrderNo(order.getOrderno());
		queryParam.setIsusenow(order.isIsusenow() ? 1 : 0);
		queryParam.setDriverState(order.isIsusenow() ? DriverEnum.WORK_STATUS_LEISURE.code : null);
		queryParam.setUsetime(order.getUsetime());
		queryParam.setEstimatedEndtime(StringUtil.addDate(order.getUsetime(), (order.getEstimatedtime() + 60) * 60));
		queryParam.setVehicleid(vehicleid);
		queryParam.setDriverid(order.getDriverid());
		if(dao.getDriverCountByQuery(queryParam) > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 生成司机消息
	 * @param orderno
	 * @param price
	 */
	private void createDriverNews(OpTaxiOrder order, double price, OrderMessageType type) {
		String content = "";
		String title = "";
		String newsType = "";
		if(OrderMessageType.REVIEWORDER.equals(type)) {
			Double orderamount = order.getShouldpayamount();
			if(null == orderamount) {
				orderamount = order.getOrderamount();
			}
			if(price > 0) {
				title = "复核反馈";
				content = "订单复核完成，行程费金额应为" + orderamount + "元，差额部分不计入贡献。";
			} else if(price < 0) {
				title = "复核反馈";
				content = "订单复核完成，行程费金额应为" + orderamount + "元。";
			} else {
				title = "复核反馈";
				content = "经核实，车费金额正确。";
			}
			newsType = UserNews.USER_NEWS_TYPE_SYSTEM;
		} else if(OrderMessageType.MANTICORDER.equals(type)) {
			newsType = UserNews.USER_NEWS_TYPE_ORDER;
		}
		OrderMessageFactory factory = new OrderMessageFactory(order, type, title, content);
		OrderInfoMessage orderinfo = factory.createMessage();
		
		//消息结构
		UserNews userNews = new UserNews();
		userNews.setUserid(order.getDriverid());
		userNews.setType(newsType);
		userNews.setContent(orderinfo.toString());
		
		UserNewsParam param = new UserNewsParam();
		param.setUserNewsTbName(UserNewsParam.DRIVER_USERNEWS_TABNAME);
		param.setUserNews(userNews);
		templateHelper.dealRequestWithFullUrlToken(SystemConfig.getSystemProperty("carserviceApi") + "/UserNews/addUserNews", HttpMethod.POST, null, param, JSONObject.class);
	}
	
	/**
	 * 查询司机服务结束时间
	 * @param driverid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private long getDriverEndtime(String driverid) {
		Map<String, Object> driverMap = dao.getInServiceOrderByDriver(driverid);
		if(null == driverMap) {
			return 0;
		}
		
		//查询司机服务中的订单结束时间
		BaiduApiQueryParam baiduparam = new BaiduApiQueryParam();
		baiduparam.setOrderStartLat((double) driverMap.get("driverLat"));
		baiduparam.setOrderStartLng((double) driverMap.get("driverLng"));
		baiduparam.setOrderEndLat((double) driverMap.get("offaddrlat"));
		baiduparam.setOrderEndLng((double) driverMap.get("offaddrlng"));
		Map<String,Object> hintMap = templateHelper.dealRequestWithFullUrlToken(
				SystemConfig.getSystemProperty("carserviceApi") +"/BaiduApi/GetMileageInfo", 
				HttpMethod.POST, null, baiduparam, Map.class);
		if(hintMap != null && (Integer)hintMap.get("status") == Retcode.OK.code){
			int duration = (int) hintMap.get("duration");
			return new Date().getTime() + duration * 1000;
		} else {
			return 0;
		}
	}
	
}

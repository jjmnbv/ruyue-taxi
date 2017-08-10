package com.szyciov.organ.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.enums.OrderEnum;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderDetails;
import com.szyciov.org.param.OrgOrderQueryParam;
import com.szyciov.organ.dao.OrgIndexDao;
import com.szyciov.util.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("OrgIndexService")
public class OrgIndexService {
	private OrgIndexDao dao;

	@Resource(name = "OrgIndexDao")
	public void setDao(OrgIndexDao dao) {
		this.dao = dao;
	}

	public List<OrgOrder> getOrgderList(OrgOrderQueryParam orgOrderQueryParam) {
		return dao.getOrgderList(orgOrderQueryParam);
	};

	public int getOrgderListCount(OrgOrderQueryParam orgOrderQueryParam) {
		return dao.getOrgderListCount(orgOrderQueryParam);
	};

	public PageBean getOrgderByQuery(OrgOrderQueryParam orgOrderQueryParam) {
			PageBean pageBean = new PageBean();
		pageBean.setsEcho(orgOrderQueryParam.getsEcho());
		if(orgOrderQueryParam.getQueryOrderTemp() != null && !orgOrderQueryParam.getQueryOrderTemp().equals("")){
			String[] s = orgOrderQueryParam.getQueryOrderTemp().split(",");
			String[] s1 = new String[s.length];
			for(int i=0;i<s.length;i++){
				s1[i] = s[i];
			}
			orgOrderQueryParam.setQueryOrder(s1);
		}
		List<OrgOrder> list = getOrgderList(orgOrderQueryParam);

		for (OrgOrder o : list) {
			//设置如约的士
			o.setCompanyName("如约的士");
			if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
				if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_RESERVE.code)) {
					o.setOrdertype("约车");
				}
			}
			if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
				if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_PICKUP.code)) {
					o.setOrdertype("接机");
				}
			}
			if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
				if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_DROPOFF.code)) {
					o.setOrdertype("送机");
				}
			}
			if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
				if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_TAXI.code)) {
					o.setOrdertype("出租车");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.WAITTAKE.state)) {
					o.setOrderStatusShow("等待接单");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.MANTICSEND.state)) {
					o.setOrderStatusShow("等待接单");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.WAITSTART.state)) {
					o.setOrderStatusShow("等待服务");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.START.state)) {
					o.setOrderStatusShow("等待服务");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.ARRIVAL.state)) {
					o.setOrderStatusShow("等待服务");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.PICKUP.state)) {
					o.setOrderStatusShow("服务中");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.INSERVICE.state)) {
					o.setOrderStatusShow("服务中");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.SERVICEDONE.state)) {
//					if(o.getPaymentstatus().equals("0")){
//						o.setOrderStatusShow("未支付");
//					}else if(o.getPaymentstatus().equals("1")){
//						o.setOrderStatusShow("已支付");
//					}else 
					if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
						o.setOrderStatusShow(PayState.STATEMENTING.msg);
					}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
						o.setOrderStatusShow(PayState.STATEMENTED.msg);
					}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
						o.setOrderStatusShow(PayState.MENTED.msg);
					}
//					else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
//						o.setOrderStatusShow(PayState.CLOSE.msg);
//					}
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
					if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
						o.setOrderStatusShow(PayState.STATEMENTING.msg);
					}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
						o.setOrderStatusShow(PayState.STATEMENTED.msg);
					}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
						o.setOrderStatusShow(PayState.MENTED.msg);
					}else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
						o.setOrderStatusShow(PayState.CLOSE.msg);
					}
					if(o.getCancelnature()==null){
						o.setOrderStatusShow(OrderState.CANCEL.msg);
					}
				}
			}
			if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
				if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
					if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
						if (o.getPaymethod().equals("2")) {
							o.setPayTypeShow("机构支付");
						}
					}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
						if (o.getPaymethod().equals("2")) {
							o.setPayTypeShow("机构支付");
						}
					}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
						if (o.getPaymethod().equals("2")) {
							o.setPayTypeShow("机构支付");
						}
					}else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
						o.setPayTypeShow("/");
					}
					if(o.getCancelnature()==null){
						o.setPayTypeShow("/");
					}
				}else{
//					if (o.getPaymethod().equals("0")) {
//						o.setPayTypeShow("个人支付");
//					}else if (o.getPaymethod().equals("1")) {
//						o.setPayTypeShow("个人垫付");
//					}else 
					if (o.getPaymethod().equals("2")) {
						o.setPayTypeShow("机构支付");
					}
				}
			}
			if(o.getExpensetype() != null && o.getExpensetype() != 0 && !o.getExpensetype().toString().equals("")){
				if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
					if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
						if(o.getExpensetype() == 2){
							o.setExpensetypeShow("取消处罚");
						}
					}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
						if(o.getExpensetype() == 2){
							o.setExpensetypeShow("取消处罚");
						}
					}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
						if(o.getExpensetype() == 2){
							o.setExpensetypeShow("取消处罚");
						}
					}else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
						o.setExpensetypeShow("/");
					}
					if(o.getCancelnature()==null){
						o.setExpensetypeShow("/");
					}
				}else{
					if(o.getExpensetype() == 1){
						o.setExpensetypeShow("行程服务");
					}else if(o.getExpensetype() == 2){
						o.setExpensetypeShow("取消处罚");
					}
				}
			}
		}
		int iTotalRecords = getOrgderListCount(orgOrderQueryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<LeLeasescompany> getQueryCompany(Map<String, Object> params){
		List<LeLeasescompany> l = dao.getQueryCompany(params);
		//设置如约的士
		l.get(0).setShortName("如约的士");
		return l;
	};
	
	public OrgOrderDetails getById(String id){
		OrgOrderDetails o = dao.getById(id);
		o.setCompanyName("如约的士");
		if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
			if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_RESERVE.code)) {
				o.setOrdertype("约车");
			}
		}
		if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
			if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_PICKUP.code)) {
				o.setOrdertype("接机");
			}
		}
		if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
			if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_DROPOFF.code)) {
				o.setOrdertype("送机");
			}
		}
		if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
			if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_TAXI.code)) {
				o.setOrdertype("出租车");
			}
		}
		
		if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
			if (o.getOrderstatus().equals(OrderState.WAITTAKE.state)) {
				o.setOrderStatusShow("等待接单");
			}
		}
		if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
			if (o.getOrderstatus().equals(OrderState.MANTICSEND.state)) {
				o.setOrderStatusShow("等待接单");
			}
		}
		if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
			if (o.getOrderstatus().equals(OrderState.WAITSTART.state)) {
				o.setOrderStatusShow("等待服务");
			}
		}
		if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
			if (o.getOrderstatus().equals(OrderState.START.state)) {
				o.setOrderStatusShow("等待服务");
			}
		}
		if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
			if (o.getOrderstatus().equals(OrderState.ARRIVAL.state)) {
				o.setOrderStatusShow("等待服务");
			}
		}
		if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
			if (o.getOrderstatus().equals(OrderState.PICKUP.state)) {
				o.setOrderStatusShow("服务中");
			}
		}
		if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
			if (o.getOrderstatus().equals(OrderState.INSERVICE.state)) {
				o.setOrderStatusShow("服务中");
			}
		}
		
		
		if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
			if (o.getOrderstatus().equals(OrderState.SERVICEDONE.state)) {
//				if(o.getPaymentstatus().equals("0")){
//					o.setOrderStatusShow("未支付");
//				}else if(o.getPaymentstatus().equals("1")){
//					o.setOrderStatusShow("已支付");
//				}else 
				if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
					o.setOrderStatusShow(PayState.STATEMENTING.msg);
				}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
					o.setOrderStatusShow(PayState.STATEMENTED.msg);
				}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
					o.setOrderStatusShow(PayState.MENTED.msg);
				}
//				else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
//					o.setOrderStatusShow(PayState.CLOSE.msg);
//				}
			}
		}
		if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
			if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
				if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
					o.setOrderStatusShow(PayState.STATEMENTING.msg);
				}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
					o.setOrderStatusShow(PayState.STATEMENTED.msg);
				}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
					o.setOrderStatusShow(PayState.MENTED.msg);
				}else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
					o.setOrderStatusShow(PayState.CLOSE.msg);
				}
				if(o.getCancelnature()==null){
					o.setOrderStatusShow(OrderState.CANCEL.msg);
				}
			}
		}
		if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
			if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
				if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
					if (o.getPaymethod().equals("2")) {
						o.setPaymethod("机构支付");
					}
				}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
					if (o.getPaymethod().equals("2")) {
						o.setPaymethod("机构支付");
					}
				}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
					if (o.getPaymethod().equals("2")) {
						o.setPaymethod("机构支付");
					}
				}else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
					o.setPaymethod("/");
				}
				if(o.getCancelnature()==null){
					o.setPaymethod("/");
				}
			}else{
//				if (o.getPaymethod().equals("0")) {
//					o.setPayTypeShow("个人支付");
//				}else if (o.getPaymethod().equals("1")) {
//					o.setPayTypeShow("个人垫付");
//				}else 
				if (o.getPaymethod().equals("2")) {
					o.setPaymethod("机构支付");
				}
			}
		}
		
//		if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
//			if (o.getOrderstatus().equals(OrderState.SERVICEDONE.state)) {
//				if(o.getPaymentstatus().equals("0")){
//					o.setOrderStatusShow("未支付");
//				}else if(o.getPaymentstatus().equals("1")){
//					o.setOrderStatusShow("已支付");
//				}else if(o.getPaymentstatus().equals("2")){
//					o.setOrderStatusShow("结算中");
//				}else if(o.getPaymentstatus().equals("3")){
//					o.setOrderStatusShow("已结算");
//				}else if(o.getPaymentstatus().equals("4")){
//					o.setOrderStatusShow("未结算");
//				}
//			}
//		}
//		if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
//			if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
//				o.setOrderStatusShow("已取消");
//			}
//		}
//		if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
//			if (o.getPaymethod().equals("0")) {
//				o.setPaymethod("个人支付");
//			}
//		}
//		if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
//			if (o.getPaymethod().equals("1")) {
//				o.setPaymethod("个人垫付");
//			}
//		}
//		if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
//			if (o.getPaymethod().equals("2")) {
//				o.setPaymethod("机构支付");
//			}
//		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    String dateString = formatter.format(o.getUsetime());
		o.setUsetimeShow(dateString);
		if(o.getCanceltime()!=null && !o.getCanceltime().equals("")){
			String canceltime = formatter.format(o.getCanceltime()) ;
			o.setCanceltimeShow(canceltime);
		}
		if(o.getCancelparty()!=null && !o.getCancelparty().equals("")){
			//0-租赁端，1-乘客端，2-司机端，3-系统派单失败
			if(o.getCancelparty().equals("0")){
				o.setCancelparty("租赁端");
			}
			if(o.getCancelparty().equals("1")){
				o.setCancelparty("乘客端");		
			}
			if(o.getCancelparty().equals("2")){
				o.setCancelparty("司机端");
			}
			if(o.getCancelparty().equals("3")){
				o.setCancelparty("系统派单失败");
			}
		}
		return o;
	}
	
	public List<OrgOrder> exportExcel(OrgOrderQueryParam orgOrderQueryParam){
		if(orgOrderQueryParam.getQueryOrderTemp() != null && !orgOrderQueryParam.getQueryOrderTemp().equals("")){
			String[] s = orgOrderQueryParam.getQueryOrderTemp().split(",");
			String[] s1 = new String[s.length];
			for(int i=0;i<s.length;i++){
				s1[i] = s[i];
			}
			orgOrderQueryParam.setQueryOrder(s1);
		}
		List<OrgOrder> list =  dao.exportExcel(orgOrderQueryParam);
		
		for(OrgOrder o : list){
			//<!-- 订单状态 -->
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.WAITTAKE.state)) {
					o.setOrderStatusShow("等待接单");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.MANTICSEND.state)) {
					o.setOrderStatusShow("等待接单");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.WAITSTART.state)) {
					o.setOrderStatusShow("等待服务");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.START.state)) {
					o.setOrderStatusShow("等待服务");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.ARRIVAL.state)) {
					o.setOrderStatusShow("等待服务");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.PICKUP.state)) {
					o.setOrderStatusShow("服务中");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.INSERVICE.state)) {
					o.setOrderStatusShow("服务中");
				}
			}
//			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
//				if (o.getOrderstatus().equals(OrderState.SERVICEDONE.state)) {
//					if(o.getPaymentstatus().equals("0")){
//						o.setOrderStatusShow("未支付");
//					}else if(o.getPaymentstatus().equals("1")){
//						o.setOrderStatusShow("已支付");
//					}else if(o.getPaymentstatus().equals("2")){
//						o.setOrderStatusShow("结算中");
//					}else if(o.getPaymentstatus().equals("3")){
//						o.setOrderStatusShow("已结算");
//					}else if(o.getPaymentstatus().equals("4")){
//						o.setOrderStatusShow("未结算");
//					}
//				}
//			}
//			if(o.getPaymentstatus()!= null && !o.getPaymentstatus().equals("")){
//				if(o.getOrderstatus().equals(OrderState.CANCEL.state)){
//					o.setPaymentstatus("");
//				}else{
//					if(o.getPaymentstatus().equals("0")){
//						o.setPaymentstatus("未支付");
//					}else if(o.getPaymentstatus().equals("1")){
//						o.setPaymentstatus("已支付");
//					}else if(o.getPaymentstatus().equals("2")){
//						o.setPaymentstatus("结算中");
//					}else if(o.getPaymentstatus().equals("3")){
//						o.setPaymentstatus("已结算");
//					}else if(o.getPaymentstatus().equals("4")){
//						o.setPaymentstatus("未结算");
//					}
//				}
//			}
//			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
//				if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
//					o.setOrderStatusShow("已取消");
//				}
//			}
//			//<!-- 支付方式  	paymethod--><!-- 订单结算方式 包括：0-个人支付，1-个人垫付，2-机构支付 -->
//			if(o.getOrderstatus().equals(OrderState.CANCEL.state)){
//				o.setPaymethod("");
//			}else{
//				if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
//					if (o.getPaymethod().equals("2")) {
//						o.setPaymethod("机构支付");
//					}
//				}
//			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.SERVICEDONE.state)) {
//					if(o.getPaymentstatus().equals("0")){
//						o.setOrderStatusShow("未支付");
//					}else if(o.getPaymentstatus().equals("1")){
//						o.setOrderStatusShow("已支付");
//					}else 
					if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
						o.setOrderStatusShow(PayState.STATEMENTING.msg);
					}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
						o.setOrderStatusShow(PayState.STATEMENTED.msg);
					}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
						o.setOrderStatusShow(PayState.MENTED.msg);
					}
//					else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
//						o.setOrderStatusShow(PayState.CLOSE.msg);
//					}
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
					if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
						o.setOrderStatusShow(PayState.STATEMENTING.msg);
					}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
						o.setOrderStatusShow(PayState.STATEMENTED.msg);
					}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
						o.setOrderStatusShow(PayState.MENTED.msg);
					}else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
						o.setOrderStatusShow(PayState.CLOSE.msg);
					}
					if(o.getCancelnature()==null){
						o.setOrderStatusShow(OrderState.CANCEL.msg);
					}
				}
			}
			if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
				if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
					if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
						if (o.getPaymethod().equals("2")) {
							o.setPaymethod("机构支付");
						}
					}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
						if (o.getPaymethod().equals("2")) {
							o.setPaymethod("机构支付");
						}
					}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
						if (o.getPaymethod().equals("2")) {
							o.setPaymethod("机构支付");
						}
					}else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
						o.setPaymethod("/");
					}
					if(o.getCancelnature()==null){
						o.setPaymethod("/");
					}
				}else{
//					if (o.getPaymethod().equals("0")) {
//						o.setPayTypeShow("个人支付");
//					}else if (o.getPaymethod().equals("1")) {
//						o.setPayTypeShow("个人垫付");
//					}else 
					if (o.getPaymethod().equals("2")) {
						o.setPaymethod("机构支付");
					}
				}
			}
			if(o.getExpensetype() != null && o.getExpensetype() != 0 && !o.getExpensetype().toString().equals("")){
				if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
					if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
						if(o.getExpensetype() == 2){
							o.setExpensetypeShow("取消处罚");
						}
					}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
						if(o.getExpensetype() == 2){
							o.setExpensetypeShow("取消处罚");
						}
					}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
						if(o.getExpensetype() == 2){
							o.setExpensetypeShow("取消处罚");
						}
					}else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
						o.setExpensetypeShow("/");
					}
					if(o.getCancelnature()==null){
						o.setExpensetypeShow("/");
					}
				}else{
					if(o.getExpensetype() == 1){
						o.setExpensetypeShow("行程服务");
					}else if(o.getExpensetype() == 2){
						o.setExpensetypeShow("取消处罚");
					}
				}
			}
			//<!-- 订单类型(1-约车、2-接机、3-送机，4-出租车) -->
			if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
				if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_RESERVE.code)) {
					o.setOrdertype("约车");
				}
			}
			if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
				if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_PICKUP.code)) {
					o.setOrdertype("接机");
				}
			}
			if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
				if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_DROPOFF.code)) {
					o.setOrdertype("送机");
				}
			}
			if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
				if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_TAXI.code)) {
					o.setOrdertype("出租车");
				}
			}
			//<!-- 下单来源(00-安卓乘客端，01-IOS乘客端，02-微信乘客端，10-租赁端，20-机构端，30-运管端) -->
			if (o.getOrdersource() != null && !o.getOrdersource().equals("")) {
				if (o.getOrdersource().equals(OrderEnum.ORDERSOURCE_PASSENGER_ANDROID.code)) {
					o.setOrdersource(OrderEnum.ORDERSOURCE_PASSENGER_ANDROID.msg);
				}
			}
			if (o.getOrdersource() != null && !o.getOrdersource().equals("")) {
				if (o.getOrdersource().equals(OrderEnum.ORDERSOURCE_PASSENGER_IOS.code)) {
					o.setOrdersource(OrderEnum.ORDERSOURCE_PASSENGER_IOS.msg);
				}
			}
			if (o.getOrdersource() != null && !o.getOrdersource().equals("")) {
				if (o.getOrdersource().equals(OrderEnum.ORDERSOURCE_PASSENGER_WECHAT.code)) {
					o.setOrdersource(OrderEnum.ORDERSOURCE_PASSENGER_WECHAT.msg);
				}
			}
			if (o.getOrdersource() != null && !o.getOrdersource().equals("")) {
				if (o.getOrdersource().equals(OrderEnum.ORDERSOURCE_LEASE.code)) {
					o.setOrdersource(OrderEnum.ORDERSOURCE_LEASE.msg);
				}
			}
			if (o.getOrdersource() != null && !o.getOrdersource().equals("")) {
				if (o.getOrdersource().equals(OrderEnum.ORDERSOURCE_ORGAN.code)) {
					o.setOrdersource(OrderEnum.ORDERSOURCE_ORGAN.msg);
				}
			}
			if (o.getOrdersource() != null && !o.getOrdersource().equals("")) {
				if (o.getOrdersource().equals(OrderEnum.ORDERSOURCE_ORGAN.code)) {
					o.setOrdersource(OrderEnum.ORDERSOURCE_ORGAN.msg);
				}
			}
			//<!-- 支付渠道   1-钱包支付，2-微信支付，3-支付宝支付 -->
			if (o.getPaytype() != null && !o.getPaytype().equals("")) {
				if (o.getPaytype().equals("1")) {
					o.setPaytype("余额支付");
				}
			}
			if (o.getPaytype() != null && !o.getPaytype().equals("")) {
				if (o.getPaytype().equals("2")) {
					o.setPaytype("微信支付");
				}
			}
			if (o.getPaytype() != null && !o.getPaytype().equals("")) {
				if (o.getPaytype().equals("3")) {
					o.setPaytype("支付宝支付");
				}
			}
			//<!-- 用车方式   用车类型(0-因公，1-因私，2-个人)-->
			if (o.getUsetype() != null && !o.getUsetype().equals("")) {
				if (o.getUsetype().equals(OrderEnum.USETYPE_PUBLIC.code)) {
					o.setUsetype(OrderEnum.USETYPE_PUBLIC.msg);
				}
			}
			if (o.getUsetype() != null && !o.getUsetype().equals("")) {
				if (o.getUsetype().equals(OrderEnum.USETYPE_PRIVATE.code)) {
					o.setUsetype(OrderEnum.USETYPE_PRIVATE.msg);
				}
			}
			if (o.getUsetype() != null && !o.getUsetype().equals("")) {
				if (o.getUsetype().equals(OrderEnum.USETYPE_PERSONAL.code)) {
					o.setUsetype(OrderEnum.USETYPE_PERSONAL.msg);
				}
			}
		}
		return list;
	};
	
	public Map<String, Object> getOrgOrderByOrderno(String orderno) {
		return dao.getOrgOrderByOrderno(orderno);
	}
	
	public JSONArray getServiceOrder(OrgOrderQueryParam orgOrderQueryParam){
//		long startTime=System.nanoTime();   //获取开始时间  
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", orgOrderQueryParam.getUserid());
		map.put("organId", orgOrderQueryParam.getKey());
		map.put("usertype", orgOrderQueryParam.getUsertype());
		map.put("iDisplayStart",orgOrderQueryParam.getiDisplayStart());
		map.put("iDisplayLength",orgOrderQueryParam.getiDisplayLength());
		List<OrgOrder> list = dao.getServiceOrder(map);
		for (OrgOrder o : list) {
			//设置如约的士
			o.setCompanyName("如约的士");
			if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
				if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_RESERVE.code)) {
					o.setOrdertype("约车");
				}
			}
			if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
				if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_PICKUP.code)) {
					o.setOrdertype("接机");
				}
			}
			if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
				if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_DROPOFF.code)) {
					o.setOrdertype("送机");
				}
			}
			if (o.getOrdertype() != null && !o.getOrdertype().equals("")) {
				if (o.getOrdertype().equals(OrderEnum.ORDERTYPE_TAXI.code)) {
					o.setOrdertype("出租车");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.WAITTAKE.state)) {
					o.setOrderStatusShow("等待接单");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.MANTICSEND.state)) {
					o.setOrderStatusShow("等待接单");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.WAITSTART.state)) {
					o.setOrderStatusShow("等待服务");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.START.state)) {
					o.setOrderStatusShow("等待服务");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.ARRIVAL.state)) {
					o.setOrderStatusShow("等待服务");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.PICKUP.state)) {
					o.setOrderStatusShow("服务中");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.INSERVICE.state)) {
					o.setOrderStatusShow("服务中");
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.SERVICEDONE.state)) {
					if(o.getPaymentstatus().equals("0")){
						o.setOrderStatusShow("未支付");
					}else if(o.getPaymentstatus().equals("1")){
						o.setOrderStatusShow("已支付");
					}else if(o.getPaymentstatus().equals("2")){
						o.setOrderStatusShow("结算中");
					}else if(o.getPaymentstatus().equals("3")){
						o.setOrderStatusShow("已结算");
					}else if(o.getPaymentstatus().equals("4")){
						o.setOrderStatusShow("未结算");
					}
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
					o.setOrderStatusShow("已取消");
				}
			}
			if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
				if (o.getPaymethod().equals("0")) {
					o.setPayTypeShow("个人支付");
				}
			}
			if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
				if (o.getPaymethod().equals("1")) {
					o.setPayTypeShow("个人垫付");
				}
			}
			if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
				if (o.getPaymethod().equals("2")) {
					o.setPayTypeShow("机构支付");
				}
			}
			if(o.getUsetime() != null && !o.getUsetime().equals("")){
				o.setUsetimeShow(getStringDate(o.getUsetime()));
			}
		}
		JSONArray json = new JSONArray();
		for(OrgOrder o : list){
			JSONObject jo = new JSONObject();
			jo.put("orderno",o.getOrderno());
			jo.put("userMessage",o.getUserMessage());
			jo.put("companyName",o.getCompanyName());
			jo.put("ordertype",o.getOrdertype());
			jo.put("usetime",o.getUsetimeShow());
			jo.put("orderStatusShow",o.getOrderStatusShow());
			jo.put("payTypeShow",o.getPayTypeShow());
			jo.put("orderamount",o.getOrderamount());
			json.add(jo);
		}
//		long endTime=System.nanoTime(); //获取结束时间  
//		System.out.println("程序运行时间： "+(endTime-startTime)+"ns"); 
		return json;
	};
	public static String getStringDate(long now) {
		Date d = new Date(now);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(now);
//		return formatter.format(calendar.getTime());
		return formatter.format(d);
	}
	public static String getStringDate(Date date) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	   String dateString = formatter.format(date);
	   return dateString;
	}
}

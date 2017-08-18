package com.szyciov.organ.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.OrderEnum;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderDetails;
import com.szyciov.org.param.OrgOrderQueryParam;
import com.szyciov.organ.dao.MyOrderDao;
import com.szyciov.passenger.entity.PassengerOrder;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.UNID;
import com.wx.DocFunc;
import com.wx.WXOrderUtil;

@Service("MyOrderService")
public class MyOrderService {
	private MyOrderDao dao;

	@Resource(name = "MyOrderDao")
	public void setDao(MyOrderDao dao) {
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
//					}else if(o.getPaymentstatus().equals("2")){
//						o.setOrderStatusShow("结算中");
//					}else if(o.getPaymentstatus().equals("3")){
//						o.setOrderStatusShow("已结算");
//					}else if(o.getPaymentstatus().equals("4")){
//						o.setOrderStatusShow("未结算");
//					}
					if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
						o.setOrderStatusShow(PayState.STATEMENTING.msg);
					}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
						o.setOrderStatusShow(PayState.STATEMENTED.msg);
					}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
						o.setOrderStatusShow(PayState.MENTED.msg);
					}
				}
			}
			if (o.getOrderstatus() != null && !o.getOrderstatus().equals("")) {
				if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
					if(o.getCancelnature()==null){
						o.setOrderStatusShow(OrderState.CANCEL.msg);
					}else{
						if(o.getCancelnature() == 1){
							if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
								o.setOrderStatusShow(PayState.STATEMENTING.msg);
							}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
								o.setOrderStatusShow(PayState.STATEMENTED.msg);
							}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
								o.setOrderStatusShow(PayState.MENTED.msg);
							}else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
								o.setOrderStatusShow(PayState.CLOSE.msg);
							}
						}else{
							o.setOrderStatusShow(OrderState.CANCEL.msg);
						}
					}
				}
			}
//			if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
//				if (o.getPaymethod().equals("0")) {
//					o.setPayTypeShow("个人支付");
//				}
//			}
//			if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
//				if (o.getPaymethod().equals("1")) {
//					o.setPayTypeShow("个人垫付");
//				}
//			}
//			if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
//				if (o.getPaymethod().equals("2")) {
//					o.setPayTypeShow("机构支付");
//				}
//			}
			if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
				if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
					if(o.getCancelnature()==null){
						o.setPayTypeShow("/");
					}else{
						if(o.getCancelnature()==1){
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
						}else{
							o.setPayTypeShow("/");
						}
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
					if(o.getCancelnature()==null){
						o.setExpensetypeShow("/");
					}else{
						if(o.getCancelnature()==1){
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
						}else{
							o.setExpensetypeShow("/");
						}
					}
				}else{
					if(o.getExpensetype() == 1){
						o.setExpensetypeShow("行程服务");
						o.setCancelamount(0);
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
	
	public List<LeLeasescompany> getQueryCompany(){
		return dao.getQueryCompany();
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
				if(o.getCancelnature()==null){
					o.setOrderStatusShow(OrderState.CANCEL.msg);
				}else{
					if(o.getCancelnature() == 1){
						if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
							o.setOrderStatusShow(PayState.STATEMENTING.msg);
						}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
							o.setOrderStatusShow(PayState.STATEMENTED.msg);
						}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
							o.setOrderStatusShow(PayState.MENTED.msg);
						}else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
							o.setOrderStatusShow(PayState.CLOSE.msg);
						}
					}else{
						o.setOrderStatusShow(OrderState.CANCEL.msg);
					}
				}
			}
		}
		if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
			if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
				if(o.getCancelnature()==null){
					o.setPaymethod("/");
				}else{
					if(o.getCancelnature()==1){
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
					}else{
						o.setPaymethod("/");
					}
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
//					if (o.getPaymethod().equals("0")) {
//						o.setPaymethod("个人支付");
//					}
//				}
//				if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
//					if (o.getPaymethod().equals("1")) {
//						o.setPaymethod("个人垫付");
//					}
//				}
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
					if(o.getCancelnature()==null){
						o.setOrderStatusShow(OrderState.CANCEL.msg);
					}else{
						if(o.getCancelnature() == 1){
							if(o.getPaymentstatus().equals(PayState.STATEMENTING.state)){
								o.setOrderStatusShow(PayState.STATEMENTING.msg);
							}else if(o.getPaymentstatus().equals(PayState.STATEMENTED.state)){
								o.setOrderStatusShow(PayState.STATEMENTED.msg);
							}else if(o.getPaymentstatus().equals(PayState.MENTED.state)){
								o.setOrderStatusShow(PayState.MENTED.msg);
							}else if(o.getPaymentstatus().equals(PayState.CLOSE.state)){
								o.setOrderStatusShow(PayState.CLOSE.msg);
							}
						}else{
							o.setOrderStatusShow(OrderState.CANCEL.msg);
						}
					}
				}
			}
			if (o.getPaymethod() != null && !o.getPaymethod().equals("")) {
				if (o.getOrderstatus().equals(OrderState.CANCEL.state)) {
					if(o.getCancelnature()==null){
						o.setPayTypeShow("/");
					}else{
						if(o.getCancelnature()==1){
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
						}else{
							o.setPayTypeShow("/");
						}
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
					if(o.getCancelnature()==null){
						o.setExpensetypeShow("/");
					}else{
						if(o.getCancelnature()==1){
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
						}else{
							o.setExpensetypeShow("/");
						}
					}
				}else{
					if(o.getExpensetype() == 1){
						o.setExpensetypeShow("行程服务");
						o.setCancelamount(0);
					}
				}
			}
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
	
	public void cancelOrder(String id){
		dao.cancelOrder(id);
	};
	
	public Map<String, Object> updatePaytype(Map<String,Object> payparam){
		Map<String, Object> map = new HashMap<String, Object>();
		String orderno = (String) payparam.get("orderno");
		String paytype = (String) payparam.get("paytype");
		map.put("status", "fail");
		try{
			Map<String, Object> orderobj = dao.getOrgOrderByOrderno(orderno);
			if(orderobj==null){
				map.put("message", Retcode.ORDERNOTEXIT.msg);
				return map;
			}
			Object orderstatus = orderobj.get("orderstatus");
			if(orderstatus==null||!OrderState.SERVICEDONE.state.equalsIgnoreCase((String)orderstatus)){
				map.put("message", "订单行程还未结束");
				return map;
			}
			Object paymentstatus = orderobj.get("paymentstatus");
			if(PayState.PAYED.state.equalsIgnoreCase((String) paymentstatus)){
				map.put("message", "订单已经支付过");
				return map;
			}
			Map<String,Object> payinfo = dao.getPayInfo4LeByCompanyid((String)orderobj.get("companyid"));
			if(payinfo==null){
				map.put("message", "收款方信息不存在");
				return map;
			}
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			//获取订单的交易号 (时间加上5位随机码)
			String out_trade_no = format.format(date)+UNID.getUNID();
			Map<String,String> orderinfo = new HashMap<String,String>();
			orderinfo.put("out_trade_no", out_trade_no);
			orderinfo.put("orderno", orderno);
			//微信是2，支付宝是1
			orderinfo.put("paymenttype", paytype);
//			double orderamount = (double)orderobj.get("orderamount");
			BigDecimal b = (BigDecimal) orderobj.get("orderamount");
			double orderamount = b.doubleValue(); 
			if("2".equalsIgnoreCase(paytype)){
				String appid = (String) payinfo.get("wechatappid");
				String shno = (String) payinfo.get("wechatmerchantno");
				String md5key = (String) payinfo.get("wechatsecretkey");
				if(StringUtils.isBlank(appid)||StringUtils.isBlank(shno)||StringUtils.isBlank(md5key)){
					map.put("status", "fail");
					map.put("message","收款方信息不完整");
					return map;
				}
//				Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam("wx261914ebf1cd50d4", "1355896702", "好约车——订单付款", out_trade_no, "1", SystemConfig.getSystemProperty("paynotifyaddr")+"/organ-api/MyOrder/DillWXPayed", "NATIVE");
//				Document doc = WXOrderUtil.createPreOrderInfo(prepayorderparam, WXOrderUtil.getSign(prepayorderparam, "831764137665de732af6044bfbcdd68b"));
				//自己的账户
//				Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam("wx946621226aba6ffc", "1416436502", "好约车——订单付款", out_trade_no, "1", SystemConfig.getSystemProperty("paynotifyaddr")+"/organ-api/MyOrder/DillWXPayed", "NATIVE");
//				Document doc = WXOrderUtil.createPreOrderInfo(prepayorderparam, WXOrderUtil.getSign(prepayorderparam, "shenzhenyunchuang2015lishuhui038"));
				String wxbody = StringUtils.isBlank(SystemConfig.getSystemProperty("wxbody"))?"如约的士——订单付款":SystemConfig.getSystemProperty("wxbody");
//				String ordermoney = new java.text.DecimalFormat("#").format(orderamount*100);
				String ordermoney = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?"1":new java.text.DecimalFormat("#").format(orderamount*100);
				String ipadd = payparam.get("ipadd")!=null?(String)payparam.get("ipadd"):null;
				int a=ipadd.indexOf(":");
				if(a>=0){
					ipadd = "0.0.0.0";
				}
				Map<String,String> prepayorderparam = WXOrderUtil.createPreOrderParam(appid, shno, wxbody, out_trade_no, ordermoney, SystemConfig.getSystemProperty("paynotifyaddr")+"/organ-api/MyOrder/DillWXPayed", "NATIVE",ipadd);
				Document doc = WXOrderUtil.createPreOrderInfo(prepayorderparam, WXOrderUtil.getSign(prepayorderparam, md5key));
				System.out.println(DocFunc.doc2String(doc));
				Map<String,String> res = WXOrderUtil.getPreOrderCodeUrl(DocFunc.doc2String(doc), "https://api.mch.weixin.qq.com/pay/unifiedorder");
				if(res!=null&&"0".equalsIgnoreCase(res.get("status"))){
					String code_url = res.get("code_url");
					map.put("code_url", code_url);
					map.put("status", "success");
				}
			}else{
				String zfbsubject = StringUtils.isBlank(SystemConfig.getSystemProperty("zfbsubject"))?"如约的士——订单付款":SystemConfig.getSystemProperty("zfbsubject");
				map.put("status", "success");
				map.put("out_trade_no", out_trade_no);
				map.put("notify_url", SystemConfig.getSystemProperty("paynotifyaddr")+"/organ-api/MyOrder/DillZFBPayed");
				map.put("partner", payinfo.get("alipaypartnerid"));
				map.put("subject", zfbsubject);
				orderamount = "off".equalsIgnoreCase(SystemConfig.getSystemProperty("payrealmoney"))?0.01:orderamount;
				map.put("total_fee", String.valueOf(orderamount));
				map.put("partner_private_key", payinfo.get("alipaypartnerprivatekey"));
			}
			//添加记录到数据库
			dao.addTradeNo4Order(orderinfo);
		}catch(Exception e){
			map.put("message","支付异常");
			e.printStackTrace();
		}
		return map;
	};
	
	public void updateOrderstatus(String id){
		dao.updateOrderstatus(id);
	};
	
	public void updatePaymentstatus(String id){
		dao.updatePaymentstatus(id);
	};
	
	public Map<String, String> updateUserrate(OrgOrder orgOrder){
		Map<String, String> map = new HashMap<>();
		map.put("ResultSign", "Successful");
		map.put("MessageKey", "感谢您的评价");
		dao.updateUserrate(orgOrder);
		return map;
	};
	
	/**
	 * 微信支付结果通知
	 * @param req
	 * @param res
	 */
	public void dillWXPayed(HttpServletRequest request,HttpServletResponse response){
		//根据支付结果，更改订单状态，并且返回微信“success”
		response.setContentType("application/xml");
		String res = "SUCCESS";
		try{
			 try {
		            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		            DocumentBuilder builder=factory.newDocumentBuilder();  
		            Document doc = builder.parse(request.getInputStream()); 
	            	Node return_code = doc.getElementsByTagName("return_code").item(0);
	                if(return_code!=null){
	                    if("SUCCESS".equalsIgnoreCase(return_code.getFirstChild().getNodeValue())){
	                    	//检查签名是否有效
	    		            Node sign = doc.getElementsByTagName("sign").item(0);
	    		            boolean flag= isWXSignValid(sign.getFirstChild().getNodeValue());
	    		            if(flag){
	    		            	//attach存储订单号，根据订单号修改订单状态
				            	Node out_trade_no = doc.getElementsByTagName("out_trade_no").item(0);
		                    	String outtradeno = out_trade_no.getFirstChild().getNodeValue();
	                    		//更新订单状态
		                    	Map<String, Object> orderparam = new HashMap<String,Object>();
		                    	orderparam.put("paymentstatus", "1");
		                    	orderparam.put("outtradeno", outtradeno);
		                    	dao.payed4OrgOrder(orderparam);
		                    	
		                    	Map<String,Object> tradeparam = new HashMap<String,Object>();
		                    	tradeparam.put("outtradeno", outtradeno);
		                    	tradeparam.put("tradeno", doc.getElementsByTagName("transaction_id").item(0).getFirstChild().getNodeValue());
		                    	tradeparam.put("paymenttype", "1");
		                    	dao.updateTradeInfo4OrgOrder(tradeparam);
		                    	
		                    	//添加记录
		                    	String orderno = dao.getOrdernoByOutno(outtradeno);
		                    	OrgOrderDetails order = dao.getById(orderno);
		                		Map<String,Object> expenses = new HashMap<String,Object>();
		                		expenses.put("logid", GUIDGenerator.newGUID());
		                		expenses.put("userid",order.getUserid());
		                		expenses.put("companyid",order.getCompanyid());
		                		expenses.put("expensetype","1");
		                		expenses.put("money",order.getOrderamount());
		                		expenses.put("remark","订单支付");
		                		expenses.put("tradetype","1");
		                		expenses.put("detailtype","1");
		                		expenses.put("operateresult","0");
		                		dao.addExpenses4OrgSec(expenses);
	    		            }else{
	    		                //签名失败记录日志并且返回失败
	    		            	res = "FAIL";
	    		            }
	                    	
	                    }else{
	                        //支付失败
	                        res = "FAIL";
	                    }
	                }else{
	                	//解析参数格式失败
	                    res = "FAIL";
	                }
		        } catch (Exception e) {
		        	//异常记录日志
		        	 res = "FAIL";
		        }
			 	writeMessge4WX(response, res);
		}catch(Exception e){
			//记录日志
			e.printStackTrace();
		}
    }
	
	private void writeMessge4WX(HttpServletResponse response, String res)
			throws ParserConfigurationException, IOException, Exception {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();  
		DocumentBuilder builder=factory.newDocumentBuilder();  
		Document doc = builder.newDocument();
		Element root = doc.createElement("xml");
		Element return_code = doc.createElement("return_code");
		CDATASection return_codedata = doc.createCDATASection(res);
		return_code.appendChild(return_codedata);
		root.appendChild(return_code);
		Element return_msg = doc.createElement("return_msg");
		String msg = res.equalsIgnoreCase("SUCCESS")?"OK":"FAIL";
		CDATASection return_msgdata = doc.createCDATASection(msg);
		return_msg.appendChild(return_msgdata);
		root.appendChild(return_msg);
		doc.appendChild(root);
		response.getWriter().write(doc2String(doc));
	}
	
	private String doc2String(Document doc) throws Exception{
		TransformerFactory  tf  =  TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();    
        ByteArrayOutputStream  bos  =  new  ByteArrayOutputStream();   
        t.transform(new DOMSource(doc), new StreamResult(bos));    
        return bos.toString();
	}
	
	/**
	 * 微信签名是否正确
	 * @param nodeValue
	 * @return
	 */
	private boolean isWXSignValid(String nodeValue) {
		//do校验签名
		return true;
	}

	/**
	 * 检查订单的状态是否已支付
	 * @param params
	 * @return
	 */
	public Map<String, Object> checkOrderState(Map<String, Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		String orderno = (String) params.get("orderno");
		Map<String,Object> orderinfo = dao.getOrgOrderByOrderno(orderno);
		res.put("status", "success");
		if(orderinfo!=null){
			if(OrderState.SERVICEDONE.state.equalsIgnoreCase((String) orderinfo.get("orderstatus"))&&PayState.PAYED.state.equalsIgnoreCase((String) orderinfo.get("paymentstatus"))){
				res.put("payed", true);
			}else{
				res.put("payed", false);
			}
		}else{
			res.put("status", "fail");
		}
		return res;
	}

	public void dillZFBPayed(HttpServletRequest request, HttpServletResponse response) {
		//根据支付结果，更改订单状态，并且返回支付宝“success”
		String res = "success";
		try{
			String result_code = request.getParameter("trade_status");
			if("TRADE_SUCCESS".equalsIgnoreCase(result_code)){
				//机构订单
				if(isZFBSignValid(request)){
					String out_trade_no = request.getParameter("out_trade_no");
					//根据out_trade_no查询订单号，并且修改订单状态
                	Map<String, Object> orderparam = new HashMap<String,Object>();
                	orderparam.put("paymentstatus", "1");
                	orderparam.put("outtradeno", out_trade_no);
                	dao.payed4OrgOrder(orderparam);
                	
                	Map<String,Object> tradeparam = new HashMap<String,Object>();
                	tradeparam.put("outtradeno", out_trade_no);
                	tradeparam.put("tradeno", request.getParameter("trade_no"));
                	tradeparam.put("paymenttype", "0");
                	dao.updateTradeInfo4OrgOrder(tradeparam);
                	
                	//3、记录
                	String orderno = dao.getOrdernoByOutno(out_trade_no);
                	OrgOrderDetails order = dao.getById(orderno);
            		Map<String,Object> expenses = new HashMap<String,Object>();
            		expenses.put("logid", GUIDGenerator.newGUID());
            		expenses.put("userid",order.getUserid());
            		expenses.put("companyid",order.getCompanyid());
            		expenses.put("expensetype","2");
            		expenses.put("money",order.getOrderamount());
            		expenses.put("remark","订单支付");
            		expenses.put("tradetype","1");
            		expenses.put("detailtype","1");
            		expenses.put("operateresult","0");
            		dao.addExpenses4OrgSec(expenses);
				}else{
					//签名失败
					res = "failure";
				}
			}else{
				//交易失败，返回失败
				res = "failure";
			}
			response.getWriter().write(res);
		}catch(Exception e){
			//记录日志
		}
	}
	
	/**
	 * 验证支付宝签名正确与否
	 * @param type 0-"运管端","1"-租赁端
	 * @param sign
	 * @return
	 * @throws AlipayApiException 
	 */
	private boolean isZFBSignValid(HttpServletRequest request) throws AlipayApiException {
		Map<String,String> pp = new HashMap<String,String>();
		Enumeration<String> pnames = request.getParameterNames();
		while(pnames.hasMoreElements()){
			String pname = pnames.nextElement();
			pp.put(pname, request.getParameter(pname));
		}
		String out_trade_no = request.getParameter("out_trade_no");
		String orderno = dao.getOrdernoByOutno(out_trade_no);
		if(StringUtils.isBlank(orderno)){
			return false;
		}
		Map<String,Object> orderinfo = dao.getOrgOrderByOrderno(orderno);
		if(orderinfo==null){
			return false;
		}
		Map<String,Object> payinfo = dao.getPayInfo4LeByCompanyid((String)orderinfo.get("companyid"));
		//给租赁端付钱
		if(payinfo==null){
			return false;
		}
		String publickey = (String) payinfo.get("alipaypartnerpublickey");
		return AlipaySignature.rsaCheckV1(pp, publickey, AlipayConfig.input_charset);
	}
	
	public LeLeasescompany getOrdernoByLeasescompany(String id){
		return dao.getOrdernoByLeasescompany(id);
	};
}

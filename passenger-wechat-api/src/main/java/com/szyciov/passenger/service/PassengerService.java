package com.szyciov.passenger.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

import com.szyciov.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import com.alipay.util.OrderInfoUtil;
import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.enums.PayState;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.entity.CancelParty;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.OrderCost;
import com.szyciov.entity.PayMethod;
import com.szyciov.entity.PlatformType;
import com.szyciov.entity.PubAdImage;
import com.szyciov.entity.Retcode;
import com.szyciov.enums.OrderEnum;
import com.szyciov.enums.RedisKeyEnum;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.message.UserMessage;
import com.szyciov.op.entity.PeUser;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.param.BaiduApiQueryParam;
import com.szyciov.param.OrderApiParam;
import com.szyciov.passenger.Const;
import com.szyciov.passenger.dao.DictionaryDao;
import com.szyciov.passenger.dao.OpDao;
import com.szyciov.passenger.dao.OrderDao;
import com.szyciov.passenger.dao.OrgDao;
import com.szyciov.passenger.dao.UserDao;
import com.szyciov.passenger.entity.AccountRules;
import com.szyciov.passenger.entity.AirportAddr;
import com.szyciov.passenger.entity.DriverInfo;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.MostAddress;
import com.szyciov.passenger.entity.MostContact;
import com.szyciov.passenger.entity.Order4List;
import com.szyciov.passenger.entity.PassengerOrder;
import com.szyciov.passenger.entity.SysVersion;
import com.szyciov.passenger.entity.VehicleModels;
import com.szyciov.passenger.param.LoginParam;
import com.szyciov.passenger.param.RegisterParam;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.passenger.util.VelocityUtil;
import com.wx.DocFunc;
import com.wx.WXOrderUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("PassengerService")
public class PassengerService {
	private static final Logger logger = Logger.getLogger(PassengerService.class);
	
	private TemplateHelper4OrgApi orgapi = new TemplateHelper4OrgApi();
	
	private TemplateHelper4leaseApi leaseapi = new TemplateHelper4leaseApi();
	
	private TemplateHelper4CarServiceApi carserviceapi = new TemplateHelper4CarServiceApi();
	
	/**
	 * 字典相关的dao
	 */
	private DictionaryDao dicdao;
	
	/**
	 * 用户相关的dao
	 */
	private UserDao userdao;
	
	/**
	 * 订单相关dao
	 */
	private OrderDao orderdao;
	
	/**
	 * 机构相关的dao
	 */
	private OrgDao orgdao;
	
	/**
	 * 运营相关的dao
	 */
	private OpDao opdao;
	
	@Resource(name = "DictionaryDao")
	public void setDictionaryDao(DictionaryDao dicdao) {
		this.dicdao = dicdao;
	}
	
	@Resource(name = "UserDao")
	public void setUserDao(UserDao userdao) {
		this.userdao = userdao;
	}
	
	@Resource(name = "OrderDao")
	public void setOrderDao(OrderDao orderdao) {
		this.orderdao = orderdao;
	}
	
	@Resource(name = "OrgDao")
	public void setOrgDao(OrgDao orgdao) {
		this.orgdao = orgdao;
	}
	
	@Resource(name = "OpDao")
	public void setOpDao(OpDao opdao) {
		this.opdao = opdao;
	}
	
	/**
	 * 判断是否是taxiorder
	 * @param ordertype
	 * @return
	 */
	private boolean isTaxiOrder(String ordertype){
		return OrderEnum.ORDERTYPE_TAXI.code.equals(ordertype);
	}
	
	/**
	 * 转化obj成value，空值就为0
	 * @param value
	 * @return
	 */
	private double parseDouble(Object value){
		if(value==null||"".equalsIgnoreCase(String.valueOf(value))){
			return 0;
		}
		return Double.parseDouble(String.valueOf(value));
	}
	
	/**
	 * 给司机添加订单金额到钱包并且添加明细
	 * @param optaxiorder
	 */
	private void addMoney4Driver(Map<String, Object> optaxiorder) {
		if(optaxiorder==null){
			return ;
		}
		try{
			String paymentmethod = (String) optaxiorder.get("paymentmethod");
			if("0".equalsIgnoreCase(paymentmethod)){
				//线上支付
				double orderamount = parseDouble(optaxiorder.get("orderamount"));
				//给司机钱包加钱并且加收入明细
				String driverid = (String) optaxiorder.get("driverid");
				String companyid = (String) optaxiorder.get("companyid");
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("driverid",driverid);
				params.put("companyid",companyid);
				Map<String, Object> platinfo = dicdao.getPayInfo4Op();
				String platid = (String) platinfo.get("id");
				if(platid==null||platid.equals(companyid)){
					params.put("platformtype",0);
				}else{
					params.put("platformtype",1);
				}
				params.put("orderamount",orderamount);
				Map<String,Object> balanceinfo = userdao.getDriverBalance(params);
				if(balanceinfo==null){
					//司机不存在钱包，创建
					params.put("id",GUIDGenerator.newGUID());
					userdao.createDriverBalanceInfo(params);
				}else{
					//更新钱包
					userdao.updateDriverBalanceInfo(params);
				}
				Map<String,Object> infolog = new HashMap<String,Object>();
				infolog.put("id",GUIDGenerator.newGUID());
				infolog.put("companyid",companyid);
				infolog.put("driverid",driverid);
				infolog.put("tradetype","4");
				infolog.put("remark","");
				infolog.put("expensetype","5");
				infolog.put("amount",orderamount);
				infolog.put("detailtype","0");
				infolog.put("operateresult","0");
				infolog.put("platformtype",params.get("platformtype"));
				userdao.addDriverExpenses(infolog);
			}
		}catch (Exception e){
			logger.error("给司机添加钱包钱时出错",e);
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
	 * 微信支付结果通知
	 * @param request
	 * @param response
	 */
	public void dillWXPubPayed4Op(HttpServletRequest request,HttpServletResponse response){
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
	                    	Node result_code = doc.getElementsByTagName("result_code").item(0);
	    		            if(result_code!=null&&"SUCCESS".equalsIgnoreCase(result_code.getFirstChild().getNodeValue())){
	    		            	//attach存储订单号，根据订单号修改订单状态
				            	Node out_trade_no = doc.getElementsByTagName("out_trade_no").item(0);
		                    	String outtradeno = out_trade_no.getFirstChild().getNodeValue();
		                    	Map<String,Object> tradeinfo = orderdao.getPayTradeRecord4OpTaxi(outtradeno);
		    					String userid = null;
		    					double amount = 0;
		    					if(tradeinfo!=null){
		    						//出租车订单
		    						//更改订单状态
		    						String orderno = (String) tradeinfo.get("orderno");
		    						Map<String, Object> orderparam = new HashMap<String,Object>();
		    	                	orderparam.put("paymentstatus", "1");
		    	                	orderparam.put("orderno", orderno);
		    	                	orderparam.put("paytype", "4");
		    	                	orderdao.payed4OpTaxiOrder(orderparam);
		    	                	//更改交易流水
		    	                	Map<String,Object> tradeparam = new HashMap<String,Object>();
		    	                	String tradeno = doc.getElementsByTagName("transaction_id").item(0).getFirstChild().getNodeValue();
		    	                	tradeparam.put("outtradeno", outtradeno);
		    	                	tradeparam.put("tradeno", tradeno);
		    	                	tradeparam.put("paymenttype", "2");
		    	                	orderdao.updateTradeInfo4OpTaxiOrder(tradeparam);
		    	                	Map<String,Object> param = new HashMap<String,Object>();
		    	                	param.put("orderno", orderno);
		    	                	Map<String,Object> optaxiorder = orderdao.getOrder4OpTaxi(param);
		    	                	userid = (String) optaxiorder.get("userid");
		    	                	amount = parseDouble(optaxiorder.get("schedulefee"));
		    	    				String paymentmethod = (String) optaxiorder.get("paymentmethod");
		    	    				if("0".equalsIgnoreCase(paymentmethod)){
		    	    					//线上支付
		    	    					amount += parseDouble(optaxiorder.get("orderamount"));
										//给司机钱包加钱并且加收入明细
										addMoney4Driver(optaxiorder);

										//添加记录
										Map<String,Object> expenses = new HashMap<String,Object>();
										expenses.put("logid", GUIDGenerator.newGUID());
										expenses.put("userid",userid);
										expenses.put("expensetype",5);
										expenses.put("money",amount);
										expenses.put("remark","微信公众号支付");
										expenses.put("tradetype","1");
										expenses.put("detailtype","1");
										expenses.put("operateresult","0");
										userdao.addExpenses4OpSec(expenses);

//										//出租车返现
//										try{
//											Map<String,Object> awardparams = new HashMap<String,Object>();
//											awardparams.put("usertype","1");
//											awardparams.put("passengerphone",optaxiorder.get("passengerphone"));
//											awardparams.put("money",amount);
//											PeUser peuser = userdao.getPeUserById((String) optaxiorder.get("userid"));
//											awardparams.put("userphone",peuser.getAccount());
//											carserviceapi.dealRequestWithToken("/AwardPassenger/AwardPoint", HttpMethod.POST, null, awardparams, JSONObject.class);
//										}catch (Exception e){
//											logger.error("返现出错了",e);
//										}
		    	    				}else{
										//添加记录
										Map<String,Object> expenses = new HashMap<String,Object>();
										expenses.put("logid", GUIDGenerator.newGUID());
										expenses.put("userid",userid);
										expenses.put("expensetype",5);
										expenses.put("money",amount);
										expenses.put("remark","微信公众号支付");
										expenses.put("tradetype","1");
										expenses.put("detailtype","1");
										expenses.put("operateresult","0");
										userdao.addExpenses4OpSec(expenses);
									}
		    	    				//优惠券使用
		    	    				dillCouponUseInfo4Op(optaxiorder);
		    					}else{
		    						//网约车订单
		    						tradeinfo = orderdao.getPayTradeRecord4OpNetCar(outtradeno);
		    						//根据out_trade_no查询订单号，并且修改订单状态
		    	                	Map<String, Object> orderparam = new HashMap<String,Object>();
		    	                	orderparam.put("paymentstatus", "1");
		    	                	orderparam.put("outtradeno", outtradeno);
		    	                	orderparam.put("paytype", "4");
		    	                	orderdao.payed4OpOrder(orderparam);
		    	                	//更改交易流水
		    						Map<String,Object> tradeparam = new HashMap<String,Object>();
		    						String tradeno = doc.getElementsByTagName("transaction_id").item(0).getFirstChild().getNodeValue();
		    	                	tradeparam.put("outtradeno", outtradeno);
		    	                	tradeparam.put("tradeno", tradeno);
		    	                	tradeparam.put("paymenttype", "2");
		    	                	orderdao.updateTradeInfo4OpOrder(tradeparam);
		    	                	
		    	                	String orderno = (String) tradeinfo.get("orderno");
		    	                	PassengerOrder order = orderdao.getOrderByOrderno4Op(orderno);
		    	                	userid = order.getUserid();
		    	                	amount = parseDouble(order.getOrderamount());

									//添加记录
									Map<String,Object> expenses = new HashMap<String,Object>();
									expenses.put("logid", GUIDGenerator.newGUID());
									expenses.put("userid",userid);
									expenses.put("expensetype",5);
									expenses.put("money",amount);
									expenses.put("remark","微信公众号支付");
									expenses.put("tradetype","1");
									expenses.put("detailtype","1");
									expenses.put("operateresult","0");
									userdao.addExpenses4OpSec(expenses);

									//优惠券处理
									//优惠券处理
				    				dillCouponUseInfo4Op(order);
									//网约车返现
//									try{
//										Map<String,Object> awardparams = new HashMap<String,Object>();
//										awardparams.put("usertype","1");
//										awardparams.put("passengerphone",order.getPassengerphone());
//										awardparams.put("money",amount);
//										PeUser peuser = userdao.getPeUserById(userid);
//										awardparams.put("userphone",peuser.getAccount());
//										carserviceapi.dealRequestWithToken("/AwardPassenger/AwardPoint", HttpMethod.POST, null, awardparams, JSONObject.class);
//									}catch (Exception e){
//										logger.error("返现出错了",e);
//									}
		    					}
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
		}
    }
	
	/**
	 * 处理优惠券的使用信息
	 * @param userid
	 * @param orderno
	 */
	private void dillCouponUseInfo4Op(Map<String,Object> orderinfo){
		if(orderinfo==null){
			return ;
		}
		String orderno = (String) orderinfo.get("orderno");
		Map<String,Object> pp = new HashMap<String,Object>();
		pp.put("orderno", orderno);
		Map<String,Object> useinfo = userdao.getOrderCouponUseInfo(pp);
		if(useinfo!=null){
			String userid = (String) orderinfo.get("userid");
			double amount = parseDouble(orderinfo.get("orderamount"));
			String ordertype = (String) orderinfo.get("ordertype");
			double couponmoney = parseDouble(useinfo.get("couponmoney"));
			if(isTaxiOrder(ordertype)){
				//出租车
				String paymentmethod = (String) orderinfo.get("paymentmethod");
				if("0".equalsIgnoreCase(paymentmethod)){
					//线上支付
					amount += parseDouble(orderinfo.get("schedulefee"));
				}
			}
			if(couponmoney<=amount){
				useinfo.put("discountamount", couponmoney);
			}else{
				useinfo.put("discountamount", amount);
			}
			//使用优惠券
			userdao.useCoupon(useinfo);
			//更新优惠券的实际抵用金额
			userdao.updateCouponDiscountamount(useinfo);
			//优惠券使用明细
			String couponid = (String) useinfo.get("couponidref");
			Map<String,Object> couponinfo = userdao.getCouponInfo(couponid);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("id", GUIDGenerator.newGUID());
			params.put("userid", userid);
			params.put("couponidref", couponid);
			params.put("usetype", 2);
			params.put("amount", couponmoney);
			params.put("remark", couponinfo!=null?couponinfo.get("name"):"");
			userdao.addCouponDetail(params);
		}
	}
	
	/**
	 * 处理优惠券的使用信息
	 * @param userid
	 * @param orderno
	 */
	private void dillCouponUseInfo4Op(PassengerOrder orderinfo){
		if(orderinfo==null){
			return ;
		}
		String orderno = orderinfo.getOrderno();
		Map<String,Object> pp = new HashMap<String,Object>();
		pp.put("orderno", orderno);
		Map<String,Object> useinfo = userdao.getOrderCouponUseInfo(pp);
		if(useinfo!=null){
			String userid = orderinfo.getUserid();
			double amount = parseDouble(orderinfo.getOrderamount());
			double couponmoney = parseDouble(useinfo.get("couponmoney"));
			if(couponmoney<=amount){
				useinfo.put("discountamount", couponmoney);
			}else{
				useinfo.put("discountamount", amount);
			}
			//使用优惠券
			userdao.useCoupon(useinfo);
			//更新优惠券的实际抵用金额
			userdao.updateCouponDiscountamount(useinfo);
			//优惠券使用明细
			String couponid = (String) useinfo.get("couponidref");
			Map<String,Object> couponinfo = userdao.getCouponInfo(couponid);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("id", GUIDGenerator.newGUID());
			params.put("userid", userid);
			params.put("couponidref", couponid);
			params.put("usetype", 2);
			params.put("amount", couponmoney);
			params.put("remark", couponinfo!=null?couponinfo.get("name"):"");
			userdao.addCouponDetail(params);
		}
	}
	
}

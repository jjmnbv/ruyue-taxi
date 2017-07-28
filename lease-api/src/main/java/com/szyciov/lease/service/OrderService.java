package com.szyciov.lease.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.dto.PagingResponse;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.MinOrderInfo;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubSendrules;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.enums.OrderEnum;
import com.szyciov.lease.dao.OrderDao;
import com.szyciov.lease.dto.request.GetFreeDriverRequest;
import com.szyciov.lease.entity.FavUser;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.lease.param.GetSendInfoParam;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.message.OrderMessage;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.org.entity.OrgMostAddress;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.param.BaseOrgParam;
import com.szyciov.param.Select2Param;
import com.szyciov.passenger.util.MessageUtil;
import com.szyciov.util.PageBean;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("OrderService")
public class OrderService {
	private Logger logger = LoggerFactory.getLogger(OrderService.class);
	private OrderDao dao;
	private TemplateHelper templateHelper = new TemplateHelper();
	
	@Resource(name="OrderDao")
	public void setMapper(OrderDao dao) {
		this.dao = dao;
	}
	
	public JSONObject getMinOrderInfo(String orderno){
		JSONObject result = new JSONObject();
		MinOrderInfo order = dao.getMinOrderInfo(orderno);
		result.put("order", order);
		return result;
	}
	
	public JSONObject getSpecialDrivers(PubDriverInBoundParam param){
		JSONObject result = new JSONObject();
		List<PubDriver> list = dao.getSpecialDrivers(param);
		result.put("count", list.size());
		result.put("specialdrivers", list);
		if(param.getOrderno() != null && !param.getOrderno().isEmpty()){
			param.setPushcount(param.getPushcount() + list.size());
			dao.updatePushNum(param);
		}
		return result;
	}
	
	public JSONObject getDriversInBound(PubDriverInBoundParam param){
		JSONObject result = new JSONObject();
		List<PubDriver> list = dao.getPubDriverInBound(param);
		result.put("count", list.size());
		result.put("drivers", list);
		if(param.getOrderno() != null && !param.getOrderno().isEmpty()){
			param.setPushcount(param.getPushcount() + list.size());
			dao.updatePushNum(param);
		}
		return result;
	}
	
	public JSONObject getSendRuleInfo(GetSendInfoParam param){
		JSONObject result = new JSONObject();
		PubSendrules rule = new PubSendrules();
		rule.setLeasescompanyid(param.getCompanyid());
		rule.setVehicletype(Integer.valueOf(OrderEnum.ORDERSTYPE_CAR.code));
		rule.setUsetype("0");
		rule.setCity(param.getCity());
		rule = dao.getSendRule(rule);
		result.put("sendrule", rule);
		return result;
	}
	
	public JSONObject checkOrderState(String orderno){
		OrgOrder order = dao.checkOrderState(orderno);
		JSONObject result = new JSONObject();
		result.put("order", order);
		result.put("orderno", order.getOrderno());
		return result;
	}
	
	public JSONObject getCarTypes(GetCarTypesParam param){
		List<LeVehiclemodels> list = dao.getCarTypes(param);
		JSONObject result = new JSONObject();
		if(list == null || list.isEmpty()){
			result.put("status", Retcode.NOCARTYPEINCITY.code);
			result.put("message", Retcode.NOCARTYPEINCITY.msg);
		}else{
			String fileserver = SystemConfig.getSystemProperty("fileserver")+"/";
			for(LeVehiclemodels lv : list){
				if(lv.getLogo() != null && !lv.getLogo().isEmpty()){
					lv.setLogo(fileserver + lv.getLogo());
				}
			}
			result.put("count", list.size());
			result.put("cartypes", list);
		}
		return result;
	}
	
	public JSONObject getOrgOrderCost(OrderCostParam ocp){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApi");
		JSONObject result = templateHelper.dealRequestWithFullUrl(
				carserviceApiUrl+"/OrderApi/GetOrderCost", 
				HttpMethod.POST, 
				ocp, 
				JSONObject.class,
				new HashMap<>());
		//如果是下单过程中还要判断余额够不够
		if(result.getInt("status") == Retcode.OK.code && ocp.getOrderprop() == 0){
			OrgOrganCompanyRef oocr = dao.getOrgBalance(ocp);
			if(oocr == null) return result;
			double cost = Double.parseDouble(result.getString("cost").replace("元", ""));
			boolean payable = oocr.getBalance() - cost >= 0;
			result.put("payable", payable);
		}
		return result;
	}
	
	/**
	 *  此方法已废弃,使用carservice-api中的下单接口
	 * @deprecated
	 * @param orderInfo
	 * @return
	 */
	public JSONObject createOrgOrder(OrgOrder orderInfo){
//		JSONObject result = new JSONObject();
//		result = checkPermission(orderInfo);
//		if(result.getInt("status") == Retcode.OK.code){
//			OrderCost oc = StringUtil.parseJSONToBean(result.getJSONObject("ordercost").toString(), OrderCost.class);
//			orderInfo.setEstimatedmileage(StringUtil.formatNum(oc.getMileage()/1000, 1));
//			orderInfo.setEstimatedtime((int)StringUtil.formatNum(oc.getTimes()/60,1));
//			orderInfo.setEstimatedcost(oc.getCost());
//			orderInfo.setPricecopy(JSONObject.fromObject(oc).toString());
//			orderInfo.setOrderno(GUIDGenerator.newGUID());
//			orderInfo.setUndertime(new Date());
//			orderInfo.setCreatetime(new Date());
//			orderInfo.setUpdatetime(new Date());
//			orderInfo.setOrderstatus(OrderState.WAITTAKE.state);
//			orderInfo.setPaymentstatus(PayState.NOTPAY.state);
//			orderInfo.setReviewstatus(ReviewState.NOTREVIEW.state);
//			orderInfo.setStatus(1);               //数据生效
//			orderInfo.setUserhidden("1");   //消息未读
//			dao.createOrgOrder(orderInfo);
//			result.clear();
//			result.put("orderno", orderInfo.getOrderno());
//			logger.info("下单成功.订单号:" + orderInfo.getOrderno());
//			//创建司机接单消息,放入队列
//			MessageUtil.sendMessage(new OrderMessage(orderInfo,OrderMessage.UNDERORDER));
//		}
		return null;
	}
	
	public JSONObject createOpOrder(OpOrder orderInfo){
		return createOpOrder_test(orderInfo);
	}
	
	public  JSONObject  getOrganList(String companyid){
		JSONObject result = new JSONObject();
		List<OrgOrgan> list = dao.getOrganList(companyid);
		result.put("count", list.size());
		result.put("orglist", list);
		return result;
	}
	
	public JSONObject getPriOrganList(String companyid) {
		JSONObject result = new JSONObject();
		List<OrgOrgan> list = dao.getPriOrganList(companyid);
		result.put("count", list.size());
		result.put("priOrganList", list);
		return result;
	}
	
	public JSONObject getUseCarReason(){
		JSONObject result = new JSONObject();
		List<Dictionary> list = dao.getUseCarReason();
		result.put("count", list.size());
		result.put("reason", list);
		return result;
	}
	
	public JSONObject getCities(){
		JSONObject result = new JSONObject();
		List<String> list = dao.getCities();
		result.put("count", list.size());
		result.put("city" ,list);
		return result;
	}
	
	public JSONObject getOrgUserForSelect(Select2Param param){
		JSONObject result = new JSONObject();
		List<Select2Entity> list = dao.getOrgUserForSelect(param);
		result.put("count", list.size());
		result.put("list", list);
		return result;
	}
	
	public JSONObject getFavUserForSelect(Select2Param param){
		JSONObject result = new JSONObject();
		List<Select2Entity> list = dao.getFavUserForSelect(param);
		result.put("count", list.size());
		result.put("list", list);
		return result;
	}
	
	public JSONObject getOrgUser(Select2Param param){
		int userCount = dao.getOrgUserCount(param);
		List<OrgUser> list = dao.getOrgUser(param);
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(param.getsEcho()+"");
		pageBean.setiTotalDisplayRecords(userCount);
		pageBean.setiTotalRecords(userCount);
		pageBean.setAaData(list);
		return JSONObject.fromObject(pageBean);
	}
	
	public JSONObject getFavUser(Select2Param param){
		int userCount = dao.getFavUserCount(param);
		List<FavUser> list = dao.getFavUser(param);
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(param.getsEcho()+"");
		pageBean.setiTotalDisplayRecords(userCount);
		pageBean.setiTotalRecords(userCount);
		pageBean.setAaData(list);
		return JSONObject.fromObject(pageBean);
	}
	
	/**
	 * 获取机构用户常用地址列表
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getMostAddress(BaseOrgParam param) {
		JSONObject result = new JSONObject();
		List<OrgMostAddress> list = dao.getMostAddress(param);
		result.put("count", list.size());
		result.put("mostaddress", list);
		return result;
	}
	
	/**
	 * 下单页面查询业务城市
	 * @param param
	 * @return
	 */
	public JSONObject getOrgUserBusCity(Map<String, Object> param) {
		JSONObject json = new JSONObject();
		List<PubCityAddr> list = null;
		if(OrderEnum.USETYPE_PUBLIC.code.equals(param.get("usetype"))) { //因公用车
			list = dao.getOrgUserPubBusCity(param);
		} else { //因私用车
			list = dao.getOrgUserPriBusCity(param);
		}
		if(null == list) {
			list = new ArrayList<PubCityAddr>();
		}
		json.put("count", list.size());
		json.put("cities", list);
		return json;
	}

    /**
     * 获取指派司机列表
     * @param model 查询条件
     * @return 司机列表
     */
    public PagingResponse getManualSelectDriver(GetFreeDriverRequest model){
        int pageNum = 1;

        if (model.getiDisplayLength() != 0) {
            pageNum = model.getiDisplayStart() / model.getiDisplayLength() + 1;
        } else {
            PageHelper.startPage(1, 0);
        }

        PageHelper.startPage(pageNum, model.getiDisplayLength());
        Page page = (Page) this.dao.getManualSelectDriver(model);

        PagingResponse res = new PagingResponse();
        res.setAaData(page.getResult());
        res.setiTotalDisplayRecords((int) page.getTotal());
        res.setiTotalRecords((int) page.getTotal());
        res.setsEcho("");

        return res;
    }

	//================================All Test Method====================================//
	
	public JSONObject getOrgUser_test(Select2Param param){
//		String sEcho = (String) param.get("sEcho");// DataTables 用来生成的信息
//		String iDisplayStart = (String) param.get("iDisplayStart");//显示的起始索引
//		String iDisplayLength = (String) param.get("iDisplayLength");//显示的行数
//		String iSortCol_0 = (String) param.get("iSortCol_0");//被排序的列
//		String sSortDir_0 = (String) param.get("sSortDir_0");//排序的方向 "desc" 或者 "asc".
//		String sSearch = (String) param.get("sSearch");//全局搜索字段 
//		for(String key : param.keySet()){
//			System.out.println(key+":"+param.get(key));
//		}
//		String[] keys = new String[]{"sEcho","iDisplayStart","iDisplayLength","iSortCol_0","sSortDir_0","sSearch","orgid"};
//		for(String key : keys){
//			System.out.println(key+":"+param.get(key));
//		}
		JSONObject result = new JSONObject();
		JSONArray json = new JSONArray();
		json.addAll(dao.getOrgUser(param));
	    result.put("sEcho", param.getsEcho());
		result.put("iTotalRecords", json.size());
		result.put("iTotalDisplayRecords", json.size());
		result.put("data", json);
		return result;
	}
	
	public JSONObject getFavUser_test(Select2Param param){
//		String sEcho = (String) param.get("sEcho");// DataTables 用来生成的信息
//		String iDisplayStart = (String) param.get("iDisplayStart");//显示的起始索引
//		String iDisplayLength = (String) param.get("iDisplayLength");//显示的行数
//		String iSortCol_0 = (String) param.get("iSortCol_0");//被排序的列
//		String sSortDir_0 = (String) param.get("sSortDir_0");//排序的方向 "desc" 或者 "asc".
//		String sSearch = (String) param.get("sSearch");//全局搜索字段 
//		for(String key : param.keySet()){
//			System.out.println(key+":"+param.get(key));
//		}
//		String[] keys = new String[]{"sEcho","iDisplayStart","iDisplayLength","iSortCol_0","sSortDir_0","sSearch","userid"};
//		for(String key : keys){
//			System.out.println(key+":"+param.get(key));
//		}
		JSONObject result = new JSONObject();
		JSONArray json = new JSONArray();
		json.addAll(dao.getFavUser(param));
	    result.put("sEcho", param.getsEcho());
		result.put("iTotalRecords", json.size());
		result.put("iTotalDisplayRecords", json.size());
		result.put("data", json);
		return result;
	}
	public JSONObject createOrgOrder_test(OrgOrder orderInfo){
		JSONObject result = new JSONObject();
		int rn = Integer.parseInt(getRandomNumber(1));
		if(rn<8){                                  //成功
			orderInfo.setOrderno("YC2016080800001");
			orderInfo.setEstimatedtime(20);                       //预估时间
			orderInfo.setEstimatedmileage(26.0D);           //预估里程
			orderInfo.setEstimatedcost(45.00D);                //预估费用
			result.put("orderid", orderInfo.getOrderno());
			logger.info("下单成功.订单号:" + orderInfo.getOrderno());
			//创建司机接单消息,放入队列
			MessageUtil.sendMessage(new OrderMessage(orderInfo,OrderMessage.UNDERORDER));
		}else{                                     //没有权限
			result.put("status", Retcode.NOPERMISSION.code);
			result.put("message", Retcode.NOPERMISSION.msg);
			logger.info("下单失败.没有权限.");
		}
		return result;
	}
	
	public JSONObject createOpOrder_test(OpOrder orderInfo){
		JSONObject result = new JSONObject();
		int rn = Integer.parseInt(getRandomNumber(1));
		if(rn<8){                                                              //成功
			orderInfo.setOrderno("YC2016080800001");
			result.put("orderid", orderInfo.getOrderno());
			logger.info("下单成功.订单号:" + orderInfo.getOrderno());
			//创建司机接单消息,放入队列
			MessageUtil.sendMessage(new OrderMessage(orderInfo,OrderMessage.UNDERORDER));
		}else{                                                                     //没有权限
			result.put("status", Retcode.NOPERMISSION.code);
			result.put("message", Retcode.NOPERMISSION.msg);
			logger.info("下单失败.没有权限.");
		}
		return result;
	}
	
	public JSONObject createManOrgOrder_test(OpOrder orderInfo){
		JSONObject result = new JSONObject();
		int rn = Integer.parseInt(getRandomNumber(1));
		if(rn<8){                                                              //成功
			orderInfo.setOrderno("YC2016080800001");
			result.put("order", JSONObject.fromObject(orderInfo));
			logger.info("下单成功.订单号:" + orderInfo.getOrderno());
			//创建司机接单消息,放入队列
//			MessageConnection.add(new OrderMessage(orderInfo,false,OrderMessage.MANTICORDER));
		}else{                                                                     //没有权限
			result.put("status", Retcode.NOPERMISSION.code);
			result.put("message", Retcode.NOPERMISSION.msg);
			logger.info("下单失败.没有权限.");
		}
		return result;
	}
	
	public JSONObject createManOpOrder_test(OpOrder orderInfo){
		JSONObject result = new JSONObject();
		int rn = Integer.parseInt(getRandomNumber(1));
		if(rn<8){                                                              //成功
			orderInfo.setOrderno("YC2016080800001");
			result.put("order", JSONObject.fromObject(orderInfo));
			logger.info("下单成功.订单号:" + orderInfo.getOrderno());
			//创建司机接单消息,放入队列
//			MessageConnection.add(new OrderMessage(orderInfo,false,OrderMessage.MANTICORDER));
		}else{                                                                     //没有权限
			result.put("status", Retcode.NOPERMISSION.code);
			result.put("message", Retcode.NOPERMISSION.msg);
			logger.info("下单失败.没有权限.");
		}
		return result;
	}
	
	//=======================Util Method===========================//
	public String formatDate(Date src,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA);
		return sdf.format(src);
	}
	
	public String getRandomNumber(int len){
		String str = "0123456789";
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<len;i++){
			sb.append(str.charAt((int)(Math.random()*str.length())));
		}
		return sb.toString();
	}

	/**
	 * 获取如约的机构列表
	 * @param param
	 * @return
	 */
	public Map<String,Object> getOrgans(Map<String, Object> param) {
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("status",Retcode.OK.code);
		res.put("message",Retcode.OK.msg);
		Map<String,Object> ruyueinfo = dao.getRUYUE(param);
		if(ruyueinfo!=null){
            param.put("companyid",ruyueinfo.get("id"));
        }
		res.put("organs",dao.getOrgans(param));
		return res;
	}

	public Map<String,Object> getCompanyidByruyue(){
        Map<String,Object> res = new HashMap<String,Object>();
        Map<String,Object> ruyueinfo = dao.getRUYUE(new HashedMap());
        if(ruyueinfo!=null){
            res.put("companyid", ruyueinfo.get("id"));
        }else{
            res.put("companyid", "");
        }

        return res;
    }


	/**
	 * 获取机构的用户列表
	 * @param param
	 * @return
	 */
    public Map<String,Object> getOrganUsers(Map<String, Object> param) {
    	Map<String,Object> res = new HashMap<String,Object>();
    	res.put("status",Retcode.OK.code);
    	res.put("message",Retcode.OK.msg);
    	res.put("users",dao.getOrganUsers(param));
    	return res;
    }

}

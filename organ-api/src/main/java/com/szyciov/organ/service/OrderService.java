/**
 * 
 */
package com.szyciov.organ.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.entity.CancelParty;
import com.szyciov.entity.DataStatus;
import com.szyciov.entity.Retcode;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.org.entity.OrgMostAddress;
import com.szyciov.org.entity.OrgMostContact;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.param.BaseOrgParam;
import com.szyciov.org.param.OrgUserParam;
import com.szyciov.organ.dao.OrderDao;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.SystemConfig;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @ClassName OrderService 
 * @author Efy Shu
 * @date 2016年10月17日 下午4:04:26 
 */
@Service("OrderService")
public class OrderService {
	
	private TemplateHelper templateHelper = new TemplateHelper();
	
	private OrderDao dao;
	
	/**  
	 * 设置dao  
	 * @param dao dao  
	 */
	@Resource(name = "OrderDao")
	public void setDao(OrderDao dao) {
		this.dao = dao;
	}

	/**
	 * 根据ID查机构用户
	 * @return
	 */
	public JSONObject getOrgUserById(OrgUserParam param){
		JSONObject result = new JSONObject();
		OrgUser user = dao.getOrgUserById(param);
		if(user == null) return null;
		result.put("user", user);
		return result;
	}
	
	/**
	 * 获取租赁公司列表
	 * 
	 * @return
	 */
	public JSONObject getLeaseList(BaseOrgParam param) {
		JSONObject result = new JSONObject();
		List<LeLeasescompany> list = dao.getLeaseList(param.getOrgUser().getOrganId());
		//设置如约的士
		list.get(0).setShortName("如约的士");
		if(list == null) return null;
		result.put("count", list.size());
		result.put("lease", list);
		return result;
	}

	/**
	 * 获取订单状态
	 * 
	 * @param orderno
	 * @return
	 */
	public JSONObject checkOrderState(String orderno) {
		JSONObject result = new JSONObject();
		OrgOrder order = dao.getOrgOrder(orderno);
		if(order == null) {
			result.put("status", Retcode.ORDERNOTEXIT.code);
			result.put("message", Retcode.ORDERNOTEXIT.msg);
		}else{
			result.put("order", order);
			result.put("orderno", order.getOrderno());
		}
		return result;
	}

	/**
	 * 取消订单
	 * 
	 * @param orderno
	 * @return
	 */
	public JSONObject cancelOrder(String orderno) {
		JSONObject result = new JSONObject();
		OrgOrder order = dao.getOrgOrder(orderno);
		if(order == null) {
			result.put("status", Retcode.ORDERNOTEXIT.code);
			result.put("message", Retcode.ORDERNOTEXIT.msg);
		}else{
			order.setOrderstatus(OrderState.CANCEL.state);
			order.setCanceltime(new Date());
			order.setCancelparty(CancelParty.PASSENGER.code);
			dao.updateOrgOrder(order);
		}
		return result;
	}
	
	/**
	 * 添加机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject addMostContact(OrgMostContact param) {
		JSONObject result = new JSONObject();
		BaseOrgParam bop = new BaseOrgParam();
		bop.setUserid(param.getUserid());
		bop.setsSearch(param.getPhone());
		List<OrgMostContact> list = dao.getMostContact(bop);
		if(list != null && !list.isEmpty()) {
			result.put("status", Retcode.USERALREADYEXIST.code);
			result.put("message", Retcode.USERALREADYEXIST.msg);
			return result; //如果联系人已存在则不添加
		}
		param.setId(GUIDGenerator.newGUID());
		param.setStatus(DataStatus.OK.code);
		param.setUpdatetime(new Date());
		param.setCreatetime(new Date());
		dao.addMostContact(param);
		return result;
	}
	
	/**
	 * 删除机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject delMostContact(OrgMostContact param) {
		JSONObject result = new JSONObject();
		param.setStatus(2);
		dao.delMostContact(param);
		return result;
	}
	
	/**
	 * 获取机构用户常用联系人列表
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getMostContact(BaseOrgParam param) {
		JSONObject result = new JSONObject();
		List<OrgMostContact> list = dao.getMostContact(param);
		if(list == null || list.isEmpty()) return null;
		result.put("count", list.size());
		result.put("mostcontact", list);
		return result;
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
		if(list == null) return null;
		result.put("count", list.size());
		result.put("mostaddress", list);
		return result;
	}
	
	/**
	 * 获取预估费用
	 * @param param
	 * @return
	 */
	public JSONObject getOrderCost(OrderCostParam param){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl+"/OrderApi/GetOrderCost", 
				HttpMethod.POST, 
				param.getToken(),
				param, 
				JSONObject.class);
		//如果是下单过程中还要判断余额够不够
		if(result.getInt("status") == Retcode.OK.code && param.getOrderprop() == 0){
			OrgOrganCompanyRef oocr = dao.getOrgBalance(param);
			if(oocr == null) {
				result.put("payable", false);
			}else{
				double cost = Double.parseDouble(result.getString("cost").replace("元", ""));
				boolean payable = oocr.getBalance() - cost > 0;
				result.put("payable", payable);
			}
		}
		return result;
	}
	
	/**
	 * 创建机构订单
	 * 
	 * @param orderInfo
	 * @return
	 */
	public JSONObject createOrgOrder(OrgOrder orderInfo) {
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl+"/OrderApi/CreateOrgOrder", 
				HttpMethod.POST, 
				null,
				orderInfo, 
				JSONObject.class);
		return result;
	}

	/**
	 * 获取机构车型
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getCarTypes(GetCarTypesParam param) {
		JSONObject result = new JSONObject();
		List<LeVehiclemodels> list = dao.getCarTypes(param);
		if(list == null) return null;
		JSONArray arr = new JSONArray();
		for(LeVehiclemodels lv : list){
			String fileserver = SystemConfig.getSystemProperty("fileserver") + "/";
			if(lv.getLogo() != null && !lv.getLogo().isEmpty()) lv.setLogo(fileserver + lv.getLogo());
			JSONObject cartype = new JSONObject();
			cartype.put("id", lv.getId());
			cartype.put("model", lv.getName());
			cartype.put("startprice", lv.getStartprice());
			cartype.put("rangeprice", lv.getRangeprice());
			cartype.put("timeprice", lv.getTimeprice());
			cartype.put("img", lv.getLogo());
			arr.add(cartype);
		}
		result.put("count", list.size());
		result.put("cartypes", arr);
		return result;
	}
	
	/**
	 * 查询因公用车业务城市
	 * @param param
	 * @return
	 */
	public JSONObject getOrgUserPubBusCity(Map<String, Object> param) {
		JSONObject json = new JSONObject();
		List<PubCityAddr> list = dao.getOrgUserPubBusCity(param);
		if(null == list) {
			list = new ArrayList<PubCityAddr>();
		}
		json.put("count", list.size());
		json.put("cities", list);
		return json;
	}
	
}

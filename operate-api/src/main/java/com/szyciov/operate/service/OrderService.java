package com.szyciov.operate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.szyciov.dto.PagingResponse;
import com.szyciov.op.dto.request.GetManualDriverRequest;
import com.szyciov.op.dto.request.GetTaxiManualDriverRequest;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.driver.enums.OrderState;
import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.entity.CancelParty;
import com.szyciov.entity.MinOrderInfo;
import com.szyciov.entity.Retcode;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.entity.PeMostAddress;
import com.szyciov.op.entity.PeMostContact;
import com.szyciov.op.param.BaseOpParam;
import com.szyciov.operate.dao.OrderDao;
import com.szyciov.param.Select2Param;
import com.szyciov.util.PageBean;
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

	public JSONObject getMinOrderInfo(String orderno){
		JSONObject result = new JSONObject();
		MinOrderInfo order = dao.getMinOrderInfo(orderno);
		result.put("order", order);
		return result;
	}
	
	/**
	 * 获取加入toC业务的租赁公司列表
	 * @return
	 */
	public JSONObject getCompanyList(){
		JSONObject result = new JSONObject();
		List<LeLeasescompany> list = dao.getCompanyList();
		if(list == null) return null;
		result.put("count", list.size());
		result.put("lease", list);
		return result;
	}

    /**
     * 获取服务车企
     * @return r
     */
    public JSONObject getBelongCompanyList(){
        JSONObject result = new JSONObject();
        List list = dao.getBelongCompanyList();
        if(list == null) return null;
        result.put("count", list.size());
        result.put("lease", list);
        return result;
    }

	/**
	 * 创建个人订单
	 * 
	 * @param orderInfo
	 * @return
	 */
	public JSONObject createOpOrder(OpOrder orderInfo) {
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl+"/OrderApi/CreateOpOrder", 
				HttpMethod.POST, 
				null,
				orderInfo, 
				JSONObject.class);
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
		OpOrder order = dao.getOpOrder(orderno);
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
	 * @deprecated
	 * @param orderno
	 * @return
	 */
	public JSONObject cancelOrder(String orderno) {
		JSONObject result = new JSONObject();
		OpOrder order = dao.getOpOrder(orderno);
		if(order == null) {
			result.put("status", Retcode.ORDERNOTEXIT.code);
			result.put("message", Retcode.ORDERNOTEXIT.msg);
		}else{
			order.setOrderstatus(OrderState.CANCEL.state);
			order.setCanceltime(new Date());
			order.setCancelparty(CancelParty.PASSENGER.code);
			dao.updateOpOrder(order);
		}
		return result;
	}
	
	/**
	 * 将订单变更为人工派单
	 * @deprecated
	 * @param orderno
	 * @return
	 */
	public JSONObject manticOrder(String orderno) {
		JSONObject result = new JSONObject();
		OpOrder order = dao.getOpOrder(orderno);
		if(order == null) {
			result.put("status", Retcode.ORDERNOTEXIT.code);
			result.put("message", Retcode.ORDERNOTEXIT.msg);
		}else{
			order.setOrderstatus(OrderState.MANTICSEND.state);
			order.setUpdatetime(new Date());
			dao.updateOpOrder(order);
		}
		return result;
	}
	
	/**
	 * 添加机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject addMostContact(PeMostContact param) {
		JSONObject result = new JSONObject();
		dao.addMostContact(param);
		return result;
	}
	
	/**
	 * 删除机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject delMostContact(PeMostContact param) {
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
	public JSONObject getMostContact(Select2Param param) {
		int userCount = dao.getMostContactCount(param);
		List<PeMostContact> list = dao.getMostContact(param);
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(param.getsEcho()+"");
		pageBean.setiTotalDisplayRecords(userCount);
		pageBean.setiTotalRecords(userCount);
		pageBean.setAaData(list);
		return JSONObject.fromObject(pageBean);
	}

	/**
	 * 获取个人用户常用联系人列表
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getMostContactForSelect(Select2Param param){
		JSONObject result = new JSONObject();
		List<Select2Entity> list = dao.getMostContactForSelect(param);
		result.put("count", list.size());
		result.put("list", list);
		return result;
	}
	
	/**
	 * 获取个人用户列表
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getPeUserForSelect(Select2Param param){
		JSONObject result = new JSONObject();
		List<Select2Entity> list = dao.getPeUserForSelect(param);
		result.put("count", list.size());
		result.put("list", list);
		return result;
	}
	
	/**
	 * 获取机构用户常用地址列表
	 * 
	 * @param param
	 * @return
	 */
	public JSONObject getMostAddress(BaseOpParam param) {
		JSONObject result = new JSONObject();
		List<PeMostAddress> list = dao.getMostAddress(param);
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
		result.put("payable", true);
//		if(result.getInt("status") == Retcode.OK.code && param.getOrderprop() == 0){
//			OrgOrganCompanyRef oocr = dao.getOrgBalance(param);
//			if(oocr == null) {
//				result.put("payable", false);
//			}else{
//				double cost = Double.parseDouble(result.getString("cost").replace("元", ""));
//				boolean payable = oocr.getBalance() - cost > 0;
//				result.put("payable", payable);
//			}
//		}
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
		List<OpVehiclemodels> list = dao.getCarTypes(param);
		if(list == null) return null;
		String fileserver = SystemConfig.getSystemProperty("fileserver") + "/";
		JSONArray arr = new JSONArray();
		for(OpVehiclemodels ov : list){
			//添加服务器路径
			if(ov.getLogo() != null && !ov.getLogo().isEmpty()) ov.setLogo(fileserver + ov.getLogo());
			JSONObject cartype = new JSONObject();
			cartype.put("id", ov.getId());
			cartype.put("name", ov.getName());
			cartype.put("startprice", ov.getStartprice());
			cartype.put("rangeprice", ov.getRangeprice());
			cartype.put("timeprice", ov.getTimeprice());
			cartype.put("logo", ov.getLogo());
			arr.add(cartype);
		}
		result.put("count", list.size());
		result.put("cartypes", arr);
		return result;
	}
	
	/**
	 * 获取出租车订单费用
	 * @param param
	 * @return
	 */
	public JSONObject getOpTaxiOrderCost(OrderCostParam param){
		String carserviceApiUrl = SystemConfig.getSystemProperty("carserviceApiUrl");
		JSONObject result = templateHelper.dealRequestWithFullUrlToken(
				carserviceApiUrl+"/OrderApi/GetOpTaxiOrderCost", 
				HttpMethod.POST, 
				param.getToken(),
				param, 
				JSONObject.class);
		return result;
	}
	
	/**
	 * 查询网约车业务城市
	 * @param param
	 * @return
	 */
	public JSONObject getNetBusCity(Map<String, Object> param) {
		JSONObject json = new JSONObject();
		List<PubCityAddr> list = dao.getNetBusCity(param);
		if(null == list) {
			list = new ArrayList<PubCityAddr>();
		}
		json.put("count", list.size());
		json.put("cities", list);
		return json;
	}
	
	public JSONObject getTaxiBusCity(Map<String, String> param) {
		JSONObject cityJson = new JSONObject();
		
		//查询所有省/城市信息
		List<Map<String, String>> cityList = dao.getTaxiBusCity(param);
		//城市数据分类
		if(null != cityList && !cityList.isEmpty()) {
			for (Map<String, String> obj : cityList) {
				String provinceid = obj.get("provinceid");
				String provincecity = obj.get("provincecity");
				String provincemarkid = obj.get("provincemarkid");
				String cityid = obj.get("cityid");
				String city = obj.get("city");
				String citymarkid = obj.get("citymarkid");
				
				JSONArray citys = null;
				if(cityJson.containsKey(provincecity)) {
					citys = cityJson.getJSONObject(provincecity).getJSONArray("citys");
				} else {
					citys = new JSONArray();
				}
				//城市信息
				JSONObject cityObj = new JSONObject();
				cityObj.put("id", cityid);
				cityObj.put("text", city);
				cityObj.put("markid", citymarkid);
				citys.add(cityObj);
				//省信息
				JSONObject provinceObj = new JSONObject();
				provinceObj.put("id", provinceid);
				provinceObj.put("markid", provincemarkid);
				provinceObj.put("citys", citys);
				
				cityJson.put(provincecity, provinceObj);
			}
		}
		return cityJson;
	}

    /**
     * 获取指派司机列表
     * @param model 查询条件
     * @return 司机列表
     */
    public PagingResponse getManualSelectDriver(GetManualDriverRequest model){
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

    /**
     * 获取指派司机列表
     * @param model 查询条件
     * @return 司机列表
     */
    public PagingResponse getTaxiManualSelectDriver(GetTaxiManualDriverRequest model){
        int pageNum = 1;

        if (model.getiDisplayLength() != 0) {
            pageNum = model.getiDisplayStart() / model.getiDisplayLength() + 1;
        } else {
            PageHelper.startPage(1, 0);
        }

        PageHelper.startPage(pageNum, model.getiDisplayLength());
        Page page = (Page) this.dao.getTaxiManualSelectDriver(model);

        PagingResponse res = new PagingResponse();
        res.setAaData(page.getResult());
        res.setiTotalDisplayRecords((int) page.getTotal());
        res.setiTotalRecords((int) page.getTotal());
        res.setsEcho("");

        return res;
    }
}

package com.szyciov.operate.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.dto.PagingResponse;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.op.dto.request.GetManualDriverRequest;
import com.szyciov.op.dto.request.GetTaxiManualDriverRequest;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.PeMostContact;
import com.szyciov.op.param.BaseOpParam;
import com.szyciov.operate.service.OrderService;
import com.szyciov.param.Select2Param;
import com.szyciov.util.ApiExceptionHandle;

import net.sf.json.JSONObject;

/**
 * @ClassName OrderController 
 * @author Efy Shu
 * @Description 机构端订单控制类
 * @date 2016年10月17日 下午4:04:08 
 */
@Controller
public class OrderController extends ApiExceptionHandle {
	private OrderService os;
	
	@Resource(name="OrderService")
	public void setMs(OrderService os) {
		this.os = os;
	}
	
	/**
	 * 获取加入toC业务的租赁公司
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetCompanyList")
	public JSONObject getCompanyList(){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getCompanyList();
		return checkResult(result);
	}

    /**
     * 获取服务车企
     *
     * @return r
     */
    @ResponseBody
    @RequestMapping(value = "api/Order/GetBelongCompanyList")
    public JSONObject getBelongCompanyList(){
        starttime.set(System.currentTimeMillis());
        JSONObject result = os.getBelongCompanyList();
        return checkResult(result);
    }
	
	/**
	 * 获取订单状态
	 * 
	 * @param orderno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/CheckOrderState/{orderno}")
	public JSONObject checkOrderState(@PathVariable String orderno){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.checkOrderState(orderno);
		return checkResult(result);
	}

	/**
	 * 取消订单
	 * 
	 * @param orderno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/CancelOrder/{orderno}")
	public JSONObject cancelOrder(@PathVariable String orderno){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.cancelOrder(orderno);
		return checkResult(result);
	}
	
	/**
	 * 将订单变更为人工派单
	 * 
	 * @param orderno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/ManticOrder/{orderno}")
	public JSONObject manticOrder(@PathVariable String orderno){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.manticOrder(orderno);
		return checkResult(result);
	}
	
	/**
	 * 添加机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/AddMostContact")
	public JSONObject addMostContact(@RequestBody PeMostContact param ,HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.addMostContact(param);
		return checkResult(result);
	}
	
	/**
	 * 删除机构用户常用联系人
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/DelMostContact")
	public JSONObject delMostContact(@RequestBody PeMostContact param , HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.delMostContact(param);
		return checkResult(result);
	}
	
	/**
	 * 获取机构用户常用联系人列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetMostContact")
	public JSONObject getMostContact(@RequestBody Select2Param param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getMostContact(param);
		return checkResult(result);
	}
	
	/**
	 * 获取个人用户常用联系人列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetMostContactForSelect")
	public JSONObject getMostContactForSelect(@RequestBody Select2Param param,HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getMostContactForSelect(param);
		return checkResult(result);
	}
	
	/**
	 * 获取个人用户列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetPeUserForSelect")
	public JSONObject getPeUserForSelect(@RequestBody Select2Param param,HttpServletRequest request){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getPeUserForSelect(param);
		return checkResult(result);
	}
	
	/**
	 * 获取机构用户常用地址列表
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetMostAddress")
	public JSONObject getMostAddress(@RequestBody BaseOpParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getMostAddress(param);
		return checkResult(result);
	}
	
	/**
	 * 创建机构订单
	 * 
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/CreateOpOrder")
	public JSONObject createOpOrder(@RequestBody OpOrder orderInfo){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.createOpOrder(orderInfo);
		return checkResult(result);
	}

	/**
	 * 获取机构车型
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetCarTypes")
	public JSONObject getCarTypes(@RequestBody GetCarTypesParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result =  os.getCarTypes(param);
		return checkResult(result);
	}
	
	/**
	 * 获取预估费用
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetOrderCost")
	public JSONObject getOrderCost(@RequestBody OrderCostParam param){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getOrderCost(param);
		return checkResult(result);
	}
	
	/**
	 * 获取订单简要信息
	 * 
	 * @param orderno
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "api/Order/GetMinOrderInfo/{orderno}")
	public JSONObject getMinOrderInfo(@PathVariable String orderno){
		starttime.set(System.currentTimeMillis());
		JSONObject result = os.getMinOrderInfo(orderno);
		return checkResult(result);
	}
	
	/**
	 * 获取出租车订单费用
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "api/Order/GetOpTaxiOrderCost")
	@ResponseBody
	public JSONObject getOpTaxiOrderCost(@RequestBody OrderCostParam param) {
		return os.getOpTaxiOrderCost(param);
	}
	
	/**
	 * 查询网约车业务城市
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "api/Order/GetNetBusCity")
	@ResponseBody
	public JSONObject getNetBusCity(@RequestBody Map<String, Object> param) {
		JSONObject result =  os.getNetBusCity(param);
		return checkResult(result);
	}
	
	/**
	 * 查询出租车业务城市
	 * @return
	 */
	@RequestMapping(value = "api/Order/GetTaxiBusCity")
	@ResponseBody
	public JSONObject getTaxiBusCity(@RequestBody Map<String, String> param) {
		return os.getTaxiBusCity(param);
	}

    /**
     * 获取指派司机列表
     * @param model 查询条件
     * @return 司机列表
     */
    @ResponseBody
    @RequestMapping(value = "api/Order/GetManualSelectDriver")
    public PagingResponse getManualSelectDriver(@RequestBody GetManualDriverRequest model) {
        starttime.set(System.currentTimeMillis());
        PagingResponse result =  os.getManualSelectDriver(model);
        return result;
    }

    /**
     * 获取出租车指派司机列表
     * @param model 查询条件
     * @return 司机列表
     */
    @ResponseBody
    @RequestMapping(value = "api/Order/GetTaxiManualSelectDriver")
    public PagingResponse getTaxiManualSelectDriver(@RequestBody GetTaxiManualDriverRequest model) {
        starttime.set(System.currentTimeMillis());
        PagingResponse result =  os.getTaxiManualSelectDriver(model);
        return result;
    }
}

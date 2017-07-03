package com.szyciov.organ.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgOrderDetails;
import com.szyciov.org.param.OrgOrderQueryParam;
import com.szyciov.organ.service.MyOrderService;
import com.szyciov.util.BaseController;
import com.szyciov.util.Constants;
import com.szyciov.util.PageBean;

/**
 * 控制器
 */
@Controller
public class MyOrderController extends BaseController {
	private static final Logger logger = Logger.getLogger(MyOrderController.class);

	public MyOrderService myOrderService;

	@Resource(name = "MyOrderService")
	public void setOrgOrganService(MyOrderService myOrderService) {
		this.myOrderService = myOrderService;
	}
	/**
	 * <p>
	 * 初始化、查询
	 * </p>
	 *
	 * @param QueryParam
	 * @return
	 */
	@RequestMapping(value = "api/MyOrder/GetOrgderByQuery", method = RequestMethod.POST)
	@ResponseBody
	public PageBean getOrgderByQuery(@RequestBody OrgOrderQueryParam orgOrderQueryParam) {
		return myOrderService.getOrgderByQuery(orgOrderQueryParam);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/MyOrder/GetQueryCompany", method = RequestMethod.POST)
	@ResponseBody
	public List<LeLeasescompany> getQueryCompany() {
		return myOrderService.getQueryCompany();
	}
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/MyOrder/GetById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public OrgOrderDetails getById(@PathVariable String id) {
		return myOrderService.getById(id);
	}
	
	/**
	 * <p>
	 * 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/MyOrder/ExportExcel", method = RequestMethod.POST)
	@ResponseBody
	public List<OrgOrder> exportExcel(@RequestBody OrgOrderQueryParam orgOrderQueryParam) {
		return myOrderService.exportExcel(orgOrderQueryParam);
	}
	
	
	/**
	 * 根据订单号获取订单信息
	 * @param orderno
	 * @return
	 */
	@RequestMapping(value = "api/MyOrder/GetOrgOrderByOrderno/{orderno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrgOrderByOrderno(@PathVariable String orderno) {
		return myOrderService.getOrgOrderByOrderno(orderno);
	}
	
	/**
	 * 
	 * 
	 */
	@RequestMapping(value = "api/MyOrder/CancelOrder/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void cancelOrder(@PathVariable String id) {
		myOrderService.cancelOrder(id);
	}
	
	/**
	 * 
	 * 
	 */
	@RequestMapping(value = "api/MyOrder/UpdatePaytype", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePaytype(@RequestBody Map<String,Object> payparam) {
		return myOrderService.updatePaytype(payparam);
	}
	
	/**
	 * 评价
	 * 
	 */
	@RequestMapping(value = "api/MyOrder/UpdateUserrate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateUserrate(@RequestBody OrgOrder orgOrder) {
		return myOrderService.updateUserrate(orgOrder);
	}
	
	@RequestMapping(value = "MyOrder/DillWXPayed", method = RequestMethod.POST)
	public void dillWXPayed(HttpServletRequest req,HttpServletResponse res){
		myOrderService.dillWXPayed(req,res);
	}
	
	@RequestMapping(value = "MyOrder/DillZFBPayed", method = RequestMethod.POST)
	public void dillZFBPayed(HttpServletRequest req,HttpServletResponse res){
		myOrderService.dillZFBPayed(req,res);
	}
	
	/**
	 * CheckOrderState
	 * @param loginparam
	 * @return
	 */
	@RequestMapping(value = "api/MyOrder/CheckOrderState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkOrderState(@RequestBody Map<String,Object> params,HttpServletRequest request,HttpServletResponse response){
		return myOrderService.checkOrderState(params);
	}
	/**
	 * <p>
	 * 根据订单id关联查出 租赁公司信息 
	 * </p>
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "api/MyOrder/GetOrdernoByLeasescompany/{id}", method = RequestMethod.GET)
	@ResponseBody
	public LeLeasescompany getOrdernoByLeasescompany(@PathVariable String id) {
		return myOrderService.getOrdernoByLeasescompany(id);
	}
}

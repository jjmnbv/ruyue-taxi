package com.szyciov.carservice.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.DriverDao;
import com.szyciov.driver.entity.DriverInfo;
import com.szyciov.driver.entity.OrderInfoDetail;
import com.szyciov.dto.driver.PubDriverSelectDto;
import com.szyciov.entity.PubDriver;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.param.OrderManageQueryParam;
import com.szyciov.lease.param.PubDriverInBoundParam;
import com.szyciov.lease.param.PubDriverSelectParam;
import com.szyciov.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("driverService")
public class DriverService {
	
	private DriverDao driverDao;
	
	@Resource(name="driverDao")
	public void setDriverDao(DriverDao driverDao) {
		this.driverDao = driverDao;
	}
	
	public List<Map<String, Object>> getAllDriverByQuery(OrderManageQueryParam queryParam) {
		queryParam.setDriverPaging(null);
		return driverDao.getDriverByQuery(queryParam);
	}

	public List<PubDriverSelectDto> listPubDriverBySelect(PubDriverSelectParam param) {
		return driverDao.listPubDriverBySelect(param);
	}

	/**
	 * 联想查询资格证号
	 * @param param
	 * @return
	 */
	public List<PubDriverSelectDto> listPubDriverBySelectJobNum(PubDriverSelectParam param) {
		return driverDao.listPubDriverBySelectJobNum(param);
	}
	
	/**
	 * 查询运管平台附近司机(含加入toC平台的租赁公司司机)
	 * @param param
	 * @return
	 */
	public JSONObject getOpDriverInBound(PubDriverInBoundParam param) {
		List<PubDriver> list = driverDao.getOpDriverInBound(param);
		JSONObject result = new JSONObject();
		if(list == null || list.isEmpty()){
			result.put("count", 0);
			result.put("list", new JSONArray());
		}else{
			result.put("count", list.size());
			result.put("list", list);
		}
		if(param.getOrderno() != null && !param.getOrderno().isEmpty()){
			param.setPushcount(param.getPushcount() + list.size());
			driverDao.updatePushNum(param);
		}
		return result;
	}
	
	/**
	 * 查询租赁平台附近司机
	 * @param param
	 * @return
	 */
	public JSONObject getLeDriverInBound(PubDriverInBoundParam param) {
		List<PubDriver> list = driverDao.getLeDriverInBound(param);
		JSONObject result = new JSONObject();
		if(list == null || list.isEmpty()){
			result.put("count", 0);
			result.put("list", new JSONArray());
		}else{
			result.put("count", list.size());
			result.put("list", list);
		}
		if(param.getOrderno() != null && !param.getOrderno().isEmpty()){
			param.setPushcount(param.getPushcount() + list.size());
			driverDao.updatePushNum(param);
		}
		return result;
	}
	
	
	/**
	 * 查询租赁平台附近司机
	 * @param param
	 * @return
	 */
	public List<PubDriver> getLeDriversInBound(PubDriverInBoundParam param) {
		List<PubDriver> list = driverDao.getLeDriverInBound(param);
//		if(param.getOrderno() != null && !param.getOrderno().isEmpty()){
//			param.setPushcount(param.getPushcount() + list.size());
//			driverDao.updatePushNum(param);
//		}
		return list;
	}
	
	/**
	 * 获取租赁公司车型级别列表(升序)
	 * @param param
	 * @return
	 */
	public List<LeVehiclemodels> getLeDriverLevels(PubDriverInBoundParam param){
		return driverDao.getLeDriverLevels(param);
	}
	
	/**
	 * 查询特殊司机
	 * @param param
	 * @return
	 */
	public JSONObject getSpecialDriver(PubDriverInBoundParam param) {
		List<PubDriver> list = driverDao.getSpecialDriver(param);
		JSONObject result = new JSONObject();
		if(list == null || list.isEmpty()){
			result.put("count", 0);
			result.put("list", new JSONArray());
		}else{
			result.put("count", list.size());
			result.put("list", list);
		}
		if(param.getOrderno() != null && !param.getOrderno().isEmpty()){
			param.setPushcount(param.getPushcount() + list.size());
			driverDao.updatePushNum(param);
		}
		return result;
	}

	public List<PubDriver> getSpecialDrivers(PubDriverInBoundParam param){
		List<PubDriver> list = driverDao.getSpecialDriver(param);
		if(param.getOrderno() != null && !param.getOrderno().isEmpty()){
			param.setPushcount(param.getPushcount() + list.size());
			driverDao.updatePushNum(param);
		}
		return list;
	}
	
	/**
	 * 根据ID获取司机信息(包含车辆信息)
	 * @param driverid
	 * @return
	 */
	public DriverInfo getDriverInfoById(String driverid){
		return driverDao.getDriverInfoById(driverid);
	}
	
    /**
     * 获取不推送网约车司机
     * 即刻单：
     * 1、服务中不推；
     * 2、已有即刻单不推；
     * 3、预约单用车时间在一小时内不推；
     * <p>
     * 预约单
     * 1、已有预约单不推 预约单；
     * 2、已有即刻单 且 时间间隔在3小时内 不推；
     * 3、服务中不推；
     *
     * @param order o
     * @return 司机
     */
    public JSONObject getUnExceptDriver(OrderInfoDetail order) {
        List<String> list = null;
		String usetime = StringUtil.formatDate(order.getUsetime(), "yyyy-MM-dd HH:mm:ss");
        if(order.isIsusenow()){
            list = this.driverDao.getRightNowUnExceptDriver(usetime);
        }else{
            list = this.driverDao.getOrderUnExceptDriver(usetime);
        }

        JSONObject result = new JSONObject();
        if(list == null || list.isEmpty()){
            result.put("count", 0);
            result.put("list", new JSONArray());
        }else{
            result.put("count", list.size());
            result.put("list", list);
        }
        return result;
    }
}

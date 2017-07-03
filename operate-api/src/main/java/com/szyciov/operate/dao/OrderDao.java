package com.szyciov.operate.dao;

import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.entity.MinOrderInfo;
import com.szyciov.entity.Select2Entity;
import com.szyciov.lease.dto.response.GetFreeDriverResponse;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.OrgOrganCompanyRef;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.param.GetCarTypesParam;
import com.szyciov.op.dto.request.GetManualDriverRequest;
import com.szyciov.op.dto.request.GetTaxiManualDriverRequest;
import com.szyciov.op.dto.response.GetManualDriverResponse;
import com.szyciov.op.dto.response.GetTaxiManualDriverResponse;
import com.szyciov.op.entity.OpOrder;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.entity.PeMostAddress;
import com.szyciov.op.entity.PeMostContact;
import com.szyciov.op.param.BaseOpParam;
import com.szyciov.operate.mapper.OrderMapper;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.param.Select2Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderDao
 * @author Efy Shu
 * @Description TODO(这里用一句话描述这个类的作用)
 * @date 2016年10月17日 下午4:06:26
 */

@Repository("OrderDao")
public class OrderDao {
	private OrderMapper mapper;

	@Resource
	public void setMapper(OrderMapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * 获取加入toC业务的租赁公司列表
	 * @return {@linkplain LeLeasescompany}
	 */
	public List<LeLeasescompany> getCompanyList(){
		return mapper.getCompanyList();
	}

    /**
     * 获取服务车企列表
     * @return r
     */
    public List getBelongCompanyList(){
        return mapper.getBelongCompanyList();
    }

	/**
	 * 获取车型列表
	 * @param param
	 * @return {@linkplain OpVehiclemodels}
	 * @see {@linkplain GetCarTypesParam}
	 */
	public List<OpVehiclemodels> getCarTypes(GetCarTypesParam param){
		return mapper.getCarTypes(param);
	}

	/**
	 * 添加常用联系人
	 * @param param
	 * @see {@linkplain PeMostContact}
	 */
	public void addMostContact(PeMostContact param){
		mapper.addMostContact(param);
	}

	/**
	 * 删除常用联系人
	 * @param param
	 * @see {@linkplain PeMostContact}
	 */
	public void delMostContact(PeMostContact param){
		mapper.delMostContact(param);
	}

	/**
	 * 获取常用联系人列表
	 * @param param
	 * @return {@linkplain PeMostContact}
	 */
	public List<PeMostContact> getMostContact(Select2Param param){
		return mapper.getMostContact(param);
	}

	/**
	 * 获取常用联系人(总数)
	 * @param param
	 * @return {@linkplain PeMostContact}
	 */
	public int getMostContactCount(Select2Param param){
		return mapper.getMostContactCount(param);
	}


	/**
	 * 获取常用联系地址列表
	 * @param param
	 * @return {@linkplain PeMostAddress}
	 * @see {@linkplain BaseOpParam}
	 */
	public List<PeMostAddress> getMostAddress(BaseOpParam param){
		return mapper.getMostAddress(param);
	}

	/**
	 * 获取常用联系人列表
	 * @param param
	 * @return {@linkplain Select2Entity}
	 * @see {@linkplain Select2Param}
	 */
	public List<Select2Entity> getMostContactForSelect(Select2Param param){
		return mapper.getMostContactForSelect(param);
	}


	/**
	 * 获取个人用户列表
	 * @param param
	 * @return {@linkplain Select2Entity}
	 * @see {@linkplain Select2Param}
	 */
	public List<Select2Entity> getPeUserForSelect(Select2Param param){
		return mapper.getPeUserForSelect(param);
	}

	/**
	 * 获取订单状态
	 * @param orderno
	 * @return {@linkplain OrgOrder}
	 */
	public OpOrder getOpOrder(String orderno){
		return mapper.getOpOrder(orderno);
	}

	/**
	 * 更新OpOrder
	 * @param order
	 * @see {@linkplain OpOrder}
	 */
	public void updateOpOrder(OpOrder order){
		mapper.updateOpOrder(order);
	}

	/**
	 * 获取个人账户余额
	 * @param param
	 * @return {@linkplain OrgOrganCompanyRef}
	 * @see {@linkplain OrderCostParam}
	 */
	public OrgOrganCompanyRef getPeBalance(OrderCostParam param){
		return mapper.getPeBalance(param);
	}

	/**
	 * 获取订单简要信息
	 * @param orderno
	 * @return
	 */
	public MinOrderInfo getMinOrderInfo(String orderno){
		return mapper.getMinOrderInfo(orderno);
	}

	public List<PubCityAddr> getNetBusCity(Map<String, Object> param) {
		return mapper.getNetBusCity(param);
	}

	public List<Map<String, String>> getTaxiBusCity(Map<String, String> param) {
		return mapper.getTaxiBusCity(param);
	}

    public List<GetManualDriverResponse> getManualSelectDriver(GetManualDriverRequest model){
        List<GetManualDriverResponse> list = mapper.getManualSelectDriver(model);
        return list;
    }

    public List<GetTaxiManualDriverResponse> getTaxiManualSelectDriver(GetTaxiManualDriverRequest model){
        List<GetTaxiManualDriverResponse> list = mapper.getTaxiManualSelectDriver(model);
        return list;
    }
}

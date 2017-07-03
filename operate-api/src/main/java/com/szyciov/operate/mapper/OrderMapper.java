package com.szyciov.operate.mapper;

import com.szyciov.driver.param.OrderCostParam;
import com.szyciov.entity.MinOrderInfo;
import com.szyciov.entity.Select2Entity;
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
import com.szyciov.param.Select2Param;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderMapper
 * @author Efy Shu
 * @date 2016年10月17日 下午4:06:12
 */
public interface OrderMapper {
	public List<LeLeasescompany> getCompanyList();

    List getBelongCompanyList();

	public List<OpVehiclemodels> getCarTypes(GetCarTypesParam param);

	public List<PeMostContact> getMostContact(Select2Param param);
	
	public int getMostContactCount(Select2Param param);
	
	public List<Select2Entity> getMostContactForSelect(Select2Param param);
	
	public List<Select2Entity> getPeUserForSelect(Select2Param param);
	
	public void addMostContact(PeMostContact param);
	
	public void delMostContact(PeMostContact param);

	public List<PeMostAddress> getMostAddress(BaseOpParam param);

	public OpOrder getOpOrder(@Param("orderno") String orderno);

	public void updateOpOrder(OpOrder order);

	public OrgOrganCompanyRef getPeBalance(OrderCostParam param);
	
	public MinOrderInfo getMinOrderInfo(@Param("orderno") String orderno);
	
	public List<PubCityAddr> getNetBusCity(Map<String, Object> param);
	
	public List<Map<String, String>> getTaxiBusCity(Map<String, String> param);

    List<GetManualDriverResponse> getManualSelectDriver(GetManualDriverRequest model);

    List<GetTaxiManualDriverResponse> getTaxiManualSelectDriver(GetTaxiManualDriverRequest model);
}

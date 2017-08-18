package com.szyciov.lease.mapper;

import java.util.List;

import com.szyciov.dto.pubVehInsur.PubVehInsurQueryDto;
import com.szyciov.entity.PubVehInsur;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.param.pubVehInsurance.AddPubVehInsurs;
import com.szyciov.lease.param.pubVehInsurance.PubVehInsurQueryParam;
/**
 * 
 * Title:PubVehInsurMapper.java
 * Description: 车辆保险
 * @author zhangdd
 * Create at:2017年8月11日 上午10:45:26
 */
public interface PubVehInsurMapper {
	
	/**
	 * 查询车辆保险信息
	 */
    List<PubVehInsurQueryDto> getPubVehInsurListByQuery(PubVehInsurQueryParam queryParam);
	
    /**
     * 查询车辆保险返回总行数
     */
	int getPubVehInsurListCountByQuery(PubVehInsurQueryParam queryParam);
    
	/**
	 * 删除车辆保险
	 */
	void deletePubVehInsur(String id);
	
	/**
	 * 更改车辆保险信息
	 */
	void updatePubVehInsur(PubVehInsur pubVehInsur);
	
	/**
	 * 根据id查询车辆保险
	 */
	PubVehInsurQueryDto getPubVehInsurById(String id);
	
	/**
	 * 批量插入车辆保险信息
	 */
	int addPubVehInsurs(AddPubVehInsurs pubInsurs);
	
	/**
	 * 根据车牌号获取车辆
	 */
	PubVehicle getPubVehicleByPlateNo(String fullplateno);
	
	/**
	 * 判断车辆保险是否重复
	 */
	int checkPubVehInsur(PubVehInsur pubVehInsur);
	
	/**
	 * 导出数据
	 */
	List<PubVehInsurQueryDto> exportExcel(PubVehInsurQueryParam queryParam);
}

package com.szyciov.operate.dao;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.op.entity.PubVehicle;
import com.szyciov.op.entity.PubVehicleScope;
import com.szyciov.op.param.PubVehicleQueryParam;
import com.szyciov.op.param.vehicleManager.VehicleIndexQueryParam;
import com.szyciov.op.vo.vehiclemanager.VehicleExportVo;
import com.szyciov.op.vo.vehiclemanager.VehicleIndexVo;
import com.szyciov.operate.dto.VehicleQueryDto;
import com.szyciov.operate.mapper.PubVehicleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("PubVehicleDao")
public class PubVehicleDao {
	public PubVehicleDao() {
	}
	@Autowired
	private PubVehicleMapper mapper;


	public void createPubVehicle(PubVehicle pubVehicle) {
		mapper.createPubVehicle(pubVehicle);
	}
	
	public int updatePubVehicle(PubVehicle pubVehicle) {
		return mapper.updatePubVehicle(pubVehicle);
	}
	
	public int checkPubVehicle(PubVehicle pubVehicle){
		return mapper.checkPubVehicle(pubVehicle);
	};
	
	public void deletePubVehicle(String id){
		mapper.deletePubVehicle(id);
	};
	
//	public int checkDelete(String id,String platformType){
//		return mapper.checkDelete(id,platformType);
//	};
	
	public List<VehicleIndexVo> getPubVehicleListByQuery(VehicleIndexQueryParam param){
		return mapper.getPubVehicleListByQuery(param);
	};
	
	public int getPubVehicleListCountByQuery(VehicleIndexQueryParam param){
		return mapper.getPubVehicleListCountByQuery(param);
	};
	
	public List<Map<String, Object>> getBrandCars(PubVehicle pubVehicle){
		return mapper.getBrandCars(pubVehicle);
	};
	
	public List<PubVehicle> getServiceCars(String platformType){
		return mapper.getServiceCars(platformType);
	};
	
	public List<City> getCity( String queryText, String platformType){
		return mapper.getCity(queryText,platformType);
	};
	
	public PubVehicle getById(String id){
		return mapper.getById(id);
	}
	
	public List<City> getPubCityaddr(){
		return mapper.getPubCityaddr();
	};
	
	public List<Dictionary> getPlateNoProvince(){
		return mapper.getPlateNoProvince();
	};
	
	public List<Dictionary> getPlateNoCity(String id){
		return mapper.getPlateNoCity(id);
	};

	public List<VehicleExportVo> exportExcel(VehicleIndexQueryParam param){
		return mapper.exportExcel(param);
	};
	public PubDictionary getPlateCode(String plateOne) {
		return mapper.getPlateCode(plateOne);
	}
	public String getPlateCity(PubDictionary plateTow) {
		return mapper.getPlateCity(plateTow);
	}
	public PubCityAddr getCityCode(String city) {
		return mapper.getCityCode(city);
	}
	public String getVehclineId(PubVehicle vehcline) {
		return mapper.getVehclineId(vehcline);
	}
	public List<PubVehicleScope> getVehicleidByVehicleScope(String id){
		return mapper.getVehicleidByVehicleScope(id);
	};
	public List<VehicleQueryDto> listVehicleAndBindInfo(PubVehicleQueryParam pubVehicleQueryParam){
		return mapper.listVehicleAndBindInfo(pubVehicleQueryParam);
	}

	/**
	 * 返回车辆信息集合总数
	 * @param pubVehicleQueryParam
	 * @return
	 */
	public Integer getlistVehicleAndBindInfoCount(PubVehicleQueryParam pubVehicleQueryParam){
		return mapper.getlistVehicleAndBindInfoCount(pubVehicleQueryParam);
	}

	/**
	 * 修改车辆信息，目前仅支持二期新增字段，可新增修改字段；
	 * @param pubVehicle	修改对象
	 * @return
	 */
	public int updateVehicleById(PubVehicle pubVehicle){
		return mapper.updateVehicleById(pubVehicle);
	}

	/**
	 * 修改车辆对应司机
	 * @param pubVehicle
	 * @return
	 */
	public int updateDriverId(PubVehicle pubVehicle){
		return mapper.updateDriverId(pubVehicle);
	}

	public List<Map<String, Object>> getPubVehicleSelectByUser(Map<String, Object> param) {
        return mapper.getPubVehicleSelectByUser(param);
    }

}

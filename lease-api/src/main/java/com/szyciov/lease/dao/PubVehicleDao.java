package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.lease.dto.VehicleQueryDto;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.entity.PubVehicleScope;
import com.szyciov.lease.mapper.PubVehicleMapper;
import com.szyciov.lease.param.PubVehicleQueryParam;

@Repository("PubVehicleDao")
public class PubVehicleDao {
	public PubVehicleDao() {
	}

	private PubVehicleMapper mapper;

	@Resource
	public void setMapper(PubVehicleMapper mapper) {
		this.mapper = mapper;
	}
	
	public void createPubVehicle(PubVehicle pubVehicle) {
		mapper.createPubVehicle(pubVehicle);
	}
	
	public void updatePubVehicle(PubVehicle pubVehicle) {
		mapper.updatePubVehicle(pubVehicle);
	}
	
	public int checkPubVehicle(PubVehicle pubVehicle){
		return mapper.checkPubVehicle(pubVehicle);
	};
	
	public void deletePubVehicle(String id){
		mapper.deletePubVehicle(id);
	};
	
	public int checkDelete(String id){
		return mapper.checkDelete(id);
	};
	
	public List<PubVehicle> getPubVehicleListByQuery(PubVehicleQueryParam pubVehicleQueryParam){
		return mapper.getPubVehicleListByQuery(pubVehicleQueryParam);
	};
	
	public int getPubVehicleListCountByQuery(PubVehicleQueryParam pubVehicleQueryParam){
		return mapper.getPubVehicleListCountByQuery(pubVehicleQueryParam);
	};
	
	public List<Map<String, Object>> getBrandCars(PubVehicle pubVehicle){
		return mapper.getBrandCars(pubVehicle);
	};
	
	public List<PubVehicle> getServiceCars(String leasesCompanyId){
		return mapper.getServiceCars(leasesCompanyId);
	};
	
	public List<PubVehicle> getCity(String leasesCompanyId){
		return mapper.getCity(leasesCompanyId);
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
	
	public List<PubVehicle> exportExcel(PubVehicleQueryParam pubVehicleQueryParam){
		return mapper.exportExcel(pubVehicleQueryParam);
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
	public List<PubVehicleScope> getVehicleidByVehicleScope(List<PubVehicle> list){
		return mapper.getVehicleidByVehicleScope(list);
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
}

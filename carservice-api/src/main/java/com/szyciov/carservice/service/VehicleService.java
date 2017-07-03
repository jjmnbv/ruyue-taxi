package com.szyciov.carservice.service;

import com.szyciov.carservice.mapper.VehicleMapper;
import com.szyciov.dto.pubvehicle.PubVehicleSelectDto;
import com.szyciov.param.pubvehicle.PubVehicleSelectParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

	@Autowired
	private VehicleMapper vehicleMapper;


	/**
	 * 根据车架号联想查询车辆ID
	 * @param param
	 * @return
	 */
	public List<PubVehicleSelectDto> listVehicleBySelect(PubVehicleSelectParam param) {
		return vehicleMapper.listVehicleBySelect(param);
	}


	/**
	 * 根据车架号联想查询车辆ID
	 * @param param
	 * @return
	 */
	public List<PubVehicleSelectDto> listVehicleByPlateno(PubVehicleSelectParam param) {
		return vehicleMapper.listVehicleByPlateno(param);
	}


}

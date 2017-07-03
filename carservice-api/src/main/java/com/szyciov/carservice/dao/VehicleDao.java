package com.szyciov.carservice.dao;

import com.szyciov.carservice.mapper.VehicleMapper;
import com.szyciov.dto.pubvehicle.PubVehicleSelectDto;
import com.szyciov.param.pubvehicle.PubVehicleSelectParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleDao {

	@Autowired
	private VehicleMapper vehicleMapper;



	public List<PubVehicleSelectDto> listVehicleBySelect(PubVehicleSelectParam param) {
		return vehicleMapper.listVehicleBySelect(param);
	}

}

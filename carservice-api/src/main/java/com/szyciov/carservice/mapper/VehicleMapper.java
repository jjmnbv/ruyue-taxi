package com.szyciov.carservice.mapper;

import com.szyciov.dto.pubvehicle.PubVehicleSelectDto;
import com.szyciov.param.pubvehicle.PubVehicleSelectParam;

import java.util.List;

public interface VehicleMapper {


	List<PubVehicleSelectDto> listVehicleBySelect(PubVehicleSelectParam param) ;

	List<PubVehicleSelectDto> listVehicleByPlateno(PubVehicleSelectParam param) ;
	
}

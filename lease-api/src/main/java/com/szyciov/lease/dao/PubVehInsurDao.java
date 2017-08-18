package com.szyciov.lease.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.dto.pubVehInsur.PubVehInsurQueryDto;
import com.szyciov.entity.PubVehInsur;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.mapper.PubVehInsurMapper;
import com.szyciov.lease.param.pubVehInsurance.AddPubVehInsurs;
import com.szyciov.lease.param.pubVehInsurance.PubVehInsurQueryParam;

@Repository("PubVehInsurDao")
public class PubVehInsurDao {
	
	public PubVehInsurDao() {
	}

	private PubVehInsurMapper mapper;

	@Resource
	public void setMapper(PubVehInsurMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<PubVehInsurQueryDto> getPubVehInsurListByQuery(PubVehInsurQueryParam queryParam){
		return mapper.getPubVehInsurListByQuery(queryParam);
	}
	
	public int getPubVehInsurListCountByQuery(PubVehInsurQueryParam queryParam){
		return mapper.getPubVehInsurListCountByQuery(queryParam);
	}
	 
	public void deletePubVehInsur(String id){
		mapper.deletePubVehInsur(id);
	};
	
	public void updatePubVehInsur(PubVehInsur pubDriver){
		mapper.updatePubVehInsur(pubDriver);
	}
	
	public PubVehInsurQueryDto getPubVehInsurById(String id){
		return mapper.getPubVehInsurById(id);
	}
	
	public int addPubVehInsurs(AddPubVehInsurs pubInsurs){
		return mapper.addPubVehInsurs(pubInsurs);
	}
	
	public PubVehicle getPubVehicleByPlateNo(String fullplateno){
		return mapper.getPubVehicleByPlateNo(fullplateno);
	}
	
	public int checkPubVehInsur(PubVehInsur pubVehInsur){
		return mapper.checkPubVehInsur(pubVehInsur);
	}
	
	public List<PubVehInsurQueryDto> exportExcel(PubVehInsurQueryParam queryParam){
		return mapper.exportExcel(queryParam);
	}
	
}

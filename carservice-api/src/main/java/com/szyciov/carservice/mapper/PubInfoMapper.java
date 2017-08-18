package com.szyciov.carservice.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.entity.Dictionary;
import com.szyciov.entity.PubAdImage;
import com.szyciov.entity.PubAirPortAddr;
import com.szyciov.entity.PubSysVersion;
import com.szyciov.lease.entity.PubCityAddr;
import org.springframework.web.bind.annotation.RequestBody;

public interface PubInfoMapper {
	
	public List<PubAirPortAddr> getAirPorts();
	
	public List<PubCityAddr> getCities();
	
	public List<Dictionary> getUseCarReason();
	
	public PubSysVersion getPubSysVersion(PubSysVersion param);
	
	public PubAdImage getPubAdImage(PubAdImage param);
	
	public void createPubSysVersion(PubSysVersion param);

	public boolean checkTokenValid(Map<String, Object> param);
	
	public List<Map<String, String>> getCitySelect1();

	public List<PubCityAddr> getCitySelect2();

	public PubCityAddr getCityById(String id);

	public List<Map<String, String>> getCityIdByName(String name);

	public List<Map<String,String>> getSearchCitySelect(PubCityAddr pubCityAddr);
	
}

package com.szyciov.carservice.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.PubInfoDao;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.PubAdImage;
import com.szyciov.entity.PubAirPortAddr;
import com.szyciov.entity.PubSysVersion;
import com.szyciov.lease.entity.PubCityAddr;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("PubInfoService")
public class PubInfoService {
	
	private PubInfoDao dao;
	
	@Resource(name="PubInfoDao")
	public void setPubInfoDao(PubInfoDao dao) {
		this.dao = dao;
	}
	
	public JSONObject getAirPorts(){
		JSONObject result = new JSONObject();
		List<PubAirPortAddr> list = dao.getAirPorts();
		if(list == null) return null;
		result.put("count", list.size());
		result.put("airports", list);
		return result;
	}
	
	public JSONObject getCities(){
		JSONObject result = new JSONObject();
		List<PubCityAddr> list = dao.getCities();
		if(list == null) return null;
		result.put("count", list.size());
		result.put("cities", list);
		return result;
	}
	
	public JSONObject getUseCarReason() {
		JSONObject result = new JSONObject();
		List<Dictionary> list = dao.getUseCarReason();
		if(list == null) return null;
		result.put("count", list.size());
		result.put("usecarreason", list);
		return result;
	}
	
	public JSONObject getPubAdImage(PubAdImage param){
		JSONObject result = new JSONObject();
		if(param.getVersion() != null && !param.getVersion().trim().isEmpty()){
			if(param.getVersion().toLowerCase().startsWith("v")){
				param.setVersion(param.getVersion().toLowerCase());
			}else {
				param.setVersion("v"+param.getVersion());
			}
		}
		PubAdImage adimage = dao.getPubAdImage(param);
		if(adimage == null) return null;
		result.put("adimage", adimage);
		return result;
	}
	
	public JSONObject getPubSysVersion(PubSysVersion param){
		JSONObject result = new JSONObject();
		PubSysVersion version = dao.getPubSysVersion(param);
		if(version == null) return null;
		result.put("version", version);
		return result;
	}
	
	public JSONObject createPubSysVersion(PubSysVersion param){
		JSONObject result = new JSONObject();
		dao.createPubSysVersion(param);
		return result;
	}

	public boolean checkTokenValid(Map<String, Object> param) {
		return dao.checkTokenValid(param);
	}
	
	public JSONObject getCitySelect1() {
		JSONObject cityJson = new JSONObject();
		
		//查询所有省/城市信息
		List<Map<String, String>> cityList = dao.getCitySelect1();
		//城市数据分类
		if(null != cityList && !cityList.isEmpty()) {
			for (Map<String, String> obj : cityList) {
				String provinceid = obj.get("provinceid");
				String provincecity = obj.get("provincecity");
				String provincemarkid = obj.get("provincemarkid");
				String cityid = obj.get("cityid");
				String city = obj.get("city");
				String citymarkid = obj.get("citymarkid");
				
				JSONArray citys = null;
				if(cityJson.containsKey(provincecity)) {
					citys = cityJson.getJSONObject(provincecity).getJSONArray("citys");
				} else {
					citys = new JSONArray();
				}
				//城市信息
				JSONObject cityObj = new JSONObject();
				cityObj.put("id", cityid);
				cityObj.put("text", city);
				cityObj.put("markid", citymarkid);
				citys.add(cityObj);
				//省信息
				JSONObject provinceObj = new JSONObject();
				provinceObj.put("id", provinceid);
				provinceObj.put("markid", provincemarkid);
				provinceObj.put("citys", citys);
				
				cityJson.put(provincecity, provinceObj);
			}
		}
		return cityJson;
	}
}

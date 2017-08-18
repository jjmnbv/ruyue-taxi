package com.szyciov.carservice.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.carservice.mapper.PubInfoMapper;
import com.szyciov.entity.Dictionary;
import com.szyciov.entity.PubAdImage;
import com.szyciov.entity.PubAirPortAddr;
import com.szyciov.entity.PubSysVersion;
import com.szyciov.lease.entity.PubCityAddr;

@Repository("PubInfoDao")
public class PubInfoDao {
	
	private PubInfoMapper mapper;

	@Resource
	public void setPubInfoMapper(PubInfoMapper mapper) {
		this.mapper = mapper;
	}
	
	/**
	 * 获取机场列表
	 * @return {@linkplain PubAirPortAddr}
	 */
	public List<PubAirPortAddr> getAirPorts(){
		return mapper.getAirPorts();
	}
	
	/**
	 * 获取城市列表
	 * @return {@linkplain PubCityAddr}
	 */
	public List<PubCityAddr> getCities(){
		return mapper.getCities();
	}
	
	/**
	 * 获取用车事由
	 * @return
	 * @see {@linkplain Dictionary}
	 */
	public List<Dictionary> getUseCarReason(){
		return mapper.getUseCarReason();
	}
	
	/**
	 * 根据平台,系统获取版本信息
	 * 不传版本号则查询最新版本
	 * @param param
	 * @return {@linkplain PubSysVersion}
	 */
	public PubSysVersion getPubSysVersion(PubSysVersion param){
		return mapper.getPubSysVersion(param);
	}
	
	/**
	 * 根据平台,图片类型和版本号获取(引导|广告)页
	 * 不传版本号则查询最新版本
	 * @param param
	 * @return {@linkplain PubAdImage}
	 */
	public PubAdImage getPubAdImage(PubAdImage param){
		return mapper.getPubAdImage(param);
	}
	
	/**
	 * 创建版本信息
	 * @param param  
	 * @see {@linkplain PubSysVersion}
	 */
	public void createPubSysVersion(PubSysVersion param){
		mapper.createPubSysVersion(param);
	}

	public boolean checkTokenValid(Map<String, Object> param) {
		return mapper.checkTokenValid(param);
	}
	
	public List<Map<String, String>> getCitySelect1() {
		return mapper.getCitySelect1();
	}

	public List<PubCityAddr> getCitySelect2() {
		return mapper.getCitySelect2();
	}
	
	public PubCityAddr getCityById(String id) {
		return mapper.getCityById(id);
	}


	/**
	 * 获取城市id
	 * @param name
	 * @return
	 */
	public List<Map<String, String>> getCityIdByName(String name){
		return  mapper.getCityIdByName(name);
	}

	public List<Map<String,String>> getSearchCitySelect(PubCityAddr pubCityAddr){
		return  mapper.getSearchCitySelect(pubCityAddr);
	}
}

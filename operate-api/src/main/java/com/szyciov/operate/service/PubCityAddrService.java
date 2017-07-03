package com.szyciov.operate.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.operate.dao.PubCityAddrDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("PubCityAddrService")
public class PubCityAddrService {
	
	private PubCityAddrDao dao;
	@Resource(name = "PubCityAddrDao")
	public void setDao(PubCityAddrDao dao) {
		this.dao = dao;
	}
	
	public JSONObject getPubCityAddrByList() {
		JSONObject ret = new JSONObject();
		
		List<PubCityAddr> cityAddrList = dao.getPubCityAddrByList();
		if(null != cityAddrList && !cityAddrList.isEmpty()) {
			Iterator<PubCityAddr> iterator = cityAddrList.iterator();
			while(iterator.hasNext()) {
				PubCityAddr cityAddr = iterator.next();
				String id = cityAddr.getId();
				String city = cityAddr.getCity();
				String initials = cityAddr.getCityInitials();
				if(StringUtils.isBlank(id) || StringUtils.isBlank(city) || StringUtils.isBlank(initials)) {
					continue;
				}
				//根据字母对城市进行分类
				JSONObject json = new JSONObject();
				json.put("id", id);
				json.put("text", city);
				if(ret.containsKey(initials)) {
					ret.getJSONArray(initials).add(json);
				} else {
					JSONArray arr = new JSONArray();
					arr.add(json);
					ret.put(initials, arr);
				}
			}
		}
		return ret;
	}

}

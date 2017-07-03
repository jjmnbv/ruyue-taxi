package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.PubVehclineDao;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubVehcbrand;
import com.szyciov.lease.entity.PubVehcline;
import com.szyciov.lease.param.PubVehclineQueryParam;
import com.szyciov.util.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("PubVehclineService")
public class PubVehclineService {
	private PubVehclineDao dao;
	@Resource(name = "PubVehclineDao")
	public void setDao(PubVehclineDao dao) {
		this.dao = dao;
	}
	public Map<String, String> createPubVehcline(PubVehcline pubVehcline) {
		Map<String, String> ret = new HashMap<String, String>();
		int count = checkLine(pubVehcline);
		if(count > 0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", pubVehcline.getName()+" 车系已存在");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "保存成功");
			dao.createPubVehcline(pubVehcline);
		}
		return ret;
	}
	public Map<String, String> updatePubVehcline(PubVehcline pubVehcline) {
		Map<String, String> ret = new HashMap<String, String>();
		int count = checkLine(pubVehcline);
		if(count > 0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", pubVehcline.getName()+" 车系已存在");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "保存成功");
			dao.updatePubVehcline(pubVehcline);
		}
		return ret;
	}
	public Map<String, String> deletePubVehcline(String id){
		Map<String, String> ret = new HashMap<String, String>();
		int count = checkDelete(id);
		if(count>0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "该车系已分配在服务车型，请先到服务车型功能解除分配");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "删除成功");
			dao.deletePubVehcline(id);
		}
		return ret;
	};
	
	public PageBean getPubVehclineByQuery(PubVehclineQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PubVehcline> list = getPubVehclineListByQuery(queryParam);
		int iTotalRecords = getPubVehclineListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<PubVehcline> getPubVehclineListByQuery(PubVehclineQueryParam queryParam){
		return dao.getPubVehclineListByQuery(queryParam);
	};
	
	public int getPubVehclineListCountByQuery(PubVehclineQueryParam queryParam){
		return dao.getPubVehclineListCountByQuery(queryParam);
	};
	
	public List<Map<String, Object>> getBrandCars(PubVehcline pubVehcline){
		return dao.getBrandCars(pubVehcline);
	};
	
	public PubVehcline getById(String id){
		return dao.getById(id);
	};
	
	public int checkDelete(String id){
		return dao.checkDelete(id);
	};
	
	public int checkLine(PubVehcline pubVehcline){
		return dao.checkLine(pubVehcline);
	};
	
	public int checkImprot(PubVehcline pubVehcline){
		return dao.checkImprot(pubVehcline);
	};
	
	public JSONObject getBrand(String id){
		JSONObject ret = new JSONObject();
		List<PubVehcbrand> pubVehcline = dao.getBrand(id);
		if(null != pubVehcline && !pubVehcline.isEmpty()) {
			Iterator<PubVehcbrand> iterator = pubVehcline.iterator();
			while(iterator.hasNext()) {
				PubVehcbrand pv = iterator.next();
				String id1 = pv.getId();
				String name = pv.getName();
				String initials = pv.getInitials();
				if(StringUtils.isBlank(id1) || StringUtils.isBlank(name) || StringUtils.isBlank(initials)) {
					continue;
				}
				//根据字母对城市进行分类
				JSONObject json = new JSONObject();
				json.put("id", id1);
				json.put("text", name);
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
	};
	
	public List<PubVehcbrand> getBrands(String id){
		return dao.getBrand(id);
	}
}

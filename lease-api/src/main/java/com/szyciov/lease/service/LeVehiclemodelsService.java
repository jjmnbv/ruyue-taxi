package com.szyciov.lease.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.LeVehiclemodelsDao;
import com.szyciov.lease.entity.LeVehiclemodels;
import com.szyciov.lease.entity.PubVehcbrand;
import com.szyciov.lease.entity.PubVehcline;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.param.QueryParam;
import com.szyciov.util.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("LeVehiclemodelsService")
public class LeVehiclemodelsService {
	private LeVehiclemodelsDao dao;
	@Resource(name = "LeVehiclemodelsDao")
	public void setDao(LeVehiclemodelsDao dao) {
		this.dao = dao;
	}
	public Map<String, String> createLeVehiclemodels(LeVehiclemodels leVehiclemodels) {
		Map<String, String> ret = new HashMap<String, String>();
		int count = checkLeVehiclemodels(leVehiclemodels);
		if(count > 0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "车型级别已存在");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "创建成功");
			dao.createLeVehiclemodels(leVehiclemodels);
		}
		return ret;
	}
	public Map<String, String> updateLeVehiclemodels(LeVehiclemodels leVehiclemodels) {
		Map<String, String> ret = new HashMap<String, String>();
		int count = checkLeVehiclemodels(leVehiclemodels);
		if(count > 0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "车型级别已存在");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "修改成功");
			dao.updateLeVehiclemodels(leVehiclemodels);
		}
		return ret;
	}
	public Map<String, String> deleteLeVehiclemodels(String id){
		Map<String, String> ret = new HashMap<String, String>();
		int count = dao.checkDelete(id);
		if(count>0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "当前车型已绑定品牌车系，请先在【分配车系】解绑");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "删除成功");
			dao.deleteLeVehiclemodels(id);
		}
		return ret;
	};
	
	public PageBean getLeVehiclemodelsByQuery(QueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<LeVehiclemodels> list = getLeVehiclemodelsListByQuery(queryParam);
		for(LeVehiclemodels l:list){
			if(getBrandCars(l.getId()) != null){
				l.setBrandCars(getBrandCars(l.getId()).getBrandCars());
			}else{
				l.setBrandCars("");
			}
		}
		int iTotalRecords = getLeVehiclemodelsListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<LeVehiclemodels> getLeVehiclemodelsListByQuery(QueryParam queryParam){
		return dao.getLeVehiclemodelsListByQuery(queryParam);
	};
	
	public int getLeVehiclemodelsListCountByQuery(QueryParam queryParam){
		return dao.getLeVehiclemodelsListCountByQuery(queryParam);
	};
	
	public LeVehiclemodels getBrandCars(String id){
		return dao.getBrandCars(id);
	};
	
	public int checkLeVehiclemodels(LeVehiclemodels leVehiclemodels){
		return dao.checkLeVehiclemodels(leVehiclemodels);
	};
	
	public LeVehiclemodels getById(String id){
		return dao.getById(id);
	};
	
	public List<Map<String, Object>> getPubVehcbrand(String leasesCompanyId){
		return dao.getPubVehcbrand(leasesCompanyId);
	};
	
	public LeVehiclemodels getVehicleModelsName(String id){
		return dao.getVehicleModelsName(id);
	};
	
	public JSONObject getPubVehcline(String leasesCompanyId){
		JSONObject resultJson = new JSONObject();
		
		List<Map<String, Object>> list =  getPubVehcbrand(leasesCompanyId);
		Iterator<Map<String, Object>> ite = list.iterator();
		
		while(ite.hasNext()) {
			Map<String, Object> map = ite.next();
			String initials = (String) map.get("initials");//品牌首字母
			String brandName = (String) map.get("brandName");//品牌名字
			String vechileId = (String) map.get("Id");//车系id
			String vechileName = (String) map.get("name");//车系名字
			
			PubVehicle pubVehicle = new PubVehicle();
			pubVehicle.setVehclineId(vechileId);
			pubVehicle.setLeasesCompanyId(leasesCompanyId);
			
			if(resultJson.containsKey(initials)) {
				JSONObject brandJson = new JSONObject();
				brandJson.put("brandName", brandName);
				brandJson.put("vechileName", vechileName);
				brandJson.put("vechileId", vechileId);
				if(getVehicleModelsName(vechileId) != null){
					brandJson.put("type", getVehicleModelsName(vechileId).getName());
				}else{
					brandJson.put("type", "");
				}
				if(getVehclineBindstate(pubVehicle) > 0){
					brandJson.put("count","1");
				}else{
					brandJson.put("count","0");
				}
				resultJson.getJSONArray(initials).add(brandJson);
			} else {
				JSONArray brandJsonArr = new JSONArray();
				JSONObject brandJson = new JSONObject();
				brandJson.put("brandName", brandName);
				brandJson.put("vechileName", vechileName);
				brandJson.put("vechileId", vechileId);
				if(getVehicleModelsName(vechileId) != null){
					brandJson.put("type", getVehicleModelsName(vechileId).getName());
				}else{
					brandJson.put("type", "");
				}
				if(getVehclineBindstate(pubVehicle) > 0){
					brandJson.put("count","1");
				}else{
					brandJson.put("count","0");
				}
				brandJsonArr.add(brandJson);
				resultJson.put(initials, brandJsonArr);
			}
			
		}
		
		
		return resultJson;
	};
	
	public int getVehclineBindstate(PubVehicle pubVehicle){
		return dao.getVehclineBindstate(pubVehicle);
	};
	
	public int checkDisable(String id){
		return dao.checkDisable(id);
	};
	
	public Map<String,String> updateEnableOrDisable(LeVehiclemodels leVehiclemodels){
		Map<String, String> ret = new HashMap<String, String>();
		//0 qi 1 jin
		if(leVehiclemodels.getModelstatus().equals("0")){
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "启用成功");
		}else if(leVehiclemodels.getModelstatus().equals("1")){
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "禁用成功");
		}
		dao.updateEnableOrDisable(leVehiclemodels);
		return ret;
	};
	
	public int checkAllocationVehcline(String id){
		return dao.checkAllocationVehcline(id);
	};
	public LeVehiclemodels getBrandcar(String id){
		return dao.getBrandcar(id);
	};
}

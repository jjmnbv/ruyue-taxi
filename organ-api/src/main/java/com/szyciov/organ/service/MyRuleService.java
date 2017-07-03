package com.szyciov.organ.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.organ.dao.MyRuleDao;

@Service("MyRuleService")
public class MyRuleService {
	private MyRuleDao dao;

	@Resource(name = "MyRuleDao")
	public void setDao(MyRuleDao dao) {
		this.dao = dao;
	}
	public Map getOrgUsecarrules(Map<String, String> map1){
		List<Map<String, Object>> list = dao.getOrgUsecarrules(map1);
		Map<String, Map<String,List<Map<String,Object>>>> map = new HashMap<>();
		Map<String, Map<String,Object>> leasescompanyidtemp = new HashMap<String, Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Map<String, Object> obj = list.get(i);
			String name = (String)obj.get("name");
			Map<String,List<Map<String,Object>>> nameinfo = map.get(name);
			if(nameinfo==null){
				nameinfo = new HashMap<String,List<Map<String,Object>>>();
				map.put(name, nameinfo);
			}
			String usetype = (String)obj.get("usetype");
			List<Map<String,Object>> usetypeinfo = nameinfo.get(usetype);
			if(usetypeinfo == null){
				usetypeinfo = new ArrayList<Map<String,Object>>();
				nameinfo.put(usetype, usetypeinfo);
			}
			String leasescompanyid = (String)obj.get("leasescompanyid");
			Map<String,Object> leasescompanyidinfo = leasescompanyidtemp.get(name+usetype+leasescompanyid);
			if(leasescompanyidinfo == null){
				leasescompanyidinfo = new HashMap<String,Object>();
				usetypeinfo.add(leasescompanyidinfo);
				leasescompanyidtemp.put(name+usetype+leasescompanyid, leasescompanyidinfo);
			}
			leasescompanyidinfo.put("leasescompanyid", obj.get("leasescompanyid"));
			leasescompanyidinfo.put("leasesCompanyName", obj.get("leasesCompanyName"));
			
			List<Map<String,Object>>  vehiclemodels = (List<Map<String, Object>>) leasescompanyidinfo.get("vehiclemodels");
			if(vehiclemodels==null){
				vehiclemodels = new ArrayList<Map<String,Object>>();
				leasescompanyidinfo.put("vehiclemodels", vehiclemodels);
			}
			Map<String,Object> vehiclemodelinfo = new HashMap<String,Object>();
			vehiclemodels.add(vehiclemodelinfo);
			vehiclemodelinfo.put("vehiclemodelid", obj.get("vehiclemodels"));
			vehiclemodelinfo.put("vehicleModelsName", obj.get("vehicleModelsName"));
		}
		
		return map;
	};
}

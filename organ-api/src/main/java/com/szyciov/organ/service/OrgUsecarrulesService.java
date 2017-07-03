package com.szyciov.organ.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.entity.LeAccountRules;
import com.szyciov.org.entity.OrgUsecarrules;
import com.szyciov.organ.dao.OrgUsecarrulesDao;
import com.szyciov.util.GUIDGenerator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("OrgUsecarrulesService")
public class OrgUsecarrulesService {
	private OrgUsecarrulesDao dao;

	@Resource(name = "OrgUsecarrulesDao")
	public void setDao(OrgUsecarrulesDao dao) {
		this.dao = dao;
	}
//	public List<Map<String, Object>> getLeLeasescompany(String id){
//		LeLeasescompany l = new LeLeasescompany();
//		l.setId(id);
//		return dao.getLeLeasescompany(l);
//	};
	
//	public List<Map<String, Object>> getLeLeasescompany(OrgOrganCompanyRef o){
//		o.setId("");
//		return dao.getLeLeasescompany(o);
//	};
//	
//	public List<Map<String, Object>> getLeVehiclemodels(OrgOrganCompanyRef o){
//		return dao.getLeVehiclemodels(o);
//	};
	
	public Map getOrgUsecarrules(OrgUsecarrules orgUsecarrules){
		List<Map<String, Object>> list = dao.getOrgUsecarrules(orgUsecarrules);
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
//			vehiclemodelinfo.put("count", obj.get("count"));
		}
		return map;
	};
	
	public List<OrgUsecarrules> getByName(OrgUsecarrules orgUsecarrules){
		return dao.getByName(orgUsecarrules);
	};
	
//	public void deleteOrgUserRulesRef(String id){
//		dao.deleteOrgUserRulesRef(id);
//	};
	
	public void deleteOrgUsecarrules(String id){
		dao.deleteOrgUsecarrules(id);
	};
	public Map<String, String> delete(OrgUsecarrules orgUsecarrules){
		Map<String, String> map = new HashMap<String, String>();
		List<OrgUsecarrules> org = getByName(orgUsecarrules);
		for(OrgUsecarrules o : org){
			//deleteOrgUserRulesRef(o.getId());
			deleteOrgUsecarrules(o.getId());
		}
		map.put("ResultSign", "Successful");
		map.put("MessageKey", "规则已删除");
		return map;
	}
	
	public Map<String, String> add(Map map1,String ruleName,String organId,String add){
		Map<String, String> map = new HashMap<String, String>();
		boolean flag = false;
		if(map1.size() > 0){
			OrgUsecarrules oo = new OrgUsecarrules();
			oo.setName(ruleName);
			oo.setOrganId(organId);
			if(checkRulesname(oo)>0){
				map.put("ResultSign", "Error");
				map.put("MessageKey", "用车规则已存在");
			}else{
				JSONObject json = JSONObject.fromObject(map1);
				Iterator<String> ite = json.keys();
				while(ite.hasNext()) {
					String key = (String)ite.next();
					if(key == "ruleName"){
						map.put("ResultSign", "Error");
						map.put("MessageKey", "用车方式不能为空");
						continue;
					}
	//				System.out.println(key);
					JSONArray arr = json.getJSONArray(key);
					for(int i = 0; i < arr.size(); i++) {
						JSONObject info = arr.getJSONObject(i);
	//					System.out.println(info.get("leasesCompanyId"));
	//					System.out.println(info.get("modelId"));
						String vel = (String)info.get("modelId");
						if(vel!=null){
							String[] vel1 = vel.split(",");
							for(int j = 0;j < vel1.length;j++){
								OrgUsecarrules orgUsecarrules = new OrgUsecarrules();
								orgUsecarrules.setId(GUIDGenerator.newGUID());
								orgUsecarrules.setName(ruleName);
								orgUsecarrules.setUserType(key);
								orgUsecarrules.setLeasesCompanyId((String)info.get("leasesCompanyId"));
								orgUsecarrules.setVehicleModels(vel1[j]);
								orgUsecarrules.setOrganId(organId);
								
								add(orgUsecarrules);
								
		//						OrgUserRulesRef orgUserRulesRef = new OrgUserRulesRef();
		//						orgUserRulesRef.setId(GUIDGenerator.newGUID());
		//						orgUserRulesRef.setUserId(userId);
		//						orgUserRulesRef.setUseRulesId(orgUsecarrules.getId());
		//						addourr(orgUserRulesRef);
								flag = true;
							}
						}else{
							map.put("ResultSign", "Error");
							map.put("MessageKey", "请选择用车方式的车型");
						}
					}
				}
			}
		}else{
			map.put("ResultSign", "Error");
			map.put("MessageKey", "用车方式不能为空");
		}
		if(flag){
			map.put("ResultSign", "Successful");
			map.put("MessageKey", "规则已添加");
		}
		return map;
	}
	
	public Map<String, String> update(Map map1,String ruleName,String organId,String update,String ruleYName){
		boolean flag = false;
		Map<String, String> map = new HashMap<String, String>();
		if(map1.size() > 0){
			OrgUsecarrules o = new OrgUsecarrules();
			o.setName(ruleYName);
			List<OrgUsecarrules> org = getByName(o);
			String[] queryId = new String[org.size()];
			for(int i=0;i<org.size();i++){
				//deleteOrgUserRulesRef(o.getId());
//				deleteOrgUsecarrules(oo.getId());
				queryId[i] = org.get(i).getId();
			}
			o.setQueryId(queryId);
			OrgUsecarrules oo = new OrgUsecarrules();
			oo.setName(ruleName);
			oo.setQueryId(queryId);
			oo.setOrganId(organId);
			if(checkRulesname(oo)>0){
				map.put("ResultSign", "Error");
				map.put("MessageKey", "用车规则已存在");
			}else{
				OrgUsecarrules ooo = new OrgUsecarrules();
				ooo.setName(ruleYName);
				List<OrgUsecarrules> oooo = getByName(ooo);
				for(OrgUsecarrules ou : oooo){
					//deleteOrgUserRulesRef(o.getId());
					deleteOrgUsecarrules(ou.getId());
				}
				JSONObject json = JSONObject.fromObject(map1);
				Iterator<String> ite = json.keys();
				while(ite.hasNext()) {
					String key = (String)ite.next();
					if(key == "ruleName" || key == "ruleYName"){
						continue;
					}
	//				System.out.println(key);
					JSONArray arr = json.getJSONArray(key);
					for(int i = 0; i < arr.size(); i++) {
						JSONObject info = arr.getJSONObject(i);
	//					System.out.println(info.get("leasesCompanyId"));
	//					System.out.println(info.get("modelId"));
						String vel = (String)info.get("modelId");
						if(vel!=null){
							String[] vel1 = vel.split(",");
							for(int j = 0;j < vel1.length;j++){
								OrgUsecarrules orgUsecarrules = new OrgUsecarrules();
								orgUsecarrules.setId(GUIDGenerator.newGUID());
								orgUsecarrules.setName(ruleName);
								orgUsecarrules.setUserType(key);
								orgUsecarrules.setLeasesCompanyId((String)info.get("leasesCompanyId"));
								orgUsecarrules.setVehicleModels(vel1[j]);
								orgUsecarrules.setOrganId(organId);
								
								add(orgUsecarrules);
								
		//						OrgUserRulesRef orgUserRulesRef = new OrgUserRulesRef();
		//						orgUserRulesRef.setId(GUIDGenerator.newGUID());
		//						orgUserRulesRef.setUserId(userId);
		//						orgUserRulesRef.setUseRulesId(orgUsecarrules.getId());
		//						addourr(orgUserRulesRef);
								flag = true;
							}
						}else{
							map.put("ResultSign", "Error");
							map.put("MessageKey", "请选择用车方式的车型");
						}
					}
				}
			}
		}else{
			map.put("ResultSign", "Error");
			map.put("MessageKey", "用车方式不能为空");
		}
		if(flag){
			map.put("ResultSign", "Successful");
			map.put("MessageKey", "规则已修改");
		}
		return map;
		
//		Map<String, String> map = new HashMap<String, String>();
//		if(map1.size() > 0){
//			OrgUsecarrules o = new OrgUsecarrules();
//			o.setName(ruleYName);
//			List<OrgUsecarrules> org = getByName(o);
//			String[] queryId = new String[org.size()];
//			for(int i=0;i<org.size();i++){
//				//deleteOrgUserRulesRef(o.getId());
////				deleteOrgUsecarrules(oo.getId());
//				queryId[i] = org.get(i).getId();
//			}
//			o.setQueryId(queryId);
//			OrgUsecarrules oo = new OrgUsecarrules();
//			oo.setName(ruleName);
//			oo.setQueryId(queryId);
//			oo.setOrganId(organId);
//			if(checkRulesname(oo)>0){
//				map.put("ResultSign", "Error");
//				map.put("MessageKey", "用车规则已存在");
//			}else{
//				JSONObject json = JSONObject.fromObject(map1);
//				Iterator<String> ite = json.keys();
//				while(ite.hasNext()) {
//					String key = (String)ite.next();
//					JSONArray arr = json.getJSONArray(key);
//					for(int i = 0; i < arr.size(); i++) {
//						JSONObject info = arr.getJSONObject(i);
//						String vel = (String)info.get("modelId");
//						if(vel != null){
//							String[] vel1 = vel.split(",");
//							for(int j = 0;j < vel1.length;j++){
//								for(int k=0;k<org.size();k++){
//									if(org.get(k).getVehicleModels().equals(vel1[j])){
//										org.remove(k);
//										break;
//									}
//								}
//								OrgUsecarrules ou = new OrgUsecarrules();
//								ou.setName(ruleYName);
//								ou.setVehicleModels(vel1[j]);
//								List<OrgUsecarrules> ou1 = getByName(ou);
//								if(ou1.size()>0){
//									for(int k=0;k<ou1.size();k++){
//										OrgUsecarrules orgUsecarrules = new OrgUsecarrules();
//										orgUsecarrules.setId(ou1.get(k).getId());
//										orgUsecarrules.setName(ruleName);
//										orgUsecarrules.setUserType(key);
//										orgUsecarrules.setLeasesCompanyId((String)info.get("leasesCompanyId"));
//										orgUsecarrules.setVehicleModels(vel1[j]);
//										orgUsecarrules.setOrganId(organId);
//										update(orgUsecarrules);
//									}
//								}else{
////									deleteOrgUsecarrules(ou1.get(0).getId());
//									OrgUsecarrules orgUsecarrules = new OrgUsecarrules();
//									orgUsecarrules.setId(GUIDGenerator.newGUID());
//									orgUsecarrules.setName(ruleName);
//									orgUsecarrules.setUserType(key);
//									orgUsecarrules.setLeasesCompanyId((String)info.get("leasesCompanyId"));
//									orgUsecarrules.setVehicleModels(vel1[j]);
//									orgUsecarrules.setOrganId(organId);
//									
//									add(orgUsecarrules);
//								}
//							}
//							map.put("ResultSign", "Successful");
//							map.put("MessageKey", "规则已修改");
//						}else{
//							map.put("ResultSign", "Error");
//							map.put("MessageKey", "用车方式不能为空");
//						}
//					}
//				}
//				if(map.size()>0){
//					
//				}else{
//					for(int k = 0;k<org.size();k++){
////					    System.out.println(org.get(i).getId());
//						deleteOrgUsecarrules(org.get(k).getId());
//					}
//				}
//			}
//		}else{
//			map.put("ResultSign", "Error");
//			map.put("MessageKey", "用车方式不能为空");
//		}
//		return map;
	}
	
	public void add(OrgUsecarrules orgUsecarrules){
		dao.add(orgUsecarrules);
	};
	
//	public void addourr(OrgUserRulesRef orgUserRulesRef){
//		dao.addourr(orgUserRulesRef);
//	};
	
	public List<LeAccountRules> getRulestype(String organid){
		return dao.getRulestype(organid);
	};
	
	public int checkRulesname(OrgUsecarrules orgUsecarrules){
		return dao.checkRulesname(orgUsecarrules);
	};
	
	public Map getAllRules(String organid){
		List<Map<String, Object>> list = dao.getAllRules(organid);
		Map<String, Map<String,List<Map<String,Object>>>> map = new HashMap<>();
		Map<String, Map<String,Object>> leasescompanyidtemp = new HashMap<String, Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Map<String, Object> obj = list.get(i);
			String name = "key";
			Map<String,List<Map<String,Object>>> nameinfo = map.get(name);
			if(nameinfo==null){
				nameinfo = new HashMap<String,List<Map<String,Object>>>();
				map.put(name, nameinfo);
			}
			String usetype = (String)obj.get("rulestype");
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
			
			List<Map<String,Object>>  vehiclemodels = (List<Map<String, Object>>) leasescompanyidinfo.get("cartype");
			if(vehiclemodels==null){
				vehiclemodels = new ArrayList<Map<String,Object>>();
				leasescompanyidinfo.put("cartype", vehiclemodels);
			}
			Map<String,Object> vehiclemodelinfo = new HashMap<String,Object>();
			vehiclemodels.add(vehiclemodelinfo);
			vehiclemodelinfo.put("vehiclemodelid", obj.get("cartype"));
			vehiclemodelinfo.put("vehicleModelsName", obj.get("vehicleModelsName"));
		}
		List<Map<String,List<Map<String,Object>>>> map1 = new ArrayList<>();
		for (String key : map.keySet()) {
			System.out.println("key= "+ key + " and value= " + map.get(key));
			map1.add(map.get(key));
		}
		Map<String,List<Map<String,Object>>> map2 = new HashMap<>();
		for(int i = 0;i<map1.size();i++){
			map2.putAll(map1.get(i));
		}
		return map2;
	};
	
	public void update(OrgUsecarrules orgUsecarrules){
		dao.update(orgUsecarrules);
	};
	
	public int checkRulesUpdate(String name){
		return dao.checkRulesUpdate(name);
	};
}

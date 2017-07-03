package com.szyciov.lease.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.lease.dao.LeVehclineModelsRefDao;
import com.szyciov.lease.dao.LeVehiclemodelsDao;
import com.szyciov.lease.entity.LeVehclineModelsRef;
import com.szyciov.lease.entity.PubVehcline;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.op.entity.OpVehclineModelsRef;
import com.szyciov.util.GUIDGenerator;

@Service("LeVehclineModelsRefService")
public class LeVehclineModelsRefService {
	private LeVehclineModelsRefDao dao;
	@Resource(name = "LeVehclineModelsRefDao")
	public void setDao(LeVehclineModelsRefDao dao) {
		this.dao = dao;
	}
	private LeVehiclemodelsDao dao1;
	@Resource(name = "LeVehiclemodelsDao")
	public void setDao(LeVehiclemodelsDao dao1) {
		this.dao1 = dao1;
	}
	public Map<String, String> createLeVehclineModelsRef(Map map) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		if(map.size()>0){
			if(map.get("id")!=null && map.get("jsonData")!=null){
				String modelid = (String) map.get("id");
				List<String> oldvehclinesid = genVehiclineId(modelid);
				List<String> newvehclinesid = (List<String>) ((Map)map.get("jsonData")).get("vechileId");
				if(oldvehclinesid==null||oldvehclinesid.size()<=0){
					//之前没有车系
					deleteLeVehclineModelsRefAll(modelid);
					for(int i=0;i<newvehclinesid.size();i++){
						LeVehclineModelsRef leVehclineModelsRef = new LeVehclineModelsRef();
						leVehclineModelsRef.setId(GUIDGenerator.newGUID());
						leVehclineModelsRef.setVehicleModelsId((String)map.get("id"));;
						leVehclineModelsRef.setVehclineid(newvehclinesid.get(i));
						leVehclineModelsRef.setCreater((String)map.get("creater"));
						leVehclineModelsRef.setUpdater((String)map.get("updater"));
						dao.createLeVehclineModelsRef(leVehclineModelsRef);
					}
				}else{
					//之前有车系,需要判断是否有绑定司机的车系被取消了
					List<String> tempvehclinesid = new ArrayList<String>(); 
					for(int i=0;i<oldvehclinesid.size();i++){
						String oldvehclineid = oldvehclinesid.get(i);
						if(!newvehclinesid.contains(oldvehclineid)){
							//之前就有现在取消的
							tempvehclinesid.add(oldvehclineid);
						}
					}
					if(tempvehclinesid.size()<=0){
						//之前的没有取消
						deleteLeVehclineModelsRefAll(modelid);
						if(newvehclinesid != null && newvehclinesid.size() > 0){
							for(int i=0;i<newvehclinesid.size();i++){
								LeVehclineModelsRef leVehclineModelsRef = new LeVehclineModelsRef();
								leVehclineModelsRef.setId(GUIDGenerator.newGUID());
								leVehclineModelsRef.setVehicleModelsId((String)map.get("id"));;
								leVehclineModelsRef.setVehclineid(newvehclinesid.get(i));
								leVehclineModelsRef.setCreater((String)map.get("creater"));
								leVehclineModelsRef.setUpdater((String)map.get("updater"));
								dao.createLeVehclineModelsRef(leVehclineModelsRef);
							}
						}
	 				}else{
						//之前取消
						List<PubVehcline> bindvehclines = getBindVehclines(tempvehclinesid);
						if(bindvehclines==null||bindvehclines.size()<=0){
							deleteLeVehclineModelsRefAll(modelid);
							if(newvehclinesid != null && newvehclinesid.size() > 0){
								for(int i=0;i<newvehclinesid.size();i++){
									LeVehclineModelsRef leVehclineModelsRef = new LeVehclineModelsRef();
									leVehclineModelsRef.setId(GUIDGenerator.newGUID());
									leVehclineModelsRef.setVehicleModelsId((String)map.get("id"));;
									leVehclineModelsRef.setVehclineid(newvehclinesid.get(i));
									leVehclineModelsRef.setCreater((String)map.get("creater"));
									leVehclineModelsRef.setUpdater((String)map.get("updater"));
									dao.createLeVehclineModelsRef(leVehclineModelsRef);
								}
							}
						}else{
							ret.put("ResultSign", "Error");
							StringBuffer message = new StringBuffer("【");
							for(int i=0;i<bindvehclines.size();i++){
								PubVehcline pubvehcline = bindvehclines.get(i);
								message.append(pubvehcline.getVehcBrandName()+" "+pubvehcline.getName());
							}
							if(message.length()>1){
								message.append("】");
								message.append("品牌车系的车辆已绑定司机，解绑后方可取消");
							}
							ret.put("MessageKey", message.toString());
						}
					}
				}
//				List map1 = (List) ((Map)map.get("jsonData")).get("vechileId");
//				if(map1.size() > 0){
//					List<LeVehclineModelsRef> list = genVehiclineId((String)map.get("id"));
//					if(list.size()>0){
//						for(int i=0;i<map1.size();i++){
//							if(){
//								
//							}
//						}
//						for(int i=0;i<list.size();i++){
//							if(dao1.checkAllocationVehcline(list.get(i).getVehclineid())>0){
//								
//							}else{
//								
//							}
//							LeVehclineModelsRef leVehclineModelsRef = new LeVehclineModelsRef();
//							leVehclineModelsRef.setId(GUIDGenerator.newGUID());
//							leVehclineModelsRef.setVehicleModelsId((String)map.get("id"));;
//							leVehclineModelsRef.setVehclineid((String)map1.get(i));
//							leVehclineModelsRef.setCreater((String)map.get("creater"));
//							leVehclineModelsRef.setUpdater((String)map.get("updater"));
//							deleteLeVehclineModelsRef(leVehclineModelsRef);
//							dao.createLeVehclineModelsRef(leVehclineModelsRef);
//							
//							ret.put("ResultSign", "Successful");
//							ret.put("MessageKey", "分配成功");
//						}
//					}else{
//						deleteLeVehclineModelsRefAll((String)map.get("id"));
//						for(int i=0;i<map1.size();i++){
//							LeVehclineModelsRef leVehclineModelsRef = new LeVehclineModelsRef();
//							leVehclineModelsRef.setId(GUIDGenerator.newGUID());
//							leVehclineModelsRef.setVehicleModelsId((String)map.get("id"));;
//							leVehclineModelsRef.setVehclineid((String)map1.get(i));
//							leVehclineModelsRef.setCreater((String)map.get("creater"));
//							leVehclineModelsRef.setUpdater((String)map.get("updater"));
//							deleteLeVehclineModelsRef(leVehclineModelsRef);
//							dao.createLeVehclineModelsRef(leVehclineModelsRef);
//						}
//						ret.put("ResultSign", "Successful");
//						ret.put("MessageKey", "分配成功");
//					}
//				}else{
//					List<LeVehclineModelsRef> list = genVehiclineId((String)map.get("id"));
//					boolean flag1 = true;
//					List<String> list1 = new ArrayList<>();
//					for(int i=0;i<list.size();i++){
//						if(dao1.checkAllocationVehcline(list.get(i).getVehclineid())>0){
//							list1.add(list.get(i).getVehclineid());
//							flag1 = false;
//						}
//					}
//					if(flag1){
//						deleteLeVehclineModelsRefAll((String)map.get("id"));
//						ret.put("ResultSign", "Successful");
//						ret.put("MessageKey", "分配成功");
//					}else{
//						StringBuffer str = new StringBuffer();
//						for(int i=0;i<list1.size();i++){
//							str.append(dao1.getBrandcar(list1.get(i)).getBrandCars()+"、");
//						}
//						ret.put("ResultSign", "Error");
//						ret.put("MessageKey", "【"+str.toString().substring(0,str.toString().length()-1)+"】品牌车系的车辆已绑定司机，解绑后方可取消");
//					}
//				}
			}else{
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "没有分配车系");
			}
		}else{
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "没有分配车系");
		}
		
		return ret;
	}
	public void deleteLeVehclineModelsRef(LeVehclineModelsRef leVehclineModelsRef) {
		dao.deleteLeVehclineModelsRef(leVehclineModelsRef);
	}
	
	public int checkVelMod(PubVehicle pubVehicle){
		return dao.checkVelMod(pubVehicle);
	};
	
	public List<String> genVehiclineId(String id){
		return dao.genVehiclineId(id);
	};
	
	public void deleteLeVehclineModelsRefAll(String id){
		dao.deleteLeVehclineModelsRefAll(id);
	};
	
	public List<PubVehcline> getBindVehclines(List<String> tempvehclinesid) {
		return dao.getBindVehclines(tempvehclinesid);
	}
}

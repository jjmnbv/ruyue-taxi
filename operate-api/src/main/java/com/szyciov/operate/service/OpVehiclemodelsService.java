package com.szyciov.operate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.szyciov.dto.pubVehicleModelsRef.UpdateVehicleModelsRefByVehclineDto;
import com.szyciov.op.entity.OpVehclineModelsRef;
import com.szyciov.op.entity.OpVehiclemodels;
import com.szyciov.op.entity.OpVehiclemodelsVehicleRef;
import com.szyciov.op.entity.PubVehcline;
import com.szyciov.operate.dao.OpAccountrulesDao;
import com.szyciov.operate.dao.OpVehiclemodelsDao;
import com.szyciov.param.QueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import com.szyciov.util.TemplateHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("OpVehiclemodelsService")
public class OpVehiclemodelsService {
	private TemplateHelper templateHelper = new TemplateHelper();
	private OpVehiclemodelsDao dao;
	@Resource(name = "OpVehiclemodelsDao")
	public void setDao(OpVehiclemodelsDao dao) {
		this.dao = dao;
	}
	
	private OpAccountrulesDao accountrulesDao;
	@Resource(name = "OpAccountrulesDao")
	public void setAccountrulesDao(OpAccountrulesDao accountrulesDao) {
		this.accountrulesDao = accountrulesDao;
	}
	
	public PageBean getOpVehiclemodelsByQuery(QueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OpVehiclemodels> vehiclemodelsList = getOpVehiclemodelsListByQuery(queryParam);
		int iTotalRecords = getOpVehiclemodelsCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(vehiclemodelsList);
		return pageBean;
	}
	
	public JSONObject getPubVehcline() {
		JSONObject resultJson = new JSONObject();
		
		List<Map<String, Object>> list =  getPubVehcbrand();
		Iterator<Map<String, Object>> ite = list.iterator();
		while(ite.hasNext()) {
			Map<String, Object> map = ite.next();
			String initials = (String) map.get("initials");//品牌首字母
			String brandName = (String) map.get("brandname");//品牌名字
			String vechileId = (String) map.get("Id");//车系id
			String vechileName = (String) map.get("name");//车系名字
			
			JSONObject brandJson = new JSONObject();
			brandJson.put("brandName", brandName);
			brandJson.put("vechileName", vechileName);
			brandJson.put("vechileId", vechileId);
			OpVehiclemodels vehiclemodels = getVehicleModelsByLineId(vechileId);
			if(null != vehiclemodels){
				brandJson.put("type", vehiclemodels.getName());
			}else{
				brandJson.put("type", "");
			}
			//根据车品牌首字母分类
			if(resultJson.containsKey(initials)) {
				resultJson.getJSONArray(initials).add(brandJson);
			} else {
				JSONArray brandJsonArr = new JSONArray();
				brandJsonArr.add(brandJson);
				resultJson.put(initials, brandJsonArr);
			}
		}
		return resultJson;
	}
	
	public Map<String, String> createOpVehiclemodels(OpVehiclemodels opVehiclemodels) {
		Map<String, String> ret = new HashMap<String, String>();
		//判断车型名称是否唯一
		OpVehiclemodels vehiclemodels = new OpVehiclemodels();
		vehiclemodels.setName(opVehiclemodels.getName());
		vehiclemodels.setStatus(1);
		List<OpVehiclemodels> vehiclemodelsList = getOpVehiclemodelsByList(vehiclemodels);
		if(null != vehiclemodelsList && !vehiclemodelsList.isEmpty()) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "车型名称已存在");
			return ret;
		}
		//判断车型级别是否唯一
		vehiclemodels = new OpVehiclemodels();
		vehiclemodels.setLevel(opVehiclemodels.getLevel());
		vehiclemodels.setStatus(1);
		vehiclemodelsList = getOpVehiclemodelsByList(vehiclemodels);
		if(null != vehiclemodelsList && !vehiclemodelsList.isEmpty()) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "车型级别已存在");
			return ret;
		}
		//添加服务车型
		Date currentTime = new Date();
		opVehiclemodels.setId(GUIDGenerator.newGUID());
		opVehiclemodels.setCreatetime(currentTime);
		opVehiclemodels.setUpdatetime(currentTime);
		opVehiclemodels.setStatus(1);
		opVehiclemodels.setModelstatus("0");
		insertOpVehiclemodels(opVehiclemodels);
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "添加成功");
		return ret;
	}

	public Map<String, String> editOpVehiclemodels(OpVehiclemodels opVehiclemodels) {
		Map<String, String> ret = new HashMap<String, String>();
		//判断车型级别是否唯一
		OpVehiclemodels vehiclemodels = new OpVehiclemodels();
		vehiclemodels.setLevel(opVehiclemodels.getLevel());
		vehiclemodels.setStatus(1);
		List<OpVehiclemodels> vehiclemodelsList = getOpVehiclemodelsByList(vehiclemodels);
		if(null != vehiclemodelsList && !vehiclemodelsList.isEmpty())
		{
			vehiclemodels = vehiclemodelsList.get(0);
			if(!opVehiclemodels.getId().equals(vehiclemodels.getId())) {
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "车型级别已存在");
				return ret;
			}
		}
		//修改车型信息
		Date currentTime = new Date();
		opVehiclemodels.setUpdatetime(currentTime);
		updateOpVehiclemodels(opVehiclemodels);
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "修改成功");
		return ret;
	}
	
	public Map<String, String> deleteOpVehiclemodels(OpVehiclemodels object) {
		Map<String, String> ret = new HashMap<String, String>();
		
		//验证车型是否绑定车辆
		OpVehiclemodelsVehicleRef vehicleRef = new OpVehiclemodelsVehicleRef();
		vehicleRef.setVehiclemodelsid(object.getId());
		List<OpVehiclemodelsVehicleRef> vehicleRefs = getModelsVehicl6eRefByModel(vehicleRef);
		if(null != vehicleRefs && !vehicleRefs.isEmpty()) {
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "该服务车型已被分配给车辆，请取消分配后再删除");
			return ret;
		}
		
		//删除服务车型
		Date currentTime = new Date();
		object.setUpdatetime(currentTime);
		object.setStatus(2);
		updateOpVehiclemodels(object);
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "删除成功");
		return ret;
	}
	
	public List<OpVehiclemodels> getOpVehiclemodelsListByQuery(QueryParam queryParam) {
		return dao.getOpVehiclemodelsListByQuery(queryParam);
	}
	
	public int getOpVehiclemodelsCountByQuery(QueryParam queryParam) {
		return dao.getOpVehiclemodelsCountByQuery(queryParam);
	}
	
	public OpVehiclemodels getBrandCars(String id) {
		return dao.getBrandCars(id);
	}
	
	public List<Map<String, Object>> getPubVehcbrand(){
		return dao.getPubVehcbrand();
	}
	
	public OpVehiclemodels getVehicleModelsByLineId(String id){
		return dao.getVehicleModelsByLineId(id);
	}
	
	public List<OpVehiclemodels> getOpVehiclemodelsByList(OpVehiclemodels opVehiclemodels) {
		return dao.getOpVehiclemodelsByList(opVehiclemodels);
	}
	
	public void insertOpVehiclemodels(OpVehiclemodels opVehiclemodels) {
		dao.insertOpVehiclemodels(opVehiclemodels);
	}
	
	public void updateOpVehiclemodels(OpVehiclemodels opVehiclemodels) {
		dao.updateOpVehiclemodels(opVehiclemodels);
	}
	
	public OpVehiclemodels getOpVehiclemodelsById(String id) {
		return dao.getOpVehiclemodelsById(id);
	}
	
	public List<OpVehiclemodelsVehicleRef> getModelsVehicl6eRefByModel(OpVehiclemodelsVehicleRef object) {
		return dao.getModelsVehicl6eRefByModel(object);
	}

	public Map<String, Object> saveLeVehclineModelsRef(Map<String, Object> params) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "分配成功");
		if(params.size()>0){
			String operid = (String)params.get("updater");
			String modelid = (String) params.get("id");
			List<String> oldvehclinesid = dao.getVehclinesIdByModelId(modelid);
			String leaseCompanyId = (String) params.get("leaseCompanyId");
			List<String> newvehclinesid = (List<String>) ((Map)params.get("jsonData")).get("vechileId");
			if(oldvehclinesid==null||oldvehclinesid.size()<=0){
				//之前没有车系
				changeVehclines(modelid,newvehclinesid,operid,leaseCompanyId);
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
					changeVehclines(modelid,newvehclinesid,operid,leaseCompanyId);
				}else{
					List<PubVehcline> bindvehclines = dao.getBindVehclines(tempvehclinesid);
					if(bindvehclines==null||bindvehclines.size()<=0){
						changeVehclines(modelid,newvehclinesid,operid,leaseCompanyId);
					}else{
						ret.put("ResultSign", "Error");
						StringBuffer message = new StringBuffer("【");
						for(int i=0;i<bindvehclines.size();i++){
							PubVehcline pubvehcline = bindvehclines.get(i);
							message.append(pubvehcline.getVehcBrandName()+"-"+pubvehcline.getName());
						}
						if(message.length()>1){
							message.append("】");
							message.append("品牌车系的车辆已绑定司机，解绑后方可取消");
						}
						ret.put("MessageKey", message.toString());
					}
				}
			}
		}else{
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "传值错误");
		}
		return ret;
	}
	
	/**
	 * 更新车型车系关系
	 * @param modelid
	 * @param newvehclinesid
	 * @param operuserid
	 */
	public void changeVehclines(String modelid,List<String> newvehclinesid,String operuserid,String leaseCompanyId){
		deleteLeVehclineModelsRef(modelid);
		UpdateVehicleModelsRefByVehclineDto uvmrbvd = new UpdateVehicleModelsRefByVehclineDto();
		if(newvehclinesid!=null&&newvehclinesid.size()>0){
			List<String> vehclineId = new ArrayList<String>();
			for(int i=0;i<newvehclinesid.size();i++){
				OpVehclineModelsRef opVehclineModelsRef = new OpVehclineModelsRef();
				opVehclineModelsRef.setId(GUIDGenerator.newGUID());
				opVehclineModelsRef.setVehiclemodelsid(modelid);
				opVehclineModelsRef.setVehclineid(newvehclinesid.get(i));
				opVehclineModelsRef.setCreater(operuserid);
				opVehclineModelsRef.setUpdater(operuserid);
				dao.createLeVehclineModelsRef(opVehclineModelsRef);
				vehclineId.add(newvehclinesid.get(i));
			}
			int platform = 0;
			String vehiclemodels = modelid;
			String updater = operuserid;
			uvmrbvd.setLeaseCompanyId(leaseCompanyId);
			uvmrbvd.setPlatform(platform);
			uvmrbvd.setUpdater(updater);
			uvmrbvd.setVehclineId(vehclineId);
			uvmrbvd.setVehiclemodels(vehiclemodels);
			templateHelper.dealRequestWithTokenCarserviceApiUrl("/PubVehicleModelsRef/updateVehicleModelsRefByVehcline", HttpMethod.POST, null, uvmrbvd,
					JSONObject.class);
		}
	}
	
	public void changeVehclines4batch(String modelid,List<String> newvehclinesid,String operuserid){
		deleteLeVehclineModelsRef(modelid);
		if(newvehclinesid!=null&&newvehclinesid.size()>0){
			List<OpVehclineModelsRef> OpVehclineModelsRefs = new ArrayList<OpVehclineModelsRef>();
			for(int i=0;i<newvehclinesid.size();i++){
				OpVehclineModelsRef opVehclineModelsRef = new OpVehclineModelsRef();
				opVehclineModelsRef.setId(GUIDGenerator.newGUID());
				opVehclineModelsRef.setVehiclemodelsid(modelid);
				opVehclineModelsRef.setVehclineid(newvehclinesid.get(i));
				opVehclineModelsRef.setCreater(operuserid);
				opVehclineModelsRef.setUpdater(operuserid);
				OpVehclineModelsRefs.add(opVehclineModelsRef);
			}
			dao.createLeVehclineModelsRef4Batch(OpVehclineModelsRefs);
		}
	}
	
	public void deleteLeVehclineModelsRef(String id) {
		dao.deleteLeVehclineModelsRef(id);
	}

	public Map<String, Object> changeState(Map<String, Object> params) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("ResultSign", "Successful");
		String state = (String) params.get("state");
		if("0".equalsIgnoreCase(state)){
			//启用
			dao.changeState(params);
			ret.put("MessageKey", "启用成功");
		}else if("1".equalsIgnoreCase(state)){
			ret.put("MessageKey", "禁用成功");
			//禁用，需要判断是否可以禁用
			String modelid = (String) params.get("id");
			List<String> oldvehclinesid = dao.getVehclinesIdByModelId(modelid);
			if(oldvehclinesid!=null&&oldvehclinesid.size()>0){
				//有车系不能禁用
				ret.put("ResultSign", "Warning");
				ret.put("MessageKey", "当前服务车型已分配品牌车系，请取消分配后再试");
			}else{
				dao.changeState(params);
				dao.deleteVehicleByVehiclemodel(params);
			}
		}else{
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "传值错误");
		}
		return ret;
	}
	
	public Map<String,Object> hasBindLeaseCars(Map<String,Object> params){
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("ResultSign", "Successful");
		boolean hasbindleasecars = dao.hasBindLeaseCars((String)params.get("id"));
		ret.put("hasbindleasecars", hasbindleasecars?"true":"false");
		return ret;
	}
}

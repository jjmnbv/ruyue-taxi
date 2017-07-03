package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.enums.VehicleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.enums.BindingStateEnum;
import com.szyciov.enums.DriverEnum;
import com.szyciov.lease.dao.PubVehicleDao;
import com.szyciov.lease.dto.VehicleQueryDto;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.entity.PubVehicleScope;
import com.szyciov.lease.param.PubVehicleQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("PubVehicleService")
public class PubVehicleService {
	private PubVehicleDao dao;
	@Resource(name = "PubVehicleDao")
	public void setDao(PubVehicleDao dao) {
		this.dao = dao;
	}

	private PubVehicleScopeService pubVehicleScopeService;

	@Autowired
	private PubDriverVehicleRefService refService;

	@Resource(name = "PubVehicleScopeService")
	public void setPubVehicleScopeServiceService(PubVehicleScopeService pubVehicleScopeService) {
		this.pubVehicleScopeService = pubVehicleScopeService;
	}
	
	public Map<String, String> createPubVehicle(PubVehicle pubVehicle) {
		Map<String, String> ret = new HashMap<String, String>();
		int count = checkPubVehicle(pubVehicle);
		if(count>0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "车牌号已存在");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "创建成功");
			dao.createPubVehicle(pubVehicle);
			if(pubVehicle.getBusinessScope()!=null){
				String businessScopeTemp = pubVehicle.getBusinessScope();
				String[] businessScope = businessScopeTemp.split(",");
				for(int i=0;i<businessScope.length;i++){
					PubVehicleScope pubVehicleScope = new PubVehicleScope();
					pubVehicleScope.setId(GUIDGenerator.newGUID());
					pubVehicleScope.setVehicleId(pubVehicle.getId());
					pubVehicleScope.setCityId(businessScope[i]);
					pubVehicleScope.setUpdater(pubVehicle.getUpdater());
					pubVehicleScope.setCreater(pubVehicle.getCreater());
					pubVehicleScopeService.createPubVehicleScope(pubVehicleScope);
				}
				if(pubVehicle.getSetAsDefault()!=null && !pubVehicle.getSetAsDefault().equals("")){
					if(pubVehicle.getSetAsDefault().equals("0")){
						Dictionary delete = new Dictionary();
						delete.setValue(pubVehicle.getLeasesCompanyId());
						delete.setType("经营区域");
						pubVehicleScopeService.deleteSetAsDefault(delete);
						for(int i=0;i<businessScope.length;i++){
							Dictionary dictionary = new Dictionary();
							dictionary.setId(GUIDGenerator.newGUID());
							dictionary.setValue(pubVehicle.getLeasesCompanyId());
							dictionary.setType("经营区域");
							dictionary.setText(businessScope[i]);
							dictionary.setSort(i+1);
							pubVehicleScopeService.setAsDefault(dictionary);
						}
					}
				}
			}
		}
		return ret;
	}
	public Map<String, String> updatePubVehicle(PubVehicle pubVehicle) {
		Map<String, String> ret = new HashMap<String, String>();
		int count = checkPubVehicle(pubVehicle);
		if(count>0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "车牌号已存在");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "修改成功");
//			if(pubVehicle.getVehicleTypes().equals("1")){
//				if(pubVehicle.getVehicleStatus().equals("1")){
//					pubVehicle.setDriverId("");
//				}
//			}
			dao.updatePubVehicle(pubVehicle);
			if(pubVehicle.getBusinessScope()!=null){
				pubVehicleScopeService.deletePubVehicleScope(pubVehicle.getId());
				String businessScopeTemp = pubVehicle.getBusinessScope();
				String[] businessScope = businessScopeTemp.split(",");
				for(int i=0;i<businessScope.length;i++){
					PubVehicleScope pubVehicleScope = new PubVehicleScope();
					pubVehicleScope.setId(GUIDGenerator.newGUID());
					pubVehicleScope.setVehicleId(pubVehicle.getId());
					pubVehicleScope.setCityId(businessScope[i]);
					pubVehicleScope.setUpdater(pubVehicle.getUpdater());
					pubVehicleScope.setCreater(pubVehicle.getCreater());
					pubVehicleScopeService.createPubVehicleScope(pubVehicleScope);
				}
				if(pubVehicle.getSetAsDefault()!=null && !pubVehicle.getSetAsDefault().equals("")){
					if(pubVehicle.getSetAsDefault().equals("0")){
						Dictionary delete = new Dictionary();
						delete.setValue(pubVehicle.getLeasesCompanyId());
						delete.setType("经营区域");
						pubVehicleScopeService.deleteSetAsDefault(delete);
						for(int i=0;i<businessScope.length;i++){
							Dictionary dictionary = new Dictionary();
							dictionary.setId(GUIDGenerator.newGUID());
							dictionary.setValue(pubVehicle.getLeasesCompanyId());
							dictionary.setType("经营区域");
							dictionary.setText(businessScope[i]);
							dictionary.setSort(i+1);
							pubVehicleScopeService.setAsDefault(dictionary);
						}
					}
				}
			}
		}
		return ret;
	}
	public Map<String, String> deletePubVehicle(String id){
		Map<String, String> ret = new HashMap<String, String>();
		int count = checkDelete(id);
		if(count>0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "请先在[司机管理]解绑车辆再删除");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "删除成功");
			dao.deletePubVehicle(id);
		}
		return ret;
	};
	
	public int checkDelete(String id){
		return dao.checkDelete(id);
	};
	
	public int checkPubVehicle(PubVehicle pubVehicle){
		return dao.checkPubVehicle(pubVehicle);
	};
	
	public List<PubVehicle> getPubVehicleListByQuery(PubVehicleQueryParam pubVehicleQueryParam){
		return dao.getPubVehicleListByQuery(pubVehicleQueryParam);
	};
	
	public int getPubVehicleListCountByQuery(PubVehicleQueryParam pubVehicleQueryParam){
		return dao.getPubVehicleListCountByQuery(pubVehicleQueryParam);
	};
	
	public PageBean getPubVehicleByQuery(PubVehicleQueryParam pubVehicleQueryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(pubVehicleQueryParam.getsEcho());
		List<PubVehicle> list = getPubVehicleListByQuery(pubVehicleQueryParam);
		for(PubVehicle l : list){
			List<PubVehicleScope> l1 = getVehicleidByVehicleScope(l.getId());
			String cityName = "";
			if(l1.size()>0){
				for(PubVehicleScope ll : l1){
					cityName+=ll.getCityName()+",";
				}
				l.setBusinessScope(cityName.substring(0, cityName.length()-1));
			}else{
				l.setBusinessScope("");
			}
			
		}
		int iTotalRecords = getPubVehicleListCountByQuery(pubVehicleQueryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<Map<String, Object>> getBrandCars(PubVehicle pubVehicle){
		return dao.getBrandCars(pubVehicle);
	};
	
	public List<PubVehicle> getServiceCars(String leasesCompanyId){
		return dao.getServiceCars(leasesCompanyId);
	};
	
	public List<PubVehicle> getCity(String leasesCompanyId){
		return dao.getCity(leasesCompanyId);
	};
	
	public PubVehicle getById(String id){
		PubVehicle pubVehicle = dao.getById(id);
		if(pubVehicle.getId()!=null && !pubVehicle.getId().equals("")){
			List<City> city = pubVehicleScopeService.getByVelId(pubVehicle.getId());
			String businessScope = "";
			String businessScopeTemp = "";
			for(int i=0;i<city.size();i++){
				if(i==(city.size()-1)){
					businessScope+=city.get(i).getId();
					businessScopeTemp+=city.get(i).getCity();
				}else{
					businessScope+=city.get(i).getId()+",";
					businessScopeTemp+=city.get(i).getCity()+",";
				}
			}
			pubVehicle.setBusinessScope(businessScope);
			pubVehicle.setBusinessScopeTemp(businessScopeTemp);
		}else{
			pubVehicle.setBusinessScope("");
			pubVehicle.setBusinessScopeTemp("");
		}
		return pubVehicle;
	}


	public List<City> getPubCityaddr(){
		return dao.getPubCityaddr();
	};
	

	public List<Dictionary> getPlateNoProvince(){
		return dao.getPlateNoProvince();
	};
	
	public List<Dictionary> getPlateNoCity(String id){
		return dao.getPlateNoCity(id);
	};
	
	public List<PubVehicle> exportExcel(PubVehicleQueryParam pubVehicleQueryParam){
		List<PubVehicle> list = dao.exportExcel(pubVehicleQueryParam);
		for(PubVehicle l : list){
			List<PubVehicleScope> l1 = getVehicleidByVehicleScope(l.getId());
			String cityName = "";
			for(PubVehicleScope ll : l1){
				cityName+=ll.getCityName()+"、";
			}
			if(VehicleEnum.VEHICLE_TYPE_TAXI.code.equals(l.getVehicleType())){
				l.setServiceCars("/");
			}
			l.setBusinessScope(cityName.substring(0, cityName.length()-1));
		}
		return list;
	};
	public PubDictionary getPlateCode(String plateOne) {
		return dao.getPlateCode(plateOne);
	}
	public String getPlateCity(PubDictionary plateTow) {
		return dao.getPlateCity(plateTow);
	}
	public PubCityAddr getCityCode(String city) {
		return dao.getCityCode(city);
	}
	public String getVehclineId(PubVehicle vehcline) {
		return dao.getVehclineId(vehcline);
	}
	//根据车辆id查经营区域
	public List<PubVehicleScope> getVehicleidByVehicleScope(String id){
		return dao.getVehicleidByVehicleScope(id);
	};


	/**
	 * 返回车辆信息及对应绑定司机姓名及电话
	 * @param pubVehicleQueryParam		查询参数对象
	 * @return
	 */
	public PageBean listVehicleAndBindInfo(PubVehicleQueryParam pubVehicleQueryParam){

		List<VehicleQueryDto> list = dao.listVehicleAndBindInfo(pubVehicleQueryParam);

		//设置绑定信息
		for(VehicleQueryDto dto:list){
			//获取车辆对应的绑定司机信息
			String bindDriver = refService.getAllVehicleBindInfos(dto.getId());
			//如果绑定司机信息为空，则状态为未绑定
			if("/".equals(bindDriver)){
				dto.setDriverState(BindingStateEnum.UN_BINDING.msg);
			}else if(dto.getDriverState()==null){
				//如果绑定司机状态不为空 且司机状态为空，则为下线状态
				dto.setDriverState(DriverEnum.WORK_STATUS_OFFLINE.msg);
			}
			dto.setBindInfoStr(bindDriver);
		}


		PageBean pageBean = new PageBean();
		pageBean.setsEcho(pubVehicleQueryParam.getsEcho());

		int iTotalRecords = dao.getlistVehicleAndBindInfoCount(pubVehicleQueryParam);
		int iTotalDisplayRecords = iTotalRecords;

		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}


	/**
	 * 根据ID 返回车辆信息
	 * @param id
	 * @return
	 */
	public PubVehicle getVehicleById(String id){
		PubVehicle pubVehicle = dao.getById(id);
		return pubVehicle;
	}




}

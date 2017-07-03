package com.szyciov.operate.service;

import com.szyciov.entity.City;
import com.szyciov.entity.Dictionary;
import com.szyciov.enums.BindingStateEnum;
import com.szyciov.enums.DriverEnum;
import com.szyciov.enums.VehicleEnum;
import com.szyciov.lease.entity.PubCityAddr;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.op.entity.PubVehicle;
import com.szyciov.op.entity.PubVehicleScope;
import com.szyciov.op.param.PubVehicleQueryParam;
import com.szyciov.op.param.vehicleManager.VehicleIndexQueryParam;
import com.szyciov.op.vo.vehiclemanager.VehicleExportVo;
import com.szyciov.op.vo.vehiclemanager.VehicleIndexVo;
import com.szyciov.operate.dao.PubVehicleDao;
import com.szyciov.operate.dto.VehicleQueryDto;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("PubVehicleService")
public class PubVehicleService {

	@Autowired
	private PubVehicleDao dao;

	private PubVehicleScopeService pubVehicleScopeService;

	@Autowired
	private PubDriverVehicleRefService refService;

	@Resource(name = "PubVehicleScopeService")
	public void setPubVehicleScopeServiceService(PubVehicleScopeService pubVehicleScopeService) {
		this.pubVehicleScopeService = pubVehicleScopeService;
	}
	
	public String createPubVehicle(PubVehicle pubVehicle)throws Exception {
		//创建车辆时默认营运中
		pubVehicle.setVehicleStatus(VehicleEnum.VEHICLE_STATUS_ONLINE.code);
		//保存车辆
		dao.createPubVehicle(pubVehicle);

		this.saveVehicleScope(pubVehicle);

		return pubVehicle.getId();
	}


	public int updatePubVehicle(PubVehicle pubVehicle) {

		int count = dao.updatePubVehicle(pubVehicle);

		this.saveVehicleScope(pubVehicle);
		return count;
	}

	/**
	 * 保存车辆经营范围
	 * @param pubVehicle
	 */
	private void saveVehicleScope(PubVehicle pubVehicle){
		if(StringUtils.isNotEmpty(pubVehicle.getBusinessScope())){
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
						dictionary.setType("经营区域");
						dictionary.setText(businessScope[i]);
						dictionary.setSort(i+1);
						pubVehicleScopeService.setAsDefault(dictionary);
					}
				}
			}
		}
	}
	public void deletePubVehicle(String id){
		dao.deletePubVehicle(id);
	};
	
	public int checkPubVehicle(PubVehicle pubVehicle){
		return dao.checkPubVehicle(pubVehicle);
	};
	
	public List<VehicleIndexVo> getPubVehicleListByQuery(VehicleIndexQueryParam param){
		return dao.getPubVehicleListByQuery(param);
	};
	
	public int getPubVehicleListCountByQuery(VehicleIndexQueryParam param){
		return dao.getPubVehicleListCountByQuery(param);
	};
	
	public PageBean getPubVehicleByQuery(VehicleIndexQueryParam param) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(param.getsEcho());
		List<VehicleIndexVo> volist = new ArrayList<>();
		//如果ID不为空且当前分页开始条数为第一条的情况下，将刚id对应的数据显示并置顶
//		if(StringUtils.isNotEmpty(param.getId())&&param.getiDisplayStart()==0){
//			volist.addAll(this.getPubVehicle(param));
//		}

		//添加正常结果记录集
		volist.addAll(this.listPubVehicle(param));
		int iTotalRecords = getPubVehicleListCountByQuery(param);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(volist);
		return pageBean;
	}

	/**
	 * 返回单个车辆显示信息
	 * @param id
	 * @return
	 */
	private List<VehicleIndexVo> getPubVehicle(VehicleIndexQueryParam param){
		List<VehicleIndexVo> list = getPubVehicleListByQuery(param);
		for(VehicleIndexVo l : list){
			l.setScopeStr(this.getScopeStr(l.getId()));

		}
		return list;
	}

	/**
	 * 返回车辆列表信息
	 * @param param
	 * @return
	 */
	private List<VehicleIndexVo> listPubVehicle(VehicleIndexQueryParam param){
		//正常查询清空id
		param.setId(null);
		List<VehicleIndexVo> list = getPubVehicleListByQuery(param);
		for(VehicleIndexVo l : list){
			l.setScopeStr(this.getScopeStr(l.getId()));

		}
		return list;
	}

	/**
	 * 拼装并返回车辆经营范围
	 * @param vehicleId
	 * @return
	 */
	private String getScopeStr(String vehicleId){
		List<PubVehicleScope> l1 = getVehicleidByVehicleScope(vehicleId);
		String cityName = "";
		if(l1.size()>0){
			for(PubVehicleScope ll : l1){
				cityName+=ll.getCityName()+",";
			}
			return cityName.substring(0, cityName.length()-1);
		}else{
			return "";
		}
	}
	public List<Map<String, Object>> getBrandCars(PubVehicle pubVehicle){
		return dao.getBrandCars(pubVehicle);
	};
	
	public List<PubVehicle> getServiceCars(String platformType){
		return dao.getServiceCars(platformType);
	};
	
	public List<City> getCity(String queryText,String platformType){
		return dao.getCity(queryText,platformType);
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
	
	public List<VehicleExportVo> exportExcel(VehicleIndexQueryParam param){
		List<VehicleExportVo> list = dao.exportExcel(param);
		for(VehicleExportVo l : list){
			List<PubVehicleScope> l1 = getVehicleidByVehicleScope(l.getId());
			String cityName = "";
			for(PubVehicleScope ll : l1){
				cityName+=ll.getCityName()+"、";
			}
			if(l1.size()>0){
				cityName = cityName.substring(0, cityName.length()-1);
				l.setScopes(cityName);
			}else{
				l.setScopes("/");
			}

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

    /**
     * 根据用户查询拥有权限的所有车辆信息
     * @param param
     * @return
     */
	public List<Map<String, Object>> getPubVehicleSelectByUser(Map<String, Object> param) {
        return dao.getPubVehicleSelectByUser(param);
    }

}

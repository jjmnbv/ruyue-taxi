package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.dto.pubVehInsur.PubVehInsurQueryDto;
import com.szyciov.entity.PubVehInsur;
import com.szyciov.lease.dao.PubVehInsurDao;
import com.szyciov.lease.entity.PubVehicle;
import com.szyciov.lease.param.pubVehInsurance.AddPubVehInsurs;
import com.szyciov.lease.param.pubVehInsurance.PubVehInsurQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("PubVehInsurService")
public class PubVehInsurService {
	
	private PubVehInsurDao dao;
	
	@Resource(name = "PubVehInsurDao")
	public void setDao(PubVehInsurDao dao) {
		this.dao = dao;
	}
    
	/**
	 * 
	 * @param  @param queryParam
	 * @param  @return
	 * @return PageBean
	 * @throws 
	 * @Title  getPubVehInsurByQuery 
	 * @author zhangdd
	 * @Description 车辆保险分页查询
	 */
	public PageBean getPubVehInsurByQuery(PubVehInsurQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PubVehInsurQueryDto> list = dao.getPubVehInsurListByQuery(queryParam);
		int iTotalRecords = dao.getPubVehInsurListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	/**
	 * 
	 * @param  @param id
	 * @param  @return
	 * @return Map<String,String>
	 * @throws 
	 * @Title  deletePubVehInsur 
	 * @author zhangdd
	 * @Description 删除车辆保险
	 */
	public Map<String, String> deletePubVehInsur(String id){
		Map<String, String> ret = new HashMap<String, String>();
		dao.deletePubVehInsur(id);
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "删除成功");
		return ret;
	}
	
	/**
	 * 
	 * @param  @param pubDriver
	 * @param  @return
	 * @return Map<String,String>
	 * @throws 
	 * @Title  updatePubVehInsur 
	 * @author zhangdd
	 * @Description 更新车辆保险
	 */
	public Map<String, String> updatePubVehInsur(PubVehInsur pubDriver){
		Map<String, String> ret = new HashMap<String, String>();
		dao.updatePubVehInsur(pubDriver);
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		return ret;
	}
	
	/**
	 * 
	 * @param  @param id
	 * @param  @return
	 * @return PubVehInsurQueryDto
	 * @throws 
	 * @Title  getPubVehInsurById 
	 * @author zhangdd
	 * @Description 根据保险id查找保险
	 */
	public PubVehInsurQueryDto getPubVehInsurById(String id){
		return dao.getPubVehInsurById(id);
	}
	
	/**
	 * 
	 * @param  @param pubInsurs
	 * @param  @return
	 * @return Map<String,String>
	 * @throws 
	 * @Title  addPubVehInsurs 
	 * @author zhangdd
	 * @Description 批量添加车辆保险
	 */
	public Map<String, String> addPubVehInsurs(AddPubVehInsurs pubInsurs){
		Map<String, String> ret = new HashMap<String, String>();
		//1. 判断车辆是否存在
		PubVehicle pubVehicle = dao.getPubVehicleByPlateNo(pubInsurs.getFullplateno());
		if(pubVehicle == null){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "车辆不存在,添加保险失败");
			return ret;
		}
		pubInsurs.setVehicleid(pubVehicle.getId());
		pubInsurs.setLeasescompanyid(pubVehicle.getLeasesCompanyId());
		
		//2. 检查保险是否重复
		StringBuilder errorBuilder = new StringBuilder("以下车辆保险已存在,请勿重复添加:").append("\n");
		int count = 0;
		int insurSize = pubInsurs.getInsurList().size();
		PubVehInsur pubVehInsur = new PubVehInsur();
		pubVehInsur.setVehicleid(pubInsurs.getVehicleid());
		for(int i = 0 ;i < insurSize; i ++){
			pubVehInsur.setInsurcom(pubInsurs.getInsurList().get(i).getInsurcom());
			pubVehInsur.setInsurtype(pubInsurs.getInsurList().get(i).getInsurtype());
			pubVehInsur.setInsurnum(pubInsurs.getInsurList().get(i).getInsurnum());
			pubInsurs.getInsurList().get(i).setId(GUIDGenerator.newGUID());
			int checkCount =  checkPubVehInsur(pubVehInsur);
			if (checkCount > 0){
				errorBuilder.append("保险公司:").append(pubVehInsur.getInsurcom()).append(",").append("保险号:").append(pubVehInsur.getInsurnum()).append("\n");
				count = count + checkCount;
			}
		}
		//2.1  如果保险重复,则终止添加操作
		if (count > 0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", errorBuilder.toString());
		}
		//3. 添加保险
		else{
			int addCount = dao.addPubVehInsurs(pubInsurs);
			if(addCount <= 0){
				ret.put("ResultSign", "Error");
				ret.put("MessageKey", "添加失败,请稍后重试");
			}else{
				ret.put("ResultSign", "Successful");
				ret.put("MessageKey", "添加成功");	
			}
		}	
		return ret;
	}
	
	/**
	 * 
	 * @param  @param pubVehInsur
	 * @param  @return
	 * @return int
	 * @throws 
	 * @Title  checkPubVehInsur 
	 * @author zhangdd
	 * @Description 检查车辆保险是否重复 (用于添加保险时)
	 */
	public int checkPubVehInsur(PubVehInsur pubVehInsur){
		return dao.checkPubVehInsur(pubVehInsur);
		
	}
	
	/**
	 * 导出excel
	 */
	public List<PubVehInsurQueryDto> exportExcel(PubVehInsurQueryParam pubVehInsurQueryParam) {
		return  dao.exportExcel(pubVehInsurQueryParam);
	}
	
	/**
	 * 查询车牌号是否存在
	 */
	public PubVehicle checkPubVehicle(String plateNo){
		return dao.getPubVehicleByPlateNo(plateNo);
	}
	
}

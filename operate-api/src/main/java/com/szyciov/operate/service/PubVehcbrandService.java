package com.szyciov.operate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.operate.dao.PubVehcbrandDao;
import com.szyciov.op.entity.PubVehcbrand;
import com.szyciov.op.param.PubVehcbrandQueryParam;
import com.szyciov.param.QueryParam;
import com.szyciov.util.PageBean;

@Service("PubVehcbrandService")
public class PubVehcbrandService {
	private PubVehcbrandDao dao;
	@Resource(name = "PubVehcbrandDao")
	public void setDao(PubVehcbrandDao dao) {
		this.dao = dao;
	}
	public Map<String, String> createPubVehcbrand(PubVehcbrand pubVehcbrand) {
		Map<String, String> ret = new HashMap<String, String>();
		int count = checkBrand(pubVehcbrand);
		if(count>0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", pubVehcbrand.getName()+"品牌已存在");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "保存成功");
			dao.createPubVehcbrand(pubVehcbrand);
		}
		return ret;
	}
	public Map<String, String> updatePubVehcbrand(PubVehcbrand pubVehcbrand) {
		Map<String, String> ret = new HashMap<String, String>();
		
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		dao.updatePubVehcbrand(pubVehcbrand);
	
		return ret;
	}
	public Map<String, String> deletePubVehcbrand(String id){
		Map<String, String> ret = new HashMap<String, String>();
		int count = dao.checkDelete(id);
		if(count>0){
			ret.put("ResultSign", "Error");
			ret.put("MessageKey", "该品牌下存在车系，请先到车系管理删除车系");
		}else{
			ret.put("ResultSign", "Successful");
			ret.put("MessageKey", "删除成功");
			dao.deletePubVehcbrand(id);
		}
		return ret;
	};
	
	public PageBean getPubVehcbrandByQuery(PubVehcbrandQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PubVehcbrand> list = getPubVehcbrandListByQuery(queryParam);
		int iTotalRecords = getPubVehcbrandListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);
		return pageBean;
	}
	
	public List<PubVehcbrand> getPubVehcbrandListByQuery(PubVehcbrandQueryParam queryParam){
		return dao.getPubVehcbrandListByQuery(queryParam);
	};
	
	public int getPubVehcbrandListCountByQuery(PubVehcbrandQueryParam queryParam){
		return dao.getPubVehcbrandListCountByQuery(queryParam);
	};
	
	public List<Map<String, Object>> getBrand(PubVehcbrand pubVehcbrand){
		return dao.getBrand(pubVehcbrand);
	};
	
	public PubVehcbrand getById(String id){
		return dao.getById(id);
	};
	
	public List<PubVehcbrand> exportData(Map<String, String> map){
		return dao.exportData(map);
	};
	
	public int checkBrand(PubVehcbrand pubVehcbrand){
		return dao.checkBrand(pubVehcbrand);
	};
	
	public PubVehcbrand getByName(PubVehcbrand pubVehcbrand){
		return dao.getByName(pubVehcbrand);
	};
}

package com.szyciov.operate.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.entity.PubAdImage;
import com.szyciov.operate.dao.PubAdimageDao;
import com.szyciov.param.PubAdimageQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("PubAdimageService")
public class PubAdimageService {
	
	private PubAdimageDao dao;
	@Resource(name = "PubAdimageDao")
	public void setDao(PubAdimageDao dao) {
		this.dao = dao;
	}
	
	public PageBean getPubAdimageByQuery(PubAdimageQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		int iTotalRecords = getPubAdimageCountByQuery(queryParam);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setiTotalDisplayRecords(iTotalRecords);
		pageBean.setAaData(getPubAdimageListByQuery(queryParam));
		return pageBean;
	}
	
	public Map<String, String> createPubAdimage(PubAdImage object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		Date currentTime = new Date();
		object.setCreatetime(currentTime);
		object.setUpdatetime(currentTime);
		object.setId(GUIDGenerator.newGUID());
		object.setStatus(1);
		object.setUsestate("1");
		insertPubAdimage(object);
		return ret;
	}
	
	public Map<String, String> editPubAdimage(PubAdImage object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "修改成功");
		Date currentTime = new Date();
		object.setUpdatetime(currentTime);
		updatePubAdimage(object);
		return ret;
	}
	
	public Map<String, String> editPubAdviseToStart(PubAdImage object) {
		//禁用所有
		PubAdImage adImage = getPubAdimageById(object.getId());
		adImage.setUpdater(object.getUpdater());
		adImage.setUsestate("1");
		updatePubAdviseUseState(adImage);
		
		//启用当前广告配置
		updatePubAdimage(object);
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "修改成功");
		return ret;
	}
	
	public List<PubAdImage> getPubAdimageList(PubAdImage object) {
		return dao.getPubAdimageList(object);
	}
	
	public List<PubAdImage> getPubAdimageListByQuery(PubAdimageQueryParam queryParam) {
		return dao.getPubAdimageListByQuery(queryParam);
	}
	
	public int getPubAdimageCountByQuery(PubAdimageQueryParam queryParam) {
		return dao.getPubAdimageCountByQuery(queryParam);
	}
	
	public void insertPubAdimage(PubAdImage object) {
		dao.insertPubAdimage(object);
	}
	
	public void updatePubAdimage(PubAdImage object) {
		dao.updatePubAdimage(object);;
	}
	
	public void updatePubAdviseUseState(PubAdImage object) {
		dao.updatePubAdviseUseState(object);
	}
	
	public PubAdImage getPubAdimageById(String id) {
		return dao.getPubAdimageById(id);
	}
	
}

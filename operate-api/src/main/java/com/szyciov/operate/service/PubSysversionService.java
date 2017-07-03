package com.szyciov.operate.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.entity.PubSysVersion;
import com.szyciov.lease.entity.PubDictionary;
import com.szyciov.operate.dao.PubSysversionDao;
import com.szyciov.param.PubSysversionQueryParam;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("PubSysversionService")
public class PubSysversionService {
	
	private PubSysversionDao dao;
	@Resource(name = "PubSysversionDao")
	public void setDao(PubSysversionDao dao) {
		this.dao = dao;
	}
	
	public PageBean getPubSysversionByQuery(PubSysversionQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		int iTotalRecords = getPubSysversionCountByQuery(queryParam);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setiTotalDisplayRecords(iTotalRecords);
		pageBean.setAaData(getPubSysversionListByQuery(queryParam));
		return pageBean;
	}
	
	public Map<String, String> editPubSysversion(PubSysVersion object) {
		object.setVersionno(getVersionno(object.getCurversion()));
		
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "修改成功");
		//验证唯一性
		PubSysVersion sysversion = new PubSysVersion();
		sysversion.setStatus(1);
		sysversion.setVersionno(object.getVersionno());
		sysversion.setPlatformtype(object.getPlatformtype());
		List<PubSysVersion> sysversionList = getPubSysversionList(sysversion);
		if(null != sysversionList && !sysversionList.isEmpty()) {
			for (PubSysVersion pubSysversion : sysversionList) {
				if(pubSysversion.getId().equals(object.getId())) {
					continue;
				}
				//当系统类型为2(ios、Android)时，返回错误
				if ("2".equals(pubSysversion.getSystemtype()) || "2".equals(object.getSystemtype())
						|| pubSysversion.getSystemtype().equals(object.getSystemtype())) {
					ret.put("ResultSign", "Error");
					ret.put("MessageKey", "已创建该版本说明");
					return ret;
				}
			}
		}
		//修改版本说明
		Date currentTime = new Date();
		object.setUpdatetime(currentTime);
		//判断是否强制更新
		if(object.getIsForceUpdate() == 1) {
			object.setMaxversionno(object.getVersionno());
		} else {
			object.setMaxversionno(0);
		}
		updatePubSysversion(object);
		return ret;
	}
	
	public Map<String, String> createPubSysversion(PubSysVersion object) {
		object.setVersionno(getVersionno(object.getCurversion()));
		
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		//验证唯一性
		PubSysVersion sysversion = new PubSysVersion();
		sysversion.setStatus(1);
		sysversion.setVersionno(object.getVersionno());
		sysversion.setPlatformtype(object.getPlatformtype());
		List<PubSysVersion> sysversionList = getPubSysversionList(sysversion);
		if(null != sysversionList && !sysversionList.isEmpty()) {
			for (PubSysVersion pubSysversion : sysversionList) {
				//当系统类型为2(ios、Android)时，返回错误
				if ("2".equals(pubSysversion.getSystemtype()) || "2".equals(object.getSystemtype())
						|| pubSysversion.getSystemtype().equals(object.getSystemtype())) {
					ret.put("ResultSign", "Error");
					ret.put("MessageKey", "已创建该版本说明");
					return ret;
				}
			}
		}
		//添加版本说明
		Date currentTime = new Date();
		object.setId(GUIDGenerator.newGUID());
		object.setCreatetime(currentTime);
		object.setUpdatetime(currentTime);
		object.setStatus(1);
		//判断是否强制更新
		if(object.getIsForceUpdate() == 1) {
			object.setMaxversionno(object.getVersionno());
		} else {
			object.setMaxversionno(0);
		}
		insertPubSysversion(object);
		return ret;
	}
	
	public Map<String, String> deletePubSysversion(PubSysVersion object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "修改成功");
		Date currentTime = new Date();
		object.setUpdatetime(currentTime);
		updatePubSysversion(object);
		return ret;
	}
	
	public List<PubDictionary> getSystemtypeByList() {
		return dao.getSystemtypeByList();
	}
	
	public List<PubSysVersion> getPubSysversionList(PubSysVersion object) {
		return dao.getPubSysversionList(object);
	}
	
	public List<Map<String, String>> getCurversionByList(PubSysVersion object) {
		return dao.getCurversionByList(object);
	}
	
	public List<PubSysVersion> getPubSysversionListByQuery(PubSysversionQueryParam queryParam) {
		return dao.getPubSysversionListByQuery(queryParam);
	}
	
	public int getPubSysversionCountByQuery(PubSysversionQueryParam queryParam) {
		return dao.getPubSysversionCountByQuery(queryParam);
	}
	
	public List<PubSysVersion> getPubSysversionById(String id) {
		return dao.getPubSysversionById(id);
	}
	
	public void insertPubSysversion(PubSysVersion object) {
		dao.insertPubSysversion(object);
	}
	
	public void updatePubSysversion(PubSysVersion object) {
		dao.updatePubSysversion(object);
	}
	
	/**
	 * 将版本号转换为数字形式
	 * @param version
	 * @return
	 */
	private int getVersionno(String version) {
		version = version.replaceAll("\\.", "").replaceAll("V", "");
		return Integer.valueOf(version);
	}
	
}

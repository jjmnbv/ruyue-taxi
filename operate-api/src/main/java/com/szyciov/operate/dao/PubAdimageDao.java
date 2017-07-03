package com.szyciov.operate.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.PubAdImage;
import com.szyciov.operate.mapper.PubAdimageMapper;
import com.szyciov.param.PubAdimageQueryParam;

@Repository("PubAdimageDao")
public class PubAdimageDao {
	
	private PubAdimageMapper mapper;
	@Resource
	public void setMapper(PubAdimageMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<PubAdImage> getPubAdimageListByQuery(PubAdimageQueryParam queryParam) {
		return mapper.getPubAdimageListByQuery(queryParam);
	}
	
	public int getPubAdimageCountByQuery(PubAdimageQueryParam queryParam) {
		return mapper.getPubAdimageCountByQuery(queryParam);
	}
	
	public void insertPubAdimage(PubAdImage object) {
		mapper.insertPubAdimage(object);;
	}
	
	public void updatePubAdimage(PubAdImage object) {
		mapper.updatePubAdimage(object);
	}
	
	public List<PubAdImage> getPubAdimageList(PubAdImage object) {
		return mapper.getPubAdimageList(object);
	}
	
	public void updatePubAdviseUseState(PubAdImage object) {
		mapper.updatePubAdviseUseState(object);
	}
	
	public PubAdImage getPubAdimageById(String id) {
		return mapper.getPubAdimageById(id);
	}

}

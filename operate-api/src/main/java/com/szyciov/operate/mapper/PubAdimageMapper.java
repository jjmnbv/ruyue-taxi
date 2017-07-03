package com.szyciov.operate.mapper;

import java.util.List;

import com.szyciov.entity.PubAdImage;
import com.szyciov.param.PubAdimageQueryParam;

public interface PubAdimageMapper {
	
	List<PubAdImage> getPubAdimageListByQuery(PubAdimageQueryParam queryParam);
	
	int getPubAdimageCountByQuery(PubAdimageQueryParam queryParam);
	
	void insertPubAdimage(PubAdImage object);
	
	void updatePubAdimage(PubAdImage object);
	
	List<PubAdImage> getPubAdimageList(PubAdImage object);
	
	void updatePubAdviseUseState(PubAdImage object);
	
	PubAdImage getPubAdimageById(String id);

}

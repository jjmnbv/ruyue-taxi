package com.szyciov.operate.mapper;

import java.util.List;

import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.PeUser;
import com.szyciov.op.entity.PeUserdisablelog;
import com.szyciov.op.param.PeUserQueryParam;
import com.szyciov.param.QueryParam;

public interface PeUserMapper {
    
	List<PeUser> getPeUserListByQuery(PeUserQueryParam queryParam);
	
	int getPeUserListCountByQuery(PeUserQueryParam queryParam);
	
	void enable(String id);
	
	void disable(String id);
	
	int disableRecord(String id);
	
	PeUser getById(String id);

	void resetPassword(PeUser peUser);
	
	List<PeUserdisablelog> getPeUserdisablelogListByQuery(QueryParam queryParam);
	
	int getPeUserdisablelogListCountByQuery(QueryParam queryParam);
	
	List<PeUser> exportData(PeUserQueryParam peUserQueryParam);
	
	void createPeUserdisablelog(PeUserdisablelog peUserdisablelog);
	
	OpPlatformInfo getOpPlatforminfo();
	
	void resetOrgUserPassword(PeUser peUser);
}

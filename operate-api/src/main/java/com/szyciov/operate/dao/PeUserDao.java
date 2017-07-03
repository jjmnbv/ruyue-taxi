package com.szyciov.operate.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.op.entity.PeUser;
import com.szyciov.op.entity.PeUserdisablelog;
import com.szyciov.op.param.PeUserQueryParam;
import com.szyciov.operate.mapper.PeUserMapper;
import com.szyciov.param.QueryParam;

@Repository("PeUserDao")
public class PeUserDao {
	public PeUserDao() {
	}

	private PeUserMapper mapper;

	@Resource
	public void setMapper(PeUserMapper mapper) {
		this.mapper = mapper;
	}
	
	public List<PeUser> getPeUserListByQuery(PeUserQueryParam queryParam){
		return mapper.getPeUserListByQuery(queryParam);
	};
	
	public int getPeUserListCountByQuery(PeUserQueryParam queryParam){
		return mapper.getPeUserListCountByQuery(queryParam);
	};
	
	public int disableRecord(String id){
		return mapper.disableRecord(id);
	};
	
	public void enable(String id){
		mapper.enable(id);
	};

	public PeUser getById(String id){
		return mapper.getById(id);
	};

	public void resetPassword(PeUser peUser){
		mapper.resetPassword(peUser);
	};
	
	public List<PeUserdisablelog> getPeUserdisablelogListByQuery(QueryParam queryParam){
		return mapper.getPeUserdisablelogListByQuery(queryParam);
	};
	
	public int getPeUserdisablelogListCountByQuery(QueryParam queryParam){
		return mapper.getPeUserdisablelogListCountByQuery(queryParam);
	};
	
	public List<PeUser> exportData(PeUserQueryParam peUserQueryParam){
		return mapper.exportData(peUserQueryParam);
	};
	
	public void createPeUserdisablelog(PeUserdisablelog peUserdisablelog){
		mapper.createPeUserdisablelog(peUserdisablelog);
	};
	
	public void disable(String id){
		mapper.disable(id);
	};
	
	public OpPlatformInfo getOpPlatforminfo(){
		return mapper.getOpPlatforminfo();
	};
	
	public void resetOrgUserPassword(PeUser peUser){
		mapper.resetOrgUserPassword(peUser);
	};
}

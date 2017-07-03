package com.szyciov.lease.dao;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.mapper.LeLeasescompanyMapper;
import com.szyciov.op.entity.LeCompanyAgreement;
import com.szyciov.op.entity.OpUser;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository("LeLeasescompanyDao")
public class LeLeasescompanyDao {
	public LeLeasescompanyDao() {
	}

	private LeLeasescompanyMapper mapper;

	@Resource
	public void setMapper(LeLeasescompanyMapper mapper) {
		this.mapper = mapper;
	}
	
	public void updateLeLeasescompany(LeLeasescompany leLeasescompany) {
		mapper.updateLeLeasescompany(leLeasescompany);
	}
	
	public LeLeasescompany getLeLeasescompany(String id){
		return mapper.getLeLeasescompany(id);
	};
	
	public void updatePassword(User user){
		mapper.updatePassword(user);
	};
	
	public void updateToC(LeLeasescompany leLeasescompany){
		mapper.updateToC(leLeasescompany);
	};
	
	public void outToC(LeLeasescompany leLeasescompany){
		mapper.outToC(leLeasescompany);
	};
	
	public LeCompanyAgreement getLeCompanyAgreement(String id){
		return mapper.getLeCompanyAgreement(id);
	};
	
	public void updataLeUserFristtime(String id){
		mapper.updataLeUserFristtime(id);
	};
	
	public OpUser getOpUser(){
		return mapper.getOpUser();
	}

	/**
	 * 返回租赁公司对象
	 * @param leaseid	租赁公司ID
	 * @return
	 */
	public LeLeasescompany getLeLeasescompanyById(String leaseid){
		return mapper.getLeLeasescompanyById(leaseid);
	}
}

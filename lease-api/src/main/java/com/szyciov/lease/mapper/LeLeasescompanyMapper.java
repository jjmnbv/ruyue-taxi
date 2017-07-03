package com.szyciov.lease.mapper;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.User;
import com.szyciov.op.entity.LeCompanyAgreement;
import com.szyciov.op.entity.OpUser;

public interface LeLeasescompanyMapper {

	void updateLeLeasescompany(LeLeasescompany leLeasescompany);
	
	LeLeasescompany getLeLeasescompany(String id);
	
	void updatePassword(User user);
	
	void updateToC(LeLeasescompany leLeasescompany);
	
	void outToC(LeLeasescompany leLeasescompany);
	
	LeCompanyAgreement getLeCompanyAgreement(String id);
	
	void updataLeUserFristtime(String id);
	
	OpUser getOpUser();

	LeLeasescompany getLeLeasescompanyById(String leaseid);
}
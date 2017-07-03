package com.szyciov.lease.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.PubDriver;
import com.szyciov.lease.mapper.PubDriverMapper;
import com.szyciov.lease.param.PubDriverQueryParam;
import com.szyciov.lease.param.ServiceOrgQueryParam;
import com.szyciov.org.entity.OrgOrganDriverRef;
import com.szyciov.org.entity.OrgUser;

@Repository("PubDriverDao")
public class PubDriverDao {
	public PubDriverDao() {
	}

	private PubDriverMapper mapper;

	@Resource
	public void setMapper(PubDriverMapper mapper) {
		this.mapper = mapper;
	}
	
	public void createPubDriver(PubDriver pubDriver) {
		mapper.createPubDriver(pubDriver);
	}
	
	public int checkPubDriver(PubDriver pubDriver){
		return mapper.checkPubDriver(pubDriver);
	}
	
	public PubDriver getByPubDriverId(String id){
		return mapper.getByPubDriverId(id);
	}
	
	public void updatePubDriver(PubDriver pubDriver){
		mapper.updatePubDriver(pubDriver);
	};
	
	public void resetPassword(PubDriver pubDriver){
		mapper.resetPassword(pubDriver);
	};
	
	public int checkDelete(String id){
		return mapper.checkDelete(id);
	};
	
	public void deletePubDriver(String id){
		mapper.deletePubDriver(id);
	};
	
	public List<PubDriver> getPubDriverListByQuery(PubDriverQueryParam pubDriverQueryParam){
		return mapper.getPubDriverListByQuery(pubDriverQueryParam);
	};
	
	public List<PubDriver> getPubDriverListByBound(PubDriverQueryParam pubDriverQueryParam){
		return mapper.getPubDriverListByBound(pubDriverQueryParam);
	};
	
	public int getPubDriverListCountByQuery(PubDriverQueryParam pubDriverQueryParam){
		return mapper.getPubDriverListCountByQuery(pubDriverQueryParam);
	};
	
	public PubDriver getVehicleInfo(String id){
		return mapper.getVehicleInfo(id);
	};
	
	public List<OrgOrgan> getServiceOrgListByQuery(ServiceOrgQueryParam serviceOrgQueryParam){
		return mapper.getServiceOrgListByQuery(serviceOrgQueryParam);
	};
	
	public int getServiceOrgListCountByQuery(ServiceOrgQueryParam serviceOrgQueryParam){
		return mapper.getServiceOrgListCountByQuery(serviceOrgQueryParam);
	};
	
	public List<Map<String, Object>> getCity(PubDriverQueryParam pubDriverQueryParam){
		return mapper.getCity(pubDriverQueryParam);
	};

	public PubDriver getById(String id){
		return mapper.getById(id);
	};
	
	public int judgeBinding(String id){
		return mapper.judgeBinding(id);
	};
	
//	public PubDriver unwrapVel(String id){
//		return mapper.unwrapVel(id);
//	};
	
//	public int judgeUnwrapRecord(String id){
//		return mapper.judgeUnwrapRecord(id);
//	};
	
	public void createOrgOrganDriverRef(OrgOrganDriverRef orgOrganDriverRef){
		mapper.createOrgOrganDriverRef(orgOrganDriverRef);
	};
	
	public List<OrgOrganDriverRef> getOrgOrganDriverRef(String id){
		return mapper.getOrgOrganDriverRef(id);
	};
	
	public void deleteOrgOrganDriverRef(String id){
		mapper.deleteOrgOrganDriverRef(id);
	};
	
	public List<Map<String, Object>> getSpecialDri(PubDriverQueryParam pubDriverQueryParam){
		return mapper.getSpecialDri(pubDriverQueryParam);
	};
	
	public List<OrgOrganDriverRef> getFullNameBySpecialDri(String fullName){
		return mapper.getFullNameBySpecialDri(fullName);
	};
	
	public List<PubDriver> exportData(PubDriverQueryParam pubDriverQueryParam){
		return mapper.exportData(pubDriverQueryParam);
	};
	
	public int checkPubDriverPhone(PubDriver pubDriver){
		return mapper.checkPubDriverPhone(pubDriver);
	};
	
	public LeLeasescompany getLeLeasescompany(String id){
		return mapper.getLeLeasescompany(id);
	};
	
	public int checkUnbundling(String driverId){
		return mapper.checkUnbundling(driverId);
	};
	
	public OrgUser getOrgUserUsertype(String id){
		return mapper.getOrgUserUsertype(id);
	};


	/**
	 * 更新司机相关状态
	 * @param driver
	 * @return
	 */
	public int updatePubDriverStatus(PubDriver driver){
		return mapper.updatePubDriverStatus(driver);
	}

	public int cleanPubDriverOnlineTime (PubDriver driver){
		return mapper.cleanPubDriverOnlineTime(driver);
	}


	public List<Map<String, Object>> getQueryJobNum(PubDriverQueryParam pubDriverQueryParam){
		return mapper.getQueryJobNum(pubDriverQueryParam);
	};
}

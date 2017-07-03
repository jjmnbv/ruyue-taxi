package com.szyciov.operate.dao;


import com.szyciov.op.entity.PubDriver;
import com.szyciov.op.param.PubDriverQueryParam;
import com.szyciov.op.vo.pubdriver.ExportDriverVo;
import com.szyciov.operate.mapper.PubDriverMapper;
import com.szyciov.org.entity.OrgOrganDriverRef;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
	

	
	public List<Map<String, Object>> getCity(PubDriverQueryParam pubDriverQueryParam){
		return mapper.getCity(pubDriverQueryParam);
	};

	public PubDriver getById(String id){
		return mapper.getById(id);
	};
	
	public int judgeBinding(String id){
		return mapper.judgeBinding(id);
	};

	public List<Map<String, Object>> getSpecialDri(PubDriverQueryParam pubDriverQueryParam){
		return mapper.getSpecialDri(pubDriverQueryParam);
	};
	
	public List<OrgOrganDriverRef> getFullNameBySpecialDri(String fullName){
		return mapper.getFullNameBySpecialDri(fullName);
	};
	
	public List<ExportDriverVo> exportData(PubDriverQueryParam pubDriverQueryParam){
		return mapper.exportData(pubDriverQueryParam);
	};
	
	public int checkPubDriverPhone(PubDriver pubDriver){
		return mapper.checkPubDriverPhone(pubDriver);
	};
	

	public int checkUnbundling(String driverId){
		return mapper.checkUnbundling(driverId);
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
}

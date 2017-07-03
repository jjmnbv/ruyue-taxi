package com.szyciov.touch.dao;

import com.szyciov.dto.pubAlarmprocess.PubAlarmprocessSaveDto;
import com.szyciov.entity.PubDriver;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.entity.TreeNode;
import com.szyciov.touch.dto.AirPortDTO;
import com.szyciov.touch.dto.CityDTO;
import com.szyciov.touch.dto.ServiceBusinessDTO;
import com.szyciov.touch.dto.ServiceModelsDTO;
import com.szyciov.touch.mapper.PartnerUseCarMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by shikang on 2017/5/10.
 */

@Component("PartnerUseCarDao")
public class PartnerUseCarDao {

    private PartnerUseCarMapper partnerUseCarMapper;
    @Resource
    public void setPartnerUseCarMapper(PartnerUseCarMapper partnerUseCarMapper) {
        this.partnerUseCarMapper = partnerUseCarMapper;
    }
    
    public List<CityDTO> getFullCityList() {
		return partnerUseCarMapper.getFullCityList();
	}
    
    public List<CityDTO> getServiceCityList(String companyid) {
    	return partnerUseCarMapper.getServiceCityList(companyid);
    }
    
    public List<AirPortDTO> getAirPortList(String cityId) {
    	return partnerUseCarMapper.getAirPortList(cityId);
    }
    
	public List<ServiceBusinessDTO> getServiceBusiness(Map<String, Object> map) {
		return partnerUseCarMapper.getServiceBusiness(map);
	}
	
	public List<ServiceModelsDTO> getServiceModels(Map<String, Object> map) {
		return partnerUseCarMapper.getServiceModels(map);
	}
	
	public Map<String, Object> getOrgOrderCost(Map<String, Object> map) {
		return partnerUseCarMapper.getOrgOrderCost(map);
	}

    public Map<String, Object> getOrgOrderByOrderno(String orderno) {
        return partnerUseCarMapper.getOrgOrderByOrderno(orderno);
    }

    public Map<String, Object> getPubDriverById(String driverid) {
        return partnerUseCarMapper.getPubDriverById(driverid);
    }

    public List<Map<String, Object>> getSimpleOrder(Map<String, Object> param) {
        return partnerUseCarMapper.getSimpleOrder(param);
    }

    public PubDriver getDriverByPhone(String phone) {
        return partnerUseCarMapper.getDriverByPhone(phone);
    }

    public void insertPubAlarmprocessSaveDto(PubAlarmprocessSaveDto object) {
        partnerUseCarMapper.insertPubAlarmprocessSaveDto(object);
    }

    public List<String> getLeUserByCompanyid(String companyid) {
        return partnerUseCarMapper.getLeUserByCompanyid(companyid);
    }

    public void updateOrgOrderPaymentStatus(OrgOrder object) {
        partnerUseCarMapper.updateOrgOrderPaymentStatus(object);
    }

    public void insertOrgOrderpaymentrecord(Map<String, String> param) {
        partnerUseCarMapper.insertOrgOrderpaymentrecord(param);
    }

    public void updateDriverRate(Map<String, String> param) {
        partnerUseCarMapper.updateDriverRate(param);
    }

    public void updateOrgOrderRate(OrgOrder object) {
        partnerUseCarMapper.updateOrgOrderRate(object);
    }

	public OrgUser getUser4Org(String account) {
		return partnerUseCarMapper.getUser4Org(account);
	}
	public void updateUser4Org(Map<String, Object> userinfo) {
		 partnerUseCarMapper.updateUser4Org(userinfo);
	}

	public boolean hasSMSCode(Map<String, String> smscodeobj) {
		return partnerUseCarMapper.hasSMSCode(smscodeobj);
	}

	public void updateSMSCode(Map<String, String> smscodeobj) {
		partnerUseCarMapper.updateSMSCode(smscodeobj);
	}

	public void saveSMSCode(Map<String, String> smscodeobj) {
		partnerUseCarMapper.saveSMSCode(smscodeobj);
	}

	public void addLog4Org(Map<String, Object> loginfo) {
		partnerUseCarMapper.addLog4Org(loginfo);
	}

	public Map<String, Object> getUserTokenByUserId(Map<String, Object> pp) {
		return partnerUseCarMapper.getUserTokenByUserId(pp);
	}

	public void createOrUpdateUsertoken(Map<String, Object> tokeninfo) {
         partnerUseCarMapper.createOrUpdateUsertoken(tokeninfo);
	}

	public Map<String, Object> getSMSInfo(Map<String, Object> params) {
		return partnerUseCarMapper.getSMSInfo(params);
	}

	public void deleteSMSCode(Map<String, Object> params) {
		partnerUseCarMapper.deleteSMSCode(params);
	}

	public String acquireUserIDByChannel(String channelId) {
		return null;
	}

	public void addAuthTimeliness(Map<String,Object> params) {
		partnerUseCarMapper.addAuthTimeliness(params);
	}

	public Map<String, Object> getAuthInfoByKey(String channelKey) {
		return partnerUseCarMapper.getAuthInfoByKey(channelKey);
	}

	public boolean checkRegister4Org(String phone, String channelId) {
		return partnerUseCarMapper.checkRegister4Org(phone,channelId);
	}

	public Map<String, Object> getChInfoByKey(String channelkey) {
		return partnerUseCarMapper.getChInfoByKey(channelkey);
	}

	public void registerOrgUser(OrgUser user) {
		partnerUseCarMapper.registerOrgUser(user);		
	}

	public List<TreeNode> getDeptTreeNodes(String organid) {
		return partnerUseCarMapper.getDeptTreeNodes(organid);
	}

	public boolean hasResId(String resid) {
		return partnerUseCarMapper.hasResId(resid);
	}

	public void addDept(Map<String, Object> deptInfo) {
		partnerUseCarMapper.addDept(deptInfo);
	}
}

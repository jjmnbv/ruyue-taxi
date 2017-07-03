package com.szyciov.touch.mapper;

import com.szyciov.dto.pubAlarmprocess.PubAlarmprocessSaveDto;
import com.szyciov.entity.PubDriver;
import com.szyciov.org.entity.OrgOrder;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.org.entity.TreeNode;
import com.szyciov.touch.dto.AirPortDTO;
import com.szyciov.touch.dto.CityDTO;
import com.szyciov.touch.dto.ServiceBusinessDTO;
import com.szyciov.touch.dto.ServiceModelsDTO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Created by shikang on 2017/5/10.
 */
public interface PartnerUseCarMapper {
	
    List<CityDTO> getFullCityList();
	
	List<CityDTO> getServiceCityList(String companyid);
	
	List<AirPortDTO> getAirPortList(String cityId);
	
	List<ServiceBusinessDTO> getServiceBusiness(Map<String, Object> map);
	
	List<ServiceModelsDTO> getServiceModels(Map<String, Object> map);
	
	Map<String, Object> getOrgOrderCost(Map<String, Object> map);

    Map<String, Object> getOrgOrderByOrderno(String orderno);

    Map<String, Object> getPubDriverById(String driverid);

    List<Map<String, Object>> getSimpleOrder(Map<String, Object> param);

    PubDriver getDriverByPhone(String phone);

    void insertPubAlarmprocessSaveDto(PubAlarmprocessSaveDto object);

    List<String> getLeUserByCompanyid(String companyid);

    void updateOrgOrderPaymentStatus(OrgOrder object);

    void insertOrgOrderpaymentrecord(Map<String, String> param);

    void updateDriverRate(Map<String, String> param);

    void updateOrgOrderRate(OrgOrder object);
	OrgUser getUser4Org(String account);

	void updateUser4Org(Map<String, Object> userinfo);

	boolean hasSMSCode(Map<String, String> smscodeobj);

	void updateSMSCode(Map<String, String> smscodeobj);

	void saveSMSCode(Map<String, String> smscodeobj);

	void addLog4Org(Map<String, Object> loginfo);

	Map<String, Object> getUserTokenByUserId(Map<String, Object> pp);

	void createOrUpdateUsertoken(Map<String, Object> tokeninfo);

	Map<String, Object> getSMSInfo(Map<String, Object> params);

	void deleteSMSCode(Map<String, Object> params);

	void addAuthTimeliness(Map<String, Object> params);

	Map<String, Object> getAuthInfoByKey(String channelKey);

	boolean checkRegister4Org(@Param("phone") String phone,@Param("channelkey") String channelId);

	Map<String, Object> getChInfoByKey(String channelkey);

	void registerOrgUser(OrgUser user);

	List<TreeNode> getDeptTreeNodes(String organid);

	boolean hasResId(String resid);

	void addDept(Map<String, Object> deptInfo);
}

package com.szyciov.message;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;

import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.util.TemplateHelper4CarServiceApi;
import com.szyciov.util.TemplateHelper4leaseApi;

/**
 * 获取消息推送需要的api信息
 * @author admin
 *
 */
public class MessageAPIUtil {
	
	private static TemplateHelper4leaseApi templateHelper = new TemplateHelper4leaseApi();
	
	private static TemplateHelper4CarServiceApi carserviceTemplate = new TemplateHelper4CarServiceApi();

	public static String getDriverPhone(String driverid) {
		if(StringUtils.isBlank(driverid)){
			return null;
		}
		return carserviceTemplate.dealRequestWithToken("/MessagePubInfo/GetDriverPhoneById/{driverid}", HttpMethod.GET, null, null, String.class, driverid);
	}

	public static LeasesCompany getLeaseCompanyById(String companyid) {
		if(StringUtils.isBlank(companyid)){
			return null;
		}
		return carserviceTemplate.dealRequestWithToken("/MessagePubInfo/GetLeaseCompanyById/{companyid}", HttpMethod.GET, null, null, LeasesCompany.class, companyid);
	}

	public static Map<String, Object> getOrgUserInfo(String userid) {
		return carserviceTemplate.dealRequestWithToken("/MessagePubInfo/GetOrgUserInfo/{userid}", HttpMethod.GET, null, null, Map.class, userid);
	}

	public static Map<String, Object> getDriverInfo(String driverid) {
		if(StringUtils.isBlank(driverid)){
			return null;
		}
		return carserviceTemplate.dealRequestWithToken("/MessagePubInfo/GetDriverInfo/{driverid}", HttpMethod.GET, null, null, Map.class, driverid);
	}

	public static Map<String, Object> getCarInfo(String vehicleid) {
		if(StringUtils.isBlank(vehicleid)){
			return null;
		}
		return carserviceTemplate.dealRequestWithToken("/MessagePubInfo/GetCarInfo/{vehicleid}", HttpMethod.GET, null, null, Map.class, vehicleid);
	}

	public static Map<String,Object> getOpInfo(){
		return carserviceTemplate.dealRequestWithToken("/MessagePubInfo/GetOpInfo", HttpMethod.GET, null, null, Map.class);
	}

	public static Map<String, Object> getOpUserInfo(String userid) {
		if(StringUtils.isBlank(userid)){
			return null;
		}
		return carserviceTemplate.dealRequestWithToken("/MessagePubInfo/GetOpUserInfo/{userid}", HttpMethod.GET, null, null, Map.class, userid);
	}
}

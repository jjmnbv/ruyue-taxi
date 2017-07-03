package com.szyciov.operate.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.enums.UserTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.szyciov.entity.PubAlipayaccountHistory;
import com.szyciov.entity.PubWechataccountHistory;
import com.szyciov.enums.PayAccountStatusEnum;
import com.szyciov.enums.PlatformTypeByDb;
import com.szyciov.op.entity.OpPlatformInfo;
import com.szyciov.operate.dao.OpInformationSetDao;
import com.szyciov.util.GUIDGenerator;

@Service("OpInformationSetService")
public class OpInformationSetService {
	private OpInformationSetDao dao;

	@Resource(name = "OpInformationSetDao")
	public void setDao(OpInformationSetDao dao) {
		this.dao = dao;
	}
	
	public OpPlatformInfo getOpPlatformInfo() {
    	return dao.getOpPlatformInfo();
    }
	
	public Map<String, String> updateOpPlatformInfo(OpPlatformInfo opPlatformInfo) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		
		int infoCount = dao.getOpPlatformInfoCount();
		if (infoCount == 0) {
			String uuid = GUIDGenerator.newGUID();
			opPlatformInfo.setId(uuid);
			dao.createOpPlatformInfo(opPlatformInfo);
		} else {
			dao.updateOpPlatformInfo(opPlatformInfo);
		}

		return ret;
	}
	public OpPlatformInfo getCityName(OpPlatformInfo opPlatformInfo){
		return dao.getCityName(opPlatformInfo);
	}
	public Map<String, String> updateServcietel(OpPlatformInfo opPlatformInfo) {
		Map<String, String> ret = new HashMap<String, String>();
	   //转换城市id
		String city = dao.getCityId(opPlatformInfo.getCity());
		opPlatformInfo.setCity(city);
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		int infoCount = dao.getOpPlatformInfoCount();
		OpPlatformInfo opPlatformInfoReturn = dao.getOpPlatformInfo();
		if (infoCount == 0) {
			String uuid = GUIDGenerator.newGUID();
			opPlatformInfo.setId(uuid);
		 //插入信息表
			dao.createServcietel(opPlatformInfo);
		} else {
			dao.updateServcietel(opPlatformInfo);
		}
		int countreturn = dao.getLeLeasescompany(opPlatformInfoReturn.getId());
		if(countreturn == 0){
		 //插入租赁公司表
		  dao.insertLeLeasescompany(opPlatformInfo);
		}else{
		 //修改租赁公司表
		 dao.updateLeLeasescompany(opPlatformInfo);
		}
		return ret;
	}
	
	/**
	 * 根据条件修改运管端信息
	 * @param object
	 * @return
	 */
	public Map<String, String> editOpPlatformInfo(OpPlatformInfo object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		dao.editOpPlatformInfo(object);
		return ret;
	}
	
	/**
	 * 配置支付宝账户信息
	 * @param object
	 * @return
	 */
	public Map<String, String> updateAlipay(OpPlatformInfo object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		saveAlipay(object);
		//添加支付宝更新历史记录
		addAlipayaccountHistory(object);
		return ret;
	}

    /**
     * 配置支付宝账户信息
     * @param object
     * @return
     */
    public Map<String, String> updateDriverAlipay(OpPlatformInfo object) {
        Map<String, String> ret = new HashMap<String, String>();
        ret.put("ResultSign", "Successful");
        ret.put("MessageKey", "保存成功");
        saveDriverAlipay(object);
        //添加支付宝更新历史记录
        addDriverAlipayaccountHistory(object);
        return ret;
    }

	private void saveDriverAlipay(OpPlatformInfo object){
        //验证是否已配置运管平台信息
        OpPlatformInfo platformInfo = dao.getOpPlatformInfo();
        if(null == platformInfo) {
            object.setId(GUIDGenerator.newGUID());
            object.setDriveralipaystatus(PayAccountStatusEnum.FORBIDDEN.code);
            dao.createOpPlatformInfo(object);
        } else {
            if(StringUtils.isBlank(platformInfo.getDriveralipaystatus())) {
                object.setDriveralipaystatus(PayAccountStatusEnum.FORBIDDEN.code);
            }
            object.setId(platformInfo.getId());
            dao.editOpPlatformInfo(object);
        }
    }
	
	private void saveAlipay(OpPlatformInfo object){
        //验证是否已配置运管平台信息
        OpPlatformInfo platformInfo = dao.getOpPlatformInfo();
        if(null == platformInfo) {
            object.setId(GUIDGenerator.newGUID());
            object.setAlipaystatus(PayAccountStatusEnum.FORBIDDEN.code);
            dao.createOpPlatformInfo(object);
        } else {
            if(StringUtils.isBlank(platformInfo.getAlipaystatus())) {
                object.setAlipaystatus(PayAccountStatusEnum.FORBIDDEN.code);
            }
            object.setId(platformInfo.getId());
            dao.editOpPlatformInfo(object);
        }
    }

    private void saveWechat(OpPlatformInfo object){
        //验证是否已配置运管平台信息
        OpPlatformInfo platformInfo = dao.getOpPlatformInfo();
        if(null == platformInfo) {
            object.setId(GUIDGenerator.newGUID());
            object.setWechatstatus(PayAccountStatusEnum.FORBIDDEN.code);
            dao.createOpPlatformInfo(object);
        } else {
            if(StringUtils.isBlank(platformInfo.getWechatstatus())) {
                object.setWechatstatus(PayAccountStatusEnum.FORBIDDEN.code);
            }
            object.setId(platformInfo.getId());
            dao.editOpPlatformInfo(object);
        }
    }
    
    private void saveDriverWechat(OpPlatformInfo object){
        //验证是否已配置运管平台信息
        OpPlatformInfo platformInfo = dao.getOpPlatformInfo();
        if(null == platformInfo) {
            object.setId(GUIDGenerator.newGUID());
            object.setDriverwechatstatus(PayAccountStatusEnum.FORBIDDEN.code);
            dao.createOpPlatformInfo(object);
        } else {
            if(StringUtils.isBlank(platformInfo.getDriverwechatstatus())) {
                object.setDriverwechatstatus(PayAccountStatusEnum.FORBIDDEN.code);
            }
            object.setId(platformInfo.getId());
            dao.editOpPlatformInfo(object);
        }
    }

	
	/**
	 * 配置微信账户信息
	 * @param object
	 * @return
	 */
	public Map<String, String> updateWechat(OpPlatformInfo object) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		saveWechat(object);
		//添加微信更新历史记录
		addWechataccountHistory(object);
		return ret;
	}


    /**
     * 配置微信账户信息
     * @param object
     * @return
     */
    public Map<String, String> updateDriverWechat(OpPlatformInfo object) {
        Map<String, String> ret = new HashMap<String, String>();
        ret.put("ResultSign", "Successful");
        ret.put("MessageKey", "保存成功");
        saveDriverWechat(object);
        //添加微信更新历史记录
        addDriverWechataccountHistory(object);
        return ret;
    }
	
	/**
	 * 添加支付宝更新历史记录
	 * @param object
	 */
	private void addAlipayaccountHistory(OpPlatformInfo object) {
		PubAlipayaccountHistory alipayaccountHistory = new PubAlipayaccountHistory();
		alipayaccountHistory.setId(GUIDGenerator.newGUID());
		alipayaccountHistory.setCompanyid(object.getId());
		alipayaccountHistory.setAlipayaccount(object.getAliPayAccount());
		alipayaccountHistory.setAlipayappid(object.getAliPayAppId());
		alipayaccountHistory.setAlipayprivatekey(object.getAliPayPrivateKey());
		alipayaccountHistory.setAlipaypublickey(object.getAliPayPublicKey());
		alipayaccountHistory.setAlipaypartnerid(object.getAliPayPartnerId());
		alipayaccountHistory.setAlipaypartnerpublickey(object.getAliPayPartnerPublicKey());
		alipayaccountHistory.setAlipaypartnerprivatekey(object.getAliPayPartnerPrivateKey());
		alipayaccountHistory.setCreater(object.getUpdater());
		alipayaccountHistory.setUpdater(object.getUpdater());
        alipayaccountHistory.setUsertype(UserTypeEnum.PASSENGER.value);
		alipayaccountHistory.setPlatformtype(Integer.valueOf(PlatformTypeByDb.OPERATING.code));
		dao.insertAlipayAccountHistory(alipayaccountHistory);
	}

    /**
     * 添加支付宝更新历史记录
     * @param object
     */
    private void addDriverAlipayaccountHistory(OpPlatformInfo object) {
        PubAlipayaccountHistory alipayaccountHistory = new PubAlipayaccountHistory();
        alipayaccountHistory.setId(GUIDGenerator.newGUID());
        alipayaccountHistory.setCompanyid(object.getId());
        alipayaccountHistory.setAlipayaccount(object.getDriveralipayaccount());
        alipayaccountHistory.setAlipayappid(object.getDriveralipayappid());
        alipayaccountHistory.setAlipayprivatekey(object.getDriveralipayprivatekey());
        alipayaccountHistory.setAlipaypublickey(object.getDriveralipaypublickey());
        alipayaccountHistory.setAlipaypartnerid(object.getDriveralipaypartnerid());
        alipayaccountHistory.setAlipaypartnerpublickey(object.getDriveralipaypartnerpublickey());
        alipayaccountHistory.setAlipaypartnerprivatekey(object.getDriveralipaypartnerprivatekey());
        alipayaccountHistory.setCreater(object.getUpdater());
        alipayaccountHistory.setUpdater(object.getUpdater());
        alipayaccountHistory.setUsertype(UserTypeEnum.DRIVER.value);
        alipayaccountHistory.setPlatformtype(Integer.valueOf(PlatformTypeByDb.OPERATING.code));
        dao.insertAlipayAccountHistory(alipayaccountHistory);
    }
	
	/**
	 * 添加微信更新历史记录
	 * @param object
	 */
	public void addWechataccountHistory(OpPlatformInfo object) {
		PubWechataccountHistory wechataccountHistory = new PubWechataccountHistory();
		wechataccountHistory.setId(GUIDGenerator.newGUID());
		wechataccountHistory.setCompanyid(object.getId());
		wechataccountHistory.setWechataccount(object.getWechatPayAccount());
		wechataccountHistory.setWechatappid(object.getWechatAppId());
		wechataccountHistory.setWechatmerchantno(object.getWechatMerchantNo());
		wechataccountHistory.setWechatsecretkey(object.getWechatSecretKey());
		wechataccountHistory.setCreater(object.getUpdater());
		wechataccountHistory.setUpdater(object.getUpdater());
		wechataccountHistory.setPlatformtype(Integer.valueOf(PlatformTypeByDb.OPERATING.code));
        wechataccountHistory.setUsertype(UserTypeEnum.PASSENGER.value);
		dao.insertWechatAccountHistory(wechataccountHistory);
	}

    /**
     * 添加微信更新历史记录
     * @param object
     */
    public void addDriverWechataccountHistory(OpPlatformInfo object) {
        PubWechataccountHistory w = new PubWechataccountHistory();
        w.setId(GUIDGenerator.newGUID());
        w.setCompanyid(object.getId());
        w.setWechataccount(object.getDriverwechatpayaccount());
        w.setWechatappid(object.getDriverwechatappid());
        w.setWechatmerchantno(object.getDriverwechatmerchantno());
        w.setWechatsecretkey(object.getDriverwechatsecretkey());
        w.setCreater(object.getUpdater());
        w.setUpdater(object.getUpdater());
        w.setPlatformtype(Integer.valueOf(PlatformTypeByDb.OPERATING.code));
        w.setUsertype(UserTypeEnum.DRIVER.value);
        dao.insertWechatAccountHistory(w);
    }

}

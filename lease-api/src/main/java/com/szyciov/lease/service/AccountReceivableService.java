package com.szyciov.lease.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.szyciov.enums.UserTypeEnum;
import org.springframework.stereotype.Service;

import com.szyciov.entity.PubAlipayaccountHistory;
import com.szyciov.entity.PubWechataccountHistory;
import com.szyciov.lease.dao.AccountReceivableDao;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.util.GUIDGenerator;

@Service("AccountReceivableService")
public class AccountReceivableService {
	private AccountReceivableDao dao;
	@Resource(name = "AccountReceivableDao")
	public void setDao(AccountReceivableDao dao) {
		this.dao = dao;
	}
	public Map<String, String> updateWechat(LeLeasescompany leLeasescompany){
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
//		leLeasescompany.setAlipaystatus("1");
		dao.updateWechat(leLeasescompany);
		PubWechataccountHistory p = new PubWechataccountHistory();
		p.setId(GUIDGenerator.newGUID());
		p.setCompanyid(leLeasescompany.getId());
		p.setWechataccount(leLeasescompany.getWechatAccount());
		p.setWechatappid(leLeasescompany.getWechatappid());
		p.setWechatmerchantno(leLeasescompany.getWechatmerchantno());
		p.setWechatsecretkey(leLeasescompany.getWechatsecretkey());
		p.setUpdater(leLeasescompany.getId());
		p.setCreater(leLeasescompany.getId());
        p.setUsertype(UserTypeEnum.PASSENGER.value);
		addWechat(p);
		return ret;
	};
	
	public Map<String, String> updateAlipay(LeLeasescompany leLeasescompany){
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
//		leLeasescompany.setWechatstatus("1");
		dao.updateAlipay(leLeasescompany);
		PubAlipayaccountHistory p = new PubAlipayaccountHistory();
		p.setId(GUIDGenerator.newGUID());
		p.setCompanyid(leLeasescompany.getId());
		p.setAlipayaccount(leLeasescompany.getAlipayAccount());
		p.setAlipayappid(leLeasescompany.getAlipayappid());
		p.setAlipaypartnerid(leLeasescompany.getAlipaypartnerid());
		p.setAlipaypartnerprivatekey(leLeasescompany.getAlipaypartnerprivatekey());
		p.setAlipaypartnerpublickey(leLeasescompany.getAlipaypartnerpublickey());
		p.setAlipayprivatekey(leLeasescompany.getAlipayprivatekey());
		p.setAlipaypublickey(leLeasescompany.getAlipaypublickey());
		p.setUpdater(leLeasescompany.getUpdater());
		p.setCreater(leLeasescompany.getUpdater());
		p.setUsertype(UserTypeEnum.PASSENGER.value);
		addAlipay(p);
		return ret;
	}

    public Map<String, String> updateDriverAlipay(LeLeasescompany leLeasescompany){
        Map<String, String> ret = new HashMap<String, String>();
        ret.put("ResultSign", "Successful");
        ret.put("MessageKey", "保存成功");
        dao.updateDriverAlipay(leLeasescompany);
        PubAlipayaccountHistory p = new PubAlipayaccountHistory();
        p.setId(GUIDGenerator.newGUID());
        p.setCompanyid(leLeasescompany.getId());
        p.setAlipayaccount(leLeasescompany.getDriveralipayaccount());
        p.setAlipayappid(leLeasescompany.getDriveralipayappid());
        p.setAlipaypartnerid(leLeasescompany.getDriveralipaypartnerid());
        p.setAlipaypartnerprivatekey(leLeasescompany.getDriveralipaypartnerprivatekey());
        p.setAlipaypartnerpublickey(leLeasescompany.getDriveralipaypartnerpublickey());
        p.setAlipayprivatekey(leLeasescompany.getDriveralipayprivatekey());
        p.setAlipaypublickey(leLeasescompany.getDriveralipaypublickey());
        p.setUpdater(leLeasescompany.getUpdater());
        p.setCreater(leLeasescompany.getUpdater());
        p.setUsertype(UserTypeEnum.DRIVER.value);
        addAlipay(p);
        return ret;
    }

    public Map<String, String> updateDriverWechat(LeLeasescompany leLeasescompany){
        Map<String, String> ret = new HashMap<String, String>();
        ret.put("ResultSign", "Successful");
        ret.put("MessageKey", "保存成功");
        dao.updateDriverWechat(leLeasescompany);
        PubWechataccountHistory p = new PubWechataccountHistory();
        p.setId(GUIDGenerator.newGUID());
        p.setCompanyid(leLeasescompany.getId());
        p.setWechataccount(leLeasescompany.getDriverwechatpayaccount());
        p.setWechatappid(leLeasescompany.getDriverwechatappid());
        p.setWechatmerchantno(leLeasescompany.getDriverwechatmerchantno());
        p.setWechatsecretkey(leLeasescompany.getDriverwechatsecretkey());
        p.setUpdater(leLeasescompany.getUpdater());
        p.setCreater(leLeasescompany.getUpdater());
        p.setUsertype(UserTypeEnum.DRIVER.value);
        addWechat(p);
        return ret;
    };
	
	public void addWechat(PubWechataccountHistory pubWechataccountHistory){
		dao.addWechat(pubWechataccountHistory);
	};
	
	public void addAlipay(PubAlipayaccountHistory pubAlipayaccountHistory){
		dao.addAlipay(pubAlipayaccountHistory);
	};
	
	public void openOrCloseAlipay(LeLeasescompany leLeasescompany){
		dao.openOrCloseAlipay(leLeasescompany);
	};
	
	public void openOrCloseWechat(LeLeasescompany leLeasescompany){
		dao.openOrCloseWechat(leLeasescompany);
	};

    public void openOrCloseDriverAlipay(LeLeasescompany leLeasescompany){
        dao.openOrCloseDriverAlipay(leLeasescompany);
    }

    public void openOrCloseDriverWechat(LeLeasescompany leLeasescompany){
        dao.openOrCloseDriverWechat(leLeasescompany);
    }
}

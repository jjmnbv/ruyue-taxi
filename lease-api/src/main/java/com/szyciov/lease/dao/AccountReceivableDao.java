package com.szyciov.lease.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.entity.PubWechataccountHistory;
import com.szyciov.entity.PubAlipayaccountHistory;
import com.szyciov.lease.entity.LeLeasescompany;
import com.szyciov.lease.entity.User;
import com.szyciov.lease.mapper.AccountReceivableMapper;
import com.szyciov.op.entity.LeCompanyAgreement;
import com.szyciov.op.entity.OpUser;

@Repository("AccountReceivableDao")
public class AccountReceivableDao {
	public AccountReceivableDao() {
	}

	private AccountReceivableMapper mapper;

	@Resource
	public void setMapper(AccountReceivableMapper mapper) {
		this.mapper = mapper;
	}

	public void updateWechat(LeLeasescompany leLeasescompany){
		mapper.updateWechat(leLeasescompany);
	};

	public void updateAlipay(LeLeasescompany leLeasescompany){
		mapper.updateAlipay(leLeasescompany);
	};


	public void updateDriverWechat(LeLeasescompany leLeasescompany){
		mapper.updateDriverWechat(leLeasescompany);
	}

	public void updateDriverAlipay(LeLeasescompany leLeasescompany){
		mapper.updateDriverAlipay(leLeasescompany);
	}


	public void addAlipay(PubAlipayaccountHistory pubWechataccountHistory){
		mapper.addAlipay(pubWechataccountHistory);
	};

	public void addWechat(PubWechataccountHistory pubAlipayaccountHistory){
		mapper.addWechat(pubAlipayaccountHistory);
	};

	public void openOrCloseAlipay(LeLeasescompany leLeasescompany){
		mapper.openOrCloseAlipay(leLeasescompany);
	};

	public void openOrCloseWechat(LeLeasescompany leLeasescompany){
		mapper.openOrCloseWechat(leLeasescompany);
	};


	public void openOrCloseDriverWechat(LeLeasescompany leLeasescompany){
		mapper.openOrCloseDriverWechat(leLeasescompany);
	}

	public void openOrCloseDriverAlipay(LeLeasescompany leLeasescompany){
		mapper.openOrCloseDriverAlipay(leLeasescompany);
	}
}

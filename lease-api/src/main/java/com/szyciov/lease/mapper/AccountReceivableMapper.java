package com.szyciov.lease.mapper;

import com.szyciov.entity.PubWechataccountHistory;
import com.szyciov.entity.PubAlipayaccountHistory;
import com.szyciov.lease.entity.LeLeasescompany;

public interface AccountReceivableMapper {
    void updateWechat(LeLeasescompany leLeasescompany);

    void updateAlipay(LeLeasescompany leLeasescompany);

    void updateDriverWechat(LeLeasescompany leLeasescompany);

    void updateDriverAlipay(LeLeasescompany leLeasescompany);

    void addWechat(PubWechataccountHistory pubAlipayaccountHistory);

    void addAlipay(PubAlipayaccountHistory pubWechataccountHistory);

    void openOrCloseAlipay(LeLeasescompany leLeasescompany);

    void openOrCloseWechat(LeLeasescompany leLeasescompany);

    void openOrCloseDriverAlipay(LeLeasescompany leLeasescompany);

    void openOrCloseDriverWechat(LeLeasescompany leLeasescompany);
}
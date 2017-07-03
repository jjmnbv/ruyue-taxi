package com.szyciov.carservice.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.PubWithDrawDao;
import com.szyciov.entity.OrderSource4WithdrawNO;
import com.szyciov.entity.WithDrawNO;

@Service("pubWithDrawService")
public class PubWithDrawService {
	public PubWithDrawDao pubWithDrawDao;

	@Resource(name="pubWithDrawDao")
	public void setPubWithDrawDao(PubWithDrawDao pubWithDrawDao) {
		this.pubWithDrawDao = pubWithDrawDao;
	}
	
	/**
	 * 获取提现编号
	 * @param organ
	 * @return
	 */
	public String getPubWithDrawNo(OrderSource4WithdrawNO orderSource4WithdrawNO) {
		if(!WithDrawNO.hasInstance()) {
			String pubWithDrawNo = pubWithDrawDao.getMaxPubWithDrawNo();
			WithDrawNO.getInstance().init(pubWithDrawNo);
		} else {
			WithDrawNO.getInstance().init(WithDrawNO.getInstance().getCurrentWithDrawNo());
		}

		return WithDrawNO.getInstance().getOrderNO(orderSource4WithdrawNO);
	}
}

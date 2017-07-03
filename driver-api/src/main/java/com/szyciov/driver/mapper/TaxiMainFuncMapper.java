package com.szyciov.driver.mapper;

import com.szyciov.driver.entity.OrderStatistics;
import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.driver.model.PassengersPayDetail;
import com.szyciov.driver.param.DriverMessageParam;
import com.szyciov.driver.param.PurseParam;
import com.szyciov.entity.PubDriver;
import com.szyciov.entity.PubDriverTradingrecord;
import com.szyciov.lease.entity.LeUserNews;
import com.szyciov.lease.entity.PubDriverAccount;
import com.szyciov.op.entity.OpUserNews;
import com.szyciov.op.entity.PeUserExpenses;
import com.szyciov.op.entity.PubDriverExpenses;
import com.szyciov.org.entity.PubWithdraw;

import java.util.List;

/**
  * @ClassName TaxiMainFuncMapper
  * @author Efy Shu
  * @Description 出租车侧边栏功能Mapper
  * @date 2017年3月23日 09:34:23
  */
public interface TaxiMainFuncMapper{
	public PubDriverAccount getPurse(PubDriver param);
	public void saveOrUpdatePubDriverAccount(PubDriverAccount param);
	public void savePubDriverExpenses(PubDriverExpenses param);
	public List<PubDriverExpenses> getPurseExpenses(PurseParam param);
	public void savePubWithDraw(PubWithdraw param);
	public PubWithdraw getLastBankCard(PubWithdraw param);
	public double getTodayWithDrawLimit(PubWithdraw param);
	public List<OrderStatistics> getTaxiOrderStatistics(PurseParam param);
	public boolean checkAliPayAccount(PubDriver param);
	public boolean checkWeChatAccount(PubDriver param);
	public void saveDriverTradeRecord(PubDriverTradingrecord param);
	public PubDriverTradingrecord getDriverTradeRecord(PubDriverTradingrecord param);
	public List<PubDriverNews> getDriverMessageByType(DriverMessageParam param);
	public PubDriverNews getDriverMessageById(DriverMessageParam param);
    void savePeUserExpenses(PeUserExpenses peUserExpenses);
    PassengersPayDetail getOpTaxiOrderByOrderNo(String orderNo);
    List<String> getSystemNewsLeUser(String leCompanyId);
    void saveLeUserNews(LeUserNews news);
    List<String> getLeUserIds(String companyId);
    void saveOpUsersNews(OpUserNews news);
    List<String> getOpUserIds();
}

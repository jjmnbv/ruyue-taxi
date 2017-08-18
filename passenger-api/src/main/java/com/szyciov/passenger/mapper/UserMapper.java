package com.szyciov.passenger.mapper;

import java.util.List;
import java.util.Map;

import com.szyciov.op.entity.PeUser;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.MostAddress;
import com.szyciov.passenger.entity.MostContact;

public interface UserMapper {

	void saveSMSCode(Map<String, String> smscodeobj);

	boolean hasSMSCode(Map<String, String> smscodeobj);

	void updateSMSCode(Map<String, String> smscodeobj);

	Map<String, Object> getSMSInfo(Map<String, Object> params);

	OrgUser getUser4Org(String account);

	PeUser getUser4Op(String account);

	void addLog4Org(Map<String, Object> loginfo);

	void addLog4Pe(Map<String, Object> loginfo);

	void registerPeUser(PeUser peuser);

	List<LeasesCompany> getValiableLeasesCompanys(Map<String,Object> params);

	List<MostContact> getMostContact4Org(String account);

	List<MostContact> getMostContact4Op(String account);

	void addMostContact4Org(Map<String, Object> params);

	void addMostContact4Op(Map<String, Object> params);

	void deleteMostContact4Org(String contactid);

	void deleteMostContact4Op(String contactid);

	void updateUser4Org(Map<String, Object> userinfo);

	void updateUser4Op(Map<String, Object> userinfo);

	double getUserBalance4Org(Map<String, Object> param);

	double getBalanceMoney4Org(Map<String, Object> param);

	List<Map<String, Object>> getBalanceDetail4Org(Map<String, Object> param);

	List<Map<String, Object>> getMessages4Org(Map<String, Object> params);

	void updateMessageState4Org(Map<String, Object> params);

	List<MostAddress> getMostAddress4Org(Map<String, Object> params);

	void deleteMostAddress4Org(String addressid);

	void addMostAddress4Org(Map<String, Object> params);

	void updatePwd4Org(Map<String, Object> params);

	Object getOrgBalance(Map<String, Object> param);

	Map<String,Object> getUserTokenByUserId(Map<String, Object> pp);

	void createOrUpdateUsertoken(Map<String, Object> tokeninfo);

	Map<String, Object> getPayAccounts4Org(String companyid);

	double getUserBalance4Op(String userid);

	Map<String, Object> getPayAccounts4Op();

	double getBalanceMoney4Op(Map<String, Object> param);

	List<Map<String, Object>> getBalanceDetail4Op(Map<String, Object> param);

	List<Map<String, Object>> getMessages4Op(Map<String, Object> params);

	void updateMessageStateOp(Map<String, Object> params);

	List<MostAddress> getMostAddress4Op(Map<String, Object> params);

	void deleteMostAddress4Op(String addressid);

	void addMostAddress4Op(Map<String, Object> params);

	void updatePwd4Op(Map<String, Object> params);

	boolean hasRegister(String phone);

	void readMessageAll4Org(String account);

	void readMessageAll4Op(String account);

	void updateUserToken(String usertoken);

	Map<String, Object> getUserLastDisableInfo4Op(Map<String,Object> params);

	boolean hasContact4Org(Map<String, Object> params);

	boolean hasContact4Op(Map<String, Object> params);

	void addExpenses4Op(Map<String, Object> expenses);

	boolean hasAddress4Org(Map<String, Object> params);

	boolean hasAddress4Op(Map<String, Object> params);

	List<LeasesCompany> getSelfCompanys(Map<String, Object> pprm);

	List<LeasesCompany> getTocCompanys();

	List<Map<String, Object>> getTocCompanys4Op();

	void updateCashPwd4Org(Map<String, Object> param);

	void updateCashPwd4Op(Map<String, Object> param);

	List<Map<String, Object>> getDealDetail4Org(Map<String, Object> param);

	List<Map<String, Object>> getDealDetail4Op(Map<String, Object> param);

	List<Map<String, Object>> getBalanceDetail4OrgSec(Map<String, Object> param);

	List<Map<String, Object>> getBalanceDetail4OpSec(Map<String, Object> param);

	void applyCash4Org(Map<String, Object> param);

	void applyCash4Op(Map<String, Object> param);

	void updateUserBalance4OrgSec(Map<String, Object> param);

	void updateUserBalance4OpSec(Map<String, Object> param);

	void addExpenses4OrgSec(Map<String, Object> param);

	void addExpenses4OpSec(Map<String, Object> param);

	Map<String, Object> getLastBankInfo4Org(Map<String, Object> param);

	Map<String, Object> getLastBankInfo4Op(Map<String, Object> param);

	double getApplyCash4OrgToday(Map<String, Object> param);

	double getApplyCash4OpToday(Map<String, Object> param);

	void updatePwdAWd4Org(Map<String, Object> params);

	void updatePwdAWd4Op(Map<String, Object> params);

	void addTradingrecord4Op(Map<String, Object> tradeparam);

	void addTradingrecord4Org(Map<String, Object> tradeparam);

	Map<String, Object> getTradeRecord4Op(String out_trade_no);

	void updateUserTradeRecord4Op(Map<String, Object> tradeparam);

	Map<String, Object> getTradeRecord4Org(String out_trade_no);

	void updateUserTradeRecord4Org(Map<String, Object> tradeparam);

	List<Map<String, Object>> getMessages4OrgSec(Map<String, Object> param);

	List<Map<String, Object>> getMessages4OpSec(Map<String, Object> param);

	void deleteSMSCode(Map<String, Object> params);

	PeUser getPeUserById(String userid);

	OrgUser getOrgUserById(String userid);

    Map<String,Object> getTokenInfo(String usertoken);

	void updateRegisterId4Org(Map<String, Object> updateparams);

	void updateRegisterId4Op(Map<String, Object> updateparams);

    List<Map<String,Object>> getMessages(Map<String, Object> params);

    Map<String,Object> getDriverBalance(Map<String, Object> params);

	void createDriverBalanceInfo(Map<String, Object> params);

	void updateDriverBalanceInfo(Map<String, Object> params);

	void addDriverExpenses(Map<String, Object> infolog);

    List<Map<String,Object>> getDealDetail4OpThird(Map<String, Object> param);

	List<Map<String,Object>> getBalanceDetail4OpThird(Map<String, Object> param);

	int getCouponCount(Map<String, Object> params);

	List<Map<String, Object>> getCouponDetail(Map<String, Object> params);

	List<Map<String, Object>> getCouponUseCitys(String couponid);

	Map<String, Object> getInviteInfoByInvitee(String inviteephone);

	void updateExpireInviteInfos();

	void addInviteInfo(Map<String, Object> pp);

	void updateInviteState(Map<String, Object> params);

	List<Map<String, Object>> getTocCompanysNew();

	List<String> getValiableCompanys(Map<String, Object> params);

	List<Map<String, Object>> getOrganServiceCompany(Map<String, Object> params);

	List<Map<String, Object>> getAbleCoupons(Map<String, Object> params);

	Map<String, Object> getOrderCouponUseInfo(Map<String, Object> params);

	Map<String, Object> getCouponInfo(String couponid);

	void addCouponUseOrder(Map<String, Object> usecouponp);

	void updateCouponUseOrder(Map<String, Object> usecouponp);

	void doComplaint(Map<String, Object> params);

	Map<String, Object> getComplaintbyOrderno(String orderno);

	Map<String, Object> getHighestAbleCoupon(Map<String, Object> couponp);

	void updateCouponDiscountamount(Map<String, Object> useinfo);

	void addCouponDetail(Map<String, Object> params);

	void useCoupon(Map<String, Object> useinfo);

	void deleteUseCouponOrder(Map<String, Object> usecouponp);
}
package com.szyciov.passenger.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.szyciov.op.entity.PeUser;
import com.szyciov.org.entity.OrgUser;
import com.szyciov.passenger.entity.LeasesCompany;
import com.szyciov.passenger.entity.MostAddress;
import com.szyciov.passenger.entity.MostContact;
import com.szyciov.passenger.mapper.UserMapper;

@Repository("UserDao")
public class UserDao {

	private UserMapper mapper;

	@Resource
	public void setMapper(UserMapper mapper) {
		this.mapper = mapper;
	}

	public void saveSMSCode(Map<String, String> smscodeobj) {
		mapper.saveSMSCode(smscodeobj);
	}

	public boolean hasSMSCode(Map<String, String> smscodeobj) {
		return mapper.hasSMSCode(smscodeobj);
	}

	public void updateSMSCode(Map<String, String> smscodeobj) {
		mapper.updateSMSCode(smscodeobj);
	}

	public Map<String, Object> getSMSInfo(Map<String, Object> params) {
		return mapper.getSMSInfo(params);
	}

	public void addLog4Org(Map<String, Object> loginfo) {
		mapper.addLog4Org(loginfo);
	}

	public void addLog4Pe(Map<String, Object> loginfo) {
		mapper.addLog4Pe(loginfo);
	}

	public void registerPeUser(PeUser peuser) {
		mapper.registerPeUser(peuser);
	}

	public OrgUser getUser4Org(String account) {
		return mapper.getUser4Org(account);
	}

	public PeUser getUser4Op(String account) {
		return mapper.getUser4Op(account);
	}

	/**
	 * 判断考虑机构用户是否已经离职
	 * @param account
	 * @return
	 */
	public List<LeasesCompany> getValiableLeasesCompanys(Map<String,Object> params) {
		return mapper.getValiableLeasesCompanys(params);
	}

	public List<MostContact> getMostContact4Op(String account) {
		return mapper.getMostContact4Op(account);
	}

	public List<MostContact> getMostContact4Org(String account) {
		return mapper.getMostContact4Org(account);
	}

	public void addMostContact4Org(Map<String, Object> params) {
		mapper.addMostContact4Org(params);
	}

	public void addMostContact4Op(Map<String, Object> params) {
		mapper.addMostContact4Op(params);
	}

	public void deleteMostContact4Org(String contactid) {
		mapper.deleteMostContact4Org(contactid);
	}

	public void deleteMostContact4Op(String contactid) {
		mapper.deleteMostContact4Op(contactid);
	}

	public void updateUser4Org(Map<String, Object> userinfo) {
		mapper.updateUser4Org(userinfo);
	}

	public void updateUser4Op(Map<String, Object> userinfo) {
		mapper.updateUser4Op(userinfo);
	}

	public double getUserBalance4Org(Map<String, Object> param) {
		return mapper.getUserBalance4Org(param);
	}

	public double getUserBalance4Op(String userid) {
		return mapper.getUserBalance4Op(userid);
	}

	public Map<String, Object> getPayAccounts4Org(String companyid) {
		return mapper.getPayAccounts4Org(companyid);
	}

	public Map<String, Object> getPayAccounts4Op() {
		return mapper.getPayAccounts4Op();
	}

	public double getBalanceMoney4Org(Map<String, Object> param) {
		return mapper.getBalanceMoney4Org(param);
	}

	public List<Map<String, Object>> getBalanceDetail4Org(Map<String, Object> param) {
		return mapper.getBalanceDetail4Org(param);
	}

	public double getBalanceMoney4Op(Map<String, Object> param) {
		return mapper.getBalanceMoney4Op(param);
	}

	public List<Map<String, Object>> getBalanceDetail4Op(Map<String, Object> param) {
		return mapper.getBalanceDetail4Op(param);
	}

	public List<Map<String, Object>> getMessages4Op(Map<String, Object> params) {
		return mapper.getMessages4Op(params);
	}

	public List<Map<String, Object>> getMessages4Org(Map<String, Object> params) {
		return mapper.getMessages4Org(params);
	}

	public void updateMessageState4Org(Map<String, Object> params) {
		mapper.updateMessageState4Org(params);
	}

	public void updateMessageStateOp(Map<String, Object> params) {
		mapper.updateMessageStateOp(params);
	}

	public List<MostAddress> getMostAddress4Org(Map<String, Object> params) {
		return mapper.getMostAddress4Org(params);
	}

	public List<MostAddress> getMostAddress4Op(Map<String, Object> params) {
		return mapper.getMostAddress4Op(params);
	}

	public void deleteMostAddress4Org(String addressid) {
		mapper.deleteMostAddress4Org(addressid);
	}

	public void deleteMostAddress4Op(String addressid) {
		mapper.deleteMostAddress4Op(addressid);
	}

	public void addMostAddress4Org(Map<String, Object> params) {
		mapper.addMostAddress4Org(params);
	}

	public void addMostAddress4Op(Map<String, Object> params) {
		mapper.addMostAddress4Op(params);
	}

	public void updatePwd4Org(Map<String, Object> params) {
		mapper.updatePwd4Org(params);
	}

	public void updatePwd4Op(Map<String, Object> params) {
		mapper.updatePwd4Op(params);
	}

	public Object getOrgBalance(Map<String, Object> param) {
		return mapper.getOrgBalance(param);
	}

	public Map<String,Object> getUserTokenByUserId(Map<String, Object> pp) {
		return mapper.getUserTokenByUserId(pp);
	}

	public void createOrUpdateUsertoken(Map<String, Object> tokeninfo) {
		mapper.createOrUpdateUsertoken(tokeninfo);
	}

	public boolean hasRegister(String phone) {
		return mapper.hasRegister(phone);
	}

	public void readMessageAll4Org(String account) {
		 mapper.readMessageAll4Org(account);
	}

	public void readMessageAll4Op(String account) {
		 mapper.readMessageAll4Op(account);
	}

	public void updateUserToken(String usertoken) {
		 mapper.updateUserToken(usertoken);
	}

	public Map<String, Object> getUserLastDisableInfo4Op(Map<String,Object> params) {
		return mapper.getUserLastDisableInfo4Op(params);
	}

	public boolean hasContact4Org(Map<String, Object> params) {
		return mapper.hasContact4Org(params);
	}

	public boolean hasContact4Op(Map<String, Object> params) {
		return mapper.hasContact4Op(params);
	}

	public boolean hasAddress4Org(Map<String, Object> params) {
		return mapper.hasAddress4Org(params);
	}

	public boolean hasAddress4Op(Map<String, Object> params) {
		return mapper.hasAddress4Op(params);
	}

	public List<LeasesCompany> getSelfCompanys(Map<String, Object> pprm) {
		return mapper.getSelfCompanys(pprm);
	}

	public List<LeasesCompany> getTocCompanys() {
		return mapper.getTocCompanys();
	}

	public List<Map<String, Object>> getTocCompanys4Op() {
		return mapper.getTocCompanys4Op();
	}

	public void updateCashPwd4Org(Map<String, Object> param) {
		mapper.updateCashPwd4Org(param);
	}

	public void updateCashPwd4Op(Map<String, Object> param) {
		mapper.updateCashPwd4Op(param);
	}

	public List<Map<String, Object>> getDealDetail4Org(Map<String, Object> param) {
		return mapper.getDealDetail4Org(param);
	}

	public List<Map<String, Object>> getDealDetail4Op(Map<String, Object> param) {
		return mapper.getDealDetail4Op(param);
	}

	public List<Map<String, Object>> getBalanceDetail4OrgSec(Map<String, Object> param) {
		return mapper.getBalanceDetail4OrgSec(param);
	}

	public List<Map<String, Object>> getBalanceDetail4OpSec(Map<String, Object> param) {
		return mapper.getBalanceDetail4OpSec(param);
	}

	public void applyCash4Org(Map<String, Object> param) {
		mapper.applyCash4Org(param);
	}

	public void applyCash4Op(Map<String, Object> param) {
		mapper.applyCash4Op(param);
	}

	public void updateUserBalance4OrgSec(Map<String, Object> param) {
		mapper.updateUserBalance4OrgSec(param);
	}

	public void updateUserBalance4OpSec(Map<String, Object> param) {
		mapper.updateUserBalance4OpSec(param);
	}

	public void addExpenses4OrgSec(Map<String, Object> param) {
		mapper.addExpenses4OrgSec(param);
	}

	public void addExpenses4OpSec(Map<String, Object> param) {
		mapper.addExpenses4OpSec(param);
	}

	public Map<String, Object> getLastBankInfo4Org(Map<String, Object> param) {
		return mapper.getLastBankInfo4Org(param);
	}

	public Map<String, Object> getLastBankInfo4Op(Map<String, Object> param) {
		return mapper.getLastBankInfo4Op(param);
	}

	public double getApplyCash4OrgToday(Map<String, Object> param) {
		return mapper.getApplyCash4OrgToday(param);
	}

	public double getApplyCash4OpToday(Map<String, Object> param) {
		return mapper.getApplyCash4OpToday(param);
	}

	public void updatePwdAWd4Org(Map<String, Object> params) {
		mapper.updatePwdAWd4Org(params);
	}

	public void updatePwdAWd4Op(Map<String, Object> params) {
		mapper.updatePwdAWd4Op(params);
	}

	public void addTradingrecord4Op(Map<String, Object> tradeparam) {
		mapper.addTradingrecord4Op(tradeparam);
	}

	public void addTradingrecord4Org(Map<String, Object> tradeparam) {
		mapper.addTradingrecord4Org(tradeparam);
	}

	public Map<String, Object> getTradeRecord4Op(String out_trade_no) {
		return mapper.getTradeRecord4Op(out_trade_no);
	}

	public void updateUserTradeRecord4Op(Map<String, Object> tradeparam) {
		mapper.updateUserTradeRecord4Op(tradeparam);
	}

	public Map<String, Object> getTradeRecord4Org(String out_trade_no) {
		return mapper.getTradeRecord4Org(out_trade_no);
	}

	public void updateUserTradeRecord4Org(Map<String, Object> tradeparam) {
		mapper.updateUserTradeRecord4Org(tradeparam);
	}

	public List<Map<String, Object>> getMessages4OrgSec(Map<String, Object> param) {
		return mapper.getMessages4OrgSec(param);
	}

	public List<Map<String, Object>> getMessages4OpSec(Map<String, Object> param) {
		return mapper.getMessages4OpSec(param);
	}

	/**
	 * 删除验证码
	 * @param params
	 */
	public void deleteSMSCode(Map<String, Object> params) {
		mapper.deleteSMSCode(params);
	}

	public PeUser getPeUserById(String userid) {
		return mapper.getPeUserById(userid);
	}

	public OrgUser getOrgUserById(String userid) {
		return mapper.getOrgUserById(userid);
	}

    public Map<String,Object> getTokenInfo(String usertoken) {
		return mapper.getTokenInfo(usertoken);
    }

    public void updateRegisterId4Org(Map<String, Object> updateparams) {
		mapper.updateRegisterId4Org(updateparams);
    }

	public void updateRegisterId4Op(Map<String, Object> updateparams) {
		mapper.updateRegisterId4Op(updateparams);
	}

    public List<Map<String,Object>> getMessages(Map<String, Object> params) {
		return mapper.getMessages(params);
    }

    public Map<String,Object> getDriverBalance(Map<String, Object> params) {
		return mapper.getDriverBalance(params);
    }

	public void createDriverBalanceInfo(Map<String, Object> params) {
		mapper.createDriverBalanceInfo(params);
	}

	public void updateDriverBalanceInfo(Map<String, Object> params) {
		mapper.updateDriverBalanceInfo(params);
	}

	public void addDriverExpenses(Map<String, Object> infolog) {
		mapper.addDriverExpenses(infolog);
	}

    public List<Map<String,Object>> getDealDetail4OpThird(Map<String, Object> param) {
		return mapper.getDealDetail4OpThird(param);
    }

	public List<Map<String,Object>> getBalanceDetail4OpThird(Map<String, Object> param) {
		return mapper.getBalanceDetail4OpThird(param);
	}

	public int getCouponCount(Map<String, Object> params) {
		return mapper.getCouponCount(params);
	}

	public List<Map<String, Object>> getCouponDetail(Map<String, Object> params) {
		return mapper.getCouponDetail(params);
	}

	public List<Map<String, Object>> getCouponUseCitys(String couponid) {
		return mapper.getCouponUseCitys(couponid);
	}

	public Map<String, Object> getInviteInfoByInvitee(String inviteephone) {
		return mapper.getInviteInfoByInvitee(inviteephone);
	}

	public void updateExpireInviteInfos() {
		mapper.updateExpireInviteInfos();
	}

	public void addInviteInfo(Map<String, Object> pp) {
		mapper.addInviteInfo(pp);
	}

	public void updateInviteState(Map<String, Object> params) {
		mapper.updateInviteState(params);
	}

	public List<Map<String, Object>> getTocCompanysNew() {
		return mapper.getTocCompanysNew();
	}

	public List<String> getValiableCompanys(Map<String, Object> params) {
		return mapper.getValiableCompanys(params);
	}

	public List<Map<String, Object>> getOrganServiceCompany(Map<String, Object> params) {
		return mapper.getOrganServiceCompany(params);
	}

	public List<Map<String, Object>> getAbleCoupons(Map<String, Object> params) {
		return mapper.getAbleCoupons(params);
	}

	public Map<String, Object> getOrderCouponUseInfo(Map<String, Object> params) {
		return mapper.getOrderCouponUseInfo(params);
	}

	public Map<String, Object> getCouponInfo(String couponid) {
		return mapper.getCouponInfo(couponid);
	}

	public void addCouponUseOrder(Map<String, Object> usecouponp) {
		mapper.addCouponUseOrder(usecouponp);
	}

	public void updateCouponUseOrder(Map<String, Object> usecouponp) {
		mapper.updateCouponUseOrder(usecouponp);
	}

	public void doComplaint(Map<String, Object> params) {
		mapper.doComplaint(params);
	}

	public Map<String, Object> getComplaintbyOrderno(String orderno) {
		return mapper.getComplaintbyOrderno(orderno);
	}

	public Map<String, Object> getHighestAbleCoupon(Map<String, Object> couponp) {
		return mapper.getHighestAbleCoupon(couponp);
	}

	public void updateCouponDiscountamount(Map<String, Object> useinfo) {
		mapper.updateCouponDiscountamount(useinfo);
	}

	public void addCouponDetail(Map<String, Object> params) {
		mapper.addCouponDetail(params);
	}

	public void useCoupon(Map<String, Object> useinfo) {
		mapper.useCoupon(useinfo);
	}

	public void deleteUseCouponOrder(Map<String, Object> usecouponp) {
		mapper.deleteUseCouponOrder(usecouponp);
	}
}

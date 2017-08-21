package com.szyciov.operate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.entity.coupon.CouponDetail;
import com.szyciov.enums.coupon.CouponActivityEnum;
import com.szyciov.lease.entity.OrgUserExpenses;
import com.szyciov.lease.param.OrganUserAccountQueryParam;
import com.szyciov.op.entity.PeUser;
import com.szyciov.op.entity.PeUserExpenses;
import com.szyciov.op.entity.PeUseraccount;
import com.szyciov.operate.dao.OpUserAccountDao;
import com.szyciov.util.GUIDGenerator;
import com.szyciov.util.PageBean;

@Service("opUserAccountService")
public class OpUserAccountService {
	private OpUserAccountDao dao;

	@Resource(name = "OpUserAccountDao")
	public void setDao(OpUserAccountDao dao) {
		this.dao = dao;
	}
	
	public PageBean getOpUserAccountByQuery(OrganUserAccountQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<PeUser> list = getOpUserAccountListByQuery(queryParam);
		int iTotalRecords = getOpUserAccountListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
	
	public List<PeUser> getOpUserAccountListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return dao.getOpUserAccountListByQuery(organUserAccountQueryParam);
    }
	
    public int getOpUserAccountListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return dao.getOpUserAccountListCountByQuery(organUserAccountQueryParam);
    }
    
    public PageBean getUserExpensesByQuery(OrganUserAccountQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<OrgUserExpenses> list = getUserExpensesListByQuery(queryParam);
		int iTotalRecords = getUserExpensesListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
    
    public List<OrgUserExpenses> getUserExpensesListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return dao.getUserExpensesListByQuery(organUserAccountQueryParam);
    }
	
    public int getUserExpensesListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return dao.getUserExpensesListCountByQuery(organUserAccountQueryParam);
    }
    
    public List<Map<String, Object>> getExistUserList(String nameAccount) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("nameAccount", nameAccount);
		return dao.getExistUserList(map);
	}
	
    public List<OrgUserExpenses> getUserExpensesListExport(OrganUserAccountQueryParam organUserAccountQueryParam) {
		return dao.getUserExpensesListExport(organUserAccountQueryParam);
	}
    public PeUser admoney(PeUser peUser) {
    	PeUser aa  = dao.admoney(peUser);
    	if(aa == null){
    		//增加一个账户
    		PeUseraccount peUseraccount = new PeUseraccount();
    		peUseraccount.setUserid(peUser.getId());
    		peUseraccount.setBalance(peUser.getBalance().doubleValue());
    		peUseraccount.setId(GUIDGenerator.newGUID());
    		dao.insertAccount(peUseraccount);
    		aa.setBalance(peUser.getBalance());
    		aa.setAccount(peUser.account);
    		return aa;
    	}
		return dao.admoney(peUser);
	}
    public Map<String,String> admoneyOk(PeUser peUser) {
    	Map<String,String> ret = new HashMap<>();
    	ret.put("ResultSign", "Successful");
		ret.put("MessageKey", "保存成功");
		//后台判断传过来的值
		Pattern pattern = Pattern.compile("^[+]?\\d*([.]\\d{0,1})?$",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(peUser.balance.toString());
		if(matcher.matches() && peUser.balance.floatValue() <=10000){
		  dao.admoneyOk(peUser);
		  PeUserExpenses peUserExpenses = new PeUserExpenses();
		  PeUser peUserA = dao.admoney(peUser);
		  peUserExpenses.setId(GUIDGenerator.newGUID());
		  peUserExpenses.setBalance(peUserA.getBalance());
		  peUserExpenses.setUserid(peUser.id);
		  peUserExpenses.setCreater(peUser.getAccount());
		  peUserExpenses.setUpdater(peUser.getAccount());
		  peUserExpenses.setAmount(peUser.getBalance());
		  dao.addPeuserexpenses(peUserExpenses);
		}
		 return ret;
	}
    
    public PageBean getCouponDetailByQuery(OrganUserAccountQueryParam queryParam) {
		PageBean pageBean = new PageBean();
		pageBean.setsEcho(queryParam.getsEcho());
		List<CouponDetail> list = getCouponDetailListByQuery(queryParam);
		for (CouponDetail coupon : list) {
			if (CouponActivityEnum.USE_TYPE_DESIGNATE.code.equals(coupon.getUsetype())) {
				List<String> citylist = getCouponUseCityById(coupon.getId());
				StringBuilder city = new StringBuilder();
				for (String cityname : citylist) {
					city.append(cityname).append("、");
				}
				coupon.setCity(city.toString().substring(0, city.length() - 1));
			} else {
				coupon.setCity("");
			}
		}
		int iTotalRecords = getCouponDetailListCountByQuery(queryParam);
		int iTotalDisplayRecords = iTotalRecords;
		pageBean.setiTotalDisplayRecords(iTotalDisplayRecords);
		pageBean.setiTotalRecords(iTotalRecords);
		pageBean.setAaData(list);

		return pageBean;
	}
    
    public List<CouponDetail> getCouponDetailListByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return dao.getCouponDetailListByQuery(organUserAccountQueryParam);
    }
    
    public int getCouponDetailListCountByQuery(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	return dao.getCouponDetailListCountByQuery(organUserAccountQueryParam);
    }
    
    public List<String> getCouponUseCityById(String couponid) {
    	return dao.getCouponUseCityById(couponid);
    }
    
    public List<CouponDetail> getCouponDetailListExport(OrganUserAccountQueryParam organUserAccountQueryParam) {
    	List<CouponDetail> list = dao.getCouponDetailListExport(organUserAccountQueryParam);
    	for (CouponDetail coupon : list) {
			if (CouponActivityEnum.USE_TYPE_DESIGNATE.code.equals(coupon.getUsetype())) {
				List<String> citylist = getCouponUseCityById(coupon.getId());
				StringBuilder city = new StringBuilder();
				for (String cityname : citylist) {
					city.append(cityname).append("、");
				}
				coupon.setCity(city.toString().substring(0, city.length() - 1));
			} else {
				coupon.setCity("");
			}
		}
    	return list;
    }
}

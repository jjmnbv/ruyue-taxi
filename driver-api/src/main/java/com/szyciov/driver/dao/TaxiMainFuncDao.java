package com.szyciov.driver.dao;


import com.szyciov.driver.entity.OrderStatistics;
import com.szyciov.driver.entity.PubDriverNews;
import com.szyciov.driver.mapper.TaxiMainFuncMapper;
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
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;



/**
  * @ClassName TaxiMainFuncDao
  * @author Efy Shu
  * @Description 出租车侧边栏功能Dao
  * @date 2017年3月23日 09:34:23
  */ 
@Repository("TaxiMainFuncDao")
public class TaxiMainFuncDao{

	/**
	  *依赖
	  */
	private TaxiMainFuncMapper taximainfuncmapper;

	/**
	  *依赖注入
	  */
	@Resource
	public void setTaxiMainFuncMapper(TaxiMainFuncMapper taximainfuncmapper){
		this.taximainfuncmapper=taximainfuncmapper;
	}

	/**
	 * 获取司机钱包
	 * @param driver
	 * @return
	 */
	public PubDriverAccount getPurse(PubDriver param){
		return taximainfuncmapper.getPurse(param);
	}
	
	/**
	 * 获取司机钱包
	 * @param driver
	 * @return
	 */
	public List<PubDriverExpenses> getPurseExpenses(PurseParam param){
		return taximainfuncmapper.getPurseExpenses(param);
	}
	
	/**
	 * 保存提现申请
	 * @param param
	 * @return
	 */
	public boolean savePubWithDraw(PubWithdraw param){
		taximainfuncmapper.savePubWithDraw(param);
		return true;
	}
	
	/**
	 * 从最后一次提现申请获取银行卡号
	 * @param param
	 * @return
	 */
	public PubWithdraw getLastBankCard(PubWithdraw param){
		return taximainfuncmapper.getLastBankCard(param);
	}
	
	/**
	 * 获取出租车我的贡献
	 * @param param
	 * @return
	 */
	public List<OrderStatistics> getTaxiOrderStatistics(PurseParam param){
		List<OrderStatistics> list = taximainfuncmapper.getTaxiOrderStatistics(param);
		return list;
	}
	
	/**
	 * 获取出租车今日提现限额
	 * @param param
	 * @return
	 */
	public double getTodayWithDrawLimit(PubWithdraw param){
		return taximainfuncmapper.getTodayWithDrawLimit(param);
	}
	
	/**
	 * 保存/更新钱包信息
	 * @param param
	 * @return
	 */
	public boolean saveOrUpdatePubDriverAccount(PubDriverAccount param){
		taximainfuncmapper.saveOrUpdatePubDriverAccount(param);
		return true;
	}

	
	/**
	 * 保存明细交易
	 * @param param
	 * @return
	 */
	public boolean savePubDriverExpenses(PubDriverExpenses param){
		taximainfuncmapper.savePubDriverExpenses(param);
		return true;
	}

    public PassengersPayDetail getOpTaxiOrderByOrderNo(String orderNo){
        return taximainfuncmapper.getOpTaxiOrderByOrderNo(orderNo);
    }

    public void savePeUserExpenses(PeUserExpenses peUserExpenses){
        this.taximainfuncmapper.savePeUserExpenses(peUserExpenses);
    }
	
	/**
	 * 检查支付宝开通状态
	 * @param param
	 * @return
	 */
	public boolean checkAlipayAccount(PubDriver param){
		return taximainfuncmapper.checkAliPayAccount(param);
	}
	
	/**
	 * 检查微信开通状态
	 * @param param
	 * @return
	 */
	public boolean checkWeChatAccount(PubDriver param){
		return taximainfuncmapper.checkWeChatAccount(param);
	}
	
	/**
	 * 获取司机第三方交易记录
	 * @param param
	 * @return
	 */
	public PubDriverTradingrecord getDriverTradeRecord(PubDriverTradingrecord param){
		return taximainfuncmapper.getDriverTradeRecord(param);
	}
	
	/**
	 * 保存司机第三方交易记录
	 * @param param
	 * @return
	 */
	public boolean saveDriverTradeRecord(PubDriverTradingrecord param){
		taximainfuncmapper.saveDriverTradeRecord(param);
		return true;
	}
	
	/**
	 * 按类型获取司机消息
	 * @param param
	 * @return
	 */
	public List<PubDriverNews> getDriverMessageByType(DriverMessageParam param){
		return taximainfuncmapper.getDriverMessageByType(param);
	}
	
	/**
	 * 获取指定司机消息
	 * @param param
	 * @return
	 */
	public PubDriverNews getDriverMessagesById(DriverMessageParam param){
		return taximainfuncmapper.getDriverMessageById(param);
	}

    /**
     * 保存租赁端支付后的消息
     * @param news 消息
     */
	public void saveLeUserNews(LeUserNews news){
        this.taximainfuncmapper.saveLeUserNews(news);
    }

    /**
     * 获取租赁公司下超级管理员和财务管理员Id
     * @param companyId 租赁公司id
     * @return 人员Id
     */
    public List<String> getLeUserIds(String companyId){
	    return this.taximainfuncmapper.getLeUserIds(companyId);
    }

    /**
     * 保存运管支付后的消息
     * @param news 消息
     */
    public void saveOpUsersNews(OpUserNews news){
        this.taximainfuncmapper.saveOpUsersNews(news);
    }

    /**
     * 获取运管端超级管理员和财务管理员Id
     * @return 人员Id
     */
    public List<String> getOpUserIds(){
        return this.taximainfuncmapper.getOpUserIds();
    }

}

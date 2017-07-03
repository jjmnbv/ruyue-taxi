package com.szyciov.carservice.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.szyciov.carservice.dao.OrderBillDao;
import com.szyciov.carservice.util.sendservice.FactoryProducer;
import com.szyciov.carservice.util.sendservice.factory.SendServiceFactory;
import com.szyciov.carservice.util.sendservice.sendmethod.SendMethodHelper;
import com.szyciov.carservice.util.sendservice.sendrules.SendRuleHelper;
import com.szyciov.entity.BillNO;
import com.szyciov.lease.entity.OrgOrgan;
import com.szyciov.lease.entity.OrgOrganBill;
import com.szyciov.util.GUIDGenerator;

@Service("orderBillService")
public class OrderBillService {

	public OrderBillDao orderBillDao;

	@Resource(name="orderBillDao")
	public void setOrderBillDao(OrderBillDao orderBillDao) {
		this.orderBillDao = orderBillDao;
	}
	
	/**
	 * 批量生成机构账单数据
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, Object> batchCreateOrganBill() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat msdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<OrgOrgan> organList = getBillOrgan();
		boolean first = true;
		int second = 1;
		for(OrgOrgan organ : organList) {
			OrgOrganBill orgOrganBill = new OrgOrganBill();
			calendar.setTime(new Date());			
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));			
			orgOrganBill.setEndTime(dateFormat.format(calendar.getTime()));
			orgOrganBill.setOrganId(organ.getId());
			String leasesCompanyId = orderBillDao.getLeasesCompanyByOrgan(organ.getId());
			orgOrganBill.setLeasesCompanyId(leasesCompanyId);
			organ.setCompanyId(leasesCompanyId);
			// 如果是季度账单
			if("1".equalsIgnoreCase(organ.getBillType())) {
				orgOrganBill.setName(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1)/3 + "季度账单");
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 2);
				orgOrganBill.setStartTime(dateFormat.format(calendar.getTime()));
				orgOrganBill.setSource("1");
			} else {
				orgOrganBill.setName(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月账单");
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				orgOrganBill.setStartTime(dateFormat.format(calendar.getTime()));
				orgOrganBill.setSource("0");
			}

			if (first) {
				calendar.setTime(new Date());
				second = calendar.get(Calendar.SECOND);
				first = false;
			} else {
				calendar.setTime(new Date());
				calendar.set(Calendar.SECOND, ++second);
			}
			
			try {
				orgOrganBill.setCreateTime(msdateFormat.parse(msdateFormat.format(calendar.getTime())));
				orgOrganBill.setUpdateTime(msdateFormat.parse(msdateFormat.format(calendar.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// 获取账单总金额
			BigDecimal orderAmount = getOrderAmountByQuery(orgOrganBill);
			orgOrganBill.setMoney(orderAmount);
			// String uuid = GUIDGenerator.newGUID();
			String uuid = getBillNo(organ);
			orgOrganBill.setId(uuid);
			// 插入数据到机构账单表
			orderBillDao.createOrganbill(orgOrganBill);

			// 插入数据到机构账单状态记录表
			// 1-生成账单
			calendar.setTime(new Date());
			createOrganBillState(orgOrganBill.getRemark(), uuid, "1", msdateFormat.format(calendar.getTime()));
			// 新生成（2-待核对）   重新生成（9-重新生成）
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 1);
			createOrganBillState(orgOrganBill.getRemark(), uuid, "2", msdateFormat.format(calendar.getTime()));
			
			// 插入数据到机构账单明细表
			createOrganBillDetailsAuto(orgOrganBill, uuid);
		}
		return resultMap;
	}

	/**
	 * 获取待生成账单机构
	 * @return
	 */
	private List<OrgOrgan> getBillOrgan() {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		if(month == 1 || month == 4 || month == 7 || month == 10) {
			paramsMap.put("quarter", Boolean.TRUE);
		}
		
		paramsMap.put("day", day);
		
		return orderBillDao.getBillOrgan(paramsMap);
	}
	
	public BigDecimal getOrderAmountByQuery(OrgOrganBill orgOrganBill) {
    	return orderBillDao.getOrderAmountByQuery(orgOrganBill);
    }
	
	public List<String> getOrderListByQuery(OrgOrganBill orgOrganBill) {
		return orderBillDao.getOrderListByQuery(orgOrganBill);
	}
	
	public void createOrganBillState(String comment, String billsId, String billState, String time) {
		String uuid = GUIDGenerator.newGUID();
		Map<String, String> organBillState = new HashMap<String, String>();
		organBillState.put("id", uuid);
		organBillState.put("billState", billState);
		organBillState.put("billsId", billsId);
		organBillState.put("comment", comment);
		organBillState.put("operationTime", time);
		organBillState.put("createTime", time);
		organBillState.put("updateTime", time);
		orderBillDao.createOrganBillState(organBillState);
	}
	
	
	public void createOrganBillDetailsAuto(OrgOrganBill orgOrganBill, String billsId) {
		List<String> orderList = getOrderListByQuery(orgOrganBill);
		if (orderList != null && orderList.size() > 0) {
			for (String order : orderList) {
				Map<String, String> organBillDetails = new HashMap<String, String>();
				String uuid = GUIDGenerator.newGUID();
				organBillDetails.put("id", uuid);
				organBillDetails.put("billsId", billsId);
				organBillDetails.put("orderId", order);
				orderBillDao.createOrganBillDetails(organBillDetails);
				
				// 改变订单状态  2-结算中
				orderBillDao.changeOrderStatusToBalance(order);
			}
		}
	}
	
	/**
	 * 获取账单编号
	 * @param organ
	 * @return
	 */
	public String getBillNo(OrgOrgan organ) {
		if(!BillNO.hasInstance()) {
			String maxBillNo = orderBillDao.getMaxBillNo();
			BillNO.getInstance().init(maxBillNo);
		}
		
		String companySeq = orderBillDao.getLeaseCompanySeq(organ.getCompanyId());
		
		return BillNO.getInstance().getBillNO(companySeq, organ.getRegorder());
	}
}

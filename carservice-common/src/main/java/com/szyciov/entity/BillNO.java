package com.szyciov.entity;

import java.util.Date;

import com.szyciov.util.StringUtil;

/**
 * @ClassName OrderNO 
 * @author Efy Shu
 * @Description 
 * <pre>
 * 账单号号类
 * 说明：
 * 1、第1位字母和2、3位数字代表对账租赁公司在运管平台的注册顺序，字母顺序为A-Z
 * 2、第4位字母和5、6位数字代表对账机构在对账租赁公司的注册顺序，字母顺序为A-Z
 * 3、第7-14位数字为出账的日期
 * 4、第15-18位数字为流水号
 * 示例：A01A01201608310001
 * </pre>
 * @date 2016年11月26日 下午7:00:35
 */
public class BillNO {
	/**
	 * 流水号长度
	 */
	private int FLOWNO_LEN = 4;
	
	/**
	 * 帐单前缀(对账租赁公司在运管平台的注册顺序)
	 */
	private String leaseprefix;
	/**
	 * 帐单前缀(对账机构在对账租赁公司的注册顺序)
	 */
	private String organprefix;
	/**
	 * 生成日期(yyyyMMdd)
	 */
	private String dateTime;
	/**
	 * 流水号
	 */
	private int flowNO;
	/**
	 * 单例对象
	 */
	private static BillNO ins;
	
	/**
	 * 默认构造
	 */
	private BillNO() {}
	
	public static BillNO getInstance(){
		if(ins == null){
			ins = new BillNO();
		}
		return ins;
	}
	
	/**
	 * 判断是否已经初始化
	 * @return
	 */
	public synchronized static boolean hasInstance() {
		if(ins == null) return false;
		return true;
	}
	
	/**
	 * 重置
	 */
	public static void reset(){
		ins = null;
	}
	
	/**
	 * 初始化
	 * @param orderno
	 */
	public void init(String orderno){
		//如果最后一条记录不是当前日期,则重置为0;
		if(orderno == null || !StringUtil.formatDate(new Date(), "yyyyMMdd").equals(orderno.substring(6,14))){
			flowNO = 0;
		}else{
			flowNO = Integer.parseInt(orderno.substring(14,18));
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		//租赁公司注册顺序前缀
		sb.append(leaseprefix);
		//机构前缀注册顺序前缀
		sb.append(organprefix);
		//帐单号生成时间(yyyyMMdd)
		dateTime = StringUtil.formatDate(new Date(), "yyyyMMdd");
		sb.append(dateTime);
		//流水号
		flowNO = flowNO+1;
		sb.append(StringUtil.formatNumToLength(flowNO, FLOWNO_LEN, null));
		return sb.toString();
	}
	
	/**
	 * 获取帐单号
	 * @param leaseprefix 租赁公司前缀
	 * @param organprefix 机构前缀
	 * @return
	 */
	public synchronized String getBillNO(String leaseprefix,String organprefix){
		this.leaseprefix = leaseprefix;
		this.organprefix = organprefix;
		return ins.toString();
	}
	
	/**  
	 * 设置帐单前缀(对账租赁公司在运管平台的注册顺序)  
	 * @param leaseprefix 帐单前缀(对账租赁公司在运管平台的注册顺序)  
	 */
	public void setLeaseprefix(String leaseprefix) {
		this.leaseprefix = leaseprefix;
	}

	/**  
	 * 设置帐单前缀(对账机构在对账租赁公司的注册顺序)  
	 * @param organprefix 帐单前缀(对账机构在对账租赁公司的注册顺序)  
	 */
	public void setOrganprefix(String organprefix) {
		this.organprefix = organprefix;
	}
	
	public static void main(String[] args) {
		String billNO = "";
		billNO = BillNO.getInstance().getBillNO("A01", "B01");
		System.out.println(billNO);
		billNO = BillNO.getInstance().getBillNO("A02", "B02");
		System.out.println(billNO);
	}
}

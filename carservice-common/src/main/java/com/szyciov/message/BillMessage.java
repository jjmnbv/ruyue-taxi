package com.szyciov.message;

import java.util.List;


/**
 * 账单消息对象
 * @author admin
 *
 */
public class BillMessage extends BaseMessage {
	
	public static final String LOWBALANCE = "余额不足";
	
	public static final String ORGPAYED = "机构已付款";
	
	public static final String BACKBALANCE = "退回账单";
	
	private String title;
	
	private String content;
	
	private List<String> userids;
	
	private String messagetype;
	
	public BillMessage(String title,String content,List<String> userids,String messagetype){
		this.title = title;
		this.content = content;
		this.userids = userids;
		this.messagetype = messagetype;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getUserids() {
		return userids;
	}

	public void setUserids(List<String> userids) {
		this.userids = userids;
	}

	public String getMessagetype() {
		return messagetype;
	}

	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}

	public static String getLowbalance() {
		return LOWBALANCE;
	}

	public static String getOrgpayed() {
		return ORGPAYED;
	}

	public static String getBackbalance() {
		return BACKBALANCE;
	}

	@Override
	public void send() {
		if(LOWBALANCE.equalsIgnoreCase(messagetype)){
			//余额不足
			lowBalance();
		}else if(ORGPAYED.equalsIgnoreCase(messagetype)){
			//机构支付
			orgPayed();
		}else if(BACKBALANCE.equalsIgnoreCase(messagetype)){
			//退回账单
			backBalance();
		}
	}

	/**
	 * 余额不足消息
	 */
	private void lowBalance(){
		System.out.println("这里是账单余额不足消息，待实现");
	}
	
	/**
	 * 机构已支付消息
	 */
	private void orgPayed(){
		System.out.println("这里是机构已支付消息，待实现");
	}
	
	/**
	 * 退回账单消息
	 */
	private void backBalance(){
		System.out.println("这里是退回账单消息，待实现");
	}
}

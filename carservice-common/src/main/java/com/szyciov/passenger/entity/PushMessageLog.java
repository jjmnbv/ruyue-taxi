package com.szyciov.passenger.entity;

/**
 * 手机推送消息日志
 * @author xuxxtr
 *
 */
public class PushMessageLog {

	private String id;
	/**
	 * 推送内容
	 */
	private String content;
	/**
	 * 推送平台
	 */
	private String platform;
	/**
	 * 推送返回信息
	 */
	private String receiptState;
	/**
	 * 推送状态
	 */
	private String sendState;
	public PushMessageLog() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getReceiptState() {
		return receiptState;
	}
	public void setReceiptState(String receiptState) {
		this.receiptState = receiptState;
	}
	public String getSendState() {
		return sendState;
	}
	public void setSendState(String sendState) {
		this.sendState = sendState;
	}
}

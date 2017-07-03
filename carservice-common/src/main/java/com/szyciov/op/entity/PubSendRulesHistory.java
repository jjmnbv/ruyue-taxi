package com.szyciov.op.entity;

import java.util.Date;

/**
 * 网约车派单规则历史
 * @author xuxxtr
 *
 */
public class PubSendRulesHistory extends PubSendRules{

	
	/**
	 * 操作类型
	 * 0-新增操作，1-修改操作，2-启用操作，3-禁用操作
	 */
	private String operateType;
	
	/**
	 * 所属规则
	 */
	private String sendRulesId;
	
	/**
	 * 操作人
	 */
	private String operator;
	
	/**
	 * 操作人名字
	 */
	private String operateName;
	
	/**
	 * 操作时间
	 */
	private String operateTime;
	
	public PubSendRulesHistory() {
		super();
	}


	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getSendRulesId() {
		return sendRulesId;
	}

	public void setSendRulesId(String sendRulesId) {
		this.sendRulesId = sendRulesId;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getOperateName() {
		return operateName;
	}


	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}


	public String getOperateTime() {
		return operateTime;
	}


	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
}

/**
 * 
 */
package com.ry.taxi.base.query;

import org.apache.http.impl.execchain.MainClientExec;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Title:BaseResponse.java
 * @Package com.ry.taxi.base.common
 * @Description ����������
 * @author zhangdd
 * @date 2017��7��10�� ����3:32:08
 * @version 
 *
 * @Copyrigth  ��Ȩ���� (C) 2017 ����Ѷ����Ϣ�Ƽ����޹�˾.
 */
public class BaseResult<T> {

	
	@JsonProperty("Cmd")
	private String cmd;
	
	@JsonProperty("result")
	private Integer result;
	
	@JsonProperty("Remark")
	private String remark;
	
	@JsonProperty("data")
	private T Data;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public T getData() {
		return Data;
	}

	public void setData(T data) {
		Data = data;
	}
	

}

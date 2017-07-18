/**
 * 
 */
package com.ry.taxi.base.query;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @Title:RealTaxiMonitor.java
 * @Package com.ry.taxi.sync.monitor
 * @Description
 * @author zhangdd
 * @date 2017年7月10日 下午4:17:37
 * @version 
 *
 * @Copyrigth  版权所有 (C) 2017 广州讯心信息科技有限公司.
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

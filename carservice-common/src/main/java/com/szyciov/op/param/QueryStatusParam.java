package com.szyciov.op.param;

import java.util.List;

/**
 * 设备状态查询参数 <一句话功能简述> <功能详细描述>
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  袁金林
 * @version  [版本号, 2017年5月03日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QueryStatusParam extends BasePageParam {
	/**
	 * 关联设备Id
	 */
	private String eqpId;
	/**
	 * 设备IMEI
	 */
	private String imei;
	/**
	 * 关联类型 必须(1_车辆;)默认车辆
	 */
	private Integer relationType;
	/**
	 * 纠偏选项 int 1_不纠偏;2_百度纠偏;默认不纠偏
	 */
	private Integer processOption;
	
	private List<String> typeArray;
	
	public Integer getRelationType() {
		return relationType;
	}
	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}
	public Integer getProcessOption() {
		return processOption;
	}
	public void setProcessOption(Integer processOption) {
		this.processOption = processOption;
	}
	public String getEqpId() {
		return eqpId;
	}
	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public List<String> getTypeArray() {
		return typeArray;
	}
	public void setTypeArray(List<String> typeArray) {
		this.typeArray = typeArray;
	}

}

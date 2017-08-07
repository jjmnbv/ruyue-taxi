package com.szyciov.op.param;

/**
 * 区域栅栏返回值 <一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年3月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class QueryAreaFence {
	/**
	 * 栅栏Id
	 */
	private String id;
	/**
	 * 栅栏名称
	 */
	private String name;
	/**
	 * 受控设备数
	 */
	private Integer count;
	/**
	 * 允许运行城市 格式：xx城市|xx城市
	 */
	private String allowRunCity;
	/**
	 * 城市表ID 格式：xx|xx（用于修改时树形勾选）t
	 */
	private String cityId;
	/**
	 * 启用状态
	 */
	private Integer switchState;
	/**
	 * 最后操作人
	 */
	private String operateStaff;
	/**
	 * 最后操作时间
	 */
	private String operateTime;
   
    //操作
  	public String cz;
  	
	public String getCz() {
		return cz;
	}

	public void setCz(String cz) {
		this.cz = cz;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getAllowRunCity() {
		return allowRunCity;
	}

	public void setAllowRunCity(String allowRunCity) {
		this.allowRunCity = allowRunCity;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Integer getSwitchState() {
		return switchState;
	}

	public void setSwitchState(Integer switchState) {
		this.switchState = switchState;
	}

	public String getOperateStaff() {
		return operateStaff;
	}

	public void setOperateStaff(String operateStaff) {
		this.operateStaff = operateStaff;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

}

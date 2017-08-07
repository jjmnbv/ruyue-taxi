package com.szyciov.op.param;

/**
 * 设置电子围栏 <一句话功能简述> <功能详细描述>
 * 
 * @author huangyanan
 * @version [版本号, 2017年3月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SetElectronicFenceParam extends BaseParam {
	/**
	 * 电子围栏设置Id
	 */
	private String electronicFenceId;
	/**
	 * 栅栏名称
	 */
	private String name;
	/**
	 * 开关状态 1_开;2_关；新增时，默认关
	 */
	private Integer switchState;
	/**
	 * 区域类型 区域类型(1_矩形区域;2_圆形区域;3_多边形区域)
	 */
	private Integer shape;
	/**
	 * 报警类型 报警类型(1_区域内报警;2_区域外)
	 */
	private Integer alartType;
	/**
	 * 半径
	 */
	private Double radius;
	/**
	 * 电子围栏区域点经纬度
	 */
	private String points;
	/**
	 * 电子围栏区域面积
	 */
	private Double calculatitudee;
	/**
	 * 最后操作人Id
	 */
	private String operateId;
	/**
	 * 最后操作人
	 */
	private String operateStaff;
	/**
	 * 操作类型 1_新增;2_修改;3_启用/停用
	 */
	private Integer operateType;

	public String getElectronicFenceId() {
		return electronicFenceId;
	}

	public void setElectronicFenceId(String electronicFenceId) {
		this.electronicFenceId = electronicFenceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSwitchState() {
		return switchState;
	}

	public void setSwitchState(Integer switchState) {
		this.switchState = switchState;
	}

	public Integer getShape() {
		return shape;
	}

	public void setShape(Integer shape) {
		this.shape = shape;
	}

	public Integer getAlartType() {
		return alartType;
	}

	public void setAlartType(Integer alartType) {
		this.alartType = alartType;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public Double getCalculatitudee() {
		return calculatitudee;
	}

	public void setCalculatitudee(Double calculatitudee) {
		this.calculatitudee = calculatitudee;
	}

	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}

	public String getOperateStaff() {
		return operateStaff;
	}

	public void setOperateStaff(String operateStaff) {
		this.operateStaff = operateStaff;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

}

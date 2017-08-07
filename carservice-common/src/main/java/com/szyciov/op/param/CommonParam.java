package com.szyciov.op.param;

/**
 * 根据ID查询数据库实体/删除数据 参数 <一句话功能简述> <功能详细描述>
 * 
 * @author 黄亚楠
 * @version [版本号, 2017年4月10日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CommonParam extends BasePageParam{
	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * 关键字
	 */
	private String key;
	/**
	 * 数据状态 正常
	 */
	private Integer dataStatusNormal;
	/**
	 * 数据状态删除
	 */
	private Integer dataStatusDelete;
	/**
	 * 最后操作人ID
	 */
	private String operateId;
	/**
	 * 最后操作人
	 */
	private String operateStaff;
	/**
	 * 最后操作时间
	 */
	private String operateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getDataStatusNormal() {
		return dataStatusNormal;
	}

	public void setDataStatusNormal(Integer dataStatusNormal) {
		this.dataStatusNormal = dataStatusNormal;
	}

	public Integer getDataStatusDelete() {
		return dataStatusDelete;
	}

	public void setDataStatusDelete(Integer dataStatusDelete) {
		this.dataStatusDelete = dataStatusDelete;
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

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
